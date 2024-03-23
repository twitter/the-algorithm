package com.ExTwitter.follow_recommendations.modules

import com.google.inject.Provides
import com.google.inject.Singleton
import com.google.inject.name.Named
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.follow_recommendations.common.constants.GuiceNamedConstants
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.logging.BareFormatter
import com.ExTwitter.logging.HandlerFactory
import com.ExTwitter.logging.Level
import com.ExTwitter.logging.LoggerFactory
import com.ExTwitter.logging.NullHandler
import com.ExTwitter.logging.QueueingHandler
import com.ExTwitter.logging.ScribeHandler

object ScribeModule extends ExTwitterModule {
  val useProdLogger = flag(
    name = "scribe.use_prod_loggers",
    default = false,
    help = "whether to use production logging for service"
  )

  @Provides
  @Singleton
  @Named(GuiceNamedConstants.CLIENT_EVENT_LOGGER)
  def provideClientEventsLoggerFactory(stats: StatsReceiver): LoggerFactory = {
    val loggerCategory = "client_event"
    val clientEventsHandler: HandlerFactory = if (useProdLogger()) {
      QueueingHandler(
        maxQueueSize = 10000,
        handler = ScribeHandler(
          category = loggerCategory,
          formatter = BareFormatter,
          level = Some(Level.INFO),
          statsReceiver = stats.scope("client_event_scribe")
        )
      )
    } else { () => NullHandler }
    LoggerFactory(
      node = "abdecider",
      level = Some(Level.INFO),
      useParents = false,
      handlers = clientEventsHandler :: Nil
    )
  }

  @Provides
  @Singleton
  @Named(GuiceNamedConstants.REQUEST_LOGGER)
  def provideFollowRecommendationsLoggerFactory(stats: StatsReceiver): LoggerFactory = {
    val loggerCategory = "follow_recommendations_logs"
    val handlerFactory: HandlerFactory = if (useProdLogger()) {
      QueueingHandler(
        maxQueueSize = 10000,
        handler = ScribeHandler(
          category = loggerCategory,
          formatter = BareFormatter,
          level = Some(Level.INFO),
          statsReceiver = stats.scope("follow_recommendations_logs_scribe")
        )
      )
    } else { () => NullHandler }
    LoggerFactory(
      node = loggerCategory,
      level = Some(Level.INFO),
      useParents = false,
      handlers = handlerFactory :: Nil
    )
  }

  @Provides
  @Singleton
  @Named(GuiceNamedConstants.FLOW_LOGGER)
  def provideFrsRecommendationFlowLoggerFactory(stats: StatsReceiver): LoggerFactory = {
    val loggerCategory = "frs_recommendation_flow_logs"
    val handlerFactory: HandlerFactory = if (useProdLogger()) {
      QueueingHandler(
        maxQueueSize = 10000,
        handler = ScribeHandler(
          category = loggerCategory,
          formatter = BareFormatter,
          level = Some(Level.INFO),
          statsReceiver = stats.scope("frs_recommendation_flow_logs_scribe")
        )
      )
    } else { () => NullHandler }
    LoggerFactory(
      node = loggerCategory,
      level = Some(Level.INFO),
      useParents = false,
      handlers = handlerFactory :: Nil
    )
  }
}
