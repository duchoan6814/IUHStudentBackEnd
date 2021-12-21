package com.iuh.IUHStudent;

import com.iuh.IUHStudent.entity.Account;
import com.iuh.IUHStudent.entity.ChuyenNganh;
import com.iuh.IUHStudent.entity.Lop;
import com.iuh.IUHStudent.entity.SinhVien;
import com.iuh.IUHStudent.entityinput.account_input.AccountInput;
import com.iuh.IUHStudent.repository.AccountRepository;
import com.iuh.IUHStudent.service.AccountService;
import com.iuh.IUHStudent.service.ChuyenNganhService;
import com.iuh.IUHStudent.service.SinhVienService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@RequiredArgsConstructor
public class IuhStudentApplication {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ChuyenNganhService chuyenNganhService;

    @Autowired
    private SinhVienService sinhVienService;

    public static void main(String[] args) {SpringApplication.run(IuhStudentApplication.class, args);}

    @Bean
    public Filter OpenFilter() {
        return new OpenEntityManagerInViewFilter();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {


//        List<ChuyenNganh> chuyenNganhs = new ArrayList<>();
//        chuyenNganhs = chuyenNganhService.getChuyenNganhByKhoaVienId(3);
        AccountInput input = AccountInput.builder()
                .userName("admin")
                .password("admin")
                .build();

        boolean isExistAccount = accountService.exists(input);

        if (isExistAccount) return;


        accountRepository.saveAndFlush(Account
                .builder()
                .username(input.getUserName())
                .password(passwordEncoder.encode(input.getPassword()))
                .roles(Set.of("ADMIN"))
                .build());

    }
}


