
package com.tw ter.search.common.sc ma.earlyb rd;

 mport java.ut l.Collect on;
 mport java.ut l.EnumSet;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Set;

 mport javax.annotat on.Nullable;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.collect. mmutableL st;
 mport com.google.common.collect. mmutableMap;
 mport com.google.common.collect. mmutableSet;
 mport com.google.common.collect.Sets;

 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftGeoLocat onS ce;
 mport com.tw ter.search.common.sc ma. mmutableSc ma;
 mport com.tw ter.search.common.sc ma.Sc maBu lder;
 mport com.tw ter.search.common.sc ma.base.FeatureConf gurat on;
 mport com.tw ter.search.common.sc ma.base.F eldNa To dMapp ng;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftFeatureNormal zat onType;

/**
 * F eld na s, f eld  Ds etc.
 */
publ c class Earlyb rdF eldConstants extends F eldNa To dMapp ng {
  @V s bleForTest ng
  publ c stat c f nal Str ng ENCODED_TWEET_FEATURES_F ELD_NAME = "encoded_t et_features";

  @V s bleForTest ng
  publ c stat c f nal Str ng EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME =
      "extended_encoded_t et_features";

  pr vate enum FlagFeatureF eldType {
    NON_FLAG_FEATURE_F ELD,
    FLAG_FEATURE_F ELD
  }

  pr vate enum UnusedFeatureF eldType {
    USED_FEATURE_F ELD,
    UNUSED_FEATURE_F ELD
  }

  /**
   * CSF_NAME_TO_M N_ENGAGEMENT_F ELD_MAP and M N_ENGAGEMENT_F ELD_TO_CSF_NAME_MAP are used  n
   * Earlyb rdLuceneQueryV s or to map t  CSFs REPLY_COUNT, RETWEET_COUNT, and FAVOR TE_COUNT to
   * t  r respect ve m n engage nt f elds, and v ce versa.
   */
  publ c stat c f nal  mmutableMap<Str ng, Earlyb rdF eldConstant>
      CSF_NAME_TO_M N_ENGAGEMENT_F ELD_MAP =  mmutableMap.<Str ng, Earlyb rdF eldConstant>bu lder()
          .put(Earlyb rdF eldConstant.REPLY_COUNT.getF eldNa (),
              Earlyb rdF eldConstant.NORMAL ZED_REPLY_COUNT_GREATER_THAN_OR_EQUAL_TO_F ELD)
          .put(Earlyb rdF eldConstant.RETWEET_COUNT.getF eldNa (),
              Earlyb rdF eldConstant.NORMAL ZED_RETWEET_COUNT_GREATER_THAN_OR_EQUAL_TO_F ELD)
          .put(Earlyb rdF eldConstant.FAVOR TE_COUNT.getF eldNa (),
              Earlyb rdF eldConstant.NORMAL ZED_FAVOR TE_COUNT_GREATER_THAN_OR_EQUAL_TO_F ELD)
          .bu ld();

  publ c stat c f nal  mmutableMap<Str ng, Earlyb rdF eldConstant>
      M N_ENGAGEMENT_F ELD_TO_CSF_NAME_MAP =  mmutableMap.<Str ng, Earlyb rdF eldConstant>bu lder()
      .put(Earlyb rdF eldConstant.NORMAL ZED_REPLY_COUNT_GREATER_THAN_OR_EQUAL_TO_F ELD
              .getF eldNa (),
          Earlyb rdF eldConstant.REPLY_COUNT)
      .put(Earlyb rdF eldConstant.NORMAL ZED_RETWEET_COUNT_GREATER_THAN_OR_EQUAL_TO_F ELD
              .getF eldNa (),
          Earlyb rdF eldConstant.RETWEET_COUNT)
      .put(Earlyb rdF eldConstant.NORMAL ZED_FAVOR TE_COUNT_GREATER_THAN_OR_EQUAL_TO_F ELD
              .getF eldNa (),
          Earlyb rdF eldConstant.FAVOR TE_COUNT)
      .bu ld();

  /**
   * A l st of Earlyb rd f eld na s and f eld  Ds, and t  clusters that need t m.
   */
  publ c enum Earlyb rdF eldConstant {
    // T se enums are grouped by category and sorted alphabet cally.
    // Next  ndexed f eld  D  s 76
    // Next CSF f eld  D  s 115
    // Next encoded_features CSF f eld  D  s 185
    // Next extended_encoded_features CSF f eld  D  s 284

    // Text searchable f elds
    // Prov des slow  D Mapp ng from t et  D to doc  D through TermsEnum.seekExact().
     D_F ELD(" d", 0, Earlyb rdCluster.ALL_CLUSTERS),
    RESOLVED_L NKS_TEXT_F ELD("resolved_l nks_text", 1),
    TEXT_F ELD("text", 2),
    TOKEN ZED_FROM_USER_F ELD("token zed_from_user", 3),

    // Ot r  ndexed f elds
    CARD_T TLE_F ELD("card_t le", 4),
    CARD_DESCR PT ON_F ELD("card_descr pt on", 5),
    //   requ re t  createdAt f eld to be set so   can properly f lter t ets based on t  .
    CREATED_AT_F ELD("created_at", 6, Earlyb rdCluster.ALL_CLUSTERS),
    // 7 was for rly EVENT_ DS_F ELD("event_ ds", 7, Earlyb rdCluster.REALT ME)
    ENT TY_ D_F ELD("ent y_ d", 40),
    // T  screen na  of t  user that created t  t et. Should be set to t  normal zed value  n
    // t  com.tw ter.g zmoduck.thr ftjava.Prof le.screen_na  f eld.
    FROM_USER_F ELD("from_user", 8),
    // T  nu r c  D of t  user that created t  t et.
    FROM_USER_ D_F ELD("from_user_ d", 9, Earlyb rdCluster.ALL_CLUSTERS),
    CARD_DOMA N_F ELD("card_doma n", 11),
    CARD_NAME_F ELD("card_na ", 12),
    GEO_HASH_F ELD("geo_hash", 13),
    HASHTAGS_F ELD("hashtags", 14),
    HF_PHRASE_PA RS_F ELD( mmutableSc ma.HF_PHRASE_PA RS_F ELD, 15),
    HF_TERM_PA RS_F ELD( mmutableSc ma.HF_TERM_PA RS_F ELD, 16),
     MAGE_L NKS_F ELD(" mage_l nks", 17),
     N_REPLY_TO_TWEET_ D_F ELD(" n_reply_to_t et_ d", 59),
     N_REPLY_TO_USER_ D_F ELD(" n_reply_to_user_ d", 38),
    // T   nternal f eld  s used for many purposes:
    // 1. to store facet sk pl sts
    // 2. to po r t  f lter operator, by stor ng post ng l st for terms l ke __f lter_tw mg
    // 3. to store post ng l sts for pos  ve and negat ve sm leys
    // 4. to store geo locat on types.
    // etc.
     NTERNAL_F ELD(" nternal", 18, Earlyb rdCluster.ALL_CLUSTERS),
     SO_LANGUAGE_F ELD(" so_lang", 19),
    L NK_CATEGORY_F ELD("l nk_category", 36),
    L NKS_F ELD("l nks", 21),
    MENT ONS_F ELD(" nt ons", 22),
    // F eld 23 used to be NAMED_ENT T ES_F ELD
    NEWS_L NKS_F ELD("news_l nks", 24),
    NORMAL ZED_SOURCE_F ELD("norm_s ce", 25),
    PLACE_F ELD("place", 26),
    // F eld 37 used to be PUBL CLY_ NFERRED_USER_LOCAT ON_PLACE_ D_F ELD
    // T   D of t  s ce t et. Set for ret ets only.
    RETWEET_SOURCE_TWEET_ D_F ELD("ret et_s ce_t et_ d", 60,
        Earlyb rdCluster.ALL_CLUSTERS),
    // T   D of t  s ce t et's author. Set for ret ets only.
    RETWEET_SOURCE_USER_ D_F ELD("ret et_s ce_user_ d", 39),
    SOURCE_F ELD("s ce", 29),
    STOCKS_F ELD("stocks", 30),
    // T  screen na  of t  user that a t et was d rected at.
    TO_USER_F ELD("to_user", 32),
    // F eld 33 used to be TOP C_ DS_F ELD and  s now unused.   can be reused later.
    TW MG_L NKS_F ELD("tw mg_l nks", 34),
    V DEO_L NKS_F ELD("v deo_l nks", 35),
    CAMELCASE_USER_HANDLE_F ELD("ca lcase_token zed_from_user", 41),
    // T  f eld should be set to t  t  token zed and normal zed value  n t 
    // com.tw ter.g zmoduck.thr ftjava.Prof le.na  f eld.
    TOKEN ZED_USER_NAME_F ELD("token zed_from_user_d splay_na ", 42),
    CONVERSAT ON_ D_F ELD("conversat on_ d", 43),
    PLACE_ D_F ELD("place_ d", 44),
    PLACE_FULL_NAME_F ELD("place_full_na ", 45),
    PLACE_COUNTRY_CODE_F ELD("place_country_code", 46),
    PROF LE_GEO_COUNTRY_CODE_F ELD("prof le_geo_country_code", 47),
    PROF LE_GEO_REG ON_F ELD("prof le_geo_reg on", 48),
    PROF LE_GEO_LOCAL TY_F ELD("prof le_geo_local y", 49),
    L KED_BY_USER_ D_F ELD("l ked_by_user_ d", 50, Earlyb rdCluster.REALT ME),
    NORMAL ZED_REPLY_COUNT_GREATER_THAN_OR_EQUAL_TO_F ELD(
        "normal zed_reply_count_greater_than_or_equal_to", 51, Earlyb rdCluster.FULL_ARCH VE),
    NORMAL ZED_RETWEET_COUNT_GREATER_THAN_OR_EQUAL_TO_F ELD(
        "normal zed_ret et_count_greater_than_or_equal_to", 52, Earlyb rdCluster.FULL_ARCH VE),
    NORMAL ZED_FAVOR TE_COUNT_GREATER_THAN_OR_EQUAL_TO_F ELD(
        "normal zed_favor e_count_greater_than_or_equal_to", 53, Earlyb rdCluster.FULL_ARCH VE),
    COMPOSER_SOURCE("composer_s ce", 54),
    QUOTED_TWEET_ D_F ELD("quoted_t et_ d", 55),
    QUOTED_USER_ D_F ELD("quoted_user_ d", 56),
    RETWEETED_BY_USER_ D("ret eted_by_user_ d", 57, Earlyb rdCluster.REALT ME),
    REPL ED_TO_BY_USER_ D("repl ed_to_by_user_ d", 58, Earlyb rdCluster.REALT ME),
    CARD_LANG("card_lang", 61),
    // SEARCH-27823: F eld  D 62 used to be na d_ent y, wh ch was t  comb nat on of all
    // na d_ent y* f elds below.   need to leave 62 unused for backwards compat b l y.
    NAMED_ENT TY_FROM_URL_F ELD("na d_ent y_from_url", 63),
    NAMED_ENT TY_FROM_TEXT_F ELD("na d_ent y_from_text", 64),
    NAMED_ENT TY_W TH_TYPE_FROM_URL_F ELD("na d_ent y_w h_type_from_url", 65),
    NAMED_ENT TY_W TH_TYPE_FROM_TEXT_F ELD("na d_ent y_w h_type_from_text", 66),
    D RECTED_AT_USER_ D_F ELD("d rected_at_user_ d", 67),
    SPACE_ D_F ELD("space_ d", 68,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_GENERAL_PURPOSE_CLUSTERS),
    SPACE_T TLE_F ELD("space_t le", 69,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_GENERAL_PURPOSE_CLUSTERS),

    // Deta led descr pt on of t  space adm n f elds can be found at go/earlyb rdf elds.
    SPACE_ADM N_F ELD("space_adm n", 70,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_GENERAL_PURPOSE_CLUSTERS),
    TOKEN ZED_SPACE_ADM N_F ELD("token zed_space_adm n", 71,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_GENERAL_PURPOSE_CLUSTERS),
    CAMELCASE_TOKEN ZED_SPACE_ADM N_F ELD("ca lcase_token zed_space_adm n", 72,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_GENERAL_PURPOSE_CLUSTERS),
    TOKEN ZED_SPACE_ADM N_D SPLAY_NAME_F ELD("token zed_space_adm n_d splay_na ", 73,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_GENERAL_PURPOSE_CLUSTERS),
    URL_DESCR PT ON_F ELD("url_descr pt on", 74),
    URL_T TLE_F ELD("url_t le", 75),

    // CSF
    CARD_TYPE_CSF_F ELD("card_type_csf", 100),
    ENCODED_TWEET_FEATURES_F ELD(ENCODED_TWEET_FEATURES_F ELD_NAME, 102,
        Earlyb rdCluster.ALL_CLUSTERS),
    // Prov des t  doc  D -> or g nal t et  D mapp ng for ret ets.
    SHARED_STATUS_ D_CSF("shared_status_ d_csf", 106, Earlyb rdCluster.ALL_CLUSTERS),
    // Prov des t  doc  D -> t et author's user  D mapp ng.
    FROM_USER_ D_CSF("from_user_ d_csf", 103, Earlyb rdCluster.ALL_CLUSTERS),
    CREATED_AT_CSF_F ELD("created_at_csf", 101, Earlyb rdCluster.ARCH VE_CLUSTERS),
    // Prov des t  doc  D -> t et  D mapp ng.
     D_CSF_F ELD(" d_csf", 104, Earlyb rdCluster.ARCH VE_CLUSTERS),
    LAT_LON_CSF_F ELD("latlon_csf", 105),
    CONVERSAT ON_ D_CSF("conversat on_ d_csf", 107, Earlyb rdCluster.ALL_CLUSTERS),
    QUOTED_TWEET_ D_CSF("quoted_t et_ d_csf", 108),
    QUOTED_USER_ D_CSF("quoted_user_ d_csf", 109),
    CARD_LANG_CSF("card_lang_csf", 110),
    D RECTED_AT_USER_ D_CSF("d rected_at_user_ d_csf", 111),
    REFERENCE_AUTHOR_ D_CSF("reference_author_ d_csf", 112),
    EXCLUS VE_CONVERSAT ON_AUTHOR_ D_CSF("exclus ve_conversat on_author_ d_csf", 113),
    CARD_UR _CSF("card_ur _csf", 114),

    // CSF V ews on top of ENCODED_TWEET_FEATURES_F ELD
     S_RETWEET_FLAG(ENCODED_TWEET_FEATURES_F ELD_NAME, " S_RETWEET_FLAG", 150,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
     S_OFFENS VE_FLAG(ENCODED_TWEET_FEATURES_F ELD_NAME, " S_OFFENS VE_FLAG", 151,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
    HAS_L NK_FLAG(ENCODED_TWEET_FEATURES_F ELD_NAME, "HAS_L NK_FLAG", 152,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
    HAS_TREND_FLAG(ENCODED_TWEET_FEATURES_F ELD_NAME, "HAS_TREND_FLAG", 153,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
     S_REPLY_FLAG(ENCODED_TWEET_FEATURES_F ELD_NAME, " S_REPLY_FLAG", 154,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
     S_SENS T VE_CONTENT(ENCODED_TWEET_FEATURES_F ELD_NAME, " S_SENS T VE_CONTENT", 155,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
    HAS_MULT PLE_HASHTAGS_OR_TRENDS_FLAG(ENCODED_TWEET_FEATURES_F ELD_NAME,
        "HAS_MULT PLE_HASHTAGS_OR_TRENDS_FLAG", 156, FlagFeatureF eldType.FLAG_FEATURE_F ELD,
        Earlyb rdCluster.ALL_CLUSTERS),
    FROM_VER F ED_ACCOUNT_FLAG(ENCODED_TWEET_FEATURES_F ELD_NAME, "FROM_VER F ED_ACCOUNT_FLAG",
        157,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
    TEXT_SCORE(ENCODED_TWEET_FEATURES_F ELD_NAME, "TEXT_SCORE", 158,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
    LANGUAGE(ENCODED_TWEET_FEATURES_F ELD_NAME, "LANGUAGE", 159,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
    L NK_LANGUAGE(ENCODED_TWEET_FEATURES_F ELD_NAME, "L NK_LANGUAGE", 160,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
    HAS_ MAGE_URL_FLAG(ENCODED_TWEET_FEATURES_F ELD_NAME, "HAS_ MAGE_URL_FLAG", 161,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
    HAS_V DEO_URL_FLAG(ENCODED_TWEET_FEATURES_F ELD_NAME, "HAS_V DEO_URL_FLAG", 162,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
    HAS_NEWS_URL_FLAG(ENCODED_TWEET_FEATURES_F ELD_NAME, "HAS_NEWS_URL_FLAG", 163,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
    HAS_EXPANDO_CARD_FLAG(ENCODED_TWEET_FEATURES_F ELD_NAME, "HAS_EXPANDO_CARD_FLAG", 164,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
    HAS_MULT PLE_MED A_FLAG(ENCODED_TWEET_FEATURES_F ELD_NAME, "HAS_MULT PLE_MED A_FLAG", 165,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
    PROF LE_ S_EGG_FLAG(ENCODED_TWEET_FEATURES_F ELD_NAME, "PROF LE_ S_EGG_FLAG", 166,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
    NUM_MENT ONS(ENCODED_TWEET_FEATURES_F ELD_NAME, "NUM_MENT ONS", 167,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
    NUM_HASHTAGS(ENCODED_TWEET_FEATURES_F ELD_NAME, "NUM_HASHTAGS", 168,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
    HAS_CARD_FLAG(ENCODED_TWEET_FEATURES_F ELD_NAME, "HAS_CARD_FLAG", 169,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
    HAS_V S BLE_L NK_FLAG(ENCODED_TWEET_FEATURES_F ELD_NAME, "HAS_V S BLE_L NK_FLAG", 170,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
    USER_REPUTAT ON(ENCODED_TWEET_FEATURES_F ELD_NAME, "USER_REPUTAT ON", 171,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
     S_USER_SPAM_FLAG(ENCODED_TWEET_FEATURES_F ELD_NAME, " S_USER_SPAM_FLAG", 172,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
     S_USER_NSFW_FLAG(ENCODED_TWEET_FEATURES_F ELD_NAME, " S_USER_NSFW_FLAG", 173,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
     S_USER_BOT_FLAG(ENCODED_TWEET_FEATURES_F ELD_NAME, " S_USER_BOT_FLAG", 174,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
     S_USER_NEW_FLAG(ENCODED_TWEET_FEATURES_F ELD_NAME, " S_USER_NEW_FLAG", 175,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
    PREV_USER_TWEET_ENGAGEMENT(ENCODED_TWEET_FEATURES_F ELD_NAME, "PREV_USER_TWEET_ENGAGEMENT",
        176,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
    COMPOSER_SOURCE_ S_CAMERA_FLAG(
        ENCODED_TWEET_FEATURES_F ELD_NAME,
        "COMPOSER_SOURCE_ S_CAMERA_FLAG",
        177,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD,
        Earlyb rdCluster.ALL_CLUSTERS),
    RETWEET_COUNT(
        ENCODED_TWEET_FEATURES_F ELD_NAME,
        "RETWEET_COUNT",
        178,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.LEGACY_BYTE_NORMAL ZER_W TH_LOG2),
    FAVOR TE_COUNT(
        ENCODED_TWEET_FEATURES_F ELD_NAME,
        "FAVOR TE_COUNT",
        179,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.LEGACY_BYTE_NORMAL ZER_W TH_LOG2),
    REPLY_COUNT(
        ENCODED_TWEET_FEATURES_F ELD_NAME,
        "REPLY_COUNT",
        180,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.LEGACY_BYTE_NORMAL ZER_W TH_LOG2),
    PARUS_SCORE(ENCODED_TWEET_FEATURES_F ELD_NAME, "PARUS_SCORE", 181,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),

    /**
     * T   s t  rough percentage of t  nth token at 140 d v ded by num tokens
     * and  s bas cally n / num tokens w re n  s t  token start ng before 140 characters
     */
    V S BLE_TOKEN_RAT O(ENCODED_TWEET_FEATURES_F ELD_NAME, "V S BLE_TOKEN_RAT O", 182,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
    HAS_QUOTE_FLAG(ENCODED_TWEET_FEATURES_F ELD_NAME, "HAS_QUOTE_FLAG", 183,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),

    FROM_BLUE_VER F ED_ACCOUNT_FLAG(ENCODED_TWEET_FEATURES_F ELD_NAME,
        "FROM_BLUE_VER F ED_ACCOUNT_FLAG",
        184,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),

    TWEET_S GNATURE(ENCODED_TWEET_FEATURES_F ELD_NAME, "TWEET_S GNATURE", 188,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),

    // MED A TYPES
    HAS_CONSUMER_V DEO_FLAG(ENCODED_TWEET_FEATURES_F ELD_NAME, "HAS_CONSUMER_V DEO_FLAG", 189,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
    HAS_PRO_V DEO_FLAG(ENCODED_TWEET_FEATURES_F ELD_NAME, "HAS_PRO_V DEO_FLAG", 190,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
    HAS_V NE_FLAG(ENCODED_TWEET_FEATURES_F ELD_NAME, "HAS_V NE_FLAG", 191,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
    HAS_PER SCOPE_FLAG(ENCODED_TWEET_FEATURES_F ELD_NAME, "HAS_PER SCOPE_FLAG", 192,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),
    HAS_NAT VE_ MAGE_FLAG(ENCODED_TWEET_FEATURES_F ELD_NAME, "HAS_NAT VE_ MAGE_FLAG", 193,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),

    // NOTE:  f poss ble, please reserve f eld  D 194 to 196 for future  d a types (SEARCH-9131)

     S_NULLCAST_FLAG(ENCODED_TWEET_FEATURES_F ELD_NAME, " S_NULLCAST_FLAG", 197,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD, Earlyb rdCluster.ALL_CLUSTERS),

    // EXTENDED ENCODED TWEET FEATURES that's not ava lable on arch ve clusters
    EXTENDED_ENCODED_TWEET_FEATURES_F ELD(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME, 200,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS),

    EMBEDS_ MPRESS ON_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "EMBEDS_ MPRESS ON_COUNT",
        221,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.LEGACY_BYTE_NORMAL ZER),
    EMBEDS_URL_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "EMBEDS_URL_COUNT",
        222,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.LEGACY_BYTE_NORMAL ZER),
    V DEO_V EW_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "V DEO_V EW_COUNT",
        223,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.LEGACY_BYTE_NORMAL ZER),

    // empty b s  n  nteger 0 (start ng b  24, 8 b s)
    EXTENDED_FEATURE_UNUSED_B TS_0_24_8(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "UNUSED_B TS_0_24_8", 244,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        UnusedFeatureF eldType.UNUSED_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS),

    // SEARCH-8564 - Reference T et Author  D
    REFERENCE_AUTHOR_ D_LEAST_S GN F CANT_ NT(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "REFERENCE_AUTHOR_ D_LEAST_S GN F CANT_ NT", 202,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS),
    REFERENCE_AUTHOR_ D_MOST_S GN F CANT_ NT(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "REFERENCE_AUTHOR_ D_MOST_S GN F CANT_ NT", 203,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS),

    // SEARCHQUAL-8130: engage nt counters v2
    //  nteger 3
    RETWEET_COUNT_V2(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "RETWEET_COUNT_V2", 225,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.SMART_ NTEGER_NORMAL ZER),
    FAVOR TE_COUNT_V2(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "FAVOR TE_COUNT_V2", 226,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.SMART_ NTEGER_NORMAL ZER),
    REPLY_COUNT_V2(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "REPLY_COUNT_V2", 227,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.SMART_ NTEGER_NORMAL ZER),
    EMBEDS_ MPRESS ON_COUNT_V2(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "EMBEDS_ MPRESS ON_COUNT_V2",
        228,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.SMART_ NTEGER_NORMAL ZER),

    //  nteger 4
    EMBEDS_URL_COUNT_V2(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "EMBEDS_URL_COUNT_V2",
        229,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.SMART_ NTEGER_NORMAL ZER),
    V DEO_V EW_COUNT_V2(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "V DEO_V EW_COUNT_V2",
        230,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.SMART_ NTEGER_NORMAL ZER),
    QUOTE_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "QUOTE_COUNT",
        231,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.SMART_ NTEGER_NORMAL ZER),

    // T et Safety Labels
    LABEL_ABUS VE_FLAG(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "LABEL_ABUS VE_FLAG", 232,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS),

    LABEL_ABUS VE_H _RCL_FLAG(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "LABEL_ABUS VE_H _RCL_FLAG", 233,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS),

    LABEL_DUP_CONTENT_FLAG(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "LABEL_DUP_CONTENT_FLAG", 234,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS),

    LABEL_NSFW_H _PRC_FLAG(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "LABEL_NSFW_H _PRC_FLAG", 235,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS),

    LABEL_NSFW_H _RCL_FLAG(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "LABEL_NSFW_H _RCL_FLAG", 236,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS),

    LABEL_SPAM_FLAG(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "LABEL_SPAM_FLAG", 237,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS),

    LABEL_SPAM_H _RCL_FLAG(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "LABEL_SPAM_H _RCL_FLAG", 238,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS),

    // please save t  b  for ot r safety labels
    EXTENDED_TEST_FEATURE_UNUSED_B TS_4_31_1(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "UNUSED_B TS_4_31_1", 239,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        UnusedFeatureF eldType.UNUSED_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS),

    //  nteger 5
    WE GHTED_RETWEET_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "WE GHTED_RETWEET_COUNT",
        240,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.SMART_ NTEGER_NORMAL ZER),
    WE GHTED_REPLY_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "WE GHTED_REPLY_COUNT",
        241,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.SMART_ NTEGER_NORMAL ZER),
    WE GHTED_FAVOR TE_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "WE GHTED_FAVOR TE_COUNT",
        242,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.SMART_ NTEGER_NORMAL ZER),
    WE GHTED_QUOTE_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "WE GHTED_QUOTE_COUNT",
        243,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.SMART_ NTEGER_NORMAL ZER),

    //  nteger 6
    // Per scope features
    PER SCOPE_EX STS(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "PER SCOPE_EX STS", 245,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS),
    PER SCOPE_HAS_BEEN_FEATURED(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "PER SCOPE_HAS_BEEN_FEATURED", 246,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS),
    PER SCOPE_ S_CURRENTLY_FEATURED(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "PER SCOPE_ S_CURRENTLY_FEATURED", 247,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS),
    PER SCOPE_ S_FROM_QUAL TY_SOURCE(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "PER SCOPE_ S_FROM_QUAL TY_SOURCE", 248,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS),
    PER SCOPE_ S_L VE(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "PER SCOPE_ S_L VE", 249,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS),
     S_TREND NG_NOW_FLAG(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        " S_TREND NG_NOW_FLAG", 292,
        FlagFeatureF eldType.FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS),

    // rema n ng b s for  nteger 6 (start ng b  6, 26 rema n ng b s)
    EXTENDED_TEST_FEATURE_UNUSED_B TS_7_6_26(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "UNUSED_B TS_7_6_26", 250,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        UnusedFeatureF eldType.UNUSED_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS),

    // Decay ng engage nt counters
    //  nteger 7
    DECAYED_RETWEET_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "DECAYED_RETWEET_COUNT",
        251,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.SMART_ NTEGER_NORMAL ZER),
    DECAYED_REPLY_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "DECAYED_REPLY_COUNT",
        252,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.SMART_ NTEGER_NORMAL ZER),
    DECAYED_FAVOR TE_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "DECAYED_FAVOR TE_COUNT",
        253,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.SMART_ NTEGER_NORMAL ZER),
    DECAYED_QUOTE_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "DECAYED_QUOTE_COUNT",
        254,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.SMART_ NTEGER_NORMAL ZER),

    // Fake engage nt counters. T  fake  re  s  n t  sense of spam, not  n t  sense of test ng.
    // Refer to [J RA SEARCHQUAL-10736 Remove Fake Engage nts  n Search] for more deta ls.
    //  nteger 8
    FAKE_RETWEET_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "FAKE_RETWEET_COUNT", 269,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.SMART_ NTEGER_NORMAL ZER),
    FAKE_REPLY_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "FAKE_REPLY_COUNT", 270,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.SMART_ NTEGER_NORMAL ZER),
    FAKE_FAVOR TE_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "FAKE_FAVOR TE_COUNT", 271,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.SMART_ NTEGER_NORMAL ZER),
    FAKE_QUOTE_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "FAKE_QUOTE_COUNT", 272,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.SMART_ NTEGER_NORMAL ZER),

    // Last engage nt t  stamps. T se features use t  T et's creat on t   as base and
    // are  ncre nted every 1 h 
    //  nteger 9
    LAST_RETWEET_S NCE_CREAT ON_HRS(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "LAST_RETWEET_S NCE_CREAT ON_HRS",
        273,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.NONE),
    LAST_REPLY_S NCE_CREAT ON_HRS(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "LAST_REPLY_S NCE_CREAT ON_HRS",
        274,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.NONE),
    LAST_FAVOR TE_S NCE_CREAT ON_HRS(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "LAST_FAVOR TE_S NCE_CREAT ON_HRS",
        275,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.NONE),
    LAST_QUOTE_S NCE_CREAT ON_HRS(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "LAST_QUOTE_S NCE_CREAT ON_HRS",
        276,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.NONE),

    // 4 b s hashtag count,  nt on count and stock count (SEARCH-24336)
    //  nteger 10
    NUM_HASHTAGS_V2(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "NUM_HASHTAGS_V2",
        277,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.NONE
    ),
    NUM_MENT ONS_V2(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "NUM_MENT ONS_V2",
        278,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.NONE
    ),
    NUM_STOCKS(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "NUM_STOCKS",
        279,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.NONE
    ),

    //  nteger 11
    // Bl nk engage nt counters
    BL NK_RETWEET_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "BL NK_RETWEET_COUNT",
        280,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.SMART_ NTEGER_NORMAL ZER),
    BL NK_REPLY_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "BL NK_REPLY_COUNT",
        281,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.SMART_ NTEGER_NORMAL ZER),
    BL NK_FAVOR TE_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "BL NK_FAVOR TE_COUNT",
        282,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.SMART_ NTEGER_NORMAL ZER),
    BL NK_QUOTE_COUNT(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "BL NK_QUOTE_COUNT",
        283,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.SMART_ NTEGER_NORMAL ZER),

    //  nteger 10 (rema n ng)
    // Product on Tox c y and PBlock score from HML (go/tox c y, go/pblock)
    TOX C TY_SCORE(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "TOX C TY_SCORE", 284,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.PRED CT ON_SCORE_NORMAL ZER
    ),
    PBLOCK_SCORE(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "PBLOCK_SCORE", 285,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.PRED CT ON_SCORE_NORMAL ZER
    ),

    //  nteger 12
    // Exper  ntal  alth model scores from HML
    EXPER MENTAL_HEALTH_MODEL_SCORE_1(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "EXPER MENTAL_HEALTH_MODEL_SCORE_1", 286,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.PRED CT ON_SCORE_NORMAL ZER
    ),
    EXPER MENTAL_HEALTH_MODEL_SCORE_2(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "EXPER MENTAL_HEALTH_MODEL_SCORE_2", 287,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.PRED CT ON_SCORE_NORMAL ZER
    ),
    EXPER MENTAL_HEALTH_MODEL_SCORE_3(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "EXPER MENTAL_HEALTH_MODEL_SCORE_3", 288,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.PRED CT ON_SCORE_NORMAL ZER
    ),
    // rema n ng b s for  ndex 12 (unused_b s_12)
    EXTENDED_TEST_FEATURE_UNUSED_B TS_12_30_2(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "UNUSED_B TS_12_30_2", 289,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        UnusedFeatureF eldType.UNUSED_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS),

    //  nteger 13
    // Exper  ntal  alth model scores from HML (cont.)
    EXPER MENTAL_HEALTH_MODEL_SCORE_4(
        EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "EXPER MENTAL_HEALTH_MODEL_SCORE_4", 290,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.PRED CT ON_SCORE_NORMAL ZER
    ),
    // Product on pSpam T et score from HML (go/pspam t et)
    P_SPAMMY_TWEET_SCORE(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "P_SPAMMY_TWEET_SCORE", 291,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.PRED CT ON_SCORE_NORMAL ZER
    ),
    // Product on pReportedT et score from HML (go/preportedt et)
    P_REPORTED_TWEET_SCORE(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "P_REPORTED_TWEET_SCORE", 293,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.PRED CT ON_SCORE_NORMAL ZER
    ),
    // rema n ng b s for  ndex 13 (unused_b s_13)
    EXTENDED_TEST_FEATURE_UNUSED_B TS_13_30_2(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "UNUSED_B TS_13_30_2", 294,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        UnusedFeatureF eldType.UNUSED_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS
    ),

    //  nteger 14
    //  alth model scores from HML (cont.)
    // Prod Spam  T et Content model score from Platform Man pulat on (go/spam -t et-content)
    SPAMMY_TWEET_CONTENT_SCORE(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "SPAMMY_TWEET_CONTENT_SCORE", 295,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS,
        Thr ftFeatureNormal zat onType.PRED CT ON_SCORE_NORMAL ZER
    ),
    // rema n ng b s for  ndex 14 (unused_b s_14)
    EXTENDED_TEST_FEATURE_UNUSED_B TS_14_10_22(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "UNUSED_B TS_14_10_22", 296,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        UnusedFeatureF eldType.UNUSED_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS
    ),

    // Note that t   nteger block  ndex    n t  na s UNUSED_B TS{ }" below  s 1-based, but t 
    //  ndex j  n UNUSED_B TS_{j}_x_y above  s 0-based.
    EXTENDED_TEST_FEATURE_UNUSED_B TS_16(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "UNUSED_B TS16", 216,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        UnusedFeatureF eldType.UNUSED_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS),

    EXTENDED_TEST_FEATURE_UNUSED_B TS_17(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "UNUSED_B TS17", 217,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        UnusedFeatureF eldType.UNUSED_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS),

    EXTENDED_TEST_FEATURE_UNUSED_B TS_18(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "UNUSED_B TS18", 218,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        UnusedFeatureF eldType.UNUSED_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS),

    EXTENDED_TEST_FEATURE_UNUSED_B TS_19(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "UNUSED_B TS19", 219,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        UnusedFeatureF eldType.UNUSED_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS),

    EXTENDED_TEST_FEATURE_UNUSED_B TS_20(EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME,
        "UNUSED_B TS20", 220,
        FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
        UnusedFeatureF eldType.UNUSED_FEATURE_F ELD,
        Earlyb rdCluster.TW TTER_ N_MEMORY_ NDEX_FORMAT_ALL_CLUSTERS);

    // F lter f eld terms. T se end up as terms  n t  " nternal" f eld ( d=18). So for example
    //   can have a doc w h f eld( nternal) = "__f lter_nullcast", "__f lter_v ne" and that w ll
    // be a nullcast t et w h a v ne l nk  n  .
    publ c stat c f nal Str ng NULLCAST_F LTER_TERM = "nullcast";
    publ c stat c f nal Str ng VER F ED_F LTER_TERM = "ver f ed";
    publ c stat c f nal Str ng BLUE_VER F ED_F LTER_TERM = "blue_ver f ed";
    publ c stat c f nal Str ng NAT VE_RETWEETS_F LTER_TERM = "nat veret ets";
    publ c stat c f nal Str ng QUOTE_F LTER_TERM = "quote";
    publ c stat c f nal Str ng REPL ES_F LTER_TERM = "repl es";
    publ c stat c f nal Str ng CONSUMER_V DEO_F LTER_TERM = "consu r_v deo";
    publ c stat c f nal Str ng PRO_V DEO_F LTER_TERM = "pro_v deo";
    publ c stat c f nal Str ng V NE_F LTER_TERM = "v ne";
    publ c stat c f nal Str ng PER SCOPE_F LTER_TERM = "per scope";
    publ c stat c f nal Str ng PROF LE_GEO_F LTER_TERM = "prof le_geo";
    publ c stat c f nal Str ng SELF_THREAD_F LTER_TERM = "self_threads";
    publ c stat c f nal Str ng D RECTED_AT_F LTER_TERM = "d rected_at";
    publ c stat c f nal Str ng EXCLUS VE_F LTER_TERM = "exclus ve";

    // Reserved terms for t   nternal f eld.
    publ c stat c f nal Str ng HAS_POS T VE_SM LEY = "__has_pos  ve_sm ley";
    publ c stat c f nal Str ng HAS_NEGAT VE_SM LEY = "__has_negat ve_sm ley";
    publ c stat c f nal Str ng  S_OFFENS VE = "__ s_offens ve";

    // Facet f elds
    publ c stat c f nal Str ng MENT ONS_FACET = " nt ons";
    publ c stat c f nal Str ng HASHTAGS_FACET = "hashtags";
    publ c stat c f nal Str ng STOCKS_FACET = "stocks";
    publ c stat c f nal Str ng V DEOS_FACET = "v deos";
    publ c stat c f nal Str ng  MAGES_FACET = " mages";
    publ c stat c f nal Str ng NEWS_FACET = "news";
    publ c stat c f nal Str ng LANGUAGES_FACET = "languages";
    publ c stat c f nal Str ng SOURCES_FACET = "s ces";
    publ c stat c f nal Str ng TW MG_FACET = "tw mg";
    publ c stat c f nal Str ng FROM_USER_ D_FACET = "user_ d";
    publ c stat c f nal Str ng RETWEETS_FACET = "ret ets";
    publ c stat c f nal Str ng L NKS_FACET = "l nks";
    publ c stat c f nal Str ng SPACES_FACET = "spaces";

    /**
     * Used by t  query parser to c ck that t  operator of a [f lter X] query  s val d.
     * Also used by blender, though   probably shouldn't be.
     */
    publ c stat c f nal  mmutableSet<Str ng> FACETS =  mmutableSet.<Str ng>bu lder()
        .add(MENT ONS_FACET)
        .add(HASHTAGS_FACET)
        .add(STOCKS_FACET)
        .add(V DEOS_FACET)
        .add( MAGES_FACET)
        .add(NEWS_FACET)
        .add(L NKS_FACET)
        .add(LANGUAGES_FACET)
        .add(SOURCES_FACET)
        .add(TW MG_FACET)
        .add(SPACES_FACET)
        .bu ld();

    /**
     * Used by blender to convert facet na s to f eld na s.   should f nd a way to get t 
     *  nformat on   need  n blender w hout need ng t  map.
     */
    publ c stat c f nal  mmutableMap<Str ng, Str ng> FACET_TO_F ELD_MAP =
         mmutableMap.<Str ng, Str ng>bu lder()
            .put(MENT ONS_FACET, MENT ONS_F ELD.getF eldNa ())
            .put(HASHTAGS_FACET, HASHTAGS_F ELD.getF eldNa ())
            .put(STOCKS_FACET, STOCKS_F ELD.getF eldNa ())
            .put(V DEOS_FACET, V DEO_L NKS_F ELD.getF eldNa ())
            .put( MAGES_FACET,  MAGE_L NKS_F ELD.getF eldNa ())
            .put(NEWS_FACET, NEWS_L NKS_F ELD.getF eldNa ())
            .put(LANGUAGES_FACET,  SO_LANGUAGE_F ELD.getF eldNa ())
            .put(SOURCES_FACET, SOURCE_F ELD.getF eldNa ())
            .put(TW MG_FACET, TW MG_L NKS_F ELD.getF eldNa ())
            .put(L NKS_FACET, L NKS_F ELD.getF eldNa ())
            .put(SPACES_FACET, SPACE_ D_F ELD.getF eldNa ())
            .bu ld();

    publ c stat c Str ng getFacetSk pF eldNa (Str ng f eldNa ) {
      return "__has_" + f eldNa ;
    }

    pr vate f nal Str ng f eldNa ;
    pr vate f nal  nt f eld d;
    pr vate f nal EnumSet<Earlyb rdCluster> clusters;
    pr vate f nal FlagFeatureF eldType flagFeatureF eld;

    pr vate f nal UnusedFeatureF eldType unusedF eld;

    // Only set for feature f elds.
    @Nullable
    pr vate f nal FeatureConf gurat on featureConf gurat on;

    // Only set for feature f elds.
    pr vate f nal Thr ftFeatureNormal zat onType featureNormal zat onType;

    // To s mpl fy f eld conf gurat ons and reduce dupl cate code,   g ve clusters a default value
    Earlyb rdF eldConstant(Str ng f eldNa ,  nt f eld d) {
      t (f eldNa , f eld d, Earlyb rdCluster.GENERAL_PURPOSE_CLUSTERS, null);
    }

    Earlyb rdF eldConstant(Str ng f eldNa ,  nt f eld d, Set<Earlyb rdCluster> clusters) {
      t (f eldNa , f eld d, clusters, null);
    }

    Earlyb rdF eldConstant(Str ng f eldNa ,  nt f eld d, Earlyb rdCluster cluster) {
      t (f eldNa , f eld d,  mmutableSet.<Earlyb rdCluster>of(cluster), null);
    }

    /**
     * Base f eld na   s needed  re  n order to construct t  full
     * na  of t  feature.   convent on  s that a feature should be na d
     * as: baseF eldNa .featureNa .  For example: encoded_t et_features.ret et_count.
     */
    Earlyb rdF eldConstant(
        Str ng baseNa ,
        Str ng f eldNa ,
         nt f eld d,
        FlagFeatureF eldType flagFeatureF eld,
        Set<Earlyb rdCluster> clusters) {
      t ((baseNa  + Sc maBu lder.CSF_V EW_NAME_SEPARATOR + f eldNa ).toLo rCase(),
          f eld d, clusters, flagFeatureF eld, null);
    }

    Earlyb rdF eldConstant(
        Str ng baseNa ,
        Str ng f eldNa ,
         nt f eld d,
        FlagFeatureF eldType flagFeatureF eld,
        UnusedFeatureF eldType unusedF eld,
        Set<Earlyb rdCluster> clusters) {
      t ((baseNa  + Sc maBu lder.CSF_V EW_NAME_SEPARATOR + f eldNa ).toLo rCase(),
          f eld d, clusters, flagFeatureF eld, unusedF eld, null);
    }

    Earlyb rdF eldConstant(
        Str ng baseNa ,
        Str ng f eldNa ,
         nt f eld d,
        FlagFeatureF eldType flagFeatureF eld,
        Set<Earlyb rdCluster> clusters,
        Thr ftFeatureNormal zat onType featureNormal zat onType) {
      t ((baseNa  + Sc maBu lder.CSF_V EW_NAME_SEPARATOR + f eldNa ).toLo rCase(),
          f eld d, clusters, flagFeatureF eld, UnusedFeatureF eldType.USED_FEATURE_F ELD,
          featureNormal zat onType, null);
    }

    /**
     * Constructor.
     */
    Earlyb rdF eldConstant(Str ng f eldNa ,  nt f eld d, Set<Earlyb rdCluster> clusters,
                                   @Nullable FeatureConf gurat on featureConf gurat on) {
      t (f eldNa , f eld d, clusters, FlagFeatureF eldType.NON_FLAG_FEATURE_F ELD,
          featureConf gurat on);
    }

    /**
     * Constructor.
     */
    Earlyb rdF eldConstant(Str ng f eldNa ,
                            nt f eld d,
                           Set<Earlyb rdCluster> clusters,
                           FlagFeatureF eldType flagFeatureF eld,
                           @Nullable FeatureConf gurat on featureConf gurat on) {
      t (f eldNa , f eld d, clusters, flagFeatureF eld,
          UnusedFeatureF eldType.USED_FEATURE_F ELD, featureConf gurat on);
    }

    /**
     * Constructor.
     */
    Earlyb rdF eldConstant(Str ng f eldNa ,
                            nt f eld d,
                           Set<Earlyb rdCluster> clusters,
                           FlagFeatureF eldType flagFeatureF eld,
                           UnusedFeatureF eldType unusedF eld,
                           @Nullable FeatureConf gurat on featureConf gurat on) {
      t (f eldNa , f eld d, clusters, flagFeatureF eld, unusedF eld, null, featureConf gurat on);
    }

    /**
     * Constructor.
     */
    Earlyb rdF eldConstant(Str ng f eldNa ,
                            nt f eld d,
                           Set<Earlyb rdCluster> clusters,
                           FlagFeatureF eldType flagFeatureF eld,
                           UnusedFeatureF eldType unusedF eld,
                           @Nullable Thr ftFeatureNormal zat onType featureNormal zat onType,
                           @Nullable FeatureConf gurat on featureConf gurat on) {
      t .f eld d = f eld d;
      t .f eldNa  = f eldNa ;
      t .clusters = EnumSet.copyOf(clusters);
      t .flagFeatureF eld = flagFeatureF eld;
      t .unusedF eld = unusedF eld;
      t .featureNormal zat onType = featureNormal zat onType;
      t .featureConf gurat on = featureConf gurat on;
    }

    // Overr de toStr ng to make replac ng StatusConstant Eas er.
    @Overr de
    publ c Str ng toStr ng() {
      return f eldNa ;
    }

    publ c boolean  sVal dF eld nCluster(Earlyb rdCluster cluster) {
      return clusters.conta ns(cluster);
    }

    publ c Str ng getF eldNa () {
      return f eldNa ;
    }

    publ c  nt getF eld d() {
      return f eld d;
    }

    publ c FlagFeatureF eldType getFlagFeatureF eld() {
      return flagFeatureF eld;
    }

    publ c boolean  sFlagFeatureF eld() {
      return flagFeatureF eld == FlagFeatureF eldType.FLAG_FEATURE_F ELD;
    }

    publ c boolean  sUnusedF eld() {
      return unusedF eld == UnusedFeatureF eldType.UNUSED_FEATURE_F ELD;
    }

    @Nullable
    publ c FeatureConf gurat on getFeatureConf gurat on() {
      return featureConf gurat on;
    }

    @Nullable
    publ c Thr ftFeatureNormal zat onType getFeatureNormal zat onType() {
      return featureNormal zat onType;
    }
  }

  pr vate stat c f nal Map<Str ng, Earlyb rdF eldConstant> NAME_TO_ D_MAP;
  pr vate stat c f nal Map< nteger, Earlyb rdF eldConstant>  D_TO_F ELD_MAP;
  stat c {
     mmutableMap.Bu lder<Str ng, Earlyb rdF eldConstant> na To dMapBu lder =
         mmutableMap.bu lder();
     mmutableMap.Bu lder< nteger, Earlyb rdF eldConstant>  dToF eldMapBu lder =
         mmutableMap.bu lder();
    Set<Str ng> f eldNa DupDetector = Sets.newHashSet();
    Set< nteger> f eld dDupDetector = Sets.newHashSet();
    for (Earlyb rdF eldConstant fc : Earlyb rdF eldConstant.values()) {
       f (f eldNa DupDetector.conta ns(fc.getF eldNa ())) {
        throw new  llegalStateExcept on("detected f elds shar ng f eld na : " + fc.getF eldNa ());
      }
       f (f eld dDupDetector.conta ns(fc.getF eld d())) {
        throw new  llegalStateExcept on("detected f elds shar ng f eld  d: " + fc.getF eld d());
      }

      f eldNa DupDetector.add(fc.getF eldNa ());
      f eld dDupDetector.add(fc.getF eld d());
      na To dMapBu lder.put(fc.getF eldNa (), fc);
       dToF eldMapBu lder.put(fc.getF eld d(), fc);
    }
    NAME_TO_ D_MAP = na To dMapBu lder.bu ld();
     D_TO_F ELD_MAP =  dToF eldMapBu lder.bu ld();
  }

  // T  def ne t  l st of boolean features, but t  na  does not have "flag"  ns de.  T 
  // def n  on  s only for double c ck ng purpose to prevent code change m stakes.  T  sett ng
  // of t  flag feature  s based on FlagFeatureF eldType.FLAG_FEATURE_F ELD.
  publ c stat c f nal Set<Earlyb rdF eldConstants.Earlyb rdF eldConstant> EXTRA_FLAG_F ELDS =
      Sets.newHashSet(Earlyb rdF eldConstants.Earlyb rdF eldConstant. S_SENS T VE_CONTENT);
  publ c stat c f nal Str ng FLAG_STR NG = "flag";

  pr vate stat c f nal L st<Earlyb rdF eldConstant> FLAG_FEATURE_F ELDS;
  stat c {
     mmutableL st.Bu lder<Earlyb rdF eldConstant> flagF eldBu lder =  mmutableL st.bu lder();
    for (Earlyb rdF eldConstant fc : Earlyb rdF eldConstant.values()) {
       f (fc.getFlagFeatureF eld() == FlagFeatureF eldType.FLAG_FEATURE_F ELD
          && !fc. sUnusedF eld()) {
        flagF eldBu lder.add(fc);
      }
    }
    FLAG_FEATURE_F ELDS = flagF eldBu lder.bu ld();
  }

  /**
   * Get all t  flag features  an ng that t y are boolean features w h only 1 b   n t  packed
   * feature encod ng.
   */
  publ c stat c Collect on<Earlyb rdF eldConstant> getFlagFeatureF elds() {
    return FLAG_FEATURE_F ELDS;
  }

  /**
   * Get t  Earlyb rdF eldConstant for t  spec f ed f eld.
   */
  publ c stat c Earlyb rdF eldConstant getF eldConstant(Str ng f eldNa ) {
    Earlyb rdF eldConstant f eld = NAME_TO_ D_MAP.get(f eldNa );
     f (f eld == null) {
      throw new  llegalArgu ntExcept on("Unknown f eld: " + f eldNa );
    }
    return f eld;
  }

  /**
   * Get t  Earlyb rdF eldConstant for t  spec f ed f eld.
   */
  publ c stat c Earlyb rdF eldConstant getF eldConstant( nt f eld d) {
    Earlyb rdF eldConstant f eld =  D_TO_F ELD_MAP.get(f eld d);
     f (f eld == null) {
      throw new  llegalArgu ntExcept on("Unknown f eld: " + f eld d);
    }
    return f eld;
  }

  /**
   * Determ nes  f t re's a f eld w h t  g ven  D.
   */
  publ c stat c boolean hasF eldConstant( nt f eld d) {
    return  D_TO_F ELD_MAP.keySet().conta ns(f eld d);
  }

  @Overr de
  publ c f nal  nt getF eld D(Str ng f eldNa ) {
    return getF eldConstant(f eldNa ).getF eld d();
  }

  publ c stat c f nal Str ng formatGeoType(Thr ftGeoLocat onS ce s ce) {
    return "__geo_locat on_type_" + s ce.na ().toLo rCase();
  }
}
