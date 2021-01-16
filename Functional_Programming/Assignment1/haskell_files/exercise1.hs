{-
	Exercise 1: Part A
	(part B below)
-}

-- counts how many of the the two inputs is true
f1 :: Bool -> Bool -> Int
f1 True True = 2
f1 True False = 1
f1 False True = 1
f1 _ _ = 0

-- assumes that the first and the second argument is of same size
-- check, in how many of elements of the first argument,
--      the truth value of condition (greater than zero, in our case)
--          is the same as the respective element of second argument
f2 :: [Int] -> [Bool] -> Int
f2 [] [] = 0
f2 (x:xs) (y:ys)
    | (x > 0) == y = 1 + (f2 xs ys)
    | otherwise = 0 + (f2 xs ys)


-- maps each bool value in first argument using the second argument
-- second argument should be a function that takes as input a bool and produces an int
--      an example for second argument could be a voltageMapper function given below
-- example usage:
--      f3 [True,False,True,True,False] voltageMapper
-- answer:
--      [5,0,5,5,0]
f3 :: [Bool] -> (Bool -> Int) -> [Int]
f3 [] mapFunc = []
f3 (x:xs) mapFunc = (mapFunc x):(f3 xs mapFunc)

-- maps voltages into the values that system accepts
-- for computer systems, it is either 5V or 12V, we preferred 12V
voltageMapper :: Bool -> Int
voltageMapper True = 5
voltageMapper False = 0


{-
	Exercise 1: Part B
	Suppose that f has the type Bool -> [Int] -> Int.
	What is the type of nx y -> f ((f True x)>0) [y]?
-}

-- \x y -> f ((f True x)>0) [y]?
--            (f True x) evaluates to Int
--            (Int > 0) evaluates to Bool
--         f Bool [Int] evaluates to Int, as definition of f shows