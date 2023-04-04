#include "libultra_internal.h"

typedef struct {
    u8 unk00 : 2;
    u8 pad : 4;
    u8 unk01 : 2;
    u8 unk2[3];
} unkStruct;

extern u32 D_80334A44;

u32 D_80334A30 = 0;
u32 D_80334A34 = 0;
s32 D_80334A38 = 0;

extern u8 D_80365E40[0x100];

OSThread gInterruptedThread;

void u32_to_string(u32 i, u8 *str) {
    str[0] = (i >> 0x18) & 0xff;
    str[1] = (i >> 0x10) & 0xff;
    str[2] = (i >> 0x8) & 0xff;
    str[3] = i & 0xff;
}

u32 string_to_u32(u8 *str) {
    u32 i;
    i = (str[0] & 0xff) << 0x18;
    i |= (str[1] & 0xff) << 0x10;
    i |= (str[2] & 0xff) << 0x8;
    i |= (str[3] & 0xff);
    return i;
}

void send_packet(u8 *a0, s32 a1) {
    unkStruct sp1c;
    s32 i;
    sp1c.unk00 = 2;
    for (sp1c.unk01 = a1, i = 0; i < a1; i++) {
        sp1c.unk2[i] = a0[i];
    }
    *(volatile u32 *) 0xc0000000 = *(u32 *) &sp1c;
    while (!(__osGetCause() & 0x2000)) {
        ;
    }
    *(volatile u32 *) 0xc000000c = 0;
}

void send(u8 *buff, s32 len) {
    s32 i;
    s32 end;
    s32 rem;
    if (!D_80334A44) {
        while (!(__osGetCause() & 0x2000)) {
            ;
        }
        *(volatile u32 *) 0xc000000c = 0;
        D_80334A44 = 1;
    }
    i = 0;
    rem = len % 3;
    end = len - rem;
    for (; i < end; i += 3) {
        send_packet(&buff[i], 3);
    }
    if (rem > 0) {
        send_packet(&buff[end], rem);
    }
}

void process_command_memory(void) {
    u32 sp1c;
    u32 sp18;
    sp1c = string_to_u32(&D_80365E40[1]);
    sp18 = string_to_u32(&D_80365E40[5]);
    send((u8 *) (uintptr_t) sp1c, sp18);
}

void process_command_register(void) {
    send((u8 *) &gInterruptedThread.context, sizeof(__OSThreadContext));
}

void kdebugserver(u32 a0) {
    u32 sp2c;
    unkStruct sp28;
    *(u32 *) &sp28 = a0;
    for (sp2c = 0; sp2c < sp28.unk01; sp2c++) {
        D_80365E40[D_80334A34] = sp28.unk2[sp2c];
        D_80334A34++;
    }
    D_80334A38 -= sp28.unk01;
    switch (D_80334A30) {
        case 0:
            switch (sp28.unk2[0]) {
                case 1:
                    D_80334A30 = 1;
                    D_80334A38 = 9 - sp28.unk01;
                    break;
                case 2:
                    process_command_register();
                    D_80334A30 = 0;
                    D_80334A34 = 0;
                    D_80334A38 = 0;
                    break;
                default:
                    D_80334A30 = 0;
                    D_80334A34 = 0;
                    D_80334A38 = 0;
                    break;
            }
            break;
        case 1:
            if (D_80334A38 <= 0) {
                if (D_80365E40[0] == 1) {
                    process_command_memory();
                    D_80334A30 = 0;
                    D_80334A34 = 0;
                    D_80334A38 = 0;
                } else {
                    D_80334A30 = 0;
                    D_80334A34 = 0;
                    D_80334A38 = 0;
                }
            }
            break;
        default:
            D_80334A30 = 0;
            D_80334A34 = 0;
            D_80334A38 = 0;
            break;
    }
}
