package com.twitter.home_mixer.store

import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaClusterId95Store @Inject() (
  override val statsReceiver: StatsReceiver,
  override val serviceIdentifier: ServiceIdentifier)
    extends MediaClusterIdStoreTrait {

  override def appId: String = "media_understanding_embeddings_prod"
  override def dataset: String = "media_embedding_twitter_clip_v0_cluster_id_95"
  override def memcacheDest: String = "/s/cache/clip_cluster_id_95"
  override def keyPrefix: String = "" // Empty key prefix as per config
  override def storeName: String = "MediaClusterId95Store"
}
