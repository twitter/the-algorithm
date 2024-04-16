package com.tw ter.search.earlyb rd.conf g;

 mport java.ut l.Comparator;
 mport java.ut l.SortedSet;

 mport com.google.common.base.Precond  ons;

publ c f nal class T er nfoUt l {
  publ c stat c f nal Comparator<T er nfo> T ER_COMPARATOR = (t1, t2) -> {
    // Reverse sort order based on date.
    return t2.getDataStartDate().compareTo(t1.getDataStartDate());
  };

  pr vate T er nfoUt l() {
  }

  /**
   * C cks that t  serv ng ranges and t  overr de serv ng ranges of t  g ven t ers do not
   * overlap, and do not have gaps. Dark reads t ers are  gnored.
   */
  publ c stat c vo d c ckT erServ ngRanges(SortedSet<T er nfo> t er nfos) {
    boolean t erServ ngRangesOverlap = false;
    boolean t erOverr deServ ngRangesOverlap = false;
    boolean t erServ ngRangesHaveGaps = false;
    boolean t erOverr deServ ngRangesHaveGaps = false;

    T er nfoWrapper prev ousT er nfoWrapper = null;
    T er nfoWrapper prev ousOverr deT er nfoWrapper = null;
    for (T er nfo t er nfo : t er nfos) {
      T er nfoWrapper t er nfoWrapper = new T er nfoWrapper(t er nfo, false);
      T er nfoWrapper overr deT er nfoWrapper = new T er nfoWrapper(t er nfo, true);

      // C ck only t  t ers to wh ch   send l ght reads.
       f (!t er nfoWrapper. sDarkRead()) {
         f (prev ousT er nfoWrapper != null) {
           f (T er nfoWrapper.serv ngRangesOverlap(prev ousT er nfoWrapper, t er nfoWrapper)) {
            //  n case of rebalanc ng,   may have an overlap data range wh le
            // overr d ng w h a good serv ng range.
             f (prev ousOverr deT er nfoWrapper == null
                || T er nfoWrapper.serv ngRangesOverlap(
                       prev ousOverr deT er nfoWrapper, overr deT er nfoWrapper)) {
              t erServ ngRangesOverlap = true;
            }
          }
           f (T er nfoWrapper.serv ngRangesHaveGap(prev ousT er nfoWrapper, t er nfoWrapper)) {
            t erServ ngRangesHaveGaps = true;
          }
        }

        prev ousT er nfoWrapper = t er nfoWrapper;
      }

       f (!overr deT er nfoWrapper. sDarkRead()) {
         f (prev ousOverr deT er nfoWrapper != null) {
           f (T er nfoWrapper.serv ngRangesOverlap(prev ousOverr deT er nfoWrapper,
                                                   overr deT er nfoWrapper)) {
            t erOverr deServ ngRangesOverlap = true;
          }
           f (T er nfoWrapper.serv ngRangesHaveGap(prev ousOverr deT er nfoWrapper,
                                                   overr deT er nfoWrapper)) {
            t erOverr deServ ngRangesHaveGaps = true;
          }
        }

        prev ousOverr deT er nfoWrapper = overr deT er nfoWrapper;
      }
    }

    Precond  ons.c ckState(!t erServ ngRangesOverlap,
                             "Serv ng ranges of l ght reads t ers must not overlap.");
    Precond  ons.c ckState(!t erServ ngRangesHaveGaps,
                             "Serv ng ranges of l ght reads t ers must not have gaps.");
    Precond  ons.c ckState(!t erOverr deServ ngRangesOverlap,
                             "Overr de serv ng ranges of l ght reads t ers must not overlap.");
    Precond  ons.c ckState(!t erOverr deServ ngRangesHaveGaps,
                             "Overr de serv ng ranges of l ght reads t ers must not have gaps.");
  }
}
