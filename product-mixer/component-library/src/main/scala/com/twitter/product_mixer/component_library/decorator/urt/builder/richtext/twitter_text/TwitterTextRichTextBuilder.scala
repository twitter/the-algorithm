package com.ExTwitter.product_mixer.component_library.decorator.urt.builder.richtext.ExTwitter_text

import com.ExTwitter.product_mixer.component_library.decorator.urt.builder.richtext.RichTextReferenceObjectBuilder
import com.ExTwitter.product_mixer.component_library.decorator.urt.builder.richtext.RichTextRtlOptionBuilder
import com.ExTwitter.product_mixer.component_library.decorator.urt.builder.richtext.StaticRichTextRtlOptionBuilder
import com.ExTwitter.product_mixer.component_library.decorator.urt.builder.richtext.ExTwitter_text.ExTwitterTextEntityProcessor.DefaultReferenceObjectBuilder
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseStr
import com.ExTwitter.product_mixer.core.functional_component.decorator.urt.builder.richtext.BaseRichTextBuilder
import com.ExTwitter.product_mixer.core.model.common.UniversalNoun
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.richtext.Plain
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.richtext.RichText
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.richtext.RichTextAlignment
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.richtext.RichTextFormat
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.richtext.Strong
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery

case class ExTwitterTextRichTextBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]](
  stringBuilder: BaseStr[Query, Candidate],
  alignment: Option[RichTextAlignment] = None,
  formats: Set[RichTextFormat] = Set(Plain, Strong),
  ExTwitterTextRtlOptionBuilder: RichTextRtlOptionBuilder[Query] =
    StaticRichTextRtlOptionBuilder[Query](None),
  ExTwitterTextReferenceObjectBuilder: RichTextReferenceObjectBuilder = DefaultReferenceObjectBuilder)
    extends BaseRichTextBuilder[Query, Candidate] {
  def apply(query: Query, candidate: Candidate, candidateFeatures: FeatureMap): RichText = {
    val ExTwitterTextRenderer = ExTwitterTextRenderer(
      text = stringBuilder(query, candidate, candidateFeatures),
      rtl = ExTwitterTextRtlOptionBuilder(query),
      alignment = alignment)

    ExTwitterTextRenderer
      .transform(ExTwitterTextFormatProcessor(formats))
      .transform(ExTwitterTextEntityProcessor(ExTwitterTextReferenceObjectBuilder))
      .build
  }
}
