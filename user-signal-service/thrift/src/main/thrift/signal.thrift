na space java com.tw ter.users gnalserv ce.thr ftjava
na space py gen.tw ter.users gnalserv ce.s gnal
#@na space scala com.tw ter.users gnalserv ce.thr ftscala
#@na space strato com.tw ter.users gnalserv ce.strato

 nclude "com/tw ter/s mclusters_v2/ dent f er.thr ft"


enum S gnalType {
  /**
  Please ma nta n t  key space rule to avo d compat b l y  ssue for t  downstream product on job
  * Prod  Key space:  0-1000
  * Devel Key space:  1000+
  **/


  /* t et based s gnals */
  T etFavor e       = 0, // 540 Days Looback w ndow
  Ret et             = 1, // 540 Days Lookback w ndow
  Traff cAttr but on  = 2,
  Or g nalT et       = 3, // 540 Days Looback w ndow
  Reply               = 4, // 540 Days Looback w ndow
  /* T ets that t  user shared (sharer s de)
    *  V1: successful shares (cl ck share  con -> cl ck  n-app, or off-platform share opt on
    * or copy ng l nk)
    * */
  T etShare_V1       = 5, // 14 Days Lookback w ndow

  T etFavor e_90D_V2 = 6, // 90 Days Lookback w ndow : t et fav from user w h recent engage nt  n t  past 90 days
  Ret et_90D_V2 = 7, // 90 Days Lookback w ndow : ret et from user w h recent engage nt  n t  past 90 days
  Or g nalT et_90D_V2 = 8, // 90 Days Lookback w ndow : or g nal t et from user w h recent engage nt  n t  past 90 days
  Reply_90D_V2 = 9,// 90 Days Lookback w ndow : reply from user w h recent engage nt  n t  past 90 days
  GoodT etCl ck = 10,// GoodT etC l ck S gnal : D ll T    Threshold >=2s

  // v deo t ets that  re watc d (10s OR 95%)  n t  past 90 days, are not ads, and have >=10s v deo
  V deoV ew_90D_Qual y_V1 = 11   // 90 Days Lookback w ndow
  // v deo t ets that  re watc d 50%  n t  past 90 days, are not ads, and have >=10s v deo
  V deoV ew_90D_Playback50_V1 = 12   // 90 Days Lookback w ndow

  /* user based s gnals */
  AccountFollow = 100, //  nf n e lookback w ndow
  RepeatedProf leV s _14D_M nV s 2_V1 = 101,
  RepeatedProf leV s _90D_M nV s 6_V1 = 102,
  RepeatedProf leV s _180D_M nV s 6_V1 = 109,
  RepeatedProf leV s _14D_M nV s 2_V1_No_Negat ve = 110,
  RepeatedProf leV s _90D_M nV s 6_V1_No_Negat ve = 111,
  RepeatedProf leV s _180D_M nV s 6_V1_No_Negat ve = 112,
  RealGraphOon                          = 104,
  Traff cAttr but onProf le_30D_LastV s  = 105,
  Traff cAttr but onProf le_30D_DecayedV s  = 106,
  Traff cAttr but onProf le_30D_  ghtedEventDecayedV s  = 107,
  Traff cAttr but onProf le_30D_DecayedV s _W houtAgathaF lter = 108,
  GoodProf leCl ck = 120, // GoodT etC l ck S gnal : D ll T    Threshold >=10s
  AdFavor e = 121, // Favor es f ltered to ads T etFavor e has both organ c and ads Favs

  // AccountFollowW hDelay should only be used by h gh-traff c cl ents and has 1 m n delay
  AccountFollowW hDelay = 122,


  /* not f cat ons based s gnals */
  /* V1: not f cat on cl cks from past 90 days w h negat ve events (reports, d sl kes) be ng f ltered */
  Not f cat onOpenAndCl ck_V1 = 200,

  /*
  negat ve s gnals for f lter
   */
  Negat veEngagedT et d = 901 // t et d for all negat ve engage nts
  Negat veEngagedUser d  = 902 // user d for all negat ve engage nts
  AccountBlock = 903,
  AccountMute = 904,
  // sk p 905 - 906 for Account report abuse / report spam
  // User cl cked dont l ke from past 90 Days
  T etDontL ke = 907
  // User cl cked see fe r on t  recom nded t et from past 90 Days
  T etSeeFe r = 908
  // User cl cked on t  "report t et" opt on  n t  t et caret dropdown  nu from past 90 days
  T etReport = 909

  /*
  devel s gnals
  use t  num > 1000 to test out s gnals under develop nt/ddg
  put   back to t  correct correspond ng Key space (0-1000) before sh p
  */
  GoodT etCl ck_5s = 1001,// GoodT etC l ck S gnal : D ll T    Threshold >=5s
  GoodT etCl ck_10s = 1002,// GoodT etC l ck S gnal : D ll T    Threshold >=10s
  GoodT etCl ck_30s = 1003,// GoodT etC l ck S gnal : D ll T    Threshold >=30s

  GoodProf leCl ck_20s = 1004,// GoodProf leCl ck S gnal : D ll T    Threshold >=20s
  GoodProf leCl ck_30s = 1005,// GoodProf leCl ck S gnal : D ll T    Threshold >=30s

  GoodProf leCl ck_F ltered = 1006, // GoodProf leCl ck S gnal f ltered by blocks and mutes.
  GoodProf leCl ck_20s_F ltered = 1007// GoodProf leCl ck S gnal : D ll T    Threshold >=20s, f ltered  byblocks and mutes.
  GoodProf leCl ck_30s_F ltered = 1008,// GoodProf leCl ck S gnal : D ll T    Threshold >=30s, f ltered by blocks and mutes.

  /*
  Un f ed S gnals
  T se s gnals are a  d to un fy mult ple s gnal fetc s  nto a s ngle response.
  T  m ght be a  alth er way for   retr evals layer to run  nference on.
   */
   T etBasedUn f edUn formS gnal = 1300
   T etBasedUn f edEngage nt  ghtedS gnal = 1301
   T etBasedUn f edQual y  ghtedS gnal = 1302
   ProducerBasedUn f edUn formS gnal = 1303
   ProducerBasedUn f edEngage nt  ghtedS gnal = 1304
   ProducerBasedUn f edQual y  ghtedS gnal = 1305

}

struct S gnal {
  1: requ red S gnalType s gnalType
  2: requ red  64 t  stamp
  3: opt onal  dent f er. nternal d target nternal d
}
