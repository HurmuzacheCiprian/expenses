(function() {
    'use strict';

    angular
        .module('expensesApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'HomeService'];

    function HomeController ($scope, Principal, LoginService, $state, HomeService) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        vm.fadeInClasses = ['one','two','three'];
        $scope.items = [];
        $scope.details = details;
        $scope.fund = {};
        $scope.funds = [];
        $scope.registerFund = registerFund;
        $scope.progressiveFadeIn = progressiveFadeIn;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();
        init();

        function init() {
            HomeService.getLastThreeDayExpenses()
                .then(function(data) {
                    console.log(data.data);
                    $scope.items = data.data.dailyExpensesDto;
                }, function (error) {
                    console.log(error);
                });
        }

        function details(item) {

        }

        function registerFund() {

        }

        function progressiveFadeIn(index) {
            return vm.fadeInClasses[index % vm.fadeInClasses.length];
        }

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
    }
})();
