#define _GNU_SOURCE // for sigset
#include <stdbool.h>
#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <assert.h>
#include <errno.h>
#include <time.h>
#include <limits.h>
#include <ctype.h>
#include <locale.h>
#include <libgen.h>

#ifdef __CYGWIN__
#include <windows.h>
#endif
#ifdef __APPLE__
  #include <mach-o/dyld.h>
#endif

#include <sys/mman.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/times.h>
#include <sys/file.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <utime.h>
#include <unistd.h>
#include <signal.h>

#include "libc_impl.h"
#include "helpers.h"

#define MIN(a, b) ((a) < (b) ? (a) : (b))
#define MAX(a, b) ((a) > (b) ? (a) : (b))

#define STRING(param) size_t param##_len = wrapper_strlen(mem, param##_addr); \
    char param[param##_len + 1]; \
    for (size_t i = 0; i <= param##_len; i++) { \
        param[i] = MEM_S8(param##_addr + i); \
    }

#if !defined(IDO53) && !defined(IDO71)
#define IDO71
#endif

#define MEM_REGION_START 0xfb00000
#define MEM_REGION_SIZE (512 * 1024 * 1024)

#ifdef IDO53
// IDO 5.3
#define IOB_ADDR 0x0fb528e4
#define ERRNO_ADDR 0x0fb52720
#define CTYPE_ADDR 0x0fb504f0
#define LIBC_ADDR 0x0fb50000
#define LIBC_SIZE 0x3000
#endif

#ifdef IDO71
// IDO 7.1
#define IOB_ADDR 0x0fb4ee44
#define ERRNO_ADDR 0x0fb4ec80
#define CTYPE_ADDR 0x0fb4cba0
#define LIBC_ADDR 0x0fb4c000
#define LIBC_SIZE 0x3000
#endif

#define STDIN_ADDR IOB_ADDR
#define STDOUT_ADDR (IOB_ADDR + 0x10)
#define STDERR_ADDR (IOB_ADDR + 0x20)
#define STDIN ((struct FILE_irix *)&MEM_U32(STDIN_ADDR))
#define STDOUT ((struct FILE_irix *)&MEM_U32(STDOUT_ADDR))
#define STDERR ((struct FILE_irix *)&MEM_U32(STDERR_ADDR))

#define MALLOC_BINS_ADDR custom_libc_data_addr
#define STRTOK_DATA_ADDR (MALLOC_BINS_ADDR + (30 - 3) * 4)
#define INTBUF_ADDR (STRTOK_DATA_ADDR + 4)

#define SIGNAL_HANDLER_STACK_START LIBC_ADDR

#define NFILE 100

#define IOFBF 0000 /* full buffered */
#define IOLBF 0100 /* line buffered */
#define IONBF 0004 /* not buffered */
#define IOEOF 0020 /* EOF reached on read */
#define IOERR 0040 /* I/O error from system */

#define IOREAD  0001 /* currently reading */
#define IOWRT   0002 /* currently writing */
#define IORW    0200 /* opened for reading and writing */
#define IOMYBUF 0010 /* stdio malloc()'d buffer */

#define STDIO_BUFSIZE 16384

struct timespec_t_irix {
    int tv_sec;
    int tv_nsec;
};

struct FILE_irix {
    int _cnt;
    uint32_t _ptr_addr;
    uint32_t _base_addr;
    uint8_t pad[2];
    uint8_t _file;
    uint8_t _flag;
};

static struct {
    struct {
        uint64_t (*trampoline)(uint8_t *mem, uint32_t sp, uint32_t a0, uint32_t a1, uint32_t a2, uint32_t a3, uint32_t fp_dest);
        uint8_t *mem;
        uint32_t fp_dest;
    } handlers[65];
    volatile uint32_t recursion_level;
} signal_context;

static uint32_t cur_sbrk;
static uint32_t bufendtab[NFILE]; // this version contains the size and not the end ptr
static uint32_t custom_libc_data_addr;

#define _U 01   /* Upper case */
#define _L 02   /* Lower case */
#define _N 04   /* Numeral (digit) */
#define _S 010  /* Spacing character */
#define _P 020  /* Punctuation */
#define _C 040  /* Control character */
#define _B 0100 /* Blank */
#define _X 0200 /* heXadecimal digit */

static char ctype[] = { 0,

/*       0       1       2       3       4       5       6       7  */

/* 0*/  _C,     _C,     _C,     _C,     _C,     _C,     _C,     _C,
/* 10*/ _C,     _S|_C,  _S|_C,  _S|_C,  _S|_C,  _S|_C,  _C,     _C,
/* 20*/ _C,     _C,     _C,     _C,     _C,     _C,     _C,     _C,
/* 30*/ _C,     _C,     _C,     _C,     _C,     _C,     _C,     _C,
/* 40*/ _S|_B,  _P,     _P,     _P,     _P,     _P,     _P,     _P,
/* 50*/ _P,     _P,     _P,     _P,     _P,     _P,     _P,     _P,
/* 60*/ _N|_X,  _N|_X,  _N|_X,  _N|_X,  _N|_X,  _N|_X,  _N|_X,  _N|_X,
/* 70*/ _N|_X,  _N|_X,  _P,     _P,     _P,     _P,     _P,     _P,
/*100*/ _P,     _U|_X,  _U|_X,  _U|_X,  _U|_X,  _U|_X,  _U|_X,  _U,
/*110*/ _U,     _U,     _U,     _U,     _U,     _U,     _U,     _U,
/*120*/ _U,     _U,     _U,     _U,     _U,     _U,     _U,     _U,
/*130*/ _U,     _U,     _U,     _P,     _P,     _P,     _P,     _P,
/*140*/ _P,     _L|_X,  _L|_X,  _L|_X,  _L|_X,  _L|_X,  _L|_X,  _L,
/*150*/ _L,     _L,     _L,     _L,     _L,     _L,     _L,     _L,
/*160*/ _L,     _L,     _L,     _L,     _L,     _L,     _L,     _L,
/*170*/ _L,     _L,     _L,     _P,     _P,     _P,     _P,     _C,
/*200*/  0,      0,      0,      0,      0,      0,      0,      0,
         0,      0,      0,      0,      0,      0,      0,      0,
         0,      0,      0,      0,      0,      0,      0,      0,
         0,      0,      0,      0,      0,      0,      0,      0,
         0,      0,      0,      0,      0,      0,      0,      0,
         0,      0,      0,      0,      0,      0,      0,      0,
         0,      0,      0,      0,      0,      0,      0,      0,
         0,      0,      0,      0,      0,      0,      0,      0,
         0,      0,      0,      0,      0,      0,      0,      0,
         0,      0,      0,      0,      0,      0,      0,      0,
         0,      0,      0,      0,      0,      0,      0,      0,
         0,      0,      0,      0,      0,      0,      0,      0,
         0,      0,      0,      0,      0,      0,      0,      0,
         0,      0,      0,      0,      0,      0,      0,      0,
         0,      0,      0,      0,      0,      0,      0,      0,
         0,      0,      0,      0,      0,      0,      0,      0
};

#define REDIRECT_USR_LIB

#ifdef REDIRECT_USR_LIB
static char bin_dir[PATH_MAX + 1];
#endif
static int g_file_max = 3;

#if defined(__CYGWIN__) || defined(__APPLE__)
static size_t g_Pagesize;
#endif

static uint8_t *memory_map(size_t length)
{
#if defined(__CYGWIN__) || defined(__APPLE__)
    uint8_t *mem = mmap(0, length, PROT_NONE, MAP_PRIVATE | MAP_ANONYMOUS | MAP_NORESERVE, -1, 0);
    g_Pagesize = sysconf(_SC_PAGESIZE);
    assert(((uintptr_t)mem & (g_Pagesize-1)) == 0);
#else
    uint8_t *mem = mmap(0, length, PROT_NONE, MAP_PRIVATE | MAP_ANONYMOUS, -1, 0);
#endif
    if (mem == MAP_FAILED) {
        perror("mmap");
        exit(1);
    }
    return mem;
}

static void memory_allocate(uint8_t *mem, uint32_t start, uint32_t end)
{
    assert(start >= MEM_REGION_START);
    assert(end <= MEM_REGION_START + MEM_REGION_SIZE);
#if defined(__CYGWIN__) || defined(__APPLE__)
    uintptr_t _start = ((uintptr_t)mem + start) & ~(g_Pagesize-1);
    uintptr_t _end = ((uintptr_t)mem + end + (g_Pagesize-1)) & ~(g_Pagesize-1);

    if(mprotect((void*)_start, _end - _start, PROT_READ | PROT_WRITE) < 0) {
        perror("mprotect");
        exit(1);
    }
#else
    if (mmap(mem + start, end - start, PROT_READ | PROT_WRITE, MAP_FIXED | MAP_PRIVATE | MAP_ANONYMOUS, -1, 0) == MAP_FAILED) {
        perror("mmap");
        exit(1);
    }
#endif
}

static void memory_unmap(uint8_t *mem, size_t length)
{
    if (munmap(mem, length)) {
        perror("munmap");
        exit(1);
    }
}


static void free_all_file_bufs(uint8_t *mem) {
    struct FILE_irix *f = (struct FILE_irix *)&MEM_U32(IOB_ADDR);
    for (int i = 0; i < g_file_max; i++) {
        if (f[i]._flag & IOMYBUF) {
            wrapper_free(mem, f[i]._base_addr);
        }
    }
}

static void find_bin_dir(void) {
#ifdef REDIRECT_USR_LIB
    // gets the current executable's path
    char path[PATH_MAX + 1] = {0};
#ifdef __CYGWIN__
    uint32_t size = GetModuleFileName(NULL, path, PATH_MAX);
    if (size == 0 || size == PATH_MAX) {
        return;
    }
#elif defined __APPLE__
    uint32_t size = PATH_MAX;
    if (_NSGetExecutablePath(path, &size) < 0) {
        return;
    }
#else
    ssize_t size = readlink("/proc/self/exe", path, PATH_MAX);
    if (size < 0 || size == PATH_MAX) {
        return;
    }
#endif

    strcpy(bin_dir, dirname(path));
#endif
}

int main(int argc, char *argv[]) {
    int ret;

    find_bin_dir();

    uint8_t *mem = memory_map(MEM_REGION_SIZE);
    mem -= MEM_REGION_START;
    int run(uint8_t *mem, int argc, char *argv[]);
    ret = run(mem, argc, argv);
    wrapper_fflush(mem, 0);
    free_all_file_bufs(mem);
    mem += MEM_REGION_START;
    memory_unmap(mem, MEM_REGION_SIZE);
    return ret;
}

void mmap_initial_data_range(uint8_t *mem, uint32_t start, uint32_t end) {
    custom_libc_data_addr = end;
    end += 4096;
    memory_allocate(mem, start, end);
    cur_sbrk = end;
}

void setup_libc_data(uint8_t *mem) {
    memory_allocate(mem, LIBC_ADDR, (LIBC_ADDR + LIBC_SIZE));
    for (size_t i = 0; i < sizeof(ctype); i++) {
        MEM_S8(CTYPE_ADDR + i) = ctype[i];
    }
    STDIN->_flag = IOREAD;
    STDIN->_file = 0;
    STDOUT->_flag = IOWRT;
    STDOUT->_file = 1;
    STDERR->_flag = IOWRT | IONBF;
    STDERR->_file = 2;
}

static uint32_t strcpy1(uint8_t *mem, uint32_t dest_addr, const char *str) {
    for (;;) {
        char c = *str;
        ++str;
        MEM_S8(dest_addr) = c;
        ++dest_addr;
        if (c == '\0') {
            return dest_addr - 1;
        }
    }
}

static uint32_t strcpy2(uint8_t *mem, uint32_t dest_addr, uint32_t src_addr) {
    for (;;) {
        char c = MEM_S8(src_addr);
        ++src_addr;
        MEM_S8(dest_addr) = c;
        ++dest_addr;
        if (c == '\0') {
            return dest_addr - 1;
        }
    }
}

uint32_t wrapper_sbrk(uint8_t *mem, int increment) {
    uint32_t old = cur_sbrk;
    memory_allocate(mem, old, (old + increment));
    cur_sbrk += increment;
    return old;
}

#if 0
uint32_t wrapper_malloc(uint8_t *mem, uint32_t size) {
    uint32_t orig_size = size;
    size += 8;
    size = (size + 0xfff) & ~0xfff;
    uint32_t ret = wrapper_sbrk(mem, size);
    MEM_U32(ret) = orig_size;
    return ret + 8;
}

uint32_t wrapper_calloc(uint8_t *mem, uint32_t num, uint32_t size) {
    uint64_t new_size = (uint64_t)num * size;
    assert(new_size == (uint32_t)new_size);
    uint32_t ret = wrapper_malloc(mem, new_size);
    return wrapper_memset(mem, ret, 0, new_size);
}

uint32_t wrapper_realloc(uint8_t *mem, uint32_t data_addr, uint32_t size) {
    if (data_addr == 0) {
        return wrapper_malloc(mem, size);
    }
    uint32_t orig_size = MEM_U32(data_addr - 8);
    if (size < orig_size || orig_size < 4088 && size < 4088) {
        MEM_U32(data_addr - 8) = size;
        return data_addr;
    }
    uint32_t new_addr = wrapper_malloc(mem, size);
    return wrapper_memcpy(mem, new_addr, data_addr, MIN(size, orig_size));
}

void wrapper_free(uint8_t *mem, uint32_t data_addr) {
    // NOP
}
#else

/*
Simple bin-based malloc algorithm

The memory is divided into bins of item sizes 8, 16, 32, 64, 128, ..., 2^30.
Size requests are divided into these bin sizes and each bin is handled
completely separate from other bins.

For each bin there is a linked list of free'd items.
Linked list node:
struct FreeListNode {
    struct Node *next;
    size_t free_space_after;
    uint8_t data[bin_item_size];
};
At most one value of next and space_after is non-zero.
If a node exists in the linked list, it is the memory node to return.
struct AllocatedNode {
    int bin;
    uint32_t current_size;
    uint8_t data[bin_item_size];
};
The returned address is the data element.
When the last list node is returned, and free_space_after is big enough
for a new node, a new node is created having free_space_after set to
(free_space_after - (8 + bin_item_size)), and is appended to the list.

If the list was empty, a new memory chunk is requested from the system
of 65536 bytes, or at least (8 + bin_item_size), rounded up to nearest
page size boundary. It can also be smaller if it leaves holes bigger than
4096 bytes that can never be used. This chunk is then inserted to the list,
and the algorithm restarts.

This algorithm, for each bin, never uses more than twice as much as is
maximally in use (plus 65536 bytes).
The malloc/free calls run in O(1) and calloc/realloc calls run in O(size).
*/

size_t mem_used;
size_t mem_allocated;
size_t max_mem_used;
size_t num_sbrks;
size_t num_allocs;
uint32_t wrapper_malloc(uint8_t *mem, uint32_t size) {
    int bin = -1;
    for (int i = 3; i < 30; i++) {
        if (size <= (1 << i)) {
            bin = i;
            break;
        }
    }
    if (bin == -1) {
        return 0;
    }
    ++num_allocs;
    mem_used += size;
    max_mem_used = MAX(mem_used, max_mem_used);
    uint32_t item_size = 1 << bin;
    uint32_t list_ptr = MALLOC_BINS_ADDR + (bin - 3) * 4;
    uint32_t node_ptr = MEM_U32(list_ptr);
    if (node_ptr == 0) {
        uint32_t sbrk_request = 0x10000;
        if (8 + item_size > sbrk_request) {
            sbrk_request = 8 + item_size;
            sbrk_request = (sbrk_request + 0xfff) & ~0xfff;
        }
        uint32_t left_over = sbrk_request % (8 + item_size);
        sbrk_request -= left_over & ~0xfff;
        mem_allocated += sbrk_request;
        ++num_sbrks;
        node_ptr = wrapper_sbrk(mem, sbrk_request);
        MEM_U32(node_ptr + 4) = sbrk_request - (8 + item_size);
    }
    uint32_t next = MEM_U32(node_ptr);
    if (next == 0) {
        uint32_t free_space_after = MEM_U32(node_ptr + 4);
        if (free_space_after >= 8 + item_size) {
            next = node_ptr + 8 + item_size;
            MEM_U32(next + 4) = free_space_after - (8 + item_size);
        }
    } else {
        assert(MEM_U32(node_ptr + 4) == 0);
    }
    MEM_U32(list_ptr) = next;
    MEM_U32(node_ptr) = bin;
    MEM_U32(node_ptr + 4) = size;
    return node_ptr + 8;
}

uint32_t wrapper_calloc(uint8_t *mem, uint32_t num, uint32_t size) {
    uint64_t new_size = (uint64_t)num * size;
    assert(new_size == (uint32_t)new_size);
    uint32_t ret = wrapper_malloc(mem, new_size);
    return wrapper_memset(mem, ret, 0, new_size);
}

uint32_t wrapper_realloc(uint8_t *mem, uint32_t data_addr, uint32_t size) {
    if (data_addr == 0) {
        return wrapper_malloc(mem, size);
    } else {
        uint32_t node_ptr = data_addr - 8;
        int bin = MEM_U32(node_ptr);
        uint32_t old_size = MEM_U32(node_ptr + 4);
        uint32_t max_size = 1 << bin;
        assert(bin >= 3 && bin < 30);
        assert(old_size <= max_size);
        if (size <= max_size) {
            mem_used = mem_used - old_size + size;
            MEM_U32(node_ptr + 4) = size;
            return data_addr;
        } else {
            uint32_t new_addr = wrapper_malloc(mem, size);
            wrapper_memcpy(mem, new_addr, data_addr, old_size);
            wrapper_free(mem, data_addr);
            return new_addr;
        }
    }
}

void wrapper_free(uint8_t *mem, uint32_t data_addr) {
    uint32_t node_ptr = data_addr - 8;
    int bin = MEM_U32(node_ptr);
    uint32_t size = MEM_U32(node_ptr + 4);
    uint32_t list_ptr = MALLOC_BINS_ADDR + (bin - 3) * 4;
    assert(bin >= 3 && bin < 30);
    assert(size <= (1 << bin));
    MEM_U32(node_ptr) = MEM_U32(list_ptr);
    MEM_U32(node_ptr + 4) = 0;
    MEM_U32(list_ptr) = node_ptr;
    mem_used -= size;
}
#endif

int wrapper_fscanf(uint8_t *mem, uint32_t fp_addr, uint32_t format_addr, uint32_t sp) {
    struct FILE_irix *f = (struct FILE_irix *)&MEM_U32(fp_addr);
    STRING(format) // for debug

    int ret = 0;
    char c;
    int ch;
    sp += 2 * 4;
    for (;;) {
        c = MEM_S8(format_addr);
        ++format_addr;
        if (c == '%') {
            c = MEM_S8(format_addr);
            ++format_addr;
            if (c == '%') {
                goto percent;
            }
            for (;;) {
                ch = wrapper_fgetc(mem, fp_addr);
                if (ch == -1) {
                    return ret;
                }
                if (!isspace(ch)) {
                    //wrapper_ungetc(mem, ch, fp_addr);
                    break;
                }
            }
            bool l = false;
            continue_format:
            switch (c) {
                case 'l':
                    assert(!l && "ll not implemented in fscanf");
                    l = true;
                    c = MEM_S8(format_addr);
                    ++format_addr;
                    goto continue_format;
                case 'd':
                {
                    int64_t num = 0;
                    int sign = 1;
                    bool found_first = false;
                    if (ch == '-') {
                        sign = -1;
                        ch = wrapper_fgetc(mem, fp_addr);
                        if (ch == -1) {
                            return ret;
                        }
                    }
                    for (;;) {
                        if (isdigit(ch)) {
                            num *= 10;
                            num += ch - '0';
                            found_first = true;
                            ch = wrapper_fgetc(mem, fp_addr);
                            if (ch == -1) {
                                break;
                            }
                        } else {
                            wrapper_ungetc(mem, ch, fp_addr);
                            break;
                        }
                    }
                    if (found_first) {
                        uint32_t int_addr = MEM_U32(sp);
                        sp += 4;
                        MEM_S32(int_addr) = (int)(num * sign);
                        ++ret;
                    } else {
                        return ret;
                    }
                    break;
                }
                default:
                    assert(0 && "fscanf format not implemented");
            }
        } else if (c == '\0') {
            break;
        } else {
            percent:
            ch = wrapper_fgetc(mem, fp_addr);
            if (ch == -1) {
                break;
            }
            if ((char)ch != c) {
                break;
            }
        }
    }

    return ret;
}

int wrapper_printf(uint8_t *mem, uint32_t format_addr, uint32_t sp) {
    STRING(format)
    if (!strcmp(format, " child died due to signal %d.\n")) {
        printf(format, MEM_U32(sp + 4));
        return 1;
    }
    assert(0 && "printf not implemented");
    return 0;
}

int wrapper_sprintf(uint8_t *mem, uint32_t str_addr, uint32_t format_addr, uint32_t sp) {
    STRING(format) // for debug
    char temp[32];

    if (!strcmp(format, "%.16e")) {
        union {
            uint32_t w[2];
            double d;
        } d;
        d.w[1] = MEM_U32(sp + 2 * 4);
        d.w[0] = MEM_U32(sp + 3 * 4);
        sprintf(temp, "%.16e", d.d);
        strcpy1(mem, str_addr, temp);
        return 1;
    }
    if (!strcmp(format, "\\%03o")) {
        sprintf(temp, "\\%03o", MEM_U32(sp + 2 * 4));
        strcpy1(mem, str_addr, temp);
        return 1;
    }
    if (!strcmp(format, "%*ld=")) {
        sprintf(temp, "%*d=", MEM_U32(sp + 2 * 4), MEM_U32(sp + 3 * 4));
        strcpy1(mem, str_addr, temp);
        return 1;
    }

    uint32_t orig_str_addr = str_addr;
    uint32_t pos = 0;
    int ret = 0;
    char c;
    sp += 2 * 4;
    for (;;) {
        c = MEM_S8(format_addr + pos);
        ++pos;
        if (c == '%') {
            bool l = false;
            c = MEM_S8(format_addr + pos);
            ++pos;
            uint32_t zeros = 0;
            bool zero_prefix = false;
            continue_format:
            switch (c) {
                case '0':
                    do {
                        c = MEM_S8(format_addr + pos);
                        ++pos;
                        if (c >= '0' && c <= '9') {
                            zeros *= 10;
                            zeros += c - '0';
                        }
                    } while (c >= '0' && c <= '9');
                    goto continue_format;
                case '#':
                    c = MEM_S8(format_addr + pos);
                    ++pos;
                    zero_prefix = true;
                    goto continue_format;
                    break;
                case 'l':
                    assert(!l && "ll not implemented in fscanf");
                    c = MEM_S8(format_addr + pos);
                    ++pos;
                    l = true;
                    goto continue_format;
                    break;
                case 'd':
                    if (zeros != 0) {
                        char temp1[32];
                        sprintf(temp1, "%%0%dd", zeros);
                        sprintf(temp, temp1, MEM_S32(sp));
                    } else {
                        sprintf(temp, "%d", MEM_S32(sp));
                    }
                    sp += 4;
                    str_addr = strcpy1(mem, str_addr, temp);
                    ++ret;
                    break;
                case 'o':
                    if (zero_prefix) {
                        sprintf(temp, "%#o", MEM_S32(sp));
                    } else {
                        sprintf(temp, "%o", MEM_S32(sp));
                    }
                    sp += 4;
                    str_addr = strcpy1(mem, str_addr, temp);
                    ++ret;
                    break;
                case 'x':
                    if (zero_prefix) {
                        sprintf(temp, "%#x", MEM_S32(sp));
                    } else {
                        sprintf(temp, "%x", MEM_S32(sp));
                    }
                    sp += 4;
                    str_addr = strcpy1(mem, str_addr, temp);
                    ++ret;
                    break;
                case 'u':
                    sprintf(temp, "%u", MEM_S32(sp));
                    sp += 4;
                    str_addr = strcpy1(mem, str_addr, temp);
                    ++ret;
                    break;
                case 's':
                    str_addr = strcpy2(mem, str_addr, MEM_U32(sp));
                    sp += 4;
                    ++ret;
                    break;
                case 'c':
                    MEM_S8(str_addr) = (char)MEM_U32(sp);
                    ++str_addr;
                    sp += 4;
                    ++ret;
                    break;
                case '%':
                    MEM_S8(str_addr) = '%';
                    ++str_addr;
                    break;
                default:
                    fprintf(stderr, "%s\n", format);
                    assert(0 && "non-implemented sprintf format");
            }
        } else if (c == '\0') {
            break;
        } else {
            MEM_S8(str_addr) = c;
            ++str_addr;
        }
    }

    MEM_S8(str_addr) = '\0';
    STRING(orig_str) // for debug
    //printf("result: '%s' '%s'\n", format, orig_str);
    return ret;
}

int wrapper_fprintf(uint8_t *mem, uint32_t fp_addr, uint32_t format_addr, uint32_t sp) {
    struct FILE_irix *f = (struct FILE_irix *)&MEM_U32(fp_addr);
    STRING(format)
    sp += 8;
    /*if (!strcmp(format, "%s")) {
        uint32_t s_addr = MEM_U32(sp);
        STRING(s)
        if (fp_addr == STDERR_ADDR) {
            fprintf(stderr, "%s", s);
            fflush(stderr);
            return 1;
        }
    }
    if (!strcmp(format, "%s: %s: ")) {
        uint32_t s1_addr = MEM_U32(sp), s2_addr = MEM_U32(sp + 4);
        STRING(s1)
        STRING(s2)
        if (fp_addr == STDERR_ADDR) {
            fprintf(stderr, "%s: %s: ", s1, s2);
            fflush(stderr);
            return 1;
        }
    }*/
    int ret = 0;
    for (;;) {
        uint32_t pos = format_addr;
        char ch = MEM_S8(pos);
        while (ch != '%' && ch != '\0') {
            ++pos;
            ch = MEM_S8(pos);
        }
        if (format_addr != pos) {
            if (wrapper_fwrite(mem, format_addr, 1, pos - format_addr, fp_addr) != pos - format_addr) {
                break;
            }
        }
        if (ch == '\0') {
            break;
        }
        ++pos;
        ch = MEM_S8(pos);
        switch (ch) {
            case 'd':
            {
                char buf[32];
                sprintf(buf, "%d", MEM_U32(sp));
                strcpy1(mem, INTBUF_ADDR, buf);
                if (wrapper_fputs(mem, INTBUF_ADDR, fp_addr) == -1) {
                    return ret;
                }
                sp += 4;
                ++ret;
                break;
            }
            case 's':
            {
                if (wrapper_fputs(mem, MEM_U32(sp), fp_addr) == -1) {
                    return ret;
                }
                sp += 4;
                ++ret;
                break;
            }
            case 'c':
            {
                char buf[32];
                sprintf(buf, "%c", MEM_U32(sp));
                strcpy1(mem, INTBUF_ADDR, buf);
                if (wrapper_fputs(mem, INTBUF_ADDR, fp_addr) == -1) {
                    return ret;
                }
                sp += 4;
                ++ret;
                break;
            }
            default:
                fprintf(stderr, "missing format: '%s'\n", format);
                assert(0 && "non-implemented fprintf format");
        }
        format_addr = ++pos;
    }
    return ret;
}

int wrapper__doprnt(uint8_t *mem, uint32_t format_addr, uint32_t params_addr, uint32_t fp_addr) {
    assert(0 && "_doprnt not implemented");
    return 0;
}

uint32_t wrapper_strlen(uint8_t *mem, uint32_t str_addr) {
    uint32_t len = 0;
    while (MEM_S8(str_addr) != '\0') {
        ++str_addr;
        ++len;
    }
    return len;
}

int wrapper_open(uint8_t *mem, uint32_t pathname_addr, int flags, int mode) {
    STRING(pathname)
    int f = flags & O_ACCMODE;
    if (flags & 0x100) {
        f |= O_CREAT;
    }
    if (flags & 0x200) {
        f |= O_TRUNC;
    }
    if (flags & 0x400) {
        f |= O_EXCL;
    }
    if (flags & 0x800) {
        f |= O_NOCTTY;
    }
    if (flags & 0x08) {
        f |= O_APPEND;
    }
    int fd = open(pathname, f, mode);
    MEM_U32(ERRNO_ADDR) = errno;
    return fd;
}

int wrapper_creat(uint8_t *mem, uint32_t pathname_addr, int mode) {
    STRING(pathname)
    int ret = creat(pathname, mode);
    if (ret < 0) {
        MEM_U32(ERRNO_ADDR) = errno;
    }
    return ret;
}

int wrapper_access(uint8_t *mem, uint32_t pathname_addr, int mode) {
    STRING(pathname)
    int ret = access(pathname, mode);
    if (ret != 0) {
        MEM_U32(ERRNO_ADDR) = errno;
    }
    return ret;
}

int wrapper_rename(uint8_t *mem, uint32_t oldpath_addr, uint32_t newpath_addr) {
    STRING(oldpath)
    STRING(newpath)
    int ret = rename(oldpath, newpath);
    if (ret != 0) {
        MEM_U32(ERRNO_ADDR) = errno;
    }
    return ret;
}

int wrapper_utime(uint8_t *mem, uint32_t filename_addr, uint32_t times_addr) {
    STRING(filename)
    struct utimbuf buf = {0, 0};
    int ret = utime(filename, times_addr == 0 ? NULL : &buf);
    if (ret == 0) {
        if (times_addr != 0) {
            MEM_U32(times_addr + 0) = buf.actime;
            MEM_U32(times_addr + 4) = buf.modtime;
        }
    } else {
        MEM_U32(ERRNO_ADDR) = errno;
    }
    return ret;
}

int wrapper_flock(uint8_t *mem, int fd, int operation) {
    int ret = flock(fd, operation);
    if (ret != 0) {
        MEM_U32(ERRNO_ADDR) = errno;
    }
    return ret;
}

int wrapper_chmod(uint8_t *mem, uint32_t path_addr, uint32_t mode) {
    STRING(path)
    int ret = chmod(path, mode);
    if (ret < 0) {
        MEM_U32(ERRNO_ADDR) = errno;
    }
    return ret;
}

int wrapper_umask(int mode) {
    return umask(mode);
}

uint32_t wrapper_ecvt(uint8_t *mem, double number, int ndigits, uint32_t decpt_addr, uint32_t sign_addr) {
    assert(0);
}

uint32_t wrapper_fcvt(uint8_t *mem, double number, int ndigits, uint32_t decpt_addr, uint32_t sign_addr) {
    assert(0);
}

double wrapper_sqrt(double v) {
    return sqrt(v);
}

float wrapper_sqrtf(float v) {
    return sqrtf(v);
}

int wrapper_atoi(uint8_t *mem, uint32_t nptr_addr) {
    STRING(nptr)
    return atoi(nptr);
}

int wrapper_atol(uint8_t *mem, uint32_t nptr_addr) {
    return wrapper_atoi(mem, nptr_addr);
}

double wrapper_atof(uint8_t *mem, uint32_t nptr_addr) {
    STRING(nptr);
    return atof(nptr);
}

int wrapper_strtol(uint8_t *mem, uint32_t nptr_addr, uint32_t endptr_addr, int base) {
    STRING(nptr)
    char *endptr = NULL;
    int64_t res = strtoll(nptr, endptr_addr != 0 ? &endptr : NULL, base);
    if (res > INT_MAX) {
        MEM_U32(ERRNO_ADDR) = ERANGE;
        res = INT_MAX;
    }
    if (res < INT_MIN) {
        MEM_U32(ERRNO_ADDR) = ERANGE;
        res = INT_MIN;
    }
    if (endptr != NULL) {
        MEM_U32(endptr_addr) = nptr_addr + (uint32_t)(endptr - nptr);
    }
    return res;
}

uint32_t wrapper_strtoul(uint8_t *mem, uint32_t nptr_addr, uint32_t endptr_addr, int base) {
    STRING(nptr)
    char *endptr = NULL;
    uint64_t res = strtoull(nptr, endptr_addr != 0 ? &endptr : NULL, base);
    if (res > INT_MAX) {
        MEM_U32(ERRNO_ADDR) = ERANGE;
        res = INT_MAX;
    }
    if (endptr != NULL) {
        MEM_U32(endptr_addr) = nptr_addr + (uint32_t)(endptr - nptr);
    }
    return res;
}

double wrapper_strtod(uint8_t *mem, uint32_t nptr_addr, uint32_t endptr_addr) {
    STRING(nptr)
    char *endptr = NULL;
    errno = 0;
    double res = strtod(nptr, endptr_addr != 0 ? &endptr : NULL);
    if (errno != 0) {
        MEM_U32(ERRNO_ADDR) = errno;
    }
    if (endptr != NULL) {
        MEM_U32(endptr_addr) = nptr_addr + (uint32_t)(endptr - nptr);
    }
    return res;
}

uint32_t wrapper_strchr(uint8_t *mem, uint32_t str_addr, int c) {
    c = c & 0xff;
    for (;;) {
        unsigned char ch = MEM_U8(str_addr);
        if (ch == c) {
            return str_addr;
        }
        if (ch == '\0') {
            return 0;
        }
        ++str_addr;
    }
}

uint32_t wrapper_strrchr(uint8_t *mem, uint32_t str_addr, int c) {
    c = c & 0xff;
    uint32_t ret = 0;
    for (;;) {
        unsigned char ch = MEM_U8(str_addr);
        if (ch == c) {
            ret = str_addr;
        }
        if (ch == '\0') {
            return ret;
        }
        ++str_addr;
    }
}

uint32_t wrapper_strcspn(uint8_t *mem, uint32_t str_addr, uint32_t invalid_addr) {
    STRING(invalid)
    uint32_t n = strlen(invalid);
    uint32_t pos = 0;
    char c;
    while ((c = MEM_S8(str_addr)) != 0) {
        for (int i = 0; i < n; i++) {
            if (c == invalid[i]) {
                return pos;
            }
        }
        ++pos;
        ++str_addr;
    }
    return pos;
}

uint32_t wrapper_strpbrk(uint8_t *mem, uint32_t str_addr, uint32_t accept_addr) {
    STRING(accept)
    uint32_t n = strlen(accept);
    char c;
    while ((c = MEM_S8(str_addr)) != 0) {
        for (int i = 0; i < n; i++) {
            if (c == accept[i]) {
                return str_addr;
            }
        }
        ++str_addr;
    }
    return 0;
}

static void stat_common(uint8_t *mem, uint32_t buf_addr, struct stat *statbuf) {
    struct irix_stat {
        int st_dev;
        int pad1[3];
        int st_ino;
        int st_mode;
        int st_nlink;
        int st_uid;
        int st_gid;
        int st_rdev;
        int pad2[2];
        int st_size;
        int pad3;
        struct timespec_t_irix st_atim;
        struct timespec_t_irix st_mtim;
        struct timespec_t_irix st_ctim;
        int st_blksize;
        int st_blocks;
    } s;
    s.st_dev = statbuf->st_dev;
    s.st_ino = statbuf->st_ino;
    s.st_mode = statbuf->st_mode;
    s.st_nlink = statbuf->st_nlink;
    s.st_uid = statbuf->st_uid;
    s.st_gid = statbuf->st_gid;
    s.st_rdev = statbuf->st_rdev;
    s.st_size = statbuf->st_size;
#ifdef __APPLE__
    s.st_atim.tv_sec = statbuf->st_atimespec.tv_sec;
    s.st_atim.tv_nsec = statbuf->st_atimespec.tv_nsec;
    s.st_mtim.tv_sec = statbuf->st_mtimespec.tv_sec;
    s.st_mtim.tv_nsec = statbuf->st_mtimespec.tv_nsec;
    s.st_ctim.tv_sec = statbuf->st_ctimespec.tv_sec;
    s.st_ctim.tv_nsec = statbuf->st_ctimespec.tv_nsec;
#else
     s.st_atim.tv_sec = statbuf->st_atim.tv_sec;
     s.st_atim.tv_nsec = statbuf->st_atim.tv_nsec;
     s.st_mtim.tv_sec = statbuf->st_mtim.tv_sec;
     s.st_mtim.tv_nsec = statbuf->st_mtim.tv_nsec;
     s.st_ctim.tv_sec = statbuf->st_ctim.tv_sec;
     s.st_ctim.tv_nsec = statbuf->st_ctim.tv_nsec;
#endif
    memcpy(&MEM_U32(buf_addr), &s, sizeof(s));
}

int wrapper_fstat(uint8_t *mem, int fildes, uint32_t buf_addr) {
    struct stat statbuf;
    if (fstat(fildes, &statbuf) < 0) {
        MEM_U32(ERRNO_ADDR) = errno;
        return -1;
    } else {
        stat_common(mem, buf_addr, &statbuf);
        return 0;
    }
}

int wrapper_stat(uint8_t *mem, uint32_t pathname_addr, uint32_t buf_addr) {
    STRING(pathname)
    struct stat statbuf;
    if (stat(pathname, &statbuf) < 0) {
        MEM_U32(ERRNO_ADDR) = errno;
        return -1;
    } else {
        stat_common(mem, buf_addr, &statbuf);
        return 0;
    }
}

int wrapper_ftruncate(uint8_t *mem, int fd, int length) {
    int ret = ftruncate(fd, length);
    if (ret != 0) {
        MEM_U32(ERRNO_ADDR) = errno;
    }
    return ret;
}

void wrapper_bcopy(uint8_t *mem, uint32_t src_addr, uint32_t dst_addr, uint32_t len) {
    wrapper_memcpy(mem, dst_addr, src_addr, len);
}

uint32_t wrapper_memcpy(uint8_t *mem, uint32_t dst_addr, uint32_t src_addr, uint32_t len) {
    uint32_t saved = dst_addr;
    if (dst_addr % 4 == 0 && src_addr % 4 == 0 && len % 4 == 0) {
        memcpy(&MEM_U32(dst_addr), &MEM_U32(src_addr), len);
    } else {
        while (len--) {
            MEM_U8(dst_addr) = MEM_U8(src_addr);
            ++dst_addr;
            ++src_addr;
        }
    }
    return saved;
}

uint32_t wrapper_memccpy(uint8_t *mem, uint32_t dst_addr, uint32_t src_addr, int c, uint32_t len) {
    while (len--) {
        uint8_t ch = MEM_U8(src_addr);
        MEM_U8(dst_addr) = ch;
        ++dst_addr;
        ++src_addr;
        if (ch == c) {
            return dst_addr;
        }
    }
    return 0;
}

int wrapper_read(uint8_t *mem, int fd, uint32_t buf_addr, uint32_t nbytes) {
    uint8_t *buf = (uint8_t *)malloc(nbytes);
    ssize_t ret = read(fd, buf, nbytes);
    if (ret < 0) {
        MEM_U32(ERRNO_ADDR) = errno;
    } else {
        for (ssize_t i = 0; i < ret; i++) {
            MEM_U8(buf_addr + i) = buf[i];
        }
    }
    free(buf);
    return (int)ret;
}

int wrapper_write(uint8_t *mem, int fd, uint32_t buf_addr, uint32_t nbytes) {
    uint8_t *buf = (uint8_t *)malloc(nbytes);
    for (size_t i = 0; i < nbytes; i++) {
        buf[i] = MEM_U8(buf_addr + i);
    }
    ssize_t ret = write(fd, buf, nbytes);
    if (ret < 0) {
        MEM_U32(ERRNO_ADDR) = errno;
    }
    free(buf);
    return (int)ret;
}

static uint32_t init_file(uint8_t *mem, int fd, int i, const char *path, const char *mode) {
    int flags = O_RDONLY;
    if (strcmp(mode, "r") == 0 || strcmp(mode, "rb") == 0) {
        flags = O_RDONLY;
    } else if (strcmp(mode, "w") == 0 || strcmp(mode, "wb") == 0) {
        flags = O_WRONLY | O_CREAT | O_TRUNC;
    } else if (strcmp(mode, "a") == 0 || strcmp(mode, "ab") == 0) {
        flags = O_WRONLY | O_CREAT | O_APPEND;
    } else if (strcmp(mode, "r+") == 0 || strcmp(mode, "r+b") == 0) {
        flags = O_RDWR;
    } else if (strcmp(mode, "w+") == 0 || strcmp(mode, "w+b") == 0) {
        flags = O_RDWR | O_CREAT | O_TRUNC;
    } else if (strcmp(mode, "a+") == 0 || strcmp(mode, "a+b") == 0) {
        flags = O_RDWR | O_CREAT | O_APPEND;
    }
    if (fd == -1) {

#ifdef REDIRECT_USR_LIB
        char fixed_path[PATH_MAX + 1];
        if (!strcmp(path, "/usr/lib/err.english.cc") && bin_dir[0] != '\0') {
            int n = snprintf(fixed_path, sizeof(fixed_path), "%s/err.english.cc", bin_dir);
            if (n >= 0 && n < sizeof(fixed_path)) {
                path = fixed_path;
            }
        }
#endif
        fd = open(path, flags, 0666);
        if (fd < 0) {
            MEM_U32(ERRNO_ADDR) = errno;
            return 0;
        }
    }
    struct FILE_irix *f = (struct FILE_irix *)&MEM_U32(IOB_ADDR);
    uint32_t ret = 0;
    if (i == -1) {
        for (i = 3; i < NFILE; i++) {
            if (f[i]._flag == 0) {
                break;
            }
        }
    }
    assert(i < NFILE);
    g_file_max = i + 1;
    ret = IOB_ADDR + i * sizeof(struct FILE_irix);
    f[i]._cnt = 0;
    f[i]._ptr_addr = 0;
    f[i]._base_addr = 0;
    f[i]._file = fd;
    f[i]._flag = (flags & O_ACCMODE) == O_RDONLY ? IOREAD : 0;
    f[i]._flag |= (flags & O_ACCMODE) == O_WRONLY ? IOWRT : 0;
    f[i]._flag |= (flags & O_ACCMODE) == O_RDWR ? IORW : 0;
    bufendtab[i] = 0;
    return ret;
}

uint32_t wrapper_fopen(uint8_t *mem, uint32_t path_addr, uint32_t mode_addr) {
    STRING(path)
    STRING(mode)
    return init_file(mem, -1, -1, path, mode);
}

uint32_t wrapper_freopen(uint8_t *mem, uint32_t path_addr, uint32_t mode_addr, uint32_t fp_addr) {
    STRING(path)
    STRING(mode)
    struct FILE_irix *f = (struct FILE_irix *)&MEM_U32(fp_addr);
    wrapper_fclose(mem, fp_addr);
    return init_file(mem, -1, f - (struct FILE_irix *)&MEM_U32(IOB_ADDR), path, mode);
}

int wrapper_fclose(uint8_t *mem, uint32_t fp_addr) {
    struct FILE_irix *f = (struct FILE_irix *)&MEM_U32(fp_addr);
    wrapper_fflush(mem, fp_addr);
    if (f->_flag & IOMYBUF) {
        wrapper_free(mem, f->_base_addr);
    }
    f->_flag = 0;
    close(f->_file);
    return 0;
}

static int flush_all(uint8_t *mem) {
    struct FILE_irix *f = (struct FILE_irix *)&MEM_U32(IOB_ADDR);
    int ret = 0;
    for (int i = 0; i < g_file_max; i++) {
        if (f[i]._flag & IOWRT) {
            ret |= wrapper_fflush(mem, IOB_ADDR + i * sizeof(struct FILE_irix));
        }
    }
    return ret;
}

int wrapper_fflush(uint8_t *mem, uint32_t fp_addr) {
    if (fp_addr == 0) {
        // Flush all
        return flush_all(mem);
    }
    struct FILE_irix *f = (struct FILE_irix *)&MEM_U32(fp_addr);
    if (f->_flag & IOWRT) {
        int p = 0;
        int to_flush = f->_ptr_addr - f->_base_addr;
        int c = to_flush;
        while (c > 0) {
            int r = wrapper_write(mem, f->_file, f->_base_addr + p, c);
            if (r < 0) {
                f->_file |= IOERR;
                return -1;
            }
            p += r;
            c -= r;
        }
        f->_ptr_addr = f->_base_addr;
        f->_cnt += to_flush;
    }
    return 0;
}

int wrapper_ftell(uint8_t *mem, uint32_t fp_addr) {
    struct FILE_irix *f = (struct FILE_irix *)&MEM_U32(fp_addr);
    int adjust;
    if (f->_cnt < 0) {
        f->_cnt = 0;
    }
    if (f->_flag & IOREAD) {
        adjust = -f->_cnt;
    } else if (f->_flag & (IOWRT | IORW)) {
        adjust = 0;
        if ((f->_flag & IOWRT) && f->_base_addr != 0 && (f->_flag & IONBF) == 0) {
            adjust = f->_ptr_addr - f->_base_addr;
        }
    } else {
        return -1;
    }
    int res = wrapper_lseek(mem, f->_file, 0, 1);
    if (res >= 0) {
        res += adjust;
    }
    return res;
}

void wrapper_rewind(uint8_t *mem, uint32_t fp_addr) {
    (void)wrapper_fseek(mem, fp_addr, 0, SEEK_SET);
    struct FILE_irix *f = (struct FILE_irix *)&MEM_U32(fp_addr);
    f->_flag &= ~IOERR;
}

int wrapper_fseek(uint8_t *mem, uint32_t fp_addr, int offset, int origin) {
    struct FILE_irix *f = (struct FILE_irix *)&MEM_U32(fp_addr);
    int c, p;
    f->_flag &= ~IOEOF;
    if (f->_flag & IOREAD) {
        if (origin < SEEK_END && f->_base_addr && !(f->_flag & IONBF)) {
            c = f->_cnt;
            p = offset;
            if (origin == SEEK_SET) {
                p += c - lseek(f->_file, 0L, SEEK_CUR);
            } else {
                offset -= c;
            }
            if (!(f->_flag & IORW) && c > 0 && p <= c && p >= f->_base_addr - f->_ptr_addr) {
                f->_ptr_addr += p;
                f->_cnt -= p;
                return 0;
            }
        }
        if (f->_flag & IORW) {
            f->_ptr_addr = f->_base_addr;
            f->_flag &= ~IOREAD;
        }
        p = lseek(f->_file, offset, origin);
        f->_cnt = 0;
    } else if (f->_flag & (IOWRT | IORW)) {
        wrapper_fflush(mem, fp_addr);
        if (f->_flag & IORW) {
            f->_cnt = 0;
            f->_flag &= ~IOWRT;
            f->_ptr_addr = f->_base_addr;
        }
        p = lseek(f->_file, offset, origin);
    }
    if (p < 0) {
        MEM_U32(ERRNO_ADDR) = errno;
        return p;
    }
    return 0;
}

int wrapper_lseek(uint8_t *mem, int fd, int offset, int whence) {
    int ret = (int)lseek(fd, offset, whence);
    if (ret == -1) {
        MEM_U32(ERRNO_ADDR) = errno;
    }
    return ret;
}

int wrapper_dup(uint8_t *mem, int fd) {
    fd = dup(fd);
    if (fd < 0) {
        MEM_U32(ERRNO_ADDR) = errno;
    }
    return fd;
}

int wrapper_dup2(uint8_t *mem, int oldfd, int newfd) {
    int fd = dup2(oldfd, newfd);
    if (fd < 0) {
        MEM_U32(ERRNO_ADDR) = errno;
    }
    return fd;
}

int wrapper_pipe(uint8_t *mem, uint32_t pipefd_addr) {
    int pipefd[2];
    int ret = pipe(pipefd);
    if (ret == 0) {
        MEM_U32(pipefd_addr + 0) = pipefd[0];
        MEM_U32(pipefd_addr + 4) = pipefd[1];
    } else {
        MEM_U32(ERRNO_ADDR) = errno;
    }
    return ret;
}

void wrapper_perror(uint8_t *mem, uint32_t str_addr) {
    STRING(str)
    perror(str);
}

int wrapper_fdopen(uint8_t *mem, int fd, uint32_t mode_addr) {
    STRING(mode)
    return init_file(mem, fd, -1, NULL, mode);
}

uint32_t wrapper_memset(uint8_t *mem, uint32_t dest_addr, int byte, uint32_t n) {
    uint32_t saved = dest_addr;
    if (dest_addr % 4 == 0 && n % 4 == 0) {
        memset(&MEM_U32(dest_addr), byte, n);
    } else {
        while (n--) {
            MEM_U8(dest_addr) = (uint8_t)byte;
            ++dest_addr;
        }
    }
    return saved;
}

int wrapper_bcmp(uint8_t *mem, uint32_t s1_addr, uint32_t s2_addr, uint32_t n) {
    while (n--) {
        if (MEM_U8(s1_addr) != MEM_U8(s2_addr)) {
            return 1;
        }
        ++s1_addr;
        ++s2_addr;
    }
    return 0;
}

int wrapper_memcmp(uint8_t *mem, uint32_t s1_addr, uint32_t s2_addr, uint32_t n) {
    while (n--) {
        unsigned char c1 = MEM_U8(s1_addr);
        unsigned char c2 = MEM_U8(s2_addr);
        if (c1 < c2) {
            return -1;
        }
        if (c1 > c2) {
            return 1;
        }
        ++s1_addr;
        ++s2_addr;
    }
    return 0;
}

int wrapper_getpid(void) {
    return getpid();
}

int wrapper_getpgrp(uint8_t *mem) {
    int ret = getpgrp();
    if (ret == -1) {
        MEM_U32(ERRNO_ADDR) = errno;
    }
    return ret;
}

int wrapper_remove(uint8_t *mem, uint32_t path_addr) {
    STRING(path)
    int ret = remove(path);
    if (ret < 0) {
        MEM_U32(ERRNO_ADDR) = errno;
    }
    return ret;
}

int wrapper_unlink(uint8_t *mem, uint32_t path_addr) {
    if (path_addr == 0) {
        fprintf(stderr, "Warning: unlink with NULL as arguement\n");
        MEM_U32(ERRNO_ADDR) = EFAULT;
        return -1;
    }
    STRING(path)
    int ret = unlink(path);
    if (ret < 0) {
        MEM_U32(ERRNO_ADDR) = errno;
    }
    return ret;
}

int wrapper_close(uint8_t *mem, int fd) {
    int ret = close(fd);
    if (ret < 0) {
        MEM_U32(ERRNO_ADDR) = errno;
    }
    return ret;
}

int wrapper_strcmp(uint8_t *mem, uint32_t s1_addr, uint32_t s2_addr) {
    for (;;) {
        char c1 = MEM_S8(s1_addr);
        char c2 = MEM_S8(s2_addr);
        if (c1 != c2) {
            return c1 < c2 ? -1 : 1;
        }
        if (c1 == '\0') {
            return 0;
        }
        ++s1_addr;
        ++s2_addr;
    }
}

int wrapper_strncmp(uint8_t *mem, uint32_t s1_addr, uint32_t s2_addr, uint32_t n) {
    if (n == 0) {
        return 0;
    }
    for (;;) {
        char c1 = MEM_S8(s1_addr);
        char c2 = MEM_S8(s2_addr);
        if (c1 != c2) {
            return c1 < c2 ? -1 : 1;
        }
        if (--n == 0 || c1 == '\0') {
            return 0;
        }
        ++s1_addr;
        ++s2_addr;
    }
}

uint32_t wrapper_strcpy(uint8_t *mem, uint32_t dest_addr, uint32_t src_addr) {
    uint32_t saved = dest_addr;
    for (;;) {
        char c = MEM_S8(src_addr);
        ++src_addr;
        MEM_S8(dest_addr) = c;
        ++dest_addr;
        if (c == '\0') {
            return saved;
        }
    }
}

uint32_t wrapper_strncpy(uint8_t *mem, uint32_t dest_addr, uint32_t src_addr, uint32_t n) {
    uint32_t i;
    for (i = 0; i < n && MEM_S8(src_addr) != '\0'; i++) {
        MEM_S8(dest_addr + i) = MEM_S8(src_addr + i);
    }
    for (; i < n; i++) {
        MEM_S8(dest_addr + i) = '\0';
    }
    return dest_addr;
}

uint32_t wrapper_strcat(uint8_t *mem, uint32_t dest_addr, uint32_t src_addr) {
    uint32_t saved = dest_addr;
    while (MEM_S8(dest_addr) != '\0') {
        ++dest_addr;
    }
    while (MEM_S8(src_addr) != '\0') {
        MEM_S8(dest_addr) = MEM_S8(src_addr);
        ++src_addr;
        ++dest_addr;
    }
    MEM_S8(dest_addr) = '\0';
    return saved;
}

uint32_t wrapper_strncat(uint8_t *mem, uint32_t dest_addr, uint32_t src_addr, uint32_t n) {
    uint32_t saved = dest_addr;
    while (MEM_S8(dest_addr) != '\0') {
        ++dest_addr;
    }
    while (n-- && MEM_S8(src_addr) != '\0') {
        MEM_S8(dest_addr) = MEM_S8(src_addr);
        ++src_addr;
        ++dest_addr;
    }
    MEM_S8(dest_addr) = '\0';
    return saved;
}

uint32_t wrapper_strtok(uint8_t *mem, uint32_t str_addr, uint32_t delimiters_addr) {
    if (str_addr == 0) {
        str_addr = MEM_U32(STRTOK_DATA_ADDR);
    }
    if (str_addr == 0) {
        // nothing remaining
        return 0;
    }
    uint32_t p;
    for (p = str_addr; MEM_S8(p) != '\0'; p++) {
        uint32_t q;
        for (q = delimiters_addr; MEM_S8(q) != '\0' && MEM_S8(q) != MEM_S8(p); q++) {
        }
        if (MEM_S8(q) == '\0') {
            break;
        }
    }
    if (MEM_S8(p) == '\0') {
        return 0;
    }
    uint32_t ret = p;
    for (;;) {
        uint32_t q;
        for (q = delimiters_addr; MEM_S8(q) != '\0' && MEM_S8(q) != MEM_S8(p); q++) {
        }
        if (MEM_S8(q) != '\0') {
            MEM_S8(p) = '\0';
            MEM_U32(STRTOK_DATA_ADDR) = ++p;
            return ret;
        }
        char next = MEM_S8(p);
        ++p;
        if (next == '\0') {
            MEM_U32(STRTOK_DATA_ADDR) = 0;
            return ret;
        }
    }
}

uint32_t wrapper_strstr(uint8_t *mem, uint32_t str1_addr, uint32_t str2_addr) {
    for (;;) {
        if (MEM_S8(str1_addr) == '\0') {
            return 0;
        }
        uint32_t s1 = str1_addr;
        uint32_t s2 = str2_addr;
        for (;;) {
            char c2 = MEM_S8(s2);
            if (c2 == '\0') {
                return str1_addr;
            }
            if (MEM_S8(s1) == c2) {
                ++s1;
                ++s2;
            } else {
                break;
            }
        }
        ++str1_addr;
    }
}

uint32_t wrapper_strdup(uint8_t *mem, uint32_t str_addr) {
    uint32_t len = wrapper_strlen(mem, str_addr) + 1;
    uint32_t ret = wrapper_malloc(mem, len);
    if (ret == 0) {
        MEM_U32(ERRNO_ADDR) = ENOMEM;
        return 0;
    }
    return wrapper_memcpy(mem, ret, str_addr, len);
}

int wrapper_toupper(int c) {
    return toupper(c);
}

int wrapper_tolower(int c) {
    return tolower(c);
}

int wrapper_gethostname(uint8_t *mem, uint32_t name_addr, uint32_t len) {
    char buf[256] = {0};
    if (len > 256) {
        len = 256;
    }
    int ret = gethostname(buf, len);
    if (ret < 0) {
        MEM_U32(ERRNO_ADDR) = errno;
    } else {
        for (uint32_t i = 0; i < len; i++) {
            MEM_S8(name_addr + i) = buf[i];
        }
    }
    return ret;
}

int wrapper_isatty(uint8_t *mem, int fd) {
    int ret = isatty(fd);
    if (ret == 0) {
        MEM_U32(ERRNO_ADDR) = errno;
    }
    return ret;
}

uint32_t wrapper_strftime(uint8_t *mem, uint32_t ptr_addr, uint32_t maxsize, uint32_t format_addr, uint32_t timeptr_addr) {
    //assert(0 && "strftime not implemented");
    MEM_S8(ptr_addr) = 0;
    return 0;
}

int wrapper_times(uint8_t *mem, uint32_t buffer_addr) {
    struct tms_irix {
        int tms_utime;
        int tms_stime;
        int tms_cutime;
        int tms_cstime;
    } r;
    struct tms t;
    clock_t ret = times(&t);
    if (ret == (clock_t)-1) {
        MEM_U32(ERRNO_ADDR) = errno;
    } else {
        r.tms_utime = t.tms_utime;
        r.tms_stime = t.tms_stime;
        r.tms_cutime = t.tms_cutime;
        r.tms_cstime = t.tms_cstime;
    }
    return (int)ret;
}

int wrapper_clock(void) {
    return (int)clock();
}

uint32_t wrapper_ctime(uint8_t *mem, uint32_t timep_addr) {
    time_t t = MEM_S32(timep_addr);
    char *res = ctime(&t);
    size_t len = strlen(res) + 1;
    uint32_t ret_addr = wrapper_malloc(mem, len);
    uint32_t pos = ret_addr;
    while (len--) {
        MEM_S8(pos) = *res;
        ++pos;
        ++res;
    }
    return ret_addr;
    //assert(0 && "ctime not implemented");
    //return 0;
}

uint32_t wrapper_localtime(uint8_t *mem, uint32_t timep_addr) {
    time_t t = MEM_S32(timep_addr);
    struct irix_tm {
        int tm_sec;
        int tm_min;
        int tm_hour;
        int tm_mday;
        int tm_mon;
        int tm_year;
        int tm_wday;
        int tm_yday;
        int tm_isdst;
    };
    uint32_t ret = wrapper_malloc(mem, sizeof(struct irix_tm));
    struct irix_tm *r = (struct irix_tm *)&MEM_U32(ret);
    struct tm *l = localtime(&t);
    r->tm_sec = l->tm_sec;
    r->tm_min = l->tm_min;
    r->tm_hour = l->tm_hour;
    r->tm_mday = l->tm_mday;
    r->tm_mon = l->tm_mon;
    r->tm_year = l->tm_year;
    r->tm_wday = l->tm_wday;
    r->tm_yday = l->tm_yday;
    r->tm_isdst = l->tm_isdst;
    return ret;
}

int wrapper_setvbuf(uint8_t *mem, uint32_t fp_addr, uint32_t buf_addr, int mode, uint32_t size) {
    struct FILE_irix *f = (struct FILE_irix *)&MEM_U32(fp_addr);
    wrapper_fflush(mem, fp_addr);
    if ((f->_flag & IOMYBUF) && f->_base_addr != 0) {
        wrapper_free(mem, f->_base_addr);
    }
    size &= ~0xf;
    f->_flag &= ~IOMYBUF;
    f->_base_addr = buf_addr;
    f->_ptr_addr = buf_addr;
    f->_cnt = 0;
    bufendtab[(fp_addr - IOB_ADDR) / sizeof(struct FILE_irix)] = size;
    return 0;
}

int wrapper___semgetc(uint8_t *mem, uint32_t fp_addr) {
    assert(0);
}

int wrapper___semputc(uint8_t *mem, int c, uint32_t fp_addr) {
    assert(0);
}

int wrapper_fgetc(uint8_t *mem, uint32_t fp_addr) {
    struct FILE_irix *f = (struct FILE_irix *)&MEM_U32(fp_addr);
    if (--f->_cnt < 0) {
        return wrapper___filbuf(mem, fp_addr);
    } else {
        int ret = MEM_U8(f->_ptr_addr);
        ++f->_ptr_addr;
        return ret;
    }
}

int wrapper_fgets(uint8_t *mem, uint32_t str_addr, int count, uint32_t fp_addr) {
    bool modified = false;
    uint32_t saved = str_addr;
    for (count--; count > 0; count--) {
        int ch = wrapper_fgetc(mem, fp_addr);
        if (ch == -1) {
            MEM_S8(str_addr) = '\0';
            return modified ? saved : 0;
        }
        modified = true;
        MEM_S8(str_addr) = (char)ch;
        ++str_addr;
        if (ch == '\n') {
            break;
        }
    }
    MEM_S8(str_addr) = '\0';
    return saved;
}

static void file_assign_buffer(uint8_t *mem, struct FILE_irix *f) {
    f->_base_addr = wrapper_malloc(mem, STDIO_BUFSIZE);
    f->_ptr_addr = f->_base_addr;
    f->_flag |= IOMYBUF;
    f->_cnt = 0;
    bufendtab[f - (struct FILE_irix *)&MEM_U32(IOB_ADDR)] = STDIO_BUFSIZE;
}

int wrapper___filbuf(uint8_t *mem, uint32_t fp_addr) {
    struct FILE_irix *f = (struct FILE_irix *)&MEM_U32(fp_addr);
    if (!(f->_flag & IOREAD)) {
        if (f->_flag & IORW) {
            f->_flag |= IOREAD;
        } else {
            MEM_U32(ERRNO_ADDR) = 9; // EBADF
            return -1;
        }
    }
    if (f->_base_addr == 0) {
        file_assign_buffer(mem, f);
    }
    uint32_t size = bufendtab[(fp_addr - IOB_ADDR) / sizeof(struct FILE_irix)];
    int nread = wrapper_read(mem, f->_file, f->_base_addr, size);
    int ret = -1;
    if (nread > 0) {
        f->_ptr_addr = f->_base_addr;
        f->_cnt = nread;
        ret = MEM_U8(f->_ptr_addr);
        ++f->_ptr_addr;
        --f->_cnt;
    } else if (nread == 0) {
        f->_flag |= IOEOF;
    } else {
        f->_flag |= IOERR;
    }
    return ret;
}

int wrapper___flsbuf(uint8_t *mem, int ch, uint32_t fp_addr) {
    struct FILE_irix *f = (struct FILE_irix *)&MEM_U32(fp_addr);
    if (wrapper_fflush(mem, fp_addr) != 0) {
        return -1;
    }
    if (f->_base_addr == 0) {
        file_assign_buffer(mem, f);
        f->_cnt = bufendtab[f - (struct FILE_irix *)&MEM_U32(IOB_ADDR)];
    }
    MEM_U8(f->_ptr_addr) = ch;
    ++f->_ptr_addr;
    --f->_cnt;
    if (f->_flag & IONBF) {
        if (wrapper_fflush(mem, fp_addr) != 0) {
            return -1;
        }
        f->_cnt = 0;
    }
    return ch;
}

int wrapper_ungetc(uint8_t *mem, int ch, uint32_t fp_addr) {
    struct FILE_irix *f = (struct FILE_irix *)&MEM_U32(fp_addr);
    if (ch == -1 || f->_ptr_addr == f->_base_addr) {
        return -1;
    }
    --f->_ptr_addr;
    MEM_U8(f->_ptr_addr) = (uint8_t)ch;
    ++f->_cnt;
    f->_flag &= ~IOEOF;
    return ch;
}

uint32_t wrapper_gets(uint8_t *mem, uint32_t str_addr) {
    uint32_t p, str0 = str_addr;
    int n;

    for (;;) {
        if (STDIN->_cnt <= 0) {
            if (wrapper___filbuf(mem, STDIN_ADDR) == -1) {
                if (str0 == str_addr) {
                    return 0;
                }
                break;
            }
            --STDIN->_ptr_addr;
            ++STDIN->_cnt;
        }
        n = STDIN->_cnt;
        if ((p = wrapper_memccpy(mem, str_addr, STDIN->_ptr_addr, '\n', n)) != 0) {
            n = p - str_addr;
        }
        str_addr += n;
        STDIN->_cnt -= n;
        STDIN->_ptr_addr += n;
        // bufsync
        if (p != 0) {
            // found '\n' in buffer
            --str_addr;
            break;
        }
    }
    MEM_S8(str_addr) = '\0';
    return str0;
}

uint32_t wrapper_fread(uint8_t *mem, uint32_t data_addr, uint32_t size, uint32_t count, uint32_t fp_addr) {
    struct FILE_irix *f = (struct FILE_irix *)&MEM_U32(fp_addr);
    int nleft = count * size;
    int n;
    for (;;) {
        if (f->_cnt <= 0) {
            if (wrapper___filbuf(mem, fp_addr) == -1) {
                return count - (nleft + size - 1) / size;
            }
            --f->_ptr_addr;
            ++f->_cnt;
        }
        n = MIN(nleft, f->_cnt);
        data_addr = wrapper_memcpy(mem, data_addr, f->_ptr_addr, n) + n;
        f->_cnt -= n;
        f->_ptr_addr += n;
        if ((nleft -= n) <= 0) {
            return count;
        }
    }
}

uint32_t wrapper_fwrite(uint8_t *mem, uint32_t data_addr, uint32_t size, uint32_t count, uint32_t fp_addr) {
    struct FILE_irix *f = (struct FILE_irix *)&MEM_U32(fp_addr);
    if (size > 0 && count > 0 && f->_base_addr == 0) {
        file_assign_buffer(mem, f);
        f->_cnt = bufendtab[f - (struct FILE_irix *)&MEM_U32(IOB_ADDR)];
        f->_flag |= IOWRT;
    }
    uint32_t num_written = 0;
    while (count--) {
        uint32_t s = size;
        while (s > 0) {
            uint32_t to_write = f->_cnt;
            if (s < to_write) {
                to_write = s;
            }
            if (f->_cnt == 0) {
                if (wrapper_fflush(mem, fp_addr) != 0) {
                    return num_written;
                }
            }
            wrapper_memcpy(mem, f->_ptr_addr, data_addr, to_write);
            data_addr += to_write;
            f->_ptr_addr += to_write;
            f->_cnt -= to_write;
            s -= to_write;
        }
        num_written++;
    }
    if (f->_flag & IONBF) {
        wrapper_fflush(mem, fp_addr); // TODO check error return value
    }
    return num_written;
}

int wrapper_fputs(uint8_t *mem, uint32_t str_addr, uint32_t fp_addr) {
    uint32_t len = wrapper_strlen(mem, str_addr);
    uint32_t ret = wrapper_fwrite(mem, str_addr, 1, len, fp_addr);
    return ret == 0 && len != 0 ? -1 : 0;
}

int wrapper_puts(uint8_t *mem, uint32_t str_addr) {
    int ret = wrapper_fputs(mem, str_addr, STDOUT_ADDR);
    if (ret != 0) {
        return ret;
    }
    struct FILE_irix *f = STDOUT;
    if (--f->_cnt < 0) {
        if (wrapper___flsbuf(mem, '\n', STDOUT_ADDR) != '\n') {
            return -1;
        }
    } else {
        MEM_S8(f->_ptr_addr) = '\n';
        ++f->_ptr_addr;
    }
    return 0;
}

uint32_t wrapper_getcwd(uint8_t *mem, uint32_t buf_addr, uint32_t size) {
    char buf[size];
    if (getcwd(buf, size) == NULL) {
        MEM_U32(ERRNO_ADDR) = errno;
        return 0;
    } else {
        if (buf_addr == 0) {
            buf_addr = wrapper_malloc(mem, size);
        }
        strcpy1(mem, buf_addr, buf);
        return buf_addr;
    }
}

int wrapper_time(uint8_t *mem, uint32_t tloc_addr) {
    time_t ret = time(NULL);
    if (ret == (time_t)-1) {
        MEM_U32(ERRNO_ADDR) = errno;
    } else if (tloc_addr != 0) {
        MEM_S32(tloc_addr) = ret;
    }
    return ret;
}

void wrapper_bzero(uint8_t *mem, uint32_t str_addr, uint32_t n) {
    while (n--) {
        MEM_U8(str_addr) = 0;
        ++str_addr;
    }
}

int wrapper_fp_class_d(double d) {
    union {
        uint32_t w[2];
        double d;
    } bits;
    bits.d = d;
    uint32_t a2 =  bits.w[1];
    uint32_t a1 = a2 >> 20;
    uint32_t a0 = a1;
    a2 &= 0xfffff;
    uint32_t a3 = bits.w[0];
    a1 &= 0x7ff;
    a0 &= 0x800;
    if (a1 == 0x7ff) {
        if (a2 == 0 && a3 == 0) {
            return a0 == 0 ? 2 : 3;
        }
        a0 = a2 & 0x80000;
        return a0 == 0 ? 1 : 0;
    }
    if (a1 == 0) {
        if (a2 == 0 && a3 == 0) {
            return a0 == 0 ? 8 : 9;
        }
        return a0 == 0 ? 6 : 7;
    }
    return a0 == 0 ? 4 : 5;
}

double wrapper_ldexp(double d, int i) {
    return ldexp(d, i);
}

int64_t wrapper___ll_mul(int64_t a0, int64_t a1) {
    return a0 * a1;
}

int64_t wrapper___ll_div(int64_t a0, int64_t a1) {
    return a0 / a1;
}

int64_t wrapper___ll_rem(uint64_t a0, int64_t a1) {
    return a0 % a1;
}

int64_t wrapper___ll_lshift(int64_t a0, uint64_t shift) {
    return a0 << (shift & 0x3f);
}

int64_t wrapper___ll_rshift(int64_t a0, uint64_t shift) {
    return a0 >> (shift & 0x3f);
}

uint64_t wrapper___ull_div(uint64_t a0, uint64_t a1) {
    return a0 / a1;
}

uint64_t wrapper___ull_rem(uint64_t a0, uint64_t a1) {
    return a0 % a1;
}

uint64_t wrapper___ull_rshift(uint64_t a0, uint64_t shift) {
    return a0 >> (shift & 0x3f);
}

uint64_t wrapper___d_to_ull(double d) {
    return d;
}

int64_t wrapper___d_to_ll(double d) {
    return d;
}

uint64_t wrapper___f_to_ull(float f) {
    return f;
}

int64_t wrapper___f_to_ll(float f) {
    return f;
}

float wrapper___ull_to_f(uint64_t v) {
    return v;
}

float wrapper___ll_to_f(int64_t v) {
    return v;
}

double wrapper___ull_to_d(uint64_t v) {
    return v;
}

double wrapper___ll_to_d(int64_t v) {
    return v;
}

void wrapper_abort(uint8_t *mem) {
    abort();
}

void wrapper_exit(uint8_t *mem, int status) {
    exit(status);
}

void wrapper__exit(uint8_t *mem, int status) {
    assert(0 && "_exit not implemented"); // exit() is already overridden
}

void wrapper__cleanup(uint8_t *mem) {
}

uint32_t wrapper__rld_new_interface(uint8_t *mem, uint32_t operation, uint32_t sp) {
    assert(0 && "_rld_new_interface not implemented");
    return 0;
}

void wrapper__exithandle(uint8_t *mem) {
    assert(0 && "_exithandle not implemented");
}

int wrapper__prctl(uint8_t *mem, int operation, uint32_t sp) {
    assert(0 && "_prctl not implemented");
    return 0;
}

double wrapper__atod(uint8_t *mem, uint32_t buffer_addr, int ndigits, int dexp) {
    // ftp://atoum.hst.nerim.net/irix/src/irix-6.5.5-src/6.5.5/m/irix/lib/libc/src/math/atod.c
    assert(0 && "_atod not implemented");
    return 0.0;
}

int wrapper_pathconf(uint8_t *mem, uint32_t path_addr, int name) {
    STRING(path)
    if (name == 5) {
        errno = 0;
        int ret = pathconf(path, _PC_PATH_MAX);
        if (errno != 0) {
            MEM_U32(ERRNO_ADDR) = errno;
        }
        return ret;
    }
    assert(0 && "pathconf not implemented for the specific 'name'");
    return 0;
}

uint32_t wrapper_getenv(uint8_t *mem, uint32_t name_addr) {
    // Return null for everything, for now
    return 0;
}

uint32_t wrapper_gettxt(uint8_t *mem, uint32_t msgid_addr, uint32_t default_str_addr) {
    // Return default for now
    return default_str_addr;
}

uint32_t wrapper_setlocale(uint8_t *mem, int category, uint32_t locale_addr) {
    assert(locale_addr != 0);
    STRING(locale)
    assert(category == 6); // LC_ALL
    char *ret = setlocale(LC_ALL, locale);
    // Let's hope the caller doesn't use the return value
    return 0;
}

uint32_t wrapper_mmap(uint8_t *mem, uint32_t addr, uint32_t length, int prot, int flags, int fd, int offset) {
    assert(0 && "mmap not implemented");
    return 0;
}

int wrapper_munmap(uint8_t *mem, uint32_t addr, uint32_t length) {
    assert(0 && "munmap not implemented");
    return 0;
}

int wrapper_mprotect(uint8_t *mem, uint32_t addr, uint32_t length, int prot) {
    assert(0 && "mprotect not implemented");
    return 0;
}

int wrapper_sysconf(uint8_t *mem, int name) {
    assert(0 && "sysconf not implemented");
    return 0;
}

int wrapper_getpagesize(uint8_t *mem) {
    return 4096;
}

int wrapper_strerror(uint8_t *mem, int errnum) {
    errno = errnum;
    perror("strerror");
    assert(0 && "strerror not implemented");
    return 0;
}

int wrapper_ioctl(uint8_t *mem, int fd, uint32_t request, uint32_t sp) {
    assert(0 && "ioctl not implemented");
    return 0;
}

int wrapper_fcntl(uint8_t *mem, int fd, int cmd, uint32_t sp) {
    assert(0 && "fcntl not implemented");
    return 0;
}

static void signal_handler(int signum) {
    uint32_t level = signal_context.recursion_level++;
    uint8_t *mem = signal_context.handlers[signum].mem;
    uint32_t fp_dest = signal_context.handlers[signum].fp_dest;
    uint32_t sp = SIGNAL_HANDLER_STACK_START - 16 - level * 0x1000;
    signal_context.handlers[signum].trampoline(mem, sp, signum, 0, 0, 0, fp_dest);
    signal_context.recursion_level--;
}

uint32_t wrapper_signal(uint8_t *mem, int signum, uint64_t (*trampoline)(uint8_t *mem, uint32_t sp, uint32_t a0, uint32_t a1, uint32_t a2, uint32_t a3, uint32_t fp_dest), uint32_t handler_addr, uint32_t sp) {
    //assert(0 && "signal not implemented");
    return 0;
}

uint32_t wrapper_sigset(uint8_t *mem, int signum, uint64_t (*trampoline)(uint8_t *mem, uint32_t sp, uint32_t a0, uint32_t a1, uint32_t a2, uint32_t a3, uint32_t fp_dest), uint32_t disp_addr, uint32_t sp) {
    void (*handler)(int) = signal_handler;

    if ((int)disp_addr >= -1 && (int)disp_addr <= 1) {
        // SIG_DFL etc.
        handler = (void (*)(int))(intptr_t)(int)disp_addr;
    }

    switch (signum) {
        case 2:
            signum = SIGINT;
            break;
        case 13:
            signum = SIGPIPE;
            break;
        case 15:
            signum = SIGTERM;
            break;
        default:
            assert(0 && "sigset with this signum not implemented");
            break;
    }

    signal_context.handlers[signum].trampoline = trampoline;
    signal_context.handlers[signum].mem = mem;
    signal_context.handlers[signum].fp_dest = disp_addr;

    return (uint32_t)(uintptr_t)sigset(signum, handler); // for now only support SIG_DFL etc. as return value
}

int wrapper_get_fpc_csr(uint8_t *mem) {
    //assert(0 && "get_fpc_csr not implemented");
    return 0;
}

int wrapper_set_fpc_csr(uint8_t *mem, int csr) {
    //assert(0 && "set_fpc_csr not implemented");
    return 0;
}

int wrapper_setjmp(uint8_t *mem, uint32_t addr) {
    return 0;
}

void wrapper_longjmp(uint8_t *mem, uint32_t addr, int status) {
    assert(0 && "longjmp not implemented");
}

uint32_t wrapper_tempnam(uint8_t *mem, uint32_t dir_addr, uint32_t pfx_addr) {
    STRING(dir)
    STRING(pfx)
    char *ret = tempnam(dir, pfx);
    char *ret_saved = ret;
    if (ret == NULL) {
        MEM_U32(ERRNO_ADDR) = errno;
        return 0;
    }
    size_t len = strlen(ret) + 1;
    uint32_t ret_addr = wrapper_malloc(mem, len);
    uint32_t pos = ret_addr;
    while (len--) {
        MEM_S8(pos) = *ret;
        ++pos;
        ++ret;
    }
    free(ret_saved);
    return ret_addr;
}

uint32_t wrapper_tmpnam(uint8_t *mem, uint32_t str_addr) {
    char buf[1024];
    assert(str_addr != 0 && "s NULL not implemented for tmpnam");
    char *ret = tmpnam(buf);
    if (ret == NULL) {
        return 0;
    } else {
        strcpy1(mem, str_addr, ret);
        return str_addr;
    }
}

uint32_t wrapper_mktemp(uint8_t *mem, uint32_t template_addr) {
    STRING(template)
    mktemp(template);
    strcpy1(mem, template_addr, template);
    return template_addr;
}

int wrapper_mkstemp(uint8_t *mem, uint32_t name_addr) {
    STRING(name)
    int fd = mkstemp(name);
    if (fd < 0) {
        MEM_U32(ERRNO_ADDR) = errno;
    } else {
        strcpy1(mem, name_addr, name);
    }
    return fd;
}

uint32_t wrapper_tmpfile(uint8_t *mem) {
    // create and fopen a temporary file that is removed when the program exits
    char name[] = "/tmp/copt_temp_XXXXXX";
    int fd = mkstemp(name);
    if (fd < 0) {
        MEM_U32(ERRNO_ADDR) = errno;
        return 0;
    }

    // the file will be removed from disk when it's closed later
    unlink(name);

    // fdopen:
    uint32_t ret = init_file(mem, fd, -1, NULL, "w+");
    if (ret == 0) {
        close(fd);
    }
    return ret;
}

int wrapper_wait(uint8_t *mem, uint32_t wstatus_addr) {
    int wstatus;
    pid_t ret = wait(&wstatus);
    MEM_S32(wstatus_addr) = wstatus;
    return ret;
}

int wrapper_kill(uint8_t *mem, int pid, int sig) {
    int ret = kill(pid, sig);
    if (ret != 0) {
        MEM_U32(ERRNO_ADDR) = errno;
    }
    return ret;
}

int wrapper_execlp(uint8_t *mem, uint32_t file_addr, uint32_t sp) {
    uint32_t argv_addr = sp + 4;
    return wrapper_execvp(mem, file_addr, argv_addr);
}

int wrapper_execv(uint8_t *mem, uint32_t pathname_addr, uint32_t argv_addr) {
    STRING(pathname)
    uint32_t argc = 0;
    while (MEM_U32(argv_addr + argc * 4) != 0) {
        ++argc;
    }
    char *argv[argc + 1];
    for (uint32_t i = 0; i < argc; i++) {
        uint32_t str_addr = MEM_U32(argv_addr + i * 4);
        uint32_t len = wrapper_strlen(mem, str_addr) + 1;
        argv[i] = (char *)malloc(len);
        char *pos = argv[i];
        while (len--) {
            *pos++ = MEM_S8(str_addr);
            ++str_addr;
        }
    }
    argv[argc] = NULL;
    execv(pathname, argv);
    MEM_U32(ERRNO_ADDR) = errno;
    for (uint32_t i = 0; i < argc; i++) {
        free(argv[i]);
    }
    return -1;
}

int wrapper_execvp(uint8_t *mem, uint32_t file_addr, uint32_t argv_addr) {
    STRING(file)
    uint32_t argc = 0;
    while (MEM_U32(argv_addr + argc * 4) != 0) {
        ++argc;
    }
    char *argv[argc + 1];
    for (uint32_t i = 0; i < argc; i++) {
        uint32_t str_addr = MEM_U32(argv_addr + i * 4);
        uint32_t len = wrapper_strlen(mem, str_addr) + 1;
        argv[i] = (char *)malloc(len);
        char *pos = argv[i];
        while (len--) {
            *pos++ = MEM_S8(str_addr);
            ++str_addr;
        }
    }
    argv[argc] = NULL;

#ifdef REDIRECT_USR_LIB
    if (!strncmp(file, "/usr/lib/", 9) && bin_dir[0] != '\0') {
        char fixed_path[PATH_MAX + 1];
#ifdef __CYGWIN__
        int n = snprintf(fixed_path, sizeof(fixed_path), "%s/%s.exe", bin_dir, file + 9);
#else
        int n = snprintf(fixed_path, sizeof(fixed_path), "%s/%s", bin_dir, file + 9);
#endif
        if (n > 0 && n < sizeof(fixed_path)) {
            execvp(fixed_path, argv);
        } else {
            execvp(file, argv);
        }
    } else {
        execvp(file, argv);
    }
#else
    execvp(file, argv);
#endif

    MEM_U32(ERRNO_ADDR) = errno;
    for (uint32_t i = 0; i < argc; i++) {
        free(argv[i]);
    }
    return -1;
}

int wrapper_fork(uint8_t *mem) {
    int ret = fork();
    if (ret == -1) {
        MEM_U32(ERRNO_ADDR) = errno;
    }
    return ret;
}

int wrapper_system(uint8_t *mem, uint32_t command_addr) {
    STRING(command)
    return system(command); // no errno
}

static int name_compare(uint8_t *mem, uint32_t a_addr, uint32_t b_addr) {
    //printf("pc=0x00438180\n");
    return wrapper_strcmp(mem, MEM_U32(a_addr), MEM_U32(b_addr));
}

static uint32_t tsearch_tfind(uint8_t *mem, uint32_t key_addr, uint32_t rootp_addr, uint32_t compar_addr, bool insert) {
    //assert(compar_addr == 0x438180); // name_compare in as1

    if (rootp_addr == 0) {
        return 0;
    }
    while (MEM_U32(rootp_addr) != 0) {
        uint32_t node_addr = MEM_U32(rootp_addr);
        int r = name_compare(mem, key_addr, MEM_U32(node_addr));
        if (r == 0) {
            return node_addr;
        }
        rootp_addr = r < 0 ? node_addr + 4 : node_addr + 8;
    }
    if (insert) {
        uint32_t node_addr = wrapper_malloc(mem, 12);
        if (node_addr != 0) {
            MEM_U32(rootp_addr) = node_addr;
            MEM_U32(node_addr) = key_addr;
            MEM_U32(node_addr + 4) = 0;
            MEM_U32(node_addr + 8) = 0;
            return node_addr;
        }
    }
    return 0;
}

uint32_t wrapper_tsearch(uint8_t *mem, uint32_t key_addr, uint32_t rootp_addr, uint32_t compar_addr) {
    return tsearch_tfind(mem, key_addr, rootp_addr, compar_addr, true);
}

uint32_t wrapper_tfind(uint8_t *mem, uint32_t key_addr, uint32_t rootp_addr, uint32_t compar_addr) {
    return tsearch_tfind(mem, key_addr, rootp_addr, compar_addr, false);
}

uint32_t wrapper_qsort(uint8_t *mem, uint32_t base_addr, uint32_t num, uint32_t size, uint64_t (*trampoline)(uint8_t *mem, uint32_t sp, uint32_t a0, uint32_t a1, uint32_t a2, uint32_t a3, uint32_t fp_dest), uint32_t compare_addr, uint32_t sp) {
    assert(0 && "qsort not implemented");
    return 0;
}

uint32_t wrapper_regcmp(uint8_t *mem, uint32_t string1_addr, uint32_t sp) {
    STRING(string1);
    fprintf(stderr, "regex string: %s\n", string1);
    assert(0 && "regcmp not implemented");
    return 0;
}

uint32_t wrapper_regex(uint8_t *mem, uint32_t re_addr, uint32_t subject_addr, uint32_t sp) {
    STRING(subject);
    assert(0 && "regex not implemented");
    return 0;
}

void wrapper___assert(uint8_t *mem, uint32_t assertion_addr, uint32_t file_addr, int line) {
    STRING(assertion)
    STRING(file)
    __assert(assertion, file, line);
}
