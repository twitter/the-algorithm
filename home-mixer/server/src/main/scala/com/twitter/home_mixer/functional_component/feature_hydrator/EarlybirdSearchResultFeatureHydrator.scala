package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.EarlybirdScoreFeature
import com.twitter.home_mixer.model.HomeFeatures.EarlybirdSearchResultFeature
import com.twitter.home_mixer.param.HomeMixerInjectionNames.EarlybirdRepository
import com.twitter.home_mixer.util.ObservedKeyValueResultHandler
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.search.earlybird.{thriftscala => eb}
import com.twitter.servo.keyvalue.KeyValueResult
import com.twitter.servo.repository.KeyValueRepository
import com.twitter.stitch.Stitch
import com.twitter.util.Return
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class EarlybirdSearchResultFeatureHydrator @Inject() (
  @Named(EarlybirdRepository) client: KeyValueRepository[
    (Seq[Long], Long),
    Long,
    eb.ThriftSearchResult
  ],
  override val statsReceiver: StatsReceiver)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with ObservedKeyValueResultHandler {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("EarlybirdSearchResult")

  override val features: Set[Feature[_, _]] = Set(
    EarlybirdScoreFeature,
    EarlybirdSearchResultFeature
  )

  override val statScope: String = identifier.toString

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    client((candidates.map(_.candidate.id), query.getRequiredUserId))
      .map(handleResponse(candidates, _))
  }

  private def handleResponse(
    candidates: Seq[CandidateWithFeatures[TweetCandidate]],
    results: KeyValueResult[Long, eb.ThriftSearchResult]
  ): Seq[FeatureMap] = {
    candidates
      .map { candidate =>
        observedGet(Some(candidate.candidate.id), results)
      }.map {
        case Return(Some(searchResult)) =>
          FeatureMapBuilder()
            .add(EarlybirdScoreFeature, searchResult.metadata.flatMap(_.score))
            .add(EarlybirdSearchResultFeature, Some(searchResult))
            .build()
        case other =>
          FeatureMapBuilder()
            .add(EarlybirdScoreFeature, None)
            .add(EarlybirdSearchResultFeature, other)
            .build()
      }
  }
}
