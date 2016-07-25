(function () {
    'use strict';

    angular
        .module('expensesApp')
        .service('ExpensesYearlyService',ExpensesYearlyService);

    ExpensesYearlyService.$inject = ['$http'];

    function ExpensesYearlyService($http) {
        return {
            getYearlyExpenses: getYearlyExpenses
        }

        function getYearlyExpenses() {
            return $http({
                url: '/expenses/yearly-expenses',
                method: 'GET'
            });
        }

    }


})();
