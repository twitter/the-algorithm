#!/usr/bin/env python3
import re
import os
import traceback
import sys

num_headers = 0
items = []
len_mapping = {}
order_mapping = {}
line_number_mapping = {}

def raise_error(filename, lineindex, msg):
    raise SyntaxError("Error in " + filename + ":" + str(line_number_mapping[lineindex] + 1) + ": " + msg)

def parse_struct(filename, lines, lineindex, name):
    global items, order_mapping
    lineindex += 1
    if lineindex + 9 >= len(lines):
        raise_error(filename, lineindex, "struct Animation must be 11 lines")
    v1 = int(lines[lineindex + 0].rstrip(","), 0)
    v2 = int(lines[lineindex + 1].rstrip(","), 0)
    v3 = int(lines[lineindex + 2].rstrip(","), 0)
    v4 = int(lines[lineindex + 3].rstrip(","), 0)
    v5 = int(lines[lineindex + 4].rstrip(","), 0)
    values = lines[lineindex + 6].rstrip(",")
    indices = lines[lineindex + 7].rstrip(",")
    items.append(("header", name, (v1, v2, v3, v4, v5, values, indices)))
    if lines[lineindex + 9] != "};":
        raise_error(filename, lineindex + 9, "Expected \"};\" but got " + lines[lineindex + 9])
    order_mapping[name] = len(items)
    lineindex += 10
    return lineindex

def parse_array(filename, lines, lineindex, name, is_indices):
    global items, len_mapping, order_mapping
    lineindex += 1
    values = []
    while lineindex < len(lines) and lines[lineindex] != "};":
        line = lines[lineindex].rstrip(",")
        if line:
            values.extend(line.split(","))
        lineindex += 1
    if lineindex >= len(lines):
        raise_error(filename, lineindex, "Expected \"};\" but reached end of file")
    items.append(("array", name, (is_indices, values)))
    len_mapping[name] = len(values)
    order_mapping[name] = len(items)
    lineindex += 1
    return lineindex

def parse_file(filename, lines):
    global num_headers
    lineindex = 0
    while lineindex < len(lines):
        line = lines[lineindex]
        for prefix in ["static ", "const "]:
            if line.startswith(prefix):
                line = line[len(prefix):]
        lines[lineindex] = line

        is_struct = line.startswith("struct Animation ") and line.endswith("[] = {")
        is_indices = line.startswith("u16 ") and line.endswith("[] = {")
        is_values = line.startswith("s16 ") and line.endswith("[] = {")
        if not is_struct and not is_indices and not is_values:
            raise_error(filename, lineindex, "\"" + line + "\" does not follow the pattern \"static const struct Animation anim_x[] = {\", \"static const u16 anim_x_indices[] = {\" or \"static const s16 anim_x_values[] = {\"")

        if is_struct:
            name = lines[lineindex][len("struct Animation "):-6]
            lineindex = parse_struct(filename, lines, lineindex, name)
            num_headers += 1
        else:
            name = lines[lineindex][len("s16 "):-6]
            lineindex = parse_array(filename, lines, lineindex, name, is_indices)

try:
    files = os.listdir("assets/anims")
    files.sort()

    for filename in files:
        if filename.endswith(".inc.c"):
            lines = []
            with open("assets/anims/" + filename) as f:
                for i, line in enumerate(f):
                    line = re.sub(r"/\*.*?\*/", "", line)
                    if "/*" in line:
                        line_number_mapping[-1] = i
                        raise_error(filename, -1, "Multiline comments are not supported")
                    line = line.split("//", 1)[0].strip()
                    if line:
                        line_number_mapping[len(lines)] = i
                        lines.append(line)
            if lines:
                parse_file(filename, lines)

    structdef = ["u32 numEntries;", "const struct Animation *addrPlaceholder;", "struct OffsetSizePair entries[" + str(num_headers) + "];"]
    structobj = [str(num_headers) + ",", "NULL,","{"]

    for item in items:
        type, name, obj = item
        if type == "header":
            v1, v2, v3, v4, v5, values, indices = obj
            if order_mapping[indices] < order_mapping[name]:
                raise SyntaxError("Error: Animation struct must be written before indices array for " + name)
            if order_mapping[values] < order_mapping[indices]:
                raise SyntaxError("Error: values array must be written after indices array for " + name)
            values_num_values = len_mapping[values]
            offset_to_struct = "offsetof(struct MarioAnimsObj, " + name + ")"
            offset_to_end = "offsetof(struct MarioAnimsObj, " + values + ") + sizeof(gMarioAnims." + values + ")"
            structobj.append("{" + offset_to_struct + ", " + offset_to_end + " - " + offset_to_struct + "},")
    structobj.append("},")

    for item in items:
        type, name, obj = item
        if type == "header":
            v1, v2, v3, v4, v5, values, indices = obj
            indices_len = len_mapping[indices] // 6 - 1
            values_num_values = len_mapping[values]
            offset_to_struct = "offsetof(struct MarioAnimsObj, " + name + ")"
            offset_to_end = "offsetof(struct MarioAnimsObj, " + values + ") + sizeof(gMarioAnims." + values + ")"
            structdef.append("struct Animation " + name + ";")
            structobj.append("{" + ", ".join([
                str(v1),
                str(v2),
                str(v3),
                str(v4),
                str(v5),
                str(indices_len),
                "(const s16 *)(offsetof(struct MarioAnimsObj, " + values + ") - " + offset_to_struct + ")",
                "(const u16 *)(offsetof(struct MarioAnimsObj, " + indices + ") - " + offset_to_struct + ")",
                offset_to_end + " - " + offset_to_struct
            ]) + "},")
        else:
            is_indices, arr = obj
            type = "u16" if is_indices else "s16"
            structdef.append("{} {}[{}];".format(type, name, len(arr)))
            structobj.append("{" + ",".join(arr) + "},")

    print("#include \"game/memory.h\"")
    print("#include <stddef.h>")
    print("")

    print("const struct MarioAnimsObj {")
    for s in structdef:
        print(s)
    print("} gMarioAnims = {")
    for s in structobj:
        print(s)
    print("};")

except Exception as e:
    note = "NOTE! The mario animation C files are not processed by a normal C compiler, but by the script in tools/mario_anims_converter.py. The format is much more strict than normal C, so please follow the syntax of existing files.\n"
    if e is SyntaxError:
        e.msg = note + e.msg
    else:
        print(note, file=sys.stderr)
    traceback.print_exc()
    sys.exit(1)
