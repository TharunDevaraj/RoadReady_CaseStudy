package com.hexaware.userservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexaware.userservice.dto.ChangePasswordDTO;
import com.hexaware.userservice.dto.UserDTO;
import com.hexaware.userservice.dto.UserInfoDTO;
import com.hexaware.userservice.entity.UserInfo;
import com.hexaware.userservice.exception.EmailAlreadyExistsException;
import com.hexaware.userservice.exception.InvalidSecretKeyException;
import com.hexaware.userservice.exception.UserNameAlreadyExistsException;
import com.hexaware.userservice.exception.UserNotFoundException;
import com.hexaware.userservice.repository.UserInfoRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Date: 02-06-2025
 * Author: Tharun D
 * Handles all business logic related to user registration such as registering,
 * updating, deactivating, etc.
 */

@Service
@Slf4j
public class UserInfoServiceImp implements IUserInfoService{

	@Autowired
	UserInfoRepository userInfoRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Value("${app.admin.secret}")
	private String adminSecret;
	
	@Override
	public UserInfoDTO registerUser(UserDTO userInfo) throws UserNameAlreadyExistsException, InvalidSecretKeyException {
		
		if (userInfoRepository.findByUserName(userInfo.getUserName()) != null) {
			log.warn("Username already exists");
		    throw new UserNameAlreadyExistsException("Username already exists");
		}
		
		if (userInfoRepository.existsByEmail(userInfo.getEmail())) {
			log.warn("Email already exists");
		    throw new EmailAlreadyExistsException("Email already exists");
		}
		
		if ("admin".equalsIgnoreCase(userInfo.getRoles())) {
		    if (userInfo.getAdminKey() == null || !userInfo.getAdminKey().equals(adminSecret)) {
		        throw new InvalidSecretKeyException("Invalid admin secret key");
		    }
		}
		
		log.info("Registering new user with username={}, email={} and role={}",userInfo.getUserName(), userInfo.getEmail(), userInfo.getRoles());
		
		UserInfo registerDataInfo=new UserInfo();
		registerDataInfo.setUserName(userInfo.getUserName());
		registerDataInfo.setEmail(userInfo.getEmail());
		registerDataInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
		registerDataInfo.setPhoneNumber(userInfo.getPhoneNumber());
		registerDataInfo.setRoles(userInfo.getRoles());
		
		 UserInfo savedUser = userInfoRepository.save(registerDataInfo);
	     log.debug("User registered successfully: {}", savedUser);
	     return EntityToDTO(registerDataInfo);
	}

	@Override
	public List<UserInfoDTO> getAllUsers() {
		log.info("Fetching all users");
		List<UserInfoDTO> usersDetails=new ArrayList<>();
		
		List<UserInfo> users = userInfoRepository.findAll();
		
		for(UserInfo userInfo : users)
		{
			usersDetails.add(EntityToDTO(userInfo));
		}
		log.debug("Total users fetched: {}", users.size());
		return usersDetails;
	}

	@Override
	public UserInfoDTO getUserById(Long id) throws UserNotFoundException {
		log.info("Fetching user with id={} ", id);
		UserInfo user = userInfoRepository.findById(id).orElse(null);
		if(user==null)
		{
			log.warn("User with id={} not found", id);
			throw new UserNotFoundException();
		}
		log.debug("User found: {}", user);
		return EntityToDTO(user);
	}

	@Override
	public UserInfoDTO updateUser(Long id, UserInfoDTO updatedUser) throws UserNotFoundException {
		log.info("Updating user with id={}", id);
		UserInfo existingUserInfo=userInfoRepository.findById(id).orElse(null);
		
		if(existingUserInfo==null)
		{
			log.warn("User with id={} not found for update", id);
			throw new UserNotFoundException();
		}
		existingUserInfo.setUserName(updatedUser.getUserName());
        existingUserInfo.setEmail(updatedUser.getEmail());
        existingUserInfo.setRoles(updatedUser.getRoles());
        existingUserInfo.setPhoneNumber(updatedUser.getPhoneNumber());

     
        log.debug("User with id={} updated successfully: {}", id, existingUserInfo);
        return EntityToDTO(userInfoRepository.save(existingUserInfo));
		
	}

	 @Override
	    public String deactivateUser(Long id) throws UserNotFoundException {
		 log.info("Deactivating user with id={}", id);
	        UserInfo user = userInfoRepository.findById(id).orElse(null);
	        if(user==null)
	        {
	        	log.warn("Attempted to deactivate non-existing user with id={}", id);
	        	throw new UserNotFoundException();
	        }
	        user.setRoles("INACTIVE"); 
	        log.debug("User with id={} deactivated successfully", id);
	        userInfoRepository.save(user);
	        return "User deactivated!";
	    }
	 
	 private UserInfoDTO EntityToDTO(UserInfo userInfo)
	 {
		 UserInfoDTO userInfoDTO=new UserInfoDTO();
		 userInfoDTO.setUserId(userInfo.getUserId());
		 userInfoDTO.setUserName(userInfo.getUserName());
		 userInfoDTO.setEmail(userInfo.getEmail());
		 userInfoDTO.setRoles(userInfo.getRoles());
		 userInfoDTO.setPhoneNumber(userInfo.getPhoneNumber());
		 return userInfoDTO;
	 }

	@Override
	public String updatePassword(Long userId,String newPassword) throws UserNotFoundException {
		UserInfo userInfo=userInfoRepository.findById(userId).orElse(null);
		if(userInfo==null)
		{
			throw new UserNotFoundException();
		}
		userInfo.setPassword(passwordEncoder.encode(newPassword));
		userInfoRepository.save(userInfo);
		return "Password updated successfully!";
	}

	@Override
	public String verifyDetails(ChangePasswordDTO changePasswordDTO) throws UserNotFoundException {
		UserInfo userInfo=userInfoRepository.findById(changePasswordDTO.getUserId()).orElse(null);
		if(userInfo==null)
		{
			throw new UserNotFoundException();
		}
		String message="Failed";
		if(userInfo.getPhoneNumber().equals(changePasswordDTO.getPhoneNumber()) && userInfo.getUserName().equals(changePasswordDTO.getUserName()))
		{
			message="Success";
		}
		return message;
	}

}
