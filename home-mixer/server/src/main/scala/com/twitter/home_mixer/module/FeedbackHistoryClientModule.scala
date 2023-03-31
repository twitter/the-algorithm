package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.timelinemixer.clients.feedback.FeedbackHistoryManhattanClient
import com.twitter.timelinemixer.clients.feedback.FeedbackHistoryManhattanClientConfig
import com.twitter.timelines.clients.manhattan.mhv3.ManhattanClientBuilder
import javax.inject.Singleton

object FeedbackHistoryClientModule extends TwitterModule {
  private val ProdDataset = "feedback_history"
  private val StagingDataset = "feedback_history_nonprod"

  @Provides
  @Singleton
  def providesFeedbackHistoryClient(
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
    }

    new FeedbackHistoryManhattanClient(
      ManhattanClientBuilder.buildManhattanEndpoint(config, statsReceiver),
      manhattanDataset,
      statsReceiver
    )
  }
}
