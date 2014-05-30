package uk.co.mdc.spec.authentication;
import geb.spock.GebReportingSpec
import uk.co.mdc.pages.DashboardPage;
import uk.co.mdc.pages.authentication.LoginPage
import uk.co.mdc.pages.metadataCuration.ListPage.ModelListPage
import uk.co.mdc.pages.pathways.*

class UserLoginSpec extends GebReportingSpec {

	def "Login screen standard elements"() {
		when: 'I go to the login screen'
		to LoginPage

		then: ' The title of the page is "Login" and there are the standard components present'
		at LoginPage
		username.@type == "text"
		password.@type == "password"
		rememberMe.@type == "checkbox"
		forgottenPasswordLink.@text == "Forgot Password"
		registerLink.@text == "Signup"
	}
	
	//When I authenticate with an incorrect username/password combination
	//Then an error message is displayed stating the combination is incorrect and I can try again
	def "User authentication attempts (bad)"() {
		when: 'I enter the incorrect credentials'
		to LoginPage
		username = "baduser"
		password = "badpassword"
		submitButton.click(LoginPage)

		then: 'Then an error message is displayed stating the combination is incorrect and I can try again'
		waitFor{ 
			at LoginPage
		}
		invalidUsernameOrPasswordError
		
		when: 'I enter the incorrect credentials again'
		username = "baduser43"
		password = "badrpassword1"
		submitButton.click(LoginPage)

		then: 'Then an error message is displayed stating the combination is incorrect and I can try again'
		waitFor{ 
			at LoginPage
		}
		invalidUsernameOrPasswordError
	}
	
	//When I authenticate correctly after using an incorrect username/password combination
	//I am presented with the dashboard
	def "Successful second authentication"() {
		when: 'I enter the incorrect credentials'
		to LoginPage
		username = "baduser"
		password = "badpassword"
		submitButton.click(LoginPage)

		then: 'Then an error message is displayed stating the combination is incorrect and I can try again'
		at LoginPage
		invalidUsernameOrPasswordError
		
		when: 'I enter the correct credentials'
		username = "ruser1"
		password = "rpassword1@"
		submitButton.click(ModelListPage)

		then: 'I am taken to the ModelList page'
		waitFor{
			at ModelListPage
		}
	}

	//When I authenticate with a correct username/password combination
	//and I have navigated directly to the login screen
	//Then I am redirected to the system dashboard
	def "Successful login when going to the login page directly"() {		
		when: 'I enter the correct credentials'
		to LoginPage
		username = "ruser1"
		password = "rpassword1@"
		submitButton.click(ModelListPage)

		then: 'Then I am redirected to ModelListPage'
		waitFor{
			at ModelListPage
		}
	}
	
	//When I authenticate with a correct username/password combination
	//and I have been redirected to the login screen whilst trying to access another resource
	//and I am authorised to access that resource
	//Then I am redirected to the requested resource
//	def "Successful login when attempting to go somewhere else"() {
//		when: 'I go to a restricted page and I am not logged in'
//		go ModelListPage.url
//
//		then:'I am redirected to the login screen'
//		waitFor{
//			at LoginPage
//		}
//
//		when:'I authenticate successfully'
//		username = "ruser1"
//		password = "rpassword1@"
//		submitButton.click(ModelListPage)
//
//		then: 'I am redirected to my original destination'
//		waitFor{
//			at ModelListPage
//		}
//	}
	
}