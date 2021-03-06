package uk.co.mdc.pages.metadataCuration.ListPage

import uk.co.mdc.pages.BasePageWithNav
import uk.co.mdc.pages.metadataCuration.ShowPage.ModelShowPage

/**
 * Created by soheil on 15/05/2014.
 */
class DataElementListPage extends ListPage {

	static url = "metadataCurator/#/catalogue/dataElement/all"

	static at = {
		url == "metadataCurator/#/catalogue/dataElement/all" &&
		title == "Metadata Curation"
	}

	@Override
	def getRow(rowIndex){

		def row = ["object":null,"name":null,"desc":null];
		def table = waitFor {  $("table[list='list']")}

		if(!table)
			return  row

		def object = $("table[list='list'] tbody tr",rowIndex)


		if(object){
			row = [ "object":$("table[list='list'] tbody tr",rowIndex),
					"catalogueId" :$("table[list='list'] tbody tr",rowIndex).find("td",0).find("a"),
					"name":$("table[list='list'] tbody tr",rowIndex).find("td",1).find("a"),
					"desc":$("table[list='list'] tbody tr",rowIndex).find("td",2).find("span")]
		}
		return row
	}
}