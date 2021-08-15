<!DOCTYPE html>
<html lang="en">
<head>
    <#include "../includes/_base_href.ftl">
    <#include "../includes/_head.ftl">
    <link rel="stylesheet" href="assets/common/css/error-page.css"/>
    <title>401 权限不足</title>
</head>
<body>
<#include "../includes/_loading.ftl">
<div class="error-page">
    <img class="error-page-img" src="assets/common/images/ic_403.png">
    <div class="error-page-info">
        <h1>403</h1>
        <div class="error-page-info-desc">抱歉，你无权访问此页面</div>
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