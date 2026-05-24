package com.twitter.home_mixer.functional_component.feature_hydrator

import com.github.nscala_time.time.Imports.LocalDate
import com.twitter.ads.entities.db.{thriftscala => ae}
import com.twitter.conversions.DurationOps._
import com.twitter.gizmoduck.{thriftscala => gt}
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.gizmoduck_features.GFeatures
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.gizmoduck_features.GizmoduckFeaturesAdapter
import com.twitter.home_mixer.model.HomeFeatures.AuthorAccountAge
import com.twitter.home_mixer.model.HomeFeatures.AuthorFollowersFeature
import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.AuthorIsBlueVerifiedFeature
import com.twitter.home_mixer.model.HomeFeatures.AuthorIsProtectedFeature
import com.twitter.home_mixer.model.HomeFeatures.FromInNetworkSourceFeature
import com.twitter.home_mixer.model.HomeFeatures.InReplyToTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.IsRetweetFeature
import com.twitter.home_mixer.model.HomeFeatures.IsSupportAccountReplyFeature
import com.twitter.home_mixer.model.HomeFeatures.AuthorSafetyLabels
import com.twitter.home_mixer.module.SupportAccountsConfig
import com.twitter.home_mixer.param.HomeMixerInjectionNames.GizmoduckTimelinesCache
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
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.servo.cache.TtlCache
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.stitch.Stitch
import com.twitter.util.Duration
import com.twitter.util.Future
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import scala.collection.JavaConverters._

private case class UserFeatures(
  isBlueVerified: Boolean,
  isVerifiedOrganization: Boolean,
  isVerifiedOrganizationAffiliate: Boolean,
  isProtected: Boolean,
  isSupportAccount: Boolean,
  followersCount: Option[Long],
  accountAge: Option[Duration],
  labels: Option[Seq[String]])
    extends GFeatures

object GizmoduckAuthorFeatures
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class GizmoduckAuthorFeatureHydrator @Inject() (
  gizmoduck: gt.UserService.MethodPerEndpoint,
  @Named(GizmoduckTimelinesCache) cacheClient: TtlCache[Long, gt.User],
  supportAccounts: SupportAccountsConfig)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("GizmoduckAuthor")

  override val features: Set[Feature[_, _]] = Set(
    AuthorAccountAge,
    AuthorFollowersFeature,
    AuthorIsBlueVerifiedFeature,
    AuthorIsProtectedFeature,
    IsSupportAccountReplyFeature,
    GizmoduckAuthorFeatures,
    AuthorSafetyLabels
  )

  private val CacheTTL = 24.hours

  private val queryFields: Set[gt.QueryFields] = Set(
    gt.QueryFields.AdvertiserAccount,
    gt.QueryFields.Profile,
    gt.QueryFields.Safety,
    gt.QueryFields.Labels,
    gt.QueryFields.Counts
  )

  private val lookupContext = gt.LookupContext(isRequestSheddable = Some(true))

  // Advertiser service levels which are assumed to provide support via replies
  private val AdvertiserServiceLevels = Set[ae.ServiceLevel](
    ae.ServiceLevel.Dso,
    ae.ServiceLevel.Mms,
    ae.ServiceLevel.Reseller,
    ae.ServiceLevel.Smb
  )

  private val SupportAccounts = supportAccounts.accounts

  private val DefaultUserFeatures = UserFeatures(
    isBlueVerified = false,
    isVerifiedOrganization = false,
    isVerifiedOrganizationAffiliate = false,
    isProtected = false,
    isSupportAccount = false,
    followersCount = None,
    accountAge = None,
    labels = None
  )

  private def extractFeaturesFromUser(
    user: gt.User
  ): UserFeatures = {
    val isBlueVerified = user.safety.flatMap(_.isBlueVerified).getOrElse(false)
    val safetyLabels = user.labels.map(_.labels.map(_.labelValue.name))
    val verifiedOrganizationDetails = user.safety.flatMap(_.verifiedOrganizationDetails)
    val isVerifiedOrganization =
      verifiedOrganizationDetails.flatMap(_.isVerifiedOrganization).getOrElse(false)
    val isVerifiedOrganizationAffiliate =
      verifiedOrganizationDetails.flatMap(_.isVerifiedOrganizationAffiliate).getOrElse(false)

    val isProtected = user.safety.exists(_.isProtected)
    val isSupportAccount =
      user.profile.exists(_.businessProfileState == gt.BusinessProfileState.Enabled) ||
        (user.advertiserAccount.flatMap(_.advertiserType).nonEmpty &&
          user.advertiserAccount
            .flatMap(_.serviceLevels).getOrElse(Seq.empty)
            .exists(AdvertiserServiceLevels.contains))
    val followersCount = user.counts.map(_.followers)
    val accountAge = Duration.fromSeconds(
      (LocalDate.now().toDate.toInstant.getEpochSecond - (user.createdAtMsec / 1000)).toInt)

    UserFeatures(
      isBlueVerified,
      isVerifiedOrganization,
      isVerifiedOrganizationAffiliate,
      isProtected,
      isSupportAccount,
      followersCount,
      Some(accountAge),
      safetyLabels
    )
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val authorIds = candidates.flatMap(_.features.getOrElse(AuthorIdFeature, None)).distinct
    cacheClient
      .get(authorIds)
      .flatMap { cacheResponse =>
        val cacheHydratedUsersMap = cacheResponse.found
        val notFoundUsers = cacheResponse.notFound.toSeq.distinct

        val gizmoduckResponseFuture = if (notFoundUsers.nonEmpty) {
          gizmoduck.get(lookupContext, notFoundUsers, queryFields)
        } else Future.value(Seq.empty)

        gizmoduckResponseFuture.map { gizmoduckResponse =>
          val gizmoduckHydratedUsersMap = gizmoduckResponse.collect {
            case userResult if userResult.user.isDefined =>
              val user = userResult.user.get
              cacheClient.add(user.id, user, CacheTTL)
              user.id -> user
          }.toMap

          val hydratedUsersMap = (cacheHydratedUsersMap ++ gizmoduckHydratedUsersMap)
            .mapValues(extractFeaturesFromUser)

          candidates.map { candidate =>
            val authorIdOpt = candidate.features.getOrElse(AuthorIdFeature, None)
            val userFeatures =
              authorIdOpt.flatMap(hydratedUsersMap.get).getOrElse(DefaultUserFeatures)

            // Some accounts run promotions and send replies automatically.
            // We assume that a reply that took more than one minute is not an auto-reply.
            // If time difference doesn't exist, this means that one of the tweets was
            // not snowflake and therefore much older, and therefore OK as an extended reply.
            val timeDifference = candidate.features.getOrElse(InReplyToTweetIdFeature, None).map {
              SnowflakeId.timeFromId(candidate.candidate.id) - SnowflakeId.timeFromId(_)
            }
            val isAutoReply = timeDifference.exists(_ < 1.minute)

            val isSupportAccountReply =
              candidate.features.getOrElse(InReplyToTweetIdFeature, None).nonEmpty &&
                !candidate.features.getOrElse(IsRetweetFeature, false) &&
                candidate.features.getOrElse(FromInNetworkSourceFeature, false) &&
                (userFeatures.isSupportAccount ||
                  authorIdOpt.exists(SupportAccounts.contains) || isAutoReply)

            val gizmoduckFeaturesRichDataRecords =
              GizmoduckFeaturesAdapter.adaptToDataRecords(userFeatures).asScala.head

            FeatureMapBuilder()
              .add(AuthorAccountAge, userFeatures.accountAge)
              .add(AuthorFollowersFeature, userFeatures.followersCount)
              .add(AuthorIsBlueVerifiedFeature, userFeatures.isBlueVerified)
              .add(AuthorIsProtectedFeature, userFeatures.isProtected)
              .add(IsSupportAccountReplyFeature, isSupportAccountReply)
              .add(GizmoduckAuthorFeatures, gizmoduckFeaturesRichDataRecords)
              .add(AuthorSafetyLabels, userFeatures.labels)
              .build()
          }
        }
      }
  }
}
