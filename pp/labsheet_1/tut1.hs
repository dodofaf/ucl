eff :: [Int] -> Int
eff [] = 0
eff (x:_) = x+1

eff2 :: [Int] -> Int
eff2 [] = 0
eff2 (x:[]) = x
eff2 (x:y:_) = x+y

eff' :: [Int] -> Int
eff' xs = head (xs++[-1]) + 1

eff2' :: [Int] -> Int
eff2' xs = head xs2 + head (tail xs2)
    where xs2 = xs++[0,0]

firstDigit' :: [Char] -> [Char]
firstDigit' xs = if xs2 == [] then [] else [head xs2]
    where xs2 = [x | x<-xs, elem x ['0'..'9']]

exOr :: Bool -> Bool -> Bool
exOr x y = (x || y) && not (x && y)

elemNum :: Eq a => a -> [a] -> Integer
elemNum x [] = 0
elemNum x (y:ys) = (if x==y then 1 else 0) + elemNum x ys 

unique :: Eq a => [a] -> [a]
unique xs = [x | x <- xs, (elemNum x xs) == 1]

