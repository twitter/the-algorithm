package com.tw ter.search.common.sc ma;

 mport java.ut l.Map;
 mport java.ut l.Set;
 mport javax.annotat on.Nullable;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect. mmutableL st;
 mport com.google.common.collect.Sets;

 mport com.tw ter.common.text.ut l.CharSequenceTermAttr buteSer al zer;
 mport com.tw ter.common.text.ut l.Pos  on ncre ntAttr buteSer al zer;
 mport com.tw ter.common.text.ut l.TokenStreamSer al zer;
 mport com.tw ter.common.text.ut l.TokenTypeAttr buteSer al zer;
 mport com.tw ter.search.common.sc ma.base.FeatureConf gurat on;
 mport com.tw ter.search.common.sc ma.base.F eldNa To dMapp ng;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftCSFF eldSett ngs;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftCSFType;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftCSFV ewSett ngs;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftFacetF eldSett ngs;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftFeatureNormal zat onType;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftFeatureUpdateConstra nt;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftF eldConf gurat on;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftF eldSett ngs;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftF xedLengthCSFSett ngs;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndexOpt ons;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndexedF eldSett ngs;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndexedNu r cF eldSett ngs;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftNu r cType;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftSc ma;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftSearchF eldSett ngs;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftTokenStreamSer al zer;
 mport com.tw ter.search.common.ut l.analys s.CharTermAttr buteSer al zer;
 mport com.tw ter.search.common.ut l.analys s. ntTermAttr buteSer al zer;
 mport com.tw ter.search.common.ut l.analys s.LongTermAttr buteSer al zer;
 mport com.tw ter.search.common.ut l.analys s.PayloadAttr buteSer al zer;

publ c class Sc maBu lder {

  publ c stat c f nal Str ng CSF_V EW_NAME_SEPARATOR = ".";
  protected f nal Thr ftSc ma sc ma = new Thr ftSc ma();
  protected f nal F eldNa To dMapp ng  dMapp ng;
  protected f nal  nt tokenStreamSer al zerVers on;

  // As of now,   do not allow two f elds to share t  sa  f eld na .
  // T  set  s used to perform t  c ck.
  pr vate f nal Set<Str ng> f eldNa Set = Sets.newHashSet();

  /**
   * Construct a sc ma bu lder w h t  g ven F eldNa To dMapper.
   * A Sc maBu lder  s used to bu ld a Thr ftSc ma  ncre ntally.
   */
  publ c Sc maBu lder(F eldNa To dMapp ng  dMapp ng,
                       TokenStreamSer al zer.Vers on tokenStreamSer al zerVers on) {
    t . dMapp ng =  dMapp ng;
    Precond  ons.c ckArgu nt(
        tokenStreamSer al zerVers on == TokenStreamSer al zer.Vers on.VERS ON_2);
    t .tokenStreamSer al zerVers on = tokenStreamSer al zerVers on.ord nal();
  }

  /**
   * Bu ld Thr ftSc ma us ng sett ngs accumulated so far.
   */
  publ c f nal Thr ftSc ma bu ld() {
    return sc ma;
  }

  /**
   * Uses f eldNa  also as facetNa .
   */
  publ c f nal Sc maBu lder w hFacetConf gs(Str ng f eldNa ,
      boolean storeSk pL st,
      boolean storeOffens veCounters,
      boolean useCSFForFacetCount ng) {
    return w hFacetConf gs(
        f eldNa ,
        f eldNa ,
        storeSk pL st,
        storeOffens veCounters,
        useCSFForFacetCount ng);
  }

  /**
   * Add facet f eld conf gurat on.
   */
  publ c f nal Sc maBu lder w hFacetConf gs(Str ng f eldNa ,
      Str ng facetNa ,
      boolean storeSk pL st,
      boolean storeOffens veCounters,
      boolean useCSFForFacetCount ng) {
     f (!should ncludeF eld(f eldNa )) {
      return t ;
    }
    Thr ftFacetF eldSett ngs facetSett ngs = new Thr ftFacetF eldSett ngs();
    // As of now, all   facet na s are t  sa  as f eld na s
    facetSett ngs.setFacetNa (facetNa );
    facetSett ngs.setStoreSk pl st(storeSk pL st);
    facetSett ngs.setStoreOffens veCounters(storeOffens veCounters);
    facetSett ngs.setUseCSFForFacetCount ng(useCSFForFacetCount ng);

     nt f eld d =  dMapp ng.getF eld D(f eldNa );
    Thr ftF eldConf gurat on f eldConf gurat on = sc ma.getF eldConf gs().get(f eld d);
    Precond  ons.c ckNotNull(f eldConf gurat on,
        " n Earlyb rd, a facet f eld must be  ndexed. "
            + "No Thr ft ndexedF eldSett ngs found for f eld " + f eldNa );
    f eldConf gurat on.getSett ngs().setFacetF eldSett ngs(facetSett ngs);
    return t ;
  }

  /**
   * Conf gure t  g ven f eld  D to be used for part  on ng.
   */
  publ c f nal Sc maBu lder w hPart  onF eld d( nt part  onF eld d) {
    sc ma.setPart  onF eld d(part  onF eld d);
    return t ;
  }

  /**
   * Add a column str de f eld  nto sc ma.
   */
  publ c f nal Sc maBu lder w hColumnStr deF eld(Str ng f eldNa ,
      Thr ftCSFType type,
       nt numValuesPerDoc,
      boolean updatable,
      boolean load ntoRam) {
    return w hColumnStr deF eld(f eldNa , type, numValuesPerDoc, updatable, load ntoRam, null);
  }

  /**
   * Add a column str de f eld  nto sc ma that  s var able length.
   */
  publ c f nal Sc maBu lder w hB naryColumnStr deF eld(Str ng f eldNa ,
                                                         boolean load ntoRam) {
     f (!should ncludeF eld(f eldNa )) {
      return t ;
    }
    Thr ftCSFF eldSett ngs csfF eldSett ngs = new Thr ftCSFF eldSett ngs();
    csfF eldSett ngs.setCsfType(Thr ftCSFType.BYTE)
        .setVar ableLength(true)
        .setLoad ntoRAM(load ntoRam);

    Thr ftF eldSett ngs f eldSett ngs =
        new Thr ftF eldSett ngs().setCsfF eldSett ngs(csfF eldSett ngs);
    Thr ftF eldConf gurat on f eldConf =
        new Thr ftF eldConf gurat on(f eldNa ).setSett ngs(f eldSett ngs);
    put ntoF eldConf gs( dMapp ng.getF eld D(f eldNa ), f eldConf);
    return t ;
  }

  /**
   * Add a column str de f eld  nto sc ma wh ch has a default value.
   */
  publ c f nal Sc maBu lder w hColumnStr deF eld(Str ng f eldNa ,
      Thr ftCSFType type,
       nt numValuesPerDoc,
      boolean updatable,
      boolean load ntoRam,
      Long defaultValue) {
     f (!should ncludeF eld(f eldNa )) {
      return t ;
    }
    Thr ftCSFF eldSett ngs csfF eldSett ngs = new Thr ftCSFF eldSett ngs();
    csfF eldSett ngs.setCsfType(type)
        .setVar ableLength(false)
        .setF xedLengthSett ngs(
            new Thr ftF xedLengthCSFSett ngs()
                .setNumValuesPerDoc(numValuesPerDoc)
                .setUpdateable(updatable))
        .setLoad ntoRAM(load ntoRam);

     f (defaultValue != null) {
      csfF eldSett ngs.setDefaultValue(defaultValue);
    }

    Thr ftF eldSett ngs f eldSett ngs =
        new Thr ftF eldSett ngs().setCsfF eldSett ngs(csfF eldSett ngs);
    Thr ftF eldConf gurat on f eldConf =
        new Thr ftF eldConf gurat on(f eldNa ).setSett ngs(f eldSett ngs);
    put ntoF eldConf gs( dMapp ng.getF eld D(f eldNa ), f eldConf);
    return t ;
  }

  /**
   * Add a CSF v ew  nto sc ma. A v ew  s a port on of anot r CSF.
   */
  publ c f nal Sc maBu lder w hColumnStr deF eldV ew(
      Str ng f eldNa ,
      Thr ftCSFType csfType,
      Thr ftCSFType outputCSFType,
      Str ng baseF eldNa ,
       nt value ndex,
       nt b StartPos  on,
       nt b Length,
      Thr ftFeatureNormal zat onType featureNormal zat onType,
      @Nullable Set<Thr ftFeatureUpdateConstra nt> constra nts) {
     f (!should ncludeF eld(f eldNa )) {
      return t ;
    }

     nt baseF eldConf g D =  dMapp ng.getF eld D(baseF eldNa );

    Thr ftCSFV ewSett ngs csfV ewSett ngs = new Thr ftCSFV ewSett ngs()
            .setBaseF eldConf g d(baseF eldConf g D)
            .setCsfType(csfType)
            .setValue ndex(value ndex)
            .setB StartPos  on(b StartPos  on)
            .setB Length(b Length);
     f (outputCSFType != null) {
      csfV ewSett ngs.setOutputCSFType(outputCSFType);
    }
     f (featureNormal zat onType != Thr ftFeatureNormal zat onType.NONE) {
      csfV ewSett ngs.setNormal zat onType(featureNormal zat onType);
    }
     f (constra nts != null) {
      csfV ewSett ngs.setFeatureUpdateConstra nts(constra nts);
    }
    Thr ftF eldSett ngs f eldSett ngs = new Thr ftF eldSett ngs()
            .setCsfV ewSett ngs(csfV ewSett ngs);
    Thr ftF eldConf gurat on f eldConf = new Thr ftF eldConf gurat on(f eldNa )
            .setSett ngs(f eldSett ngs);

    Map< nteger, Thr ftF eldConf gurat on> f eldConf gs = sc ma.getF eldConf gs();
    ver fyCSFV ewSett ngs(f eldConf gs, f eldConf);

    put ntoF eldConf gs( dMapp ng.getF eld D(f eldNa ), f eldConf);
    return t ;
  }

  /**
   * San y c cks for CSF v ew sett ngs.
   */
  publ c stat c vo d ver fyCSFV ewSett ngs(Map< nteger, Thr ftF eldConf gurat on> f eldConf gs,
      Thr ftF eldConf gurat on f eldConf) {
    Precond  ons.c ckNotNull(f eldConf.getSett ngs());
    Precond  ons.c ckNotNull(f eldConf.getSett ngs().getCsfV ewSett ngs());
    Thr ftCSFV ewSett ngs csfV ewSett ngs = f eldConf.getSett ngs().getCsfV ewSett ngs();

     f (f eldConf gs != null) {
      Thr ftF eldConf gurat on baseF eldConf g = f eldConf gs.get(
              csfV ewSett ngs.getBaseF eldConf g d());
       f (baseF eldConf g != null) {
        Str ng baseF eldNa  = baseF eldConf g.getF eldNa ();
        Str ng expectedV ewNa Pref x = baseF eldNa  + CSF_V EW_NAME_SEPARATOR;
         f (f eldConf.getF eldNa ().startsW h(expectedV ewNa Pref x)) {
          Thr ftF eldSett ngs baseF eldSett ngs = baseF eldConf g.getSett ngs();
          Thr ftCSFF eldSett ngs baseF eldCSFSett ngs = baseF eldSett ngs.getCsfF eldSett ngs();

           f (baseF eldCSFSett ngs != null) {
              f (!baseF eldCSFSett ngs. sVar ableLength()
                 && baseF eldCSFSett ngs.getF xedLengthSett ngs() != null) {

               Thr ftCSFType baseCSFType = baseF eldCSFSett ngs.getCsfType();
               sw ch (baseCSFType) {
                 case BYTE:
                   c ckCSFV ewPos  ons(baseF eldCSFSett ngs, 8, csfV ewSett ngs);
                   break;
                 case  NT:
                   c ckCSFV ewPos  ons(baseF eldCSFSett ngs, 32, csfV ewSett ngs);
                   break;
                 default:
                   throw new  llegalStateExcept on("Base f eld: " + baseF eldNa 
                           + "  s of a non-supported CSFType: " + baseCSFType);
               }
             } else {
               throw new  llegalStateExcept on("Base f eld: " + baseF eldNa 
                       + " must be a f xed-length CSF f eld");
             }
          } else {
            throw new  llegalStateExcept on("Base f eld: " + baseF eldNa  + "  s not a CSF f eld");
          }
        } else {
          throw new  llegalStateExcept on("V ew f eld na  for baseF eldConf g D: "
                  + csfV ewSett ngs.getBaseF eldConf g d() + " must start w h: '"
                  + expectedV ewNa Pref x + "'");
        }
      } else {
        throw new  llegalStateExcept on("Can't add a v ew, no f eld def ned for base f eld D: "
                + csfV ewSett ngs.getBaseF eldConf g d());
      }
    } else {
      throw new  llegalStateExcept on("Can't add a v ew, no f eld conf gs def ned.");
    }
  }

  pr vate stat c vo d c ckCSFV ewPos  ons(Thr ftCSFF eldSett ngs baseF eldCSFSett ngs,
       nt b sPerValue,
      Thr ftCSFV ewSett ngs csfV ewSett ngs) {
    Thr ftF xedLengthCSFSett ngs f xedLengthCSFSett ngs =
            baseF eldCSFSett ngs.getF xedLengthSett ngs();
    Precond  ons.c ckNotNull(f xedLengthCSFSett ngs);

     nt numValues = f xedLengthCSFSett ngs.getNumValuesPerDoc();
    Precond  ons.c ckState(csfV ewSett ngs.getValue ndex() >= 0,
        "value  ndex must be pos  ve: " + csfV ewSett ngs.getValue ndex());
    Precond  ons.c ckState(csfV ewSett ngs.getValue ndex() < numValues, "value  ndex "
        + csfV ewSett ngs.getValue ndex() + " must be less than numValues: " + numValues);

    Precond  ons.c ckState(csfV ewSett ngs.getB StartPos  on() >= 0,
        "b StartPos  on must be pos  ve: " + csfV ewSett ngs.getB StartPos  on());
    Precond  ons.c ckState(csfV ewSett ngs.getB StartPos  on() < b sPerValue,
        "b StartPos  on " + csfV ewSett ngs.getB StartPos  on()
            + " must be less than b sPerValue " + b sPerValue);

    Precond  ons.c ckState(csfV ewSett ngs.getB Length() >= 1,
        "b Length must be pos  ve: " + csfV ewSett ngs.getB Length());

    Precond  ons.c ckState(
        csfV ewSett ngs.getB StartPos  on() + csfV ewSett ngs.getB Length() <= b sPerValue,
        Str ng.format("b StartPos  on (%d) + b Length (%d) must be less than b sPerValue (%d)",
        csfV ewSett ngs.getB StartPos  on(), csfV ewSett ngs.getB Length(), b sPerValue));
  }

  // No pos  on; no freq; not pretoken zed; not token zed.
  /**
   * Norm  s d sabled as default. L ke Lucene str ng f eld, or  nt/long f elds.
   */
  publ c f nal Sc maBu lder w h ndexedNotToken zedF eld(Str ng f eldNa ) {
    return w h ndexedNotToken zedF eld(f eldNa , false);
  }

  /**
   * Add an  ndexed but not token zed f eld. T   s s m lar to Lucene's Str ngF eld.
   */
  publ c f nal Sc maBu lder w h ndexedNotToken zedF eld(Str ng f eldNa ,
                                                          boolean supportOutOfOrderAppends) {
    return w h ndexedNotToken zedF eld(f eldNa , supportOutOfOrderAppends, true);
  }

  pr vate f nal Sc maBu lder w h ndexedNotToken zedF eld(Str ng f eldNa ,
                                                          boolean supportOutOfOrderAppends,
                                                          boolean om Norms) {
     f (!should ncludeF eld(f eldNa )) {
      return t ;
    }
    Thr ftF eldSett ngs sett ngs = getNoPos  onNoFreqSett ngs(supportOutOfOrderAppends);
    sett ngs.get ndexedF eldSett ngs().setOm Norms(om Norms);
    Thr ftF eldConf gurat on conf g = new Thr ftF eldConf gurat on(f eldNa )
        .setSett ngs(sett ngs);
    put ntoF eldConf gs( dMapp ng.getF eld D(f eldNa ), conf g);
    return t ;
  }


  /** Makes t  g ven f eld searchable by default, w h t  g ven   ght. */
  publ c f nal Sc maBu lder w hSearchF eldByDefault(
      Str ng f eldNa , float textSearchableF eld  ght) {
     f (!should ncludeF eld(f eldNa )) {
      return t ;
    }

    Thr ftF eldSett ngs sett ngs =
        sc ma.getF eldConf gs().get( dMapp ng.getF eld D(f eldNa )).getSett ngs();
    sett ngs.setSearchF eldSett ngs(
        new Thr ftSearchF eldSett ngs()
            .setTextSearchableF eld  ght(textSearchableF eld  ght)
            .setTextDefaultSearchable(true));

    return t ;
  }

  /**
   * S m lar to Lucene's TextF eld. T  str ng  s analyzed us ng t  default/overr de analyzer.
   * @param f eldNa 
   * @param addHfPa r fHfF eldsArePresent Add hfPa r f elds  f t y ex sts  n t  sc ma.
   *            For certa n text f elds, add ng hfPa r f elds are usually preferred, but t y may
   *            not ex st  n t  sc ma,  n wh ch case t  hfPa r f elds w ll not be added.
   */
  publ c f nal Sc maBu lder w hTextF eld(Str ng f eldNa ,
                                           boolean addHfPa r fHfF eldsArePresent) {
     f (!should ncludeF eld(f eldNa )) {
      return t ;
    }
    Thr ftF eldConf gurat on conf g = new Thr ftF eldConf gurat on(f eldNa ).setSett ngs(
        getDefaultSett ngs(Thr ft ndexOpt ons.DOCS_AND_FREQS_AND_POS T ONS));

     f (addHfPa r fHfF eldsArePresent) {
      // Add hfPa r f elds only  f t y ex st  n t  sc ma for t  cluster
      boolean hfPa r = should ncludeF eld( mmutableSc ma.HF_TERM_PA RS_F ELD)
                       && should ncludeF eld( mmutableSc ma.HF_PHRASE_PA RS_F ELD);
      conf g.getSett ngs().get ndexedF eldSett ngs().set ndexH ghFreqTermPa rs(hfPa r);
    }

    conf g.getSett ngs().get ndexedF eldSett ngs().setToken zed(true);
    put ntoF eldConf gs( dMapp ng.getF eld D(f eldNa ), conf g);
    return t ;
  }

  /**
   * Marked t  g ven f eld as hav ng per pos  on payload.
   */
  publ c f nal Sc maBu lder w hPerPos  onPayload(Str ng f eldNa ,  nt defaultPayloadLength) {
     f (!should ncludeF eld(f eldNa )) {
      return t ;
    }
    Thr ftF eldSett ngs sett ngs =
            sc ma.getF eldConf gs().get( dMapp ng.getF eld D(f eldNa )).getSett ngs();

    sett ngs.get ndexedF eldSett ngs().setStorePerPos  onPayloads(true);
    sett ngs.get ndexedF eldSett ngs().setDefaultPerPos  onPayloadLength(defaultPayloadLength);
    return t ;
  }

  /**
   * Add f eld  nto sc ma that  s pre-token zed and does not have pos  on.
   * E.g. hashtags / stocks / card_doma n
   */
  publ c f nal Sc maBu lder w hPretoken zedNoPos  onF eld(Str ng f eldNa ) {
     f (!should ncludeF eld(f eldNa )) {
      return t ;
    }
    Thr ftF eldConf gurat on conf g = new Thr ftF eldConf gurat on(f eldNa )
        .setSett ngs(getPretoken zedNoPos  onF eldSett ng());
    // Add hfPa r f elds only  f t y ex st  n t  sc ma for t  cluster
    boolean hfPa r = should ncludeF eld( mmutableSc ma.HF_TERM_PA RS_F ELD)
                         && should ncludeF eld( mmutableSc ma.HF_PHRASE_PA RS_F ELD);
    conf g.getSett ngs().get ndexedF eldSett ngs().set ndexH ghFreqTermPa rs(hfPa r);
    put ntoF eldConf gs( dMapp ng.getF eld D(f eldNa ), conf g);
    return t ;
  }

  /**
   * Mark t  f eld to have ordered term d ct onary.
   *  n Lucene, term d ct onary  s sorted.  n Earlyb rd, term d ct onary order  s not
   * guaranteed unless t   s turned on.
   */
  publ c f nal Sc maBu lder w hOrderedTerms(Str ng f eldNa ) {
     f (!should ncludeF eld(f eldNa )) {
      return t ;
    }
    Thr ftF eldSett ngs sett ngs =
        sc ma.getF eldConf gs().get( dMapp ng.getF eld D(f eldNa )).getSett ngs();

    sett ngs.get ndexedF eldSett ngs().setSupportOrderedTerms(true);
    return t ;
  }

  /**
   * Support lookup of term text by term  d  n t  term d ct onary.
   */
  publ c f nal Sc maBu lder w hTermTextLookup(Str ng f eldNa ) {
     f (!should ncludeF eld(f eldNa )) {
      return t ;
    }
    Thr ftF eldSett ngs sett ngs =
        sc ma.getF eldConf gs().get( dMapp ng.getF eld D(f eldNa )).getSett ngs();

    sett ngs.get ndexedF eldSett ngs().setSupportTermTextLookup(true);
    return t ;
  }

  /**
   * Add a text f eld that  s pre-token zed, so not analyzed aga n  n t   ndex (e.g. Earlyb rd).
   *
   * Note that t  token streams MUST be created us ng t  attr butes def ned  n
   * {@l nk com.tw ter.search.common.ut l.text.T etTokenStreamSer al zer}.
   */
  publ c f nal Sc maBu lder w hPretoken zedTextF eld(
      Str ng f eldNa ,
      boolean addHfPa r fHfF eldsArePresent) {
     f (!should ncludeF eld(f eldNa )) {
      return t ;
    }
    Thr ftF eldConf gurat on conf g = new Thr ftF eldConf gurat on(f eldNa )
        .setSett ngs(getDefaultPretoken zedSett ngs(
            Thr ft ndexOpt ons.DOCS_AND_FREQS_AND_POS T ONS));
    put ntoF eldConf gs( dMapp ng.getF eld D(f eldNa ), conf g);
    // Add hfPa r f elds only  f t y ex st  n t  sc ma for t  cluster
     f (addHfPa r fHfF eldsArePresent) {
      // Add hfPa r f elds only  f t y ex st  n t  sc ma for t  cluster
      boolean hfPa r = should ncludeF eld( mmutableSc ma.HF_TERM_PA RS_F ELD)
                       && should ncludeF eld( mmutableSc ma.HF_PHRASE_PA RS_F ELD);
      conf g.getSett ngs().get ndexedF eldSett ngs().set ndexH ghFreqTermPa rs(hfPa r);
    }
    return t ;
  }

  /**
   * Add a feature conf gurat on
   */
  publ c f nal Sc maBu lder w hFeatureConf gurat on(Str ng baseF eldNa , Str ng v ewNa ,
                                                      FeatureConf gurat on featureConf gurat on) {
    return w hColumnStr deF eldV ew(
        v ewNa ,
        // Default ng all encoded t et features to  nt s nce t  underly ng encoded t et features
        // are  nts.
        Thr ftCSFType. NT,
        featureConf gurat on.getOutputType(),
        baseF eldNa ,
        featureConf gurat on.getValue ndex(),
        featureConf gurat on.getB StartPos  on(),
        featureConf gurat on.getB Length(),
        featureConf gurat on.getFeatureNormal zat onType(),
        featureConf gurat on.getUpdateConstra nts()
    );
  }

  /**
   * Add a long f eld  n sc ma. T  f eld uses LongTermAttr bute.
   */
  pr vate Sc maBu lder addLongTermF eld(Str ng f eldNa , boolean useSortableEncod ng) {
     f (!should ncludeF eld(f eldNa )) {
      return t ;
    }
    Thr ftF eldSett ngs longTermSett ngs = getEarlyb rdNu r cF eldSett ngs();
    Thr ftTokenStreamSer al zer tokenStreamSer al zer =
        new Thr ftTokenStreamSer al zer(tokenStreamSer al zerVers on);
    tokenStreamSer al zer.setAttr buteSer al zerClassNa s(
         mmutableL st.<Str ng>of(LongTermAttr buteSer al zer.class.getNa ()));
    longTermSett ngs.get ndexedF eldSett ngs().setTokenStreamSer al zer(tokenStreamSer al zer);

    Thr ft ndexedNu r cF eldSett ngs nu r cF eldSett ngs =
        new Thr ft ndexedNu r cF eldSett ngs(true);
    nu r cF eldSett ngs.setNu r cType(Thr ftNu r cType.LONG);
    nu r cF eldSett ngs.setUseSortableEncod ng(useSortableEncod ng);
    longTermSett ngs.get ndexedF eldSett ngs().setNu r cF eldSett ngs(nu r cF eldSett ngs);

    put ntoF eldConf gs( dMapp ng.getF eld D(f eldNa ),
        new Thr ftF eldConf gurat on(f eldNa ).setSett ngs(longTermSett ngs));
    return t ;
  }

  publ c f nal Sc maBu lder w hSortableLongTermF eld(Str ng f eldNa ) {
    return addLongTermF eld(f eldNa , true);
  }

  publ c f nal Sc maBu lder w hLongTermF eld(Str ng f eldNa ) {
    return addLongTermF eld(f eldNa , false);
  }

  /**
   * Add an  nt f eld  n sc ma. T  f eld uses  ntTermAttr bute.
   */
  publ c f nal Sc maBu lder w h ntTermF eld(Str ng f eldNa ) {
     f (!should ncludeF eld(f eldNa )) {
      return t ;
    }
    Thr ftF eldSett ngs  ntTermSett ngs = getEarlyb rdNu r cF eldSett ngs();
    Thr ftTokenStreamSer al zer attr buteSer al zer =
        new Thr ftTokenStreamSer al zer(tokenStreamSer al zerVers on);
    attr buteSer al zer.setAttr buteSer al zerClassNa s(
         mmutableL st.<Str ng>of( ntTermAttr buteSer al zer.class.getNa ()));
     ntTermSett ngs.get ndexedF eldSett ngs().setTokenStreamSer al zer(attr buteSer al zer);

    Thr ft ndexedNu r cF eldSett ngs nu r cF eldSett ngs =
        new Thr ft ndexedNu r cF eldSett ngs(true);
    nu r cF eldSett ngs.setNu r cType(Thr ftNu r cType. NT);
     ntTermSett ngs.get ndexedF eldSett ngs().setNu r cF eldSett ngs(nu r cF eldSett ngs);

    put ntoF eldConf gs( dMapp ng.getF eld D(f eldNa ),
        new Thr ftF eldConf gurat on(f eldNa ).setSett ngs( ntTermSett ngs));
    return t ;
  }

  /**
   * T  l ne and ExpertSearch uses
   * {@l nk com.tw ter.search.common.ut l.analys s.Payload  ghtedToken zer} to store   ghted
   * values.
   *
   * E.g. for t  PRODUCED_LANGUAGES and CONSUMED_LANGUAGES f elds, t y conta n not a s ngle,
   * value, but  nstead a l st of values w h a   ght assoc ated w h each value.
   *
   * T   thod adds an  ndexed f eld that uses
   * {@l nk com.tw ter.search.common.ut l.analys s.Payload  ghtedToken zer}.
   */
  publ c f nal Sc maBu lder w hCharTermPayload  ghtedF eld(Str ng f eldNa ) {
    Thr ftF eldConf gurat on conf g = new Thr ftF eldConf gurat on(f eldNa )
        .setSett ngs(getPayload  ghtedSett ngs(Thr ft ndexOpt ons.DOCS_AND_FREQS_AND_POS T ONS));
    put ntoF eldConf gs( dMapp ng.getF eld D(f eldNa ), conf g);
    return t ;
  }

  /**
   * Set t  vers on and descr pt on of t  sc ma.
   */
  publ c f nal Sc maBu lder w hSc maVers on(
       nt majorVers onNumber,
       nt m norVers onNumber,
      Str ng vers onDesc,
      boolean  sOff c al) {
    sc ma.setMajorVers onNumber(majorVers onNumber);
    sc ma.setM norVers onNumber(m norVers onNumber);

    sc ma.setVers on(majorVers onNumber + ":" + vers onDesc);
    sc ma.setVers on sOff c al( sOff c al);

    return t ;
  }

  publ c f nal Sc maBu lder w hSc maVers on(
       nt majorVers onNumber,
      Str ng vers onDesc,
      boolean  sOff c al) {
    return w hSc maVers on(majorVers onNumber, 0, vers onDesc,  sOff c al);
  }

  protected vo d put ntoF eldConf gs( nt  d, Thr ftF eldConf gurat on conf g) {
     f (sc ma.getF eldConf gs() != null && sc ma.getF eldConf gs().conta nsKey( d)) {
      throw new  llegalStateExcept on("Already have a Thr ftF eldConf gurat on for f eld  d " +  d);
    }

     f (f eldNa Set.conta ns(conf g.getF eldNa ())) {
      throw new  llegalStateExcept on("Already have a Thr ftF eldConf gurat on for f eld "
          + conf g.getF eldNa ());
    }
    f eldNa Set.add(conf g.getF eldNa ());
    sc ma.putToF eldConf gs( d, conf g);
  }

  // Default f eld sett ngs. Most f eld sett ngs are s m lar to t .
  protected Thr ftF eldSett ngs getDefaultSett ngs(Thr ft ndexOpt ons  ndexOpt on) {
    return getDefaultSett ngs( ndexOpt on, false);
  }

  protected Thr ftF eldSett ngs getDefaultSett ngs(Thr ft ndexOpt ons  ndexOpt on,
                                                   boolean supportOutOfOrderAppends) {
    Thr ftF eldSett ngs f eldSett ngs = new Thr ftF eldSett ngs();
    Thr ft ndexedF eldSett ngs  ndexedF eldSett ngs = new Thr ft ndexedF eldSett ngs();
     ndexedF eldSett ngs
        .set ndexed(true)
        .setStored(false)
        .setToken zed(false)
        .setStoreTermVectors(false)
        .setStoreTermVectorOffsets(false)
        .setStoreTermVectorPayloads(false)
        .setStoreTermVectorPos  ons(false)
        .setSupportOutOfOrderAppends(supportOutOfOrderAppends)
        .set ndexOpt ons( ndexOpt on)
        .setOm Norms(true); // All Earlyb rd f elds om  norms.
    f eldSett ngs.set ndexedF eldSett ngs( ndexedF eldSett ngs);
    return f eldSett ngs;
  }

  /**
   * Default f eld sett ngs for f elds that are pretoken zed
   *
   * T  f elds that use t se sett ngs w ll need to be token zed us ng a ser al zer w h t 
   * attr butes def ned  n {@l nk com.tw ter.search.common.ut l.text.T etTokenStreamSer al zer}.
   */
  protected f nal Thr ftF eldSett ngs getDefaultPretoken zedSett ngs(
      Thr ft ndexOpt ons  ndexOpt on) {
    Thr ftF eldSett ngs f eldSett ngs = getDefaultSett ngs( ndexOpt on);
    f eldSett ngs.get ndexedF eldSett ngs().setToken zed(true);
    Thr ftTokenStreamSer al zer attr buteSer al zer =
        new Thr ftTokenStreamSer al zer(tokenStreamSer al zerVers on);
    attr buteSer al zer.setAttr buteSer al zerClassNa s(
         mmutableL st.<Str ng>of(
            CharSequenceTermAttr buteSer al zer.class.getNa (),
            Pos  on ncre ntAttr buteSer al zer.class.getNa (),
            TokenTypeAttr buteSer al zer.class.getNa ()));

    f eldSett ngs.get ndexedF eldSett ngs().setTokenStreamSer al zer(attr buteSer al zer);
    return f eldSett ngs;
  }

  protected f nal Thr ftF eldSett ngs getPretoken zedNoPos  onF eldSett ng() {
    return getDefaultPretoken zedSett ngs(Thr ft ndexOpt ons.DOCS_AND_FREQS);
  }

  protected f nal Thr ftF eldSett ngs getNoPos  onNoFreqSett ngs() {
    return getNoPos  onNoFreqSett ngs(false);
  }

  protected f nal Thr ftF eldSett ngs getNoPos  onNoFreqSett ngs(
      boolean supportOutOfOrderAppends) {
    return getDefaultSett ngs(Thr ft ndexOpt ons.DOCS_ONLY, supportOutOfOrderAppends);
  }

  protected f nal Thr ftF eldSett ngs getEarlyb rdNu r cF eldSett ngs() {
    // Supposedly nu r c f elds are not token zed.
    // Ho ver, Earlyb rd uses S ngleTokenTokenStream to handle  nt/long f elds.
    // So   need to set  ndexed to true for t se f elds.
    Thr ftF eldSett ngs sett ngs = getNoPos  onNoFreqSett ngs();
    sett ngs.get ndexedF eldSett ngs().setToken zed(true);
    return sett ngs;
  }

  pr vate Thr ftF eldSett ngs getPayload  ghtedSett ngs(Thr ft ndexOpt ons  ndexOpt on) {
    Thr ftF eldSett ngs f eldSett ngs = getDefaultSett ngs( ndexOpt on);
    f eldSett ngs.get ndexedF eldSett ngs().setToken zed(true);
    Thr ftTokenStreamSer al zer attr buteSer al zer =
        new Thr ftTokenStreamSer al zer(tokenStreamSer al zerVers on);
    attr buteSer al zer.setAttr buteSer al zerClassNa s(
         mmutableL st.<Str ng>of(CharTermAttr buteSer al zer.class.getNa (),
            Pos  on ncre ntAttr buteSer al zer.class.getNa (),
            PayloadAttr buteSer al zer.class.getNa ()));
    f eldSett ngs.get ndexedF eldSett ngs().setTokenStreamSer al zer(attr buteSer al zer);
    return f eldSett ngs;
  }

  protected boolean should ncludeF eld(Str ng f eldNa ) {
    return true;
  }
}
