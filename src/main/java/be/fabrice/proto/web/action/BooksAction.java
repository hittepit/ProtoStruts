package be.fabrice.proto.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import be.fabrice.proto.model.entity.Book;
import be.fabrice.proto.service.BookService;
import be.fabrice.proto.service.CategoryService;
import be.fabrice.proto.web.mapper.BookMapper;
import be.fabrice.proto.web.mapper.CategoryMapper;
import be.fabrice.proto.web.vo.BookVo;
import be.fabrice.proto.web.vo.CategoryVo;

public class BooksAction {
	@Autowired
	private BookService bookService;
	@Autowired
	private CategoryService categoryService;
	
	private List<BookVo> books;
	
	private Integer id;
	
	private Map<String, Object> answer;
	
	/**
	 * Méthode utilisée pour afficher la page de base. Ne fait rien ici, mais pourrait dans d'autres cas
	 * rechercher des informations pseudo-statiques.
	 * @return
	 */
	public String execute(){
		return "success";
	}
	
	public String list(){
		books = BookMapper.map(bookService.findAll());
		return "books";
	}
	
	public String detail(){
		Book book = bookService.find(id);
		BookVo bv = BookMapper.map(book);
		answer = new HashMap<String, Object>();
		answer.put("book", bv);
		answer.put("bookCategories", CategoryMapper.map(book.getCategories()));
		return "detail";
	}
	
	public String edit(){
		Book book = bookService.find(id);
		BookVo bv = BookMapper.map(book);
		answer = new HashMap<String, Object>();
		answer.put("book", bv);
		answer.put("bookCategories", CategoryMapper.map(book.getCategories()));
		answer.put("categories", categoryService.findAll());
		return "detail";
	}
	
	public List<BookVo> getBooks() {
		return books;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public Map<String, Object> getAnswer() {
		return answer;
	}
}
