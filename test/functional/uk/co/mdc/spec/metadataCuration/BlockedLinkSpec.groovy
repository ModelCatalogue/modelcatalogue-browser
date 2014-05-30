package uk.co.mdc.spec.metadataCuration

import geb.spock.GebReportingSpec
import org.openqa.selenium.Dimension
import uk.co.mdc.pages.authentication.LoginPage
import uk.co.mdc.pages.metadataCuration.ListPage.ConceptualDomainListPage
import uk.co.mdc.pages.metadataCuration.ListPage.ModelListPage

/**
 * Created by soheil on 20/05/2014.
 */
class BlockedLinkSpec extends GebReportingSpec {


	def setup() {
		to LoginPage
		loginAdminUser()
		waitFor {
			at ModelListPage
		}
	}
	def "Accessing Pathways will lead user to main model page"() {

		when: "at /pathways"
		go "pathways"

		then: "it redirects to main model list page"
		//as the actual link of the page may not match the list model page link,
		//like http://localhost:8080/model_catalogue/pathways#/catalogue/model/all
		//so we have to just test the page title
		page.title  == "Metadata Curation"


		when: "at /pathways"
		go "pathway"

		then: "it redirects to main model list page"
		//as the actual link of the page may not match the list model page link,
		//like http://localhost:8080/model_catalogue/pathways#/catalogue/model/all
		//so we have to just test the page title
		page.title  == "Metadata Curation"
	}
}
