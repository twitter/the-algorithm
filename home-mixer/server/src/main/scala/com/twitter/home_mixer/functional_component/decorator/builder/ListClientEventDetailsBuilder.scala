package com.twitter.home_mixer.functional_component.decorator.builder

import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventDetailsBuilder
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventDetails
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.TimelinesDetails
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelineservice.suggests.{thriftscala => st}

case class ListClientEventDetailsBuilder(suggestType: st.SuggestType)
    extends BaseClientEventDetailsBuilder[PipelineQuery, UniversalNoun[Any]] {

  override def apply(
    query: PipelineQuery,
    candidate: UniversalNoun[Any],
    candidateFeatures: FeatureMap
  ): Option[ClientEventDetails] = {
    val clientEventDetails = ClientEventDetails(
      conversationDetails = None,
      timelinesDetails = Some(
        TimelinesDetails(
          injectionType = Some(suggestType.name),
          controllerData = None,
          sourceData = None)),
      articleDetails = None,
      liveEventDetails = None,
      commerceDetails = None
    )

    Some(clientEventDetails)
  }
}
