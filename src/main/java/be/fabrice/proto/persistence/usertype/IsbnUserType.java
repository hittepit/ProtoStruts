package be.fabrice.proto.persistence.usertype;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import be.fabrice.proto.model.Isbn;

public class IsbnUserType implements UserType{

	public int[] sqlTypes() {
		return new int[]{Types.VARCHAR};
	}

	public Class<Isbn> returnedClass() {
		return Isbn.class;
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		return (x==null && y==null) || ((x!=null)& x.equals(y));
	}

	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
		String i = rs.getString(names[0]);
		return new Isbn(i);
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
		st.setString(index,((Isbn)value).getValue());
	}

	public Object deepCopy(Object value) throws HibernateException {
		return new Isbn(((Isbn)value).getValue());
	}

	public boolean isMutable() {
		return false;
	}

	public Serializable disassemble(Object value) throws HibernateException {
		return ((Isbn)value).getValue();
	}

	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return new Isbn((String)cached);
	}

	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

}
