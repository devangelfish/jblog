package com.bitacademy.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bitacademy.jblog.vo.BlogVo;
import com.bitacademy.jblog.vo.CategoryVo;
import com.bitacademy.jblog.vo.UserVo;

@Repository
public class CategoryRepository {
	@Autowired
	SqlSession sqlSession;
	
	public int insert(UserVo userVo) {
		return sqlSession.insert("category.init", userVo);
	}
	
	public int insert(CategoryVo categoryVo) {
		return sqlSession.insert("category.insert", categoryVo);
	}
	
	public List<CategoryVo> select(BlogVo blogVo) {
		return sqlSession.selectList("category.fetch", blogVo);
	}
	
	public List<Long> select(String category) {
		return sqlSession.selectList("category.selectList", category);
	}

	public int delete(CategoryVo categoryVo) {
		return sqlSession.delete("category.delete", categoryVo);
	}
}
