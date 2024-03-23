package com.ExTwitter.home_mixer.module

import com.google.inject.Provides
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.finagle.mtls.authentication.ServiceIdentifier
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.inject.annotations.Flag
import com.ExTwitter.timelinemixer.clients.persistence.TimelinePersistenceManhattanClientBuilder
import com.ExTwitter.timelinemixer.clients.persistence.TimelinePersistenceManhattanClientConfig
import com.ExTwitter.timelinemixer.clients.persistence.TimelineResponseBatchesClient
import com.ExTwitter.timelinemixer.clients.persistence.TimelineResponseV3
import com.ExTwitter.util.Duration
import javax.inject.Singleton

object TimelinesPersistenceStoreClientModule extends ExTwitterModule {
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
      override val defaultMaxTimeout = timeout
      override val maxRetryCount = 2
    }

    TimelinePersistenceManhattanClientBuilder.buildTimelineResponseV3BatchesClient(
      timelineResponseBatchesConfig,
      statsReceiver
    )
  }
}
