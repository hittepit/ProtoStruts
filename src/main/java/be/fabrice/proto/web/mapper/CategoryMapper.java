package be.fabrice.proto.web.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import be.fabrice.proto.model.entity.Category;
import be.fabrice.proto.web.vo.CategoryVo;

public final class CategoryMapper {
	private CategoryMapper(){}
	
	public static CategoryVo map(Category cat){
		CategoryVo cv = new CategoryVo();
		cv.setId(cat.getId());
		cv.setName(cat.getName());
		cv.setDescription(cat.getDescription());
		return cv;
	}
	
	public static List<CategoryVo> map(Set<Category> cats){
		List<CategoryVo> cvs = new ArrayList<CategoryVo>();
		for(Category c : cats){
			cvs.add(map(c));
		}
		return cvs;
	}
}
