package uz.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.test.entity.User;
import uz.test.entity.enums.RoleEnum;
import uz.test.jwtSecret.JwtUtils;
import uz.test.payload.ReqLogin;
import uz.test.payload.ReqRegister;
import uz.test.payload.res.JwtResponse;
import uz.test.repository.RoleRepository;
import uz.test.repository.UserRepository;
import uz.test.security.UserDetailsImpl;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JwtUtils jwtUtils;

    public HttpEntity<?> authRegister(ReqRegister reqRegister){

        User user=new User();
        if (reqRegister.getUsername()==null && reqRegister.getFullName()==null && reqRegister.getPassword()==null && reqRegister.getPhoneNumber()==null){
            return new ResponseEntity<>("Error registration user", HttpStatus.CONFLICT);
        }
        user.setUsername(reqRegister.getUsername());
        user.setPassword(passwordEncoder.encode(reqRegister.getPassword()));
        user.setFullName(reqRegister.getFullName());
        user.setPhoneNumber(reqRegister.getPhoneNumber());
        user.setRoles(roleRepository.findAllByRoleEnum(RoleEnum.ROLE_USER));
        userRepository.save(user);
        return new ResponseEntity<>("Successfully saved user", HttpStatus.OK);
    }
    public HttpEntity<?> authLogin(ReqLogin reqLogin){

        Optional<User> byPhoneNumber = userRepository.findByPhoneNumber(reqLogin.getPhoneNumber());
        if (!byPhoneNumber.isPresent()){
            return new ResponseEntity<>("This phone number is not found", HttpStatus.NO_CONTENT);
        }
        User user = byPhoneNumber.get();
        if (!passwordEncoder.matches(reqLogin.getPassword(), user.getPassword())){
            return new ResponseEntity<>("Password is error!", HttpStatus.CONFLICT);
        }
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(reqLogin.getPhoneNumber(), reqLogin.getPassword()));
        System.out.println(authenticate);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        if (authenticate==null){
            return new ResponseEntity<>("Phone number or password is error", HttpStatus.NO_CONTENT);
        }
        String jwt=jwtUtils.generateJwtToken(authenticate);
        UserDetailsImpl userDetails= (UserDetailsImpl) authenticate.getDetails();
        return new ResponseEntity<>(new JwtResponse(jwt, userDetails.getPhoneNumber(), userDetails.getFullName()), HttpStatus.OK);
    }


}
