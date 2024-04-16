package com.tw ter.search.earlyb rd.except on;

 mport com.tw ter.ut l.AbstractMon or;

publ c class UncaughtExcept onHandler extends AbstractMon or {
  pr vate f nal Cr  calExcept onHandler cr  calExcept onHandler;

  publ c UncaughtExcept onHandler() {
    t .cr  calExcept onHandler = new Cr  calExcept onHandler();
  }

  publ c vo d setShutdownHook(Runnable shutdown) {
    t .cr  calExcept onHandler.setShutdownHook(shutdown);
  }

  @Overr de
  publ c boolean handle(Throwable e) {
    cr  calExcept onHandler.handle(t , e);

    //   return true  re because   handle all except ons.
    return true;
  }
}
