#!/usr/bin/env python3
import sys
import os
import shlex
import subprocess
import tempfile

dir_path = os.path.dirname(os.path.realpath(__file__))
asm_processor = ['python3', os.path.join(dir_path, "asm-processor.py")]
prelude = os.path.join(dir_path, "prelude.inc")

all_args = sys.argv[1:]
sep1 = all_args.index('--')
sep2 = all_args.index('--', sep1+1)

compiler = all_args[:sep1]

assembler = all_args[sep1+1:sep2]
assembler_sh = ' '.join(shlex.quote(x) for x in assembler)

compile_args = all_args[sep2+1:]
in_file = compile_args[-1]
out_ind = compile_args.index('-o')
out_file = compile_args[out_ind + 1]
del compile_args[-1]
del compile_args[out_ind + 1]
del compile_args[out_ind]

in_dir = os.path.split(os.path.realpath(in_file))[0]
opt_flags = [x for x in compile_args if x in ['-g', '-O2', '-O1', '-framepointer']]

preprocessed_file = tempfile.NamedTemporaryFile(prefix='preprocessed', suffix='.c')

subprocess.check_call(asm_processor + opt_flags + [in_file], stdout=preprocessed_file)
subprocess.check_call(compiler + compile_args + ['-I', in_dir, '-o', out_file, preprocessed_file.name])
subprocess.check_call(asm_processor + opt_flags + [in_file, '--post-process', out_file, '--assembler', assembler_sh, '--asm-prelude', prelude])
