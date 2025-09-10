package com.twitter.tweet_mixer.functional_component

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.param_gated.ParamGatedBulkCandidateFeatureHydrator
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.component_library.selector._
import com.twitter.product_mixer.core.functional_component.common.AllExceptPipelines
import com.twitter.product_mixer.core.functional_component.common.AllPipelines
import com.twitter.product_mixer.core.functional_component.common.SpecificPipelines
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.selector.Selector
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.ComponentIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.marshalling.request.HasExcludedIds
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.spam.rtf.thriftscala.SafetyLevel
import com.twitter.stitch.cache.AsyncValueCache
import com.twitter.stitch.tweetypie.{TweetyPie => TweetypieStitchClient}
import com.twitter.strato.client.Client
import com.twitter.strato.generated.client.content_understanding.ColdStartPostsMetadataMhClientColumn
import com.twitter.strato.generated.client.unified_counter.service.UecAggTotalOnTweetClientColumn
import com.twitter.strato.generated.client.videoRecommendations.twitterClip.TwitterClipClusterIdMhClientColumn
import com.twitter.timelines.configapi.FSEnumParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.tweet_mixer.feature.SourceSignalFeature
import com.twitter.tweet_mixer.feature.USSFeatures
import com.twitter.tweet_mixer.functional_component.filter.ImpressedTweetsFilter
import com.twitter.tweet_mixer.functional_component.filter.TweetVisibilityAndReplyFilter
import com.twitter.tweet_mixer.functional_component.hydrator._
import com.twitter.tweet_mixer.model.ModuleNames
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.BlendingEnum
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.BlendingParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableMediaMetadataCandidateFeatureHydrator
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.GrokFilterFeatureHydratorEnabled
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ViewCountInfoOnTweetFeatureHydratorEnabled
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import com.twitter.tweet_mixer.utils.MemcacheStitchClient
import com.twitter.usersignalservice.thriftscala.SignalType
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import scala.util.Random

@Singleton
class TweetMixerFunctionalComponents @Inject() (
  ussQueryFeatureHydrator: USSQueryFeatureHydrator,
  highQualitySourceSignalQueryFeatureHydrator: HighQualitySourceSignalQueryFeatureHydrator,
  requestPlaceIdsQueryFeatureHydrator: RequestPlaceIdsQueryFeatureHydrator,
  tweetypieStitchClient: TweetypieStitchClient,
  twitterClipClusterIdMhClientColumn: TwitterClipClusterIdMhClientColumn,
  coldStartPostsMetadataMhClientColumn: ColdStartPostsMetadataMhClientColumn,
  uecAggTotalOnTweetClientColumn: UecAggTotalOnTweetClientColumn,
  userTopicIdsFeatureHydrator: UserTopicIdsFeatureHydrator,
  memcache: MemcacheStitchClient,
  @Named(ModuleNames.TweeypieInMemCache)
  inMemoryCache: AsyncValueCache[java.lang.Long, Option[(Int, Long, Long)]],
  @Named(ModuleNames.MediaMetadataInMemCache)
  mediaMetadataInMemCache: AsyncValueCache[java.lang.Long, Option[Long]],
  @Named(ModuleNames.GrokFilterInMemCache)
  grokFilterInMemCache: AsyncValueCache[java.lang.Long, Boolean],
  statsReceiver: StatsReceiver,
  @Named("StratoClientWithModerateTimeout") stratoClient: Client) {

  private val identifierPrefix: String = "HomeRecommendedTweets"

  private val tweetypieCandidateFeatureHydrator = new TweetypieCandidateFeatureHydrator(
    tweetypieStitchClient = tweetypieStitchClient,
    safetyLevelPredicate = _ => SafetyLevel.Recommendations,
    memcache = memcache,
    inMemoryCache = inMemoryCache,
    statsReceiver = statsReceiver,
    stratoClient
  )

  private val mediaMetadataCandidateFeatureHydrator = new MediaMetadataCandidateFeatureHydrator(
    twitterClipClusterIdMhClientColumn = twitterClipClusterIdMhClientColumn,
    memcache = memcache,
    inMemoryCache = mediaMetadataInMemCache,
    statsReceiver = statsReceiver
  )

  private val uecAggTweetTotalFeatureHydrator = new UecAggTweetTotalFeatureHydrator(
    uecAggTotalOnTweetClientColumn = uecAggTotalOnTweetClientColumn,
    candidatePipelinesToInclude = Some(
      Set(
        CandidatePipelineIdentifier(
          identifierPrefix + CandidatePipelineConstants.ContentExplorationEmbeddingSimilarity),
        CandidatePipelineIdentifier(
          identifierPrefix + CandidatePipelineConstants.ContentExplorationEmbeddingSimilarityTierTwo),
        CandidatePipelineIdentifier(
          identifierPrefix + CandidatePipelineConstants.ContentExplorationDRTweetTweet),
        CandidatePipelineIdentifier(
          identifierPrefix + CandidatePipelineConstants.ContentExplorationDRTweetTweetTierTwo),
        CandidatePipelineIdentifier(
          identifierPrefix + CandidatePipelineConstants.ContentExplorationDRUserTweet),
        CandidatePipelineIdentifier(
          identifierPrefix + CandidatePipelineConstants.ContentExplorationDRUserTweetTierTwo)
      )
    ),
    statsReceiver = statsReceiver
  )

  private val grokFilterFeatureHydrator = new GrokFilterFeatureHydrator(
    coldStartPostsMetadataMhClientColumn = coldStartPostsMetadataMhClientColumn,
    statsReceiver = statsReceiver,
    memcache = memcache,
    inMemoryCache = grokFilterInMemCache
  )

  val globalQueryFeatureHydrators: Seq[QueryFeatureHydrator[PipelineQuery]] = Seq(
    ussQueryFeatureHydrator,
    highQualitySourceSignalQueryFeatureHydrator,
    requestPlaceIdsQueryFeatureHydrator,
    userTopicIdsFeatureHydrator
  )

  val globalCandidateFeatureHydrators: Seq[
    BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate]
  ] = Seq(
    tweetypieCandidateFeatureHydrator,
    SignalInfoCandidateFeatureHydrator,
    ParamGatedBulkCandidateFeatureHydrator(
      EnableMediaMetadataCandidateFeatureHydrator,
      mediaMetadataCandidateFeatureHydrator
    ),
    ParamGatedBulkCandidateFeatureHydrator(
      ViewCountInfoOnTweetFeatureHydratorEnabled,
      uecAggTweetTotalFeatureHydrator
    ),
    ParamGatedBulkCandidateFeatureHydrator(
      GrokFilterFeatureHydratorEnabled,
      grokFilterFeatureHydrator
    )
  )

  def globalFilters(
    candidatePipelinesToExclude: Set[CandidatePipelineIdentifier] = Set.empty
  ): Seq[Filter[PipelineQuery with HasExcludedIds, TweetCandidate]] = Seq(
    TweetVisibilityAndReplyFilter(candidatePipelinesToExclude),
    ImpressedTweetsFilter
  )

  private val roundRobinBucketer: Bucketer[(ComponentIdentifier, Long)] =
    (candidateWithDetails: CandidateWithDetails) =>
      (
        candidateWithDetails.source,
        candidateWithDetails.features.getOrElse(SourceSignalFeature, -1L),
      )

  private val roundRobinSourceSignalBucketer: Bucketer[Long] =
    (candidateWithDetails: CandidateWithDetails) =>
      candidateWithDetails.features.getOrElse(SourceSignalFeature, -1L)

  private val roundRobinSourceBucketer: Bucketer[ComponentIdentifier] =
    (candidateWithDetails: CandidateWithDetails) => candidateWithDetails.source

  private val signalPriorityBucketer: Bucketer[(ComponentIdentifier, Long, Int)] =
    (candidateWithDetails: CandidateWithDetails) =>
      (
        candidateWithDetails.source,
        candidateWithDetails.features.getOrElse(SourceSignalFeature, -1L),
        USSFeatures.getPriority(
          candidateWithDetails.features.getOrElse(SignalInfoFeature, Seq.empty)
        )
      )

  private val getPriorityFn: ((ComponentIdentifier, Long, Int)) => Int = bucket => bucket._3

  private val weightedPriorityBucketer: Bucketer[
    (ComponentIdentifier, Long, (Option[SignalType], Double))
  ] =
    (candidateWithDetails: CandidateWithDetails) =>
      (
        candidateWithDetails.source,
        candidateWithDetails.features.getOrElse(SourceSignalFeature, -1L),
        USSFeatures.getWeightedPriority(
          candidateWithDetails.features.getOrElse(SignalInfoFeature, Seq.empty)
        )
      )

  private val getWeightedPriorityFn: (
    (ComponentIdentifier, Long, (Option[SignalType], Double))
  ) => (Option[SignalType], Double) =
    bucket => bucket._3

  private def paramEnumGated[T <: Enumeration](
    selectors: Seq[Selector[PipelineQuery]],
    enumParam: FSEnumParam[T],
    matchingValue: T#Value
  ): Seq[Selector[PipelineQuery]] =
    SelectConditionally(selectors, (query, _, _) => query.params(enumParam) == matchingValue)

  private def paramEnumGated[T <: Enumeration](
    selector: Selector[PipelineQuery],
    enumParam: FSEnumParam[T],
    matchingValue: T#Value
  ): Selector[PipelineQuery] =
    SelectConditionally(selector, (query, _, _) => query.params(enumParam) == matchingValue)

  private def paramNotEnumGated[T <: Enumeration](
    selector: Selector[PipelineQuery],
    enumParam: FSEnumParam[T],
    matchingValue: T#Value
  ): Selector[PipelineQuery] =
    SelectConditionally(selector, (query, _, _) => query.params(enumParam) != matchingValue)

  val roundRobinSelector = paramEnumGated[BlendingEnum.type](
    InsertAppendWeaveResults(AllPipelines, roundRobinBucketer),
    BlendingParam,
    BlendingEnum.RoundRobinBlending
  )

  def roundRobinConditionSelectors(
    selectors: Seq[Selector[PipelineQuery]]
  ): Seq[Selector[PipelineQuery]] = {
    paramEnumGated[BlendingEnum.type](
      selectors,
      BlendingParam,
      BlendingEnum.RoundRobinBlending
    )
  }

  def exclusiveRoundRobinSelector(pipelinesToExclude: Set[CandidatePipelineIdentifier]) = {
    paramEnumGated[BlendingEnum.type](
      InsertAppendWeaveResults(
        AllExceptPipelines(pipelinesToExclude = pipelinesToExclude),
        roundRobinBucketer
      ),
      BlendingParam,
      BlendingEnum.RoundRobinBlending
    )
  }

  def inclusiveRoundRobinSelector(pipelinesToInclude: Set[CandidatePipelineIdentifier]) = {
    paramEnumGated[BlendingEnum.type](
      InsertAppendWeaveResults(
        SpecificPipelines(pipelines = pipelinesToInclude),
        roundRobinBucketer
      ),
      BlendingParam,
      BlendingEnum.RoundRobinBlending
    )
  }

  def exclusiveRoundRobinSelectorWithParam(
    pipelinesToExcludeMap: Map[CandidatePipelineIdentifier, FSParam[Boolean]]
  ): SelectConditionallyWithFn[PipelineQuery] = {
    val selectorFn: PipelineQuery => Selector[PipelineQuery] = query => {
      val pipelinesToExclude = pipelinesToExcludeMap.filter {
        case (_, enableParam) => query.params(enableParam)
      }.keySet
      InsertAppendWeaveResults(
        AllExceptPipelines(pipelinesToExclude = pipelinesToExclude),
        roundRobinBucketer)
    }
    SelectConditionallyWithFn(
      selectorFn,
      (
        query: PipelineQuery,
        _,
        _
      ) => query.params(BlendingParam) == BlendingEnum.RoundRobinBlending,
      AllPipelines
    )
  }

  def signalPrioritySelector(pipelinesToExclude: Set[CandidatePipelineIdentifier] = Set.empty) = {
    paramEnumGated[BlendingEnum.type](
      InsertAppendPriorityWeaveResults(
        AllExceptPipelines(pipelinesToExclude = pipelinesToExclude),
        signalPriorityBucketer,
        getPriorityFn),
      BlendingParam,
      BlendingEnum.SignalPriorityBlending
    )
  }

  def weightedSignalPrioritySelector(
    pipelinesToExclude: Set[CandidatePipelineIdentifier] = Set.empty
  ): Selector[PipelineQuery] = {
    paramEnumGated[BlendingEnum.type](
      InsertAppendWeightedSignalPriorityWeaveResults(
        AllExceptPipelines(pipelinesToExclude = pipelinesToExclude),
        weightedPriorityBucketer,
        getWeightedPriorityFn,
        random = new Random(0)
      ),
      BlendingParam,
      BlendingEnum.WeightedPriorityBlending
    )
  }

  val roundRobinSourceSignalSelector = paramEnumGated[BlendingEnum.type](
    InsertAppendWeaveResults(AllPipelines, roundRobinSourceSignalBucketer),
    BlendingParam,
    BlendingEnum.RoundRobinSourceSignalBlending
  )

  val roundRobinSourceSelector = paramEnumGated[BlendingEnum.type](
    InsertAppendWeaveResults(AllPipelines, roundRobinSourceBucketer),
    BlendingParam,
    BlendingEnum.RoundRobinSourceBlending
  )

  val rankingOnlySelector = paramEnumGated[BlendingEnum.type](
    InsertAppendResults(AllPipelines),
    BlendingParam,
    BlendingEnum.RankingOnly
  )

  def exclusiveRankingOnlySelector(pipelinesToExclude: Set[CandidatePipelineIdentifier]) = {
    paramEnumGated[BlendingEnum.type](
      InsertAppendResults(AllExceptPipelines(pipelinesToExclude = pipelinesToExclude)),
      BlendingParam,
      BlendingEnum.RankingOnly
    )
  }

  val rankingOnlyDeduplicationSelector = paramEnumGated[BlendingEnum.type](
    DropDuplicateResults(duplicationKey = IdDuplicationKey),
    BlendingParam,
    BlendingEnum.RankingOnly
  )

  val rankingOnlyNotDeduplicationSelector = paramNotEnumGated[BlendingEnum.type](
    DropDuplicateResults(duplicationKey = IdDuplicationKey),
    BlendingParam,
    BlendingEnum.RankingOnly
  )
}
