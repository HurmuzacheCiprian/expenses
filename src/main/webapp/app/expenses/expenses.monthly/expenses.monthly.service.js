(function() {
    'use strict';

    angular
        .module('expensesApp')
        .service('ExpensesMonthlyService',ExpensesMonthlyService);

    ExpensesMonthlyService.$inject = ['$http'];

    function ExpensesMonthlyService($http) {
        return {
            getMonthlyExpenses: getMonthlyExpenses
        }

        function getMonthlyExpenses() {
            return $http({
                url: '/expenses/monthly-expenses',
                method: 'GET'
            });
        }

    }


})();
