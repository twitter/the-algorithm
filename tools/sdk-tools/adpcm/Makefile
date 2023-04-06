# Makefile for building vadpcm_enc and vadpcm_dec for either IRIX or natively.
# For an IRIX build, the env variable IRIX_ROOT should point to the root of an
# IRIX filesystem, and QEMU_IRIX should point to the qemu-irix binary.

IRIX_CC := $(QEMU_IRIX) -silent -L $(IRIX_ROOT) $(IRIX_ROOT)/usr/bin/cc
IRIX_CFLAGS := -fullwarn -woff 515,608,658,799 -Wab,-r4300_mul -g -Xcpluscomm -mips1 -o32

NATIVE_CC := gcc
NATIVE_CFLAGS := -g -Wall -O2 -Wno-unused-result -Wno-uninitialized

default: native
all: irix native

irix: vadpcm_dec_irix vadpcm_enc_irix
native: vadpcm_dec_native vadpcm_enc_native

clean:
	$(RM) *.o vadpcm_dec_irix vadpcm_enc_irix vadpcm_dec_native vadpcm_enc_native

%.o: %.c
	$(IRIX_CC) -c $(IRIX_CFLAGS) $< -o $@

vadpcm_dec_irix: SHELL := /usr/bin/env bash
vadpcm_dec_irix: vadpcm_dec.o vpredictor.o sampleio.o vdecode.o util.o
	$(IRIX_CC) $^ -o $@ -lm
	@dd status=none iflag=skip_bytes,count_bytes skip=$$((0x120)) count=$$((0x5000 - 0x120)) if=$@ | sha256sum | diff -q - <(echo 'ffaf4d0e4a5d13279d8de8e1eb4ba0f6350e7e3940ad1339f665036bf74f81c1  -') >/dev/null && echo $@: OK || echo $@: FAILED

vadpcm_enc_irix: SHELL := /usr/bin/env bash
vadpcm_enc_irix: vadpcm_enc.o vpredictor.o quant.o util.o vencode.o
	$(IRIX_CC) $^ -o $@ -lm
	@dd status=none iflag=skip_bytes,count_bytes skip=$$((0x120)) count=$$((0x6000 - 0x120)) if=$@ | sha256sum | diff -q - <(echo '803be21f985c520eafdde0ff2d0ad2d6dd0db364f146c6d5f5763251f4c1796b  -') >/dev/null && echo $@: OK || echo $@: FAILED

vadpcm_dec_native: vadpcm_dec.c vpredictor.c sampleio.c vdecode.c util.c
	$(NATIVE_CC) $(NATIVE_CFLAGS) $^ -o $@ -lm

vadpcm_enc_native: vadpcm_enc.c vpredictor.c quant.c util.c vencode.c
	$(NATIVE_CC) $(NATIVE_CFLAGS) $^ -o $@ -lm

.PHONY: default all irix native clean
