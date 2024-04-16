package com.tw ter.s mclustersann.modules

 mport com.google. nject.Prov des
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.relevance_platform.s mclustersann.mult cluster.ClusterConf gMapper
 mport javax. nject.S ngleton

object ClusterConf gMapperModule extends Tw terModule {
  @S ngleton
  @Prov des
  def prov desClusterConf gMapper(
  ): ClusterConf gMapper = {
    ClusterConf gMapper
  }
}
