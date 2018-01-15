package com.soauth.api.client.openid.connect.validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author zhoujie
 * @date 2017/12/13
 */
public class ImplicitValidator extends  AbstractValidator {


    public ImplicitValidator(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    protected boolean validateSelf() {
        return false;
    }
}
