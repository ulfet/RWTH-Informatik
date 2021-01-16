--a)
drop_mult :: Int -> [Int] -> [Int]
drop_mult x xs = [y | y <- xs , y `mod` x /= 0]

dropall :: [Int] -> [ Int]
dropall (x:xs) = x : dropall ( drop_mult x xs)

primes :: [Int ]
primes = dropall [2 ..]

goldbach :: Int -> [(Int, Int)]
goldbach target = [ (x,(target-x)) | x<-(takeWhile (\elem -> (elem <= target) ) primes ), elem (target-x) (takeWhile (\elem -> (elem <= target) ) primes ), x<=(target-x)]

--b)
range :: [a] -> Int -> Int -> [a]
range list startIndex endIndex = [list!!x | x<-[startIndex..endIndex], x>=0, x<=((length list) -1)]