package com.tw ter.search.common.converter.earlyb rd;

 mport java. o. OExcept on;
 mport java.ut l.HashSet;
 mport java.ut l.L st;
 mport java.ut l.Locale;
 mport java.ut l.Map;
 mport java.ut l.Opt onal;
 mport java.ut l.Set;
 mport java.ut l.regex.Matc r;
 mport java.ut l.regex.Pattern;
 mport java.ut l.stream.Collectors;

 mport com.google.common.base.Jo ner;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.L sts;
 mport com.google.common.collect.Maps;

 mport org.apac .commons.lang.Str ngUt ls;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.text.token.Token zedCharSequence;
 mport com.tw ter.common.text.token.Token zedCharSequenceStream;
 mport com.tw ter.common.text.ut l.TokenStreamSer al zer;
 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers on;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Place;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Potent alLocat on;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Prof leGeoEnr ch nt;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftExpandedUrl;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Vers onedT etFeatures;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.relevance.ent  es.Potent alLocat onObject;
 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssage;
 mport com.tw ter.search.common.relevance.features.FeatureS nk;
 mport com.tw ter.search.common.relevance.features.MutableFeatureNormal zers;
 mport com.tw ter.search.common.relevance.features.RelevanceS gnalConstants;
 mport com.tw ter.search.common.relevance.features.T etTextFeatures;
 mport com.tw ter.search.common.relevance.features.T etTextQual y;
 mport com.tw ter.search.common.relevance.features.T etUserFeatures;
 mport com.tw ter.search.common.sc ma.base.FeatureConf gurat on;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdEncodedFeatures;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.common.ut l.lang.Thr ftLanguageUt l;
 mport com.tw ter.search.common.ut l.text.Language dent f er lper;
 mport com.tw ter.search.common.ut l.text.Normal zer lper;
 mport com.tw ter.search.common.ut l.text.S ceNormal zer;
 mport com.tw ter.search.common.ut l.text.Token zer lper;
 mport com.tw ter.search.common.ut l.text.Token zerResult;
 mport com.tw ter.search.common.ut l.text.T etTokenStreamSer al zer;
 mport com.tw ter.search.common.ut l.url.L nkV s b l yUt ls;
 mport com.tw ter.search.common.ut l.url.Nat veV deoClass f cat onUt ls;
 mport com.tw ter.search. ngester.model.V s bleTokenRat oUt l;

/**
 * EncodedFeatureBu lder  lps to bu ld encoded features for Tw ter ssage.
 *
 * T   s stateful so should only be used one t et at a t  
 */
publ c class EncodedFeatureBu lder {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(EncodedFeatureBu lder.class);

  pr vate stat c f nal SearchCounter NUM_TWEETS_W TH_ NVAL D_TWEET_ D_ N_PHOTO_URL =
      SearchCounter.export("t ets_w h_ nval d_t et_ d_ n_photo_url");

  // Tw terTokenStream for convert ng Token zedCharSequence  nto a stream for ser al zat on
  // T   s stateful so should only be used one t et at a t  
  pr vate f nal Token zedCharSequenceStream tokenSeqStream = new Token zedCharSequenceStream();

  // SUPPRESS CHECKSTYLE:OFF L neLength
  pr vate stat c f nal Pattern TW TTER_PHOTO_PERMA_L NK_PATTERN =
          Pattern.comp le("(? :^(?:(?:https?\\:\\/\\/)?(?:www\\.)?)?tw ter\\.com\\/(?:\\?[^#]+)?(?:#!?\\/?)?\\w{1,20}\\/status\\/(\\d+)\\/photo\\/\\d*$)");

  pr vate stat c f nal Pattern TW TTER_PHOTO_COPY_PASTE_L NK_PATTERN =
          Pattern.comp le("(? :^(?:(?:https?\\:\\/\\/)?(?:www\\.)?)?tw ter\\.com\\/(?:#!?\\/)?\\w{1,20}\\/status\\/(\\d+)\\/photo\\/\\d*$)");
  // SUPPRESS CHECKSTYLE:ON L neLength

  pr vate stat c f nal V s bleTokenRat oUt l V S BLE_TOKEN_RAT O = new V s bleTokenRat oUt l();

  pr vate stat c f nal Map<Pengu nVers on, SearchCounter> SER AL ZE_FA LURE_COUNTERS_MAP =
      Maps.newEnumMap(Pengu nVers on.class);
  stat c {
    for (Pengu nVers on pengu nVers on : Pengu nVers on.values()) {
      SER AL ZE_FA LURE_COUNTERS_MAP.put(
          pengu nVers on,
          SearchCounter.export(
              "tokenstream_ser al zat on_fa lure_" + pengu nVers on.na ().toLo rCase()));
    }
  }

  publ c stat c class T etFeatureW hEncodeFeatures {
    publ c f nal Vers onedT etFeatures vers onedFeatures;
    publ c f nal Earlyb rdEncodedFeatures encodedFeatures;
    publ c f nal Earlyb rdEncodedFeatures extendedEncodedFeatures;

    publ c T etFeatureW hEncodeFeatures(
        Vers onedT etFeatures vers onedFeatures,
        Earlyb rdEncodedFeatures encodedFeatures,
        Earlyb rdEncodedFeatures extendedEncodedFeatures) {
      t .vers onedFeatures = vers onedFeatures;
      t .encodedFeatures = encodedFeatures;
      t .extendedEncodedFeatures = extendedEncodedFeatures;
    }
  }

  /**
   * Create t et text features and t  encoded features.
   *
   * @param  ssage t  t et  ssage
   * @param pengu nVers on t  based pengu n vers on to create t  features
   * @param sc maSnapshot t  sc ma assoc ated w h t  features
   * @return t  text features and t  encoded features
   */
  publ c T etFeatureW hEncodeFeatures createT etFeaturesFromTw ter ssage(
      Tw ter ssage  ssage,
      Pengu nVers on pengu nVers on,
       mmutableSc ma nterface sc maSnapshot) {
    Vers onedT etFeatures vers onedT etFeatures = new Vers onedT etFeatures();

    // Wr e extendedPackedFeatures.
    Earlyb rdEncodedFeatures extendedEncodedFeatures =
        createExtendedEncodedFeaturesFromTw ter ssage( ssage, pengu nVers on, sc maSnapshot);
     f (extendedEncodedFeatures != null) {
      extendedEncodedFeatures
          .wr eExtendedFeaturesToVers onedT etFeatures(vers onedT etFeatures);
    }

    setS ceAndNormal zedS ce(
         ssage.getStr ppedS ce(), vers onedT etFeatures, pengu nVers on);

    T etTextFeatures textFeatures =  ssage.getT etTextFeatures(pengu nVers on);

    ///////////////////////////////
    // Add hashtags and  nt ons
    textFeatures.getHashtags().forEach(vers onedT etFeatures::addToHashtags);
    textFeatures.get nt ons().forEach(vers onedT etFeatures::addTo nt ons);

    ///////////////////////////////
    // Extract so  extra  nformat on from t   ssage text.
    //  ndex stock symbols w h $ prepended
    textFeatures.getStocks().stream()
        .f lter(stock -> stock != null)
        .forEach(stock -> vers onedT etFeatures.addToStocks(stock.toLo rCase()));

    // Quest on marks
    vers onedT etFeatures.setHasQuest onMark(textFeatures.hasQuest onMark());
    // Sm leys
    vers onedT etFeatures.setHasPos  veSm ley(textFeatures.hasPos  veSm ley());
    vers onedT etFeatures.setHasNegat veSm ley(textFeatures.hasNegat veSm ley());

    TokenStreamSer al zer streamSer al zer =
        T etTokenStreamSer al zer.getT etTokenStreamSer al zer();
    Token zedCharSequence tokenSeq = textFeatures.getTokenSequence();
    tokenSeqStream.reset(tokenSeq);
     nt tokenPercent = V S BLE_TOKEN_RAT O.extractAndNormal zeTokenPercentage(tokenSeqStream);
    tokenSeqStream.reset(tokenSeq);

    // Wr e packedFeatures.
    Earlyb rdEncodedFeatures encodedFeatures = createEncodedFeaturesFromTw ter ssage(
         ssage, pengu nVers on, sc maSnapshot, tokenPercent);
    encodedFeatures.wr eFeaturesToVers onedT etFeatures(vers onedT etFeatures);

    try {
      vers onedT etFeatures.setT etTokenStream(streamSer al zer.ser al ze(tokenSeqStream));
      vers onedT etFeatures.setT etTokenStreamText(tokenSeq.toStr ng());
    } catch ( OExcept on e) {
      LOG.error("Tw terTokenStream ser al zat on error! Could not ser al ze: "
          + tokenSeq.toStr ng());
      SER AL ZE_FA LURE_COUNTERS_MAP.get(pengu nVers on). ncre nt();
      vers onedT etFeatures.unsetT etTokenStream();
      vers onedT etFeatures.unsetT etTokenStreamText();
    }

    // User na  features
     f ( ssage.getFromUserD splayNa (). sPresent()) {
      Locale locale = Language dent f er lper
          . dent fyLanguage( ssage.getFromUserD splayNa ().get());
      Str ng normal zedD splayNa  = Normal zer lper.normal ze(
           ssage.getFromUserD splayNa ().get(), locale, pengu nVers on);
      Token zerResult result = Token zer lper
          .token zeT et(normal zedD splayNa , locale, pengu nVers on);
      tokenSeqStream.reset(result.tokenSequence);
      try {
        vers onedT etFeatures.setUserD splayNa TokenStream(
            streamSer al zer.ser al ze(tokenSeqStream));
        vers onedT etFeatures.setUserD splayNa TokenStreamText(result.tokenSequence.toStr ng());
      } catch ( OExcept on e) {
        LOG.error("Tw terTokenStream ser al zat on error! Could not ser al ze: "
            +  ssage.getFromUserD splayNa ().get());
        SER AL ZE_FA LURE_COUNTERS_MAP.get(pengu nVers on). ncre nt();
        vers onedT etFeatures.unsetUserD splayNa TokenStream();
        vers onedT etFeatures.unsetUserD splayNa TokenStreamText();
      }
    }

    Str ng resolvedUrlsText = Jo ner.on(" ").sk pNulls().jo n(textFeatures.getResolvedUrlTokens());
    vers onedT etFeatures.setNormal zedResolvedUrlText(resolvedUrlsText);

    addPlace( ssage, vers onedT etFeatures, pengu nVers on);
    addProf leGeoEnr ch nt( ssage, vers onedT etFeatures, pengu nVers on);

    vers onedT etFeatures.setT etS gnature( ssage.getT etS gnature(pengu nVers on));

    return new T etFeatureW hEncodeFeatures(
        vers onedT etFeatures, encodedFeatures, extendedEncodedFeatures);
  }


  protected stat c vo d setS ceAndNormal zedS ce(
      Str ng str ppedS ce,
      Vers onedT etFeatures vers onedT etFeatures,
      Pengu nVers on pengu nVers on) {

     f (str ppedS ce != null && !str ppedS ce. sEmpty()) {
      // normal ze s ce for searchable f eld - replaces wh espace w h underscores (???).
      vers onedT etFeatures.setNormal zedS ce(
          S ceNormal zer.normal ze(str ppedS ce, pengu nVers on));

      // s ce facet has s mpler normal zat on.
      Locale locale = Language dent f er lper. dent fyLanguage(str ppedS ce);
      vers onedT etFeatures.setS ce(Normal zer lper.normal zeKeepCase(
          str ppedS ce, locale, pengu nVers on));
    }
  }

  /**
   * Adds t  g ven photo url to t  thr ft status  f    s a tw ter photo permal nk.
   * Returns true,  f t  was  ndeed a tw ter photo, false ot rw se.
   */
  publ c stat c boolean addPhotoUrl(Tw ter ssage  ssage, Str ng photoPermal nk) {
    Matc r matc r = TW TTER_PHOTO_COPY_PASTE_L NK_PATTERN.matc r(photoPermal nk);
     f (!matc r.matc s() || matc r.groupCount() < 1) {
      matc r = TW TTER_PHOTO_PERMA_L NK_PATTERN.matc r(photoPermal nk);
    }

     f (matc r.matc s() && matc r.groupCount() == 1) {
      // t   s a nat ve photo url wh ch   need to store  n a separate f eld
      Str ng  dStr = matc r.group(1);
       f ( dStr != null) {
        //  dStr should be a val d t et  D (and t refore, should f   nto a Long), but   have
        // t ets for wh ch  dStr  s a long sequence of d g s that does not f   nto a Long.
        try {
          long photoStatus d = Long.parseLong( dStr);
           ssage.addPhotoUrl(photoStatus d, null);
        } catch (NumberFormatExcept on e) {
          LOG.warn("Found a t et w h a photo URL w h an  nval d t et  D: " +  ssage);
          NUM_TWEETS_W TH_ NVAL D_TWEET_ D_ N_PHOTO_URL. ncre nt();
        }
      }
      return true;
    }
    return false;
  }

  pr vate vo d addPlace(Tw ter ssage  ssage,
                        Vers onedT etFeatures vers onedT etFeatures,
                        Pengu nVers on pengu nVers on) {
    Str ng place d =  ssage.getPlace d();
     f (place d == null) {
      return;
    }

    // T et.Place. d and T et.Place.full_na  are both requ red f elds.
    Str ng placeFullNa  =  ssage.getPlaceFullNa ();
    Precond  ons.c ckNotNull(placeFullNa , "T et.Place w hout full_na .");

    Locale placeFullNa Locale = Language dent f er lper. dent fyLanguage(placeFullNa );
    Str ng normal zedPlaceFullNa  =
        Normal zer lper.normal ze(placeFullNa , placeFullNa Locale, pengu nVers on);
    Str ng token zedPlaceFullNa  = Str ngUt ls.jo n(
        Token zer lper.token zeQuery(normal zedPlaceFullNa , placeFullNa Locale, pengu nVers on),
        " ");

    Place place = new Place(place d, token zedPlaceFullNa );
    Str ng placeCountryCode =  ssage.getPlaceCountryCode();
     f (placeCountryCode != null) {
      Locale placeCountryCodeLocale = Language dent f er lper. dent fyLanguage(placeCountryCode);
      place.setCountryCode(
          Normal zer lper.normal ze(placeCountryCode, placeCountryCodeLocale, pengu nVers on));
    }

    vers onedT etFeatures.setToken zedPlace(place);
  }

  pr vate vo d addProf leGeoEnr ch nt(Tw ter ssage  ssage,
                                       Vers onedT etFeatures vers onedT etFeatures,
                                       Pengu nVers on pengu nVers on) {
    L st<Potent alLocat onObject> potent alLocat ons =  ssage.getPotent alLocat ons();
     f (potent alLocat ons. sEmpty()) {
      return;
    }

    L st<Potent alLocat on> thr ftPotent alLocat ons = L sts.newArrayL st();
    for (Potent alLocat onObject potent alLocat on : potent alLocat ons) {
      thr ftPotent alLocat ons.add(potent alLocat on.toThr ftPotent alLocat on(pengu nVers on));
    }
    vers onedT etFeatures.setToken zedProf leGeoEnr ch nt(
        new Prof leGeoEnr ch nt(thr ftPotent alLocat ons));
  }

  /** Returns t  encoded features. */
  publ c stat c Earlyb rdEncodedFeatures createEncodedFeaturesFromTw ter ssage(
      Tw ter ssage  ssage,
      Pengu nVers on pengu nVers on,
       mmutableSc ma nterface sc ma,
       nt normal zedTokenPercentBucket) {
    FeatureS nk s nk = new FeatureS nk(sc ma);

    // Stat c features
    s nk.setBooleanValue(Earlyb rdF eldConstant. S_RETWEET_FLAG,  ssage. sRet et())
        .setBooleanValue(Earlyb rdF eldConstant. S_REPLY_FLAG,  ssage. sReply())
        .setBooleanValue(
            Earlyb rdF eldConstant.FROM_VER F ED_ACCOUNT_FLAG,  ssage. sUserVer f ed())
        .setBooleanValue(
            Earlyb rdF eldConstant.FROM_BLUE_VER F ED_ACCOUNT_FLAG,  ssage. sUserBlueVer f ed())
        .setBooleanValue(Earlyb rdF eldConstant. S_SENS T VE_CONTENT,  ssage. sSens  veContent());

    T etTextFeatures textFeatures =  ssage.getT etTextFeatures(pengu nVers on);
     f (textFeatures != null) {
      f nal FeatureConf gurat on featureConf gNumHashtags = sc ma.getFeatureConf gurat onByNa (
          Earlyb rdF eldConstant.NUM_HASHTAGS.getF eldNa ());
      f nal FeatureConf gurat on featureConf gNum nt ons = sc ma.getFeatureConf gurat onByNa (
          Earlyb rdF eldConstant.NUM_MENT ONS.getF eldNa ());

      s nk.setNu r cValue(
              Earlyb rdF eldConstant.NUM_HASHTAGS,
              Math.m n(textFeatures.getHashtagsS ze(), featureConf gNumHashtags.getMaxValue()))
          .setNu r cValue(
              Earlyb rdF eldConstant.NUM_MENT ONS,
              Math.m n(textFeatures.get nt onsS ze(), featureConf gNum nt ons.getMaxValue()))
          .setBooleanValue(
              Earlyb rdF eldConstant.HAS_MULT PLE_HASHTAGS_OR_TRENDS_FLAG,
              Tw ter ssage.hasMult pleHashtagsOrTrends(textFeatures))
          .setBooleanValue(
              Earlyb rdF eldConstant.HAS_TREND_FLAG,
              textFeatures.getTrend ngTermsS ze() > 0);
    }

    T etTextQual y textQual y =  ssage.getT etTextQual y(pengu nVers on);
     f (textQual y != null) {
      s nk.setNu r cValue(Earlyb rdF eldConstant.TEXT_SCORE, textQual y.getTextScore());
      s nk.setBooleanValue(
          Earlyb rdF eldConstant. S_OFFENS VE_FLAG,
          textQual y.hasBoolQual y(T etTextQual y.BooleanQual yType.OFFENS VE)
              || textQual y.hasBoolQual y(T etTextQual y.BooleanQual yType.OFFENS VE_USER)
              // Note:  f json  ssage "poss bly_sens  ve" flag  s set,   cons der t  t et
              // sens  ve and  s currently f ltered out  n safe search mode v a a hacky setup:
              // earlyb rd does not create _f lter_sens  ve_content f eld, only
              // _ s_offens ve f eld  s created, and used  n f lter:safe operator
              || textQual y.hasBoolQual y(T etTextQual y.BooleanQual yType.SENS T VE));
       f (textQual y.hasBoolQual y(T etTextQual y.BooleanQual yType.SENS T VE)) {
        s nk.setBooleanValue(Earlyb rdF eldConstant. S_SENS T VE_CONTENT, true);
      }
    } else {
      //   don't have text score, for whatever reason, set to sent nel value so   won't be
      // sk pped by scor ng funct on
      s nk.setNu r cValue(Earlyb rdF eldConstant.TEXT_SCORE,
          RelevanceS gnalConstants.UNSET_TEXT_SCORE_SENT NEL);
    }

     f ( ssage. sSetLocale()) {
      s nk.setNu r cValue(Earlyb rdF eldConstant.LANGUAGE,
          Thr ftLanguageUt l.getThr ftLanguageOf( ssage.getLocale()).getValue());
    }

    // User features
    T etUserFeatures userFeatures =  ssage.getT etUserFeatures(pengu nVers on);
     f (userFeatures != null) {
      s nk.setBooleanValue(Earlyb rdF eldConstant. S_USER_SPAM_FLAG, userFeatures. sSpam())
          .setBooleanValue(Earlyb rdF eldConstant. S_USER_NSFW_FLAG, userFeatures. sNsfw())
          .setBooleanValue(Earlyb rdF eldConstant. S_USER_BOT_FLAG, userFeatures. sBot());
    }
     f ( ssage.getUserReputat on() != Tw ter ssage.DOUBLE_F ELD_NOT_PRESENT) {
      s nk.setNu r cValue(Earlyb rdF eldConstant.USER_REPUTAT ON,
          (byte)  ssage.getUserReputat on());
    } else {
      s nk.setNu r cValue(Earlyb rdF eldConstant.USER_REPUTAT ON,
          RelevanceS gnalConstants.UNSET_REPUTAT ON_SENT NEL);
    }

    s nk.setBooleanValue(Earlyb rdF eldConstant. S_NULLCAST_FLAG,  ssage.getNullcast());

    // Realt    ngest on does not wr e engage nt features. Updater does that.
     f ( ssage.getNumFavor es() > 0) {
      s nk.setNu r cValue(Earlyb rdF eldConstant.FAVOR TE_COUNT,
          MutableFeatureNormal zers.BYTE_NORMAL ZER.normal ze( ssage.getNumFavor es()));
    }
     f ( ssage.getNumRet ets() > 0) {
      s nk.setNu r cValue(Earlyb rdF eldConstant.RETWEET_COUNT,
          MutableFeatureNormal zers.BYTE_NORMAL ZER.normal ze( ssage.getNumRet ets()));
    }
     f ( ssage.getNumRepl es() > 0) {
      s nk.setNu r cValue(Earlyb rdF eldConstant.REPLY_COUNT,
          MutableFeatureNormal zers.BYTE_NORMAL ZER.normal ze( ssage.getNumRepl es()));
    }

    s nk.setNu r cValue(Earlyb rdF eldConstant.V S BLE_TOKEN_RAT O, normal zedTokenPercentBucket);

    Earlyb rdEncodedFeatures encodedFeatures =
        (Earlyb rdEncodedFeatures) s nk.getFeaturesForBaseF eld(
            Earlyb rdF eldConstant.ENCODED_TWEET_FEATURES_F ELD.getF eldNa ());
    updateL nkEncodedFeatures(encodedFeatures,  ssage);
    return encodedFeatures;
  }

  /**
   * Returns t  extended encoded features.
   */
  publ c stat c Earlyb rdEncodedFeatures createExtendedEncodedFeaturesFromTw ter ssage(
    Tw ter ssage  ssage,
    Pengu nVers on pengu nVers on,
     mmutableSc ma nterface sc ma) {
    FeatureS nk s nk = new FeatureS nk(sc ma);

    T etTextFeatures textFeatures =  ssage.getT etTextFeatures(pengu nVers on);

     f (textFeatures != null) {
      setExtendedEncodedFeature ntValue(s nk, sc ma,
          Earlyb rdF eldConstant.NUM_HASHTAGS_V2, textFeatures.getHashtagsS ze());
      setExtendedEncodedFeature ntValue(s nk, sc ma,
          Earlyb rdF eldConstant.NUM_MENT ONS_V2, textFeatures.get nt onsS ze());
      setExtendedEncodedFeature ntValue(s nk, sc ma,
          Earlyb rdF eldConstant.NUM_STOCKS, textFeatures.getStocksS ze());
    }

    Opt onal<Long> referenceAuthor d =  ssage.getReferenceAuthor d();
     f (referenceAuthor d. sPresent()) {
      setEncodedReferenceAuthor d(s nk, referenceAuthor d.get());
    }

    return (Earlyb rdEncodedFeatures) s nk.getFeaturesForBaseF eld(
        Earlyb rdF eldConstant.EXTENDED_ENCODED_TWEET_FEATURES_F ELD.getF eldNa ());
  }

  /**
   * Updates all URL-related features, based on t  values stored  n t  g ven  ssage.
   *
   * @param encodedFeatures T  features to be updated.
   * @param  ssage T   ssage.
   */
  publ c stat c vo d updateL nkEncodedFeatures(
      Earlyb rdEncodedFeatures encodedFeatures, Tw ter ssage  ssage) {
     f ( ssage.getL nkLocale() != null) {
      encodedFeatures.setFeatureValue(
          Earlyb rdF eldConstant.L NK_LANGUAGE,
          Thr ftLanguageUt l.getThr ftLanguageOf( ssage.getL nkLocale()).getValue());
    }

     f ( ssage.hasCard()) {
      encodedFeatures.setFlag(Earlyb rdF eldConstant.HAS_CARD_FLAG);
    }

    // Set HAS_ MAGE HAS_NEWS HAS_V DEO etc. flags for expanded urls.
     f ( ssage.getExpandedUrlMapS ze() > 0) {
      encodedFeatures.setFlag(Earlyb rdF eldConstant.HAS_L NK_FLAG);

      for (Thr ftExpandedUrl url :  ssage.getExpandedUrlMap().values()) {
         f (url. sSet d aType()) {
          sw ch (url.get d aType()) {
            case NAT VE_ MAGE:
              encodedFeatures.setFlag(Earlyb rdF eldConstant.HAS_ MAGE_URL_FLAG);
              encodedFeatures.setFlag(Earlyb rdF eldConstant.HAS_NAT VE_ MAGE_FLAG);
              break;
            case  MAGE:
              encodedFeatures.setFlag(Earlyb rdF eldConstant.HAS_ MAGE_URL_FLAG);
              break;
            case V DEO:
              encodedFeatures.setFlag(Earlyb rdF eldConstant.HAS_V DEO_URL_FLAG);
              break;
            case NEWS:
              encodedFeatures.setFlag(Earlyb rdF eldConstant.HAS_NEWS_URL_FLAG);
              break;
            case UNKNOWN:
              break;
            default:
              throw new  llegalStateExcept on("Unexpected enum value: " + url.get d aType());
          }
        }
      }
    }

    Set<Str ng> canon calLastHopUrlsStr ngs =  ssage.getCanon calLastHopUrls();
    Set<Str ng> expandedUrlsStr ngs =  ssage.getExpandedUrls()
        .stream()
        .map(Thr ftExpandedUrl::getExpandedUrl)
        .collect(Collectors.toSet());
    Set<Str ng> expandedAndLastHopUrlsStr ngs = new HashSet<>();
    expandedAndLastHopUrlsStr ngs.addAll(expandedUrlsStr ngs);
    expandedAndLastHopUrlsStr ngs.addAll(canon calLastHopUrlsStr ngs);
    // C ck both expanded and last hop url for consu r v deos as consu r v deo urls are
    // so t  s red rected to t  url of t  t ets conta n ng t  v deos (SEARCH-42612).
     f (Nat veV deoClass f cat onUt ls.hasConsu rV deo(expandedAndLastHopUrlsStr ngs)) {
      encodedFeatures.setFlag(Earlyb rdF eldConstant.HAS_CONSUMER_V DEO_FLAG);
    }
     f (Nat veV deoClass f cat onUt ls.hasProV deo(canon calLastHopUrlsStr ngs)) {
      encodedFeatures.setFlag(Earlyb rdF eldConstant.HAS_PRO_V DEO_FLAG);
    }
     f (Nat veV deoClass f cat onUt ls.hasV ne(canon calLastHopUrlsStr ngs)) {
      encodedFeatures.setFlag(Earlyb rdF eldConstant.HAS_V NE_FLAG);
    }
     f (Nat veV deoClass f cat onUt ls.hasPer scope(canon calLastHopUrlsStr ngs)) {
      encodedFeatures.setFlag(Earlyb rdF eldConstant.HAS_PER SCOPE_FLAG);
    }
     f (L nkV s b l yUt ls.hasV s bleL nk( ssage.getExpandedUrls())) {
      encodedFeatures.setFlag(Earlyb rdF eldConstant.HAS_V S BLE_L NK_FLAG);
    }
  }

  pr vate stat c vo d setExtendedEncodedFeature ntValue(
      FeatureS nk s nk,
       mmutableSc ma nterface sc ma,
      Earlyb rdF eldConstant f eld,
       nt value) {
    boolean f eld nSc ma = sc ma.hasF eld(f eld.getF eldNa ());
     f (f eld nSc ma) {
      FeatureConf gurat on featureConf g =
          sc ma.getFeatureConf gurat onByNa (f eld.getF eldNa ());
      s nk.setNu r cValue(f eld, Math.m n(value, featureConf g.getMaxValue()));
    }
  }

  pr vate stat c vo d setEncodedReferenceAuthor d(FeatureS nk s nk, long referenceAuthor d) {
    Long ntConverter. ntegerRepresentat on  nts =
        Long ntConverter.convertOneLongToTwo nt(referenceAuthor d);
    s nk.setNu r cValue(
        Earlyb rdF eldConstant.REFERENCE_AUTHOR_ D_LEAST_S GN F CANT_ NT,  nts.leastS gn f cant nt);
    s nk.setNu r cValue(
        Earlyb rdF eldConstant.REFERENCE_AUTHOR_ D_MOST_S GN F CANT_ NT,  nts.mostS gn f cant nt);
  }
}
