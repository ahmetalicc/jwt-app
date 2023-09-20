package com.alic.springsecurityjwtexample.Service;

import com.alic.springsecurityjwtexample.Dao.UserDao;
import com.alic.springsecurityjwtexample.Dto.UserDto;
import com.alic.springsecurityjwtexample.Dto.UserRequest;
import com.alic.springsecurityjwtexample.Dto.UserResponse;
import com.alic.springsecurityjwtexample.Entity.User;
import com.alic.springsecurityjwtexample.Enum.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserDao userDao;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;
    public UserResponse save(UserDto userDto) {

        User user = User.builder() .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nameSurname(userDto.getNameSurname())
                .role(Role.USER).build();
        userDao.save(user);

        var token = jwtService.generateToken(user);

        return UserResponse.builder().token(token).build();
    }

    public UserResponse auth(UserRequest userRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(),userRequest.getPassword()));
        User user = userDao.findByUsername(userRequest.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);
        return UserResponse.builder().token(token).build();
    }
}
