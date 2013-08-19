package be.fabrice.proto.persistence.dao;

import java.util.List;

import be.fabrice.proto.model.entity.Category;

public interface CategoryDao {
	List<Category> findAll();

	Category find(Long id);
}
