package com.ExTwitter.cr_mixer.module

import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.simclusters_v2.thriftscala.EmbeddingType
import com.ExTwitter.simclusters_v2.thriftscala.InternalId
import com.ExTwitter.simclusters_v2.thriftscala.ModelVersion
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.frigate.common.store.strato.StratoFetchableStore
import com.ExTwitter.simclusters_v2.common.UserId
import com.ExTwitter.simclusters_v2.common.TweetId
import com.ExTwitter.strato.client.{Client => StratoClient}
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.simclusters_v2.thriftscala.ScoringAlgorithm
import com.google.inject.Provides
import com.google.inject.Singleton
import com.ExTwitter.hermit.store.common.ObservedReadableStore
import javax.inject.Named
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.representationscorer.thriftscala.ListScoreId

object RepresentationScorerModule extends ExTwitterModule {

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
