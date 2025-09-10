package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.content_understanding.UserTopTagsMhClientColumn
import com.twitter.strato.generated.client.trends.trip.UserAssociatedTopicsClientColumn
import com.twitter.trends.trip_v1.user_topics.{thriftscala => ut}

import javax.inject.Inject
import javax.inject.Singleton

object RealtimeUserEngagedGrokTagFeature
    extends Feature[TweetCandidate, Seq[(String, Option[Double])]]
object EvergreenUserEngagedGrokTagFeature
    extends Feature[TweetCandidate, Seq[(String, Option[Double])]]

@Singleton
class UserEngagementGrokTagFeatureHydrator @Inject() (
  evergreenUserTopTags: UserTopTagsMhClientColumn,
  tripUserTopTags: UserAssociatedTopicsClientColumn)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(
    "UserEngagementGrokTag")

  override val features: Set[Feature[_, _]] =
    Set(RealtimeUserEngagedGrokTagFeature, EvergreenUserEngagedGrokTagFeature)

  def fetchRealTimeUserTags(userId: Long): Stitch[Seq[(String, Option[Double])]] = {
    tripUserTopTags.fetcher.fetch(ut.UserTopicDomain(userId = userId, sourceId = None)).map { result =>
      result.v.flatMap(_.tags).getOrElse(Seq.empty[ut.TagCandidate]).map(tc => (tc.tag, tc.score))
    }
  }

  def fetchEvergreenUserTags(userId: Long): Stitch[Seq[(String, Option[Double])]] = {
    evergreenUserTopTags.fetcher.fetch(userId).map { view =>
      view.v.map(_.tags.map(tc => (tc.tag, Some(tc.score)))).getOrElse(Seq.empty)
    }
  }

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val userId = query.getRequiredUserId
    Stitch.join(fetchRealTimeUserTags(userId), fetchEvergreenUserTags(userId)).map {
      case (realtime, evergreen) =>
        FeatureMapBuilder()
          .add(RealtimeUserEngagedGrokTagFeature, realtime)
          .add(EvergreenUserEngagedGrokTagFeature, evergreen)
          .build()
    }
  }
}
