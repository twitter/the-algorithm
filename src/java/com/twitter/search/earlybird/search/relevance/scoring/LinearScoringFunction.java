package com.tw ter.search.earlyb rd.search.relevance.scor ng;

 mport java. o. OExcept on;
 mport java.ut l.L st;

 mport com.google.common.collect.L sts;

 mport org.apac .lucene.search.Explanat on;

 mport com.tw ter.search.common.relevance.features.MutableFeatureNormal zers;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserTable;
 mport com.tw ter.search.earlyb rd.search.Ant Gam ngF lter;
 mport com.tw ter.search.earlyb rd.search.relevance.L nearScor ngData;
 mport com.tw ter.search.earlyb rd.search.relevance.L nearScor ngParams;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultType;

/**
 * Scor ng funct on that uses t    ghts and boosts prov ded  n t  scor ng para ters from t 
 * request.
 */
publ c class L nearScor ngFunct on extends FeatureBasedScor ngFunct on {
  pr vate stat c f nal double BASE_SCORE = 0.0001;

  publ c L nearScor ngFunct on(
       mmutableSc ma nterface sc ma,
      Thr ftSearchQuery searchQuery,
      Ant Gam ngF lter ant Gam ngF lter,
      Thr ftSearchResultType searchResultType,
      UserTable userTable) throws  OExcept on {
    super("L nearScor ngFunct on", sc ma, searchQuery, ant Gam ngF lter, searchResultType,
        userTable);
  }

  @Overr de
  protected double computeScore(L nearScor ngData data, boolean forExplanat on) throws  OExcept on {
    double score = BASE_SCORE;

    data.luceneContr b = params.useLuceneScoreAsBoost
        ? 0.0 : params.lucene  ght * data.luceneScore;

    data.reputat onContr b = params.reputat on  ght * data.userRep;
    data.textScoreContr b = params.textScore  ght * data.textScore;
    data.parusContr b = params.parus  ght * data.parusScore;

    // contr but ons from engage nt counters. Note that   have "true" argu nt for all getters,
    // wh ch  ans all values w ll get scaled down for scor ng, t y  re unbounded  n raw form.
    data.ret etContr b = params.ret et  ght * data.ret etCountPostLog2;
    data.favContr b = params.fav  ght * data.favCountPostLog2;
    data.replyContr b = params.reply  ght * data.replyCountPostLog2;
    data.embeds mpress onContr b =
        params.embeds mpress on  ght * data.getEmbeds mpress onCount(true);
    data.embedsUrlContr b =
        params.embedsUrl  ght * data.getEmbedsUrlCount(true);
    data.v deoV ewContr b =
        params.v deoV ew  ght * data.getV deoV ewCount(true);
    data.quotedContr b =
        params.quotedCount  ght * data.quotedCount;

    for ( nt   = 0;   < L nearScor ngData.MAX_OFFL NE_EXPER MENTAL_F ELDS;  ++) {
      data.offl neExpFeatureContr but ons[ ] =
          params.rank ngOffl neExp  ghts[ ] * data.offl neExpFeatureValues[ ];
    }

    data.hasUrlContr b = params.url  ght * (data.hasUrl ? 1.0 : 0.0);
    data. sReplyContr b = params. sReply  ght * (data. sReply ? 1.0 : 0.0);
    data. sFollowRet etContr b =
        params.followRet et  ght * (data. sRet et && data. sFollow ? 1.0 : 0.0);
    data. sTrustedRet etContr b =
        params.trustedRet et  ght * (data. sRet et && data. sTrusted ? 1.0 : 0.0);
    double replyCountOr g nal = getUnscaledReplyCountFeatureValue();
    data.mult pleReplyContr b = params.mult pleReply  ght
        * (replyCountOr g nal < params.mult pleReplyM nVal ? 0.0 : replyCountOr g nal);

    //   d rectly t  query spec f c score as t  contr but on below as   doesn't need a   ght
    // for contr but on computat on.
    score += data.luceneContr b
        + data.reputat onContr b
        + data.textScoreContr b
        + data.replyContr b
        + data.mult pleReplyContr b
        + data.ret etContr b
        + data.favContr b
        + data.parusContr b
        + data.embeds mpress onContr b
        + data.embedsUrlContr b
        + data.v deoV ewContr b
        + data.quotedContr b
        + data.hasUrlContr b
        + data. sReplyContr b
        + data. sFollowRet etContr b
        + data. sTrustedRet etContr b
        + data.querySpec f cScore
        + data.authorSpec f cScore;

    for ( nt   = 0;   < L nearScor ngData.MAX_OFFL NE_EXPER MENTAL_F ELDS;  ++) {
      score += data.offl neExpFeatureContr but ons[ ];
    }

    return score;
  }

  /**
   * Generates t  explanat on for t  l near score.
   */
  @Overr de
  protected vo d generateExplanat onForScor ng(
      L nearScor ngData scor ngData, boolean  sH , L st<Explanat on> deta ls) throws  OExcept on {
    // 1. L near components
    f nal L st<Explanat on> l nearDeta ls = L sts.newArrayL st();
    addL nearEle ntExplanat on(
        l nearDeta ls, "[LuceneQueryScore]",
        params.lucene  ght, scor ngData.luceneScore, scor ngData.luceneContr b);
     f (scor ngData.hasCard) {
       f (scor ngData.cardAuthorMatchBoostAppl ed) {
        l nearDeta ls.add(Explanat on.match(
            (float) params.cardAuthorMatchBoosts[scor ngData.cardType],
            "[x] card author match boost"));
      }
       f (scor ngData.cardDescr pt onMatchBoostAppl ed) {
        l nearDeta ls.add(Explanat on.match(
            (float) params.cardDescr pt onMatchBoosts[scor ngData.cardType],
            "[x] card descr pt on match boost"));
      }
       f (scor ngData.cardDoma nMatchBoostAppl ed) {
        l nearDeta ls.add(Explanat on.match(
            (float) params.cardDoma nMatchBoosts[scor ngData.cardType],
            "[x] card doma n match boost"));
      }
       f (scor ngData.cardT leMatchBoostAppl ed) {
        l nearDeta ls.add(Explanat on.match(
            (float) params.cardT leMatchBoosts[scor ngData.cardType],
            "[x] card t le match boost"));
      }
    }
    addL nearEle ntExplanat on(
        l nearDeta ls, "reputat on",
        params.reputat on  ght, scor ngData.userRep, scor ngData.reputat onContr b);
    addL nearEle ntExplanat on(
        l nearDeta ls, "text score",
        params.textScore  ght, scor ngData.textScore, scor ngData.textScoreContr b);
    addL nearEle ntExplanat on(
        l nearDeta ls, "reply count (log2)",
        params.reply  ght, scor ngData.replyCountPostLog2, scor ngData.replyContr b);
    addL nearEle ntExplanat on(
        l nearDeta ls, "mult  reply",
        params.mult pleReply  ght,
        getUnscaledReplyCountFeatureValue() > params.mult pleReplyM nVal ? 1 : 0,
        scor ngData.mult pleReplyContr b);
    addL nearEle ntExplanat on(
        l nearDeta ls, "ret et count (log2)",
        params.ret et  ght, scor ngData.ret etCountPostLog2, scor ngData.ret etContr b);
    addL nearEle ntExplanat on(
        l nearDeta ls, "fav count (log2)",
        params.fav  ght, scor ngData.favCountPostLog2, scor ngData.favContr b);
    addL nearEle ntExplanat on(
        l nearDeta ls, "parus score",
        params.parus  ght, scor ngData.parusScore, scor ngData.parusContr b);
    for ( nt   = 0;   < L nearScor ngData.MAX_OFFL NE_EXPER MENTAL_F ELDS;  ++) {
       f (params.rank ngOffl neExp  ghts[ ] != L nearScor ngParams.DEFAULT_FEATURE_WE GHT) {
        addL nearEle ntExplanat on(l nearDeta ls,
            "rank ng exp score offl ne exper  ntal #" +  ,
            params.rank ngOffl neExp  ghts[ ], scor ngData.offl neExpFeatureValues[ ],
            scor ngData.offl neExpFeatureContr but ons[ ]);
      }
    }
    addL nearEle ntExplanat on(l nearDeta ls,
        "embedded t et  mpress on count",
        params.embeds mpress on  ght, scor ngData.getEmbeds mpress onCount(false),
        scor ngData.embeds mpress onContr b);
    addL nearEle ntExplanat on(l nearDeta ls,
        "embedded t et url count",
        params.embedsUrl  ght, scor ngData.getEmbedsUrlCount(false),
        scor ngData.embedsUrlContr b);
    addL nearEle ntExplanat on(l nearDeta ls,
        "v deo v ew count",
        params.v deoV ew  ght, scor ngData.getV deoV ewCount(false),
        scor ngData.v deoV ewContr b);
    addL nearEle ntExplanat on(l nearDeta ls,
        "quoted count",
        params.quotedCount  ght, scor ngData.quotedCount, scor ngData.quotedContr b);

    addL nearEle ntExplanat on(
        l nearDeta ls, "has url", params.url  ght, scor ngData.hasUrl ? 1.0 : 0.0,
        scor ngData.hasUrlContr b);

    addL nearEle ntExplanat on(
        l nearDeta ls, " s reply", params. sReply  ght,
        scor ngData. sReply ? 1.0 : 0.0, scor ngData. sReplyContr b);
    addL nearEle ntExplanat on(
        l nearDeta ls, " s follow ret et", params.followRet et  ght,
        scor ngData. sRet et && scor ngData. sFollow ? 1.0 : 0.0,
        scor ngData. sFollowRet etContr b);
    addL nearEle ntExplanat on(
        l nearDeta ls, " s trusted ret et", params.trustedRet et  ght,
        scor ngData. sRet et && scor ngData. sTrusted ? 1.0 : 0.0,
        scor ngData. sTrustedRet etContr b);

     f (scor ngData.querySpec f cScore != 0.0) {
      l nearDeta ls.add(Explanat on.match((float) scor ngData.querySpec f cScore,
          "[+] query spec f c score adjust nt"));
    }
     f (scor ngData.authorSpec f cScore != 0.0) {
      l nearDeta ls.add(Explanat on.match((float) scor ngData.authorSpec f cScore,
          "[+] author spec f c score adjust nt"));
    }


    Explanat on l nearCombo =  sH 
        ? Explanat on.match((float) scor ngData.scoreBeforeBoost,
          "(MATCH) L near components, sum of:", l nearDeta ls)
        : Explanat on.noMatch("L near components, sum of:", l nearDeta ls);


    deta ls.add(l nearCombo);
  }

  pr vate vo d addL nearEle ntExplanat on(L st<Explanat on> explanat on,
                                           Str ng na ,
                                           double   ght,
                                           double componentValue,
                                           double contr b) {
     f (contr b == 0.0) {
      return;
    }
    explanat on.add(
        Explanat on.match((float) contr b,
            Str ng.format("[+] %s=%.3f   ght=%.3f", na , componentValue,   ght)));
  }

  pr vate double getUnscaledReplyCountFeatureValue() throws  OExcept on {
    byte featureValue = (byte) docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.REPLY_COUNT);
    return MutableFeatureNormal zers.BYTE_NORMAL ZER.unnormLo rBound(featureValue);
  }
}
