(function() {

    'use strict';

    angular
        .module('expensesApp')
        .controller('ExpensesMonthlyController',ExpensesMonthlyController);

    ExpensesMonthlyController.$inject = ['$scope'];

    function ExpensesMonthlyController($scope) {
        $scope.message = 'Monthly expenses';
    }


})();
