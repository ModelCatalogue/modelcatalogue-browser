#= require_self
#= require readonlyStates
#= require catalogueElementViewReadOnly
#= require dataElementViewReadOnly
#= require conceptualDomainViewReadOnly
#= require dataTypeViewReadOnly
#= require enumeratedDataTypeViewReadOnly
#= require modelViewReadOnly
#= require readonlyFilter




angular.module('mc.core.ui.readonly', [
  # depends on
  'mc.core.ui'
  # list of modules
  'mc.core.ui.readonly.readonlyStates',
  'mc.core.ui.catalogueElementViewReadOnly',
  'mc.core.readonlyFilter'
])