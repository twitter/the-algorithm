package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.audio_space

import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.audio_space.AudioSpaceCandidateUrtItemBuilder.AudioSpaceClientEventInfoElement
import com.twitter.product_mitwitterer.component_library.model.candidate.AudioSpaceCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventInfoBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.audio_space.AudioSpaceItem
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

object AudioSpaceCandidateUrtItemBuilder {
  val AudioSpaceClientEventInfoElement: String = "audiospace"
}

case class AudioSpaceCandidateUrtItemBuilder[-Query <: PipelineQuery](
  clientEventInfoBuilder: BaseClientEventInfoBuilder[Query, UniversalNoun[Any]],
  feedbackActionInfoBuilder: Option[
    BaseFeedbackActionInfoBuilder[Query, UniversalNoun[Any]]
  ] = None)
    etwittertends CandidateUrtEntryBuilder[Query, AudioSpaceCandidate, AudioSpaceItem] {

  override def apply(
    query: Query,
    audioSpaceCandidate: AudioSpaceCandidate,
    candidateFeatures: FeatureMap
  ): AudioSpaceItem = AudioSpaceItem(
    id = audioSpaceCandidate.id,
    sortIndetwitter = None, // Sort indetwitteres are automatically set in the domain marshaller phase
    clientEventInfo = clientEventInfoBuilder(
      query,
      audioSpaceCandidate,
      candidateFeatures,
      Some(AudioSpaceClientEventInfoElement)),
    feedbackActionInfo =
      feedbackActionInfoBuilder.flatMap(_.apply(query, audioSpaceCandidate, candidateFeatures))
  )
}
