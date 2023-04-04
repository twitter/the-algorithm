#!/usr/bin/env python3
import sys

commands = {}
commands['seq'] = {
    # non-arg commands
    0xff: ['end'],
    0xfe: ['delay1'],
    0xfd: ['delay', 'var'],
    0xfc: ['call', 'addr'],
    0xfb: ['jump', 'addr'],
    0xfa: ['beqz', 'addr'],
    0xf9: ['bltz', 'addr'],
    0xf8: ['loop', 'u8'],
    0xf7: ['loopend'],
    0xf5: ['bgez', 'addr'],
    0xf2: ['reservenotes', 'u8'],
    0xf1: ['unreservenotes'],
    0xdf: ['transpose', 's8'],
    0xde: ['transposerel', 's8'],
    0xdd: ['settempo', 'u8'],
    0xdc: ['addtempo', 's8'],
    0xdb: ['setvol', 'u8'],
    0xda: ['changevol', 's8'],
    0xd7: ['initchannels', 'hex16'],
    0xd6: ['disablechannels', 'hex16'],
    0xd5: ['setmutescale', 's8'],
    0xd4: ['mute'],
    0xd3: ['setmutebhv', 'hex8'],
    0xd2: ['setshortnotevelocitytable', 'addr'],
    0xd1: ['setshortnotedurationtable', 'addr'],
    0xd0: ['setnoteallocationpolicy', 'u8'],
    0xcc: ['setval', 'u8'],
    0xc9: ['bitand', 'u8'],
    0xc8: ['subtract', 'u8'],
    # arg commands
    0x00: ['testchdisabled', 'bits:4'],
    0x50: ['subvariation', 'bits:4:ign'],
    0x70: ['setvariation', 'bits:4:ign'],
    0x80: ['getvariation', 'bits:4:ign'],
    0x90: ['startchannel', 'bits:4', 'addr'],
}

commands['chan'] = {
    # non-arg commands
    0xff: ['end'],
    0xfe: ['delay1'],
    0xfd: ['delay', 'var'],
    0xfc: ['call', 'addr'],
    0xfb: ['jump', 'addr'],
    0xfa: ['beqz', 'addr'],
    0xf9: ['bltz', 'addr'],
    0xf8: ['loop', 'u8'],
    0xf7: ['loopend'],
    0xf6: ['break'],
    0xf5: ['bgez', 'addr'],
    0xf3: ['hang'],
    0xf2: ['reservenotes', 'u8'],
    0xf1: ['unreservenotes'],
    0xe4: ['dyncall'],
    0xe3: ['setvibratodelay', 'u8'],
    0xe2: ['setvibratoextentlinear', 'u8', 'u8', 'u8'],
    0xe1: ['setvibratoratelinear', 'u8', 'u8', 'u8'],
    0xe0: ['setvolscale', 'u8'],
    0xdf: ['setvol', 'u8'],
    0xde: ['freqscale', 'u16'],
    0xdd: ['setpan', 'u8'],
    0xdc: ['setpanmix', 'u8'],
    0xdb: ['transpose', 's8'],
    0xda: ['setenvelope', 'addr'],
    0xd9: ['setdecayrelease', 'u8'],
    0xd8: ['setvibratoextent', 'u8'],
    0xd7: ['setvibratorate', 'u8'],
    0xd6: ['setupdatesperframe_unimplemented', 'u8'],
    0xd4: ['setreverb', 'u8'],
    0xd3: ['pitchbend', 's8'],
    0xd2: ['setsustain', 'u8'],
    0xd1: ['setnoteallocationpolicy', 'u8'],
    0xd0: ['stereoheadseteffects', 'u8'],
    0xcc: ['setval', 'u8'],
    0xcb: ['readseq', 'addr'],
    0xca: ['setmutebhv', 'hex8'],
    0xc9: ['bitand', 'u8'],
    0xc8: ['subtract', 'u8'],
    0xc7: ['writeseq', 'u8', 'addr'],
    0xc6: ['setbank', 'u8'],
    0xc5: ['dynsetdyntable'],
    0xc4: ['largenoteson'],
    0xc3: ['largenotesoff'],
    0xc2: ['setdyntable', 'addr'],
    0xc1: ['setinstr', 'u8'],
    # arg commands
    0x00: ['testlayerfinished', 'bits:4'],
    0x10: ['startchannel', 'bits:4', 'addr'],
    0x20: ['disablechannel', 'bits:4'],
    0x30: ['iowriteval2', 'bits:4', 'u8'],
    0x40: ['ioreadval2', 'bits:4', 'u8'],
    0x50: ['ioreadvalsub', 'bits:4'],
    0x60: ['setnotepriority', 'bits:4'],
    0x70: ['iowriteval', 'bits:4'],
    0x80: ['ioreadval', 'bits:4'],
    0x90: ['setlayer', 'bits:4', 'addr'],
    0xa0: ['freelayer', 'bits:4'],
    0xb0: ['dynsetlayer', 'bits:4'],
}

commands_layer_base = {
    # non-arg commands
    0xc0: ['delay', 'var'],
    0xc1: ['setshortnotevelocity', 'u8'],
    0xc2: ['transpose', 's8'],
    0xc3: ['setshortnotedefaultplaypercentage', 'var'],
    0xc4: ['somethingon'], # ?? (something to do with decay behavior)
    0xc5: ['somethingoff'], # ??
    0xc6: ['setinstr', 'u8'],
    0xc7: ['portamento', 'hex8', 'u8', 'u8'],
    0xc8: ['disableportamento'],
    0xc9: ['setshortnoteduration', 'u8'],
    0xca: ['setpan', 'u8'],
    0xf7: ['loopend'],
    0xf8: ['loop', 'u8'],
    0xfb: ['jump', 'addr'],
    0xfc: ['call', 'addr'],
    0xff: ['end'],
    # arg commands
    0xd0: ['setshortnotevelocityfromtable', 'bits:4'],
    0xe0: ['setshortnotedurationfromtable', 'bits:4'],
}

commands['layer_large'] = dict(list(commands_layer_base.items()) + list({
    0x00: ['note0', 'bits:6', 'var', 'u8', 'u8'],
    0x40: ['note1', 'bits:6', 'var', 'u8'],
    0x80: ['note2', 'bits:6', 'u8', 'u8'],
}.items()))

commands['layer_small'] = dict(list(commands_layer_base.items()) + list({
    0x00: ['smallnote0', 'bits:6', 'var'],
    0x40: ['smallnote1', 'bits:6'],
    0x80: ['smallnote2', 'bits:6'],
}.items()))

sh_chan_overrides = [
    (0x80, ['testlayerfinished', 'bits:3']),
    (0x88, ['setlayer', 'bits:3', 'addr']),
    (0x60, ['ioreadval', 'bits:4']),
    (0x90, ['freelayer', 'bits:4']),
]

def valid_cmd_for_nbits(cmd_list, nbits):
    for arg in cmd_list:
        if arg.startswith('bits:'):
            return int(arg.split(':')[1]) == nbits
    return nbits == 0

print_end_padding = False
if "--print-end-padding" in sys.argv:
    print_end_padding = True
    sys.argv.remove("--print-end-padding")

if len(sys.argv) != 2:
    print(f"Usage: {sys.argv[0]} (--emit-asm-macros | input.m64)")
    sys.exit(0)

if sys.argv[1] == "--emit-asm-macros":
    print("// Macros for disassembled sequence files. This file was automatically generated by seq_decoder.py.")
    print("// To regenerate it, run: ./tools/seq_decoder.py --emit-asm-macros > include/seq_macros.inc")
    print()
    def print_hword(x):
        print(f"    .byte {x} >> 8, {x} & 0xff")

    def emit_cmd(key, op, cmd):
        mn = cmd[0]
        args = cmd[1:]
        param_names = []
        param_list = []
        bits_param_name = None
        for i, arg in enumerate(args):
            param_name = chr(97 + i)
            param_names.append(param_name)
            param_list.append(param_name + ("=0" if arg.endswith(":ign") else ""))
            if arg.startswith("bits:"):
                bits_param_name = param_name
        print(f".macro {key}_{mn} {', '.join(param_list)}".rstrip())
        if bits_param_name is not None:
            print(f"    .byte {hex(op)} + \\{bits_param_name}")
        else:
            print(f"    .byte {hex(op)}")
        for arg, param_name in zip(args, param_names):
            if arg.startswith('bits:'):
                pass
            elif arg in ['s8', 'u8', 'hex8']:
                print(f"    .byte \\{param_name}")
            elif arg in ['u16', 'hex16']:
                print_hword("\\" + param_name)
            elif arg == 'addr':
                print_hword(f"(\\{param_name} - sequence_start)")
            elif arg == 'var_long':
                print(f"    var_long \\{param_name}")
            elif arg == 'var':
                print(f"    var \\{param_name}")
            else:
                raise Exception("Unknown argument type " + arg)
        print(".endm")
        print()

    def emit_env_cmd(op, cmd):
        mn = cmd[0]
        param_names = []
        param_list = []
        for i, arg in enumerate(cmd[1:]):
            param_name = chr(97 + i)
            param_names.append(param_name)
            param_list.append(param_name + ("=0" if arg.endswith(":ign") else ""))
        print(f".macro envelope_{mn} {', '.join(param_list)}".rstrip())
        if op is not None:
            print(f"    .byte {hex(op >> 8)}, {hex(op & 0xff)}")
        for param in param_names:
            print_hword("\\" + param)
        print(".endm\n")

    for key in ['seq', 'chan', 'layer']:
        print(f"// {key} commands\n")
        if key == 'layer':
            cmds = commands['layer_large']
            for op in sorted(commands['layer_small'].keys()):
                if op not in cmds:
                    emit_cmd(key, op, commands['layer_small'][op])
        else:
            cmds = commands[key]
        eu_sh = []
        us_jp = []
        sh = sh_chan_overrides if key == 'chan' else []
        non_sh = []
        for op in sorted(cmds.keys()):
            mn = cmds[op][0]
            if mn == 'setnotepriority':
                eu_sh.append((0xe9, ['setnotepriority', 'u8']))
                us_jp.append((op, cmds[op]))
            elif mn in ['reservenotes', 'unreservenotes']:
                eu_sh.append((op - 1, cmds[op]))
                us_jp.append((op, cmds[op]))
            elif mn in ['testlayerfinished', 'setlayer', 'ioreadval', 'freelayer']:
                non_sh.append((op, cmds[op]))
            elif mn not in ['portamento', 'writeseq']:
                emit_cmd(key, op, cmds[op])

        if key == 'chan':
            print(".macro chan_writeseq val, pos, offset")
            print("    .byte 0xc7, \\val")
            print_hword("(\\pos - sequence_start + \\offset)")
            print(".endm\n")
            print(".macro chan_writeseq_nextinstr val, offset")
            print("    .byte 0xc7, \\val")
            print_hword("(writeseq\\@ - sequence_start + \\offset)")
            print("    writeseq\\@:")
            print(".endm\n")
            print(".macro layer_portamento a, b, c")
            print("    .byte 0xc7, \\a, \\b")
            print("    .if ((\\a & 0x80) == 0)")
            print("        var \\c")
            print("    .else")
            print("        .byte \\c")
            print("    .endif")
            print(".endm\n")
            emit_cmd(key, 0xfd, ['delay_long', 'var_long'])
        if key == 'layer':
            emit_cmd(key, 0xc0, ['delay_long', 'var_long'])
            emit_cmd(key, 0x40, ['note1_long', 'bits:4', 'var_long', 'u8'])
        if eu_sh or us_jp or sh or non_sh:
            print("#ifdef VERSION_SH\n")
            for (op, cmd) in eu_sh:
                emit_cmd(key, op, cmd)
            for (op, cmd) in sh:
                emit_cmd(key, op, cmd)
            print("#else\n")
            for (op, cmd) in non_sh:
                emit_cmd(key, op, cmd)
            print("#ifdef VERSION_EU\n")
            for (op, cmd) in eu_sh:
                emit_cmd(key, op, cmd)
            print("#else\n")
            for (op, cmd) in us_jp:
                emit_cmd(key, op, cmd)
            print("#endif\n")
            print("#endif\n")

    print("// envelope commands\n")
    emit_env_cmd(0, ['disable', 'u16'])
    emit_env_cmd(2**16-1, ['hang', 'u16:ign'])
    emit_env_cmd(2**16-2, ['goto', 'u16'])
    emit_env_cmd(2**16-3, ['restart', 'u16:ign'])
    emit_env_cmd(None, ['line', 'u16', 'u16'])

    print("// other commands\n")
    print(".macro var_long x")
    print("     .byte (0x80 | (\\x & 0x7f00) >> 8), (\\x & 0xff)")
    print(".endm\n")
    print(".macro var x")
    print("    .if (\\x >= 0x80)")
    print("        var_long \\x")
    print("    .else")
    print("        .byte \\x")
    print("    .endif")
    print(".endm\n")
    print(".macro sound_ref a")
    print_hword("(\\a - sequence_start)")
    print(".endm")
    sys.exit(0)

filename = sys.argv[1]
try:
    lang = filename.split('/')[-2]
    assert lang in ['us', 'jp', 'eu', 'sh']
    seq_num = int(filename.split('/')[-1].split('_')[0], 16)
except Exception:
    lang = ''
    seq_num = -1

try:
    with open(filename, 'rb') as f:
        data = f.read()
except Exception:
    print("Error: could not open file {filename} for reading.", file=sys.stderr)
    sys.exit(1)

output = [None] * len(data)
output_instate = [None] * len(data)
label_name = [None] * len(data)
script_start = [False] * len(data)
hit_eof = False
errors = []
seq_writes = []

# Our analysis of large notes mode doesn't persist through multiple channel activations
# For simplicity, we force large notes always instead, which is valid for SM64.
force_large_notes = True

if lang in ['eu', 'sh']:
    # unreservenotes moved to 0xf0 in EU, and reservenotes took its place
    commands['chan'][0xf0] = commands['chan'][0xf1]
    commands['chan'][0xf1] = commands['chan'][0xf2]
    del commands['chan'][0xf2]
    # total guess: the same is true for the 'seq'-type command
    commands['seq'][0xf0] = commands['seq'][0xf1]
    commands['seq'][0xf1] = commands['seq'][0xf2]
    del commands['seq'][0xf2]
    # setnotepriority moved to 0xe9, becoming a non-arg command
    commands['chan'][0xe9] = ['setnotepriority', 'u8']
    del commands['chan'][0x60]

if lang == 'sh':
    del commands['chan'][0x00]
    del commands['chan'][0xa0]
    for op, cmd_list in sh_chan_overrides:
        commands['chan'][op] = cmd_list

def gen_label(ind, tp):
    nice_tp = tp.replace('_small', '').replace('_large', '')
    addr = hex(ind)[2:].upper()
    ret = f".{nice_tp}_{addr}"
    if ind >= len(data):
        errors.append(f"reference to oob label {ret}")
        return ret

    if label_name[ind] is not None:
        return label_name[ind]
    label_name[ind] = ret
    return ret

def gen_mnemonic(tp, b):
    nice_tp = tp.split('_')[0]
    mn = commands[tp][b][0]
    if not mn:
        mn = f"{b:02X}"
    return f"{nice_tp}_{mn}"

decode_list = []

def decode_one(state):
    pos, tp, nesting, large = state
    orig_pos = pos

    if pos >= len(data):
        global hit_eof
        hit_eof = True
        return

    if output[pos] is not None:
        if output_instate[pos] != state:
            errors.append(f"got to {gen_label(orig_pos, tp)} with both state {state} and {output_instate[pos]}")
        return

    def u8():
        nonlocal pos
        global hit_eof
        if pos == len(data):
            hit_eof = True
            return 0
        ret = data[pos]
        pos += 1
        return ret

    def u16():
        hi = u8()
        lo = u8()
        return (hi << 8) | lo

    def var():
        ret = u8()
        if ret & 0x80:
            ret = (ret << 8) & 0x7f00;
            ret |= u8()
            return (ret, ret < 0x80)
        return (ret, False)

    if tp == 'soundref':
        sound = u16()
        decode_list.append((sound, 'chan', 0, True))
        if sound < len(data):
            script_start[sound] = True
        for p in range(orig_pos, pos):
            output[p] = ''
            output_instate[p] = state
        output[orig_pos] = 'sound_ref ' + gen_label(sound, 'chan')
        return

    if tp == 'envelope':
        a = u16()
        b = u16()
        for p in range(orig_pos, pos):
            output[p] = ''
            output_instate[p] = state
        if a >= 2**16 - 3:
            a -= 2**16
        if a <= 0:
            mn = ['disable', 'hang', 'goto', 'restart'][-a]
            output[orig_pos] = f'envelope_{mn} {b}'
            # assume any goto is backwards and stop decoding
        else:
            output[orig_pos] = f'envelope_line {a} {b}'
            decode_list.append((pos, tp, nesting, large))
        return

    ins_byte = u8()

    cmds = commands[tp]
    nbits = 0
    used_b = ins_byte
    while True:
        if used_b in cmds and valid_cmd_for_nbits(cmds[used_b], nbits):
            break
        used_b &= ~(1 << nbits)
        nbits += 1
        if nbits == 8:
            errors.append(f"unrecognized instruction {hex(ins_byte)} for type {tp} at label {gen_label(orig_pos, tp)}")
            return

    out_mn = gen_mnemonic(tp, used_b)
    out_args = []
    cmd_mn = cmds[used_b][0]
    cmd_args = cmds[used_b][1:]
    long_var = False

    for a in cmd_args:
        if cmd_mn == 'portamento' and len(out_args) == 2 and (int(out_args[0], 0) & 0x80) == 0:
            a = 'var'
        if a.startswith('bits:'):
            low_bits = ins_byte & ((1 << nbits) - 1)
            if not (a.endswith(':ign') and low_bits == 0):
                out_args.append(str(low_bits))
        elif a == 'u8':
            out_args.append(str(u8()))
        elif a == 'hex8':
            out_args.append(hex(u8()))
        elif a == 's8':
            v = u8()
            out_args.append(str(v if v < 128 else v - 256))
        elif a == 'u16':
            out_args.append(str(u16()))
        elif a == 'hex16':
            out_args.append(hex(u16()))
        elif a == 'var':
            val, bad = var()
            out_args.append(hex(val))
            if bad:
                long_var = True
        elif a == 'addr':
            v = u16()

            kind = 'addr'
            if cmd_mn == 'call':
                kind = tp + '_fn'
            elif cmd_mn in ['jump', 'beqz', 'bltz', 'bgez']:
                kind = tp
            elif cmd_mn == 'startchannel':
                kind = 'chan'
            elif cmd_mn == 'setlayer':
                kind = 'layer'
            elif cmd_mn == 'setdyntable':
                kind = 'table'
            elif cmd_mn == 'setenvelope':
                kind = 'envelope'

            if v >= len(data):
                label = gen_label(v, kind)
                out_args.append(label)
                errors.append(f"reference to oob label {label}")
            elif cmd_mn == 'writeseq':
                out_args.append('<fixup>')
                seq_writes.append((orig_pos, v))
            else:
                out_args.append(gen_label(v, kind))
                if cmd_mn == 'call':
                    decode_list.append((v, tp, 0, large))
                    script_start[v] = True
                elif cmd_mn in ['jump', 'beqz', 'bltz', 'bgez']:
                    decode_list.append((v, tp, nesting, large))
                elif cmd_mn == 'startchannel':
                    decode_list.append((v, 'chan', 0, force_large_notes))
                    script_start[v] = True
                elif cmd_mn == 'setlayer':
                    if large:
                        decode_list.append((v, 'layer_large', 0, True))
                    else:
                        decode_list.append((v, 'layer_small', 0, True))
                    script_start[v] = True
                elif cmd_mn == 'setenvelope':
                    decode_list.append((v, 'envelope', 0, True))
                    script_start[v] = True
                else:
                    script_start[v] = True

    out_all = out_mn
    if long_var:
        out_all += "_long"
    if out_args:
        out_all += ' '
        out_all += ', '.join(out_args)
    for p in range(orig_pos, pos):
        output[p] = ''
        output_instate[p] = state
    output[orig_pos] = out_all

    if cmd_mn in ['hang', 'jump']:
        return
    if cmd_mn in ['loop']:
        nesting += 1
    if cmd_mn == 'end':
        nesting -= 1
    if cmd_mn in ['break', 'loopend']:
        nesting -= 1
        if nesting < 0:
            # This is iffy, and actually happens in sequence 0. It will make us
            # return to the caller's caller at function end.
            nesting = 0
    if cmd_mn == 'largenoteson':
        large = True
    if cmd_mn == 'largenotesoff':
        large = False
    if nesting >= 0:
        decode_list.append((pos, tp, nesting, large))

def decode_rec(state, initial):
    if not initial:
        v = state[0]
        gen_label(v, state[1])
        script_start[v] = True
    decode_list.append(state)
    while decode_list:
        decode_one(decode_list.pop())

def main():
    decode_rec((0, 'seq', 0, False), initial=True)

    if seq_num == 0:
        if lang == 'jp':
            sound_banks = [
                (0x14C, 0x70),
                (0x8A8, 0x38), # stated as 0x30
                (0xB66, 0x38), # stated as 0x30
                (0xE09, 0x80),
                (0x194B, 0x28), # stated as 0x20
                (0x1CA6, 0x80),
                (0x27C9, 0x20),
                (0x2975, 0x30),
                # same script as bank 3
                # same script as bank 5
            ]
            unused = [
                (0x1FC4, 'layer_large'),
                (0x2149, 'layer_large'),
                (0x2223, 'layer_large'),
                (0x28C5, 'chan'),
                (0x3110, 'envelope'),
                (0x31EC, 'envelope'),
            ]
        elif lang == 'us':
            sound_banks = [
                (0x14C, 0x70),
                (0x8F6, 0x38), # stated as 0x30
                (0xBB4, 0x40),
                (0xF8E, 0x80),
                (0x1AF3, 0x28), # stated as 0x20
                (0x1E4E, 0x80),
                (0x2971, 0x20),
                (0x2B1D, 0x40),
                # same script as bank 3
                # same script as bank 5
            ]
            unused = [
                (0x216C, 'layer_large'),
                (0x22F1, 'layer_large'),
                (0x23CB, 'layer_large'),
                (0x2A6D, 'chan'),
                (0x339C, 'envelope'),
                (0x3478, 'envelope'),
            ]
        elif lang == 'eu':
            sound_banks = [
                (0x154, 0x70),
                (0x8FE, 0x38), # stated as 0x30
                (0xBBC, 0x40),
                (0xFA5, 0x80),
                (0x1B0C, 0x28), # stated as 0x20
                (0x1E67, 0x80),
                (0x298A, 0x20),
                (0x2B36, 0x40),
                # same script as bank 3
                # same script as bank 5
            ]
            unused = [
                (0xF9A, 'chan'),
                (0x2185, 'layer_large'),
                (0x230A, 'layer_large'),
                (0x23E4, 'layer_large'),
                (0x2A86, 'chan'),
                (0x33CC, 'envelope'),
                (0x34A8, 'envelope'),
            ]
        elif lang == 'sh':
            sound_banks = [
                (0x154, 0x70),
                (0x8FE, 0x38), # stated as 0x30
                (0xBBC, 0x40),
                (0xFA5, 0x80),
                (0x1B11, 0x28), # stated as 0x20
                (0x1E6C, 0x80),
                (0x298F, 0x20),
                (0x2B3B, 0x40),
                # same script as bank 3
                # same script as bank 5
            ]

            unused = [
                (0xF9A, 'chan'),
                (0x218A, 'layer_large'),
                (0x230F, 'layer_large'),
                (0x23E9, 'layer_large'),
                (0x2A8B, 'chan'),
                (0x33D0, 'envelope'),
                (0x34AC, 'envelope'),
            ]


        for (addr, count) in sound_banks:
            for i in range(count):
                decode_rec((addr + 2*i, 'soundref', 0, False), initial=True)

        for (addr, tp) in unused:
            gen_label(addr, tp + '_unused')
            decode_rec((addr, tp, 0, force_large_notes), initial=False)

    for (pos, write_to) in seq_writes:
        assert '<fixup>' in output[pos]
        delta = 0
        while output[write_to] == '':
            write_to -= 1
            delta += 1
        if write_to > pos and all(output[i] == '' for i in range(pos+1, write_to)):
            nice_target = str(delta)
            output[pos] = output[pos].replace('writeseq', 'writeseq_nextinstr')
        else:
            tp = output_instate[write_to][1] if output_instate[write_to] is not None else 'addr'
            nice_target = gen_label(write_to, tp) + ", " + str(delta)
        output[pos] = output[pos].replace('<fixup>', nice_target)

    # Add unreachable 'end' markers
    for i in range(1, len(data)):
        if (data[i] == 0xff and output[i] is None and output[i - 1] is not None
                and label_name[i] is None):
            tp = output_instate[i - 1][1]
            if tp in ["seq", "chan", "layer_small", "layer_large"]:
                output[i] = gen_mnemonic(tp, 0xff)

    # Add envelope padding
    for i in range(1, len(data) - 1):
        if (data[i] == 0 and output[i] is None and output[i - 1] is not None and
                output[i + 1] is not None and label_name[i] is None and
                output[i + 1].startswith('envelope')):
            script_start[i] = True
            output[i] = "# padding\n.byte 0"

    # Add 'unused' marker labels
    for i in range(1, len(data)):
        if (output[i] is None and output[i - 1] is not None and label_name[i] is None):
            script_start[i] = True
            gen_label(i, 'unused')

    # Remove up to 15 bytes of padding at the end
    end_padding = 0
    for i in range(len(data)-1, -1, -1):
        if output[i] is not None:
            break
        end_padding += 1
    if end_padding > 15:
        end_padding = 0

    if print_end_padding:
        print(end_padding)
        sys.exit(0)

    print(".include \"seq_macros.inc\"")
    print(".section .rodata")
    print(".align 0")
    print("sequence_start:")
    print()
    for i in range(len(data) - end_padding):
        if script_start[i] and i > 0:
            print()
        if label_name[i] is not None:
            print(f"{label_name[i]}:")
        o = output[i]
        if o is None:
            print(f".byte {hex(data[i])}")
        elif o:
            print(o)
        elif label_name[i] is not None:
            print("<mid-instruction>")
            errors.append(f"mid-instruction label {label_name[i]}")

    if hit_eof:
        errors.append("hit eof!?")

    if errors:
        print(f"[{filename}] errors:", file=sys.stderr)
        for w in errors:
            print(w, file=sys.stderr)

main()
