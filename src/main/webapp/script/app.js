var toDo = angular.module('toDo', [ 'ui.router' ]);

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
			
			$stateProvider.state('forgotPassword', {
				url : '/forgotPassword',
				templateUrl : 'template/ForgotPassword.html',
				controller : 'forgotpasswordController'
			});

			$stateProvider.state('home', {
				url : '/home',
				templateUrl : 'template/Home.html'
			// controller: 'loginController'
			});

			$urlRouterProvider.otherwise('login');
		} ]);
