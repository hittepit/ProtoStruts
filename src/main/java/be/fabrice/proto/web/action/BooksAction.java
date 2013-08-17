package be.fabrice.proto.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import be.fabrice.proto.service.BookService;
import be.fabrice.proto.web.mapper.BookMapper;
import be.fabrice.proto.web.vo.BookVo;

public class BooksAction {
	@Autowired
	private BookService bookService;
	
	private List<BookVo> books;
	
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
	
	public List<BookVo> getBooks() {
		return books;
	}
}
