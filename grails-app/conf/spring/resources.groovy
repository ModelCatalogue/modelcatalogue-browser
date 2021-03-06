import grails.rest.render.RenderContext
import org.modelcatalogue.core.CatalogueElement
import org.modelcatalogue.core.util.ListWrapper
import org.modelcatalogue.core.util.marshalling.xlsx.XLSXListRenderer
//import uk.co.mdc.ModelCatalogueSearchService
import uk.co.mdc.pathways.PathwayMarshaller
import util.marshalling.CustomObjectMarshallers
import uk.co.mdc.pathways.LinkMarshaller
import uk.co.mdc.pathways.NodeMarshaller
import grails.util.Environment
import util.marshalling.DataElementMarshaller

// Place your Spring DSL code here
beans = {

	Environment.executeForCurrentEnvironment {
		// Override mail server for dummy in 'development' mode only.
		development {
			mailService(uk.co.mdc.mail.DummyMailService)
		}
	}

	customObjectMarshallers( CustomObjectMarshallers ) {
		marshallers = [
            new PathwayMarshaller(),
            new LinkMarshaller(),
            new NodeMarshaller(),
            new DataElementMarshaller()
		]
	}

}



