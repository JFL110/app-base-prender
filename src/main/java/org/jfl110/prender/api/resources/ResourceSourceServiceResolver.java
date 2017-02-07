package org.jfl110.prender.api.resources;

import java.io.InputStream;

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
	InputStream toInputStream(ResourceSource resourceSource,ServletContext servletContext);
}
