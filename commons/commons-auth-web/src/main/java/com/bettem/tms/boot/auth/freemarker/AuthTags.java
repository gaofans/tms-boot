package com.bettem.tms.boot.auth.freemarker;

import com.bettem.tms.boot.auth.utils.CurrentUserKit;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.SimpleHash;

/**
 * 自定义的权限标签
 * @author GaoFans
 */
public class AuthTags extends SimpleHash {

    public AuthTags(CurrentUserKit currentUserKit) {
        super(new DefaultObjectWrapper(Configuration.VERSION_2_3_29));
        this.put("hasPermission", new HasPermissionTag(currentUserKit));
        this.put("hasRole", new HasRoleTag(currentUserKit));
    }
}
