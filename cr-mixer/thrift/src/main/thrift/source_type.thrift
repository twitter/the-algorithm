na space java com.tw ter.cr_m xer.thr ftjava
#@na space scala com.tw ter.cr_m xer.thr ftscala
#@na space strato com.tw ter.cr_m xer

// Due to legacy reason, S ceType used to represent both S ceS gnalType and S m lar yEng neType
//  nce,   can see several S ceType such as User nterested n, HashSpace, etc.
// Mov ng forward, S ceType w ll be used for S ceS gnalType ONLY. eg., T etFavor e, UserFollow
//   w ll create a new S m lar yEng neType to separate t m. eg., S mClustersANN
enum S ceType {
  // T et based S ce S gnal
  T etFavor e       = 0
  Ret et             = 1
  Traff cAttr but on  = 2 // Traff c Attr but on w ll be m grated over  n Q3
  Or g nalT et       = 3
  Reply               = 4
  T etShare          = 5
  GoodT etCl ck      = 6 // total d ll t   > N seconds after cl ck on t  t et
  V deoT etQual yV ew = 7
  V deoT etPlayback50  = 8

  // User d based S ce S gnal ( ncludes both Producer/Consu r)
  UserFollow               = 101
  UserRepeatedProf leV s  = 102

  CurrentUser_DEPRECATED   = 103

  RealGraphOon             = 104
  FollowRecom ndat on     = 105

  Tw ceUser d              = 106
  UserTraff cAttr but onProf leV s  = 107
  GoodProf leCl ck         = 108 // total d ll t   > N seconds after cl ck  nto t  prof le page

  // (Not f cat on) T et based S ce S gnal
  Not f cat onCl ck   = 201

  // (Ho ) T et based S ce S gnal
  Ho T etCl ck       = 301
  Ho V deoV ew        = 302
  Ho Songb rdShowMore = 303

  // Top c based S ce S gnal
  Top cFollow         = 401 // Deprecated
  PopularTop c        = 402 // Deprecated

  // Old CR code
  User nterested n    = 501 // Deprecated
  Tw ce nterested n   = 502 // Deprecated
  MBCG                = 503 // Deprecated
  HashSpace           = 504 // Deprecated

  // Old CR code
  Cluster             = 601 // Deprecated

  // Search based S ce S gnal
  SearchProf leCl ck  = 701 // Deprecated
  SearchT etCl ck    = 702 // Deprecated

  // Graph based S ce
  StrongT ePred ct on      = 801 // STP
  Tw ceClusters mbers     = 802
  Lookal ke                = 803 // Deprecated
  RealGraph n              = 804

  // Current requester User  d.    s only used for scr b ng. Placeholder value
  RequestUser d       = 1001
  // Current request T et  d used  n RelatedT et. Placeholder value
  RequestT et d      = 1002

  // Negat ve S gnals
  T etReport = 1101
  T etDontL ke = 1102
  T etSeeFe r = 1103
  AccountBlock = 1104
  AccountMute = 1105

  // Aggregated S gnals
  T etAggregat on = 1201
  ProducerAggregat on = 1202
} (pers sted='true', hasPersonalData='true')

enum S m lar yEng neType {
  S mClustersANN              = 1
  T etBasedUserT etGraph    = 2
  T etBasedTwH NANN          = 3
  Follow2VecANN               = 4 // Consu rEmbedd ngBasedFollow2Vec
  Q G                         = 5
  Offl neS mClustersANN       = 6
  Lookal keUTG_DEPRECATED     = 7
  ProducerBasedUserT etGraph = 8
  FrsUTG_DEPRECATED           = 9
  RealGraphOonUTG_DEPRECATED  = 10
  Consu rEmbedd ngBasedTwH NANN = 11
  Twh nCollabF lter           = 12
  Tw ceUTG_DEPRECATED         = 13
  Consu rEmbedd ngBasedTwoTo rANN = 14
  T etBasedBeTANN            = 15
  StpUTG_DEPRECATED           = 16
  UTEG                        = 17
  ROMR                        = 18
  Consu rsBasedUserT etGraph  = 19
  T etBasedUserV deoGraph    = 20
  CertoTop cT et             = 24
  Consu rsBasedUserAdGraph   = 25
  T etBasedUserAdGraph       = 26
  Sk TfgTop cT et           = 27
  Consu rBasedWalsANN        = 28
  ProducerBasedUserAdGraph    = 29
  Sk H ghPrec s onTop cT et = 30
  Sk  nterestBrowserTop cT et = 31
  Sk ProducerBasedTop cT et   = 32
  ExploreTr pOffl neS mClustersT ets = 33
  D ffus onBasedT et = 34
  Consu rsBasedUserV deoGraph  = 35

  //  n network
  Earlyb rdRecencyBasedS m lar yEng ne = 21
  Earlyb rdModelBasedS m lar yEng ne = 22
  Earlyb rdTensorflowBasedS m lar yEng ne = 23
  // Compos e
  T etBasedUn f edS m lar yEng ne    = 1001
  ProducerBasedUn f edS m lar yEng ne = 1002
} (pers sted='true')
