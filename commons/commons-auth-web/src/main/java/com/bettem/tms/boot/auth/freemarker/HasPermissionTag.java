package com.bettem.tms.boot.auth.freemarker;

import com.bettem.tms.boot.auth.utils.CurrentUserKit;
import com.bettem.tms.boot.commons.utils.StringUtil;

/**
 * 判断是否拥有某个权限
 * @author GaoFans
 */
public class HasPermissionTag extends AbstractPermissionTag {

    public HasPermissionTag(CurrentUserKit currentUserKit) {
        super(currentUserKit);
    }

    @Override
    protected boolean showTagBody(String p) {
        if(StringUtil.isEmpty(p)){
            return false;
        }
        return this.isPermitted(p);
    }
}
