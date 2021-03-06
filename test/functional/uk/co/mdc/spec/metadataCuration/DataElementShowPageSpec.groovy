package uk.co.mdc.spec.metadataCuration

import geb.spock.GebReportingSpec
import org.openqa.selenium.Dimension
import uk.co.mdc.pages.authentication.LoginPage
import uk.co.mdc.pages.metadataCuration.ListPage.DataElementListPage
import uk.co.mdc.pages.metadataCuration.ListPage.ModelListPage
import uk.co.mdc.pages.metadataCuration.ShowPage.DataElementShowPage

/**
 * Created by soheil on 15/05/2014.
 */
class DataElementShowPageSpec extends GebReportingSpec {


	def setup() {
		to LoginPage
		loginRegularUser()
		waitFor {
			at ModelListPage
		}
	}


	def "At dataElementShowPage, it shows properties, metadata, values and relations"() {

		when: "Click on a dataElement"
		at ModelListPage
		waitFor {
			$(ModelListPage.modelTree).displayed
		}
		waitFor {
			NHIC_Model_Item_Icon.displayed
		}
		interact {
			click(NHIC_Model_Item_Icon)
		}


		waitFor {
			ParentModel1_Item.displayed
		}
		interact {
			click(ParentModel1_Item_Icon)
		}


		waitFor{
			Model1_Item.displayed
		}
		interact {
			click(Model1_Item_Name)
		}

		waitFor {
			dataElementsTable.displayed
			getDataElementRow(0)["object"].displayed
		}
		def elementName =  getDataElementRow(0)["name"]
		waitFor {
			elementName.displayed
		}
		interact {
			click(elementName)
		}

		then: "its properties, metadata, values and relations will be displayed in datElementShowPage"
		waitFor {
			at DataElementShowPage
			mainLabel.displayed
		}

		mainLabel.text().contains("DE (Data Element:")
		description.text() == "Desc"
		waitFor {
			propertiesTab.displayed
			dataTypesTab.displayed
			metadataTab.displayed
			modelsTab.displayed
		}
//		relatedToTab
	}


	def "At dataElementShowPage, clicking on its tabs will show related table"() {

		when: "Click on a dataElement"
		at ModelListPage
		waitFor {
			$(ModelListPage.modelTree).displayed
		}
		NHIC_Model_Item_Icon.click()
		waitFor {
			ParentModel1_Item.displayed
		}
		ParentModel1_Item_Icon.click()
		waitFor{
			Model1_Item.displayed
		}
		Model1_Item_Name.click()
		waitFor {
			dataElementsTable.displayed
			getDataElementRow(0)["object"].displayed
		}
		def nameElement = getDataElementRow(0)["name"]
		waitFor {
			nameElement
		}
		interact {
			click(nameElement)
		}

		then: "its properties, metadata, values and relations will be displayed in datElementShowPage"
		at DataElementShowPage


		when:"Clicking on properties Tab"
		waitFor {
			propertiesTab.displayed
			propertiesTab.find("a").displayed
		}
		interact {
			click(propertiesTab.find("a"))
		}

		then:"properties Table will be displayed"
		waitFor {
			propertiesTable.displayed
		}


		when:"Clicking on dataTypes Tab"
		waitFor {
			dataTypesTab.displayed
			dataTypesTab.find("a").displayed
		}
		dataTypesTab.find("a").click()


		then:"dataTypes Table will be displayed"
		waitFor {
		 dataTypesTable.displayed
		}


		when:"Clicking on metadata Tab"
		waitFor {
			metadataTab.displayed
			metadataTab.find("a")
		}
		interact {
			click(metadataTab.find("a"))
		}

		then:"metadata Table will be displayed"
		waitFor {
			metadataTable.displayed
		}


//		when:"Clicking on relatedTo Tab"
//		relatedToTab.find("a").click()
//
//		then:"relatedTo Table will be displayed"
//		relatedToTable.displayed
	}

}