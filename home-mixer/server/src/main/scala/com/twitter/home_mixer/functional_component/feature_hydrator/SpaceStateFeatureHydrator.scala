package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures.TweetUrlsFeature
import com.twitter.periscope.api.{thriftscala => ps}
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import com.twitter.strato.catalog.Fetch
import com.twitter.strato.client.Fetcher
import com.twitter.strato.generated.client.periscope.CoreOnAudioSpaceClientColumn
import com.twitter.ubs.{thriftscala => ubs}
import javax.inject.Inject
import javax.inject.Singleton

object SpaceStateFeature extends Feature[TweetCandidate, Option[ubs.BroadcastState]]

@Singleton
class SpaceStateFeatureHydrator @Inject() (
  coreOnAudioSpaceClientColumn: CoreOnAudioSpaceClientColumn)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with WithDefaultFeatureMap {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("SpaceState")

  override val features: Set[Feature[_, _]] = Set(SpaceStateFeature)

  private val pattern = """https?://(?:x|twitter).com/i/spaces/(\w+).*""".r

  private val fetcher: Fetcher[String, ps.AudioSpacesLookupContext, ubs.AudioSpace] =
    coreOnAudioSpaceClientColumn.fetcher

  private val lookupContext = ps.AudioSpacesLookupContext(participantHydrationLevel =
    Some(ps.ParticipantHydrationLevel.NoParticipantInfo))

  override val defaultFeatureMap: FeatureMap = FeatureMap(SpaceStateFeature, None)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] =
    OffloadFuturePools.offloadStitch {
      val spaceIdMap = candidates.flatMap { candidate =>
        candidate.features
          .getOrElse(TweetUrlsFeature, Seq.empty)
          .collectFirst { case pattern(spaceId) => spaceId }
          .map(spaceId => candidate.candidate.id -> spaceId)
      }.toMap

      Stitch
        .collect {
          spaceIdMap.values.toSeq.distinct.map { spaceId =>
            fetcher
              .fetch(spaceId, lookupContext)
              .map {
                case Fetch.Result(Some(audioSpace), _) if audioSpace.broadcastId.nonEmpty =>
                  Some(spaceId -> audioSpace)
                case _ => None
              }
          }
        }.map { results =>
          val audioSpaceMap = results.flatten.toMap
          candidates.map { candidate =>
            val spaceState = spaceIdMap.get(candidate.candidate.id).flatMap { spaceId =>
              audioSpaceMap.get(spaceId).flatMap(_.state)
            }

            FeatureMapBuilder().add(SpaceStateFeature, spaceState).build()
          }
        }
    }
}
