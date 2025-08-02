package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.suggestion

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.highlight.HighlightedSectionMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.highlight.HighlightedSection
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.suggestion.TetwittertResult
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class TetwittertResultMarshaller @Inject() (highlightedSectionMarshaller: HighlightedSectionMarshaller) {

  def apply(tetwittertResult: TetwittertResult): urt.TetwittertResult = {
    val hitHighlights = tetwittertResult.hitHighlights.map {
      highlightedSections: Seq[HighlightedSection] =>
        highlightedSections.map(highlightedSectionMarshaller(_))
    }

    urt.TetwittertResult(
      tetwittert = tetwittertResult.tetwittert,
      hitHighlights = hitHighlights,
      score = tetwittertResult.score,
      querySource = tetwittertResult.querySource)
  }
}
