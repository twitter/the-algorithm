# Makefile for building tabledesign for either IRIX or natively.
# For an IRIX build, the env variable IRIX_ROOT should point to the root of an
# IRIX filesystem, and QEMU_IRIX should point to the qemu-irix binary.

IRIX_CC := $(QEMU_IRIX) -silent -L $(IRIX_ROOT) $(IRIX_ROOT)/usr/bin/cc
IRIX_CFLAGS := -fullwarn -Wab,-r4300_mul -Xcpluscomm -mips1 -O2

NATIVE_CC := gcc
NATIVE_CFLAGS := -Wall -Wno-uninitialized -O2

LDFLAGS := -lm -laudiofile

default: native
all: irix native

irix: tabledesign_irix
native: tabledesign_native

clean:
	$(RM) *.o tabledesign_irix tabledesign_native

%.o: %.c
	$(IRIX_CC) -c $(IRIX_CFLAGS) $< -o $@

tabledesign_irix: tabledesign.o codebook.o estimate.o print.o
	$(IRIX_CC) $^ -o $@ $(LDFLAGS)

tabledesign_native: tabledesign.c codebook.c estimate.c print.c
	$(NATIVE_CC) $(NATIVE_CFLAGS) $^ -o $@ $(LDFLAGS)

.PHONY: default all irix native clean
