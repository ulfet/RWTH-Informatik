-- a)

data List a = Nil | Cons a (List a) deriving Show

instance Eq a => Eq (List a) where 
 (Cons x y) == (Cons m n) = x==m && y==n
 Nil == Nil = True
 _ == _ = False
 
-- b)

class Eq a => Mono a where
 binOp :: a -> a -> a
 one :: a
 pow :: Word -> a -> a
 pow 0 x = one
 pow n x = binOp x (pow (n-1) x)

-- c)
 
instance Mono Integer where
 one = 1
 binOp a b = a*b
 
instance Eq a => Mono (List a) where
 one = Nil
 binOp Nil a = a
 binOp (Cons a b) c = Cons a (binOp b c)

-- d)

-- [(3,Cons 'a' Nil),(1,Cons 'b' Nil),(2,Cons 'c' (Cons 'd' Nil))] ==
-- Cons 'a' (Cons 'a' (Cons 'a' (Cons 'b' (Cons 'c' (Cons 'd' (Cons 'c' (Cons 'd' Nil)))))))