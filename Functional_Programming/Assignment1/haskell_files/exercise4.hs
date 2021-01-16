-- 	Ulfet CETIN - 391819
-- 	Alisafa GASIMOV - 392560
-- 	Orkhan HUSEYNOV - 374297
-- 	Shreya KAR - 392325

-- equivalent of ^ operator, user defined for bonus points
myPowOp :: Int -> Int -> Int
myPowOp _ 0 = 1
myPowOp a b = a * (myPowOp a (b-1))

{-

    The function call xs ^^^ ys evaluates to xs to the power of ys interpreted as vectors, where the negative
    entries of ys are ignored. In other words, [x1, x2, ..., xn] ^^^ [y1, y2, ..., yn] == x1 ^ y1
    * x2 ^ y2 * ... * xn ^ yn.

    For example:
        [1, 4, 5] ^^^ [7, 2, 3] evaluates to 1 ^ 7 * 4 ^ 2 * 5 ^ 3 == 2000 
        [1, 4, 5] ^^^ [5, -1, 0] evaluates to 1 ^ 5 * 5 ^ 0 == 1.
    
    • xs ^^^ ys * z, where xs and ys have type [Int] and z has type Int, is a valid expression.
    (^^^) :: [Int] -> [Int] -> Int

    
-}

(^^^) :: [Int] -> [Int] -> Int
[] ^^^ _ = 1
_ ^^^ [] = 1
(x:xs) ^^^ (y:ys)
    | y <0 = xs ^^^ ys
    | otherwise = (myPowOp x y) * (xs ^^^ ys)

infixl 9 ^^^

