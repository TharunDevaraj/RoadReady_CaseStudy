package com.hexaware.userservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 *  Date: 02-06-2025
 *  Author: Tharun D
 * Entity representing user information in the system.
 * Includes fields like username, email, and roles.
 */

@Entity
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String userName; 
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    @NotBlank(message = "Phone Number is required")
    @Pattern(regexp = "[0-9]{10}")
    private String phoneNumber;
    
    @NotBlank(message = "Password is required")
    @Size(min = 4, message = "Password must be at least 4 characters")
    private String password;
    
    @NotBlank(message = "Role is required")
    @Pattern(
        regexp = "^(customer|admin|agent|INACTIVE)$",
        message = "Role must be either user, admin, or agent"
    )
    private String roles;
    
    public UserInfo()
    {
    	
    }


	public UserInfo(Long userId,
			@NotBlank(message = "Username is required") @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters") String userName,
			@NotBlank(message = "Email is required") @Email(message = "Email should be valid") String email,
			@NotBlank(message = "Phone Number is required") @Pattern(regexp = "[0-9]{10}") String phoneNumber,
			@NotBlank(message = "Password is required") @Size(min = 4, message = "Password must be at least 4 characters") String password,
			@NotBlank(message = "Role is required") @Pattern(regexp = "^(customer|admin|agent|INACTIVE)$", message = "Role must be either user, admin, or agent") String roles) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.roles = roles;
	}


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
	

	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", userName=" + userName + ", email=" + email + ", phoneNumber="
				+ phoneNumber + ", password=" + password + ", roles=" + roles + "]";
	}


	
	
        
}
