package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.bijection.Bufferable
import com.twitter.bijection.Injection
import com.twitter.bijection.scrooge.CompactScalaCodec
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.inject.TwitterModule
import com.twitter.simclusters_v2.thriftscala.ClusterDetails
import com.twitter.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.twitter.storehaus.ReadableStore
import com.twitter.storehaus_internal.manhattan.Athena
import com.twitter.storehaus_internal.manhattan.ManhattanRO
import com.twitter.storehaus_internal.manhattan.ManhattanROConfig
import com.twitter.storehaus_internal.util.ApplicationID
import com.twitter.storehaus_internal.util.DatasetName
import com.twitter.storehaus_internal.util.HDFSPath
import javax.inject.Singleton

object ClusterDetailsModule extends TwitterModule {

  @Provides
  @Singleton
  def providesClusterDetailsStore(
    serviceIdentifier: ServiceIdentifier
  ): ReadableStore[String, ClusterDetails] = {
    implicit val keyInjection: Injection[(String, Int), Array[Byte]] =
      Bufferable.injectionOf[(String, Int)]
    implicit val valueInjection: Injection[ClusterDetails, Array[Byte]] =
      CompactScalaCodec(ClusterDetails)

    val modelName = "20M_145K_2020"

    ManhattanRO
      .getReadableStoreWithMtls[(String, Int), ClusterDetails](
        ManhattanROConfig(
          HDFSPath(""),
          ApplicationID("simclusters_v2"),
          DatasetName("simclusters_v2_cluster_details_20m_145k_2020"),
          Athena
        ),
        ManhattanKVClientMtlsParams(serviceIdentifier)
      ).composeKeyMapping(clusterIdString => (modelName, clusterIdString.toInt))
  }
}
