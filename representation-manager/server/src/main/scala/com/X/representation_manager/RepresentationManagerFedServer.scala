package com.X.representation_manager

import com.google.inject.Module
import com.X.inject.thrift.modules.ThriftClientIdModule
import com.X.representation_manager.columns.topic.LocaleEntityIdSimClustersEmbeddingCol
import com.X.representation_manager.columns.topic.TopicIdSimClustersEmbeddingCol
import com.X.representation_manager.columns.tweet.TweetSimClustersEmbeddingCol
import com.X.representation_manager.columns.user.UserSimClustersEmbeddingCol
import com.X.representation_manager.modules.CacheModule
import com.X.representation_manager.modules.InterestsThriftClientModule
import com.X.representation_manager.modules.LegacyRMSConfigModule
import com.X.representation_manager.modules.StoreModule
import com.X.representation_manager.modules.TimerModule
import com.X.representation_manager.modules.UttClientModule
import com.X.strato.fed._
import com.X.strato.fed.server._

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
