package com.iuh.IUHStudent.resolvers;

import com.iuh.IUHStudent.entity.Lop;
import com.iuh.IUHStudent.repository.LopRepository;
import com.iuh.IUHStudent.response.AccountResponse;
import com.iuh.IUHStudent.response.ErrorsResponse;
import com.iuh.IUHStudent.response.LopsResponse;
import com.iuh.IUHStudent.response.ResponseStatus;
import com.iuh.IUHStudent.service.AccountService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QueryResolver implements GraphQLQueryResolver {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private LopRepository lopRepository;

    @PreAuthorize("isAuthenticated()")
    public LopsResponse getLops() {
        List<Lop> lops = lopRepository.findAll();
        return LopsResponse.builder()
                .status(ResponseStatus.OK)
                .data(lops).build();
    }

    @PreAuthorize("isAnonymous()")
    public AccountResponse login(String username, String password) {
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(username, password);
        try {
            SecurityContextHolder.getContext().setAuthentication(authenticationProvider.authenticate(credentials));
            return AccountResponse
                    .builder()
                    .status(ResponseStatus.OK)
                    .message("Đăng nhập thành công.")
                    .data(accountService.getCurrentAccount())
                    .build();
        } catch (AuthenticationException ex) {
            return AccountResponse
                    .builder()
                    .status(ResponseStatus.ERROR)
                    .message("Đăng nhập không thành công.")
                    .errors(new ArrayList<ErrorsResponse>() {
                        {
                            add(new ErrorsResponse("Tên tài khoản hoặc mật khẩu không đúng!"));
                        }
                    })
                    .build();
        }
    }
}
