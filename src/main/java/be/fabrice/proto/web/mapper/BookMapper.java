package be.fabrice.proto.web.mapper;

import java.util.ArrayList;
import java.util.List;

import be.fabrice.proto.model.entity.Book;
import be.fabrice.proto.web.vo.BookVo;

public final class BookMapper {
	private BookMapper(){}
	
	static public BookVo map(Book book){
		BookVo bv = new BookVo();
		bv.setId(book.getId());
		bv.setTitle(book.getTitle());
		bv.setIsbn(book.getIsbn().getValue());
		bv.setAuthor(book.getAuthor());
		return bv;
	}
	
	static public List<BookVo> map(List<Book> books){
		List<BookVo> bvs = new ArrayList<BookVo>();
		for(Book b : books){
			bvs.add(map(b));
		}
		return bvs;
	}
}
