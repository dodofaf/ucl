def next_pos(i, a):
    n = len(a)
    i += 1
    while i < n and a[i] < 0:
        i += 1
    return i


def next_neg(i, a):
    n = len(a)
    i += 1
    while i < n and a[i] > 0:
        i += 1
    return i


a = [int(x) for x in input().strip().split(" ")]
n = len(a)

pos = next_pos(-1, a)
neg = next_neg(-1, a)

for i in range(n):
    if i%2 == 0:
        if neg == i:
            a[pos], a[neg] = a[neg], a[pos]
            neg = next_neg(neg, a)
        pos = next_pos(pos, a)
    else:
        if pos == i:
            a[pos], a[neg] = a[neg], a[pos]
            pos = next_pos(pos, a)
        neg = next_neg(neg, a)
    if pos == n or neg == n:
        break

print(a)
