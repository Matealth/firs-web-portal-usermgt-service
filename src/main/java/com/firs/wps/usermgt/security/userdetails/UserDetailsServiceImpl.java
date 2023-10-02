package com.firs.wps.usermgt.security.userdetails;

import com.firs.wps.usermgt.entity.User;
import com.firs.wps.usermgt.repo.RoleRepo;
import com.firs.wps.usermgt.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    private Locale locale = new Locale("en", "NG");
    private ResourceBundle messages = ResourceBundle.getBundle("messages", locale);

    public User save(User user) {
        return userRepo.save(user);
    }

    public Optional<User> findByUsername(String email){
        return userRepo.findByUsername(email);
    }


    public Optional<User> findByPhone(String phone){
        return userRepo.findByPhone(phone);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo
               .findByUsername(username)
               .orElseThrow(() -> new UsernameNotFoundException(messages.getString("user-not-found")));

        if(!user.getEnabled())
            throw new UsernameNotFoundException(messages.getString("account-disabled"));

        return UserDetailsImpl.build(user);
    }

    public void setLocale(Locale locale){
        this.locale = locale;
        messages = ResourceBundle.getBundle("messages", this.locale);
    }
}
