square :: Int -> Int
square x = x*x

pyth :: Int -> Int -> Int
pyth x y = square x + square y

isTriple :: Int -> Int -> Int -> Bool
isTriple x y z = pyth x y == square z

halfEvens :: [Int] -> [Int]
halfEvens xs = [ if (x `mod` 2 == 0) then (x `div` 2) else x | x <- xs ]

inRange :: Int -> Int -> [Int] -> [Int]
inRange a b xs = [x | x <- [a..b], x `elem` xs]

countPositives :: [Int] -> Int
countPositives xs = sum [if x > 0 then 1 else 0 | x <- xs]

capitalized :: [Char] -> [Char]
capitalized [] = []
capitalized x:xs = toUpper x : [toLower c | c <- xs]


