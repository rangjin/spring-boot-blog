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

@Service
@RequiredArgsConstructor
public class AdminService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final AdminRepository adminRepository;

    public Long register(AdminRequestDto.RegisterDto dto) {
        Admin admin = Admin.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(AdminRole.Waiting)
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
