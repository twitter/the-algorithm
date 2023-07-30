package com.X.cr_mixer.model

import com.X.simclusters_v2.common.TweetId

/***
 * Bind a tweetId with a raw score generated from one single Similarity Engine
 */
case class TweetWithScore(tweetId: TweetId, score: Double)
