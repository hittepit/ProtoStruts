package be.fabrice.proto.model.entity;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.Test;

import be.fabrice.proto.model.Isbn;

public class TestBook {
	private Isbn isbn = new Isbn("9782736126650");

	@Test
	public void testBookCreationWithCorrectValuesCreatesABookWithCorrectTrimmedProperties(){
		Book b = new Book(isbn," titre "," auteur  ");
		assertEquals(b.getTitle(), "titre");
		assertEquals(b.getAuthor(),"auteur");
		assertEquals(b.getIsbn(),isbn);
	}

	@Test
	public void testBookCreationSetNullAuthorIfAuthorIsEmpty(){
		Book b = new Book(isbn," titre ","  ");
		assertEquals(b.getTitle(), "titre");
		assertNull(b.getAuthor());
		assertEquals(b.getIsbn(),isbn);
	}
	
	@Test(expectedExceptions=IllegalArgumentException.class)
	public void testBookCreationFailsIfNullTitle(){
		new Book(isbn,null," auteur  ");
	}
	
	@Test(expectedExceptions=IllegalArgumentException.class)
	public void testBookCreationFailsIfEmptyTitle(){
		new Book(isbn,"  "," auteur  ");
	}
	
	@Test(expectedExceptions=IllegalArgumentException.class)
	public void testBookCreationFailsIfNullIsbn(){
		new Book(null," titre"," auteur  ");
	}
	
	@Test
	public void testCategoriesIsEmptyAtCreation(){
		Book b = new Book(isbn," titre ","  ");
		assertTrue(b.getCategories().isEmpty());
	}
	
	@Test
	public void testAddNewCategoryToEmptyCollectionAddsCategoryToCollection(){
		Category c1 = new Category("test1","juste un test");
		Book b = new Book(isbn," titre ","  ");
		b.addCategory(c1);
		assertEquals(b.getCategories().size(),1);
		assertTrue(b.getCategories().contains(c1));
	}
	
	@Test
	public void testAddNewCategoryToNonEmptyCollectionAddsCategoryToCollection(){
		Category c1 = new Category("test1","juste un test");
		Category c2 = new Category("test2","juste un test");
		Book b = new Book(isbn," titre ","  ");
		b.addCategory(c1);
		b.addCategory(c2);
		assertEquals(b.getCategories().size(),2);
		assertTrue(b.getCategories().contains(c1));
		assertTrue(b.getCategories().contains(c2));
	}
	
	@Test
	public void testAddExistingCategoryDoesNotChangeCollection(){
		Category c1 = new Category("test1","juste un test");
		Category c2 = new Category("test2","juste un test");
		Book b = new Book(isbn," titre ","  ");
		b.addCategory(c1);
		b.addCategory(c2);
		b.addCategory(c2);
		assertEquals(b.getCategories().size(),2);
		assertTrue(b.getCategories().contains(c1));
		assertTrue(b.getCategories().contains(c2));
	}
	
	@Test
	public void testRemovingExistingCategoryRemovesFromCollection(){
		Category c1 = new Category("test1","juste un test");
		Category c2 = new Category("test2","juste un test");
		Book b = new Book(isbn," titre ","  ");
		b.addCategory(c1);
		b.addCategory(c2);
		b.removeCategory(c1);
		assertEquals(b.getCategories().size(),1);
		assertTrue(b.getCategories().contains(c2));
	}
	
	@Test
	public void testRemovingNonExistingCategoryRemovesNothingFromCollection(){
		Category c1 = new Category("test1","juste un test");
		Category c2 = new Category("test2","juste un test");
		Category c3 = new Category("test3","juste un test");
		Book b = new Book(isbn," titre ","  ");
		b.addCategory(c1);
		b.addCategory(c2);
		b.removeCategory(c3);
		assertEquals(b.getCategories().size(),2);
		assertTrue(b.getCategories().contains(c2));
		assertTrue(b.getCategories().contains(c1));
		assertFalse(b.getCategories().contains(c3));
	}
	
	@Test
	public void testSynchronizeCategoriesSynrhconizeCollection(){
		Category c1 = new Category("test1","juste un test");
		Category c2 = new Category("test2","juste un test");
		Category c3 = new Category("test3","juste un test");
		Book b = new Book(isbn," titre ","  ");
		b.addCategory(c1);
		b.addCategory(c2);
		
		Set<Category> updatedCategories = new HashSet<Category>();
		updatedCategories.add(c1);
		updatedCategories.add(c3);
		
		b.synchronizeCategories(updatedCategories);
		assertEquals(b.getCategories().size(),2);
		assertTrue(b.getCategories().contains(c1));
		assertTrue(b.getCategories().contains(c3));
		assertFalse(b.getCategories().contains(c2));
	}
}
