package uz.test.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.test.entity.Role;
import uz.test.entity.User;
import uz.test.entity.enums.RoleEnum;
import uz.test.repository.RoleRepository;
import uz.test.repository.UserRepository;

import java.util.LinkedList;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${spring.datasource.initialization-mode}")
    private String initMode;

    @Override
    public void run(String... args) throws Exception {
        if (initMode.equals("always")){

            Role adminRole=new Role();
            adminRole.setRoleEnum(RoleEnum.ROLE_ADMIN);
            roleRepository.save(adminRole);

            Role userRole=new Role();
            userRole.setRoleEnum(RoleEnum.ROLE_USER);
            roleRepository.save(userRole);

            Role teacherRole=new Role();
            teacherRole.setRoleEnum(RoleEnum.ROLE_TEACHER);
            roleRepository.save(teacherRole);

            User admin=new User();
            admin.setUsername("admin");
            admin.setPhoneNumber("998971971200");
            admin.setFullName("Super Admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRoles(roleRepository.findAllByRoleEnum(RoleEnum.ROLE_ADMIN));
            userRepository.save(admin);

            User user=new User();
            user.setUsername("user");
            user.setFullName("Super user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setPhoneNumber("+998971971212");
            user.setRoles(roleRepository.findAllByRoleEnum(RoleEnum.ROLE_ADMIN));
            userRepository.save(user);

            User teacher=new User();
            user.setUsername("teacher");
            user.setFullName("Super teacher");
            user.setPassword(passwordEncoder.encode("teacher123"));
            user.setPhoneNumber("+998971970012");
            user.setRoles(roleRepository.findAllByRoleEnum(RoleEnum.ROLE_TEACHER));
            userRepository.save(user);
        }
    }
}
