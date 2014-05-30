package uk.co.mdc.pages;

import geb.Browser
import geb.Page
import geb.Module
import uk.co.mdc.modules.FooterNav
import uk.co.mdc.modules.TopNavElementsAdmin

class BasePageWithNav extends Page{
	
	static at = {
		assert navPresentAndVisible
	}
	
	static content = {
		nav { module TopNavElementsAdmin }
		footer { module FooterNav }
	}
}