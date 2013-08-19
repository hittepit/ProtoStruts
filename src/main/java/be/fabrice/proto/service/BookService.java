package be.fabrice.proto.service;

import java.util.List;

import be.fabrice.proto.model.entity.Book;

public interface BookService {

	List<Book> findAll();

	Book find(Integer id);

	void save(Book book);

}
