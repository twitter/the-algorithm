package com.X.product_mixer.core.module

import com.google.inject.Provides
import com.X.abdecider.ABDeciderFactory
import com.X.abdecider.LoggingABDecider
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.inject.annotations.Flag
import com.X.logging._
import com.X.product_mixer.core.module.product_mixer_flags.ProductMixerFlagModule.ScribeABImpressions
import javax.inject.Singleton

object ABDeciderModule extends XModule {
  private val YmlPath = "/usr/local/config/abdecider/abdecider.yml"

  @Provides
  @Singleton
  def provideLoggingABDecider(
    @Flag(ScribeABImpressions) isScribeAbImpressions: Boolean,
    stats: StatsReceiver
  ): LoggingABDecider = {
    val clientEventsHandler: HandlerFactory =
      if (isScribeAbImpressions) {
        QueueingHandler(
          maxQueueSize = 10000,
          handler = ScribeHandler(
            category = "client_event",
            formatter = BareFormatter,
            level = Some(Level.INFO),
            statsReceiver = stats.scope("abdecider"))
        )
      } else { () =>
        NullHandler
      }

    val factory = LoggerFactory(
      node = "abdecider",
      level = Some(Level.INFO),
      useParents = false,
      handlers = clientEventsHandler :: Nil
    )

    val abDeciderFactory = ABDeciderFactory(
      abDeciderYmlPath = YmlPath,
      scribeLogger = Some(factory()),
      environment = Some("production")
    )

    abDeciderFactory.buildWithLogging()
  }
}
