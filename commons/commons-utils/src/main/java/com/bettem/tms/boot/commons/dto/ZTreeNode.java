package com.bettem.tms.boot.commons.dto;


/**
 * jquery ztree 插件的节点
 * @author gaofans
 */
public class ZTreeNode {

    /**
     * 节点id
     */
    private String id;

    /**
     * 父节点id
     */
    private String pid;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 是否打开节点
     */
    private Boolean open;

    /**
     * 是否被选中
     */
    private Boolean checked;

    /**
     * 节点图标  single or group
     */
    private String iconSkin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getIconSkin() {
        return iconSkin;
    }

    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    /**
     * 创建ztree的父级节点
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:51 PM
     */
    public static ZTreeNode createParent() {
        ZTreeNode zTreeNode = new ZTreeNode();
        zTreeNode.setChecked(true);
        zTreeNode.setId("0");
        zTreeNode.setName("顶级");
        zTreeNode.setOpen(true);
        zTreeNode.setPid("0");
        return zTreeNode;
    }
}
