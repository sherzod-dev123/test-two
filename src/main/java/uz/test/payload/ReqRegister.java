package uz.test.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqRegister {

    private String username;

    private String password;

    private String fullName;

    private String phoneNumber;

}
