#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>

#define EI_DATA 5
#define EI_NIDENT 16

#define STT_NOTYPE  0
#define STT_OBJECT  1
#define STT_FUNC    2
#define STT_SECTION 3
#define STT_FILE    4
#define STT_COMMON  5
#define STT_TLS     6

#define ELF_ST_TYPE(x) (((unsigned int) x) & 0xf)

typedef uint32_t Elf32_Addr;
typedef uint32_t Elf32_Off;

typedef struct {
    unsigned char e_ident[EI_NIDENT];
    uint16_t      e_type;
    uint16_t      e_machine;
    uint32_t      e_version;
    Elf32_Addr    e_entry;
    Elf32_Off     e_phoff;
    Elf32_Off     e_shoff;
    uint32_t      e_flags;
    uint16_t      e_ehsize;
    uint16_t      e_phentsize;
    uint16_t      e_phnum;
    uint16_t      e_shentsize;
    uint16_t      e_shnum;
    uint16_t      e_shstrndx;
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
    uint32_t      st_name;
    Elf32_Addr    st_value;
    uint32_t      st_size;
    unsigned char st_info;
    unsigned char st_other;
    uint16_t      st_shndx;
} Elf32_Sym;

typedef struct {
    uint16_t magic; //To verify validity of the table
    uint16_t vstamp; //Version stamp
    uint32_t ilineMax; //Number of line number entries
    uint32_t cbLine; //Number of bytes for line number entries
    uint32_t cbLineOffset; //Index to start of line numbers
    uint32_t idnMax; //Max index into dense numbers
    uint32_t cbDnOffset; //Index to start dense numbers
    uint32_t ipdMax; //Number of procedures
    uint32_t cbPdOffset; //Index to procedure descriptors
    uint32_t isymMax; //Number of local symbols
    uint32_t cbSymOffset; //Index to start of local symbols
    uint32_t ioptMax; //Maximum index into optimization entries
    uint32_t cbOptOffset; //Index to start of optimization entries
    uint32_t iauxMax; //Number of auxiliary symbols
    uint32_t cbAuxOffset; //Index to the start of auxiliary symbols
    uint32_t issMax; //Max index into local strings
    uint32_t cbSsOffset; //Index to start of local strings
    uint32_t issExtMax; //Max index into external strings
    uint32_t cbSsExtOffset; //Index to the start of external strings
    uint32_t ifdMax; //Number of file descriptors
    uint32_t cbFdOffset; //Index to file descriptor
    uint32_t crfd; //Number of relative file descriptors
    uint32_t cbRfdOffset; //Index to relative file descriptors
    uint32_t iextMax; //Maximum index into external symbols
    uint32_t cbExtOffset; //Index to the start of external symbols.
} SymbolicHeader;

typedef struct {
    uint32_t adr; // Memory address of start of file
    uint32_t rss; // Source file name
    uint32_t issBase; // Start of local strings
    uint32_t cbSs; // Number of bytes in local strings
    uint32_t isymBase; // Start of local symbol entries
    uint32_t csym; // Count of local symbol entries
    uint32_t ilineBase; // Start of line number entries
    uint32_t cline; // Count of line number entries
    uint32_t ioptBase; // Start of optimization symbol entries
    uint32_t copt; // Count of optimization symbol entries
    uint16_t ipdFirst; // Start of procedure descriptor table
    uint16_t cpd; // Count of procedures descriptors
    uint32_t iauxBase; // Start of auxiliary symbol entries
    uint32_t caux; // Count of auxiliary symbol entries
    uint32_t rfdBase; // Index into relative file descriptors
    uint32_t crfd; // Relative file descriptor count
    uint32_t flags;
    uint32_t cbLineOffset; // Byte offset from header or file ln's
    uint32_t cbLine;
} FileDescriptorTable;

typedef struct {
    uint32_t iss;
    uint32_t value;
    uint32_t st_sc_index;
} LocalSymbolsEntry;

typedef enum {
    stNil,
    stGlobal,
    stStatic,
    stParam,
    stLocal,
    stLabel,
    stProc,
    stBlock,
    stEnd,
    stMember,
    stTypedef,
    stFile,
    stStaticProc,
    stConstant
} StConstants;

uint32_t u32be(uint32_t val) {
#if __BYTE_ORDER__ == __ORDER_LITTLE_ENDIAN__
    return __builtin_bswap32(val);
#else
    return val;
#endif
}

uint16_t u16be(uint16_t val) {
#if __BYTE_ORDER__ == __ORDER_LITTLE_ENDIAN__
    return __builtin_bswap16(val);
#else
    return val;
#endif    
}

static bool elf_get_section_range(uint8_t *file, const char *searched_name, uint32_t *address, uint32_t *offset, uint32_t *size, uint32_t *section_index) {
    Elf32_Ehdr *ehdr = (Elf32_Ehdr *)file;

    for (int i = 0; i < u16be(ehdr->e_shnum); i++) {
        if (memcmp("\x7f" "ELF", ehdr->e_ident, 4) != 0) {
            fprintf(stderr, "Missing ELF magic\n");
            exit(1);
        }
        if (ehdr->e_ident[EI_DATA] != 2) {
            fprintf(stderr, "ELF file is not big-endian\n");
            exit(1);
        }

        Elf32_Shdr *shdr = (Elf32_Shdr *)(file + u32be(ehdr->e_shoff) + i * u16be(ehdr->e_shentsize));
        if (u16be(ehdr->e_shstrndx) >= u16be(ehdr->e_shnum)) {
            fprintf(stderr, "Invalid ELF file\n");
            exit(1);
        }
        Elf32_Shdr *str_shdr = (Elf32_Shdr *)(file + u32be(ehdr->e_shoff) + u16be(ehdr->e_shstrndx) * u16be(ehdr->e_shentsize));
        char *name = (char *)(file + u32be(str_shdr->sh_offset) + u32be(shdr->sh_name));
        if (memcmp(name, searched_name, strlen(searched_name)) == 0) {
            *address = u32be(shdr->sh_addr);
            *offset = u32be(shdr->sh_offset);
            *size = u32be(shdr->sh_size);
            *section_index = i;
            return true;
        }
    }
    return false;
}

int main(int argc, char *argv[]) {
    if (argc < 3) {
        fprintf(stderr, "Usage: %s INFILE OUTFILE\n", argv[0]);
        return 1;
    }

    FILE *in = fopen(argv[1], "rb");
    if (in == NULL) {
        perror("fopen couldn't open input file");
        exit(1);
    }
    fseek(in, 0, SEEK_END);
    size_t file_size = ftell(in);
    fseek(in, 0, SEEK_SET);
    uint8_t *file = malloc(file_size);
    if (fread(file, 1, file_size, in) != file_size) {
        fclose(in);
        fprintf(stderr, "Failed to read file: %s\n", argv[1]);
        exit(1);
    }
    fclose(in);

    uint32_t data_address, data_offset, data_size, data_index;
    if (!elf_get_section_range(file, ".data", &data_address, &data_offset, &data_size, &data_index)) {
        fprintf(stderr, "section .data not found\n");
        exit(1);
    }

    uint32_t rodata_address, rodata_offset, rodata_size, rodata_index;
    if (elf_get_section_range(file, ".rodata", &rodata_address, &rodata_offset, &rodata_size, &rodata_index)) {
        fprintf(stderr, ".rodata section found, please put everything in .data instead (non-const variables)\n");
        exit(1);
    }

    uint32_t symtab_address, symtab_offset, symtab_size, symtab_index;
    if (!elf_get_section_range(file, ".symtab", &symtab_address, &symtab_offset, &symtab_size, &symtab_index)) {
        fprintf(stderr, "section .symtab not found\n");
        exit(1);
    }

    uint32_t strtab_address, strtab_offset, strtab_size, strtab_index;
    if (!elf_get_section_range(file, ".strtab", &strtab_address, &strtab_offset, &strtab_size, &strtab_index)) {
        fprintf(stderr, "section .strtab not found\n");
        exit(1);
    }

    // IDO might pad the section to the nearest 16 byte boundary,
    // but the mio0 data should not include that. Therefore find
    // the "real" end by finding where the last symbol ends.
    uint32_t last_symbol_end = 0;

    for (uint32_t i = 0; i < symtab_size / sizeof(Elf32_Sym); i++) {
        Elf32_Sym *symbol = (Elf32_Sym *)(file + symtab_offset + i * sizeof(Elf32_Sym));
#if DEBUG
        const char *name = "(null)";
        if (symbol->st_name != 0U) {
            name = (const char*)file + strtab_offset + u32be(symbol->st_name);
        }
        printf("%08x\t%08x\t%02x\t%02x\t%02x\t%s\n", u32be(symbol->st_value), u32be(symbol->st_size), symbol->st_info, symbol->st_other, u16be(symbol->st_shndx), name);
#endif
        if (ELF_ST_TYPE(symbol->st_info) == STT_OBJECT && u16be(symbol->st_shndx) == data_index) {
            uint32_t symbol_end = u32be(symbol->st_value) + u32be(symbol->st_size);
            if (symbol_end > last_symbol_end) {
                last_symbol_end = symbol_end;
            }
        }
    }

    uint32_t mdebug_address, mdebug_offset, mdebug_size, mdebug_index;
    if (elf_get_section_range(file, ".mdebug", &mdebug_address, &mdebug_offset, &mdebug_size, &mdebug_index)) {
        SymbolicHeader *symbolic_header = (SymbolicHeader *)(file + mdebug_offset);

        for (uint32_t i = 0; i < u32be(symbolic_header->ifdMax); i++) {
            FileDescriptorTable *fdt = (FileDescriptorTable *)(file + u32be(symbolic_header->cbFdOffset) + i * sizeof(FileDescriptorTable));

            for (uint32_t j = 0; j < u32be(fdt->csym); j++) {
                LocalSymbolsEntry lse;
                memcpy(&lse, file + u32be(symbolic_header->cbSymOffset) + (u32be(fdt->isymBase) + j) * sizeof(LocalSymbolsEntry), sizeof(LocalSymbolsEntry));

                uint32_t value = u32be(lse.value);
                uint32_t st_sc_index = u32be(lse.st_sc_index);
                uint32_t st = (st_sc_index >> 26);
#ifdef DEBUG
                uint32_t sc = (st_sc_index >> 21) & 0x1f;
                uint32_t index = st_sc_index & 0xfffff;
                uint32_t iss = u32be(lse.iss);
                const char *symbol_name = file + u32be(symbolic_header->cbSsOffset) + iss;
                printf("%s %08x\n", symbol_name, value);
#endif

                if (st == stStatic || st == stGlobal) {
                    // Right now just assume length 8 since it's quite much work to extract the real size
                    uint32_t symbol_end = value + 8;
                    if (symbol_end > last_symbol_end) {
                        last_symbol_end = symbol_end;
                    }
                }
            }
        }
    }

#ifdef DEBUG
    printf("Last symbol end: %08x\n", last_symbol_end);
#endif

    size_t new_size = last_symbol_end - data_address;
    if (new_size + 16 <= data_size) {
        // There seems to be more than 16 bytes padding or non-identified data, so abort and take the original size
        new_size = data_size;
    } else {
        // Make sure we don't cut off non-zero bytes
        for (size_t i = new_size; i < data_size; i++) {
            if (file[data_offset + i] != 0) {
                // Must be some symbol missing, so abort and take the original size
                new_size = data_size;
                break;
            }
        }
    }

    FILE *out = fopen(argv[2], "wb");
    fwrite(file + data_offset, 1, new_size, out);
    fclose(out);

    free(file);
    return 0;
}
