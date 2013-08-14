package be.fabrice.proto.persistence.dao.hibernate;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import be.fabrice.proto.model.Isbn;
import be.fabrice.proto.model.entity.Book;
import be.fabrice.proto.persistence.dao.BookDao;
import be.fabrice.utils.TestUtilReflexion;

@ContextConfiguration(locations="classpath:proto/proto-test-applicationContext.xml")
public class BookHibernateDaoTest extends AbstractTransactionalTestNGSpringContextTests{
	private class BookRowMapper implements RowMapper<Book>{
		public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
			Book book = new Book(
					new Isbn(rs.getString("ISBN")),
					rs.getString("TITLE"),
					rs.getString("AUTHOR")
			);
			TestUtilReflexion.setProperty(book, "id", rs.getInt("ID"));
			return book;
		}
	}
	
	@Autowired
	private BookDao bookDao;
	
	@BeforeMethod
	public void beforeMethod(){
		executeSqlScript("proto/script.sql",false);
	}
	
	@Test
	public void testFindExistingBookReturnsBook(){
		Book b = bookDao.find(1000);
		assertEquals(b.getId(), Integer.valueOf(1000));
		assertEquals(b.getTitle(), "Test book");
		assertEquals(b.getIsbn().getValue(),"9782736126650");
	}
	
	@Test
	public void testInsertNewBook(){
		Book b = new Book(new Isbn("9/782736/126650"),"Dune","Frank Herbert");
		bookDao.save(b);
		assertNotNull(b.getId());
		List<Book> books = jdbcTemplate.query("select * from BOOK where ID=?",new BookRowMapper(),b.getId());
		assertEquals(books.size(), 1);
		Book bInDb = books.get(0);
		assertEquals(bInDb.getIsbn().getValue(),"9782736126650");
		assertEquals(bInDb.getTitle(),"Dune");
		assertEquals(bInDb.getAuthor(),"Frank Herbert");
	}
	
	@AfterMethod
	public void afterMethod(){
		deleteFromTables("BOOK");
	}
}
