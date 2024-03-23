package com.ExTwitter.follow_recommendations.modules

import com.google.inject.Provides
import com.google.inject.name.Named
import com.ExTwitter.abdecider.ABDeciderFactory
import com.ExTwitter.abdecider.LoggingABDecider
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.follow_recommendations.common.constants.GuiceNamedConstants
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.logging.LoggerFactory
import javax.inject.Singleton

object ABDeciderModule extends ExTwitterModule {
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
