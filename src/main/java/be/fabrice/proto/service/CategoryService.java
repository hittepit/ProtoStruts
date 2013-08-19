package be.fabrice.proto.service;

import java.util.List;

import be.fabrice.proto.model.entity.Category;

public interface CategoryService {
	List<Category> findAll();

	Category find(Long id);
}
