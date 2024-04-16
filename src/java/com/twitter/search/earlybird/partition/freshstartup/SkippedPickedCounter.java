package com.tw ter.search.earlyb rd.part  on.freshstartup;

class Sk ppedP ckedCounter {
  pr vate long sk pped;
  pr vate long p cked;
  pr vate Str ng na ;

  publ c Sk ppedP ckedCounter(Str ng na ) {
    t .sk pped = 0;
    t .p cked = 0;
    t .na  = na ;
  }

  @Overr de
  publ c Str ng toStr ng() {
    return Str ng.format("[%s - p cked: %,d, sk pped: %,d]",
        na , p cked, sk pped);
  }

  vo d  ncre ntSk pped() {
    sk pped++;
  }
  vo d  ncre ntP cked() {
    p cked++;
  }
}
