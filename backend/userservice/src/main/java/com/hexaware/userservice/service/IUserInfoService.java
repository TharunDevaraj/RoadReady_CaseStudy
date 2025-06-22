package com.hexaware.userservice.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.hexaware.userservice.dto.ChangePasswordDTO;
import com.hexaware.userservice.dto.UserDTO;
import com.hexaware.userservice.dto.UserInfoDTO;
import com.hexaware.userservice.entity.UserInfo;
import com.hexaware.userservice.exception.InvalidSecretKeyException;
import com.hexaware.userservice.exception.UserNameAlreadyExistsException;
import com.hexaware.userservice.exception.UserNotFoundException;

public interface IUserInfoService {
	
	public UserInfoDTO registerUser(UserDTO userInfo) throws UserNameAlreadyExistsException, InvalidSecretKeyException;
	
	public List<UserInfoDTO> getAllUsers();
	
	public UserInfoDTO getUserById(Long id) throws UserNotFoundException;
	
	public UserInfoDTO updateUser(Long id, UserInfoDTO updatedUser) throws UserNotFoundException;
	
	public String deactivateUser(Long id) throws UserNotFoundException;
	
	public String updatePassword(Long userId,String newPassword) throws UserNotFoundException;

	public String verifyDetails(ChangePasswordDTO changePasswordDTO) throws UserNotFoundException;
	

}
