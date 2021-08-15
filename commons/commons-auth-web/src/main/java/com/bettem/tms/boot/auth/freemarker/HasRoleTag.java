package com.bettem.tms.boot.auth.freemarker;

import com.bettem.tms.boot.auth.utils.CurrentUserKit;
import com.bettem.tms.boot.commons.utils.StringUtil;

import java.util.Collections;

/**
 * 角色校验标签
 * @author GaoFans
 */
public class HasRoleTag extends AbstractRoleTag {
    public HasRoleTag(CurrentUserKit currentUserKit) {
        super(currentUserKit);
    }

    /**
     * 查找当前用户是否拥有这个角色
     * @param r
     * @return
     */
    @Override
    protected boolean showTagBody(String r) {
        if (StringUtil.isEmpty(r)){
            return false;
        }
        return Collections.binarySearch(this.getUser().getRoles(),() -> r) >= 0;
    }

}
