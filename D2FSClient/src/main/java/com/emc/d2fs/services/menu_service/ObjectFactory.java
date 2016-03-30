
package com.emc.d2fs.services.menu_service;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.emc.d2fs.services.menu_service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.emc.d2fs.services.menu_service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetFullMenusTypeRequest }
     * 
     */
    public GetFullMenusTypeRequest createGetFullMenusTypeRequest() {
        return new GetFullMenusTypeRequest();
    }

    /**
     * Create an instance of {@link GetFullMenusTypeResponse }
     * 
     */
    public GetFullMenusTypeResponse createGetFullMenusTypeResponse() {
        return new GetFullMenusTypeResponse();
    }

    /**
     * Create an instance of {@link GetMenusTypeRequest }
     * 
     */
    public GetMenusTypeRequest createGetMenusTypeRequest() {
        return new GetMenusTypeRequest();
    }

    /**
     * Create an instance of {@link GetMenusTypeResponse }
     * 
     */
    public GetMenusTypeResponse createGetMenusTypeResponse() {
        return new GetMenusTypeResponse();
    }

}