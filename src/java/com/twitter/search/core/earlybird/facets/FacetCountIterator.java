package com.tw ter.search.core.earlyb rd.facets;

 mport java. o. OExcept on;
 mport java.ut l.L st;

 mport com.tw ter.common.collect ons.Pa r;

/**
 * T  collect()  thod  s called for every docu nt for wh ch facets shall be counted.
 * T   erator t n calls t  FacetAccumulators for all facets that belong to t 
 * current docu nt.
 */
publ c abstract class FacetCount erator  mple nts FacetTermCollector {

  publ c stat c class  ncre ntData {
    publ c FacetAccumulator[] accumulators;
    publ c  nt   ghtedCount ncre nt;
    publ c  nt penalty ncre nt;
    publ c  nt t epCred;
    publ c  nt language d;
  }

  publ c  ncre ntData  ncre ntData = new  ncre ntData();

  pr vate L st<Pa r< nteger, Long>> proofs = null;

  vo d set ncre ntData( ncre ntData  ncre ntData) {
    t . ncre ntData =  ncre ntData;
  }

  publ c vo d setProofs(L st<Pa r< nteger, Long>> proofs) {
    t .proofs = proofs;
  }

  //  nterface  thod that collects a spec f c term  n a spec f c f eld for t  docu nt.
  @Overr de
  publ c boolean collect( nt doc D, long term D,  nt f eld D) {
    FacetAccumulator accumulator =  ncre ntData.accumulators[f eld D];
    accumulator.add(term D,  ncre ntData.  ghtedCount ncre nt,  ncre ntData.penalty ncre nt,
                     ncre ntData.t epCred);
    accumulator.recordLanguage( ncre ntData.language d);

     f (proofs != null) {
      addProof(doc D, term D, f eld D);
    }
    return true;
  }

  protected vo d addProof( nt doc D, long term D,  nt f eld D) {
    proofs.add(new Pa r<>(f eld D, term D));
  }

  /**
   * Collected facets for t  g ven docu nt.
   */
  publ c abstract vo d collect( nt doc D) throws  OExcept on;
}
