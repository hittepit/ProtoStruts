package be.fabrice.proto.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.fabrice.proto.model.entity.Category;
import be.fabrice.proto.persistence.dao.CategoryDao;
import be.fabrice.proto.service.CategoryService;

@Service
@Transactional(readOnly=true)
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryDao categoryDao;
	
	@Override
	public List<Category> findAll() {
		return categoryDao.findAll();
	}

	@Override
	public Category find(Long id) {
		return categoryDao.find(id);
	}

}
