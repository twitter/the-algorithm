package com.twitter.tweet_mixer.candidate_source.uss_service

import com.twitter.product_mixer.core.functional_component.candidate_source.strato.StratoKeyFetcherSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.strato.client.Fetcher
import com.twitter.strato.generated.client.recommendations.user_signal_service.SignalsClientColumn
import com.twitter.usersignalservice.thriftscala.SignalType
import com.twitter.usersignalservice.thriftscala.{Signal => UssSignal}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class USSSignalCandidateSource @Inject() (
  signalsClientColumn: SignalsClientColumn)
    extends StratoKeyFetcherSource[
      SignalsClientColumn.Key,
      SignalsClientColumn.Value,
      (SignalType, Seq[UssSignal])
    ] {

  override val fetcher: Fetcher[SignalsClientColumn.Key, Unit, SignalsClientColumn.Value] =
    signalsClientColumn.fetcher

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier("USSSignal")

  override protected def stratoResultTransformer(
    stratoResult: signalsClientColumn.Value
  ): Seq[(SignalType, Seq[UssSignal])] = {
    stratoResult.signalResponse.toSeq.map {
      case (signalType, signals) =>
        (signalType, signals)
    }
  }
}
