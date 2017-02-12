package org.jfl110.prender.impl.serve;

import org.jfl110.prender.api.serve.ServeBuilder;

public class Serving {
	
	public static ServeBuilder<ServeBuilder<?>> of(String path){
		return new ServeBuilderImpl(path);
	}
}
