package uk.co.mdc

import grails.gorm.DetachedCriteria
import org.modelcatalogue.core.CatalogueElement
import org.modelcatalogue.core.DataElement
import org.modelcatalogue.core.MeasurementUnit
import org.modelcatalogue.core.PublishedElement
import org.modelcatalogue.core.PublishedElementStatus
import org.modelcatalogue.core.RelationshipType
import org.modelcatalogue.core.ValueDomain


import org.modelcatalogue.core.SearchCatalogue




/**
 * Poor man's search service searching in name and description
 * , you should use search service designed for particular search plugin
 */
class ModelCatalogueSearchService {

	def search(Class resource, Map params) {
		if (!params.search) {
			return [errors: "No query string to search on"]
		}
		def searchResults = [:]

		String query = "%$params.search%"

		if (PublishedElement.isAssignableFrom(resource)) {
			DetachedCriteria criteria = new DetachedCriteria(resource)
			criteria.and {
				eq('status', PublishedElementStatus.FINALIZED)
				or {
					ilike('name', query)
					ilike('description', query)
				}
			}
			searchResults.searchResults = criteria.list(params)
			searchResults.total = criteria.count()
		} else if (CatalogueElement.isAssignableFrom(resource)) {

			searchResults.searchResults = resource.findAllByNameIlikeOrDescriptionIlike(query, query, params)
			searchResults.total = resource.countByNameIlikeOrDescriptionIlike(query, query, params)

		} else if (RelationshipType.isAssignableFrom(resource)) {
			searchResults.searchResults = resource.findAllByNameIlikeOrSourceToDestinationIlikeOrDestinationToSourceIlike(query, query, query, params)
			searchResults.total = resource.countByNameIlikeOrSourceToDestinationIlikeOrDestinationToSourceIlike(query, query, query, params)
		} else {
			searchResults.searchResults = resource.findAllByNameIlike(query, params)
			searchResults.total         = resource.countByNameIlike(query, params)
		}

		/*  remove ValueDomains and MeasurementUnit items
				from the search result
			*/
//		def invalidItems =
//				searchResults.searchResults.findAll { item ->
//					item.instanceOf(org.modelcatalogue.core.MeasurementUnit) ||
//					item.instanceOf(org.modelcatalogue.core.ValueDomain)
//				}
//		searchResults.searchResults.removeAll(invalidItems)
//		searchResults.total -= invalidItems.size()

		searchResults
	}

	def searchReadOnly(Class resource, Map params) {
        if (!params.search) {
            return [errors: "No query string to search on"]
        }
        def searchResults = [:]

        String query = "%$params.search%"

        if (PublishedElement.isAssignableFrom(resource)) {
            DetachedCriteria criteria = new DetachedCriteria(resource)
            criteria.and {
                eq('status', PublishedElementStatus.FINALIZED)
                or {
                    ilike('name', query)
                    ilike('description', query)
                }
            }
            searchResults.searchResults = criteria.list()
            searchResults.total = criteria.count()
        } else if (CatalogueElement.isAssignableFrom(resource)) {

			searchResults.searchResults = resource.findAllByNameIlikeOrDescriptionIlike(query, query)
            searchResults.total = resource.countByNameIlikeOrDescriptionIlike(query, query)

        } else if (RelationshipType.isAssignableFrom(resource)) {
            searchResults.searchResults = resource.findAllByNameIlikeOrSourceToDestinationIlikeOrDestinationToSourceIlike(query, query, query)
            searchResults.total = resource.countByNameIlikeOrSourceToDestinationIlikeOrDestinationToSourceIlike(query, query, query)
        } else {
            searchResults.searchResults = resource.findAllByNameIlike(query)
            searchResults.total         = resource.countByNameIlike(query)
        }

		//find ValueDomains and MeasurementUnit items
		//from search result
		def invalidItems =
				searchResults.searchResults.findAll { item ->
					item.instanceOf(org.modelcatalogue.core.MeasurementUnit) ||
					item.instanceOf(org.modelcatalogue.core.ValueDomain)
				}
		//remove those from result
		 searchResults.searchResults.removeAll(invalidItems)

		//find total result
		def totalCount = searchResults.searchResults.size()

		//do pagination in memory on the result!
		def filteredResult = getPaginatedList(searchResults.searchResults,params.max,params.offset)

		//update final result
		searchResults.searchResults = filteredResult
		searchResults.total = totalCount

        searchResults
    }

	def getPaginatedList(list, max, offset) {
		max = Math.min(max ?: 10, 100)
		offset = offset?:0
		int total = list.size()
		offset = offset.toInteger()
		max = max.toInteger()
		int upperLimit = ((offset + max) >= total ? total : offset + max ) - 1
		return offset < total ? list.getAt(offset..upperLimit) : []
	}

    def search(Map params){
		searchReadOnly CatalogueElement, params
    }

    def index(Class resource){}
	def index(Collection<Object> object){}
	def unindex(Object object){}
    def unindex(Collection<Object> object){}

}


//
//
//class ModelCatalogueSearchService implements SearchCatalogue{
//
//	def elasticSearchService, elasticSearchAdminService
//
//	def search(Class resource, Map params) {
//		def searchResults = [:]
//		def searchParams = getSearchParams(params)
//		if(searchParams.errors){
//			searchResults.put("errors" , searchParams.errors)
//			return searchResults
//		}
//		try{
//			searchResults = resource.search(searchParams){
//				bool {
//					must {
//						query_string(query: params.search)
//					}
//					must_not {
//						terms status: ['archived', 'draft', 'pending', 'removed'], system: ['true']
//					}
//				}
//			}
//		}catch(IllegalArgumentException e){
//			searchResults.put("errors" , "Illegal argument: ${e.getMessage()}")
//		}catch(Exception e){
//			searchResults.put("errors" , e.getMessage())
//		}
//
//		return searchResults
//	}
//
//	def search(Map params){
//		def searchResults = [:]
//		def searchParams = getSearchParams(params)
//		if(searchParams.errors){
//			searchResults.put("errors" , searchParams.errors)
//			return searchResults
//		}
//		searchParams.put("indices", "org.modelcatalogue.core")
//		searchParams.put("types", ["org.modelcatalogue.core.DataElement",  "org.modelcatalogue.core.ConceptualDomain", "org.modelcatalogue.core.DataType", "org.modelcatalogue.core.EnumeratedType", "org.modelcatalogue.core.Model"])
//		try{
//			searchResults = elasticSearchService.search(searchParams){
//				bool {
//					must {
//						query_string(query: params.search)
//					}
//					must_not {
//						terms status: ['archived', 'draft', 'pending', 'removed']
//					}
//				}
//			}
//		}catch(IllegalArgumentException e){
//			searchResults.put("errors" , "Illegal argument: ${e.getMessage()}")
//		}catch(Exception e){
//			searchResults.put("errors" , e.getMessage())
//		}
//
//		return searchResults
//	}
//
//	private static Map getSearchParams(Map params){
//		def searchParams = [:]
//		if(!params.search){
//			searchParams.put("errors" , "No query string to search on")
//			return searchParams
//		}
//		if(params.max){ searchParams.put("size" , "$params.max")}
//		if(params.sort){searchParams.put("sort" , "name")}
//		if(params.order){searchParams.put("order" , params.order.toLowerCase())}
//		if(params.offset){searchParams.put("from" , "$params.offset")}
//		return searchParams
//	}
//
//	//TODO add a few more of these
//
//	def index(Class resource){
//		elasticSearchService.index(resource)
//	}
//
//	def index(Collection<Class> resource){
//		elasticSearchService.index(resource)
//	}
//
//	def unindex(Object object){
//		elasticSearchService.unindex(object)
//	}
//
//	def unindex(Collection<Object> object){
//		elasticSearchService.unindex(object)
//	}
//
//	def refresh(){
//		elasticSearchAdminService.refresh()
//	}
//
//
//}