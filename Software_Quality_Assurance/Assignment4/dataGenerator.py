values = [-1,0,1,5,9,10,11]
actions = ["fillWithDrink", "insertCoin", "moneyBack", "getDrink"]
drinks = ["coke", "lemonade", "water"]
containCoin = [0,1]
for i in values:
    for j in values:
        for k in values:
            for a in actions:
                for d in drinks:
                    for c in containCoin:
                        print( str(i)  + "," + str(j) + "," + str(k) + "," + a + "," + d + "," + str(c))