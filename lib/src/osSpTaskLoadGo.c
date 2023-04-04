#include "libultra_internal.h"
#include "hardware.h"
#include <macros.h>

#define _osVirtualToPhysical(ptr)                                                                      \
    if (ptr != NULL) {                                                                                 \
        ptr = (void *) osVirtualToPhysical(ptr);                                                       \
    }

OSTask D_803638B0;
OSTask *_VirtualToPhysicalTask(OSTask *task) {
    OSTask *physicalTask;
    physicalTask = &D_803638B0;
    bcopy(task, physicalTask, sizeof(OSTask));
    _osVirtualToPhysical(physicalTask->t.ucode);
    _osVirtualToPhysical(physicalTask->t.ucode_data);
    _osVirtualToPhysical(physicalTask->t.dram_stack);
    _osVirtualToPhysical(physicalTask->t.output_buff);
    _osVirtualToPhysical(physicalTask->t.output_buff_size);
    _osVirtualToPhysical(physicalTask->t.data_ptr);
    _osVirtualToPhysical(physicalTask->t.yield_data_ptr);
    return physicalTask;
}

void osSpTaskLoad(OSTask *task) {
    OSTask *physicalTask;
    physicalTask = _VirtualToPhysicalTask(task);
    if (physicalTask->t.flags & M_TASK_FLAG0) {
        physicalTask->t.ucode_data = physicalTask->t.yield_data_ptr;
        physicalTask->t.ucode_data_size = physicalTask->t.yield_data_size;
        task->t.flags &= ~M_TASK_FLAG0;
#ifdef VERSION_SH
        if (physicalTask->t.flags & M_TASK_FLAG2) {
            physicalTask->t.ucode = (u64*)HW_REG((uintptr_t)task->t.yield_data_ptr + 0xBFC, u64*);
        }
#endif
    }
    osWritebackDCache(physicalTask, sizeof(OSTask));
    __osSpSetStatus(SPSTATUS_CLEAR_SIGNAL0 | SPSTATUS_CLEAR_SIGNAL1 | SPSTATUS_CLEAR_SIGNAL2
                    | SPSTATUS_SET_INTR_ON_BREAK);
    while (__osSpSetPc((void *) SP_IMEM_START) == -1) {
        ;
    }
    while (__osSpRawStartDma(1, (void *) (SP_IMEM_START - sizeof(*physicalTask)), physicalTask,
                             sizeof(OSTask))
           == -1) {
        ;
    }
    while (__osSpDeviceBusy()) {
        ;
    }
    while (__osSpRawStartDma(1, (void *) SP_IMEM_START, physicalTask->t.ucode_boot,
                             physicalTask->t.ucode_boot_size)
           == -1) {
        ;
    }
}

void osSpTaskStartGo(UNUSED OSTask *task) {
    while (__osSpDeviceBusy()) {
        ;
    }
    __osSpSetStatus(SPSTATUS_SET_INTR_ON_BREAK | SPSTATUS_CLEAR_SSTEP | SPSTATUS_CLEAR_BROKE
                    | SPSTATUS_CLEAR_HALT);
}
