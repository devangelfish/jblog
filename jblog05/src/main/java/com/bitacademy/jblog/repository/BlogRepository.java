package com.bitacademy.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bitacademy.jblog.vo.BlogVo;
import com.bitacademy.jblog.vo.UserVo;

@Repository
public class BlogRepository {
	@Autowired
	SqlSession sqlSession;
	
	public int insert(UserVo userVo) {
		return sqlSession.insert("blog.init", userVo);
	}
	
	public BlogVo select(String id) {
		return sqlSession.selectOne("blog.select", id);
	}

	public int update(BlogVo blogVo) {
		return sqlSession.update("blog.update", blogVo);
	}
}
