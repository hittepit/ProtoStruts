package be.fabrice.proto.persistence.dao.hibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import be.fabrice.proto.model.entity.Book;
import be.fabrice.proto.persistence.dao.BookDao;

@Repository("bookDao")
@Transactional(readOnly=true)
public class BookHibernateDao extends HibernateDaoSupport implements BookDao{

	public Book find(Integer id) {
		return (Book)getSession().get(Book.class,id);
	}

	public List<Book> findAll() {
		return getSession().createQuery("from Book").list();
	}

	@Transactional(readOnly=false)
	public void save(Book book) {
		getSession().saveOrUpdate(book);
	}

}
