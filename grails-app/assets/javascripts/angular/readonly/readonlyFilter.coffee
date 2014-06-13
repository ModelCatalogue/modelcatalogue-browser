angular.module('mc.core.readonlyFilter', [])

.service 'ReadonlyFilter', ->


    #Tabs that should not be displayed for a specific element type
    excludeList: {
      "org.modelcatalogue.core.ConceptualDomain":["History","Superseded By","Supersedes","Relationships"],
      "org.modelcatalogue.core.Model":["History","Superseded By","Supersedes","Relationships","History"],
      "org.modelcatalogue.core.DataType":["History","Superseded By","Supersedes","Value Domains","Relationships","Properties"]
      "org.modelcatalogue.core.EnumeratedType":["History","Superseded By","Supersedes","Value Domains","Relationships","Properties"]
      "org.modelcatalogue.core.DataElement":["History","Superseded By","Supersedes"]
      "org.modelcatalogue.core.ValueDomain":["History","Superseded By","Supersedes","Relationships"]
    }

    #Changes column names based on type of the element
    refineTab: (element,tabDefinition) ->
      switch element.elementType
        when "org.modelcatalogue.core.ConceptualDomain" then this.filterConceptualDomainHeader(tabDefinition)
        when "org.modelcatalogue.core.Model"            then this.filterModeleHeader(tabDefinition)
        when "org.modelcatalogue.core.DataElement"      then this.filterDataElementHeader(tabDefinition);
        when "org.modelcatalogue.core.DataType"         then this.filterDataTypeHeader(tabDefinition)
        when "org.modelcatalogue.core.EnumeratedType"   then this.filterEnumeratedTypeHeader(tabDefinition)
        when "org.modelcatalogue.core.ValueDomain"      then this.filterValueDomainHeader(tabDefinition)
        else return


    #Exclude the specfied tabs in 'excludeList' for specific element type
    isTabExcluded: (element,tabDefinition) ->
      if this.excludeList[element.elementType] && this.excludeList[element.elementType].indexOf(tabDefinition.heading) != -1
        return true
      return false



    filterValueDomainHeader: (tabDefinition) ->
      if tabDefinition.heading == "Included In"
        tabDefinition.heading = "Conceptual Domains"
        tabDefinition.columns = [
          {header: 'Name',     value: "relation.name", classes: 'col-md-3', show: "relation.show()"}
          {header: 'Identification',         value:  "'ConceptualDomain: '+relation.id+''",  classes: 'col-md-4', show: "relation.show()"}]
      if tabDefinition.heading == "Instantiates"
        tabDefinition.heading = "Data Elements"
        tabDefinition.columns =  [
          {header: 'Name',     value: "relation.name",    classes: 'col-md-2', show: "relation.show()"}
          {header: 'Identification',        value:  "relation.modelCatalogueId",     classes: 'col-md-3', show: "relation.show()"}]
      if tabDefinition.heading == "Ext"
        tabDefinition.heading = "Metadata"


    #Remane tab and list columns for ConceptualDomain
    filterConceptualDomainHeader: (tabDefinition) ->
      if tabDefinition.heading == "Is Context For"
        tabDefinition.heading = "Models"
        tabDefinition.columns = [
          {header: 'Name',     value: "relation.name", classes: 'col-md-3', show: "relation.show()"}
          {header: 'Identification',        value: 'relation.modelCatalogueId',  classes: 'col-md-4', show: "relation.show()"}]
      if tabDefinition.heading == "Includes"
        tabDefinition.heading = "DataType"
        tabDefinition.columns =  [
          {header: 'Name',     value: "relation.dataType.name",    classes: 'col-md-2', show: "relation.dataType.show()"}
          {header: 'Identification',        value:  "'DataType: '+relation.dataType.id+''",     classes: 'col-md-3', show: "relation.dataType.show()"}]
      if tabDefinition.heading == "Ext"
        tabDefinition.heading = "Metadata"



    #Remane tab and list columns for Model
    filterModeleHeader: (tabDefinition) ->
      if tabDefinition.heading == "Ext"
        tabDefinition.heading = "Metadata"
      if tabDefinition.heading == "Has Context Of"
        tabDefinition.heading = "Context"
        tabDefinition.columns = [
          {header: 'Name',     value: "relation.name",                                 classes: 'col-md-2', show: "relation.show()"}
          {header: 'Identification',  value: "relation.elementTypeName + ': ' + relation.id", classes: 'col-md-3', show: "relation.show()"}]
      if tabDefinition.heading == "Ext"
        tabDefinition.heading = "Metadata"
      if tabDefinition.heading == "Contains"
        tabDefinition.heading = "Data Elements"
        tabDefinition.columns = [
          {header: 'Name',     value: "relation.name",                                 classes: 'col-md-2', show: "relation.show()"}
          {header: 'Identification',  value: "relation.elementTypeName + ': ' + relation.id", classes: 'col-md-3', show: "relation.show()"}]
      if tabDefinition.heading == "Child Of" || tabDefinition.heading == "Parent Of"
        tabDefinition.columns = [
          {header: 'Name',     value: "relation.name",                                 classes: 'col-md-2', show: "relation.show()"}
          {header: 'Identification',  value: "relation.elementTypeName + ': ' + relation.id", classes: 'col-md-3', show: "relation.show()"}]



    #Remane tab and list columns for EnumeratedTyp
    filterEnumeratedTypeHeader: (tabDefinition) ->
      if tabDefinition.heading == "Ext"
        tabDefinition.heading = "Metadata"


    #Remane tab and list columns for DataType
    filterDataTypeHeader: (tabDefinition) ->
      if tabDefinition.heading == "Ext"
        tabDefinition.heading = "Metadata"


    #Remane tab and list columns for DataElement
    filterDataElementHeader: (tabDefinition) ->
      if tabDefinition.heading == "Contained In"
        tabDefinition.heading = "Models"
        tabDefinition.columns = [
          {header: 'Name',     value: "relation.name", classes: 'col-md-3', show: "relation.show()"}
          {header: 'Identification',        value: 'relation.modelCatalogueId',  classes: 'col-md-4', show: "relation.show()"}]
      if tabDefinition.heading == "Instantiated By"
        tabDefinition.heading = "DataType"
        tabDefinition.columns =  [
          {header: 'Name',     value: "relation.dataType.name",    classes: 'col-md-2', show: "relation.dataType.show()"}
          {header: 'Identification',        value:  "'DataType: '+relation.dataType.id+''",     classes: 'col-md-3', show: "relation.dataType.show()"}]
      if tabDefinition.heading == "Ext"
        tabDefinition.heading = "Metadata"