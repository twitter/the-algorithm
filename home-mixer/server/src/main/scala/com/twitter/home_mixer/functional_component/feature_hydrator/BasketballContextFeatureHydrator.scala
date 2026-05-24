package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.BasketballContextFeature
import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.home_mixer.param.HomeGlobalParams.BasketballTeamAccountIdsParam
import com.twitter.home_mixer.{thriftscala => hmt}
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
import com.twitter.strato.client.Fetcher
import com.twitter.timelines.render.{thriftscala => urt}
import com.twitter.strato.catalog.Fetch
import com.twitter.strato.generated.client.events.experiences.basketball.PostBasketballContextClientColumn

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BasketballContextFeatureHydrator @Inject() (
  postBasketballContextClientColumn: PostBasketballContextClientColumn
) extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("BasketballContext")

  override val features: Set[Feature[_, _]] = Set(BasketballContextFeature)

  private val fetcher: Fetcher[Long, Unit, urt.BasketballContext] = postBasketballContextClientColumn.fetcher

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadStitch {
    val basketballAuthorIds = query.params(BasketballTeamAccountIdsParam)

    Stitch.collect {
      candidates.map { candidate =>
        val servedType = candidate.features.getOrElse(ServedTypeFeature, hmt.ServedType.Undefined)
        val isPromoted = (servedType == hmt.ServedType.ForYouPromoted || servedType == hmt.ServedType.FollowingPromoted)

        val authorId = candidate.features.getOrElse(AuthorIdFeature, None)
        val isBasketballAuthor = authorId.exists(id => basketballAuthorIds.contains(id))

        // Skip hydration if the post is an ad or not from a basketball account
        if (isPromoted || !isBasketballAuthor) {
          Stitch.value(FeatureMapBuilder().add(BasketballContextFeature, None).build())
        } else {
          fetcher.fetch(candidate.candidate.id, Unit).map {
            case Fetch.Result(Some(basketballContext), _) =>
              FeatureMapBuilder().add(BasketballContextFeature, Some(basketballContext)).build()
            case _ =>
              FeatureMapBuilder().add(BasketballContextFeature, None).build()
          }
        }
      }
    }
  }
} 
