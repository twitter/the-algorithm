package com.twitter.tweet_mixer.product.home_recommended_tweets

import com.twitter.product_mixer.component_library.feature_hydrator.query.async.AsyncQueryFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.query.location.UserLocationQueryFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.query.param_gated.ParamGatedQueryFeatureHydrator
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.component_library.selector.Bucketer
import com.twitter.product_mixer.component_library.selector.DropAllCandidates
import com.twitter.product_mixer.component_library.selector.DropDuplicateCandidates
import com.twitter.product_mixer.component_library.selector.DropDuplicateResults
import com.twitter.product_mixer.component_library.selector.DropMaxResults
import com.twitter.product_mixer.component_library.selector.DropRequestedMaxResults
import com.twitter.product_mixer.component_library.selector.IdDuplicationKey
import com.twitter.product_mixer.component_library.selector.InsertAppendRatioResults
import com.twitter.product_mixer.component_library.selector.InsertAppendResults
import com.twitter.product_mixer.component_library.selector.SelectConditionally
import com.twitter.product_mixer.core.functional_component.common.AllPipelines
import com.twitter.product_mixer.core.functional_component.common.alert.NotificationGroup
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseQueryFeatureHydrator
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.marshaller.TransportMarshaller
import com.twitter.product_mixer.core.functional_component.premarshaller.DomainMarshaller
import com.twitter.product_mixer.core.functional_component.selector.Selector
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.RecommendationPipelineIdentifier
import com.twitter.product_mixer.core.pipeline.FailOpenPolicy
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.product_mixer.core.pipeline.recommendation.RecommendationPipelineConfig
import com.twitter.product_mixer.core.pipeline.recommendation.RecommendationPipelineConfig.scoringPipelinesStep
import com.twitter.product_mixer.core.pipeline.scoring.ScoringPipelineConfig
import com.twitter.timelines.impressionbloomfilter.{thriftscala => blm}
import com.twitter.tweet_mixer.candidate_pipeline.CuratedUserTlsPerLanguageCandidatePipelineConfigFactory
import com.twitter.tweet_mixer.candidate_pipeline._
import com.twitter.tweet_mixer.feature.AuthorIdFeature
import com.twitter.tweet_mixer.feature.USSFeatures
import com.twitter.tweet_mixer.functional_component.TweetMixerFunctionalComponents
import com.twitter.tweet_mixer.functional_component.filter.ImpressedTweetsBloomFilter
import com.twitter.tweet_mixer.functional_component.filter.IsVideoTweetFilter
import com.twitter.tweet_mixer.functional_component.filter.MaxViewCountFilter
import com.twitter.tweet_mixer.functional_component.filter.GrokFilter
import com.twitter.tweet_mixer.functional_component.filter.MinScoreFilter
import com.twitter.tweet_mixer.functional_component.gate.AllowLowSignalUserGate
import com.twitter.tweet_mixer.functional_component.hydrator.GizmoduckQueryFeatureHydrator
import com.twitter.tweet_mixer.functional_component.hydrator.HaploliteQueryFeatureHydrator
import com.twitter.tweet_mixer.functional_component.hydrator.HydraRankingPreparationQueryFeatureHydrator
import com.twitter.tweet_mixer.functional_component.hydrator.ImpressionBloomFilterQueryFeatureHydratorFactory
import com.twitter.tweet_mixer.functional_component.hydrator.LastNonPollingTimeQueryFeatureHydrator
import com.twitter.tweet_mixer.functional_component.hydrator.RealGraphInNetworkScoresQueryFeatureHydrator
import com.twitter.tweet_mixer.functional_component.hydrator.SGSFollowedUsersQueryFeatureHydrator
import com.twitter.tweet_mixer.functional_component.hydrator.USSDeepRetrievalTweetEmbeddingFeatureHydrator
import com.twitter.tweet_mixer.functional_component.hydrator.USSGrokCategoryFeatureHydratorFactory
import com.twitter.tweet_mixer.functional_component.side_effect.DeepRetrievalAdHocSideEffect
import com.twitter.tweet_mixer.functional_component.side_effect.EvergreenVideosSideEffect
import com.twitter.tweet_mixer.functional_component.side_effect.HydraScoringSideEffect
import com.twitter.tweet_mixer.functional_component.side_effect.PublishGroxUserInterestsSideEffect
import com.twitter.tweet_mixer.functional_component.side_effect.RequestMultimodalEmbeddingSideEffect
import com.twitter.tweet_mixer.functional_component.side_effect.ScribeServedCandidatesSideEffectFactory
import com.twitter.tweet_mixer.functional_component.side_effect.SelectedStatsSideEffect
import com.twitter.tweet_mixer.marshaller.response.TweetMixerResponseTransportMarshaller
import com.twitter.tweet_mixer.model.response.TweetMixerResponse
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ContentExplorationNonVideoTweetFeaturesEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalFilterOldSignalsEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.DeepRetrievalNonVideoTweetFeaturesEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.LastNonPollingTimeFeatureHydratorEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.LowSignalUserBackfillRatioParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.LowSignalUserGrokTopicRatioParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.LowSignalUserPopGeoRatioParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.LowSignalUserSimclustersRatioParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.UVGHighQualityTweetBasedExpansionEnabled
import com.twitter.tweet_mixer.param.UserLocationParams.UserLocationEnabled
import com.twitter.tweet_mixer.product.home_recommended_tweets.marshaller.response.HomeRecommendedTweetsDomainResponseMarshaller
import com.twitter.tweet_mixer.product.home_recommended_tweets.model.request.HomeRecommendedTweetsQuery
import com.twitter.tweet_mixer.product.home_recommended_tweets.param.HomeRecommendedTweetsParam._
import com.twitter.tweet_mixer.scorer.HydraScorer
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.ForYouGroupMap
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultEmptyResponseRateAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants.ContentExplorationEvergreenDRTweetTweet
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants._
import com.twitter.tweet_mixer.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRecommendedTweetsRecommendationPipelineConfig @Inject() (
  // Query Feature Hydrators
  gizmoduckQueryFeatureHydrator: GizmoduckQueryFeatureHydrator,
  haploliteQueryFeatureHydrator: HaploliteQueryFeatureHydrator,
  hydraRankingPreparationQueryFeatureHydrator: HydraRankingPreparationQueryFeatureHydrator,
  impressionBloomFilterQueryFeatureHydratorFactory: ImpressionBloomFilterQueryFeatureHydratorFactory,
  followedUsersQueryFeatureHydrator: SGSFollowedUsersQueryFeatureHydrator,
  realgraphInNetworkScoresQueryFeatureHydrator: RealGraphInNetworkScoresQueryFeatureHydrator,
  userLocationQueryFeatureHydrator: UserLocationQueryFeatureHydrator,
  ussGrokCategoryFeatureHydratorFactory: USSGrokCategoryFeatureHydratorFactory,
  ussDeepRetrievalTweetEmbeddingFeatureHydrator: USSDeepRetrievalTweetEmbeddingFeatureHydrator,
  lastNonPollingTimeQueryFeatureHydrator: LastNonPollingTimeQueryFeatureHydrator,
  // Candidate Pipelines
  haploliteCandidatePipelineConfigFactory: HaploliteCandidatePipelineConfigFactory,
  popularGeoTweetsCandidatePipelineConfigFactory: PopularGeoTweetsCandidatePipelineConfigFactory,
  popGrokTopicTweetsCandidatePipelineConfigFactory: PopGrokTopicTweetsCandidatePipelineConfigFactory,
  popularTopicTweetsCandidatePipelineConfigFactory: PopularTopicTweetsCandidatePipelineConfigFactory,
  simclustersInterestedInCandidatePipelineConfigFactory: SimclustersInterestedInCandidatePipelineConfigFactory,
  simclustersProducerBasedCandidatePipelineConfigFactory: SimclustersProducerBasedCandidatePipelineConfigFactory,
  simclustersTweetBasedCandidatePipelineConfigFactory: SimclustersTweetBasedCandidatePipelineConfigFactory,
  contentAnnTweetBasedCandidatePipelineConfigFactory: ContentAnnTweetBasedCandidatePipelineConfigFactory,
  twHINRebuildTweetSimilarityCandidatePipelineConfigFactory: TwHINRebuildTweetSimilarityCandidatePipelineConfigFactory,
  twHINTweetSimilarityCandidatePipelineConfigFactory: TwHINTweetSimilarityCandidatePipelineConfigFactory,
  utegCandidatePipelineConfigFactory: UTEGCandidatePipelineConfigFactory,
  utgExpansionTweetBasedCandidatePipelineConfigFactory: UTGExpansionTweetBasedCandidatePipelineConfigFactory,
  utgTweetBasedCandidatePipelineConfigFactory: UTGTweetBasedCandidatePipelineConfigFactory,
  uvgExpansionTweetBasedCandidatePipelineConfigFactory: UVGExpansionTweetBasedCandidatePipelineConfigFactory,
  uvgTweetBasedCandidatePipelineConfigFactory: UVGTweetBasedCandidatePipelineConfigFactory,
  deepRetrievalUserTweetSimilarityCandidatePipelineConfig: DeepRetrievalUserTweetSimilarityCandidatePipelineConfig,
  deepRetrievalTweetTweetSimilarityCandidatePipelineConfigFactory: DeepRetrievalTweetTweetSimilarityCandidatePipelineConfigFactory,
  deepRetrievalTweetTweetEmbeddingSimilarityCandidatePipelineConfigFactory: DeepRetrievalTweetTweetEmbeddingSimilarityCandidatePipelineConfigFactory,
  contentExplorationEmbeddingSimilarityCandidatePipelineConfigFactory: ContentExplorationEmbeddingSimilarityCandidatePipelineConfigFactory,
  twhinUserTweetSimilarityCandidatePipelineConfig: TwhinUserTweetSimilarityCandidatePipelineConfig,
  contentExplorationEmbeddingSimilarityTierTwoCandidatePipelineConfigFactory: ContentExplorationEmbeddingSimilarityTierTwoCandidatePipelineConfigFactory,
  contentExplorationDRTweetTweetCandidatePipelineConfigFactory: ContentExplorationDRTweetTweetCandidatePipelineConfigFactory,
  contentExplorationDRTweetTweetTierTwoCandidatePipelineConfigFactory: ContentExplorationDRTweetTweetTierTwoCandidatePipelineConfigFactory,
  contentExplorationEvergreenDRTweetTweetCandidatePipelineConfigFactory: ContentExplorationEvergreenDRTweetTweetCandidatePipelineConfigFactory,
  contentExplorationDRUserTweetCandidatePipelineConfig: ContentExplorationDRUserTweetCandidatePipelineConfig,
  contentExplorationDRUserTweetTierTwoCandidatePipelineConfig: ContentExplorationDRUserTweetTierTwoCandidatePipelineConfig,
  contentExplorationSimclusterColdCandidatePipelineConfig: ContentExplorationSimclusterColdCandidatePipelineConfig,
  userInterestSummaryCandidatePipelineConfigFactory: UserInterestsSummaryCandidatePipelineConfigFactory,
  evergreenDRUserTweetCandidatePipelineConfig: EvergreenDRUserTweetCandidatePipelineConfig,
  evergreenDRCrossBorderUserTweetCandidatePipelineConfig: EvergreenDRCrossBorderUserTweetCandidatePipelineConfig,
  earlybirdInNetworkCandidatePipelineConfigFactory: EarlybirdInNetworkCandidatePipelineConfigFactory,
  evergreenVideosCandidatePipelineConfigFactory: EvergreenVideosCandidatePipelineConfigFactory,
  curatedUserTlsPerLanguageCandidatePipelineConfigFactory: CuratedUserTlsPerLanguageCandidatePipelineConfigFactory,
  qigSearchHistoryTweetsCandidatePipelineConfigFactory: QigSearchHistoryTweetsCandidatePipelineConfigFactory,
  // Scoring
  hydraScorer: HydraScorer,
  // SideEffect
  hydraScoringSideEffect: HydraScoringSideEffect,
  selectedStatsSideEffect: SelectedStatsSideEffect,
  evergreenVideosSideEffect: EvergreenVideosSideEffect,
  deepRetrievalAdHocSideEffect: DeepRetrievalAdHocSideEffect,
  scribeServedCandidatesSideEffectFactory: ScribeServedCandidatesSideEffectFactory,
  publishGroxUserInterestsSideEffect: PublishGroxUserInterestsSideEffect,
  requestMultimodalEmbeddingSideEffect: RequestMultimodalEmbeddingSideEffect,
  // Domain Response Marshaller
  homeRecommendedTweetsDomainResponseMarshaller: HomeRecommendedTweetsDomainResponseMarshaller,
  // Transport Response Marshaller
  tweetMixerResponseTransportMarshaller: TweetMixerResponseTransportMarshaller,
  // Remaining functional components
  tweetMixerFunctionalComponents: TweetMixerFunctionalComponents,
) extends RecommendationPipelineConfig[
      HomeRecommendedTweetsQuery,
      TweetCandidate,
      TweetMixerResponse,
      t.TweetMixerRecommendationResponse
    ] {

  // Keep it same as home recommended tweets domain marshaller name
  private val identifierPrefix: String = "HomeRecommendedTweets"

  override val identifier: RecommendationPipelineIdentifier =
    RecommendationPipelineIdentifier(identifierPrefix)

  private val impressionBloomFilterQueryFeatureHydrator =
    impressionBloomFilterQueryFeatureHydratorFactory
      .build[HomeRecommendedTweetsQuery](blm.SurfaceArea.HomeTimeline)

  implicit val notificationGroupMap: Map[String, NotificationGroup] = ForYouGroupMap

  override def fetchQueryFeatures: Seq[
    QueryFeatureHydrator[HomeRecommendedTweetsQuery]
  ] = tweetMixerFunctionalComponents.globalQueryFeatureHydrators ++ Seq(
    AsyncQueryFeatureHydrator(scoringPipelinesStep, hydraRankingPreparationQueryFeatureHydrator),
    haploliteQueryFeatureHydrator,
    gizmoduckQueryFeatureHydrator,
    impressionBloomFilterQueryFeatureHydrator,
    followedUsersQueryFeatureHydrator,
    realgraphInNetworkScoresQueryFeatureHydrator,
    ParamGatedQueryFeatureHydrator(
      UserLocationEnabled,
      userLocationQueryFeatureHydrator
    ),
    ParamGatedQueryFeatureHydrator(
      LastNonPollingTimeFeatureHydratorEnabled,
      lastNonPollingTimeQueryFeatureHydrator
    )
  )

  override def fetchQueryFeaturesPhase2: Seq[
    BaseQueryFeatureHydrator[HomeRecommendedTweetsQuery, _]
  ] = Seq(
    ussGrokCategoryFeatureHydratorFactory.build(tweetBasedSignalFn),
    ussDeepRetrievalTweetEmbeddingFeatureHydrator,
  )

  private val tweetBasedSignalFn: PipelineQuery => Seq[Long] = { query =>
    USSFeatures.getSignals[Long](query, USSFeatures.TweetFeatures)
  }

  private val producerBasedSignalFn: PipelineQuery => Seq[Long] = { query =>
    USSFeatures.getSignals[Long](query, USSFeatures.ProducerFeatures)
  }

  private val contentExplorationTweetBasedSignalFn: PipelineQuery => Seq[Long] = { query =>
    if (query.params(ContentExplorationNonVideoTweetFeaturesEnabled))
      USSFeatures.getSignals[Long](query, USSFeatures.ExplicitEngagementTweetFeatures)
    else USSFeatures.getSignals[Long](query, USSFeatures.TweetFeatures)
  }

  private val deepRetrievalTweetBasedSignalFn: PipelineQuery => Seq[Long] = { query =>
    val filterOldSignals = query.params(DeepRetrievalFilterOldSignalsEnabled)
    if (query.params(DeepRetrievalNonVideoTweetFeaturesEnabled)) {
      USSFeatures.getSignals[Long](
        query,
        USSFeatures.NonVideoTweetFeatures,
        filterOldSignals = filterOldSignals
      )
    } else {
      USSFeatures
        .getSignals[Long](query, USSFeatures.TweetFeatures, filterOldSignals = filterOldSignals)
    }
  }

  private val searcherRealtimeHistorySignalFn: PipelineQuery => Seq[String] = { query =>
    USSFeatures.getSignals[String](query, USSFeatures.SearcherRealtimeHistoryFeatures)
  }

  private val highQualityTweetBasedSignalFn: PipelineQuery => Seq[Long] = { query =>
    USSFeatures.getSignals[Long](
      query,
      if (query.params(UVGHighQualityTweetBasedExpansionEnabled)) {
        USSFeatures.HighQualityTweetFeatures
      } else { Set.empty })
  }

  val popularGeoCandidatePipelineIdentifier =
    popularGeoTweetsCandidatePipelineConfigFactory.build(identifierPrefix).identifier
  val popGrokTopicTweetsCandidatePipelineIdentifier =
    popGrokTopicTweetsCandidatePipelineConfigFactory.build(identifierPrefix).identifier
  val popularTopicCandidatePipelineIdentifier =
    popularTopicTweetsCandidatePipelineConfigFactory.build(identifierPrefix).identifier
  val utegCandidatePipelineIdentifier =
    utegCandidatePipelineConfigFactory.build(identifierPrefix).identifier
  val simclustersProducerCandidatePipelineIdentifier =
    simclustersProducerBasedCandidatePipelineConfigFactory
      .build(identifierPrefix, producerBasedSignalFn).identifier
  val simclustersInterestedInCandidatePipelineIdentifier =
    simclustersInterestedInCandidatePipelineConfigFactory
      .build(identifierPrefix).identifier

  private val contentExplorationPipelineIds = Set(
    ContentExplorationEmbeddingSimilarity,
    ContentExplorationEmbeddingSimilarityTierTwo,
    ContentExplorationDRTweetTweet,
    ContentExplorationDRTweetTweetTierTwo,
    ContentExplorationDRUserTweet,
    ContentExplorationDRUserTweetTierTwo,
    ContentExplorationEvergreenDRTweetTweet
  ).map(pipeline => CandidatePipelineIdentifier(identifierPrefix + pipeline))

  private val deepRetrievalTweetTweetPipelineIds = Set(
    DeepRetrievalTweetTweetSimilarity,
    DeepRetrievalTweetTweetEmbeddingSimilarity
  ).map(pipeline => CandidatePipelineIdentifier(identifierPrefix + pipeline))

  private val uvgPipelineId = CandidatePipelineIdentifier(identifierPrefix + UVGTweetBased)
  private val uvgExpansionPipelineId = CandidatePipelineIdentifier(
    identifierPrefix + UVGExpansionTweetBased)

  override def candidatePipelines: Seq[
    CandidatePipelineConfig[HomeRecommendedTweetsQuery, _, _, _]
  ] = Seq(
    earlybirdInNetworkCandidatePipelineConfigFactory.build(identifierPrefix),
    haploliteCandidatePipelineConfigFactory.build(identifierPrefix),
    utegCandidatePipelineConfigFactory.build(identifierPrefix),
    contentAnnTweetBasedCandidatePipelineConfigFactory
      .build(identifierPrefix, signalsFn = tweetBasedSignalFn),
    simclustersTweetBasedCandidatePipelineConfigFactory.build(
      identifierPrefix,
      signalsFn = tweetBasedSignalFn,
      Seq(defaultSuccessRateAlert(), defaultEmptyResponseRateAlert())
    ),
    simclustersProducerBasedCandidatePipelineConfigFactory.build(
      identifierPrefix,
      producerBasedSignalFn,
      Seq(defaultSuccessRateAlert(), defaultEmptyResponseRateAlert())
    ),
    uvgTweetBasedCandidatePipelineConfigFactory
      .build(identifierPrefix, signalsFn = tweetBasedSignalFn),
    uvgExpansionTweetBasedCandidatePipelineConfigFactory
      .build(identifierPrefix, signalFn = highQualityTweetBasedSignalFn),
    utgTweetBasedCandidatePipelineConfigFactory
      .build(identifierPrefix, signalsFn = tweetBasedSignalFn),
    utgExpansionTweetBasedCandidatePipelineConfigFactory
      .build(identifierPrefix, signalsFn = tweetBasedSignalFn),
    twHINRebuildTweetSimilarityCandidatePipelineConfigFactory
      .build(identifierPrefix, tweetBasedSignalFn),
    twHINTweetSimilarityCandidatePipelineConfigFactory
      .build(identifierPrefix, tweetBasedSignalFn),
    simclustersInterestedInCandidatePipelineConfigFactory
      .build(identifierPrefix),
    deepRetrievalUserTweetSimilarityCandidatePipelineConfig,
    deepRetrievalTweetTweetSimilarityCandidatePipelineConfigFactory
      .build(identifierPrefix, deepRetrievalTweetBasedSignalFn),
    deepRetrievalTweetTweetEmbeddingSimilarityCandidatePipelineConfigFactory
      .build(identifierPrefix, tweetBasedSignalFn),
    twhinUserTweetSimilarityCandidatePipelineConfig,
    contentExplorationEmbeddingSimilarityTierTwoCandidatePipelineConfigFactory
      .build(identifierPrefix, contentExplorationTweetBasedSignalFn),
    contentExplorationEmbeddingSimilarityCandidatePipelineConfigFactory
      .build(identifierPrefix, contentExplorationTweetBasedSignalFn),
    contentExplorationDRTweetTweetTierTwoCandidatePipelineConfigFactory
      .build(identifierPrefix, contentExplorationTweetBasedSignalFn),
    contentExplorationDRTweetTweetCandidatePipelineConfigFactory
      .build(identifierPrefix, contentExplorationTweetBasedSignalFn),
    contentExplorationEvergreenDRTweetTweetCandidatePipelineConfigFactory
      .build(identifierPrefix, contentExplorationTweetBasedSignalFn),
    contentExplorationDRUserTweetTierTwoCandidatePipelineConfig,
    contentExplorationSimclusterColdCandidatePipelineConfig,
    contentExplorationDRUserTweetCandidatePipelineConfig,
    evergreenDRUserTweetCandidatePipelineConfig,
    evergreenDRCrossBorderUserTweetCandidatePipelineConfig,
    userInterestSummaryCandidatePipelineConfigFactory.build(identifierPrefix),
    popularGeoTweetsCandidatePipelineConfigFactory.build(identifierPrefix),
    popGrokTopicTweetsCandidatePipelineConfigFactory.build(identifierPrefix),
    popularTopicTweetsCandidatePipelineConfigFactory.build(identifierPrefix),
    evergreenVideosCandidatePipelineConfigFactory.build(identifierPrefix),
    qigSearchHistoryTweetsCandidatePipelineConfigFactory
      .build(identifierPrefix, searcherRealtimeHistorySignalFn),
    curatedUserTlsPerLanguageCandidatePipelineConfigFactory.build(identifierPrefix)
  )

  override def candidatePipelineFailOpenPolicies: Map[CandidatePipelineIdentifier, FailOpenPolicy] =
    candidatePipelines.map { candidatePipeline =>
      (candidatePipeline.identifier, FailOpenPolicy.Always)
    }.toMap

  override def postCandidatePipelinesSelectors: Seq[Selector[HomeRecommendedTweetsQuery]] = Seq(
    InsertAppendResults(AllPipelines),
    DropDuplicateResults(duplicationKey = IdDuplicationKey),
  )

  override def postCandidatePipelinesFeatureHydration: Seq[
    BaseCandidateFeatureHydrator[HomeRecommendedTweetsQuery, TweetCandidate, _]
  ] = tweetMixerFunctionalComponents.globalCandidateFeatureHydrators

  override def globalFilters: Seq[Filter[HomeRecommendedTweetsQuery, TweetCandidate]] =
    tweetMixerFunctionalComponents.globalFilters() ++ Seq(
      ImpressedTweetsBloomFilter,
      IsVideoTweetFilter(Some(Set(uvgPipelineId, uvgExpansionPipelineId))),
      MinScoreFilter(candidatePipelinesToInclude = Some(deepRetrievalTweetTweetPipelineIds)),
      MaxViewCountFilter(candidatePipelinesToInclude = Some(contentExplorationPipelineIds)),
      GrokFilter
    )

  override def scoringPipelines: Seq[
    ScoringPipelineConfig[HomeRecommendedTweetsQuery, TweetCandidate]
  ] = Seq.empty

  // Weave candidates together from non-personalized sources
  private val lowSignalUserInsertSelectors = SelectConditionally(
    Seq(
      DropDuplicateCandidates(duplicationKey = _.features.getOrElse(AuthorIdFeature, None)),
      InsertAppendRatioResults(
        candidatePipelines.map(_.identifier).toSet,
        bucketer = Bucketer.ByCandidateSource,
        ratios = Map(
          popGrokTopicTweetsCandidatePipelineIdentifier -> LowSignalUserGrokTopicRatioParam,
          simclustersInterestedInCandidatePipelineIdentifier -> LowSignalUserSimclustersRatioParam,
          popularGeoCandidatePipelineIdentifier -> LowSignalUserPopGeoRatioParam
        ) ++ candidatePipelines
          .filterNot { candidatePipeline =>
            candidatePipeline.identifier == popGrokTopicTweetsCandidatePipelineIdentifier ||
            candidatePipeline.identifier == simclustersInterestedInCandidatePipelineIdentifier ||
            candidatePipeline.identifier == popularGeoCandidatePipelineIdentifier
          }
          .map(_.identifier -> LowSignalUserBackfillRatioParam).toMap
      ),
      DropAllCandidates() // Drop all remaining candidates to prevent other selectors from inserting them
    ),
    (query: HomeRecommendedTweetsQuery, _, _) => AllowLowSignalUserGate.evaluate(query)
  )

  private val userToPostPipelines = Set(
    deepRetrievalUserTweetSimilarityCandidatePipelineConfig.identifier,
    contentExplorationDRUserTweetCandidatePipelineConfig.identifier,
    contentExplorationDRUserTweetTierTwoCandidatePipelineConfig.identifier,
    contentExplorationSimclusterColdCandidatePipelineConfig.identifier,
    evergreenDRUserTweetCandidatePipelineConfig.identifier,
    evergreenDRCrossBorderUserTweetCandidatePipelineConfig.identifier,
    twhinUserTweetSimilarityCandidatePipelineConfig.identifier
  )

  override def resultSelectors: Seq[Selector[HomeRecommendedTweetsQuery]] =
    lowSignalUserInsertSelectors ++ Seq(
      // Only one of the next three selectors will be used depending on blending algorithm
      tweetMixerFunctionalComponents
        .exclusiveRoundRobinSelector(pipelinesToExclude = userToPostPipelines),
      tweetMixerFunctionalComponents
        .signalPrioritySelector(pipelinesToExclude = userToPostPipelines),
      tweetMixerFunctionalComponents.weightedSignalPrioritySelector(pipelinesToExclude =
        userToPostPipelines),
      DropMaxResults(maxResultsParam = LightRankerMaxResultsParam),
      tweetMixerFunctionalComponents
        .inclusiveRoundRobinSelector(pipelinesToInclude = userToPostPipelines),
      DropDuplicateResults(duplicationKey = IdDuplicationKey),
      DropRequestedMaxResults(
        defaultRequestedMaxResultsParam = DefaultRequestedMaxResultsParam,
        serverMaxResultsParam = ServerMaxResultsParam
      )
    )

  override def resultSideEffects: Seq[
    PipelineResultSideEffect[HomeRecommendedTweetsQuery, TweetMixerResponse]
  ] = Seq(
    hydraScoringSideEffect,
    selectedStatsSideEffect,
    evergreenVideosSideEffect,
    deepRetrievalAdHocSideEffect,
    scribeServedCandidatesSideEffectFactory.build(identifierPrefix),
    publishGroxUserInterestsSideEffect,
    requestMultimodalEmbeddingSideEffect
  )

  override def domainMarshaller: DomainMarshaller[
    HomeRecommendedTweetsQuery,
    TweetMixerResponse
  ] = homeRecommendedTweetsDomainResponseMarshaller

  override def transportMarshaller: TransportMarshaller[
    TweetMixerResponse,
    t.TweetMixerRecommendationResponse
  ] = tweetMixerResponseTransportMarshaller
}
