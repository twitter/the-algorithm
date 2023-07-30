package com.X.product_mixer.core.functional_component.decorator.urt.builder.richtext

import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.model.marshalling.response.urt.richtext.RichText
import com.X.product_mixer.core.pipeline.PipelineQuery

trait BaseRichTextBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]] {

  def apply(query: Query, candidate: Candidate, candidateFeatures: FeatureMap): RichText
}
