package com.twitter.home_mixer.model

import com.twitter.ml.api.DataRecord
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.pipeline.PipelineQuery

object HomeLargeEmbeddingsFeatures {
  object AuthorLargeEmbeddingsFeature
      extends DataRecordInAFeature[TweetCandidate]
      with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
    override def defaultValue: DataRecord = new DataRecord()
  }

  object AuthorLargeEmbeddingsKeyFeature extends Feature[TweetCandidate, Seq[Long]]

  object OriginalAuthorLargeEmbeddingsFeature
      extends DataRecordInAFeature[TweetCandidate]
      with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
    override def defaultValue: DataRecord = new DataRecord()
  }

  object OriginalAuthorLargeEmbeddingsKeyFeature extends Feature[TweetCandidate, Seq[Long]]

  object TweetLargeEmbeddingsFeature
      extends DataRecordInAFeature[TweetCandidate]
      with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
    override def defaultValue: DataRecord = new DataRecord()
  }

  object TweetLargeEmbeddingsKeyFeature extends Feature[TweetCandidate, Seq[Long]]

  object OriginalTweetLargeEmbeddingsFeature
      extends DataRecordInAFeature[TweetCandidate]
      with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
    override def defaultValue: DataRecord = new DataRecord()
  }

  object OriginalTweetLargeEmbeddingsKeyFeature extends Feature[TweetCandidate, Seq[Long]]

  object UserLargeEmbeddingsFeature
      extends DataRecordInAFeature[PipelineQuery]
      with FeatureWithDefaultOnFailure[PipelineQuery, DataRecord] {
    override def defaultValue: DataRecord = new DataRecord()
  }

  object UserLargeEmbeddingsKeyFeature extends Feature[PipelineQuery, Seq[Long]]
}
