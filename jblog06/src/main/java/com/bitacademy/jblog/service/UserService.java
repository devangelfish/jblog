package com.bitacademy.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitacademy.jblog.repository.UserRepository;
import com.bitacademy.jblog.vo.UserVo;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	
	public int registerUser(UserVo userVo) {
		return userRepository.insert(userVo);
	}

	public UserVo find(UserVo userVo) {
		return userRepository.select(userVo);
	}
}
