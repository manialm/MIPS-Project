#!/usr/bin/env python3

code = [
    b"\x8c\x00\x00\x00", # lw $0, 0($0)
    b"\x8c\x01\x00\x00", # lw $1, 0($0)
    b"\x00\x01\x00\x27", # nor $0, $0, $1
]

def bytes_to_bin(bytes_):
    res = ''

    for byte in bytes_:
        res += bin(byte)[2:].zfill(8)

    return res.ljust(32, '0')

# print(bytes_to_bin(code[0]))

res = []
addr = 0
for line in code:
    binary = bytes_to_bin(line)
    
    for i, c in enumerate(binary):
        if c == '1':
            q, r = divmod(i, 8)
            res.append(f'mem[{addr + q}][{r}] = true;\n')

    res.append('\n')
    addr += 4

with open('res.txt', 'w') as f:
    f.writelines(res)