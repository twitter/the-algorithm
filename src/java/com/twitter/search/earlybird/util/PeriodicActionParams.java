package com.tw ter.search.earlyb rd.ut l;

 mport java.ut l.concurrent.T  Un ;

/**
 * Spec f es t m ng and type of per od act ons that   sc dule.
 *
 * See:
 *  https://docs.oracle.com/javase/8/docs/ap /java/ut l/concurrent/Sc duledExecutorServ ce.html
 */
publ c f nal class Per od cAct onParams {
  pr vate enum DelayType {
    F XED_DELAY,
    F XED_RATE
  }

  pr vate long  n  alDelayDurat on;
  pr vate long  ntervalDurat on;
  pr vate T  Un   ntervalUn ;
  pr vate DelayType delayType;

  publ c long get n  alDelayDurat on() {
    return  n  alDelayDurat on;
  }

  publ c long get ntervalDurat on() {
    return  ntervalDurat on;
  }

  publ c T  Un  get ntervalUn () {
    return  ntervalUn ;
  }

  publ c DelayType getDelayType() {
    return delayType;
  }

  pr vate Per od cAct onParams(
      DelayType delayType,
      long  n  alDelayDurat on,
      long  ntervalDurat on,
      T  Un   ntervalUn ) {
    t .delayType = delayType;
    t . ntervalDurat on =  ntervalDurat on;
    t . n  alDelayDurat on =  n  alDelayDurat on;
    t . ntervalUn  =  ntervalUn ;
  }

  // Runs start at t  s start, start+X, start+2*X etc., so t y can poss bly overlap.
  publ c stat c Per od cAct onParams atF xedRate(
      long  ntervalDurat on,
      T  Un   ntervalUn ) {
    return new Per od cAct onParams(DelayType.F XED_RATE, 0,
         ntervalDurat on,  ntervalUn );
  }

  // Delay bet en every run.
  // T  order of what happens  s:
  //    n  al delay, run task, wa  X t  , run task, wa  X t  , etc.
  // Runs can't overlap.
  publ c stat c Per od cAct onParams w h nt alWa AndF xedDelay(
      long  n  alDelayDurat on,
      long  ntervalDurat on,
      T  Un   ntervalUn ) {
    return new Per od cAct onParams(DelayType.F XED_DELAY,  n  alDelayDurat on,
         ntervalDurat on,  ntervalUn );
  }

  // Delay bet en every run.
  publ c stat c Per od cAct onParams w hF xedDelay(
      long  ntervalDurat on,
      T  Un   ntervalUn ) {
    return w h nt alWa AndF xedDelay(0,  ntervalDurat on,  ntervalUn );
  }

  boolean  sF xedDelay() {
    return t .delayType == DelayType.F XED_DELAY;
  }
}
