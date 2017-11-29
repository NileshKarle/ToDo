var toDo = angular.module('toDo', ['ui.router', 'ngSanitize', 'ui.bootstrap', 'toastr','ui.bootstrap.datepicker']);

toDo.config([ '$stateProvider', '$urlRouterProvider',
		function($stateProvider, $urlRouterProvider) {

			$stateProvider.state('register', {
				url : '/register',
				templateUrl : 'template/Register.html',
				controller : 'registerController'
			});

			$stateProvider.state('login', {
				url : '/login',
				templateUrl : 'template/login.html',
				controller : 'loginController'
			});
			
			$stateProvider.state('EmailVerification', {
				url : '/EmailVerification',
				templateUrl : 'template/EmailVerification.html',
				controller : 'EmailVerificationController'
			});
			
			$stateProvider.state('forgotPassword', {
				url : '/forgotPassword',
				templateUrl : 'template/ForgotPassword.html',
				controller : 'forgotpasswordController'
			});

			$stateProvider.state('home', {
				url : '/home',
				templateUrl : 'template/Home.html',
					controller: 'homeController'
			});
			
			$stateProvider.state('dummyPage',{
				url : '/dummyPage',
				templateUrl : 'template/DummyPage.html',
				controller : 'dummyController'
			});
			
			$stateProvider.state('archive', {
				url : '/archive',
				templateUrl : 'template/Archive.html',
				controller: 'homeController'
			});

			$stateProvider.state('trash',{
				url : '/trash',
				templateUrl : 'template/Trash.html',
				controller: 'homeController'
			});
			
			$stateProvider.state('reminder',{
				url : '/reminder',
				templateUrl : 'template/reminder.html',
				controller: 'homeController'
			});
			
			$stateProvider.state('searchPage',{
				url : '/searchPage',
				templateUrl : 'template/searchPage.html',
				controller: 'homeController'
			});
			
			$urlRouterProvider.otherwise('login');
		} ]);
