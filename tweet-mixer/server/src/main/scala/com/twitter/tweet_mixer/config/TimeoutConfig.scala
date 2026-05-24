package com.twitter.tweet_mixer.config

import com.twitter.util.Duration

/**
 * Specifies the timeout budgets of various components. We need these to limit how much time to
 * spend on each step (e.x. candidate sources should not take more than 100ms).
 */
case class TimeoutConfig(
  thriftAnnServiceClientTimeout: Duration,
  thriftSANNServiceClientTimeout: Duration,
  thriftTweetypieClientTimeout: Duration,
  thriftUserTweetGraphClientTimeout: Duration,
  thriftUserVideoGraphClientTimeout: Duration,
  candidateSourceTimeout: Duration,
  userStateStoreTimeout: Duration
)
