package com.X.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.google.inject.name.Named
import com.X.inject.XModule
import com.X.simclusters_v2.common.UserId
import com.X.conversions.DurationOps._
import com.X.cr_mixer.model.ModuleNames
import com.X.cr_mixer.param.decider.CrMixerDecider
import com.X.finagle.stats.StatsReceiver
import com.X.finagle.memcached.{Client => MemcachedClient}
import com.X.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.X.storehaus.ReadableStore
import com.X.storehaus_internal.manhattan.Apollo
import com.X.storehaus_internal.manhattan.ManhattanRO
import com.X.storehaus_internal.manhattan.ManhattanROConfig
import com.X.storehaus_internal.util.ApplicationID
import com.X.storehaus_internal.util.DatasetName
import com.X.storehaus_internal.util.HDFSPath
import com.X.bijection.scrooge.BinaryScalaCodec
import com.X.cr_mixer.param.decider.DeciderKey
import com.X.hermit.store.common.DeciderableReadableStore
import com.X.hermit.store.common.ObservedMemcachedReadableStore
import com.X.wtf.candidate.thriftscala.CandidateSeq

object RealGraphStoreMhModule extends XModule {

  @Provides
  @Singleton
  @Named(ModuleNames.RealGraphInStore)
  def providesRealGraphStoreMh(
    decider: CrMixerDecider,
    statsReceiver: StatsReceiver,
    manhattanKVClientMtlsParams: ManhattanKVClientMtlsParams,
    @Named(ModuleNames.UnifiedCache) crMixerUnifiedCacheClient: MemcachedClient,
  ): ReadableStore[UserId, CandidateSeq] = {

    implicit val valueCodec = new BinaryScalaCodec(CandidateSeq)
    val underlyingStore = ManhattanRO
      .getReadableStoreWithMtls[UserId, CandidateSeq](
        ManhattanROConfig(
          HDFSPath(""),
          ApplicationID("cr_mixer_apollo"),
          DatasetName("real_graph_scores_apollo"),
          Apollo),
        manhattanKVClientMtlsParams
      )

    val memCachedStore = ObservedMemcachedReadableStore
      .fromCacheClient(
        backingStore = underlyingStore,
        cacheClient = crMixerUnifiedCacheClient,
        ttl = 24.hours,
      )(
        valueInjection = valueCodec,
        statsReceiver = statsReceiver.scope("memCachedUserRealGraphMh"),
        keyToString = { k: UserId => s"uRGraph/$k" }
      )

    DeciderableReadableStore(
      memCachedStore,
      decider.deciderGateBuilder.idGate(DeciderKey.enableRealGraphMhStoreDeciderKey),
      statsReceiver.scope("RealGraphMh")
    )
  }
}
