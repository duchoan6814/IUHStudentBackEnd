package com.iuh.IUHStudent.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.iuh.IUHStudent.entity.Account;
import com.iuh.IUHStudent.entity.User;
import com.iuh.IUHStudent.entityinput.AccountInput;
import com.iuh.IUHStudent.entityinput.account_input.UpdatePasswordInput;
import com.iuh.IUHStudent.exception.BadTokenException;
import com.iuh.IUHStudent.exception.UserAlreadyExistsException;
import com.iuh.IUHStudent.exception.UserNotFoundException;
import com.iuh.IUHStudent.repository.AccountRepository;
import com.iuh.IUHStudent.security.JWTUserDetails;
import com.iuh.IUHStudent.security.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.iuh.IUHStudent.util.StreamUtils.collectionStream;
import static java.util.function.Predicate.not;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    private static final String ADMIN_AUTHORITY = "ADMIN";
    private static final String USER_AUTHORITY = "USER";
    private final Algorithm algorithm;
    private final JWTVerifier verifier;
    private final PasswordEncoder passwordEncoder;
    private final SecurityProperties properties;

    @Autowired
    private UserService userService;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByUsername(username).map(account -> getUserDetails(account, getToken(account))).orElseThrow(() -> new UsernameNotFoundException("Tài khoản hoặc mật khẩu không đúng!"));
    }

    @Transactional
    public JWTUserDetails loadAccountByToken(String token) {
        return getDecodedToken(token)
                .map(DecodedJWT::getSubject)
                .flatMap(accountRepository::findByUsername)
                .map(account -> getUserDetails(account, token))
                .orElseThrow(BadTokenException::new);
    }

    @Transactional
    public Account getCurrentAccount() {
        return Optional
                .ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getName)
                .flatMap(accountRepository::findByUsername)
                .orElse(null);
    }

    @Transactional
    public Account createAccount(User user, AccountInput input) {
        if (!exists(input)) {
            return accountRepository.saveAndFlush(Account
                    .builder()
                    .username(input.getUserName())
                    .password(passwordEncoder.encode(input.getPassword()))
                    .roles(Set.of(USER_AUTHORITY))
                    .user(user)
                    .build());
        } else {
            throw new UserAlreadyExistsException(input.getUserName());
        }
    }

    private boolean exists(AccountInput input) {
        return accountRepository.existsByUsername(input.getUserName());
    }

    @Transactional
    public Account updatePassword(Long userId, UpdatePasswordInput input) {
        Account account = accountRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        if (passwordEncoder.matches(input.getCurrentPassword(), account.getPassword())) {
            account.setPassword(passwordEncoder.encode(input.getNewPassword()));
        } else {
            throw new BadCredentialsException(account.getUsername());
        }
        return account;
    }


    public boolean isAdmin() {
        return Optional
                .ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getAuthorities)
                .stream()
                .flatMap(Collection::stream)
                .map(GrantedAuthority::getAuthority)
                .anyMatch(ADMIN_AUTHORITY::equals);
    }

    public boolean isAuthenticated() {
        return Optional
                .ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .filter(not(this::isAnonymous))
                .isPresent();
    }

    @Transactional
    public boolean deleteUser(Long userId) {
        if (accountRepository.existsById(userId)) {
            accountRepository.deleteById(userId);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public User promotePerson(Long personId) {
        User user = userService.getById(personId);
        user.getAccount().withRole(ADMIN_AUTHORITY);
        return user;
    }

    @Transactional
    public User unpromotePerson(Long personId) {
        User user = userService.getById(personId);
        user.getAccount().withoutRole(ADMIN_AUTHORITY);
        return user;
    }

    private boolean isAnonymous(Authentication authentication) {
        return authentication instanceof AnonymousAuthenticationToken;
    }

    @Transactional
    public String getToken(Account account) {
        Instant now = Instant.now();
        Instant expiry = Instant.now().plus(properties.getTokenExpiration());
        return JWT
                .create()
                .withIssuer(properties.getTokenIssuer())
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(expiry))
                .withSubject(account.getUsername())
                .sign(algorithm);
    }

    private Optional<DecodedJWT> getDecodedToken(String token) {
        try {
            return Optional.of(verifier.verify(token));
        } catch (JWTVerificationException ex) {
            return Optional.empty();
        }
    }

    private JWTUserDetails getUserDetails(Account account, String token) {
        return JWTUserDetails
                .builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .authorities(collectionStream(account.getRoles())
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()))
                .token(token)
                .build();
    }
}
