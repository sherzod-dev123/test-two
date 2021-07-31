package uz.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.test.entity.Role;
import uz.test.entity.enums.RoleEnum;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    List<Role> findAllByRoleEnum(RoleEnum roleEnum);

}
