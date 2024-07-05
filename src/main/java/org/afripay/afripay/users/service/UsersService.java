package org.afripay.afripay.users.service;

import org.afripay.afripay.users.dto.UsersDTO;

public interface UsersService {
    UsersDTO getUserProfile(String name);
}