package com.twitter.tweet_mixer.feature

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.pipeline.PipelineQuery

object LowSignalUserFeature extends Feature[PipelineQuery, Boolean]
