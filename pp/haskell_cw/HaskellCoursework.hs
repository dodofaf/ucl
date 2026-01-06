getNum :: IO Int
getNum = do
    input <- getLine
    return (read input)

printILoveYou :: Int -> IO ()
printILoveYou 0 = putStr ""
printILoveYou x = do 
	putStr "I Love You\n"
	printILoveYou (x-1)

iLoveYou :: IO ()
iLoveYou = do 
	x <- getNum
	if x > 0 then printILoveYou x
	else return ()

--------------------------------------------------------------

data STree = Leaf String | Node STree String STree deriving Show

tDepth :: STree -> Int
tDepth (Leaf _) = 1
tDepth (Node l _ r) = 1 + max (tDepth l) (tDepth r)

tMirror :: STree -> STree
tMirror (Leaf s) = (Leaf s)
tMirror (Node l s r) = (Node (tMirror r) s (tMirror l))

{- Proof that tDepth . tMirror = tDepth:
 - To prove this equality we need to prove that for every (STree x): tDepth (tMirror x) = tDepth x
 - We'll use strong induction over the depth of x to prove this
 - Base:
 -     Let x be a tree of a depth 1, therefore x is a (Leaf s) for some s, thus
 -     tDepth (tMirror x) = 
 -     tDepth (tMirror (Leaf s)) = | by definition of tMirror
 -     tDepth (Leaf s) = 
 -     tDepth x
 - Iduction step:
 -     Suppose for every tree of depth less or equal to n the equality holds.
 -     Now, let x = (Node l s r) be a tree of a depth (n+1) for some l,s, and r, then
 -     depth of l and r is less or equal to n, thus  
 -     tDepth (tMirror x) =
 -     tDepth (tMirror (Node l s r)) = | by definition of tMirror
 -     tDepth (Node (tMirror r) s (tMirror l)) = | by definition of tDepth
 -     1 + max (tDepth (tMirror r)) (tDepth (tMirror l)) = | by induction hypothesis, since depths of l and r are less or equal to n
 -     1 + max (tDepth r) (tDepth l) = | since max is commutative
 -     1 + max (tDepth l) (tDepth r) = | by definition of tDepth
 -     tDepth (Node l s r) = 
 -     tDepth x
 -     QED
 -}

--------------------------------------------------------------

trib :: Integral a => a -> a
trib 0 = 0
trib 1 = 0
trib 2 = 1
trib x = trib (x-1) + trib (x-2) + trib (x-3)

{- Proof of correctness by strong induction:
 - We'll denote n-th Tribonacci number as T(n).
 - Base case:
 -     n = 0:
 -         trib 0 = 0
 -         T(0) = 0
 -     n = 1:
 -         trib 1 = 0
 -         T(1) = 0
 -     n = 2:
 -         trib 2 = 1
 -         T(2) = 1
 - Induction step:
 -     Assume trib k = T(k) for every 0 <= k < n, then
 -     trib n = | by definition of trib
 -     trib (n-1) + trib (n-2) + trib (n-3) = | by induction hypothesis, since (n-1),(n-2),(n-3) are strictly less then n
 -     T(n-1) + T(n-2) + T(n-3) = | by definition of Tribonacci numbers
 -     T(n)
 -     QED
 -}

trib2 :: Integral a => a -> a
trib2 x = tribTail x 0 0 1

tribTail :: Integral a => a -> a -> a -> a -> a
tribTail 0 x _ _ = x
tribTail n a b c = tribTail (n-1) b c (a+b+c)

{- Proof of correctness:
 - Firsly, we'll prove that for every non-negative integers n and k tribTail n T(k) T(k+1) T(k+2) = T(n+k).
 - We'll do so by induction over n.
 - Base case:
 -     n = 0:
 -         tribTail 0 T(k) T(k+1) T(k+2) = T(k) for every k >= 0 by defintion of tribTail
 - Induction step:
 -     Assume tribTail n T(k) T(k+1) T(k+2) = T(n+k) for every k >= 0, then
 -     tribTail (n+1) T(k) T(k+1) T(k+2) = | by definition of tribTail
 -     tribTail n T(k+1) T(k+2) (T(k) + T(k+1) + T(k+2)) = | by definition of Tribonacci numbers
 -     tribTail n T(k+1) T(k+2) T(k+3) = 
 -     tribTail n T(k+1) T((k+1)+1) T((k+1)+2) = | by induction hypothesis
 -     T(n+(k+1)) = T((n+1)+k)
 -     QED
 - Now, trib2 n = tribTail n 0 0 1 = tribTail n T(0) T(1) T(2) = T(n+0) = T(n)
 - QED
 -}

{- C while loop implementation:
 - #include <stdio.h>
 -
 - int main()
 - {
 -     int n;
 -     scanf("%d", n);
 -     int current = 0;
 -     int next = 0;
 -     int next2 = 1;
 -     while (n != 0) {
 -         int temp = current+next+next2;
 -         current = next;
 -         next = next2;
 -         next2 = temp;
 -     }
 -     printf("%d\n", current);
 - }
 -}
