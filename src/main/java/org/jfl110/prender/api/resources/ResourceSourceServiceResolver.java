package org.jfl110.prender.api.resources;

import javax.servlet.ServletContext;

/**
 * Uses known ResourceSourceServices to transform a 
 * ResourceSource to an InputStream
 *
 * @author JFL110
 */
public interface ResourceSourceServiceResolver {

	/**
	 * Transform the ResourceSource to an InputStream
	 * 
	 * @param resourceSource the ResourceSource to transform
	 * @param servletContext the ServletContex
	 */
	Resource toInputStream(ResourceSource resourceSource,ServletContext servletContext);
}
