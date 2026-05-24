package com.twitter.follow_recommendations.modules

import com.google.inject.Provides
import com.google.inject.name.Named
import com.twitter.abdecider.ABDeciderFactory
import com.twitter.abdecider.LoggingABDecider
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.follow_recommendations.common.constants.GuiceNamedConstants
import com.twitter.inject.TwitterModule
import com.twitter.logging.LoggerFactory
import javax.inject.Singleton

object ABDeciderModule extends TwitterModule {
  @Provides
  @Singleton
  def provideABDecider(
    stats: StatsReceiver,
    @Named(GuiceNamedConstants.CLIENT_EVENT_LOGGER) factory: LoggerFactory
  ): LoggingABDecider = {

    val ymlPath = "/usr/local/config/abdecider/abdecider.yml"

    val abDeciderFactory = ABDeciderFactory(
      abDeciderYmlPath = ymlPath,
      scribeLogger = Some(factory()),
      environment = Some("production")
    )

    abDeciderFactory.buildWithLogging()
  }
}
