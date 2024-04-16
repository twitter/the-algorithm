package com.tw ter.search.earlyb rd.search.relevance.scor ng;

 mport java. o. OExcept on;
 mport java.ut l.EnumSet;
 mport java.ut l.HashMap;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Set;
 mport java.ut l.concurrent.T  Un ;
 mport javax.annotat on.Nullable;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect. mmutableSet;
 mport com.google.common.collect. erables;
 mport com.google.common.collect.L sts;
 mport com.google.common.collect.Maps;
 mport com.google.common.pr m  ves. nts;
 mport com.google.common.pr m  ves.Longs;

 mport org.apac .lucene.search.Explanat on;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common_ nternal.bloomf lter.BloomF lter;
 mport com.tw ter.search.common.constants.SearchCardType;
 mport com.tw ter.search.common.constants.thr ftjava.Thr ftLanguage;
 mport com.tw ter.search.common.database.DatabaseConf g;
 mport com.tw ter.search.common.features.ExternalT etFeature;
 mport com.tw ter.search.common.features.FeatureHandler;
 mport com.tw ter.search.common.features.thr ft.Thr ftSearchFeatureSc maEntry;
 mport com.tw ter.search.common.features.thr ft.Thr ftSearchFeatureType;
 mport com.tw ter.search.common.features.thr ft.Thr ftSearchResultFeatures;
 mport com.tw ter.search.common.query.QueryCommonF eldH sV s or;
 mport com.tw ter.search.common.rank ng.thr ftjava.Thr ftRank ngParams;
 mport com.tw ter.search.common.relevance.features.AgeDecay;
 mport com.tw ter.search.common.relevance.features.RelevanceS gnalConstants;
 mport com.tw ter.search.common.relevance.text.V s bleTokenRat oNormal zer;
 mport com.tw ter.search.common.results.thr ftjava.F eldH L st;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.common.ut l.Long ntConverter;
 mport com.tw ter.search.common.ut l.lang.Thr ftLanguageUt l;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserTable;
 mport com.tw ter.search.earlyb rd.search.Ant Gam ngF lter;
 mport com.tw ter.search.earlyb rd.search.relevance.L nearScor ngData;
 mport com.tw ter.search.earlyb rd.search.relevance.L nearScor ngData.Sk pReason;
 mport com.tw ter.search.earlyb rd.search.relevance.L nearScor ngParams;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultExtra tadata;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult tadata;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult tadataOpt ons;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultType;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultsRelevanceStats;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSoc alF lterType;

/**
 * Base class for scor ng funct ons that rely on t  extracted features stored  n L nearScor ngData.
 *
 * Extens ons of t  class must  mple nt 2  thods:
 *
 * - computeScore
 * - generateExplanat onForScor ng
 *
 * T y are called for scor ng and generat ng t  debug  nformat on of t  docu nt that  's
 * currently be ng evaluated. T  f eld 'data' holds t  features of t  docu nt.
 */
publ c abstract class FeatureBasedScor ngFunct on extends Scor ngFunct on {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(FeatureBasedScor ngFunct on.class);

  // A mult pl er that's appl ed to all scores to avo d scores too low.
  publ c stat c f nal float SCORE_ADJUSTER = 100.0f;

  pr vate stat c f nal V s bleTokenRat oNormal zer V S BLE_TOKEN_RAT O_NORMAL ZER =
      V s bleTokenRat oNormal zer.create nstance();

  // Allow default values only for nu r c types.
  pr vate stat c f nal Set<Thr ftSearchFeatureType> ALLOWED_TYPES_FOR_DEFAULT_FEATURE_VALUES =
      EnumSet.of(Thr ftSearchFeatureType. NT32_VALUE,
                 Thr ftSearchFeatureType.LONG_VALUE,
                 Thr ftSearchFeatureType.DOUBLE_VALUE);

  pr vate stat c f nal Set< nteger> NUMER C_FEATURES_FOR_WH CH_DEFAULTS_SHOULD_NOT_BE_SET =
       mmutableSet.of(Earlyb rdF eldConstant.TWEET_S GNATURE.getF eld d(),
                      Earlyb rdF eldConstant.REFERENCE_AUTHOR_ D_LEAST_S GN F CANT_ NT.getF eld d(),
                      Earlyb rdF eldConstant.REFERENCE_AUTHOR_ D_MOST_S GN F CANT_ NT.getF eld d());

  // Na  of t  scor ng funct on. Used for generat ng explanat ons.
  pr vate f nal Str ng funct onNa ;

  pr vate f nal BloomF lter trustedF lter;
  pr vate f nal BloomF lter followF lter;

  // Current t  stamp  n seconds. Overr dable by un  test or by t  stamp set  n search query.
  pr vate  nt now;

  pr vate f nal Ant Gam ngF lter ant Gam ngF lter;

  @Nullable
  pr vate f nal AgeDecay ageDecay;

  protected f nal L nearScor ngParams params;  // Para ters and query-dependent values.

  //  n order for t  AP  calls to retr eve t  correct `L nearScor ngData`
  // for t  passed `doc d`,   need to ma nta n a map of `doc d` -> `L nearScor ngData`
  // NOTE: TH S CAN ONLY BE REFERENCED AT H T COLLECT ON T ME, S NCE DOC  DS ARE NOT UN QUE
  // ACROSS SEGMENTS.  T'S NOT USABLE DUR NG BATCH SCOR NG.
  pr vate f nal Map< nteger, L nearScor ngData> doc dToScor ngData;

  pr vate f nal Thr ftSearchResultType searchResultType;

  pr vate f nal UserTable userTable;

  @V s bleForTest ng
  vo d setNow( nt fakeNow) {
    now = fakeNow;
  }

  publ c FeatureBasedScor ngFunct on(
      Str ng funct onNa ,
       mmutableSc ma nterface sc ma,
      Thr ftSearchQuery searchQuery,
      Ant Gam ngF lter ant Gam ngF lter,
      Thr ftSearchResultType searchResultType,
      UserTable userTable) throws  OExcept on {
    super(sc ma);

    t .funct onNa  = funct onNa ;
    t .searchResultType = searchResultType;
    t .userTable = userTable;

    Precond  ons.c ckNotNull(searchQuery.getRelevanceOpt ons());
    Thr ftRank ngParams rank ngParams = searchQuery.getRelevanceOpt ons().getRank ngParams();
    Precond  ons.c ckNotNull(rank ngParams);

    params = new L nearScor ngParams(searchQuery, rank ngParams);
    doc dToScor ngData = new HashMap<>();

    long t  stamp = searchQuery. sSetT  stampMsecs() && searchQuery.getT  stampMsecs() > 0
        ? searchQuery.getT  stampMsecs() : System.currentT  M ll s();
    now =  nts.c ckedCast(T  Un .M LL SECONDS.toSeconds(t  stamp));

    t .ant Gam ngF lter = ant Gam ngF lter;

    t .ageDecay = params.useAgeDecay
        ? new AgeDecay(params.ageDecayBase, params.ageDecayHalfl fe, params.ageDecaySlope)
        : null;

     f (searchQuery. sSetTrustedF lter()) {
      trustedF lter = new BloomF lter(searchQuery.getTrustedF lter());
    } else {
      trustedF lter = null;
    }

     f (searchQuery. sSetD rectFollowF lter()) {
      followF lter = new BloomF lter(searchQuery.getD rectFollowF lter());
    } else {
      followF lter = null;
    }
  }

  @V s bleForTest ng
  f nal L nearScor ngParams getScor ngParams() {
    return params;
  }

  /**
   * Returns t  L nearScor ngData  nstance assoc ated w h t  current doc  D.  f   doesn't ex st,
   * an empty L nearScor ngData  s created.
   */
  @Overr de
  publ c L nearScor ngData getScor ngDataForCurrentDocu nt() {
    L nearScor ngData data = doc dToScor ngData.get(getCurrentDoc D());
     f (data == null) {
      data = new L nearScor ngData();
      doc dToScor ngData.put(getCurrentDoc D(), data);
    }
    return data;
  }

  @Overr de
  publ c vo d setDebugMode( nt debugMode) {
    super.setDebugMode(debugMode);
  }

  /**
   * Normal t  lucene score, wh ch was unbounded, to a range of [1.0, maxLuceneScoreBoost].
   * T  normal zed value  ncreases almost l nearly  n t  lucene score range 2.0 ~ 7.0, w re
   * most quer es fall  n. For rare long ta l quer es, l ke so  hashtags, t y have h gh  df and
   * thus h gh lucene score, t  normal zed value won't have much d fference bet en t ets.
   * T  normal zat on funct on  s:
   *   ls = luceneScore
   *   norm = m n(max, 1 + (max - 1.0) / 2.4 * ln(1 + ls)
   */
  stat c float normal zeLuceneScore(float luceneScore, float maxBoost) {
    return (float) Math.m n(maxBoost, 1.0 + (maxBoost - 1.0) / 2.4 * Math.log1p(luceneScore));
  }

  @Overr de
  protected float score(float luceneQueryScore) throws  OExcept on {
    return score nternal(luceneQueryScore, null);
  }

  protected L nearScor ngData updateL nearScor ngData(float luceneQueryScore) throws  OExcept on {
    // Reset t  data for each t et!!!
    L nearScor ngData data = new L nearScor ngData();
    doc dToScor ngData.put(getCurrentDoc D(), data);

    // Set proper vers on for engage nt counters for t  request.
    data.sk pReason = Sk pReason.NOT_SK PPED;
    data.luceneScore = luceneQueryScore;
    data.userRep = (byte) docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.USER_REPUTAT ON);

     f (ant Gam ngF lter != null && !ant Gam ngF lter.accept(getCurrentDoc D())) {
      data.sk pReason = Sk pReason.ANT GAM NG;
      return data;
    }

    data.textScore = (byte) docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.TEXT_SCORE);
    data.tokenAt140D v dedByNumTokensBucket = V S BLE_TOKEN_RAT O_NORMAL ZER.denormal ze(
        (byte) docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.V S BLE_TOKEN_RAT O));
    data.fromUser d = docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.FROM_USER_ D_CSF);
    data. sFollow = followF lter != null
        && followF lter.conta ns(Longs.toByteArray(data.fromUser d));
    data. sTrusted = trustedF lter != null
        && trustedF lter.conta ns(Longs.toByteArray(data.fromUser d));
    data. sFromVer f edAccount = docu ntFeatures. sFlagSet(
        Earlyb rdF eldConstant.FROM_VER F ED_ACCOUNT_FLAG);
    data. sFromBlueVer f edAccount = docu ntFeatures. sFlagSet(
        Earlyb rdF eldConstant.FROM_BLUE_VER F ED_ACCOUNT_FLAG);
    data. sSelfT et = data.fromUser d == params.searc r d;
    // v1 engage nt counters, note that t  f rst three values are post-log2 vers on
    // of t  or g nal unnormal zed values.
    data.ret etCountPostLog2 = docu ntFeatures.getUnnormal zedFeatureValue(
        Earlyb rdF eldConstant.RETWEET_COUNT);
    data.replyCountPostLog2 = docu ntFeatures.getUnnormal zedFeatureValue(
        Earlyb rdF eldConstant.REPLY_COUNT);
    data.favCountPostLog2 = docu ntFeatures.getUnnormal zedFeatureValue(
        Earlyb rdF eldConstant.FAVOR TE_COUNT);
    data.embeds mpress onCount = docu ntFeatures.getUnnormal zedFeatureValue(
        Earlyb rdF eldConstant.EMBEDS_ MPRESS ON_COUNT);
    data.embedsUrlCount = docu ntFeatures.getUnnormal zedFeatureValue(
        Earlyb rdF eldConstant.EMBEDS_URL_COUNT);
    data.v deoV ewCount = docu ntFeatures.getUnnormal zedFeatureValue(
        Earlyb rdF eldConstant.V DEO_V EW_COUNT);
    // v2 engage nt counters
    data.ret etCountV2 = docu ntFeatures.getUnnormal zedFeatureValue(
        Earlyb rdF eldConstant.RETWEET_COUNT_V2);
    data.replyCountV2 = docu ntFeatures.getUnnormal zedFeatureValue(
        Earlyb rdF eldConstant.REPLY_COUNT_V2);
    data.favCountV2 = docu ntFeatures.getUnnormal zedFeatureValue(
        Earlyb rdF eldConstant.FAVOR TE_COUNT_V2);
    // ot r v2 engage nt counters
    data.embeds mpress onCountV2 = docu ntFeatures.getUnnormal zedFeatureValue(
        Earlyb rdF eldConstant.EMBEDS_ MPRESS ON_COUNT_V2);
    data.embedsUrlCountV2 = docu ntFeatures.getUnnormal zedFeatureValue(
        Earlyb rdF eldConstant.EMBEDS_URL_COUNT_V2);
    data.v deoV ewCountV2 = docu ntFeatures.getUnnormal zedFeatureValue(
        Earlyb rdF eldConstant.V DEO_V EW_COUNT_V2);
    // pure v2 engage nt counters w hout v1 counterpart
    data.quotedCount = docu ntFeatures.getUnnormal zedFeatureValue(
        Earlyb rdF eldConstant.QUOTE_COUNT);
    data.  ghtedRet etCount = docu ntFeatures.getUnnormal zedFeatureValue(
        Earlyb rdF eldConstant.WE GHTED_RETWEET_COUNT);
    data.  ghtedReplyCount = docu ntFeatures.getUnnormal zedFeatureValue(
        Earlyb rdF eldConstant.WE GHTED_REPLY_COUNT);
    data.  ghtedFavCount = docu ntFeatures.getUnnormal zedFeatureValue(
        Earlyb rdF eldConstant.WE GHTED_FAVOR TE_COUNT);
    data.  ghtedQuoteCount = docu ntFeatures.getUnnormal zedFeatureValue(
        Earlyb rdF eldConstant.WE GHTED_QUOTE_COUNT);

    Double querySpec f cScoreAdjust nt = params.querySpec f cScoreAdjust nts == null ? null
        : params.querySpec f cScoreAdjust nts.get(t et DMapper.getT et D(getCurrentDoc D()));
    data.querySpec f cScore =
        querySpec f cScoreAdjust nt == null ? 0.0 : querySpec f cScoreAdjust nt;

    data.authorSpec f cScore = params.authorSpec f cScoreAdjust nts == null
        ? 0.0
        : params.authorSpec f cScoreAdjust nts.getOrDefault(data.fromUser d, 0.0);

    // respect soc al f lter type
     f (params.soc alF lterType != null && !data. sSelfT et) {
       f ((params.soc alF lterType == Thr ftSoc alF lterType.ALL
              && !data. sFollow && !data. sTrusted)
          || (params.soc alF lterType == Thr ftSoc alF lterType.TRUSTED && !data. sTrusted)
          || (params.soc alF lterType == Thr ftSoc alF lterType.FOLLOWS && !data. sFollow)) {
        //   can sk p t  h  as   only want soc al results  n t  mode.
        data.sk pReason = Sk pReason.SOC AL_F LTER;
        return data;
      }
    }

    // 1. f rst apply all t  f lters to only non-follow t ets and non-ver f ed accounts,
    //    but be tender to sent nel values
    // unless   spec f cally asked to apply f lters regardless
     f (params.applyF ltersAlways
            || (!data. sSelfT et && !data. sFollow && !data. sFromVer f edAccount
                && !data. sFromBlueVer f edAccount)) {
       f (data.userRep < params.reputat onM nVal
          // don't f lter unset userreps,   g ve t m t  benef  of doubt and let  
          // cont nue to scor ng. userrep  s unset w n e  r user just s gned up or
          // dur ng  ngest on t     had trouble gett ng userrep from reputat on serv ce.
          && data.userRep != RelevanceS gnalConstants.UNSET_REPUTAT ON_SENT NEL) {
        data.sk pReason = Sk pReason.LOW_REPUTAT ON;
        return data;
      } else  f (data.textScore < params.textScoreM nVal
                 // don't f lter unset text scores, use goodw ll value
                 && data.textScore != RelevanceS gnalConstants.UNSET_TEXT_SCORE_SENT NEL) {
        data.sk pReason = Sk pReason.LOW_TEXT_SCORE;
        return data;
      } else  f (data.ret etCountPostLog2 != L nearScor ngData.UNSET_S GNAL_VALUE
                 && data.ret etCountPostLog2 < params.ret etM nVal) {
        data.sk pReason = Sk pReason.LOW_RETWEET_COUNT;
        return data;
      } else  f (data.favCountPostLog2 != L nearScor ngData.UNSET_S GNAL_VALUE
                 && data.favCountPostLog2 < params.favM nVal) {
        data.sk pReason = Sk pReason.LOW_FAV_COUNT;
        return data;
      }
    }

    //  f sent nel value  s set, assu  goodw ll score and let scor ng cont nue.
     f (data.textScore == RelevanceS gnalConstants.UNSET_TEXT_SCORE_SENT NEL) {
      data.textScore = RelevanceS gnalConstants.GOODW LL_TEXT_SCORE;
    }
     f (data.userRep == RelevanceS gnalConstants.UNSET_REPUTAT ON_SENT NEL) {
      data.userRep = RelevanceS gnalConstants.GOODW LL_REPUTAT ON;
    }

    data.t etAge nSeconds = now - t  Mapper.getT  (getCurrentDoc D());
     f (data.t etAge nSeconds < 0) {
      data.t etAge nSeconds = 0; // Age cannot be negat ve
    }

    // T  PARUS_SCORE feature should be read as  s.
    data.parusScore = docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.PARUS_SCORE);

    data. sNullcast = docu ntFeatures. sFlagSet(Earlyb rdF eldConstant. S_NULLCAST_FLAG);
    data.hasUrl =  docu ntFeatures. sFlagSet(Earlyb rdF eldConstant.HAS_L NK_FLAG);
    data.has mageUrl = docu ntFeatures. sFlagSet(Earlyb rdF eldConstant.HAS_ MAGE_URL_FLAG);
    data.hasV deoUrl = docu ntFeatures. sFlagSet(Earlyb rdF eldConstant.HAS_V DEO_URL_FLAG);
    data.hasNewsUrl = docu ntFeatures. sFlagSet(Earlyb rdF eldConstant.HAS_NEWS_URL_FLAG);
    data. sReply =  docu ntFeatures. sFlagSet(Earlyb rdF eldConstant. S_REPLY_FLAG);
    data. sRet et = docu ntFeatures. sFlagSet(Earlyb rdF eldConstant. S_RETWEET_FLAG);
    data. sOffens ve = docu ntFeatures. sFlagSet(Earlyb rdF eldConstant. S_OFFENS VE_FLAG);
    data.hasTrend = docu ntFeatures. sFlagSet(Earlyb rdF eldConstant.HAS_TREND_FLAG);
    data.hasMult pleHashtagsOrTrends =
        docu ntFeatures. sFlagSet(Earlyb rdF eldConstant.HAS_MULT PLE_HASHTAGS_OR_TRENDS_FLAG);
    data. sUserSpam = docu ntFeatures. sFlagSet(Earlyb rdF eldConstant. S_USER_SPAM_FLAG);
    data. sUserNSFW = docu ntFeatures. sFlagSet(Earlyb rdF eldConstant. S_USER_NSFW_FLAG)
        || userTable. sSet(data.fromUser d, UserTable.NSFW_B T);
    data. sUserAnt Soc al =
        userTable. sSet(data.fromUser d, UserTable.ANT SOC AL_B T);
    data. sUserBot = docu ntFeatures. sFlagSet(Earlyb rdF eldConstant. S_USER_BOT_FLAG);
    data.hasCard = docu ntFeatures. sFlagSet(Earlyb rdF eldConstant.HAS_CARD_FLAG);
    data.cardType = SearchCardType.UNKNOWN.getByteValue();
     f (data.hasCard) {
      data.cardType =
          (byte) docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.CARD_TYPE_CSF_F ELD);
    }
    data.hasV s bleL nk = docu ntFeatures. sFlagSet(Earlyb rdF eldConstant.HAS_V S BLE_L NK_FLAG);

    data.hasConsu rV deo =
        docu ntFeatures. sFlagSet(Earlyb rdF eldConstant.HAS_CONSUMER_V DEO_FLAG);
    data.hasProV deo = docu ntFeatures. sFlagSet(Earlyb rdF eldConstant.HAS_PRO_V DEO_FLAG);
    data.hasV ne = docu ntFeatures. sFlagSet(Earlyb rdF eldConstant.HAS_V NE_FLAG);
    data.hasPer scope = docu ntFeatures. sFlagSet(Earlyb rdF eldConstant.HAS_PER SCOPE_FLAG);
    data.hasNat ve mage = docu ntFeatures. sFlagSet(Earlyb rdF eldConstant.HAS_NAT VE_ MAGE_FLAG);
    data.hasQuote = docu ntFeatures. sFlagSet(Earlyb rdF eldConstant.HAS_QUOTE_FLAG);
    data. sComposerS ceCa ra =
        docu ntFeatures. sFlagSet(Earlyb rdF eldConstant.COMPOSER_SOURCE_ S_CAMERA_FLAG);

    // Only read t  shared status  f t   sRet et or  sReply b   s true (m nor opt m zat on).
     f (data. sRet et || (params.get nReplyToStatus d && data. sReply)) {
      data.sharedStatus d =
          docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.SHARED_STATUS_ D_CSF);
    }

    // Only read t  reference t et author  D  f t   sRet et or  sReply b 
    //  s true (m nor opt m zat on).
     f (data. sRet et || data. sReply) {
      // t  REFERENCE_AUTHOR_ D_CSF stores t  s ce t et author  d for all ret ets
      long referenceAuthor d =
          docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.REFERENCE_AUTHOR_ D_CSF);
       f (referenceAuthor d > 0) {
        data.referenceAuthor d = referenceAuthor d;
      } else {
        //   also store t  reference author  d for ret ets, d rected at t ets, and self threaded
        // t ets separately on Realt  /Protected Earlyb rds. T  data w ll be moved to t 
        // REFERENCE_AUTHOR_ D_CSF and t se f elds w ll be deprecated  n SEARCH-34958.
        referenceAuthor d = Long ntConverter.convertTwo ntToOneLong(
            ( nt) docu ntFeatures.getFeatureValue(
                Earlyb rdF eldConstant.REFERENCE_AUTHOR_ D_MOST_S GN F CANT_ NT),
            ( nt) docu ntFeatures.getFeatureValue(
                Earlyb rdF eldConstant.REFERENCE_AUTHOR_ D_LEAST_S GN F CANT_ NT));
         f (referenceAuthor d > 0) {
          data.referenceAuthor d = referenceAuthor d;
        }
      }
    }

    // Convert language to a thr ft language and t n back to an  nt  n order to
    // ensure a value compat ble w h   current Thr ftLanguage def n  on.
    Thr ftLanguage t etLang = Thr ftLanguageUt l.safeF ndByValue(
        ( nt) docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.LANGUAGE));
    data.t etLang d = t etLang.getValue();
    // Set t  language-related features  re so that t y can be later used  n promot on/demot on
    // and also be transferred to Thr ftSearchResult tadata
    data.userLangMult = computeUserLangMult pl er(data, params);
    data.hasD fferentLang = params.u Lang d != Thr ftLanguage.UNKNOWN.getValue()
        && params.u Lang d != data.t etLang d;
    data.hasEngl shT etAndD fferentU Lang = data.hasD fferentLang
        && data.t etLang d == Thr ftLanguage.ENGL SH.getValue();
    data.hasEngl shU AndD fferentT etLang = data.hasD fferentLang
        && params.u Lang d == Thr ftLanguage.ENGL SH.getValue();

    // Exposed all t se features for t  cl ents.
    data. sSens  veContent =
        docu ntFeatures. sFlagSet(Earlyb rdF eldConstant. S_SENS T VE_CONTENT);
    data.hasMult ple d aFlag =
        docu ntFeatures. sFlagSet(Earlyb rdF eldConstant.HAS_MULT PLE_MED A_FLAG);
    data.prof le sEggFlag = docu ntFeatures. sFlagSet(Earlyb rdF eldConstant.PROF LE_ S_EGG_FLAG);
    data. sUserNewFlag = docu ntFeatures. sFlagSet(Earlyb rdF eldConstant. S_USER_NEW_FLAG);
    data.num nt ons = ( nt) docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.NUM_MENT ONS);
    data.numHashtags = ( nt) docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.NUM_HASHTAGS);
    data.l nkLanguage =
        ( nt) docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.L NK_LANGUAGE);
    data.prevUserT etEngage nt =
        ( nt) docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.PREV_USER_TWEET_ENGAGEMENT);

    //  alth model scores by HML
    data.tox c yScore = docu ntFeatures.getUnnormal zedFeatureValue(
        Earlyb rdF eldConstant.TOX C TY_SCORE);
    data.pBlockScore = docu ntFeatures.getUnnormal zedFeatureValue(
        Earlyb rdF eldConstant.PBLOCK_SCORE);
    data.pSpam T etScore = docu ntFeatures.getUnnormal zedFeatureValue(
        Earlyb rdF eldConstant.P_SPAMMY_TWEET_SCORE);
    data.pReportedT etScore = docu ntFeatures.getUnnormal zedFeatureValue(
        Earlyb rdF eldConstant.P_REPORTED_TWEET_SCORE);
    data.spam T etContentScore = docu ntFeatures.getUnnormal zedFeatureValue(
        Earlyb rdF eldConstant.SPAMMY_TWEET_CONTENT_SCORE
    );
    data.exper  ntal althModelScore1 = docu ntFeatures.getUnnormal zedFeatureValue(
        Earlyb rdF eldConstant.EXPER MENTAL_HEALTH_MODEL_SCORE_1);
    data.exper  ntal althModelScore2 = docu ntFeatures.getUnnormal zedFeatureValue(
        Earlyb rdF eldConstant.EXPER MENTAL_HEALTH_MODEL_SCORE_2);
    data.exper  ntal althModelScore3 = docu ntFeatures.getUnnormal zedFeatureValue(
        Earlyb rdF eldConstant.EXPER MENTAL_HEALTH_MODEL_SCORE_3);
    data.exper  ntal althModelScore4 = docu ntFeatures.getUnnormal zedFeatureValue(
        Earlyb rdF eldConstant.EXPER MENTAL_HEALTH_MODEL_SCORE_4);

    return data;
  }

  protected float score nternal(
      float luceneQueryScore, Explanat onWrapper explanat on) throws  OExcept on {
    L nearScor ngData data = updateL nearScor ngData(luceneQueryScore);
     f (data.sk pReason != null && data.sk pReason != Sk pReason.NOT_SK PPED) {
      return f nal zeScore(data, explanat on, SK P_H T);
    }

    double score = computeScore(data, explanat on != null);
    return postScoreComputat on(data, score, true, explanat on);
  }

  protected float postScoreComputat on(
      L nearScor ngData data,
      double score,
      boolean boostScoreW hH Attr but on,
      Explanat onWrapper explanat on) throws  OExcept on {
    double mod f edScore = score;
    data.scoreBeforeBoost = mod f edScore;
     f (params.applyBoosts) {
      mod f edScore =
          applyBoosts(data, mod f edScore, boostScoreW hH Attr but on, explanat on != null);
    }
    // F nal adjust nt to avo d too-low scores.
    mod f edScore *= SCORE_ADJUSTER;
    data.scoreAfterBoost = mod f edScore;

    // 3. f nal score f lter
    data.scoreF nal = mod f edScore;
     f ((params.applyF ltersAlways || (!data. sSelfT et && !data. sFollow))
        && mod f edScore < params.m nScore) {
      data.sk pReason = Sk pReason.LOW_F NAL_SCORE;
      mod f edScore = SK P_H T;
    }

    // clear f eld h s
    t .f eldH Attr but on = null;
    return f nal zeScore(data, explanat on, mod f edScore);
  }

  /**
   * Apply ng promot on/demot on to t  scores generated by feature-based scor ng funct ons
   *
   * @param data Or g nal L nearScor ngData (to be mod f ed w h boosts  re)
   * @param score Score generated by t  feature-based scor ng funct on
   * @param w hH Attr but on Determ nes  f h  attr but on data should be  ncluded.
   * @param forExplanat on  nd cates  f t  score w ll be computed for generat ng t  explanat on.
   * @return Score after apply ng promot on/demot on
   */
  pr vate double applyBoosts(
      L nearScor ngData data,
      double score,
      boolean w hH Attr but on,
      boolean forExplanat on) {
    double boostedScore = score;

     f (params.useLuceneScoreAsBoost) {
      data.normal zedLuceneScore = normal zeLuceneScore(
          (float) data.luceneScore, (float) params.maxLuceneScoreBoost);
      boostedScore *= data.normal zedLuceneScore;
    }
     f (data. sOffens ve) {
      boostedScore *= params.offens veDamp ng;
    }
     f (data. sUserSpam && params.spamUserDamp ng != L nearScor ngData.NO_BOOST_VALUE) {
      data.spamUserDampAppl ed = true;
      boostedScore *= params.spamUserDamp ng;
    }
     f (data. sUserNSFW && params.nsfwUserDamp ng != L nearScor ngData.NO_BOOST_VALUE) {
      data.nsfwUserDampAppl ed = true;
      boostedScore *= params.nsfwUserDamp ng;
    }
     f (data. sUserBot && params.botUserDamp ng != L nearScor ngData.NO_BOOST_VALUE) {
      data.botUserDampAppl ed = true;
      boostedScore *= params.botUserDamp ng;
    }

    // cards
     f (data.hasCard && params.hasCardBoosts[data.cardType] != L nearScor ngData.NO_BOOST_VALUE) {
      boostedScore *= params.hasCardBoosts[data.cardType];
      data.hasCardBoostAppl ed = true;
    }

    // trends
     f (data.hasMult pleHashtagsOrTrends) {
      boostedScore *= params.mult pleHashtagsOrTrendsDamp ng;
    } else  f (data.hasTrend) {
      data.t etHasTrendsBoostAppl ed = true;
      boostedScore *= params.t etHasTrendBoost;
    }

    //  d a/News url boosts.
     f (data.has mageUrl || data.hasV deoUrl) {
      data.has d alUrlBoostAppl ed = true;
      boostedScore *= params.t etHas d aUrlBoost;
    }
     f (data.hasNewsUrl) {
      data.hasNewsUrlBoostAppl ed = true;
      boostedScore *= params.t etHasNewsUrlBoost;
    }

     f (data. sFromVer f edAccount) {
      data.t etFromVer f edAccountBoostAppl ed = true;
      boostedScore *= params.t etFromVer f edAccountBoost;
    }

     f (data. sFromBlueVer f edAccount) {
      data.t etFromBlueVer f edAccountBoostAppl ed = true;
      boostedScore *= params.t etFromBlueVer f edAccountBoost;
    }

     f (data. sFollow) {
      // d rect follow, so boost both repl es and non-repl es.
      data.d rectFollowBoostAppl ed = true;
      boostedScore *= params.d rectFollowBoost;
    } else  f (data. sTrusted) {
      // trusted c rcle
       f (!data. sReply) {
        // non-at-reply,  n trusted network
        data.trustedC rcleBoostAppl ed = true;
        boostedScore *= params.trustedC rcleBoost;
      }
    } else  f (data. sReply) {
      // at-reply out of   network
      data.outOfNetworkReplyPenaltyAppl ed = true;
      boostedScore -= params.outOfNetworkReplyPenalty;
    }

     f (data. sSelfT et) {
      data.selfT etBoostAppl ed = true;
      data.selfT etMult = params.selfT etBoost;
      boostedScore *= params.selfT etBoost;
    }

    // Language Demot on
    // User language based demot on
    // T  data.userLangMult  s set  n score nternal(), and t  sett ng step  s always before
    // t  apply ng boosts step
     f (params.useUserLanguage nfo) {
      boostedScore *= data.userLangMult;
    }
    // U  language based demot on
     f (params.u Lang d != Thr ftLanguage.UNKNOWN.getValue()
        && params.u Lang d != data.t etLang d) {
       f (data.t etLang d == Thr ftLanguage.ENGL SH.getValue()) {
        data.u LangMult = params.langEngl shT etDemote;
      } else  f (params.u Lang d == Thr ftLanguage.ENGL SH.getValue()) {
        data.u LangMult = params.langEngl shU Demote;
      } else {
        data.u LangMult = params.langDefaultDemote;
      }
    } else {
      data.u LangMult = L nearScor ngData.NO_BOOST_VALUE;
    }
    boostedScore *= data.u LangMult;

     f (params.useAgeDecay) {
      // shallow s gmo d w h an  nflect on po nt at ageDecayHalfl fe
      data.ageDecayMult = ageDecay.getAgeDecayMult pl er(data.t etAge nSeconds);
      boostedScore *= data.ageDecayMult;
    }

    // H  Attr bute Demot on
    // Scor ng  s currently based on token zed user na , text, and url  n t  t et
    //  f h  attr bute collect on  s enabled,   demote score based on t se f elds
     f (h Attr bute lper != null && params.enableH Demot on) {

      Map< nteger, L st<Str ng>> h Map;
       f (forExplanat on && f eldH Attr but on != null) {
        //  f t  scor ng call  s for generat ng an explanat on,
        //  'll use t  f eldH Attr but on found  n t  search result's  tadata because
        // collectors are not called dur ng t  debug workflow
        h Map = Maps.transformValues(f eldH Attr but on.getH Map(), F eldH L st::getH F elds);
      } else  f (w hH Attr but on) {
        h Map = h Attr bute lper.getH Attr but on(getCurrentDoc D());
      } else {
        h Map = Maps.newHashMap();
      }
      Set<Str ng> un queF eldH s =  mmutableSet.copyOf( erables.concat(h Map.values()));

      data.h F elds.addAll(un queF eldH s);
      // t re should always be f elds that are h 
      //  f t re aren't,   assu  t   s a call from 'expla n'  n debug mode
      // do not overr de h  attr bute data  f  n debug mode
       f (!un queF eldH s. sEmpty()) {
        // demot ons based str ctly on f eld h s
         f (un queF eldH s.s ze() == 1) {
           f (un queF eldH s.conta ns(
                  Earlyb rdF eldConstant.RESOLVED_L NKS_TEXT_F ELD.getF eldNa ())) {
            //  f url was t  only f eld that was h , demote
            data.hasUrlOnlyH Demot onAppl ed = true;
            boostedScore *= params.urlOnlyH Demot on;
          } else  f (un queF eldH s.conta ns(
                         Earlyb rdF eldConstant.TOKEN ZED_FROM_USER_F ELD.getF eldNa ())) {
            //  f na  was t  only f eld that was h , demote
            data.hasNa OnlyH Demot onAppl ed = true;
            boostedScore *= params.na OnlyH Demot on;
          }
        } else  f (!un queF eldH s.conta ns(Earlyb rdF eldConstant.TEXT_F ELD.getF eldNa ())
            && !un queF eldH s.conta ns(Earlyb rdF eldConstant.MENT ONS_F ELD.getF eldNa ())
            && !un queF eldH s.conta ns(Earlyb rdF eldConstant.HASHTAGS_F ELD.getF eldNa ())
            && !un queF eldH s.conta ns(Earlyb rdF eldConstant.STOCKS_F ELD.getF eldNa ())) {
          //  f text or spec al text was never h , demote
          data.hasNoTextH Demot onAppl ed = true;
          boostedScore *= params.noTextH Demot on;
        } else  f (un queF eldH s.s ze() == 2) {
          // demot ons based on f eld h  comb nat ons
          // want to demote  f   only h  two of t  f elds (one be ng text)
          // but w h separate terms
          Set<Str ng> f eld ntersect ons = QueryCommonF eldH sV s or.f nd ntersect on(
              h Attr bute lper.getNodeToRankMap(),
              h Map,
              query);

           f (f eld ntersect ons. sEmpty()) {
             f (un queF eldH s.conta ns(
                    Earlyb rdF eldConstant.TOKEN ZED_FROM_USER_F ELD.getF eldNa ())) {
              //  f na   s h  but has no h s  n common w h text, demote
              // want to demote cases w re   h  part of t  person's na 
              // and t et text separately
              data.hasSeparateTextAndNa H Demot onAppl ed = true;
              boostedScore *= params.separateTextAndNa H Demot on;
            } else  f (un queF eldH s.conta ns(
                           Earlyb rdF eldConstant.RESOLVED_L NKS_TEXT_F ELD.getF eldNa ())) {
              //  f url  s h  but has no h s  n common w h text, demote
              // want to demote cases w re   h  a potent al doma n keyword
              // and t et text separately
              data.hasSeparateTextAndUrlH Demot onAppl ed = true;
              boostedScore *= params.separateTextAndUrlH Demot on;
            }
          }
        }
      }
    }

    return boostedScore;
  }

  /**
   * Compute t  user language based demot on mult pl er
   */
  pr vate stat c double computeUserLangMult pl er(
      L nearScor ngData data, L nearScor ngParams params) {
     f (data.t etLang d == params.u Lang d
        && data.t etLang d != Thr ftLanguage.UNKNOWN.getValue()) {
      // Effect vely t  u Lang  s cons dered a language that user knows w h 1.0 conf dence.
      return L nearScor ngData.NO_BOOST_VALUE;
    }

     f (params.userLangs[data.t etLang d] > 0.0) {
      return params.userLangs[data.t etLang d];
    }

    return params.unknownLanguageBoost;
  }

  /**
   * Computes t  score of t  docu nt that  's currently be ng evaluated.
   *
   * T  extracted features from t  docu nt are ava lable  n t  f eld 'data'.
   *
   * @param data T  L nearScor ngData  nstance that w ll store t  docu nt features.
   * @param forExplanat on  nd cates  f t  score w ll be computed for generat ng t  explanat on.
   */
  protected abstract double computeScore(
      L nearScor ngData data, boolean forExplanat on) throws  OExcept on;

  pr vate float f nal zeScore(
      L nearScor ngData scor ngData,
      Explanat onWrapper explanat on,
      double score) throws  OExcept on {
    scor ngData.scoreReturned = score;
     f (explanat on != null) {
      explanat on.explanat on = generateExplanat on(scor ngData);
    }
    return (float) score;
  }

  @Overr de
  protected vo d  n  al zeNextSeg nt(Earlyb rd ndexSeg ntAtom cReader reader)
      throws  OExcept on {
     f (ant Gam ngF lter != null) {
      ant Gam ngF lter.startSeg nt(reader);
    }
  }

  /*
   * Generate t  scor ng explanat on for debug.
   */
  pr vate Explanat on generateExplanat on(L nearScor ngData scor ngData) throws  OExcept on {
    f nal L st<Explanat on> deta ls = L sts.newArrayL st();

    deta ls.add(Explanat on.match(0.0f, "[PROPERT ES] "
        + scor ngData.getPropertyExplanat on()));

    // 1. F lters
    boolean  sH  = scor ngData.sk pReason == Sk pReason.NOT_SK PPED;
     f (scor ngData.sk pReason == Sk pReason.ANT GAM NG) {
      deta ls.add(Explanat on.noMatch("SK PPED for ant gam ng"));
    }
     f (scor ngData.sk pReason == Sk pReason.LOW_REPUTAT ON) {
      deta ls.add(Explanat on.noMatch(
          Str ng.format("SK PPED for low reputat on: %.3f < %.3f",
              scor ngData.userRep, params.reputat onM nVal)));
    }
     f (scor ngData.sk pReason == Sk pReason.LOW_TEXT_SCORE) {
      deta ls.add(Explanat on.noMatch(
          Str ng.format("SK PPED for low text score: %.3f < %.3f",
              scor ngData.textScore, params.textScoreM nVal)));
    }
     f (scor ngData.sk pReason == Sk pReason.LOW_RETWEET_COUNT) {
      deta ls.add(Explanat on.noMatch(
          Str ng.format("SK PPED for low ret et count: %.3f < %.3f",
              scor ngData.ret etCountPostLog2, params.ret etM nVal)));
    }
     f (scor ngData.sk pReason == Sk pReason.LOW_FAV_COUNT) {
      deta ls.add(Explanat on.noMatch(
          Str ng.format("SK PPED for low fav count: %.3f < %.3f",
              scor ngData.favCountPostLog2, params.favM nVal)));
    }
     f (scor ngData.sk pReason == Sk pReason.SOC AL_F LTER) {
      deta ls.add(Explanat on.noMatch("SK PPED for not  n t  r ght soc al c rcle"));
    }

    // 2. Explanat on depend ng on t  scor ng type
    generateExplanat onForScor ng(scor ngData,  sH , deta ls);

    // 3. Explanat on depend ng on boosts
     f (params.applyBoosts) {
      generateExplanat onForBoosts(scor ngData,  sH , deta ls);
    }

    // 4. F nal score f lter.
     f (scor ngData.sk pReason == Sk pReason.LOW_F NAL_SCORE) {
      deta ls.add(Explanat on.noMatch("SK PPED for low f nal score: " + scor ngData.scoreF nal));
       sH  = false;
    }

    Str ng hostAndSeg nt = Str ng.format("%s host = %s  seg nt = %s",
        funct onNa , DatabaseConf g.getLocalHostna (), DatabaseConf g.getDatabase());
     f ( sH ) {
      return Explanat on.match((float) scor ngData.scoreF nal, hostAndSeg nt, deta ls);
    } else {
      return Explanat on.noMatch(hostAndSeg nt, deta ls);
    }
  }

  /**
   * Generates t  explanat on for t  docu nt that  s currently be ng evaluated.
   *
   *  mple ntat ons of t   thod must use t  'deta ls' para ter to collect  s output.
   *
   * @param scor ngData Scor ng components for t  docu nt
   * @param  sH   nd cates w t r t  docu nt  s not sk pped
   * @param deta ls Deta ls of t  explanat on. Used to collect t  output.
   */
  protected abstract vo d generateExplanat onForScor ng(
      L nearScor ngData scor ngData, boolean  sH , L st<Explanat on> deta ls) throws  OExcept on;

  /**
   * Generates t  boosts part of t  explanat on for t  docu nt that  s currently
   * be ng evaluated.
   */
  pr vate vo d generateExplanat onForBoosts(
      L nearScor ngData scor ngData,
      boolean  sH ,
      L st<Explanat on> deta ls) {
    L st<Explanat on> boostDeta ls = L sts.newArrayL st();

    boostDeta ls.add(Explanat on.match((float) scor ngData.scoreBeforeBoost, "Score before boost"));


    // Lucene score boost
     f (params.useLuceneScoreAsBoost) {
      boostDeta ls.add(Explanat on.match(
          (float) scor ngData.normal zedLuceneScore,
          Str ng.format("[x] Lucene score boost, luceneScore=%.3f",
              scor ngData.luceneScore)));
    }

    // card boost
     f (scor ngData.hasCardBoostAppl ed) {
      boostDeta ls.add(Explanat on.match((float) params.hasCardBoosts[scor ngData.cardType],
          "[x] card boost for type " + SearchCardType.cardTypeFromByteValue(scor ngData.cardType)));
    }

    // Offens ve
     f (scor ngData. sOffens ve) {
      boostDeta ls.add(Explanat on.match((float) params.offens veDamp ng, "[x] Offens ve damp ng"));
    } else {
      boostDeta ls.add(Explanat on.match(L nearScor ngData.NO_BOOST_VALUE,
          Str ng.format("Not Offens ve, damp ng=%.3f", params.offens veDamp ng)));
    }

    // Spam
     f (scor ngData.spamUserDampAppl ed) {
      boostDeta ls.add(Explanat on.match((float) params.spamUserDamp ng, "[x] Spam"));
    }
    // NSFW
     f (scor ngData.nsfwUserDampAppl ed) {
      boostDeta ls.add(Explanat on.match((float) params.nsfwUserDamp ng, "[X] NSFW"));
    }
    // Bot
     f (scor ngData.botUserDampAppl ed) {
      boostDeta ls.add(Explanat on.match((float) params.botUserDamp ng, "[X] Bot"));
    }

    // Mult ple hashtags or trends
     f (scor ngData.hasMult pleHashtagsOrTrends) {
      boostDeta ls.add(Explanat on.match((float) params.mult pleHashtagsOrTrendsDamp ng,
          "[x] Mult ple hashtags or trends boost"));
    } else {
      boostDeta ls.add(Explanat on.match(L nearScor ngData.NO_BOOST_VALUE,
          Str ng.format("No mult ple hashtags or trends, damp ng=%.3f",
              params.mult pleHashtagsOrTrendsDamp ng)));
    }

     f (scor ngData.t etHasTrendsBoostAppl ed) {
      boostDeta ls.add(Explanat on.match(
          (float) params.t etHasTrendBoost, "[x] T et has trend boost"));
    }

     f (scor ngData.has d alUrlBoostAppl ed) {
      boostDeta ls.add(Explanat on.match(
          (float) params.t etHas d aUrlBoost, "[x]  d a url boost"));
    }

     f (scor ngData.hasNewsUrlBoostAppl ed) {
      boostDeta ls.add(Explanat on.match(
          (float) params.t etHasNewsUrlBoost, "[x] News url boost"));
    }

    boostDeta ls.add(Explanat on.match(0.0f, "[F ELDS H T] " + scor ngData.h F elds));

     f (scor ngData.hasNoTextH Demot onAppl ed) {
      boostDeta ls.add(Explanat on.match(
          (float) params.noTextH Demot on, "[x] No text h  demot on"));
    }

     f (scor ngData.hasUrlOnlyH Demot onAppl ed) {
      boostDeta ls.add(Explanat on.match(
          (float) params.urlOnlyH Demot on, "[x] URL only h  demot on"));
    }

     f (scor ngData.hasNa OnlyH Demot onAppl ed) {
      boostDeta ls.add(Explanat on.match(
          (float) params.na OnlyH Demot on, "[x] Na  only h  demot on"));
    }

     f (scor ngData.hasSeparateTextAndNa H Demot onAppl ed) {
      boostDeta ls.add(Explanat on.match((float) params.separateTextAndNa H Demot on,
          "[x] Separate text/na  demot on"));
    }

     f (scor ngData.hasSeparateTextAndUrlH Demot onAppl ed) {
      boostDeta ls.add(Explanat on.match((float) params.separateTextAndUrlH Demot on,
          "[x] Separate text/url demot on"));
    }

     f (scor ngData.t etFromVer f edAccountBoostAppl ed) {
      boostDeta ls.add(Explanat on.match((float) params.t etFromVer f edAccountBoost,
          "[x] Ver f ed account boost"));
    }

     f (scor ngData.t etFromBlueVer f edAccountBoostAppl ed) {
      boostDeta ls.add(Explanat on.match((float) params.t etFromBlueVer f edAccountBoost,
          "[x] Blue-ver f ed account boost"));
    }

     f (scor ngData.selfT etBoostAppl ed) {
      boostDeta ls.add(Explanat on.match((float) params.selfT etBoost,
          "[x] Self t et boost"));
    }

     f (scor ngData.sk pReason == L nearScor ngData.Sk pReason.SOC AL_F LTER) {
      boostDeta ls.add(Explanat on.noMatch("SK PPED for soc al f lter"));
    } else {
       f (scor ngData.d rectFollowBoostAppl ed) {
        boostDeta ls.add(Explanat on.match((float) params.d rectFollowBoost,
            "[x] D rect follow boost"));
      }
       f (scor ngData.trustedC rcleBoostAppl ed) {
        boostDeta ls.add(Explanat on.match((float) params.trustedC rcleBoost,
            "[x] Trusted c rcle boost"));
      }
       f (scor ngData.outOfNetworkReplyPenaltyAppl ed) {
        boostDeta ls.add(Explanat on.match((float) params.outOfNetworkReplyPenalty,
            "[-] Out of network reply penalty"));
      }
    }

    // Language demot ons
    Str ng langDeta ls = Str ng.format(
        "t etLang=[%s] u Lang=[%s]",
        Thr ftLanguageUt l.getLocaleOf(
            Thr ftLanguage.f ndByValue(scor ngData.t etLang d)).getLanguage(),
        Thr ftLanguageUt l.getLocaleOf(Thr ftLanguage.f ndByValue(params.u Lang d)).getLanguage());
     f (scor ngData.u LangMult == 1.0) {
      boostDeta ls.add(Explanat on.match(
          L nearScor ngData.NO_BOOST_VALUE, "No U  Language demot on: " + langDeta ls));
    } else {
      boostDeta ls.add(Explanat on.match(
          (float) scor ngData.u LangMult, "[x] U  LangMult: " + langDeta ls));
    }
    Str ngBu lder userLangDeta ls = new Str ngBu lder();
    userLangDeta ls.append("userLang=[");
    for ( nt   = 0;   < params.userLangs.length;  ++) {
       f (params.userLangs[ ] > 0.0) {
        Str ng lang = Thr ftLanguageUt l.getLocaleOf(Thr ftLanguage.f ndByValue( )).getLanguage();
        userLangDeta ls.append(Str ng.format("%s:%.3f,", lang, params.userLangs[ ]));
      }
    }
    userLangDeta ls.append("]");
     f (!params.useUserLanguage nfo) {
      boostDeta ls.add(Explanat on.noMatch(
          "No User Language Demot on: " + userLangDeta ls.toStr ng()));
    } else {
      boostDeta ls.add(Explanat on.match(
          (float) scor ngData.userLangMult,
          "[x] User LangMult: " + userLangDeta ls.toStr ng()));
    }

    // Age decay
    Str ng ageDecayDeta ls = Str ng.format(
        "age=%d seconds, slope=%.3f, base=%.1f, half-l fe=%.0f",
        scor ngData.t etAge nSeconds, params.ageDecaySlope,
        params.ageDecayBase, params.ageDecayHalfl fe);
     f (params.useAgeDecay) {
      boostDeta ls.add(Explanat on.match(
          (float) scor ngData.ageDecayMult, "[x] AgeDecay: " + ageDecayDeta ls));
    } else {
      boostDeta ls.add(Explanat on.match(1.0f, "Age decay d sabled: " + ageDecayDeta ls));
    }

    // Score adjuster
    boostDeta ls.add(Explanat on.match(SCORE_ADJUSTER, "[x] score adjuster"));

    Explanat on boostCombo =  sH 
        ? Explanat on.match((float) scor ngData.scoreAfterBoost,
          "(MATCH) After Boosts and Demot ons:", boostDeta ls)
        : Explanat on.noMatch("After Boosts and Demot ons:", boostDeta ls);

    deta ls.add(boostCombo);
  }

  @Overr de
  protected Explanat on doExpla n(float luceneQueryScore) throws  OExcept on {
    // Run t  scorer aga n and get t  explanat on.
    Explanat onWrapper explanat on = new Explanat onWrapper();
    score nternal(luceneQueryScore, explanat on);
    return explanat on.explanat on;
  }

  @Overr de
  publ c vo d populateResult tadataBasedOnScor ngData(
      Thr ftSearchResult tadataOpt ons opt ons,
      Thr ftSearchResult tadata  tadata,
      L nearScor ngData data) throws  OExcept on {
     tadata.setResultType(searchResultType);
     tadata.setScore(data.scoreReturned);
     tadata.setFromUser d(data.fromUser d);

     f (data. sTrusted) {
       tadata.set sTrusted(true);
    }
     f (data. sFollow) {
       tadata.set sFollow(true);
    }
     f (data.sk pReason != Sk pReason.NOT_SK PPED) {
       tadata.setSk pped(true);
    }
     f ((data. sRet et || (params.get nReplyToStatus d && data. sReply))
        && data.sharedStatus d != L nearScor ngData.UNSET_S GNAL_VALUE) {
       tadata.setSharedStatus d(data.sharedStatus d);
    }
     f (data.hasCard) {
       tadata.setCardType(data.cardType);
    }

    // Opt onal features.  Note: ot r opt onal  tadata  s populated by
    // AbstractRelevanceCollector, not t  scor ng funct on.

     f (opt ons. sGetLuceneScore()) {
       tadata.setLuceneScore(data.luceneScore);
    }
     f (opt ons. sGetReferencedT etAuthor d()
        && data.referenceAuthor d != L nearScor ngData.UNSET_S GNAL_VALUE) {
       tadata.setReferencedT etAuthor d(data.referenceAuthor d);
    }

     f (opt ons. sGet d aB s()) {
       tadata.setHasConsu rV deo(data.hasConsu rV deo);
       tadata.setHasProV deo(data.hasProV deo);
       tadata.setHasV ne(data.hasV ne);
       tadata.setHasPer scope(data.hasPer scope);
      boolean hasNat veV deo =
          data.hasConsu rV deo || data.hasProV deo || data.hasV ne || data.hasPer scope;
       tadata.setHasNat veV deo(hasNat veV deo);
       tadata.setHasNat ve mage(data.hasNat ve mage);
    }

     tadata
        .set sOffens ve(data. sOffens ve)
        .set sReply(data. sReply)
        .set sRet et(data. sRet et)
        .setHasL nk(data.hasUrl)
        .setHasTrend(data.hasTrend)
        .setHasMult pleHashtagsOrTrends(data.hasMult pleHashtagsOrTrends)
        .setRet etCount(( nt) data.ret etCountPostLog2)
        .setFavCount(( nt) data.favCountPostLog2)
        .setReplyCount(( nt) data.replyCountPostLog2)
        .setEmbeds mpress onCount(( nt) data.embeds mpress onCount)
        .setEmbedsUrlCount(( nt) data.embedsUrlCount)
        .setV deoV ewCount(( nt) data.v deoV ewCount)
        .setResultType(searchResultType)
        .setFromVer f edAccount(data. sFromVer f edAccount)
        .set sUserSpam(data. sUserSpam)
        .set sUserNSFW(data. sUserNSFW)
        .set sUserBot(data. sUserBot)
        .setHas mage(data.has mageUrl)
        .setHasV deo(data.hasV deoUrl)
        .setHasNews(data.hasNewsUrl)
        .setHasCard(data.hasCard)
        .setHasV s bleL nk(data.hasV s bleL nk)
        .setParusScore(data.parusScore)
        .setTextScore(data.textScore)
        .setUserRep(data.userRep)
        .setTokenAt140D v dedByNumTokensBucket(data.tokenAt140D v dedByNumTokensBucket);

     f (! tadata. sSetExtra tadata()) {
       tadata.setExtra tadata(new Thr ftSearchResultExtra tadata());
    }
    Thr ftSearchResultExtra tadata extra tadata =  tadata.getExtra tadata();

    // Promot on/Demot on features
    extra tadata.setUserLangScore(data.userLangMult)
        .setHasD fferentLang(data.hasD fferentLang)
        .setHasEngl shT etAndD fferentU Lang(data.hasEngl shT etAndD fferentU Lang)
        .setHasEngl shU AndD fferentT etLang(data.hasEngl shU AndD fferentT etLang)
        .setHasQuote(data.hasQuote)
        .setQuotedCount(( nt) data.quotedCount)
        .set  ghtedRet etCount(( nt) data.  ghtedRet etCount)
        .set  ghtedReplyCount(( nt) data.  ghtedReplyCount)
        .set  ghtedFavCount(( nt) data.  ghtedFavCount)
        .set  ghtedQuoteCount(( nt) data.  ghtedQuoteCount)
        .setQuerySpec f cScore(data.querySpec f cScore)
        .setAuthorSpec f cScore(data.authorSpec f cScore)
        .setRet etCountV2(( nt) data.ret etCountV2)
        .setFavCountV2(( nt) data.favCountV2)
        .setReplyCountV2(( nt) data.replyCountV2)
        .set sComposerS ceCa ra(data. sComposerS ceCa ra)
        .setFromBlueVer f edAccount(data. sFromBlueVer f edAccount);

    //  alth model scores features
    extra tadata
        .setTox c yScore(data.tox c yScore)
        .setPBlockScore(data.pBlockScore)
        .setPSpam T etScore(data.pSpam T etScore)
        .setPReportedT etScore(data.pReportedT etScore)
        .setSpam T etContentScore(data.spam T etContentScore)
        .setExper  ntal althModelScore1(data.exper  ntal althModelScore1)
        .setExper  ntal althModelScore2(data.exper  ntal althModelScore2)
        .setExper  ntal althModelScore3(data.exper  ntal althModelScore3)
        .setExper  ntal althModelScore4(data.exper  ntal althModelScore4);

    // Return all extra features for cl ents to consu .
     f (opt ons. sGetAllFeatures()) {
      extra tadata.set sSens  veContent(data. sSens  veContent)
          .setHasMult ple d aFlag(data.hasMult ple d aFlag)
          .setProf le sEggFlag(data.prof le sEggFlag)
          .set sUserNewFlag(data. sUserNewFlag)
          .setNum nt ons(data.num nt ons)
          .setNumHashtags(data.numHashtags)
          .setL nkLanguage(data.l nkLanguage)
          .setPrevUserT etEngage nt(data.prevUserT etEngage nt);
    }

    // Set features  n new Feature Access AP  format,  n t  future t  w ll be t  only part
    // needed  n t   thod,   don't need to set any ot r  tadata f elds any more.
     f (opt ons. sReturnSearchResultFeatures()) {
      //  f t  features are unset, and t y  re requested, t n   can retr eve t m.  f t y are
      // already set, t n   don't need to re-read t  docu nt features, and t  reader
      //  s probably pos  oned over t  wrong docu nt so   w ll return  ncorrect results.
       f (!extra tadata. sSetFeatures()) {
        //    gnore all features w h default values w n return ng t m  n t  response,
        // because   saves a lot of network bandw dth.
        Thr ftSearchResultFeatures features = createFeaturesForDocu nt(data, true).getFeatures();
        extra tadata.setFeatures(features);
      }

      // T  raw score may have changed s nce   created t  features, so   should update  .
      extra tadata.getFeatures().getDoubleValues()
          .put(ExternalT etFeature.RAW_EARLYB RD_SCORE.get d(), data.scoreF nal);
    }

     tadata
        .set sSelfT et(data. sSelfT et)
        .set sUserAnt Soc al(data. sUserAnt Soc al);
  }

  /**
   * Create earlyb rd bas c features and derv ed features for current docu nt.
   * @return a FeatureHandler object w re   can keep add ng extra feature values, or   can
   * call .getFeatures() on   to get a Thr ft object to return.
   */
  protected FeatureHandler createFeaturesForDocu nt(
      L nearScor ngData data, boolean  gnoreDefaultValues) throws  OExcept on {
    Thr ftSearchResultFeatures features = docu ntFeatures.getSearchResultFeatures(getSc ma());
     f (! gnoreDefaultValues) {
      setDefaultFeatureValues(features);
    }

    // add der ved features
    return new FeatureHandler(features,  gnoreDefaultValues)
        .addDouble(ExternalT etFeature.LUCENE_SCORE, data.luceneScore)
        .add nt(ExternalT etFeature.TWEET_AGE_ N_SECS, data.t etAge nSeconds)
        .addBoolean(ExternalT etFeature. S_SELF_TWEET, data. sSelfT et)
        .addBoolean(ExternalT etFeature. S_FOLLOW_RETWEET, data. sFollow && data. sRet et)
        .addBoolean(ExternalT etFeature. S_TRUSTED_RETWEET, data. sTrusted && data. sRet et)
        .addBoolean(ExternalT etFeature.AUTHOR_ S_FOLLOW, data. sFollow)
        .addBoolean(ExternalT etFeature.AUTHOR_ S_TRUSTED, data. sTrusted)
        .addBoolean(ExternalT etFeature.AUTHOR_ S_ANT SOC AL, data. sUserAnt Soc al)
        .addBoolean(ExternalT etFeature.HAS_D FF_LANG, data.hasD fferentLang)
        .addBoolean(ExternalT etFeature.HAS_ENGL SH_TWEET_D FF_U _LANG,
            data.hasEngl shT etAndD fferentU Lang)
        .addBoolean(ExternalT etFeature.HAS_ENGL SH_U _D FF_TWEET_LANG,
            data.hasEngl shU AndD fferentT etLang)
        .addDouble(ExternalT etFeature.SEARCHER_LANG_SCORE, data.userLangMult)
        .addDouble(ExternalT etFeature.QUERY_SPEC F C_SCORE, data.querySpec f cScore)
        .addDouble(ExternalT etFeature.AUTHOR_SPEC F C_SCORE, data.authorSpec f cScore);
  }

  /**
   * Adds default values for most nu r c features that do not have a value set yet  n t  g ven
   * Thr ftSearchResultFeatures  nstance.
   *
   * T   thod  s needed because so  models do not work properly w h m ss ng features.  nstead,
   * t y expect all features to be present even  f t y are unset (t  r values are 0).
   */
  protected vo d setDefaultFeatureValues(Thr ftSearchResultFeatures features) {
    for (Map.Entry< nteger, Thr ftSearchFeatureSc maEntry> entry
             : getSc ma().getSearchFeatureSc ma().getEntr es().entrySet()) {
       nt feature d = entry.getKey();
      Thr ftSearchFeatureSc maEntry sc maEntry = entry.getValue();
       f (shouldSetDefaultValueForFeature(sc maEntry.getFeatureType(), feature d)) {
        sw ch (sc maEntry.getFeatureType()) {
          case  NT32_VALUE:
            features.get ntValues().put fAbsent(feature d, 0);
            break;
          case LONG_VALUE:
            features.getLongValues().put fAbsent(feature d, 0L);
            break;
          case DOUBLE_VALUE:
            features.getDoubleValues().put fAbsent(feature d, 0.0);
            break;
          default:
            throw new  llegalArgu ntExcept on(
                "Should set default values only for  nteger, long or double features.  nstead, "
                + "found feature " + feature d + " of type " + sc maEntry.getFeatureType());
        }
      }
    }
  }

  protected vo d overr deFeatureValues(Thr ftSearchResultFeatures features,
                                       Thr ftSearchResultFeatures overr deFeatures) {
    LOG. nfo("Features before overr de {}", features);
     f (overr deFeatures. sSet ntValues()) {
      overr deFeatures.get ntValues().forEach(features::putTo ntValues);
    }
     f (overr deFeatures. sSetLongValues()) {
      overr deFeatures.getLongValues().forEach(features::putToLongValues);
    }
     f (overr deFeatures. sSetDoubleValues()) {
      overr deFeatures.getDoubleValues().forEach(features::putToDoubleValues);
    }
     f (overr deFeatures. sSetBoolValues()) {
      overr deFeatures.getBoolValues().forEach(features::putToBoolValues);
    }
     f (overr deFeatures. sSetStr ngValues()) {
      overr deFeatures.getStr ngValues().forEach(features::putToStr ngValues);
    }
     f (overr deFeatures. sSetBytesValues()) {
      overr deFeatures.getBytesValues().forEach(features::putToBytesValues);
    }
     f (overr deFeatures. sSetFeatureStoreD screteValues()) {
      overr deFeatures.getFeatureStoreD screteValues().forEach(
          features::putToFeatureStoreD screteValues);
    }
     f (overr deFeatures. sSetSparseB naryValues()) {
      overr deFeatures.getSparseB naryValues().forEach(features::putToSparseB naryValues);
    }
     f (overr deFeatures. sSetSparseCont nuousValues()) {
      overr deFeatures.getSparseCont nuousValues().forEach(features::putToSparseCont nuousValues);
    }
     f (overr deFeatures. sSetGeneralTensorValues()) {
      overr deFeatures.getGeneralTensorValues().forEach(features::putToGeneralTensorValues);
    }
     f (overr deFeatures. sSetStr ngTensorValues()) {
      overr deFeatures.getStr ngTensorValues().forEach(features::putToStr ngTensorValues);
    }
    LOG. nfo("Features after overr de {}", features);
  }

  /**
   * C ck  f a feature  s el g ble to have  s default value automat cally set w n absent.
   *   have a s m lar log c for bu ld ng data record.
   */
  pr vate stat c boolean shouldSetDefaultValueForFeature(
      Thr ftSearchFeatureType type,  nt feature d) {
    return ALLOWED_TYPES_FOR_DEFAULT_FEATURE_VALUES.conta ns(type)
        && !NUMER C_FEATURES_FOR_WH CH_DEFAULTS_SHOULD_NOT_BE_SET.conta ns(feature d)
        && (ExternalT etFeature.EARLYB RD_ NDEXED_FEATURE_ DS.conta ns(feature d)
            || ExternalT etFeature.EARLYB RD_DER VED_FEATURE_ DS.conta ns(feature d));
  }

  @Overr de
  publ c vo d updateRelevanceStats(Thr ftSearchResultsRelevanceStats relevanceStats) {
     f (relevanceStats == null) {
      return;
    }

    L nearScor ngData data = getScor ngDataForCurrentDocu nt();

     f (data.t etAge nSeconds > relevanceStats.getOldestScoredT etAge nSeconds()) {
      relevanceStats.setOldestScoredT etAge nSeconds(data.t etAge nSeconds);
    }
    relevanceStats.setNumScored(relevanceStats.getNumScored() + 1);
     f (data.scoreReturned == SK P_H T) {
      relevanceStats.setNumSk pped(relevanceStats.getNumSk pped() + 1);
      sw ch(data.sk pReason) {
        case ANT GAM NG:
          relevanceStats.setNumSk ppedForAnt Gam ng(
              relevanceStats.getNumSk ppedForAnt Gam ng() + 1);
          break;
        case LOW_REPUTAT ON:
          relevanceStats.setNumSk ppedForLowReputat on(
              relevanceStats.getNumSk ppedForLowReputat on() + 1);
          break;
        case LOW_TEXT_SCORE:
          relevanceStats.setNumSk ppedForLowTextScore(
              relevanceStats.getNumSk ppedForLowTextScore() + 1);
          break;
        case SOC AL_F LTER:
          relevanceStats.setNumSk ppedForSoc alF lter(
              relevanceStats.getNumSk ppedForSoc alF lter() + 1);
          break;
        case LOW_F NAL_SCORE:
          relevanceStats.setNumSk ppedForLowF nalScore(
              relevanceStats.getNumSk ppedForLowF nalScore() + 1);
          break;
        case LOW_RETWEET_COUNT:
          break;
        default:
          LOG.warn("Unknown Sk pReason: " + data.sk pReason);
      }
    }

     f (data. sFollow) {
      relevanceStats.setNumFromD rectFollows(relevanceStats.getNumFromD rectFollows() + 1);
    }
     f (data. sTrusted) {
      relevanceStats.setNumFromTrustedC rcle(relevanceStats.getNumFromTrustedC rcle() + 1);
    }
     f (data. sReply) {
      relevanceStats.setNumRepl es(relevanceStats.getNumRepl es() + 1);
       f (data. sTrusted) {
        relevanceStats.setNumRepl esTrusted(relevanceStats.getNumRepl esTrusted() + 1);
      } else  f (!data. sFollow) {
        relevanceStats.setNumRepl esOutOfNetwork(relevanceStats.getNumRepl esOutOfNetwork() + 1);
      }
    }
     f (data. sSelfT et) {
      relevanceStats.setNumSelfT ets(relevanceStats.getNumSelfT ets() + 1);
    }
     f (data.has mageUrl || data.hasV deoUrl) {
      relevanceStats.setNumW h d a(relevanceStats.getNumW h d a() + 1);
    }
     f (data.hasNewsUrl) {
      relevanceStats.setNumW hNews(relevanceStats.getNumW hNews() + 1);
    }
     f (data. sUserSpam) {
      relevanceStats.setNumSpamUser(relevanceStats.getNumSpamUser() + 1);
    }
     f (data. sUserNSFW) {
      relevanceStats.setNumOffens ve(relevanceStats.getNumOffens ve() + 1);
    }
     f (data. sUserBot) {
      relevanceStats.setNumBot(relevanceStats.getNumBot() + 1);
    }
  }

  @V s bleForTest ng
  stat c f nal class Explanat onWrapper {
    pr vate Explanat on explanat on;

    publ c Explanat on getExplanat on() {
      return explanat on;
    }

    @Overr de
    publ c Str ng toStr ng() {
      return explanat on.toStr ng();
    }
  }
}
