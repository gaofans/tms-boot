package com.bettem.tms.boot.auth.freemarker;

import com.bettem.tms.boot.auth.model.BaseUser;
import com.bettem.tms.boot.auth.utils.CurrentUserKit;
import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.util.Map;

/**
 * 抽象的鉴权标签
 * @author GaoFans
 */
public abstract class AbstractSecureTag implements TemplateDirectiveModel {

    private CurrentUserKit currentUserKit;

    public AbstractSecureTag(CurrentUserKit currentUserKit) {
        this.currentUserKit = currentUserKit;
    }

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        this.verifyParameters(params);
        this.render(env, params, body);
    }

    /**
     * 渲染标签中的内容
     * @param env
     * @param map
     * @param body
     * @throws IOException
     * @throws TemplateException
     */
    public abstract void render(Environment env, Map map, TemplateDirectiveBody body) throws IOException, TemplateException;

    protected String getParam(Map params, String name) {
        Object value = params.get(name);
        return value instanceof SimpleScalar ? ((SimpleScalar)value).getAsString() : null;
    }

    protected BaseUser getUser() {
        return currentUserKit.currentUser();
    }

    protected void verifyParameters(Map params) throws TemplateModelException {
    }

    protected void renderBody(Environment env, TemplateDirectiveBody body) throws IOException, TemplateException {
        if (body != null) {
            body.render(env.getOut());
        }
    }
}
