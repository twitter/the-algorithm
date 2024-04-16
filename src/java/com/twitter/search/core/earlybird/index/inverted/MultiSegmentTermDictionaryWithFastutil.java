package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java.ut l.Arrays;
 mport java.ut l.HashMap;
 mport java.ut l.L st;
 mport java.ut l.Opt onal nt;
 mport java.ut l.concurrent.T  Un ;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Stopwatch;
 mport com.google.common.collect. mmutableL st;
 mport com.google.common.collect.Maps;

 mport org.apac .lucene.ut l.BytesRef;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. tr cs.SearchT  rStats;
 mport com.tw ter.search.common.ut l.LogFormatUt l;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;

 mport  .un m .ds .fastut l. nts. ntArrayL st;

/**
 * T   mple ntat on took Mult Seg ntTermD ct onaryW hMap and replaced so  of t 
 * data structures w h fastut l equ valents and   also uses a more  mory eff c ent way to
 * store t  precomputed data.
 *
 * T   mple ntat on has a requ re nt that each term per f eld needs to be present at
 * most once per docu nt, s nce   only have space to  ndex 2^24 terms and   have 2^23
 * docu nts as of now  n realt   earlyb rds.
 *
 * See User dMult Seg ntQuery class com nt for more  nformat on on how t   s used.
 */
publ c class Mult Seg ntTermD ct onaryW hFastut l  mple nts Mult Seg ntTermD ct onary {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(
      Mult Seg ntTermD ct onaryW hFastut l.class);

  @V s bleForTest ng
  publ c stat c f nal SearchT  rStats TERM_D CT ONARY_CREAT ON_STATS =
      SearchT  rStats.export("mult _seg nt_term_d ct onary_w h_fastut l_creat on",
          T  Un .M LL SECONDS, false);

  pr vate stat c f nal  nt MAX_TERM_ D_B TS = 24;
  pr vate stat c f nal  nt TERM_ D_MASK = (1 << MAX_TERM_ D_B TS) - 1; // F rst 24 b s.
  pr vate stat c f nal  nt MAX_SEGMENT_S ZE = 1 << (MAX_TERM_ D_B TS - 1);

  pr vate f nal  mmutableL st<Opt m zed mory ndex>  ndexes;

  // For each term, a l st of ( ndex  d, term  d) packed  nto an  nteger.
  // T   nteger conta ns:
  // byte 0:  ndex (seg nt  d). S nce   have ~20 seg nts, t  f s  nto a byte.
  // bytes [1-3]: term  d. T  terms  're bu ld ng t  d ct onary for are user  ds
  //   assoc ated w h a t et - from_user_ d and  n_reply_to_user_ d. S nce   have
  //   at most 2**23 t ets  n realt  ,  'll have at most 2**23 un que terms per
  //   seg nts. T  term  ds post opt m zat on are consecut ve numbers, so t y w ll
  //   f   n 24 b s.   don't use t  term d ct onary  n arch ve, wh ch has more
  //   t ets per seg nt.
  //
  //   To ver fy t  max mum amount of t ets  n a seg nt, see max_seg nt_s ze  n
  //   earlyb rd-conf g.yml.
  pr vate f nal HashMap<BytesRef,  ntArrayL st> termsMap;
  pr vate f nal  nt numTerms;
  pr vate f nal  nt numTermEntr es;

   nt encode ndexAndTerm d( nt  ndex d,  nt term d) {
    // Push t   ndex  d to t  left and use t  ot r 24 b s for t  term  d.
    return ( ndex d << MAX_TERM_ D_B TS) | term d;
  }

  vo d decode ndexAndTerm d( nt[] arr,  nt packed) {
    arr[packed >> MAX_TERM_ D_B TS] = packed & TERM_ D_MASK;
  }


  /**
   * Creates a new mult -seg nt term d ct onary backed by a regular java map.
   */
  publ c Mult Seg ntTermD ct onaryW hFastut l(
      Str ng f eld,
      L st<Opt m zed mory ndex>  ndexes) {

    t . ndexes =  mmutableL st.copyOf( ndexes);

    // Pre-s ze t  map w h est mate of max number of terms.   should be at least that b g.
    Opt onal nt opt onalMax =  ndexes.stream().mapTo nt(Opt m zed mory ndex::getNumTerms).max();
     nt maxNumTerms = opt onalMax.orElse(0);
    t .termsMap = Maps.newHashMapW hExpectedS ze(maxNumTerms);

    LOG. nfo("About to  rge {}  ndexes for f eld {}, est mated {} terms",
         ndexes.s ze(), f eld, LogFormatUt l.format nt(maxNumTerms));
    Stopwatch stopwatch = Stopwatch.createStarted();

    BytesRef termBytesRef = new BytesRef();

    for ( nt  ndex d = 0;  ndex d <  ndexes.s ze();  ndex d++) {
      // T   nverted  ndex for t  f eld.
      Opt m zed mory ndex  ndex =  ndexes.get( ndex d);

       nt  ndexNumTerms =  ndex.getNumTerms();

       f ( ndexNumTerms > MAX_SEGMENT_S ZE) {
        throw new  llegalStateExcept on("too many terms: " +  ndexNumTerms);
      }

      for ( nt term d = 0; term d <  ndexNumTerms; term d++) {
         ndex.getTerm(term d, termBytesRef);

         ntArrayL st  ndexTerms = termsMap.get(termBytesRef);
         f ( ndexTerms == null) {
          BytesRef term = BytesRef.deepCopyOf(termBytesRef);

           ndexTerms = new  ntArrayL st();
          termsMap.put(term,  ndexTerms);
        }

         ndexTerms.add(encode ndexAndTerm d( ndex d, term d));
      }
    }

    t .numTerms = termsMap.s ze();
    t .numTermEntr es =  ndexes.stream().mapTo nt(Opt m zed mory ndex::getNumTerms).sum();

    TERM_D CT ONARY_CREAT ON_STATS.t  r ncre nt(stopwatch.elapsed(T  Un .M LL SECONDS));
    LOG. nfo("Done  rg ng {} seg nts for f eld {}  n {} - "
            + "num terms: {}, num term entr es: {}.",
         ndexes.s ze(), f eld, stopwatch,
        LogFormatUt l.format nt(t .numTerms),
        LogFormatUt l.format nt(t .numTermEntr es));
  }

  @Overr de
  publ c  nt[] lookupTerm ds(BytesRef term) {
     nt[] term ds = new  nt[ ndexes.s ze()];
    Arrays.f ll(term ds, Earlyb rd ndexSeg ntAtom cReader.TERM_NOT_FOUND);

     ntArrayL st  ndexTerms = termsMap.get(term);
     f ( ndexTerms != null) {
      for ( nt   = 0;   <  ndexTerms.s ze();  ++) {
        decode ndexAndTerm d(term ds,  ndexTerms.get nt( ));
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
