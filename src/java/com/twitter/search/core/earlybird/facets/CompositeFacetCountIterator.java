package com.tw ter.search.core.earlyb rd.facets;

 mport java. o. OExcept on;
 mport java.ut l.Collect on;
 mport java.ut l.L st;

 mport com.tw ter.common.collect ons.Pa r;

/**
 * Calls mult ple FacetCount erators. Currently t   s used for call ng t 
 * default FacetCount ngArray  erator and t  CSF and ret et  erators
 */
publ c class Compos eFacetCount erator extends FacetCount erator {
  pr vate f nal Collect on<FacetCount erator>  erators;

  /**
   * Creates a new compos e  erator on t  prov ded collect on of  erators.
   */
  publ c Compos eFacetCount erator(Collect on<FacetCount erator>  erators) {
    t . erators =  erators;
    for (FacetCount erator  erator :  erators) {
       erator.set ncre ntData(t . ncre ntData);
    }
  }

  @Overr de
  publ c vo d collect( nt doc D) throws  OExcept on {
    for (FacetCount erator  erator :  erators) {
       erator.collect(doc D);
    }
  }

  @Overr de
  protected vo d addProof( nt doc D, long term D,  nt f eld D) {
    for (FacetCount erator  erator :  erators) {
       erator.addProof(doc D, term D, f eld D);
    }
  }

  @Overr de
  publ c vo d setProofs(L st<Pa r< nteger, Long>> proof) {
    for (FacetCount erator  erator :  erators) {
       erator.setProofs(proof);
    }
  }
}
