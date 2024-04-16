na space java com.tw ter.recos.user_t et_ent y_graph.thr ftjava
na space py gen.tw ter.recos.user_t et_ent y_graph
#@na space scala com.tw ter.recos.user_t et_ent y_graph.thr ftscala
#@na space strato com.tw ter.recos.user_t et_ent y_graph
na space rb UserT etEnt yGraph

 nclude "com/tw ter/recos/features/t et.thr ft"
 nclude "com/tw ter/recos/recos_common.thr ft"

enum T etType {
  Summary    = 0
  Photo      = 1
  Player     = 2
  Promote    = 3
  Regular    = 4
}

enum Recom ndat onType {
  T et      = 0
  Hashtag    = 1 // Ent y type
  Url        = 2 // Ent y type
}

enum T etEnt yD splayLocat on {
  Mag cRecs                 = 0
  Ho T  l ne              = 1
  H ghl ghtsEma lUrlRecs    = 2
  H ghl ghts                = 3
  Ema l                     = 4
  Mag cRecsF1               = 5
  Gu deV deo                = 6
  Mag cRecsRareT et        = 7
  TopArt cles               = 8 // Tw ter Blue most shared art cles page
  ContentRecom nder        = 9
  Fr gateNTab               = 10
}

struct Recom ndT etEnt yRequest {
  // user  d of t  request ng user
  1: requ red  64                                        requester d

  // d splay locat on from t  cl ent
  2: requ red T etEnt yD splayLocat on                 d splayLocat on

  // t  recom ndat on ent y types to return
  3: requ red l st<Recom ndat onType>                   recom ndat onTypes

  // seed  ds and   ghts used  n left hand s de
  4: requ red map< 64,double>                            seedsW h  ghts

  // number of suggested results per recom ndat on ent y type
  5: opt onal map<Recom ndat onType,  32>               maxResultsByType

  // t  t et age threshold  n m ll seconds
  6: opt onal  64                                        maxT etAge nM ll s

  // l st of t et  ds to exclude from response
  7: opt onal l st< 64>                                  excludedT et ds

  // max user soc al proof s ze per engage nt type
  8: opt onal  32                                        maxUserSoc alProofS ze

  // max t et soc al proof s ze per user
  9: opt onal  32                                        maxT etSoc alProofS ze

  // m n user soc al proof s ze per each recom ndat on ent y type
  10: opt onal map<Recom ndat onType,  32>              m nUserSoc alProofS zes

  // summary, photo, player, promote, regular
  11: opt onal l st<T etType>                           t etTypes

  // t  l st of soc al proof types to return
  12: opt onal l st<recos_common.Soc alProofType>        soc alProofTypes

  // set of groups of soc al proof types allo d to be comb ned for compar son aga nst m nUserSoc alProofS zes.
  // e.g.  f t   nput  s set<l st<T et, Favor e>>, t n t  un on of those two soc al proofs
  // w ll be compared aga nst t  m nUserSoc alProofS ze of T et Recom ndat onType.
  13: opt onal set<l st<recos_common.Soc alProofType>>   soc alProofTypeUn ons

  // t  recom ndat ons returned  n t  response are authored by t  follow ng users
  14: opt onal set< 64>                                  t etAuthors

  // t  t et engage nt age threshold  n m ll seconds
  15: opt onal  64                                       maxEngage ntAge nM ll s

  // t  recom ndat ons w ll not return any t et authored by t  follow ng users
  16: opt onal set< 64>                                  excludedT etAuthors
}

struct T etRecom ndat on {
  // t et  d
  1: requ red  64                                                               t et d
  // sum of   ghts of seed users who engaged w h t  t et.
  //  f a user engaged w h t  sa  t et tw ce, l ked   and ret eted  , t n  / r   ght was counted tw ce.
  2: requ red double                                                            score
    // user soc al proofs per engage nt type
  3: requ red map<recos_common.Soc alProofType, l st< 64>>                      soc alProofByType
  // user soc al proofs along w h edge  tadata per engage nt type. T  value of t  map  s a l st of Soc alProofs.
  4: opt onal map<recos_common.Soc alProofType, l st<recos_common.Soc alProof>> soc alProofs
}

struct HashtagRecom ndat on {
  1: requ red  32                                        d                   //  nteger hashtag  d, wh ch w ll be converted to hashtag str ng by cl ent l brary.
  2: requ red double                                    score
  // sum of   ghts of seed users who engaged w h t  hashtag.
  //  f a user engaged w h t  sa  hashtag tw ce, l ked   and ret eted  , t n  / r   ght was counted tw ce.
  3: requ red map<recos_common.Soc alProofType, map< 64, l st< 64>>> soc alProofByType
  // user and t et soc al proofs per engage nt type. T  key of  nner map  s user  d, and t  value of  nner map  s
  // a l st of t et  ds that t  user engaged w h.
}

struct UrlRecom ndat on {
  1: requ red  32                                        d                   //  nteger url  d, wh ch w ll be converted to url str ng by cl ent l brary.
  2: requ red double                                    score
  // sum of   ghts of seed users who engaged w h t  url.
  //  f a user engaged w h t  sa  url tw ce, l ked   and ret eted  , t n  / r   ght was counted tw ce.
  3: requ red map<recos_common.Soc alProofType, map< 64, l st< 64>>> soc alProofByType
  // user and t et soc al proofs per engage nt type. T  key of  nner map  s user  d, and t  value of  nner map  s
  // a l st of t et  ds that t  user engaged w h.
}

un on UserT etEnt yRecom ndat onUn on {
  1: T etRecom ndat on t etRec
  2: HashtagRecom ndat on hashtagRec
  3: UrlRecom ndat on urlRec
}

struct Recom ndT etEnt yResponse {
  1: requ red l st<UserT etEnt yRecom ndat onUn on> recom ndat ons
}

struct Soc alProofRequest {
  1: requ red l st< 64>                                   nputT ets             // Only for so  t ets   need requst  s soc al proofs.
  2: requ red map< 64, double>                           seedsW h  ghts        // a set of seed users w h   ghts
  3: opt onal  64                                        requester d             //  d of t  request ng user
  4: opt onal l st<recos_common.Soc alProofType>         soc alProofTypes        // t  l st of soc al proof types to return
}

struct Soc alProofResponse {
  1: requ red l st<T etRecom ndat on> soc alProofResults
}

struct Recom ndat onSoc alProofRequest {
  /**
   * Cl ents can request soc al proof from mult ple recom ndat on types  n a s ngle request.
   * NOTE: Avo d m x ng t et soc al proof requests w h ent y soc al proof requests as t 
   * underly ng l brary call retr eves t se d fferently.
   */
  1: requ red map<Recom ndat onType, set< 64>>           recom ndat on dsForSoc alProof
  // T se w ll be t  only val d LHS nodes used to fetch soc al proof.
  2: requ red map< 64, double>                            seedsW h  ghts
  3: opt onal  64                                         requester d
  // T  l st of val d soc al proof types to return, e.g.   may only want Favor e and T et proofs.
  4: opt onal l st<recos_common.Soc alProofType>          soc alProofTypes
}

struct Recom ndat onSoc alProofResponse {
  1: requ red l st<UserT etEnt yRecom ndat onUn on> soc alProofResults
}

/**
 * T  ma n  nterface-def n  on for UserT etEnt yGraph.
 */
serv ce UserT etEnt yGraph {
  Recom ndT etEnt yResponse recom ndT ets (Recom ndT etEnt yRequest request)

  /**
   * G ven a query user,  s seed users, and a set of  nput t ets, return t  soc al proofs of
   *  nput t ets  f any.
   *
   * Currently t  supports cl ents such as Ema l Recom ndat ons, Mag cRecs, and Ho T  l ne.
   *  n order to avo d  avy m grat on work,   are reta n ng t  endpo nt.
   */
  Soc alProofResponse f ndT etSoc alProofs(Soc alProofRequest request)

  /**
   * F nd soc al proof for t  spec f ed Recom ndat onType g ven a set of  nput  ds of that type.
   * Only f nd soc al proofs from t  spec f ed seed users w h t  spec f ed soc al proof types.
   *
   * Currently t  supports url soc al proof generat on for Gu de.
   *
   * T  endpo nt  s flex ble enough to support soc al proof generat on for all recom ndat on
   * types, and should be used for all future cl ents of t  serv ce.
   */
  Recom ndat onSoc alProofResponse f ndRecom ndat onSoc alProofs(Recom ndat onSoc alProofRequest request)
}

