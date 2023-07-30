package com.X.product_mixer.component_library.decorator.urt.builder.metadata

import com.X.bijection.scrooge.BinaryScalaCodec
import com.X.bijection.Base64String
import com.X.bijection.{Injection => Serializer}
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventDetailsBuilder
import com.X.product_mixer.component_library.model.candidate.BaseTopicCandidate
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventDetails
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.TimelinesDetails
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.suggests.controller_data.thriftscala.ControllerData
import com.X.suggests.controller_data.timelines_topic.thriftscala.TimelinesTopicControllerData
import com.X.suggests.controller_data.timelines_topic.v1.thriftscala.{
  TimelinesTopicControllerData => TimelinesTopicControllerDataV1
}
import com.X.suggests.controller_data.v2.thriftscala.{ControllerData => ControllerDataV2}

object TopicClientEventDetailsBuilder {
  implicit val ByteSerializer: Serializer[ControllerData, Array[Byte]] =
    BinaryScalaCodec(ControllerData)

  val ControllerDataSerializer: Serializer[ControllerData, String] =
    Serializer.connect[ControllerData, Array[Byte], Base64String, String]
}

case class TopicClientEventDetailsBuilder[-Query <: PipelineQuery]()
    extends BaseClientEventDetailsBuilder[Query, BaseTopicCandidate] {

  import TopicClientEventDetailsBuilder._

  override def apply(
    query: Query,
    topicCandidate: BaseTopicCandidate,
    candidateFeatures: FeatureMap
  ): Option[ClientEventDetails] =
    Some(
      ClientEventDetails(
        conversationDetails = None,
        timelinesDetails = Some(
          TimelinesDetails(
            injectionType = None,
            controllerData = buildControllerData(topicCandidate.id),
            sourceData = None)),
        articleDetails = None,
        liveEventDetails = None,
        commerceDetails = None
      ))

  private def buildControllerData(topicId: Long): Option[String] =
    Some(
      ControllerData
        .V2(ControllerDataV2.TimelinesTopic(TimelinesTopicControllerData.V1(
          TimelinesTopicControllerDataV1(topicTypesBitmap = 0L, topicId = topicId)))))
      .map(ControllerDataSerializer)
}
