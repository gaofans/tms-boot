<!DOCTYPE html>
<html lang="en">
<head>
    <#include "../includes/_base_href.ftl">
    <#include "../includes/_head.ftl">
    <title>500 服务器异常</title>
    <link rel="stylesheet" href="assets/common/css/error-page.css"/>
</head>
<body>
<#include "../includes/_loading.ftl">
<div class="error-page">
    <img class="error-page-img" src="assets/common/images/ic_500.png">
    <div class="error-page-info">
        <h1>500</h1>
        <div class="error-page-info-desc">服务器异常</div>
        <p>${(error)!''}</p>
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