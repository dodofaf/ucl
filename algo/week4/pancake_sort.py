def pancake_sort(ar):
    n = len(ar)
    flips = []

    def flip(ind):
        for j in range(0, ind // 2 + 1):
            ar[j], ar[ind - j] = ar[ind - j], ar[j]
        print(ar)

    for curr_size in range(n, 1, -1):
        max_idx = ar.index(max(ar[:curr_size]))

        if max_idx == curr_size - 1:
            continue

        if max_idx != 0:
            flip(max_idx)
            flips.append(max_idx)

        flip(curr_size - 1)
        flips.append(curr_size - 1)

    return flips


a = [int(x) for x in input().strip().split(" ")]
indices = pancake_sort(a)
print(indices)