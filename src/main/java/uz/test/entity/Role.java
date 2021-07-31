package uz.test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.data.annotation.LastModifiedDate;
import uz.test.entity.enums.RoleEnum;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    private RoleEnum roleEnum;

    @CreationTimestamp
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

}
