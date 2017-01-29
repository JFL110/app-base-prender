package org.jfl110.prender.api.cache;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.util.concurrent.Callable;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public final class TestingCacheService{

	@SuppressWarnings("unchecked")
	public static CacheService testingCacheService(){
		CacheService cacheService = mock(CacheService.class);
		
		doAnswer(new Answer<Object>(){

			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				@SuppressWarnings("rawtypes")
				Callable computeFunction = (Callable) invocation.getArgument(2);
				return computeFunction.call();
			}
			
		})
		.when(cacheService)
		.get(Mockito.any(String.class),Mockito.any(String.class), Mockito.any(Callable.class));
		
		return cacheService;
	}
}
