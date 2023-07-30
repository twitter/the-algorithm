package com.X.tsp

import com.google.inject.Module
import com.X.strato.fed._
import com.X.strato.fed.server._
import com.X.strato.warmup.Warmer
import com.X.tsp.columns.TopicSocialProofColumn
import com.X.tsp.columns.TopicSocialProofBatchColumn
import com.X.tsp.handlers.UttChildrenWarmupHandler
import com.X.tsp.modules.RepresentationScorerStoreModule
import com.X.tsp.modules.GizmoduckUserModule
import com.X.tsp.modules.TSPClientIdModule
import com.X.tsp.modules.TopicListingModule
import com.X.tsp.modules.TopicSocialProofStoreModule
import com.X.tsp.modules.TopicTweetCosineSimilarityAggregateStoreModule
import com.X.tsp.modules.TweetInfoStoreModule
import com.X.tsp.modules.TweetyPieClientModule
import com.X.tsp.modules.UttClientModule
import com.X.tsp.modules.UttLocalizationModule
import com.X.util.Future

object TopicSocialProofStratoFedServerMain extends TopicSocialProofStratoFedServer

trait TopicSocialProofStratoFedServer extends StratoFedServer {
  override def dest: String = "/s/topic-social-proof/topic-social-proof"

  override val modules: Seq[Module] =
    Seq(
      GizmoduckUserModule,
      RepresentationScorerStoreModule,
      TopicSocialProofStoreModule,
      TopicListingModule,
      TopicTweetCosineSimilarityAggregateStoreModule,
      TSPClientIdModule,
      TweetInfoStoreModule,
      TweetyPieClientModule,
      UttClientModule,
      UttLocalizationModule
    )

  override def columns: Seq[Class[_ <: StratoFed.Column]] =
    Seq(
      classOf[TopicSocialProofColumn],
      classOf[TopicSocialProofBatchColumn]
    )

  override def configureWarmer(warmer: Warmer): Unit = {
    warmer.add(
      "uttChildrenWarmupHandler",
      () => {
        handle[UttChildrenWarmupHandler]()
        Future.Unit
      }
    )
  }
}
