package com.tw ter.search.earlyb rd.search.relevance;

 mport java.ut l.Arrays;
 mport java.ut l.L st;

 mport com.google.common.collect.L sts;

 mport com.tw ter.search.common.constants.SearchCardType;
 mport com.tw ter.search.common.constants.thr ftjava.Thr ftLanguage;

publ c class L nearScor ngData {
  publ c stat c f nal float NO_BOOST_VALUE = 1.0f;

  // A s gnal value so   can tell  f so th ng  s unset, also used  n explanat on.
  publ c stat c f nal  nt UNSET_S GNAL_VALUE = -999;

  //T   s so what arb rary, and  s  re so that   have so  l m  on
  //how many offl ne exper  ntal features   support per query
  publ c stat c f nal  nt MAX_OFFL NE_EXPER MENTAL_F ELDS = 5;

  publ c enum Sk pReason {
    NOT_SK PPED,
    ANT GAM NG,
    LOW_REPUTAT ON,
    LOW_TEXT_SCORE,
    LOW_RETWEET_COUNT,
    LOW_FAV_COUNT,
    SOC AL_F LTER,
    LOW_F NAL_SCORE
  }

  // W n   add f elds  re, make sure   also update t  clear() funct on.
  publ c double luceneScore;
  publ c double textScore;
  //  am not sure why t  has to be double...
  publ c double tokenAt140D v dedByNumTokensBucket;
  publ c double userRep;
  publ c double parusScore;
  publ c f nal double[] offl neExpFeatureValues = new double[MAX_OFFL NE_EXPER MENTAL_F ELDS];

  // v1 engage nt counters
  publ c double ret etCountPostLog2;
  publ c double favCountPostLog2;
  publ c double replyCountPostLog2;
  publ c double embeds mpress onCount;
  publ c double embedsUrlCount;
  publ c double v deoV ewCount;

  // v2 engage nt counters (that have a v1 counter part)
  publ c double ret etCountV2;
  publ c double favCountV2;
  publ c double replyCountV2;
  publ c double embeds mpress onCountV2;
  publ c double embedsUrlCountV2;
  publ c double v deoV ewCountV2;
  // pure v2 engage nt counters, t y started v2 only
  publ c double quotedCount;
  publ c double   ghtedRet etCount;
  publ c double   ghtedReplyCount;
  publ c double   ghtedFavCount;
  publ c double   ghtedQuoteCount;

  // card related propert es
  publ c boolean hasCard;
  publ c byte cardType;

  publ c boolean hasUrl;
  publ c boolean  sReply;
  publ c boolean  sRet et;
  publ c boolean  sOffens ve;
  publ c boolean hasTrend;
  publ c boolean  sFromVer f edAccount;
  publ c boolean  sFromBlueVer f edAccount;
  publ c boolean  sUserSpam;
  publ c boolean  sUserNSFW;
  publ c boolean  sUserBot;
  publ c boolean  sUserAnt Soc al;
  publ c boolean hasV s bleL nk;

  publ c double luceneContr b;
  publ c double reputat onContr b;
  publ c double textScoreContr b;
  publ c double favContr b;
  publ c double replyContr b;
  publ c double mult pleReplyContr b;
  publ c double ret etContr b;
  publ c double parusContr b;
  publ c f nal double[] offl neExpFeatureContr but ons =
      new double[MAX_OFFL NE_EXPER MENTAL_F ELDS];
  publ c double embeds mpress onContr b;
  publ c double embedsUrlContr b;
  publ c double v deoV ewContr b;
  publ c double quotedContr b;

  publ c double hasUrlContr b;
  publ c double  sReplyContr b;
  publ c double  sFollowRet etContr b;
  publ c double  sTrustedRet etContr b;

  // Value passed  n t  request (Thr ftRank ngParams.querySpec f cScoreAdjust nts)
  publ c double querySpec f cScore;

  // Value passed  n t  request (Thr ftRank ngParams.authorSpec f cScoreAdjust nts)
  publ c double authorSpec f cScore;

  publ c double normal zedLuceneScore;

  publ c  nt t etLang d;
  publ c double u LangMult;
  publ c double userLangMult;
  publ c boolean hasD fferentLang;
  publ c boolean hasEngl shT etAndD fferentU Lang;
  publ c boolean hasEngl shU AndD fferentT etLang;

  publ c  nt t etAge nSeconds;
  publ c double ageDecayMult;

  //  nter d ate scores
  publ c double scoreBeforeBoost;
  publ c double scoreAfterBoost;
  publ c double scoreF nal;
  publ c double scoreReturned;

  publ c Sk pReason sk pReason;

  publ c boolean  sTrusted;
  publ c boolean  sFollow;
  publ c boolean spamUserDampAppl ed;
  publ c boolean nsfwUserDampAppl ed;
  publ c boolean botUserDampAppl ed;
  publ c boolean trustedC rcleBoostAppl ed;
  publ c boolean d rectFollowBoostAppl ed;
  publ c boolean outOfNetworkReplyPenaltyAppl ed;
  publ c boolean hasMult pleHashtagsOrTrends;

  publ c boolean t etHasTrendsBoostAppl ed;
  publ c boolean t etFromVer f edAccountBoostAppl ed;
  publ c boolean t etFromBlueVer f edAccountBoostAppl ed;
  publ c boolean hasCardBoostAppl ed;
  publ c boolean cardDoma nMatchBoostAppl ed;
  publ c boolean cardAuthorMatchBoostAppl ed;
  publ c boolean cardT leMatchBoostAppl ed;
  publ c boolean cardDescr pt onMatchBoostAppl ed;

  publ c L st<Str ng> h F elds;
  publ c boolean hasNoTextH Demot onAppl ed;
  publ c boolean hasUrlOnlyH Demot onAppl ed;
  publ c boolean hasNa OnlyH Demot onAppl ed;
  publ c boolean hasSeparateTextAndNa H Demot onAppl ed;
  publ c boolean hasSeparateTextAndUrlH Demot onAppl ed;

  publ c long fromUser d;
  // T   s actually ret et status  D, or t   D of t  or g nal t et be ng (nat vely) ret eted
  publ c long sharedStatus d;
  publ c long referenceAuthor d; // SEARCH-8564

  publ c boolean  sSelfT et;
  publ c boolean selfT etBoostAppl ed;
  publ c double selfT etMult;

  publ c boolean has mageUrl;
  publ c boolean hasV deoUrl;
  publ c boolean has d alUrlBoostAppl ed;
  publ c boolean hasNewsUrl;
  publ c boolean hasNewsUrlBoostAppl ed;

  publ c boolean hasConsu rV deo;
  publ c boolean hasProV deo;
  publ c boolean hasV ne;
  publ c boolean hasPer scope;
  publ c boolean hasNat ve mage;
  publ c boolean  sNullcast;
  publ c boolean hasQuote;

  publ c boolean  sSens  veContent;
  publ c boolean hasMult ple d aFlag;
  publ c boolean prof le sEggFlag;
  publ c boolean  sUserNewFlag;

  publ c  nt num nt ons;
  publ c  nt numHashtags;
  publ c  nt l nkLanguage;
  publ c  nt prevUserT etEngage nt;

  publ c boolean  sComposerS ceCa ra;

  //  alth model scores by HML
  publ c double tox c yScore; // go/tox c y
  publ c double pBlockScore; // go/pblock
  publ c double pSpam T etScore; // go/pspam t et
  publ c double pReportedT etScore; // go/preportedt et
  publ c double spam T etContentScore; // go/spam -t et-content
  publ c double exper  ntal althModelScore1;
  publ c double exper  ntal althModelScore2;
  publ c double exper  ntal althModelScore3;
  publ c double exper  ntal althModelScore4;

  publ c L nearScor ngData() {
    h F elds = L sts.newArrayL st();
    clear();
  }

  // t  follow ng three counters  re added later and t y got denormal zed  n standard way,
  //   can choose to apply scald ng (for legacy L nearScor ngFunct on) or
  // not apply (for return ng  n  tadata and d splay  n debug).
  publ c double getEmbeds mpress onCount(boolean scaleForScor ng) {
    return scaleForScor ng ? logW h0(embeds mpress onCount) : embeds mpress onCount;
  }
  publ c double getEmbedsUrlCount(boolean scaleForScor ng) {
    return scaleForScor ng ? logW h0(embedsUrlCount) : embedsUrlCount;
  }
  publ c double getV deoV ewCount(boolean scaleForScor ng) {
    return scaleForScor ng ? logW h0(v deoV ewCount) : v deoV ewCount;
  }
  pr vate stat c double logW h0(double value) {
    return value > 0 ? Math.log(value) : 0.0;
  }

  /**
   * Returns a str ng descr pt on of all data stored  n t   nstance.
   */
  publ c Str ng getPropertyExplanat on() {
    Str ngBu lder sb = new Str ngBu lder();
    sb.append(hasCard ? "CARD " + SearchCardType.cardTypeFromByteValue(cardType) : "");
    sb.append(hasUrl ? "URL " : "");
    sb.append( sReply ? "REPLY " : "");
    sb.append( sRet et ? "RETWEET " : "");
    sb.append( sOffens ve ? "OFFENS VE " : "");
    sb.append(hasTrend ? "TREND " : "");
    sb.append(hasMult pleHashtagsOrTrends ? "HASHTAG/TREND+ " : "");
    sb.append( sFromVer f edAccount ? "VER F ED " : "");
    sb.append( sFromBlueVer f edAccount ? "BLUE_VER F ED " : "");
    sb.append( sUserSpam ? "SPAM " : "");
    sb.append( sUserNSFW ? "NSFW " : "");
    sb.append( sUserBot ? "BOT " : "");
    sb.append( sUserAnt Soc al ? "ANT SOC AL " : "");
    sb.append( sTrusted ? "TRUSTED " : "");
    sb.append( sFollow ? "FOLLOW " : "");
    sb.append( sSelfT et ? "SELF " : "");
    sb.append(has mageUrl ? " MAGE " : "");
    sb.append(hasV deoUrl ? "V DEO " : "");
    sb.append(hasNewsUrl ? "NEWS " : "");
    sb.append( sNullcast ? "NULLCAST" : "");
    sb.append(hasQuote ? "QUOTE" : "");
    sb.append( sComposerS ceCa ra ? "Composer S ce: CAMERA" : "");
    sb.append(favCountPostLog2 > 0 ? "Faves:" + favCountPostLog2 + " " : "");
    sb.append(ret etCountPostLog2 > 0 ? "Ret ets:" + ret etCountPostLog2 + " " : "");
    sb.append(replyCountPostLog2 > 0 ? "Repl es:" + replyCountPostLog2 + " " : "");
    sb.append(getEmbeds mpress onCount(false) > 0
        ? "Embedded  mps:" + getEmbeds mpress onCount(false) + " " : "");
    sb.append(getEmbedsUrlCount(false) > 0
        ? "Embedded Urls:" + getEmbedsUrlCount(false) + " " : "");
    sb.append(getV deoV ewCount(false) > 0
        ? "V deo v ews:" + getV deoV ewCount(false) + " " : "");
    sb.append(  ghtedRet etCount > 0 ? "  ghted Ret ets:"
        + (( nt)   ghtedRet etCount) + " " : "");
    sb.append(  ghtedReplyCount > 0
        ? "  ghted Repl es:" + (( nt)   ghtedReplyCount) + " " : "");
    sb.append(  ghtedFavCount > 0
        ? "  ghted Faves:" + (( nt)   ghtedFavCount) + " " : "");
    sb.append(  ghtedQuoteCount > 0
        ? "  ghted Quotes:" + (( nt)   ghtedQuoteCount) + " " : "");
    return sb.toStr ng();
  }

  /**
   * Resets all data stored  n t   nstance.
   */
  publ c vo d clear() {
    luceneScore = UNSET_S GNAL_VALUE;
    textScore = UNSET_S GNAL_VALUE;
    tokenAt140D v dedByNumTokensBucket = UNSET_S GNAL_VALUE;
    userRep = UNSET_S GNAL_VALUE;
    ret etCountPostLog2 = UNSET_S GNAL_VALUE;
    favCountPostLog2 = UNSET_S GNAL_VALUE;
    replyCountPostLog2 = UNSET_S GNAL_VALUE;
    parusScore = UNSET_S GNAL_VALUE;
    Arrays.f ll(offl neExpFeatureValues, 0);
    embeds mpress onCount = UNSET_S GNAL_VALUE;
    embedsUrlCount = UNSET_S GNAL_VALUE;
    v deoV ewCount = UNSET_S GNAL_VALUE;
    // v2 engage nt, t se each have a v1 counterpart
    ret etCountV2 = UNSET_S GNAL_VALUE;
    favCountV2 = UNSET_S GNAL_VALUE;
    replyCountV2 = UNSET_S GNAL_VALUE;
    embeds mpress onCountV2 = UNSET_S GNAL_VALUE;
    embedsUrlCountV2 = UNSET_S GNAL_VALUE;
    v deoV ewCountV2 = UNSET_S GNAL_VALUE;
    // new engage nt counters, t y only have one vers on w h t  v2 normal zer
    quotedCount = UNSET_S GNAL_VALUE;
      ghtedRet etCount = UNSET_S GNAL_VALUE;
      ghtedReplyCount = UNSET_S GNAL_VALUE;
      ghtedFavCount = UNSET_S GNAL_VALUE;
      ghtedQuoteCount = UNSET_S GNAL_VALUE;

    hasUrl = false;
     sReply = false;
     sRet et = false;
     sOffens ve = false;
    hasTrend = false;
     sFromVer f edAccount = false;
     sFromBlueVer f edAccount = false;
     sUserSpam = false;
     sUserNSFW = false;
     sUserBot = false;
     sUserAnt Soc al = false;
    hasV s bleL nk = false;
     sNullcast = false;

    luceneContr b = UNSET_S GNAL_VALUE;
    reputat onContr b = UNSET_S GNAL_VALUE;
    textScoreContr b = UNSET_S GNAL_VALUE;
    replyContr b = UNSET_S GNAL_VALUE;
    mult pleReplyContr b = UNSET_S GNAL_VALUE;
    ret etContr b = UNSET_S GNAL_VALUE;
    favContr b = UNSET_S GNAL_VALUE;
    parusContr b = UNSET_S GNAL_VALUE;
    Arrays.f ll(offl neExpFeatureContr but ons, 0);
    embeds mpress onContr b = UNSET_S GNAL_VALUE;
    embedsUrlContr b = UNSET_S GNAL_VALUE;
    v deoV ewContr b = UNSET_S GNAL_VALUE;
    hasUrlContr b = UNSET_S GNAL_VALUE;
     sReplyContr b = UNSET_S GNAL_VALUE;

    querySpec f cScore = UNSET_S GNAL_VALUE;
    authorSpec f cScore = UNSET_S GNAL_VALUE;

    normal zedLuceneScore = NO_BOOST_VALUE;

    t etLang d = Thr ftLanguage.UNKNOWN.getValue();
    u LangMult = NO_BOOST_VALUE;
    userLangMult = NO_BOOST_VALUE;
    hasD fferentLang = false;
    hasEngl shT etAndD fferentU Lang = false;
    hasEngl shU AndD fferentT etLang = false;

    t etAge nSeconds = 0;
    ageDecayMult = NO_BOOST_VALUE;

    //  nter d ate scores
    scoreBeforeBoost = UNSET_S GNAL_VALUE;
    scoreAfterBoost = UNSET_S GNAL_VALUE;
    scoreF nal = UNSET_S GNAL_VALUE;
    scoreReturned = UNSET_S GNAL_VALUE;

    sk pReason = Sk pReason.NOT_SK PPED;

     sTrusted = false;  // Set later
     sFollow = false; // Set later
    trustedC rcleBoostAppl ed = false;
    d rectFollowBoostAppl ed = false;
    outOfNetworkReplyPenaltyAppl ed = false;
    hasMult pleHashtagsOrTrends = false;
    spamUserDampAppl ed = false;
    nsfwUserDampAppl ed = false;
    botUserDampAppl ed = false;

    t etHasTrendsBoostAppl ed = false;
    t etFromVer f edAccountBoostAppl ed = false;
    t etFromBlueVer f edAccountBoostAppl ed = false;

    fromUser d = UNSET_S GNAL_VALUE;
    sharedStatus d = UNSET_S GNAL_VALUE;
    referenceAuthor d = UNSET_S GNAL_VALUE;

     sSelfT et = false;
    selfT etBoostAppl ed = false;
    selfT etMult = NO_BOOST_VALUE;

    trustedC rcleBoostAppl ed = false;
    d rectFollowBoostAppl ed = false;

    has mageUrl = false;
    hasV deoUrl = false;
    has d alUrlBoostAppl ed = false;
    hasNewsUrl = false;
    hasNewsUrlBoostAppl ed = false;

    hasCard = false;
    cardType = SearchCardType.UNKNOWN.getByteValue();
    hasCardBoostAppl ed = false;
    cardDoma nMatchBoostAppl ed = false;
    cardAuthorMatchBoostAppl ed = false;
    cardT leMatchBoostAppl ed = false;
    cardDescr pt onMatchBoostAppl ed = false;

    h F elds.clear();
    hasNoTextH Demot onAppl ed = false;
    hasUrlOnlyH Demot onAppl ed = false;
    hasNa OnlyH Demot onAppl ed = false;
    hasSeparateTextAndNa H Demot onAppl ed = false;
    hasSeparateTextAndUrlH Demot onAppl ed = false;

    hasConsu rV deo = false;
    hasProV deo = false;
    hasV ne = false;
    hasPer scope = false;
    hasNat ve mage = false;

     sSens  veContent = false;
    hasMult ple d aFlag = false;
    prof le sEggFlag = false;
    num nt ons = 0;
    numHashtags = 0;
     sUserNewFlag = false;
    l nkLanguage = 0;
    prevUserT etEngage nt = 0;

     sComposerS ceCa ra = false;

    //  alth model scores by HML
    tox c yScore = UNSET_S GNAL_VALUE;
    pBlockScore = UNSET_S GNAL_VALUE;
    pSpam T etScore = UNSET_S GNAL_VALUE;
    pReportedT etScore = UNSET_S GNAL_VALUE;
    spam T etContentScore = UNSET_S GNAL_VALUE;
    exper  ntal althModelScore1 = UNSET_S GNAL_VALUE;
    exper  ntal althModelScore2 = UNSET_S GNAL_VALUE;
    exper  ntal althModelScore3 = UNSET_S GNAL_VALUE;
    exper  ntal althModelScore4 = UNSET_S GNAL_VALUE;
  }
}
