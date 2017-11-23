<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.6/angular.min.js"></script>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-router/1.0.3/angular-ui-router.min.js"></script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
 
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.8/angular-sanitize.js"></script>

<script src="bower_components/angular-bootstrap/ui-bootstrap-tpls.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<script type="text/javascript" src="script/app.js"></script>

<script src="controller/RegisterController.js"></script>
<script src="controller/loginController.js"></script>
<script src="controller/HomePageController.js"></script>
<script src="controller/forgotPasswordController.js"></script>
<script src="controller/EmailVerificationController.js"></script>
<script src="controller/DummyPageController.js"></script>

<script type="text/javascript" src="service/RegisterService.js"></script>
<script type="text/javascript" src="service/LoginService.js"></script>
<script type="text/javascript" src="service/ForgotPasswordService.js"></script>
<script type="text/javascript" src="service/HomePageService.js"></script>
<script type="text/javascript" src="service/EmailVerificationService.js"></script>
<script type="text/javascript" src="service/DummyPageService.js"></script>

<script type="text/javascript" src="Directives/CustomiseDirective.js"></script>

<link rel="stylesheet" href="style/RegisterStyle.css">
<link rel="stylesheet" href="style/LoginStyle.css">
<link rel="stylesheet" href="style/TopNavigationBar.css">
<link rel="stylesheet" href="style/SideNavigationBar.css">
<link rel="stylesheet" href="style/HomePage.css">
<link rel="stylesheet" href="style/AddNote.css">

<style type="text/css"></style>

</head>

<body ng-app="toDo">
	<div ui-view></div>
</body>
</html>