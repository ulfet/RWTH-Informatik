--a)
collatz :: Int -> [Int]
collatz x = (applyCollatz x): (collatz (applyCollatz x))

applyCollatz :: Int -> Int
applyCollatz x
    | (mod x 2) == 0    = (div x 2)
    | otherwise         = 3*x+1

isNot1 :: Int -> Bool
isNot1 1 = False
isNot1 _ = True

total_stopping_time :: Int -> Int
total_stopping_time x = length (takeWhile isNot1 (collatz x)) + 1

-- b)
check_collatz :: Int -> Bool
check_collatz x =
    let list = [total_stopping_time elem | elem<-[1..x]]
    in
        if (length list) /= 0
            then True
        else
            False                