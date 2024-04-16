package com.tw ter.s mclustersann.modules

 mport com.google. nject.Prov des
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.relevance_platform.s mclustersann.mult cluster.ClusterConf g
 mport com.tw ter.relevance_platform.s mclustersann.mult cluster.ClusterConf gMapper
 mport com.tw ter.s mclustersann.except ons.M ss ngClusterConf gForS mClustersAnnVar antExcept on
 mport javax. nject.S ngleton

object ClusterConf gModule extends Tw terModule {
  @S ngleton
  @Prov des
  def prov desClusterConf g(
    serv ce dent f er: Serv ce dent f er,
    clusterConf gMapper: ClusterConf gMapper
  ): ClusterConf g = {
    val serv ceNa  = serv ce dent f er.serv ce

    clusterConf gMapper.getClusterConf g(serv ceNa ) match {
      case So (conf g) => conf g
      case None => throw M ss ngClusterConf gForS mClustersAnnVar antExcept on(serv ceNa )
    }
  }
}
