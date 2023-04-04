#!/usr/bin/env python3
# PYTHON_ARGCOMPLETE_OK
import argparse
import sys
from typing import (
    Any,
    Dict,
    List,
    Match,
    NamedTuple,
    NoReturn,
    Optional,
    Set,
    Tuple,
    Union,
    Callable,
    Pattern,
)


def fail(msg: str) -> NoReturn:
    print(msg, file=sys.stderr)
    sys.exit(1)


# Prefer to use diff_settings.py from the current working directory
sys.path.insert(0, ".")
try:
    import diff_settings
except ModuleNotFoundError:
    fail("Unable to find diff_settings.py in the same directory.")
sys.path.pop(0)

# ==== COMMAND-LINE ====

try:
    import argcomplete  # type: ignore
except ModuleNotFoundError:
    argcomplete = None

parser = argparse.ArgumentParser(description="Diff MIPS or AArch64 assembly.")

start_argument = parser.add_argument(
    "start",
    help="Function name or address to start diffing from.",
)

if argcomplete:

    def complete_symbol(
        prefix: str, parsed_args: argparse.Namespace, **kwargs: object
    ) -> List[str]:
        if not prefix or prefix.startswith("-"):
            # skip reading the map file, which would
            # result in a lot of useless completions
            return []
        config: Dict[str, Any] = {}
        diff_settings.apply(config, parsed_args)  # type: ignore
        mapfile = config.get("mapfile")
        if not mapfile:
            return []
        completes = []
        with open(mapfile) as f:
            data = f.read()
            # assume symbols are prefixed by a space character
            search = f" {prefix}"
            pos = data.find(search)
            while pos != -1:
                # skip the space character in the search string
                pos += 1
                # assume symbols are suffixed by either a space
                # character or a (unix-style) line return
                spacePos = data.find(" ", pos)
                lineReturnPos = data.find("\n", pos)
                if lineReturnPos == -1:
                    endPos = spacePos
                elif spacePos == -1:
                    endPos = lineReturnPos
                else:
                    endPos = min(spacePos, lineReturnPos)
                if endPos == -1:
                    match = data[pos:]
                    pos = -1
                else:
                    match = data[pos:endPos]
                    pos = data.find(search, endPos)
                completes.append(match)
        return completes

    setattr(start_argument, "completer", complete_symbol)

parser.add_argument(
    "end",
    nargs="?",
    help="Address to end diff at.",
)
parser.add_argument(
    "-o",
    dest="diff_obj",
    action="store_true",
    help="Diff .o files rather than a whole binary. This makes it possible to "
    "see symbol names. (Recommended)",
)
parser.add_argument(
    "--elf",
    dest="diff_elf_symbol",
    metavar="SYMBOL",
    help="Diff a given function in two ELFs, one being stripped and the other "
    "one non-stripped. Requires objdump from binutils 2.33+.",
)
parser.add_argument(
    "--source",
    action="store_true",
    help="Show source code (if possible). Only works with -o and -e.",
)
parser.add_argument(
    "--inlines",
    action="store_true",
    help="Show inline function calls (if possible). Only works with -o and -e.",
)
parser.add_argument(
    "--base-asm",
    dest="base_asm",
    metavar="FILE",
    help="Read assembly from given file instead of configured base img.",
)
parser.add_argument(
    "--write-asm",
    dest="write_asm",
    metavar="FILE",
    help="Write the current assembly output to file, e.g. for use with --base-asm.",
)
parser.add_argument(
    "-m",
    "--make",
    dest="make",
    action="store_true",
    help="Automatically run 'make' on the .o file or binary before diffing.",
)
parser.add_argument(
    "-l",
    "--skip-lines",
    dest="skip_lines",
    type=int,
    default=0,
    metavar="LINES",
    help="Skip the first N lines of output.",
)
parser.add_argument(
    "-f",
    "--stop-jr-ra",
    dest="stop_jrra",
    action="store_true",
    help="Stop disassembling at the first 'jr ra'. Some functions have multiple return points, so use with care!",
)
parser.add_argument(
    "-i",
    "--ignore-large-imms",
    dest="ignore_large_imms",
    action="store_true",
    help="Pretend all large enough immediates are the same.",
)
parser.add_argument(
    "-I",
    "--ignore-addr-diffs",
    action="store_true",
    help="Ignore address differences. Currently only affects AArch64.",
)
parser.add_argument(
    "-B",
    "--no-show-branches",
    dest="show_branches",
    action="store_false",
    help="Don't visualize branches/branch targets.",
)
parser.add_argument(
    "-S",
    "--base-shift",
    dest="base_shift",
    type=str,
    default="0",
    help="Diff position X in our img against position X + shift in the base img. "
    'Arithmetic is allowed, so e.g. |-S "0x1234 - 0x4321"| is a reasonable '
    "flag to pass if it is known that position 0x1234 in the base img syncs "
    "up with position 0x4321 in our img. Not supported together with -o.",
)
parser.add_argument(
    "-w",
    "--watch",
    dest="watch",
    action="store_true",
    help="Automatically update when source/object files change. "
    "Recommended in combination with -m.",
)
parser.add_argument(
    "-3",
    "--threeway=prev",
    dest="threeway",
    action="store_const",
    const="prev",
    help="Show a three-way diff between target asm, current asm, and asm "
    "prior to -w rebuild. Requires -w.",
)
parser.add_argument(
    "-b",
    "--threeway=base",
    dest="threeway",
    action="store_const",
    const="base",
    help="Show a three-way diff between target asm, current asm, and asm "
    "when diff.py was started. Requires -w.",
)
parser.add_argument(
    "--width",
    dest="column_width",
    type=int,
    default=50,
    help="Sets the width of the left and right view column.",
)
parser.add_argument(
    "--algorithm",
    dest="algorithm",
    default="levenshtein",
    choices=["levenshtein", "difflib"],
    help="Diff algorithm to use. Levenshtein gives the minimum diff, while difflib "
    "aims for long sections of equal opcodes. Defaults to %(default)s.",
)
parser.add_argument(
    "--max-size",
    "--max-lines",
    dest="max_lines",
    type=int,
    default=1024,
    help="The maximum length of the diff, in lines.",
)

# Project-specific flags, e.g. different versions/make arguments.
add_custom_arguments_fn = getattr(diff_settings, "add_custom_arguments", None)
if add_custom_arguments_fn:
    add_custom_arguments_fn(parser)

if argcomplete:
    argcomplete.autocomplete(parser)

# ==== IMPORTS ====

# (We do imports late to optimize auto-complete performance.)

import re
import os
import ast
import subprocess
import difflib
import string
import itertools
import threading
import queue
import time


MISSING_PREREQUISITES = (
    "Missing prerequisite python module {}. "
    "Run `python3 -m pip install --user colorama ansiwrap watchdog python-Levenshtein cxxfilt` to install prerequisites (cxxfilt only needed with --source)."
)

try:
    from colorama import Fore, Style, Back  # type: ignore
    import ansiwrap  # type: ignore
    import watchdog  # type: ignore
except ModuleNotFoundError as e:
    fail(MISSING_PREREQUISITES.format(e.name))

# ==== CONFIG ====

args = parser.parse_args()

# Set imgs, map file and make flags in a project-specific manner.
config: Dict[str, Any] = {}
diff_settings.apply(config, args)  # type: ignore

arch: str = config.get("arch", "mips")
baseimg: Optional[str] = config.get("baseimg")
myimg: Optional[str] = config.get("myimg")
mapfile: Optional[str] = config.get("mapfile")
makeflags: List[str] = config.get("makeflags", [])
source_directories: Optional[List[str]] = config.get("source_directories")
objdump_executable: Optional[str] = config.get("objdump_executable")

MAX_FUNCTION_SIZE_LINES: int = args.max_lines
MAX_FUNCTION_SIZE_BYTES: int = MAX_FUNCTION_SIZE_LINES * 4

COLOR_ROTATION: List[str] = [
    Fore.MAGENTA,
    Fore.CYAN,
    Fore.GREEN,
    Fore.RED,
    Fore.LIGHTYELLOW_EX,
    Fore.LIGHTMAGENTA_EX,
    Fore.LIGHTCYAN_EX,
    Fore.LIGHTGREEN_EX,
    Fore.LIGHTBLACK_EX,
]

BUFFER_CMD: List[str] = ["tail", "-c", str(10 ** 9)]
LESS_CMD: List[str] = ["less", "-SRic", "-#6"]

DEBOUNCE_DELAY: float = 0.1
FS_WATCH_EXTENSIONS: List[str] = [".c", ".h", ".s"]

# ==== LOGIC ====

ObjdumpCommand = Tuple[List[str], str, Optional[str]]

if args.algorithm == "levenshtein":
    try:
        import Levenshtein  # type: ignore
    except ModuleNotFoundError as e:
        fail(MISSING_PREREQUISITES.format(e.name))

if args.source:
    try:
        import cxxfilt  # type: ignore
    except ModuleNotFoundError as e:
        fail(MISSING_PREREQUISITES.format(e.name))

if args.threeway and not args.watch:
    fail("Threeway diffing requires -w.")

if objdump_executable is None:
    for objdump_cand in ["mips-linux-gnu-objdump", "mips64-elf-objdump"]:
        try:
            subprocess.check_call(
                [objdump_cand, "--version"],
                stdout=subprocess.DEVNULL,
                stderr=subprocess.DEVNULL,
            )
            objdump_executable = objdump_cand
            break
        except subprocess.CalledProcessError:
            pass
        except FileNotFoundError:
            pass

if not objdump_executable:
    fail(
        "Missing binutils; please ensure mips-linux-gnu-objdump or mips64-elf-objdump exist, or configure objdump_executable."
    )


def maybe_eval_int(expr: str) -> Optional[int]:
    try:
        ret = ast.literal_eval(expr)
        if not isinstance(ret, int):
            raise Exception("not an integer")
        return ret
    except Exception:
        return None


def eval_int(expr: str, emsg: str) -> int:
    ret = maybe_eval_int(expr)
    if ret is None:
        fail(emsg)
    return ret


def eval_line_num(expr: str) -> int:
    return int(expr.strip().replace(":", ""), 16)


def run_make(target: str) -> None:
    subprocess.check_call(["make"] + makeflags + [target])


def run_make_capture_output(target: str) -> "subprocess.CompletedProcess[bytes]":
    return subprocess.run(
        ["make"] + makeflags + [target],
        stderr=subprocess.PIPE,
        stdout=subprocess.PIPE,
    )


def restrict_to_function(dump: str, fn_name: str) -> str:
    out: List[str] = []
    search = f"<{fn_name}>:"
    found = False
    for line in dump.split("\n"):
        if found:
            if len(out) >= MAX_FUNCTION_SIZE_LINES:
                break
            out.append(line)
        elif search in line:
            found = True
    return "\n".join(out)


def maybe_get_objdump_source_flags() -> List[str]:
    if not args.source:
        return []

    flags = [
        "--source",
        "--source-comment=│ ",
        "-l",
    ]

    if args.inlines:
        flags.append("--inlines")

    return flags


def run_objdump(cmd: ObjdumpCommand) -> str:
    flags, target, restrict = cmd
    assert objdump_executable, "checked previously"
    out = subprocess.check_output(
        [objdump_executable] + arch_flags + flags + [target], universal_newlines=True
    )
    if restrict is not None:
        return restrict_to_function(out, restrict)
    return out


base_shift: int = eval_int(
    args.base_shift, "Failed to parse --base-shift (-S) argument as an integer."
)


def search_map_file(fn_name: str) -> Tuple[Optional[str], Optional[int]]:
    if not mapfile:
        fail(f"No map file configured; cannot find function {fn_name}.")

    try:
        with open(mapfile) as f:
            lines = f.read().split("\n")
    except Exception:
        fail(f"Failed to open map file {mapfile} for reading.")

    try:
        cur_objfile = None
        ram_to_rom = None
        cands = []
        last_line = ""
        for line in lines:
            if line.startswith(" .text"):
                cur_objfile = line.split()[3]
            if "load address" in line:
                tokens = last_line.split() + line.split()
                ram = int(tokens[1], 0)
                rom = int(tokens[5], 0)
                ram_to_rom = rom - ram
            if line.endswith(" " + fn_name):
                ram = int(line.split()[0], 0)
                if cur_objfile is not None and ram_to_rom is not None:
                    cands.append((cur_objfile, ram + ram_to_rom))
            last_line = line
    except Exception as e:
        import traceback

        traceback.print_exc()
        fail(f"Internal error while parsing map file")

    if len(cands) > 1:
        fail(f"Found multiple occurrences of function {fn_name} in map file.")
    if len(cands) == 1:
        return cands[0]
    return None, None


def dump_elf() -> Tuple[str, ObjdumpCommand, ObjdumpCommand]:
    if not baseimg or not myimg:
        fail("Missing myimg/baseimg in config.")
    if base_shift:
        fail("--base-shift not compatible with -e")

    start_addr = eval_int(args.start, "Start address must be an integer expression.")

    if args.end is not None:
        end_addr = eval_int(args.end, "End address must be an integer expression.")
    else:
        end_addr = start_addr + MAX_FUNCTION_SIZE_BYTES

    flags1 = [
        f"--start-address={start_addr}",
        f"--stop-address={end_addr}",
    ]

    flags2 = [
        f"--disassemble={args.diff_elf_symbol}",
    ]

    objdump_flags = ["-drz", "-j", ".text"]
    return (
        myimg,
        (objdump_flags + flags1, baseimg, None),
        (objdump_flags + flags2 + maybe_get_objdump_source_flags(), myimg, None),
    )


def dump_objfile() -> Tuple[str, ObjdumpCommand, ObjdumpCommand]:
    if base_shift:
        fail("--base-shift not compatible with -o")
    if args.end is not None:
        fail("end address not supported together with -o")
    if args.start.startswith("0"):
        fail("numerical start address not supported with -o; pass a function name")

    objfile, _ = search_map_file(args.start)
    if not objfile:
        fail("Not able to find .o file for function.")

    if args.make:
        run_make(objfile)

    if not os.path.isfile(objfile):
        fail(f"Not able to find .o file for function: {objfile} is not a file.")

    refobjfile = "expected/" + objfile
    if not os.path.isfile(refobjfile):
        fail(f'Please ensure an OK .o file exists at "{refobjfile}".')

    objdump_flags = ["-drz"]
    return (
        objfile,
        (objdump_flags, refobjfile, args.start),
        (objdump_flags + maybe_get_objdump_source_flags(), objfile, args.start),
    )


def dump_binary() -> Tuple[str, ObjdumpCommand, ObjdumpCommand]:
    if not baseimg or not myimg:
        fail("Missing myimg/baseimg in config.")
    if args.make:
        run_make(myimg)
    start_addr = maybe_eval_int(args.start)
    if start_addr is None:
        _, start_addr = search_map_file(args.start)
        if start_addr is None:
            fail("Not able to find function in map file.")
    if args.end is not None:
        end_addr = eval_int(args.end, "End address must be an integer expression.")
    else:
        end_addr = start_addr + MAX_FUNCTION_SIZE_BYTES
    objdump_flags = ["-Dz", "-bbinary", "-EB"]
    flags1 = [
        f"--start-address={start_addr + base_shift}",
        f"--stop-address={end_addr + base_shift}",
    ]
    flags2 = [f"--start-address={start_addr}", f"--stop-address={end_addr}"]
    return (
        myimg,
        (objdump_flags + flags1, baseimg, None),
        (objdump_flags + flags2, myimg, None),
    )


def ansi_ljust(s: str, width: int) -> str:
    """Like s.ljust(width), but accounting for ANSI colors."""
    needed: int = width - ansiwrap.ansilen(s)
    if needed > 0:
        return s + " " * needed
    else:
        return s


if arch == "mips":
    re_int = re.compile(r"[0-9]+")
    re_comment = re.compile(r"<.*?>")
    re_reg = re.compile(
        r"\$?\b(a[0-3]|t[0-9]|s[0-8]|at|v[01]|f[12]?[0-9]|f3[01]|k[01]|fp|ra|zero)\b"
    )
    re_sprel = re.compile(r"(?<=,)([0-9]+|0x[0-9a-f]+)\(sp\)")
    re_large_imm = re.compile(r"-?[1-9][0-9]{2,}|-?0x[0-9a-f]{3,}")
    re_imm = re.compile(r"(\b|-)([0-9]+|0x[0-9a-fA-F]+)\b(?!\(sp)|%(lo|hi)\([^)]*\)")
    forbidden = set(string.ascii_letters + "_")
    arch_flags = ["-m", "mips:4300"]
    branch_likely_instructions = {
        "beql",
        "bnel",
        "beqzl",
        "bnezl",
        "bgezl",
        "bgtzl",
        "blezl",
        "bltzl",
        "bc1tl",
        "bc1fl",
    }
    branch_instructions = branch_likely_instructions.union(
        {
            "b",
            "beq",
            "bne",
            "beqz",
            "bnez",
            "bgez",
            "bgtz",
            "blez",
            "bltz",
            "bc1t",
            "bc1f",
        }
    )
    instructions_with_address_immediates = branch_instructions.union({"jal", "j"})
elif arch == "aarch64":
    re_int = re.compile(r"[0-9]+")
    re_comment = re.compile(r"(<.*?>|//.*$)")
    # GPRs and FP registers: X0-X30, W0-W30, [DSHQ]0..31
    # The zero registers and SP should not be in this list.
    re_reg = re.compile(r"\$?\b([dshq][12]?[0-9]|[dshq]3[01]|[xw][12]?[0-9]|[xw]30)\b")
    re_sprel = re.compile(r"sp, #-?(0x[0-9a-fA-F]+|[0-9]+)\b")
    re_large_imm = re.compile(r"-?[1-9][0-9]{2,}|-?0x[0-9a-f]{3,}")
    re_imm = re.compile(r"(?<!sp, )#-?(0x[0-9a-fA-F]+|[0-9]+)\b")
    arch_flags = []
    forbidden = set(string.ascii_letters + "_")
    branch_likely_instructions = set()
    branch_instructions = {
        "bl",
        "b",
        "b.eq",
        "b.ne",
        "b.cs",
        "b.hs",
        "b.cc",
        "b.lo",
        "b.mi",
        "b.pl",
        "b.vs",
        "b.vc",
        "b.hi",
        "b.ls",
        "b.ge",
        "b.lt",
        "b.gt",
        "b.le",
        "cbz",
        "cbnz",
        "tbz",
        "tbnz",
    }
    instructions_with_address_immediates = branch_instructions.union({"adrp"})
else:
    fail("Unknown architecture.")


def hexify_int(row: str, pat: Match[str]) -> str:
    full = pat.group(0)
    if len(full) <= 1:
        # leave one-digit ints alone
        return full
    start, end = pat.span()
    if start and row[start - 1] in forbidden:
        return full
    if end < len(row) and row[end] in forbidden:
        return full
    return hex(int(full))


def parse_relocated_line(line: str) -> Tuple[str, str, str]:
    try:
        ind2 = line.rindex(",")
    except ValueError:
        ind2 = line.rindex("\t")
    before = line[: ind2 + 1]
    after = line[ind2 + 1 :]
    ind2 = after.find("(")
    if ind2 == -1:
        imm, after = after, ""
    else:
        imm, after = after[:ind2], after[ind2:]
    if imm == "0x0":
        imm = "0"
    return before, imm, after


def process_mips_reloc(row: str, prev: str) -> str:
    before, imm, after = parse_relocated_line(prev)
    repl = row.split()[-1]
    if imm != "0":
        # MIPS uses relocations with addends embedded in the code as immediates.
        # If there is an immediate, show it as part of the relocation. Ideally
        # we'd show this addend in both %lo/%hi, but annoyingly objdump's output
        # doesn't include enough information to pair up %lo's and %hi's...
        # TODO: handle unambiguous cases where all addends for a symbol are the
        # same, or show "+???".
        mnemonic = prev.split()[0]
        if mnemonic in instructions_with_address_immediates and not imm.startswith(
            "0x"
        ):
            imm = "0x" + imm
        repl += "+" + imm if int(imm, 0) > 0 else imm
    if "R_MIPS_LO16" in row:
        repl = f"%lo({repl})"
    elif "R_MIPS_HI16" in row:
        # Ideally we'd pair up R_MIPS_LO16 and R_MIPS_HI16 to generate a
        # correct addend for each, but objdump doesn't give us the order of
        # the relocations, so we can't find the right LO16. :(
        repl = f"%hi({repl})"
    elif "R_MIPS_26" in row:
        # Function calls
        pass
    elif "R_MIPS_PC16" in row:
        # Branch to glabel. This gives confusing output, but there's not much
        # we can do here.
        pass
    else:
        assert False, f"unknown relocation type '{row}' for line '{prev}'"
    return before + repl + after


def pad_mnemonic(line: str) -> str:
    if "\t" not in line:
        return line
    mn, args = line.split("\t", 1)
    return f"{mn:<7s} {args}"


class Line(NamedTuple):
    mnemonic: str
    diff_row: str
    original: str
    normalized_original: str
    line_num: str
    branch_target: Optional[str]
    source_lines: List[str]
    comment: Optional[str]


class DifferenceNormalizer:
    def normalize(self, mnemonic: str, row: str) -> str:
        """This should be called exactly once for each line."""
        row = self._normalize_arch_specific(mnemonic, row)
        if args.ignore_large_imms:
            row = re.sub(re_large_imm, "<imm>", row)
        return row

    def _normalize_arch_specific(self, mnemonic: str, row: str) -> str:
        return row


class DifferenceNormalizerAArch64(DifferenceNormalizer):
    def __init__(self) -> None:
        super().__init__()
        self._adrp_pair_registers: Set[str] = set()

    def _normalize_arch_specific(self, mnemonic: str, row: str) -> str:
        if args.ignore_addr_diffs:
            row = self._normalize_adrp_differences(mnemonic, row)
            row = self._normalize_bl(mnemonic, row)
        return row

    def _normalize_bl(self, mnemonic: str, row: str) -> str:
        if mnemonic != "bl":
            return row

        row, _ = split_off_branch(row)
        return row

    def _normalize_adrp_differences(self, mnemonic: str, row: str) -> str:
        """Identifies ADRP + LDR/ADD pairs that are used to access the GOT and
        suppresses any immediate differences.

        Whenever an ADRP is seen, the destination register is added to the set of registers
        that are part of an ADRP + LDR/ADD pair. Registers are removed from the set as soon
        as they are used for an LDR or ADD instruction which completes the pair.

        This method is somewhat crude but should manage to detect most such pairs.
        """
        row_parts = row.split("\t", 1)
        if mnemonic == "adrp":
            self._adrp_pair_registers.add(row_parts[1].strip().split(",")[0])
            row, _ = split_off_branch(row)
        elif mnemonic == "ldr":
            for reg in self._adrp_pair_registers:
                # ldr xxx, [reg]
                # ldr xxx, [reg, <imm>]
                if f", [{reg}" in row_parts[1]:
                    self._adrp_pair_registers.remove(reg)
                    return normalize_imms(row)
        elif mnemonic == "add":
            for reg in self._adrp_pair_registers:
                # add reg, reg, <imm>
                if row_parts[1].startswith(f"{reg}, {reg}, "):
                    self._adrp_pair_registers.remove(reg)
                    return normalize_imms(row)

        return row


def make_difference_normalizer() -> DifferenceNormalizer:
    if arch == "aarch64":
        return DifferenceNormalizerAArch64()
    return DifferenceNormalizer()


def process(lines: List[str]) -> List[Line]:
    normalizer = make_difference_normalizer()
    skip_next = False
    source_lines = []
    if not args.diff_obj:
        lines = lines[7:]
        if lines and not lines[-1]:
            lines.pop()

    output: List[Line] = []
    stop_after_delay_slot = False
    for row in lines:
        if args.diff_obj and (">:" in row or not row):
            continue

        if args.source and (row and row[0] != " "):
            source_lines.append(row)
            continue

        if "R_AARCH64_" in row:
            # TODO: handle relocation
            continue

        if "R_MIPS_" in row:
            # N.B. Don't transform the diff rows, they already ignore immediates
            # if output[-1].diff_row != "<delay-slot>":
            # output[-1] = output[-1].replace(diff_row=process_mips_reloc(row, output[-1].row_with_imm))
            new_original = process_mips_reloc(row, output[-1].original)
            output[-1] = output[-1]._replace(original=new_original)
            continue

        m_comment = re.search(re_comment, row)
        comment = m_comment[0] if m_comment else None
        row = re.sub(re_comment, "", row)
        row = row.rstrip()
        tabs = row.split("\t")
        row = "\t".join(tabs[2:])
        line_num = tabs[0].strip()
        row_parts = row.split("\t", 1)
        mnemonic = row_parts[0].strip()
        if mnemonic not in instructions_with_address_immediates:
            row = re.sub(re_int, lambda m: hexify_int(row, m), row)
        original = row
        normalized_original = normalizer.normalize(mnemonic, original)
        if skip_next:
            skip_next = False
            row = "<delay-slot>"
            mnemonic = "<delay-slot>"
        if mnemonic in branch_likely_instructions:
            skip_next = True
        row = re.sub(re_reg, "<reg>", row)
        row = re.sub(re_sprel, "addr(sp)", row)
        row_with_imm = row
        if mnemonic in instructions_with_address_immediates:
            row = row.strip()
            row, _ = split_off_branch(row)
            row += "<imm>"
        else:
            row = normalize_imms(row)

        branch_target = None
        if mnemonic in branch_instructions:
            target = row_parts[1].strip().split(",")[-1]
            if mnemonic in branch_likely_instructions:
                target = hex(int(target, 16) - 4)[2:]
            branch_target = target.strip()

        output.append(
            Line(
                mnemonic=mnemonic,
                diff_row=row,
                original=original,
                normalized_original=normalized_original,
                line_num=line_num,
                branch_target=branch_target,
                source_lines=source_lines,
                comment=comment,
            )
        )
        source_lines = []

        if args.stop_jrra and mnemonic == "jr" and row_parts[1].strip() == "ra":
            stop_after_delay_slot = True
        elif stop_after_delay_slot:
            break

    return output


def format_single_line_diff(line1: str, line2: str, column_width: int) -> str:
    return ansi_ljust(line1, column_width) + line2


class SymbolColorer:
    symbol_colors: Dict[str, str]

    def __init__(self, base_index: int) -> None:
        self.color_index = base_index
        self.symbol_colors = {}

    def color_symbol(self, s: str, t: Optional[str] = None) -> str:
        try:
            color = self.symbol_colors[s]
        except:
            color = COLOR_ROTATION[self.color_index % len(COLOR_ROTATION)]
            self.color_index += 1
            self.symbol_colors[s] = color
        t = t or s
        return f"{color}{t}{Fore.RESET}"


def normalize_imms(row: str) -> str:
    return re.sub(re_imm, "<imm>", row)


def normalize_stack(row: str) -> str:
    return re.sub(re_sprel, "addr(sp)", row)


def split_off_branch(line: str) -> Tuple[str, str]:
    parts = line.split(",")
    if len(parts) < 2:
        parts = line.split(None, 1)
    off = len(line) - len(parts[-1])
    return line[:off], line[off:]


ColorFunction = Callable[[str], str]


def color_fields(
    pat: Pattern[str],
    out1: str,
    out2: str,
    color1: ColorFunction,
    color2: Optional[ColorFunction] = None,
) -> Tuple[str, str]:
    diffs = [
        of.group() != nf.group()
        for (of, nf) in zip(pat.finditer(out1), pat.finditer(out2))
    ]

    it = iter(diffs)

    def maybe_color(color: ColorFunction, s: str) -> str:
        return color(s) if next(it, False) else f"{Style.RESET_ALL}{s}"

    out1 = pat.sub(lambda m: maybe_color(color1, m.group()), out1)
    it = iter(diffs)
    out2 = pat.sub(lambda m: maybe_color(color2 or color1, m.group()), out2)

    return out1, out2


def color_branch_imms(br1: str, br2: str) -> Tuple[str, str]:
    if br1 != br2:
        br1 = f"{Fore.LIGHTBLUE_EX}{br1}{Style.RESET_ALL}"
        br2 = f"{Fore.LIGHTBLUE_EX}{br2}{Style.RESET_ALL}"
    return br1, br2


def diff_sequences_difflib(
    seq1: List[str], seq2: List[str]
) -> List[Tuple[str, int, int, int, int]]:
    differ = difflib.SequenceMatcher(a=seq1, b=seq2, autojunk=False)
    return differ.get_opcodes()


def diff_sequences(
    seq1: List[str], seq2: List[str]
) -> List[Tuple[str, int, int, int, int]]:
    if (
        args.algorithm != "levenshtein"
        or len(seq1) * len(seq2) > 4 * 10 ** 8
        or len(seq1) + len(seq2) >= 0x110000
    ):
        return diff_sequences_difflib(seq1, seq2)

    # The Levenshtein library assumes that we compare strings, not lists. Convert.
    # (Per the check above we know we have fewer than 0x110000 unique elements, so chr() works.)
    remapping: Dict[str, str] = {}

    def remap(seq: List[str]) -> str:
        seq = seq[:]
        for i in range(len(seq)):
            val = remapping.get(seq[i])
            if val is None:
                val = chr(len(remapping))
                remapping[seq[i]] = val
            seq[i] = val
        return "".join(seq)

    rem1 = remap(seq1)
    rem2 = remap(seq2)
    return Levenshtein.opcodes(rem1, rem2)  # type: ignore


def diff_lines(
    lines1: List[Line],
    lines2: List[Line],
) -> List[Tuple[Optional[Line], Optional[Line]]]:
    ret = []
    for (tag, i1, i2, j1, j2) in diff_sequences(
        [line.mnemonic for line in lines1],
        [line.mnemonic for line in lines2],
    ):
        for line1, line2 in itertools.zip_longest(lines1[i1:i2], lines2[j1:j2]):
            if tag == "replace":
                if line1 is None:
                    tag = "insert"
                elif line2 is None:
                    tag = "delete"
            elif tag == "insert":
                assert line1 is None
            elif tag == "delete":
                assert line2 is None
            ret.append((line1, line2))

    return ret


class OutputLine:
    base: Optional[str]
    fmt2: str
    key2: Optional[str]

    def __init__(self, base: Optional[str], fmt2: str, key2: Optional[str]) -> None:
        self.base = base
        self.fmt2 = fmt2
        self.key2 = key2

    def __eq__(self, other: object) -> bool:
        if not isinstance(other, OutputLine):
            return NotImplemented
        return self.key2 == other.key2

    def __hash__(self) -> int:
        return hash(self.key2)


def do_diff(basedump: str, mydump: str) -> List[OutputLine]:
    output: List[OutputLine] = []

    lines1 = process(basedump.split("\n"))
    lines2 = process(mydump.split("\n"))

    sc1 = SymbolColorer(0)
    sc2 = SymbolColorer(0)
    sc3 = SymbolColorer(4)
    sc4 = SymbolColorer(4)
    sc5 = SymbolColorer(0)
    sc6 = SymbolColorer(0)
    bts1: Set[str] = set()
    bts2: Set[str] = set()

    if args.show_branches:
        for (lines, btset, sc) in [
            (lines1, bts1, sc5),
            (lines2, bts2, sc6),
        ]:
            for line in lines:
                bt = line.branch_target
                if bt is not None:
                    btset.add(bt + ":")
                    sc.color_symbol(bt + ":")

    for (line1, line2) in diff_lines(lines1, lines2):
        line_color1 = line_color2 = sym_color = Fore.RESET
        line_prefix = " "
        if line1 and line2 and line1.diff_row == line2.diff_row:
            if line1.normalized_original == line2.normalized_original:
                out1 = line1.original
                out2 = line2.original
            elif line1.diff_row == "<delay-slot>":
                out1 = f"{Style.BRIGHT}{Fore.LIGHTBLACK_EX}{line1.original}"
                out2 = f"{Style.BRIGHT}{Fore.LIGHTBLACK_EX}{line2.original}"
            else:
                mnemonic = line1.original.split()[0]
                out1, out2 = line1.original, line2.original
                branch1 = branch2 = ""
                if mnemonic in instructions_with_address_immediates:
                    out1, branch1 = split_off_branch(line1.original)
                    out2, branch2 = split_off_branch(line2.original)
                branchless1 = out1
                branchless2 = out2
                out1, out2 = color_fields(
                    re_imm,
                    out1,
                    out2,
                    lambda s: f"{Fore.LIGHTBLUE_EX}{s}{Style.RESET_ALL}",
                )

                same_relative_target = False
                if line1.branch_target is not None and line2.branch_target is not None:
                    relative_target1 = eval_line_num(
                        line1.branch_target
                    ) - eval_line_num(line1.line_num)
                    relative_target2 = eval_line_num(
                        line2.branch_target
                    ) - eval_line_num(line2.line_num)
                    same_relative_target = relative_target1 == relative_target2

                if not same_relative_target:
                    branch1, branch2 = color_branch_imms(branch1, branch2)

                out1 += branch1
                out2 += branch2
                if normalize_imms(branchless1) == normalize_imms(branchless2):
                    if not same_relative_target:
                        # only imms differences
                        sym_color = Fore.LIGHTBLUE_EX
                        line_prefix = "i"
                else:
                    out1, out2 = color_fields(
                        re_sprel, out1, out2, sc3.color_symbol, sc4.color_symbol
                    )
                    if normalize_stack(branchless1) == normalize_stack(branchless2):
                        # only stack differences (luckily stack and imm
                        # differences can't be combined in MIPS, so we
                        # don't have to think about that case)
                        sym_color = Fore.YELLOW
                        line_prefix = "s"
                    else:
                        # regs differences and maybe imms as well
                        out1, out2 = color_fields(
                            re_reg, out1, out2, sc1.color_symbol, sc2.color_symbol
                        )
                        line_color1 = line_color2 = sym_color = Fore.YELLOW
                        line_prefix = "r"
        elif line1 and line2:
            line_prefix = "|"
            line_color1 = Fore.LIGHTBLUE_EX
            line_color2 = Fore.LIGHTBLUE_EX
            sym_color = Fore.LIGHTBLUE_EX
            out1 = line1.original
            out2 = line2.original
        elif line1:
            line_prefix = "<"
            line_color1 = sym_color = Fore.RED
            out1 = line1.original
            out2 = ""
        elif line2:
            line_prefix = ">"
            line_color2 = sym_color = Fore.GREEN
            out1 = ""
            out2 = line2.original

        if args.source and line2 and line2.comment:
            out2 += f" {line2.comment}"

        def format_part(
            out: str,
            line: Optional[Line],
            line_color: str,
            btset: Set[str],
            sc: SymbolColorer,
        ) -> Optional[str]:
            if line is None:
                return None
            in_arrow = "  "
            out_arrow = ""
            if args.show_branches:
                if line.line_num in btset:
                    in_arrow = sc.color_symbol(line.line_num, "~>") + line_color
                if line.branch_target is not None:
                    out_arrow = " " + sc.color_symbol(line.branch_target + ":", "~>")
            out = pad_mnemonic(out)
            return f"{line_color}{line.line_num} {in_arrow} {out}{Style.RESET_ALL}{out_arrow}"

        part1 = format_part(out1, line1, line_color1, bts1, sc5)
        part2 = format_part(out2, line2, line_color2, bts2, sc6)
        key2 = line2.original if line2 else None

        mid = f"{sym_color}{line_prefix}"

        if line2:
            for source_line in line2.source_lines:
                color = Style.DIM
                # File names and function names
                if source_line and source_line[0] != "│":
                    color += Style.BRIGHT
                    # Function names
                    if source_line.endswith("():"):
                        # Underline. Colorama does not provide this feature, unfortunately.
                        color += "\u001b[4m"
                        try:
                            source_line = cxxfilt.demangle(
                                source_line[:-3], external_only=False
                            )
                        except:
                            pass
                output.append(
                    OutputLine(
                        None,
                        f"  {color}{source_line}{Style.RESET_ALL}",
                        source_line,
                    )
                )

        fmt2 = mid + " " + (part2 or "")
        output.append(OutputLine(part1, fmt2, key2))

    return output


def chunk_diff(diff: List[OutputLine]) -> List[Union[List[OutputLine], OutputLine]]:
    cur_right: List[OutputLine] = []
    chunks: List[Union[List[OutputLine], OutputLine]] = []
    for output_line in diff:
        if output_line.base is not None:
            chunks.append(cur_right)
            chunks.append(output_line)
            cur_right = []
        else:
            cur_right.append(output_line)
    chunks.append(cur_right)
    return chunks


def format_diff(
    old_diff: List[OutputLine], new_diff: List[OutputLine]
) -> Tuple[str, List[str]]:
    old_chunks = chunk_diff(old_diff)
    new_chunks = chunk_diff(new_diff)
    output: List[Tuple[str, OutputLine, OutputLine]] = []
    assert len(old_chunks) == len(new_chunks), "same target"
    empty = OutputLine("", "", None)
    for old_chunk, new_chunk in zip(old_chunks, new_chunks):
        if isinstance(old_chunk, list):
            assert isinstance(new_chunk, list)
            if not old_chunk and not new_chunk:
                # Most of the time lines sync up without insertions/deletions,
                # and there's no interdiffing to be done.
                continue
            differ = difflib.SequenceMatcher(a=old_chunk, b=new_chunk, autojunk=False)
            for (tag, i1, i2, j1, j2) in differ.get_opcodes():
                if tag in ["equal", "replace"]:
                    for i, j in zip(range(i1, i2), range(j1, j2)):
                        output.append(("", old_chunk[i], new_chunk[j]))
                if tag in ["insert", "replace"]:
                    for j in range(j1 + i2 - i1, j2):
                        output.append(("", empty, new_chunk[j]))
                if tag in ["delete", "replace"]:
                    for i in range(i1 + j2 - j1, i2):
                        output.append(("", old_chunk[i], empty))
        else:
            assert isinstance(new_chunk, OutputLine)
            assert new_chunk.base
            # old_chunk.base and new_chunk.base have the same text since
            # both diffs are based on the same target, but they might
            # differ in color. Use the new version.
            output.append((new_chunk.base, old_chunk, new_chunk))

    # TODO: status line, with e.g. approximate permuter score?
    width = args.column_width
    if args.threeway:
        header_line = "TARGET".ljust(width) + "  CURRENT".ljust(width) + "  PREVIOUS"
        diff_lines = [
            ansi_ljust(base, width)
            + ansi_ljust(new.fmt2, width)
            + (old.fmt2 or "-" if old != new else "")
            for (base, old, new) in output
        ]
    else:
        header_line = ""
        diff_lines = [
            ansi_ljust(base, width) + new.fmt2
            for (base, old, new) in output
            if base or new.key2 is not None
        ]
    return header_line, diff_lines


def debounced_fs_watch(
    targets: List[str],
    outq: "queue.Queue[Optional[float]]",
    debounce_delay: float,
) -> None:
    import watchdog.events  # type: ignore
    import watchdog.observers  # type: ignore

    class WatchEventHandler(watchdog.events.FileSystemEventHandler):  # type: ignore
        def __init__(
            self, queue: "queue.Queue[float]", file_targets: List[str]
        ) -> None:
            self.queue = queue
            self.file_targets = file_targets

        def on_modified(self, ev: object) -> None:
            if isinstance(ev, watchdog.events.FileModifiedEvent):
                self.changed(ev.src_path)

        def on_moved(self, ev: object) -> None:
            if isinstance(ev, watchdog.events.FileMovedEvent):
                self.changed(ev.dest_path)

        def should_notify(self, path: str) -> bool:
            for target in self.file_targets:
                if path == target:
                    return True
            if args.make and any(
                path.endswith(suffix) for suffix in FS_WATCH_EXTENSIONS
            ):
                return True
            return False

        def changed(self, path: str) -> None:
            if self.should_notify(path):
                self.queue.put(time.time())

    def debounce_thread() -> NoReturn:
        listenq: "queue.Queue[float]" = queue.Queue()
        file_targets: List[str] = []
        event_handler = WatchEventHandler(listenq, file_targets)
        observer = watchdog.observers.Observer()
        observed = set()
        for target in targets:
            if os.path.isdir(target):
                observer.schedule(event_handler, target, recursive=True)
            else:
                file_targets.append(target)
                target = os.path.dirname(target) or "."
                if target not in observed:
                    observed.add(target)
                    observer.schedule(event_handler, target)
        observer.start()
        while True:
            t = listenq.get()
            more = True
            while more:
                delay = t + debounce_delay - time.time()
                if delay > 0:
                    time.sleep(delay)
                # consume entire queue
                more = False
                try:
                    while True:
                        t = listenq.get(block=False)
                        more = True
                except queue.Empty:
                    pass
            outq.put(t)

    th = threading.Thread(target=debounce_thread, daemon=True)
    th.start()


class Display:
    basedump: str
    mydump: str
    emsg: Optional[str]
    last_diff_output: Optional[List[OutputLine]]
    pending_update: Optional[Tuple[str, bool]]
    ready_queue: "queue.Queue[None]"
    watch_queue: "queue.Queue[Optional[float]]"
    less_proc: "Optional[subprocess.Popen[bytes]]"

    def __init__(self, basedump: str, mydump: str) -> None:
        self.basedump = basedump
        self.mydump = mydump
        self.emsg = None
        self.last_diff_output = None

    def run_less(self) -> "Tuple[subprocess.Popen[bytes], subprocess.Popen[bytes]]":
        if self.emsg is not None:
            output = self.emsg
        else:
            diff_output = do_diff(self.basedump, self.mydump)
            last_diff_output = self.last_diff_output or diff_output
            if args.threeway != "base" or not self.last_diff_output:
                self.last_diff_output = diff_output
            header, diff_lines = format_diff(last_diff_output, diff_output)
            header_lines = [header] if header else []
            output = "\n".join(header_lines + diff_lines[args.skip_lines :])

        # Pipe the output through 'tail' and only then to less, to ensure the
        # write call doesn't block. ('tail' has to buffer all its input before
        # it starts writing.) This also means we don't have to deal with pipe
        # closure errors.
        buffer_proc = subprocess.Popen(
            BUFFER_CMD, stdin=subprocess.PIPE, stdout=subprocess.PIPE
        )
        less_proc = subprocess.Popen(LESS_CMD, stdin=buffer_proc.stdout)
        assert buffer_proc.stdin
        assert buffer_proc.stdout
        buffer_proc.stdin.write(output.encode())
        buffer_proc.stdin.close()
        buffer_proc.stdout.close()
        return (buffer_proc, less_proc)

    def run_sync(self) -> None:
        proca, procb = self.run_less()
        procb.wait()
        proca.wait()

    def run_async(self, watch_queue: "queue.Queue[Optional[float]]") -> None:
        self.watch_queue = watch_queue
        self.ready_queue = queue.Queue()
        self.pending_update = None
        dthread = threading.Thread(target=self.display_thread)
        dthread.start()
        self.ready_queue.get()

    def display_thread(self) -> None:
        proca, procb = self.run_less()
        self.less_proc = procb
        self.ready_queue.put(None)
        while True:
            ret = procb.wait()
            proca.wait()
            self.less_proc = None
            if ret != 0:
                # fix the terminal
                os.system("tput reset")
            if ret != 0 and self.pending_update is not None:
                # killed by program with the intent to refresh
                msg, error = self.pending_update
                self.pending_update = None
                if not error:
                    self.mydump = msg
                    self.emsg = None
                else:
                    self.emsg = msg
                proca, procb = self.run_less()
                self.less_proc = procb
                self.ready_queue.put(None)
            else:
                # terminated by user, or killed
                self.watch_queue.put(None)
                self.ready_queue.put(None)
                break

    def progress(self, msg: str) -> None:
        # Write message to top-left corner
        sys.stdout.write("\x1b7\x1b[1;1f{}\x1b8".format(msg + " "))
        sys.stdout.flush()

    def update(self, text: str, error: bool) -> None:
        if not error and not self.emsg and text == self.mydump:
            self.progress("Unchanged. ")
            return
        self.pending_update = (text, error)
        if not self.less_proc:
            return
        self.less_proc.kill()
        self.ready_queue.get()

    def terminate(self) -> None:
        if not self.less_proc:
            return
        self.less_proc.kill()
        self.ready_queue.get()


def main() -> None:
    if args.diff_elf_symbol:
        make_target, basecmd, mycmd = dump_elf()
    elif args.diff_obj:
        make_target, basecmd, mycmd = dump_objfile()
    else:
        make_target, basecmd, mycmd = dump_binary()

    if args.write_asm is not None:
        mydump = run_objdump(mycmd)
        with open(args.write_asm, "w") as f:
            f.write(mydump)
        print(f"Wrote assembly to {args.write_asm}.")
        sys.exit(0)

    if args.base_asm is not None:
        with open(args.base_asm) as f:
            basedump = f.read()
    else:
        basedump = run_objdump(basecmd)

    mydump = run_objdump(mycmd)

    display = Display(basedump, mydump)

    if not args.watch:
        display.run_sync()
    else:
        if not args.make:
            yn = input(
                "Warning: watch-mode (-w) enabled without auto-make (-m). "
                "You will have to run make manually. Ok? (Y/n) "
            )
            if yn.lower() == "n":
                return
        if args.make:
            watch_sources = None
            watch_sources_for_target_fn = getattr(
                diff_settings, "watch_sources_for_target", None
            )
            if watch_sources_for_target_fn:
                watch_sources = watch_sources_for_target_fn(make_target)
            watch_sources = watch_sources or source_directories
            if not watch_sources:
                fail("Missing source_directories config, don't know what to watch.")
        else:
            watch_sources = [make_target]
        q: "queue.Queue[Optional[float]]" = queue.Queue()
        debounced_fs_watch(watch_sources, q, DEBOUNCE_DELAY)
        display.run_async(q)
        last_build = 0.0
        try:
            while True:
                t = q.get()
                if t is None:
                    break
                if t < last_build:
                    continue
                last_build = time.time()
                if args.make:
                    display.progress("Building...")
                    ret = run_make_capture_output(make_target)
                    if ret.returncode != 0:
                        display.update(
                            ret.stderr.decode("utf-8-sig", "replace")
                            or ret.stdout.decode("utf-8-sig", "replace"),
                            error=True,
                        )
                        continue
                mydump = run_objdump(mycmd)
                display.update(mydump, error=False)
        except KeyboardInterrupt:
            display.terminate()


main()
