package be.fabrice.proto.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	public String getAuthor() {
		return author;
	}
	
	
}
