package com.twitter.home_mixer.store

import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaClusterId88Store @Inject() (
  val statsReceiver: StatsReceiver,
  val serviceIdentifier: ServiceIdentifier)
    extends MediaClusterIdStoreTrait {

  override val appId: String = "media_understanding_embeddings_prod"
  override val dataset: String = "media_embedding_twitter_clip_v0_cluster_id_88"
  override val memcacheDest: String = "/s/cache/clip_cluster_id_88"
  override val keyPrefix: String = "" // Empty key prefix as per config
  override val storeName: String = "MediaClusterId88Store"
}
