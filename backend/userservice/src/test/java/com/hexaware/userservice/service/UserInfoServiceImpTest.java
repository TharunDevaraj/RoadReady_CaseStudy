package com.hexaware.userservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hexaware.userservice.dto.UserDTO;
import com.hexaware.userservice.dto.UserInfoDTO;
import com.hexaware.userservice.entity.UserInfo;
import com.hexaware.userservice.exception.InvalidSecretKeyException;
import com.hexaware.userservice.exception.UserNameAlreadyExistsException;
import com.hexaware.userservice.exception.UserNotFoundException;

@SpringBootTest
class UserInfoServiceImpTest {

	@Autowired
	UserInfoServiceImp userService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	@Disabled
	void testRegisterUser() throws UserNameAlreadyExistsException, InvalidSecretKeyException {
		
		UserDTO user1 = new UserDTO();
        user1.setUserName("john");
        user1.setEmail("john@example.com");
        user1.setPassword(passwordEncoder.encode("password"));
        user1.setRoles("user");
        
        UserInfoDTO result = userService.registerUser(user1);

        assertNotNull(result);
    
        
        assertThrows(UserNameAlreadyExistsException.class, ()-> userService.registerUser(user1));
	}

	@Test
	@Disabled
	void testGetAllUsers() {
		
		List<UserInfoDTO> result = userService.getAllUsers();

        assertEquals(7, result.size());
	}

	@Test
	@Disabled
	void testGetUserById() throws UserNotFoundException {
		
		UserInfoDTO dto = userService.getUserById(202L);
		
		assertEquals("john", dto.getUserName());
        assertEquals("john@example.com", dto.getEmail());
	}

	@Test
	@Disabled
	void testUpdateUser() throws UserNotFoundException {
		UserInfoDTO updated = new UserInfoDTO();
        updated.setUserName("john_updated");
        updated.setEmail("new@example.com");
        updated.setRoles("admin");
        
        UserInfoDTO dto = userService.updateUser(202L, updated);

        assertEquals("john_updated", dto.getUserName());
        assertEquals("new@example.com", dto.getEmail());
        assertEquals("admin", dto.getRoles());
		
		UserInfoDTO updated2 = new UserInfoDTO();
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(10L, updated2));
	}

	@Test
	@Disabled
	void testDeactivateUser() throws UserNotFoundException {
		 userService.deactivateUser(202L);
		 
		 UserInfoDTO user1=userService.getUserById(202L);

	        assertEquals("INACTIVE", user1.getRoles());
		
	}

}
