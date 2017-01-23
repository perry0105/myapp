(function() {
    'use strict';

    angular
        .module('myappApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-sku', {
            parent: 'entity',
            url: '/user-sku?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UserSkus'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-sku/user-skus.html',
                    controller: 'UserSkuController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('user-sku-detail', {
            parent: 'entity',
            url: '/user-sku/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UserSku'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-sku/user-sku-detail.html',
                    controller: 'UserSkuDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'UserSku', function($stateParams, UserSku) {
                    return UserSku.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-sku',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-sku-detail.edit', {
            parent: 'user-sku-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-sku/user-sku-dialog.html',
                    controller: 'UserSkuDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserSku', function(UserSku) {
                            return UserSku.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-sku.new', {
            parent: 'user-sku',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-sku/user-sku-dialog.html',
                    controller: 'UserSkuDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('user-sku', null, { reload: 'user-sku' });
                }, function() {
                    $state.go('user-sku');
                });
            }]
        })
        .state('user-sku.edit', {
            parent: 'user-sku',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-sku/user-sku-dialog.html',
                    controller: 'UserSkuDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserSku', function(UserSku) {
                            return UserSku.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-sku', null, { reload: 'user-sku' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-sku.delete', {
            parent: 'user-sku',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-sku/user-sku-delete-dialog.html',
                    controller: 'UserSkuDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserSku', function(UserSku) {
                            return UserSku.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-sku', null, { reload: 'user-sku' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
