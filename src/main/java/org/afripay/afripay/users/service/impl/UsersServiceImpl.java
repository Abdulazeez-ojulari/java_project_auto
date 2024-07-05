package org.afripay.afripay.users.service.impl;

import lombok.RequiredArgsConstructor;
import org.afripay.afripay.exceptions.GeneralException;
import org.afripay.afripay.users.dto.UsersDTO;
import org.afripay.afripay.users.models.Users;
import org.afripay.afripay.users.repo.UsersRepository;
import org.afripay.afripay.users.service.UsersService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    
    private final UsersRepository usersRepository;

    @Override
    public UsersDTO getUserProfile(String email) {
        
        return Users.getUsersDTO(usersRepository.findByEmail(email).orElseThrow(()-> new GeneralException("User not found")));
    }
}