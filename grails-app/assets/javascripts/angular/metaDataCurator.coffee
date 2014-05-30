#= require jquery/dist/jquery
#= require bootstrap/dist/js/bootstrap
#= require angular/angular
#= require angular-ui-router/release/angular-ui-router
#= require modelcatalogue/util/index
#= require modelcatalogue/core/index
#= require modelcatalogue/core/ui/index
#= require angular/readonly/index
#= require modelcatalogue/core/ui/bs/index

@grailsAppName = 'model_catalogue'

metadataCurator = angular.module('metadataCurator', [
  'demo.config'
  'mc.core.ui.bs'
  'mc.core.ui.readonly'
  'ui.bootstrap'
])


metadataCurator.config ['$urlRouterProvider', ($urlRouterProvider)->
# For any unmatched url, send to /dataelement
  $urlRouterProvider.otherwise("/catalogue/model/all")
]


metadataCurator.controller('metadataCurator.searchCtrl', ['catalogueElementResource', 'modelCatalogueSearch','modelCatalogueDataArchitect', '$scope', '$log', '$q', '$state','$modal', 'names', (catalogueElementResource, modelCatalogueSearch, modelCatalogueDataArchitect, $scope, $log, $q, $state,$modal, names)->
  $scope.search = () ->
    if (typeof $scope.searchSelect == 'string')
      $state.go('mc.search', {searchString: $scope.searchSelect })
    else
      $state.go('mc.resource.show', {resource: names.getPropertyNameFromType($scope.searchSelect.elementType) , id: $scope.searchSelect.id})

])