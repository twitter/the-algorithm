package com.twitter.home_mixer.product.scored_tweets.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures.ListIdFeature
import com.twitter.home_mixer.model.HomeFeatures.ListNameFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.socialgraph.{thriftscala => sg}
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Fetcher
import com.twitter.strato.generated.client.lists.reads.CoreOnListClientColumn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListNameFeatureHydrator @Inject() (coreOnListClientColumn: CoreOnListClientColumn)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("ListName")

  override val features: Set[Feature[_, _]] = Set(ListNameFeature)

  private val fetcher: Fetcher[Long, Unit, sg.SocialgraphList] = coreOnListClientColumn.fetcher

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = {
    val listIds = candidates.flatMap(_.features.getOrElse(ListIdFeature, None)).distinct

    val listIdNameMapStitch = Stitch.collect {
      listIds.map { listId => listId -> fetcher.fetch(listId).map(_.v.map(_.name)) }.toMap
    }

    listIdNameMapStitch.map { listIdNameMap =>
      candidates.map { candidate =>
        val listId = candidate.features.getOrElse(ListIdFeature, None)
        val listName = listId.flatMap(listIdNameMap.get).flatten
        FeatureMapBuilder().add(ListNameFeature, listName).build()
      }
    }
  }
}
