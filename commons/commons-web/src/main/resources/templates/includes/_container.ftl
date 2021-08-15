<#macro container script="" title="" form="" css="" editor="" echarts="">
    <!DOCTYPE html>
    <html lang="en">
        <head>
            <#if title != "">
                <title>${title}</title>
            </#if>
            <#include "_base_href.ftl">
            <#include "_head.ftl">
            <#if editor="y">
                <script src="assets/common/libs/ckeditor/ckeditor.js"></script>
                <script src="assets/common/libs/ckeditor/lang/zh-cn.js"></script>
            </#if>
            <#if echarts="y">
                <script src="assets/common/libs/echarts/echarts.min.js"></script>
            </#if>
            <#if form == "y">
                <link rel="stylesheet" type='text/css' href="assets/expand/css/form.css" media="all"/>
            </#if>
            <#if css != "">
                <link rel="stylesheet" type='text/css' href="${css}"/>
            </#if>
        </head>
        <#include "_loading.ftl">
        <body>
            <#nested>
            <#if script != "">
                <script src="${script}"></script>
            </#if>
        </body>
    </html>
</#macro>