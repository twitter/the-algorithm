package com.X.timelineranker

import com.X.timelineranker.core.FutureDependencyTransformer
import com.X.timelineranker.recap.model.ContentFeatures
import com.X.timelines.model.TweetId

package object contentfeatures {
  type ContentFeaturesProvider =
    FutureDependencyTransformer[Seq[TweetId], Map[TweetId, ContentFeatures]]
}
