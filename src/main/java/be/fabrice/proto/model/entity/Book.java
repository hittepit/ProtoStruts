package be.fabrice.proto.model.entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Type;

import be.fabrice.proto.model.Isbn;

/**
 * <h2>Avertissement</h2>
 * <p>Ce type de construction ne permet pas la définition de Criteria par l'exemple puisqu'il n'est pas possible
 * de donner des valeurs nulles à des propriétés qui ne devraient pas être prises en compte (exemple, recherche
 * sur l'auteur par l'exemple, isbn et title devrait être null => impossible). C'est une limitation acceptable.</p>
 * @author fabrice.claes
 *
 */
@Entity
@Table(name="BOOK")
public class Book {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(name="ISBN")
	@Type(type="be.fabrice.proto.persistence.usertype.IsbnUserType")
	private Isbn isbn;
	@Column(name="TITLE")
	private String title;
	@Column(name="AUTHOR")
	private String author;
	@ManyToMany
	@JoinTable(name="BOOK_CAT",joinColumns=@JoinColumn(name="BOOK_ID"),inverseJoinColumns=@JoinColumn(name="CAT_ID"))
	private Set<Category> categories;
	
	/**
	 * Constructeur sans paramètre utilisé par Hibernate
	 */
	private Book(){}
	
	/**
	 * Construteur utilisé pour la création de Book transient.
	 * @param isbn
	 * @param title
	 * @param author
	 */
	public Book(Isbn isbn, String title, String author){
		if(isbn == null){
			throw new IllegalArgumentException("Isbn must be defined");
		}
		if(StringUtils.isBlank(title)){
			throw new IllegalArgumentException("Title must be defined");
		}
		this.isbn = isbn;
		this.title = title.trim();
		this.author = StringUtils.isBlank(author)?null:author.trim(); //Auteur vide=null
	}	
	
	public Integer getId() {
		return id;
	}
	public Isbn getIsbn() {
		return isbn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Set<Category> getCategories() {
		return this.categories==null?Collections.EMPTY_SET:Collections.unmodifiableSet(categories);
	}
	
	public void addCategory(Category category){
		if(this.categories == null){
			this.categories = new HashSet<Category>();
		}
		this.categories.add(category);
	}
	
	public void removeCategory(Category category){
		if(this.categories == null){
			this.categories = new HashSet<Category>();
		}
		this.categories.remove(category);
	}
	
	public void replaceCategories(List<Category> cats){
		this.categories = new HashSet<Category>(cats);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getIsbn() == null) ? 0 : getIsbn().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Book))
			return false;
		Book other = (Book) obj;
		if (getIsbn() == null) {
			if (other.getIsbn() != null)
				return false;
		} else if (!getIsbn().equals(other.getIsbn()))
			return false;
		return true;
	}
	
	
}
