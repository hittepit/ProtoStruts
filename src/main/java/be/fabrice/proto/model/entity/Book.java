package be.fabrice.proto.model.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
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
 * <p>Définition d'une entité (Hibernate) Book 'solide'. Cela signifie que certaines caractéristiques de validation (voir métier)
 * sont développer afin d'interdire la création d'un objet Book incorrect.</p>
 * <p>Les règles business utilisées ici sont les suivantes:</p>
 * <ul>
 * 	<li>un livre doit toujours avoir un numéro Isbn correct</li>
 * 	<li>un livre doit toujours avoir un titre non null et non vide</li>
 * 	<li>l'isbn d'un livre ne peut jamais être modifié (édité)</li>
 * </ul>
 * <p>A cela s'ajoutent des règles fonctionnelles:</p>
 * <ul>
 * 	<li>les espaces devant et/ou derrière le titre et le nom sont toujours supprimés</li>
 * 	<li>si le nom d'un auteur est vide, il sera null</li>
 * 	<li>afin de ne pas exposer une propriété à la modification non contrôlée (même si pas de problème dans ce cas)
 * l'accès à la collection de catégories est limité</li> 
 * 	<li>la collection de catégories ne peut évidemment contenir aucune duplication</li>
 * </ul>
 * <p>La traduction de ces règles se fait de la manière suivante.
 * <ul>
 * 	<li>les obligations concernant les propriétés se traduisent par le seul constructeur public de la classe
 * lequel garantit que les paramètres seront passés et vérifient qu'ils sont correctes (non nulls, non vide). A noter
 * que l'entité étant Hibernate, elle doit avoir un constructeur sans paramètre. Toutefois, ce constructeur construit
 * un objet qui est incorrect, raison pour laquelle il est private. Son utilisation est normalement interdite. Hibernate
 * pourra y accéder et injecter directement les valeurs des colonnes dans les variables d'instance. Cela ne garantit pas
 * la cohérence du modèle. La cohérence doit donc aussi être garantie au niveau DB.</li>
 * 	<li>toutes les variables d'instance peuvent être lues, mais le getter de la collection de catégories
 * ne renvoie pas directement la collection, mais une autre, ce qui limite la modification directe de la collection. (C'est
 * particulièrement intéressant si la relation est bidirectionnelle.) De plus, c'est une condition non modifiable
 * qui est rnevoyée. Cela limite le risque qu'un développeur utilise le getter pour ajouter (ou retirer) des catégories et
 * ne produise donc aucun effet. Ici, il y aura une exception à l'utilisation (l'information doit aussi se trouver sur 
 * la documentation du getter).</li>
 * 	<li>la collection d'une catégorie est un Set, ce qui est beaucoup plus efficace qu'une List. D'une manière générale, le Set
 * est toujours plus efficace qu'une List et pourtant, c'est souvent cette dernière que les développeurs utilisent alors que
 * les cas d'utilisation sont radicalement différents</li>
 * 	<li>le numéro Isbn est correct parce que c'est un Domain Logical Value Object qui est utilisé et qui garantit 
 * que la valeur est correcte (voir {@link Isbn})</li>
 * 	<li> il n'y a des setters que sur les propriétés qui peuvent être modifiées (donc pas sur l'Isbn)</li>
 * 	</ul>
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
	@Column(name="TITLE",nullable=false)
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
	/**
	 * Retourne l'ensemble des catégories auquel le livre appartient. Attention, cette collection n'est pas modifiable.
	 * Si vous souhaitez ajouter ou retirer des {@link Category}, utilisez les méthodes addCategory, removeCategory et replaceCategories
	 * @return
	 */
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
	
	public void synchronizeCategories(Collection<Category> cats){
		if(this.categories == null){
			this.categories = new HashSet<Category>();
		}
		
		this.categories.retainAll(cats);
		this.categories.addAll(cats);
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
