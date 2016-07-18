(function() {
    'use strict';

    angular
        .module('expensesApp')
        .service('HomeService', HomeService);

    HomeService.$inject = ['$http'];

    function HomeService($http) {
        return {
            getLastThreeDayExpenses: getLastThreeDayExpenses,
            saveFund: saveFund,
            getFunds: getFunds,
            removeFund: removeFund
        }

        function removeFund(fundId) {
            return $http({
                url: '/funds?fundId='+fundId,
                method: 'DELETE'
            });
        }

        function getFunds() {
            return $http({
                url: '/funds',
                method: 'GET'
            });
        }

        function getLastThreeDayExpenses() {
            return $http({
                url: '/expenses/last-three-days',
                method: 'GET'
            });
        }

        function saveFund(expense) {
            return $http({
                url: '/funds',
                method: 'POST',
                data: expense
            });
        }

    }

})();
