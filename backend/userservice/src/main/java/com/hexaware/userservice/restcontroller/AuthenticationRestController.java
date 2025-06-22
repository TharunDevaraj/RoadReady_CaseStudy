package com.hexaware.userservice.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.userservice.dto.AuthRequest;
import com.hexaware.userservice.dto.ChangePasswordDTO;
import com.hexaware.userservice.dto.UserDTO;
import com.hexaware.userservice.dto.UserInfoDTO;
import com.hexaware.userservice.entity.UserInfo;
import com.hexaware.userservice.exception.InvalidSecretKeyException;
import com.hexaware.userservice.exception.UserNameAlreadyExistsException;
import com.hexaware.userservice.exception.UserNotFoundException;
import com.hexaware.userservice.service.IUserInfoService;
import com.hexaware.userservice.service.JwtService;

import jakarta.validation.Valid;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/authenticate")
public class AuthenticationRestController {

	@Autowired
	JwtService jwtService;
	
	@Autowired
	IUserInfoService userService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@PostMapping("/register")
	public ResponseEntity<UserInfoDTO> addUser(@RequestBody @Valid UserDTO userInfo) throws UserNameAlreadyExistsException, InvalidSecretKeyException
	{
		return new ResponseEntity<UserInfoDTO>( userService.registerUser(userInfo), HttpStatus.CREATED);
	}
	

    @PostMapping("/verifyDetails")
    public String verifyDetails(@RequestBody ChangePasswordDTO changePasswordDTO) throws UserNotFoundException
    {
    	String token="";
    	if(userService.verifyDetails(changePasswordDTO).equals("Success"))
    	{
    		token = jwtService.generateToken(changePasswordDTO.getUserName());
    		return token;
    	}
    	return null;
    }
    
	
	@PostMapping("/login")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		
		

		String token = null;

		if (authentication.isAuthenticated()) {

			// call generate token method from jwtService class

			token = jwtService.generateToken(authRequest.getUsername());

			//logger.info("Token : " + token);

		} else {

			//logger.info("invalid");

			throw new UsernameNotFoundException("UserName or Password in Invalid / Invalid Request");

		}
			return token;

	}
}
