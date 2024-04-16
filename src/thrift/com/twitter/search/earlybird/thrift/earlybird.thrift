na space java com.tw ter.search.earlyb rd.thr ft
#@na space scala com.tw ter.search.earlyb rd.thr ftscala
#@na space strato com.tw ter.search.earlyb rd
na space py gen.tw ter.search.earlyb rd

 nclude "com/tw ter/ads/adserver/adserver_common.thr ft"
 nclude "com/tw ter/search/common/cach ng/cach ng.thr ft"
 nclude "com/tw ter/search/common/constants/query.thr ft"
 nclude "com/tw ter/search/common/constants/search_language.thr ft"
 nclude "com/tw ter/search/common/conversat on/conversat on.thr ft"
 nclude "com/tw ter/search/common/features/features.thr ft"
 nclude "com/tw ter/search/common/ ndex ng/status.thr ft"
 nclude "com/tw ter/search/common/query/search.thr ft"
 nclude "com/tw ter/search/common/rank ng/rank ng.thr ft"
 nclude "com/tw ter/search/common/results/expans ons.thr ft"
 nclude "com/tw ter/search/common/results/h ghl ght.thr ft"
 nclude "com/tw ter/search/common/results/h _attr but on.thr ft"
 nclude "com/tw ter/search/common/results/h s.thr ft"
 nclude "com/tw ter/search/common/results/soc al.thr ft"
 nclude "com/tw ter/serv ce/sp derduck/gen/ tadata_store.thr ft"
 nclude "com/tw ter/t etyp e/deprecated.thr ft"
 nclude "com/tw ter/t etyp e/t et.thr ft"
 nclude "com/tw ter/esc rb rd/t et_annotat on.thr ft"

enum Thr ftSearchRank ngMode {
  // good old realt   search mode
  RECENCY = 0,
  // new super fancy relevance rank ng
  RELEVANCE = 1,
  DEPRECATED_D SCOVERY = 2,
  // top t ets rank ng mode
  TOPTWEETS = 3,
  // results from accounts follo d by t  searc r
  FOLLOWS = 4,

  PLACE_HOLDER5 = 5,
  PLACE_HOLDER6 = 6,
}

enum Thr ftSearchResultType {
  //  's a t  -ordered result.
  RECENCY = 0,
  //  's a h ghly relevant t et (aka top t et).
  RELEVANCE = 1,
  // top t et result type
  POPULAR = 2,
  // promoted t ets (ads)
  PROMOTED = 3,
  // relevance-ordered (as opposed to t  -ordered) t ets generated from a var ety of cand dates
  RELEVANCE_ORDERED = 4,

  PLACE_HOLDER5 = 5,
  PLACE_HOLDER6 = 6,
}

enum Thr ftSoc alF lterType {
  // f lter only users that t  searc r  s d rectly follow ng.
  FOLLOWS = 0,
  // f lter only users that are  n searc r's soc al c rcle of trust.
  TRUSTED = 1,
  // f lter both follows and trusted.
  ALL = 2,

  PLACE_HOLDER3 = 3,
  PLACE_HOLDER4 = 4,

}

enum Thr ftT etS ce {
  ///// enums set by Earlyb rd
  REALT ME_CLUSTER = 1,
  FULL_ARCH VE_CLUSTER = 2,
  REALT ME_PROTECTED_CLUSTER = 4,

  ///// enums set  ns de Blender
  ADSERVER = 0,
  // from top news search, only used  n un versal search
  TOP_NEWS = 3,
  // spec al t ets  ncluded just for EventParrot.
  FORCE_ NCLUDED = 5,
  // from Content Recom nder
  // from top c to T et path
  CONTENT_RECS_TOP C_TO_TWEET = 6,
  // used for hydrat ng Q G T ets (go/q g)
  Q G = 8,
  // used for TOPTWEETS rank ng mode
  TOP_TWEET = 9,
  // used for exper  ntal cand date s ces
  EXPER MENTAL = 7,
  // from Scanr serv ce
  SCANR = 10,

  PLACE_HOLDER11 = 11,
  PLACE_HOLDER12 = 12
}

enum Na dEnt yS ce {
  TEXT = 0,
  URL = 1,

  PLACE_HOLDER2 = 2,
  PLACE_HOLDER3 = 3,
  PLACE_HOLDER4 = 4,
}

enum Exper  ntCluster {
  EXP0 = 0, // Send requests to t  earlyb rd-realt  -exp0 cluster
  PLACE_HOLDER1 = 1,
  PLACE_HOLDER2 = 2,
}

enum Aud oSpaceState {
   RUNN NG = 0,
   ENDED = 1,

   PLACE_HOLDER2 = 2,
   PLACE_HOLDER3 = 3,
   PLACE_HOLDER4 = 4,
   PLACE_HOLDER5 = 5,
}

// Conta ns all scor ng and relevance-f lter ng related controls and opt ons for Earlyb rd.
struct Thr ftSearchRelevanceOpt ons {
  // Next ava lable f eld  D: 31 and note that 45 and 50 have been used already

  2: opt onal bool f lterDups = 0         // f lter out dupl cate search results
  26: opt onal bool keepDupW hH g rScore = 1 // keep t  dupl cate t et w h t  h g r score

  3: opt onal bool prox m yScor ng = 0   // w t r to do prox m y scor ng or not
  4: opt onal  32 maxConsecut veSa User  // f lter consecut ve results from t  sa  user
  5: opt onal rank ng.Thr ftRank ngParams rank ngParams  // composed by blender
  // deprecated  n favor of t  maxH sToProcess  n CollectorParams
  6: opt onal  32 maxH sToProcess // w n to early-term nate for relevance
  7: opt onal str ng exper  ntNa       // what relevance exper  nt  s runn ng
  8: opt onal str ng exper  ntBucket    // what bucket t  user  s  n; DDG defaults to hard-coded 'control'
  9: opt onal bool  nterpretS nce d = 1   // w t r to  nterpret s nce_ d operator

  24: opt onal  32 maxH sPerUser // Overr des Thr ftSearchQuery.maxH sPerUser

  // only used by d scovery for capp ng d rect follow t ets
  10: opt onal  32 maxConsecut veD rectFollows

  // Note - t  orderByRelevance flag  s cr  cal to understand ng how  rg ng
  // and tr mm ng works  n relevance mode  n t  search root.
  //
  // W n orderByRelevance  s true, results are tr m d  n score-order.  T   ans t 
  // cl ent w ll get t  top results from (maxH sToProcess * numHashPart  ons) h s,
  // ordered by score.
  //
  // W n orderByRelevance  s false, results are tr m d  n  d-order.  T   ans t 
  // cl ent w ll get t  top results from an approx mat on of maxH sToProcess h s
  // (across t  ent re corpus).  T se results ordered by  D.
  14: opt onal bool orderByRelevance = 0

  // Max blend ng count for results returned due to from:user rewr es
  16: opt onal  32 maxUserBlendCount

  // T    ght for prox m y phrases generated wh le translat ng t  ser al zed query to t 
  // lucene query.
  19: opt onal double prox m yPhrase  ght = 1.0
  20: opt onal  32 prox m yPhraseSlop = 255

  // Overr de t    ghts of searchable f elds.
  // Negat ve   ght  ans t  t  f eld  s not enabled for search by default,
  // but  f    s (e.g., by annotat on), t  absolute value of t    ght shall be
  // used ( f t  annotat on does not spec fy a   ght).
  21: opt onal map<str ng, double> f eld  ghtMapOverr de

  // w t r d sable t  coord nat on  n t  rewr ten d sjunct on query, term query and phrase query
  // t  deta ls can be found  n LuceneV s or
  22: opt onal bool deprecated_d sableCoord = 0

  // Root only. Returns all results seen by root to t  cl ent w hout tr mm ng
  //  f set to true.
  23: opt onal bool returnAllResults

  // DEPRECATED: All v2 counters w ll be used expl c ly  n t  scor ng funct on and
  // returned  n t  r own f eld ( n e  r  tadata or feature map  n response).
  25: opt onal bool useEngage ntCountersV2 = 0

  // -------- PERSONAL ZAT ON-RELATED RELEVANCE OPT ONS --------
  // Take spec al care w h t se opt ons w n reason ng about cach ng.

  // Deprecated  n SEARCH-8616.
  45: opt onal map< 32, double> deprecated_top c D  ghts

  // Collect h  attr but on on quer es and l kedByUser DF lter64-enhanced quer es to
  // get l kedByUser ds l st  n  tadata f eld.
  // NOTE: t  flag has no affect on fromUser DF lter64.
  50: opt onal bool collectF eldH Attr but ons = 0

  // W t r to collect all h s regardless of t  r score w h RelevanceAllCollector.
  27: opt onal bool useRelevanceAllCollector = 0

  // Overr de features of spec f c t ets before t  t ets are scored.  
  28: opt onal map< 64, features.Thr ftSearchResultFeatures> perT etFeaturesOverr de

  // Overr de features of all t ets from spec f c users before t  t ets are scored. 
  29: opt onal map< 64, features.Thr ftSearchResultFeatures> perUserFeaturesOverr de

  // Overr de features of all t ets before t  t ets are scored.
  30: opt onal features.Thr ftSearchResultFeatures globalFeaturesOverr de
}(pers sted='true')

// Facets types that may have d fferent rank ng para ters.
enum Thr ftFacetType {
  DEFAULT = 0,
  MENT ONS_FACET = 1,
  HASHTAGS_FACET = 2,
  // Deprecated  n SEARCH-13708
  DEPRECATED_NAMED_ENT T ES_FACET = 3,
  STOCKS_FACET = 4,
  V DEOS_FACET = 5,
   MAGES_FACET = 6,
  NEWS_FACET = 7,
  LANGUAGES_FACET = 8,
  SOURCES_FACET = 9,
  TW MG_FACET = 10,
  FROM_USER_ D_FACET = 11,
  DEPRECATED_TOP C_ DS_FACET = 12,
  RETWEETS_FACET = 13,
  L NKS_FACET = 14,

  PLACE_HOLDER15 = 15,
  PLACE_HOLDER16 = 16,
}

struct Thr ftSearchDebugOpt ons {
  // Make earlyb rd only score and return t ets (spec f ed by t et  d)  re, regardless
  //  f t y have a h  for t  current query or not.
  1: opt onal set< 64> status ds;

  // Assorted structures to pass  n debug opt ons.
  2: opt onal map<str ng, str ng> str ngMap;
  3: opt onal map<str ng, double> valueMap;
  4: opt onal l st<double> valueL st;
}(pers sted='true')

// T se opt ons control what  tadata w ll be returned by earlyb rd for each search result
//  n t  Thr ftSearchResult tadata struct.  T se opt ons are currently mostly supported by
// AbstractRelevanceCollector and part ally  n SearchResultsCollector.  Most are true by default to
// preserve backwards compat b l y, but can be d sabled as necessary to opt m ze searc s return ng
// many results (such as d scover).
struct Thr ftSearchResult tadataOpt ons {
  //  f true, f lls  n t  t etUrls f eld  n Thr ftSearchResult tadata.
  // Populated by AbstractRelevanceCollector.
  1: opt onal bool getT etUrls = 1

  //  f true, f lls  n t  resultLocat on f eld  n Thr ftSearchResult tadata.
  // Populated by AbstractRelevanceCollector.
  2: opt onal bool getResultLocat on = 1
  
  // Deprecated  n SEARCH-8616.
  3: opt onal bool deprecated_getTop c Ds = 1

  //  f true, f lls  n t  luceneScore f eld  n Thr ftSearchResult tadata.
  // Populated by L nearScor ngFunct on.
  4: opt onal bool getLuceneScore = 0

  // Deprecated but used to be for Offl ne feature values for stat c  ndex
  5: opt onal bool deprecated_getExpFeatureValues = 0

  //  f true, w ll om  all features der vable from packedFeatures, and set packedFeatures
  //  nstead.
  6: opt onal bool deprecated_usePackedFeatures = 0

  //  f true, f lls sharedStatus d. For repl es t   s t   n-reply-to status  d and for
  // ret ets t   s t  ret et s ce status  d.
  // Also f lls  n t  t   sRet et and  sReply flags.
  7: opt onal bool get nReplyToStatus d = 0

  //  f true, f lls referencedT etAuthor d. Also f lls  n t  t   sRet et and  sReply flags.
  8: opt onal bool getReferencedT etAuthor d = 0

  //  f true, f lls  d a b s (v deo/v ne/per scope/etc.)
  9: opt onal bool get d aB s = 0

  //  f true, w ll return all def ned features  n t  packed features.  T  flag does not cover
  // t  above def ned features.
  10: opt onal bool getAllFeatures = 0

  //  f true, w ll return all features as Thr ftSearchResultFeatures format.
  11: opt onal bool returnSearchResultFeatures = 0

  //  f t  cl ent cac s so  features sc mas, cl ent can  nd cate  s cac  sc mas through
  // t  f eld based on (vers on, c cksum).
  12: opt onal l st<features.Thr ftSearchFeatureSc maSpec f er> featureSc masAva lable nCl ent

  // Spec f c feature  Ds to return for recency requests. Populated  n SearchResultFeatures.
  // Values must be  Ds of CSF f elds from Earlyb rdF eldConstants.
  13: opt onal l st< 32> requestedFeature Ds

  //  f true, f lls  n t  na dEnt  es f eld  n Thr ftSearchResultExtra tadata
  14: opt onal bool getNa dEnt  es = 0

  //  f true, f lls  n t  ent yAnnotat ons f eld  n Thr ftSearchResultExtra tadata
  15: opt onal bool getEnt yAnnotat ons = 0

  //  f true, f lls  n t  fromUser d f eld  n t  Thr ftSearchResultExtra tadata
  16: opt onal bool getFromUser d = 0

  //  f true, f lls  n t  spaces f eld  n t  Thr ftSearchResultExtra tadata
  17: opt onal bool getSpaces = 0

  18: opt onal bool getExclus veConversat onAuthor d = 0
}(pers sted='true')


// Thr ftSearchQuery descr bes an earlyb rd search request, wh ch typ cally cons sts
// of t se parts:
//  - a query to retr eve h s
//  - relevance opt ons to score h s
//  - a collector to collect h s and process  nto search results
// Note that t  struct  s used  n both Thr ftBlenderRequest and Earlyb rdRequest.
// Most f elds are not set w n t  struct  s embedded  n Thr ftBlenderRequest, and
// are f lled  n by t  blender before send ng to earlyb rd.
struct Thr ftSearchQuery {
  // Next ava lable f eld  D: 42

  // -------- SECT ON ZERO: TH NGS USED ONLY BY THE BLENDER --------
  // See SEARCHQUAL-2398
  // T se f elds are used by t  blender and cl ents of t  blender, but not by earlyb rd.

  // blender use only
  // T  raw un-parsed user search query.
  6: opt onal str ng rawQuery(personalDataType = 'SearchQuery')

  // blender use only
  // Language of t  rawQuery.
  18: opt onal str ng queryLang(personalDataType = ' nferredLanguage')

  // blender use only
  // What page of results to return,  ndexed from 1.
  7: opt onal  32 page = 1

  // blender use only
  // Number of results to sk p (for pag nat on).   ndexed from 0.
  2: opt onal  32 deprecated_resultOffset = 0


  // -------- SECT ON ONE: RETR EVAL OPT ONS --------
  // T se opt ons control t  query that w ll be used to retr eve docu nts / h s.

  // T  parsed query tree, ser al zed to a str ng.  Restr cts t  search results to
  // t ets match ng t  query.
  1: opt onal str ng ser al zedQuery(personalDataType = 'SearchQuery')

  // Restr cts t  search results to t ets hav ng t  m n mum t ep cred, out of 100.
  5: opt onal  32 m nT epCredF lter = -1

  // Restr cts t  search results to t ets from t se users.
  34: opt onal l st< 64> fromUser DF lter64(personalDataType = 'Pr vateAccountsFollow ng, Publ cAccountsFollow ng')
  // Restr cts t  search results to t ets l ked by t se users.
  40: opt onal l st< 64> l kedByUser DF lter64(personalDataType = 'Pr vateAccountsFollow ng, Publ cAccountsFollow ng')

  //  f searchStatus ds are present, earlyb rd w ll  gnore t  ser al zedQuery completely
  // and s mply score each of searchStatus ds, also bypass ng features l ke dupl cate
  // f lter ng and early term nat on.
  //  MPORTANT: t   ans that    s poss ble to get scores equal to Scor ngFunct on.SK P_H T,
  // for results sk pped by t  scor ng funct on.
  31: opt onal set< 64> searchStatus ds

  35: opt onal set< 64> deprecated_eventCluster dsF lter

  41: opt onal map<str ng, l st< 64>> na dD sjunct onMap

  // -------- SECT ON TWO: H T COLLECTOR OPT ONS --------
  // T se opt ons control what h s w ll be collected by t  h  collector.
  // W t r   want to collect and return per-f eld h  attr but ons  s set  n RelevanceOpt ons.
  // See SEARCH-2784
  // Number of results to return (after offset/page correct on).
  // T   s  gnored w n searchStatus ds  s set.
  3: requ red  32 numResults

  // Max mum number of h s to process by t  collector.
  // deprecated  n favor of t  maxH sToProcess  n CollectorParams
  4: opt onal  32 maxH sToProcess = 1000

  // Collect h  counts for t se t   per ods ( n m ll seconds).
  30: opt onal l st< 64> h CountBuckets

  //  f set, earlyb rd w ll also return t  facet labels of t  spec f ed facet f elds
  //  n result t ets.
  33: opt onal l st<str ng> facetF eldNa s

  // Opt ons controll ng wh ch search result  tadata  s returned.
  36: opt onal Thr ftSearchResult tadataOpt ons result tadataOpt ons

  // Collect on related Params
  38: opt onal search.CollectorParams collectorParams

  // W t r to collect conversat on  Ds
  39: opt onal bool collectConversat on d = 0

  // -------- SECT ON THREE: RELEVANCE OPT ONS --------
  // T se opt ons control relevance scor ng and ant -gam ng.

  // Rank ng mode (RECENCY  ans t  -ordered rank ng w h no relevance).
  8: opt onal Thr ftSearchRank ngMode rank ngMode = Thr ftSearchRank ngMode.RECENCY

  // Relevance scor ng opt ons.
  9: opt onal Thr ftSearchRelevanceOpt ons relevanceOpt ons

  // L m s t  number of h s that can be contr buted by t  sa  user, for ant -gam ng.
  // Set to -1 to d sable t  ant -gam ng f lter.  T   s  gnored w n searchStatus ds
  //  s set.
  11: opt onal  32 maxH sPerUser = 3

  // D sables ant -gam ng f lter c cks for any t ets that exceed t  t epcred.
  12: opt onal  32 maxT epcredForAnt Gam ng = 65

  // -------- PERSONAL ZAT ON-RELATED RELEVANCE OPT ONS --------
  // Take spec al care w h t se opt ons w n reason ng about cach ng.  All of t se
  // opt ons,  f set, w ll bypass t  cac  w h t  except on of u Lang wh ch  s t 
  // only form of personal zat on allo d for cach ng.

  // User  D of searc r.  T   s used for relevance, and w ll be used for retr eval
  // by t  protected t ets  ndex.   f set, query w ll not be cac d.
  20: opt onal  64 searc r d(personalDataType = 'User d')

  // Bloom f lter conta n ng trusted user  Ds.   f set, query w ll not be cac d.
  10: opt onal b nary trustedF lter(personalDataType = 'User d')

  // Bloom f lter conta n ng d rect follow user  Ds.   f set, query w ll not be cac d.
  16: opt onal b nary d rectFollowF lter(personalDataType = 'User d, Pr vateAccountsFollow ng, Publ cAccountsFollow ng')

  // U  language from t  searc r's prof le sett ngs.
  14: opt onal str ng u Lang(personalDataType = 'GeneralSett ngs')

  // Conf dence of t  understandab l y of d fferent languages for t  user.
  // u Lang f eld above  s treated as a userlang w h a conf dence of 1.0.
  28: opt onal map<search_language.Thr ftLanguage, double> userLangs(personalDataTypeKey = ' nferredLanguage')

  // An alternat ve to fromUser DF lter64 that rel es on t  relevance bloom f lters
  // for user f lter ng.  Not currently used  n product on.  Only supported for realt  
  // searc s.
  //  f set, earlyb rd expects both trustedF lter and d rectFollowF lter to also be set.
  17: opt onal Thr ftSoc alF lterType soc alF lterType

  // -------- SECT ON FOUR: DEBUG OPT ONS, FORGOTTEN FEATURES --------

  // Earlyb rd search debug opt ons.
  19: opt onal Thr ftSearchDebugOpt ons debugOpt ons

  // Overr des t  query t   for debugg ng.
  29: opt onal  64 t  stampMsecs = 0

  // Support for t  feature has been removed and t  f eld  s left for backwards compat b l y
  // (and to detect  mproper usage by cl ents w n    s set).
  25: opt onal l st<str ng> deprecated_ erat veQuer es

  // Spec f es a lucene query that w ll only be used  f ser al zedQuery  s not set,
  // for debugg ng.  Not currently used  n product on.
  27: opt onal str ng luceneQuery(personalDataType = 'SearchQuery')

  // T  f eld  s deprecated and  s not used by earlyb rds w n process ng t  query.
  21: opt onal  32 deprecated_m nDocsToProcess = 0
}(pers sted='true', hasPersonalData = 'true')


struct Thr ftFacetLabel {
  1: requ red str ng f eldNa 
  2: requ red str ng label
  // t  number of t  s t  facet has shown up  n t ets w h offens ve words.
  3: opt onal  32 offens veCount = 0

  // only f lled for TW MG facets
  4: opt onal str ng nat vePhotoUrl
}(pers sted='true')

struct Thr ftSearchResultGeoLocat on {
  1: opt onal double lat ude(personalDataType = 'GpsCoord nates')
  2: opt onal double long ude(personalDataType = 'GpsCoord nates')
  3: opt onal double d stanceKm
}(pers sted='true', hasPersonalData = 'true')

// Conta ns an expanded url and  d a type from t  URL facet f elds  n earlyb rd.
// Note: thr ft cop ed from status.thr ft w h unused f elds rena d.
struct Thr ftSearchResultUrl {
  // Next ava lable f eld  D: 6.  F elds 2-4 removed.

  // Note: t   s actually t  expanded url.  Rena  after deprecated f elds are removed.
  1: requ red str ng or g nalUrl

  //  d a type of t  url.
  5: opt onal  tadata_store. d aTypes  d aType
}(pers sted='true')

struct Thr ftSearchResultNa dEnt y {
  1: requ red str ng canon calNa 
  2: requ red str ng ent yType
  3: requ red Na dEnt yS ce s ce
}(pers sted='true')

struct Thr ftSearchResultAud oSpace {
  1: requ red str ng  d
  2: requ red Aud oSpaceState state
}(pers sted='true')

// Even more  tadata
struct Thr ftSearchResultExtra tadata {
  // Next ava lable f eld  D: 49

  1: opt onal double userLangScore
  2: opt onal bool hasD fferentLang
  3: opt onal bool hasEngl shT etAndD fferentU Lang
  4: opt onal bool hasEngl shU AndD fferentT etLang
  5: opt onal  32 quotedCount
  6: opt onal double querySpec f cScore
  7: opt onal bool hasQuote
  29: opt onal  64 quotedT et d
  30: opt onal  64 quotedUser d
  31: opt onal search_language.Thr ftLanguage cardLang
  8: opt onal  64 conversat on d
  9: opt onal bool  sSens  veContent
  10: opt onal bool hasMult ple d aFlag
  11: opt onal bool prof le sEggFlag
  12: opt onal bool  sUserNewFlag
  26: opt onal double authorSpec f cScore
  28: opt onal bool  sComposerS ceCa ra

  // temporary V2 engage nt counters, or g nal ones  n Thr ftSearchResult tadata has log()
  // appl ed on t m and t n converted to  nt  n Thr ft, wh ch  s effect vely a premature
  // d scret zat on.   doesn't affect t  scor ng  ns de Earlyb rd but for scor ng and ML tra n ng
  // outs de earlyb rd, t y  re bad. T se newly added ones stores a proper value of t se
  // counts. T  also prov des an eas er trans  on to v2 counter w n Earlyb rd  s eventually
  // ready to consu  t m from DL
  // See SEARCHQUAL-9536, SEARCH-11181
  18: opt onal  32 ret etCountV2
  19: opt onal  32 favCountV2
  20: opt onal  32 replyCountV2
  // T epcred   ghted vers on of var ous engage nt counts
  22: opt onal  32   ghtedRet etCount
  23: opt onal  32   ghtedReplyCount
  24: opt onal  32   ghtedFavCount
  25: opt onal  32   ghtedQuoteCount

  // 2 b s - 0, 1, 2, 3+
  13: opt onal  32 num nt ons
  14: opt onal  32 numHashtags

  // 1 byte - 256 poss ble languages
  15: opt onal  32 l nkLanguage
  // 6 b s - 64 poss ble values
  16: opt onal  32 prevUserT etEngage nt

  17: opt onal features.Thr ftSearchResultFeatures features

  //  f t  Thr ftSearchQuery.l kedByUser dF lter64 and Thr ftSearchRelevanceOpt ons.collectF eldH Attr but ons 
  // f elds are set, t n t  f eld w ll conta n t  l st of all users  n t  query that l ked t  t et.
  // Ot rw se, t  f eld  s not set.
  27: opt onal l st< 64> l kedByUser ds


  // Deprecated. See SEARCHQUAL-10321
  21: opt onal double dopam neNonPersonal zedScore

  32: opt onal l st<Thr ftSearchResultNa dEnt y> na dEnt  es
  33: opt onal l st<t et_annotat on.T etEnt yAnnotat on> ent yAnnotat ons

  //  alth model scores from HML
  34: opt onal double tox c yScore // (go/tox c y)
  35: opt onal double pBlockScore // (go/pblock)
  36: opt onal double exper  ntal althModelScore1
  37: opt onal double exper  ntal althModelScore2
  38: opt onal double exper  ntal althModelScore3
  39: opt onal double exper  ntal althModelScore4

  40: opt onal  64 d rectedAtUser d

  //  alth model scores from HML (cont.)
  41: opt onal double pSpam T etScore // (go/pspam t et)
  42: opt onal double pReportedT etScore // (go/preportedt et)
  43: opt onal double spam T etContentScore // (go/spam -t et-content)
  //    s populated by look ng up user table and    s only ava lable  n arch ve earlyb rds response
  44: opt onal bool  sUserProtected
  45: opt onal l st<Thr ftSearchResultAud oSpace> spaces

  46: opt onal  64 exclus veConversat onAuthor d
  47: opt onal str ng cardUr 
  48: opt onal bool fromBlueVer f edAccount(personalDataType = 'UserVer f edFlag')
}(pers sted='true')

// So  bas c  tadata about a search result.  Useful for re-sort ng, f lter ng, etc.
//
// NOTE: DO NOT ADD NEW F ELD!!
// Stop add ng new f elds to t  struct, all new f elds should go to
// Thr ftSearchResultExtra tadata (VM-1897), or t re w ll be performance  ssues  n product on.
struct Thr ftSearchResult tadata {
  // Next ava lable f eld  D: 86

  // -------- BAS C SCOR NG METADATA --------

  // W n resultType  s RECENCY most scor ng  tadata w ll not be ava lable.
  1: requ red Thr ftSearchResultType resultType

  // Relevance score computed for t  result.
  3: opt onal double score

  // True  f t  result was sk pped by t  scor ng funct on.  Only set w n t  collect-all
  // results collector was used -  n ot r cases sk pped results are not returned.
  // T  score w ll be Scor ngFunct on.SK P_H T w n sk pped  s true.
  43: opt onal bool sk pped

  // opt onally a Lucene-style explanat on for t  result
  5: opt onal str ng explanat on


  // -------- NETWORK-BASED SCOR NG METADATA --------

  // Found t  t et  n t  trusted c rcle.
  6: opt onal bool  sTrusted

  // Found t  t et  n t  d rect follows.
  8: opt onal bool  sFollow

  // True  f t  fromUser d of t  t et was wh el sted by t  dup / ant gam ng f lter.
  // T  typ cally  nd cates t  result was from a t et that matc d a fromUser d query.
  9: opt onal bool dontF lterUser


  // -------- COMMON DOCUMENT METADATA --------

  // User  D of t  author.  W n  sRet et  s true, t   s t  user  D of t  ret eter
  // and NOT that of t  or g nal t et.
  7: opt onal  64 fromUser d = 0

  // W n  sRet et (or packed features equ valent)  s true, t   s t  status  d of t 
  // or g nal t et. W n  sReply and getReplyS ce are true, t   s t  status  d of t 
  // or g nal t et.  n all ot r c rcumstances t   s 0.
  40: opt onal  64 sharedStatus d = 0

  // W n hasCard (or packed features equ valent)  s true, t   s one of SearchCardType.
  49: opt onal  8 cardType = 0

  // -------- EXTENDED DOCUMENT METADATA --------
  // T   s add  onal  tadata from facet f elds and column str de f elds.
  // Return of t se f elds  s controlled by Thr ftSearchResult tadataOpt ons to
  // allow for f ne-gra ned control over w n t se f elds are returned, as an
  // opt m zat on for searc s return ng a large quant y of results.

  // Lucene component of t  relevance score.  Only returned w n
  // Thr ftSearchResult tadataOpt ons.getLuceneScore  s true.
  31: opt onal double luceneScore = 0.0

  // Urls found  n t  t et.  Only returned w n
  // Thr ftSearchResult tadataOpt ons.getT etUrls  s true.
  18: opt onal l st<Thr ftSearchResultUrl> t etUrls

  // Deprecated  n SEARCH-8616.
  36: opt onal l st< 32> deprecated_top c Ds

  // Facets ava lable  n t  t et, t  w ll only be f lled  f
  // Thr ftSearchQuery.facetF eldNa s  s set  n t  request.
  22: opt onal l st<Thr ftFacetLabel> facetLabels

  // T  locat on of t  result, and t  d stance to   from t  center of t  query
  // locat on.  Only returned w n Thr ftSearchResult tadataOpt ons.getResultLocat on  s true.
  35: opt onal Thr ftSearchResultGeoLocat on resultLocat on

  // Per f eld h  attr but on.
  55: opt onal h _attr but on.F eldH Attr but on f eldH Attr but on

  // w t r t  has geolocat on_type:geotag h 
  57: opt onal bool geotagH  = 0

  // t  user  d of t  author of t  s ce/referenced t et (t  t et one repl ed
  // to, ret eted and poss bly quoted, etc.) (SEARCH-8561)
  // Only returned w n Thr ftSearchResult tadataOpt ons.getReferencedT etAuthor d  s true.
  60: opt onal  64 referencedT etAuthor d = 0

  // W t r t  t et has certa n types of  d a.
  // Only returned w n Thr ftSearchResult tadataOpt ons.get d aB s  s true.
  // "Nat ve v deo"  s e  r consu r, pro, v ne, or per scope.
  // "Nat ve  mage"  s an  mage hosted on p c.tw ter.com.
  62: opt onal bool hasConsu rV deo
  63: opt onal bool hasProV deo
  64: opt onal bool hasV ne
  65: opt onal bool hasPer scope
  66: opt onal bool hasNat veV deo
  67: opt onal bool hasNat ve mage

  // Packed features for t  result. T  f eld  s never populated.
  50: opt onal status.PackedFeatures deprecated_packedFeatures

  // T  features stored  n earlyb rd

  // From  nteger 0 from Earlyb rdFeatureConf gurat on:
  16: opt onal bool  sRet et
  71: opt onal bool  sSelfT et
  10: opt onal bool  sOffens ve
  11: opt onal bool hasL nk
  12: opt onal bool hasTrend
  13: opt onal bool  sReply
  14: opt onal bool hasMult pleHashtagsOrTrends
  23: opt onal bool fromVer f edAccount
  // Stat c text qual y score.  T   s actually an  nt bet en 0 and 100.
  30: opt onal double textScore
  51: opt onal search_language.Thr ftLanguage language

  // From  nteger 1 from Earlyb rdFeatureConf gurat on:
  52: opt onal bool has mage
  53: opt onal bool hasV deo
  28: opt onal bool hasNews
  48: opt onal bool hasCard
  61: opt onal bool hasV s bleL nk
  // T ep cred aka user rep.  T   s actually an  nt bet en 0 and 100.
  32: opt onal double userRep
  24: opt onal bool  sUserSpam
  25: opt onal bool  sUserNSFW
  26: opt onal bool  sUserBot
  54: opt onal bool  sUserAnt Soc al

  // From  nteger 2 from Earlyb rdFeatureConf gurat on:

  // Ret et, fav, reply, embeds counts, and v deo v ew counts are APPROX MATE ONLY.
  // Note that ret etCount, favCount and replyCount are not or g nal unnormal zed values,
  // but after a log2() funct on for  tor cal reason, t  loses us so  granular y.
  // For more accurate counts, use {ret et, fav, reply}CountV2  n extra tadata.
  2: opt onal  32 ret etCount
  33: opt onal  32 favCount
  34: opt onal  32 replyCount
  58: opt onal  32 embeds mpress onCount
  59: opt onal  32 embedsUrlCount
  68: opt onal  32 v deoV ewCount

  // Parus score.  T   s actually an  nt bet en 0 and 100.
  29: opt onal double parusScore

  // Extra feature data, all new feature f elds   want to return from Earlyb rd should go  nto
  // t  one, t  outer one  s always reach ng  s l m  of t  number of f elds JVM can
  // comfortably support!!
  86: opt onal Thr ftSearchResultExtra tadata extra tadata

  //  nteger 3  s om ted, see expFeatureValues above for more deta ls.

  // From  nteger 4 from Earlyb rdFeatureConf gurat on:
  // S gnature, for dupl cate detect on and removal.
  4: opt onal  32 s gnature

  // -------- TH NGS USED ONLY BY THE BLENDER --------

  // Soc al proof of t  t et, for network d scovery.
  // Do not use t se f elds outs de of network d scovery.
  41: opt onal l st< 64> ret etedUser Ds64
  42: opt onal l st< 64> replyUser Ds64

  // Soc al connect on bet en t  search user and t  result.
  19: opt onal soc al.Thr ftSoc alContext soc alContext

  // used by RelevanceT  l neSearchWorkflow, w t r a t et should be h ghl ghted or not
  46: opt onal bool h ghl ghtResult

  // used by RelevanceT  l neSearchWorkflow, t  h ghl ght context of t  h ghl ghted t et
  47: opt onal h ghl ght.Thr ftH ghl ghtContext h ghl ghtContext

  // t  pengu n vers on used to token ze t  t ets by t  serv ng earlyb rd  ndex as def ned
  //  n com.tw ter.common.text.vers on.Pengu nVers on
  56: opt onal  8 pengu nVers on

  69: opt onal bool  sNullcast

  // T   s t  normal zed rat o(0.00 to 1.00) of nth token(start ng before 140) d v ded by
  // numTokens and t n normal zed  nto 16 pos  ons(4 b s) but on a scale of 0 to 100% as
  //   unnormal ze   for  
  70: opt onal double tokenAt140D v dedByNumTokensBucket

}(pers sted='true')

// Query level result stats.
// Next  d: 20
struct Thr ftSearchResultsRelevanceStats {
  1: opt onal  32 numScored = 0
  // Sk pped docu nts count, t y  re also scored but t  r scores got  gnored (sk pped), note that t   s d fferent
  // from numResultsSk pped  n t  Thr ftSearchResults.
  2: opt onal  32 numSk pped = 0
  3: opt onal  32 numSk ppedForAnt Gam ng = 0
  4: opt onal  32 numSk ppedForLowReputat on = 0
  5: opt onal  32 numSk ppedForLowTextScore = 0
  6: opt onal  32 numSk ppedForSoc alF lter = 0
  7: opt onal  32 numSk ppedForLowF nalScore = 0
  8: opt onal  32 oldestScoredT etAge nSeconds = 0

  // More counters for var ous features.
  9:  opt onal  32 numFromD rectFollows = 0
  10: opt onal  32 numFromTrustedC rcle = 0
  11: opt onal  32 numRepl es = 0
  12: opt onal  32 numRepl esTrusted = 0
  13: opt onal  32 numRepl esOutOfNetwork = 0
  14: opt onal  32 numSelfT ets = 0
  15: opt onal  32 numW h d a = 0
  16: opt onal  32 numW hNews = 0
  17: opt onal  32 numSpamUser = 0
  18: opt onal  32 numOffens ve = 0
  19: opt onal  32 numBot = 0
}(pers sted='true')

// Per result debug  nfo.
struct Thr ftSearchResultDebug nfo {
  1: opt onal str ng hostna 
  2: opt onal str ng clusterNa 
  3: opt onal  32 part  on d
  4: opt onal str ng t erna 
}(pers sted='true')

struct Thr ftSearchResult {
  // Next ava lable f eld  D: 22

  // Result status  d.
  1: requ red  64  d

  // T etyP e status of t  search result
  7: opt onal deprecated.Status t etyp eStatus
  19: opt onal t et.T et t etyp eT et  // v2 struct

  //  f t  search result  s a ret et, t  f eld conta ns t  s ce T etyP e status.
  10: opt onal deprecated.Status s ceT etyp eStatus
  20: opt onal t et.T et s ceT etyp eT et  // v2 struct

  //  f t  search result  s a quote t et, t  f eld conta ns t  quoted T etyP e status.
  17: opt onal deprecated.Status quotedT etyp eStatus
  21: opt onal t et.T et quotedT etyp eT et  // v2 struct

  // Add  onal  tadata about a search result.
  5: opt onal Thr ftSearchResult tadata  tadata

  // H  h ghl ghts for var ous parts of t  t et
  // for t et text
  6: opt onal l st<h s.Thr ftH s> h H ghl ghts
  // for t  t le and descr pt on  n t  card expando.
  12: opt onal l st<h s.Thr ftH s> cardT leH H ghl ghts
  13: opt onal l st<h s.Thr ftH s> cardDescr pt onH H ghl ghts

  // Expans on types,  f expandResult == False, t  expans ons set should be  gnored.
  8: opt onal bool expandResult = 0
  9: opt onal set<expans ons.Thr ftT etExpans onType> expans ons

  // Only set  f t   s a promoted t et
  11: opt onal adserver_common.Ad mpress on ad mpress on

  // w re t  t et  s from
  // S nce Thr ftSearchResult used not only as an Earlyb rd response, but also an  nternal
  // data transfer object of Blender, t  value of t  f eld  s mutable  n Blender, not
  // necessar ly reflect ng Earlyb rd response.
  14: opt onal Thr ftT etS ce t etS ce

  // t  features of a t et used for relevance t  l ne
  // t  f eld  s populated by blender  n RelevanceT  l neSearchWorkflow
  15: opt onal features.Thr ftT etFeatures t etFeatures

  // t  conversat on context of a t et
  16: opt onal conversat on.Thr ftConversat onContext conversat onContext

  // per-result debugg ng  nfo that's pers sted across  rges.
  18: opt onal Thr ftSearchResultDebug nfo debug nfo
}(pers sted='true')

enum Thr ftFacetRank ngMode {
  COUNT = 0,
  F LTER_W TH_TERM_STAT ST CS = 1,
}

struct Thr ftFacetF eldRequest {
  // next ava lable f eld  D: 4
  1: requ red str ng f eldNa 
  2: opt onal  32 numResults = 5

  // use facetRank ngOpt ons  n Thr ftFacetRequest  nstead
  3: opt onal Thr ftFacetRank ngMode rank ngMode = Thr ftFacetRank ngMode.COUNT
}(pers sted='true')

struct Thr ftFacetRequest {
  // Next ava lable f eld  D: 7
  1: opt onal l st<Thr ftFacetF eldRequest> facetF elds
  5: opt onal rank ng.Thr ftFacetRank ngOpt ons facetRank ngOpt ons
  6: opt onal bool us ngQueryCac  = 0
}(pers sted='true')

struct Thr ftTermRequest {
  1: opt onal str ng f eldNa  = "text"
  2: requ red str ng term
}(pers sted='true')

enum Thr ft togramGranular yType {
  M NUTES = 0
  HOURS = 1,
  DAYS = 2,
  CUSTOM = 3,

  PLACE_HOLDER4 = 4,
  PLACE_HOLDER5 = 5,
}

struct Thr ft togramSett ngs {
  1: requ red Thr ft togramGranular yType granular y
  2: opt onal  32 numB ns = 60
  3: opt onal  32 sampl ngRate = 1
  4: opt onal  32 b nS ze nSeconds   // t  b n s ze, only used  f granular y  s set to CUSTOM.
}(pers sted='true')

// next  d  s 4
struct Thr ftTermStat st csRequest {
  1: opt onal l st<Thr ftTermRequest> termRequests
  2: opt onal Thr ft togramSett ngs  togramSett ngs
  //  f t   s set to true, even  f t re  s no termRequests above, so long as t   togramSett ngs
  //  s set, Earlyb rd w ll return a null->Thr ftTermResults entry  n t  termResults map, conta n ng
  // t  global t et count  togram for current query, wh ch  s t  number of t ets match ng t 
  // query  n d fferent m nutes/h s/days.
  3: opt onal bool  ncludeGlobalCounts = 0
  // W n t   s set, t  background facets call does anot r search  n order to f nd t  best
  // representat ve t et for a g ven term request, t  representat ve t et  s stored  n t 
  //  tadata of t  termstats result
  4: opt onal bool scoreT etsForRepresentat ves = 0
}(pers sted='true')

// Next  d  s 12
struct Thr ftFacetCount tadata {
  // t   s t   d of t  f rst t et  n t   ndex that conta ned t  facet
  1: opt onal  64 status d = -1

  // w t r t  t et w h t  above status d  s NSFW, from an ant soc al user,
  // marked as sens  ve content, etc.
  10: opt onal bool statusPoss blySens  ve

  // t   d of t  user who sent t  t et above - only returned  f
  // status d  s returned too
  // NOTE: for nat ve photos   may not be able to determ ne t  user,
  // even though t  status d can be returned. T   s because t  status d
  // can be determ ned from t  url, but t  user can't and t  t et may
  // not be  n t   ndex anymore.  n t  case status d would be set but
  // tw terUser d would not.
  2: opt onal  64 tw terUser d = -1

  // t  language of t  t et above.
  8: opt onal search_language.Thr ftLanguage statusLanguage

  // opt onally wh el st t  fromUser d from dup/tw terUser d f lter ng
  3: opt onal bool dontF lterUser = 0;

  //  f t  facet  s a nat ve photo   return for conven ence t 
  // tw mg url
  4: opt onal str ng nat vePhotoUrl

  // opt onally returns so  debug  nformat on about t  facet
  5: opt onal str ng explanat on

  // t  created_at value for t  t et from status d - only returned
  //  f status d  s returned too
  6: opt onal  64 created_at

  // t  max mum t epcred of t  h s that conta ned t  facet
  7: opt onal  32 maxT epCred

  // W t r t  facet result  s force  nserted,  nstead of organ cally returned from search.
  // T  f eld  s only used  n Blender to mark t  force- nserted facet results
  // (from recent t ets, etc).
  11: opt onal bool force nserted = 0
}(pers sted='true')

struct Thr ftTermResults {
  1: requ red  32 totalCount
  2: opt onal l st< 32>  togramB ns
  3: opt onal Thr ftFacetCount tadata  tadata
}(pers sted='true')

struct Thr ftTermStat st csResults {
  1: requ red map<Thr ftTermRequest,Thr ftTermResults> termResults
  2: opt onal Thr ft togramSett ngs  togramSett ngs
  //  f  togramSett ngs are set, t  w ll have a l st of Thr ft togramSett ngs.numB ns b n ds,
  // that t  correspond ng  togramB ns  n Thr ftTermResults w ll have counts for.
  // T  b n ds w ll correspond to t  t  s of t  h s match ng t  dr v ng search query for t 
  // term stat st cs request.
  //  f t re  re no h s match ng t  search query, numB ns b n ds w ll be returned, but t 
  // values of t  b n ds w ll not  an ngfully correspond to anyth ng related to t  query, and
  // should not be used. Such cases can be  dent f ed by Thr ftSearchResults.numH sProcessed be ng
  // set to 0  n t  response, and t  response not be ng early term nated.
  3: opt onal l st< 32> b n ds
  //  f set, t   d  nd cates t   d of t  m n mum (oldest) b n that has been completely searc d,
  // even  f t  query was early term nated.  f not set no b n was searc d fully, or no  togram
  // was requested.
  // Note that  f e.g. a query only matc s a b n part ally (due to e.g. a s nce operator) t  b n
  //  s st ll cons dered fully searc d  f t  query d d not early term nate.
  4: opt onal  32 m nCompleteB n d
}(pers sted='true')

struct Thr ftFacetCount {
  // t  text of t  facet
  1: requ red str ng facetLabel

  // deprecated; currently matc s   ghtedCount for backwards-compat b l y reasons
  2: opt onal  32 facetCount

  // t  s mple count of t ets that conta ned t  facet, w hout any
  //   ght ng appl ed
  7: opt onal  32 s mpleCount

  // a   ghted vers on of t  count, us ng s gnals l ke t epcred, parus, etc.
  8: opt onal  32   ghtedCount

  // t  number of t  s t  facet occurred  n t ets match ng t  background query
  // us ng t  term stat st cs AP  - only set  f F LTER_W TH_TERM_STAT ST CS was used
  3: opt onal  32 backgroundCount

  // t  relevance score that was computed for t  facet  f F LTER_W TH_TERM_STAT ST CS
  // was used
  4: opt onal double score

  // a counter for how often t  facet was penal zed
  5: opt onal  32 penaltyCount

  6: opt onal Thr ftFacetCount tadata  tadata
}(pers sted='true')

// L st of facet labels and counts for a g ven facet f eld, t 
// total count for t  f eld, and a qual y score for t  f eld
struct Thr ftFacetF eldResults {
  1: requ red l st<Thr ftFacetCount> topFacets
  2: requ red  32 totalCount
  3: opt onal double scoreQual y
  4: opt onal  32 totalScore
  5: opt onal  32 totalPenalty

  // T  rat o of t  t et language  n t  t ets w h t  facet f eld, a map from t  language
  // na  to a number bet en (0.0, 1.0]. Only languages w h rat o h g r than 0.1 w ll be  ncluded.
  6: opt onal map<search_language.Thr ftLanguage, double> language togram
}

struct Thr ftFacetResults {
  1: requ red map<str ng, Thr ftFacetF eldResults> facetF elds
  2: opt onal  32 backgroundNumH s
  // returns opt onally a l st of user  ds that should not get f ltered
  // out by th ngs l ke ant gam ng f lters, because t se users  re expl c ly
  // quer ed for
  // Note that Thr ftFacetCount tadata returns already dontF lterUser
  // for facet requests  n wh ch case t  l st  s not needed. Ho ver,  
  //  s needed for subsequent term stat st cs quer es,  re user  d lookups
  // are perfor d, but a d fferent background query  s used.
  3: opt onal set< 64> user DWh el st
}

struct Thr ftSearchResults {
  // Next ava lable f eld  D: 23
  1: requ red l st<Thr ftSearchResult> results = []

  // (SEARCH-11950): Now resultOffset  s deprecated, so t re  s no use  n numResultsSk pped too.
  9: opt onal  32 deprecated_numResultsSk pped

  // Number of docs that matc d t  query and  re processed.
  7: opt onal  32 numH sProcessed

  // Range of status  Ds searc d, from max  D to m n  D (both  nclus ve).
  // T se may be unset  n case that t  search query conta ned  D or t  
  // operators that  re completely out of range for t  g ven  ndex.
  10: opt onal  64 maxSearc dStatus D
  11: opt onal  64 m nSearc dStatus D

  // T   range that was searc d (both  nclus ve).
  19: opt onal  32 maxSearc dT  S nceEpoch
  20: opt onal  32 m nSearc dT  S nceEpoch

  12: opt onal Thr ftSearchResultsRelevanceStats relevanceStats

  // Overall qual y of t  search result set
  13: opt onal double score = -1.0
  18: opt onal double nsfwRat o = 0.0

  // T  count of h  docu nts  n each language.
  14: opt onal map<search_language.Thr ftLanguage,  32> language togram

  // H  counts per t   per od:
  // T  key  s a t   cutoff  n m ll seconds (e.g. 60000 msecs ago).
  // T  value  s t  number of h s that are more recent than t  cutoff.
  15: opt onal map< 64,  32> h Counts

  // t  total cost for t  query
  16: opt onal double queryCost

  // Set to non-0  f t  query was term nated early (e  r due to a t  out, or exceeded query cost)
  // W n gett ng t  response from a s ngle earlyb rd, t  w ll be set to 1,  f t  query
  // term nated early.
  // W n gett ng t  response from a search root, t  should be set to t  number of  nd v dual
  // earlyb rd requests that  re term nated early.
  17: opt onal  32 numPart  onsEarlyTerm nated

  //  f Thr ftSearchResults returns features  n features.Thr ftSearchResultFeature format, t 
  // f eld would def ne t  sc ma of t  features.
  //  f t  earlyb rd sc ma  s already  n t  cl ent cac d sc mas  nd cated  n t  request, t n
  // searchFeatureSc ma would only have (vers on, c cksum)  nformat on.
  //
  // Not ce that earlyb rd root only sends one sc ma back to t  superroot even though earlyb rd
  // root m ght rece ve mult ple vers on of sc mas.
  //
  // Earlyb rd roots' sc ma  rge/choose log c w n return ng results to superroot:
  // . p ck t  most occurred vers oned sc ma and return t  sc ma to t  superroot
  // .  f t  superroot already cac s t  sc ma, only send t  vers on  nformat on back
  //
  // Superroots' sc ma  rge/choose log c w n return ng results to cl ents:
  // . p ck t  sc ma based on t  order of: realt   > protected > arch ve
  // . because of t  above order ng,    s poss ble that arch ve earlyb rd sc ma w h a new flush
  //   vers on (w h new b  features) m ght be lost to older realt   earlyb rd sc ma; t   s
  //   cons dered to to be rare and acceptable because one realt   earlyb rd deploy would f x  
  21: opt onal features.Thr ftSearchFeatureSc ma featureSc ma

  // How long   took to score t  results  n earlyb rd ( n nanoseconds). T  number of results
  // that  re scored should be set  n numH sProcessed.
  // Expected to only be set for requests that actually do scor ng ( .e. Relevance and TopT ets).
  22: opt onal  64 scor ngT  Nanos

  8: opt onal  32 deprecated_numDocsProcessed
}

// Note: Earlyb rd no longer respects t  f eld, as   does not conta n statuses.
// Blender should respect  .
enum Earlyb rdReturnStatusType {
  NO_STATUS = 0
  // deprecated
  DEPRECATED_BAS C_STATUS = 1,
  // deprecated
  DEPRECATED_SEARCH_STATUS = 2,
  TWEETYP E_STATUS = 3,

  PLACE_HOLDER4 = 4,
  PLACE_HOLDER5 = 5,
}

struct AdjustedRequestParams {
  // Next ava lable f eld  D: 4

  // Adjusted value for Earlyb rdRequest.searchQuery.numResults.
  1: opt onal  32 numResults

  // Adjusted value for Earlyb rdRequest.searchQuery.maxH sToProcess and
  // Earlyb rdRequest.searchQuery.relevanceOpt ons.maxH sToProcess.
  2: opt onal  32 maxH sToProcess

  // Adjusted value for Earlyb rdRequest.searchQuery.relevanceOpt ons.returnAllResults
  3: opt onal bool returnAllResults
}

struct Earlyb rdRequest {
  // Next ava lable f eld  D: 36

  // -------- COMMON REQUEST OPT ONS --------
  // T se f elds conta n opt ons respected by all k nds of earlyb rd requests.

  // Search query conta n ng general earlyb rd retr eval and h  collect on opt ons.
  // Also conta ns t  opt ons spec f c to search requests.
  1: requ red Thr ftSearchQuery searchQuery

  // Common RPC  nformat on - cl ent hostna  and request  D.
  12: opt onal str ng cl entHost
  13: opt onal str ng cl entRequest D

  // A str ng  dent fy ng t  cl ent that  n  ated t  request.
  // Ex: macaw-search.prod,  bforall.prod,  bforall.stag ng.
  // T   ntent on  s to track t  load   get from each cl ent, and eventually enforce
  // per-cl ent QPS quotas, but t  f eld could also be used to allow access to certa n features
  // only to certa n cl ents, etc.
  21: opt onal str ng cl ent d

  // T  t   ( n m ll s s nce epoch) w n t  earlyb rd cl ent  ssued t  request.
  // Can be used to est mate request t  out t  , captur ng  n-trans  t   for t  request.
  23: opt onal  64 cl entRequestT  Ms

  // Cach ng para ters used by earlyb rd roots.
  24: opt onal cach ng.Cach ngParams cach ngParams

  // Deprecated. See SEARCH-2784
  // Earlyb rd requests w ll be early term nated  n a best-effort way to prevent t m from
  // exceed ng t  g ven t  out.   f t  out  s <= 0 t  early term nat on cr er a  s
  // d sabled.
  17: opt onal  32 t  outMs = -1

  // Deprecated. See SEARCH-2784
  // Earlyb rd requests w ll be early term nated  n a best-effort way to prevent t m from
  // exceed ng t  g ven query cost.   f maxQueryCost <= 0 t  early term nat on cr er a
  //  s d sabled.
  20: opt onal double maxQueryCost = -1


  // -------- REQUEST-TYPE SPEC F C OPT ONS --------
  // T se f elds conta n opt ons for one spec f c k nd of request.   f one of t se opt ons
  //  s set t  request w ll be cons dered to be t  appropr ate type of request.

  // Opt ons for facet count ng requests.
  11: opt onal Thr ftFacetRequest facetRequest

  // Opt ons for term stat st cs requests.
  14: opt onal Thr ftTermStat st csRequest termStat st csRequest


  // -------- DEBUG OPT ONS --------
  // Used for debugg ng only.

  // Debug mode, 0 for no debug  nformat on.
  15: opt onal  8 debugMode = 0

  // Can be used to pass extra debug argu nts to earlyb rd.
  34: opt onal Earlyb rdDebugOpt ons debugOpt ons

  // Searc s a spec f c seg nt by t   sl ce  d  f set and seg nt  d  s > 0.
  22: opt onal  64 searchSeg nt d

  // -------- TH NGS USED ONLY BY THE BLENDER --------
  // T se f elds are used by t  blender and cl ents of t  blender, but not by earlyb rd.

  // Spec f es what k nd of status object to return,  f any.
  7: opt onal Earlyb rdReturnStatusType returnStatusType


  // -------- TH NGS USED BY THE ROOTS --------
  // T se f elds are not  n use by earlyb rds t mselves, but are  n use by earlyb rd roots
  // (and t  r cl ents).
  // T se f elds l ve  re s nce   currently reuse t  sa  thr ft request and response structs
  // for both earlyb rds and earlyb rd roots, and could potent ally be moved out  f    re to
  //  ntroduce separate request / response structs spec f cally for t  roots.

  //   have a threshold for how many hash part  on requests need to succeed at t  root level
  //  n order for t  earlyb rd root request to be cons dered successful.
  // Each type or earlyb rd quer es (e.g. relevance, or term stat st cs) has a predef ned default
  // threshold value (e.g. 90% or hash part  ons need to succeed for a recency query).
  // T  cl ent can opt onally set t  threshold value to be so th ng ot r than t  default,
  // by sett ng t  f eld to a value  n t  range of 0 (exclus ve) to 1 ( nclus ve).
  //  f t  value  s set outs de of t  (0, 1] range, a CL ENT_ERROR Earlyb rdResponseCode w ll
  // be returned.
  25: opt onal double successfulResponseThreshold

  // W re does t  query co  from?
  26: opt onal query.Thr ftQueryS ce queryS ce

  // W t r to get arch ve results T  flag  s adv sory. A request may st ll be restr cted from
  // gett ng reqults from t  arch ve based on t  request ng cl ent, query s ce, requested
  // t  / d range, etc.
  27: opt onal bool getOlderResults

  // T  l st of users follo d by t  current user.
  // Used to restr ct t  values  n t  fromUser DF lter64 f eld w n send ng a request
  // to t  protectected cluster.
  28: opt onal l st< 64> follo dUser ds

  // T  adjusted para ters for t  protected request.
  29: opt onal AdjustedRequestParams adjustedProtectedRequestParams

  // T  adjusted para ters for t  full arch ve request.
  30: opt onal AdjustedRequestParams adjustedFullArch veRequestParams

  // Return only t  protected t ets. T  flag  s used by t  SuperRoot to return relevance
  // results that conta n only protected t ets.
  31: opt onal bool getProtectedT etsOnly

  // Token ze ser al zed quer es w h t  appropr ate Peng n vers on(s).
  // Only has an effect on superroot.
  32: opt onal bool retoken zeSer al zedQuery

  // Flag to  gnore t ets that are very recent and could be  ncompletely  ndexed.
  //  f false, w ll allow quer es to see results that may v olate  mpl c  stream ng
  // guarantees and w ll search T ets that have been part ally  ndexed.
  // See go/ ndex ng-latency for more deta ls. W n enabled, prevents see ng t ets
  // that are less than 15 seconds old (or a s m larly conf gured threshold).
  // May be set to false unless expl c ly set to true.
  33: opt onal bool sk pVeryRecentT ets = 1

  // Sett ng an exper  ntal cluster w ll reroute traff c at t  realt   root layer to an exper  ntal
  // Earlyb rd cluster. T  w ll have no  mpact  f set on requests to anyw re ot r than realt   root.
  35: opt onal Exper  ntCluster exper  ntClusterToUse

  // Caps number of results returned by roots after  rg ng results from d fferent earlyb rd part  ons/clusters. 
  //  f not set, Thr ftSearchQuery.numResults or CollectorParams.numResultsToReturn w ll be used to cap results. 
  // T  para ter w ll be  gnored  f Thr ftRelevanceOpt ons.returnAllResults  s set to true.
  36: opt onal  32 numResultsToReturnAtRoot
}

enum Earlyb rdResponseCode {
  SUCCESS = 0,
  PART T ON_NOT_FOUND = 1,
  PART T ON_D SABLED = 2,
  TRANS ENT_ERROR = 3,
  PERS STENT_ERROR = 4,
  CL ENT_ERROR = 5,
  PART T ON_SK PPED = 6,
  // Request was queued up on t  server for so long that   t  d out, and was not
  // executed at all.
  SERVER_T MEOUT_ERROR = 7,
  T ER_SK PPED = 8,
  // Not enough part  ons returned a successful response. T   rged response w ll have part  on
  // counts and early term nat on  nfo set, but w ll not have search results.
  TOO_MANY_PART T ONS_FA LED_ERROR = 9,
  // Cl ent  nt over  s quota, and t  request was throttled.
  QUOTA_EXCEEDED_ERROR = 10,
  // Cl ent's request  s blocked based on Search  nfra's pol cy. Search  nfra can can block cl ent's
  // requests based on t  query s ce of t  request.
  REQUEST_BLOCKED_ERROR = 11,

  CL ENT_CANCEL_ERROR = 12,

  CL ENT_BLOCKED_BY_T ER_ERROR = 13,

  PLACE_HOLDER_2015_09_21 = 14,
}

// A recorded request and response.
struct Earlyb rdRequestResponse {
  // W re d d   send t  request to.
  1: opt onal str ng sentTo;
  2: opt onal Earlyb rdRequest request;
  // T  can't be an Earlyb rdResponse, because t  thr ft comp ler for Python
  // doesn't allow cycl c references and   have so  Python ut l  es that w ll fa l.
  3: opt onal str ng response;
}

struct Earlyb rdDebug nfo {
  1: opt onal str ng host
  2: opt onal str ng parsedQuery
  3: opt onal str ng luceneQuery
  // Requests sent to dependent serv ces. For example, superroot sends to realt   root,
  // arch ve root, etc.
  4: opt onal l st<Earlyb rdRequestResponse> sentRequests;
  // seg nt level debug  nfo (eg. h sPerSeg nt, max/m nSearc dT   etc.)
  5: opt onal l st<str ng> collectorDebug nfo
  6: opt onal l st<str ng> termStat st csDebug nfo
}

struct Earlyb rdDebugOpt ons {
  1: opt onal bool  ncludeCollectorDebug nfo
}

struct T erResponse {
  1: opt onal Earlyb rdResponseCode t erResponseCode
  2: opt onal  32 numPart  ons
  3: opt onal  32 numSuccessfulPart  ons
}

struct Earlyb rdServerStats {
  // T  hostna  of t  Earlyb rd that processed t  request.
  1: opt onal str ng hostna 

  // T  part  on to wh ch t  earlyb rd belongs.
  2: opt onal  32 part  on

  // Current Earlyb rd QPS.
  // Earlyb rds should set t  f eld at t  end of a request (not at t  start). T  would g ve
  // roots a more up-to-date v ew of t  load on t  earlyb rds.
  3: opt onal  64 currentQps

  // T  t   t  request wa ed  n t  queue before Earlyb rd started process ng  .
  // T  does not  nclude t  t   spent  n t  f nagle queue:  's t  t   bet en t  mo nt
  // earlyb rd rece ved t  request, and t  mo nt   started process ng t  request.
  4: opt onal  64 queueT  M ll s

  // T  average request t    n t  queue before Earlyb rd started process ng  .
  // T  does not  nclude t  t   that requests spent  n t  f nagle queue:  's t  average t  
  // bet en t  mo nt earlyb rd rece ved  s requests, and t  mo nt   started process ng t m.
  5: opt onal  64 averageQueueT  M ll s

  // Current average per-request latency as perce ved by Earlyb rd.
  6: opt onal  64 averageLatencyM cros

  // T  t er to wh ch t  earlyb rd belongs.
  7: opt onal str ng t erNa 
}

struct Earlyb rdResponse {
  // Next ava lable f eld  D: 17
  1: opt onal Thr ftSearchResults searchResults
  5: opt onal Thr ftFacetResults facetResults
  6: opt onal Thr ftTermStat st csResults termStat st csResults
  2: requ red Earlyb rdResponseCode responseCode
  3: requ red  64 responseT  
  7: opt onal  64 responseT  M cros
  // f elds below w ll only be returned  f debug > 1  n t  request.
  4: opt onal str ng debugStr ng
  8: opt onal Earlyb rdDebug nfo debug nfo

  // Only ex sts for  rged earlyb rd response.
  10: opt onal  32 numPart  ons
  11: opt onal  32 numSuccessfulPart  ons
  // Only ex sts for  rged earlyb rd response from mult ple t ers.
  13: opt onal l st<T erResponse> perT erResponse

  // Total number of seg nts that  re searc d. Part ally searc d seg nts are fully counted.
  // e.g.  f   searc d 1 seg nt fully, and early term nated half way through t  second
  // seg nt, t  f eld should be set to 2.
  15: opt onal  32 numSearc dSeg nts

  // W t r t  request early term nated,  f so, t  term nat on reason.
  12: opt onal search.EarlyTerm nat on nfo earlyTerm nat on nfo

  // W t r t  response  s from cac .
  14: opt onal bool cac H 

  // Stats used by roots to determ ne  f   should go  nto degraded mode.
  16: opt onal Earlyb rdServerStats earlyb rdServerStats
}

enum Earlyb rdStatusCode {
  START NG = 0,
  CURRENT = 1,
  STOPP NG = 2,
  UNHEALTHY = 3,
  BLACKL STED = 4,

  PLACE_HOLDER5 = 5,
  PLACE_HOLDER6 = 6,
}

struct Earlyb rdStatusResponse {
  1: requ red Earlyb rdStatusCode code
  2: requ red  64 al veS nce
  3: opt onal str ng  ssage
}

serv ce Earlyb rdServ ce {
  str ng getNa (),
  Earlyb rdStatusResponse getStatus(),
  Earlyb rdResponse search( 1: Earlyb rdRequest request )
}
