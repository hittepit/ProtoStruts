package be.fabrice.proto.web.action;

import java.util.List;

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
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.ExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

public class BookJsonAction extends ActionSupport {
	@Autowired
	private BookService bookService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private BookMapper bookMapper;
	@Autowired
	private CategoryMapper categoryMapper;

	private Integer id;
	private BookVo bookVo;
	private List<BookVo> books;
	private List<Integer> categoriesId;
	private List<CategoryVo> bookCategories;
	private List<CategoryVo> categories;

	@SkipValidation
	public String list(){
		books = bookMapper.map(bookService.findAll());
		return "books";
	}
	
	@SkipValidation
	public String detail(){
		Book book = bookService.find(id);
		bookVo = bookMapper.map(book);
		bookCategories=categoryMapper.map(book.getCategories());
		return "detail";
	}
	
	@SkipValidation
	public String edit(){
		Book book = bookService.find(id);
		bookVo = bookMapper.map(book);
		bookCategories=categoryMapper.map(book.getCategories());
		return "detail";
	}
	
	@SkipValidation
	public String categoriesList(){
		categories=categoryMapper.map(categoryService.findAll());
		return "categories";
	}
	
	@Validations(requiredStrings={
			@RequiredStringValidator(fieldName="bookVo.title",message="Le titre ne peut être vide")},
			customValidators={
			@CustomValidator(key="bookVo.isbn",fieldName="bookVo.isbn",type="isbnValidation",message="Isbn incorrect (13 chiffres commençant par 978)"),
			@CustomValidator(fieldName="categoriesId",type="nonEmptyListValidation",message="Sélectionnez au moins une catégorie")
			})
	public String save(){
		Book book = bookMapper.map(bookVo);
		List<Category> categories = categoryMapper.map(categoriesId.toArray(new Integer[0]));
		book.synchronizeCategories(categories);
		bookService.save(book);
		return "saved";
	}

	public void setBookVo(BookVo bookVo) {
		this.bookVo = bookVo;
	}
	public BookVo getBookVo() {
		return bookVo;
	}
	public List<BookVo> getBooks() {
		return books;
	}	
	public void setId(Integer id) {
		this.id = id;
	}
	public void setCategoriesId(List<Integer> categoriesId) {
		this.categoriesId = categoriesId;
	}
	public List<Integer> getCategoriesId() {
		return categoriesId;
	}
	public List<CategoryVo> getBookCategories() {
		return bookCategories;
	}
	public List<CategoryVo> getCategories() {
		return categories;
	}
}
