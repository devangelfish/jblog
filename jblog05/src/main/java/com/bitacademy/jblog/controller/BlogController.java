package com.bitacademy.jblog.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bitacademy.jblog.service.BlogService;
import com.bitacademy.jblog.vo.BlogVo;
import com.bitacademy.jblog.vo.CategoryVo;
import com.bitacademy.jblog.vo.PostVo;
import com.bitacademy.security.Auth;
import com.bitacademy.security.ValidationGroups;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	
	@Autowired
	BlogService blogService;

	@RequestMapping({"", "/{category}", "/{category}/{post}"})
	public ModelAndView index(
			@PathVariable String id,
			@PathVariable Optional<Long> category,
			@PathVariable Optional<Long> post) {
		ModelAndView model = blogService.fetch(id, category, post);
		model.addObject("newLine", "\n");
		model.setViewName("blog/blog-main");
		return model;
	}
	
	@Auth("OWN")
	@RequestMapping(value = "/admin/basic", method = RequestMethod.GET)
	public String basic(@PathVariable String id, Model model) {
		BlogVo blogVo = blogService.getBlog(id);
		model.addAttribute("blog", blogVo);
		return "/blog/blog-admin-basic";
	}
	
	@Auth("OWN")
	@RequestMapping(value = "/admin/basic", method = RequestMethod.POST)
	public String editBasic(@PathVariable String id,
							@RequestParam String title,
							@RequestParam(value = "file") MultipartFile multipartFile,
							Model model) {
		BlogVo blogVo = blogService.getBlog(id);
		blogVo.setTitle(title);
		blogService.editBlog(blogVo, multipartFile);
		model.addAttribute("blog", blogVo);
		return "/blog/blog-admin-basic";
	}	
	
	@Auth("OWN")
	@RequestMapping(value = "/admin/category", method = RequestMethod.GET)
	public String category(@PathVariable String id, @ModelAttribute CategoryVo categoryVo, Model model) {
		BlogVo blogVo = blogService.getBlog(id);
		List<CategoryVo> categories = blogService.getCategories(blogVo);
		model.addAttribute("categories", categories);
		model.addAttribute("blog", blogVo);
		return "/blog/blog-admin-category";
	}
	
	@Auth("OWN")
	@RequestMapping(value = "/admin/category", method = RequestMethod.POST)
	public String updateCategory(@PathVariable String id, @ModelAttribute @Validated(ValidationGroups.full.class) CategoryVo categoryVo, BindingResult result, Model model) {
		if(!result.hasErrors()) {
			blogService.createCategory(categoryVo);
		} else {
			model.addAllAttributes(result.getModel());
		}
		BlogVo blogVo = blogService.getBlog(id);
		List<CategoryVo> categories = blogService.getCategories(blogVo);
		model.addAttribute("categories", categories);
		model.addAttribute("blog", blogVo);
		return "/blog/blog-admin-category";
	}
	
	@Auth("OWN")
	@RequestMapping(value = "/admin/category/delete", method = RequestMethod.GET)
	public String deleteCategory(@PathVariable String id, @Validated(ValidationGroups.option.class) CategoryVo categoryVo, BindingResult result, Model model) {
		if(!result.hasErrors()) {
			blogService.deleteCategory(categoryVo);
		}	
		BlogVo blogVo = blogService.getBlog(id);
		List<CategoryVo> categories = blogService.getCategories(blogVo);
		model.addAttribute("categories", categories);
		model.addAttribute("blog", blogVo);
		return "/blog/blog-admin-category";
	}
	
	@Auth("OWN")
	@RequestMapping(value = "/admin/write", method = RequestMethod.GET)
	public String write(@PathVariable String id, @ModelAttribute PostVo postVo, Model model) {
		BlogVo blogVo = blogService.getBlog(id);
		List<CategoryVo> categories = blogService.getCategories(blogVo);
		model.addAttribute("categories", categories);
		model.addAttribute("blog", blogVo);
		return "/blog/blog-admin-write";
	}
	
	@Auth("OWN")
	@RequestMapping(value = "/admin/write", method = RequestMethod.POST)
	public String writePost(@PathVariable String id, @RequestParam String category, @ModelAttribute @Valid PostVo postVo,  BindingResult result, Model model) {
		if(!result.hasErrors()) {
			blogService.writePost(postVo, category);
		} else {
			model.addAllAttributes(result.getModel());
		}
		BlogVo blogVo = blogService.getBlog(id);
		List<CategoryVo> categories = blogService.getCategories(blogVo);
		model.addAttribute("categories", categories);
		model.addAttribute("blog", blogVo);
		return "/blog/blog-admin-write";
	}
}	
