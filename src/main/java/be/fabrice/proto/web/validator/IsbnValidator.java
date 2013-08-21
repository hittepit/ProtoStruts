package be.fabrice.proto.web.validator;

import be.fabrice.proto.model.Isbn;
import be.fabrice.proto.web.vo.BookVo;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

public class IsbnValidator extends FieldValidatorSupport {
	@Override
	public void validate(Object object) throws ValidationException {
		BookVo bookVo = (BookVo)getFieldValue("bookVo", object);
		if(!Isbn.validate(bookVo.getIsbn())){
			addFieldError("bookVo.isbn",object);
		}
	}
}
