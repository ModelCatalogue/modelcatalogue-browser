package uk.co.mdc.pages.metadataCuration.ShowPage

import uk.co.mdc.pages.BasePageWithNav

/**
 * Created by soheil on 15/05/2014.
 */
class DataTypeShowPage extends BasePageWithNav {

	static url = "metadataCurator/#/catalogue/dataType/"

	static at = {
		url == "metadataCurator/#/catalogue/dataType/" &&
				title == "Metadata Curation"
	}

	static content = {
		mainLabel(wait:true) {  $("h3.ce-name") }
		description(wait:true) { $("blockquote.ce-description")}
	}
}