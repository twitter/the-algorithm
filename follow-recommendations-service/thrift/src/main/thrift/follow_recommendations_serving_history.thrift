na space java com.tw ter.follow_recom ndat ons.thr ftjava
#@na space scala com.tw ter.follow_recom ndat ons.thr ftscala
#@na space strato com.tw ter.follow_recom ndat ons

// struct used for stor ng t   tory of comput ng and serv ng of recom ndat ons to a user
struct FollowRecom ndat onsServ ng tory {
  1: requ red  64 lastComputat onT  Ms (personalDataType = 'Pr vateT  stamp')
  2: requ red  64 lastServ ngT  Ms (personalDataType = 'Pr vateT  stamp')
}(pers sted='true', hasPersonalData='true')
