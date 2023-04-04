#!/usr/bin/env python

import argparse
import re
import sys

def read_file(filepath):
    with open(filepath) as f:
        lines = f.readlines()
        split_lines = [re.split(r'[ ,]+', l.strip().replace('$', '')) for l in lines]
        return split_lines

# jumps and branches with named targets
jumps = ['jal', 'j']
branches = ['beq', 'bgez', 'bgtz', 'blez', 'bltz', 'bne']
jump_branches = jumps + branches
# jumps and branches with delay slots
has_delay_slot = jump_branches + ['jr']

def decode_references(instructions):
    refs = []
    for ins in instructions:
        if ins[3] in jump_branches:
            target = int(ins[-1], 0)
            if target not in refs:
                refs.append(target)
    return refs

def reassemble(args, instructions, refs):
    print('.rsp')
    print('\n.create DATA_FILE, 0x%04X' % 0x0000)
    print('\n.close // DATA_FILE\n')
    print('.create CODE_FILE, 0x%08X\n' % args.base)
    delay_slot = False
    for ins in instructions:
        addr = int(ins[0], 0)
        if (addr & 0xFFFF) in refs:
            print('%s_%08x:' % (args.name, addr))
        sys.stdout.write(' ' * args.indent)
        if delay_slot:
            sys.stdout.write(' ')
            delay_slot = False
        if ins[3] in jumps:
            target = int(ins[-1], 0) | (args.base & 0xFFFF0000)
            ins[-1] = '%s_%08x' % (args.name, target)
        elif ins[3] in branches:
            if ins[3][-1] =='z' and ins[5] == 'zero':
                del ins[5] # remove 'zero' operand from branch
            target = (int(ins[-1], 0) & 0x1FFF) + (args.base & 0xFFFF0000)
            ins[-1] = '%s_%08x' % (args.name, target)
        elif ins[3] == 'vsar': # fixup last operand of vsar
            reg_map = {'ACC_H': 0, 'ACC_M': 1, 'ACC_L': 2}
            reg = ins[4].split(r'[')[0]
            num = reg_map[ins[-1]]
            ins[-1] = '%s[%d]' % (reg, num)
        if ins[3] in has_delay_slot:
            delay_slot = True
        if len(ins) > 4: # with args
            print('%-5s %s' % (ins[3], ', '.join(ins[4:])))
        else:
            print('%s' % ins[3])
    print('\n.close // CODE_FILE')

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('input_file', help="input assembly file generated from `rasm2 -D -e -a rsp -B -o 0x04001000 -f`")
    parser.add_argument('-b', type=int, help="base address of file", dest='base', default=0x04001000)
    parser.add_argument('-i', type=int, help="amount of indentation", dest='indent', default=4)
    parser.add_argument('-n', help="name to prefex labels with", dest='name', default='f3d')
    args = parser.parse_args()

    lines = read_file(args.input_file)
    refs = decode_references(lines)
    reassemble(args, lines, refs)

main()
