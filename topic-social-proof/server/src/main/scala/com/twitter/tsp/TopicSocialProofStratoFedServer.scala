package com.twitter.tsp

import com.google.inject.Module
import com.twitter.strato.fed._
import com.twitter.strato.fed.server._
import com.twitter.strato.warmup.Warmer
import com.twitter.tsp.columns.TopicSocialProofColumn
import com.twitter.tsp.columns.TopicSocialProofBatchColumn
import com.twitter.tsp.handlers.UttChildrenWarmupHandler
import com.twitter.tsp.modules.RepresentationScorerStoreModule
import com.twitter.tsp.modules.GizmoduckUserModule
import com.twitter.tsp.modules.TSPClientIdModule
import com.twitter.tsp.modules.TopicListingModule
import com.twitter.tsp.modules.TopicSocialProofStoreModule
import com.twitter.tsp.modules.TopicTweetCosineSimilarityAggregateStoreModule
import com.twitter.tsp.modules.TweetInfoStoreModule
import com.twitter.tsp.modules.TweetyPieClientModule
import com.twitter.tsp.modules.UttClientModule
import com.twitter.tsp.modules.UttLocalizationModule
import com.twitter.util.Future

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
