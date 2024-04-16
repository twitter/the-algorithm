package com.tw ter.fr gate.pushserv ce.module

 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter.abdec der.Logg ngABDec der
 mport com.tw ter.dec der.Dec der
 mport com.tw ter.featuresw c s.v2.FeatureSw c s
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.tunable.StandardTunableMap
 mport com.tw ter.fr gate.pushserv ce.conf g.DeployConf g
 mport com.tw ter.fr gate.pushserv ce.conf g.ProdConf g
 mport com.tw ter.fr gate.pushserv ce.conf g.Stag ngConf g
 mport com.tw ter.fr gate.pushserv ce.params.ShardParams
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.product_m xer.core.module.product_m xer_flags.ProductM xerFlagModule.Conf gRepoLocalPath
 mport com.tw ter.product_m xer.core.module.product_m xer_flags.ProductM xerFlagModule.Serv ceLocal

object DeployConf gModule extends Tw terModule {

  @Prov des
  @S ngleton
  def prov desDeployConf g(
    @Flag(FlagNa .numShards) numShards:  nt,
    @Flag(FlagNa .shard d) shard d:  nt,
    @Flag(FlagNa . s n mCac Off)  n mCac Off: Boolean,
    @Flag(Serv ceLocal)  sServ ceLocal: Boolean,
    @Flag(Conf gRepoLocalPath) localConf gRepoPath: Str ng,
    serv ce dent f er: Serv ce dent f er,
    dec der: Dec der,
    abDec der: Logg ngABDec der,
    featureSw c s: FeatureSw c s,
    statsRece ver: StatsRece ver
  ): DeployConf g = {
    val tunableMap =  f (serv ce dent f er.serv ce.conta ns("canary")) {
      StandardTunableMap( d = "fr gate-pushserv ce-canary")
    } else { StandardTunableMap( d = serv ce dent f er.serv ce) }
    val shardParams = ShardParams(numShards, shard d)
    serv ce dent f er.env ron nt match {
      case "devel" | "stag ng" =>
        Stag ngConf g(
           sServ ceLocal =  sServ ceLocal,
          localConf gRepoPath = localConf gRepoPath,
           n mCac Off =  n mCac Off,
          dec der = dec der,
          abDec der = abDec der,
          featureSw c s = featureSw c s,
          serv ce dent f er = serv ce dent f er,
          tunableMap = tunableMap,
          shardParams = shardParams
        )(statsRece ver)
      case "prod" =>
        ProdConf g(
           sServ ceLocal =  sServ ceLocal,
          localConf gRepoPath = localConf gRepoPath,
           n mCac Off =  n mCac Off,
          dec der = dec der,
          abDec der = abDec der,
          featureSw c s = featureSw c s,
          serv ce dent f er = serv ce dent f er,
          tunableMap = tunableMap,
          shardParams = shardParams
        )(statsRece ver)
      case env => throw new Except on(s"Unknown env ron nt $env")
    }
  }
}
