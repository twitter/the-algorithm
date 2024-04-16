na space java com.tw ter.follow_recom ndat ons.thr ftjava
#@na space scala com.tw ter.follow_recom ndat ons.thr ftscala
#@na space strato com.tw ter.follow_recom ndat on

// T se are broken  nto t  r own un on
// because   can have features that are
// complex flavors of t se (such as Seq)
un on Pr m  veFeatureValue {
    1:  32  ntValue
    2:  64 longValue
    3: str ng strValue
    4: bool boolValue
}

un on FeatureValue {
    1: Pr m  veFeatureValue pr m  veValue
}

struct DebugParams {
    1: opt onal map<str ng, FeatureValue> featureOverr des
    2: opt onal  64 random zat onSeed
    3: opt onal bool  ncludeDebug nfo nResults
    4: opt onal bool doNotLog
}

enum DebugCand dateS ce dent f er {
  UTT_ NTERESTS_RELATED_USERS_SOURCE = 0
  UTT_PRODUCER_EXPANS ON_SOURCE = 1
  UTT_SEED_ACCOUNT_SOURCE = 2
  BYF_USER_FOLLOW_CLUSTER_S MS_SOURCE = 3
  BYF_USER_FOLLOW_CLUSTER_SOURCE = 4
  USER_FOLLOW_CLUSTER_SOURCE = 5
  RECENT_SEARCH_BASED_SOURCE = 6
  PEOPLE_ACT V TY_RECENT_ENGAGEMENT_SOURCE = 7
  PEOPLE_ACT V TY_RECENT_ENGAGEMENT_S MS_SOURCE = 8,
  REVERSE_PHONE_BOOK_SOURCE = 9,
  REVERSE_EMA L_BOOK_SOURCE = 10,
  S MS_DEBUG_STORE = 11,
  UTT_PRODUCER_ONL NE_MBCG_SOURCE = 12,
  BONUS_FOLLOW_COND T ONAL_ENGAGEMENT_STORE = 13,
  // 14 (BONUS_FOLLOW_PM _STORE) was deleted as  's not used anymore
  FOLLOW2VEC_NEAREST_NE GHBORS_STORE = 15,
  OFFL NE_STP = 16,
  OFFL NE_STP_B G = 17,
  OFFL NE_MUTUAL_FOLLOW_EXPANS ON = 18,
  REPEATED_PROF LE_V S TS = 19,
  T ME_DECAY_FOLLOW2VEC_NEAREST_NE GHBORS_STORE = 20,
  L NEAR_REGRESS ON_FOLLOW2VEC_NEAREST_NE GHBORS_STORE = 21,
  REAL_GRAPH_EXPANS ON_SOURCE = 22,
  RELATABLE_ACCOUNTS_BY_ NTEREST = 23,
  EMA L_TWEET_CL CK = 24,
  GOOD_TWEET_CL CK_ENGAGEMENTS = 25,
  ENGAGED_FOLLOWER_RAT O = 26,
  TWEET_SHARE_ENGAGEMENTS = 27,
  BULK_FR END_FOLLOWS = 28,
  REAL_GRAPH_OON_V2_SOURCE = 30,
  CROWD_SEARCH_ACCOUNTS = 31,
  POP_GEOHASH = 32,
  POP_COUNTRY = 33,
  POP_COUNTRY_BACKF LL = 34,
  TWEET_SHARER_TO_SHARE_REC P ENT_ENGAGEMENTS = 35,
  TWEET_AUTHOR_TO_SHARE_REC P ENT_ENGAGEMENTS = 36,
  BULK_FR END_FOLLOWS_NEW_USER = 37,
  ONL NE_STP_EPSCORER = 38,
  ORGAN C_FOLLOW_ACCOUNTS = 39,
  NUX_LO_H STORY = 40,
  TRAFF C_ATTR BUT ON_ACCOUNTS = 41,
  ONL NE_STP_RAW_ADDRESS_BOOK = 42,
  POP_GEOHASH_QUAL TY_FOLLOW = 43,
  NOT F CAT ON_ENGAGEMENT = 44,
  EFR_BY_WORLDW DE_P CTURE_PRODUCER = 45,
  POP_GEOHASH_REAL_GRAPH = 46,
}
