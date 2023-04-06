.set noreorder # don't insert nops after branches
.set gp=64

.include "macros.inc"


.section .text, "ax"


glabel __osDisableInt
  mfc0  $t0, $12
  and   $t1, $t0, -2
  mtc0  $t1, $12
  andi  $v0, $t0, 1
  nop
  jr    $ra
   nop

