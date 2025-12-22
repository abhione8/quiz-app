package com.learn.quizapp.service;

import com.learn.quizapp.dao.UserDetailDao;
import com.learn.quizapp.model.UserDetail;
import com.learn.quizapp.model.User;
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
        User user= userDetailDao.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("User not found");
        }
        return new UserDetail(user);
    }

    public boolean saveUser(User user) {
        if(userDetailDao.findByUsername(user.getUsername())!=null){
            return false;
        }
        if(user.getRole()==null || user.getRole().isEmpty()){
            user.setRole("ROLE_USER");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        userDetailDao.save(user);
        return true;
    }

}
