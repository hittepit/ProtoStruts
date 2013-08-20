package be.fabrice.proto.web.result;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONResult;
import org.apache.struts2.json.JSONUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;

public class ValidationAwareJSONResult extends JSONResult {
	@Override
	public void execute(ActionInvocation invocation) throws Exception {
        ActionContext actionContext = invocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
        HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);

        Object action = invocation.getAction();

        try {
            Object rootObject;
            rootObject = readRootObject(invocation);
            writeToResponse(response, createJSONString(request, rootObject), enableGzip(request));
        } catch (IOException exception) {
//            LOG.error(exception.getMessage(), exception);
            throw exception;
        }
	}

    private Object readRootObject(ActionInvocation invocation) {
        if (isEnableSMD()) {
            return buildSMDObject(invocation);
        }
        return findRootObject(invocation);
    }

    private String createJSONString(HttpServletRequest request, Object rootObject) throws JSONException {
        String json;
        json = JSONUtil.serialize(rootObject, getExcludePropertiesList(), getIncludePropertiesList(), isIgnoreHierarchy(),
                isEnumAsBean(), isExcludeNullProperties());
        json = addCallbackIfApplicable(request, json);
        return json;
    }

    private boolean enableGzip(HttpServletRequest request) {
        return isEnableGZIP() && JSONUtil.isGzipInRequest(request);
    }

    private Object findRootObject(ActionInvocation invocation) {
        Object rootObject;
        if (this.getRoot() != null) {
            ValueStack stack = invocation.getStack();
            rootObject = stack.findValue(getRoot());
        } else {
            rootObject = invocation.getStack().peek(); // model overrides action
        }
        return rootObject;
    }

}
