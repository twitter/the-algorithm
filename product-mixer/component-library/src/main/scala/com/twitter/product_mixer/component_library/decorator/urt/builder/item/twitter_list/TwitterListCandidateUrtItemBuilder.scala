package com.twitter.product_mixer.component_library.decorator.urt.builder.item.twitter_list

import com.twitter.product_mixer.component_library.decorator.urt.builder.item.twitter_list.TwitterListCandidateUrtItemBuilder.ListClientEventInfoElement
import com.twitter.product_mixer.component_library.model.candidate.TwitterListCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventInfoBuilder
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.twitter_list.TwitterListDisplayType
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.twitter_list.TwitterListItem
import com.twitter.product_mixer.core.pipeline.PipelineQuery

object TwitterListCandidateUrtItemBuilder {
  val ListClientEventInfoElement: String = "list"
}

case class TwitterListCandidateUrtItemBuilder[-Query <: PipelineQuery](
  clientEventInfoBuilder: BaseClientEventInfoBuilder[Query, TwitterListCandidate],
  feedbackActionInfoBuilder: Option[
    BaseFeedbackActionInfoBuilder[Query, TwitterListCandidate]
  ] = None,
  displayType: Option[TwitterListDisplayType] = None)
    extends CandidateUrtEntryBuilder[Query, TwitterListCandidate, TwitterListItem] {

  override def apply(
    query: Query,
    twitterListCandidate: TwitterListCandidate,
    candidateFeatures: FeatureMap
  ): TwitterListItem = TwitterListItem(
    id = twitterListCandidate.id,
    sortIndex = None, // Sort indexes are automatically set in the domain marshaller phase
    clientEventInfo = clientEventInfoBuilder(
      query,
      twitterListCandidate,
      candidateFeatures,
      Some(ListClientEventInfoElement)),
    feedbackActionInfo =
      feedbackActionInfoBuilder.flatMap(_.apply(query, twitterListCandidate, candidateFeatures)),
    displayType = displayType
  )
}
