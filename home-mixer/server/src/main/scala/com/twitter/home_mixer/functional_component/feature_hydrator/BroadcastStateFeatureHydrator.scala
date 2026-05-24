package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures.TweetUrlsFeature
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
import com.twitter.strato.generated.client.periscope.CoreOnBroadcastClientColumn
import com.twitter.ubs.{thriftscala => ubs}
import javax.inject.Inject
import javax.inject.Singleton

object BroadcastStateFeature extends Feature[TweetCandidate, Option[ubs.BroadcastState]]

@Singleton
class BroadcastStateFeatureHydrator @Inject() (
  coreOnBroadcastClientColumn: CoreOnBroadcastClientColumn)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with WithDefaultFeatureMap {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("BroadcastState")

  override val features: Set[Feature[_, _]] = Set(BroadcastStateFeature)

  private val pattern = "".r

  private val fetcher: Fetcher[String, Unit, ubs.Broadcast] = coreOnBroadcastClientColumn.fetcher

  override val defaultFeatureMap: FeatureMap = FeatureMap(BroadcastStateFeature, None)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadStitch {
    val broadcastIdMap = candidates.flatMap { candidate =>
      candidate.features
        .getOrElse(TweetUrlsFeature, Seq.empty)
        .collectFirst { case pattern(broadcastId) => broadcastId }
        .map(broadcastId => candidate.candidate.id -> broadcastId)
    }.toMap

    val responses = Stitch.collect {
      broadcastIdMap.values.toSeq.distinct.map { broadcastId =>
        fetcher.fetch(broadcastId).map {
          case Fetch.Result(Some(broadcast), _) if broadcast.broadcastId.nonEmpty =>
            Some(broadcastId -> broadcast)
          case _ => None
        }
      }
    }

    responses.map { results =>
      val broadcastMap = results.flatten.toMap
      candidates.map { candidate =>
        val broadcastState = broadcastIdMap.get(candidate.candidate.id).flatMap { broadcastId =>
          broadcastMap.get(broadcastId).flatMap(_.state)
        }
        FeatureMapBuilder().add(BroadcastStateFeature, broadcastState).build()
      }
    }
  }
}
