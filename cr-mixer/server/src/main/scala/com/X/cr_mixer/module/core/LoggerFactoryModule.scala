package com.X.cr_mixer.module.core

import com.google.inject.Provides
import com.X.cr_mixer.model.ModuleNames
import com.X.cr_mixer.scribe.ScribeCategories
import com.X.cr_mixer.scribe.ScribeCategory
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.logging.BareFormatter
import com.X.logging.Level
import com.X.logging.Logger
import com.X.logging.NullHandler
import com.X.logging.QueueingHandler
import com.X.logging.ScribeHandler
import com.X.logging.{LoggerFactory => XLoggerFactory}
import javax.inject.Named
import javax.inject.Singleton

object LoggerFactoryModule extends XModule {

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
  ): XLoggerFactory = {
    environment match {
      case "prod" =>
        XLoggerFactory(
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
        XLoggerFactory(
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
