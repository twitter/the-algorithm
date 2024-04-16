na space java com.tw ter.recos.thr ftjava
#@na space scala com.tw ter.recos.thr ftscala
na space rb Recos

 nclude "com/tw ter/recos/features/t et.thr ft"

enum Recom ndT etD splayLocat on {
  Ho T  l ne       = 0
  Peek               = 1
   lco Flow        = 2
  NetworkD gest      = 3
  Backf llD gest     = 4
  NetworkD gestExp1  = 5
  NetworkD gestExp2  = 6 // deprecated
  NetworkD gestExp3  = 7 // deprecated
  HttpEndpo nt       = 8
  Ho T  l ne1      = 9
  Ho T  l ne2      = 10
  Ho T  l ne3      = 11
  Ho T  l ne4      = 12
  Poptart            = 13
  NetworkD gestExp4  = 14
  NetworkD gestExp5  = 15
  NetworkD gestExp6  = 16
  NetworkD gestExp7  = 17
  NetworkD gestExp8  = 18
  NetworkD gestExp9  = 19
   nstantT  l ne1   = 20 // AB1 + wh el st
   nstantT  l ne2   = 21 // AB1 + !wh el st
   nstantT  l ne3   = 22 // AB2 + wh el st
   nstantT  l ne4   = 23 // AB2 + !wh el st
  Backf llD gestAct ve  = 24 // deprecated
  Backf llD gestDormant = 25 // deprecated
  ExploreUS             = 26 // deprecated
  ExploreBR             = 27 // deprecated
  Explore N             = 28 // deprecated
  ExploreES             = 29 // deprecated
  ExploreJP             = 30 // deprecated
  Mag cRecs             = 31
  Mag cRecs1            = 32
  Mag cRecs2            = 33
  Mag cRecs3            = 34
  SMSD scover           = 35
  FastFollo r          = 36
   nstantT  l ne5      = 37 // for  nstant t  l ne exper  nt
   nstantT  l ne6      = 38 // for  nstant t  l ne exper  nt
   nstantT  l ne7      = 39 // for  nstant t  l ne exper  nt
   nstantT  l ne8      = 40 // for  nstant t  l ne exper  nt
  LoggedOutProf le      = 41
  LoggedOutPermal nk    = 42
  Poptart2              = 43
}

enum RelatedT etD splayLocat on {
  Permal nk       = 0
  Permal nk1      = 1
  Mob lePermal nk = 2
  Permal nk3      = 3
  Permal nk4      = 4
  RelatedT ets   = 5
  RelatedT ets1  = 6
  RelatedT ets2  = 7
  RelatedT ets3  = 8
  RelatedT ets4  = 9
  LoggedOutProf le = 10
  LoggedOutPermal nk = 11
}

enum DDGBucket {
  Control           = 0
  Treat nt         = 1
  None              = 2
}

struct Recom ndT etRequest {
  1: requ red  64                                   requester d           // user  d of t  request ng user
  2: requ red Recom ndT etD splayLocat on         d splayLocat on       // d splay locat on from t  cl ent
  3: opt onal  64                                   cl ent d              // tw ter ap  cl ent  d
  4: opt onal  32                                   maxResults            // number of suggested results to return
  5: opt onal l st< 64>                             excludedT et ds      // l st of t et  ds to exclude from response
  6: opt onal l st< 64>                             excludedAuthor ds     // l st of author  ds to exclude from response
  7: opt onal  64                                   guest d               // guest d
  8: opt onal str ng                                languageCode          // Language code
  9: opt onal str ng                                countryCode           // Country code
  10: opt onal str ng                                pAddress             //  p address of t  user
  11: opt onal str ng                               dev ce d              // ud d/uu d of dev ce
  12: opt onal bool                                 populateT etFeatures // w t r to populate t et features. Recom ndedT et.t etFeatures  n t  response w ll only be populated  f t   s set.
}

struct Bucket {
  1: requ red str ng                                exper  ntNa         // na  of exper  nt (or not). exper  nt could be product on or whatever f s
  2: requ red str ng                                bucket                // na  of bucket (may or may not be a DDG bucket, e.g., product on)
}

struct RelatedT etRequest {
  1: requ red  64                                   t et d               // or g nal t et  d
  2: requ red RelatedT etD splayLocat on           d splayLocat on       // d splay locat on from t  cl ent
  3: opt onal  64                                   cl ent d              // tw ter ap  cl ent  d
  4: opt onal  64                                   requester d           // user  d of t  request ng user
  5: opt onal  32                                   maxResults            // number of suggested results to return
  6: opt onal l st< 64>                             excludeT et ds       // l st of t et  ds to exclude from response
  7: opt onal l st< 64>                             excludedAuthor ds     // l st of author  ds to exclude from response
  8: opt onal  64                                   guest d               // guest d
  9: opt onal str ng                                languageCode          // Language code
  10: opt onal str ng                               countryCode           // Country code
  11: opt onal str ng                                pAddress             //  p address of t  user
  12: opt onal str ng                               dev ce d              // ud d/uu d of dev ce
  13: opt onal str ng                               userAgent             // userAgent of t  request ng user
}

enum Soc alProofType {
  Follo dBy = 1,
  Favor edBy = 2,
  Ret etedBy = 3,
  S m larTo = 4,
  RESERVED_2 = 5,
  RESERVED_3 = 6,
  RESERVED_4 = 7,
  RESERVED_5 = 8,
  RESERVED_6 = 9,
  RESERVED_7 = 10
}

enum Algor hm {
  Salsa = 1,
  PastEma lCl cks = 2,
  S m larToEma lCl cks = 3,
  PastCl entEventCl cks = 4,
  V News = 5,
  StrongT eScor ng = 6,
  PollsFromGraph = 7,
  PollsBasedOnGeo = 8,
  RESERVED_9 = 9,
  RESERVED_10 = 10,
  RESERVED_11 = 11,
}

struct Recom ndedT et {
  1: requ red  64                            t et d
  2: requ red  64                            author d
  3: requ red l st< 64>                      soc alProof
  4: requ red str ng                         feedbackToken
  5: opt onal l st< 64>                      favBy          // opt onally prov de a l st of users who fav'ed t  t et  f ex st
  6: opt onal t et.Recom ndedT etFeatures t etFeatures  // t  features of a recom nded t et
  7: opt onal Soc alProofType                soc alProofType // type of soc al proof. favBy should be deprecated soon
  8: opt onal str ng                         soc alProofOverr de // should be set only for DDGs, for en-only exper  nts. Soc alProofType  s  gnored w n t  f eld  s set
  9: opt onal Algor hm                      algor hm // algor hm used 
  10: opt onal double                        score     // score
  11: opt onal bool                           sFollow ngAuthor // true  f t  target user follows t  author of t  t et 
}

struct RelatedT et {
  1: requ red  64                  t et d
  2: requ red  64                  author d
  3: requ red double               score
  4: requ red str ng               feedbackToken
}

struct Recom ndT etResponse {
  1: requ red l st<Recom ndedT et> t ets
  2: opt onal DDGBucket              bucket                // deprecated
  3: opt onal Bucket                 ass gnedBucket        // for cl ent-s de exper  ntat on
}

struct RelatedT etResponse {
  1: requ red l st<RelatedT et>   t ets                                 // a l st of related t ets
  2: opt onal Bucket               ass gnedBucket                         // t  bucket used for treat nt
}

/**
 * T  ma n  nterface-def n  on for Recos.
 */
serv ce Recos {
  Recom ndT etResponse recom ndT ets  (Recom ndT etRequest request)
  RelatedT etResponse relatedT ets  (RelatedT etRequest request)
}
