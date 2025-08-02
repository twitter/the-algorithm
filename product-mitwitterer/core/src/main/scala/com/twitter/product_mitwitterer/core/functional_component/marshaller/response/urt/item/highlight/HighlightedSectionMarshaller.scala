package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.highlight

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.highlight.HighlightedSection
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class HighlightedSectionMarshaller @Inject() () {

  def apply(highlightedSection: HighlightedSection): urt.HighlightedSection =
    urt.HighlightedSection(
      startIndetwitter = highlightedSection.startIndetwitter,
      endIndetwitter = highlightedSection.endIndetwitter
    )
}
