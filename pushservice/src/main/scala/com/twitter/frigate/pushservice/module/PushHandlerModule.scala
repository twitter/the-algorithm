package com.tw ter.fr gate.pushserv ce.module

 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.pushserv ce.target.LoggedOutPushTargetUserBu lder
 mport com.tw ter.fr gate.pushserv ce.refresh_handler.RefreshForPushHandler
 mport com.tw ter.fr gate.pushserv ce.conf g.DeployConf g
 mport com.tw ter.fr gate.pushserv ce.send_handler.SendHandler
 mport com.tw ter.fr gate.pushserv ce.take.cand date_val dator.RFPHCand dateVal dator
 mport com.tw ter.fr gate.pushserv ce.take.cand date_val dator.SendHandlerPostCand dateVal dator
 mport com.tw ter.fr gate.pushserv ce.take.cand date_val dator.SendHandlerPreCand dateVal dator
 mport com.tw ter.fr gate.pushserv ce.refresh_handler.LoggedOutRefreshForPushHandler
 mport com.tw ter.fr gate.pushserv ce.take.SendHandlerNot f er
 mport com.tw ter.fr gate.pushserv ce.target.PushTargetUserBu lder
 mport com.tw ter. nject.Tw terModule

object PushHandlerModule extends Tw terModule {

  @Prov des
  @S ngleton
  def prov desRefreshForPushHandler(
    pushTargetUserBu lder: PushTargetUserBu lder,
    conf g: DeployConf g,
    statsRece ver: StatsRece ver
  ): RefreshForPushHandler = {
    new RefreshForPushHandler(
      pushTargetUserBu lder = pushTargetUserBu lder,
      candS ceGenerator = conf g.cand dateS ceGenerator,
      rfphRanker = conf g.rfphRanker,
      cand dateHydrator = conf g.cand dateHydrator,
      cand dateVal dator = new RFPHCand dateVal dator(conf g),
      rfphTakeStepUt l = conf g.rfphTakeStepUt l,
      rfphRestr ctStep = conf g.rfphRestr ctStep,
      rfphNot f er = conf g.rfphNot f er,
      rfphStatsRecorder = conf g.rfphStatsRecorder,
      mrRequestScr berNode = conf g.mrRequestScr berNode,
      rfphFeatureHydrator = conf g.rfphFeatureHydrator,
      rfphPrerankF lter = conf g.rfphPrerankF lter,
      rfphL ghtRanker = conf g.rfphL ghtRanker
    )(statsRece ver)
  }

  @Prov des
  @S ngleton
  def prov desSendHandler(
    pushTargetUserBu lder: PushTargetUserBu lder,
    conf g: DeployConf g,
    statsRece ver: StatsRece ver
  ): SendHandler = {
    new SendHandler(
      pushTargetUserBu lder,
      new SendHandlerPreCand dateVal dator(conf g),
      new SendHandlerPostCand dateVal dator(conf g),
      new SendHandlerNot f er(conf g.cand dateNot f er, statsRece ver.scope("SendHandlerNot f er")),
      conf g.sendHandlerCand dateHydrator,
      conf g.featureHydrator,
      conf g.sendHandlerPred cateUt l,
      conf g.mrRequestScr berNode)(statsRece ver, conf g)
  }

  @Prov des
  @S ngleton
  def prov desLoggedOutRefreshForPushHandler(
    loPushTargetUserBu lder: LoggedOutPushTargetUserBu lder,
    conf g: DeployConf g,
    statsRece ver: StatsRece ver
  ): LoggedOutRefreshForPushHandler = {
    new LoggedOutRefreshForPushHandler(
      loPushTargetUserBu lder = loPushTargetUserBu lder,
      loPushCand dateS ceGenerator = conf g.loCand dateS ceGenerator,
      cand dateHydrator = conf g.cand dateHydrator,
      loRanker = conf g.loggedOutRFPHRanker,
      loRfphNot f er = conf g.loRfphNot f er,
      loMrRequestScr berNode = conf g.loggedOutMrRequestScr berNode,
    )(statsRece ver)
  }
}
