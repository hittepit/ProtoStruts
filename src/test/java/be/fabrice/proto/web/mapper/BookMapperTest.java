package be.fabrice.proto.web.mapper;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import be.fabrice.proto.model.Isbn;
import be.fabrice.proto.model.entity.Book;
import be.fabrice.proto.service.BookService;
import be.fabrice.proto.web.vo.BookVo;
import be.fabrice.utils.TestUtilReflexion;

public class BookMapperTest {
	private BookMapper mapper;
	
	@BeforeMethod
	public void beforeMethod(){
		mapper = new BookMapper();
		Book book = new Book(new Isbn("9780123456789"),"titre","auteur");
		TestUtilReflexion.setProperty(book, "id", Integer.valueOf(17));
		
		BookService bookService = mock(BookService.class);
		when(bookService.find(Integer.valueOf(17))).thenReturn(book);
		TestUtilReflexion.setProperty(mapper, "bookService", bookService);		
	}
	
	@Test
	public void testMapEntityReturnsAVo(){
		Book book = new Book(new Isbn("9780123456789"),"titre","auteur");
		TestUtilReflexion.setProperty(book, "id", Integer.valueOf(17));
		BookVo vo = mapper.map(book);
		
		assertEquals(vo.getId(), Integer.valueOf(17));
		assertEquals(vo.getTitle(),"titre");
		assertEquals(vo.getAuthor(),"auteur");
		assertEquals(vo.getIsbn(),"9780123456789");
	}
	
	@Test
	public void testMapListOfEntitiesReturnsListOfVos(){
		ArrayList<Book> books = new ArrayList<Book>();
		Book book = new Book(new Isbn("9780123456781"),"titre1","auteur1");
		TestUtilReflexion.setProperty(book, "id", Integer.valueOf(1));
		books.add(book);
		book = new Book(new Isbn("9780123456782"),"titre2","auteur2");
		TestUtilReflexion.setProperty(book, "id", Integer.valueOf(2));
		books.add(book);
		
		List<BookVo> vos = mapper.map(books);
		
		assertEquals(vos.size(),2);
	}
	
	@Test
	public void testMapOfAVoWithIdreturnsTheModifiedEntityWith(){
		BookVo vo = new BookVo();
		vo.setId(Integer.valueOf(17));
		vo.setIsbn("9780000000000");
		vo.setTitle("titre");
		vo.setAuthor("toto");
		
		Book entity = mapper.map(vo);
		assertEquals(entity.getId(),Integer.valueOf(17));
		assertEquals(entity.getTitle(),"titre");
		assertEquals(entity.getIsbn(),new Isbn("9780123456789"),"Ne doit pas avoir été modifié");
		assertEquals(entity.getAuthor(),"toto");
	}
	
	@Test
	public void testMapOfAVoWithoutIdReturnsANewTransientEntity(){
		BookVo vo = new BookVo();
		vo.setIsbn("9780000000000");
		vo.setTitle("titre");
		vo.setAuthor("toto");
		
		Book entity = mapper.map(vo);
		assertNull(entity.getId());
		assertEquals(entity.getTitle(),"titre");
		assertEquals(entity.getIsbn(),new Isbn("9780000000000"));
		assertEquals(entity.getAuthor(),"toto");
	}
}
