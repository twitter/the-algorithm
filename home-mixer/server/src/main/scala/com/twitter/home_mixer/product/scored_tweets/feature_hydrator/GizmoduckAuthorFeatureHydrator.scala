package com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator

import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.ads.entities.db.{thriftscala => ae}
import com.ExTwitter.gizmoduck.{thriftscala => gt}
import com.ExTwitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.AuthorIsBlueVerifiedFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.AuthorIsProtectedFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.FromInNetworkSourceFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.InReplyToTweetIdFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.IsRetweetFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.IsSupportAccountReplyFeature
import com.ExTwitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.ExTwitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.ExTwitter.product_mixer.core.model.common.CandidateWithFeatures
import com.ExTwitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.product_mixer.core.util.OffloadFuturePools
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.snowflake.id.SnowflakeId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GizmoduckAuthorFeatureHydrator @Inject() (gizmoduck: gt.UserService.MethodPerEndpoint)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("GizmoduckAuthor")

  override val features: Set[Feature[_, _]] =
    Set(AuthorIsBlueVerifiedFeature, AuthorIsProtectedFeature, IsSupportAccountReplyFeature)

  private val queryFields: Set[gt.QueryFields] =
    Set(gt.QueryFields.AdvertiserAccount, gt.QueryFields.Profile, gt.QueryFields.Safety)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val authorIds = candidates.flatMap(_.features.getOrElse(AuthorIdFeature, None))

    val response = gizmoduck.get(
      userIds = authorIds.distinct,
      queryFields = queryFields,
      context = gt.LookupContext()
    )

    response.map { hydratedAuthors =>
      val userMetadataMap = hydratedAuthors
        .collect {
          case userResult if userResult.user.isDefined =>
            val user = userResult.user.get
            val blueVerified = user.safety.flatMap(_.isBlueVerified).getOrElse(false)
            val isProtected = user.safety.exists(_.isProtected)
            (user.id, (blueVerified, isProtected))
        }.toMap.withDefaultValue((false, false))

      candidates.map { candidate =>
        val authorId = candidate.features.get(AuthorIdFeature).get
        val (isBlueVerified, isProtected) = userMetadataMap(authorId)

        // Some accounts run promotions on ExTwitter and send replies automatically.
        // We assume that a reply that took more than one minute is not an auto-reply.
        // If time difference doesn't exist, this means that one of the tweets was
        // not snowflake and therefore much older, and therefore OK as an extended reply.
        val timeDifference = candidate.features.getOrElse(InReplyToTweetIdFeature, None).map {
          SnowflakeId.timeFromId(candidate.candidate.id) - SnowflakeId.timeFromId(_)
        }
        val isAutoReply = timeDifference.exists(_ < 1.minute)

        FeatureMapBuilder()
          .add(AuthorIsBlueVerifiedFeature, isBlueVerified)
          .add(AuthorIsProtectedFeature, isProtected)
          .add(IsSupportAccountReplyFeature, isAutoReply)
          .build()
      }
    }
  }
}
