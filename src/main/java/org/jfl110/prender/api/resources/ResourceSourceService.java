package org.jfl110.prender.api.resources;

import java.io.InputStream;

import javax.servlet.ServletContext;

/**
 * Service to transform a ResourceSource to an InputStream.
 *
 * @author JFL110
 */
public interface ResourceSourceService<T extends ResourceSource> {
	
	/**
	 * @return the type of ResourceSource transformed by this service.
	 */
	Class<T> getResourceSourceType();
	
	/**
	 * Transform the ResourceSource to an InputStream.
	 * 
	 * @param resourceSource the ResourceSource to transform.
	 * @param servletContext the ServletContex.
	 */
	InputStream toInputStream(T resourceSource,ServletContext servletContext);

}