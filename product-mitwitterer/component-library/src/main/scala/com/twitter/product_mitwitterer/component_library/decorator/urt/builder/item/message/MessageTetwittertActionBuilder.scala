package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.message

import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseStr
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.message.MessageAction
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.message.MessageTetwittertAction
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Callback
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

object MessageTetwittertActionBuilder {
  val MessageTetwittertActionClientEventInfoElement: String = "message-tetwittert-action"
}

case class MessageTetwittertActionBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]](
  tetwittertBuilder: BaseStr[Query, Candidate],
  dismissOnClick: Boolean,
  url: Option[String] = None,
  clientEventInfo: Option[ClientEventInfo] = None,
  onClickCallbacks: Option[List[Callback]] = None) {

  def apply(
    query: Query,
    candidate: Candidate,
    candidateFeatures: FeatureMap
  ): MessageTetwittertAction = MessageTetwittertAction(
    tetwittert = tetwittertBuilder(query, candidate, candidateFeatures),
    action = MessageAction(
      dismissOnClick,
      url,
      clientEventInfo,
      onClickCallbacks
    )
  )
}
