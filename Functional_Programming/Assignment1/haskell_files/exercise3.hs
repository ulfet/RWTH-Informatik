-- 	Ulfet CETIN - 391819
-- 	Alisafa GASIMOV - 392560
-- 	Orkhan HUSEYNOV - 374297
-- 	Shreya KAR - 392325

{-
	a) Write a Haskell–function myrem, where myrem x y is the remainder of the integer division when dividing
    x by y. So for example, myrem 14 3 == 2. If y == 0 then myrem x 0 == x. If y < 0 then myrem x y
    == myrem x (-y).
    myrem :: Int -> Int -> Int
    You may not use any predefined functions except comparisons, +, and -.
-}

myrem :: Int -> Int -> Int
myrem x 0 = x
myrem x y
    | y < 0 = myrem x (-y)
    | x >= y = myrem (x-y) y
    | otherwise = x

{-
    Write a Haskell-function count that given a list xs and an element x returns the number of occurences
    of x in xs. E.g., count 2 [0,2,2,0,2,5,0,2] == 4 wheras count (-7) [0,2,2,0,2,5,0,2] == 0.
    count :: Int -> [ Int ] -> Int
    You may not use any predefined functions except comparisons and +.
-}

count :: Int -> [ Int ] -> Int
count _ [] = 0
count wanted (x:xs)
    | wanted == x = 1 + count wanted xs
    | otherwise = count wanted xs

{-
    c) Write a Haskell-function simplify that given a list xs returns a list of pairs as follows. The result-
    ing list contains the pair (x,n) if and only if x occurs in xs n times and n > 0. 
    E.g., simplify [0,2,2,0,2,5,0,2] == [(0,3),(2,4),(5,1)].
    simplify :: [ Int ] -> [( Int , Int )]
    You may not use any predefined functions except comparisons.
-}

-- is the first argument the smallest element of the second argument?
isTheSmallest :: Int -> [Int] -> Bool
isTheSmallest _ [] = True
isTheSmallest elem (x:xs)
    | elem <= x = isTheSmallest elem xs
    | otherwise = False

-- my personal sort function to be used as helper
mySort :: [Int] -> [Int]
mySort [] = []
mySort (x:xs)
    | isTheSmallest x xs = x:(mySort xs)
    | otherwise = mySort (xs ++ [x])

-- helper function(s) for part c
-- finds whether element is in the list
isIn :: Int -> [Int] -> Bool
isIn _ [] = False
isIn elem (x:xs)
    | elem == x = True
    | otherwise = isIn elem xs

-- find unique elements of a list
-- second argument should start as empty list, i.e. []
findUniquesHelper:: [Int] -> [Int] -> [Int]
findUniquesHelper [] resultList = resultList
findUniquesHelper (x:xs) resultList
    | isIn x resultList = findUniquesHelper xs resultList
    | otherwise = findUniquesHelper xs (resultList ++ [x])

-- main findUniques function making use of the helper function above
findUniques :: [Int] -> [Int]
findUniques list = mySort ( findUniquesHelper list [] )

-- counts how many times an element appears in a list
countSingleElement :: Int -> [Int] -> Int
countSingleElement _ [] = 0
countSingleElement elem (x:xs)
    | elem == x = 1 + countSingleElement elem xs
    | otherwise = countSingleElement elem xs

-- first argument should be the unique elements of the second list
simplifyHelper :: [Int] -> [Int] -> [(Int, Int)]
simplifyHelper [] _ = []
simplifyHelper (unique:uList) targetList = 
    [(unique, countSingleElement unique targetList)] ++ simplifyHelper uList targetList

-- counts which element appeared how many times in the list
simplify :: [ Int ] -> [( Int , Int )]
simplify [] = []
simplify list = simplifyHelper (findUniques list) list


{-
    d) Write a Haskell-function multUnion that given two lists of pairs xs and ys concatenates these lists where
    each “multiple occurence” is simplified as follows: If xs contains a pair (x,n) and ys contains (x,m),
    then the result contains (x,n+m). You may assume that in both xs and ys an integer occurs at most once
    as first entry of a pair. Moreover, assume that the lists are sorted in ascending order w.r.t. the first entry
    of the pair. Make sure that the resulting list is sorted in ascending order w.r.t. the first entry of the pair
    as well. E.g., multUnion [(0,3),(2,4),(5,1)] [(-1,1),(0,4)] == [(-1,1),(0,7),(2,4),(5,1)].
    multUnion :: [( Int , Int )] -> [( Int , Int )] -> [( Int , Int )]
    You may not use any predefined functions except comparisons and +.

    findUniquesTupleVersion (0,3) [(-1,1),(0,7),(2,4),(5,1)]
-}

-- get all unique elements from that two list
findUniquesTupleVersionHelper :: [(Int, Int)] -> [Int] -> [Int]
findUniquesTupleVersionHelper [] resultList = resultList
findUniquesTupleVersionHelper ( (key,count):list ) resultList
    | isIn key resultList = findUniquesTupleVersionHelper list resultList
    | otherwise = findUniquesTupleVersionHelper list (key:resultList)

-- takes two lists as an argument and finds the unique keys
findUniquesTupleVersion :: [(Int, Int)] -> [(Int, Int)] -> [Int]
findUniquesTupleVersion list1 list2 = mySort (findUniquesTupleVersionHelper (list1 ++ list2) [] )

-- write a retriever from the lists
countRetriever :: Int -> [(Int, Int)] -> Int
countRetriever key [] = 0
countRetriever key ((key1, count1):xs)
    | key == key1 = count1
    | otherwise = countRetriever key xs

multUnionHelper :: [Int] -> [(Int, Int)] -> [(Int, Int)] -> [(Int, Int)]
multUnionHelper [] _ _ = []
multUnionHelper (u:uniqueList) list1 list2 =
    let summed = ( countRetriever u list1 ) + ( countRetriever u list2 )
    in ( (u,summed):(multUnionHelper uniqueList list1 list2) )

multUnion :: [(Int, Int)] -> [(Int, Int)] -> [(Int, Int)]
multUnion list1 list2 = multUnionHelper (findUniquesTupleVersion list1 list2) list1 list2