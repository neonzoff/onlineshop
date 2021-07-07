package com.neonzoff.onlineshop.service;

import com.neonzoff.onlineshop.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * @author Tseplyaev Dmitry
 */
public interface UserService extends UserDetailsService {
    boolean createUser(UserModel userModel);

    List<UserModel> allUsers();

    Page<UserModel> findPaginated(Pageable pageable);
}
