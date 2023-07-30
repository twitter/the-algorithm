package com.X.frigate.pushservice.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.pushservice.target.LoggedOutPushTargetUserBuilder
import com.X.frigate.pushservice.refresh_handler.RefreshForPushHandler
import com.X.frigate.pushservice.config.DeployConfig
import com.X.frigate.pushservice.send_handler.SendHandler
import com.X.frigate.pushservice.take.candidate_validator.RFPHCandidateValidator
import com.X.frigate.pushservice.take.candidate_validator.SendHandlerPostCandidateValidator
import com.X.frigate.pushservice.take.candidate_validator.SendHandlerPreCandidateValidator
import com.X.frigate.pushservice.refresh_handler.LoggedOutRefreshForPushHandler
import com.X.frigate.pushservice.take.SendHandlerNotifier
import com.X.frigate.pushservice.target.PushTargetUserBuilder
import com.X.inject.XModule

object PushHandlerModule extends XModule {

  @Provides
  @Singleton
  def providesRefreshForPushHandler(
    pushTargetUserBuilder: PushTargetUserBuilder,
    config: DeployConfig,
    statsReceiver: StatsReceiver
  ): RefreshForPushHandler = {
    new RefreshForPushHandler(
      pushTargetUserBuilder = pushTargetUserBuilder,
      candSourceGenerator = config.candidateSourceGenerator,
      rfphRanker = config.rfphRanker,
      candidateHydrator = config.candidateHydrator,
      candidateValidator = new RFPHCandidateValidator(config),
      rfphTakeStepUtil = config.rfphTakeStepUtil,
      rfphRestrictStep = config.rfphRestrictStep,
      rfphNotifier = config.rfphNotifier,
      rfphStatsRecorder = config.rfphStatsRecorder,
      mrRequestScriberNode = config.mrRequestScriberNode,
      rfphFeatureHydrator = config.rfphFeatureHydrator,
      rfphPrerankFilter = config.rfphPrerankFilter,
      rfphLightRanker = config.rfphLightRanker
    )(statsReceiver)
  }

  @Provides
  @Singleton
  def providesSendHandler(
    pushTargetUserBuilder: PushTargetUserBuilder,
    config: DeployConfig,
    statsReceiver: StatsReceiver
  ): SendHandler = {
    new SendHandler(
      pushTargetUserBuilder,
      new SendHandlerPreCandidateValidator(config),
      new SendHandlerPostCandidateValidator(config),
      new SendHandlerNotifier(config.candidateNotifier, statsReceiver.scope("SendHandlerNotifier")),
      config.sendHandlerCandidateHydrator,
      config.featureHydrator,
      config.sendHandlerPredicateUtil,
      config.mrRequestScriberNode)(statsReceiver, config)
  }

  @Provides
  @Singleton
  def providesLoggedOutRefreshForPushHandler(
    loPushTargetUserBuilder: LoggedOutPushTargetUserBuilder,
    config: DeployConfig,
    statsReceiver: StatsReceiver
  ): LoggedOutRefreshForPushHandler = {
    new LoggedOutRefreshForPushHandler(
      loPushTargetUserBuilder = loPushTargetUserBuilder,
      loPushCandidateSourceGenerator = config.loCandidateSourceGenerator,
      candidateHydrator = config.candidateHydrator,
      loRanker = config.loggedOutRFPHRanker,
      loRfphNotifier = config.loRfphNotifier,
      loMrRequestScriberNode = config.loggedOutMrRequestScriberNode,
    )(statsReceiver)
  }
}
