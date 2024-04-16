package com.tw ter.follow_recom ndat ons.common.cl ents.graph_feature_serv ce

 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter.follow_recom ndat ons.common.cl ents.common.BaseCl entModule
 mport com.tw ter.graph_feature_serv ce.thr ftscala.{Server => GraphFeatureServ ce}

object GraphFeatureStoreModule
    extends BaseCl entModule[GraphFeatureServ ce. thodPerEndpo nt]
    w h MtlsCl ent {
  overr de val label = "graph_feature_serv ce"
  overr de val dest = "/s/cassowary/graph_feature_serv ce-server"
}
