package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.timelinemixer.clients.persistence.TimelinePersistenceManhattanClientBuilder
import com.twitter.timelinemixer.clients.persistence.TimelinePersistenceManhattanClientConfig
import com.twitter.timelinemixer.clients.persistence.TimelineResponseBatchesClient
import com.twitter.timelinemixer.clients.persistence.TimelineResponseV3
import javax.inject.Singleton

object TimelinesPersistenceStoreClientModule extends TwitterModule {
  private val StagingDataset = "timeline_response_batches_v5_nonprod"
  private val ProdDataset = "timeline_response_batches_v5"

  @Provides
  @Singleton
  def providesTimelinesPersistenceStoreClient(
    injectedServiceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): TimelineResponseBatchesClient[TimelineResponseV3] = {
    val (timelineResponseBatchesDataset, manhattanReadOnly) =
      injectedServiceIdentifier.environment.toLowerCase match {
        case "prod" => (ProdDataset, false)
        case _ => (StagingDataset, true)
      }

    val timelineResponseBatchesConfig = new TimelinePersistenceManhattanClientConfig {
      val dataset = timelineResponseBatchesDataset
      val isReadOnly = manhattanReadOnly
      val serviceIdentifier = injectedServiceIdentifier
      override val defaultMaxTimeout = 300.milliseconds
      override val maxRetryCount = 1
    }

    TimelinePersistenceManhattanClientBuilder.buildTimelineResponseV3BatchesClient(
      timelineResponseBatchesConfig,
      statsReceiver
    )
  }
}
