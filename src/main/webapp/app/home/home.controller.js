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
        $scope.removeFund = removeFund;
        $scope.fund = {};
        $scope.funds = [];
        $scope.registerFund = registerFund;
        $scope.progressiveFadeIn = progressiveFadeIn;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });
        var vm = this;
        vm.init = init;

        getAccount();
        vm.init();

        function init() {
            $scope.funds = [];
            $scope.items = [];
            HomeService.getLastThreeDayExpenses()
                .then(function(data) {
                    console.log(data.data);
                    $scope.items = data.data.dailyExpensesDto;
                }, function (error) {
                    console.log(error);
                });

            HomeService.getFunds()
                    .then(function(data) {
                        $scope.funds = data.data;
                    });
            HomeService.getAvailableFunds()
                .then(function (data) {
                    $scope.availableFunds = data.data;
                });
        }

        function removeFund(fundId) {
            $scope.funds.forEach(function (data, index) {
                if(data.id === fundId) {
                    console.log(index);
                    $scope.funds.splice(index,1);
                }
            });
            HomeService.removeFund(fundId);
        }

        function details(item) {

        }

        function registerFund() {
            HomeService.saveFund($scope.fund)
                .then(function (data) {
                    if(data.status===200) {
                        HomeService.getFunds()
                            .then(function(data) {
                               $scope.funds = data.data;
                               $scope.fund = {name: '', amount: ''};
                             });
                    }
                });
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
