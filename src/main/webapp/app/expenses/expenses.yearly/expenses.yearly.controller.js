(function () {
    'use strict';

    angular
        .module('expensesApp')
        .controller('ExpensesYearlyController',ExpensesYearlyController);

    ExpensesYearlyController.$inject = ['$scope','ExpensesYearlyService'];

    function ExpensesYearlyController($scope,ExpensesYearlyService) {
        var vm = this;

        $scope.yearlyExpenses = [];

        vm.init = init;

        vm.init();

        function init() {
            ExpensesYearlyService.getYearlyExpenses()
                .then(function (data) {
                    $scope.yearlyExpenses = data.data.yearlyExpenses;
                    console.log($scope.yearlyExpenses);
                }, function (error) {
                    console.log(error);
                });
        }
    }


})();
