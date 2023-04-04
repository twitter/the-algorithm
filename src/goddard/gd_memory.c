#include <PR/ultratypes.h>

#include "debug_utils.h"
#include "gd_memory.h"
#include "renderer.h"

/**
 * @file gd_memory.c
 *
 * This file contains the functions need to manage allocation in
 * goddard's heap. However, the actual, useable allocation functions
 * are `gd_malloc()`, `gd_malloc_perm()`, and `gd_malloc_temp()`, as
 * well as `gd_free()`. This file is for managing the underlying memory
 * block lists.
 */

/* bss */
static struct GMemBlock *sFreeBlockListHead;
static struct GMemBlock *sUsedBlockListHead;
static struct GMemBlock *sEmptyBlockListHead;

/* Forward Declarations */
void empty_mem_block(struct GMemBlock *);
struct GMemBlock *into_free_memblock(struct GMemBlock *);
struct GMemBlock *make_mem_block(u32, u8);
u32 print_list_stats(struct GMemBlock *, s32, s32);

/**
 * Empty a `GMemBlock` into a default state. This empty block
 * doesn't point to any data, nor does it have any size. The
 * block is removed from whatever list is was in, and is added
 * to the empty block list.
 */
void empty_mem_block(struct GMemBlock *block) {
    if (block->next != NULL) {
        block->next->prev = block->prev;
    }

    if (block->prev != NULL) {
        block->prev->next = block->next;
    }

    switch (block->blockType) {
        case G_MEM_BLOCK_FREE:
            if (block->prev == NULL) {
                sFreeBlockListHead = block->next;
            }
            break;
        case G_MEM_BLOCK_USED:
            if (block->prev == NULL) {
                sUsedBlockListHead = block->next;
            }
            break;
    }

    block->next = sEmptyBlockListHead;
    if (block->next != NULL) {
        sEmptyBlockListHead->prev = block;
    }

    sEmptyBlockListHead = block;
    block->prev = NULL;
    block->ptr = NULL;
    block->size = 0;
}

/**
 * Transform a `GMemBlock` into a free block that points to memory available
 * for allocation.
 *
 * @returns pointer to the free `GMemBlock` */
struct GMemBlock *into_free_memblock(struct GMemBlock *block) {
    struct GMemBlock *freeBlock;
    void *ptr;
    u8 permanence;
    u32 space;

    ptr = block->ptr;
    space = block->size;
    permanence = block->permFlag;

    empty_mem_block(block);
    freeBlock = make_mem_block(G_MEM_BLOCK_FREE, permanence);
    freeBlock->ptr = ptr;
    freeBlock->size = space;
    freeBlock->permFlag = permanence;

    return freeBlock;
}

/**
 * Allocate a new `GMemBlock` structure of the given type and permanence.
 * It does not assign any heap space to the new block.
 *
 * @param blockType either `G_MEM_BLOCK_FREE` or `G_MEM_BLOCK_USED`
 * @param permFlag  some sort of permanence value, where setting one the upper
 *                  four bits imply a permanent block, while setting one the lower
 *                  four bits imply a temporary block
 * @returns a pointer to the new `GMemBlock`
 */
struct GMemBlock *make_mem_block(u32 blockType, u8 permFlag) {
    struct GMemBlock *newMemBlock;

    if (sEmptyBlockListHead == NULL) {
        sEmptyBlockListHead = (struct GMemBlock *) gd_allocblock(sizeof(struct GMemBlock));

        if (sEmptyBlockListHead == NULL) {
            fatal_printf("MakeMemBlock() unable to allocate");
        }

        sEmptyBlockListHead->next = NULL;
        sEmptyBlockListHead->prev = NULL;
    }

    newMemBlock = sEmptyBlockListHead;
    if ((sEmptyBlockListHead = newMemBlock->next) != NULL) {
        newMemBlock->next->prev = NULL;
    }

    switch (blockType) {
        case G_MEM_BLOCK_FREE:
            newMemBlock->next = sFreeBlockListHead;
            if (newMemBlock->next != NULL) {
                sFreeBlockListHead->prev = newMemBlock;
            }
            sFreeBlockListHead = newMemBlock;
            break;
        case G_MEM_BLOCK_USED:
            newMemBlock->next = sUsedBlockListHead;
            if (newMemBlock->next != NULL) {
                sUsedBlockListHead->prev = newMemBlock;
            }
            sUsedBlockListHead = newMemBlock;
            break;
        default:
            fatal_printf("unkown memblock type");
    }
    newMemBlock->prev = NULL;
    newMemBlock->blockType = (u8) blockType;
    newMemBlock->permFlag = permFlag;

    return newMemBlock;
}

/**
 * Free memory allocated on the goddard heap.
 *
 * @param ptr pointer to heap allocated memory
 * @returns size of memory freed
 * @retval  0    `ptr` did not point to a valid memory block
 */
u32 gd_free_mem(void *ptr) {
    register struct GMemBlock *curBlock;
    u32 bytesFreed;
    register u8 *targetBlock = ptr;

    for (curBlock = sUsedBlockListHead; curBlock != NULL; curBlock = curBlock->next) {
        if (targetBlock == curBlock->ptr) {
            bytesFreed = curBlock->size;
            into_free_memblock(curBlock);
            return bytesFreed;
        }
    }

    fatal_printf("Free() Not a valid memory block");
    return 0;
}

/**
 * Request a pointer to goddard heap memory of at least `size` and
 * of the same `permanence`.
 *
 * @return pointer to heap
 * @retval NULL could not fulfill the request
 */
void *gd_request_mem(u32 size, u8 permanence) {
    struct GMemBlock *foundBlock = NULL;
    struct GMemBlock *curBlock;
    struct GMemBlock *newBlock;

    newBlock = make_mem_block(G_MEM_BLOCK_USED, permanence);
    curBlock = sFreeBlockListHead;

    while (curBlock != NULL) {
        if (curBlock->permFlag & permanence) {
            if (curBlock->size == size) {
                foundBlock = curBlock;
                break;
            } else {
                if (curBlock->size > size) {
                    if (foundBlock != NULL) { /* find closest sized block */
                        if (curBlock->size < foundBlock->size) {
                            foundBlock = curBlock;
                        }
                    } else {
                        foundBlock = curBlock;
                    }
                }
            }
        }
        curBlock = curBlock->next;
    }

    if (foundBlock == NULL) {
        return NULL;
    }

    if (foundBlock->size > size) { /* split free block */
        newBlock->ptr = foundBlock->ptr;
        newBlock->size = size;

        foundBlock->size -= size;
        foundBlock->ptr += size;
    } else if (foundBlock->size == size) { /* recycle whole free block */
        newBlock->ptr = foundBlock->ptr;
        newBlock->size = size;
        empty_mem_block(foundBlock);
    }

    return newBlock->ptr;
}

/**
 * Add memory of `size` at `addr` to the goddard heap for later allocation.
 *
 * @returns `GMemBlock` that contains info about the new heap memory
 */
struct GMemBlock *gd_add_mem_to_heap(u32 size, void *addr, u8 permanence) {
    struct GMemBlock *newBlock;
    /* eight-byte align the new block's data stats */
    size = (size - 8) & ~7;
    addr = (void *)(((uintptr_t) addr + 8) & ~7);

    newBlock = make_mem_block(G_MEM_BLOCK_FREE, permanence);
    newBlock->ptr = addr;
    newBlock->size = size;

    return newBlock;
}

/**
 * NULL the various `GMemBlock` list heads
 */
void init_mem_block_lists(void) {
    sFreeBlockListHead = NULL;
    sUsedBlockListHead = NULL;
    sEmptyBlockListHead = NULL;
}

/**
 * Print information (size, entries) about the `GMemBlock` list. It can print
 * information for individual blocks as well as summary info for the entry list.
 *
 * @param block          `GMemBlock` to start reading the list
 * @param printBlockInfo If `TRUE`, print information about every block
 *                       in the list
 * @param permanence     Limit info printed to blocks with this permanence
 * @returns number of entries
 */
u32 print_list_stats(struct GMemBlock *block, s32 printBlockInfo, s32 permanence) {
    u32 entries = 0;
    u32 totalSize = 0;

    while (block != NULL) {
        if (block->permFlag & permanence) {
            entries++;
            if (printBlockInfo) {
                gd_printf("     %6.2fk (%d bytes)\n",
                          (f32) block->size / 1024.0, //? 1024.0f
                          block->size);
            }
            totalSize += block->size;
        }
        block = block->next;
    }

    gd_printf("Total %6.2fk (%d bytes) in %d entries\n",
              (f32) totalSize / 1024.0, //? 1024.0f
              totalSize, entries);

    return entries;
}

/**
 * Print summary information about all used, free, and empty
 * `GMemBlock`s.
 */
void mem_stats(void) {
    struct GMemBlock *list;

    gd_printf("Perm Used blocks:\n");
    list = sUsedBlockListHead;
    print_list_stats(list, FALSE, PERM_G_MEM_BLOCK);
    gd_printf("\n");

    gd_printf("Perm Free blocks:\n");
    list = sFreeBlockListHead;
    print_list_stats(list, FALSE, PERM_G_MEM_BLOCK);
    gd_printf("\n");

    gd_printf("Temp Used blocks:\n");
    list = sUsedBlockListHead;
    print_list_stats(list, FALSE, TEMP_G_MEM_BLOCK);
    gd_printf("\n");

    gd_printf("Temp Free blocks:\n");
    list = sFreeBlockListHead;
    print_list_stats(list, FALSE, TEMP_G_MEM_BLOCK);
    gd_printf("\n");

    gd_printf("Empty blocks:\n");
    list = sEmptyBlockListHead;
    print_list_stats(list, FALSE, PERM_G_MEM_BLOCK | TEMP_G_MEM_BLOCK);
}
