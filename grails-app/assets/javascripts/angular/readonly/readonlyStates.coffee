angular.module('mc.core.ui.readonly.readonlyStates', ['ui.router'])
.controller('mc.core.ui.states.ShowCtrl', ['$scope', '$stateParams', '$state', '$log', 'element','enhance', ($scope, $stateParams, $state, $log, element, enhance) ->
    listEnhancer    = enhance.getEnhancer('list')
    $scope.element  = element

#    We are not going to show ValueDomain & MeasurementUnit
    if $scope.element.elementType == 'org.modelcatalogue.core.ValueDomain' || $scope.element.elementType == "org.modelcatalogue.core.MeasurementUnit"
      $state.go("defaultHome")

  ])
.controller('mc.core.ui.states.ListCtrl', ['$scope', '$stateParams', '$state', '$log', 'list', 'names', 'enhance', ($scope, $stateParams, $state, $log, list, names, enhance) ->
    listEnhancer    = enhance.getEnhancer('list')
    $scope.list                     = list
    $scope.title                    = names.getNaturalName($stateParams.resource)
    $scope.resource                 = $stateParams.resource
    $scope.containedElements        = listEnhancer.createEmptyList('org.modelcatalogue.core.DataElement')
    $scope.selectedElement          = if list.size > 0 then list.list[0] else {name: 'No Selection'}
    $scope.natural                  = (name) -> if name then names.getNaturalName(name) else "General"
    $scope.containedElementsColumns = [
      {header: 'Name',          value: "relation.name",        classes: 'col-md-6', show: "relation.show()"}
      {header: 'Description',   value: "relation.description", classes: 'col-md-6'}
    ]

    #    We are not going to show ValueDomain & MeasurementUnit
    if $scope.resource == 'valueDomain' || $scope.resource == "measurementUnit"
      $state.go("defaultHome")


    if $scope.resource == 'model'
      for item in list
        item._containedElements_ = listEnhancer.createEmptyList('org.modelcatalogue.core.DataElement')

      $scope.$on 'treeviewElementSelected', (event, element) ->
        unless element._containedElements_?.empty
          element.contains().then (contained)->
            element._containedElements_ = contained
            $scope.containedElements    = contained
        $scope.selectedElement          = element
        $scope.containedElements        = element._containedElements_ ? listEnhancer.createEmptyList('org.modelcatalogue.core.DataElement')

])
.config(['$stateProvider', ($stateProvider) ->

  DEFAULT_ITEMS_PER_PAGE = 10

    # As we are not going to show ValueDomain & MeasurementUnit
    # This defaultHome will help us to redirect to default page when we face a valueDomain or a measurementUnit
  $stateProvider.state 'defaultHome', {
    onEnter: ($location)->
      $location.path('/catalogue/model/all')
  }

  $stateProvider.state 'mc', {
    abstract: true
    url: '/catalogue'
    templateUrl: 'modelcatalogue/core/ui/state/parent.html'
  }
  $stateProvider.state 'mc.resource', {
    abstract: true
    url: '/:resource'
    templateUrl: 'modelcatalogue/core/ui/state/parent.html'
  }
  $stateProvider.state 'mc.resource.list', {
    url: '/all?page'

    templateUrl: 'modelcatalogue/core/ui/state/list.html'

    resolve:
        list: ['$stateParams','catalogueElementResource', ($stateParams,catalogueElementResource) ->
          page = parseInt($stateParams.page ? 1, 10)
          page = 1 if isNaN(page)
          # it's safe to call top level for each controller, only model controller will respond on it
          params = offset: (page - 1) * DEFAULT_ITEMS_PER_PAGE, toplevel: true
          catalogueElementResource($stateParams.resource).list(params)
        ]

    controller: 'mc.core.ui.states.ListCtrl'
  }
  $stateProvider.state 'mc.resource.show', {
    url: '/{id:\\d+}'

    templateUrl: 'modelcatalogue/core/ui/state/show.html'

    resolve:
        element: ['$stateParams','catalogueElementResource', ($stateParams, catalogueElementResource) ->
          catalogueElementResource($stateParams.resource).get($stateParams.id)
        ]

    controller: 'mc.core.ui.states.ShowCtrl'
  }

  $stateProvider.state 'mc.resource.show.property', {url: '/:property?page'}

  $stateProvider.state('mc.search', {
      url: "/search/{searchString}",
      templateUrl: 'modelcatalogue/core/ui/state/list.html'
      resolve: {
        list: ['$stateParams','modelCatalogueSearch', ($stateParams, modelCatalogueSearch) ->
          page = parseInt($stateParams.page ? 1, 10)
          $stateParams.resource = "dataElement"
          return modelCatalogueSearch($stateParams.searchString)
        ]
      },
      controller: 'mc.core.ui.states.ListCtrl'
  })


  $stateProvider.state('mc.dataArchitect', {
      abstract: true,
      url: "/dataArchitect"
      templateUrl: 'modelcatalogue/core/ui/state/parent.html'
  })

  $stateProvider.state 'mc.dataArchitect.uninstantiatedDataElements', {
    url: "/uninstantiatedDataElements",
    templateUrl: 'modelcatalogue/core/ui/state/list.html'
    resolve:
      list: ['$stateParams', 'modelCatalogueDataArchitect', ($stateParams, modelCatalogueDataArchitect) ->
        page = parseInt($stateParams.page ? 1, 10)
        $stateParams.resource = "dataElement"
        # it's safe to call top level for each controller, only model controller will respond on it
        modelCatalogueDataArchitect.uninstantiatedDataElements()
      ]

    controller: 'mc.core.ui.states.ListCtrl'
  }

  $stateProvider.state 'mc.dataArchitect.metadataKey', {
    url: "/metadataKeyCheck",
    templateUrl: 'modelcatalogue/core/ui/state/parent.html'
    controller: ['$state','$modal',($state, $modal)->
      dialog = $modal.open {
        windowClass: 'messages-modal-prompt'
        template: '''
         <div class="modal-header">
            <h4>please enter metadata key</h4>
        </div>
        <div class="modal-body">
            <form role="form" ng-submit="$close(value)">
            <div class="form-group">
                <label for="value">metadata key</label>
                <input type="text" id="value" ng-model="value" class="form-control">
            </form>
        </div>
        <div class="modal-footer">
            <button class="btn btn-primary" ng-click="$close(value)">OK</button>
            <button class="btn btn-warning" ng-click="$dismiss()">Cancel</button>
        </div>
        '''
      }
      dialog.result.then (result) ->
        $state.go('mc.dataArchitect.metadataKeyCheck', {'metadata':result})

    ]
  }

  $stateProvider.state 'mc.dataArchitect.metadataKeyCheck', {
    url: "/metadataKey/{metadata}",
    templateUrl: 'modelcatalogue/core/ui/state/list.html'
    resolve:
      list: ['$stateParams', 'modelCatalogueDataArchitect', ($stateParams, modelCatalogueDataArchitect) ->
        page = parseInt($stateParams.page ? 1, 10)
        $stateParams.resource = "dataElement"
        # it's safe to call top level for each controller, only model controller will respond on it
        return modelCatalogueDataArchitect.metadataKeyCheck($stateParams.metadata)
      ]

    controller: 'mc.core.ui.states.ListCtrl'
  }

])
.run(['$rootScope', '$state', '$stateParams', ($rootScope, $state, $stateParams) ->
    # It's very handy to add references to $state and $stateParams to the $rootScope
    # so that you can access them from any scope within your applications.For example,
    # <li ui-sref-active="active }"> will set the <li> // to active whenever
    # 'contacts.list' or one of its decendents is active.
    $rootScope.$state = $state
    $rootScope.$stateParams = $stateParams
])
.run(['$templateCache', ($templateCache) ->

  $templateCache.put 'modelcatalogue/core/ui/state/parent.html', '''
    <ui-view></ui-view>
  '''


  $templateCache.put 'modelcatalogue/core/ui/state/list.html', '''
    <div ng-if="list.total &amp;&amp; resource != 'model'">
      <h2>{{title}} List</h2>
      <span class="pull-right">
        <div class="btn-group btn-group-sm export">
          <button type="button" class="btn btn-primary dropdown-toggle" ng-disabled="list.availableReports &amp;&amp; list.availableReports.length == 0" id="exportBtn">
            <span class="glyphicon glyphicon-download-alt"></span> Export <span class="caret"></span>
          </button>
          <ul class="dropdown-menu" role="menu" id="exportBtnItems">
            <li><a ng-href="{{report.url}}" target="_blank" ng-repeat="report in list.availableReports">{{natural(report.name)}}</a></li>
          </ul>
        </div>
      </span>
      <decorated-list list="list"></decorated-list>
    </div>
    <div>
    <div ng-if="resource == 'model'">
      <div class="row">
        <div class="col-md-4"><h2>Model Hierarchy</h2></div>
        <div class="col-md-8"><h3 ng-show="selectedElement">{{selectedElement.name}} Data Elements</h3></div>
      </div>
      <div class="row">
        <div class="col-md-4">
          <catalogue-element-treeview list="list" descend="'parentOf'"></catalogue-element-treeview>
        </div>
        <div class="col-md-8">
          <blockquote class="ce-description" ng-show="selectedElement.description">{{selectedElement.description}}</blockquote>
          <decorated-list list="containedElements" columns="containedElementsColumns" stateless="true"></decorated-list>
        </div>
        <hr/>
      </div>
    </div>
  '''

  $templateCache.put 'modelcatalogue/core/ui/state/show.html', '''
    <div>
      <div ng-if="element.elementType == 'org.modelcatalogue.core.DataElement'">
        <dataelement-view-read-only element="element" ></dataelement-view-read-only>
      </div>


    <div ng-if="element.elementType == 'org.modelcatalogue.core.DataType'">
      <datatype-view-read-only element="element" ></datatype-view-read-only>
    </div>


    <div ng-if="element.elementType == 'org.modelcatalogue.core.EnumeratedType'">
        <enumerateddatatype-view-read-only element="element" ></enumerateddatatype-view-read-only>
    </div>


    <div ng-if="element.elementType == 'org.modelcatalogue.core.Model'">
      <model-view-read-only element="element" ></model-view-read-only>
    </div>


    <div ng-if="element.elementType == 'org.modelcatalogue.core.ConceptualDomain'">
      <conceptualdomain-view-read-only element="element" ></conceptualdomain-view-read-only>
    </div>

    <!--
        <catalogue-element-view-read-only element="element" ></catalogue-element-view-read-only>
    -->


    </div>
  '''


  $templateCache.put 'modelcatalogue/core/ui/catalogueElementViewReadOnly.html', '''
    <div>
      <h3 class="ce-name">{{element.name}} <small ng-show="element.elementTypeName">({{element.elementTypeName}}: {{element.id}})</small></h3>
      <blockquote class="ce-description" ng-show="element.description">{{element.description}}</blockquote>
      <tabset ng-show="showTabs">
        <tab heading="{{tab.heading}}" disabled="tab.disabled" ng-repeat="tab in tabs" active="tab.active" select="select(tab)">
            <div ng-switch="tab.type">
              <properties-pane item="tab.value" properties="tab.properties" ng-switch-when="properties-pane" id="{{id + '-' + tab.name}}"></properties-pane>
              <decorated-list list="tab.value" columns="tab.columns" actions="tab.actions" ng-switch-when="decorated-list" id="{{id + '-' + tab.name}}" reports="tab.reports"></decorated-list>
            </div>
        </tab>
      </tabset>
    </div>
    '''



  $templateCache.put 'modelcatalogue/core/ui/modelViewReadOnly.html', '''
    <div>
      <span class="pull-right">
        <div class="btn-group btn-group-sm">
          <button type="button" class="btn btn-primary dropdown-toggle" ng-disabled="reports &amp;&amp; reports.length == 0">
            <span class="glyphicon glyphicon-download-alt"></span> Export <span class="caret"></span>
          </button>
          <ul class="dropdown-menu" role="menu">
            <li><a ng-href="{{report.url}}" target="_blank" ng-repeat="report in reports">{{report.name || 'Export'}}</a></li>
          </ul>
        </div>
      </span>

      <h3 class="ce-name">{{element.name}} <small ng-show="element.elementTypeName">({{element.elementTypeName}}: {{element.id}})</small></h3>
      <blockquote class="ce-description" ng-show="element.description">{{element.description}}</blockquote>
      <tabset ng-show="showTabs">
        <tab heading="{{tab.heading}}" disabled="tab.disabled" ng-repeat="tab in tabs" active="tab.active" select="select(tab)">
            <div ng-switch="tab.type">
              <properties-pane item="tab.value" properties="tab.properties" ng-switch-when="properties-pane" id="{{id + '-' + tab.name}}"></properties-pane>
              <decorated-list list="tab.value" columns="tab.columns" actions="tab.actions" ng-switch-when="decorated-list" id="{{id + '-' + tab.name}}" reports="tab.reports"></decorated-list>
            </div>
        </tab>
      </tabset>
    </div>
    '''


])