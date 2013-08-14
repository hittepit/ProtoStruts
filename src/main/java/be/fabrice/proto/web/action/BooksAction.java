package be.fabrice.proto.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import be.fabrice.proto.model.entity.Book;
import be.fabrice.proto.service.BookService;

public class BooksAction {
	@Autowired
	private BookService bookService;
	
	private List<Book> books;
	
	/**
	 * Méthode utilisée pour afficher la page de base. Ne fait rien ici, mais pourrait dans d'autres cas
	 * rechercher des informations pseudo-statiques.
	 * @return
	 */
	public String execute(){
		return "success";
	}
	
	public String list(){
		books = bookService.findAll();
		return "books";
	}
	
	public List<Book> getBooks() {
		return books;
	}
}
