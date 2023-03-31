package com.twitter.cr_mixer.scribe

/**
 * Categories define scribe categories used in cr-mixer service.
 */
object ScribeCategories {
  lazy val AllCategories =
    List(AbDecider, TopLevelApiDdgMetrics, TweetsRecs)

  /**
   * AbDecider represents scribe logs for experiments
   */
  lazy val AbDecider: ScribeCategory = ScribeCategory(
    "abdecider_scribe",
    "client_event"
  )

  /**
   * Top-Level Client event scribe logs, to record changes in system metrics (e.g. latency,
   * candidates returned, empty rate ) per experiment bucket, and store them in DDG metric group
   */
  lazy val TopLevelApiDdgMetrics: ScribeCategory = ScribeCategory(
    "top_level_api_ddg_metrics_scribe",
    "client_event"
  )

  lazy val TweetsRecs: ScribeCategory = ScribeCategory(
    "get_tweets_recommendations_scribe",
    "cr_mixer_get_tweets_recommendations"
  )

  lazy val VITTweetsRecs: ScribeCategory = ScribeCategory(
    "get_vit_tweets_recommendations_scribe",
    "cr_mixer_get_vit_tweets_recommendations"
  )

  lazy val RelatedTweets: ScribeCategory = ScribeCategory(
    "get_related_tweets_scribe",
    "cr_mixer_get_related_tweets"
  )

  lazy val UtegTweets: ScribeCategory = ScribeCategory(
    "get_uteg_tweets_scribe",
    "cr_mixer_get_uteg_tweets"
  )

  lazy val AdsRecommendations: ScribeCategory = ScribeCategory(
    "get_ads_recommendations_scribe",
    "cr_mixer_get_ads_recommendations"
  )
}

/**
 * Category represents each scribe log data.
 *
 * @param loggerFactoryNode loggerFactory node name in cr-mixer associated with this scribe category
 * @param scribeCategory    scribe category name (globally unique at Twitter)
 */
case class ScribeCategory(
  loggerFactoryNode: String,
  scribeCategory: String) {
  def getProdLoggerFactoryNode: String = loggerFactoryNode
  def getStagingLoggerFactoryNode: String = "staging_" + loggerFactoryNode
}
