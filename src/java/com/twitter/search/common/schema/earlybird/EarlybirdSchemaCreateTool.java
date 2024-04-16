package com.tw ter.search.common.sc ma.earlyb rd;

 mport java.ut l.Map;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.Maps;

 mport com.tw ter.common.text.ut l.TokenStreamSer al zer;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.sc ma.AnalyzerFactory;
 mport com.tw ter.search.common.sc ma.Dynam cSc ma;
 mport com.tw ter.search.common.sc ma. mmutableSc ma;
 mport com.tw ter.search.common.sc ma.Sc maBu lder;
 mport com.tw ter.search.common.sc ma.base.FeatureConf gurat on;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftCSFType;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftFeatureUpdateConstra nt;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftSc ma;

 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.BL NK_FAVOR TE_COUNT;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.BL NK_QUOTE_COUNT;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.BL NK_REPLY_COUNT;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.BL NK_RETWEET_COUNT;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.COMPOSER_SOURCE_ S_CAMERA_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.DECAYED_FAVOR TE_COUNT;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.DECAYED_QUOTE_COUNT;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.DECAYED_REPLY_COUNT;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.DECAYED_RETWEET_COUNT;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.EMBEDS_ MPRESS ON_COUNT;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.EMBEDS_ MPRESS ON_COUNT_V2;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.EMBEDS_URL_COUNT;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.EMBEDS_URL_COUNT_V2;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.EXPER MENTAL_HEALTH_MODEL_SCORE_1;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.EXPER MENTAL_HEALTH_MODEL_SCORE_2;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.EXPER MENTAL_HEALTH_MODEL_SCORE_3;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.EXPER MENTAL_HEALTH_MODEL_SCORE_4;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.EXTENDED_FEATURE_UNUSED_B TS_0_24_8;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.EXTENDED_TEST_FEATURE_UNUSED_B TS_12_30_2;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.EXTENDED_TEST_FEATURE_UNUSED_B TS_13_30_2;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.EXTENDED_TEST_FEATURE_UNUSED_B TS_14_10_22;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.EXTENDED_TEST_FEATURE_UNUSED_B TS_16;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.EXTENDED_TEST_FEATURE_UNUSED_B TS_17;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.EXTENDED_TEST_FEATURE_UNUSED_B TS_18;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.EXTENDED_TEST_FEATURE_UNUSED_B TS_19;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.EXTENDED_TEST_FEATURE_UNUSED_B TS_20;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.EXTENDED_TEST_FEATURE_UNUSED_B TS_4_31_1;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.EXTENDED_TEST_FEATURE_UNUSED_B TS_7_6_26;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.FAKE_FAVOR TE_COUNT;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.FAKE_QUOTE_COUNT;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.FAKE_REPLY_COUNT;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.FAKE_RETWEET_COUNT;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.FAVOR TE_COUNT;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.FAVOR TE_COUNT_V2;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.FROM_BLUE_VER F ED_ACCOUNT_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.FROM_VER F ED_ACCOUNT_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_CARD_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_CONSUMER_V DEO_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_EXPANDO_CARD_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_ MAGE_URL_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_L NK_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_MULT PLE_HASHTAGS_OR_TRENDS_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_MULT PLE_MED A_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_NAT VE_ MAGE_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_NEWS_URL_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_PER SCOPE_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_PRO_V DEO_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_QUOTE_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_TREND_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_V DEO_URL_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_V NE_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.HAS_V S BLE_L NK_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant. S_NULLCAST_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant. S_OFFENS VE_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant. S_REPLY_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant. S_RETWEET_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant. S_SENS T VE_CONTENT;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant. S_TREND NG_NOW_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant. S_USER_BOT_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant. S_USER_NEW_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant. S_USER_NSFW_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant. S_USER_SPAM_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.LABEL_ABUS VE_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.LABEL_ABUS VE_H _RCL_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.LABEL_DUP_CONTENT_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.LABEL_NSFW_H _PRC_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.LABEL_NSFW_H _RCL_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.LABEL_SPAM_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.LABEL_SPAM_H _RCL_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.LANGUAGE;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.LAST_FAVOR TE_S NCE_CREAT ON_HRS;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.LAST_QUOTE_S NCE_CREAT ON_HRS;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.LAST_REPLY_S NCE_CREAT ON_HRS;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.LAST_RETWEET_S NCE_CREAT ON_HRS;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.L NK_LANGUAGE;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.NORMAL ZED_FAVOR TE_COUNT_GREATER_THAN_OR_EQUAL_TO_F ELD;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.NORMAL ZED_REPLY_COUNT_GREATER_THAN_OR_EQUAL_TO_F ELD;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.NORMAL ZED_RETWEET_COUNT_GREATER_THAN_OR_EQUAL_TO_F ELD;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.NUM_HASHTAGS;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.NUM_HASHTAGS_V2;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.NUM_MENT ONS;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.NUM_MENT ONS_V2;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.NUM_STOCKS;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.PARUS_SCORE;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.PBLOCK_SCORE;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.PER SCOPE_EX STS;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.PER SCOPE_HAS_BEEN_FEATURED;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.PER SCOPE_ S_CURRENTLY_FEATURED;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.PER SCOPE_ S_FROM_QUAL TY_SOURCE;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.PER SCOPE_ S_L VE;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.PREV_USER_TWEET_ENGAGEMENT;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.PROF LE_ S_EGG_FLAG;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.P_REPORTED_TWEET_SCORE;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.P_SPAMMY_TWEET_SCORE;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.QUOTE_COUNT;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.REFERENCE_AUTHOR_ D_LEAST_S GN F CANT_ NT;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.REFERENCE_AUTHOR_ D_MOST_S GN F CANT_ NT;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.REPLY_COUNT;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.REPLY_COUNT_V2;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.RETWEET_COUNT;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.RETWEET_COUNT_V2;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.SPAMMY_TWEET_CONTENT_SCORE;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.TEXT_SCORE;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.TOX C TY_SCORE;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.TWEET_S GNATURE;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.USER_REPUTAT ON;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.V DEO_V EW_COUNT;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.V DEO_V EW_COUNT_V2;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.V S BLE_TOKEN_RAT O;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.WE GHTED_FAVOR TE_COUNT;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.WE GHTED_QUOTE_COUNT;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.WE GHTED_REPLY_COUNT;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant.WE GHTED_RETWEET_COUNT;

/**
 * F eld conf gurat ons for Earlyb rd.
 */
publ c f nal class Earlyb rdSc maCreateTool {
  // How many t  s a sc ma  s bu lt
  pr vate stat c f nal SearchCounter SCHEMA_BU LD_COUNT =
      SearchCounter.export("sc ma_bu ld_count");

  // Number of  ntegers for t  column of ENCODED_TWEET_FEATURES_F ELD.
  @V s bleForTest ng
  publ c stat c f nal  nt NUMBER_OF_ NTEGERS_FOR_FEATURES = 5;

  // Number of  ntegers for t  column of EXTENDED_ENCODED_TWEET_FEATURES_F ELD.
  // extra 80 bytes
  //  n realt   cluster, assum ng 19 seg nts total, and 8388608 docs per seg nt
  // t  would amount to about 12.75GB of  mory needed
  //
  @V s bleForTest ng
  publ c stat c f nal  nt NUMBER_OF_ NTEGERS_FOR_EXTENDED_FEATURES = 20;

  @V s bleForTest ng
  publ c stat c f nal Map<Str ng, FeatureConf gurat on> FEATURE_CONF GURAT ON_MAP
      = Maps.newL nkedHashMap();

  publ c stat c f nal Str ng BASE_F ELD_NAME =
      Earlyb rdF eldConstant.ENCODED_TWEET_FEATURES_F ELD.getF eldNa ();

  pr vate stat c Str ng getBaseF eldNa (Str ng fullNa ) {
     nt  ndex = fullNa . ndexOf(Sc maBu lder.CSF_V EW_NAME_SEPARATOR);
    Precond  ons.c ckArgu nt( ndex > 0);
    return fullNa .substr ng(0,  ndex);
  }

  pr vate stat c Str ng getBaseF eldNa (Earlyb rdF eldConstant f eldConstant) {
    return getBaseF eldNa (f eldConstant.getF eldNa ());
  }

  pr vate stat c Str ng getFeatureNa  nF eld(Earlyb rdF eldConstant f eldConstant) {
     nt  ndex = f eldConstant.getF eldNa (). ndexOf(Sc maBu lder.CSF_V EW_NAME_SEPARATOR);
    Precond  ons.c ckArgu nt( ndex > 0);
    return f eldConstant.getF eldNa ().substr ng( ndex + 1);
  }

  // def n ng all features
  stat c {
    // Add  nd v dual t et encoded features as v ews on top of
    // Earlyb rdF eldConstant.ENCODED_TWEET_FEATURES_F ELD

    //  nt  nt ndex,  nt b StartPos,  nt b Length
    newEarlyb rdFeatureConf gurat on( S_RETWEET_FLAG, Thr ftCSFType.BOOLEAN, 0, 0, 1);
    newEarlyb rdFeatureConf gurat on( S_OFFENS VE_FLAG, Thr ftCSFType.BOOLEAN, 0, 1, 1);
    newEarlyb rdFeatureConf gurat on(HAS_L NK_FLAG, Thr ftCSFType.BOOLEAN, 0, 2, 1);
    newEarlyb rdFeatureConf gurat on(HAS_TREND_FLAG, Thr ftCSFType.BOOLEAN, 0, 3, 1);
    newEarlyb rdFeatureConf gurat on( S_REPLY_FLAG, Thr ftCSFType.BOOLEAN, 0, 4, 1);
    newEarlyb rdFeatureConf gurat on( S_SENS T VE_CONTENT, Thr ftCSFType.BOOLEAN, 0, 5, 1);
    newEarlyb rdFeatureConf gurat on(HAS_MULT PLE_HASHTAGS_OR_TRENDS_FLAG,
        Thr ftCSFType.BOOLEAN, 0, 6, 1);
    newEarlyb rdFeatureConf gurat on(FROM_VER F ED_ACCOUNT_FLAG, Thr ftCSFType.BOOLEAN, 0, 7, 1);
    newEarlyb rdFeatureConf gurat on(TEXT_SCORE, Thr ftCSFType. NT, 0, 8, 8);
    newEarlyb rdFeatureConf gurat on(LANGUAGE, Thr ftCSFType. NT, 0, 16, 8);
    newEarlyb rdFeatureConf gurat on(L NK_LANGUAGE, Thr ftCSFType. NT, 0, 24, 8);

    newEarlyb rdFeatureConf gurat on(HAS_ MAGE_URL_FLAG, Thr ftCSFType.BOOLEAN, 1, 0, 1);
    newEarlyb rdFeatureConf gurat on(HAS_V DEO_URL_FLAG, Thr ftCSFType.BOOLEAN, 1, 1, 1);
    newEarlyb rdFeatureConf gurat on(HAS_NEWS_URL_FLAG, Thr ftCSFType.BOOLEAN, 1, 2, 1);
    newEarlyb rdFeatureConf gurat on(HAS_EXPANDO_CARD_FLAG, Thr ftCSFType.BOOLEAN, 1, 3, 1);
    newEarlyb rdFeatureConf gurat on(HAS_MULT PLE_MED A_FLAG, Thr ftCSFType.BOOLEAN, 1, 4, 1);
    newEarlyb rdFeatureConf gurat on(PROF LE_ S_EGG_FLAG, Thr ftCSFType.BOOLEAN, 1, 5, 1);
    newEarlyb rdFeatureConf gurat on(NUM_MENT ONS, Thr ftCSFType. NT, 1, 6, 2);     // 0, 1, 2, 3+
    newEarlyb rdFeatureConf gurat on(NUM_HASHTAGS, Thr ftCSFType. NT, 1, 8, 2);     // 0, 1, 2, 3+
    newEarlyb rdFeatureConf gurat on(HAS_CARD_FLAG, Thr ftCSFType.BOOLEAN, 1, 10, 1);
    newEarlyb rdFeatureConf gurat on(HAS_V S BLE_L NK_FLAG, Thr ftCSFType.BOOLEAN, 1, 11, 1);
    newEarlyb rdFeatureConf gurat on(USER_REPUTAT ON, Thr ftCSFType. NT, 1, 12, 8);
    newEarlyb rdFeatureConf gurat on( S_USER_SPAM_FLAG, Thr ftCSFType.BOOLEAN, 1, 20, 1);
    newEarlyb rdFeatureConf gurat on( S_USER_NSFW_FLAG, Thr ftCSFType.BOOLEAN, 1, 21, 1);
    newEarlyb rdFeatureConf gurat on( S_USER_BOT_FLAG, Thr ftCSFType.BOOLEAN, 1, 22, 1);
    newEarlyb rdFeatureConf gurat on( S_USER_NEW_FLAG, Thr ftCSFType.BOOLEAN, 1, 23, 1);
    newEarlyb rdFeatureConf gurat on(PREV_USER_TWEET_ENGAGEMENT, Thr ftCSFType. NT, 1, 24, 6);
    newEarlyb rdFeatureConf gurat on(COMPOSER_SOURCE_ S_CAMERA_FLAG,
        Thr ftCSFType.BOOLEAN, 1, 30, 1);
    newEarlyb rdFeatureConf gurat on( S_NULLCAST_FLAG, Thr ftCSFType.BOOLEAN, 1, 31, 1);

    newEarlyb rdFeatureConf gurat on(RETWEET_COUNT, Thr ftCSFType.DOUBLE, 2, 0, 8,
        Thr ftFeatureUpdateConstra nt. NC_ONLY);
    newEarlyb rdFeatureConf gurat on(FAVOR TE_COUNT, Thr ftCSFType.DOUBLE, 2, 8, 8,
        Thr ftFeatureUpdateConstra nt. NC_ONLY);
    newEarlyb rdFeatureConf gurat on(REPLY_COUNT, Thr ftCSFType.DOUBLE, 2, 16, 8,
        Thr ftFeatureUpdateConstra nt. NC_ONLY);
    newEarlyb rdFeatureConf gurat on(PARUS_SCORE, Thr ftCSFType.DOUBLE, 2, 24, 8);

    newEarlyb rdFeatureConf gurat on(HAS_CONSUMER_V DEO_FLAG, Thr ftCSFType.BOOLEAN, 3, 0, 1);
    newEarlyb rdFeatureConf gurat on(HAS_PRO_V DEO_FLAG, Thr ftCSFType.BOOLEAN, 3, 1, 1);
    newEarlyb rdFeatureConf gurat on(HAS_V NE_FLAG, Thr ftCSFType.BOOLEAN, 3, 2, 1);
    newEarlyb rdFeatureConf gurat on(HAS_PER SCOPE_FLAG, Thr ftCSFType.BOOLEAN, 3, 3, 1);
    newEarlyb rdFeatureConf gurat on(HAS_NAT VE_ MAGE_FLAG, Thr ftCSFType.BOOLEAN, 3, 4, 1);
    // NOTE: T re are 3 b s left  n t  f rst byte of  NT 3,  f poss ble, please reserve t m
    // for future  d a types (SEARCH-9131)
    // newEarlyb rdFeatureConf gurat on(FUTURE_MED A_B TS, Thr ftCSFType. NT, 3, 5, 3);

    newEarlyb rdFeatureConf gurat on(V S BLE_TOKEN_RAT O, Thr ftCSFType. NT, 3, 8, 4);
    newEarlyb rdFeatureConf gurat on(HAS_QUOTE_FLAG, Thr ftCSFType.BOOLEAN, 3, 12, 1);
    newEarlyb rdFeatureConf gurat on(FROM_BLUE_VER F ED_ACCOUNT_FLAG,
        Thr ftCSFType.BOOLEAN, 3, 13, 1);
    // Unused b s from b  14 to b  31 (18 b s)
    // newEarlyb rdFeatureConf gurat on(UNUSED_B TS, Thr ftCSFType. NT, 3, 14, 18);

    newEarlyb rdFeatureConf gurat on(TWEET_S GNATURE, Thr ftCSFType. NT, 4, 0, 32);

    newEarlyb rdFeatureConf gurat on(EMBEDS_ MPRESS ON_COUNT,
        Thr ftCSFType.DOUBLE, 0, 0, 8, Thr ftFeatureUpdateConstra nt. NC_ONLY);
    newEarlyb rdFeatureConf gurat on(EMBEDS_URL_COUNT,
        Thr ftCSFType.DOUBLE, 0, 8, 8, Thr ftFeatureUpdateConstra nt. NC_ONLY);
    newEarlyb rdFeatureConf gurat on(V DEO_V EW_COUNT,
        Thr ftCSFType.DOUBLE, 0, 16, 8, Thr ftFeatureUpdateConstra nt. NC_ONLY);

    // Unused b s from b  24 to b  31 (8 b s).
    // T  used to be a feature that was decomm ss oned (SEARCHQUAL-10321)
    newEarlyb rdFeatureConf gurat on(EXTENDED_FEATURE_UNUSED_B TS_0_24_8,
        Thr ftCSFType. NT, 0, 24, 8);

    newEarlyb rdFeatureConf gurat on(REFERENCE_AUTHOR_ D_LEAST_S GN F CANT_ NT,
        Thr ftCSFType. NT, 1, 0, 32, Thr ftFeatureUpdateConstra nt. MMUTABLE);
    newEarlyb rdFeatureConf gurat on(REFERENCE_AUTHOR_ D_MOST_S GN F CANT_ NT,
        Thr ftCSFType. NT, 2, 0, 32, Thr ftFeatureUpdateConstra nt. MMUTABLE);

    newEarlyb rdFeatureConf gurat on(RETWEET_COUNT_V2,
        Thr ftCSFType.DOUBLE, 3, 0, 8, Thr ftFeatureUpdateConstra nt. NC_ONLY);
    newEarlyb rdFeatureConf gurat on(FAVOR TE_COUNT_V2,
        Thr ftCSFType.DOUBLE, 3, 8, 8, Thr ftFeatureUpdateConstra nt. NC_ONLY);
    newEarlyb rdFeatureConf gurat on(REPLY_COUNT_V2,
        Thr ftCSFType.DOUBLE, 3, 16, 8, Thr ftFeatureUpdateConstra nt. NC_ONLY);
    newEarlyb rdFeatureConf gurat on(EMBEDS_ MPRESS ON_COUNT_V2,
        Thr ftCSFType.DOUBLE, 3, 24, 8, Thr ftFeatureUpdateConstra nt. NC_ONLY);

    newEarlyb rdFeatureConf gurat on(EMBEDS_URL_COUNT_V2,
        Thr ftCSFType.DOUBLE, 4, 0, 8, Thr ftFeatureUpdateConstra nt. NC_ONLY);
    newEarlyb rdFeatureConf gurat on(V DEO_V EW_COUNT_V2,
        Thr ftCSFType.DOUBLE, 4, 8, 8, Thr ftFeatureUpdateConstra nt. NC_ONLY);
    newEarlyb rdFeatureConf gurat on(QUOTE_COUNT,
        Thr ftCSFType.DOUBLE, 4, 16, 8);

    newEarlyb rdFeatureConf gurat on(LABEL_ABUS VE_FLAG,        Thr ftCSFType.BOOLEAN, 4, 24, 1);
    newEarlyb rdFeatureConf gurat on(LABEL_ABUS VE_H _RCL_FLAG, Thr ftCSFType.BOOLEAN, 4, 25, 1);
    newEarlyb rdFeatureConf gurat on(LABEL_DUP_CONTENT_FLAG,    Thr ftCSFType.BOOLEAN, 4, 26, 1);
    newEarlyb rdFeatureConf gurat on(LABEL_NSFW_H _PRC_FLAG,    Thr ftCSFType.BOOLEAN, 4, 27, 1);
    newEarlyb rdFeatureConf gurat on(LABEL_NSFW_H _RCL_FLAG,    Thr ftCSFType.BOOLEAN, 4, 28, 1);
    newEarlyb rdFeatureConf gurat on(LABEL_SPAM_FLAG,           Thr ftCSFType.BOOLEAN, 4, 29, 1);
    newEarlyb rdFeatureConf gurat on(LABEL_SPAM_H _RCL_FLAG,    Thr ftCSFType.BOOLEAN, 4, 30, 1);

    newEarlyb rdFeatureConf gurat on(EXTENDED_TEST_FEATURE_UNUSED_B TS_4_31_1,
        Thr ftCSFType. NT, 4, 31, 1);

    newEarlyb rdFeatureConf gurat on(WE GHTED_RETWEET_COUNT,
        Thr ftCSFType.DOUBLE, 5, 0, 8, Thr ftFeatureUpdateConstra nt. NC_ONLY);
    newEarlyb rdFeatureConf gurat on(WE GHTED_REPLY_COUNT,
        Thr ftCSFType.DOUBLE, 5, 8, 8, Thr ftFeatureUpdateConstra nt. NC_ONLY);
    newEarlyb rdFeatureConf gurat on(WE GHTED_FAVOR TE_COUNT,
        Thr ftCSFType.DOUBLE, 5, 16, 8, Thr ftFeatureUpdateConstra nt. NC_ONLY);
    newEarlyb rdFeatureConf gurat on(WE GHTED_QUOTE_COUNT,
        Thr ftCSFType.DOUBLE, 5, 24, 8, Thr ftFeatureUpdateConstra nt. NC_ONLY);

    newEarlyb rdFeatureConf gurat on(PER SCOPE_EX STS,
        Thr ftCSFType.BOOLEAN, 6, 0, 1);
    newEarlyb rdFeatureConf gurat on(PER SCOPE_HAS_BEEN_FEATURED,
        Thr ftCSFType.BOOLEAN, 6, 1, 1);
    newEarlyb rdFeatureConf gurat on(PER SCOPE_ S_CURRENTLY_FEATURED,
        Thr ftCSFType.BOOLEAN, 6, 2, 1);
    newEarlyb rdFeatureConf gurat on(PER SCOPE_ S_FROM_QUAL TY_SOURCE,
        Thr ftCSFType.BOOLEAN, 6, 3, 1);
    newEarlyb rdFeatureConf gurat on(PER SCOPE_ S_L VE,
        Thr ftCSFType.BOOLEAN, 6, 4, 1);

    newEarlyb rdFeatureConf gurat on( S_TREND NG_NOW_FLAG,
        Thr ftCSFType.BOOLEAN, 6, 5, 1);

    // rema n ng b s for  nteger 6
    newEarlyb rdFeatureConf gurat on(EXTENDED_TEST_FEATURE_UNUSED_B TS_7_6_26,
        Thr ftCSFType. NT, 6, 6, 26);

    // T  decay ng counters can beco  smaller
    newEarlyb rdFeatureConf gurat on(DECAYED_RETWEET_COUNT,
        Thr ftCSFType.DOUBLE, 7, 0, 8, Thr ftFeatureUpdateConstra nt.POS T VE);
    newEarlyb rdFeatureConf gurat on(DECAYED_REPLY_COUNT,
        Thr ftCSFType.DOUBLE, 7, 8, 8, Thr ftFeatureUpdateConstra nt.POS T VE);
    newEarlyb rdFeatureConf gurat on(DECAYED_FAVOR TE_COUNT,
        Thr ftCSFType.DOUBLE, 7, 16, 8, Thr ftFeatureUpdateConstra nt.POS T VE);
    newEarlyb rdFeatureConf gurat on(DECAYED_QUOTE_COUNT,
        Thr ftCSFType.DOUBLE, 7, 24, 8, Thr ftFeatureUpdateConstra nt.POS T VE);

    // T  fake engage nt counters.
    newEarlyb rdFeatureConf gurat on(FAKE_RETWEET_COUNT,
        Thr ftCSFType.DOUBLE, 8, 0, 8, Thr ftFeatureUpdateConstra nt.POS T VE);
    newEarlyb rdFeatureConf gurat on(FAKE_REPLY_COUNT,
        Thr ftCSFType.DOUBLE, 8, 8, 8, Thr ftFeatureUpdateConstra nt.POS T VE);
    newEarlyb rdFeatureConf gurat on(FAKE_FAVOR TE_COUNT,
        Thr ftCSFType.DOUBLE, 8, 16, 8, Thr ftFeatureUpdateConstra nt.POS T VE);
    newEarlyb rdFeatureConf gurat on(FAKE_QUOTE_COUNT,
        Thr ftCSFType.DOUBLE, 8, 24, 8, Thr ftFeatureUpdateConstra nt.POS T VE);

    newEarlyb rdFeatureConf gurat on(LAST_RETWEET_S NCE_CREAT ON_HRS,
        Thr ftCSFType. NT, 9, 0, 8, Thr ftFeatureUpdateConstra nt. NC_ONLY);
    newEarlyb rdFeatureConf gurat on(LAST_REPLY_S NCE_CREAT ON_HRS,
        Thr ftCSFType. NT, 9, 8, 8, Thr ftFeatureUpdateConstra nt. NC_ONLY);
    newEarlyb rdFeatureConf gurat on(LAST_FAVOR TE_S NCE_CREAT ON_HRS,
        Thr ftCSFType. NT, 9, 16, 8, Thr ftFeatureUpdateConstra nt. NC_ONLY);
    newEarlyb rdFeatureConf gurat on(LAST_QUOTE_S NCE_CREAT ON_HRS,
        Thr ftCSFType. NT, 9, 24, 8, Thr ftFeatureUpdateConstra nt. NC_ONLY);

    newEarlyb rdFeatureConf gurat on(NUM_HASHTAGS_V2,
        Thr ftCSFType. NT, 10, 0, 4);
    newEarlyb rdFeatureConf gurat on(NUM_MENT ONS_V2,
        Thr ftCSFType. NT, 10, 4, 4);
    newEarlyb rdFeatureConf gurat on(NUM_STOCKS,
        Thr ftCSFType. NT, 10, 8, 4);

    // Rema n ng b s for  nteger 10
    // Product on Tox c y and PBlock score from HML (go/tox c y, go/pblock)
    newEarlyb rdFeatureConf gurat on(TOX C TY_SCORE,
        Thr ftCSFType.DOUBLE, 10, 12, 10);
    newEarlyb rdFeatureConf gurat on(PBLOCK_SCORE,
        Thr ftCSFType.DOUBLE, 10, 22, 10);

    // T  bl nk engage nt counters
    newEarlyb rdFeatureConf gurat on(BL NK_RETWEET_COUNT,
        Thr ftCSFType.DOUBLE, 11, 0, 8, Thr ftFeatureUpdateConstra nt.POS T VE);
    newEarlyb rdFeatureConf gurat on(BL NK_REPLY_COUNT,
        Thr ftCSFType.DOUBLE, 11, 8, 8, Thr ftFeatureUpdateConstra nt.POS T VE);
    newEarlyb rdFeatureConf gurat on(BL NK_FAVOR TE_COUNT,
        Thr ftCSFType.DOUBLE, 11, 16, 8, Thr ftFeatureUpdateConstra nt.POS T VE);
    newEarlyb rdFeatureConf gurat on(BL NK_QUOTE_COUNT,
        Thr ftCSFType.DOUBLE, 11, 24, 8, Thr ftFeatureUpdateConstra nt.POS T VE);

    // Exper  ntal  alth model scores from HML
    newEarlyb rdFeatureConf gurat on(EXPER MENTAL_HEALTH_MODEL_SCORE_1,
        Thr ftCSFType.DOUBLE, 12, 0, 10);
    newEarlyb rdFeatureConf gurat on(EXPER MENTAL_HEALTH_MODEL_SCORE_2,
        Thr ftCSFType.DOUBLE, 12, 10, 10);
    newEarlyb rdFeatureConf gurat on(EXPER MENTAL_HEALTH_MODEL_SCORE_3,
        Thr ftCSFType.DOUBLE, 12, 20, 10);
    // rema n ng b s for  nteger 12
    newEarlyb rdFeatureConf gurat on(EXTENDED_TEST_FEATURE_UNUSED_B TS_12_30_2,
        Thr ftCSFType. NT, 12, 30, 2);

    // Exper  ntal  alth model scores from HML (cont.)
    newEarlyb rdFeatureConf gurat on(EXPER MENTAL_HEALTH_MODEL_SCORE_4,
        Thr ftCSFType.DOUBLE, 13, 0, 10);
    // Product on pSpam T et score from HML (go/pspam t et)
    newEarlyb rdFeatureConf gurat on(P_SPAMMY_TWEET_SCORE,
        Thr ftCSFType.DOUBLE, 13, 10, 10);
    // Product on pReportedT et score from HML (go/preportedt et)
    newEarlyb rdFeatureConf gurat on(P_REPORTED_TWEET_SCORE,
        Thr ftCSFType.DOUBLE, 13, 20, 10);
    // rema n ng b s for  nteger 13
    newEarlyb rdFeatureConf gurat on(EXTENDED_TEST_FEATURE_UNUSED_B TS_13_30_2,
        Thr ftCSFType. NT, 13, 30, 2);

    // Exper  ntal  alth model scores from HML (cont.)
    // Prod Spam  T et Content model score from Platform Man pulat on (go/spam -t et-content)
    newEarlyb rdFeatureConf gurat on(SPAMMY_TWEET_CONTENT_SCORE,
        Thr ftCSFType.DOUBLE, 14, 0, 10);
    // rema n ng b s for  nteger 14
    newEarlyb rdFeatureConf gurat on(EXTENDED_TEST_FEATURE_UNUSED_B TS_14_10_22,
        Thr ftCSFType. NT, 14, 10, 22);

    // Note that t   nteger  ndex below  s 0-based, but t   ndex j  n UNUSED_B TS_{j} below
    //  s 1-based.
    newEarlyb rdFeatureConf gurat on(EXTENDED_TEST_FEATURE_UNUSED_B TS_16,
        Thr ftCSFType. NT, 15, 0, 32);
    newEarlyb rdFeatureConf gurat on(EXTENDED_TEST_FEATURE_UNUSED_B TS_17,
        Thr ftCSFType. NT, 16, 0, 32);
    newEarlyb rdFeatureConf gurat on(EXTENDED_TEST_FEATURE_UNUSED_B TS_18,
        Thr ftCSFType. NT, 17, 0, 32);
    newEarlyb rdFeatureConf gurat on(EXTENDED_TEST_FEATURE_UNUSED_B TS_19,
        Thr ftCSFType. NT, 18, 0, 32);
    newEarlyb rdFeatureConf gurat on(EXTENDED_TEST_FEATURE_UNUSED_B TS_20,
        Thr ftCSFType. NT, 19, 0, 32);
  }

  pr vate Earlyb rdSc maCreateTool() { }

  /**
   * Get sc ma for t  Earlyb rd.
   */
  publ c stat c Dynam cSc ma bu ldSc ma(Earlyb rdCluster cluster)
      throws Sc ma.Sc maVal dat onExcept on {
    SCHEMA_BU LD_COUNT. ncre nt();
    return new Dynam cSc ma(new  mmutableSc ma(bu ldThr ftSc ma(cluster),
                                                 new AnalyzerFactory(),
                                                 cluster.getNa ForStats()));
  }

  /**
   * Get sc ma for t  Earlyb rd, can throw runt   except on.  T   s mostly for stat c sc ma
   * usage, wh ch does not care about sc ma updates.
   */
  @V s bleForTest ng
  publ c stat c Dynam cSc ma bu ldSc maW hRunt  Except on(Earlyb rdCluster cluster) {
    try {
      return bu ldSc ma(cluster);
    } catch (Sc ma.Sc maVal dat onExcept on e) {
      throw new Runt  Except on(e);
    }
  }

  pr vate stat c FeatureConf gurat on newEarlyb rdFeatureConf gurat on(
      Earlyb rdF eldConstant f eldConstant,
      Thr ftCSFType type,
       nt  nt ndex,  nt b StartPos,  nt b Length,
      Thr ftFeatureUpdateConstra nt... constra nts) {

     f (!f eldConstant. sFlagFeatureF eld() && type == Thr ftCSFType.BOOLEAN) {
      throw new  llegalArgu ntExcept on(
          "Non-flag feature f eld conf gured w h boolean Thr ft type: " + f eldConstant);
    }
     f (f eldConstant. sFlagFeatureF eld() && type != Thr ftCSFType.BOOLEAN) {
      throw new  llegalArgu ntExcept on(
          "Flag feature f eld conf gured w h non-boolean Thr ft type: " + f eldConstant);
    }

    Str ng baseF eldNa  = getBaseF eldNa (f eldConstant);
    Str ng na  = getFeatureNa  nF eld(f eldConstant);
    FeatureConf gurat on.Bu lder bu lder = FeatureConf gurat on.bu lder()
        .w hNa (na )
        .w hType(type)
        .w hB Range( nt ndex, b StartPos, b Length);
    // remove t  follow ng l ne once   conf gure features purely by t  sc ma
    bu lder.w hBaseF eld(baseF eldNa );

     f (!f eldConstant. sUnusedF eld()) {
      bu lder.w hOutputType(type);
    }
     f (f eldConstant.getFeatureNormal zat onType() != null) {
      bu lder.w hFeatureNormal zat onType(f eldConstant.getFeatureNormal zat onType());
    }

    for (Thr ftFeatureUpdateConstra nt constra nt : constra nts) {
      bu lder.w hFeatureUpdateConstra nt(constra nt);
    }
    FeatureConf gurat on featureConf gurat on = bu lder.bu ld();
    FEATURE_CONF GURAT ON_MAP.put(f eldConstant.getF eldNa (), featureConf gurat on);
    return featureConf gurat on;
  }

  /**
   * Bu ld Thr ftSc ma for t  Earlyb rd. Note that t  sc ma returned can be used
   * all Earlyb rd clusters. Ho ver, so  clusters may not use all t  f eld conf gurat ons.
   */
  @V s bleForTest ng
  publ c stat c Thr ftSc ma bu ldThr ftSc ma(Earlyb rdCluster cluster) {
    Earlyb rdSc maBu lder bu lder = new Earlyb rdSc maBu lder(
        new Earlyb rdF eldConstants(), cluster, TokenStreamSer al zer.Vers on.VERS ON_2);

    bu lder.w hSc maVers on(
        FlushVers on.CURRENT_FLUSH_VERS ON.getVers onNumber(),
        FlushVers on.CURRENT_FLUSH_VERS ON.getM norVers on(),
        FlushVers on.CURRENT_FLUSH_VERS ON.getDescr pt on(),
        FlushVers on.CURRENT_FLUSH_VERS ON. sOff c al());

    //  D f eld, used for part  on ng
    bu lder.w hPart  onF eld d(0)
        .w hSortableLongTermF eld(Earlyb rdF eldConstant. D_F ELD.getF eldNa ())
        // Text F elds that are searc d by default
        .w hTextF eld(Earlyb rdF eldConstant.RESOLVED_L NKS_TEXT_F ELD.getF eldNa (), true)
        .w hSearchF eldByDefault(
            Earlyb rdF eldConstant.RESOLVED_L NKS_TEXT_F ELD.getF eldNa (), 0.1f)
        .w hPretoken zedTextF eld(Earlyb rdF eldConstant.TEXT_F ELD.getF eldNa (), true)
        .w hSearchF eldByDefault(Earlyb rdF eldConstant.TEXT_F ELD.getF eldNa (), 1.0f);
    bu lder.w hT etSpec f cNormal zat on(Earlyb rdF eldConstant.TEXT_F ELD.getF eldNa ())
        .w hTextF eld(Earlyb rdF eldConstant.TOKEN ZED_FROM_USER_F ELD.getF eldNa (), true)
        .w hSearchF eldByDefault(
            Earlyb rdF eldConstant.TOKEN ZED_FROM_USER_F ELD.getF eldNa (), 0.2f)

        // Text f elds not searc d by default
        .w hTextF eld(Earlyb rdF eldConstant.FROM_USER_F ELD.getF eldNa (), false)
        .w hTextF eld(Earlyb rdF eldConstant.TO_USER_F ELD.getF eldNa (), false)

        // cards are not searc d by default, and have   ght 0.
        .w hPretoken zedTextF eld(Earlyb rdF eldConstant.CARD_T TLE_F ELD.getF eldNa (), false)
        .w hPretoken zedTextF eld(
            Earlyb rdF eldConstant.CARD_DESCR PT ON_F ELD.getF eldNa (), false)
        .w hTextF eld(Earlyb rdF eldConstant.CARD_LANG.getF eldNa (), false)

        // Out-of-order append f elds
        .w hLongTermF eld(Earlyb rdF eldConstant.L KED_BY_USER_ D_F ELD.getF eldNa ())
        .w hLongTermF eld(Earlyb rdF eldConstant.RETWEETED_BY_USER_ D.getF eldNa ())
        .w hLongTermF eld(Earlyb rdF eldConstant.REPL ED_TO_BY_USER_ D.getF eldNa ())

        // No Pos  on f elds, sorted alphabet cally
        .w hPretoken zedNoPos  onF eld(Earlyb rdF eldConstant.CARD_DOMA N_F ELD.getF eldNa ())
        .w h ndexedNotToken zedF eld(Earlyb rdF eldConstant.CARD_NAME_F ELD.getF eldNa ())
        .w h ntTermF eld(Earlyb rdF eldConstant.CREATED_AT_F ELD.getF eldNa ())
        .w h ndexedNotToken zedF eld(Earlyb rdF eldConstant.ENT TY_ D_F ELD.getF eldNa ())
        .w h ndexedNotToken zedF eld(Earlyb rdF eldConstant.GEO_HASH_F ELD.getF eldNa ())
        .w hLongTermF eld(Earlyb rdF eldConstant.FROM_USER_ D_F ELD.getF eldNa ())
        .w hLongTermF eld(Earlyb rdF eldConstant. N_REPLY_TO_TWEET_ D_F ELD.getF eldNa ())
        .w hLongTermF eld(Earlyb rdF eldConstant. N_REPLY_TO_USER_ D_F ELD.getF eldNa ())
        .w hLongTermF eld(Earlyb rdF eldConstant.RETWEET_SOURCE_TWEET_ D_F ELD.getF eldNa ())
        .w hLongTermF eld(Earlyb rdF eldConstant.RETWEET_SOURCE_USER_ D_F ELD.getF eldNa ())
        .w hLongTermF eld(Earlyb rdF eldConstant.CONVERSAT ON_ D_F ELD.getF eldNa ())
        .w h ndexedNotToken zedF eld(Earlyb rdF eldConstant.PLACE_ D_F ELD.getF eldNa ())
        .w hTextF eld(Earlyb rdF eldConstant.PLACE_FULL_NAME_F ELD.getF eldNa (), false)
        .w h ndexedNotToken zedF eld(
            Earlyb rdF eldConstant.PLACE_COUNTRY_CODE_F ELD.getF eldNa ())
        .w h ndexedNotToken zedF eld(
            Earlyb rdF eldConstant.PROF LE_GEO_COUNTRY_CODE_F ELD.getF eldNa ())
        .w hTextF eld(Earlyb rdF eldConstant.PROF LE_GEO_REG ON_F ELD.getF eldNa (), false)
        .w hTextF eld(Earlyb rdF eldConstant.PROF LE_GEO_LOCAL TY_F ELD.getF eldNa (), false)
        .w hTermTextLookup(Earlyb rdF eldConstant.FROM_USER_ D_F ELD.getF eldNa ())
        .w hTermTextLookup(Earlyb rdF eldConstant. N_REPLY_TO_USER_ D_F ELD.getF eldNa ())
        .w hPretoken zedNoPos  onF eld(Earlyb rdF eldConstant.HASHTAGS_F ELD.getF eldNa ())
        .w h ndexedNotToken zedF eld( mmutableSc ma.HF_PHRASE_PA RS_F ELD)
        .w h ndexedNotToken zedF eld( mmutableSc ma.HF_TERM_PA RS_F ELD)
        .w h ndexedNotToken zedF eld(Earlyb rdF eldConstant. MAGE_L NKS_F ELD.getF eldNa ())
        .w h ndexedNotToken zedF eld(Earlyb rdF eldConstant. NTERNAL_F ELD.getF eldNa ())
        .w h ndexedNotToken zedF eld(Earlyb rdF eldConstant. SO_LANGUAGE_F ELD.getF eldNa ())
        .w h ndexedNotToken zedF eld(Earlyb rdF eldConstant.L NKS_F ELD.getF eldNa ())
        .w h ntTermF eld(Earlyb rdF eldConstant.L NK_CATEGORY_F ELD.getF eldNa ())
        .w h ndexedNotToken zedF eld(Earlyb rdF eldConstant.MENT ONS_F ELD.getF eldNa ())
        .w h ndexedNotToken zedF eld(Earlyb rdF eldConstant.NEWS_L NKS_F ELD.getF eldNa ())
        .w h ndexedNotToken zedF eld(Earlyb rdF eldConstant.NORMAL ZED_SOURCE_F ELD.getF eldNa ())
        .w h ndexedNotToken zedF eld(Earlyb rdF eldConstant.PLACE_F ELD.getF eldNa ())
        .w h ndexedNotToken zedF eld(Earlyb rdF eldConstant.SOURCE_F ELD.getF eldNa ())
        .w hPretoken zedNoPos  onF eld(Earlyb rdF eldConstant.STOCKS_F ELD.getF eldNa ())
        .w h ndexedNotToken zedF eld(Earlyb rdF eldConstant.V DEO_L NKS_F ELD.getF eldNa ())
        .w h ntTermF eld(NORMAL ZED_FAVOR TE_COUNT_GREATER_THAN_OR_EQUAL_TO_F ELD.getF eldNa ())
        .w h ntTermF eld(NORMAL ZED_REPLY_COUNT_GREATER_THAN_OR_EQUAL_TO_F ELD.getF eldNa ())
        .w h ntTermF eld(NORMAL ZED_RETWEET_COUNT_GREATER_THAN_OR_EQUAL_TO_F ELD.getF eldNa ())

        .w h ntTermF eld(Earlyb rdF eldConstant.COMPOSER_SOURCE.getF eldNa ())

        .w hLongTermF eld(Earlyb rdF eldConstant.QUOTED_TWEET_ D_F ELD.getF eldNa ())
        .w hLongTermF eld(Earlyb rdF eldConstant.QUOTED_USER_ D_F ELD.getF eldNa ())
        .w hLongTermF eld(Earlyb rdF eldConstant.D RECTED_AT_USER_ D_F ELD.getF eldNa ())

        // Na d ent y f elds
        .w h ndexedNotToken zedF eld(
            Earlyb rdF eldConstant.NAMED_ENT TY_FROM_URL_F ELD.getF eldNa (), true)
        .w h ndexedNotToken zedF eld(
            Earlyb rdF eldConstant.NAMED_ENT TY_FROM_TEXT_F ELD.getF eldNa (), true)
        .w h ndexedNotToken zedF eld(
            Earlyb rdF eldConstant.NAMED_ENT TY_W TH_TYPE_FROM_URL_F ELD.getF eldNa (), true)
        .w h ndexedNotToken zedF eld(
            Earlyb rdF eldConstant.NAMED_ENT TY_W TH_TYPE_FROM_TEXT_F ELD.getF eldNa (), true)

        // ca lCase-token zed user handles and token zed user na s, not searchable by default
        .w hPretoken zedTextF eld(
            Earlyb rdF eldConstant.CAMELCASE_USER_HANDLE_F ELD.getF eldNa (), false)
        .w hPretoken zedTextF eld(
            Earlyb rdF eldConstant.TOKEN ZED_USER_NAME_F ELD.getF eldNa (), false)

        .w h ndexedNotToken zedF eld(
            Earlyb rdF eldConstant.SPACE_ D_F ELD.getF eldNa ())
        .w hTextF eld(Earlyb rdF eldConstant.SPACE_ADM N_F ELD.getF eldNa (), false)
        .w hPretoken zedTextF eld(Earlyb rdF eldConstant.SPACE_T TLE_F ELD.getF eldNa (), false)
        .w hTextF eld(Earlyb rdF eldConstant.TOKEN ZED_SPACE_ADM N_F ELD.getF eldNa (), true)
        .w hPretoken zedTextF eld(
            Earlyb rdF eldConstant.CAMELCASE_TOKEN ZED_SPACE_ADM N_F ELD.getF eldNa (), false)
        .w hPretoken zedTextF eld(
            Earlyb rdF eldConstant.TOKEN ZED_SPACE_ADM N_D SPLAY_NAME_F ELD.getF eldNa (), false)
        .w hPretoken zedTextF eld(
            Earlyb rdF eldConstant.URL_DESCR PT ON_F ELD.getF eldNa (), false)
        .w hPretoken zedTextF eld(
            Earlyb rdF eldConstant.URL_T TLE_F ELD.getF eldNa (), false);

    bu lder
        .w hPhotoUrlFacetF eld(Earlyb rdF eldConstant.TW MG_L NKS_F ELD.getF eldNa ())
        .w hOutOfOrderEnabledForF eld(
            Earlyb rdF eldConstant.L KED_BY_USER_ D_F ELD.getF eldNa ())
        .w hOutOfOrderEnabledForF eld(
            Earlyb rdF eldConstant.RETWEETED_BY_USER_ D.getF eldNa ())
        .w hOutOfOrderEnabledForF eld(
            Earlyb rdF eldConstant.REPL ED_TO_BY_USER_ D.getF eldNa ());

    // ColumnStr deF elds.
    boolean loadCSF ntoRAMDefault = cluster != Earlyb rdCluster.FULL_ARCH VE;

    bu lder
        .w hColumnStr deF eld(Earlyb rdF eldConstants.ENCODED_TWEET_FEATURES_F ELD_NAME,
                Thr ftCSFType. NT, NUMBER_OF_ NTEGERS_FOR_FEATURES,
                true, loadCSF ntoRAMDefault)
        .w hColumnStr deF eld(Earlyb rdF eldConstant.FROM_USER_ D_CSF.getF eldNa (),
            Thr ftCSFType.LONG, 1, false, /* t  full arch ve loads t  f eld  nto RAM */ true)
        .w hColumnStr deF eld(Earlyb rdF eldConstant.SHARED_STATUS_ D_CSF.getF eldNa (),
                Thr ftCSFType.LONG, 1, false, loadCSF ntoRAMDefault)
        .w hColumnStr deF eld(Earlyb rdF eldConstant.CARD_TYPE_CSF_F ELD.getF eldNa (),
                Thr ftCSFType.BYTE, 1, false, loadCSF ntoRAMDefault)
         // CSF Used by arch ve mappers
        .w hColumnStr deF eld(Earlyb rdF eldConstant.CREATED_AT_CSF_F ELD.getF eldNa (),
            Thr ftCSFType. NT, 1, false, /* t  full arch ve loads t  f eld  nto RAM */ true)
        .w hColumnStr deF eld(Earlyb rdF eldConstant. D_CSF_F ELD.getF eldNa (),
            Thr ftCSFType.LONG, 1, false, /* t  full arch ve loads t  f eld  nto RAM */ true)
        .w hColumnStr deF eld(Earlyb rdF eldConstant.LAT_LON_CSF_F ELD.getF eldNa (),
            Thr ftCSFType.LONG, 1, false, loadCSF ntoRAMDefault)
        .w hColumnStr deF eld(Earlyb rdF eldConstant.CONVERSAT ON_ D_CSF.getF eldNa (),
            Thr ftCSFType.LONG, 1, false, loadCSF ntoRAMDefault)
        .w hColumnStr deF eld(Earlyb rdF eldConstant.QUOTED_TWEET_ D_CSF.getF eldNa (),
            Thr ftCSFType.LONG, 1, false, loadCSF ntoRAMDefault)
        .w hColumnStr deF eld(Earlyb rdF eldConstant.QUOTED_USER_ D_CSF.getF eldNa (),
            Thr ftCSFType.LONG, 1, false, loadCSF ntoRAMDefault)
        .w hColumnStr deF eld(Earlyb rdF eldConstant.CARD_LANG_CSF.getF eldNa (),
            Thr ftCSFType. NT, 1, false, loadCSF ntoRAMDefault)
        .w hColumnStr deF eld(Earlyb rdF eldConstant.CARD_UR _CSF.getF eldNa (),
            Thr ftCSFType.LONG, 1, false, loadCSF ntoRAMDefault)
        .w hColumnStr deF eld(Earlyb rdF eldConstant.D RECTED_AT_USER_ D_CSF.getF eldNa (),
            Thr ftCSFType.LONG, 1, false, loadCSF ntoRAMDefault)
        .w hColumnStr deF eld(Earlyb rdF eldConstant.REFERENCE_AUTHOR_ D_CSF.getF eldNa (),
            Thr ftCSFType.LONG, 1, false, loadCSF ntoRAMDefault)
        .w hColumnStr deF eld(
            Earlyb rdF eldConstant.EXCLUS VE_CONVERSAT ON_AUTHOR_ D_CSF.getF eldNa (),
            Thr ftCSFType.LONG, 1, false, loadCSF ntoRAMDefault)

    /* Sem colon on separate l ne to preserve g  bla . */;

    bu lder.w hColumnStr deF eld(
        Earlyb rdF eldConstants.EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        Thr ftCSFType. NT, NUMBER_OF_ NTEGERS_FOR_EXTENDED_FEATURES,
        true, loadCSF ntoRAMDefault);

    for (Map.Entry<Str ng, FeatureConf gurat on> entry : FEATURE_CONF GURAT ON_MAP.entrySet()) {
      Str ng fullNa  = entry.getKey();
      Str ng baseNa  = getBaseF eldNa (fullNa );
      Earlyb rdF eldConstant f eldConstant = Earlyb rdF eldConstants.getF eldConstant(fullNa );
       f (f eldConstant. sVal dF eld nCluster(cluster)) {
        bu lder.w hFeatureConf gurat on(baseNa , fullNa , entry.getValue());
      }
    }
    // Add facet sett ngs for facet f elds
    // boolean args are respect vely w t r to use sk pl st, w t r offens ve, w t r to use CSF
    bu lder
        .w hFacetConf gs(Earlyb rdF eldConstant.MENT ONS_F ELD.getF eldNa (),
            Earlyb rdF eldConstant.MENT ONS_FACET, true, false, false)
        .w hFacetConf gs(Earlyb rdF eldConstant.HASHTAGS_F ELD.getF eldNa (),
            Earlyb rdF eldConstant.HASHTAGS_FACET, true, false, false)
        .w hFacetConf gs(Earlyb rdF eldConstant.STOCKS_F ELD.getF eldNa (),
            Earlyb rdF eldConstant.STOCKS_FACET, true, false, false)
        .w hFacetConf gs(Earlyb rdF eldConstant. MAGE_L NKS_F ELD.getF eldNa (),
            Earlyb rdF eldConstant. MAGES_FACET, true, true, false)
        .w hFacetConf gs(Earlyb rdF eldConstant.V DEO_L NKS_F ELD.getF eldNa (),
            Earlyb rdF eldConstant.V DEOS_FACET, true, true, false)
        .w hFacetConf gs(Earlyb rdF eldConstant.NEWS_L NKS_F ELD.getF eldNa (),
            Earlyb rdF eldConstant.NEWS_FACET, true, false, false)
        .w hFacetConf gs(Earlyb rdF eldConstant. SO_LANGUAGE_F ELD.getF eldNa (),
            Earlyb rdF eldConstant.LANGUAGES_FACET, false, false, false)
        .w hFacetConf gs(Earlyb rdF eldConstant.SOURCE_F ELD.getF eldNa (),
            Earlyb rdF eldConstant.SOURCES_FACET, false, false, false)
        .w hFacetConf gs(Earlyb rdF eldConstant.TW MG_L NKS_F ELD.getF eldNa (),
            Earlyb rdF eldConstant.TW MG_FACET, true, true, false)
        .w hFacetConf gs(Earlyb rdF eldConstant.FROM_USER_ D_CSF.getF eldNa (),
            Earlyb rdF eldConstant.FROM_USER_ D_FACET, false, false, true /* facet on CSF */)
        .w hFacetConf gs(Earlyb rdF eldConstant.SHARED_STATUS_ D_CSF.getF eldNa (),
            Earlyb rdF eldConstant.RETWEETS_FACET, false, false, true /* facet on CSF */)
        .w hFacetConf gs(Earlyb rdF eldConstant.L NKS_F ELD.getF eldNa (),
            Earlyb rdF eldConstant.L NKS_FACET, true, false, false)
        .w hFacetConf gs(
            Earlyb rdF eldConstant.NAMED_ENT TY_W TH_TYPE_FROM_URL_F ELD.getF eldNa (),
            true, false, false)
        .w hFacetConf gs(
            Earlyb rdF eldConstant.NAMED_ENT TY_W TH_TYPE_FROM_TEXT_F ELD.getF eldNa (),
            true, false, false)
        .w hFacetConf gs(
            Earlyb rdF eldConstant.ENT TY_ D_F ELD.getF eldNa (),
            true, false, false)
        .w hFacetConf gs(Earlyb rdF eldConstant.SPACE_ D_F ELD.getF eldNa (),
            Earlyb rdF eldConstant.SPACES_FACET, true, false, false);
    return bu lder.bu ld();
  }
}
