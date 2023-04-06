#!/usr/bin/env python3
import sys
import os.path

lang = None

# TODO: -S argument for shifted ROMs
args = []
for arg in sys.argv[1:]:
    if arg == '-j':
        lang = 'jp'
    elif arg == '-u':
        lang = 'us'
    elif arg == '-e':
        lang = 'eu'
    elif arg == '-s':
        lang = 'sh'
    else:
        args.append(arg)

if lang is None:
    lang = 'us'
    best = 0
    for path in ['build/us/sm64.us.z64', 'build/jp/sm64.jp.z64', 'build/eu/sm64.eu.z64', 'build/sh/sm64.sh.z64']:
        try:
            mtime = os.path.getmtime(path)
            if mtime > best:
                best = mtime
                lang = path.split('/')[1]
        except Exception:
            pass
    print("Assuming language " + lang)

baseimg = 'baserom.' + lang + '.z64'
basemap = 'sm64.' + lang + '.map'

myimg = 'build/' + lang + '/sm64.' + lang + '.z64'
mymap = 'build/' + lang + '/sm64.' + lang + '.map'

if os.path.isfile('expected/' + mymap):
    basemap = 'expected/' + mymap

required_files = [baseimg, myimg, mymap]
if any(not os.path.isfile(path) for path in required_files):
    print(', '.join(required_files[:-1]) + " and " + required_files[-1] + " must exist.")
    exit(1)

mybin = open(myimg, 'rb').read()
basebin = open(baseimg, 'rb').read()

if len(mybin) != len(basebin):
    print("Modified ROM has different size...")
    exit(1)

if mybin == basebin:
    print("No differences!")
    exit(0)

def search_map(rom_addr):
    ram_offset = None
    last_ram = 0
    last_rom = 0
    last_fn = '<start of rom>'
    last_file = '<no file>'
    prev_line = ''
    with open(mymap) as f:
        for line in f:
            if 'load address' in line:
                # Example: ".boot           0x0000000004000000     0x1000 load address 0x0000000000000000"
                if 'noload' in line or 'noload' in prev_line:
                    ram_offset = None
                    continue
                ram = int(line[16:16+18], 0)
                rom = int(line[59:59+18], 0)
                ram_offset = ram - rom
                continue
            prev_line = line

            if ram_offset is None or '=' in line or '*fill*' in line or ' 0x' not in line:
                continue
            ram = int(line[16:16+18], 0)
            rom = ram - ram_offset
            fn = line.split()[-1]
            if '0x' in fn:
                ram_offset = None
                continue
            if rom > rom_addr or (rom_addr & 0x80000000 and ram > rom_addr):
                return 'in {} (ram 0x{:08x}, rom 0x{:x}, {})'.format(last_fn, last_ram, last_rom, last_file)
            last_ram = ram
            last_rom = rom
            last_fn = fn
            if '/' in fn:
                last_file = fn
    return 'at end of rom?'

def parse_map(fname):
    ram_offset = None
    cur_file = '<no file>'
    syms = {}
    prev_sym = None
    prev_line = ''
    with open(fname) as f:
        for line in f:
            if 'load address' in line:
                if 'noload' in line or 'noload' in prev_line:
                    ram_offset = None
                    continue
                ram = int(line[16:16+18], 0)
                rom = int(line[59:59+18], 0)
                ram_offset = ram - rom
                continue
            prev_line = line

            if ram_offset is None or '=' in line or '*fill*' in line or ' 0x' not in line:
                continue
            ram = int(line[16:16+18], 0)
            rom = ram - ram_offset
            fn = line.split()[-1]
            if '0x' in fn:
                ram_offset = None
            elif '/' in fn:
                cur_file = fn
            else:
                syms[fn] = (rom, cur_file, prev_sym, ram)
                prev_sym = fn
    return syms

def map_diff():
    map1 = parse_map(mymap)
    map2 = parse_map(basemap)
    min_ram = None
    found = None
    for sym, addr in map1.items():
        if sym not in map2:
            continue
        if addr[0] != map2[sym][0]:
            if min_ram is None or addr[0] < min_ram:
                min_ram = addr[0]
                found = (sym, addr[1], addr[2])
    if min_ram is None:
        return False
    else:
        print()
        print("Map appears to have shifted just before {} ({}) -- in {}?".format(found[0], found[1], found[2]))
        if found[2] is not None and found[2] not in map2:
            print()
            print("(Base map file {} out of date due to renamed symbols, so result may be imprecise.)".format(basemap))
        return True

def hexbytes(bs):
    return ":".join("{:02x}".format(c) for c in bs)

# For convenience, allow `./first-diff.py <ROM addr | RAM addr | function name>`
# to do a symbol <-> address lookup. This should really be split out into a
# separate script...
if args:
    try:
        addr = int(args[0], 0)
        print(args[0], "is", search_map(addr))
    except ValueError:
        m = parse_map(mymap)
        try:
            print(args[0], "is at position", hex(m[args[0]][0]), "in ROM,", hex(m[args[0]][3]), "in RAM")
        except KeyError:
            print("function", args[0], "not found")
    exit()

found_instr_diff = None
diffs = 0
shift_cap = 1000
for i in range(24, len(mybin), 4):
    # (mybin[i:i+4] != basebin[i:i+4], but that's slightly slower in CPython...)
    if diffs <= shift_cap and (mybin[i] != basebin[i] or mybin[i+1] != basebin[i+1] or mybin[i+2] != basebin[i+2] or mybin[i+3] != basebin[i+3]):
        if diffs == 0:
            print("First difference at ROM addr " + hex(i) + ", " + search_map(i))
            print("Bytes:", hexbytes(mybin[i:i+4]), 'vs', hexbytes(basebin[i:i+4]))
        diffs += 1
    if found_instr_diff is None and mybin[i] >> 2 != basebin[i] >> 2:
        found_instr_diff = i
if diffs == 0:
    print("No differences!")
    exit()
definite_shift = (diffs > shift_cap)
if not definite_shift:
    print(str(diffs) + " differing word(s).")

if diffs > 100:
    if found_instr_diff is not None:
        i = found_instr_diff
        print("First instruction difference at ROM addr " + hex(i) + ", " + search_map(i))
        print("Bytes:", hexbytes(mybin[i:i+4]), 'vs', hexbytes(basebin[i:i+4]))
    if lang == 'sh':
        print("Shifted ROM, as expected.")
    else:
        if not os.path.isfile(basemap):
            if definite_shift:
                print("Tons of differences, must be a shifted ROM.")
            print("To find ROM shifts, copy a clean .map file to " + basemap + " and rerun this script.")
            exit()

        if not map_diff():
            print("No ROM shift{}.".format(" (!?)" if definite_shift else ""))
