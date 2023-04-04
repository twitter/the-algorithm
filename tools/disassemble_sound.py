#!/usr/bin/env python3
from collections import namedtuple, defaultdict
import tempfile
import subprocess
import uuid
import json
import os
import re
import struct
import sys

TYPE_CTL = 1
TYPE_TBL = 2


class AifcEntry:
    def __init__(self, data, book, loop):
        self.name = None
        self.data = data
        self.book = book
        self.loop = loop
        self.tunings = []


class SampleBank:
    def __init__(self, name, data, offset):
        self.offset = offset
        self.name = name
        self.data = data
        self.entries = {}

    def add_sample(self, offset, sample_size, book, loop):
        assert sample_size % 2 == 0
        if sample_size % 9 != 0:
            assert sample_size % 9 == 1
            sample_size -= 1

        if offset in self.entries:
            entry = self.entries[offset]
            assert entry.book == book
            assert entry.loop == loop
            assert len(entry.data) == sample_size
        else:
            entry = AifcEntry(self.data[offset : offset + sample_size], book, loop)
            self.entries[offset] = entry

        return entry


Sound = namedtuple("Sound", ["sample_addr", "tuning"])
Drum = namedtuple("Drum", ["name", "addr", "release_rate", "pan", "envelope", "sound"])
Inst = namedtuple(
    "Inst",
    [
        "name",
        "addr",
        "release_rate",
        "normal_range_lo",
        "normal_range_hi",
        "envelope",
        "sound_lo",
        "sound_med",
        "sound_hi",
    ],
)
Book = namedtuple("Book", ["order", "npredictors", "table"])
Loop = namedtuple("Loop", ["start", "end", "count", "state"])
Envelope = namedtuple("Envelope", ["name", "entries"])
Bank = namedtuple(
    "Bank",
    [
        "name",
        "iso_date",
        "sample_bank",
        "insts",
        "drums",
        "all_insts",
        "inst_list",
        "envelopes",
        "samples",
    ],
)


def align(val, al):
    return (val + (al - 1)) & -al


name_tbl = {}


def gen_name(prefix, name_table=[]):
    if prefix not in name_tbl:
        name_tbl[prefix] = 0
    ind = name_tbl[prefix]
    name_tbl[prefix] += 1
    if ind < len(name_table):
        return name_table[ind]
    return prefix + str(ind)


def parse_bcd(data):
    ret = 0
    for c in data:
        ret *= 10
        ret += c >> 4
        ret *= 10
        ret += c & 15
    return ret


def serialize_f80(num):
    num = float(num)
    (f64,) = struct.unpack(">Q", struct.pack(">d", num))
    f64_sign_bit = f64 & 2 ** 63
    if num == 0.0:
        if f64_sign_bit:
            return b"\x80" + b"\0" * 9
        else:
            return b"\0" * 10
    exponent = (f64 ^ f64_sign_bit) >> 52
    assert exponent != 0, "can't handle denormals"
    assert exponent != 0x7FF, "can't handle infinity/nan"
    exponent -= 1023
    f64_mantissa_bits = f64 & (2 ** 52 - 1)
    f80_sign_bit = f64_sign_bit << (80 - 64)
    f80_exponent = (exponent + 0x3FFF) << 64
    f80_mantissa_bits = 2 ** 63 | (f64_mantissa_bits << (63 - 52))
    f80 = f80_sign_bit | f80_exponent | f80_mantissa_bits
    return struct.pack(">HQ", f80 >> 64, f80 & (2 ** 64 - 1))


def round_f32(num):
    enc = struct.pack(">f", num)
    for decimals in range(5, 20):
        num2 = round(num, decimals)
        if struct.pack(">f", num2) == enc:
            return num2
    return num


def parse_sound(data):
    sample_addr, tuning = struct.unpack(">If", data)
    if sample_addr == 0:
        assert tuning == 0
        return None
    return Sound(sample_addr, tuning)


def parse_drum(data, addr):
    name = gen_name("drum")
    release_rate, pan, loaded, pad = struct.unpack(">BBBB", data[:4])
    assert loaded == 0
    assert pad == 0
    sound = parse_sound(data[4:12])
    (env_addr,) = struct.unpack(">I", data[12:])
    assert env_addr != 0
    return Drum(name, addr, release_rate, pan, env_addr, sound)


def parse_inst(data, addr):
    name = gen_name("inst")
    loaded, normal_range_lo, normal_range_hi, release_rate, env_addr = struct.unpack(
        ">BBBBI", data[:8]
    )
    assert env_addr != 0
    sound_lo = parse_sound(data[8:16])
    sound_med = parse_sound(data[16:24])
    sound_hi = parse_sound(data[24:])
    if sound_lo is None:
        assert normal_range_lo == 0
    if sound_hi is None:
        assert normal_range_hi == 127
    return Inst(
        name,
        addr,
        release_rate,
        normal_range_lo,
        normal_range_hi,
        env_addr,
        sound_lo,
        sound_med,
        sound_hi,
    )


def parse_loop(addr, bank_data):
    start, end, count, pad = struct.unpack(">IIiI", bank_data[addr : addr + 16])
    assert pad == 0
    if count != 0:
        state = struct.unpack(">16h", bank_data[addr + 16 : addr + 48])
    else:
        state = None
    return Loop(start, end, count, state)


def parse_book(addr, bank_data):
    order, npredictors = struct.unpack(">ii", bank_data[addr : addr + 8])
    assert order == 2
    assert npredictors == 2
    table_data = bank_data[addr + 8 : addr + 8 + 16 * order * npredictors]
    table = []
    for i in range(0, 16 * order * npredictors, 2):
        table.append(struct.unpack(">h", table_data[i : i + 2])[0])
    return Book(order, npredictors, table)


def parse_sample(data, bank_data, sample_bank, is_shindou):
    if is_shindou:
        sample_size, addr, loop, book = struct.unpack(">IIII", data)
    else:
        zero, addr, loop, book, sample_size = struct.unpack(">IIIII", data)
        assert zero == 0
    assert loop != 0
    assert book != 0
    loop = parse_loop(loop, bank_data)
    book = parse_book(book, bank_data)
    return sample_bank.add_sample(addr, sample_size, book, loop)


def parse_envelope(addr, data_bank):
    entries = []
    while True:
        delay, arg = struct.unpack(">HH", data_bank[addr : addr + 4])
        entries.append((delay, arg))
        addr += 4
        if 1 <= (-delay) % 2 ** 16 <= 3:
            break
    return entries


def parse_ctl_header(header):
    num_instruments, num_drums, shared = struct.unpack(">III", header[:12])
    date = parse_bcd(header[12:])
    y = date // 10000
    m = date // 100 % 100
    d = date % 100
    iso_date = "{:02}-{:02}-{:02}".format(y, m, d)
    assert shared in [0, 1]
    return num_instruments, num_drums, iso_date


def parse_ctl(parsed_header, data, sample_bank, index, is_shindou):
    name_tbl.clear()
    name = "{:02X}".format(index)
    num_instruments, num_drums, iso_date = parsed_header
    # print("{}: {}, {} + {}".format(name, iso_date, num_instruments, num_drums))

    (drum_base_addr,) = struct.unpack(">I", data[:4])
    drum_addrs = []
    if num_drums != 0:
        assert drum_base_addr != 0
        for i in range(num_drums):
            (drum_addr,) = struct.unpack(
                ">I", data[drum_base_addr + i * 4 : drum_base_addr + i * 4 + 4]
            )
            assert drum_addr != 0
            drum_addrs.append(drum_addr)
    else:
        assert drum_base_addr == 0

    inst_base_addr = 4
    inst_addrs = []
    inst_list = []
    for i in range(num_instruments):
        (inst_addr,) = struct.unpack(
            ">I", data[inst_base_addr + i * 4 : inst_base_addr + i * 4 + 4]
        )
        if inst_addr == 0:
            inst_list.append(None)
        else:
            inst_list.append(inst_addr)
            inst_addrs.append(inst_addr)

    inst_addrs.sort()
    assert drum_addrs == sorted(drum_addrs)
    if drum_addrs and inst_addrs:
        assert max(inst_addrs) < min(drum_addrs)

    assert len(set(inst_addrs)) == len(inst_addrs)
    assert len(set(drum_addrs)) == len(drum_addrs)

    insts = []
    for inst_addr in inst_addrs:
        insts.append(parse_inst(data[inst_addr : inst_addr + 32], inst_addr))

    drums = []
    for drum_addr in drum_addrs:
        drums.append(parse_drum(data[drum_addr : drum_addr + 16], drum_addr))

    env_addrs = set()
    sample_addrs = set()
    tunings = defaultdict(lambda: [])
    for inst in insts:
        for sound in [inst.sound_lo, inst.sound_med, inst.sound_hi]:
            if sound is not None:
                sample_addrs.add(sound.sample_addr)
                tunings[sound.sample_addr].append(sound.tuning)
        env_addrs.add(inst.envelope)
    for drum in drums:
        sample_addrs.add(drum.sound.sample_addr)
        tunings[drum.sound.sample_addr].append(drum.sound.tuning)
        env_addrs.add(drum.envelope)

    # Put drums somewhere in the middle of the instruments to make sample
    # addresses come in increasing order. (This logic isn't totally right,
    # but it works for our purposes.)
    all_insts = []
    need_drums = len(drums) > 0
    for inst in insts:
        if need_drums and any(
            s.sample_addr > drums[0].sound.sample_addr
            for s in [inst.sound_lo, inst.sound_med, inst.sound_hi]
            if s is not None
        ):
            all_insts.append(drums)
            need_drums = False
        all_insts.append(inst)

    if need_drums:
        all_insts.append(drums)

    samples = {}
    for addr in sorted(sample_addrs):
        sample_size = 16 if is_shindou else 20
        sample_data = data[addr : addr + sample_size]
        samples[addr] = parse_sample(sample_data, data, sample_bank, is_shindou)
        samples[addr].tunings.extend(tunings[addr])

    env_data = {}
    used_env_addrs = set()
    for addr in sorted(env_addrs):
        env = parse_envelope(addr, data)
        env_data[addr] = env
        for i in range(align(len(env), 4)):
            used_env_addrs.add(addr + i * 4)

    # Unused envelopes
    unused_envs = set()
    if used_env_addrs:
        for addr in range(min(used_env_addrs) + 4, max(used_env_addrs), 4):
            if addr not in used_env_addrs:
                unused_envs.add(addr)
                (stub_marker,) = struct.unpack(">I", data[addr : addr + 4])
                assert stub_marker == 0
                env = parse_envelope(addr, data)
                env_data[addr] = env
                for i in range(align(len(env), 4)):
                    used_env_addrs.add(addr + i * 4)

    envelopes = {}
    for addr in sorted(env_data.keys()):
        env_name = gen_name("envelope")
        if addr in unused_envs:
            env_name += "_unused"
        envelopes[addr] = Envelope(env_name, env_data[addr])

    return Bank(
        name,
        iso_date,
        sample_bank,
        insts,
        drums,
        all_insts,
        inst_list,
        envelopes,
        samples,
    )


def parse_seqfile(data, filetype):
    magic, num_entries = struct.unpack(">HH", data[:4])
    assert magic == filetype
    prev = align(4 + num_entries * 8, 16)
    entries = []
    for i in range(num_entries):
        offset, length = struct.unpack(">II", data[4 + i * 8 : 4 + i * 8 + 8])
        if filetype == TYPE_CTL:
            assert offset == prev
        else:
            assert offset <= prev
        prev = max(prev, offset + length)
        entries.append((offset, length))
    assert all(x == 0 for x in data[prev:])
    return entries


def parse_sh_header(data, filetype):
    (num_entries,) = struct.unpack(">H", data[:2])
    assert data[2:16] == b"\0" * 14
    prev = 0
    entries = []
    for i in range(num_entries):
        subdata = data[16 + 16 * i : 32 + 16 * i]
        offset, length, magic = struct.unpack(">IIH", subdata[:10])
        assert offset == prev
        assert magic == (0x0204 if filetype == TYPE_TBL else 0x0203)
        prev = offset + length
        if filetype == TYPE_CTL:
            assert subdata[14:16] == b"\0" * 2
            sample_bank_index, magic2, num_instruments, num_drums = struct.unpack(
                ">BBBB", subdata[10:14]
            )
            assert magic2 == 0xFF
            entries.append(
                (offset, length, (sample_bank_index, num_instruments, num_drums))
            )
        else:
            assert subdata[10:16] == b"\0" * 6
            entries.append((offset, length))
    return entries


def parse_tbl(data, entries):
    seen = {}
    tbls = []
    sample_banks = []
    sample_bank_map = {}
    for (offset, length) in entries:
        if offset not in seen:
            name = gen_name("sample_bank")
            seen[offset] = name
            sample_bank = SampleBank(name, data[offset : offset + length], offset)
            sample_banks.append(sample_bank)
            sample_bank_map[name] = sample_bank
        tbls.append(seen[offset])
    return tbls, sample_banks, sample_bank_map


class AifcWriter:
    def __init__(self, out):
        self.out = out
        self.sections = []
        self.total_size = 0

    def add_section(self, tp, data):
        assert isinstance(tp, bytes)
        assert isinstance(data, bytes)
        self.sections.append((tp, data))
        self.total_size += align(len(data), 2) + 8

    def add_custom_section(self, tp, data):
        self.add_section(b"APPL", b"stoc" + self.pstring(tp) + data)

    def pstring(self, data):
        return bytes([len(data)]) + data + (b"" if len(data) % 2 else b"\0")

    def finish(self):
        # total_size isn't used, and is regularly wrong. In particular, vadpcm_enc
        # preserves the size of the input file...
        self.total_size += 4
        self.out.write(b"FORM" + struct.pack(">I", self.total_size) + b"AIFC")
        for (tp, data) in self.sections:
            self.out.write(tp + struct.pack(">I", len(data)))
            self.out.write(data)
            if len(data) % 2:
                self.out.write(b"\0")


def write_aifc(entry, out):
    writer = AifcWriter(out)
    num_channels = 1
    data = entry.data
    assert len(data) % 9 == 0
    if len(data) % 2 == 1:
        data += b"\0"
    # (Computing num_frames this way makes it off by one when the data length
    # is odd. It matches vadpcm_enc, though.)
    num_frames = len(data) * 16 // 9
    sample_size = 16  # bits per sample

    if len(set(entry.tunings)) == 1:
        sample_rate = 32000 * entry.tunings[0]
    else:
        # Some drum sounds in sample bank B don't have unique sample rates, so
        # we have to guess. This doesn't matter for matching, it's just to make
        # the sounds easy to listen to.
        if min(entry.tunings) <= 0.5 <= max(entry.tunings):
            sample_rate = 16000
        elif min(entry.tunings) <= 1.0 <= max(entry.tunings):
            sample_rate = 32000
        elif min(entry.tunings) <= 1.5 <= max(entry.tunings):
            sample_rate = 48000
        elif min(entry.tunings) <= 2.5 <= max(entry.tunings):
            sample_rate = 80000
        else:
            sample_rate = 16000 * (min(entry.tunings) + max(entry.tunings))

    writer.add_section(
        b"COMM",
        struct.pack(">hIh", num_channels, num_frames, sample_size)
        + serialize_f80(sample_rate)
        + b"VAPC"
        + writer.pstring(b"VADPCM ~4-1"),
    )
    writer.add_section(b"INST", b"\0" * 20)
    table_data = b"".join(struct.pack(">h", x) for x in entry.book.table)
    writer.add_custom_section(
        b"VADPCMCODES",
        struct.pack(">hhh", 1, entry.book.order, entry.book.npredictors) + table_data,
    )
    writer.add_section(b"SSND", struct.pack(">II", 0, 0) + data)
    if entry.loop.count != 0:
        writer.add_custom_section(
            b"VADPCMLOOPS",
            struct.pack(
                ">HHIIi16h",
                1,
                1,
                entry.loop.start,
                entry.loop.end,
                entry.loop.count,
                *entry.loop.state
            ),
        )
    writer.finish()


def write_aiff(entry, filename):
    temp = tempfile.NamedTemporaryFile(suffix=".aifc", delete=False)
    try:
        write_aifc(entry, temp)
        temp.flush()
        temp.close()
        aifc_decode = os.path.join(os.path.dirname(__file__), "aifc_decode")
        subprocess.run([aifc_decode, temp.name, filename], check=True)
    finally:
        temp.close()
        os.remove(temp.name)


# Modified from https://stackoverflow.com/a/25935321/1359139, cc by-sa 3.0
class NoIndent(object):
    def __init__(self, value):
        self.value = value


class NoIndentEncoder(json.JSONEncoder):
    def __init__(self, *args, **kwargs):
        super(NoIndentEncoder, self).__init__(*args, **kwargs)
        self._replacement_map = {}

    def default(self, o):
        def ignore_noindent(o):
            if isinstance(o, NoIndent):
                return o.value
            return self.default(o)

        if isinstance(o, NoIndent):
            key = uuid.uuid4().hex
            self._replacement_map[key] = json.dumps(o.value, default=ignore_noindent)
            return "@@%s@@" % (key,)
        else:
            return super(NoIndentEncoder, self).default(o)

    def encode(self, o):
        result = super(NoIndentEncoder, self).encode(o)
        repl_map = self._replacement_map

        def repl(m):
            key = m.group()[3:-3]
            return repl_map[key]

        return re.sub(r"\"@@[0-9a-f]*?@@\"", repl, result)


def inst_ifdef_json(bank_index, inst_index):
    if bank_index == 7 and inst_index >= 13:
        return NoIndent(["VERSION_US", "VERSION_EU"])
    if bank_index == 8 and inst_index >= 16:
        return NoIndent(["VERSION_US", "VERSION_EU"])
    if bank_index == 10 and inst_index >= 14:
        return NoIndent(["VERSION_US", "VERSION_EU"])
    return None


def main():
    args = []
    need_help = False
    only_samples = False
    only_samples_list = []
    shindou_headers = None
    skip_next = 0
    for i, a in enumerate(sys.argv[1:], 1):
        if skip_next > 0:
            skip_next -= 1
            continue
        if a == "--help" or a == "-h":
            need_help = True
        elif a == "--only-samples":
            only_samples = True
        elif a == "--shindou-headers":
            shindou_headers = sys.argv[i + 1 : i + 5]
            skip_next = 4
        elif a.startswith("-"):
            print("Unrecognized option " + a)
            sys.exit(1)
        elif only_samples:
            only_samples_list.append(a)
        else:
            args.append(a)

    expected_num_args = 5 + (0 if only_samples else 2)
    if (
        need_help
        or len(args) != expected_num_args
        or (shindou_headers and len(shindou_headers) != 4)
    ):
        print(
            "Usage: {}"
            " <.z64 rom> <ctl offset> <ctl size> <tbl offset> <tbl size>"
            " [--shindou-headers <ctl header offset> <ctl header size>"
            " <tbl header offset> <tbl header size>]"
            " (<samples outdir> <sound bank outdir> |"
            " --only-samples file:index ...)".format(sys.argv[0])
        )
        sys.exit(0 if need_help else 1)

    rom_file = open(args[0], "rb")

    def read_at(offset, size):
        rom_file.seek(int(offset))
        return rom_file.read(int(size))

    ctl_data = read_at(args[1], args[2])
    tbl_data = read_at(args[3], args[4])

    ctl_header_data = None
    tbl_header_data = None
    if shindou_headers:
        ctl_header_data = read_at(shindou_headers[0], shindou_headers[1])
        tbl_header_data = read_at(shindou_headers[2], shindou_headers[3])

    if not only_samples:
        samples_out_dir = args[5]
        banks_out_dir = args[6]

    banks = []

    if shindou_headers:
        ctl_entries = parse_sh_header(ctl_header_data, TYPE_CTL)
        tbl_entries = parse_sh_header(tbl_header_data, TYPE_TBL)

        sample_banks = parse_tbl(tbl_data, tbl_entries)[1]

        for index, (offset, length, sh_meta) in enumerate(ctl_entries):
            sample_bank = sample_banks[sh_meta[0]]
            entry = ctl_data[offset : offset + length]
            header = (sh_meta[1], sh_meta[2], "0000-00-00")
            banks.append(parse_ctl(header, entry, sample_bank, index, True))
    else:
        ctl_entries = parse_seqfile(ctl_data, TYPE_CTL)
        tbl_entries = parse_seqfile(tbl_data, TYPE_TBL)
        assert len(ctl_entries) == len(tbl_entries)

        tbls, sample_banks, sample_bank_map = parse_tbl(tbl_data, tbl_entries)

        for index, (offset, length), sample_bank_name in zip(
            range(len(ctl_entries)), ctl_entries, tbls
        ):
            sample_bank = sample_bank_map[sample_bank_name]
            entry = ctl_data[offset : offset + length]
            header = parse_ctl_header(entry[:16])
            banks.append(parse_ctl(header, entry[16:], sample_bank, index, False))

    # Special mode used for asset extraction: generate aifc files, with paths
    # given by command line arguments
    if only_samples:
        index_to_filename = {}
        created_dirs = set()
        for arg in only_samples_list:
            filename, index = arg.rsplit(":", 1)
            index_to_filename[int(index)] = filename
        index = -1
        for sample_bank in sample_banks:
            offsets = sorted(set(sample_bank.entries.keys()))
            for offset in offsets:
                entry = sample_bank.entries[offset]
                index += 1
                if index in index_to_filename:
                    filename = index_to_filename[index]
                    dir = os.path.dirname(filename)
                    if dir not in created_dirs:
                        os.makedirs(dir, exist_ok=True)
                        created_dirs.add(dir)
                    write_aiff(entry, filename)
        return

    # Generate aiff files
    for sample_bank in sample_banks:
        dir = os.path.join(samples_out_dir, sample_bank.name)
        os.makedirs(dir, exist_ok=True)

        offsets = sorted(set(sample_bank.entries.keys()))
        # print(sample_bank.name, len(offsets), 'entries')
        offsets.append(len(sample_bank.data))

        assert 0 in offsets
        for offset, next_offset, index in zip(
            offsets, offsets[1:], range(len(offsets))
        ):
            entry = sample_bank.entries[offset]
            entry.name = "{:02X}".format(index)
            size = next_offset - offset
            assert size % 16 == 0
            assert size - 15 <= len(entry.data) <= size
            garbage = sample_bank.data[offset + len(entry.data) : offset + size]
            if len(entry.data) % 2 == 1:
                assert garbage[0] == 0
            if next_offset != offsets[-1]:
                # (The last chunk follows a more complex garbage pattern)
                assert all(x == 0 for x in garbage)
            filename = os.path.join(dir, entry.name + ".aiff")
            write_aiff(entry, filename)

    # Generate sound bank .json files
    os.makedirs(banks_out_dir, exist_ok=True)
    for bank_index, bank in enumerate(banks):
        filename = os.path.join(banks_out_dir, bank.name + ".json")
        with open(filename, "w") as out:

            def sound_to_json(sound):
                entry = bank.samples[sound.sample_addr]
                if len(set(entry.tunings)) == 1:
                    return entry.name
                return {"sample": entry.name, "tuning": round_f32(sound.tuning)}

            bank_json = {
                "date": bank.iso_date,
                "sample_bank": bank.sample_bank.name,
                "envelopes": {},
                "instruments": {},
                "instrument_list": [],
            }
            addr_to_name = {}

            # Envelopes
            for env in bank.envelopes.values():
                env_json = []
                for (delay, arg) in env.entries:
                    if delay == 0:
                        ins = "stop"
                        assert arg == 0
                    elif delay == 2 ** 16 - 1:
                        ins = "hang"
                        assert arg == 0
                    elif delay == 2 ** 16 - 2:
                        ins = ["goto", arg]
                    elif delay == 2 ** 16 - 3:
                        ins = "restart"
                        assert arg == 0
                    else:
                        ins = [delay, arg]
                    env_json.append(NoIndent(ins))
                bank_json["envelopes"][env.name] = env_json

            # Instruments/drums
            for inst_index, inst in enumerate(bank.all_insts):
                if isinstance(inst, Inst):
                    inst_json = {
                        "ifdef": inst_ifdef_json(bank_index, inst_index),
                        "release_rate": inst.release_rate,
                        "normal_range_lo": inst.normal_range_lo,
                        "normal_range_hi": inst.normal_range_hi,
                        "envelope": bank.envelopes[inst.envelope].name,
                    }

                    if inst_json["ifdef"] is None:
                        del inst_json["ifdef"]

                    if inst.sound_lo is not None:
                        inst_json["sound_lo"] = NoIndent(sound_to_json(inst.sound_lo))
                    else:
                        del inst_json["normal_range_lo"]

                    inst_json["sound"] = NoIndent(sound_to_json(inst.sound_med))

                    if inst.sound_hi is not None:
                        inst_json["sound_hi"] = NoIndent(sound_to_json(inst.sound_hi))
                    else:
                        del inst_json["normal_range_hi"]

                    bank_json["instruments"][inst.name] = inst_json
                    addr_to_name[inst.addr] = inst.name

                else:
                    assert isinstance(inst, list)
                    drums_list_json = []
                    for drum in inst:
                        drum_json = {
                            "release_rate": drum.release_rate,
                            "pan": drum.pan,
                            "envelope": bank.envelopes[drum.envelope].name,
                            "sound": sound_to_json(drum.sound),
                        }
                        drums_list_json.append(NoIndent(drum_json))
                    bank_json["instruments"]["percussion"] = drums_list_json

            # Instrument lists
            for addr in bank.inst_list:
                if addr is None:
                    bank_json["instrument_list"].append(None)
                else:
                    bank_json["instrument_list"].append(addr_to_name[addr])

            out.write(json.dumps(bank_json, indent=4, cls=NoIndentEncoder))
            out.write("\n")


if __name__ == "__main__":
    main()
