package com.X.home_mixer.module

import com.google.inject.Provides
import com.X.conversions.DurationOps._
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.inject.annotations.Flag
import com.X.timelinemixer.clients.feedback.FeedbackHistoryManhattanClient
import com.X.timelinemixer.clients.feedback.FeedbackHistoryManhattanClientConfig
import com.X.timelines.clients.manhattan.mhv3.ManhattanClientBuilder
import com.X.util.Duration
import javax.inject.Singleton

object FeedbackHistoryClientModule extends XModule {
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
