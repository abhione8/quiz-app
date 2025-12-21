package com.learn.quizapp.service;

import com.learn.quizapp.dao.UserDetailDao;
import com.learn.quizapp.model.UserDetail;
import com.learn.quizapp.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserDetailDao userDetailDao;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(15);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user= userDetailDao.findByUsername(username);
        if(user==null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        return new UserDetail(user);
    }

    public void saveUser(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userDetailDao.save(user);
    }

}
