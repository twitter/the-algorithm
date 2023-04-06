#ifndef _MEMORY_H_
#define _MEMORY_H_


#define MEMORY_POOL_LEFT  0
#define MEMORY_POOL_RIGHT 1


struct AllocOnlyPool
{
    s32 totalSpace;
    s32 usedSpace;
    u8 *startPtr;
    u8 *freePtr;
};

struct MemoryPool;

#ifndef INCLUDED_FROM_MEMORY_C
// Declaring this variable extern puts it in the wrong place in the bss order
// when this file is included from memory.c (first instead of last). Hence,
// ifdef hack. It was very likely subject to bss reordering originally.
extern struct MemoryPool *gEffectsMemoryPool;
#endif

uintptr_t set_segment_base_addr(s32 segment, void *addr);
void *get_segment_base_addr(s32 segment);
void *segmented_to_virtual(const void *addr);
void *virtual_to_segmented(u32 segment, const void *addr);
void move_segment_table_to_dmem(void);

void main_pool_init(void *start, void *end);
void *main_pool_alloc(u32 size, u32 side);
u32 main_pool_free(void *addr);
void *main_pool_realloc(void *addr, u32 size);
u32 main_pool_available(void);
u32 main_pool_push_state(void);
u32 main_pool_pop_state(void);

void *load_segment(s32 segment, u8 *srcStart, u8 *srcEnd, u32 side);
void *load_to_fixed_pool_addr(u8 *destAddr, u8 *srcStart, u8 *srcEnd);
void *load_segment_decompress(s32 segment, u8 *srcStart, u8 *srcEnd);
void *load_segment_decompress_heap(u32 segment, u8 *srcStart, u8 *srcEnd);
void load_engine_code_segment(void);

struct AllocOnlyPool *alloc_only_pool_init(u32 size, u32 side);
void *alloc_only_pool_alloc(struct AllocOnlyPool *pool, s32 size);
struct AllocOnlyPool *alloc_only_pool_resize(struct AllocOnlyPool *pool, u32 size);

struct MemoryPool *mem_pool_init(u32 size, u32 side);
void *mem_pool_alloc(struct MemoryPool *pool, u32 size);
void mem_pool_free(struct MemoryPool *pool, void *addr);

void *alloc_display_list(u32 size);
void func_80278A78(struct MarioAnimation *a, void *b, struct Animation *target);
s32 load_patchable_table(struct MarioAnimation *a, u32 b);

#endif
