package com.tw ter.search.common.relevance.features;

 mport java.ut l.Map;
 mport java.ut l.Set;
 mport javax.annotat on.Nullable;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect. mmutableMap;
 mport com.google.common.collect. mmutableSet;

 mport com.tw ter.search.common.encod ng.features. ntNormal zer;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants;

 mport stat c com.tw ter.search.common.relevance.features. ntNormal zers.BOOLEAN_NORMAL ZER;
 mport stat c com.tw ter.search.common.relevance.features. ntNormal zers.LEGACY_NORMAL ZER;
 mport stat c com.tw ter.search.common.relevance.features. ntNormal zers.PARUS_SCORE_NORMAL ZER;
 mport stat c com.tw ter.search.common.relevance.features. ntNormal zers.SMART_ NTEGER_NORMAL ZER;
 mport stat c com.tw ter.search.common.relevance.features. ntNormal zers.T MESTAMP_SEC_TO_HR_NORMAL ZER;
 mport stat c com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;

/**
 * An enum to represent all dynam c/realt   feature types   can update  n t  S gnal  ngester.
 *   prov des  nformat on for t  r normal zat on and t  r correspond ng earlyb rd feature f elds
 * and prov des ut ls both producer (S gnal  ngester) and consu r (Earlyb rd) s de.
 *
 */
publ c enum T etFeatureType {
  RETWEET                         (true,  0,  LEGACY_NORMAL ZER,
      Earlyb rdF eldConstant.RETWEET_COUNT),
  REPLY                           (true,  1,  LEGACY_NORMAL ZER,
      Earlyb rdF eldConstant.REPLY_COUNT),
  FAVOR TE                        (true,  4,  LEGACY_NORMAL ZER,
      Earlyb rdF eldConstant.FAVOR TE_COUNT),
  PARUS_SCORE                     (false, 3,  PARUS_SCORE_NORMAL ZER,
      Earlyb rdF eldConstant.PARUS_SCORE),
  EMBEDS_ MP_COUNT                (true,  10, LEGACY_NORMAL ZER,
      Earlyb rdF eldConstant.EMBEDS_ MPRESS ON_COUNT),
  EMBEDS_URL_COUNT                (true,  11, LEGACY_NORMAL ZER,
      Earlyb rdF eldConstant.EMBEDS_URL_COUNT),
  V DEO_V EW                      (false, 12, LEGACY_NORMAL ZER,
      Earlyb rdF eldConstant.V DEO_V EW_COUNT),
  // v2 engage nt counters, t y w ll eventually replace v1 counters above
  RETWEET_V2                      (true,  13, SMART_ NTEGER_NORMAL ZER,
      Earlyb rdF eldConstant.RETWEET_COUNT_V2),
  REPLY_V2                        (true,  14, SMART_ NTEGER_NORMAL ZER,
      Earlyb rdF eldConstant.REPLY_COUNT_V2),
  FAVOR TE_V2                     (true,  15, SMART_ NTEGER_NORMAL ZER,
      Earlyb rdF eldConstant.FAVOR TE_COUNT_V2),
  EMBEDS_ MP_COUNT_V2             (true,  16, SMART_ NTEGER_NORMAL ZER,
      Earlyb rdF eldConstant.EMBEDS_ MPRESS ON_COUNT_V2),
  EMBEDS_URL_COUNT_V2             (true,  17, SMART_ NTEGER_NORMAL ZER,
      Earlyb rdF eldConstant.EMBEDS_URL_COUNT_V2),
  V DEO_V EW_V2                   (false, 18, SMART_ NTEGER_NORMAL ZER,
      Earlyb rdF eldConstant.V DEO_V EW_COUNT_V2),
  // ot r new  ems
  QUOTE                           (true,  19, SMART_ NTEGER_NORMAL ZER,
      Earlyb rdF eldConstant.QUOTE_COUNT),
  //   ghted engage nt counters
  WE GHTED_RETWEET                (true,  20, SMART_ NTEGER_NORMAL ZER,
      Earlyb rdF eldConstant.WE GHTED_RETWEET_COUNT),
  WE GHTED_REPLY                  (true,  21, SMART_ NTEGER_NORMAL ZER,
      Earlyb rdF eldConstant.WE GHTED_REPLY_COUNT),
  WE GHTED_FAVOR TE               (true,  22, SMART_ NTEGER_NORMAL ZER,
      Earlyb rdF eldConstant.WE GHTED_FAVOR TE_COUNT),
  WE GHTED_QUOTE                  (true,  23, SMART_ NTEGER_NORMAL ZER,
      Earlyb rdF eldConstant.WE GHTED_QUOTE_COUNT),

  // t et-level safety labels
  LABEL_ABUS VE                   (false, 24, BOOLEAN_NORMAL ZER,
      Earlyb rdF eldConstant.LABEL_ABUS VE_FLAG),
  LABEL_ABUS VE_H _RCL            (false, 25, BOOLEAN_NORMAL ZER,
      Earlyb rdF eldConstant.LABEL_ABUS VE_H _RCL_FLAG),
  LABEL_DUP_CONTENT               (false, 26, BOOLEAN_NORMAL ZER,
      Earlyb rdF eldConstant.LABEL_DUP_CONTENT_FLAG),
  LABEL_NSFW_H _PRC               (false, 27, BOOLEAN_NORMAL ZER,
      Earlyb rdF eldConstant.LABEL_NSFW_H _PRC_FLAG),
  LABEL_NSFW_H _RCL               (false, 28, BOOLEAN_NORMAL ZER,
      Earlyb rdF eldConstant.LABEL_NSFW_H _RCL_FLAG),
  LABEL_SPAM                      (false, 29, BOOLEAN_NORMAL ZER,
      Earlyb rdF eldConstant.LABEL_SPAM_FLAG),
  LABEL_SPAM_H _RCL               (false, 30, BOOLEAN_NORMAL ZER,
      Earlyb rdF eldConstant.LABEL_SPAM_H _RCL_FLAG),

  PER SCOPE_EX STS                (false, 32, BOOLEAN_NORMAL ZER,
      Earlyb rdF eldConstant.PER SCOPE_EX STS),
  PER SCOPE_HAS_BEEN_FEATURED     (false, 33, BOOLEAN_NORMAL ZER,
      Earlyb rdF eldConstant.PER SCOPE_HAS_BEEN_FEATURED),
  PER SCOPE_ S_CURRENTLY_FEATURED (false, 34, BOOLEAN_NORMAL ZER,
      Earlyb rdF eldConstant.PER SCOPE_ S_CURRENTLY_FEATURED),
  PER SCOPE_ S_FROM_QUAL TY_SOURCE(false, 35, BOOLEAN_NORMAL ZER,
      Earlyb rdF eldConstant.PER SCOPE_ S_FROM_QUAL TY_SOURCE),
  PER SCOPE_ S_L VE               (false, 36, BOOLEAN_NORMAL ZER,
      Earlyb rdF eldConstant.PER SCOPE_ S_L VE),

  // decayed engage nt counters
  DECAYED_RETWEET                 (true,  37, SMART_ NTEGER_NORMAL ZER,
      Earlyb rdF eldConstant.DECAYED_RETWEET_COUNT),
  DECAYED_REPLY                   (true,  38, SMART_ NTEGER_NORMAL ZER,
      Earlyb rdF eldConstant.DECAYED_REPLY_COUNT),
  DECAYED_FAVOR TE                (true,  39, SMART_ NTEGER_NORMAL ZER,
      Earlyb rdF eldConstant.DECAYED_FAVOR TE_COUNT),
  DECAYED_QUOTE                   (true,  40, SMART_ NTEGER_NORMAL ZER,
      Earlyb rdF eldConstant.DECAYED_QUOTE_COUNT),

  // t  stamp of last engage nt types
  LAST_RETWEET_S NCE_CREAT ON_HR  (false, 41, T MESTAMP_SEC_TO_HR_NORMAL ZER,
      Earlyb rdF eldConstant.LAST_RETWEET_S NCE_CREAT ON_HRS),
  LAST_REPLY_S NCE_CREAT ON_HR    (false, 42, T MESTAMP_SEC_TO_HR_NORMAL ZER,
      Earlyb rdF eldConstant.LAST_REPLY_S NCE_CREAT ON_HRS),
  LAST_FAVOR TE_S NCE_CREAT ON_HR (false, 43, T MESTAMP_SEC_TO_HR_NORMAL ZER,
      Earlyb rdF eldConstant.LAST_FAVOR TE_S NCE_CREAT ON_HRS),
  LAST_QUOTE_S NCE_CREAT ON_HR    (false, 44, T MESTAMP_SEC_TO_HR_NORMAL ZER,
      Earlyb rdF eldConstant.LAST_QUOTE_S NCE_CREAT ON_HRS),

  // fake engage nt counters
  FAKE_RETWEET                    (true,  45, SMART_ NTEGER_NORMAL ZER,
      Earlyb rdF eldConstant.FAKE_RETWEET_COUNT),
  FAKE_REPLY                      (true,  46, SMART_ NTEGER_NORMAL ZER,
      Earlyb rdF eldConstant.FAKE_REPLY_COUNT),
  FAKE_FAVOR TE                   (true,  47, SMART_ NTEGER_NORMAL ZER,
      Earlyb rdF eldConstant.FAKE_FAVOR TE_COUNT),
  FAKE_QUOTE                      (true,  48, SMART_ NTEGER_NORMAL ZER,
      Earlyb rdF eldConstant.FAKE_QUOTE_COUNT),

  // bl nk engage nt counters
  BL NK_RETWEET                   (true,  49, SMART_ NTEGER_NORMAL ZER,
      Earlyb rdF eldConstant.BL NK_RETWEET_COUNT),
  BL NK_REPLY                     (true,  50, SMART_ NTEGER_NORMAL ZER,
      Earlyb rdF eldConstant.BL NK_REPLY_COUNT),
  BL NK_FAVOR TE                  (true,  51, SMART_ NTEGER_NORMAL ZER,
      Earlyb rdF eldConstant.BL NK_FAVOR TE_COUNT),
  BL NK_QUOTE                     (true,  52, SMART_ NTEGER_NORMAL ZER,
      Earlyb rdF eldConstant.BL NK_QUOTE_COUNT),

  /* sem colon  n a s ngle l ne to avo d pollut ng g  bla  */;

  pr vate stat c f nal Map<T etFeatureType, T etFeatureType> V2_COUNTER_MAP =
       mmutableMap.<T etFeatureType, T etFeatureType>bu lder()
          .put(RETWEET,          RETWEET_V2)
          .put(REPLY,            REPLY_V2)
          .put(FAVOR TE,         FAVOR TE_V2)
          .put(EMBEDS_ MP_COUNT, EMBEDS_ MP_COUNT_V2)
          .put(EMBEDS_URL_COUNT, EMBEDS_URL_COUNT_V2)
          .put(V DEO_V EW,       V DEO_V EW_V2)
      .bu ld();

  pr vate stat c f nal Map<T etFeatureType, T etFeatureType> WE GHTED_COUNTER_MAP =
       mmutableMap.<T etFeatureType, T etFeatureType>bu lder()
          .put(RETWEET,          WE GHTED_RETWEET)
          .put(REPLY,            WE GHTED_REPLY)
          .put(FAVOR TE,         WE GHTED_FAVOR TE)
          .put(QUOTE,            WE GHTED_QUOTE)
          .bu ld();

  pr vate stat c f nal Map<T etFeatureType, T etFeatureType> DECAYED_COUNTER_MAP =
       mmutableMap.<T etFeatureType, T etFeatureType>bu lder()
          .put(RETWEET,          DECAYED_RETWEET)
          .put(REPLY,            DECAYED_REPLY)
          .put(FAVOR TE,         DECAYED_FAVOR TE)
          .put(QUOTE,            DECAYED_QUOTE)
          .bu ld();

  pr vate stat c f nal Map<T etFeatureType, T etFeatureType> DECAYED_COUNTER_TO_ELAPSED_T ME =
       mmutableMap.<T etFeatureType, T etFeatureType>bu lder()
          .put(DECAYED_RETWEET,  LAST_RETWEET_S NCE_CREAT ON_HR)
          .put(DECAYED_REPLY,    LAST_REPLY_S NCE_CREAT ON_HR)
          .put(DECAYED_FAVOR TE, LAST_FAVOR TE_S NCE_CREAT ON_HR)
          .put(DECAYED_QUOTE,    LAST_QUOTE_S NCE_CREAT ON_HR)
          .bu ld();

  pr vate stat c f nal Set<T etFeatureType> DECAYED_FEATURES =
       mmutableSet.of(DECAYED_RETWEET, DECAYED_REPLY, DECAYED_FAVOR TE, DECAYED_QUOTE);

  pr vate stat c f nal Set<T etFeatureType> FAKE_ENGAGEMENT_FEATURES =
       mmutableSet.of(FAKE_RETWEET, FAKE_REPLY, FAKE_FAVOR TE, FAKE_QUOTE);

  pr vate stat c f nal Set<T etFeatureType> BL NK_ENGAGEMENT_FEATURES =
       mmutableSet.of(BL NK_RETWEET, BL NK_REPLY, BL NK_FAVOR TE, BL NK_QUOTE);

  @Nullable
  publ c T etFeatureType getV2Type() {
    return V2_COUNTER_MAP.get(t );
  }

  @Nullable
  publ c stat c T etFeatureType get  ghtedType(T etFeatureType type) {
    return WE GHTED_COUNTER_MAP.get(type);
  }

  @Nullable
  publ c stat c T etFeatureType getDecayedType(T etFeatureType type) {
    return DECAYED_COUNTER_MAP.get(type);
  }

  // W t r t  feature  s  ncre ntal or d rect value.
  pr vate f nal boolean  ncre ntal;

  // T  normal zer  s used to (1) normal ze t  output value  n DL ndexEventOutputBolt,
  // (2) c ck value change.
  pr vate f nal  ntNormal zer normal zer;

  // value for compos ng cac  key.   has to be un que and  n  ncreas ng order.
  pr vate f nal  nt type nt;

  pr vate f nal Earlyb rdF eldConstants.Earlyb rdF eldConstant earlyb rdF eld;

  pr vate f nal  ncre ntC cker  ncre ntC cker;

  /**
   * Construct ng an enum for a type. T  earlyb rdF eld can be null  f  's not prepared, t y
   * can be  re as placeholders but t y can't be outputted.
   * T  normal zer  s null for t  t  stamp features that do not requ re normal zat on
   */
  T etFeatureType(boolean  ncre ntal,
                    nt type nt,
                    ntNormal zer normal zer,
                   @Nullable Earlyb rdF eldConstant earlyb rdF eld) {
    t . ncre ntal =  ncre ntal;
    t .type nt = type nt;
    t .normal zer = normal zer;
    t .earlyb rdF eld = earlyb rdF eld;
    t . ncre ntC cker = new  ncre ntC cker(t );
  }

  publ c boolean  s ncre ntal() {
    return  ncre ntal;
  }

  publ c  ntNormal zer getNormal zer() {
    return normal zer;
  }

  publ c  nt getType nt() {
    return type nt;
  }

  publ c  nt normal ze(double value) {
    return normal zer.normal ze(value);
  }

  publ c  ncre ntC cker get ncre ntC cker() {
    return  ncre ntC cker;
  }

  publ c Earlyb rdF eldConstant getEarlyb rdF eld() {
    return Precond  ons.c ckNotNull(earlyb rdF eld);
  }

  publ c boolean hasEarlyb rdF eld() {
    return earlyb rdF eld != null;
  }

  publ c boolean  sDecayed() {
    return DECAYED_FEATURES.conta ns(t );
  }

  @Nullable
  publ c T etFeatureType getElapsedT  FeatureType() {
    return DECAYED_COUNTER_TO_ELAPSED_T ME.get(t );
  }

  publ c boolean  sFakeEngage nt() {
    return FAKE_ENGAGEMENT_FEATURES.conta ns(t );
  }

  publ c boolean  sBl nkEngage nt() {
    return BL NK_ENGAGEMENT_FEATURES.conta ns(t );
  }

  /**
   * C ck  f an  ncre nt  s el g ble for em t ng
   */
  publ c stat c class  ncre ntC cker {
    pr vate f nal  ntNormal zer normal zer;

    publ c  ncre ntC cker( ntNormal zer normal zer) {
      t .normal zer = normal zer;
    }

     ncre ntC cker(T etFeatureType type) {
      t (type.getNormal zer());
    }

    /**
     * C ck  f a value change  s el g ble for output
     */
    publ c boolean el g bleForEm ( nt oldValue,  nt newValue) {
      return normal zer.normal ze(oldValue) != normal zer.normal ze(newValue);
    }
  }
}
