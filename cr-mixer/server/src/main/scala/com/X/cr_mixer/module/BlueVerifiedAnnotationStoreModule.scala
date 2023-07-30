package com.X.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.google.inject.name.Named
import com.X.inject.XModule
import com.X.conversions.DurationOps._
import com.X.cr_mixer.model.ModuleNames
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.data_pipeline.scalding.thriftscala.BlueVerifiedAnnotationsV2
import com.X.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.X.storehaus.ReadableStore
import com.X.storehaus_internal.manhattan.Athena
import com.X.storehaus_internal.manhattan.ManhattanRO
import com.X.storehaus_internal.manhattan.ManhattanROConfig
import com.X.storehaus_internal.util.ApplicationID
import com.X.storehaus_internal.util.DatasetName
import com.X.storehaus_internal.util.HDFSPath
import com.X.bijection.scrooge.BinaryScalaCodec
import com.X.hermit.store.common.ObservedCachedReadableStore

object BlueVerifiedAnnotationStoreModule extends XModule {

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
