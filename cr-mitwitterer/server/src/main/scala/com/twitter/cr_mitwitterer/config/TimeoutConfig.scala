package com.twitter.cr_mitwitterer.config

import com.twitter.util.Duration

case class TimeoutConfig(
  /* Default timeouts for candidate generator */
  serviceTimeout: Duration,
  signalFetchTimeout: Duration,
  similarityEngineTimeout: Duration,
  annServiceClientTimeout: Duration,
  /* For Uteg Candidate Generator */
  utegSimilarityEngineTimeout: Duration,
  /* For User State Store */
  userStateUnderlyingStoreTimeout: Duration,
  userStateStoreTimeout: Duration,
  /* For FRS based tweets */
  // Timeout passed to EarlyBird server
  earlybirdServerTimeout: Duration,
  // Timeout set on CrMitwitterer side
  earlybirdSimilarityEngineTimeout: Duration,
  frsBasedTweetEndpointTimeout: Duration,
  topicTweetEndpointTimeout: Duration,
  // Timeout Settings for Navi gRPC Client
  naviRequestTimeout: Duration)
