package com.rbalasa.accountancyapp.service;

import com.rbalasa.accountancyapp.model.User;
import com.rbalasa.accountancyapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;

    public User store(User user) {
        if (user != null) {
            return userRepository.save(user);
        }
        return null;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

}
