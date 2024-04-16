package com.tw ter.search.common.sc ma.base;

 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndexedNu r cF eldSett ngs;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftNu r cType;

publ c class  ndexedNu r cF eldSett ngs {
  pr vate f nal Thr ftNu r cType nu r cType;
  pr vate f nal  nt nu r cPrec s onStep;
  pr vate f nal boolean useTw terFormat;
  pr vate f nal boolean useSortableEncod ng;

  /**
   * Create a  ndexedNu r cF eldSett ngs from a Thr ft ndexedNu r cF eldSett ngs
   */
  publ c  ndexedNu r cF eldSett ngs(Thr ft ndexedNu r cF eldSett ngs nu r cF eldSett ngs) {
    t .nu r cType            = nu r cF eldSett ngs.getNu r cType();
    t .nu r cPrec s onStep   = nu r cF eldSett ngs.getNu r cPrec s onStep();
    t .useTw terFormat       = nu r cF eldSett ngs. sUseTw terFormat();
    t .useSortableEncod ng    = nu r cF eldSett ngs. sUseSortableEncod ng();
  }

  publ c Thr ftNu r cType getNu r cType() {
    return nu r cType;
  }

  publ c  nt getNu r cPrec s onStep() {
    return nu r cPrec s onStep;
  }

  publ c boolean  sUseTw terFormat() {
    return useTw terFormat;
  }

  publ c boolean  sUseSortableEncod ng() {
    return useSortableEncod ng;
  }
}
