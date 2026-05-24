package com.twitter.product_mixer.core.model.marshalling.response.urt.item.tweet

import com.twitter.product_mixer.core.model.marshalling.response.urt.item.highlight.HighlightedSection

case class TweetHighlights(
  textHighlights: Option[List[HighlightedSection]],
  cardTitleHighlights: Option[List[HighlightedSection]],
  cardDescriptionHighlights: Option[List[HighlightedSection]])
