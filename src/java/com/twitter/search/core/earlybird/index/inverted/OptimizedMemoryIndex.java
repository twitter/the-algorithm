package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java. o. OExcept on;
 mport java.ut l.Comparator;
 mport java.ut l.Map;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene. ndex.Post ngsEnum;
 mport org.apac .lucene. ndex.Terms;
 mport org.apac .lucene. ndex.TermsEnum;
 mport org.apac .lucene.search.Doc dSet erator;
 mport org.apac .lucene.ut l.BytesRef;
 mport org.apac .lucene.ut l.packed.Packed nts;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.sc ma.base.Earlyb rdF eldType;
 mport com.tw ter.search.common.ut l.hash.BDZAlgor hm;
 mport com.tw ter.search.common.ut l.hash.BDZAlgor hm.MPHFNotFoundExcept on;
 mport com.tw ter.search.common.ut l.hash.KeysS ce;
 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;
 mport com.tw ter.search.core.earlyb rd.facets.Facet DMap.FacetF eld;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;

publ c class Opt m zed mory ndex extends  nverted ndex  mple nts Flushable {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Opt m zed mory ndex.class);
  pr vate stat c f nal Comparator<BytesRef> BYTES_REF_COMPARATOR = Comparator.naturalOrder();

  pr vate stat c f nal SearchCounter MPH_NOT_FOUND_COUNT =
      SearchCounter.export("tw ter_opt m zed_ ndex_mph_not_found_count");

  pr vate f nal Packed nts.Reader numPost ngs;
  pr vate f nal Packed nts.Reader post ngL stPo nters;
  pr vate f nal Packed nts.Reader offens veCounters;
  pr vate f nal Mult Post ngL sts post ngL sts;

  pr vate f nal TermD ct onary d ct onary;

  pr vate f nal  nt numDocs;
  pr vate f nal  nt sumTotalTermFreq;
  pr vate f nal  nt sumTermDocFreq;

  pr vate Opt m zed mory ndex(Earlyb rdF eldType f eldType,
                                nt numDocs,
                                nt sumTermDocFreq,
                                nt sumTotalTermFreq,
                               Packed nts.Reader numPost ngs,
                               Packed nts.Reader post ngL stPo nters,
                               Packed nts.Reader offens veCounters,
                               Mult Post ngL sts post ngL sts,
                               TermD ct onary d ct onary) {
    super(f eldType);
    t .numDocs = numDocs;
    t .sumTermDocFreq = sumTermDocFreq;
    t .sumTotalTermFreq = sumTotalTermFreq;
    t .numPost ngs = numPost ngs;
    t .post ngL stPo nters = post ngL stPo nters;
    t .offens veCounters = offens veCounters;
    t .post ngL sts = post ngL sts;
    t .d ct onary = d ct onary;
  }

  publ c Opt m zed mory ndex(
      Earlyb rdF eldType f eldType,
      Str ng f eld,
       nvertedRealt   ndex s ce,
      Map< nteger,  nt[]> term DMapper,
      FacetF eld facetF eld,
      Doc DToT et DMapper or g nalT et dMapper,
      Doc DToT et DMapper opt m zedT et dMapper) throws  OExcept on {
    super(f eldType);

    numDocs = s ce.getNumDocs();
    sumTermDocFreq = s ce.getSumTermDocFreq();
    sumTotalTermFreq = s ce.getSumTotalTermFreq();

    Precond  ons.c ckNotNull(or g nalT et dMapper, "T  seg nt must have a t et  D mapper.");
    Precond  ons.c ckNotNull(opt m zedT et dMapper,
                               "T  opt m zed t et  D mapper cannot be null.");

    //   rely on t  fact that new terms always have a greater term  D.    gnore all terms that
    // are equal to or greater than numTerms, as t y may be  ncompletely appl ed.  f new terms are
    // added wh le opt m z ng, t y w ll be re-added w n   re-apply updates.
    f nal KeysS ce terms erator = s ce.getKeysS ce();
     nt numTerms = terms erator.getNumberOfKeys();
     nt maxPubl s dPo nter = s ce.getMaxPubl s dPo nter();

     nt[] tempPost ngL stPo nters = new  nt[numTerms];

    BDZAlgor hm termsHashFunct on = null;

    f nal boolean supportTermTextLookup = facetF eld != null || f eldType. sSupportTermTextLookup();
     f (supportTermTextLookup) {
      try {
        termsHashFunct on = new BDZAlgor hm(terms erator);
      } catch (MPHFNotFoundExcept on e) {
        //   couldn't f nd a mphf for t  f eld
        // no problem, t  can happen for very small f elds
        // - just use t  fst  n that case
        LOG.warn("Unable to bu ld MPH for f eld: {}", f eld);
        MPH_NOT_FOUND_COUNT. ncre nt();
      }
    }

    // Make sure to only call t  expens ve computeNumPost ngs() once.
     nt[] numPost ngsS ce = computeNumPost ngs(s ce, numTerms, maxPubl s dPo nter);

    // T  BDZ Algor hm returns a funct on from bytesref to term  D. Ho ver, t se term  Ds are
    // d fferent than t  or g nal term  Ds ( 's a hash funct on, not a hash _table_), so   have
    // to remap t  term  Ds to match t  ones generated by BDZ.   track that us ng t  term DMap.
     nt[] term DMap = null;

     f (termsHashFunct on != null) {
      terms erator.rew nd();
      term DMap = BDZAlgor hm.create dMap(termsHashFunct on, terms erator);
       f (facetF eld != null) {
        term DMapper.put(facetF eld.getFacet d(), term DMap);
      }

      Packed nts.Reader termPo nters = getPacked nts(s ce.getTermPo nters(), term DMap);
      t .numPost ngs = getPacked nts(numPost ngsS ce, term DMap);
      t .offens veCounters = s ce.getOffens veCounters() == null ? null
              : getPacked nts(s ce.getOffens veCounters(), term DMap);

      t .d ct onary = new MPHTermD ct onary(
          numTerms,
          termsHashFunct on,
          termPo nters,
          s ce.getTermPool(),
          TermPo nterEncod ng.DEFAULT_ENCOD NG);
    } else {
      t .d ct onary = FSTTermD ct onary.bu ldFST(
          s ce.getTermPool(),
          s ce.getTermPo nters(),
          numTerms,
          BYTES_REF_COMPARATOR,
          supportTermTextLookup,
          TermPo nterEncod ng.DEFAULT_ENCOD NG);

      t .numPost ngs = getPacked nts(numPost ngsS ce);
      t .offens veCounters = s ce.getOffens veCounters() == null ? null
              : getPacked nts(s ce.getOffens veCounters());
    }

    TermsEnum allTerms = s ce.createTermsEnum(maxPubl s dPo nter);

    t .post ngL sts = new Mult Post ngL sts(
        !f eldType.hasPos  ons(),
        numPost ngsS ce,
        s ce.getMaxPos  on());

    for ( nt term D = 0; term D < numTerms; term D++) {
      allTerms.seekExact(term D);
      Post ngsEnum post ngsEnum = new Opt m z ngPost ngsEnumWrapper(
          allTerms.post ngs(null), or g nalT et dMapper, opt m zedT et dMapper);
       nt mappedTerm D = term DMap != null ? term DMap[term D] : term D;
      tempPost ngL stPo nters[mappedTerm D] =
          post ngL sts.copyPost ngL st(post ngsEnum, numPost ngsS ce[term D]);
    }

    t .post ngL stPo nters = getPacked nts(tempPost ngL stPo nters);
  }

  pr vate stat c  nt[] map( nt[] s ce,  nt[] map) {
     nt[] target = new  nt[map.length];
    for ( nt   = 0;   < map.length;  ++) {
      target[map[ ]] = s ce[ ];
    }
    return target;
  }

  stat c Packed nts.Reader getPacked nts( nt[] values) {
    return getPacked nts(values, null);
  }

  pr vate stat c Packed nts.Reader getPacked nts( nt[] values,  nt[] map) {
     nt[] mappedValues = values;
     f (map != null) {
      mappedValues = map(mappedValues, map);
    }

    // f rst determ ne max value
    long maxValue = Long.M N_VALUE;
    for ( nt value : mappedValues) {
       f (value > maxValue) {
        maxValue = value;
      }
    }

    Packed nts.Mutable packed =
            Packed nts.getMutable(mappedValues.length, Packed nts.b sRequ red(maxValue),
                    Packed nts.DEFAULT);
    for ( nt   = 0;   < mappedValues.length;  ++) {
      packed.set( , mappedValues[ ]);
    }

    return packed;
  }

  /**
   * Returns per-term array conta n ng t  number of post ng  n t   ndex for each term.
   * T  call  s extre ly slow.
   */
  pr vate stat c  nt[] computeNumPost ngs(
       nvertedRealt   ndex s ce,
       nt numTerms,
       nt maxPubl s dPo nter
  ) throws  OExcept on {
     nt[] numPost ngs = new  nt[numTerms];
    TermsEnum allTerms = s ce.createTermsEnum(maxPubl s dPo nter);

    for ( nt term D = 0; term D < numTerms; term D++) {
      allTerms.seekExact(term D);
      Post ngsEnum docsEnum = allTerms.post ngs(null);
      wh le (docsEnum.nextDoc() != Doc dSet erator.NO_MORE_DOCS) {
        numPost ngs[term D] += docsEnum.freq();
      }
    }

    return numPost ngs;
  }

  @Overr de
  publ c  nt getNumDocs() {
    return numDocs;
  }

  @Overr de
  publ c  nt getSumTotalTermFreq() {
    return sumTotalTermFreq;
  }

  @Overr de
  publ c  nt getSumTermDocFreq() {
    return sumTermDocFreq;
  }

  publ c Opt m zedPost ngL sts getPost ngL sts() {
    Precond  ons.c ckState(hasPost ngL sts());
    return post ngL sts;
  }

   nt getPost ngL stPo nter( nt term D) {
    Precond  ons.c ckState(hasPost ngL sts());
    return ( nt) post ngL stPo nters.get(term D);
  }

   nt getNumPost ngs( nt term D) {
    Precond  ons.c ckState(hasPost ngL sts());
    return ( nt) numPost ngs.get(term D);
  }

  publ c boolean getTerm( nt term D, BytesRef text, BytesRef termPayload) {
    return d ct onary.getTerm(term D, text, termPayload);
  }

  @Overr de
  publ c FacetLabelAccessor getLabelAccessor() {
    return new FacetLabelAccessor() {
      @Overr de
      protected boolean seek(long term D) {
         f (term D != Earlyb rd ndexSeg ntAtom cReader.TERM_NOT_FOUND) {
          hasTermPayload = getTerm(( nt) term D, termRef, termPayload);
          offens veCount = offens veCounters != null
                  ? ( nt) offens veCounters.get(( nt) term D) : 0;
          return true;
        } else {
          return false;
        }
      }
    };
  }

  @Overr de
  publ c Terms createTerms( nt maxPubl s dPo nter) {
    return new Opt m zed ndexTerms(t );
  }

  @Overr de
  publ c TermsEnum createTermsEnum( nt maxPubl s dPo nter) {
    return d ct onary.createTermsEnum(t );
  }

  @Overr de
  publ c  nt lookupTerm(BytesRef term) throws  OExcept on {
    return d ct onary.lookupTerm(term);
  }

  @Overr de
  publ c  nt getLargestDoc DForTerm( nt term D) throws  OExcept on {
    Precond  ons.c ckState(hasPost ngL sts());
     f (term D == Earlyb rd ndexSeg ntAtom cReader.TERM_NOT_FOUND) {
      return Earlyb rd ndexSeg ntAtom cReader.TERM_NOT_FOUND;
    } else {
      return post ngL sts.getLargestDoc D(( nt) post ngL stPo nters.get(term D),
              ( nt) numPost ngs.get(term D));
    }
  }

  @Overr de
  publ c  nt getDF( nt term D) {
    return ( nt) numPost ngs.get(term D);
  }

  @Overr de
  publ c  nt getNumTerms() {
    return d ct onary.getNumTerms();
  }

  @Overr de
  publ c vo d getTerm( nt term D, BytesRef text) {
    d ct onary.getTerm(term D, text, null);
  }

  @V s bleForTest ng TermD ct onary getTermD ct onary() {
    return d ct onary;
  }

  @Overr de
  publ c FlushHandler getFlushHandler() {
    return new FlushHandler(t );
  }

  publ c boolean hasPost ngL sts() {
    return post ngL stPo nters != null
        && post ngL sts != null
        && numPost ngs != null;
  }

  @V s bleForTest ng
  Opt m zedPost ngL sts getOpt m zedPost ngL sts() {
    return post ngL sts;
  }

  publ c stat c class FlushHandler extends Flushable.Handler<Opt m zed mory ndex> {
    pr vate stat c f nal Str ng NUM_DOCS_PROP_NAME = "numDocs";
    pr vate stat c f nal Str ng SUM_TOTAL_TERM_FREQ_PROP_NAME = "sumTotalTermFreq";
    pr vate stat c f nal Str ng SUM_TERM_DOC_FREQ_PROP_NAME = "sumTermDocFreq";
    pr vate stat c f nal Str ng USE_M N_PERFECT_HASH_PROP_NAME = "useM n mumPerfectHashFunct on";
    pr vate stat c f nal Str ng SK P_POST NG_L ST_PROP_NAME = "sk pPost ngL sts";
    pr vate stat c f nal Str ng HAS_OFFENS VE_COUNTERS_PROP_NAME = "hasOffens veCounters";
    publ c stat c f nal Str ng  S_OPT M ZED_PROP_NAME = " sOpt m zed";

    pr vate f nal Earlyb rdF eldType f eldType;

    publ c FlushHandler(Earlyb rdF eldType f eldType) {
      super();
      t .f eldType = f eldType;
    }

    publ c FlushHandler(Opt m zed mory ndex objectToFlush) {
      super(objectToFlush);
      f eldType = objectToFlush.f eldType;
    }

    @Overr de
    protected vo d doFlush(Flush nfo flush nfo, DataSer al zer out) throws  OExcept on {
      long startT   = getClock().nowM ll s();
      Opt m zed mory ndex objectToFlush = getObjectToFlush();
      boolean useHashFunct on = objectToFlush.d ct onary  nstanceof MPHTermD ct onary;
      boolean sk pPost ngL sts = !objectToFlush.hasPost ngL sts();

      flush nfo.add ntProperty(NUM_DOCS_PROP_NAME, objectToFlush.numDocs);
      flush nfo.add ntProperty(SUM_TERM_DOC_FREQ_PROP_NAME, objectToFlush.sumTermDocFreq);
      flush nfo.add ntProperty(SUM_TOTAL_TERM_FREQ_PROP_NAME, objectToFlush.sumTotalTermFreq);
      flush nfo.addBooleanProperty(USE_M N_PERFECT_HASH_PROP_NAME, useHashFunct on);
      flush nfo.addBooleanProperty(SK P_POST NG_L ST_PROP_NAME, sk pPost ngL sts);
      flush nfo.addBooleanProperty(HAS_OFFENS VE_COUNTERS_PROP_NAME,
          objectToFlush.offens veCounters != null);
      flush nfo.addBooleanProperty( S_OPT M ZED_PROP_NAME, true);

       f (!sk pPost ngL sts) {
        out.wr ePacked nts(objectToFlush.post ngL stPo nters);
        out.wr ePacked nts(objectToFlush.numPost ngs);
      }
       f (objectToFlush.offens veCounters != null) {
        out.wr ePacked nts(objectToFlush.offens veCounters);
      }

       f (!sk pPost ngL sts) {
        objectToFlush.post ngL sts.getFlushHandler().flush(
            flush nfo.newSubPropert es("post ngL sts"), out);
      }
      objectToFlush.d ct onary.getFlushHandler().flush(flush nfo.newSubPropert es("d ct onary"),
              out);
      getFlushT  rStats().t  r ncre nt(getClock().nowM ll s() - startT  );
    }

    @Overr de
    protected Opt m zed mory ndex doLoad(
        Flush nfo flush nfo, DataDeser al zer  n) throws  OExcept on {
      long startT   = getClock().nowM ll s();
      boolean useHashFunct on = flush nfo.getBooleanProperty(USE_M N_PERFECT_HASH_PROP_NAME);
      boolean sk pPost ngL sts = flush nfo.getBooleanProperty(SK P_POST NG_L ST_PROP_NAME);

      Packed nts.Reader post ngL stPo nters = sk pPost ngL sts ? null :  n.readPacked nts();
      Packed nts.Reader numPost ngs = sk pPost ngL sts ? null :  n.readPacked nts();
      Packed nts.Reader offens veCounters =
              flush nfo.getBooleanProperty(HAS_OFFENS VE_COUNTERS_PROP_NAME)
                  ?  n.readPacked nts() : null;

      Mult Post ngL sts post ngL sts =  sk pPost ngL sts ? null
              : (new Mult Post ngL sts.FlushHandler())
                      .load(flush nfo.getSubPropert es("post ngL sts"),  n);

      TermD ct onary d ct onary;
       f (useHashFunct on) {
        d ct onary = (new MPHTermD ct onary.FlushHandler(TermPo nterEncod ng.DEFAULT_ENCOD NG))
            .load(flush nfo.getSubPropert es("d ct onary"),  n);
      } else {
        d ct onary = (new FSTTermD ct onary.FlushHandler(TermPo nterEncod ng.DEFAULT_ENCOD NG))
            .load(flush nfo.getSubPropert es("d ct onary"),  n);
      }
      getLoadT  rStats().t  r ncre nt(getClock().nowM ll s() - startT  );

      return new Opt m zed mory ndex(f eldType,
                                      flush nfo.get ntProperty(NUM_DOCS_PROP_NAME),
                                      flush nfo.get ntProperty(SUM_TERM_DOC_FREQ_PROP_NAME),
                                      flush nfo.get ntProperty(SUM_TOTAL_TERM_FREQ_PROP_NAME),
                                      numPost ngs,
                                      post ngL stPo nters,
                                      offens veCounters,
                                      post ngL sts,
                                      d ct onary);
    }
  }
}
