package com.tw ter.search.earlyb rd.ut l;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common. tr cs.SearchCounter;

publ c abstract class Sc duledExecutorTask  mple nts Runnable {
  pr vate f nal SearchCounter counter;
  protected f nal Clock clock;

  publ c Sc duledExecutorTask(SearchCounter counter, Clock clock) {
    Precond  ons.c ckNotNull(counter);
    t .counter = counter;
    t .clock = clock;
  }

  @Overr de
  publ c f nal vo d run() {
    counter. ncre nt();
    runOne erat on();
  }

  @V s bleForTest ng
  protected abstract vo d runOne erat on();
}
