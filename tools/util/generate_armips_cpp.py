#!/usr/bin/env python

import os
import re
import sys

file_list = [
    'stdafx.h',
    'ext/tinyformat/tinyformat.h',
    'Commands/CAssemblerCommand.h',
    'Core/Expression.h',
    'Core/ExpressionFunctions.h',
    'Core/SymbolData.h',
    'Util/Util.h',
    'Util/FileClasses.h',
    'Util/ByteArray.h',
    'Core/FileManager.h',
    'Core/ELF/ElfTypes.h',
    'Core/ELF/ElfFile.h',
    'Core/ELF/ElfRelocator.h',
    'Archs/Architecture.h',
    'Archs/MIPS/Mips.h',
    'Archs/MIPS/MipsOpcodes.h',
    'Archs/MIPS/CMipsInstruction.h',
    'Util/EncodingTable.h',
    'Core/Misc.h',
    'Core/Assembler.h',
    'Core/SymbolTable.h',
    'Core/Common.h',
    'Parser/DirectivesParser.h',
    'Parser/Tokenizer.h',
    'Archs/MIPS/MipsMacros.h',
    'Archs/MIPS/MipsParser.h',
    'Archs/MIPS/CMipsInstruction.cpp',
    'Archs/MIPS/MipsExpressionFunctions.h',
    'Archs/MIPS/MipsElfRelocator.h',
    'Archs/MIPS/Mips.cpp',
    'Archs/MIPS/MipsElfFile.h',
    'Util/CRC.h',
    'Archs/MIPS/MipsElfFile.cpp',
    'Commands/CommandSequence.h',
    'Parser/Parser.h',
    'Archs/MIPS/MipsElfRelocator.cpp',
    'Archs/MIPS/MipsExpressionFunctions.cpp',
    'Archs/MIPS/MipsMacros.cpp',
    'Archs/MIPS/MipsOpcodes.cpp',
    'Parser/ExpressionParser.h',
    'Archs/MIPS/PsxRelocator.h',
    'Commands/CDirectiveFile.h',
    'Archs/MIPS/MipsParser.cpp',
    'Archs/MIPS/PsxRelocator.cpp',
    'Archs/Architecture.cpp',
    'Commands/CAssemblerCommand.cpp',
    'Commands/CAssemblerLabel.h',
    'Commands/CAssemblerLabel.cpp',
    'Commands/CDirectiveArea.h',
    'Commands/CDirectiveArea.cpp',
    'Commands/CDirectiveConditional.h',
    'Commands/CDirectiveConditional.cpp',
    'Commands/CDirectiveData.h',
    'Commands/CDirectiveData.cpp',
    'Commands/CDirectiveFile.cpp',
    'Commands/CDirectiveMessage.h',
    'Commands/CDirectiveMessage.cpp',
    'Commands/CommandSequence.cpp',
    'Parser/DirectivesParser.cpp',
    'Parser/ExpressionParser.cpp',
    'Parser/Parser.cpp',
    'Parser/Tokenizer.cpp',
    'Util/ByteArray.cpp',
    'Util/CRC.cpp',
    'Util/EncodingTable.cpp',
    'Util/FileClasses.cpp',
    'Util/Util.cpp',
    'Main/CommandLineInterface.h',
    'Main/CommandLineInterface.cpp',
    'Core/ELF/ElfFile.cpp',
    'Core/ELF/ElfRelocator.cpp',
    'Core/Assembler.cpp',
    'Core/Common.cpp',
    'Core/Expression.cpp',
    'Core/ExpressionFunctions.cpp',
    'Core/FileManager.cpp',
    'Core/Misc.cpp',
    'Core/SymbolData.cpp',
    'Core/SymbolTable.cpp',
    'Main/main.cpp',
]

file_header =  \
"""// armips assembler v0.11
// https://github.com/Kingcom/armips/
// To simplify compilation, all files have been concatenated into one.
// MIPS only, ARM is not included.\n\n"""

def banned(line):
    return '#pragma once' in line or '#include "' in line

def cat_file(fout, fin_name):
    with open(fin_name) as fin:
        lines = fin.readlines()
        lines = [l.rstrip() for l in lines if not banned(l)]
        for l in lines:
            if re.search(r'\bArm\b', l):
                fout.write("#ifdef ARMIPS_ARM\n") # must manually insert #endif
            fout.write(l + '\n')
        fout.write('\n')

def combine_armips(fout_name, armips_path):
    with open(fout_name, 'w') as fout:
        fout.write(file_header)
        fout.write("/*\n")
        cat_file(fout, os.path.join(armips_path, 'LICENSE.txt'))
        fout.write("*/\n\n")
        for f in file_list:
            fout.write(f"// file: {f}\n")
            cat_file(fout, os.path.join(armips_path, f))

def main():
    if len(sys.argv) > 1 and sys.argv[1] in ['-h', '--help']:
        print('Usage: generate_armips_cpp.py [output_filename] [armips_src_dir]')
        print('Defaults: [output_filename = "armips.cpp"] [armips_src_dir = "./armips"]')
        return
    fout_name = sys.argv[1] if len(sys.argv) > 1 else 'armips.cpp'
    armips_path = sys.argv[2] if len(sys.argv) > 2 else './armips'
    combine_armips(fout_name, os.path.expanduser(armips_path))

main()
