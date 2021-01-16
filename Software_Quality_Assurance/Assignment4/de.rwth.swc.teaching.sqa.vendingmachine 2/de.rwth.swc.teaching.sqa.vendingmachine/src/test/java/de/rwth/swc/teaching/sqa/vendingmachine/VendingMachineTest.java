package de.rwth.swc.teaching.sqa.vendingmachine;

import de.rwth.swc.teaching.sqa.Drink;
import de.rwth.swc.teaching.sqa.VendingMachine;
import de.rwth.swc.teaching.sqa.exception.*;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.omg.CosNaming.NamingContextPackage.NotEmpty;


public class VendingMachineTest {

    private static VendingMachine machine;
    private static int testMax = 10;

    @BeforeAll
    static public void init() throws InvalidDrinkNumberException {
        machine =  new VendingMachine(0,0,0);

    }
    
    @Test
    public void roundPath1() throws InvalidDrinkNumberException, CoinInsertedException, MachineFullException, InvalidDrinkException, NoCoinInsertedException {
    	// NoCoinNoDrink
    	machine = new VendingMachine(0,0,0);
    	Assertions.assertFalse(machine.coinInserted());
    	Assertions.assertTrue(machine.drinkEmpty(Drink.COKE));
    	
    	// CoinNoDrink
    	machine.insertCoin();
        Assertions.assertTrue(machine.coinInserted());
        Assertions.assertTrue(machine.drinkEmpty(Drink.COKE));
        
        // NoCoinNoDrink
        machine.moneyBack();
        Assertions.assertFalse(machine.coinInserted());
        Assertions.assertTrue(machine.drinkEmpty(Drink.COKE));
    }
    
    @Test
    public void roundPath2() throws InvalidDrinkNumberException, CoinInsertedException, MachineFullException, InvalidDrinkException {
    	// NoCoinNoDrink
    	machine = new VendingMachine(0,0,0);
    	Assertions.assertFalse(machine.coinInserted());
    	Assertions.assertTrue(machine.drinkEmpty(Drink.COKE));
    	
    	// CoinNoDrink
    	machine.insertCoin();
        Assertions.assertTrue(machine.coinInserted());
        Assertions.assertTrue(machine.drinkEmpty(Drink.COKE));
        
        // CoinDrinks
        machine.fillWithDrink(Drink.COKE);
        Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
        Assertions.assertTrue(machine.coinInserted());
    }
    
    @Test
    public void roundPath3() throws InvalidDrinkNumberException, InvalidDrinkException, MachineFullException {
    	// NoCoinNoDrink
    	machine = new VendingMachine(0,0,0);
    	Assertions.assertFalse(machine.coinInserted());
    	Assertions.assertTrue(machine.drinkEmpty(Drink.COKE));
    	
    	// NoCoinDrinks
    	machine.fillWithDrink(Drink.COKE);
    	Assertions.assertFalse(machine.coinInserted());
        Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    }
    
    // may add a generator
    @Test
    public void roundPath4() throws InvalidDrinkNumberException, InvalidDrinkException, MachineFullException {
    	// NoCoinDrinks
    	machine = new VendingMachine(5,0,0);
    	Assertions.assertFalse(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertFalse(machine.drinkFull(Drink.COKE));
    	
    	// NoCoinDrinks
    	machine.fillWithDrink(Drink.COKE);
    	Assertions.assertFalse(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertFalse(machine.drinkFull(Drink.COKE));
    }
    
    @Test
    public void roundPath5() throws InvalidDrinkNumberException, InvalidDrinkException, MachineFullException {
    	// NoCoinDrinks
    	machine = new VendingMachine(9,0,0);
    	Assertions.assertFalse(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertFalse(machine.drinkFull(Drink.COKE));
    	
    	// NoCoinFull
    	machine.fillWithDrink(Drink.COKE);
    	Assertions.assertFalse(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertTrue(machine.drinkFull(Drink.COKE));
    }
    
    @Test
    public void roundPath6() throws InvalidDrinkNumberException, InvalidDrinkException, CoinInsertedException, NoCoinInsertedException, MachineEmptyException {
    	// NoCoinDrinks
    	machine = new VendingMachine(1,0,0);
    	Assertions.assertFalse(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertFalse(machine.drinkFull(Drink.COKE));
    	
    	// CoinDrinks
    	machine.insertCoin();
    	Assertions.assertTrue(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertFalse(machine.drinkFull(Drink.COKE));	
    	
    	// NoCoinNoDrinks
    	machine.getDrink(Drink.COKE);
    	Assertions.assertFalse(machine.coinInserted());
    	Assertions.assertTrue(machine.drinkEmpty(Drink.COKE));
    }
    
    @Test
    public void roundPath7() throws InvalidDrinkNumberException, InvalidDrinkException, CoinInsertedException, NoCoinInsertedException {
    	// NoCoinDrinks
    	machine = new VendingMachine(1,0,0);
    	Assertions.assertFalse(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertFalse(machine.drinkFull(Drink.COKE));
    	
    	// CoinDrinks
    	machine.insertCoin();
    	Assertions.assertTrue(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertFalse(machine.drinkFull(Drink.COKE));
    	
    	// NoCoinDrinks
    	machine.moneyBack();
    	Assertions.assertFalse(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertFalse(machine.drinkFull(Drink.COKE));
    }
    
    @Test
    public void roundPath8() throws InvalidDrinkException, InvalidDrinkNumberException, CoinInsertedException, NoCoinInsertedException, MachineEmptyException {
    	// NoCoinDrinks
    	machine = new VendingMachine(5,0,0);
    	Assertions.assertFalse(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertFalse(machine.drinkFull(Drink.COKE));
    	
    	// CoinDrinks
    	machine.insertCoin();
    	Assertions.assertTrue(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertFalse(machine.drinkFull(Drink.COKE));
    	
    	// NoCoinDrinks
    	machine.getDrink(Drink.COKE);
    	Assertions.assertFalse(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertFalse(machine.drinkFull(Drink.COKE));
    }
    
    @Test
    public void roundPath9() throws InvalidDrinkNumberException, InvalidDrinkException, CoinInsertedException, MachineFullException {
    	// NoCoinDrinks
    	machine = new VendingMachine(5,0,0);
    	Assertions.assertFalse(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertFalse(machine.drinkFull(Drink.COKE));
    	
    	// CoinDrinks
    	machine.insertCoin();
    	Assertions.assertTrue(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertFalse(machine.drinkFull(Drink.COKE));
    	
    	// CoinDrinks
    	machine.fillWithDrink(Drink.COKE);
    	Assertions.assertTrue(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertFalse(machine.drinkFull(Drink.COKE));
    }
    
    @Test
    public void roundPath10() throws InvalidDrinkNumberException, InvalidDrinkException, CoinInsertedException, MachineFullException {
    	// NoCoinDrinks
    	machine = new VendingMachine(9,0,0);
    	Assertions.assertFalse(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertFalse(machine.drinkFull(Drink.COKE));
    	
    	// CoinDrinks
    	machine.insertCoin();
    	Assertions.assertTrue(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertFalse(machine.drinkFull(Drink.COKE));
    	
    	// CoinDrinks
    	machine.fillWithDrink(Drink.COKE);
    	Assertions.assertTrue(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertTrue(machine.drinkFull(Drink.COKE));
    }
    
    @Test
    public void roundPath11() throws InvalidDrinkNumberException, InvalidDrinkException, CoinInsertedException, NoCoinInsertedException, MachineEmptyException {
    	// NoCoinFull
    	machine = new VendingMachine(10,0,0);
    	Assertions.assertFalse(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertTrue(machine.drinkFull(Drink.COKE));
    	
    	// CoinFull
    	machine.insertCoin();
    	Assertions.assertTrue(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertTrue(machine.drinkFull(Drink.COKE));
    	
    	// NoCoinDrinks
    	machine.getDrink(Drink.COKE);
    	Assertions.assertFalse(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertFalse(machine.drinkFull(Drink.COKE));
    }
    
    @Test
    public void roundPath12() throws InvalidDrinkNumberException, InvalidDrinkException, CoinInsertedException, NoCoinInsertedException {
    	// NoCoinFull
    	machine = new VendingMachine(10,0,0);
    	Assertions.assertFalse(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertTrue(machine.drinkFull(Drink.COKE));
    	
    	// CoinFull
    	machine.insertCoin();
    	Assertions.assertTrue(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertTrue(machine.drinkFull(Drink.COKE));
    	
    	// NoCoinFull
    	machine.moneyBack();
    	Assertions.assertFalse(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertTrue(machine.drinkFull(Drink.COKE));
    }
    
    @Test
    public void sneakPath1() throws InvalidDrinkNumberException, InvalidDrinkException {
    	//NoCoinNoDrink
    	//moneyBack()
    	
    	// NoCoinNoDrink
    	machine = new VendingMachine(0,0,0);
    	Assertions.assertFalse(machine.coinInserted());
    	Assertions.assertTrue(machine.drinkEmpty(Drink.COKE));
    	
    	// try moneyBack()
    	Executable executeMoneyBack = () -> machine.moneyBack();
    	Assertions.assertThrows(NoCoinInsertedException.class, executeMoneyBack);
    }
    
    @Test
    public void sneakPath2() throws InvalidDrinkNumberException, InvalidDrinkException {
    	// NoCoinNoDrink
    	// getDrink()
    	
    	// NoCoinNoDrink
    	machine = new VendingMachine(0,0,0);
    	Assertions.assertFalse(machine.coinInserted());
    	Assertions.assertTrue(machine.drinkEmpty(Drink.COKE));
    	
    	// try getDrink
    	Executable executeGetDrinkForCoke = () -> machine.getDrink(Drink.COKE);
    	Assertions.assertThrows(NoCoinInsertedException.class, executeGetDrinkForCoke);
    }
    
    @Test
    public void sneakPath3() throws InvalidDrinkNumberException, InvalidDrinkException {
    	// NoCoinDrinks
    	// moneyBack()
    	
    	// NoCoinDrinks
    	machine = new VendingMachine(1,0,0);
    	Assertions.assertFalse(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	
    	// try moneyBack()
    	Executable executeMoneyBack = () -> machine.moneyBack();
    	Assertions.assertThrows(NoCoinInsertedException.class, executeMoneyBack);
    }
    
    @Test
    public void sneakPath4() throws InvalidDrinkNumberException, InvalidDrinkException {
    	// NoCoinDrinks
    	// getDrink()
    	
    	// NoCoinDrinks
    	machine = new VendingMachine(1,0,0);
    	Assertions.assertFalse(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));

    	// try getDrink
    	Executable executeGetDrinkForCoke = () -> machine.getDrink(Drink.COKE);
    	Assertions.assertThrows(NoCoinInsertedException.class, executeGetDrinkForCoke);
    }
    
    @Test
    public void sneakPath5() throws InvalidDrinkNumberException, InvalidDrinkException {
    	// NoCoinFull
    	// moneyBack()
    	
    	// NoCoinFull
    	machine = new VendingMachine(10,0,0);
    	Assertions.assertFalse(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertTrue(machine.drinkFull(Drink.COKE));
    	
    	// try moneyBack()
    	Executable executeMoneyBack = () -> machine.moneyBack();
    	Assertions.assertThrows(NoCoinInsertedException.class, executeMoneyBack);

    }
    
    @Test
    public void sneakPath6() throws InvalidDrinkNumberException, InvalidDrinkException {
    	// NoCoinFull
    	// fillWithDrink(“coke”)

    	// NoCoinFull
    	machine = new VendingMachine(10,0,0);
    	Assertions.assertFalse(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertTrue(machine.drinkFull(Drink.COKE));
    	
    	// try fillWithDrink("coke")
    	Executable executeFillWithDrink = () -> machine.fillWithDrink(Drink.COKE);
    	Assertions.assertThrows(MachineFullException.class, executeFillWithDrink);
    }
    
    @Test
    public void sneakPath7() throws InvalidDrinkNumberException, InvalidDrinkException {
    	// NoCoinFull
    	// getDrink()

    	// NoCoinFull
    	machine = new VendingMachine(10,0,0);
    	Assertions.assertFalse(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertTrue(machine.drinkFull(Drink.COKE));
    	
    	// try getDrink
    	Executable executeGetDrinkForCoke = () -> machine.getDrink(Drink.COKE);
    	Assertions.assertThrows(NoCoinInsertedException.class, executeGetDrinkForCoke);
    }
    
    @Test
    public void sneakPath8() throws CoinInsertedException, InvalidDrinkException, InvalidDrinkNumberException {
    	// CoinNoDrinks
    	// insertCoin()
    	
    	// CoinNoDrink
    	machine = new VendingMachine(0, 0, 0);
    	machine.insertCoin();
        Assertions.assertTrue(machine.coinInserted());
        Assertions.assertTrue(machine.drinkEmpty(Drink.COKE));
    	
        // try insertCoin()
        Executable executeInsertCoin = () -> machine.insertCoin();
        Assertions.assertThrows(CoinInsertedException.class, executeInsertCoin);
    }
    
    @Test
    public void sneakPath9() throws InvalidDrinkNumberException, CoinInsertedException, InvalidDrinkException {
    	// CoinNoDrinks
    	// getDrink()
    	
    	// CoinNoDrink
    	machine = new VendingMachine(0, 0, 0);
    	machine.insertCoin();
        Assertions.assertTrue(machine.coinInserted());
        Assertions.assertTrue(machine.drinkEmpty(Drink.COKE));

        // try getDrink()
        Executable executeGetDrinkForCoke = () -> machine.getDrink(Drink.COKE);
    	Assertions.assertThrows(MachineEmptyException.class, executeGetDrinkForCoke);
    }
    
    @Test
    public void sneakPath10() throws InvalidDrinkNumberException, InvalidDrinkException, CoinInsertedException {
    	// CoinDrinks
    	// insertCoin()
    	
    	// CoinDrinks
        machine = new VendingMachine(5, 0, 0);
        machine.insertCoin();
        Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
        Assertions.assertTrue(machine.coinInserted());

        // try insertCoin()
        Executable executeInsertCoin = () -> machine.insertCoin();
        Assertions.assertThrows(CoinInsertedException.class, executeInsertCoin);
    }
    
    @Test
    public void sneakPath11() throws CoinInsertedException, InvalidDrinkException, InvalidDrinkNumberException {
    	// CoinFull
    	// insertCoin()
    	
    	// CoinFull
    	machine = new VendingMachine(10, 0, 0);
    	machine.insertCoin();
    	Assertions.assertTrue(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertTrue(machine.drinkFull(Drink.COKE));

    	// try insertCoin()
        Executable executeInsertCoin = () -> machine.insertCoin();
        Assertions.assertThrows(CoinInsertedException.class, executeInsertCoin);
    }
    
    @Test
    public void sneakPath12() throws InvalidDrinkNumberException, CoinInsertedException, InvalidDrinkException {
    	// CoinFull
    	// fillWithDrink(“coke”)
    	
    	// CoinFull
    	machine = new VendingMachine(10, 0, 0);
    	machine.insertCoin();
    	Assertions.assertTrue(machine.coinInserted());
    	Assertions.assertFalse(machine.drinkEmpty(Drink.COKE));
    	Assertions.assertTrue(machine.drinkFull(Drink.COKE));

    	// try fillWithDrink("coke")
    	Executable executeFillWithDrink = () -> machine.fillWithDrink(Drink.COKE);
    	Assertions.assertThrows(MachineFullException.class, executeFillWithDrink);
    	
    }
    
//    @ParameterizedTest
//    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
//    void toUpperCase_ShouldGenerateTheExpectedUppercaseValueCSVFile(String input, String expected) {
//        String actualValue = input.toUpperCase();
//        Assertions.assertEquals(expected, actualValue);
//        Map<String, String> a = System.getenv();
//    }
    
    public Drink getDrinkType(String a) {
    	if (a.equals("coke"))
    		return Drink.COKE;
    	else if (a.equals("water"))
    		return Drink.WATER;
    	else
    		return Drink.LEMONADE;
    }
    
    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    public void robust(int coke, int lemonade, int water, String action, String drinkType, int containCoin) 
    		throws InvalidDrinkNumberException, CoinInsertedException, InvalidDrinkException, 
    		MachineFullException, NoCoinInsertedException, MachineEmptyException {
    	
    	if ( (coke <= testMax && lemonade <= testMax && water <= testMax) 
    		&& (coke >= 0 && lemonade >= 0 && water >= 0) ) {
    		machine = new VendingMachine(coke, lemonade, water);
    		if (containCoin == 1)
    			machine.insertCoin();
    	}
    	else {
    		Executable executeCreateMachine = () -> new VendingMachine(coke, lemonade, water);
    		Assertions.assertThrows(InvalidDrinkNumberException.class, executeCreateMachine);
    		return;
    	}
    	
    	Drink dType = getDrinkType(drinkType);
    	
    	if (action.equals("fillWithDrink")) {
    		machine.insertCoin();
    		
    		Boolean full = machine.drinkFull(dType);
    		if (full) {
    			Executable executeFill = () -> machine.fillWithDrink(dType);
        		Assertions.assertThrows(MachineFullException.class, executeFill);
    		}
    		else {
    			machine.fillWithDrink(dType);
    		}
    		
    		return;
    	}
    	
    	if (action.equals("insertCoin")) {
    		Boolean coinInside = machine.coinInserted();
    		if (coinInside) {
    			Executable executeInsertCoin = () -> machine.insertCoin();
    			Assertions.assertThrows(CoinInsertedException.class, executeInsertCoin);
    		}
    		else {
    			machine.insertCoin();
    		}
    		
    		return;
    	}
    	
    	if (action.equals("getDrink")) {
    		Boolean empty = machine.drinkEmpty(dType);
    		Boolean coinInside = machine.coinInserted();
    		//Boolean isSafe = (coinInside && !empty);
    		
    		if (!coinInside) {
    			Executable executeGetDrink = () -> machine.getDrink(dType);
    			Assertions.assertThrows(NoCoinInsertedException.class, executeGetDrink);
    		}
    		else {
    			if (empty) {
    				Executable executeGetDrink = () -> machine.getDrink(dType);
        			Assertions.assertThrows(MachineEmptyException.class, executeGetDrink);
    			}
    			else {
    				machine.getDrink(dType);
    			}
    			
    		}
    		return;
    	}
    	
    	if ( action.equals("moneyBack")) {
    		Boolean coinInside = machine.coinInserted();
    		if (!coinInside) {
    			Executable executeMoneyBack = () -> machine.moneyBack();
    			Assertions.assertThrows(NoCoinInsertedException.class, executeMoneyBack);
    		}
    		else {
    			machine.moneyBack();
    		}
    		
    		return;
    	}
    }
}
