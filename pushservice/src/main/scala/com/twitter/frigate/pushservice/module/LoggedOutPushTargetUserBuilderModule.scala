package com.tw ter.fr gate.pushserv ce.module

 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter.dec der.Dec der
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.pushserv ce.target.LoggedOutPushTargetUserBu lder
 mport com.tw ter.fr gate.pushserv ce.conf g.DeployConf g
 mport com.tw ter. nject.Tw terModule

object LoggedOutPushTargetUserBu lderModule extends Tw terModule {

  @Prov des
  @S ngleton
  def prov desLoggedOutPushTargetUserBu lder(
    dec der: Dec der,
    conf g: DeployConf g,
    statsRece ver: StatsRece ver
  ): LoggedOutPushTargetUserBu lder = {
    LoggedOutPushTargetUserBu lder(
       toryStore = conf g.loggedOut toryStore,
       nputDec der = dec der,
       nputAbDec der = conf g.abDec der,
      loggedOutPush nfoStore = conf g.loggedOutPush nfoStore
    )(statsRece ver)
  }
}
