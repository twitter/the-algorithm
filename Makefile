# Makefile to rebuild SM64 split image

include util.mk

# Default target
default: all

# Preprocessor definitions
DEFINES :=

#==============================================================================#
# Build Options                                                                #
#==============================================================================#

# These options can either be set by building with 'make SETTING=value'.
# 'make clean' may be required first.

# Build for the N64 (turn this off for ports)
TARGET_N64 ?= 1


# COMPILER - selects the C compiler to use
#   ido - uses the SGI IRIS Development Option compiler, which is used to build
#         an original matching N64 ROM
#   gcc - uses the GNU C Compiler
COMPILER ?= ido
$(eval $(call validate-option,COMPILER,ido gcc))


# VERSION - selects the version of the game to build
#   jp - builds the 1996 Japanese version
#   us - builds the 1996 North American version
#   eu - builds the 1997 PAL version
#   sh - builds the 1997 Japanese Shindou version, with rumble pak support
VERSION ?= us
$(eval $(call validate-option,VERSION,jp us eu sh))

ifeq      ($(VERSION),jp)
  DEFINES   += VERSION_JP=1
  OPT_FLAGS := -g
  GRUCODE   ?= f3d_old
  VERSION_JP_US  ?= true
else ifeq ($(VERSION),us)
  DEFINES   += VERSION_US=1
  OPT_FLAGS := -g
  GRUCODE   ?= f3d_old
  VERSION_JP_US  ?= true
else ifeq ($(VERSION),eu)
  DEFINES   += VERSION_EU=1
  OPT_FLAGS := -O2
  GRUCODE   ?= f3d_new
  VERSION_JP_US  ?= false
else ifeq ($(VERSION),sh)
  DEFINES   += VERSION_SH=1
  OPT_FLAGS := -O2
  GRUCODE   ?= f3d_new
  VERSION_JP_US  ?= false
endif

TARGET := sm64.$(VERSION)


# GRUCODE - selects which RSP microcode to use.
#   f3d_old - default for JP and US versions
#   f3d_new - default for EU and Shindou versions
#   f3dex   -
#   f3dex2  -
#   f3dzex  - newer, experimental microcode used in Animal Crossing
$(eval $(call validate-option,GRUCODE,f3d_old f3dex f3dex2 f3d_new f3dzex))

ifeq      ($(GRUCODE),f3d_old)
  DEFINES += F3D_OLD=1
else ifeq ($(GRUCODE),f3d_new) # Fast3D 2.0H
  DEFINES += F3D_NEW=1
else ifeq ($(GRUCODE),f3dex) # Fast3DEX
  DEFINES += F3DEX_GBI=1 F3DEX_GBI_SHARED=1
else ifeq ($(GRUCODE), f3dex2) # Fast3DEX2
  DEFINES += F3DEX_GBI_2=1 F3DEX_GBI_SHARED=1
else ifeq ($(GRUCODE),f3dzex) # Fast3DZEX (2.0J / Animal Forest - Dōbutsu no Mori)
  $(warning Fast3DZEX is experimental. Try at your own risk.)
  DEFINES += F3DZEX_GBI_2=1 F3DEX_GBI_2=1 F3DEX_GBI_SHARED=1
endif


# USE_QEMU_IRIX - when ido is selected, select which way to emulate IRIX programs
#   1 - use qemu-irix
#   0 - statically recompile the IRIX programs
USE_QEMU_IRIX ?= 0
$(eval $(call validate-option,USE_QEMU_IRIX,0 1))

ifeq      ($(COMPILER),ido)
  ifeq ($(USE_QEMU_IRIX),1)
    # Verify that qemu-irix exists
    QEMU_IRIX ?= $(call find-command,qemu-irix)
    ifeq (, $(QEMU_IRIX))
      $(error Using the IDO compiler requires qemu-irix. Please install qemu-irix package or set the QEMU_IRIX environment variable to the full qemu-irix binary path)
    endif
  endif

  MIPSISET := -mips2
else ifeq ($(COMPILER),gcc)
  NON_MATCHING := 1
  MIPSISET     := -mips3
  OPT_FLAGS    := -O2
endif


# NON_MATCHING - whether to build a matching, identical copy of the ROM
#   1 - enable some alternate, more portable code that does not produce a matching ROM
#   0 - build a matching ROM
NON_MATCHING ?= 0
$(eval $(call validate-option,NON_MATCHING,0 1))

ifeq ($(TARGET_N64),0)
  NON_MATCHING := 1
endif

ifeq ($(NON_MATCHING),1)
  DEFINES += NON_MATCHING=1 AVOID_UB=1
  COMPARE := 0
endif


# COMPARE - whether to verify the SHA-1 hash of the ROM after building
#   1 - verifies the SHA-1 hash of the selected version of the game
#   0 - does not verify the hash
COMPARE ?= 1
$(eval $(call validate-option,COMPARE,0 1))

TARGET_STRING := sm64.$(VERSION).$(GRUCODE)
# If non-default settings were chosen, disable COMPARE
ifeq ($(filter $(TARGET_STRING), sm64.jp.f3d_old sm64.us.f3d_old sm64.eu.f3d_new sm64.sh.f3d_new),)
  COMPARE := 0
endif

# Whether to hide commands or not
VERBOSE ?= 0
ifeq ($(VERBOSE),0)
  V := @
endif

# Whether to colorize build messages
COLOR ?= 1

# display selected options unless 'make clean' or 'make distclean' is run
ifeq ($(filter clean distclean,$(MAKECMDGOALS)),)
  $(info ==== Build Options ====)
  $(info Version:        $(VERSION))
  $(info Microcode:      $(GRUCODE))
  $(info Target:         $(TARGET))
  ifeq ($(COMPARE),1)
    $(info Compare ROM:    yes)
  else
    $(info Compare ROM:    no)
  endif
  ifeq ($(NON_MATCHING),1)
    $(info Build Matching: no)
  else
    $(info Build Matching: yes)
  endif
  $(info =======================)
endif


#==============================================================================#
# Universal Dependencies                                                       #
#==============================================================================#

TOOLS_DIR := tools

# (This is a bit hacky, but a lot of rules implicitly depend
# on tools and assets, and we use directory globs further down
# in the makefile that we want should cover assets.)

PYTHON := python3

ifeq ($(filter clean distclean print-%,$(MAKECMDGOALS)),)

  # Make sure assets exist
  NOEXTRACT ?= 0
  ifeq ($(NOEXTRACT),0)
    DUMMY != $(PYTHON) extract_assets.py $(VERSION) >&2 || echo FAIL
    ifeq ($(DUMMY),FAIL)
      $(error Failed to extract assets)
    endif
  endif

  # Make tools if out of date
  $(info Building tools...)
  DUMMY != $(MAKE) -s -C $(TOOLS_DIR) $(if $(filter-out ido0,$(COMPILER)$(USE_QEMU_IRIX)),all-except-recomp,) >&2 || echo FAIL
    ifeq ($(DUMMY),FAIL)
      $(error Failed to build tools)
    endif
  $(info Building ROM...)

endif


#==============================================================================#
# Target Executable and Sources                                                #
#==============================================================================#

BUILD_DIR_BASE := build
# BUILD_DIR is the location where all build artifacts are placed
BUILD_DIR      := $(BUILD_DIR_BASE)/$(VERSION)
ROM            := $(BUILD_DIR)/$(TARGET).z64
ELF            := $(BUILD_DIR)/$(TARGET).elf
LIBULTRA       := $(BUILD_DIR)/libultra.a
LD_SCRIPT      := sm64.ld
MIO0_DIR       := $(BUILD_DIR)/bin
SOUND_BIN_DIR  := $(BUILD_DIR)/sound
TEXTURE_DIR    := textures
ACTOR_DIR      := actors
LEVEL_DIRS     := $(patsubst levels/%,%,$(dir $(wildcard levels/*/header.h)))

# Directories containing source files
SRC_DIRS := src src/engine src/game src/audio src/menu src/buffers actors levels bin data assets asm lib sound
BIN_DIRS := bin bin/$(VERSION)

ULTRA_SRC_DIRS := lib/src lib/src/math lib/asm lib/data
ULTRA_BIN_DIRS := lib/bin

GODDARD_SRC_DIRS := src/goddard src/goddard/dynlists

# File dependencies and variables for specific files
include Makefile.split

# Source code files
LEVEL_C_FILES     := $(wildcard levels/*/leveldata.c) $(wildcard levels/*/script.c) $(wildcard levels/*/geo.c)
C_FILES           := $(foreach dir,$(SRC_DIRS),$(wildcard $(dir)/*.c)) $(LEVEL_C_FILES)
S_FILES           := $(foreach dir,$(SRC_DIRS),$(wildcard $(dir)/*.s))
ULTRA_C_FILES     := $(foreach dir,$(ULTRA_SRC_DIRS),$(wildcard $(dir)/*.c))
GODDARD_C_FILES   := $(foreach dir,$(GODDARD_SRC_DIRS),$(wildcard $(dir)/*.c))
ULTRA_S_FILES     := $(foreach dir,$(ULTRA_SRC_DIRS),$(wildcard $(dir)/*.s))
GENERATED_C_FILES := $(BUILD_DIR)/assets/mario_anim_data.c $(BUILD_DIR)/assets/demo_data.c

# Sound files
SOUND_BANK_FILES    := $(wildcard sound/sound_banks/*.json)
SOUND_SAMPLE_DIRS   := $(wildcard sound/samples/*)
SOUND_SAMPLE_AIFFS  := $(foreach dir,$(SOUND_SAMPLE_DIRS),$(wildcard $(dir)/*.aiff))
SOUND_SAMPLE_TABLES := $(foreach file,$(SOUND_SAMPLE_AIFFS),$(BUILD_DIR)/$(file:.aiff=.table))
SOUND_SAMPLE_AIFCS  := $(foreach file,$(SOUND_SAMPLE_AIFFS),$(BUILD_DIR)/$(file:.aiff=.aifc))
SOUND_SEQUENCE_DIRS := sound/sequences sound/sequences/$(VERSION)
# all .m64 files in SOUND_SEQUENCE_DIRS, plus all .m64 files that are generated from .s files in SOUND_SEQUENCE_DIRS
SOUND_SEQUENCE_FILES := \
  $(foreach dir,$(SOUND_SEQUENCE_DIRS),\
    $(wildcard $(dir)/*.m64) \
    $(foreach file,$(wildcard $(dir)/*.s),$(BUILD_DIR)/$(file:.s=.m64)) \
  )

# Object files
O_FILES := $(foreach file,$(C_FILES),$(BUILD_DIR)/$(file:.c=.o)) \
           $(foreach file,$(S_FILES),$(BUILD_DIR)/$(file:.s=.o)) \
           $(foreach file,$(GENERATED_C_FILES),$(file:.c=.o))

ULTRA_O_FILES := $(foreach file,$(ULTRA_S_FILES),$(BUILD_DIR)/$(file:.s=.o)) \
                 $(foreach file,$(ULTRA_C_FILES),$(BUILD_DIR)/$(file:.c=.o))

GODDARD_O_FILES := $(foreach file,$(GODDARD_C_FILES),$(BUILD_DIR)/$(file:.c=.o))

# Automatic dependency files
DEP_FILES := $(O_FILES:.o=.d) $(ULTRA_O_FILES:.o=.d) $(GODDARD_O_FILES:.o=.d) $(BUILD_DIR)/$(LD_SCRIPT).d

# Files with GLOBAL_ASM blocks
ifeq ($(NON_MATCHING),0)
  ifeq ($(VERSION),sh)
    GLOBAL_ASM_C_FILES != grep -rl 'GLOBAL_ASM(' $(wildcard src/**/*.c) $(wildcard lib/src/*.c)
  else
    GLOBAL_ASM_C_FILES != grep -rl 'GLOBAL_ASM(' $(wildcard src/**/*.c)
  endif
GLOBAL_ASM_O_FILES = $(foreach file,$(GLOBAL_ASM_C_FILES),$(BUILD_DIR)/$(file:.c=.o))
GLOBAL_ASM_DEP = $(BUILD_DIR)/src/audio/non_matching_dep
endif


#==============================================================================#
# Compiler Options                                                             #
#==============================================================================#

# detect prefix for MIPS toolchain
ifneq      ($(call find-command,mips-linux-gnu-ld),)
  CROSS := mips-linux-gnu-
else ifneq ($(call find-command,mips64-linux-gnu-ld),)
  CROSS := mips64-linux-gnu-
else ifneq ($(call find-command,mips64-elf-ld),)
  CROSS := mips64-elf-
else
  $(error Unable to detect a suitable MIPS toolchain installed)
endif

AS        := $(CROSS)as
ifeq ($(COMPILER),gcc)
  CC      := $(CROSS)gcc
else
  ifeq ($(USE_QEMU_IRIX),1)
    IRIX_ROOT := $(TOOLS_DIR)/ido5.3_compiler
    CC      := $(QEMU_IRIX) -silent -L $(IRIX_ROOT) $(IRIX_ROOT)/usr/bin/cc
    ACPP    := $(QEMU_IRIX) -silent -L $(IRIX_ROOT) $(IRIX_ROOT)/usr/lib/acpp
    COPT    := $(QEMU_IRIX) -silent -L $(IRIX_ROOT) $(IRIX_ROOT)/usr/lib/copt
  else
    IDO_ROOT := $(TOOLS_DIR)/ido5.3_recomp
    CC      := $(IDO_ROOT)/cc
    ACPP    := $(IDO_ROOT)/acpp
    COPT    := $(IDO_ROOT)/copt
  endif
endif
LD        := $(CROSS)ld
AR        := $(CROSS)ar
OBJDUMP   := $(CROSS)objdump
OBJCOPY   := $(CROSS)objcopy

ifeq ($(TARGET_N64),1)
  TARGET_CFLAGS := -nostdinc -DTARGET_N64 -D_LANGUAGE_C
  CC_CFLAGS := -fno-builtin
endif

INCLUDE_DIRS := include $(BUILD_DIR) $(BUILD_DIR)/include src .
ifeq ($(TARGET_N64),1)
  INCLUDE_DIRS += include/libc
endif

C_DEFINES := $(foreach d,$(DEFINES),-D$(d))
DEF_INC_CFLAGS := $(foreach i,$(INCLUDE_DIRS),-I$(i)) $(C_DEFINES)

# Prefer clang as C preprocessor if installed on the system
ifneq (,$(call find-command,clang))
  CPP      := clang
  CPPFLAGS := -E -P -x c -Wno-trigraphs $(DEF_INC_CFLAGS)
else
  CPP      := cpp
  CPPFLAGS := -P -Wno-trigraphs $(DEF_INC_CFLAGS)
endif

# Check code syntax with host compiler
CC_CHECK := gcc
CC_CHECK_CFLAGS := -fsyntax-only -fsigned-char $(CC_CFLAGS) $(TARGET_CFLAGS) -std=gnu90 -Wall -Wextra -Wno-format-security -Wno-main -DNON_MATCHING -DAVOID_UB $(DEF_INC_CFLAGS)

# C compiler options
CFLAGS = -G 0 $(OPT_FLAGS) $(TARGET_CFLAGS) $(MIPSISET) $(DEF_INC_CFLAGS)
ifeq ($(COMPILER),gcc)
  CFLAGS += -mno-shared -march=vr4300 -mfix4300 -mabi=32 -mhard-float -mdivide-breaks -fno-stack-protector -fno-common -fno-zero-initialized-in-bss -fno-PIC -mno-abicalls -fno-strict-aliasing -fno-inline-functions -ffreestanding -fwrapv -Wall -Wextra
else
  CFLAGS += -non_shared -Wab,-r4300_mul -Xcpluscomm -Xfullwarn -signed -32
endif

ASFLAGS     := -march=vr4300 -mabi=32 $(foreach i,$(INCLUDE_DIRS),-I$(i)) $(foreach d,$(DEFINES),--defsym $(d))
RSPASMFLAGS := $(foreach d,$(DEFINES),-definelabel $(subst =, ,$(d)))

ifeq ($(shell getconf LONG_BIT), 32)
  # Work around memory allocation bug in QEMU
  export QEMU_GUEST_BASE := 1
else
  # Ensure that gcc treats the code as 32-bit
  CC_CHECK_CFLAGS += -m32
endif

# Prevent a crash with -sopt
export LANG := C

#==============================================================================#
# Miscellaneous Tools                                                          #
#==============================================================================#

# N64 tools
MIO0TOOL              := $(TOOLS_DIR)/mio0
N64CKSUM              := $(TOOLS_DIR)/n64cksum
N64GRAPHICS           := $(TOOLS_DIR)/n64graphics
N64GRAPHICS_CI        := $(TOOLS_DIR)/n64graphics_ci
TEXTCONV              := $(TOOLS_DIR)/textconv
AIFF_EXTRACT_CODEBOOK := $(TOOLS_DIR)/aiff_extract_codebook
VADPCM_ENC            := $(TOOLS_DIR)/vadpcm_enc
EXTRACT_DATA_FOR_MIO  := $(TOOLS_DIR)/extract_data_for_mio
SKYCONV               := $(TOOLS_DIR)/skyconv
# Use the system installed armips if available. Otherwise use the one provided with this repository.
ifneq (,$(call find-command,armips))
  RSPASM              := armips
else
  RSPASM              := $(TOOLS_DIR)/armips
endif
ENDIAN_BITWIDTH       := $(BUILD_DIR)/endian-and-bitwidth
EMULATOR = mupen64plus
EMU_FLAGS = --noosd
LOADER = loader64
LOADER_FLAGS = -vwf
SHA1SUM = sha1sum
PRINT = printf

ifeq ($(COLOR),1)
NO_COL  := \033[0m
RED     := \033[0;31m
GREEN   := \033[0;32m
BLUE    := \033[0;34m
YELLOW  := \033[0;33m
BLINK   := \033[33;5m
endif

# Use Objcopy instead of extract_data_for_mio
ifeq ($(COMPILER),gcc)
  EXTRACT_DATA_FOR_MIO := $(OBJCOPY) -O binary --only-section=.data
endif

# Common build print status function
define print
  @$(PRINT) "$(GREEN)$(1) $(YELLOW)$(2)$(GREEN) -> $(BLUE)$(3)$(NO_COL)\n"
endef

#==============================================================================#
# Main Targets                                                                 #
#==============================================================================#

all: $(ROM)
ifeq ($(COMPARE),1)
	@$(PRINT) "$(GREEN)Checking if ROM matches.. $(NO_COL)\n"
	@$(SHA1SUM) --quiet -c $(TARGET).sha1 && $(PRINT) "$(TARGET): $(GREEN)OK$(NO_COL)\n" || ($(PRINT) "$(YELLOW)Building the ROM file has succeeded, but does not match the original ROM.\nThis is expected, and not an error, if you are making modifications.\nTo silence this message, use 'make COMPARE=0.' $(NO_COL)\n" && false)
endif

clean:
	$(RM) -r $(BUILD_DIR_BASE)

distclean: clean
	$(PYTHON) extract_assets.py --clean
	$(MAKE) -C $(TOOLS_DIR) clean

test: $(ROM)
	$(EMULATOR) $(EMU_FLAGS) $<

load: $(ROM)
	$(LOADER) $(LOADER_FLAGS) $<

libultra: $(BUILD_DIR)/libultra.a

# Extra object file dependencies
$(BUILD_DIR)/asm/boot.o:              $(IPL3_RAW_FILES)
$(BUILD_DIR)/src/game/crash_screen.o: $(CRASH_TEXTURE_C_FILES)
$(BUILD_DIR)/lib/rsp.o:               $(BUILD_DIR)/rsp/rspboot.bin $(BUILD_DIR)/rsp/fast3d.bin $(BUILD_DIR)/rsp/audio.bin
$(SOUND_BIN_DIR)/sound_data.o:        $(SOUND_BIN_DIR)/sound_data.ctl.inc.c $(SOUND_BIN_DIR)/sound_data.tbl.inc.c $(SOUND_BIN_DIR)/sequences.bin.inc.c $(SOUND_BIN_DIR)/bank_sets.inc.c
$(BUILD_DIR)/levels/scripts.o:        $(BUILD_DIR)/include/level_headers.h

ifeq ($(VERSION),sh)
  $(BUILD_DIR)/src/audio/load_sh.o: $(SOUND_BIN_DIR)/bank_sets.inc.c $(SOUND_BIN_DIR)/sequences_header.inc.c $(SOUND_BIN_DIR)/ctl_header.inc.c $(SOUND_BIN_DIR)/tbl_header.inc.c
endif

$(CRASH_TEXTURE_C_FILES): TEXTURE_ENCODING := u32

ifeq ($(COMPILER),gcc)
  $(BUILD_DIR)/lib/src/math/%.o: CFLAGS += -fno-builtin
endif

ifeq ($(VERSION),eu)
  TEXT_DIRS := text/de text/us text/fr

  # EU encoded text inserted into individual segment 0x19 files,
  # and course data also duplicated in leveldata.c
  $(BUILD_DIR)/bin/eu/translation_en.o: $(BUILD_DIR)/text/us/define_text.inc.c
  $(BUILD_DIR)/bin/eu/translation_de.o: $(BUILD_DIR)/text/de/define_text.inc.c
  $(BUILD_DIR)/bin/eu/translation_fr.o: $(BUILD_DIR)/text/fr/define_text.inc.c
  $(BUILD_DIR)/levels/menu/leveldata.o: $(BUILD_DIR)/include/text_strings.h
  $(BUILD_DIR)/levels/menu/leveldata.o: $(BUILD_DIR)/text/us/define_courses.inc.c
  $(BUILD_DIR)/levels/menu/leveldata.o: $(BUILD_DIR)/text/de/define_courses.inc.c
  $(BUILD_DIR)/levels/menu/leveldata.o: $(BUILD_DIR)/text/fr/define_courses.inc.c
else
  ifeq ($(VERSION),sh)
    TEXT_DIRS := text/jp
    $(BUILD_DIR)/bin/segment2.o: $(BUILD_DIR)/text/jp/define_text.inc.c
  else
    TEXT_DIRS := text/$(VERSION)
    # non-EU encoded text inserted into segment 0x02
    $(BUILD_DIR)/bin/segment2.o: $(BUILD_DIR)/text/$(VERSION)/define_text.inc.c
  endif
endif

ALL_DIRS := $(BUILD_DIR) $(addprefix $(BUILD_DIR)/,$(SRC_DIRS) $(GODDARD_SRC_DIRS) $(ULTRA_SRC_DIRS) $(ULTRA_BIN_DIRS) $(BIN_DIRS) $(TEXTURE_DIRS) $(TEXT_DIRS) $(SOUND_SAMPLE_DIRS) $(addprefix levels/,$(LEVEL_DIRS)) rsp include) $(MIO0_DIR) $(addprefix $(MIO0_DIR)/,$(VERSION)) $(SOUND_BIN_DIR) $(SOUND_BIN_DIR)/sequences/$(VERSION)

# Make sure build directory exists before compiling anything
DUMMY != mkdir -p $(ALL_DIRS)

$(BUILD_DIR)/include/text_strings.h: $(BUILD_DIR)/include/text_menu_strings.h
$(BUILD_DIR)/src/menu/file_select.o: $(BUILD_DIR)/include/text_strings.h
$(BUILD_DIR)/src/menu/star_select.o: $(BUILD_DIR)/include/text_strings.h
$(BUILD_DIR)/src/game/ingame_menu.o: $(BUILD_DIR)/include/text_strings.h


#==============================================================================#
# Texture Generation                                                           #
#==============================================================================#
TEXTURE_ENCODING := u8

# Convert PNGs to RGBA32, RGBA16, IA16, IA8, IA4, IA1, I8, I4 binary files
$(BUILD_DIR)/%: %.png
	$(call print,Converting:,$<,$@)
	$(V)$(N64GRAPHICS) -s raw -i $@ -g $< -f $(lastword $(subst ., ,$@))

$(BUILD_DIR)/%.inc.c: %.png
	$(call print,Converting:,$<,$@)
	$(V)$(N64GRAPHICS) -s $(TEXTURE_ENCODING) -i $@ -g $< -f $(lastword ,$(subst ., ,$(basename $<)))

# Color Index CI8
$(BUILD_DIR)/%.ci8: %.ci8.png
	$(call print,Converting:,$<,$@)
	$(V)$(N64GRAPHICS_CI) -i $@ -g $< -f ci8

# Color Index CI4
$(BUILD_DIR)/%.ci4: %.ci4.png
	$(call print,Converting:,$<,$@)
	$(V)$(N64GRAPHICS_CI) -i $@ -g $< -f ci4


#==============================================================================#
# Compressed Segment Generation                                                #
#==============================================================================#

# Link segment file to resolve external labels
# TODO: ideally this would be `-Trodata-segment=0x07000000` but that doesn't set the address
$(BUILD_DIR)/%.elf: $(BUILD_DIR)/%.o
	$(call print,Linking ELF file:,$<,$@)
	$(V)$(LD) -e 0 -Ttext=$(SEGMENT_ADDRESS) -Map $@.map -o $@ $<
# Override for leveldata.elf, which otherwise matches the above pattern
.SECONDEXPANSION:
$(BUILD_DIR)/levels/%/leveldata.elf: $(BUILD_DIR)/levels/%/leveldata.o $(BUILD_DIR)/bin/$$(TEXTURE_BIN).elf
	$(call print,Linking ELF file:,$<,$@)
	$(V)$(LD) -e 0 -Ttext=$(SEGMENT_ADDRESS) -Map $@.map --just-symbols=$(BUILD_DIR)/bin/$(TEXTURE_BIN).elf -o $@ $<

$(BUILD_DIR)/%.bin: $(BUILD_DIR)/%.elf
	$(call print,Extracting compressible data from:,$<,$@)
	$(V)$(EXTRACT_DATA_FOR_MIO) $< $@

$(BUILD_DIR)/levels/%/leveldata.bin: $(BUILD_DIR)/levels/%/leveldata.elf
	$(call print,Extracting compressible data from:,$<,$@)
	$(V)$(EXTRACT_DATA_FOR_MIO) $< $@

# Compress binary file
$(BUILD_DIR)/%.mio0: $(BUILD_DIR)/%.bin
	$(call print,Compressing:,$<,$@)
	$(V)$(MIO0TOOL) $< $@

# convert binary mio0 to object file
$(BUILD_DIR)/%.mio0.o: $(BUILD_DIR)/%.mio0
	$(call print,Converting MIO0 to ELF:,$<,$@)
	$(V)$(LD) -r -b binary $< -o $@


#==============================================================================#
# Sound File Generation                                                        #
#==============================================================================#

$(BUILD_DIR)/%.table: %.aiff
	$(call print,Extracting codebook:,$<,$@)
	$(V)$(AIFF_EXTRACT_CODEBOOK) $< >$@

$(BUILD_DIR)/%.aifc: $(BUILD_DIR)/%.table %.aiff
	$(call print,Encoding ADPCM:,$(word 2,$^),$@)
	$(V)$(VADPCM_ENC) -c $^ $@

$(ENDIAN_BITWIDTH): $(TOOLS_DIR)/determine-endian-bitwidth.c
	@$(PRINT) "$(GREEN)Generating endian-bitwidth $(NO_COL)\n"
	$(V)$(CC) -c $(CFLAGS) -o $@.dummy2 $< 2>$@.dummy1; true
	$(V)grep -o 'msgbegin --endian .* --bitwidth .* msgend' $@.dummy1 > $@.dummy2
	$(V)head -n1 <$@.dummy2 | cut -d' ' -f2-5 > $@
	$(V)$(RM) $@.dummy1
	$(V)$(RM) $@.dummy2

$(SOUND_BIN_DIR)/sound_data.ctl: sound/sound_banks/ $(SOUND_BANK_FILES) $(SOUND_SAMPLE_AIFCS) $(ENDIAN_BITWIDTH)
	@$(PRINT) "$(GREEN)Generating:  $(BLUE)$@ $(NO_COL)\n"
	$(V)$(PYTHON) $(TOOLS_DIR)/assemble_sound.py $(BUILD_DIR)/sound/samples/ sound/sound_banks/ $(SOUND_BIN_DIR)/sound_data.ctl $(SOUND_BIN_DIR)/ctl_header $(SOUND_BIN_DIR)/sound_data.tbl $(SOUND_BIN_DIR)/tbl_header $(C_DEFINES) $$(cat $(ENDIAN_BITWIDTH))

$(SOUND_BIN_DIR)/sound_data.tbl: $(SOUND_BIN_DIR)/sound_data.ctl
	@true

$(SOUND_BIN_DIR)/ctl_header: $(SOUND_BIN_DIR)/sound_data.ctl
	@true

$(SOUND_BIN_DIR)/tbl_header: $(SOUND_BIN_DIR)/sound_data.ctl
	@true

$(SOUND_BIN_DIR)/sequences.bin: $(SOUND_BANK_FILES) sound/sequences.json $(SOUND_SEQUENCE_DIRS) $(SOUND_SEQUENCE_FILES) $(ENDIAN_BITWIDTH)
	@$(PRINT) "$(GREEN)Generating:  $(BLUE)$@ $(NO_COL)\n"
	$(V)$(PYTHON) $(TOOLS_DIR)/assemble_sound.py --sequences $@ $(SOUND_BIN_DIR)/sequences_header $(SOUND_BIN_DIR)/bank_sets sound/sound_banks/ sound/sequences.json $(SOUND_SEQUENCE_FILES) $(C_DEFINES) $$(cat $(ENDIAN_BITWIDTH))

$(SOUND_BIN_DIR)/bank_sets: $(SOUND_BIN_DIR)/sequences.bin
	@true

$(SOUND_BIN_DIR)/sequences_header: $(SOUND_BIN_DIR)/sequences.bin
	@true

$(SOUND_BIN_DIR)/%.m64: $(SOUND_BIN_DIR)/%.o
	$(call print,Converting to M64:,$<,$@)
	$(V)$(OBJCOPY) -j .rodata $< -O binary $@


#==============================================================================#
# Generated Source Code Files                                                  #
#==============================================================================#

# Convert binary file to a comma-separated list of byte values for inclusion in C code
$(BUILD_DIR)/%.inc.c: $(BUILD_DIR)/%
	$(call print,Converting to C:,$<,$@)
	$(V)hexdump -v -e '1/1 "0x%X,"' $< > $@
	$(V)echo >> $@

# Generate animation data
$(BUILD_DIR)/assets/mario_anim_data.c: $(wildcard assets/anims/*.inc.c)
	@$(PRINT) "$(GREEN)Generating animation data $(NO_COL)\n"
	$(V)$(PYTHON) $(TOOLS_DIR)/mario_anims_converter.py > $@

# Generate demo input data
$(BUILD_DIR)/assets/demo_data.c: assets/demo_data.json $(wildcard assets/demos/*.bin)
	@$(PRINT) "$(GREEN)Generating demo data $(NO_COL)\n"
	$(V)$(PYTHON) $(TOOLS_DIR)/demo_data_converter.py assets/demo_data.json $(DEF_INC_CFLAGS) > $@

# Encode in-game text strings
$(BUILD_DIR)/include/text_strings.h: include/text_strings.h.in
	$(call print,Encoding:,$<,$@)
	$(V)$(TEXTCONV) charmap.txt $< $@
$(BUILD_DIR)/include/text_menu_strings.h: include/text_menu_strings.h.in
	$(call print,Encoding:,$<,$@)
	$(V)$(TEXTCONV) charmap_menu.txt $< $@
$(BUILD_DIR)/text/%/define_courses.inc.c: text/define_courses.inc.c text/%/courses.h
	@$(PRINT) "$(GREEN)Preprocessing: $(BLUE)$@ $(NO_COL)\n"
	$(V)$(CPP) $(CPPFLAGS) $< -o - -I text/$*/ | $(TEXTCONV) charmap.txt - $@
$(BUILD_DIR)/text/%/define_text.inc.c: text/define_text.inc.c text/%/courses.h text/%/dialogs.h
	@$(PRINT) "$(GREEN)Preprocessing: $(BLUE)$@ $(NO_COL)\n"
	$(V)$(CPP) $(CPPFLAGS) $< -o - -I text/$*/ | $(TEXTCONV) charmap.txt - $@

# Level headers
$(BUILD_DIR)/include/level_headers.h: levels/level_headers.h.in
	$(call print,Preprocessing level headers:,$<,$@)
	$(V)$(CPP) $(CPPFLAGS) -I . $< | sed -E 's|(.+)|#include "\1"|' > $@

# Run asm_processor on files that have NON_MATCHING code
ifeq ($(NON_MATCHING),0)
$(GLOBAL_ASM_O_FILES): CC := $(V)$(PYTHON) $(TOOLS_DIR)/asm_processor/build.py $(CC) -- $(AS) $(ASFLAGS) --
endif

# Rebuild files with 'GLOBAL_ASM' if the NON_MATCHING flag changes.
$(GLOBAL_ASM_O_FILES): $(GLOBAL_ASM_DEP).$(NON_MATCHING)
$(GLOBAL_ASM_DEP).$(NON_MATCHING):
	@$(RM) $(GLOBAL_ASM_DEP).*
	$(V)touch $@


#==============================================================================#
# Compilation Recipes                                                          #
#==============================================================================#

# Compile C code
$(BUILD_DIR)/%.o: %.c
	$(call print,Compiling:,$<,$@)
	@$(CC_CHECK) $(CC_CHECK_CFLAGS) -MMD -MP -MT $@ -MF $(BUILD_DIR)/$*.d $<
	$(V)$(CC) -c $(CFLAGS) -o $@ $<
$(BUILD_DIR)/%.o: $(BUILD_DIR)/%.c
	$(call print,Compiling:,$<,$@)
	@$(CC_CHECK) $(CC_CHECK_CFLAGS) -MMD -MP -MT $@ -MF $(BUILD_DIR)/$*.d $<
	$(V)$(CC) -c $(CFLAGS) -o $@ $<

# Alternate compiler flags needed for matching
ifeq ($(COMPILER),ido)
  $(BUILD_DIR)/levels/%/leveldata.o: OPT_FLAGS := -g
  $(BUILD_DIR)/actors/%.o:           OPT_FLAGS := -g
  $(BUILD_DIR)/bin/%.o:              OPT_FLAGS := -g
  $(BUILD_DIR)/src/goddard/%.o:      OPT_FLAGS := -g
  $(BUILD_DIR)/src/goddard/%.o:      MIPSISET := -mips1
  $(BUILD_DIR)/lib/src/%.o:          OPT_FLAGS :=
  $(BUILD_DIR)/lib/src/math/%.o:     OPT_FLAGS := -O2
  $(BUILD_DIR)/lib/src/math/ll%.o:   OPT_FLAGS :=
  $(BUILD_DIR)/lib/src/math/ll%.o:   MIPSISET := -mips3 -32
  $(BUILD_DIR)/lib/src/ldiv.o:       OPT_FLAGS := -O2
  $(BUILD_DIR)/lib/src/string.o:     OPT_FLAGS := -O2
  $(BUILD_DIR)/lib/src/gu%.o:        OPT_FLAGS := -O3
  $(BUILD_DIR)/lib/src/al%.o:        OPT_FLAGS := -O3

  ifeq ($(VERSION),sh)
    $(BUILD_DIR)/lib/src/_Ldtob.o:   OPT_FLAGS := -O3
    $(BUILD_DIR)/lib/src/_Litob.o:   OPT_FLAGS := -O3
    $(BUILD_DIR)/lib/src/_Printf.o:  OPT_FLAGS := -O3
    $(BUILD_DIR)/lib/src/sprintf.o:  OPT_FLAGS := -O3
    $(BUILD_DIR)/lib/src/osDriveRomInit.o: OPT_FLAGS := -g
  endif
  ifeq ($(VERSION),eu)
    $(BUILD_DIR)/lib/src/_Ldtob.o:   OPT_FLAGS := -O3
    $(BUILD_DIR)/lib/src/_Litob.o:   OPT_FLAGS := -O3
    $(BUILD_DIR)/lib/src/_Printf.o:  OPT_FLAGS := -O3
    $(BUILD_DIR)/lib/src/sprintf.o:  OPT_FLAGS := -O3

    # For all audio files other than external.c and port_eu.c, put string literals
    # in .data. (In Shindou, the port_eu.c string literals also moved to .data.)
    $(BUILD_DIR)/src/audio/%.o:        OPT_FLAGS := -O2 -use_readwrite_const
    $(BUILD_DIR)/src/audio/port_eu.o:  OPT_FLAGS := -O2
  endif
  ifeq ($(VERSION_JP_US),true)
    $(BUILD_DIR)/src/audio/%.o:        OPT_FLAGS := -O2 -Wo,-loopunroll,0
    $(BUILD_DIR)/src/audio/load.o:     OPT_FLAGS := -O2 -Wo,-loopunroll,0 -framepointer
    # The source-to-source optimizer copt is enabled for audio. This makes it use
    # acpp, which needs -Wp,-+ to handle C++-style comments.
    # All other files than external.c should really use copt, but only a few have
    # been matched so far.
    $(BUILD_DIR)/src/audio/effects.o:   OPT_FLAGS := -O2 -Wo,-loopunroll,0 -sopt,-inline=sequence_channel_process_sound,-scalaroptimize=1 -Wp,-+
    $(BUILD_DIR)/src/audio/synthesis.o: OPT_FLAGS := -O2 -Wo,-loopunroll,0 -sopt,-scalaroptimize=1 -Wp,-+
  endif
  $(BUILD_DIR)/src/audio/external.o: OPT_FLAGS := -O2 -Wo,-loopunroll,0

# Add a target for build/eu/src/audio/*.copt to make it easier to see debug
$(BUILD_DIR)/src/audio/%.acpp: src/audio/%.c
	$(ACPP) $(TARGET_CFLAGS) $(DEF_INC_CFLAGS) -D__sgi -+ $< > $@
$(BUILD_DIR)/src/audio/%.copt: $(BUILD_DIR)/src/audio/%.acpp
	$(COPT) -signed -I=$< -CMP=$@ -cp=i -scalaroptimize=1 $(COPTFLAGS)
$(BUILD_DIR)/src/audio/seqplayer.copt: COPTFLAGS := -inline_manual

endif

# Assemble assembly code
$(BUILD_DIR)/%.o: %.s
	$(call print,Assembling:,$<,$@)
	$(V)$(CPP) $(CPPFLAGS) $< | $(AS) $(ASFLAGS) -MD $(BUILD_DIR)/$*.d -o $@

# Assemble RSP assembly code
$(BUILD_DIR)/rsp/%.bin $(BUILD_DIR)/rsp/%_data.bin: rsp/%.s
	$(call print,Assembling:,$<,$@)
	$(V)$(RSPASM) -sym $@.sym $(RSPASMFLAGS) -strequ CODE_FILE $(BUILD_DIR)/rsp/$*.bin -strequ DATA_FILE $(BUILD_DIR)/rsp/$*_data.bin $<

# Run linker script through the C preprocessor
$(BUILD_DIR)/$(LD_SCRIPT): $(LD_SCRIPT)
	$(call print,Preprocessing linker script:,$<,$@)
	$(V)$(CPP) $(CPPFLAGS) -DBUILD_DIR=$(BUILD_DIR) -MMD -MP -MT $@ -MF $@.d -o $@ $<

# Link libultra
$(BUILD_DIR)/libultra.a: $(ULTRA_O_FILES)
	@$(PRINT) "$(GREEN)Linking libultra:  $(BLUE)$@ $(NO_COL)\n"
	$(V)$(AR) rcs -o $@ $(ULTRA_O_FILES)
	$(V)$(TOOLS_DIR)/patch_elf_32bit $@

# Link libgoddard
$(BUILD_DIR)/libgoddard.a: $(GODDARD_O_FILES)
	@$(PRINT) "$(GREEN)Linking libgoddard:  $(BLUE)$@ $(NO_COL)\n"
	$(V)$(AR) rcs -o $@ $(GODDARD_O_FILES)

# Link SM64 ELF file
$(ELF): $(O_FILES) $(MIO0_OBJ_FILES) $(SEG_FILES) $(BUILD_DIR)/$(LD_SCRIPT) undefined_syms.txt $(BUILD_DIR)/libultra.a $(BUILD_DIR)/libgoddard.a
	@$(PRINT) "$(GREEN)Linking ELF file:  $(BLUE)$@ $(NO_COL)\n"
	$(V)$(LD) -L $(BUILD_DIR) -T undefined_syms.txt -T $(BUILD_DIR)/$(LD_SCRIPT) -Map $(BUILD_DIR)/sm64.$(VERSION).map --no-check-sections $(addprefix -R ,$(SEG_FILES)) -o $@ $(O_FILES) -lultra -lgoddard

# Build ROM
$(ROM): $(ELF)
	$(call print,Building ROM:,$<,$@)
	$(V)$(OBJCOPY) --pad-to=0x800000 --gap-fill=0xFF $< $(@:.z64=.bin) -O binary
	$(V)$(N64CKSUM) $(@:.z64=.bin) $@

$(BUILD_DIR)/$(TARGET).objdump: $(ELF)
	$(OBJDUMP) -D $< > $@



.PHONY: all clean distclean default diff test load libultra
# with no prerequisites, .SECONDARY causes no intermediate target to be removed
.SECONDARY:

# Remove built-in rules, to improve performance
MAKEFLAGS += --no-builtin-rules

-include $(DEP_FILES)

print-% : ; $(info $* is a $(flavor $*) variable set to [$($*)]) @true
