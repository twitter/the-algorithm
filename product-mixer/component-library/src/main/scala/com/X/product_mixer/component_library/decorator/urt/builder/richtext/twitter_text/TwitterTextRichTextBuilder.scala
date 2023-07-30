package com.X.product_mixer.component_library.decorator.urt.builder.richtext.X_text

import com.X.product_mixer.component_library.decorator.urt.builder.richtext.RichTextReferenceObjectBuilder
import com.X.product_mixer.component_library.decorator.urt.builder.richtext.RichTextRtlOptionBuilder
import com.X.product_mixer.component_library.decorator.urt.builder.richtext.StaticRichTextRtlOptionBuilder
import com.X.product_mixer.component_library.decorator.urt.builder.richtext.X_text.XTextEntityProcessor.DefaultReferenceObjectBuilder
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseStr
import com.X.product_mixer.core.functional_component.decorator.urt.builder.richtext.BaseRichTextBuilder
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.model.marshalling.response.urt.richtext.Plain
import com.X.product_mixer.core.model.marshalling.response.urt.richtext.RichText
import com.X.product_mixer.core.model.marshalling.response.urt.richtext.RichTextAlignment
import com.X.product_mixer.core.model.marshalling.response.urt.richtext.RichTextFormat
import com.X.product_mixer.core.model.marshalling.response.urt.richtext.Strong
import com.X.product_mixer.core.pipeline.PipelineQuery

case class XTextRichTextBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]](
  stringBuilder: BaseStr[Query, Candidate],
  alignment: Option[RichTextAlignment] = None,
  formats: Set[RichTextFormat] = Set(Plain, Strong),
  XTextRtlOptionBuilder: RichTextRtlOptionBuilder[Query] =
    StaticRichTextRtlOptionBuilder[Query](None),
  XTextReferenceObjectBuilder: RichTextReferenceObjectBuilder = DefaultReferenceObjectBuilder)
    extends BaseRichTextBuilder[Query, Candidate] {
  def apply(query: Query, candidate: Candidate, candidateFeatures: FeatureMap): RichText = {
    val XTextRenderer = XTextRenderer(
      text = stringBuilder(query, candidate, candidateFeatures),
      rtl = XTextRtlOptionBuilder(query),
      alignment = alignment)

    XTextRenderer
      .transform(XTextFormatProcessor(formats))
      .transform(XTextEntityProcessor(XTextReferenceObjectBuilder))
      .build
  }
}
