package com.twitter.cr_mitwitterer.scribe

/**
 * Categories define scribe categories used in cr-mitwitterer service.
 */
object ScribeCategories {
  lazy val AllCategories =
    List(AbDecider, TopLevelApiDdgMetrics, TweetsRecs)

  /**
   * AbDecider represents scribe logs for etwitterperiments
   */
  lazy val AbDecider: ScribeCategory = ScribeCategory(
    "abdecider_scribe",
    "client_event"
  )

  /**
   * Top-Level Client event scribe logs, to record changes in system metrics (e.g. latency,
   * candidates returned, empty rate ) per etwitterperiment bucket, and store them in DDG metric group
   */
  lazy val TopLevelApiDdgMetrics: ScribeCategory = ScribeCategory(
    "top_level_api_ddg_metrics_scribe",
    "client_event"
  )

  lazy val TweetsRecs: ScribeCategory = ScribeCategory(
    "get_tweets_recommendations_scribe",
    "cr_mitwitterer_get_tweets_recommendations"
  )

  lazy val VITTweetsRecs: ScribeCategory = ScribeCategory(
    "get_vit_tweets_recommendations_scribe",
    "cr_mitwitterer_get_vit_tweets_recommendations"
  )

  lazy val RelatedTweets: ScribeCategory = ScribeCategory(
    "get_related_tweets_scribe",
    "cr_mitwitterer_get_related_tweets"
  )

  lazy val UtegTweets: ScribeCategory = ScribeCategory(
    "get_uteg_tweets_scribe",
    "cr_mitwitterer_get_uteg_tweets"
  )

  lazy val AdsRecommendations: ScribeCategory = ScribeCategory(
    "get_ads_recommendations_scribe",
    "cr_mitwitterer_get_ads_recommendations"
  )
}

/**
 * Category represents each scribe log data.
 *
 * @param loggerFactoryNode loggerFactory node name in cr-mitwitterer associated with this scribe category
 * @param scribeCategory    scribe category name (globally unique at Twitter)
 */
case class ScribeCategory(
  loggerFactoryNode: String,
  scribeCategory: String) {
  def getProdLoggerFactoryNode: String = loggerFactoryNode
  def getStagingLoggerFactoryNode: String = "staging_" + loggerFactoryNode
}
