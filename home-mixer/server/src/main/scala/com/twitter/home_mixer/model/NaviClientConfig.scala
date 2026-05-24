package com.twitter.home_mixer.model

case class NaviClientConfig(
  clientName: String,
  customizedBatchSize: Option[Int],
  clusterStr: String)
