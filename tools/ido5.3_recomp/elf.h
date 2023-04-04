#ifndef ELF_H
#define ELF_H

#include <stdint.h>

#define EI_DATA      5
#define EI_NIDENT    16
#define SHT_SYMTAB   2
#define SHT_DYNAMIC  6
#define SHT_REL      9
#define SHT_DYNSYM   11
#define SHT_MIPS_REGINFO    0x70000006
#define STN_UNDEF    0
#define STT_OBJECT   1
#define STT_FUNC     2
#define DT_PLTGOT    3
#define DT_MIPS_LOCAL_GOTNO 0x7000000a
#define DT_MIPS_SYMTABNO    0x70000011
#define DT_MIPS_GOTSYM      0x70000013

#define ELF32_R_SYM(info)  ((info) >> 8)
#define ELF32_R_TYPE(info) ((info) & 0xff)

#define ELF32_ST_TYPE(info) ((info) & 0xf)

#define R_MIPS_26    4
#define R_MIPS_HI16  5
#define R_MIPS_LO16  6

#define SHN_UNDEF 0
#define SHN_COMMON 0xfff2
#define SHN_MIPS_ACOMMON 0xff00
#define SHN_MIPS_TEXT 0xff01
#define SHN_MIPS_DATA 0xff02

typedef uint32_t Elf32_Addr;
typedef uint32_t Elf32_Off;

typedef struct {
   uint8_t    e_ident[EI_NIDENT];
   uint16_t   e_type;
   uint16_t   e_machine;
   uint32_t   e_version;
   Elf32_Addr e_entry;
   Elf32_Off  e_phoff;
   Elf32_Off  e_shoff;
   uint32_t   e_flags;
   uint16_t   e_ehsize;
   uint16_t   e_phentsize;
   uint16_t   e_phnum;
   uint16_t   e_shentsize;
   uint16_t   e_shnum;
   uint16_t   e_shstrndx;
} Elf32_Ehdr;

typedef struct {
   uint32_t   sh_name;
   uint32_t   sh_type;
   uint32_t   sh_flags;
   Elf32_Addr sh_addr;
   Elf32_Off  sh_offset;
   uint32_t   sh_size;
   uint32_t   sh_link;
   uint32_t   sh_info;
   uint32_t   sh_addralign;
   uint32_t   sh_entsize;
} Elf32_Shdr;

typedef struct {
   uint32_t   st_name;
   Elf32_Addr st_value;
   uint32_t   st_size;
   uint8_t    st_info;
   uint8_t    st_other;
   uint16_t   st_shndx;
} Elf32_Sym;

typedef struct {
   Elf32_Addr r_offset;
   uint32_t   r_info;
} Elf32_Rel;

typedef struct
{
   uint32_t ri_gprmask;    /* General registers used.  */
   uint32_t ri_cprmask[4]; /* Coprocessor registers used.  */
   int32_t  ri_gp_value;   /* $gp register value.  */
} Elf32_RegInfo;

typedef struct
{
   int32_t d_tag;          /* Dynamic entry type */
   union {
      uint32_t d_val;      /* Integer value */
      Elf32_Addr d_ptr;    /* Address value */
   } d_un;
} Elf32_Dyn;

#endif
