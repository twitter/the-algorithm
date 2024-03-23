package com.ExTwitter.home_mixer.functional_component.decorator.builder

import com.ExTwitter.bijection.Base64String
import com.ExTwitter.bijection.scrooge.BinaryScalaCodec
import com.ExTwitter.bijection.{Injection => Serializer}
import com.ExTwitter.finagle.tracing.Trace
import com.ExTwitter.home_mixer.model.HomeFeatures.CandidateSourceIdFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.PositionFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.SuggestTypeFeature
import com.ExTwitter.joinkey.context.RequestJoinKeyContext
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventDetailsBuilder
import com.ExTwitter.product_mixer.core.model.common.UniversalNoun
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventDetails
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.metadata.TimelinesDetails
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.suggests.controller_data.Home
import com.ExTwitter.suggests.controller_data.TweetTypeGenerator
import com.ExTwitter.suggests.controller_data.home_tweets.v1.{thriftscala => v1ht}
import com.ExTwitter.suggests.controller_data.home_tweets.{thriftscala => ht}
import com.ExTwitter.suggests.controller_data.thriftscala.ControllerData
import com.ExTwitter.suggests.controller_data.v2.thriftscala.{ControllerData => ControllerDataV2}

object HomeClientEventDetailsBuilder {
  implicit val ByteSerializer: Serializer[ControllerData, Array[Byte]] =
    BinaryScalaCodec(ControllerData)

  val ControllerDataSerializer: Serializer[ControllerData, String] =
    Serializer.connect[ControllerData, Array[Byte], Base64String, String]

  /**
   * define getRequestJoinId as a method(def) rather than a val because each new request
   * needs to call the context to update the id.
   */
  private def getRequestJoinId(): Option[Long] =
    RequestJoinKeyContext.current.flatMap(_.requestJoinId)
}

case class HomeClientEventDetailsBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]](
) extends BaseClientEventDetailsBuilder[Query, Candidate]
    with TweetTypeGenerator[FeatureMap] {

  import HomeClientEventDetailsBuilder._

  override def apply(
    query: Query,
    candidate: Candidate,
    candidateFeatures: FeatureMap
  ): Option[ClientEventDetails] = {

    val tweetTypesBitmaps = mkTweetTypesBitmaps(
      Home.TweetTypeIdxMap,
      HomeTweetTypePredicates.PredicateMap,
      candidateFeatures)

    val tweetTypesListBytes = mkItemTypesBitmapsV2(
      Home.TweetTypeIdxMap,
      HomeTweetTypePredicates.PredicateMap,
      candidateFeatures)

    val candidateSourceId =
      candidateFeatures.getOrElse(CandidateSourceIdFeature, None).map(_.value.toByte)

    val homeTweetsControllerDataV1 = v1ht.HomeTweetsControllerData(
      tweetTypesBitmap = tweetTypesBitmaps.getOrElse(0, 0L),
      tweetTypesBitmapContinued1 = tweetTypesBitmaps.get(1),
      candidateTweetSourceId = candidateSourceId,
      traceId = Some(Trace.id.traceId.toLong),
      injectedPosition = candidateFeatures.getOrElse(PositionFeature, None),
      tweetTypesListBytes = Some(tweetTypesListBytes),
      requestJoinId = getRequestJoinId(),
    )

    val serializedControllerData = ControllerDataSerializer(
      ControllerData.V2(
        ControllerDataV2.HomeTweets(ht.HomeTweetsControllerData.V1(homeTweetsControllerDataV1))))

    val clientEventDetails = ClientEventDetails(
      conversationDetails = None,
      timelinesDetails = Some(
        TimelinesDetails(
          injectionType = candidateFeatures.getOrElse(SuggestTypeFeature, None).map(_.name),
          controllerData = Some(serializedControllerData),
          sourceData = None)),
      articleDetails = None,
      liveEventDetails = None,
      commerceDetails = None
    )

    Some(clientEventDetails)
  }
}
