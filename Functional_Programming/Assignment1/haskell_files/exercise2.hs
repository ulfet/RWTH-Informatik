-- 	Ulfet CETIN - 391819
-- 	Alisafa GASIMOV - 392560
-- 	Orkhan HUSEYNOV - 374297
-- 	Shreya KAR - 392325

{-
    Exercise 2: Part A
-}

-- i) [[x],[y]] == [y]:z
x = 1
y = 1
z = [[1]]


-- ii) ([x] ++ z):y == (x:z):y
x = []
y = []
z = []
([x] ++ z):y == (x:z):y

-- iii) [[]] ++ ([x]:y) == ([x]:z)
-- If we simplify the left side of the giving equation, then we get the following:
-- 			[[]] ++ ([x]: y) = [[]] ++ [[x], ...] == [[], [x], ...]
			
-- 			If we look at the right-hand side of the giving equation and compare it with the
-- 			simplified expression, then we can easily see that first elements of the lists are
-- 			different. Because of that we can say that in any values of x, y and z such an 
-- 			assignment is not possible.
			
-- 			The simplified expression: [[], [x], ...] 
-- 			The right-hand side of the given equation: ([x], ...) == [[x], ...]
-- 			So, [[], [x], ...] can not be equal to [[x], ...].



-- iv) (x:y):z == (y ++ [x]):z
x = 5
y = []
z = []
(x:y):z == (y ++ [x]):z

{-
    Exercise 2: Part B
-}

{-
    p1) ([x]++y):ys
    t1) [[]]
    
    Lets assign x=[] empty list
				then  inside of bracket we will get [[]] in very simple case.
				In second part of this expression, achieved result inside of bracket ([[]]) should be added to the ys element which should append to the new nested list,
				so in very simple case,
				        we can get [[[]],ys element] 
				        which can not be equalised with [[]]
-}

{-
    p1) ([x]++y):ys
    t2) [[1,2],[3]]
    x = 1
    y = [2]
    ys = [[3]]

    does match
-}

{-
    p2) (x:y)++ys
    t1) [[]]

    x = []
    y = []
    ys = []

    does match
-}

{-
    p2) (x:y)++ys
    t2) [[1,2],[3]]

    x = [1,2]
    y = []
    ys = [[3]]

    does match
-}

