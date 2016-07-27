(function () {
    'use strict';

    angular
        .module('expensesApp')
        .controller('ExpensesYearlyController',ExpensesYearlyController);

    ExpensesYearlyController.$inject = ['$scope','ExpensesYearlyService'];

    function ExpensesYearlyController($scope,ExpensesYearlyService) {
        var vm = this;

        $scope.yearlyExpenses = [];
        $scope.months = [];
        $scope.categories = [];
        $scope.data = [2,0,0,2,3,1,0,22,44,10,1];
        $scope.getData = getData;
        vm.init = init;
        vm.init();

        function init() {
            ExpensesYearlyService.getCategories()
                .then(function (data) {
                    $scope.categories = data.data;
                    console.log($scope.categories);
                ExpensesYearlyService.getYearlyExpenses()
                                .then(function (data) {
                                    $scope.yearlyExpenses = data.data.yearlyExpenses;
                                    $scope.months = Object.keys(data.data.yearlyExpenses);

                                }, function (error) {
                                    console.log(error);
                                });
            });

        }

        function getData(month) {
            return Object.keys($scope.yearlyExpenses[month]).map(k => $scope.yearlyExpenses[month][k]);

        }


    }


})();
