na space java com.tw ter.tsp.thr ftjava
na space py gen.tw ter.tsp
#@na space scala com.tw ter.tsp.thr ftscala
#@na space strato com.tw ter.tsp.strato

 nclude "com/tw ter/contentrecom nder/common.thr ft"
 nclude "com/tw ter/s mclusters_v2/ dent f er.thr ft"
 nclude "com/tw ter/s mclusters_v2/onl ne_store.thr ft"
 nclude "top c_l st ng.thr ft"

enum Top cL st ngSett ng {
  All = 0 // All t  ex st ng Semant c Core Ent y/Top cs.  e., All top cs on tw ter, and may or may not have been launc d yet.
  Followable = 1 // All t  top cs wh ch t  user  s allo d to follow.  e., top cs that have sh pped, and user may or may not be follow ng  .
  Follow ng = 2 // Only top cs t  user  s expl c ly follow ng
   mpl c Follow = 3 // T  top cs user has not follo d but  mpl c ly may follow.  e., Only top cs that user has not follo d.
} (hasPersonalData='false')


// used to tell Top c Soc al Proof endpo nt wh ch spec f c f lter ng can be bypassed
enum Top cSoc alProofF lter ngBypassMode {
  Not nterested = 0
} (hasPersonalData='false')

struct Top cSoc alProofRequest {
  1: requ red  64 user d(personalDataType = "User d")
  2: requ red set< 64> t et ds(personalDataType = 'T et d')
  3: requ red common.D splayLocat on d splayLocat on
  4: requ red Top cL st ngSett ng top cL st ngSett ng
  5: requ red top c_l st ng.Top cL st ngV e rContext context
  6: opt onal set<Top cSoc alProofF lter ngBypassMode> bypassModes
  7: opt onal map< 64, set< tr cTag>> tags
}

struct Top cSoc alProofOpt ons {
  1: requ red  64 user d(personalDataType = "User d")
  2: requ red common.D splayLocat on d splayLocat on
  3: requ red Top cL st ngSett ng top cL st ngSett ng
  4: requ red top c_l st ng.Top cL st ngV e rContext context
  5: opt onal set<Top cSoc alProofF lter ngBypassMode> bypassModes
  6: opt onal map< 64, set< tr cTag>> tags
}

struct Top cSoc alProofResponse {
  1: requ red map< 64, l st<Top cW hScore>> soc alProofs
}(hasPersonalData='false')

// D st ngu s s bet en how a top c t et  s generated. Useful for  tr c track ng and debugg ng
enum Top cT etType {
  // CrOON cand dates
  User nterested n        = 1
  Tw stly                 = 2
  // crTop c cand dates
  Sk Consu rEmbedd ngs  = 100
  Sk ProducerEmbedd ngs  = 101
  Sk H ghPrec s on       = 102
  Sk  nterestBrowser     = 103
  Certo                   = 104
}(pers sted='true')

struct Top cW hScore {
  1: requ red  64 top c d
  2: requ red double score // score used to rank top cs relat ve to one anot r
  3: opt onal Top cT etType algor hmType // how t  top c  s generated
  4: opt onal Top cFollowType top cFollowType // W t r t  top c  s be ng expl c ly or  mpl c ly follo d
}(pers sted='true', hasPersonalData='false')


struct ScoreKey {
  1: requ red  dent f er.Embedd ngType userEmbedd ngType
  2: requ red  dent f er.Embedd ngType top cEmbedd ngType
  3: requ red onl ne_store.ModelVers on modelVers on
}(pers sted='true', hasPersonalData='false')

struct UserTop cScore {
  1: requ red map<ScoreKey, double> scores
}(pers sted='true', hasPersonalData='false')


enum Top cFollowType {
  Follow ng = 1
   mpl c Follow = 2
}(pers sted='true')

// Prov de t  Tags wh ch prov des t  Recom nded T ets S ce S gnal and ot r context.
// Warn ng: Please don't use t  tag  n any ML Features or bus ness log c.
enum  tr cTag {
  // S ce S gnal Tags
  T etFavor e         = 0
  Ret et               = 1

  UserFollow            = 101
  PushOpenOrNtabCl ck   = 201

  Ho T etCl ck        = 301
  Ho V deoV ew         = 302
  Ho Songb rdShowMore  = 303


   nterestsRankerRecentSearc s = 401  // For  nterests Cand date Expans on

  User nterested n      = 501
  MBCG                = 503
  // Ot r  tr c Tags
} (pers sted='true', hasPersonalData='true')
