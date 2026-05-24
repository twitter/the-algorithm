package com.twitter.product_mixer.component_library.decorator.urt.builder.richtext

import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.richtext.BaseRichTextBuilder
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.marshalling.response.urt.richtext.RichText
import com.twitter.product_mixer.core.pipeline.PipelineQuery

case class StaticRichTextBuilder(richText: RichText)
    extends BaseRichTextBuilder[PipelineQuery, UniversalNoun[Any]] {

  override def apply(
    query: PipelineQuery,
    candidate: UniversalNoun[Any],
    candidateFeatures: FeatureMap
  ): RichText = richText
}
