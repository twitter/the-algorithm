package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.home_mixer.model.HomeFeatures.GenericPostContextFeature
import com.twitter.home_mixer.model.HomeFeatures.InReplyToTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.IsRetweetFeature
import com.twitter.home_mixer.param.HomeGlobalParams.MaxPostContextDuplicatesPerRequest
import com.twitter.home_mixer.param.HomeGlobalParams.MaxPostContextPostsPerRequest
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
import com.twitter.strato.generated.client.events.urt.PostContextClientColumn
import javax.inject.Inject
import javax.inject.Singleton
import scala.util.Random

@Singleton
class PostContextFeatureHydrator @Inject() (
  postContextClientColumn: PostContextClientColumn)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("PostContext")

  override val features: Set[Feature[_, _]] = Set(GenericPostContextFeature)

  private val fetcher: Fetcher[Long, Unit, urt.GenericContext] = postContextClientColumn.fetcher

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadStitch {
    val maxPosts = query.params(MaxPostContextPostsPerRequest)
    val maxDuplicates = query.params(MaxPostContextDuplicatesPerRequest)

    val contextsStitch: Stitch[Seq[Option[urt.GenericContext]]] = Stitch.collect {
      candidates.map { candidate =>
        val servedType = candidate.features.getOrElse(ServedTypeFeature, hmt.ServedType.Undefined)
        val isPromoted = {
          servedType == hmt.ServedType.ForYouPromoted || servedType == hmt.ServedType.FollowingPromoted
        }

        val isOriginal = (!candidate.features.getOrElse(IsRetweetFeature, false)) &&
          candidate.features.getOrElse(InReplyToTweetIdFeature, None).isEmpty

        if (isPromoted || !isOriginal) {
          // Promoted tweets are not eligible for Post Context.
          Stitch.value(None)
        } else {
          fetcher.fetch(candidate.candidate.id, ()).map {
            case Fetch.Result(Some(postContext), _) => Some(postContext)
            case _ => None
          }
        }
      }
    }

    contextsStitch.map { rawContexts =>
      val urlCount = scala.collection.mutable.Map.empty[String, Int]
      val afterDupFilter: Vector[Option[urt.GenericContext]] =
        rawContexts.map {
          case Some(ctx) =>
            val url = ctx.url.url
            val seen = urlCount.getOrElse(url, 0)
            if (seen < maxDuplicates) {
              urlCount.update(url, seen + 1)
              Some(ctx)
            } else None // drop: duplicate overflow
          case None => None
        }(collection.breakOut)

      val keptIndices: Vector[Int] =
        afterDupFilter.iterator.zipWithIndex.collect { case (Some(_), idx) => idx }.toVector

      val indicesToDrop: Set[Int] =
        if (keptIndices.size <= maxPosts) Set.empty
        else {
          val numToDrop = keptIndices.size - maxPosts
          Random.shuffle(keptIndices).take(numToDrop).toSet
        }

      afterDupFilter.zipWithIndex.map {
        case (Some(ctx), idx) if !indicesToDrop.contains(idx) =>
          FeatureMapBuilder().add(GenericPostContextFeature, Some(ctx)).build()

        case _ =>
          FeatureMapBuilder().add(GenericPostContextFeature, None).build()
      }
    }
  }
}
