package com.bettem.tms.boot.auth.freemarker;

import com.bettem.tms.boot.auth.utils.CurrentUserKit;
import com.bettem.tms.boot.commons.utils.StringUtil;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 抽象的权限标签
 * @author GaoFans
 */
public abstract class AbstractPermissionTag extends AbstractSecureTag {

    /**
     * 权限的名称，支持多个，逗号隔开，or的关系
     */
    private static final String NAME = "name";

    public AbstractPermissionTag(CurrentUserKit currentUserKit) {
        super(currentUserKit);
    }

    String getName(Map params) {
        return this.getParam(params, NAME);
    }

    @Override
    public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException {
        String p = this.getName(params);
        //为空直接不显示了
        if(StringUtil.isEmpty(p)){
            return;
        }
        //处理一下
        List<String> ps = Arrays.stream(p.split(",")).map(s -> s.trim()).filter(s -> s.length() > 0).collect(Collectors.toList());
        for (String s : ps) {
            if(this.showTagBody(s)){
                this.renderBody(env, body);
                break;
            }
        }

    }

    /**
     * 查找是否拥有该权限
     * @param p
     * @return
     */
    protected boolean isPermitted(String p) {
        return Collections.binarySearch(this.getUser().getAuthorities(),() -> p) >= 0;
    }

    /**
     * 是否展示标签中的内容
     * @param var1
     * @return
     */
    protected abstract boolean showTagBody(String var1);
}
