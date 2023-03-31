package com.twitter.timelineranker

import com.twitter.timelineranker.core.FutureDependencyTransformer
import com.twitter.timelineranker.recap.model.ContentFeatures
import com.twitter.timelines.model.TweetId

package object contentfeatures {
  type ContentFeaturesProvider =
    FutureDependencyTransformer[Seq[TweetId], Map[TweetId, ContentFeatures]]
}
