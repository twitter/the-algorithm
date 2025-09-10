package com.twitter.tweet_mixer.feature

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature

object HydraScoreFeature extends Feature[TweetCandidate, Map[String, Double]]
