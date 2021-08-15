package com.bettem.tms.boot.auth.freemarker;

import com.bettem.tms.boot.auth.utils.CurrentUserKit;
import com.bettem.tms.boot.commons.utils.StringUtil;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 抽象的角色标签
 * @author GaoFans
 */
public abstract class AbstractRoleTag extends AbstractSecureTag {

    /**
     * 角色的名称，支持多个，逗号隔开，or的关系
     */
    private static final String NAME = "name";

    public AbstractRoleTag(CurrentUserKit currentUserKit) {
        super(currentUserKit);
    }

    String getName(Map params) {
        return this.getParam(params, NAME);
    }

    @Override
    public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException {
        String r = this.getName(params);
        if(StringUtil.isEmpty(r)){
            return;
        }
        List<String> rs = Arrays.stream(r.split(",")).map(s -> s.trim()).filter(s -> s.length() > 0).collect(Collectors.toList());
        for (String s : rs) {
            if(this.showTagBody(s)){
                this.renderBody(env, body);
                break;
            }
        }
    }

    /**
     * 是否展示标签内容
     * @param r
     * @return
     */
    protected abstract boolean showTagBody(String r);
}
