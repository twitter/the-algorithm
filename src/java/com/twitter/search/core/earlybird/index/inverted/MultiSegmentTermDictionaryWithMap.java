package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java.ut l.Arrays;
 mport java.ut l.HashMap;
 mport java.ut l.L st;
 mport java.ut l.Opt onal nt;
 mport java.ut l.concurrent.T  Un ;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.collect. mmutableL st;
 mport com.google.common.collect.L sts;
 mport com.google.common.collect.Maps;

 mport org.apac .lucene.ut l.BytesRef;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. tr cs.SearchT  rStats;
 mport com.tw ter.search.common.ut l.LogFormatUt l;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;

/**
 * A rat r s mple  mple ntat on of a Mult Seg ntTermD ct onary that just keeps all terms  n a
 * java hash map, and all t  term ds for a term  n a java l st.
 *
 * An alternate  mple ntat on could have an MPH for t  map, and a  ntBlockPool for stor ng
 * t  term  ds.
 *
 * See User dMult Seg ntQuery class com nt for more  nformat on on how t   s used.
 */
publ c class Mult Seg ntTermD ct onaryW hMap  mple nts Mult Seg ntTermD ct onary {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(
      Mult Seg ntTermD ct onaryW hMap.class);

  @V s bleForTest ng
  publ c stat c f nal SearchT  rStats TERM_D CT ONARY_CREAT ON_STATS =
      SearchT  rStats.export("mult _seg nt_term_d ct onary_w h_map_creat on",
          T  Un .M LL SECONDS, false);

  pr vate f nal  mmutableL st<Opt m zed mory ndex>  ndexes;
  pr vate f nal HashMap<BytesRef, L st< ndexTerm>> termsMap;
  pr vate f nal  nt numTerms;
  pr vate f nal  nt numTermEntr es;

  pr vate stat c class  ndexTerm {
    pr vate  nt  ndex d;
    pr vate f nal  nt term d;

    publ c  ndexTerm( nt  ndex d,  nt term d) {
      t . ndex d =  ndex d;
      t .term d = term d;
    }
  }

  /**
   * Creates a new mult -seg nt term d ct onary backed by a regular java map.
   */
  publ c Mult Seg ntTermD ct onaryW hMap(
      Str ng f eld,
      L st<Opt m zed mory ndex>  ndexes) {

    t . ndexes =  mmutableL st.copyOf( ndexes);

    // Pre-s ze t  map w h est mate of max number of terms.   should be at least that b g.
    Opt onal nt opt onalMax =  ndexes.stream().mapTo nt(Opt m zed mory ndex::getNumTerms).max();
     nt maxNumTerms = opt onalMax.orElse(0);
    t .termsMap = Maps.newHashMapW hExpectedS ze(maxNumTerms);

    LOG. nfo("About to  rge {}  ndexes for f eld {}, est mated {} terms",
         ndexes.s ze(), f eld, LogFormatUt l.format nt(maxNumTerms));
    long start = System.currentT  M ll s();

    BytesRef termText = new BytesRef();
    long cop edBytes = 0;
    for ( nt  ndex d = 0;  ndex d <  ndexes.s ze();  ndex d++) {
      // T   nverted  ndex for t  f eld.
      Opt m zed mory ndex  ndex =  ndexes.get( ndex d);

       nt  ndexNumTerms =  ndex.getNumTerms();
      for ( nt term d = 0; term d <  ndexNumTerms; term d++) {
         ndex.getTerm(term d, termText);

        // T  cop es t  underly ng array to a new array.
        BytesRef term = BytesRef.deepCopyOf(termText);
        cop edBytes += term.length;

        L st< ndexTerm>  ndexTerms = termsMap.compute fAbsent(term, k -> L sts.newArrayL st());

         ndexTerms.add(new  ndexTerm( ndex d, term d));
      }
    }

    t .numTerms = termsMap.s ze();
    t .numTermEntr es =  ndexes.stream().mapTo nt(Opt m zed mory ndex::getNumTerms).sum();

    long elapsed = System.currentT  M ll s() - start;
    TERM_D CT ONARY_CREAT ON_STATS.t  r ncre nt(elapsed);
    LOG. nfo("Done  rg ng {}  ndexes for f eld {}  n {}ms - "
      + "num terms: {}, num term entr es: {}, cop ed bytes: {}",
         ndexes.s ze(), f eld, elapsed,
        LogFormatUt l.format nt(t .numTerms), LogFormatUt l.format nt(t .numTermEntr es),
            LogFormatUt l.format nt(cop edBytes));
  }

  @Overr de
  publ c  nt[] lookupTerm ds(BytesRef term) {
     nt[] term ds = new  nt[ ndexes.s ze()];
    Arrays.f ll(term ds, Earlyb rd ndexSeg ntAtom cReader.TERM_NOT_FOUND);

    L st< ndexTerm>  ndexTerms = termsMap.get(term);
     f ( ndexTerms != null) {
      for ( ndexTerm  ndexTerm :  ndexTerms) {
        term ds[ ndexTerm. ndex d] =  ndexTerm.term d;
      }
    }

    return term ds;
  }

  @Overr de
  publ c  mmutableL st<? extends  nverted ndex> getSeg nt ndexes() {
    return  ndexes;
  }

  @Overr de
  publ c  nt getNumTerms() {
    return t .numTerms;
  }

  @Overr de
  publ c  nt getNumTermEntr es() {
    return t .numTermEntr es;
  }
}
