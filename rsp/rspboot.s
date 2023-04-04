.rsp

.include "rsp/rsp_defs.inc"

// This file assumes CODE_FILE is set on the command line
.create CODE_FILE, 0x04001000

.if defined(VERSION_JP) || defined(VERSION_US)
    ori   $1, $1, 0x0001
.endif
    j     boot_04001068
     addi  $1, $zero, OSTask_addr
boot_load_ucode:
    lw    $2, OSTask_ucode($1)
    addi  $3, $zero, 0x0f7f    // hard-coded length = 0xF80
    addi  $7, $zero, 0x1080    // hard-coded address = 0x1080
    mtc0  $7, SP_MEM_ADDR
    mtc0  $2, SP_DRAM_ADDR
    mtc0  $3, SP_RD_LEN
boot_ucode_dma_busy:
    mfc0  $4, SP_DMA_BUSY
    bne   $4, $zero, boot_ucode_dma_busy
     nop
    jal   check_yielded
     nop
    jr    $7                   // jump to the loaded ucode
     mtc0  $zero, SP_SEMAPHORE // clear semaphore

check_yielded:
    mfc0  $8, SP_STATUS
    andi  $8, $8, 0x0080       // yield signal is set
    bne   $8, $zero, boot_04001054
     nop
    jr    ra
boot_04001054:
     mtc0  $zero, SP_SEMAPHORE // clear semaphore
    ori   $8, $zero, 0x5200    // clear yield, set yielded, set taskdone?
    mtc0  $8, SP_STATUS
    break                      // halt RSP and set SP_STATUS_BROKE
    nop

boot_04001068:
    lw    $2, OSTask_flags($1)
    andi  $2, $2, OS_TASK_DP_WAIT
    beq   $2, $zero, boot_load_data
     nop
    jal   check_yielded
     nop
    mfc0  $2, DPC_STATUS
    andi  $2, $2, DPC_STATUS_DMA_BUSY
    bgtz  $2, check_yielded
     nop
boot_load_data:
    lw    $2, OSTask_ucode_data($1)
    lw    $3, OSTask_ucode_data_size($1)
    addi  $3, $3, -1
boot_dma_not_full:
    mfc0  $30, SP_DMA_FULL
    bne   $30, $zero, boot_dma_not_full
     nop
    mtc0  $zero, SP_MEM_ADDR   // ucode_data store at base of DMEM
    mtc0  $2, SP_DRAM_ADDR
    mtc0  $3, SP_RD_LEN
boot_data_dma_busy:
    mfc0  $4, SP_DMA_BUSY
    bne   $4, $zero, boot_data_dma_busy
     nop
    jal   check_yielded
     nop
    j     boot_load_ucode
     nop

.close // CODE_FILE
