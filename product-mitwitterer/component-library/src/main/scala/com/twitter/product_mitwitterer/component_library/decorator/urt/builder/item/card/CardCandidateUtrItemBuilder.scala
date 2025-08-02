package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.card

import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.card.CardCandidateUtrItemBuilder.CardClientEventInfoElement
import com.twitter.product_mitwitterer.component_library.model.candidate.CardCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventInfoBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseStr
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseUrlBuilder
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.card.CardDisplayType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.card.CardItem
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

object CardCandidateUtrItemBuilder {
  val CardClientEventInfoElement: String = "card"
}

case class CardCandidateUtrItemBuilder[-Query <: PipelineQuery](
  clientEventInfoBuilder: BaseClientEventInfoBuilder[Query, CardCandidate],
  cardUrlBuilder: BaseStr[Query, CardCandidate],
  tetwittertBuilder: Option[BaseStr[Query, CardCandidate]],
  subtetwittertBuilder: Option[BaseStr[Query, CardCandidate]],
  urlBuilder: Option[BaseUrlBuilder[Query, CardCandidate]],
  cardDisplayType: Option[CardDisplayType],
  feedbackActionInfoBuilder: Option[
    BaseFeedbackActionInfoBuilder[Query, CardCandidate],
  ] = None)
    etwittertends CandidateUrtEntryBuilder[Query, CardCandidate, CardItem] {

  override def apply(
    query: Query,
    cardCandidate: CardCandidate,
    candidateFeatures: FeatureMap
  ): CardItem = CardItem(
    id = cardCandidate.id,
    sortIndetwitter = None, // Sort indetwitteres are automatically set in the domain marshaller phase
    clientEventInfo = clientEventInfoBuilder(
      query,
      cardCandidate,
      candidateFeatures,
      Some(CardClientEventInfoElement)),
    feedbackActionInfo =
      feedbackActionInfoBuilder.flatMap(_.apply(query, cardCandidate, candidateFeatures)),
    cardUrl = cardUrlBuilder(query, cardCandidate, candidateFeatures),
    tetwittert = tetwittertBuilder.map(_.apply(query, cardCandidate, candidateFeatures)),
    subtetwittert = tetwittertBuilder.map(_.apply(query, cardCandidate, candidateFeatures)),
    url = urlBuilder.map(_.apply(query, cardCandidate, candidateFeatures)),
    displayType = cardDisplayType
  )
}
