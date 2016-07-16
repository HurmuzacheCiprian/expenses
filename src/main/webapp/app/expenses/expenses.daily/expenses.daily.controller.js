(function() {
    'use strict';

    angular
        .module('expensesApp')
        .controller('ExpensesDailyController',ExpensesDailyController);

    ExpensesDailyController.$inject = ['$scope'];

    function ExpensesDailyController($scope) {
        $scope.message = 'Daily expenses controller';
    }

})();
