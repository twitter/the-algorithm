package com.twitter.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.google.inject.name.Named
import com.twitter.inject.TwitterModule
import com.twitter.conversions.DurationOps._
import com.twitter.cr_mixer.model.ModuleNames
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.data_pipeline.scalding.thriftscala.BlueVerifiedAnnotationsV2
import com.twitter.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.twitter.storehaus.ReadableStore
import com.twitter.storehaus_internal.manhattan.Athena
import com.twitter.storehaus_internal.manhattan.ManhattanRO
import com.twitter.storehaus_internal.manhattan.ManhattanROConfig
import com.twitter.storehaus_internal.util.ApplicationID
import com.twitter.storehaus_internal.util.DatasetName
import com.twitter.storehaus_internal.util.HDFSPath
import com.twitter.bijection.scrooge.BinaryScalaCodec
import com.twitter.hermit.store.common.ObservedCachedReadableStore

object BlueVerifiedAnnotationStoreModule extends TwitterModule {

  @Provides
  @Singleton
  @Named(ModuleNames.BlueVerifiedAnnotationStore)
  def providesBlueVerifiedAnnotationStore(
    statsReceiver: StatsReceiver,
    manhattanKVClientMtlsParams: ManhattanKVClientMtlsParams,
  ): ReadableStore[String, BlueVerifiedAnnotationsV2] = {

    implicit val valueCodec = new BinaryScalaCodec(BlueVerifiedAnnotationsV2)

    val underlyingStore = ManhattanRO
      .getReadableStoreWithMtls[String, BlueVerifiedAnnotationsV2](
        ManhattanROConfig(
          HDFSPath(""),
          ApplicationID("content_recommender_athena"),
          DatasetName("blue_verified_annotations"),
          Athena),
        manhattanKVClientMtlsParams
      )

    ObservedCachedReadableStore.from(
      underlyingStore,
      ttl = 24.hours,
      maxKeys = 100000,
      windowSize = 10000L,
      cacheName = "blue_verified_annotation_cache"
    )(statsReceiver.scope("inMemoryCachedBlueVerifiedAnnotationStore"))
  }
}
