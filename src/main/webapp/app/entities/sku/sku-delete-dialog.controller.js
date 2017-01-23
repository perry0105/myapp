(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('SkuDeleteController',SkuDeleteController);

    SkuDeleteController.$inject = ['$uibModalInstance', 'entity', 'Sku'];

    function SkuDeleteController($uibModalInstance, entity, Sku) {
        var vm = this;

        vm.sku = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Sku.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
