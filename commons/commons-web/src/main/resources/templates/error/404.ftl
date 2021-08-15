<!DOCTYPE html>
<html lang="en">
<head>
    <#include "../includes/_base_href.ftl">
    <#include "../includes/_head.ftl">
    <link rel="stylesheet" href="assets/common/css/error-page.css"/>
    <title>404 页面找不到</title>
</head>
<body>
<#include "../includes/_loading.ftl">
<div class="error-page">
    <img class="error-page-img" src="assets/common/images/ic_404.png">
    <div class="error-page-info">
        <h1>404</h1>
        <div class="error-page-info-desc">啊哦，你访问的页面不存在(⋟﹏⋞)</div>
    </div>
</div>

<script>
    layui.use(['admin'], function () {
        var $ = layui.jquery;
        var admin = layui.admin;

    });
</script>
</body>
</html>