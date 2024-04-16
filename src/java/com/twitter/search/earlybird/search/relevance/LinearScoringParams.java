package com.tw ter.search.earlyb rd.search.relevance;

 mport java.ut l.Arrays;
 mport java.ut l.Map;

 mport com.google.common.annotat ons.V s bleForTest ng;

 mport com.tw ter.search.common.constants.SearchCardType;
 mport com.tw ter.search.common.constants.thr ftjava.Thr ftLanguage;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.rank ng.thr ftjava.Thr ftAgeDecayRank ngParams;
 mport com.tw ter.search.common.rank ng.thr ftjava.Thr ftCardRank ngParams;
 mport com.tw ter.search.common.rank ng.thr ftjava.Thr ftRank ngParams;
 mport com.tw ter.search.common.ut l.lang.Thr ftLanguageUt l;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSoc alF lterType;

/*
 * T  class for all query spec f c para ters,  nclud ng t  para ters from t  relevanceOpt ons and
 * values that are extracted from t  request  self.
 */
publ c class L nearScor ngParams {

  publ c stat c f nal double DEFAULT_FEATURE_WE GHT = 0;
  publ c stat c f nal double DEFAULT_FEATURE_M N_VAL = 0;
  publ c stat c f nal double DEFAULT_NO_BOOST = 1.0;
  @V s bleForTest ng
  stat c f nal SearchCounter NULL_USER_LANGS_KEY =
      SearchCounter.export("l near_scor ng_params_null_user_langs_key");

  publ c f nal double lucene  ght;
  publ c f nal double textScore  ght;
  publ c f nal double textScoreM nVal;
  publ c f nal double ret et  ght;
  publ c f nal double ret etM nVal;
  publ c f nal double fav  ght;
  publ c f nal double favM nVal;
  publ c f nal double reply  ght;
  publ c f nal double mult pleReply  ght;
  publ c f nal double mult pleReplyM nVal;
  publ c f nal double  sReply  ght;
  publ c f nal double parus  ght;
  publ c f nal double embeds mpress on  ght;
  publ c f nal double embedsUrl  ght;
  publ c f nal double v deoV ew  ght;
  publ c f nal double quotedCount  ght;

  publ c f nal double[] rank ngOffl neExp  ghts =
      new double[L nearScor ngData.MAX_OFFL NE_EXPER MENTAL_F ELDS];

  publ c f nal boolean applyBoosts;

  // Stor ng rank ng params for cards, avo d us ng maps for faster lookup
  publ c f nal double[] hasCardBoosts = new double[SearchCardType.values().length];
  publ c f nal double[] cardDoma nMatchBoosts = new double[SearchCardType.values().length];
  publ c f nal double[] cardAuthorMatchBoosts = new double[SearchCardType.values().length];
  publ c f nal double[] cardT leMatchBoosts = new double[SearchCardType.values().length];
  publ c f nal double[] cardDescr pt onMatchBoosts = new double[SearchCardType.values().length];

  publ c f nal double url  ght;
  publ c f nal double reputat on  ght;
  publ c f nal double reputat onM nVal;
  publ c f nal double followRet et  ght;
  publ c f nal double trustedRet et  ght;

  // Adjust nts for spec f c t ets (t et d -> score)
  publ c f nal Map<Long, Double> querySpec f cScoreAdjust nts;

  // Adjust nts for t ets posted by spec f c authors (user d -> score)
  publ c f nal Map<Long, Double> authorSpec f cScoreAdjust nts;

  publ c f nal double offens veDamp ng;
  publ c f nal double spamUserDamp ng;
  publ c f nal double nsfwUserDamp ng;
  publ c f nal double botUserDamp ng;
  publ c f nal double trustedC rcleBoost;
  publ c f nal double d rectFollowBoost;
  publ c f nal double m nScore;

  publ c f nal boolean applyF ltersAlways;

  publ c f nal boolean useLuceneScoreAsBoost;
  publ c f nal double maxLuceneScoreBoost;

  publ c f nal double langEngl shT etDemote;
  publ c f nal double langEngl shU Demote;
  publ c f nal double langDefaultDemote;
  publ c f nal boolean useUserLanguage nfo;
  publ c f nal double unknownLanguageBoost;

  publ c f nal double outOfNetworkReplyPenalty;

  publ c f nal boolean useAgeDecay;
  publ c f nal double ageDecayHalfl fe;
  publ c f nal double ageDecayBase;
  publ c f nal double ageDecaySlope;

  // h  attr bute demot ons
  publ c f nal boolean enableH Demot on;
  publ c f nal double noTextH Demot on;
  publ c f nal double urlOnlyH Demot on;
  publ c f nal double na OnlyH Demot on;
  publ c f nal double separateTextAndNa H Demot on;
  publ c f nal double separateTextAndUrlH Demot on;

  // trends related params
  publ c f nal double t etHasTrendBoost;
  publ c f nal double mult pleHashtagsOrTrendsDamp ng;

  publ c f nal double t etFromVer f edAccountBoost;

  publ c f nal double t etFromBlueVer f edAccountBoost;

  publ c f nal Thr ftSoc alF lterType soc alF lterType;
  publ c f nal  nt u Lang d;
  // Conf dences of t  understandab l y of d fferent languages for t  user.
  publ c f nal double[] userLangs = new double[Thr ftLanguage.values().length];

  publ c f nal long searc r d;
  publ c f nal double selfT etBoost;

  publ c f nal double t etHas d aUrlBoost;
  publ c f nal double t etHasNewsUrlBoost;

  // w t r   need  ta-data for repl es what t  reply  s to.
  publ c f nal boolean get nReplyToStatus d;

  //  n  al ze from a rank ng para ter
  publ c L nearScor ngParams(Thr ftSearchQuery searchQuery, Thr ftRank ngParams params) {
    //   ghts
    lucene  ght = params. sSetLuceneScoreParams()
        ? params.getLuceneScoreParams().get  ght() : DEFAULT_FEATURE_WE GHT;
    textScore  ght = params. sSetTextScoreParams()
        ? params.getTextScoreParams().get  ght() : DEFAULT_FEATURE_WE GHT;
    ret et  ght = params. sSetRet etCountParams()
        ? params.getRet etCountParams().get  ght() : DEFAULT_FEATURE_WE GHT;
    fav  ght = params. sSetFavCountParams()
        ? params.getFavCountParams().get  ght() : DEFAULT_FEATURE_WE GHT;
    reply  ght = params. sSetReplyCountParams()
        ? params.getReplyCountParams().get  ght() : DEFAULT_FEATURE_WE GHT;
    mult pleReply  ght = params. sSetMult pleReplyCountParams()
        ? params.getMult pleReplyCountParams().get  ght() : DEFAULT_FEATURE_WE GHT;
    parus  ght = params. sSetParusScoreParams()
        ? params.getParusScoreParams().get  ght() : DEFAULT_FEATURE_WE GHT;
    for ( nt   = 0;   < L nearScor ngData.MAX_OFFL NE_EXPER MENTAL_F ELDS;  ++) {
      Byte featureTypeByte = (byte)  ;
      // default   ght  s 0, thus contr but on for unset feature value w ll be 0.
      rank ngOffl neExp  ghts[ ] = params.getOffl neExper  ntalFeatureRank ngParamsS ze() > 0
          && params.getOffl neExper  ntalFeatureRank ngParams().conta nsKey(featureTypeByte)
              ? params.getOffl neExper  ntalFeatureRank ngParams().get(featureTypeByte).get  ght()
              : DEFAULT_FEATURE_WE GHT;
    }
    embeds mpress on  ght = params. sSetEmbeds mpress onCountParams()
        ? params.getEmbeds mpress onCountParams().get  ght() : DEFAULT_FEATURE_WE GHT;
    embedsUrl  ght = params. sSetEmbedsUrlCountParams()
        ? params.getEmbedsUrlCountParams().get  ght() : DEFAULT_FEATURE_WE GHT;
    v deoV ew  ght = params. sSetV deoV ewCountParams()
        ? params.getV deoV ewCountParams().get  ght() : DEFAULT_FEATURE_WE GHT;
    quotedCount  ght = params. sSetQuotedCountParams()
        ? params.getQuotedCountParams().get  ght() : DEFAULT_FEATURE_WE GHT;

    applyBoosts = params. sApplyBoosts();

    // conf gure card values
    Arrays.f ll(hasCardBoosts, DEFAULT_NO_BOOST);
    Arrays.f ll(cardAuthorMatchBoosts, DEFAULT_NO_BOOST);
    Arrays.f ll(cardDoma nMatchBoosts, DEFAULT_NO_BOOST);
    Arrays.f ll(cardT leMatchBoosts, DEFAULT_NO_BOOST);
    Arrays.f ll(cardDescr pt onMatchBoosts, DEFAULT_NO_BOOST);
     f (params. sSetCardRank ngParams()) {
      for (SearchCardType cardType : SearchCardType.values()) {
        byte cardType ndex = cardType.getByteValue();
        Thr ftCardRank ngParams rank ngParams = params.getCardRank ngParams().get(cardType ndex);
         f (rank ngParams != null) {
          hasCardBoosts[cardType ndex] = rank ngParams.getHasCardBoost();
          cardAuthorMatchBoosts[cardType ndex] = rank ngParams.getAuthorMatchBoost();
          cardDoma nMatchBoosts[cardType ndex] = rank ngParams.getDoma nMatchBoost();
          cardT leMatchBoosts[cardType ndex] = rank ngParams.getT leMatchBoost();
          cardDescr pt onMatchBoosts[cardType ndex] = rank ngParams.getDescr pt onMatchBoost();
        }
      }
    }

    url  ght = params. sSetUrlParams()
        ? params.getUrlParams().get  ght() : DEFAULT_FEATURE_WE GHT;
    reputat on  ght = params. sSetReputat onParams()
        ? params.getReputat onParams().get  ght() : DEFAULT_FEATURE_WE GHT;
     sReply  ght = params. sSet sReplyParams()
        ? params.get sReplyParams().get  ght() : DEFAULT_FEATURE_WE GHT;
    followRet et  ght = params. sSetD rectFollowRet etCountParams()
        ? params.getD rectFollowRet etCountParams().get  ght() : DEFAULT_FEATURE_WE GHT;
    trustedRet et  ght = params. sSetTrustedC rcleRet etCountParams()
        ? params.getTrustedC rcleRet etCountParams().get  ght() : DEFAULT_FEATURE_WE GHT;

    querySpec f cScoreAdjust nts = params.getQuerySpec f cScoreAdjust nts();
    authorSpec f cScoreAdjust nts = params.getAuthorSpec f cScoreAdjust nts();

    // m n/max f lters
    textScoreM nVal = params. sSetTextScoreParams()
        ? params.getTextScoreParams().getM n() : DEFAULT_FEATURE_M N_VAL;
    reputat onM nVal = params. sSetReputat onParams()
        ? params.getReputat onParams().getM n() : DEFAULT_FEATURE_M N_VAL;
    mult pleReplyM nVal = params. sSetMult pleReplyCountParams()
        ? params.getMult pleReplyCountParams().getM n() : DEFAULT_FEATURE_M N_VAL;
    ret etM nVal = params. sSetRet etCountParams() && params.getRet etCountParams(). sSetM n()
        ? params.getRet etCountParams().getM n() : DEFAULT_FEATURE_M N_VAL;
    favM nVal = params. sSetFavCountParams() && params.getFavCountParams(). sSetM n()
        ? params.getFavCountParams().getM n() : DEFAULT_FEATURE_M N_VAL;

    // boosts
    spamUserDamp ng = params. sSetSpamUserBoost() ? params.getSpamUserBoost() : 1.0;
    nsfwUserDamp ng = params. sSetNsfwUserBoost() ? params.getNsfwUserBoost() : 1.0;
    botUserDamp ng = params. sSetBotUserBoost() ? params.getBotUserBoost() : 1.0;
    offens veDamp ng = params.getOffens veBoost();
    trustedC rcleBoost = params.get nTrustedC rcleBoost();
    d rectFollowBoost = params.get nD rectFollowBoost();

    // language boosts
    langEngl shT etDemote = params.getLangEngl shT etBoost();
    langEngl shU Demote = params.getLangEngl shU Boost();
    langDefaultDemote = params.getLangDefaultBoost();
    useUserLanguage nfo = params. sUseUserLanguage nfo();
    unknownLanguageBoost = params.getUnknownLanguageBoost();

    // h  demot ons
    enableH Demot on = params. sEnableH Demot on();
    noTextH Demot on = params.getNoTextH Demot on();
    urlOnlyH Demot on = params.getUrlOnlyH Demot on();
    na OnlyH Demot on = params.getNa OnlyH Demot on();
    separateTextAndNa H Demot on = params.getSeparateTextAndNa H Demot on();
    separateTextAndUrlH Demot on = params.getSeparateTextAndUrlH Demot on();

    outOfNetworkReplyPenalty = params.getOutOfNetworkReplyPenalty();

     f (params. sSetAgeDecayParams()) {
      // new age decay sett ngs
      Thr ftAgeDecayRank ngParams ageDecayParams = params.getAgeDecayParams();
      ageDecaySlope = ageDecayParams.getSlope();
      ageDecayHalfl fe = ageDecayParams.getHalfl fe();
      ageDecayBase = ageDecayParams.getBase();
      useAgeDecay = true;
    } else  f (params. sSetDeprecatedAgeDecayBase()
        && params. sSetDeprecatedAgeDecayHalfl fe()
        && params. sSetDeprecatedAgeDecaySlope()) {
      ageDecaySlope = params.getDeprecatedAgeDecaySlope();
      ageDecayHalfl fe = params.getDeprecatedAgeDecayHalfl fe();
      ageDecayBase = params.getDeprecatedAgeDecayBase();
      useAgeDecay = true;
    } else {
      ageDecaySlope = 0.0;
      ageDecayHalfl fe = 0.0;
      ageDecayBase = 0.0;
      useAgeDecay = false;
    }

    // trends
    t etHasTrendBoost = params.getT etHasTrendBoost();
    mult pleHashtagsOrTrendsDamp ng = params.getMult pleHashtagsOrTrendsBoost();

    // ver f ed accounts
    t etFromVer f edAccountBoost = params.getT etFromVer f edAccountBoost();
    t etFromBlueVer f edAccountBoost = params.getT etFromBlueVer f edAccountBoost();

    // score f lter
    m nScore = params.getM nScore();

    applyF ltersAlways = params. sApplyF ltersAlways();

    useLuceneScoreAsBoost = params. sUseLuceneScoreAsBoost();
    maxLuceneScoreBoost = params.getMaxLuceneScoreBoost();

    searc r d = searchQuery. sSetSearc r d() ? searchQuery.getSearc r d() : -1;
    selfT etBoost = params.getSelfT etBoost();

    soc alF lterType = searchQuery.getSoc alF lterType();

    // t  U  language and t  conf dences of t  languages user can understand.
     f (!searchQuery. sSetU Lang() || searchQuery.getU Lang(). sEmpty()) {
      u Lang d = Thr ftLanguage.UNKNOWN.getValue();
    } else {
      u Lang d = Thr ftLanguageUt l.getThr ftLanguageOf(searchQuery.getU Lang()).getValue();
    }
     f (searchQuery.getUserLangsS ze() > 0) {
      for (Map.Entry<Thr ftLanguage, Double> lang : searchQuery.getUserLangs().entrySet()) {
        Thr ftLanguage thr ftLanguage = lang.getKey();
        // SEARCH-13441
         f (thr ftLanguage != null) {
          userLangs[thr ftLanguage.getValue()] = lang.getValue();
        } else {
          NULL_USER_LANGS_KEY. ncre nt();
        }
      }
    }

    // For now,   w ll use t  sa  boost for both  mage, and v deo.
    t etHas d aUrlBoost = params.getT etHas mageUrlBoost();
    t etHasNewsUrlBoost = params.getT etHasNewsUrlBoost();

    get nReplyToStatus d =
        searchQuery. sSetResult tadataOpt ons()
            && searchQuery.getResult tadataOpt ons(). sSetGet nReplyToStatus d()
            && searchQuery.getResult tadataOpt ons(). sGet nReplyToStatus d();
  }
}
