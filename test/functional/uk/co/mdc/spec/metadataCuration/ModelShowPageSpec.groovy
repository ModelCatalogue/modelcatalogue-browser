package uk.co.mdc.spec.metadataCuration

import geb.spock.GebReportingSpec
import org.openqa.selenium.Dimension
import uk.co.mdc.pages.authentication.LoginPage
import uk.co.mdc.pages.metadataCuration.ListPage.DataElementListPage
import uk.co.mdc.pages.metadataCuration.ListPage.ModelListPage
import uk.co.mdc.pages.metadataCuration.ShowPage.ModelShowPage

/**
 * Created by soheil on 15/05/2014.
 */
class ModelShowPageSpec extends GebReportingSpec {


	def setup() {
		to LoginPage
		loginRegularUser()
		waitFor {
			at ModelListPage
		}
	}


	def "At modelShowPage, it shows model properties, conceptualDomains, metadata and dataElements"() {
		when: "Click on a model"
		waitFor {
			at ModelListPage
		}
		goToModelShowPage()

		then: "its properties, conceptualDomains and dataElements will be displayed"
		waitFor {
			at ModelShowPage
		}
		waitFor {
			propertiesTab.displayed
		}

		waitFor {
			childOfTab.displayed
		}
		waitFor {
			contextTab.displayed
		}
		waitFor {
			dataElementsTab.displayed
		}
		waitFor {
			metadataTab.displayed
		}
		waitFor {
			parentOfTab.displayed
		}
	}
}