package be.fabrice.proto.persistence.dao.hibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import be.fabrice.proto.model.entity.Category;
import be.fabrice.proto.persistence.dao.CategoryDao;

@Repository
@Transactional(readOnly=true)
public class CategoryHibernateDao extends HibernateDaoSupport implements CategoryDao {

	@Override
	public List<Category> findAll() {
		return getSession().createQuery("from Category c").list();
	}

}
