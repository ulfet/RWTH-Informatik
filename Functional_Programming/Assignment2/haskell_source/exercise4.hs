data Polynomial a = Coeff a Int (Polynomial a)| Null deriving Show

q :: Polynomial Int
q = Coeff 4 3 (Coeff 2 1 (Coeff 5 0 Null))

-- ######## working foldPoly
foldPoly :: (Num b) =>(a -> Int -> b -> b) -> b -> Polynomial a -> b
foldPoly f e Null = e
foldPoly f e (Coeff coeff power rest) = (f coeff power 0) + (foldPoly f e rest)

--	######## new foldPoly
-- foldPoly :: (a -> Int -> b -> b) -> b -> Polynomial a -> b
-- myFold :: Polynomial a -> Int
-- myFold xs = foldr (\cItem accumulator -> accumulator+1) 0 xs 

-- f = (\c n m -> c *3^n + m)

degree :: Polynomial Int -> Int
degree Null = minBound :: Int
degree poly = foldPoly (\c n m -> if n>m then n else m) 0 poly