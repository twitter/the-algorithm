package com.ExTwitter.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.google.inject.name.Named
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.simclusters_v2.common.UserId
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.cr_mixer.param.decider.CrMixerDecider
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.finagle.memcached.{Client => MemcachedClient}
import com.ExTwitter.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.storehaus_internal.manhattan.Apollo
import com.ExTwitter.storehaus_internal.manhattan.ManhattanRO
import com.ExTwitter.storehaus_internal.manhattan.ManhattanROConfig
import com.ExTwitter.storehaus_internal.util.ApplicationID
import com.ExTwitter.storehaus_internal.util.DatasetName
import com.ExTwitter.storehaus_internal.util.HDFSPath
import com.ExTwitter.bijection.scrooge.BinaryScalaCodec
import com.ExTwitter.cr_mixer.param.decider.DeciderKey
import com.ExTwitter.hermit.store.common.DeciderableReadableStore
import com.ExTwitter.hermit.store.common.ObservedMemcachedReadableStore
import com.ExTwitter.wtf.candidate.thriftscala.CandidateSeq

object RealGraphStoreMhModule extends ExTwitterModule {

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
