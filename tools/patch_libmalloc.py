#!/usr/bin/env python
#
# Patches the malloc() function in libmalloc.so to allocate more than the
# specified number of bytes. This is needed to work around issues with the
# compiler occasionally crashing.
#
# This script replaces the "move a1, a0" (00 80 28 25) instruction with
# "addiu a1, a0, n" (24 85 nn nn), which causes the malloc function to add n to
# the size parameter that was passed in.

import hashlib
import os.path
import sys

# file to patch
filename = 'tools/ido5.3_compiler/lib/libmalloc.so'
# Expected (unpatched) hash of file
filehash = 'adde672b5d79b52ca3cce9a47c7cb648'
# location in file to patch
address = 0xAB4

# Get parameter
if len(sys.argv) != 2:
    print('Usage: ' + sys.argv[0] + ' n\n    where n is the number of extra bytes to allocate in malloc()')
    exit(1)
n = int(sys.argv[1])

# Original instruction "move a1, a0"
oldinsn = bytearray([0x00, 0x80, 0x28, 0x25])

# New instruction "addiu a1, a0, n"
newinsn = bytearray([0x24, 0x85, (n >> 8) & 0xFF, (n & 0xFF)])

# Patch the file
try:
    with open(filename, 'rb+') as f:
        # Read file contents
        contents = bytearray(f.read())

        # Unpatch the file by restoring original instruction
        contents[address:address+4] = oldinsn

        # Verify the (unpatched) hash of the file
        md5 = hashlib.md5()
        md5.update(contents)
        if md5.hexdigest() != filehash:
            print('Error: ' + filename + ' does not appear to be the correct version.')
            exit(1)

        # Patch the file
        if n != 0:
            contents[address:address+4] = newinsn

        # Write file
        f.seek(0, os.SEEK_SET)
        f.write(contents)
except IOError as e:
    print('Error: Could not open library file for writing: ' + str(e))
