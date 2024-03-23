package com.ExTwitter.home_mixer.module

import com.google.inject.Provides
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.finagle.mtls.authentication.ServiceIdentifier
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.inject.annotations.Flag
import com.ExTwitter.timelinemixer.clients.feedback.FeedbackHistoryManhattanClient
import com.ExTwitter.timelinemixer.clients.feedback.FeedbackHistoryManhattanClientConfig
import com.ExTwitter.timelines.clients.manhattan.mhv3.ManhattanClientBuilder
import com.ExTwitter.util.Duration
import javax.inject.Singleton

object FeedbackHistoryClientModule extends ExTwitterModule {
  private val ProdDataset = "feedback_history"
  private val StagingDataset = "feedback_history_nonprod"
  private final val Timeout = "mh_feedback_history.timeout"

  flag[Duration](Timeout, 150.millis, "Timeout per request")

  @Provides
  @Singleton
  def providesFeedbackHistoryClient(
    @Flag(Timeout) timeout: Duration,
    serviceId: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ) = {
    val manhattanDataset = serviceId.environment.toLowerCase match {
      case "prod" => ProdDataset
      case _ => StagingDataset
    }

    val config = new FeedbackHistoryManhattanClientConfig {
      val dataset = manhattanDataset
      val isReadOnly = true
      val serviceIdentifier = serviceId
      override val defaultMaxTimeout = timeout
    }

    new FeedbackHistoryManhattanClient(
      ManhattanClientBuilder.buildManhattanEndpoint(config, statsReceiver),
      manhattanDataset,
      statsReceiver
    )
  }
}
