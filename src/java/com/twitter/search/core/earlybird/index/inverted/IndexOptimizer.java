package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java. o. OExcept on;
 mport java.ut l.HashMap;
 mport java.ut l.Map;
 mport java.ut l.concurrent.ConcurrentHashMap;

 mport com.google.common.base.Precond  ons;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;
 mport org.apac .lucene. ndex.Post ngsEnum;
 mport org.apac .lucene. ndex.TermsEnum;
 mport org.apac .lucene.search.Doc dSet erator;
 mport org.apac .lucene.ut l.BytesRef;

 mport com.tw ter.search.common.sc ma.base.Earlyb rdF eldType;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.core.earlyb rd.facets.AbstractFacetCount ngArray;
 mport com.tw ter.search.core.earlyb rd.facets.FacetLabelProv der;
 mport com.tw ter.search.core.earlyb rd.facets.FacetUt l;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rdRealt   ndexSeg ntData;
 mport com.tw ter.search.core.earlyb rd. ndex.T  Mapper;
 mport com.tw ter.search.core.earlyb rd. ndex.column.DocValuesManager;

publ c f nal class  ndexOpt m zer {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger( ndexOpt m zer.class);

  pr vate  ndexOpt m zer() {
  }

  /**
   * Opt m zes t   n- mory  ndex seg nt.
   */
  publ c stat c Earlyb rdRealt   ndexSeg ntData opt m ze(
      Earlyb rdRealt   ndexSeg ntData s ce) throws  OExcept on {
    LOG. nfo("Start ng  ndex opt m z ng.");

    ConcurrentHashMap<Str ng,  nverted ndex> targetMap = new ConcurrentHashMap<>();
    LOG. nfo(Str ng.format(
        "S ce PerF eldMap s ze  s %d", s ce.getPerF eldMap().s ze()));

    LOG. nfo("Opt m ze doc  d mapper.");
    // Opt m ze t  doc  D mapper f rst.
    Doc DToT et DMapper or g nalT et dMapper = s ce.getDoc DToT et DMapper();
    Doc DToT et DMapper opt m zedT et dMapper = or g nalT et dMapper.opt m ze();

    T  Mapper opt m zedT  Mapper =
        s ce.getT  Mapper() != null
        ? s ce.getT  Mapper().opt m ze(or g nalT et dMapper, opt m zedT et dMapper)
        : null;

    // So  f elds have t  r terms rewr ten to support t  m n mal perfect hash funct on   use
    // (note that  's a m n mal perfect hash funct on, not a m n mal perfect hash _table_).
    // T  FacetCount ngArray stores term  Ds. T   s a map from t  facet f eld  D to a map from
    // or g nal term  D to t  new, MPH term  Ds.
    Map< nteger,  nt[]> term DMapper = new HashMap<>();

    LOG. nfo("Opt m ze  nverted  ndexes.");
    opt m ze nverted ndexes(
        s ce, targetMap, or g nalT et dMapper, opt m zedT et dMapper, term DMapper);

    LOG. nfo("Rewr e and map  ds  n facet count ng array.");
    AbstractFacetCount ngArray facetCount ngArray = s ce.getFacetCount ngArray().rewr eAndMap Ds(
        term DMapper, or g nalT et dMapper, opt m zedT et dMapper);

    Map<Str ng, FacetLabelProv der> facetLabelProv ders =
        FacetUt l.getFacetLabelProv ders(s ce.getSc ma(), targetMap);

    LOG. nfo("Opt m ze doc values manager.");
    DocValuesManager opt m zedDocValuesManager =
        s ce.getDocValuesManager().opt m ze(or g nalT et dMapper, opt m zedT et dMapper);

    LOG. nfo("Opt m ze deleted docs.");
    DeletedDocs opt m zedDeletedDocs =
        s ce.getDeletedDocs().opt m ze(or g nalT et dMapper, opt m zedT et dMapper);

    f nal boolean  sOpt m zed = true;
    return new Earlyb rdRealt   ndexSeg ntData(
        s ce.getMaxSeg ntS ze(),
        s ce.getT  Sl ce D(),
        s ce.getSc ma(),
         sOpt m zed,
        opt m zedT et dMapper.getNextDoc D( nteger.M N_VALUE),
        targetMap,
        facetCount ngArray,
        opt m zedDocValuesManager,
        facetLabelProv ders,
        s ce.getFacet DMap(),
        opt m zedDeletedDocs,
        opt m zedT et dMapper,
        opt m zedT  Mapper,
        s ce.get ndexExtens onsData());
  }

  pr vate stat c vo d opt m ze nverted ndexes(
      Earlyb rdRealt   ndexSeg ntData s ce,
      ConcurrentHashMap<Str ng,  nverted ndex> targetMap,
      Doc DToT et DMapper or g nalT et dMapper,
      Doc DToT et DMapper opt m zedT et dMapper,
      Map< nteger,  nt[]> term DMapper
  ) throws  OExcept on {
    for (Map.Entry<Str ng,  nverted ndex> entry : s ce.getPerF eldMap().entrySet()) {
      Str ng f eldNa  = entry.getKey();
      Precond  ons.c ckState(entry.getValue()  nstanceof  nvertedRealt   ndex);
       nvertedRealt   ndex s ce ndex = ( nvertedRealt   ndex) entry.getValue();
      Earlyb rdF eldType f eldType = s ce.getSc ma().getF eld nfo(f eldNa ).getF eldType();

       nverted ndex new ndex;
       f (f eldType.beco s mmutable() && s ce ndex.getNumTerms() > 0) {
        Sc ma.F eld nfo facetF eld = s ce.getSc ma().getFacetF eldByF eldNa (f eldNa );

        new ndex = new Opt m zed mory ndex(
            f eldType,
            f eldNa ,
            s ce ndex,
            term DMapper,
            s ce.getFacet DMap().getFacetF eld(facetF eld),
            or g nalT et dMapper,
            opt m zedT et dMapper);
      } else {
        new ndex = opt m zeMutable ndex(
            f eldType,
            f eldNa ,
            s ce ndex,
            or g nalT et dMapper,
            opt m zedT et dMapper);
      }

      targetMap.put(f eldNa , new ndex);
    }
  }

  /**
   * Opt m ze a mutable  ndex.
   */
  pr vate stat c  nverted ndex opt m zeMutable ndex(
      Earlyb rdF eldType f eldType,
      Str ng f eldNa ,
       nvertedRealt   ndex or g nal ndex,
      Doc DToT et DMapper or g nalMapper,
      Doc DToT et DMapper opt m zedMapper
  ) throws  OExcept on {
    Precond  ons.c ckState(!f eldType. sStorePerPos  onPayloads());
    TermsEnum allTerms = or g nal ndex.createTermsEnum(or g nal ndex.getMaxPubl s dPo nter());

     nt numTerms = or g nal ndex.getNumTerms();

     nvertedRealt   ndex  ndex = new  nvertedRealt   ndex(
        f eldType,
        TermPo nterEncod ng.DEFAULT_ENCOD NG,
        f eldNa );
     ndex.setNumDocs(or g nal ndex.getNumDocs());

    for ( nt term D = 0; term D < numTerms; term D++) {
      allTerms.seekExact(term D);
      Post ngsEnum post ngsEnum = new Opt m z ngPost ngsEnumWrapper(
          allTerms.post ngs(null), or g nalMapper, opt m zedMapper);

      BytesRef termPayload = or g nal ndex.getLabelAccessor().getTermPayload(term D);
      copyPost ngL st( ndex, post ngsEnum, term D, allTerms.term(), termPayload);
    }
    return  ndex;
  }


  /**
   * Cop es t  g ven post ng l st  nto t se post ng l sts.
   *
   * @param post ngsEnum enu rator of t  post ng l st that needs to be cop ed
   */
  pr vate stat c vo d copyPost ngL st(
       nvertedRealt   ndex  ndex,
      Post ngsEnum post ngsEnum,
       nt term D,
      BytesRef term,
      BytesRef termPayload
  ) throws  OExcept on {
     nt doc d;
    wh le ((doc d = post ngsEnum.nextDoc()) != Doc dSet erator.NO_MORE_DOCS) {
       ndex. ncre ntSumTermDocFreq();
      for ( nt   = 0;   < post ngsEnum.freq();  ++) {
         ndex. ncre ntSumTotalTermFreq();
         nt pos  on = post ngsEnum.nextPos  on();
         nt newTerm D =  nvertedRealt   ndexWr er. ndexTerm(
             ndex,
            term,
            doc d,
            pos  on,
            termPayload,
            null, //   know that f elds that rema n mutable never have a post ng payload.
            TermPo nterEncod ng.DEFAULT_ENCOD NG);

        //   term lookups are very slow, so   cac  term d ct onar es for so  f elds across many
        // seg nts, so   must keep t  term  Ds t  sa  wh le remapp ng.
        Precond  ons.c ckState(newTerm D == term D);
      }
    }
  }
}
