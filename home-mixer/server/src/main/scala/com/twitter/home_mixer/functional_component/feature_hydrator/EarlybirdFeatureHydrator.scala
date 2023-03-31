package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.earlybird.EarlybirdAdapter
import com.twitter.home_mixer.model.HomeFeatures.DeviceLanguageFeature
import com.twitter.home_mixer.model.HomeFeatures.EarlybirdFeature
import com.twitter.home_mixer.model.HomeFeatures.IsRetweetFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetUrlsFeature
import com.twitter.home_mixer.model.HomeFeatures.UserScreenNameFeature
import com.twitter.home_mixer.param.HomeMixerInjectionNames.EarlybirdRepository
import com.twitter.home_mixer.util.ObservedKeyValueResultHandler
import com.twitter.home_mixer.util.earlybird.EarlybirdResponseUtil
import com.twitter.ml.api.DataRecord
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
import com.twitter.search.earlybird.{thriftscala => eb}
import com.twitter.servo.keyvalue.KeyValueResult
import com.twitter.servo.repository.KeyValueRepository
import com.twitter.stitch.Stitch
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

  override val features: Set[Feature[_, _]] =
    Set(EarlybirdDataRecordFeature, EarlybirdFeature, TweetUrlsFeature)

  override val statScope: String = identifier.toString

  private val scopedStatsReceiver = statsReceiver.scope(statScope)
  private val originalKeyFoundCounter = scopedStatsReceiver.counter("originalKey/found")
  private val originalKeyLossCounter = scopedStatsReceiver.counter("originalKey/loss")

  private val ebFeaturesNotExistPredicate: CandidateWithFeatures[TweetCandidate] => Boolean =
    candidate => candidate.features.getOrElse(EarlybirdFeature, None).isEmpty

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = {
    val candidatesToHydrate = candidates.filter { candidate =>
      val isEmpty = ebFeaturesNotExistPredicate(candidate)
      if (isEmpty) originalKeyLossCounter.incr() else originalKeyFoundCounter.incr()
      isEmpty
    }
    Stitch
      .callFuture(client((candidatesToHydrate.map(_.candidate.id), query.getRequiredUserId)))
      .map(handleResponse(query, candidates, _))
  }

  private def handleResponse(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]],
    results: KeyValueResult[Long, eb.ThriftSearchResult]
  ): Seq[FeatureMap] = {
    val queryFeatureMap = query.features.getOrElse(FeatureMap.empty)
    val userLanguages = queryFeatureMap.getOrElse(UserLanguagesFeature, Seq.empty)
    val uiLanguageCode = queryFeatureMap.getOrElse(DeviceLanguageFeature, None)
    val screenName = queryFeatureMap.getOrElse(UserScreenNameFeature, None)

    val searchResults = candidates
      .filter(ebFeaturesNotExistPredicate).map { candidate =>
        observedGet(Some(candidate.candidate.id), results)
      }.collect {
        case Return(Some(value)) => value
      }

    val tweetIdToEbFeatures = EarlybirdResponseUtil.getOONTweetThriftFeaturesByTweetId(
      searcherUserId = query.getRequiredUserId,
      screenName = screenName,
      userLanguages = userLanguages,
      uiLanguageCode = uiLanguageCode,
      searchResults = searchResults
    )

    candidates.map { candidate =>
      val hydratedEbFeatures = tweetIdToEbFeatures.get(candidate.candidate.id)
      val earlybirdFeatures =
        if (hydratedEbFeatures.nonEmpty) hydratedEbFeatures
        else candidate.features.getOrElse(EarlybirdFeature, None)

      val candidateIsRetweet = candidate.features.getOrElse(IsRetweetFeature, false)
      val sourceTweetEbFeatures =
        candidate.features.getOrElse(SourceTweetEarlybirdFeature, None)

      val originalTweetEbFeatures =
        if (candidateIsRetweet && sourceTweetEbFeatures.nonEmpty)
          sourceTweetEbFeatures
        else earlybirdFeatures

      val earlybirdDataRecord =
        EarlybirdAdapter.adaptToDataRecords(originalTweetEbFeatures).asScala.head

      FeatureMapBuilder()
        .add(EarlybirdFeature, earlybirdFeatures)
        .add(EarlybirdDataRecordFeature, earlybirdDataRecord)
        .add(TweetUrlsFeature, earlybirdFeatures.flatMap(_.urlsList).getOrElse(Seq.empty))
        .build()
    }
  }
}
