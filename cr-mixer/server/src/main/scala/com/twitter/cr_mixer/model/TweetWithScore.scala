package com.twitter.cr_mixer.model

import com.twitter.simclusters_v2.common.TweetId

/***
 * Bind a tweetId with a raw score generated from one single Similarity Engine
 */
case class TweetWithScore(tweetId: TweetId, score: Double)
