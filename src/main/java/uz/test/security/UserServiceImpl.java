package uz.test.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.test.entity.User;
import uz.test.repository.UserRepository;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        Optional<User> byPhoneNumber = userRepository.findByPhoneNumber(phoneNumber);
        if (!byPhoneNumber.isPresent()){
            throw new UsernameNotFoundException("User not found Exception");
        }
        User user = byPhoneNumber.get();
        return UserDetailsImpl.build(user);
    }

}
