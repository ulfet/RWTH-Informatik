package de.rwth.swc.sqa.glassbox;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringHelperTest {

//    @Test
//    void demonstrationTest() {
//        assertEquals( "abc  ", StringHelper.stripFromStart("yxabc  ", "xyz"));
//    }
    
    @Test
    void branch1A() {
    	assertEquals( null, StringHelper.stripFromStart(null, ""));
    }
    
    @Test
    void branch1B2C3E3F() {
    	assertEquals( "abc", StringHelper.stripFromStart("   abc", null));
    }
    
    @Test
    void branch1B2D3G() {
    	assertEquals( "abc", StringHelper.stripFromStart("abc", ""));
    }
    
    @Test
    void branch1B2D3H3J3I() {
    	assertEquals( "abc", StringHelper.stripFromStart("yzabc", "xyz"));
    }
    
    // Boundary Interior - no loop
    @Test
    void branch1B2C3F_noloop() {
    	assertEquals( "abc", StringHelper.stripFromStart("abc", null));
    }
    
    @Test
    void branch1B2D3H3I_noloop() {
    	assertEquals( "yzabc", StringHelper.stripFromStart("yzabc", "x"));
    }
    
    // Boundary Interior - one loop (boundary)
    @Test
    void branch1B2C3E3F_oneloop() {
    	assertEquals( "abc", StringHelper.stripFromStart(" abc", null));
    }
    
    @Test
    void branch1B2D3H3J3I_oneloop() {
    	assertEquals( "zabc", StringHelper.stripFromStart("yzabc", "y"));
    }
    
    // Boundary Interior - at least two loop (interior)
    @Test
    void branch1B2C3E3F_atleasttwoloop() {
    	assertEquals( "abc", StringHelper.stripFromStart("  abc", null));
    }
    
    @Test
    void branch1B2D3H3J3I_atleasttwoloop() {
    	assertEquals( "abc", StringHelper.stripFromStart("yzabc", "yz"));
    }
}
