(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('UserSkuDeleteController',UserSkuDeleteController);

    UserSkuDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserSku'];

    function UserSkuDeleteController($uibModalInstance, entity, UserSku) {
        var vm = this;

        vm.userSku = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserSku.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
