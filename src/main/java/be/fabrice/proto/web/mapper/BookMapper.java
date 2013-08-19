package be.fabrice.proto.web.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.fabrice.proto.model.Isbn;
import be.fabrice.proto.model.entity.Book;
import be.fabrice.proto.service.BookService;
import be.fabrice.proto.web.vo.BookVo;

@Component
public class BookMapper {
	@Autowired
	private BookService bookService;
	
	public BookVo map(Book book){
		BookVo bv = new BookVo();
		bv.setId(book.getId());
		bv.setTitle(book.getTitle());
		bv.setIsbn(book.getIsbn().getValue());
		bv.setAuthor(book.getAuthor());
		return bv;
	}
	
	public List<BookVo> map(List<Book> books){
		List<BookVo> bvs = new ArrayList<BookVo>();
		for(Book b : books){
			bvs.add(map(b));
		}
		return bvs;
	}
	
	public Book map(BookVo bvo){
		Book b;
		if(bvo.getId()!=null){
			b = bookService.find(bvo.getId());
			b.setAuthor(bvo.getAuthor());
			b.setTitle(bvo.getTitle());
		} else {
			b = new Book(new Isbn(bvo.getIsbn()), bvo.getTitle(), bvo.getAuthor());
		}
		
		return b;
	}
}
