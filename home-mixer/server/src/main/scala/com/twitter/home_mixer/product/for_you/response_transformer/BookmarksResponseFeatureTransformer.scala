package com.twitter.home_mixer.product.for_you.response_transformer

import com.twitter.frigate.bookmarks.thriftscala.BookmarkedTweet
import com.twitter.home_mixer.model.HomeFeatures.BookmarkedTweetTimestamp
import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier

object BookmarksResponseFeatureTransformer extends CandidateFeatureTransformer[BookmarkedTweet] {

  override val identifier: TransformerIdentifier =
    TransformerIdentifier("BookmarksResponse")

  override val features: Set[Feature[_, _]] =
    Set(BookmarkedTweetTimestamp, ServedTypeFeature)

  def transform(input: BookmarkedTweet): FeatureMap = FeatureMapBuilder()
    .add(BookmarkedTweetTimestamp, Some(input.timestamp))
    .add(ServedTypeFeature, hmt.ServedType.ForYouResurfacedBookmark)
    .build()
}
