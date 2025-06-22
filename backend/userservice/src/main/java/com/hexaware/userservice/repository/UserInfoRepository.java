package com.hexaware.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.userservice.entity.UserInfo;

/**
 *  Date: 02-06-2025
 *  Author: Tharun D
 * Repository for performing database operations related to User entities.
 */

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long>{

	public UserInfo findByUserName(String userName);

	public boolean existsByEmail(String email);
}
