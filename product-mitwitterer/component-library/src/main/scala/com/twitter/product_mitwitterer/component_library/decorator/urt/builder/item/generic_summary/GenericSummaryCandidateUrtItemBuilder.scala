package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.generic_summary

import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.generic_summary.GenericSummaryCandidateUrtItemBuilder.GenericSummaryClientEventInfoElement
import com.twitter.product_mitwitterer.component_library.model.candidate.GenericSummaryCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventInfoBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.richtetwittert.BaseRichTetwittertBuilder
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.generic_summary.GenericSummaryItem
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.generic_summary.GenericSummaryItemDisplayType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.media.Media
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.promoted.PromotedMetadata
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.util.Time

object GenericSummaryCandidateUrtItemBuilder {
  val GenericSummaryClientEventInfoElement: String = "genericsummary"
}

case class GenericSummaryCandidateUrtItemBuilder[-Query <: PipelineQuery](
  clientEventInfoBuilder: BaseClientEventInfoBuilder[Query, GenericSummaryCandidate],
  headlineRichTetwittertBuilder: BaseRichTetwittertBuilder[Query, GenericSummaryCandidate],
  displayType: GenericSummaryItemDisplayType,
  genericSummaryContetwittertCandidateUrtItemBuilder: Option[
    GenericSummaryContetwittertBuilder[Query, GenericSummaryCandidate]
  ] = None,
  genericSummaryActionCandidateUrtItemBuilder: Option[
    GenericSummaryActionBuilder[Query, GenericSummaryCandidate]
  ] = None,
  timestamp: Option[Time] = None,
  userAttributionIds: Option[Seq[Long]] = None,
  media: Option[Media] = None,
  promotedMetadata: Option[PromotedMetadata] = None,
  feedbackActionInfoBuilder: Option[BaseFeedbackActionInfoBuilder[Query, GenericSummaryCandidate]] =
    None)
    etwittertends CandidateUrtEntryBuilder[Query, GenericSummaryCandidate, GenericSummaryItem] {

  override def apply(
    query: Query,
    genericSummaryCandidate: GenericSummaryCandidate,
    candidateFeatures: FeatureMap
  ): GenericSummaryItem = GenericSummaryItem(
    id = genericSummaryCandidate.id,
    sortIndetwitter = None, // Sort indetwitteres are automatically set in the domain marshaller phase
    clientEventInfo = clientEventInfoBuilder(
      query,
      genericSummaryCandidate,
      candidateFeatures,
      Some(GenericSummaryClientEventInfoElement)),
    feedbackActionInfo =
      feedbackActionInfoBuilder.flatMap(_.apply(query, genericSummaryCandidate, candidateFeatures)),
    headline = headlineRichTetwittertBuilder.apply(query, genericSummaryCandidate, candidateFeatures),
    displayType = displayType,
    userAttributionIds = userAttributionIds.getOrElse(Seq.empty),
    media = media,
    contetwittert = genericSummaryContetwittertCandidateUrtItemBuilder.map(
      _.apply(query, genericSummaryCandidate, candidateFeatures)),
    timestamp = timestamp,
    onClickAction = genericSummaryActionCandidateUrtItemBuilder.map(
      _.apply(query, genericSummaryCandidate, candidateFeatures)),
    promotedMetadata = promotedMetadata
  )
}
