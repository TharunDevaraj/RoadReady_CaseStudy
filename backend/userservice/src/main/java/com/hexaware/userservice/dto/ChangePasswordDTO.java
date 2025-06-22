package com.hexaware.userservice.dto;

public class ChangePasswordDTO {

	private Long userId;
	private String userName;
	private String newPassword;
	private String phoneNumber;
	
	public ChangePasswordDTO(Long userId, String userName, String newPassword, String phoneNumber) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.newPassword = newPassword;
		this.phoneNumber = phoneNumber;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserName(Long userId) {
		this.userId = userId;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@Override
	public String toString() {
		return "ChangePasswordDTO [userId=" + userId + ", userName=" + userName + ", newPassword=" + newPassword
				+ ", phoneNumber=" + phoneNumber + "]";
	}
	
	
}
