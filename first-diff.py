#!/usr/bin/env python3
import os.path
import argparse
from subprocess import check_call

# TODO: -S argument for shifted ROMs

parser = argparse.ArgumentParser(
    description="find the first difference(s) between the compiled ROM and the baserom"
)
versionGroup = parser.add_mutually_exclusive_group()
versionGroup.add_argument(
    "-j",
    "--jp",
    help="use original Japanese version",
    action="store_const",
    const="jp",
    dest="version",
)
versionGroup.add_argument(
    "-u",
    "--us",
    help="use United States version",
    action="store_const",
    const="us",
    dest="version",
)
versionGroup.add_argument(
    "-e",
    "--eu",
    help="use European (PAL) version",
    action="store_const",
    const="eu",
    dest="version",
)
versionGroup.add_argument(
    "-s",
    "--sh",
    help="use Shindou (Rumble) version",
    action="store_const",
    const="sh",
    dest="version",
)
parser.add_argument(
    "-m", "--make", help="run make before finding difference(s)", action="store_true"
)
parser.add_argument(
    "-c",
    "--count",
    type=int,
    default=1,
    help="find up to this many instruction difference(s)",
)
parser.add_argument(
    "-n", "--by-name", type=str, default="", help="perform a symbol or address lookup"
)
parser.add_argument(
    "-d", "--diff", action="store_true", help="run ./diff.py on the result"
)
args = parser.parse_args()
diff_count = args.count

version = args.version

if version is None:
    version = "us"
    best = 0
    for ver in ["us", "jp", "eu", "sh"]:
        try:
            mtime = os.path.getmtime(f"build/{ver}/sm64.{ver}.z64")
            if mtime > best:
                best = mtime
                version = ver
        except Exception:
            pass
    print("Assuming version " + version)

if args.make:
    check_call(["make", "-j4", "VERSION=" + version, "COMPARE=0"])

baseimg = f"baserom.{version}.z64"
basemap = f"sm64.{version}.map"

myimg = f"build/{version}/sm64.{version}.z64"
mymap = f"build/{version}/{basemap}"

if os.path.isfile("expected/" + mymap):
    basemap = "expected/" + mymap

required_files = [baseimg, myimg, mymap]
if not os.path.isfile(baseimg):
    print(baseimg + " must exist.")
    exit(1)
if not os.path.isfile(myimg) or not os.path.isfile(mymap):
    print(
        myimg
        + " and "
        + mymap
        + " must exist. Try rerunning with --make to build them."
    )
    exit(1)

mybin = open(myimg, "rb").read()
basebin = open(baseimg, "rb").read()

if len(mybin) != len(basebin):
    print("Modified ROM has different size...")
    exit(1)

if mybin == basebin:
    print("No differences!")
    if not args.by_name:
        exit(0)


def search_map(rom_addr):
    ram_offset = None
    last_ram = 0
    last_rom = 0
    last_fn = "<start of rom>"
    last_file = "<no file>"
    prev_line = ""
    with open(mymap) as f:
        for line in f:
            if "load address" in line:
                # Example: ".boot           0x0000000004000000     0x1000 load address 0x0000000000000000"
                if "noload" in line or "noload" in prev_line:
                    ram_offset = None
                    continue
                ram = int(line[16 : 16 + 18], 0)
                rom = int(line[59 : 59 + 18], 0)
                ram_offset = ram - rom
                continue
            prev_line = line

            if (
                ram_offset is None
                or "=" in line
                or "*fill*" in line
                or " 0x" not in line
            ):
                continue
            ram = int(line[16 : 16 + 18], 0)
            rom = ram - ram_offset
            fn = line.split()[-1]
            if "0x" in fn:
                ram_offset = None
                continue
            if rom > rom_addr or (rom_addr & 0x80000000 and ram > rom_addr):
                return f"in {last_fn} (ram 0x{last_ram:08x}, rom 0x{last_rom:06x}, {last_file})"
            last_ram = ram
            last_rom = rom
            last_fn = fn
            if "/" in fn:
                last_file = fn
    return "at end of rom?"


def parse_map(fname):
    ram_offset = None
    cur_file = "<no file>"
    syms = {}
    prev_sym = None
    prev_line = ""
    with open(fname) as f:
        for line in f:
            if "load address" in line:
                if "noload" in line or "noload" in prev_line:
                    ram_offset = None
                    continue
                ram = int(line[16 : 16 + 18], 0)
                rom = int(line[59 : 59 + 18], 0)
                ram_offset = ram - rom
                continue
            prev_line = line

            if (
                ram_offset is None
                or "=" in line
                or "*fill*" in line
                or " 0x" not in line
            ):
                continue
            ram = int(line[16 : 16 + 18], 0)
            rom = ram - ram_offset
            fn = line.split()[-1]
            if "0x" in fn:
                ram_offset = None
            elif "/" in fn:
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
        print(
            f"Map appears to have shifted just before {found[0]} ({found[1]}) -- in {found[2]}?"
        )
        if found[2] is not None and found[2] not in map2:
            print()
            print(
                f"(Base map file {basemap} out of date due to renamed symbols, so result may be imprecise.)"
            )
        return True


def hexbytes(bs):
    return ":".join("{:02x}".format(c) for c in bs)


# For convenience, allow `./first-diff.py <ROM addr | RAM addr | function name>`
# to do a symbol <-> address lookup. This should really be split out into a
# separate script...
if args.by_name:
    try:
        addr = int(args.by_name, 0)
        print(args.by_name, "is", search_map(addr))
    except ValueError:
        m = parse_map(mymap)
        try:
            print(
                args.by_name,
                "is at position",
                hex(m[args.by_name][0]),
                "in ROM,",
                hex(m[args.by_name][3]),
                "in RAM",
            )
        except KeyError:
            print("function", args.by_name, "not found")
    exit()

found_instr_diff = []
map_search_diff = []
diffs = 0
shift_cap = 1000
for i in range(24, len(mybin), 4):
    # (mybin[i:i+4] != basebin[i:i+4], but that's slightly slower in CPython...)
    if diffs <= shift_cap and (
        mybin[i] != basebin[i]
        or mybin[i + 1] != basebin[i + 1]
        or mybin[i + 2] != basebin[i + 2]
        or mybin[i + 3] != basebin[i + 3]
    ):
        if diffs == 0:
            print(f"First difference at ROM addr {hex(i)}, {search_map(i)}")
            print(
                f"Bytes: {hexbytes(mybin[i : i + 4])} vs {hexbytes(basebin[i : i + 4])}"
            )
        diffs += 1
    if (
        len(found_instr_diff) < diff_count
        and mybin[i] >> 2 != basebin[i] >> 2
        and not search_map(i) in map_search_diff
    ):
        found_instr_diff.append(i)
        map_search_diff.append(search_map(i))
if diffs == 0:
    print("No differences!")
    if not args.by_name:
        exit()
definite_shift = diffs > shift_cap
if not definite_shift:
    print(str(diffs) + " differing word(s).")

if diffs > 100:
    if len(found_instr_diff) > 0:
        for i in found_instr_diff:
            print(f"Instruction difference at ROM addr {hex(i)}, {search_map(i)}")
            print(
                f"Bytes: {hexbytes(mybin[i : i + 4])} vs {hexbytes(basebin[i : i + 4])}"
            )
    if version == "sh":
        print("Shifted ROM, as expected.")
    else:
        if not os.path.isfile(basemap):
            if definite_shift:
                print("Tons of differences, must be a shifted ROM.")
            print(
                "To find ROM shifts, copy a clean .map file to "
                + basemap
                + " and rerun this script."
            )
            exit()

        if not map_diff():
            print(f"No ROM shift{' (!?)' if definite_shift else ''}")
if args.diff:
    diff_args = input("Call ./diff.py with which arguments? ") or "--"
    if diff_args[0] != "-":
        diff_args = "-" + diff_args
    if "w" in diff_args and args.make:
        diff_args += "m"  # To avoid warnings when passing -w, also pass -m as long as -m was passed to first-diff itself

    check_call(
        [
            "python3",
            "diff.py",
            f"-{version[0]}",
            diff_args,
            search_map(found_instr_diff[0]).split()[1],
        ]
    )
