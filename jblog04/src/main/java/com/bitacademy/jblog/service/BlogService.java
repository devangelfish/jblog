package com.bitacademy.jblog.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bitacademy.jblog.repository.BlogRepository;
import com.bitacademy.jblog.repository.CategoryRepository;
import com.bitacademy.jblog.repository.PostRepository;
import com.bitacademy.jblog.vo.BlogVo;
import com.bitacademy.jblog.vo.CategoryVo;
import com.bitacademy.jblog.vo.PostVo;

@Service
public class BlogService {
	private static final String SAVE_PATH = "/jblog-uploads";
	private static final String URL_BASE = "/assets/blog-images";
	
	@Autowired
	BlogRepository blogRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	PostRepository postRepository;
	
	public int editBlog(BlogVo blogVo, MultipartFile multipartFile) {
		if (multipartFile.isEmpty()) {
			return blogRepository.update(blogVo);
		}
		blogVo.setLogo(saveLogo(blogVo, multipartFile));
		return blogRepository.update(blogVo);
	}
	
	private String saveLogo(BlogVo blogVo, MultipartFile multipartFile) {
		String filename = createFilename(blogVo, multipartFile);
		String path = pathFor(filename);
		String url = urlFor(filename);
		
		try {
			byte[] fileData = multipartFile.getBytes();
			OutputStream os = new FileOutputStream(path, false);
			os.write(fileData);
			os.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return url;
	}
	
	private String createFilename(BlogVo blogVo, MultipartFile multipartFile) {
		String saveFileName = "";
		String originalFileName = multipartFile.getOriginalFilename();
		String extName = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
		saveFileName = blogVo.getId() + "." + extName;
		return saveFileName;
	}
	
	private String pathFor(String saveFileName) {
		return SAVE_PATH + "/" + saveFileName;
	}
	
	private String urlFor(String saveFileName) {
		return URL_BASE + "/" + saveFileName;
	}
	
	public BlogVo getBlog(String id) {
		return blogRepository.select(id);
	}
	
	public List<PostVo> getPosts(Long categoryNo) {
		return postRepository.select(categoryNo);
	}
	
	public List<CategoryVo> getCategories(BlogVo blogVo) {
		List<CategoryVo> categories = categoryRepository.select(blogVo);
		return setPostsCountFor(categories);
	}
	
	public int createCategory(CategoryVo categoryVo) {
		return categoryRepository.insert(categoryVo);
	}
	
	public int deleteCategory(CategoryVo categoryVo) {
		return categoryRepository.delete(categoryVo);
	}
	
	public int writePost(PostVo postVo, String category) {
		List<Long> categoryNo = getCategoryNo(category);
		for(Long no : categoryNo) {
			postVo.setCategoryNo(no);
			postRepository.insert(postVo);
		}
		return categoryNo.size();
	}
	
	public List<Long> getCategoryNo(String category) {
		return categoryRepository.select(category);
	}
	
	private List<CategoryVo> setPostsCountFor(List<CategoryVo> categories) {
		for(CategoryVo category : categories) {
			List<PostVo> posts = getPosts(category.getNo());
			category.setPostsCount(posts.size());
		}
		return categories;
	}
	
	public ModelAndView fetch(String id, Optional<Long> category, Optional<Long> post) {
		ModelAndView model = new ModelAndView();
		BlogVo blogVo = blogRepository.select(id);
		List<CategoryVo> categories = getCategories(blogVo);
		
		Long categoryNo = setCategory(id, category, categories);
		List<PostVo> posts = getPosts(categoryNo);
		PostVo postVo = setPost(categoryNo, post, posts);
		
		model.addObject("blog", blogVo);
		model.addObject("post", postVo);
		model.addObject("categories", categories);
		model.addObject("posts", posts);
		
		return model;
	}
	
	private Long setCategory(String id, Optional<Long> category, List<CategoryVo> categories) {
		Long categoryNo = 0L;
		
		if(!category.isPresent()) {
			for(CategoryVo vo : categories) {
				if(vo.getPostsCount() > 0) {
					categoryNo = vo.getNo();
					break;
				}
			}
		} else {
			categoryNo = category.get();
		}
		return categoryNo;
	}
	
	private PostVo setPost(Long categoryNo, Optional<Long> post, List<PostVo> posts) {
		PostVo postVo = null;
		
		if(!post.isPresent()) {
			for(PostVo vo : posts) {
				postVo = vo;
				break;
			}
		} else {
			for(PostVo vo : posts) {
				if(vo.getNo().equals(post.get())) {
					postVo = vo;
					break;
				}
			}
		}
		return postVo;
	}
}
