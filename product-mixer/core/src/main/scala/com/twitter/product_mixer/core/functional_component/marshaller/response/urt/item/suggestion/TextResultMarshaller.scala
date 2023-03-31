package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.suggestion

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.highlight.HighlightedSectionMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.highlight.HighlightedSection
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.suggestion.TextResult
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TextResultMarshaller @Inject() (highlightedSectionMarshaller: HighlightedSectionMarshaller) {

  def apply(textResult: TextResult): urt.TextResult = {
    val hitHighlights = textResult.hitHighlights.map {
      highlightedSections: Seq[HighlightedSection] =>
        highlightedSections.map(highlightedSectionMarshaller(_))
    }

    urt.TextResult(
      text = textResult.text,
      hitHighlights = hitHighlights,
      score = textResult.score,
      querySource = textResult.querySource)
  }
}
