package uk.co.mdc.spec.metadataCuration

import geb.spock.GebReportingSpec
import spock.lang.Unroll
import uk.co.mdc.pages.authentication.*
import uk.co.mdc.pages.metadataCuration.ListPage.ConceptualDomainListPage
import uk.co.mdc.pages.metadataCuration.ListPage.ModelListPage

/**
 * Created by soheil on 03/06/2014.
 */
class AdminUserLoginSpec  extends GebReportingSpec{


		def setup(){
			to LoginPage
			loginAdminUser()
			waitFor{
				at ModelListPage
			}
		}

		def "Admin user has access to Administrator Menu"(){

			expect:"administration menu should be enabled for admin users"
			at ModelListPage
			nav
			nav.administrationLink.displayed
		}

		def "Admin user has access to Administrator Menu in any other pages"(){

			when:"Admin user goes to ConceptualDomainlist page"
			to ConceptualDomainListPage

			then:"administration menu should be enabled for admin users"
			waitFor {
				ConceptualDomainListPage
			}
			nav
			nav.administrationLink.displayed
		}



		@Unroll
		def"Admin user can access administration urls #AuthorizedUrl"(){
			when:"Admin user goes to an admin url"
			at ModelListPage
			go AuthorizedUrl

			then:"Admin will have access to those pages"
			waitFor {
				at AuthorizedPage
			}

			where:""
			AuthorizedUrl | AuthorizedPage
			"role/search" | RoleSearchPage
			"role/create" | RoleCreatePage
			"user/search" | UserSearchPage
			"user/create" | UserCreatePage
			"registrationCode/search" | RegistrationCodePage
			"role/pendingUsers"		  | RolePendingPage
		}
}


