modules = {

	// Standard libraries
	jquery_lib {
		resource url: "js/vendor/jquery/jquery-ui.1.10.2.js"
	}
	
	jquery_layout_lib{
		resource url: "js/vendor/jquery/jquery.layout-1.3.0.min.js"
	}
	
	jquery_dform_lib{
		resource url: "js/vendor/jquery/jquery.dform-1.1.0.min.js"
	}
	
	knockout_lib{
		resource url: "js/vendor/knockout/knockout-3.0.0.js"
		resource url: "js/vendor/knockout/knockout-es5.js"
		resource url: "js/vendor/knockout/knockout.punches.js"
	}
	
	require_lib{
		resource url: "js/vendor/require/require.js"
	}
	
	d3_lib{
		resource url: "js/vendor/d3/d3.js"
	}
	
	modernizr_lib{
		resource url: "js/vendor/modernizr/modernizr-2.6.2-respond-1.1.0.min.js"
	}

	dataTables {
		dependsOn "application"
        resource url: "js/datatable/jquery.dataTables.fnSetFilteringDelay.js"
	}
	
	dualListBox {
		resource url: "js/lib/jquery.bootstrap-duallistbox.js"
	}
	
	dataElement{
		dependsOn "dataTables"
		resource url: "js/model/dataElement/dataElement.js"
	}
	
	conceptualDomain{
		dependsOn "dataTables"
		dependsOn "dualListBox"
		resource url: "js/model/conceptualDomain/conceptualDomain.js"
	}
	
	collection{
		dependsOn "dataTables"
		dependsOn "dualListBox"
		resource url: "js/model/collection/collection.js"
	}
	
	valueDomain{
		dependsOn "dataTables"
		resource url: "js/model/valueDomain/valueDomain.js"
	}
	
	dataType{
		dependsOn "dataTables"
		resource url: "js/model/dataType/dataType.js"
	}
	
	dataElementConcept{
		dependsOn "dataTables"
		resource url: "js/model/dataElementConcept/dataElementConcept.js"
	}
	
	formDesignList{
		dependsOn "dataTables"
		resource url: "js/forms/formDesign.js"
	}
	
	formsBuilder{
		dependsOn "application"
		dependsOn "jquery_layout_lib"
		resource url: "js/vendor/knockout-2.2.1.js"
		resource url: "js/vendor/knockout-sortable.js"
		resource url: "js/forms/formsDesigner/dataTypeTemplates.js"
		resource url: "js/forms/formsDesigner/palette.js"
		resource url: "js/forms/formsDesigner/model/Form.js"
		resource url: "js/forms/formsDesigner/AppViewModel.js"
		resource url: "js/forms/formsDesigner/service/FormDesignService.js"
		resource url: "js/forms/formsDesigner/model/Component.js"
		resource url: "js/forms/formsDesigner/model/Property.js"
		resource url: "js/forms/formsDesigner/model/Question.js"
		resource url: "js/forms/formsDesigner/model/Component.js"
		resource url: "js/forms/formsDesigner/model/Section.js"
		resource url: "js/forms/formsDesigner/model/SelectOption.js"
		resource url: "js/forms/formsDesigner/model/DataTypeInstance.js"
		resource url: "js/forms/formsDesigner/show.js"
		resource url: "js/forms/formsDesigner/fDesignerLayout.js"
		
	}
	
	formsRenderer{
		
		dependsOn "application"
		dependsOn "jquery_layout_lib"
		resource url: "js/lib/bootstrap-datepicker.js"
		resource url: "js/lib/bootstrap-timepicker.js"
		resource url: "js/respond.min.js"
		resource url: "js/forms/formsRenderer/constraint.js"
		resource url: "js/forms/formsRenderer/frenderer-theme-bootstrap.js"
		resource url: "js/forms/formsRenderer/frenderer.js"
		resource url: "js/forms/formsRenderer/frendererLayout.js"
		
	}
	
	pathways{
		resource url: "js/pathways/pathwaysLayout.js"
	}

	dForms{
		dependsOn "application"
		dependsOn "jquery_dform_lib"
		resource url: "js/forms/formDesign.js"
		}

    dashboard{
        dependsOn "application"
        resource url: "js/dashboard.js"
    }

    /**
     *  Application libraries
     */
    application_libraries {
        dependsOn "modernizr_lib"
    }

    application {
        dependsOn "application_libraries"
        resource url: "js/main.js"
    }
}

