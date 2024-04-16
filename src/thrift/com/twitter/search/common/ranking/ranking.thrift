na space java com.tw ter.search.common.rank ng.thr ftjava
#@na space scala com.tw ter.search.common.rank ng.thr ftscala
#@na space strato com.tw ter.search.common.rank ng
na space py gen.tw ter.search.common.rank ng.rank ng

struct Thr ftL nearFeatureRank ngParams {
  // values below t  w ll set t  score to t  m n mal one
  1: opt onal double m n = -1e+100
  // values above t  w ll set t  score to t  m n mal one
  2: opt onal double max = 1e+100
  3: opt onal double   ght = 0
}(pers sted='true')

struct Thr ftAgeDecayRank ngParams {
  // t  rate  n wh ch t  score of older t ets decreases
  1: opt onal double slope = 0.003
  // t  age,  n m nutes, w re t  age score of a t et  s half of t  latest t et
  2: opt onal double halfl fe = 360.0
  // t  m n mal age decay score a t et w ll have
  3: opt onal double base = 0.6
}(pers sted='true')

enum Thr ftScor ngFunct onType {
  L NEAR = 1,
  MODEL_BASED = 4,
  TENSORFLOW_BASED = 5,

  // deprecated
  TOPTWEETS = 2,
  EXPER MENTAL = 3,
}

// T  struct to def ne a class that  s to be dynam cally loaded  n earlyb rd for
// exper  ntat on.
struct Thr ftExper  ntClass {
  // t  fully qual f ed class na .
  1: requ red str ng na 
  // data s ce locat on (class/jar f le) for t  dynam c class on HDFS
  2: opt onal str ng locat on
  // para ters  n key-value pa rs for t  exper  ntal class
  3: opt onal map<str ng, double> params
}(pers sted='true')

// Deprecated!!
struct Thr ftQueryEngage ntParams {
  // Rate Boosts: g ven a rate (usually a small fract on), t  score w ll be mult pl ed by
  //   (1 + rate) ^ boost
  // 0  an no boost, negat ve numbers are dampens
  1: opt onal double ret etRateBoost = 0
  2: opt onal double replyRateBoost = 0
  3: opt onal double faveRateBoost = 0
}(pers sted='true')

struct Thr ftHostQual yParams {
  // Mult pl er appl ed to host score, for t ets that have l nks.
  // A mult pl er of 0  ans that t  boost  s not appl ed
  1: opt onal double mult pl er = 0.0

  // Do not apply t  mult pl er to hosts w h score above t  level.
  //  f 0, t  mult pl er w ll be appl ed to any host.
  2: opt onal double maxScoreToMod fy = 0.0

  // Do not apply t  mult pl er to hosts w h score below t  level.
  //  f 0, t  mult pl er w ll be appl ed to any host.
  3: opt onal double m nScoreToMod fy = 0.0

  //  f true, score mod f cat on w ll be appl ed to hosts that have unknown scores.
  // T  host-score used w ll be lo r than t  score of any known host.
  4: opt onal bool applyToUnknownHosts = 0
}(pers sted='true')

struct Thr ftCardRank ngParams {
  1: opt onal double hasCardBoost          = 1.0
  2: opt onal double doma nMatchBoost      = 1.0
  3: opt onal double authorMatchBoost      = 1.0
  4: opt onal double t leMatchBoost       = 1.0
  5: opt onal double descr pt onMatchBoost = 1.0
}(pers sted='true')

# T   ds are ass gned  n 'blocks'. For add ng a new f eld, f nd an unused  d  n t  appropr ate
# block. Be sure to  nt on expl c ly wh ch  ds have been removed so that t y are not used aga n.
struct Thr ftRank ngParams {
  1: opt onal Thr ftScor ngFunct onType type

  // Dynam cally loaded scorer and collector for qu ck exper  ntat on.
  40: opt onal Thr ftExper  ntClass expScorer
  41: opt onal Thr ftExper  ntClass expCollector

  //   must set   to a value that f s  nto a float: ot rw se
  // so  earlyb rd classes that convert   to float w ll  nterpret
  //   as Float.NEGAT VE_ NF N TY, and so  compar sons w ll fa l
  2: opt onal double m nScore = -1e+30

  10: opt onal Thr ftL nearFeatureRank ngParams parusScoreParams
  11: opt onal Thr ftL nearFeatureRank ngParams ret etCountParams
  12: opt onal Thr ftL nearFeatureRank ngParams replyCountParams
  15: opt onal Thr ftL nearFeatureRank ngParams reputat onParams
  16: opt onal Thr ftL nearFeatureRank ngParams luceneScoreParams
  18: opt onal Thr ftL nearFeatureRank ngParams textScoreParams
  19: opt onal Thr ftL nearFeatureRank ngParams urlParams
  20: opt onal Thr ftL nearFeatureRank ngParams  sReplyParams
  21: opt onal Thr ftL nearFeatureRank ngParams d rectFollowRet etCountParams
  22: opt onal Thr ftL nearFeatureRank ngParams trustedC rcleRet etCountParams
  23: opt onal Thr ftL nearFeatureRank ngParams favCountParams
  24: opt onal Thr ftL nearFeatureRank ngParams mult pleReplyCountParams
  27: opt onal Thr ftL nearFeatureRank ngParams embeds mpress onCountParams
  28: opt onal Thr ftL nearFeatureRank ngParams embedsUrlCountParams
  29: opt onal Thr ftL nearFeatureRank ngParams v deoV ewCountParams
  66: opt onal Thr ftL nearFeatureRank ngParams quotedCountParams

  // A map from MutableFeatureType to l near rank ng params
  25: opt onal map<byte, Thr ftL nearFeatureRank ngParams> offl neExper  ntalFeatureRank ngParams

  //  f m n/max for score or Thr ftL nearFeatureRank ngParams should always be
  // appl ed or only to non-follows, non-self, non-ver f ed
  26: opt onal bool applyF ltersAlways = 0

  // W t r to apply promot on/demot on at all for FeatureBasedScor ngFunct on
  70: opt onal bool applyBoosts = 1

  // U  language  s engl sh, t et language  s not
  30: opt onal double langEngl shU Boost = 0.3
  // t et language  s engl sh, U  language  s not
  31: opt onal double langEngl shT etBoost = 0.7
  // user language d ffers from t et language, and ne  r  s engl sh
  32: opt onal double langDefaultBoost = 0.1
  // user that produced t et  s marked as spam r by  tastore
  33: opt onal double spamUserBoost = 1.0
  // user that produced t et  s marked as nsfw by  tastore
  34: opt onal double nsfwUserBoost = 1.0
  // user that produced t et  s marked as bot (self s m lar y) by  tastore
  35: opt onal double botUserBoost = 1.0

  // An alternat ve way of us ng lucene score  n t  rank ng funct on.
  38: opt onal bool useLuceneScoreAsBoost = 0
  39: opt onal double maxLuceneScoreBoost = 1.2

  // Use user's consu d and produced languages for scor ng
  42: opt onal bool useUserLanguage nfo = 0

  // Boost (demot on)  f t  t et language  s not one of user's understandable languages,
  // nor  nterface language.
  43: opt onal double unknownLanguageBoost = 0.01

  // Use top c  ds for scor ng.
  // Deprecated  n SEARCH-8616.
  44: opt onal bool deprecated_useTop c DsBoost = 0
  // Para ters for top c  d scor ng.  See Top c DsBoostScorer (and  s test) for deta ls.
  46: opt onal double deprecated_maxTop c DsBoost = 3.0
  47: opt onal double deprecated_top c DsBoostExponent = 2.0;
  48: opt onal double deprecated_top c DsBoostSlope = 2.0;

  // H  Attr bute Demot on
  60: opt onal bool enableH Demot on = 0
  61: opt onal double noTextH Demot on = 1.0
  62: opt onal double urlOnlyH Demot on = 1.0
  63: opt onal double na OnlyH Demot on = 1.0
  64: opt onal double separateTextAndNa H Demot on = 1.0
  65: opt onal double separateTextAndUrlH Demot on = 1.0

  // mult pl cat ve score boost for results dee d offens ve
  100: opt onal double offens veBoost = 1
  // mult pl cat ve score boost for results  n t  searc r's soc al c rcle
  101: opt onal double  nTrustedC rcleBoost = 1
  // mult pl cat ve score dampen for results w h more than one hash tag
  102: opt onal double mult pleHashtagsOrTrendsBoost = 1
  // mult pl cat ve score boost for results  n t  searc r's d rect follows
  103: opt onal double  nD rectFollowBoost = 1
  // mult pl cat ve score boost for results that has trends
  104: opt onal double t etHasTrendBoost = 1
  //  s t et from ver f ed account?
  106: opt onal double t etFromVer f edAccountBoost = 1
  //  s t et authored by t  searc r? (boost  s  n add  on to soc al boost)
  107: opt onal double selfT etBoost = 1
  // mult pl cat ve score boost for a t et that has  mage url.
  108: opt onal double t etHas mageUrlBoost = 1
  // mult pl cat ve score boost for a t et that has v deo url.
  109: opt onal double t etHasV deoUrlBoost = 1
  // mult pl cat ve score boost for a t et that has news url.
  110: opt onal double t etHasNewsUrlBoost = 1
  //  s t et from a blue-ver f ed account?
  111: opt onal double t etFromBlueVer f edAccountBoost = 1 (personalDataType = 'UserVer f edFlag')

  // subtract ve penalty appl ed after boosts for out-of-network repl es.
  120: opt onal double outOfNetworkReplyPenalty = 10.0

  150: opt onal Thr ftQueryEngage ntParams deprecatedQueryEngage ntParams

  160: opt onal Thr ftHostQual yParams deprecatedHostQual yParams

  // age decay params for regular t ets
  203: opt onal Thr ftAgeDecayRank ngParams ageDecayParams

  // for card rank ng: map bet en card na  ord nal (def ned  n com.tw ter.search.common.constants.CardConstants)
  // to rank ng params
  400: opt onal map<byte, Thr ftCardRank ngParams> cardRank ngParams

  // A map from t et  Ds to t  score adjust nt for that t et. T se are score
  // adjust nts that  nclude one or more features that can depend on t  query
  // str ng. T se features aren't  ndexed by Earlyb rd, and so t  r total contr but on
  // to t  scor ng funct on  s passed  n d rectly as part of t  request.  f present,
  // t  score adjust nt for a t et  s d rectly added to t  l near component of t 
  // scor ng funct on. S nce t  s gnal can be made up of mult ple features, any
  // re  ght ng or comb nat on of t se features  s assu d to be done by t  caller
  // ( nce t re  s no need for a   ght para ter -- t    ghts of t  features
  //  ncluded  n t  s gnal have already been  ncorporated by t  caller).
  151: opt onal map< 64, double> querySpec f cScoreAdjust nts

  // A map from user  D to t  score adjust nt for t ets from that author.
  // T  f eld prov des a way for adjust ng t  t ets of a spec f c set of users w h a score
  // that  s not present  n t  Earlyb rd features but has to be passed from t  cl ents, such as
  // real graph   ghts or a comb nat on of mult ple features.
  // T  f eld should be used ma nly for exper  ntat on s nce    ncreases t  s ze of t  thr ft
  // requests.
  154: opt onal map< 64, double> authorSpec f cScoreAdjust nts

  // -------- Para ters for Thr ftScor ngFunct onType.MODEL_BASED --------
  // Selected models along w h t  r   ghts for t  l near comb nat on
  152: opt onal map<str ng, double> selectedModels
  153: opt onal bool useLog Score = false

  // -------- Para ters for Thr ftScor ngFunct onType.TENSORFLOW_BASED --------
  // Selected tensorflow model
  303: opt onal str ng selectedTensorflowModel

  // -------- Deprecated F elds --------
  //  D 303 has been used  n t  past. Resu  add  onal deprecated f elds from 304
  105: opt onal double deprecatedT etHasTrend nTrend ngQueryBoost = 1
  200: opt onal double deprecatedAgeDecaySlope = 0.003
  201: opt onal double deprecatedAgeDecayHalfl fe = 360.0
  202: opt onal double deprecatedAgeDecayBase = 0.6
  204: opt onal Thr ftAgeDecayRank ngParams deprecatedAgeDecayForTrendsParams
  301: opt onal double deprecatedNa QueryConf dence = 0.0
  302: opt onal double deprecatedHashtagQueryConf dence = 0.0
  // W t r to use old-style engage nt features (normal zed by LogNormal zer)
  // or new ones (normal zed by S ngleBytePos  veFloatNormal zer)
  50: opt onal bool useGranularEngage ntFeatures = 0  // DEPRECATED!
}(pers sted='true')

// T  sort ng mode  s used by earlyb rd to retr eve t  top-n facets that
// are returned to blender
enum Thr ftFacetEarlyb rdSort ngMode {
  SORT_BY_S MPLE_COUNT = 0,
  SORT_BY_WE GHTED_COUNT = 1,
}

// T   s t  f nal sort order used by blender after all results from
// t  earlyb rds are  rged
enum Thr ftFacetF nalSortOrder {
  // us ng t  created_at date of t  f rst t et that conta ned t  facet
  SCORE = 0,
  S MPLE_COUNT = 1,
  WE GHTED_COUNT = 2,
  CREATED_AT = 3
}

struct Thr ftFacetRank ngOpt ons {
  // next ava lable f eld  D = 38

  // ======================================================================
  // EARLYB RD SETT NGS
  //
  // T se para ters pr mar ly affect how earlyb rd creates t  top-k
  // cand date l st to be re-ranked by blender
  // ======================================================================
  // Dynam cally loaded scorer and collector for qu ck exper  ntat on.
  26: opt onal Thr ftExper  ntClass expScorer
  27: opt onal Thr ftExper  ntClass expCollector

  //   should be less than or equal to reputat onParams.m n, and all
  // t epcreds bet en t  two get a score of 1.0.
  21: opt onal  32 m nT epcredF lterThreshold

  // t  max mum score a s ngle t et can contr bute to t    ghtedCount
  22: opt onal  32 maxScorePerT et

  15: opt onal Thr ftFacetEarlyb rdSort ngMode sort ngMode
  // T  number of top cand dates earlyb rd returns to blender
  16: opt onal  32 numCand datesFromEarlyb rd = 100

  // w n to early term nate for facet search, overr des t  sett ng  n Thr ftSearchQuery
  34: opt onal  32 maxH sToProcess = 1000

  // for ant -gam ng   want to l m  t  max mum amount of h s t  sa  user can
  // contr bute.  Set to -1 to d sable t  ant -gam ng f lter. Overr des t  sett ng  n
  // Thr ftSearchQuery
  35: opt onal  32 maxH sPerUser = 3

  //  f t  t epcred of t  user  s b gger than t  value   w ll not be excluded
  // by t  ant -gam ng f lter. Overr des t  sett ng  n Thr ftSearchQuery
  36: opt onal  32 maxT epcredForAnt Gam ng = 65

  // t se sett ngs affect how earlyb rd computes t    ghtedCount
   2: opt onal Thr ftL nearFeatureRank ngParams parusScoreParams
   3: opt onal Thr ftL nearFeatureRank ngParams reputat onParams
  17: opt onal Thr ftL nearFeatureRank ngParams favor esParams
  33: opt onal Thr ftL nearFeatureRank ngParams repl esParams
  37: opt onal map<byte, Thr ftL nearFeatureRank ngParams> rank ngExpScoreParams

  // penalty counter sett ngs
  6: opt onal  32 offens veT etPenalty  // set to -1 to d sable t  offens ve f lter
  7: opt onal  32 ant gam ngPenalty // set to -1 to d sable ant gam ng f lter ng
  //   ght of penalty counts from all t ets conta n ng a facet, not just t  t ets
  // match ng t  query
  9: opt onal double query ndependentPenalty  ght  // set to 0 to not use query  ndependent penalty   ghts
  // penalty for keyword stuff ng
  60: opt onal  32 mult pleHashtagsOrTrendsPenalty

  // Language related boosts, s m lar to those  n relevance rank ng opt ons. By default t y are
  // all 1.0 (no-boost).
  // W n t  user language  s engl sh, facet language  s not
  11: opt onal double langEngl shU Boost = 1.0
  // W n t  facet language  s engl sh, user language  s not
  12: opt onal double langEngl shFacetBoost = 1.0
  // W n t  user language d ffers from facet/t et language, and ne  r  s engl sh
  13: opt onal double langDefaultBoost = 1.0

  // ======================================================================
  // BLENDER SETT NGS
  //
  // Sett ngs for t  facet relevance scor ng happen ng  n blender
  // ======================================================================

  // T  block of para ters are only used  n t  FacetsFutureManager.
  // l m s to d scard facets
  //  f a facet has a h g r penalty count,   w ll not be returned
  5: opt onal  32 maxPenaltyCount
  //  f a facet has a lo r s mple count,   w ll not be returned
  28: opt onal  32 m nS mpleCount
  //  f a facet has a lo r   ghted count,   w ll not be returned
  8: opt onal  32 m nCount
  // t  max mum allo d value for offens veCount/facetCount a facet can have  n order to be returned
  10: opt onal double maxPenaltyCountRat o
  //  f set to true, t n facets w h offens ve d splay t ets are excluded from t  resultset
  29: opt onal bool excludePoss blySens  veFacets
  //  f set to true, t n only facets that have a d splay t et  n t  r Thr ftFacetCount tadata object
  // w ll be returned to t  caller
  30: opt onal bool onlyReturnFacetsW hD splayT et

  // para ters for scor ng force- nserted  d a  ems
  // Please c ck FacetReRanker.java computeScoreFor nserted() for t  r usage.
  38: opt onal double force nsertedBackgroundExp = 0.3
  39: opt onal double force nsertedM nBackgroundCount = 2
  40: opt onal double force nsertedMult pl er = 0.01

  // -----------------------------------------------------
  //   ghts for t  facet rank ng formula
  18: opt onal double s mpleCount  ght_DEPRECATED
  19: opt onal double   ghtedCount  ght_DEPRECATED
  20: opt onal double backgroundModelBoost_DEPRECATED

  // -----------------------------------------------------
  // Follow ng para ters are used  n t  FacetsReRanker
  // age decay params
  14: opt onal Thr ftAgeDecayRank ngParams ageDecayParams

  // used  n t  facets reranker
  23: opt onal double maxNormBoost = 5.0
  24: opt onal double globalCountExponent = 3.0
  25: opt onal double s mpleCountExponent = 3.0

  31: opt onal Thr ftFacetF nalSortOrder f nalSortOrder

  // Run facets search as  f t y happen at t  spec f c t   (ms s nce epoch).
  32: opt onal  64 fakeCurrentT  Ms  // not really used anyw re, remove?
}(pers sted='true')
