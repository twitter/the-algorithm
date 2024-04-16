package com.tw ter.search.core.earlyb rd. ndex;

/**
 * A doc  D mapper that ass gns doc  Ds sequent ally  n decreas ng order, start ng w h t  g ven
 * max  D. Used by Expertsearch, wh ch doesn't  ndex t ets.
 */
publ c class Sequent alDoc DMapper  mple nts Doc DToT et DMapper {
  pr vate f nal  nt maxSeg ntS ze;
  pr vate  nt lastAss gnedDoc d;

  publ c Sequent alDoc DMapper( nt maxSeg ntS ze) {
    t .maxSeg ntS ze = maxSeg ntS ze;
    lastAss gnedDoc d = maxSeg ntS ze;
  }

  @Overr de
  publ c long getT et D( nt doc D) {
    // Should be used only at seg nt opt m zat on t   and  n tests.
     f ((doc D < lastAss gnedDoc d) || (doc D >= maxSeg ntS ze)) {
      return  D_NOT_FOUND;
    }

    return doc D;
  }

  @Overr de
  publ c  nt getDoc D(long t et D) {
    // Should be used only at seg nt opt m zat on t   and  n tests.
     f ((t et D < lastAss gnedDoc d) || (t et D >= maxSeg ntS ze)) {
      return  D_NOT_FOUND;
    }

    return ( nt) t et D;
  }

  @Overr de
  publ c  nt getNumDocs() {
    return maxSeg ntS ze - lastAss gnedDoc d;
  }

  @Overr de
  publ c  nt getNextDoc D( nt doc D) {
     nt nextDoc D = doc D + 1;

    // nextDoc D  s larger than any doc  D that can be ass gned by t  mapper.
     f (nextDoc D >= maxSeg ntS ze) {
      return  D_NOT_FOUND;
    }

    // nextDoc D  s smaller than any doc  D ass gned by t  mapper so far.
     f (nextDoc D < lastAss gnedDoc d) {
      return lastAss gnedDoc d;
    }

    // nextDoc D  s  n t  range of doc  Ds ass gned by t  mapper.
    return nextDoc D;
  }

  @Overr de
  publ c  nt getPrev ousDoc D( nt doc D) {
     nt prev ousDoc D = doc D - 1;

    // prev ousDoc D  s larger than any doc  D that can be ass gned by t  mapper.
     f (prev ousDoc D >= maxSeg ntS ze) {
      return maxSeg ntS ze - 1;
    }

    // prev ousDoc D  s smaller than any doc  D ass gned by t  mapper so far.
     f (prev ousDoc D < lastAss gnedDoc d) {
      return  D_NOT_FOUND;
    }

    // prev ousDoc D  s  n t  range of doc  Ds ass gned by t  mapper.
    return prev ousDoc D;
  }

  @Overr de
  publ c  nt addMapp ng(f nal long t et D) {
    return --lastAss gnedDoc d;
  }

  @Overr de
  publ c Doc DToT et DMapper opt m ze() {
    // Seg nts that use t  Doc DToT et DMapper should never be opt m zed.
    throw new UnsupportedOperat onExcept on("Sequent alDoc DMapper cannot be opt m zed.");
  }
}
