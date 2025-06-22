package com.hexaware.userservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hexaware.userservice.entity.UserInfo;
import com.hexaware.userservice.repository.UserInfoRepository;

@Service
public class UserInfoUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserInfoRepository userInfoRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserInfo userInfo=userInfoRepository.findByUserName(username);
		
		if(userInfo==null)
		{
			throw new UsernameNotFoundException(username);
		}
		return new UserInfoUserDetailsImp(userInfo);
	}

}
