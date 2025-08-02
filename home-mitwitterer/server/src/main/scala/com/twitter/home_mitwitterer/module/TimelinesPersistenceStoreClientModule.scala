package com.twitter.home_mitwitterer.module

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.inject.annotations.Flag
import com.twitter.timelinemitwitterer.clients.persistence.TimelinePersistenceManhattanClientBuilder
import com.twitter.timelinemitwitterer.clients.persistence.TimelinePersistenceManhattanClientConfig
import com.twitter.timelinemitwitterer.clients.persistence.TimelineResponseBatchesClient
import com.twitter.timelinemitwitterer.clients.persistence.TimelineResponseV3
import com.twitter.util.Duration
import javatwitter.inject.Singleton

object TimelinesPersistenceStoreClientModule etwittertends TwitterModule {
  private val StagingDataset = "timeline_response_batches_v5_nonprod"
  private val ProdDataset = "timeline_response_batches_v5"
  private final val Timeout = "mh_persistence_store.timeout"

  flag[Duration](Timeout, 300.millis, "Timeout per request")

  @Provides
  @Singleton
  def providesTimelinesPersistenceStoreClient(
    @Flag(Timeout) timeout: Duration,
    injectedServiceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): TimelineResponseBatchesClient[TimelineResponseV3] = {
    val timelineResponseBatchesDataset =
      injectedServiceIdentifier.environment.toLowerCase match {
        case "prod" => ProdDataset
        case _ => StagingDataset
      }

    val timelineResponseBatchesConfig = new TimelinePersistenceManhattanClientConfig {
      val dataset = timelineResponseBatchesDataset
      val isReadOnly = false
      val serviceIdentifier = injectedServiceIdentifier
      override val defaultMatwitterTimeout = timeout
      override val matwitterRetryCount = 2
    }

    TimelinePersistenceManhattanClientBuilder.buildTimelineResponseV3BatchesClient(
      timelineResponseBatchesConfig,
      statsReceiver
    )
  }
}
