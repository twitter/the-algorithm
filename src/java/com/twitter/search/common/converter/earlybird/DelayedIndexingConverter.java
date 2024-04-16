package com.tw ter.search.common.converter.earlyb rd;

 mport java. o. OExcept on;
 mport java.ut l.ArrayL st;
 mport java.ut l.L st;
 mport java.ut l.Locale;
 mport java.ut l.Map;
 mport java.ut l.Set;
 mport javax.annotat on.Nullable;

 mport com.google.common.base.Jo ner;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.L sts;

 mport org.apac .commons.lang.Str ngUt ls;
 mport org.apac .http.annotat on.NotThreadSafe;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.text.token.Token zedCharSequenceStream;
 mport com.tw ter.common.text.ut l.TokenStreamSer al zer;
 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers on;
 mport com.tw ter.cuad.ner.pla n.thr ftjava.Na dEnt y;
 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.search.common.constants.SearchCardType;
 mport com.tw ter.search.common.dec der.Dec derUt l;
 mport com.tw ter.search.common. ndex ng.thr ftjava.SearchCard2;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftExpandedUrl;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftVers onedEvents;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Tw terPhotoUrl;
 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssage;
 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssageUser;
 mport com.tw ter.search.common.relevance.features.T etTextFeatures;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdEncodedFeatures;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdThr ftDocu ntBu lder;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftDocu nt;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftF eld;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftF eldData;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndex ngEvent;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndex ngEventType;
 mport com.tw ter.search.common.ut l.lang.Thr ftLanguageUt l;
 mport com.tw ter.search.common.ut l.text.Language dent f er lper;
 mport com.tw ter.search.common.ut l.text.Normal zer lper;
 mport com.tw ter.search.common.ut l.text.Token zer lper;
 mport com.tw ter.search.common.ut l.text.Token zerResult;
 mport com.tw ter.search.common.ut l.text.T etTokenStreamSer al zer;
 mport com.tw ter.serv ce.sp derduck.gen. d aTypes;
 mport com.tw ter.search.common. tr cs.SearchCounter;

/**
 * Create and populate Thr ftVers onedEvents from t  URL data, card data, and na d ent  es
 * conta ned  n a Tw ter ssage. T  data  s delayed because t se serv ces take a few seconds
 * to process t ets, and   want to send t  bas c data ava lable  n t  Bas c ndex ngConverter as
 * soon as poss ble, so   send t  add  onal data a few seconds later, as an update.
 *
 * Prefer to add data and process ng to t  Bas c ndex ngConverter w n poss ble. Only add data  re
 *  f y  data s ce _requ res_ data from an external serv ce AND t  external serv ce takes at
 * least a few seconds to process new t ets.
 */
@NotThreadSafe
publ c class Delayed ndex ngConverter {
  pr vate stat c f nal SearchCounter NUM_TWEETS_W TH_CARD_URL =
      SearchCounter.export("t ets_w h_card_url");
  pr vate stat c f nal SearchCounter NUM_TWEETS_W TH_NUMER C_CARD_UR  =
      SearchCounter.export("t ets_w h_nu r c_card_ur ");
  pr vate stat c f nal SearchCounter NUM_TWEETS_W TH_ NVAL D_CARD_UR  =
      SearchCounter.export("t ets_w h_ nval d_card_ur ");
  pr vate stat c f nal SearchCounter TOTAL_URLS =
      SearchCounter.export("total_urls_on_t ets");
  pr vate stat c f nal SearchCounter MED A_URLS_ON_TWEETS =
      SearchCounter.export(" d a_urls_on_t ets");
  pr vate stat c f nal SearchCounter NON_MED A_URLS_ON_TWEETS =
      SearchCounter.export("non_ d a_urls_on_t ets");
  publ c stat c f nal Str ng  NDEX_URL_DESCR PT ON_AND_T TLE_DEC DER =
      " ndex_url_descr pt on_and_t le";

  pr vate stat c class Thr ftDocu ntW hEncodedT etFeatures {
    pr vate f nal Thr ftDocu nt docu nt;
    pr vate f nal Earlyb rdEncodedFeatures encodedFeatures;

    publ c Thr ftDocu ntW hEncodedT etFeatures(Thr ftDocu nt docu nt,
                                                  Earlyb rdEncodedFeatures encodedFeatures) {
      t .docu nt = docu nt;
      t .encodedFeatures = encodedFeatures;
    }

    publ c Thr ftDocu nt getDocu nt() {
      return docu nt;
    }

    publ c Earlyb rdEncodedFeatures getEncodedFeatures() {
      return encodedFeatures;
    }
  }

  // T  l st of all t  encoded_t et_features flags that m ght be updated by t  converter.
  // No extended_encoded_t et_features are updated (ot rw se t y should be  n t  l st too).
  pr vate stat c f nal L st<Earlyb rdF eldConstants.Earlyb rdF eldConstant> UPDATED_FLAGS =
      L sts.newArrayL st(
          Earlyb rdF eldConstants.Earlyb rdF eldConstant. S_OFFENS VE_FLAG,
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_L NK_FLAG,
          Earlyb rdF eldConstants.Earlyb rdF eldConstant. S_SENS T VE_CONTENT,
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.TEXT_SCORE,
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.TWEET_S GNATURE,
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.L NK_LANGUAGE,
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_ MAGE_URL_FLAG,
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_V DEO_URL_FLAG,
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_NEWS_URL_FLAG,
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_EXPANDO_CARD_FLAG,
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_MULT PLE_MED A_FLAG,
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_CARD_FLAG,
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_V S BLE_L NK_FLAG,
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_CONSUMER_V DEO_FLAG,
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_PRO_V DEO_FLAG,
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_V NE_FLAG,
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_PER SCOPE_FLAG,
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_NAT VE_ MAGE_FLAG
      );

  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Delayed ndex ngConverter.class);
  pr vate stat c f nal Str ng AMPL FY_CARD_NAME = "ampl fy";
  pr vate stat c f nal Str ng PLAYER_CARD_NAME = "player";

  pr vate f nal EncodedFeatureBu lder featureBu lder = new EncodedFeatureBu lder();

  pr vate f nal Sc ma sc ma;
  pr vate f nal Dec der dec der;

  publ c Delayed ndex ngConverter(Sc ma sc ma, Dec der dec der) {
    t .sc ma = sc ma;
    t .dec der = dec der;
  }

  /**
   * Converts t  g ven  ssage to two Thr ftVers onedEvents  nstances: t  f rst one  s a feature
   * update event for all l nk and card related flags, and t  second one  s t  append event that
   * m ght conta n updates to all l nk and card related f elds.
   *
   *   need to spl  t  updates to f elds and flags  nto two separate events because:
   *  - W n a t et  s created, earlyb rds get t  "ma n" event, wh ch does not have resolved URLs.
   *  - T n t  earlyb rds m ght get a feature update from t  s gnal  ngesters, mark ng t  t et
   *    as spam.
   *  - T n t   ngesters resolve t  URLs and send an update event. At t  po nt, t   ngesters
   *    need to send updates for l nk-related flags too (HAS_L NK_FLAG, etc.). And t re are a few
   *    ways to do t :
   *    1. Encode t se flags  nto encoded_t et_features and extended_encoded_t et_features and
   *       add t se f elds to t  update event. T  problem  s that earlyb rds w ll t n overr de
   *       t  encoded_t et_features ane extended_encoded_t et_features f elds  n t   ndex for
   *       t  t et, wh ch w ll overr de t  feature update t  earlyb rds got earl er, wh ch
   *        ans that a spam  t et m ght no longer be marked as spam  n t   ndex.
   *    2. Send updates only for t  flags that m ght've been updated by t  converter. S nce
   *       Thr ft ndex ngEvent already has a map of f eld -> value,   seems l ke t  natural place
   *       to add t se updates to. Ho ver, earlyb rds can correctly process flag updates only  f
   *       t y co   n a feature update event (PART AL_UPDATE). So   need to send t  f eld
   *       updates  n an OUT_OF_ORDER_UPDATE event, and t  flag updates  n a PART AL_UPDATE event.
   *
   *   need to send t  feature update event before t  append event to avo d  ssues l ke t  one
   *  n SEARCH-30919 w re t ets  re returned from t  card na  f eld  ndex before t  HAS_CARD
   * feature was updated to true.
   *
   * @param  ssage T  Tw ter ssage to convert.
   * @param pengu nVers ons T  Pengu n vers ons for wh ch Thr ft ndex ngEvents should be created.
   * @return An out of order update event for all l nk- and card-related f elds and a feature update
   *         event for all l nk- and card-related flags.
   */
  publ c L st<Thr ftVers onedEvents> convert ssageToOutOfOrderAppendAndFeatureUpdate(
      Tw ter ssage  ssage, L st<Pengu nVers on> pengu nVers ons) {
    Precond  ons.c ckNotNull( ssage);
    Precond  ons.c ckNotNull(pengu nVers ons);

    Thr ftVers onedEvents featureUpdateVers onedEvents = new Thr ftVers onedEvents();
    Thr ftVers onedEvents outOfOrderAppendVers onedEvents = new Thr ftVers onedEvents();
     mmutableSc ma nterface sc maSnapshot = sc ma.getSc maSnapshot();

    for (Pengu nVers on pengu nVers on : pengu nVers ons) {
      Thr ftDocu ntW hEncodedT etFeatures docu ntW hEncodedFeatures =
          bu ldDocu ntForPengu nVers on(sc maSnapshot,  ssage, pengu nVers on);

      Thr ft ndex ngEvent featureUpdateThr ft ndex ngEvent = new Thr ft ndex ngEvent();
      featureUpdateThr ft ndex ngEvent.setEventType(Thr ft ndex ngEventType.PART AL_UPDATE);
      featureUpdateThr ft ndex ngEvent.setU d( ssage.get d());
      featureUpdateThr ft ndex ngEvent.setDocu nt(
          bu ldFeatureUpdateDocu nt(docu ntW hEncodedFeatures.getEncodedFeatures()));
      featureUpdateVers onedEvents.putToVers onedEvents(
          pengu nVers on.getByteValue(), featureUpdateThr ft ndex ngEvent);

      Thr ft ndex ngEvent outOfOrderAppendThr ft ndex ngEvent = new Thr ft ndex ngEvent();
      outOfOrderAppendThr ft ndex ngEvent.setDocu nt(docu ntW hEncodedFeatures.getDocu nt());
      outOfOrderAppendThr ft ndex ngEvent.setEventType(Thr ft ndex ngEventType.OUT_OF_ORDER_APPEND);
       ssage.getFromUserTw ter d(). fPresent(outOfOrderAppendThr ft ndex ngEvent::setU d);
      outOfOrderAppendThr ft ndex ngEvent.setSort d( ssage.get d());
      outOfOrderAppendVers onedEvents.putToVers onedEvents(
          pengu nVers on.getByteValue(), outOfOrderAppendThr ft ndex ngEvent);
    }

    featureUpdateVers onedEvents.set d( ssage.get d());
    outOfOrderAppendVers onedEvents.set d( ssage.get d());

    return L sts.newArrayL st(featureUpdateVers onedEvents, outOfOrderAppendVers onedEvents);
  }

  pr vate Thr ftDocu nt bu ldFeatureUpdateDocu nt(Earlyb rdEncodedFeatures encodedFeatures) {
    Thr ftDocu nt docu nt = new Thr ftDocu nt();
    for (Earlyb rdF eldConstants.Earlyb rdF eldConstant flag : UPDATED_FLAGS) {
      Thr ftF eld f eld = new Thr ftF eld();
      f eld.setF eldConf g d(flag.getF eld d());
      f eld.setF eldData(new Thr ftF eldData().set ntValue(encodedFeatures.getFeatureValue(flag)));
      docu nt.addToF elds(f eld);
    }
    return docu nt;
  }

  pr vate Thr ftDocu ntW hEncodedT etFeatures bu ldDocu ntForPengu nVers on(
       mmutableSc ma nterface sc maSnapshot,
      Tw ter ssage  ssage,
      Pengu nVers on pengu nVers on) {

    Earlyb rdEncodedFeatures encodedFeatures = featureBu lder.createT etFeaturesFromTw ter ssage(
         ssage, pengu nVers on, sc maSnapshot).encodedFeatures;

    Earlyb rdThr ftDocu ntBu lder bu lder = new Earlyb rdThr ftDocu ntBu lder(
        encodedFeatures,
        null,
        new Earlyb rdF eldConstants(),
        sc maSnapshot);

    bu lder.setAddLatLonCSF(false);
    bu lder.w h D( ssage.get d());
    bu ldF eldsFromUrl nfo(bu lder,  ssage, pengu nVers on, encodedFeatures);
    bu ldCardF elds(bu lder,  ssage, pengu nVers on);
    bu ldNa dEnt yF elds(bu lder,  ssage);
    bu lder.w hT etS gnature( ssage.getT etS gnature(pengu nVers on));

    bu ldSpaceAdm nAndT leF elds(bu lder,  ssage, pengu nVers on);

    bu lder.setAddEncodedT etFeatures(false);

    return new Thr ftDocu ntW hEncodedT etFeatures(bu lder.bu ld(), encodedFeatures);
  }

  publ c stat c vo d bu ldNa dEnt yF elds(
      Earlyb rdThr ftDocu ntBu lder bu lder, Tw ter ssage  ssage) {
    for (Na dEnt y na dEnt y :  ssage.getNa dEnt  es()) {
      bu lder.w hNa dEnt y(na dEnt y);
    }
  }

  pr vate vo d bu ldF eldsFromUrl nfo(
      Earlyb rdThr ftDocu ntBu lder bu lder,
      Tw ter ssage  ssage,
      Pengu nVers on pengu nVers on,
      Earlyb rdEncodedFeatures encodedFeatures) {
    //   need to update t  RESOLVED_L NKS_TEXT_F ELD, s nce   m ght have new resolved URLs.
    // Use t  sa  log c as  n EncodedFeatureBu lder.java.
    T etTextFeatures textFeatures =  ssage.getT etTextFeatures(pengu nVers on);
    Str ng resolvedUrlsText = Jo ner.on(" ").sk pNulls().jo n(textFeatures.getResolvedUrlTokens());
    bu lder.w hResolvedL nksText(resolvedUrlsText);

    bu ldURLF elds(bu lder,  ssage, encodedFeatures);
    bu ldAnalyzedURLF elds(bu lder,  ssage, pengu nVers on);
  }

  pr vate vo d bu ldAnalyzedURLF elds(
      Earlyb rdThr ftDocu ntBu lder bu lder, Tw ter ssage  ssage, Pengu nVers on pengu nVers on
  ) {
    TOTAL_URLS.add( ssage.getExpandedUrls().s ze());
     f (Dec derUt l. sAva lableForRandomRec p ent(
        dec der,
         NDEX_URL_DESCR PT ON_AND_T TLE_DEC DER)) {
      for (Thr ftExpandedUrl expandedUrl :  ssage.getExpandedUrls()) {
      /*
        Consu r  d a URLs are added to t  expanded URLs  n
        T etEventParser lper.add d aEnt  esTo ssage. T se Tw ter.com  d a URLs conta n
        t  t et text as t  descr pt on and t  t le  s "<User Na > on Tw ter". T   s
        redundant  nformat on at best and m slead ng at worst.   w ll  gnore t se URLs to avo d
        pollut ng t  url_descr pt on and url_t le f eld as  ll as sav ng space.
       */
         f (!expandedUrl. sSetConsu r d a() || !expandedUrl. sConsu r d a()) {
          NON_MED A_URLS_ON_TWEETS. ncre nt();
           f (expandedUrl. sSetDescr pt on()) {
            bu ldT etToken zerToken zedF eld(bu lder,
                Earlyb rdF eldConstants.Earlyb rdF eldConstant.URL_DESCR PT ON_F ELD.getF eldNa (),
                expandedUrl.getDescr pt on(),
                pengu nVers on);
          }
           f (expandedUrl. sSetT le()) {
            bu ldT etToken zerToken zedF eld(bu lder,
                Earlyb rdF eldConstants.Earlyb rdF eldConstant.URL_T TLE_F ELD.getF eldNa (),
                expandedUrl.getT le(),
                pengu nVers on);
          }
        } else {
          MED A_URLS_ON_TWEETS. ncre nt();
        }
      }
    }
  }

  /**
   * Bu ld t  URL based f elds from a t et.
   */
  publ c stat c vo d bu ldURLF elds(
      Earlyb rdThr ftDocu ntBu lder bu lder,
      Tw ter ssage  ssage,
      Earlyb rdEncodedFeatures encodedFeatures
  ) {
    Map<Str ng, Thr ftExpandedUrl> expandedUrlMap =  ssage.getExpandedUrlMap();

    for (Thr ftExpandedUrl expandedUrl : expandedUrlMap.values()) {
       f (expandedUrl.get d aType() ==  d aTypes.NAT VE_ MAGE) {
        EncodedFeatureBu lder.addPhotoUrl( ssage, expandedUrl.getCanon calLastHopUrl());
      }
    }

    // now add all tw ter photos l nks that ca  w h t  t et's payload
    Map<Long, Str ng> photos =  ssage.getPhotoUrls();
    L st<Tw terPhotoUrl> photoURLs = new ArrayL st<>();
     f (photos != null) {
      for (Map.Entry<Long, Str ng> entry : photos.entrySet()) {
        Tw terPhotoUrl photo = new Tw terPhotoUrl(entry.getKey());
        Str ng  d aUrl = entry.getValue();
         f ( d aUrl != null) {
          photo.set d aUrl( d aUrl);
        }
        photoURLs.add(photo);
      }
    }

    try {
      bu lder
          .w hURLs(L sts.newArrayL st(expandedUrlMap.values()))
          .w hTw mgURLs(photoURLs);
    } catch ( OExcept on  oe) {
      LOG.error("URL f eld creat on threw an  OExcept on",  oe);
    }


     f (encodedFeatures. sFlagSet(
        Earlyb rdF eldConstants.Earlyb rdF eldConstant. S_OFFENS VE_FLAG)) {
      bu lder.w hOffens veFlag();
    }
     f (encodedFeatures. sFlagSet(
        Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_CONSUMER_V DEO_FLAG)) {
      bu lder.addF lter nternalF eldTerm(
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.CONSUMER_V DEO_F LTER_TERM);
    }
     f (encodedFeatures. sFlagSet(
        Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_PRO_V DEO_FLAG)) {
      bu lder.addF lter nternalF eldTerm(
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.PRO_V DEO_F LTER_TERM);
    }
     f (encodedFeatures. sFlagSet(Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_V NE_FLAG)) {
      bu lder.addF lter nternalF eldTerm(
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.V NE_F LTER_TERM);
    }
     f (encodedFeatures. sFlagSet(
        Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_PER SCOPE_FLAG)) {
      bu lder.addF lter nternalF eldTerm(
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.PER SCOPE_F LTER_TERM);
    }
  }

  /**
   * Bu ld t  card  nformat on  ns de Thr ft ndex ngEvent's f elds.
   */
  stat c vo d bu ldCardF elds(Earlyb rdThr ftDocu ntBu lder bu lder,
                              Tw ter ssage  ssage,
                              Pengu nVers on pengu nVers on) {
     f ( ssage.hasCard()) {
      SearchCard2 card = bu ldSearchCardFromTw ter ssage(
           ssage,
          T etTokenStreamSer al zer.getT etTokenStreamSer al zer(),
          pengu nVers on);
      bu ldCardFeatures( ssage.get d(), bu lder, card);
    }
  }

  pr vate stat c SearchCard2 bu ldSearchCardFromTw ter ssage(
      Tw ter ssage  ssage,
      TokenStreamSer al zer streamSer al zer,
      Pengu nVers on pengu nVers on) {
    SearchCard2 card = new SearchCard2();
    card.setCardNa ( ssage.getCardNa ());
     f ( ssage.getCardDoma n() != null) {
      card.setCardDoma n( ssage.getCardDoma n());
    }
     f ( ssage.getCardLang() != null) {
      card.setCardLang( ssage.getCardLang());
    }
     f ( ssage.getCardUrl() != null) {
      card.setCardUrl( ssage.getCardUrl());
    }

     f ( ssage.getCardT le() != null && ! ssage.getCardT le(). sEmpty()) {
      Str ng normal zedT le = Normal zer lper.normal ze(
           ssage.getCardT le(),  ssage.getLocale(), pengu nVers on);
      Token zerResult result = Token zer lper.token zeT et(
          normal zedT le,  ssage.getLocale(), pengu nVers on);
      Token zedCharSequenceStream tokenSeqStream = new Token zedCharSequenceStream();
      tokenSeqStream.reset(result.tokenSequence);
      try {
        card.setCardT leTokenStream(streamSer al zer.ser al ze(tokenSeqStream));
        card.setCardT leTokenStreamText(result.tokenSequence.toStr ng());
      } catch ( OExcept on e) {
        LOG.error("Tw terTokenStream ser al zat on error! Could not ser al ze card t le: "
            + result.tokenSequence);
        card.unsetCardT leTokenStream();
        card.unsetCardT leTokenStreamText();
      }
    }
     f ( ssage.getCardDescr pt on() != null && ! ssage.getCardDescr pt on(). sEmpty()) {
      Str ng normal zedDesc = Normal zer lper.normal ze(
           ssage.getCardDescr pt on(),  ssage.getLocale(), pengu nVers on);
      Token zerResult result = Token zer lper.token zeT et(
          normal zedDesc,  ssage.getLocale(), pengu nVers on);
      Token zedCharSequenceStream tokenSeqStream = new Token zedCharSequenceStream();
      tokenSeqStream.reset(result.tokenSequence);
      try {
        card.setCardDescr pt onTokenStream(streamSer al zer.ser al ze(tokenSeqStream));
        card.setCardDescr pt onTokenStreamText(result.tokenSequence.toStr ng());
      } catch ( OExcept on e) {
        LOG.error("Tw terTokenStream ser al zat on error! Could not ser al ze card descr pt on: "
            + result.tokenSequence);
        card.unsetCardDescr pt onTokenStream();
        card.unsetCardDescr pt onTokenStreamText();
      }
    }

    return card;
  }

  /**
   * Bu lds card features.
   */
  pr vate stat c vo d bu ldCardFeatures(
      long t et d, Earlyb rdThr ftDocu ntBu lder bu lder, SearchCard2 card) {
     f (card == null) {
      return;
    }
    bu lder
        .w hTokenStreamF eld(
            Earlyb rdF eldConstants.Earlyb rdF eldConstant.CARD_T TLE_F ELD.getF eldNa (),
            card.getCardT leTokenStreamText(),
            card. sSetCardT leTokenStream() ? card.getCardT leTokenStream() : null)
        .w hTokenStreamF eld(
            Earlyb rdF eldConstants.Earlyb rdF eldConstant.CARD_DESCR PT ON_F ELD.getF eldNa (),
            card.getCardDescr pt onTokenStreamText(),
            card. sSetCardDescr pt onTokenStream() ? card.getCardDescr pt onTokenStream() : null)
        .w hStr ngF eld(
            Earlyb rdF eldConstants.Earlyb rdF eldConstant.CARD_NAME_F ELD.getF eldNa (),
            card.getCardNa ())
        .w h ntF eld(
            Earlyb rdF eldConstants.Earlyb rdF eldConstant.CARD_TYPE_CSF_F ELD.getF eldNa (),
            SearchCardType.cardTypeFromStr ngNa (card.getCardNa ()).getByteValue());

     f (card.getCardLang() != null) {
      bu lder.w hStr ngF eld(
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.CARD_LANG.getF eldNa (),
          card.getCardLang()).w h ntF eld(
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.CARD_LANG_CSF.getF eldNa (),
          Thr ftLanguageUt l.getThr ftLanguageOf(card.getCardLang()).getValue());
    }
     f (card.getCardDoma n() != null) {
      bu lder.w hStr ngF eld(
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.CARD_DOMA N_F ELD.getF eldNa (),
          card.getCardDoma n());
    }
     f (card.getCardUrl() != null) {
      NUM_TWEETS_W TH_CARD_URL. ncre nt();
       f (card.getCardUrl().startsW h("card://")) {
        Str ng suff x = card.getCardUrl().replace("card://", "");
         f (Str ngUt ls. sNu r c(suff x)) {
          NUM_TWEETS_W TH_NUMER C_CARD_UR . ncre nt();
          bu lder.w hLongF eld(
              Earlyb rdF eldConstants.Earlyb rdF eldConstant.CARD_UR _CSF.getF eldNa (),
              Long.parseLong(suff x));
          LOG.debug(Str ng.format(
              "Good card URL for t et %s: %s",
              t et d,
              card.getCardUrl()));
        } else {
          NUM_TWEETS_W TH_ NVAL D_CARD_UR . ncre nt();
          LOG.debug(Str ng.format(
              "Card URL starts w h \"card://\" but follo d by non-nu r c for t et %s: %s",
              t et d,
              card.getCardUrl()));
        }
      }
    }
     f ( sCardV deo(card)) {
      // Add  nto " nternal" f eld so that t  t et  s returned by f lter:v deos.
      bu lder.addFacetSk pL st(
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.V DEO_L NKS_F ELD.getF eldNa ());
    }
  }

  /**
   * Determ nes  f a card  s a v deo.
   */
  pr vate stat c boolean  sCardV deo(@Nullable SearchCard2 card) {
     f (card == null) {
      return false;
    }
    return AMPL FY_CARD_NAME.equals gnoreCase(card.getCardNa ())
        || PLAYER_CARD_NAME.equals gnoreCase(card.getCardNa ());
  }

  pr vate vo d bu ldSpaceAdm nAndT leF elds(
      Earlyb rdThr ftDocu ntBu lder bu lder,
      Tw ter ssage  ssage,
      Pengu nVers on pengu nVers on) {

    bu ldSpaceAdm nF elds(bu lder,  ssage.getSpaceAdm ns(), pengu nVers on);

    // bu ld t  space t le f eld.
    bu ldT etToken zerToken zedF eld(
        bu lder,
        Earlyb rdF eldConstants.Earlyb rdF eldConstant.SPACE_T TLE_F ELD.getF eldNa (),
         ssage.getSpaceT le(),
        pengu nVers on);
  }

  pr vate vo d bu ldSpaceAdm nF elds(
      Earlyb rdThr ftDocu ntBu lder bu lder,
      Set<Tw ter ssageUser> spaceAdm ns,
      Pengu nVers on pengu nVers on) {

    for (Tw ter ssageUser spaceAdm n : spaceAdm ns) {
       f (spaceAdm n.getScreenNa (). sPresent()) {
        // bu ld screen na  (aka handle) f elds.
        Str ng screenNa  = spaceAdm n.getScreenNa ().get();
        Str ng normal zedScreenNa  =
            Normal zer lper.normal zeW hUnknownLocale(screenNa , pengu nVers on);

        bu lder.w hStr ngF eld(
            Earlyb rdF eldConstants.Earlyb rdF eldConstant.SPACE_ADM N_F ELD.getF eldNa (),
            normal zedScreenNa );
        bu lder.w hWh eSpaceToken zedScreenNa F eld(
            Earlyb rdF eldConstants
                .Earlyb rdF eldConstant.TOKEN ZED_SPACE_ADM N_F ELD.getF eldNa (),
            normal zedScreenNa );

         f (spaceAdm n.getToken zedScreenNa (). sPresent()) {
          bu lder.w hCa lCaseToken zedScreenNa F eld(
              Earlyb rdF eldConstants
                  .Earlyb rdF eldConstant.CAMELCASE_TOKEN ZED_SPACE_ADM N_F ELD.getF eldNa (),
              screenNa ,
              normal zedScreenNa ,
              spaceAdm n.getToken zedScreenNa ().get());
        }
      }

       f (spaceAdm n.getD splayNa (). sPresent()) {
        bu ldT etToken zerToken zedF eld(
            bu lder,
            Earlyb rdF eldConstants
                .Earlyb rdF eldConstant.TOKEN ZED_SPACE_ADM N_D SPLAY_NAME_F ELD.getF eldNa (),
            spaceAdm n.getD splayNa ().get(),
            pengu nVers on);
      }
    }
  }

  pr vate vo d bu ldT etToken zerToken zedF eld(
      Earlyb rdThr ftDocu ntBu lder bu lder,
      Str ng f eldNa ,
      Str ng text,
      Pengu nVers on pengu nVers on) {

     f (Str ngUt ls. sNotEmpty(text)) {
      Locale locale = Language dent f er lper
          . dent fyLanguage(text);
      Str ng normal zedText = Normal zer lper.normal ze(
          text, locale, pengu nVers on);
      Token zerResult result = Token zer lper
          .token zeT et(normal zedText, locale, pengu nVers on);
      Token zedCharSequenceStream tokenSeqStream = new Token zedCharSequenceStream();
      tokenSeqStream.reset(result.tokenSequence);
      TokenStreamSer al zer streamSer al zer =
          T etTokenStreamSer al zer.getT etTokenStreamSer al zer();
      try {
        bu lder.w hTokenStreamF eld(
            f eldNa ,
            result.tokenSequence.toStr ng(),
            streamSer al zer.ser al ze(tokenSeqStream));
      } catch ( OExcept on e) {
        LOG.error("Tw terTokenStream ser al zat on error! Could not ser al ze: " + text);
      }
    }
  }
}
