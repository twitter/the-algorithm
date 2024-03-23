package com.ExTwitter.product_mixer.component_library.decorator.urt.builder.metadata

import com.ExTwitter.bijection.scrooge.BinaryScalaCodec
import com.ExTwitter.bijection.Base64String
import com.ExTwitter.bijection.{Injection => Serializer}
import com.ExTwitter.interests_mixer.model.request.{HasTopicId => InterestsMixerHasTopicId}
import com.ExTwitter.explore_mixer.model.request.{HasTopicId => ExploreMixerHasTopicId}
import com.ExTwitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventDetailsBuilder
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventDetails
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.metadata.TimelinesDetails
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.suggests.controller_data.home_tweets.thriftscala.HomeTweetsControllerData
import com.ExTwitter.suggests.controller_data.home_tweets.v1.thriftscala.{
  HomeTweetsControllerData => HomeTweetsControllerDataV1
}
import com.ExTwitter.suggests.controller_data.thriftscala.ControllerData
import com.ExTwitter.suggests.controller_data.v2.thriftscala.{ControllerData => ControllerDataV2}

object TopicTweetClientEventDetailsBuilder {
  implicit val ByteSerializer: Serializer[ControllerData, Array[Byte]] =
    BinaryScalaCodec(ControllerData)

  val ControllerDataSerializer: Serializer[ControllerData, String] =
    Serializer.connect[ControllerData, Array[Byte], Base64String, String]
}

case class TopicTweetClientEventDetailsBuilder[-Query <: PipelineQuery]()
    extends BaseClientEventDetailsBuilder[Query, TweetCandidate] {

  import TopicTweetClientEventDetailsBuilder._

  override def apply(
    query: Query,
    topicTweetCandidate: TweetCandidate,
    candidateFeatures: FeatureMap
  ): Option[ClientEventDetails] =
    Some(
      ClientEventDetails(
        conversationDetails = None,
        timelinesDetails = Some(
          TimelinesDetails(
            injectionType = None,
            controllerData = buildControllerData(getTopicId(query)),
            sourceData = None)),
        articleDetails = None,
        liveEventDetails = None,
        commerceDetails = None
      ))

  private def getTopicId(query: Query): Option[Long] = {
    query match {
      case query: InterestsMixerHasTopicId => query.topicId
      case query: ExploreMixerHasTopicId => query.topicId
      case _ => None
    }
  }

  private def buildControllerData(topicId: Option[Long]): Option[String] =
    Some(
      ControllerData
        .V2(ControllerDataV2.HomeTweets(HomeTweetsControllerData.V1(
          HomeTweetsControllerDataV1(tweetTypesBitmap = 0L, topicId = topicId)))))
      .map(ControllerDataSerializer)
}
