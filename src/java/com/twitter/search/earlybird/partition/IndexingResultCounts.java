package com.tw ter.search.earlyb rd.part  on;

/**
 *  lper class used to store counts to be logged.
 */
publ c class  ndex ngResultCounts {
  pr vate  nt  ndex ngCalls;
  pr vate  nt fa lureRetr able;
  pr vate  nt fa lureNotRetr able;
  pr vate  nt  ndex ngSuccess;

  publ c  ndex ngResultCounts() {
  }

  /**
   * Updates t   nternal counts w h a s ngle result.
   */
  publ c vo d countResult( Seg ntWr er.Result result) {
     ndex ngCalls++;
     f (result ==  Seg ntWr er.Result.FA LURE_NOT_RETRYABLE) {
      fa lureNotRetr able++;
    } else  f (result ==  Seg ntWr er.Result.FA LURE_RETRYABLE) {
      fa lureRetr able++;
    } else  f (result ==  Seg ntWr er.Result.SUCCESS) {
       ndex ngSuccess++;
    }
  }

   nt get ndex ngCalls() {
    return  ndex ngCalls;
  }

   nt getFa lureRetr able() {
    return fa lureRetr able;
  }

   nt getFa lureNotRetr able() {
    return fa lureNotRetr able;
  }

   nt get ndex ngSuccess() {
    return  ndex ngSuccess;
  }

  @Overr de
  publ c Str ng toStr ng() {
    return Str ng.format("[calls: %,d, success: %,d, fa l not-retryable: %,d, fa l retryable: %,d]",
         ndex ngCalls,  ndex ngSuccess, fa lureNotRetr able, fa lureRetr able);
  }
}

