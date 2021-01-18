package com.bitacademy.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bitacademy.jblog.repository.BlogRepository;
import com.bitacademy.jblog.repository.CategoryRepository;
import com.bitacademy.jblog.repository.UserRepository;
import com.bitacademy.jblog.vo.UserVo;

@Service
public class JoinService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BlogRepository blogRepository;
	
	@Autowired
	CategoryRepository categoryRepository;

	@Transactional
	public void register(UserVo userVo) {
		userRepository.insert(userVo);
		blogRepository.insert(userVo);
		categoryRepository.insert(userVo);
	}
}
