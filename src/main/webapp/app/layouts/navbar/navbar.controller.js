(function() {
    'use strict';

    angular
        .module('expensesApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$state', 'Auth', 'Principal', 'ProfileService', 'LoginService'];

    function NavbarController ($state, Auth, Principal, ProfileService, LoginService) {
        var vm = this;

        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;


        vm.init = init;
        vm.login = login;
        vm.logout = logout;
        vm.account = null;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.$state = $state;

        init();

        function init() {
            ProfileService.getProfileInfo().then(function(response) {
                vm.inProduction = response.inProduction;
                vm.swaggerDisabled = response.swaggerDisabled;
            });

            Principal.identity().then(function(account) {
                vm.account = account;
            });
        }

        function login() {
            collapseNavbar();
            LoginService.open();
        }

        function logout() {
            collapseNavbar();
            Auth.logout();
            $state.go('home');
        }

        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }
    }
})();
