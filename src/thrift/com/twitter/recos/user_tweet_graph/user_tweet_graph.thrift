na space java com.tw ter.recos.user_t et_graph.thr ftjava
na space py gen.tw ter.recos.user_t et_graph
#@na space scala com.tw ter.recos.user_t et_graph.thr ftscala
#@na space strato com.tw ter.recos.user_t et_graph
na space rb UserT etGraph

 nclude "com/tw ter/recos/features/t et.thr ft"
 nclude "com/tw ter/recos/recos_common.thr ft"

enum T etType {
  Summary    = 0
  Photo      = 1
  Player     = 2
  Promote    = 3
  Regular    = 4
}

enum Algor hm {
  Salsa              = 0
  SubGraphSalsa      = 1
}

enum Recom ndT etD splayLocat on {
  Ho T  l ne       = 0
   lco Flow        = 1
  NetworkD gest      = 2
  Backf llD gest     = 3
  HttpEndpo nt       = 4
  Poptart            = 5
   nstantT  l ne    = 6
  Explore            = 7
  Mag cRecs          = 8
  LoggedOutProf le   = 9
  LoggedOutPermal nk = 10
  V deoHo           = 11
}

struct Recom ndT etRequest {
  1: requ red  64                                      requester d              // user  d of t  request ng user
  2: requ red Recom ndT etD splayLocat on            d splayLocat on          // d splay locat on from t  cl ent
  3: requ red  32                                      maxResults               // number of suggested results to return
  4: requ red l st< 64>                                excludedT et ds         // l st of t et  ds to exclude from response
  5: requ red map< 64,double>                          seeds                    // seeds used  n salsa random walk
  6: requ red  64                                      t etRecency             // t  t et recency threshold
  7: requ red  32                                      m n nteract on           // m n mum  nteract on threshold
  8: requ red l st<T etType>                           ncludeT etTypes        // summary, photo, player, promote, ot r
  9: requ red double                                   resetProbab l y         // reset probab l y to query node
  10: requ red double                                  queryNode  ghtFract on  // t  percentage of   ghts ass gned to query node  n seed ng
  11: requ red  32                                     numRandomWalks           // number of random walks
  12: requ red  32                                     maxRandomWalkLength      // max random walk length
  13: requ red  32                                     maxSoc alProofS ze       // max soc al proof s ze
  14: requ red Algor hm                               algor hm                // algor hm type
  15: opt onal l st<recos_common.Soc alProofType>      soc alProofTypes         // t  l st of soc al proof types to return
}

struct Recom ndedT et {
  1: requ red  64                                                t et d
  2: requ red double                                             score
  3: opt onal l st< 64>                                          soc alProof              // soc al proof  n aggregate
  4: opt onal map<recos_common.Soc alProofType, l st< 64>>       soc alProofPerType       // soc al proofs per engage nt type
}

struct Recom ndT etResponse {
  1: requ red l st<Recom ndedT et> t ets
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

struct UserT etFeatureResponse {
  1: opt onal double                                favAdam cAdarAvg
  2: opt onal double                                favAdam cAdarMax 
  3: opt onal double                                favLogCos neAvg
  4: opt onal double                                favLogCos neMax
  5: opt onal double                                ret etAdam cAdarAvg
  6: opt onal double                                ret etAdam cAdarMax 
  7: opt onal double                                ret etLogCos neAvg
  8: opt onal double                                ret etLogCos neMax
}

struct RelatedT etRequest {
  1: requ red  64                                   t et d               // or g nal t et  d
  2: requ red RelatedT etD splayLocat on           d splayLocat on       // d splay locat on from t  cl ent
  3: opt onal str ng                                algor hm             // add  onal para ter that t  system can  nterpret
  4: opt onal  64                                   requester d           // user  d of t  request ng user
  5: opt onal  32                                   maxResults            // number of suggested results to return
  6: opt onal l st< 64>                             excludeT et ds       // l st of t et  ds to exclude from response
  7: opt onal  32                                   maxNumNe ghbors
  8: opt onal  32                                   m nNe ghborDegree
  9: opt onal  32                                   maxNumSamplesPerNe ghbor
  10: opt onal  32                                  m nCooccurrence
  11: opt onal  32                                  m nQueryDegree
  12: opt onal double                               maxLo rMult pl cat veDev at on
  13: opt onal double                               maxUpperMult pl cat veDev at on
  14: opt onal bool                                 populateT etFeatures // w t r to populate graph features
  15: opt onal  32                                  m nResultDegree
  16: opt onal l st< 64>                            add  onalT et ds
  17: opt onal double                               m nScore
  18: opt onal  32                                  maxT etAge nH s
}

struct T etBasedRelatedT etRequest {
  1: requ red  64                                   t et d               // query t et  d
  2: opt onal  32                                   maxResults            // number of suggested results to return
  3: opt onal l st< 64>                             excludeT et ds       // l st of t et  ds to exclude from response
  4: opt onal  32                                   m nQueryDegree        // m n degree of query t et
  5: opt onal  32                                   maxNumSamplesPerNe ghbor // max number of sampled users who engaged w h t  query t et
  6: opt onal  32                                   m nCooccurrence       // m n co-occurrence of related t et cand date 
  7: opt onal  32                                   m nResultDegree       // m n degree of related t et cand date 
  8: opt onal double                                m nScore              // m n score of related t et cand date
  9: opt onal  32                                   maxT etAge nH s    // max t et age  n h s of related t et cand date 
}

struct ProducerBasedRelatedT etRequest {
  1: requ red  64                                   producer d            // query producer  d
  2: opt onal  32                                   maxResults            // number of suggested results to return
  3: opt onal l st< 64>                             excludeT et ds       // l st of t et  ds to exclude from response
  4: opt onal  32                                   m nQueryDegree        // m n degree of query producer, e.g. number of follo rs
  5: opt onal  32                                   maxNumFollo rs       // max number of sampled users who follow t  query producer 
  6: opt onal  32                                   m nCooccurrence       // m n co-occurrence of related t et cand date 
  7: opt onal  32                                   m nResultDegree       // m n degree of related t et cand date 
  8: opt onal double                                m nScore              // m n score of related t et cand date
  9: opt onal  32                                   maxT etAge nH s    // max t et age  n h s of related t et cand date 
}

struct Consu rsBasedRelatedT etRequest {
  1: requ red l st< 64>                             consu rSeedSet       // query consu r user d set 
  2: opt onal  32                                   maxResults            // number of suggested results to return
  3: opt onal l st< 64>                             excludeT et ds       // l st of t et  ds to exclude from response 
  4: opt onal  32                                   m nCooccurrence       // m n co-occurrence of related t et cand date 
  5: opt onal  32                                   m nResultDegree       // m n degree of related t et cand date 
  6: opt onal double                                m nScore              // m n score of related t et cand date
  7: opt onal  32                                   maxT etAge nH s    // max t et age  n h s of related t et cand date 
}

struct RelatedT et {
  1: requ red  64                          t et d
  2: requ red double                       score
  3: opt onal t et.GraphFeaturesForT et  relatedT etGraphFeatures
}

struct RelatedT etResponse {
  1: requ red l st<RelatedT et>           t ets
  2: opt onal t et.GraphFeaturesForQuery  queryT etGraphFeatures
}

/**
 * T  ma n  nterface-def n  on for UserT etGraph.
 */
serv ce UserT etGraph {
  Recom ndT etResponse recom ndT ets (Recom ndT etRequest request)
  recos_common.GetRecentEdgesResponse getLeftNodeEdges (recos_common.GetRecentEdgesRequest request)
  recos_common.Node nfo getR ghtNode ( 64 node)
  RelatedT etResponse relatedT ets (RelatedT etRequest request)
  RelatedT etResponse t etBasedRelatedT ets (T etBasedRelatedT etRequest request)
  RelatedT etResponse producerBasedRelatedT ets (ProducerBasedRelatedT etRequest request)
  RelatedT etResponse consu rsBasedRelatedT ets (Consu rsBasedRelatedT etRequest request)
  UserT etFeatureResponse userT etFeatures (1: requ red  64 user d, 2: requ red  64 t et d)
}

