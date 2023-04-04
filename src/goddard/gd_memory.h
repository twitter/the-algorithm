#ifndef GD_MEMORY_H
#define GD_MEMORY_H

#include <PR/ultratypes.h>

/// A structure that holds information about memory allocation on goddard's heap.
struct GMemBlock {
    /* 0x00 */ u8 *ptr;
    /* 0x04 */ u32 size;
    /* 0x08 */ u8 blockType;
    /* 0x09 */ u8 permFlag; ///< Permanent (upper four bits) or Temporary (lower four bits)
    /* 0x0C */ struct GMemBlock *next;
    /* 0x10 */ struct GMemBlock *prev;
};

/// Block list types for `GMemBlock.blockType`. Note that Empty Blocks don't have
/// a specific value.
enum GMemBlockTypes {
    G_MEM_BLOCK_FREE = 1,
    G_MEM_BLOCK_USED = 2
};
/* Block Permanence Defines */
/* This may be collections of certain allocation types
 * eg. 0x10 = Object; 0x20 = Color Buffer; 0x40 = Z Buf; 0x01 = basic; etc. */
#define PERM_G_MEM_BLOCK 0xF0
#define TEMP_G_MEM_BLOCK 0x0F

// functions
extern u32 gd_free_mem(void *ptr);
extern void *gd_request_mem(u32 size, u8 permanence);
extern struct GMemBlock *gd_add_mem_to_heap(u32 size, void *addr, u8 permanence);
extern void init_mem_block_lists(void);
extern void mem_stats(void);

#endif // GD_MEMORY_H
