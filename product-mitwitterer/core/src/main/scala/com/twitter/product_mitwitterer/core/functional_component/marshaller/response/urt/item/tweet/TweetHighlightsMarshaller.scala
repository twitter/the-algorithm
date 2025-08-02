package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.tweet

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.highlight.HighlightedSectionMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.tweet.TweetHighlights
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class TweetHighlightsMarshaller @Inject() (
  highlightedSectionMarshaller: HighlightedSectionMarshaller) {

  def apply(tweetHighlights: TweetHighlights): urt.TweetHighlights =
    urt.TweetHighlights(
      tetwittertHighlights = tweetHighlights.tetwittertHighlights
        .map(_.map(highlightedSectionMarshaller(_))),
      cardTitleHighlights = tweetHighlights.cardTitleHighlights
        .map(_.map(highlightedSectionMarshaller(_))),
      cardDescriptionHighlights = tweetHighlights.cardDescriptionHighlights
        .map(_.map(highlightedSectionMarshaller(_)))
    )
}
