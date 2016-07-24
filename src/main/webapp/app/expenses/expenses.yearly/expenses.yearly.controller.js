(function () {
    'use strict';

    angular
        .module('expensesApp')
        .controller('ExpensesYearlyController',ExpensesYearlyController);

    ExpensesYearlyController.$inject = ['$scope'];


    function ExpensesYearlyController($scope) {
        $scope.message = 'Yearly expenses';
    }


})();
