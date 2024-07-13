package org.afripay.afripay.jwt.service;

import org.afripay.afripay.users.dto.UsersDTO;
import org.afripay.afripay.users.models.Users;
import org.afripay.afripay.users.repo.UsersRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public JwtUserDetailsService(UsersRepository usersRepository, RedisTemplate<String, Object> redisTemplate) {
        this.usersRepository = usersRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String email;
        String password;
        List<GrantedAuthority> permissions = new ArrayList<>();

        Users adminUser = usersRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found with username: " + username));

        UsersDTO adminUserDTO = Users.getUsersDTO(adminUser);
        email = adminUserDTO.getEmail();
        password = adminUser.getPassword();
//        permissions = getGrantedAuthorities(adminUserDTO.getAdminRole().getPermissionNames());

//        log.info("admin permissions here {}", null);
        return new User(email, password, permissions);
    }


//
//    private List<GrantedAuthority> getGrantedAuthorities(List<PermissionDTO> permissionDTOS) {
//        return permissionDTOS.stream().map(permissionDTO -> new SimpleGrantedAuthority(permissionDTO.getName().name()))
//                .collect(Collectors.toList());
//    }

    public void addToBlacklist(String token) {
        String key = "token_" + token;
        redisTemplate.opsForSet().add(key, token);
    }

    public boolean isBlacklisted(String token) {
        String key = "token_" + token;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

}