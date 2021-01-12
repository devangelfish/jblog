package com.bitacademy.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bitacademy.jblog.vo.PostVo;

@Repository
public class PostRepository {
	@Autowired
	SqlSession sqlSession;
	
	public List<PostVo> select(Long no) {
		return sqlSession.selectList("post.select", no);
	}

	public int insert(PostVo postVo) {
		return sqlSession.insert("post.insert", postVo);
	}
}
