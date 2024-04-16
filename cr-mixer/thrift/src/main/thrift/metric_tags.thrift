na space java com.tw ter.cr_m xer.thr ftjava
#@na space scala com.tw ter.cr_m xer.thr ftscala
#@na space strato com.tw ter.cr_m xer


// NOTE: DO NOT depend on  tr cTags for  mportant ML Features or bus ness log c.
//  tr cTags are  ant for stats track ng & debugg ng purposes ONLY.
// cr-m xer may change  s def n  ons & how each cand date  s tagged w hout publ c not ce.
// NOTE: TSPS needs t  caller (Ho ) to spec fy wh ch s gnal   uses to make Personal zed Top cs
enum  tr cTag {
  // S ce S gnal Tags
  T etFavor e         = 0
  Ret et               = 1
  Traff cAttr but on    = 2
  Or g nalT et         = 3
  Reply                 = 4
  T etShare            = 5

  UserFollow            = 101
  UserRepeatedProf leV s  = 102

  PushOpenOrNtabCl ck   = 201

  Ho T etCl ck        = 301
  Ho V deoV ew         = 302

  // s m eng ne types
  S mClustersANN        = 401
  T etBasedUserT etGraph    = 402
  T etBasedTwH NANN          = 403
  Consu rEmbedd ngBasedTwH NANN = 404


  // comb ned eng ne types
  User nterested n      = 501 // W ll deprecate soon
  Lookal keUTG          = 502
  Twh nCollabF lter     = 503

  // Offl ne Tw ce
  Tw ceUser d           = 601

  // Ot r  tr c Tags
  Request althF lterPushOpenBasedT etEmbedd ng = 701
} (pers sted='true', hasPersonalData='true')
