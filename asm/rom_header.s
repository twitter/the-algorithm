/*
 * Super Mario 64 ROM header
 * Only the first 0x18 bytes matter to the console.
 */

.byte  0x80, 0x37, 0x12, 0x40   /* PI BSD Domain 1 register */
.word  0x0000000F               /* Clockrate setting*/
.word  entry_point               /* Entrypoint */

/* Revision */
.if VERSION_SH == 1
    .word  0x00001448
.elseif VERSION_EU == 1
    .word  0x00001446
.else /* NTSC-U and NTSC-J 1.0 */
    .word  0x00001444
.endif

.word  0x4EAA3D0E               /* Checksum 1 */
.word  0x74757C24               /* Checksum 2 */
.word  0x00000000               /* Unknown */
.word  0x00000000               /* Unknown */
.if VERSION_SH == 1
.ascii "SUPERMARIO64        "   /* Internal ROM name */
.else
.ascii "SUPER MARIO 64      "   /* Internal ROM name */
.endif
.word  0x00000000               /* Unknown */
.word  0x0000004E               /* Cartridge */
.ascii "SM"                     /* Cartridge ID */

/* Region */
.if VERSION_US == 1
    .ascii "E"                  /* NTSC-U (North America) */
.elseif (VERSION_JP == 1 || VERSION_SH == 1)
    .ascii "J"                  /* NTSC-J (Japan) */
.else
    .ascii "P"                  /* PAL (Europe) */
.endif

.if VERSION_SH == 1
    .byte 0x03                  /* Version (Shindou) */
.else
    .byte  0x00                 /* Version */
.endif

