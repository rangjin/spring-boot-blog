package com.rangjin.springbootblog.service;

import com.rangjin.springbootblog.domain.admin.Admin;
import com.rangjin.springbootblog.domain.admin.AdminRepository;
import com.rangjin.springbootblog.web.dto.AdminRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService implements UserDetailsService {

    private final AdminRepository adminRepository;

    public Long register(AdminRequestDto dto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Admin admin = Admin.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        return adminRepository.save(admin).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username);

        List<GrantedAuthority> authorities = new ArrayList<>();

        return new User(admin.getUsername(), admin.getPassword(), authorities);
    }

}
