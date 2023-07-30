package com.X.frigate.pushservice.store

import com.X.conversions.DurationOps._
import com.X.finagle.stats.StatsReceiver
import com.X.onboarding.task.service.thriftscala.FatigueFlowEnrollment
import com.X.stitch.Stitch
import com.X.storage.client.manhattan.bijections.Bijections.BinaryScalaInjection
import com.X.storage.client.manhattan.bijections.Bijections.LongInjection
import com.X.storage.client.manhattan.bijections.Bijections.StringInjection
import com.X.storage.client.manhattan.kv.impl.Component
import com.X.storage.client.manhattan.kv.impl.KeyDescriptor
import com.X.storage.client.manhattan.kv.impl.ValueDescriptor
import com.X.storage.client.manhattan.kv.ManhattanKVClient
import com.X.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.X.storage.client.manhattan.kv.ManhattanKVEndpointBuilder
import com.X.storage.client.manhattan.kv.NoMtlsParams
import com.X.storehaus.ReadableStore
import com.X.storehaus_internal.manhattan.Omega
import com.X.util.Duration
import com.X.util.Future
import com.X.util.Time

case class OCFHistoryStoreKey(userId: Long, fatigueDuration: Duration, fatigueGroup: String)

class OCFPromptHistoryStore(
  manhattanAppId: String,
  dataset: String,
  mtlsParams: ManhattanKVClientMtlsParams = NoMtlsParams
)(
  implicit stats: StatsReceiver)
    extends ReadableStore[OCFHistoryStoreKey, FatigueFlowEnrollment] {

  import ManhattanInjections._

  private val client = ManhattanKVClient(
    appId = manhattanAppId,
    dest = Omega.wilyName,
    mtlsParams = mtlsParams,
    label = "ocf_history_store"
  )
  private val endpoint = ManhattanKVEndpointBuilder(client, defaultMaxTimeout = 5.seconds)
    .statsReceiver(stats.scope("ocf_history_store"))
    .build()

  private val limitResultsTo = 1

  private val datasetKey = keyDesc.withDataset(dataset)

  override def get(storeKey: OCFHistoryStoreKey): Future[Option[FatigueFlowEnrollment]] = {
    val userId = storeKey.userId
    val fatigueGroup = storeKey.fatigueGroup
    val fatigueLength = storeKey.fatigueDuration.inMilliseconds
    val currentTime = Time.now.inMilliseconds
    val fullKey = datasetKey
      .withPkey(userId)
      .from(fatigueGroup)
      .to(fatigueGroup, fatigueLength - currentTime)

    Stitch
      .run(endpoint.slice(fullKey, valDesc, limit = Some(limitResultsTo)))
      .map { results =>
        if (results.nonEmpty) {
          val (_, mhValue) = results.head
          Some(mhValue.contents)
        } else None
      }
  }
}

object ManhattanInjections {
  val keyDesc = KeyDescriptor(Component(LongInjection), Component(StringInjection, LongInjection))
  val valDesc = ValueDescriptor(BinaryScalaInjection(FatigueFlowEnrollment))
}
