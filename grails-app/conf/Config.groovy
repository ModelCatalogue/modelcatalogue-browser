
// Additional configuration file locations. This is the default, but we need to load the contents of ~/.grails/model_catalogue-config.groovy
// for the production DB connection/username/passord.
 grails.config.locations = [ "classpath:${appName}-config.properties",
                             "classpath:${appName}-config.groovy",
                             "file:${userHome}/.grails/${appName}-config.properties",
                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = uk.co.mdc // change this to alter the default package name and Maven publishing destination
grails.mime.use.accept.header = true // required to play nicely with Angular's JSON requests
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.types = [
	all:           '*/*',
	atom:          'application/atom+xml',
	css:           'text/css',
	csv:           'text/csv',
	form:          'application/x-www-form-urlencoded',
	html:          [
		'text/html',
		'application/xhtml+xml'
	],
	js:            'text/javascript',
	json:          [
		'application/json',
		'text/json'
	],
    hal:           ['application/hal+json','application/hal+xml'],
	multipartForm: 'multipart/form-data',
	rss:           'application/rss+xml',
	text:          'text/plain',
	xml:           [
		'text/xml',
		'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

//NEED TO REMOVE IN PRODUCTION - DISABLING JAVASCRIPT BUNDLING
grails.resources.debug=true

// REQUIRED FIX FOR CVE-2014-0053, see http://cxsecurity.com/issue/WLB-2014020172?utm_source=twitterfeed&utm_medium=twitter&utm_content=bugtraq,+wlb,+cxsecurity
grails.resources.adhoc.includes = ['/images/**', '/css/**', '/js/**', '/plugins/**']
grails.resources.adhoc.excludes = ['/WEB-INF/**']

grails.converters.encoding = "UTF-8"

// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

environments {
	development {
        grails.logging.jul.usebridge = true

		//disable mail send functionality
		grails.mail.disabled=true
	}
	production {
		// TODO: grails.serverURL = "http://www.changeme.com"
		grails {
			logging.jul.usebridge = false
			// TODO: serverURL = "http://www.changeme.com"
			mail {
				host = System.env.MC_MAIL_HOST ?: 'smtp.gmail.com'
				port = System.env.MC_MAIL_PORT ?: 587
				username = System.env.MC_MAIL_USER ?: ''
				password = System.env.MC_MAIL_PASS ?: ''

				props = ["mail.smtp.auth":"true",
							"mail.smtp.socketFactory.port":"465",
							"mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
							"mail.smtp.socketFactory.fallback":"false"]
			}
		 }
	}
}

// log4j configuration
log4j = {
	// Example of changing the log pattern for the default console appender:
	//
	//appenders {
	//    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
	//}

    //debug "org.grails.plugin.resource"
	error  'org.codehaus.groovy.grails.web.servlet',        // controllers
			'org.codehaus.groovy.grails.web.pages',          // GSP
			'org.codehaus.groovy.grails.web.sitemesh',       // layouts
			'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
			'org.codehaus.groovy.grails.web.mapping',        // URL mapping
			'org.codehaus.groovy.grails.commons',            // core / classloading
			'org.codehaus.groovy.grails.plugins',            // plugins
			'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
			'org.springframework',
			'org.hibernate',
			'net.sf.ehcache.hibernate'
	debug 	'grails.app.services.grails.plugin.springsecurity.ui.SpringSecurityUiService'
	info 	'org.springframework.security'
	debug  	'uk.co.mdc.mail'		// Dummy mail output for dev
    info    'uk.co.mdc.pathways'
}


//javascript libraries

grails.views.javascript.library="jquery"



grails{
    assets{
        excludes = ["**/*.less"]
        includes = ["**/application.less","**/metaDataCurator.less"]
        less.compiler='less4j' // faster than the defaultminify
		minifyJs = false
    }
	plugins{
		springsecurity{

            // redirection page for success (including successful registration

            successHandler.defaultTargetUrl = '/metadataCurator/'
			

			// Added by the Spring Security Core plugin:
			userLookup.userDomainClassName = 'uk.co.mdc.SecUser'
			userLookup.authorityJoinClassName = 'uk.co.mdc.SecUserSecAuth'
			authority.className = 'uk.co.mdc.SecAuth'
            securityConfigType = 'InterceptUrlMap'
            logout.postOnly = false

			//disable to prevent double encryption of passwords
			ui.encodePassword = false

			// User registration: don't add user to any roles by default (this is done by an admin to approve the account)
			ui.register.defaultRoleNames = ['ROLE_READONLY_PENDING']

			// Grails security password requirements
			// Stupendously long password validation regex (courtesy of Ryan Brooks (ryan.brooks@ndm.ox.ac.uk). Checks for all permutations of digit, character, symbol.
			//ui.password.validationRegex='((?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&]))|((?=.*\\d)(?=.*[!@#$%^&](?=.*[a-zA-Z])))|((?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&]))|((?=.*[a-zA-Z])(?=.*[!@#$%^&])(?=.*\\d))|((?=.*[!@#$%^&])(?=.*\\d)(?=.*[a-zA-Z]))|((?=.*[!@#$%^&])(?=.*[a-zA-Z])(?=.*\\d))'
			ui.password.minLength=8
			ui.password.maxLength=64

			useSecurityEventListener = true

			onInteractiveAuthenticationSuccessEvent = { e, appCtx ->
				uk.co.mdc.SecUser.withTransaction {
					def user = uk.co.mdc.SecUser.get(appCtx.springSecurityService.currentUser.id)
					user.lastLoginDate = new Date()
					user.save()
				}
			}

			interceptUrlMap = [
					'/': 				['permitAll'],
					'/**/favicon.ico':	['permitAll'],
					'/fonts/**': 		['permitAll'],
					'/assets/**': 		['permitAll'],
					'/plugins/**/js/**':['permitAll'],
					'/js/vendor/**': 	['permitAll'],
					'/**/*.less': 		['permitAll'],
					'/**/js/**': 		['permitAll'],
					'/**/css/**': 		['permitAll'],
					'/**/images/**': 	['permitAll'],
					'/**/img/**': 		['permitAll'],
					'/login': 			['permitAll'],
					'/login.*': 		['permitAll'],
					'/login/*': 		['permitAll'],
					'/logout': 			['permitAll'],
					'/logout.*': 		['permitAll'],
					'/logout/*': 		['permitAll'],
					'/register': 		['permitAll'],
					'/register/index': 			['permitAll'],
					'/register/forgotPassword': ['permitAll'],
					'/register/resetPassword': 	['permitAll'],
					'/register/register': 		['permitAll'],
					'/errors': 			['permitAll'],
					'/errors/*': 		['permitAll'],


					//only permit users
					'/dashboard':	 	  [ "hasAnyRole('ROLE_READONLY_ADMIN', 'ROLE_READONLY_USER') && isFullyAuthenticated()"],
					'/dashboard/**':	  [ "hasAnyRole('ROLE_READONLY_ADMIN', 'ROLE_READONLY_USER') && isFullyAuthenticated()"],
					'/pathways':  		  [ "hasAnyRole('ROLE_READONLY_ADMIN', 'ROLE_READONLY_USER') && isFullyAuthenticated()"],
					'/pathways/**':  	  [ "hasAnyRole('ROLE_READONLY_ADMIN', 'ROLE_READONLY_USER') && isFullyAuthenticated()"],
					'/pathway': 		  [ "hasAnyRole('ROLE_READONLY_ADMIN', 'ROLE_READONLY_USER') && isFullyAuthenticated()"],
					'/pathway/**':   	  [ "hasAnyRole('ROLE_READONLY_ADMIN', 'ROLE_READONLY_USER') && isFullyAuthenticated()"],
					'/pathways.json':     [ "hasAnyRole('ROLE_READONLY_ADMIN', 'ROLE_READONLY_USER') && isFullyAuthenticated()"],
					'/metadataCurator':   [ "hasAnyRole('ROLE_READONLY_ADMIN', 'ROLE_READONLY_USER') && isFullyAuthenticated()"],
					'/metadataCurator/**':[ "hasAnyRole('ROLE_READONLY_ADMIN', 'ROLE_READONLY_USER') && isFullyAuthenticated()"],

					'/bootstrap-data/**': 		[ "hasRole('ROLE_READONLY_ADMIN') && isFullyAuthenticated()"],
					'/admin': 					[ "hasRole('ROLE_READONLY_ADMIN') && isFullyAuthenticated()"],
					'/admin/**': 				[ "hasRole('ROLE_READONLY_ADMIN') && isFullyAuthenticated()"],
					'/securityInfo/**': 		[ "hasRole('ROLE_READONLY_ADMIN') && isFullyAuthenticated()"],
					'/role': 					[ "hasRole('ROLE_READONLY_ADMIN') && isFullyAuthenticated()"],
					'/role/**': 				[ "hasRole('ROLE_READONLY_ADMIN') && isFullyAuthenticated()"],
					'/registrationCode': 		[ "hasRole('ROLE_READONLY_ADMIN') && isFullyAuthenticated()"],
					'/registrationCode/**': 	[ "hasRole('ROLE_READONLY_ADMIN') && isFullyAuthenticated()"],
					'/user': 					[ "hasRole('ROLE_READONLY_ADMIN') && isFullyAuthenticated()"],
					'/user/**': 				[ "hasRole('ROLE_READONLY_ADMIN') && isFullyAuthenticated()"],
					'/aclClass': 				[ "hasRole('ROLE_READONLY_ADMIN') && isFullyAuthenticated()"],
					'/aclClass/**': 			[ "hasRole('ROLE_READONLY_ADMIN') && isFullyAuthenticated()"],
					'/aclSid': 					[ "hasRole('ROLE_READONLY_ADMIN') && isFullyAuthenticated()"],
					'/aclSid/**': 				[ "hasRole('ROLE_READONLY_ADMIN') && isFullyAuthenticated()"],
					'/aclEntry': 				[ "hasRole('ROLE_READONLY_ADMIN') && isFullyAuthenticated()"],
					'/aclEntry/**': 			[ "hasRole('ROLE_READONLY_ADMIN') && isFullyAuthenticated()"],
					'/aclObjectIdentity': 		[ "hasRole('ROLE_READONLY_ADMIN') && isFullyAuthenticated()"],

					'/register/changePassword': [ "hasAnyRole('ROLE_READONLY_ADMIN', 'ROLE_READONLY_USER') && isFullyAuthenticated()"],

					'/api/modelCatalogue/core/*/*/outgoing/**':["hasAnyRole('ROLE_READONLY_ADMIN', 'ROLE_READONLY_USER') && request.getMethod().equals('GET')"],
					'/api/modelCatalogue/core/*/*/incoming/**':["hasAnyRole('ROLE_READONLY_ADMIN', 'ROLE_READONLY_USER') && request.getMethod().equals('GET')"],
					'/api/modelCatalogue/core/**':			   ["hasAnyRole('ROLE_READONLY_ADMIN', 'ROLE_READONLY_USER') && request.getMethod().equals('GET')"],
					'/api/modelCatalogue/core/search/**':	   ["hasAnyRole('ROLE_READONLY_ADMIN', 'ROLE_READONLY_USER') && request.getMethod().equals('GET')"],
					'/api/modelCatalogue/core/*/create':       ["hasAnyRole('ROLE_READONLY_ADMIN', 'ROLE_READONLY_USER') && request.getMethod().equals('GET')"],
					'/api/modelCatalogue/core/*/edit':         ["hasAnyRole('ROLE_READONLY_ADMIN', 'ROLE_READONLY_USER') && request.getMethod().equals('GET')"]

			]

		}
	}

	views {
		codec = "html" // none, html, base64
		gsp {
			encoding = 'UTF-8'
			htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
			codecs {
				expression = 'html' // escapes values inside ${}
				scriptlet = 'html' // escapes output from scriptlets in GSPs
				taglib = 'none' // escapes output from taglibs
				staticparts = 'none' // escapes output from static template parts
			}
			sitemesh {
				preprocess = true
			}
		}
		// escapes all not-encoded output at final stage of outputting
		filteringCodecForContentType {
			//'text/html' = 'html'
		}
	}
}


//get username and add to audit logging

auditLog {
	actorClosure = { request, session ->
		if (request.applicationContext.springSecurityService.principal instanceof java.lang.String){
			return request.applicationContext.springSecurityService.principal
		}
		def username = request.applicationContext.springSecurityService.principal?.username
		if (SpringSecurityUtils.isSwitched()){
			username = SpringSecurityUtils.switchedUserOriginalUsername+" AS "+username
		}
		return username
	}
}

//elastic search settings - please see elastic search GORM plugin for more deta

elasticSearch.client.mode = 'local'
elasticSearch.index.store.type = 'memory' // store local node in memory and not on disk
elasticSearch.datastoreImpl = 'hibernateDatastore'