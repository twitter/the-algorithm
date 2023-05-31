package com.twitter.representation_manager

import com.google.inject.Module
import com.twitter.inject.thrift.modules.ThriftClientIdModule
import com.twitter.representation_manager.columns.topic.LocaleEntityIdSimClustersEmbeddingCol
import com.twitter.representation_manager.columns.topic.TopicIdSimClustersEmbeddingCol
import com.twitter.representation_manager.columns.tweet.TweetSimClustersEmbeddingCol
import com.twitter.representation_manager.columns.user.UserSimClustersEmbeddingCol
import com.twitter.representation_manager.modules.CacheModule
import com.twitter.representation_manager.modules.InterestsThriftClientModule
import com.twitter.representation_manager.modules.LegacyRMSConfigModule
import com.twitter.representation_manager.modules.StoreModule
import com.twitter.representation_manager.modules.TimerModule
import com.twitter.representation_manager.modules.UttClientModule
import com.twitter.strato.fed._
import com.twitter.strato.fed.server._

object RepresentationManagerFedServerMain extends RepresentationManagerFedServer

trait RepresentationManagerFedServer extends StratoFedServer {
  override def dest: String = "/s/representation-manager/representation-manager"
  override val modules: Seq[Module] =
    Seq(
      CacheModule,
      InterestsThriftClientModule,
      LegacyRMSConfigModule,
      StoreModule,
      ThriftClientIdModule,
      TimerModule,
      UttClientModule
    )

  override def columns: Seq[Class[_ <: StratoFed.Column]] =
    Seq(
      classOf[TweetSimClustersEmbeddingCol],
      classOf[UserSimClustersEmbeddingCol],
      classOf[TopicIdSimClustersEmbeddingCol],
      classOf[LocaleEntityIdSimClustersEmbeddingCol]
    )
}
