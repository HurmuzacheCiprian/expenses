(function() {
    'use strict';

    angular
        .module('expensesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('dailyExpenses', {
            parent: 'expenses',
            url: '/daily',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/expenses/expenses.daily/expenses.daily.html',
                    controller: 'ExpensesDailyController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
