package com.ExTwitter.cr_mixer.module.core

import com.google.inject.Provides
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.cr_mixer.scribe.ScribeCategories
import com.ExTwitter.cr_mixer.scribe.ScribeCategory
import com.ExTwitter.finagle.mtls.authentication.ServiceIdentifier
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.logging.BareFormatter
import com.ExTwitter.logging.Level
import com.ExTwitter.logging.Logger
import com.ExTwitter.logging.NullHandler
import com.ExTwitter.logging.QueueingHandler
import com.ExTwitter.logging.ScribeHandler
import com.ExTwitter.logging.{LoggerFactory => ExTwitterLoggerFactory}
import javax.inject.Named
import javax.inject.Singleton

object LoggerFactoryModule extends ExTwitterModule {

  private val DefaultQueueSize = 10000

  @Provides
  @Singleton
  @Named(ModuleNames.AbDeciderLogger)
  def provideAbDeciderLogger(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): Logger = {
    buildLoggerFactory(
      ScribeCategories.AbDecider,
      serviceIdentifier.environment,
      statsReceiver.scope("ScribeLogger"))
      .apply()
  }

  @Provides
  @Singleton
  @Named(ModuleNames.TopLevelApiDdgMetricsLogger)
  def provideTopLevelApiDdgMetricsLogger(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): Logger = {
    buildLoggerFactory(
      ScribeCategories.TopLevelApiDdgMetrics,
      serviceIdentifier.environment,
      statsReceiver.scope("ScribeLogger"))
      .apply()
  }

  @Provides
  @Singleton
  @Named(ModuleNames.TweetRecsLogger)
  def provideTweetRecsLogger(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): Logger = {
    buildLoggerFactory(
      ScribeCategories.TweetsRecs,
      serviceIdentifier.environment,
      statsReceiver.scope("ScribeLogger"))
      .apply()
  }

  @Provides
  @Singleton
  @Named(ModuleNames.BlueVerifiedTweetRecsLogger)
  def provideVITTweetRecsLogger(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): Logger = {
    buildLoggerFactory(
      ScribeCategories.VITTweetsRecs,
      serviceIdentifier.environment,
      statsReceiver.scope("ScribeLogger"))
      .apply()
  }

  @Provides
  @Singleton
  @Named(ModuleNames.RelatedTweetsLogger)
  def provideRelatedTweetsLogger(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): Logger = {
    buildLoggerFactory(
      ScribeCategories.RelatedTweets,
      serviceIdentifier.environment,
      statsReceiver.scope("ScribeLogger"))
      .apply()
  }

  @Provides
  @Singleton
  @Named(ModuleNames.UtegTweetsLogger)
  def provideUtegTweetsLogger(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): Logger = {
    buildLoggerFactory(
      ScribeCategories.UtegTweets,
      serviceIdentifier.environment,
      statsReceiver.scope("ScribeLogger"))
      .apply()
  }

  @Provides
  @Singleton
  @Named(ModuleNames.AdsRecommendationsLogger)
  def provideAdsRecommendationsLogger(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): Logger = {
    buildLoggerFactory(
      ScribeCategories.AdsRecommendations,
      serviceIdentifier.environment,
      statsReceiver.scope("ScribeLogger"))
      .apply()
  }

  private def buildLoggerFactory(
    category: ScribeCategory,
    environment: String,
    statsReceiver: StatsReceiver
  ): ExTwitterLoggerFactory = {
    environment match {
      case "prod" =>
        ExTwitterLoggerFactory(
          node = category.getProdLoggerFactoryNode,
          level = Some(Level.INFO),
          useParents = false,
          handlers = List(
            QueueingHandler(
              maxQueueSize = DefaultQueueSize,
              handler = ScribeHandler(
                category = category.scribeCategory,
                formatter = BareFormatter,
                statsReceiver = statsReceiver.scope(category.getProdLoggerFactoryNode)
              )
            )
          )
        )
      case _ =>
        ExTwitterLoggerFactory(
          node = category.getStagingLoggerFactoryNode,
          level = Some(Level.DEBUG),
          useParents = false,
          handlers = List(
            { () => NullHandler }
          )
        )
    }
  }
}
