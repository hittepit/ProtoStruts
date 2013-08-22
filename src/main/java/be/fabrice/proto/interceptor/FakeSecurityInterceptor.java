package be.fabrice.proto.interceptor;

import be.fabrice.proto.web.action.BookJsonAction;
import be.fabrice.proto.web.action.BooksAction;
import be.fabrice.proto.web.vo.BookVo;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * A des fins de démo seulement, uniquement pour {@link BooksAction} si le livre auquel on essaye d'accéder a
 * le titre "unauthorized"
 * @author fabrice.claes
 *
 */
public class FakeSecurityInterceptor implements Interceptor{

	@Override
	public void destroy() {}

	@Override
	public void init() {}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Object action = invocation.getAction();
		if(action instanceof BookJsonAction){
			BookVo book = ((BookJsonAction)action).getBookVo();
			if(book!=null && "unauthorized".equals(book.getTitle())){
				return "unauthorized";
			}
		}
		return invocation.invoke();
	}

}
