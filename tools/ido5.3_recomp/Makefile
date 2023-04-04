IRIX_ROOT := ../ido5.3_compiler

cc: OPT_CFLAGS := -O2
cfe: OPT_CFLAGS := -O2
uopt: OPT_CFLAGS := -O2
ugen: OPT_CFLAGS := -O2
as1: OPT_CFLAGS := -O2
acpp: OPT_CFLAGS := -O2

RECOMP := recomp

ugen_c.c: RECOMP_FLAGS := --conservative

all: cc cfe uopt ugen as1 acpp copt ujoin uld umerge usplit err.english.cc

clean:
	$(RM) cc* cfe* uopt* ugen* as1* acpp* copt* ujoin* uld* umerge* usplit* err.english.cc $(RECOMP) libc_impl.o

$(RECOMP): recomp.cpp
	$(CXX) $^ -o $@ -std=c++11 -O2 -Wno-switch `pkg-config --cflags --libs capstone`

libc_impl.o: libc_impl.c libc_impl.h
	$(CC) $< -c -fno-strict-aliasing -O2 -DIDO53

err.english.cc: $(IRIX_ROOT)/usr/lib/err.english.cc
	cp $^ $@

cc_c.c: $(IRIX_ROOT)/usr/bin/cc $(RECOMP)
	./$(RECOMP) $(RECOMP_FLAGS) $< > $@

%_c.c: $(IRIX_ROOT)/usr/lib/% $(RECOMP)
	./$(RECOMP) $(RECOMP_FLAGS) $< > $@

%: %_c.c libc_impl.o
	$(CC) libc_impl.o $< -o $@ $(OPT_CFLAGS) -fno-strict-aliasing -lm -no-pie

.PHONY: all clean
