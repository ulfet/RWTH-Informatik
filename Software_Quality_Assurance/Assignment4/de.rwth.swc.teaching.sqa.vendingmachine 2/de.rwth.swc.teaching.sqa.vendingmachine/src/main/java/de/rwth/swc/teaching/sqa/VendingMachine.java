package de.rwth.swc.teaching.sqa;

import de.rwth.swc.teaching.sqa.exception.*;

import java.util.HashMap;

public class VendingMachine {

    protected static final int MAX = 10;
    protected HashMap<Drink, Integer> drinks;
    protected boolean coin;

    public VendingMachine(int coke, int lemonade, int water) throws InvalidDrinkNumberException {
        if (coke <= this.MAX && lemonade <= this.MAX && water <= this.MAX) {
            drinks = new HashMap<Drink, Integer>();
            drinks.put(Drink.COKE, coke);
            drinks.put(Drink.LEMONADE, lemonade);
            drinks.put(Drink.WATER, water);
            this.coin = false;
        } else {
            throw new InvalidDrinkNumberException();
        }
    }

    public void insertCoin() throws CoinInsertedException {
        if (!this.coinInserted()) {
            this.coin = true;
        } else {
            throw new CoinInsertedException();
        }
    }

    public void moneyBack() throws NoCoinInsertedException {
        if (this.coinInserted()) {
            this.coin = false;
        } else {
            throw new NoCoinInsertedException();
        }

    }

    public void fillWithDrink(Drink drinkType) throws MachineFullException, InvalidDrinkException {
        if (this.drinks.containsKey(drinkType)) {
            if (!this.drinkFull(drinkType)) {
                this.drinks.put(drinkType, drinks.get(drinkType) + 1);
            } else {
                throw new MachineFullException();
            }
        } else {
            throw new InvalidDrinkException();
        }
    }

    public void getDrink(Drink selectedDrink) throws NoCoinInsertedException, InvalidDrinkException, MachineEmptyException {
        if (this.coin) {
            if (this.drinks.containsKey(selectedDrink)) {
                if (!this.drinkEmpty(selectedDrink)) {
                    this.drinks.put(selectedDrink, this.drinks.get(selectedDrink) - 1);
                    this.coin = false;
                } else {
                    throw new MachineEmptyException();
                }
            } else {
                throw new InvalidDrinkException();
            }
        } else {
            throw new NoCoinInsertedException();
        }

    }

    public boolean coinInserted() {
        return this.coin;
    }

    public boolean drinkEmpty(Drink drinkType) throws InvalidDrinkException {
        if (this.drinks.containsKey(drinkType)) {
            return this.drinks.get(drinkType) == 0;
        } else {
            throw new InvalidDrinkException();
        }
    }

    public boolean drinkFull(Drink drinkType) throws InvalidDrinkException {
        if (drinks.containsKey(drinkType)) {
            return drinks.get(drinkType) == this.MAX;
        } else {
            throw new InvalidDrinkException();
        }
    }
}
