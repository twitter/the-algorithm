package com.twitter.tweet_mixer.utils

import com.twitter.product_mixer.core.pipeline.pipeline_failure.ServerFailure

object PipelineFailureCategories {

  object FailedEmbeddingHydrationResponse extends ServerFailure {
    override val failureName: String = "FailedEmbeddingHydrationResponse"
  }

  object InvalidEmbeddingHydrationResponse extends ServerFailure {
    override val failureName: String = "InvalidEmbeddingHydrationResponse"
  }
}
