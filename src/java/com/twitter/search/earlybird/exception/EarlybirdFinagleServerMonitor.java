package com.tw ter.search.earlyb rd.except on;

 mport com.tw ter.f nagle.Fa lure;
 mport com.tw ter.ut l.AbstractMon or;

publ c class Earlyb rdF nagleServerMon or extends AbstractMon or {
  pr vate f nal Cr  calExcept onHandler cr  calExcept onHandler;

  publ c Earlyb rdF nagleServerMon or(Cr  calExcept onHandler cr  calExcept onHandler) {
    t .cr  calExcept onHandler = cr  calExcept onHandler;
  }

  @Overr de
  publ c boolean handle(Throwable e) {
     f (e  nstanceof Fa lure) {
      // sk p F nagle fa lure
      return true;
    }

    cr  calExcept onHandler.handle(t , e);

    //   return true  re because   handle all except ons.
    return true;
  }
}
