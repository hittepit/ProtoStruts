package be.fabrice.proto.web.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.fabrice.proto.model.entity.Category;
import be.fabrice.proto.service.CategoryService;
import be.fabrice.proto.web.vo.CategoryVo;

@Component
public class CategoryMapper {
	@Autowired
	private CategoryService categoryService;
	
	public CategoryVo map(Category cat){
		CategoryVo cv = new CategoryVo();
		cv.setId(cat.getId());
		cv.setName(cat.getName());
		cv.setDescription(cat.getDescription());
		return cv;
	}
	
	public List<CategoryVo> map(Set<Category> cats){
		List<CategoryVo> cvs = new ArrayList<CategoryVo>();
		for(Category c : cats){
			cvs.add(map(c));
		}
		return cvs;
	}
	
	public Category map(CategoryVo cvo){
		Category cat;
		if(cvo.getId()!=null){
			cat = categoryService.find(cvo.getId());
			cat.setDescription(cvo.getDescription()); //La seule propriété mutable
		} else {
			cat = new Category(cvo.getName(), cvo.getDescription());
		}
		return cat;
	}
	
	public List<Category> map(Integer[] ids){
		List<Category> cats = new ArrayList<Category>();
		for(Integer id:ids){
			cats.add(categoryService.find(id.longValue()));
		}
		return cats;
	}
	
	public List<Category> map(List<CategoryVo> cvs){
		List<Category> cats = new ArrayList<Category>();
		for(CategoryVo cv:cvs){
			cats.add(map(cv));
		}
		return cats;
	}
}
