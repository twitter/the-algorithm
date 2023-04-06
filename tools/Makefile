CC := gcc
CFLAGS := -I . -Wall -Wextra -Wno-unused-parameter -pedantic -std=c99 -O3 -s
PROGRAMS := n64graphics n64graphics_ci mio0 n64cksum textconv patch_libultra_math iplfontutil aifc_decode aiff_extract_codebook vadpcm_enc tabledesign extract_data_for_mio skyconv

n64graphics_SOURCES := n64graphics.c utils.c
n64graphics_CFLAGS  := -DN64GRAPHICS_STANDALONE

n64graphics_ci_SOURCES := n64graphics_ci_dir/n64graphics_ci.c n64graphics_ci_dir/exoquant/exoquant.c n64graphics_ci_dir/utils.c
n64graphics_ci_CFLAGS := -O2 # 3s faster compile time

mio0_SOURCES := libmio0.c
mio0_CFLAGS := -DMIO0_STANDALONE

n64cksum_SOURCES := n64cksum.c libmio0.c libsm64.c utils.c

textconv_SOURCES := textconv.c utf8.c hashtable.c
patch_libultra_math_SOURCES := patch_libultra_math.c

iplfontutil_SOURCES := iplfontutil.c
iplfontutil_CFLAGS := -O2 # faster compile time

aifc_decode_SOURCES := aifc_decode.c
aifc_decode_CFLAGS := -O2 # both runs and compiles faster than -O3

aiff_extract_codebook_SOURCES := aiff_extract_codebook.c

tabledesign_SOURCES := sdk-tools/tabledesign/codebook.c sdk-tools/tabledesign/estimate.c sdk-tools/tabledesign/print.c sdk-tools/tabledesign/tabledesign.c
tabledesign_CFLAGS := -Wno-uninitialized -laudiofile

vadpcm_enc_SOURCES := sdk-tools/adpcm/vadpcm_enc.c sdk-tools/adpcm/vpredictor.c sdk-tools/adpcm/quant.c sdk-tools/adpcm/util.c sdk-tools/adpcm/vencode.c
vadpcm_enc_CFLAGS := -Wno-unused-result -Wno-uninitialized -Wno-sign-compare -Wno-absolute-value

extract_data_for_mio_SOURCES := extract_data_for_mio.c
extract_data_for_mio_CFLAGS := -O2

skyconv_SOURCES := skyconv.c n64graphics.c utils.c
skyconv_CFLAGS := -O2 -lm

all: $(PROGRAMS)

clean:
	$(RM) $(PROGRAMS)

define COMPILE
$(1): $($1_SOURCES)
	$(CC) $(CFLAGS) $$^ -lm -o $$@ $($1_CFLAGS)
endef

$(foreach p,$(PROGRAMS),$(eval $(call COMPILE,$(p))))
