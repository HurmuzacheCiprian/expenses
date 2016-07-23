(function() {

    'use strict';

    angular
        .module('expensesApp')
        .controller('ExpensesMonthlyController',ExpensesMonthlyController);

    ExpensesMonthlyController.$inject = ['$scope','ExpensesMonthlyService'];

    function ExpensesMonthlyController($scope, ExpensesMonthlyService) {
        var vm = this;
        $scope.message = 'Monthly expenses';

        vm.init = init;

        vm.init();

        function init() {
            ExpensesMonthlyService.getMonthlyExpenses()
                .then(function(data) {
                $scope.currentMonth = data.data.currentMonth;
                $scope.expenses = data.data.monthlyExpenses;
                   console.log(data.data);
                }, function (error) {

                });
            ExpensesMonthlyService.getMonthlyCategoryInfo()
                .then(function (data) {
                    $scope.categoryMonthlyInfo = data.data;
                });
        }

    }


})();
