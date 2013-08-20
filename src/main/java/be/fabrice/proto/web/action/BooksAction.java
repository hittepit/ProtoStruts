package be.fabrice.proto.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;

import be.fabrice.proto.model.entity.Book;
import be.fabrice.proto.model.entity.Category;
import be.fabrice.proto.service.BookService;
import be.fabrice.proto.service.CategoryService;
import be.fabrice.proto.web.mapper.BookMapper;
import be.fabrice.proto.web.mapper.CategoryMapper;
import be.fabrice.proto.web.vo.BookVo;
import be.fabrice.proto.web.vo.CategoryVo;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@Validations(requiredStrings={@RequiredStringValidator(fieldName="bookVo.title",message="Le titre ne peut être vide")})
public class BooksAction extends ActionSupport{
	@Autowired
	private BookService bookService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private BookMapper bookMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	
	private List<BookVo> books;
	
	private Integer id;
	
//	private Map<String, Object> answer;
	
	private BookVo bookVo;
	
	private List<Integer> categoriesId;
	private List<CategoryVo> bookCategories;
	private List<Category> categories;
	
	/**
	 * Méthode utilisée pour afficher la page de base. Ne fait rien ici, mais pourrait dans d'autres cas
	 * rechercher des informations pseudo-statiques.
	 * @return
	 */
	@SkipValidation
	public String execute(){
		return "success";
	}
	
	@SkipValidation
	public String list(){
		books = bookMapper.map(bookService.findAll());
		return "books";
	}
	
	@SkipValidation
	public String detail(){
		Book book = bookService.find(id);
//		BookVo bv = bookMapper.map(book);
//		answer = new HashMap<String, Object>();
//		answer.put("book", bv);
//		answer.put("bookCategories", categoryMapper.map(book.getCategories()));
		bookVo = bookMapper.map(book);
		bookCategories=categoryMapper.map(book.getCategories());
		return "detail";
	}
	
	@SkipValidation
	public String edit(){
		Book book = bookService.find(id);
//		BookVo bv = bookMapper.map(book);
//		answer = new HashMap<String, Object>();
//		answer.put("book", bv);
//		answer.put("bookCategories", categoryMapper.map(book.getCategories()));
//		answer.put("categories", categoryService.findAll());
		bookVo = bookMapper.map(book);
		bookCategories=categoryMapper.map(book.getCategories());
		categories=categoryService.findAll();
		return "detail";
	}
	
	public String save(){
		Book book = bookMapper.map(bookVo);
		List<Category> categories = categoryMapper.map(categoriesId.toArray(new Integer[0]));
		book.replaceCategories(categories);
		bookService.save(book);
		return "saved";
	}
	
	public List<BookVo> getBooks() {
		return books;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
//	public Map<String, Object> getAnswer() {
//		return answer;
//	}
	public void setCategoriesId(List<Integer> categoriesId) {
		this.categoriesId = categoriesId;
	}
	public void setBookVo(BookVo bookVo) {
		this.bookVo = bookVo;
	}
	public BookVo getBookVo() {
		return bookVo;
	}
	public List<CategoryVo> getBookCategories() {
		return bookCategories;
	}
	public List<Category> getCategories() {
		return categories;
	}
}
