#ifndef _ULTRA64_UCODE_H_
#define _ULTRA64_UCODE_H_

#define SP_DRAM_STACK_SIZE8 0x400
#define SP_UCODE_SIZE       0x1000
#define SP_UCODE_DATA_SIZE  0x800

// standard boot ucode
extern u64 rspF3DBootStart[], rspF3DBootEnd[];

// F3D ucode
extern u64 rspF3DStart[], rspF3DEnd[];

// F3D ucode data
extern u64 rspF3DDataStart[], rspF3DDataEnd[];

// aspMain (audio) ucode
extern u64 rspAspMainStart[], rspAspMainEnd[];

// aspMain ucode data
extern u64 rspAspMainDataStart[], rspAspMainDataEnd[];

#endif
