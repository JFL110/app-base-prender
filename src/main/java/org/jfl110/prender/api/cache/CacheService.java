package org.jfl110.prender.api.cache;

import java.util.concurrent.Callable;

import com.google.inject.ImplementedBy;

/**
 * Cache wrapper
 *
 * @author JFL110
 */
@ImplementedBy(CacheService.NoOpCacheService.class)
public interface CacheService {

	<T> T get(String cacheName,String key, Callable<T> computeFunction);
	
	/**
	 * Implementation that just calls the compute function on everything
	 * 
	 * @author JFL110
	 */
	class NoOpCacheService implements CacheService{

		@Override
		public <T> T get(String cacheName,String key, Callable<T> computeFunction) {
			try{
				return computeFunction.call();
			}catch(Exception e){
				throw new RuntimeException(e);
			}
		}
		
	}
}