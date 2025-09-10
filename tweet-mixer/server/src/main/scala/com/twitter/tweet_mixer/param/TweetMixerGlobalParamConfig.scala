package com.twitter.tweet_mixer.param

import com.twitter.product_mixer.core.functional_component.configapi.registry.GlobalParamConfig
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Register Params that do not relate to a specific product. See GlobalParamConfig -> ParamConfig
 * for hooks to register Params based on type.
 */
@Singleton
class TweetMixerGlobalParamConfig @Inject() () extends GlobalParamConfig {

  override val boundedIntFSOverrides =
    TweetMixerGlobalParams.boundedIntFSOverrides ++
      USSParams.boundedIntFSOverrides ++
      HighQualitySourceSignalParams.intFSOverrides ++
      UTGParams.boundedIntFSOverrides ++
      UVGParams.boundedIntFSOverrides ++
      PopularGeoTweetsParams.boundedIntFSOverrides ++
      PopGrokTopicTweetsParams.boundedIntFSOverrides ++
      PopularTopicTweetsParams.boundedIntFSOverrides ++
      CertoTopicTweetsParams.boundedIntFSOverrides ++
      EvergreenParams.boundedIntFSOverrides ++
      SimClustersANNParams.boundedIntFSOverrides ++
      SkitTopicTweetsParams.boundedIntFSOverrides ++
      ContentEmbeddingAnnParams.boundedIntFSOverrides

  override val booleanDeciderOverrides = TweetMixerGlobalParams.booleanDeciderOverrides

  override val boundedDoubleDeciderOverrides = TweetMixerGlobalParams.boundedDoubleDeciderOverrides

  override val enumFSOverrides =
    TweetMixerGlobalParams.enumFSOverrides ++
      USSParams.enumFSOverrides ++
      SimClustersANNParams.enumFSOverrides ++
      UTGParams.enumFSOverrides ++
      UVGParams.enumFSOverrides

  override val booleanFSOverrides =
    TweetMixerGlobalParams.booleanFSOverrides ++
      USSParams.booleanFSOverrides ++
      HighQualitySourceSignalParams.booleanFSOverrides ++
      SimClustersANNParams.booleanFSOverrides ++
      UTGParams.booleanFSOverrides ++
      UVGParams.booleanFSOverrides ++
      PopularGeoTweetsParams.booleanFSOverrides ++
      PopGrokTopicTweetsParams.booleanFSOverrides ++
      PopularTopicTweetsParams.booleanFSOverrides ++
      CertoTopicTweetsParams.booleanFSOverrides ++
      EarlybirdInNetworkTweetsParams.booleanFSOverrides ++
      UserLocationParams.booleanFSOverrides ++
      ContentEmbeddingAnnParams.booleanFSOverrides ++
      CandidateSourceParams.booleanFSOverrides ++
      SkitTopicTweetsParams.booleanFSOverrides ++
      CuratedUserTlsPerLanguageParams.booleanFSOverrides

  override val stringFSOverrides =
    SimClustersANNParams.stringFSOverrides ++ TweetMixerGlobalParams.stringFSOverrides

  override val boundedDurationFSOverrides =
    TweetMixerGlobalParams.boundedDurationFSOverrides ++ USSParams.boundedDurationFSOverrides ++ SkitTopicTweetsParams.boundedDurationFSOverrides

  override val boundedDoubleFSOverrides =
    TweetMixerGlobalParams.boundedDoubleFSOverrides ++
      HighQualitySourceSignalParams.boundedDoubleFSOverrides ++
      SimClustersANNParams.boundedDoubleFSOverrides ++
      UTGParams.boundedDoubleFSOverrides ++
      UVGParams.boundedDoubleFSOverrides ++
      CertoTopicTweetsParams.boundedDoubleFsOverrides ++
      ContentEmbeddingAnnParams.boundedDoubleFSOverrides

  override val stringSeqFSOverrides =
    PopularGeoTweetsParams.stringSeqFSOverrides ++
      PopularTopicTweetsParams.stringSeqFSOverrides

  override val longSeqFSOverrides =
    PopularTopicTweetsParams.longSeqFSOverrides ++
      CuratedUserTlsPerLanguageParams.longSeqFSOverrides

  override val boundedLongFSOverrides = SkitTopicTweetsParams.boundedLongFSOverrides
}
