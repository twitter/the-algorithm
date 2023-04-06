#!/usr/bin/env python3
import sys
import re
import os
import ast
import argparse
import subprocess
import difflib
import string
import itertools
import threading
import queue
import time

def fail(msg):
    print(msg, file=sys.stderr)
    sys.exit(1)

try:
    import attr
    from colorama import Fore, Style, Back
    import ansiwrap
    import watchdog
except ModuleNotFoundError as e:
    fail(f"Missing prerequisite python module {e.name}. "
        "Run `python3 -m pip install --user colorama ansiwrap attrs watchdog` to install prerequisites.")

# Prefer to use diff_settings.py from the current working directory
sys.path.insert(0, '.')
try:
    import diff_settings
except ModuleNotFoundError:
    fail("Unable to find diff_settings.py in the same directory.")

# ==== CONFIG ====

parser = argparse.ArgumentParser(
        description="Diff MIPS assembly.")
parser.add_argument('start',
        help="Function name or address to start diffing from.")
parser.add_argument('end', nargs='?',
        help="Address to end diff at.")
parser.add_argument('-o', dest='diff_obj', action='store_true',
        help="Diff .o files rather than a whole binary. This makes it possible to see symbol names. (Recommended)")
parser.add_argument('--base-asm', dest='base_asm', metavar='FILE',
        help="Read assembly from given file instead of configured base img.")
parser.add_argument('--write-asm', dest='write_asm', metavar='FILE',
        help="Write the current assembly output to file, e.g. for use with --base-asm.")
parser.add_argument('-m', '--make', dest='make', action='store_true',
        help="Automatically run 'make' on the .o file or binary before diffing.")
parser.add_argument('-l', '--skip-lines', dest='skip_lines', type=int, default=0,
        help="Skip the first N lines of output.")
parser.add_argument('-f', '--stop-jr-ra', dest='stop_jrra', action='store_true',
        help="Stop disassembling at the first 'jr ra'. Some functions have multiple return points, so use with care!")
parser.add_argument('-i', '--ignore-large-imms', dest='ignore_large_imms', action='store_true',
        help="Pretend all large enough immediates are the same.")
parser.add_argument('-B', '--no-show-branches', dest='show_branches', action='store_false',
        help="Don't visualize branches/branch targets.")
parser.add_argument('-S', '--base-shift', dest='base_shift', type=str, default='0',
        help="Diff position X in our img against position X + shift in the base img. "
        "Arithmetic is allowed, so e.g. |-S \"0x1234 - 0x4321\"| is a reasonable "
        "flag to pass if it is known that position 0x1234 in the base img syncs "
        "up with position 0x4321 in our img. Not supported together with -o.")
parser.add_argument('-w', '--watch', dest='watch', action='store_true',
        help="Automatically update when source/object files change. "
        "Recommended in combination with -m.")
parser.add_argument('--width', dest='column_width', type=int, default=50,
        help="Sets the width of the left and right view column.")

# Project-specific flags, e.g. different versions/make arguments.
if hasattr(diff_settings, "add_custom_arguments"):
    diff_settings.add_custom_arguments(parser)

args = parser.parse_args()

# Set imgs, map file and make flags in a project-specific manner.
config = {}
diff_settings.apply(config, args)

baseimg = config.get('baseimg', None)
myimg = config.get('myimg', None)
mapfile = config.get('mapfile', None)
makeflags = config.get('makeflags', [])
source_directories = config.get('source_directories', None)

MAX_FUNCTION_SIZE_LINES = 1024
MAX_FUNCTION_SIZE_BYTES = 1024 * 4

COLOR_ROTATION = [
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

BUFFER_CMD = ["tail", "-c", str(10**9)]
LESS_CMD = ["less", "-Ric"]

DEBOUNCE_DELAY = 0.1
FS_WATCH_EXTENSIONS = ['.c', '.h']

# ==== LOGIC ====

binutils_prefix = None

for binutils_cand in ['mips-linux-gnu-', 'mips64-elf-']:
    try:
        subprocess.check_call([binutils_cand + "objdump", "--version"], stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
        binutils_prefix = binutils_cand
        break
    except subprocess.CalledProcessError:
        pass
    except FileNotFoundError:
        pass

if not binutils_prefix:
    fail("Missing binutils; please ensure mips-linux-gnu-objdump or mips64-elf-objdump exist.")

def eval_int(expr, emsg=None):
    try:
        ret = ast.literal_eval(expr)
        if not isinstance(ret, int):
            raise Exception("not an integer")
        return ret
    except Exception:
        if emsg is not None:
            fail(emsg)
        return None

def run_make(target, capture_output=False):
    if capture_output:
        return subprocess.run(["make"] + makeflags + [target], stderr=subprocess.PIPE, stdout=subprocess.PIPE)
    else:
        subprocess.check_call(["make"] + makeflags + [target])

def restrict_to_function(dump, fn_name):
    out = []
    search = f'<{fn_name}>:'
    found = False
    for line in dump.split('\n'):
        if found:
            if len(out) >= MAX_FUNCTION_SIZE_LINES:
                break
            out.append(line)
        elif search in line:
            found = True
    return '\n'.join(out)

def run_objdump(cmd):
    flags, target, restrict = cmd
    out = subprocess.check_output([binutils_prefix + "objdump"] + flags + [target], universal_newlines=True)
    if restrict is not None:
        return restrict_to_function(out, restrict)
    return out

base_shift = eval_int(args.base_shift, "Failed to parse --base-shift (-S) argument as an integer.")

def search_map_file(fn_name):
    if not mapfile:
        fail(f"No map file configured; cannot find function {fn_name}.")

    try:
        with open(mapfile) as f:
            lines = f.read().split('\n')
    except Exception:
        fail(f"Failed to open map file {mapfile} for reading.")

    try:
        cur_objfile = None
        ram_to_rom = None
        cands = []
        last_line = ''
        for line in lines:
            if line.startswith(' .text'):
                cur_objfile = line.split()[3]
            if 'load address' in line:
                tokens = last_line.split() + line.split()
                ram = int(tokens[1], 0)
                rom = int(tokens[5], 0)
                ram_to_rom = rom - ram
            if line.endswith(' ' + fn_name):
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

def dump_objfile():
    if base_shift:
        fail("--base-shift not compatible with -o")
    if args.end is not None:
        fail("end address not supported together with -o")
    if args.start.startswith('0'):
        fail("numerical start address not supported with -o; pass a function name")

    objfile, _ = search_map_file(args.start)
    if not objfile:
        fail("Not able to find .o file for function.")

    if args.make:
        run_make(objfile)

    if not os.path.isfile(objfile):
        fail("Not able to find .o file for function.")

    refobjfile = "expected/" + objfile
    if not os.path.isfile(refobjfile):
        fail(f'Please ensure an OK .o file exists at "{refobjfile}".')

    objdump_flags = ["-drz"]
    return (
        objfile,
        (objdump_flags, refobjfile, args.start),
        (objdump_flags, objfile, args.start)
    )

def dump_binary():
    if not baseimg or not myimg:
        fail("Missing myimg/baseimg in config.")
    if args.make:
        run_make(myimg)
    start_addr = eval_int(args.start)
    if start_addr is None:
        _, start_addr = search_map_file(args.start)
        if start_addr is None:
            fail("Not able to find function in map file.")
    if args.end is not None:
        end_addr = eval_int(args.end, "End address must be an integer expression.")
    else:
        end_addr = start_addr + MAX_FUNCTION_SIZE_BYTES
    objdump_flags = ['-Dz', '-bbinary', '-mmips', '-EB']
    flags1 = [f"--start-address={start_addr + base_shift}", f"--stop-address={end_addr + base_shift}"]
    flags2 = [f"--start-address={start_addr}", f"--stop-address={end_addr}"]
    return (
        myimg,
        (objdump_flags + flags1, baseimg, None),
        (objdump_flags + flags2, myimg, None)
    )

# Alignment with ANSI colors is broken, let's fix it.
def ansi_ljust(s, width):
    needed = width - ansiwrap.ansilen(s)
    if needed > 0:
        return s + ' ' * needed
    else:
        return s

re_int = re.compile(r'[0-9]+')
re_comments = re.compile(r'<.*?>')
re_regs = re.compile(r'\b(a[0-3]|t[0-9]|s[0-7]|at|v[01]|f[12]?[0-9]|f3[01]|fp)\b')
re_sprel = re.compile(r',([1-9][0-9]*|0x[1-9a-f][0-9a-f]*)\(sp\)')
re_large_imm = re.compile(r'-?[1-9][0-9]{2,}|-?0x[0-9a-f]{3,}')
forbidden = set(string.ascii_letters + '_')
branch_likely_instructions = set([
    'beql', 'bnel', 'beqzl', 'bnezl', 'bgezl', 'bgtzl', 'blezl', 'bltzl',
    'bc1tl', 'bc1fl'
])
branch_instructions = set([
    'b', 'beq', 'bne', 'beqz', 'bnez', 'bgez', 'bgtz', 'blez', 'bltz',
    'bc1t', 'bc1f'
] + list(branch_likely_instructions))

def hexify_int(row, pat):
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

def parse_relocated_line(line):
    try:
        ind2 = line.rindex(',')
    except ValueError:
        ind2 = line.rindex('\t')
    before = line[:ind2+1]
    after = line[ind2+1:]
    ind2 = after.find('(')
    if ind2 == -1:
        imm, after = after, ''
    else:
        imm, after = after[:ind2], after[ind2:]
    if imm == '0x0':
        imm = '0'
    return before, imm, after

def process_reloc(row, prev):
    before, imm, after = parse_relocated_line(prev)
    repl = row.split()[-1]
    if imm != '0':
        if before.strip() == 'jal' and not imm.startswith('0x'):
            imm = '0x' + imm
        repl += '+' + imm if int(imm,0) > 0 else imm
    if 'R_MIPS_LO16' in row:
        repl = f'%lo({repl})'
    elif 'R_MIPS_HI16' in row:
        # Ideally we'd pair up R_MIPS_LO16 and R_MIPS_HI16 to generate a
        # correct addend for each, but objdump doesn't give us the order of
        # the relocations, so we can't find the right LO16. :(
        repl = f'%hi({repl})'
    else:
        assert 'R_MIPS_26' in row, f"unknown relocation type '{row}'"
    return before + repl + after

def process(lines):
    mnemonics = []
    diff_rows = []
    skip_next = False
    originals = []
    line_nums = []
    branch_targets = []
    if not args.diff_obj:
        lines = lines[7:]
        if lines and not lines[-1]:
            lines.pop()

    for row in lines:
        if args.diff_obj and ('>:' in row or not row):
            continue

        if 'R_MIPS_' in row:
            if diff_rows[-1] != '<delay-slot>':
                diff_rows[-1] = process_reloc(row, diff_rows[-1])
            originals[-1] = process_reloc(row, originals[-1])
            continue

        row = re.sub(re_comments, '', row)
        row = row.rstrip()
        tabs = row.split('\t')
        row = '\t'.join(tabs[2:])
        line_num = tabs[0].strip()
        row_parts = row.split('\t', 1)
        mnemonic = row_parts[0].strip()
        if mnemonic not in branch_instructions:
            row = re.sub(re_int, lambda s: hexify_int(row, s), row)
        original = row
        if skip_next:
            skip_next = False
            row = '<delay-slot>'
            mnemonic = '<delay-slot>'
        if mnemonic in branch_likely_instructions:
            skip_next = True
        row = re.sub(re_regs, '<reg>', row)
        row = re.sub(re_sprel, ',addr(sp)', row)
        if args.ignore_large_imms:
            row = re.sub(re_large_imm, '<imm>', row)

        # Replace tabs with spaces
        mnemonics.append(mnemonic)
        diff_rows.append(row)
        originals.append(original)
        line_nums.append(line_num)
        if mnemonic in branch_instructions:
            target = row_parts[1].strip().split(',')[-1]
            if mnemonic in branch_likely_instructions:
                target = hex(int(target, 16) - 4)[2:]
            branch_targets.append(target)
        else:
            branch_targets.append(None)
        if args.stop_jrra and mnemonic == 'jr' and row_parts[1].strip() == 'ra':
            break

    # Cleanup whitespace
    originals = [original.strip() for original in originals]
    originals = [''.join(f'{o:<8s}' for o in original.split('\t')) for original in originals]
    # return diff_rows, diff_rows, line_nums
    return mnemonics, diff_rows, originals, line_nums, branch_targets

def format_single_line_diff(line1, line2, column_width):
    return f"{ansi_ljust(line1,column_width)}{ansi_ljust(line2,column_width)}"

class SymbolColorer:
    def __init__(self, base_index):
        self.color_index = base_index
        self.symbol_colors = {}

    def color_symbol(self, s, t=None):
        try:
            color = self.symbol_colors[s]
        except:
            color = COLOR_ROTATION[self.color_index % len(COLOR_ROTATION)]
            self.color_index += 1
            self.symbol_colors[s] = color
        t = t or s
        return f'{color}{t}{Fore.RESET}'

def normalize_large_imms(row):
    if args.ignore_large_imms:
        row = re.sub(re_large_imm, '<imm>', row)
    return row

def do_diff(basedump, mydump):
    asm_lines1 = basedump.split('\n')
    asm_lines2 = mydump.split('\n')

    output = []

    # TODO: status line?
    # output.append(sha1sum(mydump))

    mnemonics1, asm_lines1, originals1, line_nums1, branch_targets1 = process(asm_lines1)
    mnemonics2, asm_lines2, originals2, line_nums2, branch_targets2 = process(asm_lines2)

    sc1 = SymbolColorer(0)
    sc2 = SymbolColorer(0)
    sc3 = SymbolColorer(4)
    sc4 = SymbolColorer(4)
    sc5 = SymbolColorer(0)
    sc6 = SymbolColorer(0)
    bts1 = set()
    bts2 = set()

    if args.show_branches:
        for (bts, btset, sc) in [(branch_targets1, bts1, sc5), (branch_targets2, bts2, sc6)]:
            for bt in bts:
                if bt is not None:
                    btset.add(bt + ":")
                    sc.color_symbol(bt + ":")

    differ: difflib.SequenceMatcher = difflib.SequenceMatcher(a=mnemonics1, b=mnemonics2, autojunk=False)
    for (tag, i1, i2, j1, j2) in differ.get_opcodes():
        lines1 = asm_lines1[i1:i2]
        lines2 = asm_lines2[j1:j2]

        for k, (line1, line2) in enumerate(itertools.zip_longest(lines1, lines2)):
            if tag == 'replace':
                if line1 is None:
                    tag = 'insert'
                elif line2 is None:
                    tag = 'delete'

            try:
                original1 = originals1[i1+k]
                line_num1 = line_nums1[i1+k]
            except:
                original1 = ''
                line_num1 = ''
            try:
                original2 = originals2[j1+k]
                line_num2 = line_nums2[j1+k]
            except:
                original2 = ''
                line_num2 = ''

            line_color = Fore.RESET
            line_prefix = ' '
            if line1 == line2:
                if normalize_large_imms(original1) == normalize_large_imms(original2):
                    out1 = f'{original1}'
                    out2 = f'{original2}'
                elif line1 == '<delay-slot>':
                    out1 = f'{Style.DIM}{original1}'
                    out2 = f'{Style.DIM}{original2}'
                else:
                    line_color = Fore.YELLOW
                    line_prefix = 'r'
                    out1 = f'{Fore.YELLOW}{original1}{Style.RESET_ALL}'
                    out2 = f'{Fore.YELLOW}{original2}{Style.RESET_ALL}'
                    out1 = re.sub(re_regs, lambda s: sc1.color_symbol(s.group()), out1)
                    out2 = re.sub(re_regs, lambda s: sc2.color_symbol(s.group()), out2)
                    out1 = re.sub(re_sprel, lambda s: sc3.color_symbol(s.group()), out1)
                    out2 = re.sub(re_sprel, lambda s: sc4.color_symbol(s.group()), out2)
            elif tag in ['replace', 'equal']:
                line_prefix = '|'
                line_color = Fore.BLUE
                out1 = f"{Fore.BLUE}{original1}{Style.RESET_ALL}"
                out2 = f"{Fore.BLUE}{original2}{Style.RESET_ALL}"
            elif tag == 'delete':
                line_prefix = '<'
                line_color = Fore.RED
                out1 = f"{Fore.RED}{original1}{Style.RESET_ALL}"
                out2 = ''
            elif tag == 'insert':
                line_prefix = '>'
                line_color = Fore.GREEN
                out1 = ''
                out2 = f"{Fore.GREEN}{original2}{Style.RESET_ALL}"

            in_arrow1 = '  '
            in_arrow2 = '  '
            out_arrow1 = ''
            out_arrow2 = ''
            line_num1 = line_num1 if out1 else ''
            line_num2 = line_num2 if out2 else ''

            if args.show_branches and out1:
                if line_num1 in bts1:
                    in_arrow1 = sc5.color_symbol(line_num1, '~>')
                if branch_targets1[i1+k] is not None:
                    out_arrow1 = ' ' + sc5.color_symbol(branch_targets1[i1+k] + ":", '~>')
            if args.show_branches and out2:
                if line_num2 in bts2:
                    in_arrow2 = sc6.color_symbol(line_num2, '~>')
                if branch_targets2[j1+k] is not None:
                    out_arrow2 = ' ' + sc6.color_symbol(branch_targets2[j1+k] + ":", '~>')

            out1 =               f"{line_color}{line_num1} {in_arrow1} {out1}{Style.RESET_ALL}{out_arrow1}"
            out2 = f"{line_color}{line_prefix} {line_num2} {in_arrow2} {out2}{Style.RESET_ALL}{out_arrow2}"
            output.append(format_single_line_diff(out1, out2, args.column_width))

    return output[args.skip_lines:]


def debounced_fs_watch(targets, outq, debounce_delay):
    import watchdog.events
    import watchdog.observers

    class WatchEventHandler(watchdog.events.FileSystemEventHandler):
        def __init__(self, queue, file_targets):
            self.queue = queue
            self.file_targets = file_targets

        def on_modified(self, ev):
            if isinstance(ev, watchdog.events.FileModifiedEvent):
                self.changed(ev.src_path)

        def on_moved(self, ev):
            if isinstance(ev, watchdog.events.FileMovedEvent):
                self.changed(ev.dest_path)

        def should_notify(self, path):
            for target in self.file_targets:
                if path == target:
                    return True
            if args.make and any(path.endswith(suffix) for suffix in FS_WATCH_EXTENSIONS):
                return True
            return False

        def changed(self, path):
            if self.should_notify(path):
                self.queue.put(time.time())

    def debounce_thread():
        listenq = queue.Queue()
        file_targets = []
        event_handler = WatchEventHandler(listenq, file_targets)
        observer = watchdog.observers.Observer()
        observed = set()
        for target in targets:
            if os.path.isdir(target):
                observer.schedule(event_handler, target, recursive=True)
            else:
                file_targets.append(target)
                target = os.path.dirname(target)
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


class Display():
    def __init__(self, basedump, mydump):
        self.basedump = basedump
        self.mydump = mydump
        self.emsg = None

    def run_less(self):
        if self.emsg is not None:
            output = self.emsg
        else:
            output = '\n'.join(do_diff(self.basedump, self.mydump))

        # Pipe the output through 'tail' and only then to less, to ensure the
        # write call doesn't block. ('tail' has to buffer all its input before
        # it starts writing.) This also means we don't have to deal with pipe
        # closure errors.
        buffer_proc = subprocess.Popen(BUFFER_CMD, stdin=subprocess.PIPE,
                stdout=subprocess.PIPE)
        less_proc = subprocess.Popen(LESS_CMD, stdin=buffer_proc.stdout)
        buffer_proc.stdin.write(output.encode())
        buffer_proc.stdin.close()
        buffer_proc.stdout.close()
        return (buffer_proc, less_proc)

    def run_sync(self):
        proca, procb = self.run_less()
        procb.wait()
        proca.wait()

    def run_async(self, watch_queue):
        self.watch_queue = watch_queue
        self.ready_queue = queue.Queue()
        self.pending_update = None
        dthread = threading.Thread(target=self.display_thread)
        dthread.start()
        self.ready_queue.get()

    def display_thread(self):
        proca, procb = self.run_less()
        self.less_proc = procb
        self.ready_queue.put(0)
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
                self.ready_queue.put(0)
            else:
                # terminated by user, or killed
                self.watch_queue.put(None)
                self.ready_queue.put(0)
                break

    def progress(self, msg):
        # Write message to top-left corner
        sys.stdout.write("\x1b7\x1b[1;1f{}\x1b8".format(msg + " "))
        sys.stdout.flush()

    def update(self, text, error):
        if not error and not self.emsg and text == self.mydump:
            self.progress("Unchanged. ")
            return
        self.pending_update = (text, error)
        if not self.less_proc:
            return
        self.less_proc.kill()
        self.ready_queue.get()

    def terminate(self):
        if not self.less_proc:
            return
        self.less_proc.kill()
        self.ready_queue.get()


def main():
    if args.diff_obj:
        make_target, basecmd, mycmd = dump_objfile()
    else:
        make_target, basecmd, mycmd = dump_binary()

    if args.write_asm is not None:
        mydump = run_objdump(mycmd)
        with open(args.write_asm) as f:
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
            yn = input("Warning: watch-mode (-w) enabled without auto-make (-m). You will have to run make manually. Ok? (Y/n) ")
            if yn.lower() == 'n':
                return
        if args.make:
            watch_sources = None
            if hasattr(diff_settings, "watch_sources_for_target"):
                watch_sources = diff_settings.watch_sources_for_target(make_target)
            watch_sources = watch_sources or source_directories
            if not watch_sources:
                fail("Missing source_directories config, don't know what to watch.")
        else:
            watch_sources = [make_target]
        q = queue.Queue()
        debounced_fs_watch(watch_sources, q, DEBOUNCE_DELAY)
        display.run_async(q)
        last_build = 0
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
                    ret = run_make(make_target, capture_output=True)
                    if ret.returncode != 0:
                        display.update(ret.stderr.decode() or ret.stdout.decode(), error=True)
                        continue
                mydump = run_objdump(mycmd)
                display.update(mydump, error=False)
        except KeyboardInterrupt:
            display.terminate()

main()
