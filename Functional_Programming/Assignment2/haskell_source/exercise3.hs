myCount  :: (Eq a, Integral n) => a -> [a] -> n
myCount x = foldr (\cItem count -> if cItem == x then count+1 else count) 0

myLength :: (Num n) => [a] -> n
myLength = foldr (\cItem count -> count+1) 0

removeDuplicates :: Eq a => [a] -> [a]
removeDuplicates xs = foldr (\cItem accumulationList -> if (myCount cItem accumulationList) == 0 then cItem:accumulationList else accumulationList) [] xs

differentDigits :: Int -> Int
differentDigits digitInt = 
    let digitStr = show digitInt
    in myLength (removeDuplicates digitStr)