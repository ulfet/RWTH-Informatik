package de.rwth.swc.teaching.sqa;


import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class EmailValidatorTest {

    private static EmailValidator validator;
    
    @BeforeAll
    public static void init(){
        validator =  new EmailValidator();
    }
    
    // localPart boundary variables
    public static final int localMinLength_minus = 0;
    public static final int localMinLength = 1;
    public static final int localMinLength_plus = 2;
    
    public static final int localNomLength = 33;
    
    public static final int localMaxLength_minus = 63;
    public static final int localMaxLength = 64;
    public static final int localMaxLength_plus = 65;
    
    // domainPart boundary variables
    public static final int domainMinLength_minus = 0;
    public static final int domainMinLength = 1;
    public static final int domainMinLength_plus = 2;
    
    public static final int domainNomLength = 65;
    
    public static final int domainMaxLength_minus = 127;
    public static final int domainMaxLength = 128;
    public static final int domainMaxLength_plus = 129;
    
    // topLevelPart boundary variables
    public static final int topMinLength_minus = 0;
    public static final int topMinLength = 1;
    public static final int topMinLength_plus = 2;
    
    public static final int topNomLength = 32;
    
    public static final int topMaxLength_minus = 62;
    public static final int topMaxLength = 63;
    public static final int topMaxLength_plus = 64;
    
    String characterPool = new String ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
    int characterPoolSize = characterPool.length();
    
    public String stringGeneratorbyLength(Random r, int stringLength) {
		if (stringLength == 0)
			return new String("");
		
		String resultString = new String("");
		for (int i=0; i<stringLength; i++) {
			int pickedValue = r.nextInt(characterPoolSize);
			resultString = resultString + characterPool.charAt(pickedValue);
		}
		return resultString;
    }
    
    public Triple<String, String, String> mailGeneratorByLength(Random r, int localLength, int domainLength, int topLength) {
    	String _local = stringGeneratorbyLength(r, localLength);
    	String _domain = stringGeneratorbyLength(r, domainLength);
    	String _top = stringGeneratorbyLength(r, topLength);
    	
    	Triple<String, String, String> emailTriple = Triple.of(_local, _domain, _top);
    	return emailTriple;
    }
   
    @Test
    public void testEmail(){
        Random r =  new Random();
        Assertions.assertTrue(validator.validateEMailAdress("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa","aaa","aaa"),"correct e-Mail address marked as invalid");
    }
    
    @TestFactory
    Collection<DynamicTest> simpleBVT() {
    	List<Integer> localValues	= Arrays.asList( localMinLength, localMinLength_plus, localMaxLength_minus, localMaxLength);
    	List<Integer> domainValues	= Arrays.asList( domainMinLength	, domainMinLength_plus	, domainMaxLength_minus	, domainMaxLength);
    	List<Integer> topValues		= Arrays.asList( topMinLength, topMinLength_plus, topMaxLength_minus, topMaxLength);
    	
    	Random r = new Random();
    	ArrayList<DynamicTest> tests = new ArrayList<DynamicTest>();
    	
    	// all nominal case
    	Triple<String, String, String> sNom = mailGeneratorByLength(r, localNomLength, domainNomLength, topNomLength);
    	String testMessageNom = "[Simple] -> with [ " + ((Integer)localNomLength) + " " + ((Integer)topNomLength) + " " +  ((Integer)topNomLength) + " ]" ;
    	System.out.println(testMessageNom);
    	DynamicTest tNom;
    	tNom = DynamicTest.dynamicTest(testMessageNom,
				() -> Assertions.assertTrue(validator.validateEMailAdress(sNom.getLeft(), sNom.getMiddle(), sNom.getRight()), "pass"));
    	tests.add(tNom);
    	
    	for (Integer lVal: localValues) 
    	{
			Triple<String, String, String> s = mailGeneratorByLength(r, lVal, domainNomLength, topNomLength);
    		String testMessage = "[Simple] -> with [ " + lVal.toString() + " " + ((Integer)domainNomLength) + " " + ((Integer)topNomLength) + " ]";
    		System.out.println(testMessage);
    		DynamicTest t;
    		t = DynamicTest.dynamicTest(testMessage,
					() -> Assertions.assertTrue(validator.validateEMailAdress(s.getLeft(), s.getMiddle(), s.getRight()), "pass"));
        	tests.add(t);
    	}
    	
    	for (Integer dVal: domainValues) 
    	{
			Triple<String, String, String> s = mailGeneratorByLength(r, localNomLength, dVal, topNomLength);
    		String testMessage = "[Simple] -> with [ " + ((Integer)localNomLength).toString() + " " + dVal.toString() + " " + ((Integer)topNomLength).toString() + " ]";
    		System.out.println(testMessage);
    		DynamicTest t;
        		t = DynamicTest.dynamicTest(testMessage,
						() -> Assertions.assertTrue(validator.validateEMailAdress(s.getLeft(), s.getMiddle(), s.getRight()), "pass"));
        	tests.add(t);
    	}
    	
    	for (Integer tVal: topValues) 
    	{
			Triple<String, String, String> s = mailGeneratorByLength(r, localNomLength, domainNomLength, tVal);
    		String testMessage = "[Simple] -> with [ " + ((Integer)localNomLength).toString() + " " + ((Integer)topNomLength).toString() + " " + tVal.toString() + " ]" ;
    		System.out.println(testMessage);
    		DynamicTest t;
        		t = DynamicTest.dynamicTest(testMessage,
						() -> Assertions.assertTrue(validator.validateEMailAdress(s.getLeft(), s.getMiddle(), s.getRight()), "pass"));
        	tests.add(t);
    	}

    	System.out.println("[Simple] #Cases: " + ((Integer)tests.size()).toString());
    	System.out.println();
    	return tests;
    }
    
    @TestFactory
    Collection<DynamicTest> robustnessBVT() {
    	List<Integer> localValues	= Arrays.asList( localMinLength_minus, localMinLength, localMinLength_plus, localMaxLength_minus, localMaxLength, localMaxLength_plus);
    	List<Integer> domainValues	= Arrays.asList( domainMinLength_minus, domainMinLength	, domainMinLength_plus	, domainMaxLength_minus	, domainMaxLength, domainMaxLength_plus);
    	List<Integer> topValues		= Arrays.asList( topMinLength_minus, topMinLength, topMinLength_plus, topMaxLength_minus, topMaxLength, topMaxLength_plus);
    	
    	List<Integer> safeL	= Arrays.asList( localMinLength, localMinLength_plus,localNomLength, localMaxLength_minus, localMaxLength);
    	List<Integer> safeD	= Arrays.asList( domainMinLength, domainMinLength_plus, domainNomLength, domainMaxLength_minus	, domainMaxLength);
    	List<Integer> safeT	= Arrays.asList( topMinLength, topMinLength_plus, topNomLength, topMaxLength_minus, topMaxLength);
    	
    	Random r = new Random();
    	ArrayList<DynamicTest> tests = new ArrayList<DynamicTest>();
    	
    	// all nominal case
    	Triple<String, String, String> sNom = mailGeneratorByLength(r, localNomLength, domainNomLength, topNomLength);
    	String testMessageNom = "[Robustness] -> with [ " + ((Integer)localNomLength) + " " + ((Integer)topNomLength) + " " +  ((Integer)topNomLength) + " ]" ;
    	
    	Boolean actualResultNom = validator.validateEMailAdress(sNom.getLeft(), sNom.getMiddle(), sNom.getRight());
    	Boolean isSafeNom = true;
    	System.out.println(testMessageNom + " " + isSafeNom + " | " + actualResultNom);
    	
    	DynamicTest tNom;
    	tNom = DynamicTest.dynamicTest(testMessageNom,
				() -> Assertions.assertTrue(validator.validateEMailAdress(sNom.getLeft(), sNom.getMiddle(), sNom.getRight()), "pass"));
    	tests.add(tNom);
    	
    	for (Integer lVal: localValues) 
    	{
			Triple<String, String, String> s = mailGeneratorByLength(r, lVal, domainNomLength, topNomLength);
    		Boolean isSafe = safeL.contains(lVal);
    		String testMessage = "[Robustness] -> with [ " + lVal.toString() + " " + ((Integer)domainNomLength) + " " + ((Integer)topNomLength) + " ]";
    		
    		Boolean actualResult = validator.validateEMailAdress(s.getLeft(), s.getMiddle(), s.getRight());
    		System.out.println(testMessage + " " + isSafe + " | " + actualResult);
    		
    		DynamicTest t;
        	if (isSafe)
        		t = DynamicTest.dynamicTest(testMessage,
						() -> Assertions.assertTrue(validator.validateEMailAdress(s.getLeft(), s.getMiddle(), s.getRight()), "pass"));
        	else
        		t = DynamicTest.dynamicTest(testMessage,
						() -> Assertions.assertFalse(validator.validateEMailAdress(s.getLeft(), s.getMiddle(), s.getRight()), "pass"));
        	tests.add(t);
    	}
    	
    	for (Integer dVal: domainValues) 
    	{
			Triple<String, String, String> s = mailGeneratorByLength(r, localNomLength, dVal, topNomLength);
    		Boolean isSafe = safeD.contains(dVal);
    		String testMessage = "[Robustness] -> with [ " + ((Integer)localNomLength).toString() + " " + dVal.toString() + " " + ((Integer)topNomLength).toString() + " ]";
    		
    		Boolean actualResult = validator.validateEMailAdress(s.getLeft(), s.getMiddle(), s.getRight());
			System.out.println(testMessage + " " + isSafe + " | " + actualResult);
    		
    		DynamicTest t;
        	if (isSafe)
        		t = DynamicTest.dynamicTest(testMessage,
						() -> Assertions.assertTrue(validator.validateEMailAdress(s.getLeft(), s.getMiddle(), s.getRight()), "pass"));
        	else
        		t = DynamicTest.dynamicTest(testMessage,
						() -> Assertions.assertFalse(validator.validateEMailAdress(s.getLeft(), s.getMiddle(), s.getRight()), "pass"));
        	tests.add(t);
    	}
    	
    	for (Integer tVal: topValues) 
    	{
			Triple<String, String, String> s = mailGeneratorByLength(r, localNomLength, domainNomLength, tVal);
    		Boolean isSafe = safeT.contains(tVal);
    		String testMessage = "[Robustness] -> with [ " + ((Integer)localNomLength).toString() + " " + ((Integer)topNomLength).toString() + " " + tVal.toString() + " ]" ;
    		
    		Boolean actualResult = validator.validateEMailAdress(s.getLeft(), s.getMiddle(), s.getRight());
			System.out.println(testMessage + " " + isSafe + " | " + actualResult);
			
    		DynamicTest t;
        	if (isSafe)
        		t = DynamicTest.dynamicTest(testMessage,
						() -> Assertions.assertTrue(validator.validateEMailAdress(s.getLeft(), s.getMiddle(), s.getRight()), "pass"));
        	else
        		t = DynamicTest.dynamicTest(testMessage,
						() -> Assertions.assertFalse(validator.validateEMailAdress(s.getLeft(), s.getMiddle(), s.getRight()), "pass"));
        	tests.add(t);
    	}

    	System.out.println("[Robustness] #Cases: " + ((Integer)tests.size()).toString());
    	System.out.println();
    	return tests;
    }
    
    @TestFactory
    Collection<DynamicTest> worstCaseBVT() {
    	
    	List<Integer> localValues	= Arrays.asList( localMinLength, localMinLength_plus,localNomLength, localMaxLength_minus, localMaxLength);
    	List<Integer> domainValues	= Arrays.asList( domainMinLength, domainMinLength_plus, domainNomLength, domainMaxLength_minus	, domainMaxLength);
    	List<Integer> topValues		= Arrays.asList( topMinLength, topMinLength_plus, topNomLength, topMaxLength_minus, topMaxLength);
    	
    	List<Integer> safeL	= Arrays.asList( localMinLength, localMinLength_plus,localNomLength, localMaxLength_minus, localMaxLength);
    	List<Integer> safeD	= Arrays.asList( domainMinLength, domainMinLength_plus, domainNomLength, domainMaxLength_minus	, domainMaxLength);
    	List<Integer> safeT	= Arrays.asList( topMinLength, topMinLength_plus, topNomLength, topMaxLength_minus, topMaxLength);
    	
    	Random r = new Random();
    	ArrayList<DynamicTest> tests = new ArrayList<DynamicTest>();
    	
    	for (Integer lVal: localValues) 
    	{
    		
    		for (Integer dVal: domainValues) 
    		{    			
    			for (Integer tVal: topValues) 
    			{
    				Boolean isSafe = (safeL.contains(lVal) && safeD.contains(dVal) && safeT.contains(tVal));
    				String testMessage = "[Worst] -> with [ " + lVal.toString() + " " + dVal.toString() + " " + tVal.toString() + " ]";
    				
    				Triple<String, String, String> s = mailGeneratorByLength(r, lVal, dVal, tVal);
    				
    				Boolean actualResult = validator.validateEMailAdress(s.getLeft(), s.getMiddle(), s.getRight());
					System.out.println(testMessage + " " + isSafe + " | " + actualResult);
    				
    				DynamicTest t;
    				if ( isSafe )
    					t = DynamicTest.dynamicTest(testMessage,
    						() -> Assertions.assertTrue(validator.validateEMailAdress(s.getLeft(), s.getMiddle(), s.getRight()), "pass"));
    				else
    					t = DynamicTest.dynamicTest(testMessage,
        						() -> Assertions.assertFalse(validator.validateEMailAdress(s.getLeft(), s.getMiddle(), s.getRight()), "pass"));
    				tests.add(t);
    			}
    		}
    	}
    	
    	System.out.println("[WorstCase] #Cases: " + ((Integer)tests.size()).toString());
    	System.out.println();
    	return tests;
    }
    
    @TestFactory
    Collection<DynamicTest> robustnessWorstCaseBVT() {
    	
    	List<Integer> localValues	= Arrays.asList( localMinLength_minus, localMinLength, localMinLength_plus,localNomLength, localMaxLength_minus, localMaxLength, localMaxLength_plus);
    	List<Integer> domainValues	= Arrays.asList( domainMinLength_minus, domainMinLength	, domainMinLength_plus, domainNomLength, domainMaxLength_minus	, domainMaxLength, domainMaxLength_plus);
    	List<Integer> topValues	= Arrays.asList( topMinLength_minus, topMinLength, topMinLength_plus, topNomLength, topMaxLength_minus, topMaxLength, topMaxLength_plus );
    	
    	List<Integer> safeL	= Arrays.asList( localMinLength, localMinLength_plus,localNomLength, localMaxLength_minus, localMaxLength);
    	List<Integer> safeD	= Arrays.asList( domainMinLength, domainMinLength_plus, domainNomLength, domainMaxLength_minus	, domainMaxLength);
    	List<Integer> safeT	= Arrays.asList( topMinLength, topMinLength_plus, topNomLength, topMaxLength_minus, topMaxLength);
    	
    	Random r = new Random();
    	ArrayList<DynamicTest> tests = new ArrayList<DynamicTest>();
    	
    	for (Integer lVal: localValues) 
    	{
    		
    		for (Integer dVal: domainValues) 
    		{    			
    			for (Integer tVal: topValues) 
    			{
    				Boolean isSafe = (safeL.contains(lVal) && safeD.contains(dVal) && safeT.contains(tVal));
    				String testMessage = "[RobustnessWorst] -> with [ " + lVal.toString() + " " + dVal.toString() + " " + tVal.toString() + " ]";
    				
    				Triple<String, String, String> s = mailGeneratorByLength(r, lVal, dVal, tVal);
    				
    				Boolean actualResult = validator.validateEMailAdress(s.getLeft(), s.getMiddle(), s.getRight());
    				if (isSafe != actualResult)
    					System.out.println(testMessage + " " + isSafe + " | " + actualResult);
    				
    				DynamicTest t;
    				if ( isSafe )
    					t = DynamicTest.dynamicTest(testMessage,
    						() -> Assertions.assertTrue(validator.validateEMailAdress(s.getLeft(), s.getMiddle(), s.getRight()), "pass"));
    				else
    					t = DynamicTest.dynamicTest(testMessage,
        						() -> Assertions.assertFalse(validator.validateEMailAdress(s.getLeft(), s.getMiddle(), s.getRight()), "pass"));
    				tests.add(t);
    			}
    		}
    	}
    	System.out.println("[RobustnessWorstCase] #Cases: " + ((Integer)tests.size()).toString());
    	System.out.println();
    	return tests;
    }
}
