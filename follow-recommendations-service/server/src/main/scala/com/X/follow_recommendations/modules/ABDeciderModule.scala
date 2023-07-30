package com.X.follow_recommendations.modules

import com.google.inject.Provides
import com.google.inject.name.Named
import com.X.abdecider.ABDeciderFactory
import com.X.abdecider.LoggingABDecider
import com.X.finagle.stats.StatsReceiver
import com.X.follow_recommendations.common.constants.GuiceNamedConstants
import com.X.inject.XModule
import com.X.logging.LoggerFactory
import javax.inject.Singleton

object ABDeciderModule extends XModule {
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
