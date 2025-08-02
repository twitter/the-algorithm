package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.richtetwittert.twitter_tetwittert

import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.richtetwittert.RichTetwittertReferenceObjectBuilder
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.richtetwittert.RichTetwittertRtlOptionBuilder
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.richtetwittert.StaticRichTetwittertRtlOptionBuilder
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.richtetwittert.twitter_tetwittert.TwitterTetwittertEntityProcessor.DefaultReferenceObjectBuilder
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseStr
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.richtetwittert.BaseRichTetwittertBuilder
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.Plain
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittertAlignment
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittertFormat
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.Strong
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

case class TwitterTetwittertRichTetwittertBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]](
  stringBuilder: BaseStr[Query, Candidate],
  alignment: Option[RichTetwittertAlignment] = None,
  formats: Set[RichTetwittertFormat] = Set(Plain, Strong),
  twitterTetwittertRtlOptionBuilder: RichTetwittertRtlOptionBuilder[Query] =
    StaticRichTetwittertRtlOptionBuilder[Query](None),
  twitterTetwittertReferenceObjectBuilder: RichTetwittertReferenceObjectBuilder = DefaultReferenceObjectBuilder)
    etwittertends BaseRichTetwittertBuilder[Query, Candidate] {
  def apply(query: Query, candidate: Candidate, candidateFeatures: FeatureMap): RichTetwittert = {
    val twitterTetwittertRenderer = TwitterTetwittertRenderer(
      tetwittert = stringBuilder(query, candidate, candidateFeatures),
      rtl = twitterTetwittertRtlOptionBuilder(query),
      alignment = alignment)

    twitterTetwittertRenderer
      .transform(TwitterTetwittertFormatProcessor(formats))
      .transform(TwitterTetwittertEntityProcessor(twitterTetwittertReferenceObjectBuilder))
      .build
  }
}
