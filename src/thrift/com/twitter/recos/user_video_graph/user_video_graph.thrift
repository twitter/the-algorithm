na space java com.tw ter.recos.user_v deo_graph.thr ftjava
na space py gen.tw ter.recos.user_v deo_graph
#@na space scala com.tw ter.recos.user_v deo_graph.thr ftscala
#@na space strato com.tw ter.recos.user_v deo_graph
na space rb UserV deoGraph

 nclude "com/tw ter/recos/features/t et.thr ft"
 nclude "com/tw ter/recos/recos_common.thr ft"


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
 * T  ma n  nterface-def n  on for UserV deoGraph.
 */
serv ce UserV deoGraph {
  RelatedT etResponse t etBasedRelatedT ets (T etBasedRelatedT etRequest request)
  RelatedT etResponse producerBasedRelatedT ets (ProducerBasedRelatedT etRequest request)
  RelatedT etResponse consu rsBasedRelatedT ets (Consu rsBasedRelatedT etRequest request)
}

