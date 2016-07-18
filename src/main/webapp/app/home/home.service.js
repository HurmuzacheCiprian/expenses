(function() {
    'use strict';

    angular
        .module('expensesApp')
        .service('HomeService', HomeService);

    HomeService.$inject = ['$http'];

    function HomeService($http) {
        return {
            getLastThreeDayExpenses: getLastThreeDayExpenses
        }

        function getLastThreeDayExpenses() {
            return $http({
                url: '/expenses/last-three-days',
                method: 'GET'
            });
        }

    }

})();
