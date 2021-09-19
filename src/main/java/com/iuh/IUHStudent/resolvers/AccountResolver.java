package com.iuh.IUHStudent.resolvers;

import com.iuh.IUHStudent.entity.Account;
import com.iuh.IUHStudent.service.AccountService;
import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class AccountResolver implements GraphQLResolver<Account> {
    @Autowired
    private AccountService accountService;

    @PreAuthorize("isAuthenticated()")
    public String getToken(Account account) {
        return accountService.getToken(account);
    }
}
