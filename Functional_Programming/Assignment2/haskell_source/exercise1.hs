-- Data Types
-- a is of type arbitrary,
--  nevertheless, we have to generate our elements using type a

-- #### Assignment 2 ####
--  part a)
data QItem a = QItem a Int deriving (Show, Eq)
data PriorityQueue a = EmptyQueue | Push (QItem a) (PriorityQueue a) deriving (Show, Eq)
-- qc = Push (QItem "c" 3) (Push (QItem "b" 2) (Push (QItem "a" 1) EmptyQueue))

qi :: PriorityQueue Int
qi = Push (QItem 103 3) (Push (QItem 102 2) (Push (QItem 101 1) EmptyQueue))

-- getters for QItem
getValue :: (QItem a) -> a
getValue (QItem value _) = value

getPriority :: (QItem a) -> Int
getPriority (QItem _ priority) = priority

-- getters for PriorityQueue
-- firstElement :: (PriorityQueue a) -> (QItem a)
-- firstElement (EmptyQueue) = error "No Item in the List"
-- firstElement (Push qItem q) = qItem

--  part b)
isWaiting :: (Eq a) => a -> (PriorityQueue a) -> Bool
isWaiting _ EmptyQueue = False
isWaiting wantedValue (Push qItem q)
    | wantedValue == (getValue qItem)  = True
    | otherwise = isWaiting wantedValue q

-- part c)
fromList :: [(a,Int)] -> (PriorityQueue a)
fromList [] = EmptyQueue
fromList ((value, priority):xs) = Push (QItem value priority) (fromList xs)

-- part d)
-- compare a QItem with the all of the elements of PriorityQueue
checkIftheBiggestPriority :: (QItem a) -> (PriorityQueue a) -> Bool
checkIftheBiggestPriority _ EmptyQueue = True
checkIftheBiggestPriority qItemIn (Push qItem q)
    | (getPriority qItemIn) < (getPriority qItem) = False
    | otherwise = checkIftheBiggestPriority qItemIn q

-- a slow function, n**2 complexity,
--      complexity of n possible 
--      (search the list for the greatest priority)
--      (then, scan for an item with that priority)
popElem :: (PriorityQueue a) -> (QItem a)
popElem EmptyQueue = error "Q should be non-empty"
popElem (Push qItem q)
    | checkIftheBiggestPriority qItem q = qItem
    | otherwise = popElem q

removeElemFromQ :: (Eq a) => (QItem a) -> (PriorityQueue a) -> (PriorityQueue a)
removeElemFromQ qItem1 EmptyQueue = EmptyQueue
removeElemFromQ qItem1 (Push qItem2 q)
    | ((getValue qItem1) == (getValue qItem2)) && ( (getPriority qItem1) == (getPriority qItem2) ) = q
    | otherwise = (Push qItem2 (removeElemFromQ qItem1 q))

pop :: (Eq a) => (PriorityQueue a) -> (a, PriorityQueue a)
-- pop q = (getValue (popElem q), removeElemFromQ (popElem q) q)
pop q = 
    let poppedQItem = popElem q
    in (getValue poppedQItem, removeElemFromQ poppedQItem q)

-- part e)
-- converts queue into a list of tuples, using the same order that queue uses
q2List :: (Eq a) => (PriorityQueue a) -> [(a, Int)]
q2List EmptyQueue = []
q2List (Push qItem q) = ( (getValue qItem), (getPriority qItem) ) : (q2List q)

-- works on tuple (a,key)
-- sorts on decreasing order (note largeOnes + middle + smallOnes)
quickSortTuple [] = []
quickSortTuple ((v1,p1):xs) = 
    (quickSortTuple largeOnes) ++ [(v1,p1)] ++ (quickSortTuple smallOnes)
    where 
    smallOnes = [(v2,p2) | (v2,p2) <- xs, p2 <= p1]
    largeOnes = [(v2,p2) | (v2,p2) <- xs, p2 > p1]

-- converts queue into a list of values,
--      sorts them based on their priorities in a decreasing order
toList :: (Eq a) => (PriorityQueue a) -> [a]
toList EmptyQueue = []
toList pQ =
    let
        serialized = quickSortTuple (q2List pQ)
    in
        [value | (value,key) <- serialized]