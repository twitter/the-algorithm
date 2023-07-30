package com.X.cr_mixer.model

import com.X.simclusters_v2.common.TweetId
import com.X.recos.recos_common.thriftscala.SocialProofType

/***
 * Bind a tweetId with a raw score and social proofs by type
 */
case class TweetWithScoreAndSocialProof(
  tweetId: TweetId,
  score: Double,
  socialProofByType: Map[SocialProofType, Seq[Long]])
