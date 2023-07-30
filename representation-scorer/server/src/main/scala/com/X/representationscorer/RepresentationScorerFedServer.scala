package com.X.representationscorer

import com.google.inject.Module
import com.X.inject.thrift.modules.ThriftClientIdModule
import com.X.representationscorer.columns.ListScoreColumn
import com.X.representationscorer.columns.ScoreColumn
import com.X.representationscorer.columns.SimClustersRecentEngagementSimilarityColumn
import com.X.representationscorer.columns.SimClustersRecentEngagementSimilarityUserTweetEdgeColumn
import com.X.representationscorer.modules.CacheModule
import com.X.representationscorer.modules.EmbeddingStoreModule
import com.X.representationscorer.modules.RMSConfigModule
import com.X.representationscorer.modules.TimerModule
import com.X.representationscorer.twistlyfeatures.UserSignalServiceRecentEngagementsClientModule
import com.X.strato.fed._
import com.X.strato.fed.server._

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
