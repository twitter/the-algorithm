#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>

/* from elf.h */

/* Type for a 16-bit quantity.  */
typedef uint16_t Elf32_Half;

/* Types for signed and unsigned 32-bit quantities.  */
typedef uint32_t Elf32_Word;

/* Type of addresses.  */
typedef uint32_t Elf32_Addr;

/* Type of file offsets.  */
typedef uint32_t Elf32_Off;

/* The ELF file header.  This appears at the start of every ELF file.  */

#define EI_NIDENT (16)

typedef struct
{
  unsigned char	e_ident[EI_NIDENT];	/* Magic number and other info */
  Elf32_Half	e_type;			/* Object file type */
  Elf32_Half	e_machine;		/* Architecture */
  Elf32_Word	e_version;		/* Object file version */
  Elf32_Addr	e_entry;		/* Entry point virtual address */
  Elf32_Off	e_phoff;		/* Program header table file offset */
  Elf32_Off	e_shoff;		/* Section header table file offset */
  Elf32_Word	e_flags;		/* Processor-specific flags */
  Elf32_Half	e_ehsize;		/* ELF header size in bytes */
  Elf32_Half	e_phentsize;		/* Program header table entry size */
  Elf32_Half	e_phnum;		/* Program header table entry count */
  Elf32_Half	e_shentsize;		/* Section header table entry size */
  Elf32_Half	e_shnum;		/* Section header table entry count */
  Elf32_Half	e_shstrndx;		/* Section header string table index */
} Elf32_Ehdr;

/* Conglomeration of the identification bytes, for easy testing as a word.  */
#define	ELFMAG		"\177ELF"
#define	SELFMAG		4

#define EI_CLASS	4		/* File class byte index */
#define ELFCLASS32	1		/* 32-bit objects */

#define EI_DATA		5		/* Data encoding byte index */
#define ELFDATA2MSB	2		/* 2's complement, big endian */

/* end from elf.h */

// This file will find all mips3 object files in an ar archive and set the ABI flags to O32
// this allows gcc to link them with the mips2 object files.
// Irix CC doesn't set the elf e_flags properly.

// the AR file is structured as followed
//"!<arch>" followed by 0x0A (linefeed) 8 characters
// then a file header that follows the following structure
// everything is represented using space padded characters
// the last two characters are alwos 0x60 0x0A
// then come the file contents
// you can find the location of the next header by adding file_size_in_bytes (after parsing)
// all file headers start at an even offset so if the file size in bytes is odd you have to add 1
// the first two "files" are special.  One is a symbol table with a pointer to the header of the file
// contaning the symbol the other is an extended list of filenames
struct ar_header {
    char identifier[16];
    char file_modification_timestamp[12];
    char owner_id[6];
    char group_id[6];
    char file_mode[8];
    char file_size_in_bytes[10];
    char ending[2];
};

//These constants found by inspecting output of objdump
#define FLAGS_MIPS3 0x20
#define FLAGS_O32ABI 0x100000 
int main(int argc, char **argv) {
    FILE *f = fopen(argv[1], "r+");

    if (f == NULL) {
        printf("Failed to open file! %s\n", argv[1]);
        return -1;
    }
    struct ar_header current_header;
    fseek(f, 0x8, SEEK_SET); // skip header, this is safe enough given that we check to make sure the
                             // file header is valid

    while (1 == fread(&current_header, sizeof(current_header), 1, f)) {
        if (current_header.ending[0] != 0x60 && current_header.ending[1] != 0x0A) {
            printf("Expected file header\n");
            return -1;
        }
        size_t filesize = atoi(current_header.file_size_in_bytes);
        Elf32_Ehdr hdr;
        if (filesize < sizeof(hdr) || (1 != fread(&hdr, sizeof(hdr), 1, f))) {
            printf("Failed to read ELF header\n");
            return -1;
        }

        if (strncmp((const char *) hdr.e_ident, ELFMAG, SELFMAG) == 0) {
            // found an ELF file.
            if (hdr.e_ident[EI_CLASS] != ELFCLASS32 || hdr.e_ident[EI_DATA] != ELFDATA2MSB) {
                printf("Expected 32bit big endian object files\n");
                return -1;
            }

            if ((hdr.e_flags & 0xFF) == FLAGS_MIPS3 && (hdr.e_flags & FLAGS_O32ABI) == 0) {
                hdr.e_flags |= FLAGS_O32ABI;
                fseek(f, -sizeof(hdr), SEEK_CUR);
                if (1 != fwrite(&hdr, sizeof(hdr), 1, f)) {
                    printf("Failed to write back ELF header after patching.\n");
                    return -1;
                }
            }
        }
        if (filesize % 2 == 1)
            filesize++;
        fseek(f, filesize - sizeof(hdr), SEEK_CUR);
    }
    fclose(f);
    return 0;
}
