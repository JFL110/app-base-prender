package org.jfl110.prender.impl.parse;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * Very basic tests of YuiCssCompressor.
 * See https://github.com/yui/yuicompressor
 *
 * @author JFL110
 */
@RunWith(Parameterized.class)
public class TestYUICssCompressor {
	
	private static final int DEFAULT_CHARS_PER_LINE = 1000;
	
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                 { "not-valid-css~@{{{:SS$^",
                   "not-valid-css~@{{{:SS$^",DEFAULT_CHARS_PER_LINE } ,
                 
                 { "body{\n margin: 	0px; \n}\n\n",
                   "body{margin:0;}",DEFAULT_CHARS_PER_LINE },
                 
                 { "#someDiv{ color: #FFFFFF; }",
                   "#someDiv{color:#FFF;}",DEFAULT_CHARS_PER_LINE },
                 
                 { "#someDiv{ height:100%; \n \n width:100% \n\n } \n #someDiv2 { margin:10px;} ",
                   "#someDiv{height:100%;width:100%;}\n#someDiv2{margin:10px;}",10 } ,
                 
                 { "/* A Comment! */ #div2{textProperty:'thisIsTheText';}",
                   "#div2{textProperty:'thisIsTheText';}",DEFAULT_CHARS_PER_LINE},
                 
                 {"div\n{font-family: Courier new; \n height:calc(100% - 10px);}\n",
                  "div{font-family:Courier new;height:calc(100% - 10px);}",DEFAULT_CHARS_PER_LINE}
           });
    }
    
    @Parameter
    public String input;
    
    @Parameter(value = 1)
    public String expectedOutput;
    
    @Parameter(value = 2)
    public int maxCharsPerLine;
    
	private final YUICssCompressor compressor = new YUICssCompressor();
	
	
	@Test
	public void testCompressionAsString() throws IOException{
		StringWriter output = new StringWriter();
		
		compressor.compress(output, input, maxCharsPerLine);
		
		assertEquals(expectedOutput,output.toString());
	}
	
	
	@Test
	public void testCompressionAsStream() throws IOException{
		StringWriter output = new StringWriter();
		
		compressor.compress(output,  new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)), maxCharsPerLine);
		
		assertEquals(expectedOutput,output.toString());
	}
}
