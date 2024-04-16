package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java.ut l.HashMap;
 mport java.ut l. erator;
 mport java.ut l.Map;

 mport org.apac .lucene. ndex.F elds;
 mport org.apac .lucene. ndex.Terms;

publ c class  n moryF elds extends F elds {
  pr vate f nal Map< nverted ndex, Terms> termsCac  = new HashMap<>();
  pr vate f nal Map<Str ng,  nverted ndex> perF elds;
  pr vate f nal Map< nverted ndex,  nteger> po nter ndex;

  /**
   * Returns a new {@l nk F elds}  nstance for t  prov ded {@l nk  nverted ndex}es.
   */
  publ c  n moryF elds(Map<Str ng,  nverted ndex> perF elds,
                        Map< nverted ndex,  nteger> po nter ndex) {
    t .perF elds = perF elds;
    t .po nter ndex = po nter ndex;
  }

  @Overr de
  publ c  erator<Str ng>  erator() {
    return perF elds.keySet(). erator();
  }

  @Overr de
  publ c Terms terms(Str ng f eld) {
     nverted ndex  nverted ndex = perF elds.get(f eld);
     f ( nverted ndex == null) {
      return null;
    }

    return termsCac .compute fAbsent( nverted ndex,
         ndex ->  ndex.createTerms(po nter ndex.getOrDefault( nverted ndex, -1)));
  }

  @Overr de
  publ c  nt s ze() {
    return perF elds.s ze();
  }
}
