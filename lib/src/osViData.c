#include "libultra_internal.h"
#ifdef VERSION_EU
OSViMode D_80334990 = {
    /*type*/ 16,
    /*comRegs*/
    { /*ctrl*/ 12574,
      /*width*/ 320,
      /*burst*/ 67380026,
      /*vSync*/ 625,
      /*hSync*/ 1379433,
      /*leap*/ 208604270,
      /*hStart*/ 8389376,
      /*xScale*/ 512,
      /*vCurrent*/ 0 },
    /*fldRegs*/
    { { /*origin*/ 640,
        /*yScale*/ 1024,
        /*vStart*/ 6226489,
        /*vBurst*/ 590443,
        /*vIntr*/ 2 },
      { /*origin*/ 640,
        /*yScale*/ 1024,
        /*vStart*/ 6226489,
        /*vBurst*/ 590443,
        /*vIntr*/ 2 } }
};
OSViMode D_803349E0 = {
    /*type*/ 30, //osViModePalLan1
    /*comRegs*/
    { /*ctrl*/ 12574,
      /*width*/ 320,
      /*burst*/ 73735737,
      /*vSync*/ 525,
      /*hSync*/ 265233,
      /*leap*/ 202968090,
      /*hStart*/ 7078636,
      /*xScale*/ 512,
      /*vCurrent*/ 0 },
    /*fldRegs*/
    { { /*origin*/ 640,
        /*yScale*/ 1024,
        /*vStart*/ 2425343,
        /*vBurst*/ 918020,
        /*vIntr*/ 2 },
      { /*origin*/ 640,
        /*yScale*/ 1024,
        /*vStart*/ 2425343,
        /*vBurst*/ 918020,
        /*vIntr*/ 2 } }
};
OSViMode D_80302FD0 = {
    /*type*/ 2,
    /*comRegs*/
    { /*ctrl*/ 12574,
      /*width*/ 320,
      /*burst*/ 65348153,
      /*vSync*/ 525,
      /*hSync*/ 3093,
      /*leap*/ 202705941,
      /*hStart*/ 7078636,
      /*xScale*/ 512,
      /*vCurrent*/ 0 },
    /*fldRegs*/
    { { /*origin*/ 640,
        /*yScale*/ 1024,
        /*vStart*/ 2425343,
        /*vBurst*/ 918020,
        /*vIntr*/ 2 },
      { /*origin*/ 640,
        /*yScale*/ 1024,
        /*vStart*/ 2425343,
        /*vBurst*/ 918020,
        /*vIntr*/ 2 } }
};

#else

OSViMode D_80334990 = {
    /*type*/ 2,
    /*comRegs*/
    { /*ctrl*/ 12574,
      /*width*/ 320,
      /*burst*/ 65348153,
      /*vSync*/ 525,
      /*hSync*/ 3093,
      /*leap*/ 202705941,
      /*hStart*/ 7078636,
      /*xScale*/ 512,
      /*vCurrent*/ 0 },
    /*fldRegs*/
    { { /*origin*/ 640,
        /*yScale*/ 1024,
        /*vStart*/ 2425343,
        /*vBurst*/ 918020,
        /*vIntr*/ 2 },
      { /*origin*/ 640,
        /*yScale*/ 1024,
        /*vStart*/ 2425343,
        /*vBurst*/ 918020,
        /*vIntr*/ 2 } }
};
OSViMode D_803349E0 = {
    /*type*/ 16,
    /*comRegs*/
    { /*ctrl*/ 12574,
      /*width*/ 320,
      /*burst*/ 67380026,
      /*vSync*/ 625,
      /*hSync*/ 1379433,
      /*leap*/ 208604270,
      /*hStart*/ 8389376,
      /*xScale*/ 512,
      /*vCurrent*/ 0 },
    /*fldRegs*/
    { { /*origin*/ 640,
        /*yScale*/ 1024,
        /*vStart*/ 6226489,
        /*vBurst*/ 590443,
        /*vIntr*/ 2 },
      { /*origin*/ 640,
        /*yScale*/ 1024,
        /*vStart*/ 6226489,
        /*vBurst*/ 590443,
        /*vIntr*/ 2 } }
};
#endif
