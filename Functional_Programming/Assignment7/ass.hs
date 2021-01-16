c :: (Int, Int)
c = c

d :: (Int, Int)
d = let bot=bot in (bot, bot)

f :: (Int, Int) -> Int
f (x,y) = 0

g :: (Int, Int) -> Int
g z = 0