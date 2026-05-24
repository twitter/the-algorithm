package com.twitter.representationscorer

import com.google.inject.Module
import com.twitter.inject.thrift.modules.ThriftClientIdModule
import com.twitter.representationscorer.columns.ListScoreColumn
import com.twitter.representationscorer.columns.ScoreColumn
import com.twitter.representationscorer.columns.SimClustersRecentEngagementSimilarityColumn
import com.twitter.representationscorer.columns.SimClustersRecentEngagementSimilarityUserTweetEdgeColumn
import com.twitter.representationscorer.modules.CacheModule
import com.twitter.representationscorer.modules.EmbeddingStoreModule
import com.twitter.representationscorer.modules.RMSConfigModule
import com.twitter.representationscorer.modules.TimerModule
import com.twitter.representationscorer.twistlyfeatures.UserSignalServiceRecentEngagementsClientModule
import com.twitter.strato.fed._
import com.twitter.strato.fed.server._

object RepresentationScorerFedServerMain extends RepresentationScorerFedServer

trait RepresentationScorerFedServer extends StratoFedServer {
  override def dest: String = "/s/representation-scorer/representation-scorer"
  override val modules: Seq[Module] =
    Seq(
      CacheModule,
      ThriftClientIdModule,
      UserSignalServiceRecentEngagementsClientModule,
      TimerModule,
      RMSConfigModule,
      EmbeddingStoreModule
    )

  override def columns: Seq[Class[_ <: StratoFed.Column]] =
    Seq(
      classOf[ListScoreColumn],
      classOf[ScoreColumn],
      classOf[SimClustersRecentEngagementSimilarityUserTweetEdgeColumn],
      classOf[SimClustersRecentEngagementSimilarityColumn]
    )
}
