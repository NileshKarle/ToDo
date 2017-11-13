var toDo = angular.module('toDo', ['ui.router', 'ngSanitize']);

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
			
			$urlRouterProvider.otherwise('login');
		} ]);
