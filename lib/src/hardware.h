#ifndef _HARDWARE_H_
#define _HARDWARE_H_

#define HW_REG(reg, type) *(volatile type *)(uintptr_t)((reg) | 0xa0000000)

#define AI_DRAM_ADDR_REG 0x04500000
#define AI_LEN_REG 0x04500004
#define AI_CONTROL_REG 0x04500008
#define AI_STATUS_REG 0x0450000C
#define AI_STATUS_AI_FULL (1 << 31)
#define AI_STATUS_AI_BUSY (1 << 30)
#define AI_DACRATE_REG 0x04500010
#define AI_BITRATE_REG 0x04500014

#define VI_STATUS_REG 0x04400000
#define VI_CONTROL_REG 0x04400000
#define VI_ORIGIN_REG 0x04400004
#define VI_DRAM_ADDR_REG 0x04400004
#define VI_WIDTH_REG 0x04400008
#define VI_H_WIDTH_REG 0x04400008
#define VI_INTR_REG 0x0440000C
#define VI_V_INTER_REG 0x0440000C
#define VI_CURRENT_REG 0x04400010
#define VI_V_CURRENT_LINE_REG 0x04400010
#define VI_BURST_REG 0x04400014
#define VI_TIMING_REG 0x04400014
#define VI_V_SYNC_REG 0x04400018 //VI vertical sync
#define VI_H_SYNC_REG 0x0440001C //VI horizontal sync
#define VI_LEAP_REG 0x04400020   //VI horizontal sync leap
#define VI_H_SYNC_LEAP_REG 0x04400020
#define VI_H_START_REG 0x04400024 //VI horizontal video
#define VI_H_VIDEO_REG 0x04400024
#define VI_V_START_REG 0x04400028 //VI vertical video
#define VI_V_VIDEO_REG 0x04400028
#define VI_V_BURST_REG 0x0440002C //VI vertical burst
#define VI_X_SCALE_REG 0x04400030 //VI x-scale
#define VI_Y_SCALE_REG 0x04400034 //VI y-scale

#define SP_IMEM_START 0x04001000
#define SP_DMEM_START 0x04000000

#define SP_MEM_ADDR_REG 0x04040000
#define SP_DRAM_ADDR_REG 0x04040004
#define SP_RD_LEN_REG 0x04040008
#define SP_WR_LEN_REG 0x0404000C
#define SP_STATUS_REG 0x04040010
#define SP_PC_REG 0x04080000

#define PI_DRAM_ADDR_REG 0x04600000    //PI DRAM address
#define PI_CART_ADDR_REG 0x04600004    //PI pbus (cartridge) address
#define PI_RD_LEN_REG 0x04600008       //PI read length
#define PI_WR_LEN_REG 0x0460000C       //PI write length
#define PI_STATUS_REG 0x04600010       //PI status
#define PI_BSD_DOM1_LAT_REG 0x04600014 //PI dom1 latency
#define PI_DOMAIN1_REG 0x04600014
#define PI_BSD_DOM1_PWD_REG 0x04600018 //PI dom1 pulse width
#define PI_BSD_DOM1_PGS_REG 0x0460001C //PI dom1 page size
#define PI_BSD_DOM1_RLS_REG 0x04600020 //PI dom1 release
#define PI_BSD_DOM2_LAT_REG 0x04600024 //PI dom2 latency
#define PI_DOMAIN2_REG 0x04600024
#define PI_BSD_DOM2_PWD_REG 0x04600028 //PI dom2 pulse width
#define PI_BSD_DOM2_PGS_REG 0x0460002C //PI dom2 page size
#define PI_BSD_DOM2_RLS_REG 0x04600030 //PI dom2 release

#define PI_STATUS_BUSY 0x1
#define PI_STATUS_IOBUSY 0x2
#define PI_STATUS_ERROR 0x3

#define PI_STATUS_RESET_CONTROLLER 0x1
#define PI_STATUS_CLEAR_INTR 0x2

#define SI_DRAM_ADDR_REG 0x04800000
#define SI_PIF_ADDR_RD64B_REG 0x04800004
#define SI_PIF_ADDR_WR64B_REG 0x04800010
#define SI_STATUS_REG 0x04800018

#define SI_STATUS_DMA_BUSY 0x1
#define SI_STATUS_IO_READ_BUSY 0x2
#define SI_STATUS_DMA_ERROR 0x8
#define SI_STATUS_INTERRUPT (1 << 12)

#define MI_INIT_MODE_REG 0x04300000
#define MI_MODE_REG MI_INIT_MODE_REG
#define MI_VERSION_REG 0x04300004
#define MI_INTR_REG 0x04300008
#define MI_INTR_MASK_REG 0x0430000C

//https://github.com/LuigiBlood/64dd/wiki/Registers
#define ASIC_STATUS 0x05000508

#define DATA_REQUEST              0x40000000
#define C2_TRANSFER               0x10000000
#define BUFFER_MANAGER_ERROR      0x08000000
#define BUFFER_MANAGER_INTERRUPT  0x04000000
#define MECHANIC_INTERRUPT        0x02000000
#define DISK_PRESENT              0x01000000
#define BUSY_STATE                0x00800000
#define RESET_STATE               0x00400000
#define MOTOR_NOT_SPINNING        0x00100000
#define HEAD_RETRACTED            0x00080000
#define WRITE_PROTECT_ERROR       0x00040000
#define MECHANIC_ERROR            0x00020000
#define DISK_CHANGE               0x00010000

#define _64DD_PRESENT_MASK 0xFFFF


//ro
#define ASIC_BM_STATUS 0x05000510

#define MICRO_STATUS 0x02000000
#define C1_DOUBLE 0x00400000
#define C1_SINGLE 0x00200000

//wo
#define ASIC_BM_CTL 0x05000510
#define BUFFER_MANAGER_RESET 0x10000000
#define MECHANIC_INTERRUPT_RESET 0x01000000
/*- Start Buffer Manager      (0x80000000)
- Buffer Manager Mode       (0x40000000)
- BM Interrupt Mask         (0x20000000)
- Buffer Manager Reset      (0x10000000)
- Disable OR Check?         (0x08000000)
- Disable C1 Correction     (0x04000000)
- Block Transfer            (0x02000000)
- Mechanic Interrupt Reset  (0x01000000)*/
#endif
