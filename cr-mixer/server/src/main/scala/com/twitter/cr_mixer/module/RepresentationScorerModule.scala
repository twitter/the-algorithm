package com.twitter.cr_mixer.module

import com.twitter.inject.TwitterModule
import com.twitter.simclusters_v2.thriftscala.EmbeddingType
import com.twitter.simclusters_v2.thriftscala.InternalId
import com.twitter.simclusters_v2.thriftscala.ModelVersion
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.common.store.strato.StratoFetchableStore
import com.twitter.simclusters_v2.common.UserId
import com.twitter.simclusters_v2.common.TweetId
import com.twitter.strato.client.{Client => StratoClient}
import com.twitter.storehaus.ReadableStore
import com.twitter.simclusters_v2.thriftscala.ScoringAlgorithm
import com.google.inject.Provides
import com.google.inject.Singleton
import com.twitter.hermit.store.common.ObservedReadableStore
import javax.inject.Named
import com.twitter.cr_mixer.model.ModuleNames
import com.twitter.representationscorer.thriftscala.ListScoreId

object RepresentationScorerModule extends TwitterModule {

  private val rsxColumnPath = "recommendations/representation_scorer/listScore"

  private final val SimClusterModelVersion = ModelVersion.Model20m145k2020
  private final val TweetEmbeddingType = EmbeddingType.LogFavBasedTweet

  @Provides
  @Singleton
  @Named(ModuleNames.RsxStore)
  def providesRepresentationScorerStore(
    statsReceiver: StatsReceiver,
    stratoClient: StratoClient,
  ): ReadableStore[(UserId, TweetId), Double] = {
    ObservedReadableStore(
      StratoFetchableStore
        .withUnitView[ListScoreId, Double](stratoClient, rsxColumnPath).composeKeyMapping[(
          UserId,
          TweetId
        )] { key =>
          representationScorerStoreKeyMapping(key._1, key._2)
        }
    )(statsReceiver.scope("rsx_store"))
  }

  private def representationScorerStoreKeyMapping(t1: TweetId, t2: TweetId): ListScoreId = {
    ListScoreId(
      algorithm = ScoringAlgorithm.PairEmbeddingLogCosineSimilarity,
      modelVersion = SimClusterModelVersion,
      targetEmbeddingType = TweetEmbeddingType,
      targetId = InternalId.TweetId(t1),
      candidateEmbeddingType = TweetEmbeddingType,
      candidateIds = Seq(InternalId.TweetId(t2))
    )
  }
}
