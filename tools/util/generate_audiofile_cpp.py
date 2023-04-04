#!/usr/bin/env python

import os
import re
import sys

file_list = [
    'Features.h',
    'Compiler.h',
    'error.h',
    'extended.h',
    'compression.h',
    'aupvinternal.h',
    'aupvlist.h',
    'audiofile.h',
    'afinternal.h',
    'byteorder.h',
    'AudioFormat.h',
    'debug.h',
    'util.h',
    'units.h',
    'UUID.h',
    'Shared.h',
    'Buffer.h',
    'File.h',
    'FileHandle.h',
    'Instrument.h',
    'Track.h',
    'Marker.h',
    'Setup.h',
    'Tag.h',
    'PacketTable.h',
    'pcm.h',
    'g711.h',
    'af_vfs.h',
    'Raw.h',
    'WAVE.h',
    'SampleVision.h',
    'modules/Module.h',
    'modules/ModuleState.h',
    'modules/SimpleModule.h',
    'modules/FileModule.h',
    'modules/RebufferModule.h',
    'modules/BlockCodec.h',
    'modules/BlockCodec.cpp',
    'modules/FileModule.cpp',
    'modules/G711.h',
    'modules/G711.cpp',
    'modules/Module.cpp',
    'modules/ModuleState.cpp',
    'modules/MSADPCM.h',
    'modules/MSADPCM.cpp',
    'modules/PCM.h',
    'modules/PCM.cpp',
    'modules/SimpleModule.cpp',
    'modules/RebufferModule.cpp',
    'AIFF.h',
    'AIFF.cpp',
    'AudioFormat.cpp',
    'Buffer.cpp',
    'File.cpp',
    'FileHandle.cpp',
    'Instrument.cpp',
    'Loop.cpp',
    'Marker.cpp',
    'Miscellaneous.cpp',
    'PacketTable.cpp',
    'Raw.cpp',
    'Setup.cpp',
    'Track.cpp',
    'UUID.cpp',
    'WAVE.cpp',
    'aes.cpp',
    'af_vfs.cpp',
    'aupv.c',
    'compression.cpp',
    'data.cpp',
    'debug.cpp',
    'error.c',
    'extended.c',
    'format.cpp',
    'g711.c',
    'openclose.cpp',
    'pcm.cpp',
    'query.cpp',
    'units.cpp',
    'util.cpp',
]

file_header =  \
"""// libaudiofile b62c902
// https://github.com/mpruett/audiofile
// To simplify compilation, all files have been concatenated into one.
// Support for all formats except WAVE, AIFF(C) and RAW has been stripped out.
"""

prepend_defs =  \
"""#define HAVE_UNISTD_H 1
#if defined __BIG_ENDIAN__
# define WORDS_BIGENDIAN 1
#endif
#include <stdlib.h>
"""

def banned(line):
    return '#pragma once' in line or '#include "' in line or '#include <config.h>' in line

def cat_file(fout, fin_name):
    with open(fin_name) as fin:
        lines = fin.readlines()
        lines = [l.rstrip() for l in lines if not banned(l)]
        for l in lines:
            fout.write(l + '\n')
        fout.write('\n')

def combine_libaudiofile(fout_name, libaudiofile_path):
    with open(fout_name, 'w') as fout:
        fout.write(file_header + "\n")
        fout.write("/*\n")
        cat_file(fout, os.path.join(libaudiofile_path, '../COPYING'))
        fout.write("*/\n\n")
        fout.write(prepend_defs + "\n")
        for f in file_list:
            fout.write(f"// file: {f}\n")
            cat_file(fout, os.path.join(libaudiofile_path, f))

def main():
    if len(sys.argv) > 1 and sys.argv[1] in ['-h', '--help']:
        print('Usage: generate_audiofile_cpp.py [output_filename] [libaudiofile_src_dir]')
        print('Defaults: [output_filename = "audiofile.cpp"] [libaudiofile_src_dir = "./audiofile/libaudiofile"]')
        return
    fout_name = sys.argv[1] if len(sys.argv) > 1 else 'audiofile.cpp'
    libaudiofile_path = sys.argv[2] if len(sys.argv) > 2 else './audiofile/libaudiofile'
    combine_libaudiofile(fout_name, os.path.expanduser(libaudiofile_path))

main()
