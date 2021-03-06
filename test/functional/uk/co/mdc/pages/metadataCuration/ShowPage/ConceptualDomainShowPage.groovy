package uk.co.mdc.pages.metadataCuration.ShowPage

import uk.co.mdc.pages.BasePageWithNav

/**
 * Created by soheil on 15/05/2014.
 */
class ConceptualDomainShowPage extends BasePageWithNav{

	static url = "metadataCurator/#/catalogue/conceptualDomain/"


	static at = {
		url == "metadataCurator/#/catalogue/conceptualDomain/" &&
		title == "Metadata Curation"
	}

	static content = {
		mainLabel { waitFor { $("h3.ce-name") }}
		description {waitFor { $("blockquote.ce-description")}}

		propertiesTab {waitFor {$("div.tabbable ul li[heading='Properties']")}}
		modelsTab {waitFor { $("div.tabbable ul li[heading='Models']")}}
		dataTypesTab{waitFor { $("div.tabbable ul li[heading='DataType']")}}

		propertiesTable(required:false) {waitFor {$("table#-properties")}}
		modelsTable {waitFor {$("table#-isContextFor")}}
		dataTypesTable {waitFor {$("table#-includes")}}
	}
}