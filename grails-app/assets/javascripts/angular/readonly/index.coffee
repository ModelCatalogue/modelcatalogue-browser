#= require_self
#= require readonlyStates
#= require catalogueElementViewReadOnly
#= require dataElementViewReadOnly
#= require conceptualDomainViewReadOnly
#= require dataTypeViewReadOnly
#= require enumeratedDataTypeViewReadOnly
#= require modelViewReadOnly




angular.module('mc.core.ui.readonly', [
  # depends on
  'mc.core.ui'
  # list of modules
  'mc.core.ui.readonly.readonlyStates',
  'mc.core.ui.catalogueElementViewReadOnly',
  'mc.core.ui.dataElementViewReadOnly',
  'mc.core.ui.conceptualDomainViewReadOnly',
  'mc.core.ui.dataTypeViewReadOnly',
  'mc.core.ui.enumeratedDataTypeViewReadOnly',
  'mc.core.ui.modelViewReadOnly',
])