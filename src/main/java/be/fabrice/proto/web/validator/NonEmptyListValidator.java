package be.fabrice.proto.web.validator;

import java.util.List;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

public class NonEmptyListValidator extends FieldValidatorSupport {

	@Override
	public void validate(Object object) throws ValidationException {
		String fieldName = getFieldName();
		List<?> list = (List<?>)getFieldValue(fieldName, object);
		if(list== null || list.isEmpty()){
			addFieldError(fieldName, object);
		}
	}

}
