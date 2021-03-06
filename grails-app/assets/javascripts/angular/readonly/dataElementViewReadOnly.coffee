angular.module('mc.core.ui.dataElementViewReadOnly', ['mc.core.catalogueElementEnhancer', 'mc.core.listReferenceEnhancer', 'mc.core.listEnhancer', 'mc.util.names', 'mc.util.messages', 'mc.core.ui.columns', 'ui.router']).directive 'dataelementViewReadOnly',  [-> {
restrict: 'E'
replace: true
scope:
  element: '='
  property: '=?'
  id: '@'

templateUrl: 'modelcatalogue/core/ui/catalogueElementViewReadOnly.html'


controller: ['$scope', '$log', '$filter', '$q', '$state', 'enhance', 'names', 'columns', 'messages', '$rootScope' , ($scope, $log, $filter, $q, $state, enhance, names, columns, messages, $rootScope) ->
  propExcludes     = ['version', 'name', 'description', 'incomingRelationships', 'outgoingRelationships']
  listEnhancer    = enhance.getEnhancer('list')
  getPropertyVal  = (propertyName) ->
    (element) -> element[propertyName]

  getObjectSize   = (object) ->
    size = 0
    angular.forEach object, () ->
      size++
    size

  $scope.property ?= $rootScope?.$stateParams?.property

  onPropertyUpdate = (newProperty, oldProperty) ->
    page    = 1
    options = {}
    isTable = false
    if $scope.showTabs
      if newProperty
        for tab in $scope.tabs
          tab.active = tab.name == newProperty
          if tab.active
            isTable = tab.type == 'decorated-list'
            if isTable and tab.value.total
              page = tab.value.currentPage

      else
        for tab in $scope.tabs
          if tab.active
            $scope.property = tab.name
            isTable = tab.type == 'decorated-list'
            if isTable and tab.value.total
              page = tab.value.currentPage
            break

    page = undefined if page == 1 or isNaN(page)
    options.location = "replace" if newProperty and not oldProperty
    $state.go 'mc.resource.show.property', {resource: names.getPropertyNameFromType($scope.element.elementType), id: $scope.element.id, property: newProperty, page: page}, options if $scope.element

  onElementUpdate = (element) ->
    activeTabSet     = false

    onPropertyUpdate($scope.property, $rootScope?.$stateParams?.property)

    tabs = []

    for name, fn of element when enhance.isEnhancedBy(fn, 'listReference')
      if name in propExcludes
        continue
      tabDefinition =
        heading:  names.getNaturalName(name)
        value:    listEnhancer.createEmptyList(fn.itemType)
        disabled: fn.total == 0
        loader:   fn
        type:     'decorated-list'
        columns:   columns(fn.itemType)
        actions:  []
        name:     name


      if tabDefinition.heading == "Contained In"
        tabDefinition.heading = "Models"
        tabDefinition.columns = [
            {header: 'Name',     value: "relation.name", classes: 'col-md-3', show: "relation.show()"}
            {header: 'Identification',        value: 'relation.modelCatalogueId',  classes: 'col-md-4', show: "relation.show()"}
          ]


      if tabDefinition.heading == "Instantiated By"
        tabDefinition.heading = "DataType"
        tabDefinition.columns =  [
            {header: 'Name',     value: "relation.dataType.name",    classes: 'col-md-2', show: "relation.dataType.show()"}
            {header: 'Identification',        value:  "'DataType: '+relation.dataType.id+''",     classes: 'col-md-3', show: "relation.dataType.show()"}
          ]

      #do not show the following tabs
      removeHeaders = ["History","Superseded By","Supersedes"]
      if removeHeaders.indexOf(tabDefinition.heading) == -1
        tabs.push tabDefinition

    for name, obj of element
      if name in propExcludes
        continue
      unless angular.isObject(obj) and !angular.isArray(obj) and !enhance.isEnhanced(obj)
        continue
      tabDefinition =
        name:       name
        heading:    names.getNaturalName(name)
        value:      obj
        disabled:   obj == undefined or obj == null or getObjectSize(obj) == 0
        properties: []
        type:       'properties-pane'



      for key, value of obj when not angular.isObject(value)
        tabDefinition.properties.push {
          label: key
          value: getPropertyVal(key)
        }

      if tabDefinition.name == $scope.property
        tabDefinition.active = true
        activeTabSet = true


      if tabDefinition.heading == "Ext"
        tabDefinition.heading = "Metadata"


      tabs.push tabDefinition

    tabs = $filter('orderBy')(tabs, 'heading')

    if enhance.isEnhancedBy(element, 'catalogueElement')
      newProperties = []
      for prop in element.getUpdatableProperties()
        obj = element[prop]
        if prop in propExcludes
          continue
        if enhance.isEnhancedBy(obj, 'listReference')
          continue
        if (angular.isObject(obj) and !angular.isArray(obj) and !enhance.isEnhanced(obj))
          continue
        newProperties.push(label: names.getNaturalName(prop), value: getPropertyVal(prop))

      tabDefinition =
        heading:    'Properties'
        name:       'properties'
        value:      element
        disabled:   getObjectSize(newProperties) == 0
        properties: newProperties
        type:       'properties-pane'

      if tabDefinition.name == $scope.property
        tabDefinition.active = true
        activeTabSet = true

      tabs.unshift tabDefinition


    showTabs = false
    if not activeTabSet
      for tab in tabs
        if not tab.disabled
          tab.active = true
          $scope.property = tab.name
          showTabs = true
          break
    else
      showTabs = true

    $scope.tabs = tabs
    $scope.showTabs = showTabs


  $scope.tabs   = []
  $scope.select = (tab) ->
    $scope.property = tab.name
    return if not tab.loader?
    if !tab.disabled and tab.value.empty
      tab.loader().then (result) ->
#        COLUMN ISSSUEEEE, it should not be intialised again
#        tab.columns = columns(result.itemType)

#        As we just want relations between dataElement to be displayed
#        so we added this code which is a hack
#        in later version we are going to add a filter to decoratedList in MC-Plugin and
#        then we can filter any list
        if tab.heading == "Relationships"
          index = 0
          for item in result.list.slice(0)
              if item.relation.elementTypeName != "Data Element"
                result.list.splice(index,1)
                index--
                result.total = result.total - 1
                result.size  = result.size - 1
              index++
        tab.value = result


  # watches
  $scope.$watch 'element', onElementUpdate
  $scope.$watch 'property', onPropertyUpdate


  refreshElement = () ->
    if $scope.element
      $scope.element.refresh().then (refreshed)->
        $scope.element = refreshed

  $scope.$on 'catalogueElementCreated', refreshElement
  $scope.$on 'catalogueElementDeleted', refreshElement

  $scope.$on '$stateChangeSuccess', (event, state, params) ->
    return if state.name != 'mc.resource.show.property'
    $scope.property = params.property

  # init
  onElementUpdate($scope.element)
]
}
]