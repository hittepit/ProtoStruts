package be.fabrice.proto.persistence.dao;

import java.util.List;

import be.fabrice.proto.model.entity.Book;

public interface BookDao {
	Book find(Integer id);
	List<Book> findAll();
	void save(Book book);
}
