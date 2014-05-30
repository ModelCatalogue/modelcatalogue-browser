import java.rmi.Naming.ParsedNamingURL;

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.acls.model.NotFoundException

class UrlMappings {

	static mappings = {
		
		// Default for controllers
		"/$controller/$action?/$id?(.$format)?"{
			constraints {
				// apply constraints here
			}
		}

		//Commented as it is ReadOnly View and we do not support Pathway in this version


		//Added as it is ReadOnly View and we do not support Pathway/dashboard in this version
		"/dashboard"(controller:"metadataCurator",action: "index" )
		"/pathways"(controller:"metadataCurator",action: "index" )
		"/pathway"(controller:"metadataCurator",action: "index" )
		// "/pathways"(resources: "pathway") it used to be in this format
		"/metadataCurator/demo"(controller:"metadataCurator",action: "index" )

        // API endpoints
		//"/api/forms"(version:'1.0', resources:"form", namespace:'v1')
		//"/api/dataelements"(version:'1.0', resources:"dataElement", namespace:'v1')

		name pendingUsers: "/role/pendingUsers"( controller: "role", action: "listPendingUsers" )
		name importDataOld: "/admin/oldImportData"(controller: "oldDataImport")
        name importData: "/admin/importData"(controller: "dataImport")
		name importICU: "/admin/importICU"(controller: "excelImporter")
        name importCOSD: "/admin/importCOSD"(controller:"COSDImporter")
        name importRelationships: "/admin/importRelationships"(controller:"relationshipImport")

        "/"(view:"/index")

		"403"(controller: "errors", action: "error403") 
		"404"(controller: "errors", action: "error404") 
		"500"(controller: "errors", action: "error500") 
		"500"(controller: "errors", action: "error403", exception: AccessDeniedException) 
		"500"(controller: "errors", action: "error403", exception: NotFoundException) 
	}
}