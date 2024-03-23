package com.ExTwitter.product_mixer.component_library.decorator.urt.builder.metadata

import com.ExTwitter.bijection.scrooge.BinaryScalaCodec
import com.ExTwitter.bijection.Base64String
import com.ExTwitter.bijection.{Injection => Serializer}
import com.ExTwitter.product_mixer.component_library.decorator.urt.builder.metadata.ConversationTweetClientEventDetailsBuilder.ControllerDataSerializer
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventDetailsBuilder
import com.ExTwitter.product_mixer.component_library.model.candidate.BaseTweetCandidate
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventDetails
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.metadata.TimelinesDetails
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.suggests.controller_data.home_tweets.thriftscala.HomeTweetsControllerData
import com.ExTwitter.suggests.controller_data.thriftscala.ControllerData
import com.ExTwitter.suggests.controller_data.home_tweets.v1.thriftscala.{
  HomeTweetsControllerData => HomeTweetsControllerDataV1
}
import com.ExTwitter.suggests.controller_data.v2.thriftscala.{ControllerData => ControllerDataV2}

object ConversationTweetClientEventDetailsBuilder {
  implicit val ByteSerializer: Serializer[ControllerData, Array[Byte]] =
    BinaryScalaCodec(ControllerData)

  val ControllerDataSerializer: Serializer[ControllerData, String] =
    Serializer.connect[ControllerData, Array[Byte], Base64String, String]
}

case class ConversationTweetClientEventDetailsBuilder[-Query <: PipelineQuery](
  injectionType: Option[String])
    extends BaseClientEventDetailsBuilder[Query, BaseTweetCandidate] {

  override def apply(
    query: Query,
    tweetCandidate: BaseTweetCandidate,
    candidateFeatures: FeatureMap
  ): Option[ClientEventDetails] =
    Some(
      ClientEventDetails(
        conversationDetails = None,
        timelinesDetails = Some(
          TimelinesDetails(
            injectionType = injectionType,
            controllerData = Some(buildControllerData(query.getUserOrGuestId)),
            sourceData = None)),
        articleDetails = None,
        liveEventDetails = None,
        commerceDetails = None
      ))

  private def buildControllerData(traceId: Option[Long]): String =
    ControllerDataSerializer(
      ControllerData.V2(
        ControllerDataV2.HomeTweets(
          HomeTweetsControllerData.V1(
            HomeTweetsControllerDataV1(
              tweetTypesBitmap = 0L,
              traceId = traceId,
            )
          )
        )
      )
    )
}
