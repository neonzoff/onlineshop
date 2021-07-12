package com.neonzoff.onlineshop.service;

import com.neonzoff.onlineshop.dao.UserRepository;
import com.neonzoff.onlineshop.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author Tseplyaev Dmitry
 */
@Service
public class UserServiceImpl implements UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository repository;
    private List<UserModel> users;

    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository repository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.repository = repository;
//        users = repository.findAll();
        users = allUsers();
    }

    @Override
    public Page<UserModel> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<UserModel> list;

        if (users.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, users.size());
            list = users.subList(startItem, toIndex);
        }

        Page<UserModel> userPage = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), users.size());

        return userPage;
    }

    @Override
    public boolean createUser(UserModel userModel) {
        if (repository.findByUsername(userModel.getUsername()).isPresent()) {
            return false;
        }
        userModel.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));
        repository.save(userModel);
        return true;
    }

    @Override
    public List<UserModel> allUsers() {
        return repository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return repository.findByUsername(s).get();
    }
}
