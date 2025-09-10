package com.twitter.tweet_mixer.functional_component.side_effect

import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.product_mixer.component_library.side_effect.KafkaPublishingSideEffect
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.marshalling.HasMarshalling
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.ml.kafka.serde.ThriftStructSafeSerde
import com.twitter.tweet_mixer.feature.PredictionRequestIdFeature
import com.twitter.tweet_mixer.feature.ScoreFeature
import com.twitter.tweet_mixer.feature.SourceSignalFeature
import com.twitter.tweet_mixer.functional_component.hydrator.SignalInfoFeature
import com.twitter.tweet_mixer.model.request.HomeRecommendedTweetsProduct
import com.twitter.tweet_mixer.model.request.VideoRecommendedTweetsProduct
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ScribeRetrievedCandidatesParam
import com.twitter.tweet_mixer.thriftscala.CandidateInfo
import com.twitter.tweet_mixer.thriftscala.Product
import com.twitter.tweet_mixer.thriftscala.RequestInfo
import com.twitter.tweet_mixer.thriftscala.SignalInfo
import com.twitter.tweet_mixer.thriftscala.ServedCandidatesForRequest
import com.twitter.tweet_mixer.utils.CandidateSourceUtil
import javax.inject.Inject
import javax.inject.Singleton
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.Serializer

@Singleton
class ScribeServedCandidatesSideEffectFactory @Inject() (
  injectedServiceIdentifier: ServiceIdentifier) {
  def build(identifierPrefix: String): ScribeServedCandidatesSideEffect =
    ScribeServedCandidatesSideEffect(injectedServiceIdentifier, identifierPrefix)
}

case class ScribeServedCandidatesSideEffect(
  serviceIdentifier: ServiceIdentifier,
  identifierPrefix: String)
    extends KafkaPublishingSideEffect[
      RequestInfo,
      ServedCandidatesForRequest,
      PipelineQuery,
      HasMarshalling
    ]
    with Conditionally[PipelineQuery, HasMarshalling] {

  override val bootstrapServer: String = "/s/kafka/timeline:kafka-tls"
  private val kafkaTopic: String = "tweet_mixer_retrieved_candidates"
  override val keySerde: Serializer[RequestInfo] =
    ThriftStructSafeSerde.Thrift[RequestInfo]().serializer
  override val valueSerde: Serializer[ServedCandidatesForRequest] =
    ThriftStructSafeSerde.Thrift[ServedCandidatesForRequest]().serializer
  override val clientId: String = "tweet_mixer_served_candidate_producer"

  override val identifier: SideEffectIdentifier = SideEffectIdentifier("ScribeServedCandidates")

  override def onlyIf(
    query: PipelineQuery,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: HasMarshalling
  ): Boolean = {
    serviceIdentifier.environment.toLowerCase == "prod" &&
    query.params(ScribeRetrievedCandidatesParam) &&
    selectedCandidates.nonEmpty
  }

  override def buildRecords(
    query: PipelineQuery,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: HasMarshalling
  ): Seq[ProducerRecord[RequestInfo, ServedCandidatesForRequest]] = {
    val predictionRequestId = query.features.flatMap(_.getOrElse(PredictionRequestIdFeature, None))
    val requestInfo = RequestInfo(query.getRequiredUserId, getProduct(query), predictionRequestId)
    val selectedCandidatesInfo = selectedCandidates.map(getCandidateInfo)
    val remainingCandidatesInfo = (remainingCandidates ++ droppedCandidates).map(getCandidateInfo)
    Seq(
      new ProducerRecord(
        kafkaTopic,
        requestInfo,
        ServedCandidatesForRequest(
          requestInfo,
          selectedCandidatesInfo,
          selectedCandidatesInfo ++ remainingCandidatesInfo
        )
      )
    )
  }

  private def getProduct(query: PipelineQuery): Product = {
    query.product match {
      case HomeRecommendedTweetsProduct => Product.HomeRecommendedTweets
      case VideoRecommendedTweetsProduct => Product.VideoRecommendedTweets
      case _ => throw new IllegalArgumentException(s"Unsupported product type ${query.product}")
    }
  }

  private def getCandidateInfo(candidate: CandidateWithDetails): CandidateInfo = {
    val sourceSignalInfo = candidate.features
      .getOrElse(SignalInfoFeature, Seq.empty)
    val sourceSignal = candidate.features.getTry(SourceSignalFeature).toOption

    CandidateInfo(
      tweetId = candidate.candidateIdLong,
      servedType = CandidateSourceUtil.getServedType(identifierPrefix, candidate.source.name),
      score = candidate.features.getTry(ScoreFeature).toOption,
      sourceSignalId = sourceSignal,
      sourceSignalTypeInfo = Some(
        sourceSignalInfo.map { s =>
          SignalInfo(signalType = s.signalType, timestamp = s.sourceEventTime.map(_.inMillis))
        }
      )
    )
  }
}
