package uz.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.test.payload.ReqLogin;
import uz.test.payload.ReqRegister;
import uz.test.service.AuthService;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signup")
    public HttpEntity<?> authRegister(@RequestBody ReqRegister reqRegister){
        return authService.authRegister(reqRegister);
    }

    @PostMapping("/signin")
    public HttpEntity<?> authLogin(@RequestBody ReqLogin reqLogin){
     return authService.authLogin(reqLogin);
    }


}
