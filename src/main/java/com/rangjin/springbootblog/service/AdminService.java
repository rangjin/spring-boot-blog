package com.rangjin.springbootblog.service;

import com.rangjin.springbootblog.advice.exception.CustomAdminNotFoundException;
import com.rangjin.springbootblog.domain.admin.Admin;
import com.rangjin.springbootblog.domain.admin.AdminRepository;
import com.rangjin.springbootblog.domain.admin.AdminRole;
import com.rangjin.springbootblog.web.dto.AdminRequestDto;
import com.rangjin.springbootblog.web.dto.AdminResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class AdminService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final AdminRepository adminRepository;

    @PostConstruct
    public void init() {
        Admin admin = Admin.builder()
                .username("test")
                .password(passwordEncoder.encode("1234"))
                .role(AdminRole.Admin)
                .build();

        adminRepository.save(admin);
    }

    public Long register(AdminRequestDto.RegisterDto dto) {
        Admin admin = Admin.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(AdminRole.Admin)
                .build();

        return adminRepository.save(admin).getId();
    }

    public boolean existsByUserName(String username) {
        return adminRepository.existsByUsername(username);
    }

    public AdminResponseDto findByUsername(String username) {
        return new AdminResponseDto(adminRepository.findByUsername(username).orElseThrow(CustomAdminNotFoundException::new));
    }

    @Override
    public UserDetails loadUserByUsername(String userPk) throws UsernameNotFoundException {
        return adminRepository.findById(Long.valueOf(userPk)).orElseThrow(CustomAdminNotFoundException::new);
    }

}
