package be.fabrice.proto.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.fabrice.proto.model.entity.Book;
import be.fabrice.proto.persistence.dao.BookDao;
import be.fabrice.proto.service.BookService;

@Service
public class BookServiceImpl implements BookService{
	@Autowired
	private BookDao bookDao;
	
	@Override
	public List<Book> findAll() {
		return bookDao.findAll();
	}

}
