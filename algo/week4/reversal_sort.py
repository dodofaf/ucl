import random


def reversal_sort_v1(ar):
    n = len(ar)
    reversals = []
    print(ar)

    def flip(s, t):
        for i in range((t-s)//2+1):
            ar[s+i], ar[t-i] = ar[t-i], ar[s+i]
        reversals.append([s, t])
        print(ar)

    for i in range(n):
        ind = ar.index(min(ar[i:]))

        if ind == i:
            continue

        flip(i, ind)

    return reversals


def reversal_sort_v2(ar):
    n = len(ar)
    reversals = []
    print(ar)

    def flip(s, t):
        for i in range((t-s)//2+1):
            ar[s+i], ar[t-i] = ar[t-i], ar[s+i]
        reversals.append([s, t])
        print(ar)

    breaks = []

    for i in range(1,n):
        if abs(ar[i]-ar[i-1]) != 1:
            breaks.append(i-1)

    while breaks:
        if len(breaks) == 1:
            mn = min(ar)
            mx = max(ar)
            if ar[0] == mx or ar[breaks[0]] == mx:
                flip(0, n-1)
            if not ar[0] == mn:
                flip(0, breaks[0])
            if not ar[n-1] == mx:
                flip(breaks[0], n-1)
            break

        flag = False
        for i in range(len(breaks)):
            for j in range(i+1, len(breaks)):
                if abs(ar[breaks[i]+1]-ar[breaks[j]+1]) == 1 and abs(ar[breaks[i]] - ar[breaks[j]]) == 1:
                    flip(breaks[i]+1, breaks[j])
                    del breaks[i]
                    del breaks[j-1]
                    flag = True
                    break
            if flag:
                break
        if flag:
            continue

        flag = False
        for i in range(len(breaks)):
            for j in range(i + 1, len(breaks)):
                if abs(ar[breaks[i]+1]-ar[breaks[j]+1]) == 1 or abs(ar[breaks[i]] - ar[breaks[j]]) == 1:
                    flip(breaks[i]+1, breaks[j])
                    if abs(ar[breaks[i]] - ar[breaks[i]+1]) == 1:
                        del breaks[i]
                    elif abs(ar[breaks[j]] - ar[breaks[j]+1]) == 1:
                        del breaks[j]
                    flag = True
                    break
            if flag:
                break
        if flag:
            continue

        x = random.randint(0, len(breaks)-2)
        y = random.randint(x+1, len(breaks)-1)
        flip(breaks[x]+1, breaks[y])




    return reversals


a = [int(x) for x in input().strip().split(" ")]
reverses = reversal_sort_v2(a)
print(reverses)

# 1 | 5 6 7 | 2 3 4 | 8 9 10
