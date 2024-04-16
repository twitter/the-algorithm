package com.tw ter.search.common.sc ma;

 mport java. o. OExcept on;
 mport java. o.ObjectOutputStream;
 mport java.ut l.Collect on;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Set;
 mport java.ut l.SortedMap;
 mport java.ut l.TreeMap;
 mport java.ut l.concurrent.atom c.Atom cLong;
 mport javax.annotat on.Nullable;
 mport javax.annotat on.concurrent. mmutable;
 mport javax.annotat on.concurrent.ThreadSafe;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.base.Pred cate;
 mport com.google.common.collect. mmutableCollect on;
 mport com.google.common.collect. mmutableMap;
 mport com.google.common.collect. mmutableSet;
 mport com.google.common.collect. mmutableSortedMap;
 mport com.google.common.collect.L sts;
 mport com.google.common.collect.Maps;
 mport com.google.common.collect.Sets;

 mport org.apac .lucene.analys s.Analyzer;
 mport org.apac .lucene.facet.FacetsConf g;
 mport org.apac .lucene. ndex.DocValuesType;
 mport org.apac .lucene. ndex.F eld nfos;
 mport org.apac .lucene. ndex. ndexOpt ons;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.collect ons.Pa r;
 mport com.tw ter.common.text.ut l.TokenStreamSer al zer;
 mport com.tw ter.search.common.features.ExternalT etFeature;
 mport com.tw ter.search.common.features.SearchResultFeature;
 mport com.tw ter.search.common.features.thr ft.Thr ftSearchFeatureSc ma;
 mport com.tw ter.search.common.features.thr ft.Thr ftSearchFeatureSc maEntry;
 mport com.tw ter.search.common.features.thr ft.Thr ftSearchFeatureSc maSpec f er;
 mport com.tw ter.search.common.features.thr ft.Thr ftSearchFeatureType;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchLongGauge;
 mport com.tw ter.search.common.sc ma.base.Earlyb rdF eldType;
 mport com.tw ter.search.common.sc ma.base.FeatureConf gurat on;
 mport com.tw ter.search.common.sc ma.base.F eld  ghtDefault;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.base. ndexedNu r cF eldSett ngs;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftAnalyzer;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftCSFF eldSett ngs;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftCSFType;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftCSFV ewSett ngs;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftFacetF eldSett ngs;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftF eldConf gurat on;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftF eldSett ngs;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndexedF eldSett ngs;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftSc ma;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftSearchF eldSett ngs;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftTokenStreamSer al zer;

/**
 * A sc ma  nstance that does not change at run t  .
 */
@ mmutable @ThreadSafe
publ c class  mmutableSc ma  mple nts  mmutableSc ma nterface {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger( mmutableSc ma.class);
  pr vate stat c f nal  mmutableSet<Thr ftCSFType> CAN_FACET_ON_CSF_TYPES =
       mmutableSet.<Thr ftCSFType>bu lder()
          .add(Thr ftCSFType.BYTE)
          .add(Thr ftCSFType. NT)
          .add(Thr ftCSFType.LONG)
          .bu ld();

  pr vate stat c f nal SearchCounter FEATURES_EX STED_ N_OLD_SCHEMA =
      SearchCounter.export("features_ex sted_ n_old_sc ma");

  // Currently    ndex uses 4 b s to store t  facet f eld  d.
  publ c stat c f nal  nt MAX_FACET_F ELD_ D = 15;

  publ c stat c f nal Str ng HF_TERM_PA RS_F ELD = "hf_term_pa rs";
  publ c stat c f nal Str ng HF_PHRASE_PA RS_F ELD = "hf_phrase_pa rs";

  pr vate f nal  mmutableMap< nteger, F eld nfo> f eldSett ngsMapBy d;
  pr vate f nal  mmutableMap<Str ng, F eld nfo> f eldSett ngsMapByNa ;
  pr vate f nal  mmutableMap<Str ng, FeatureConf gurat on> featureConf gMapByNa ;
  pr vate f nal  mmutableMap< nteger, FeatureConf gurat on> featureConf gMapBy d;

  @Nullable
  pr vate f nal Thr ftAnalyzer defaultAnalyzer;
  pr vate f nal AnalyzerFactory analyzerFactory;

  pr vate f nal  mmutableMap<Str ng, F eld  ghtDefault> f eld  ghtMap;
  pr vate f nal Map<Str ng, F eld nfo> facetNa ToF eldMap = Maps.newHashMap();
  pr vate f nal  nt numFacetF elds;
  pr vate f nal  mmutableSet<F eld nfo> csfFacetF elds;

  // T   s t  search result feature sc ma -   has t  def n  on for all t  column str de
  // v ew f elds.
  pr vate f nal Thr ftSearchFeatureSc ma searchFeatureSc ma;

  pr vate f nal  nt majorVers onNumber;
  pr vate f nal  nt m norVers onNumber;
  pr vate f nal Str ng vers onDesc;
  pr vate f nal boolean  sVers onOff c al;

  /**
   * Construct a Sc ma  nstance w h t  g ven Thr ftSc ma and AnalyzerFactory.
   */
  publ c  mmutableSc ma(Thr ftSc ma thr ftSc ma,
                         AnalyzerFactory analyzerFactory,
                         Str ng featureSc maVers onPref x) throws Sc maVal dat onExcept on {
    Pa r< nteger, Str ng> vers onPa r = parseVers onStr ng(thr ftSc ma.getVers on());
    t .majorVers onNumber = thr ftSc ma.getMajorVers onNumber();
    t .m norVers onNumber = thr ftSc ma.getM norVers onNumber();
    t .vers onDesc = vers onPa r.getSecond();
    t . sVers onOff c al = thr ftSc ma. sVers on sOff c al();

    t .analyzerFactory = analyzerFactory;

    Map< nteger, F eld nfo> tmpMap = Maps.newL nkedHashMap();
    Set<F eld nfo> tmpSet = Sets.newHashSet();

     f (thr ftSc ma. sSetDefaultAnalyzer()) {
      t .defaultAnalyzer = thr ftSc ma.getDefaultAnalyzer().deepCopy();
    } else {
      t .defaultAnalyzer = null;
    }

    Map< nteger, Thr ftF eldConf gurat on> conf gs = thr ftSc ma.getF eldConf gs();

    // Collect all t  CSF V ews, so that   can later ver fy that t y are appropr ately
    // conf gured once  've processed all t  ot r f eld sett ngs.
    Map< nteger, Thr ftF eldConf gurat on> csfV ewF elds = Maps.newHashMap();
    boolean requ resHfPa rF elds = false;
    boolean hasHfTermPa rF eld = false;
    boolean hasHfPhrasePa rF eld = false;
     nt numFacets = 0;
    for (Map.Entry< nteger, Thr ftF eldConf gurat on> entry : conf gs.entrySet()) {
       nt f eld d = entry.getKey();

       f (tmpMap.conta nsKey(f eld d)) {
        throw new Sc maVal dat onExcept on("Dupl cate f eld  d " + f eld d);
      }

      Thr ftF eldConf gurat on conf g = entry.getValue();
      F eld nfo f eld nfo = parseThr ftF eldSett ngs(f eld d, conf g, csfV ewF elds);
      val date(f eld nfo);
       f (f eld nfo.getF eldType(). sFacetF eld()) {
         f (numFacets > MAX_FACET_F ELD_ D) {
          throw new Sc maVal dat onExcept on(
              "Max mum supported facet f eld  D  s:  " + MAX_FACET_F ELD_ D);
        }
        numFacets++;
        facetNa ToF eldMap.put(f eld nfo.getF eldType().getFacetNa (), f eld nfo);

         f (f eld nfo.getF eldType(). sUseCSFForFacetCount ng()) {
          tmpSet.add(f eld nfo);
        }
      }

      tmpMap.put(f eld d, f eld nfo);

       f (f eld nfo.getF eldType(). s ndexHFTermPa rs()) {
        requ resHfPa rF elds = true;
      }
       f (f eld nfo.getNa ().equals(HF_TERM_PA RS_F ELD)) {
        hasHfTermPa rF eld = true;
      }
       f (f eld nfo.getNa ().equals(HF_PHRASE_PA RS_F ELD)) {
        hasHfPhrasePa rF eld = true;
      }
    }

    t .numFacetF elds = numFacets;
    t .csfFacetF elds =  mmutableSet.copyOf(tmpSet);

    //  f any f eld requ res h gh frequency term/phrase pa r f elds, make sure t y ex st
     f (requ resHfPa rF elds) {
       f (!hasHfTermPa rF eld || !hasHfPhrasePa rF eld) {
        throw new Sc maVal dat onExcept on(
            "H gh frequency term/phrase pa r f elds do not ex st  n t  sc ma.");
      }
    }

    t .f eldSett ngsMapBy d =  mmutableMap.copyOf(tmpMap);

    Pa r< mmutableMap<Str ng, FeatureConf gurat on>,  mmutableMap< nteger, FeatureConf gurat on>>
        featureConf gMapPa r = bu ldFeatureMaps(csfV ewF elds);
    t .featureConf gMapByNa  = featureConf gMapPa r.getF rst();
    t .featureConf gMapBy d = featureConf gMapPa r.getSecond();

    for (Thr ftF eldConf gurat on csfV ewF eld : csfV ewF elds.values()) {
      Sc maBu lder.ver fyCSFV ewSett ngs(conf gs, csfV ewF eld);
    }

     mmutableMap.Bu lder<Str ng, F eld nfo> bu lder =  mmutableMap.bu lder();

    for (F eld nfo  nfo : f eldSett ngsMapBy d.values()) {
       nfo.getF eldType().freeze();
      bu lder.put( nfo.getNa (),  nfo);
    }
    t .f eldSett ngsMapByNa  = bu lder.bu ld();

     mmutableMap.Bu lder<Str ng, F eld  ghtDefault> f eld  ghtMapBu lder =  mmutableMap.bu lder();

    for (F eld nfo f  : getF eld nfos()) {
      // CSF f elds are not searchable. All ot r f elds are.
       f (f .getF eldType(). s ndexedF eld()) {
        f eld  ghtMapBu lder.put(
            f .getNa (),
            new F eld  ghtDefault(
                f .getF eldType(). sTextSearchableByDefault(),
                f .getF eldType().getTextSearchableF eld  ght()));
      }
    }

    t .f eld  ghtMap = f eld  ghtMapBu lder.bu ld();
    // Create features w h extra Earlyb rd der ved f elds, extra f elds won't change t  vers on
    // but t y do change t  c cksum.
    t .searchFeatureSc ma = createSearchResultFeatureSc ma(
        featureSc maVers onPref x, f eldSett ngsMapByNa , featureConf gMapByNa );
  }

  /**
   * Add a set of features to a sc ma  f t y don't ex st yet, and update t  sc ma c cksum.
   *  f t re's confl ct, Runt  Except on w ll be thrown.
   * Old map won't be touc d, a new map w ll be returned w ll old and new data comb ned.
   */
  publ c stat c Map< nteger, Thr ftSearchFeatureSc maEntry> appendToFeatureSc ma(
      Map< nteger, Thr ftSearchFeatureSc maEntry> oldEntryMap,
      Set<? extends SearchResultFeature> features) throws Sc maVal dat onExcept on {
     f (oldEntryMap == null) {
      throw new Sc maVal dat onExcept on(
          "Cannot append features to sc ma, t  entryMap  s null");
    }
    // make a copy of t  ex st ng map
     mmutableMap.Bu lder< nteger, Thr ftSearchFeatureSc maEntry> bu lder =
         mmutableSortedMap.< nteger, Thr ftSearchFeatureSc maEntry>naturalOrder()
            .putAll(oldEntryMap);

    for (SearchResultFeature feature : features) {
       f (oldEntryMap.conta nsKey(feature.get d())) {
        FEATURES_EX STED_ N_OLD_SCHEMA. ncre nt();
      } else {
        bu lder.put(feature.get d(), new Thr ftSearchFeatureSc maEntry()
            .setFeatureNa (feature.getNa ())
            .setFeatureType(feature.getType()));
      }
    }
    return bu lder.bu ld();
  }

  /**
   * Append external features to create a new sc ma.
   * @param oldSc ma T  old sc ma to bu ld on top of
   * @param features a l st of features to be appended to t  sc ma
   * @param vers onSuff x t  vers on suff x,  f not-null,   w ll be attac d to t  end of
   * or g nal sc ma's vers on.
   * @return A new sc ma object w h t  appended f elds
   * @throws Sc maVal dat onExcept on thrown w n t  c cksum cannot be computed
   */
  publ c stat c Thr ftSearchFeatureSc ma appendToCreateNewFeatureSc ma(
      Thr ftSearchFeatureSc ma oldSc ma,
      Set<ExternalT etFeature> features,
      @Nullable Str ng vers onSuff x) throws Sc maVal dat onExcept on {

    Thr ftSearchFeatureSc ma newSc ma = new Thr ftSearchFeatureSc ma();
    // copy over all t  entr es plus t  new ones
    newSc ma.setEntr es(appendToFeatureSc ma(oldSc ma.getEntr es(), features));

    Thr ftSearchFeatureSc maSpec f er spec = new Thr ftSearchFeatureSc maSpec f er();
    // t  vers on  s d rectly  n r ed or w h a suff x
    Precond  ons.c ckArgu nt(vers onSuff x == null || !vers onSuff x. sEmpty());
    spec.setVers on(vers onSuff x == null
        ? oldSc ma.getSc maSpec f er().getVers on()
        : oldSc ma.getSc maSpec f er().getVers on() + vers onSuff x);
    spec.setC cksum(getC cksum(newSc ma.getEntr es()));
    newSc ma.setSc maSpec f er(spec);
    return newSc ma;
  }

  @Overr de
  publ c F eld nfos getLuceneF eld nfos(Pred cate<Str ng> acceptedF elds) {
    L st<org.apac .lucene. ndex.F eld nfo> acceptedF eld nfos = L sts.newArrayL st();
    for (F eld nfo f  : getF eld nfos()) {
       f (acceptedF elds == null || acceptedF elds.apply(f .getNa ())) {
        acceptedF eld nfos.add(convert(f .getNa (), f .getF eld d(), f .getF eldType()));
      }
    }
    return new F eld nfos(acceptedF eld nfos.toArray(
        new org.apac .lucene. ndex.F eld nfo[acceptedF eld nfos.s ze()]));
  }

  pr vate F eld nfo parseThr ftF eldSett ngs( nt f eld d, Thr ftF eldConf gurat on f eldConf g,
                                             Map< nteger, Thr ftF eldConf gurat on> csfV ewF elds)
      throws Sc maVal dat onExcept on {
    F eld nfo f eld nfo
        = new F eld nfo(f eld d, f eldConf g.getF eldNa (), new Earlyb rdF eldType());
    Thr ftF eldSett ngs f eldSett ngs = f eldConf g.getSett ngs();


    boolean sett ngFound = false;

     f (f eldSett ngs. sSet ndexedF eldSett ngs()) {
       f (f eldSett ngs. sSetCsfF eldSett ngs() || f eldSett ngs. sSetCsfV ewSett ngs()) {
        throw new Sc maVal dat onExcept on("Thr ftF eldSett ngs: Only one of "
            + "' ndexedF eldSett ngs', 'csfF eldSett ngs', 'csfV ewSett ngs' can be set.");
      }

      apply ndexedF eldSett ngs(f eld nfo, f eldSett ngs.get ndexedF eldSett ngs());
      sett ngFound = true;
    }

     f (f eldSett ngs. sSetCsfF eldSett ngs()) {
       f (f eldSett ngs. sSet ndexedF eldSett ngs() || f eldSett ngs. sSetCsfV ewSett ngs()) {
        throw new Sc maVal dat onExcept on("Thr ftF eldSett ngs: Only one of "
            + "' ndexedF eldSett ngs', 'csfF eldSett ngs', 'csfV ewSett ngs' can be set.");
      }

      applyCsfF eldSett ngs(f eld nfo, f eldSett ngs.getCsfF eldSett ngs());
      sett ngFound = true;
    }

     f (f eldSett ngs. sSetFacetF eldSett ngs()) {
       f (!f eldSett ngs. sSet ndexedF eldSett ngs() && !(f eldSett ngs. sSetCsfF eldSett ngs()
          && f eldSett ngs.getFacetF eldSett ngs(). sUseCSFForFacetCount ng()
          && CAN_FACET_ON_CSF_TYPES.conta ns(f eldSett ngs.getCsfF eldSett ngs().getCsfType()))) {
        throw new Sc maVal dat onExcept on("Thr ftF eldSett ngs: 'facetF eldSett ngs' can only be "
            + "used  n comb nat on w h ' ndexedF eldSett ngs' or w h 'csfF eldSett ngs' "
            + "w re ' sUseCSFForFacetCount ng' was set to true and Thr ftCSFType  s a type that "
            + "can be faceted on.");
      }

      applyFacetF eldSett ngs(f eld nfo, f eldSett ngs.getFacetF eldSett ngs());
      sett ngFound = true;
    }

     f (f eldSett ngs. sSetCsfV ewSett ngs()) {
       f (f eldSett ngs. sSet ndexedF eldSett ngs() || f eldSett ngs. sSetCsfF eldSett ngs()) {
        throw new Sc maVal dat onExcept on("Thr ftF eldSett ngs: Only one of "
            + "' ndexedF eldSett ngs', 'csfF eldSett ngs', 'csfV ewSett ngs' can be set.");
      }

      // add t  f eld now, but apply sett ngs later to make sure t  base f eld was added properly
      // before
      csfV ewF elds.put(f eld d, f eldConf g);
      sett ngFound = true;
    }

     f (!sett ngFound) {
      throw new Sc maVal dat onExcept on("Thr ftF eldSett ngs: One of ' ndexedF eldSett ngs', "
          + "'csfF eldSett ngs' or 'facetF eldSett ngs' must be set.");
    }

    // search f eld sett ngs are opt onal
     f (f eldSett ngs. sSetSearchF eldSett ngs()) {
       f (!f eldSett ngs. sSet ndexedF eldSett ngs()) {
        throw new Sc maVal dat onExcept on(
            "Thr ftF eldSett ngs: 'searchF eldSett ngs' can only be "
                + "used  n comb nat on w h ' ndexedF eldSett ngs'");
      }

      applySearchF eldSett ngs(f eld nfo, f eldSett ngs.getSearchF eldSett ngs());
    }

    return f eld nfo;
  }

  pr vate vo d applyCsfF eldSett ngs(F eld nfo f eld nfo, Thr ftCSFF eldSett ngs sett ngs)
      throws Sc maVal dat onExcept on {
    // csfType  s requ red - no need to c ck  f  's set
    f eld nfo.getF eldType().setDocValuesType(DocValuesType.NUMER C);
    f eld nfo.getF eldType().setCsfType(sett ngs.getCsfType());

     f (sett ngs. sVar ableLength()) {
      f eld nfo.getF eldType().setDocValuesType(DocValuesType.B NARY);
      f eld nfo.getF eldType().setCsfVar ableLength();
    } else {
       f (sett ngs. sSetF xedLengthSett ngs()) {
        f eld nfo.getF eldType().setCsfF xedLengthSett ngs(
            sett ngs.getF xedLengthSett ngs().getNumValuesPerDoc(),
            sett ngs.getF xedLengthSett ngs(). sUpdateable());
         f (sett ngs.getF xedLengthSett ngs().getNumValuesPerDoc() > 1) {
          f eld nfo.getF eldType().setDocValuesType(DocValuesType.B NARY);
        }
      } else {
        throw new Sc maVal dat onExcept on(
            "Thr ftCSFF eldSett ngs: E  r var ableLength should be set to 'true', "
                + "or f xedLengthSett ngs should be set.");
      }
    }

    f eld nfo.getF eldType().setCsfLoad ntoRam(sett ngs. sLoad ntoRAM());
     f (sett ngs. sSetDefaultValue()) {
      f eld nfo.getF eldType().setCsfDefaultValue(sett ngs.getDefaultValue());
    }
  }

  pr vate vo d applyCsfV ewF eldSett ngs(F eld nfo f eld nfo, F eld nfo baseF eld,
                                         Thr ftCSFV ewSett ngs sett ngs)
      throws Sc maVal dat onExcept on {
    // csfType  s requ red - no need to c ck  f  's set
    f eld nfo.getF eldType().setDocValuesType(DocValuesType.NUMER C);
    f eld nfo.getF eldType().setCsfType(sett ngs.getCsfType());

    f eld nfo.getF eldType().setCsfF xedLengthSett ngs(1 /* numValuesPerDoc*/,
        false /* updateable*/);

    f eld nfo.getF eldType().setCsfV ewSett ngs(f eld nfo.getNa (), sett ngs, baseF eld);
  }

  pr vate vo d applyFacetF eldSett ngs(F eld nfo f eld nfo, Thr ftFacetF eldSett ngs sett ngs) {
     f (sett ngs. sSetFacetNa ()) {
      f eld nfo.getF eldType().setFacetNa (sett ngs.getFacetNa ());
    } else {
      // fall back to f eld na   f no facet na   s expl c ly prov ded
      f eld nfo.getF eldType().setFacetNa (f eld nfo.getNa ());
    }
    f eld nfo.getF eldType().setStoreFacetSk pl st(sett ngs. sStoreSk pl st());
    f eld nfo.getF eldType().setStoreFacetOffens veCounters(sett ngs. sStoreOffens veCounters());
    f eld nfo.getF eldType().setUseCSFForFacetCount ng(sett ngs. sUseCSFForFacetCount ng());
  }

  pr vate vo d apply ndexedF eldSett ngs(F eld nfo f eld nfo, Thr ft ndexedF eldSett ngs sett ngs)
      throws Sc maVal dat onExcept on {
    f eld nfo.getF eldType().set ndexedF eld(true);
    f eld nfo.getF eldType().setStored(sett ngs. sStored());
    f eld nfo.getF eldType().setToken zed(sett ngs. sToken zed());
    f eld nfo.getF eldType().setStoreTermVectors(sett ngs. sStoreTermVectors());
    f eld nfo.getF eldType().setStoreTermVectorOffsets(sett ngs. sStoreTermVectorOffsets());
    f eld nfo.getF eldType().setStoreTermVectorPos  ons(sett ngs. sStoreTermVectorPos  ons());
    f eld nfo.getF eldType().setStoreTermVectorPayloads(sett ngs. sStoreTermVectorPayloads());
    f eld nfo.getF eldType().setOm Norms(sett ngs. sOm Norms());
    f eld nfo.getF eldType().set ndexHFTermPa rs(sett ngs. s ndexH ghFreqTermPa rs());
    f eld nfo.getF eldType().setUseT etSpec f cNormal zat on(
        sett ngs.deprecated_performT etSpec f cNormal zat ons);

     f (sett ngs. sSet ndexOpt ons()) {
      sw ch (sett ngs.get ndexOpt ons()) {
        case DOCS_ONLY :
          f eld nfo.getF eldType().set ndexOpt ons( ndexOpt ons.DOCS);
          break;
        case DOCS_AND_FREQS :
          f eld nfo.getF eldType().set ndexOpt ons( ndexOpt ons.DOCS_AND_FREQS);
          break;
        case DOCS_AND_FREQS_AND_POS T ONS :
          f eld nfo.getF eldType().set ndexOpt ons( ndexOpt ons.DOCS_AND_FREQS_AND_POS T ONS);
          break;
        case DOCS_AND_FREQS_AND_POS T ONS_AND_OFFSETS :
          f eld nfo.getF eldType().set ndexOpt ons(
               ndexOpt ons.DOCS_AND_FREQS_AND_POS T ONS_AND_OFFSETS);
          break;
        default:
          throw new Sc maVal dat onExcept on("Unknown value for  ndexOpt ons: "
              + sett ngs.get ndexOpt ons());
      }
    } else  f (sett ngs. s ndexed()) {
      // default for backward-compat b l y
      f eld nfo.getF eldType().set ndexOpt ons( ndexOpt ons.DOCS_AND_FREQS_AND_POS T ONS);
    }

    f eld nfo.getF eldType().setStorePerPos  onPayloads(sett ngs. sStorePerPos  onPayloads());
    f eld nfo.getF eldType().setDefaultPayloadLength(
        sett ngs.getDefaultPerPos  onPayloadLength());
    f eld nfo.getF eldType().setBeco s mmutable(!sett ngs. sSupportOutOfOrderAppends());
    f eld nfo.getF eldType().setSupportOrderedTerms(sett ngs. sSupportOrderedTerms());
    f eld nfo.getF eldType().setSupportTermTextLookup(sett ngs. sSupportTermTextLookup());

     f (sett ngs. sSetNu r cF eldSett ngs()) {
      f eld nfo.getF eldType().setNu r cF eldSett ngs(
          new  ndexedNu r cF eldSett ngs(sett ngs.getNu r cF eldSett ngs()));
    }

     f (sett ngs. sSetTokenStreamSer al zer()) {
      f eld nfo.getF eldType().setTokenStreamSer al zerBu lder(
          bu ldTokenStreamSer al zerProv der(sett ngs.getTokenStreamSer al zer()));
    }
  }

  pr vate vo d applySearchF eldSett ngs(F eld nfo f eld nfo, Thr ftSearchF eldSett ngs sett ngs)
      throws Sc maVal dat onExcept on {
    f eld nfo.getF eldType().setTextSearchableF eld  ght(
        (float) sett ngs.getTextSearchableF eld  ght());
    f eld nfo.getF eldType().setTextSearchableByDefault(sett ngs. sTextDefaultSearchable());
  }

  pr vate vo d val date(F eld nfo f eld nfo) throws Sc maVal dat onExcept on {
  }

  pr vate TokenStreamSer al zer.Bu lder bu ldTokenStreamSer al zerProv der(
      f nal Thr ftTokenStreamSer al zer sett ngs) {
    TokenStreamSer al zer.Bu lder bu lder = TokenStreamSer al zer.bu lder();
    for (Str ng ser al zerNa  : sett ngs.getAttr buteSer al zerClassNa s()) {
      try {
        bu lder.add((TokenStreamSer al zer.Attr buteSer al zer) Class.forNa (ser al zerNa )
            .new nstance());
      } catch ( nstant at onExcept on e) {
        throw new Runt  Except on(
            "Unable to  nstant ate Attr buteSer al zer for na  " + ser al zerNa );
      } catch ( llegalAccessExcept on e) {
        throw new Runt  Except on(
            "Unable to  nstant ate Attr buteSer al zer for na  " + ser al zerNa );
      } catch (ClassNotFoundExcept on e) {
        throw new Runt  Except on(
            "Unable to  nstant ate Attr buteSer al zer for na  " + ser al zerNa );
      }
    }
    return bu lder;
  }

  @Overr de
  publ c FacetsConf g getFacetsConf g() {
    FacetsConf g facetsConf g = new FacetsConf g();

    for (Str ng facetNa  : facetNa ToF eldMap.keySet()) {
      // set mult Valued = true as default, s nce  're us ng SortedSetDocValues facet,  n wh ch,
      // t re  s no d fference bet en mult Valued true or false for t  real facet, but only t 
      // c ck ng of t  values.
      facetsConf g.setMult Valued(facetNa , true);
    }

    return facetsConf g;
  }

  @Overr de
  publ c Analyzer getDefaultAnalyzer(Thr ftAnalyzer overr de) {
     f (overr de != null) {
      return analyzerFactory.getAnalyzer(overr de);
    }

     f (defaultAnalyzer != null) {
      return analyzerFactory.getAnalyzer(defaultAnalyzer);
    }

    return new SearchWh espaceAnalyzer();
  }

  @Overr de
  publ c  mmutableCollect on<F eld nfo> getF eld nfos() {
    return f eldSett ngsMapBy d.values();
  }

  /**
   * T   s t  preferred  thod to c ck w t r a f eld conf gurat on  s  n sc ma.
   * One can also use getF eld nfo and do null c cks, but should be careful about excess ve
   * warn ng logg ng result ng from look ng up f elds not  n sc ma.
   */
  @Overr de
  publ c boolean hasF eld( nt f eldConf g d) {
    return f eldSett ngsMapBy d.conta nsKey(f eldConf g d);
  }

  /**
   * T   s t  preferred  thod to c ck w t r a f eld conf gurat on  s  n sc ma.
   * One can also use getF eld nfo and do null c cks, but should be careful about excess ve
   * warn ng logg ng result ng from look ng up f elds not  n sc ma.
   */
  @Overr de
  publ c boolean hasF eld(Str ng f eldNa ) {
    return f eldSett ngsMapByNa .conta nsKey(f eldNa );
  }

  /**
   * Get F eld nfo for t  g ven f eld  d.
   *  f t  goal  s to c ck w t r a f eld  s  n t  sc ma, use {@l nk #hasF eld( nt)}  nstead.
   * T   thod logs a warn ng w never   returns null.
   */
  @Overr de
  @Nullable
  publ c F eld nfo getF eld nfo( nt f eldConf g d) {
    return getF eld nfo(f eldConf g d, null);
  }

  pr vate org.apac .lucene. ndex.F eld nfo convert(Str ng f eldNa ,
                                                     nt  ndex,
                                                    Earlyb rdF eldType type) {
    return new org.apac .lucene. ndex.F eld nfo(
        f eldNa ,                          // Str ng na 
         ndex,                              //  nt number
        type.storeTermVectors(),            // boolean storeTermVector
        type.om Norms(),                   // boolean om Norms
        type. sStorePerPos  onPayloads(),  // boolean storePayloads
        type. ndexOpt ons(),                //  ndexOpt ons  ndexOpt ons
        type.docValuesType(),               // DocValuesType docValues
        -1,                                 // long dvGen
        Maps.<Str ng, Str ng>newHashMap(),  // Map<Str ng, Str ng> attr butes
        0,                                  //  nt po ntDataD  ns onCount
        0,                                  //  nt po nt ndexD  ns onCount
        0,                                  //  nt po ntNumBytes
        false);                             // boolean softDeletesF eld
  }

  /**
   * Get F eld nfo for t  g ven f eld na , or null  f t  f eld does not ex st.
   */
  @Overr de
  @Nullable
  publ c F eld nfo getF eld nfo(Str ng f eldNa ) {
    return f eldSett ngsMapByNa .get(f eldNa );
  }

  @Overr de
  publ c Str ng getF eldNa ( nt f eldConf g d) {
    F eld nfo f eld nfo = f eldSett ngsMapBy d.get(f eldConf g d);
    return f eld nfo != null ? f eld nfo.getNa () : null;
  }

  @Overr de
  publ c F eld nfo getF eld nfo( nt f eldConf g d, Thr ftF eldConf gurat on overr de) {
    F eld nfo f eld nfo = f eldSett ngsMapBy d.get(f eldConf g d);
     f (f eld nfo == null) {
      // T   thod  s used to c ck t  ava lab l y of f elds by  Ds,
      // so no warn ng  s logged  re (would be too verbose ot rw se).
      return null;
    }

     f (overr de != null) {
      try {
        return  rge(f eldConf g d, f eld nfo, overr de);
      } catch (Sc maVal dat onExcept on e) {
        throw new Runt  Except on(e);
      }
    }

    return f eld nfo;
  }

  @Overr de
  publ c  nt getNumFacetF elds() {
    return numFacetF elds;
  }

  @Overr de
  publ c F eld nfo getFacetF eldByFacetNa (Str ng facetNa ) {
    return facetNa ToF eldMap.get(facetNa );
  }

  @Overr de
  publ c F eld nfo getFacetF eldByF eldNa (Str ng f eldNa ) {
    F eld nfo f eld nfo = getF eld nfo(f eldNa );
    return f eld nfo != null && f eld nfo.getF eldType(). sFacetF eld() ? f eld nfo : null;
  }

  @Overr de
  publ c Collect on<F eld nfo> getFacetF elds() {
    return facetNa ToF eldMap.values();
  }

  @Overr de
  publ c Collect on<F eld nfo> getCsfFacetF elds() {
    return csfFacetF elds;
  }

  @Overr de
  publ c Str ng getVers onDescr pt on() {
    return vers onDesc;
  }

  @Overr de
  publ c  nt getMajorVers onNumber() {
    return majorVers onNumber;
  }

  @Overr de
  publ c  nt getM norVers onNumber() {
    return m norVers onNumber;
  }

  @Overr de
  publ c boolean  sVers onOff c al() {
    return  sVers onOff c al;
  }

  /**
   * Parses a vers on str ng l ke "16: rena d f eld x  nto y"  nto a vers on number and
   * a str ng descr pt on.
   * @return a Pa r of t  vers on number and t  descr pt on
   */
  pr vate stat c Pa r< nteger, Str ng> parseVers onStr ng(Str ng vers on)
      throws Sc maVal dat onExcept on {
    Precond  ons.c ckNotNull(vers on, "Sc ma must have a vers on number and descr pt on.");
     nt colon ndex = vers on. ndexOf(':');
     f (colon ndex == -1) {
      throw new Sc maVal dat onExcept on("Malfor d vers on str ng: " + vers on);
    }
    try {
       nt vers onNumber =  nteger.parse nt(vers on.substr ng(0, colon ndex));
      Str ng vers onDesc = vers on.substr ng(colon ndex + 1);
      return Pa r.of(vers onNumber, vers onDesc);
    } catch (Except on e) {
      throw new Sc maVal dat onExcept on("Malfor d vers on str ng: " + vers on, e);
    }
  }

  @Overr de
  publ c Map<Str ng, F eld  ghtDefault> getF eld  ghtMap() {
    return f eld  ghtMap;
  }

  /**
   * Bu ld t  feature maps so that   can use feature na  to get t  feature conf gurat on.
   * @return: an  mmutable map keyed on f eldNa .
   */
  pr vate Pa r< mmutableMap<Str ng, FeatureConf gurat on>,
       mmutableMap< nteger, FeatureConf gurat on>> bu ldFeatureMaps(
      f nal Map< nteger, Thr ftF eldConf gurat on> csvV ewF elds)
      throws Sc maVal dat onExcept on {

    f nal  mmutableMap.Bu lder<Str ng, FeatureConf gurat on> featureConf gMapByNa Bu lder =
         mmutableMap.bu lder();
    f nal  mmutableMap.Bu lder< nteger, FeatureConf gurat on> featureConf gMapBy dBu lder =
         mmutableMap.bu lder();

    for (f nal Map.Entry< nteger, Thr ftF eldConf gurat on> entry : csvV ewF elds.entrySet()) {
      Thr ftF eldSett ngs f eldSett ngs = entry.getValue().getSett ngs();
      F eld nfo f eld nfo = getF eld nfo(entry.getKey());
      F eld nfo baseF eld nfo =
          getF eld nfo(f eldSett ngs.getCsfV ewSett ngs().getBaseF eldConf g d());
       f (baseF eld nfo == null) {
        throw new Sc maVal dat onExcept on("Base f eld ( d="
            + f eldSett ngs.getCsfV ewSett ngs().getBaseF eldConf g d() + ") not found.");
      }
      applyCsfV ewF eldSett ngs(f eld nfo, baseF eld nfo, f eldSett ngs.getCsfV ewSett ngs());

      FeatureConf gurat on featureConf g = f eld nfo.getF eldType()
          .getCsfV ewFeatureConf gurat on();
       f (featureConf g != null) {
        featureConf gMapByNa Bu lder.put(f eld nfo.getNa (), featureConf g);
        featureConf gMapBy dBu lder.put(f eld nfo.getF eld d(), featureConf g);
      }
    }

    return Pa r.of(featureConf gMapByNa Bu lder.bu ld(), featureConf gMapBy dBu lder.bu ld());
  }

  @Overr de
  publ c FeatureConf gurat on getFeatureConf gurat onByNa (Str ng featureNa ) {
    return featureConf gMapByNa .get(featureNa );
  }

  @Overr de
  publ c FeatureConf gurat on getFeatureConf gurat onBy d( nt featureF eld d) {
    return Precond  ons.c ckNotNull(featureConf gMapBy d.get(featureF eld d),
        "F eld  D: " + featureF eld d);
  }

  @Overr de
  @Nullable
  publ c Thr ftCSFType getCSFF eldType(Str ng f eldNa ) {
    F eld nfo f eld nfo = getF eld nfo(f eldNa );
     f (f eld nfo == null) {
      return null;
    }

    Earlyb rdF eldType f eldType = f eld nfo.getF eldType();
     f (f eldType.docValuesType() != org.apac .lucene. ndex.DocValuesType.NUMER C) {
      return null;
    }

    return f eldType.getCsfType();
  }

  @Overr de
  publ c  mmutableSc ma nterface getSc maSnapshot() {
    return t ;
  }

  pr vate F eld nfo  rge( nt f eldConf g d,
                          F eld nfo f eld nfo,
                          Thr ftF eldConf gurat on overr deConf g)
      throws Sc maVal dat onExcept on {

    throw new UnsupportedOperat onExcept on("F eld overr de conf g not supported");
  }

  @Overr de
  publ c Thr ftSearchFeatureSc ma getSearchFeatureSc ma() {
    return searchFeatureSc ma;
  }

  @Overr de
  publ c  mmutableMap< nteger, FeatureConf gurat on> getFeature dToFeatureConf g() {
    return featureConf gMapBy d;
  }

  @Overr de
  publ c  mmutableMap<Str ng, FeatureConf gurat on> getFeatureNa ToFeatureConf g() {
    return featureConf gMapByNa ;
  }

  pr vate Thr ftSearchFeatureSc ma createSearchResultFeatureSc ma(
      Str ng featureSc maVers onPref x,
      Map<Str ng, F eld nfo> allF eldSett ngs,
      Map<Str ng, FeatureConf gurat on> featureConf gurat ons) throws Sc maVal dat onExcept on {
    f nal  mmutableMap.Bu lder< nteger, Thr ftSearchFeatureSc maEntry> bu lder =
        new  mmutableMap.Bu lder<>();

    for (Map.Entry<Str ng, F eld nfo> f eld : allF eldSett ngs.entrySet()) {
      FeatureConf gurat on featureConf g = featureConf gurat ons.get(f eld.getKey());
       f (featureConf g == null) {
        // T   s e  r a not csf related f eld or a csf f eld.
        cont nue;
      }

      // T   s a csfV ew f eld.
       f (featureConf g.getOutputType() == null) {
        LOG. nfo("Sk p unused f eldsc mas: {} for search feature sc ma.", f eld.getKey());
        cont nue;
      }

      Thr ftSearchFeatureType featureType = getResultFeatureType(featureConf g.getOutputType());
       f (featureType != null) {
        bu lder.put(
            f eld.getValue().getF eld d(),
            new Thr ftSearchFeatureSc maEntry(f eld.getKey(), featureType));
      } else {
        LOG.error(" nval d CSFType encountered for csf f eld: {}", f eld.getKey());
      }
    }
    Map< nteger, Thr ftSearchFeatureSc maEntry>  ndexOnlySc maEntr es = bu lder.bu ld();

    // Add earlyb rd der ved features, t y are def ned  n ExternalT etFeatures and used  n t 
    // scor ng funct on. T y are no d fferent from those auto-generated  ndex-based features
    // v e d from outs de Earlyb rd.
    Map< nteger, Thr ftSearchFeatureSc maEntry> entr esW hEBFeatures =
        appendToFeatureSc ma(
             ndexOnlySc maEntr es, ExternalT etFeature.EARLYB RD_DER VED_FEATURES);

    // Add ot r features needed for t et rank ng from Earlyb rdRank ngDer vedFeature.
    Map< nteger, Thr ftSearchFeatureSc maEntry> allSc maEntr es = appendToFeatureSc ma(
        entr esW hEBFeatures, ExternalT etFeature.EARLYB RD_RANK NG_DER VED_FEATURES);

    long sc maEntr esC cksum = getC cksum(allSc maEntr es);
    SearchLongGauge.export("feature_sc ma_c cksum", new Atom cLong(sc maEntr esC cksum));

    Str ng sc maVers on = Str ng.format(
        "%s.%d.%d", featureSc maVers onPref x, majorVers onNumber, m norVers onNumber);
    Thr ftSearchFeatureSc maSpec f er sc maSpec f er =
        new Thr ftSearchFeatureSc maSpec f er(sc maVers on, sc maEntr esC cksum);

    Thr ftSearchFeatureSc ma sc ma = new Thr ftSearchFeatureSc ma();
    sc ma.setSc maSpec f er(sc maSpec f er);
    sc ma.setEntr es(allSc maEntr es);

    return sc ma;
  }

  // Ser al zes sc maEntr es to a byte array, and computes a CRC32 c cksum of t  array.
  // T  ser al zat on needs to be stable:  f sc maEntr es1.equals(sc maEntr es2),   want
  // t   thod to produce t  sa  c cksum for sc maEntr e1 and sc maEntr e2, even  f
  // t  c cksums are computed  n d fferent JVMs, etc.
  pr vate stat c long getC cksum(Map< nteger, Thr ftSearchFeatureSc maEntry> sc maEntr es)
      throws Sc maVal dat onExcept on {
    SortedMap< nteger, Thr ftSearchFeatureSc maEntry> sortedSc maEntr es =
        new TreeMap< nteger, Thr ftSearchFeatureSc maEntry>(sc maEntr es);

    CRC32OutputStream crc32OutputStream = new CRC32OutputStream();
    ObjectOutputStream objectOutputStream = null;
    try {
      objectOutputStream = new ObjectOutputStream(crc32OutputStream);
      for ( nteger f eld d : sortedSc maEntr es.keySet()) {
        objectOutputStream.wr eObject(f eld d);
        Thr ftSearchFeatureSc maEntry sc maEntry = sortedSc maEntr es.get(f eld d);
        objectOutputStream.wr eObject(sc maEntry.getFeatureNa ());
        objectOutputStream.wr eObject(sc maEntry.getFeatureType());
      }
      objectOutputStream.flush();
      return crc32OutputStream.getValue();
    } catch ( OExcept on e) {
      throw new Sc maVal dat onExcept on("Could not ser al ze feature sc ma entr es.", e);
    } f nally {
      Precond  ons.c ckNotNull(objectOutputStream);
      try {
        objectOutputStream.close();
      } catch ( OExcept on e) {
        throw new Sc maVal dat onExcept on("Could not close ObjectOutputStream.", e);
      }
    }
  }

  /**
   * Get t  search feature type based on t  csf type.
   * @param csfType t  column str de f eld type for t  data
   * @return t  correspond ng search feature type
   */
  @V s bleForTest ng
  publ c stat c Thr ftSearchFeatureType getResultFeatureType(Thr ftCSFType csfType) {
    sw ch (csfType) {
      case  NT:
      case BYTE:
        return Thr ftSearchFeatureType. NT32_VALUE;
      case BOOLEAN:
        return Thr ftSearchFeatureType.BOOLEAN_VALUE;
      case FLOAT:
      case DOUBLE:
        return Thr ftSearchFeatureType.DOUBLE_VALUE;
      case LONG:
        return Thr ftSearchFeatureType.LONG_VALUE;
      default:
        return null;
    }
  }
}
