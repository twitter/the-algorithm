package com.twitter.home_mixer.product.scored_tweets.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.functional_component.feature_hydrator.UserLanguagesFeature
import com.twitter.home_mixer.model.HomeFeatures.DeviceLanguageFeature
import com.twitter.home_mixer.model.HomeFeatures.EarlybirdFeature
import com.twitter.home_mixer.model.HomeFeatures.EarlybirdSearchResultFeature
import com.twitter.home_mixer.model.HomeFeatures.IsRetweetFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetUrlsFeature
import com.twitter.home_mixer.model.HomeFeatures.UserScreenNameFeature
import com.twitter.home_mixer.param.HomeMixerInjectionNames.EarlybirdRepository
import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.adapters.earlybird.EarlybirdAdapter
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.home_mixer.util.ObservedKeyValueResultHandler
import com.twitter.home_mixer.util.earlybird.EarlybirdResponseUtil
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.RichDataRecord
import com.twitter.product_mixer.component_library.feature_hydrator.query.social_graph.SGSFollowedUsersFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.search.common.constants.{thriftscala => scc}
import com.twitter.search.earlybird.{thriftscala => eb}
import com.twitter.servo.keyvalue.KeyValueResult
import com.twitter.servo.repository.KeyValueRepository
import com.twitter.stitch.Stitch
import com.twitter.timelines.earlybird.common.utils.InNetworkEngagement
import com.twitter.util.Future
import com.twitter.util.Return
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import scala.collection.JavaConverters._

object EarlybirdDataRecordFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class EarlybirdFeatureHydrator @Inject() (
  @Named(EarlybirdRepository) client: KeyValueRepository[
    (Seq[Long], Long),
    Long,
    eb.ThriftSearchResult
  ],
  override val statsReceiver: StatsReceiver)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with ObservedKeyValueResultHandler {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("Earlybird")

  override val features: Set[Feature[_, _]] = Set(
    EarlybirdDataRecordFeature,
    EarlybirdFeature,
    EarlybirdSearchResultFeature,
    SourceTweetEarlybirdFeature,
    TweetUrlsFeature
  )

  override val statScope: String = identifier.toString

  private val scopedStatsReceiver = statsReceiver.scope(statScope)
  private val originalKeyFoundCounter = scopedStatsReceiver.counter("originalKey/found")
  private val originalKeyNotFoundCounter = scopedStatsReceiver.counter("originalKey/notFound")

  private val ebFeaturesNotExistPredicate: CandidateWithFeatures[TweetCandidate] => Boolean =
    candidate => candidate.features.getOrElse(EarlybirdFeature, None).isEmpty

  private val InternalUrlPattern = ""

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val candidatesWithoutExistingEBFeatures = candidates
      .filter { candidate =>
        val isEmpty = ebFeaturesNotExistPredicate(candidate)
        if (isEmpty) originalKeyNotFoundCounter.incr() else originalKeyFoundCounter.incr()
        isEmpty
      }
    val (candidatesWithSearchFeatures, candidatesWithoutSearchFeatures) =
      candidatesWithoutExistingEBFeatures.partition { candidate =>
        candidate.features.getOrElse(EarlybirdSearchResultFeature, None).nonEmpty
      }
    // hydrate both candidates and their sources
    val candidateIdsToHydrate = (
      candidatesWithSearchFeatures
        .filter(_.features.getOrElse(IsRetweetFeature, false))
        .map(CandidatesUtil.getOriginalTweetId) ++
        candidatesWithoutSearchFeatures.map(_.candidate.id) ++
        candidatesWithoutSearchFeatures.map(CandidatesUtil.getOriginalTweetId)
    ).distinct

    client((candidateIdsToHydrate, query.getRequiredUserId))
      .flatMap(
        handleResponse(query, candidates, _, candidateIdsToHydrate, candidatesWithSearchFeatures))
  }

  private[feature_hydrator] def getFeatureMap(
    candidate: CandidateWithFeatures[TweetCandidate],
    query: PipelineQuery,
    idToSearchResults: Map[Long, eb.ThriftSearchResult],
    screenName: Option[String],
    userLanguages: Seq[scc.ThriftLanguage],
    uiLanguage: Option[scc.ThriftLanguage],
    tweetCountByAuthorId: Map[Long, Int],
    followedUserIds: Set[Long],
    mutuallyFollowedUserIds: Set[Long],
    inNetworkEngagement: InNetworkEngagement,
  ): FeatureMap = {
    val candidateIsRetweet = candidate.features.getOrElse(IsRetweetFeature, false)
    val existingEbFeatures = candidate.features.getOrElse(EarlybirdFeature, None)
    val existingSourceTweetEbFeatures =
      candidate.features.getOrElse(SourceTweetEarlybirdFeature, None)

    val tweetEbFeatures =
      if (existingEbFeatures.nonEmpty) existingEbFeatures
      else {
        idToSearchResults.get(candidate.candidate.id).map { searchResult =>
          EarlybirdResponseUtil.getThriftTweetFeaturesFromSearchResult(
            searcherUserId = query.getRequiredUserId,
            screenName,
            userLanguages,
            uiLanguage,
            tweetCountByAuthorId,
            followedUserIds,
            mutuallyFollowedUserIds,
            idToSearchResults,
            inNetworkEngagement,
            searchResult
          )
        }
      }

    val sourceTweetEbFeatures = if (candidateIsRetweet) {
      if (existingSourceTweetEbFeatures.nonEmpty) existingSourceTweetEbFeatures
      else {
        idToSearchResults.get(CandidatesUtil.getOriginalTweetId(candidate)).map { searchResult =>
          EarlybirdResponseUtil.getThriftTweetFeaturesFromSearchResult(
            searcherUserId = query.getRequiredUserId,
            screenName,
            userLanguages,
            uiLanguage,
            tweetCountByAuthorId,
            followedUserIds,
            mutuallyFollowedUserIds,
            idToSearchResults,
            inNetworkEngagement,
            searchResult
          )
        }
      }
    } else None

    val originalTweetEbFeatures =
      if (sourceTweetEbFeatures.nonEmpty) sourceTweetEbFeatures else tweetEbFeatures
    val earlybirdDataRecord =
      EarlybirdAdapter.adaptToDataRecords(originalTweetEbFeatures).asScala.head

    val tesUrls = candidate.features.getOrElse(TweetUrlsFeature, Seq.empty)
    val urls =
      if (tesUrls.isEmpty) tweetEbFeatures.flatMap(_.urlsList).getOrElse(Seq.empty) else tesUrls

    val rdr = new RichDataRecord(earlybirdDataRecord)

    FeatureMapBuilder(sizeHint = 5)
      .add(EarlybirdFeature, tweetEbFeatures)
      .add(EarlybirdDataRecordFeature, rdr.getRecord)
      .add(EarlybirdSearchResultFeature, idToSearchResults.get(candidate.candidate.id))
      .add(SourceTweetEarlybirdFeature, sourceTweetEbFeatures)
      .add(TweetUrlsFeature, urls)
      .build()
  }

  private def handleResponse(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]],
    results: KeyValueResult[Long, eb.ThriftSearchResult],
    candidateIdsToHydrate: Seq[Long],
    candidatesWithSearchFeatures: Seq[CandidateWithFeatures[TweetCandidate]],
  ): Future[Seq[FeatureMap]] = {
    val batchSize = 64
    val queryFeatureMap = query.features.getOrElse(FeatureMap.empty)
    val userLanguages = queryFeatureMap.getOrElse(UserLanguagesFeature, Seq.empty)
    val uiLanguageCode = queryFeatureMap.getOrElse(DeviceLanguageFeature, None)
    val screenName = queryFeatureMap.getOrElse(UserScreenNameFeature, None)
    val followedUserIds = queryFeatureMap.getOrElse(SGSFollowedUsersFeature, Seq.empty).toSet
    val mutuallyFollowedUserIds =
      queryFeatureMap.getOrElse(SGSMutuallyFollowedUsersFeature, Seq.empty).toSet

    val searchResults = candidateIdsToHydrate
      .map { id =>
        observedGet(Some(id), results)
      }.collect {
        case Return(Some(value)) => value
      }

    val allSearchResults = searchResults ++ candidatesWithSearchFeatures.flatMap { candidate =>
      candidate.features.getOrElse(EarlybirdSearchResultFeature, None)
    }

    val idToSearchResults = allSearchResults.map { searchResults =>
      searchResults.id -> searchResults
    }.toMap

    val uiLanguage = EarlybirdResponseUtil.getLanguage(uiLanguageCode)
    val tweetCountByAuthorId = EarlybirdResponseUtil.getTweetCountByAuthorId(allSearchResults)
    val inNetworkEngagement =
      InNetworkEngagement(followedUserIds.toSeq, mutuallyFollowedUserIds, allSearchResults)

    OffloadFuturePools
      .offloadBatchElementToElement[CandidateWithFeatures[TweetCandidate], FeatureMap](
        candidates,
        candidate =>
          getFeatureMap(
            candidate = candidate,
            query = query,
            idToSearchResults = idToSearchResults,
            screenName = screenName,
            userLanguages = userLanguages,
            uiLanguage = uiLanguage,
            tweetCountByAuthorId = tweetCountByAuthorId,
            followedUserIds = followedUserIds,
            mutuallyFollowedUserIds = mutuallyFollowedUserIds,
            inNetworkEngagement = inNetworkEngagement
          ),
        batchSize
      )
  }
}
