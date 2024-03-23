package com.ExTwitter.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.google.inject.name.Named
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.frigate.data_pipeline.scalding.thriftscala.BlueVerifiedAnnotationsV2
import com.ExTwitter.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.storehaus_internal.manhattan.Athena
import com.ExTwitter.storehaus_internal.manhattan.ManhattanRO
import com.ExTwitter.storehaus_internal.manhattan.ManhattanROConfig
import com.ExTwitter.storehaus_internal.util.ApplicationID
import com.ExTwitter.storehaus_internal.util.DatasetName
import com.ExTwitter.storehaus_internal.util.HDFSPath
import com.ExTwitter.bijection.scrooge.BinaryScalaCodec
import com.ExTwitter.hermit.store.common.ObservedCachedReadableStore

object BlueVerifiedAnnotationStoreModule extends ExTwitterModule {

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
