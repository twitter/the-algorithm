na space java com.tw ter.recos.recos_common.thr ftjava
na space py gen.tw ter.recos.recos_common
#@na space scala com.tw ter.recos.recos_common.thr ftscala
#@na space strato com.tw ter.recos.recos_common
na space rb Recos

// Soc al proof types for user mo nt recom ndat ons
enum Mo ntSoc alProofType {
  PUBL SH         = 0
  L KE            = 1
  CAPSULE_OPEN    = 2
}

// Soc al proof types for t et/ent y recom ndat ons
enum Soc alProofType {
  CL CK           = 0
  FAVOR TE        = 1
  RETWEET         = 2
  REPLY           = 3
  TWEET           = 4
   S_MENT ONED    = 5
   S_MED ATAGGED  = 6
  QUOTE           = 7
}

struct Soc alProof {
  1: requ red  64 user d
  2: opt onal  64  tadata
}

// Soc al proof types for user recom ndat ons
enum UserSoc alProofType {
  FOLLOW     = 0
  MENT ON    = 1
  MED ATAG   = 2
}

struct GetRecentEdgesRequest {
  1: requ red  64                          request d        // t  node to query from
  2: opt onal  32                          maxNumEdges      // t  max number of recent edges
}

struct RecentEdge {
  1: requ red  64                          node d           // t  connect ng node  d
  2: requ red Soc alProofType              engage ntType   // t  engage nt type of t  edge
}

struct GetRecentEdgesResponse {
  1: requ red l st<RecentEdge>             edges            // t  _ most recent edges from t  query node
}

struct Node nfo {
  1: requ red l st< 64> edges
}
