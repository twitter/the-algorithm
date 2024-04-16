package com.tw ter.search.common.converter.earlyb rd;

 mport java. o. OExcept on;
 mport java.ut l.Date;
 mport java.ut l.L st;
 mport java.ut l.Opt onal;
 mport javax.annotat on.concurrent.NotThreadSafe;

 mport com.google.common.base.Precond  ons;

 mport org.apac .commons.collect ons.Collect onUt ls;
 mport org.joda.t  .DateT  ;
 mport org.joda.t  .DateT  Zone;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers on;
 mport com.tw ter.search.common.converter.earlyb rd.EncodedFeatureBu lder.T etFeatureW hEncodeFeatures;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Place;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Potent alLocat on;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Prof leGeoEnr ch nt;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftVers onedEvents;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Vers onedT etFeatures;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.part  on ng.snowflakeparser.Snowflake dParser;
 mport com.tw ter.search.common.relevance.ent  es.GeoObject;
 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssage;
 mport com.tw ter.search.common.relevance.ent  es.Tw terQuoted ssage;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdEncodedFeatures;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdThr ftDocu ntBu lder;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftDocu nt;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndex ngEvent;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndex ngEventType;
 mport com.tw ter.search.common.ut l.spat al.GeoUt l;
 mport com.tw ter.search.common.ut l.text.Normal zer lper;
 mport com.tw ter.t etyp e.thr ftjava.ComposerS ce;

/**
 * Converts a Tw ter ssage  nto a Thr ftVers onedEvents. T   s only respons ble for data that
 *  s ava lable  m d ately w n a T et  s created. So  data, l ke URL data,  sn't ava lable
 *  m d ately, and so    s processed later,  n t  Delayed ndex ngConverter and sent as an
 * update.  n order to ach eve t    create t  docu nt  n 2 passes:
 *
 * 1. Bas c ndex ngConverter bu lds thr ftVers onedEvents w h t  f elds that do not requ re
 * external serv ces.
 *
 * 2. Delayed ndex ngConverter bu lds all t  docu nt f elds depend ng on external serv ces, once
 * those serv ces have processed t  relevant T et and   have retr eved that data.
 */
@NotThreadSafe
publ c class Bas c ndex ngConverter {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Bas c ndex ngConverter.class);

  pr vate stat c f nal SearchCounter NUM_NULLCAST_FEATURE_FLAG_SET_TWEETS =
      SearchCounter.export("num_nullcast_feature_flag_set_t ets");
  pr vate stat c f nal SearchCounter NUM_NULLCAST_TWEETS =
      SearchCounter.export("num_nullcast_t ets");
  pr vate stat c f nal SearchCounter NUM_NON_NULLCAST_TWEETS =
      SearchCounter.export("num_non_nullcast_t ets");
  pr vate stat c f nal SearchCounter ADJUSTED_BAD_CREATED_AT_COUNTER =
      SearchCounter.export("adjusted_ ncorrect_created_at_t  stamp");
  pr vate stat c f nal SearchCounter  NCONS STENT_TWEET_ D_AND_CREATED_AT_MS =
      SearchCounter.export(" ncons stent_t et_ d_and_created_at_ms");
  pr vate stat c f nal SearchCounter NUM_SELF_THREAD_TWEETS =
      SearchCounter.export("num_self_thread_t ets");
  pr vate stat c f nal SearchCounter NUM_EXCLUS VE_TWEETS =
      SearchCounter.export("num_exclus ve_t ets");

  //  f a t et carr es a t  stamp smaller than t  t  stamp,   cons der t  t  stamp  nval d,
  // because tw ter does not even ex st back t n before: Sun, 01 Jan 2006 00:00:00 GMT
  pr vate stat c f nal long VAL D_CREAT ON_T ME_THRESHOLD_M LL S =
      new DateT  (2006, 1, 1, 0, 0, 0, DateT  Zone.UTC).getM ll s();

  pr vate f nal EncodedFeatureBu lder featureBu lder;
  pr vate f nal Sc ma sc ma;
  pr vate f nal Earlyb rdCluster cluster;

  publ c Bas c ndex ngConverter(Sc ma sc ma, Earlyb rdCluster cluster) {
    t .featureBu lder = new EncodedFeatureBu lder();
    t .sc ma = sc ma;
    t .cluster = cluster;
  }

  /**
   * T  funct on converts Tw ter ssage to Thr ftVers onedEvents, wh ch  s a gener c data
   * structure that can be consu d by Earlyb rd d rectly.
   */
  publ c Thr ftVers onedEvents convert ssageToThr ft(
      Tw ter ssage  ssage,
      boolean str ct,
      L st<Pengu nVers on> pengu nVers ons) throws  OExcept on {
    Precond  ons.c ckNotNull( ssage);
    Precond  ons.c ckNotNull(pengu nVers ons);

    Thr ftVers onedEvents vers onedEvents = new Thr ftVers onedEvents()
        .set d( ssage.get d());

     mmutableSc ma nterface sc maSnapshot = sc ma.getSc maSnapshot();

    for (Pengu nVers on pengu nVers on : pengu nVers ons) {
      Thr ftDocu nt docu nt =
          bu ldDocu ntForPengu nVers on(sc maSnapshot,  ssage, str ct, pengu nVers on);

      Thr ft ndex ngEvent thr ft ndex ngEvent = new Thr ft ndex ngEvent()
          .setDocu nt(docu nt)
          .setEventType(Thr ft ndex ngEventType. NSERT)
          .setSort d( ssage.get d());
       ssage.getFromUserTw ter d().map(thr ft ndex ngEvent::setU d);
      vers onedEvents.putToVers onedEvents(pengu nVers on.getByteValue(), thr ft ndex ngEvent);
    }

    return vers onedEvents;
  }

  pr vate Thr ftDocu nt bu ldDocu ntForPengu nVers on(
       mmutableSc ma nterface sc maSnapshot,
      Tw ter ssage  ssage,
      boolean str ct,
      Pengu nVers on pengu nVers on) throws  OExcept on {
    T etFeatureW hEncodeFeatures t etFeature =
        featureBu lder.createT etFeaturesFromTw ter ssage(
             ssage, pengu nVers on, sc maSnapshot);

    Earlyb rdThr ftDocu ntBu lder bu lder =
        bu ldBas cF elds( ssage, sc maSnapshot, cluster, t etFeature);

    bu ldUserF elds(bu lder,  ssage, t etFeature.vers onedFeatures, pengu nVers on);
    bu ldGeoF elds(bu lder,  ssage, t etFeature.vers onedFeatures);
    bu ldRet etAndReplyF elds(bu lder,  ssage, str ct);
    bu ldQuotesF elds(bu lder,  ssage);
    bu ldVers onedFeatureF elds(bu lder, t etFeature.vers onedFeatures);
    bu ldAnnotat onF elds(bu lder,  ssage);
    bu ldNormal zedM nEngage ntF elds(bu lder, t etFeature.encodedFeatures, cluster);
    bu ldD rectedAtF elds(bu lder,  ssage);

    bu lder.w hSpace dF elds( ssage.getSpace ds());

    return bu lder.bu ld();
  }

  /**
   * Bu ld t  bas c f elds for a t et.
   */
  publ c stat c Earlyb rdThr ftDocu ntBu lder bu ldBas cF elds(
      Tw ter ssage  ssage,
       mmutableSc ma nterface sc maSnapshot,
      Earlyb rdCluster cluster,
      T etFeatureW hEncodeFeatures t etFeature) {
    Earlyb rdEncodedFeatures extendedEncodedFeatures = t etFeature.extendedEncodedFeatures;
     f (extendedEncodedFeatures == null && Earlyb rdCluster. sTw ter moryFormatCluster(cluster)) {
      extendedEncodedFeatures = Earlyb rdEncodedFeatures.newEncodedT etFeatures(
          sc maSnapshot, Earlyb rdF eldConstant.EXTENDED_ENCODED_TWEET_FEATURES_F ELD);
    }
    Earlyb rdThr ftDocu ntBu lder bu lder = new Earlyb rdThr ftDocu ntBu lder(
        t etFeature.encodedFeatures,
        extendedEncodedFeatures,
        new Earlyb rdF eldConstants(),
        sc maSnapshot);

    bu lder.w h D( ssage.get d());

    f nal Date createdAt =  ssage.getDate();
    long createdAtMs = createdAt == null ? 0L : createdAt.getT  ();

    createdAtMs = f xCreatedAtT  Stamp fNecessary( ssage.get d(), createdAtMs);

     f (createdAtMs > 0L) {
      bu lder.w hCreatedAt(( nt) (createdAtMs / 1000));
    }

    bu lder.w hT etS gnature(t etFeature.vers onedFeatures.getT etS gnature());

     f ( ssage.getConversat on d() > 0) {
      long conversat on d =  ssage.getConversat on d();
      bu lder.w hLongF eld(
          Earlyb rdF eldConstant.CONVERSAT ON_ D_CSF.getF eldNa (), conversat on d);
      //   only  ndex conversat on  D w n    s d fferent from t  t et  D.
       f ( ssage.get d() != conversat on d) {
        bu lder.w hLongF eld(
            Earlyb rdF eldConstant.CONVERSAT ON_ D_F ELD.getF eldNa (), conversat on d);
      }
    }

     f ( ssage.getComposerS ce(). sPresent()) {
      ComposerS ce composerS ce =  ssage.getComposerS ce().get();
      bu lder.w h ntF eld(
          Earlyb rdF eldConstant.COMPOSER_SOURCE.getF eldNa (), composerS ce.getValue());
       f (composerS ce == ComposerS ce.CAMERA) {
        bu lder.w hCa raComposerS ceFlag();
      }
    }

    Earlyb rdEncodedFeatures encodedFeatures = t etFeature.encodedFeatures;
     f (encodedFeatures. sFlagSet(Earlyb rdF eldConstant.FROM_VER F ED_ACCOUNT_FLAG)) {
      bu lder.addF lter nternalF eldTerm(Earlyb rdF eldConstant.VER F ED_F LTER_TERM);
    }
     f (encodedFeatures. sFlagSet(Earlyb rdF eldConstant.FROM_BLUE_VER F ED_ACCOUNT_FLAG)) {
      bu lder.addF lter nternalF eldTerm(Earlyb rdF eldConstant.BLUE_VER F ED_F LTER_TERM);
    }

     f (encodedFeatures. sFlagSet(Earlyb rdF eldConstant. S_OFFENS VE_FLAG)) {
      bu lder.w hOffens veFlag();
    }

     f ( ssage.getNullcast()) {
      NUM_NULLCAST_TWEETS. ncre nt();
      bu lder.addF lter nternalF eldTerm(Earlyb rdF eldConstant.NULLCAST_F LTER_TERM);
    } else {
      NUM_NON_NULLCAST_TWEETS. ncre nt();
    }
     f (encodedFeatures. sFlagSet(Earlyb rdF eldConstant. S_NULLCAST_FLAG)) {
      NUM_NULLCAST_FEATURE_FLAG_SET_TWEETS. ncre nt();
    }
     f ( ssage. sSelfThread()) {
      bu lder.addF lter nternalF eldTerm(
          Earlyb rdF eldConstant.SELF_THREAD_F LTER_TERM);
      NUM_SELF_THREAD_TWEETS. ncre nt();
    }

     f ( ssage. sExclus ve()) {
      bu lder.addF lter nternalF eldTerm(Earlyb rdF eldConstant.EXCLUS VE_F LTER_TERM);
      bu lder.w hLongF eld(
          Earlyb rdF eldConstant.EXCLUS VE_CONVERSAT ON_AUTHOR_ D_CSF.getF eldNa (),
           ssage.getExclus veConversat onAuthor d());
      NUM_EXCLUS VE_TWEETS. ncre nt();
    }

    bu lder.w hLanguageCodes( ssage.getLanguage(),  ssage.getBCP47LanguageTag());

    return bu lder;
  }

  /**
   * Bu ld t  user f elds.
   */
  publ c stat c vo d bu ldUserF elds(
      Earlyb rdThr ftDocu ntBu lder bu lder,
      Tw ter ssage  ssage,
      Vers onedT etFeatures vers onedT etFeatures,
      Pengu nVers on pengu nVers on) {
    // 1. Set all t  from user f elds.
     f ( ssage.getFromUserTw ter d(). sPresent()) {
      bu lder.w hLongF eld(Earlyb rdF eldConstant.FROM_USER_ D_F ELD.getF eldNa (),
           ssage.getFromUserTw ter d().get())
      // CSF
      .w hLongF eld(Earlyb rdF eldConstant.FROM_USER_ D_CSF.getF eldNa (),
           ssage.getFromUserTw ter d().get());
    } else {
      LOG.warn("fromUserTw ter d  s not set  n Tw ter ssage! Status  d: " +  ssage.get d());
    }

     f ( ssage.getFromUserScreenNa (). sPresent()) {
      Str ng fromUser =  ssage.getFromUserScreenNa ().get();
      Str ng normal zedFromUser =
          Normal zer lper.normal zeW hUnknownLocale(fromUser, pengu nVers on);

      bu lder
          .w hWh eSpaceToken zedScreenNa F eld(
              Earlyb rdF eldConstant.TOKEN ZED_FROM_USER_F ELD.getF eldNa (),
              normal zedFromUser)
          .w hStr ngF eld(Earlyb rdF eldConstant.FROM_USER_F ELD.getF eldNa (),
              normal zedFromUser);

       f ( ssage.getToken zedFromUserScreenNa (). sPresent()) {
        bu lder.w hCa lCaseToken zedScreenNa F eld(
            Earlyb rdF eldConstant.CAMELCASE_USER_HANDLE_F ELD.getF eldNa (),
            fromUser,
            normal zedFromUser,
             ssage.getToken zedFromUserScreenNa ().get());
      }
    }

    Opt onal<Str ng> toUserScreenNa  =  ssage.getToUserLo rcasedScreenNa ();
     f (toUserScreenNa . sPresent() && !toUserScreenNa .get(). sEmpty()) {
      bu lder.w hStr ngF eld(
          Earlyb rdF eldConstant.TO_USER_F ELD.getF eldNa (),
          Normal zer lper.normal zeW hUnknownLocale(toUserScreenNa .get(), pengu nVers on));
    }

     f (vers onedT etFeatures. sSetUserD splayNa TokenStreamText()) {
      bu lder.w hTokenStreamF eld(Earlyb rdF eldConstant.TOKEN ZED_USER_NAME_F ELD.getF eldNa (),
          vers onedT etFeatures.getUserD splayNa TokenStreamText(),
          vers onedT etFeatures.getUserD splayNa TokenStream());
    }
  }

  /**
   * Bu ld t  geo f elds.
   */
  publ c stat c vo d bu ldGeoF elds(
      Earlyb rdThr ftDocu ntBu lder bu lder,
      Tw ter ssage  ssage,
      Vers onedT etFeatures vers onedT etFeatures) {
    double lat = GeoUt l. LLEGAL_LATLON;
    double lon = GeoUt l. LLEGAL_LATLON;
     f ( ssage.getGeoLocat on() != null) {
      GeoObject locat on =  ssage.getGeoLocat on();
      bu lder.w hGeoF eld(Earlyb rdF eldConstant.GEO_HASH_F ELD.getF eldNa (),
          locat on.getLat ude(), locat on.getLong ude(), locat on.getAccuracy());

       f (locat on.getS ce() != null) {
        bu lder.w hStr ngF eld(Earlyb rdF eldConstant. NTERNAL_F ELD.getF eldNa (),
            Earlyb rdF eldConstants.formatGeoType(locat on.getS ce()));
      }

       f (GeoUt l.val dateGeoCoord nates(locat on.getLat ude(), locat on.getLong ude())) {
        lat = locat on.getLat ude();
        lon = locat on.getLong ude();
      }
    }

    // See SEARCH-14317 for  nvest gat on on how much space geo f led  s used  n arch ve cluster.
    //  n lucene arch ves, t  CSF  s needed regardless of w t r geoLocat on  s set.
    bu lder.w hLatLonCSF(lat, lon);

     f (vers onedT etFeatures. sSetToken zedPlace()) {
      Place place = vers onedT etFeatures.getToken zedPlace();
      Precond  ons.c ckArgu nt(place. sSet d(), "Place  D not set for t et "
          +  ssage.get d());
      Precond  ons.c ckArgu nt(place. sSetFullNa (),
          "Place full na  not set for t et " +  ssage.get d());
      bu lder.addF lter nternalF eldTerm(Earlyb rdF eldConstant.PLACE_ D_F ELD.getF eldNa ());
      bu lder
          .w hStr ngF eld(Earlyb rdF eldConstant.PLACE_ D_F ELD.getF eldNa (), place.get d())
          .w hStr ngF eld(Earlyb rdF eldConstant.PLACE_FULL_NAME_F ELD.getF eldNa (),
              place.getFullNa ());
       f (place. sSetCountryCode()) {
        bu lder.w hStr ngF eld(Earlyb rdF eldConstant.PLACE_COUNTRY_CODE_F ELD.getF eldNa (),
            place.getCountryCode());
      }
    }

     f (vers onedT etFeatures. sSetToken zedProf leGeoEnr ch nt()) {
      Prof leGeoEnr ch nt prof leGeoEnr ch nt =
          vers onedT etFeatures.getToken zedProf leGeoEnr ch nt();
      Precond  ons.c ckArgu nt(
          prof leGeoEnr ch nt. sSetPotent alLocat ons(),
          "Prof leGeoEnr ch nt.potent alLocat ons not set for t et "
              +  ssage.get d());
      L st<Potent alLocat on> potent alLocat ons = prof leGeoEnr ch nt.getPotent alLocat ons();
      Precond  ons.c ckArgu nt(
          !potent alLocat ons. sEmpty(),
          "Found t et w h an empty Prof leGeoEnr ch nt.potent alLocat ons: "
              +  ssage.get d());
      bu lder.addF lter nternalF eldTerm(Earlyb rdF eldConstant.PROF LE_GEO_F LTER_TERM);
      for (Potent alLocat on potent alLocat on : potent alLocat ons) {
         f (potent alLocat on. sSetCountryCode()) {
          bu lder.w hStr ngF eld(
              Earlyb rdF eldConstant.PROF LE_GEO_COUNTRY_CODE_F ELD.getF eldNa (),
              potent alLocat on.getCountryCode());
        }
         f (potent alLocat on. sSetReg on()) {
          bu lder.w hStr ngF eld(Earlyb rdF eldConstant.PROF LE_GEO_REG ON_F ELD.getF eldNa (),
              potent alLocat on.getReg on());
        }
         f (potent alLocat on. sSetLocal y()) {
          bu lder.w hStr ngF eld(Earlyb rdF eldConstant.PROF LE_GEO_LOCAL TY_F ELD.getF eldNa (),
              potent alLocat on.getLocal y());
        }
      }
    }

    bu lder.w hPlacesF eld( ssage.getPlaces());
  }

  /**
   * Bu ld t  ret et and reply f elds.
   */
  publ c stat c vo d bu ldRet etAndReplyF elds(
      Earlyb rdThr ftDocu ntBu lder bu lder,
      Tw ter ssage  ssage,
      boolean str ct) {
    long ret etUser dVal = -1;
    long sharedStatus dVal = -1;
     f ( ssage.getRet et ssage() != null) {
       f ( ssage.getRet et ssage().getShared d() != null) {
        sharedStatus dVal =  ssage.getRet et ssage().getShared d();
      }
       f ( ssage.getRet et ssage().hasSharedUserTw ter d()) {
        ret etUser dVal =  ssage.getRet et ssage().getSharedUserTw ter d();
      }
    }

    long  nReplyToStatus dVal = -1;
    long  nReplyToUser dVal = -1;
     f ( ssage. sReply()) {
       f ( ssage.get nReplyToStatus d(). sPresent()) {
         nReplyToStatus dVal =  ssage.get nReplyToStatus d().get();
      }
       f ( ssage.getToUserTw ter d(). sPresent()) {
         nReplyToUser dVal =  ssage.getToUserTw ter d().get();
      }
    }

    bu ldRet etAndReplyF elds(
        ret etUser dVal,
        sharedStatus dVal,
         nReplyToStatus dVal,
         nReplyToUser dVal,
        str ct,
        bu lder);
  }

  /**
   * Bu ld t  quotes f elds.
   */
  publ c stat c vo d bu ldQuotesF elds(
      Earlyb rdThr ftDocu ntBu lder bu lder,
      Tw ter ssage  ssage) {
     f ( ssage.getQuoted ssage() != null) {
      Tw terQuoted ssage quoted =  ssage.getQuoted ssage();
       f (quoted != null && quoted.getQuotedStatus d() > 0 && quoted.getQuotedUser d() > 0) {
        bu lder.w hQuote(quoted.getQuotedStatus d(), quoted.getQuotedUser d());
      }
    }
  }

  /**
   * Bu ld d rected at f eld.
   */
  publ c stat c vo d bu ldD rectedAtF elds(
      Earlyb rdThr ftDocu ntBu lder bu lder,
      Tw ter ssage  ssage) {
     f ( ssage.getD rectedAtUser d(). sPresent() &&  ssage.getD rectedAtUser d().get() > 0) {
      bu lder.w hD rectedAtUser( ssage.getD rectedAtUser d().get());
      bu lder.addF lter nternalF eldTerm(Earlyb rdF eldConstant.D RECTED_AT_F LTER_TERM);
    }
  }

  /**
   * Bu ld t  vers oned features for a t et.
   */
  publ c stat c vo d bu ldVers onedFeatureF elds(
      Earlyb rdThr ftDocu ntBu lder bu lder,
      Vers onedT etFeatures vers onedT etFeatures) {
    bu lder
        .w hHashtagsF eld(vers onedT etFeatures.getHashtags())
        .w h nt onsF eld(vers onedT etFeatures.get nt ons())
        .w hStocksF elds(vers onedT etFeatures.getStocks())
        .w hResolvedL nksText(vers onedT etFeatures.getNormal zedResolvedUrlText())
        .w hTokenStreamF eld(Earlyb rdF eldConstant.TEXT_F ELD.getF eldNa (),
            vers onedT etFeatures.getT etTokenStreamText(),
            vers onedT etFeatures. sSetT etTokenStream()
                ? vers onedT etFeatures.getT etTokenStream() : null)
        .w hStr ngF eld(Earlyb rdF eldConstant.SOURCE_F ELD.getF eldNa (),
            vers onedT etFeatures.getS ce())
        .w hStr ngF eld(Earlyb rdF eldConstant.NORMAL ZED_SOURCE_F ELD.getF eldNa (),
            vers onedT etFeatures.getNormal zedS ce());

    //  nternal f elds for sm leys and quest on marks
     f (vers onedT etFeatures.hasPos  veSm ley) {
      bu lder.w hStr ngF eld(
          Earlyb rdF eldConstant. NTERNAL_F ELD.getF eldNa (),
          Earlyb rdF eldConstant.HAS_POS T VE_SM LEY);
    }
     f (vers onedT etFeatures.hasNegat veSm ley) {
      bu lder.w hStr ngF eld(
          Earlyb rdF eldConstant. NTERNAL_F ELD.getF eldNa (),
          Earlyb rdF eldConstant.HAS_NEGAT VE_SM LEY);
    }
     f (vers onedT etFeatures.hasQuest onMark) {
      bu lder.w hStr ngF eld(Earlyb rdF eldConstant.TEXT_F ELD.getF eldNa (),
          Earlyb rdThr ftDocu ntBu lder.QUEST ON_MARK);
    }
  }

  /**
   * Bu ld t  esc rb rd annotat ons for a t et.
   */
  publ c stat c vo d bu ldAnnotat onF elds(
      Earlyb rdThr ftDocu ntBu lder bu lder,
      Tw ter ssage  ssage) {
    L st<Tw ter ssage.Esc rb rdAnnotat on> esc rb rdAnnotat ons =
         ssage.getEsc rb rdAnnotat ons();
     f (Collect onUt ls. sEmpty(esc rb rdAnnotat ons)) {
      return;
    }

    bu lder.addFacetSk pL st(Earlyb rdF eldConstant.ENT TY_ D_F ELD.getF eldNa ());

    for (Tw ter ssage.Esc rb rdAnnotat on annotat on : esc rb rdAnnotat ons) {
      Str ng groupDoma nEnt y = Str ng.format("%d.%d.%d",
          annotat on.group d, annotat on.doma n d, annotat on.ent y d);
      Str ng doma nEnt y = Str ng.format("%d.%d", annotat on.doma n d, annotat on.ent y d);
      Str ng ent y = Str ng.format("%d", annotat on.ent y d);

      bu lder.w hStr ngF eld(Earlyb rdF eldConstant.ENT TY_ D_F ELD.getF eldNa (),
          groupDoma nEnt y);
      bu lder.w hStr ngF eld(Earlyb rdF eldConstant.ENT TY_ D_F ELD.getF eldNa (),
          doma nEnt y);
      bu lder.w hStr ngF eld(Earlyb rdF eldConstant.ENT TY_ D_F ELD.getF eldNa (),
          ent y);
    }
  }

  /**
   * Bu ld t  correct Thr ft ndex ngEvent's f elds based on ret et and reply status.
   */
  publ c stat c vo d bu ldRet etAndReplyF elds(
      long ret etUser dVal,
      long sharedStatus dVal,
      long  nReplyToStatus dVal,
      long  nReplyToUser dVal,
      boolean str ct,
      Earlyb rdThr ftDocu ntBu lder bu lder) {
    Opt onal<Long> ret etUser d = Opt onal.of(ret etUser dVal).f lter(x -> x > 0);
    Opt onal<Long> sharedStatus d = Opt onal.of(sharedStatus dVal).f lter(x -> x > 0);
    Opt onal<Long>  nReplyToUser d = Opt onal.of( nReplyToUser dVal).f lter(x -> x > 0);
    Opt onal<Long>  nReplyToStatus d = Opt onal.of( nReplyToStatus dVal).f lter(x -> x > 0);

    //   have s x comb nat ons  re. A T et can be
    //   1) a reply to anot r t et (t n   has both  n-reply-to-user- d and
    //       n-reply-to-status- d set),
    //   2) d rected-at a user (t n   only has  n-reply-to-user- d set),
    //   3) not a reply at all.
    // Add  onally,   may or may not be a Ret et ( f    s, t n   has ret et-user- d and
    // ret et-status- d set).
    //
    //   want to set so  f elds uncond  onally, and so  f elds (reference-author- d and
    // shared-status- d) depend ng on t  reply/ret et comb nat on.
    //
    // 1. Normal t et (not a reply, not a ret et). None of t  f elds should be set.
    //
    // 2. Reply to a t et (both  n-reply-to-user- d and  n-reply-to-status- d set).
    //    N_REPLY_TO_USER_ D_F ELD    should be set to  n-reply-to-user- d
    //   SHARED_STATUS_ D_CSF         should be set to  n-reply-to-status- d
    //    S_REPLY_FLAG                should be set
    //
    // 3. D rected-at a user (only  n-reply-to-user- d  s set).
    //    N_REPLY_TO_USER_ D_F ELD    should be set to  n-reply-to-user- d
    //    S_REPLY_FLAG                should be set
    //
    // 4. Ret et of a normal t et (ret et-user- d and ret et-status- d are set).
    //   RETWEET_SOURCE_USER_ D_F ELD should be set to ret et-user- d
    //   SHARED_STATUS_ D_CSF         should be set to ret et-status- d
    //    S_RETWEET_FLAG              should be set
    //
    // 5. Ret et of a reply (both  n-reply-to-user- d and  n-reply-to-status- d set,
    // ret et-user- d and ret et-status- d are set).
    //   RETWEET_SOURCE_USER_ D_F ELD should be set to ret et-user- d
    //   SHARED_STATUS_ D_CSF         should be set to ret et-status- d (ret et beats reply!)
    //    S_RETWEET_FLAG              should be set
    //    N_REPLY_TO_USER_ D_F ELD    should be set to  n-reply-to-user- d
    //    S_REPLY_FLAG                should NOT be set
    //
    // 6. Ret et of a d rected-at t et (only  n-reply-to-user- d  s set,
    // ret et-user- d and ret et-status- d are set).
    //   RETWEET_SOURCE_USER_ D_F ELD should be set to ret et-user- d
    //   SHARED_STATUS_ D_CSF         should be set to ret et-status- d
    //    S_RETWEET_FLAG              should be set
    //    N_REPLY_TO_USER_ D_F ELD    should be set to  n-reply-to-user- d
    //    S_REPLY_FLAG                should NOT be set
    //
    //  n ot r words:
    // SHARED_STATUS_ D_CSF log c:  f t   s a ret et SHARED_STATUS_ D_CSF should be set to
    // ret et-status- d, ot rw se  f  's a reply to a t et,   should be set to
    //  n-reply-to-status- d.

    Precond  ons.c ckState(ret etUser d. sPresent() == sharedStatus d. sPresent());

     f (ret etUser d. sPresent()) {
      bu lder.w hNat veRet et(ret etUser d.get(), sharedStatus d.get());

       f ( nReplyToUser d. sPresent()) {
        // Set  N_REPLY_TO_USER_ D_F ELD even  f t   s a ret et of a reply.
        bu lder.w h nReplyToUser D( nReplyToUser d.get());
      }
    } else {
      //  f t   s a ret et of a reply,   don't want to mark   as a reply, or overr de f elds
      // set by t  ret et log c.
      //  f   are  n t  branch, t   s not a ret et. Potent ally,   set t  reply flag,
      // and overr de shared-status- d and reference-author- d.

       f ( nReplyToStatus d. sPresent()) {
         f (str ct) {
          // Enforc ng that  f t   s a reply to a t et, t n   also has a repl ed-to user.
          Precond  ons.c ckState( nReplyToUser d. sPresent());
        }
        bu lder.w hReplyFlag();
        bu lder.w hLongF eld(
            Earlyb rdF eldConstant.SHARED_STATUS_ D_CSF.getF eldNa (),
             nReplyToStatus d.get());
        bu lder.w hLongF eld(
            Earlyb rdF eldConstant. N_REPLY_TO_TWEET_ D_F ELD.getF eldNa (),
             nReplyToStatus d.get());
      }
       f ( nReplyToUser d. sPresent()) {
        bu lder.w hReplyFlag();
        bu lder.w h nReplyToUser D( nReplyToUser d.get());
      }
    }
  }

  /**
   * Bu ld t  engage nt f elds.
   */
  publ c stat c vo d bu ldNormal zedM nEngage ntF elds(
      Earlyb rdThr ftDocu ntBu lder bu lder,
      Earlyb rdEncodedFeatures encodedFeatures,
      Earlyb rdCluster cluster) throws  OExcept on {
     f (Earlyb rdCluster. sArch ve(cluster)) {
       nt favor eCount = encodedFeatures.getFeatureValue(Earlyb rdF eldConstant.FAVOR TE_COUNT);
       nt ret etCount = encodedFeatures.getFeatureValue(Earlyb rdF eldConstant.RETWEET_COUNT);
       nt replyCount = encodedFeatures.getFeatureValue(Earlyb rdF eldConstant.REPLY_COUNT);
      bu lder
          .w hNormal zedM nEngage ntF eld(
              Earlyb rdF eldConstant.NORMAL ZED_FAVOR TE_COUNT_GREATER_THAN_OR_EQUAL_TO_F ELD
                  .getF eldNa (),
              favor eCount);
      bu lder
          .w hNormal zedM nEngage ntF eld(
              Earlyb rdF eldConstant.NORMAL ZED_RETWEET_COUNT_GREATER_THAN_OR_EQUAL_TO_F ELD
                  .getF eldNa (),
              ret etCount);
      bu lder
          .w hNormal zedM nEngage ntF eld(
              Earlyb rdF eldConstant.NORMAL ZED_REPLY_COUNT_GREATER_THAN_OR_EQUAL_TO_F ELD
                  .getF eldNa (),
              replyCount);
    }
  }

  /**
   * As seen  n SEARCH-5617,   so t  s have  ncorrect createdAt. T   thod tr es to f x t m
   * by extract ng creat on t   from snowflake w n poss ble.
   */
  publ c stat c long f xCreatedAtT  Stamp fNecessary(long  d, long createdAtMs) {
     f (createdAtMs < VAL D_CREAT ON_T ME_THRESHOLD_M LL S
        &&  d > Snowflake dParser.SNOWFLAKE_ D_LOWER_BOUND) {
      // T  t et has a snowflake  D, and   can extract t  stamp from t   D.
      ADJUSTED_BAD_CREATED_AT_COUNTER. ncre nt();
      return Snowflake dParser.getT  stampFromT et d( d);
    } else  f (!Snowflake dParser. sT et DAndCreatedAtCons stent( d, createdAtMs)) {
      LOG.error(
          "Found  ncons stent t et  D and created at t  stamp: [status D={}], [createdAtMs={}]",
           d, createdAtMs);
       NCONS STENT_TWEET_ D_AND_CREATED_AT_MS. ncre nt();
    }

    return createdAtMs;
  }
}
