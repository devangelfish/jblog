package com.bitacademy.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bitacademy.jblog.vo.UserVo;

@Repository
public class UserRepository {
	@Autowired
	SqlSession sqlSession;
	
	public int insert(UserVo userVo) {
		return sqlSession.insert("user.insert", userVo);
	}

	public UserVo select(UserVo userVo) {
		return sqlSession.selectOne("user.select", userVo);
	}
}	
