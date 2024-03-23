package com.ExTwitter.cr_mixer.model

import com.ExTwitter.simclusters_v2.common.TweetId
import com.ExTwitter.recos.recos_common.thriftscala.SocialProofType

/***
 * Bind a tweetId with a raw score and social proofs by type
 */
case class TweetWithScoreAndSocialProof(
  tweetId: TweetId,
  score: Double,
  socialProofByType: Map[SocialProofType, Seq[Long]])
