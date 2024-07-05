package org.afripay.afripay.users.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.afripay.afripay.users.dto.UsersDTO;
import org.springframework.beans.BeanUtils;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
public class Users {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
//    private String role;

    public static UsersDTO getUsersDTO(Users adminUser) {
        UsersDTO adminUserDTO = new UsersDTO();
        BeanUtils.copyProperties(adminUser, adminUserDTO);

        return adminUserDTO;
    }
    
}