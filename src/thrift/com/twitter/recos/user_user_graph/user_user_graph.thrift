na space java com.tw ter.recos.user_user_graph.thr ftjava
na space py gen.tw ter.recos.user_user_graph
#@na space scala com.tw ter.recos.user_user_graph.thr ftscala
#@na space strato com.tw ter.recos.user_user_graph
na space rb UserUserGraph

 nclude "com/tw ter/recos/recos_common.thr ft"

enum Recom ndUserD splayLocat on {
  Mag cRecs                 = 0
  Ho T  L ne              = 1
  ConnectTab                = 2
}

struct Recom ndUserRequest {
  1: requ red  64                                           requester d                  // user  d of t  request ng user
  2: requ red Recom ndUserD splayLocat on                  d splayLocat on              // d splay locat on from t  cl ent
  3: requ red map< 64,double>                               seedsW h  ghts             // seed  ds and   ghts used  n left hand s de
  4: opt onal l st< 64>                                     excludedUser ds              // l st of users to exclude from response
  5: opt onal  32                                           maxNumResults                // number of results to return
  6: opt onal  32                                           maxNumSoc alProofs           // number of soc al proofs per recom ndat on
  7: opt onal map<recos_common.UserSoc alProofType,  32>    m nUserPerSoc alProof        // m n mum number of users for each soc al proof type
  8: opt onal l st<recos_common.UserSoc alProofType>        soc alProofTypes             // l st of requ red soc al proof types. Any recom nded user
                                                                                         // must at least have all of t se soc al proof types
  9: opt onal  64                                           maxEdgeEngage ntAge nM ll s // only events created dur ng t  per od are counted
}

struct Recom ndedUser {
  1: requ red  64                                               user d             // user  d of recom nded user
  2: requ red double                                            score              //   ght of t  recom nded user
  3: requ red map<recos_common.UserSoc alProofType, l st< 64>>  soc alProofs       // t  soc al proofs of t  recom nded user
}

struct Recom ndUserResponse {
  1: requ red l st<Recom ndedUser>                             recom ndedUsers         // l st of recom nded users
}

/**
 * T  ma n  nterface-def n  on for UserUserGraph.
 */
serv ce UserUserGraph {
  // G ven a request for recom ndat ons for a spec f c user,
  // return a l st of cand date users along w h t  r soc al proofs
  Recom ndUserResponse recom ndUsers (Recom ndUserRequest request)
}
