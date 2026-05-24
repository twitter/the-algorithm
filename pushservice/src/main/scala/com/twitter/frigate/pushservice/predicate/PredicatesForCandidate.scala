package com.twitter.frigate.pushservice.predicate

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.common.base._
import com.twitter.frigate.common.candidate.MaxTweetAge
import com.twitter.frigate.common.candidate.TargetABDecider
import com.twitter.frigate.common.predicate.tweet.TweetAuthorPredicates
import com.twitter.frigate.common.predicate._
import com.twitter.frigate.common.rec_types.RecTypes
import com.twitter.frigate.common.util.SnowflakeUtils
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.pushservice.params.PushFeatureSwitchParams
import com.twitter.frigate.pushservice.params.PushParams
import com.twitter.frigate.pushservice.util.CandidateUtil
import com.twitter.frigate.thriftscala.ChannelName
import com.twitter.frigate.thriftscala.CommonRecommendationType
import com.twitter.gizmoduck.thriftscala.User
import com.twitter.gizmoduck.thriftscala.UserType
import com.twitter.hermit.predicate.NamedPredicate
import com.twitter.hermit.predicate.Predicate
import com.twitter.hermit.predicate.gizmoduck._
import com.twitter.hermit.predicate.socialgraph.Edge
import com.twitter.hermit.predicate.socialgraph.MultiEdge
import com.twitter.hermit.predicate.socialgraph.RelationEdge
import com.twitter.hermit.predicate.socialgraph.SocialGraphPredicate
import com.twitter.service.metastore.gen.thriftscala.Location
import com.twitter.socialgraph.thriftscala.RelationshipType
import com.twitter.stitch.tweetypie.TweetyPie.TweetyPieResult
import com.twitter.storehaus.ReadableStore
import com.twitter.timelines.configapi.Param
import com.twitter.util.Duration
import com.twitter.util.Future

object PredicatesForCandidate {

  def oldTweetRecsPredicate(implicit stats: StatsReceiver): Predicate[
    TweetCandidate with RecommendationType with TargetInfo[
      TargetUser with TargetABDecider with MaxTweetAge
    ]
  ] = {
    val name = "old_tweet"
    Predicate
      .from[TweetCandidate with RecommendationType with TargetInfo[
        TargetUser with TargetABDecider with MaxTweetAge
      ]] { candidate =>
        {
          val crt = candidate.commonRecType
          val defaultAge = if (RecTypes.mrModelingBasedTypes.contains(crt)) {
            candidate.target.params(PushFeatureSwitchParams.ModelingBasedCandidateMaxTweetAgeParam)
          } else if (RecTypes.GeoPopTweetTypes.contains(crt)) {
            candidate.target.params(PushFeatureSwitchParams.GeoPopTweetMaxAgeInHours)
          } else if (RecTypes.simclusterBasedTweets.contains(crt)) {
            candidate.target.params(
              PushFeatureSwitchParams.SimclusterBasedCandidateMaxTweetAgeParam)
          } else if (RecTypes.detopicTypes.contains(crt)) {
            candidate.target.params(PushFeatureSwitchParams.DetopicBasedCandidateMaxTweetAgeParam)
          } else if (RecTypes.f1FirstDegreeTypes.contains(crt)) {
            candidate.target.params(PushFeatureSwitchParams.F1CandidateMaxTweetAgeParam)
          } else if (crt == CommonRecommendationType.ExploreVideoTweet) {
            candidate.target.params(PushFeatureSwitchParams.ExploreVideoTweetAgeParam)
          } else
            candidate.target.params(PushFeatureSwitchParams.MaxTweetAgeParam)
          SnowflakeUtils.isRecent(candidate.tweetId, defaultAge)
        }
      }
      .withStats(stats.scope(name))
      .withName(name)
  }

  def tweetIsNotAreply(
    implicit stats: StatsReceiver
  ): NamedPredicate[TweetCandidate with TweetDetails] = {
    val name = "tweet_candidate_not_a_reply"
    Predicate
      .from[TweetCandidate with TweetDetails] { c =>
        c.isReply match {
          case Some(true) => false
          case _ => true
        }
      }
      .withStats(stats.scope(name))
      .withName(name)
  }

  /**
   * Check if tweet contains any optouted free form interests.
   * Currently, we use it for media categories and semantic core
   * @param stats
   * @return
   */
  def noOptoutFreeFormInterestPredicate(
    implicit stats: StatsReceiver
  ): NamedPredicate[PushCandidate] = {
    val name = "free_form_interest_opt_out"
    val tweetMediaAnnotationFeature =
      "tweet.mediaunderstanding.tweet_annotations.safe_category_probabilities"
    val tweetSemanticCoreFeature =
      "tweet.core.tweet.semantic_core_annotations"
    val scopedStatsReceiver = stats.scope(s"predicate_$name")
    val withOptOutFreeFormInterestsCounter = stats.counter("with_optout_interests")
    val withoutOptOutInterestsCounter = stats.counter("without_optout_interests")
    val withOptOutFreeFormInterestsFromMediaAnnotationCounter =
      stats.counter("with_optout_interests_from_media_annotation")
    val withOptOutFreeFormInterestsFromSemanticCoreCounter =
      stats.counter("with_optout_interests_from_semantic_core")
    Predicate
      .fromAsync { candidate: PushCandidate =>
        val tweetSemanticCoreEntityIds = candidate.sparseBinaryFeatures
          .getOrElse(tweetSemanticCoreFeature, Set.empty[String]).map { id =>
            id.split('.')(2)
          }.toSet
        val tweetMediaAnnotationIds = candidate.sparseContinuousFeatures
          .getOrElse(tweetMediaAnnotationFeature, Map.empty[String, Double]).keys.toSet

        candidate.target.optOutFreeFormUserInterests.map {
          case optOutUserInterests: Seq[String] =>
            withOptOutFreeFormInterestsCounter.incr()
            val optOutUserInterestsSet = optOutUserInterests.toSet
            val mediaAnnoIntersect = optOutUserInterestsSet.intersect(tweetMediaAnnotationIds)
            val semanticCoreIntersect = optOutUserInterestsSet.intersect(tweetSemanticCoreEntityIds)
            if (!mediaAnnoIntersect.isEmpty) {
              withOptOutFreeFormInterestsFromMediaAnnotationCounter.incr()
            }
            if (!semanticCoreIntersect.isEmpty) {
              withOptOutFreeFormInterestsFromSemanticCoreCounter.incr()
            }
            semanticCoreIntersect.isEmpty && mediaAnnoIntersect.isEmpty
          case _ =>
            withoutOptOutInterestsCounter.incr()
            true
        }
      }
      .withStats(scopedStatsReceiver)
      .withName(name)
  }

  def tweetCandidateWithLessThan2SocialContextsIsAReply(
    implicit stats: StatsReceiver
  ): NamedPredicate[TweetCandidate with TweetDetails with SocialContextActions] = {
    val name = "tweet_candidate_with_less_than_2_social_contexts_is_not_a_reply"
    Predicate
      .from[TweetCandidate with TweetDetails with SocialContextActions] { cand =>
        cand.isReply match {
          case Some(true) if cand.socialContextTweetIds.size < 2 => false
          case _ => true
        }
      }
      .withStats(stats.scope(name))
      .withName(name)
  }

  def f1CandidateIsNotAReply(implicit stats: StatsReceiver): NamedPredicate[F1Candidate] = {
    val name = "f1_candidate_is_not_a_reply"
    Predicate
      .from[F1Candidate] { candidate =>
        candidate.isReply match {
          case Some(true) => false
          case _ => true
        }
      }
      .withStats(stats.scope(name))
      .withName(name)
  }

  def outOfNetworkTweetCandidateEnabledCrTag(
    implicit stats: StatsReceiver
  ): NamedPredicate[OutOfNetworkTweetCandidate with TargetInfo[TargetUser with TargetABDecider]] = {
    val name = "out_of_network_tweet_candidate_enabled_crtag"
    val scopedStats = stats.scope(name)
    Predicate
      .from[OutOfNetworkTweetCandidate with TargetInfo[TargetUser with TargetABDecider]] { cand =>
        val disabledCrTag = cand.target
          .params(PushFeatureSwitchParams.OONCandidatesDisabledCrTagParam)
        val candGeneratedByDisabledSignal = cand.tagsCR.exists { tagsCR =>
          val tagsCRSet = tagsCR.map(_.toString).toSet
          tagsCRSet.nonEmpty && tagsCRSet.subsetOf(disabledCrTag.toSet)
        }
        if (candGeneratedByDisabledSignal) {
          cand.tagsCR.getOrElse(Nil).foreach(tag => scopedStats.counter(tag.toString).incr())
          false
        } else true
      }
      .withStats(scopedStats)
      .withName(name)
  }

  def outOfNetworkTweetCandidateEnabledCrtGroup(
    implicit stats: StatsReceiver
  ): NamedPredicate[OutOfNetworkTweetCandidate with TargetInfo[TargetUser with TargetABDecider]] = {
    val name = "out_of_network_tweet_candidate_enabled_crt_group"
    val scopedStats = stats.scope(name)
    Predicate
      .from[OutOfNetworkTweetCandidate with TargetInfo[TargetUser with TargetABDecider]] { cand =>
        val disabledCrtGroup = cand.target
          .params(PushFeatureSwitchParams.OONCandidatesDisabledCrtGroupParam)
        val crtGroup = CandidateUtil.getCrtGroup(cand.commonRecType)
        val candGeneratedByDisabledCrt = disabledCrtGroup.contains(crtGroup)
        if (candGeneratedByDisabledCrt) {
          scopedStats.counter("filter_" + crtGroup.toString).incr()
          false
        } else true
      }
      .withStats(scopedStats)
      .withName(name)
  }

  def outOfNetworkTweetCandidateIsNotAReply(
    implicit stats: StatsReceiver
  ): NamedPredicate[OutOfNetworkTweetCandidate] = {
    val name = "out_of_network_tweet_candidate_is_not_a_reply"
    Predicate
      .from[OutOfNetworkTweetCandidate] { cand =>
        cand.isReply match {
          case Some(true) => false
          case _ => true
        }
      }
      .withStats(stats.scope(name))
      .withName(name)
  }

  def recommendedTweetIsAuthoredBySelf(
    implicit statsReceiver: StatsReceiver
  ): NamedPredicate[PushCandidate] =
    Predicate
      .from[PushCandidate] {
        case tweetCandidate: PushCandidate with TweetDetails =>
          tweetCandidate.authorId match {
            case Some(authorId) => authorId != tweetCandidate.target.targetId
            case None => true
          }
        case _ =>
          true
      }
      .withStats(statsReceiver.scope("predicate_self_author"))
      .withName("self_author")

  def authorInSocialContext(implicit statsReceiver: StatsReceiver): NamedPredicate[PushCandidate] =
    Predicate
      .from[PushCandidate] {
        case tweetCandidate: PushCandidate with TweetDetails with SocialContextActions =>
          tweetCandidate.authorId match {
            case Some(authorId) =>
              !tweetCandidate.socialContextUserIds.contains(authorId)
            case None => true
          }
        case _ => true
      }
      .withStats(statsReceiver.scope("predicate_author_social_context"))
      .withName("author_social_context")

  def selfInSocialContext(implicit statsReceiver: StatsReceiver): NamedPredicate[PushCandidate] = {
    val name = "self_social_context"
    Predicate
      .from[PushCandidate] {
        case candidate: PushCandidate with SocialContextActions =>
          !candidate.socialContextUserIds.contains(candidate.target.targetId)
        case _ =>
          true
      }
      .withStats(statsReceiver.scope(s"${name}_predicate"))
      .withName(name)
  }

  def minSocialContext(
    threshold: Int
  )(
    implicit statsReceiver: StatsReceiver
  ): NamedPredicate[PushCandidate with SocialContextActions] = {
    Predicate
      .from { candidate: PushCandidate with SocialContextActions =>
        candidate.socialContextUserIds.size >= threshold
      }
      .withStats(statsReceiver.scope("predicate_min_social_context"))
      .withName("min_social_context")
  }

  private def anyWithheldContent(
    userStore: ReadableStore[Long, User],
    userCountryStore: ReadableStore[Long, Location]
  )(
    implicit statsReceiver: StatsReceiver
  ): Predicate[TargetRecUser] =
    GizmoduckUserPredicate.withheldContentPredicate(
      userStore = userStore,
      userCountryStore = userCountryStore,
      statsReceiver = statsReceiver,
      checkAllCountries = true
    )

  def targetUserExists(implicit statsReceiver: StatsReceiver): NamedPredicate[PushCandidate] = {
    TargetUserPredicates
      .targetUserExists()(statsReceiver)
      .flatContraMap { candidate: PushCandidate => Future.value(candidate.target) }
      .withName("target_user_exists")
  }

  def secondaryDormantAccountPredicate(
    implicit statsReceiver: StatsReceiver
  ): NamedPredicate[PushCandidate] = {
    val name = "secondary_dormant_account"
    TargetUserPredicates
      .secondaryDormantAccountPredicate()(statsReceiver)
      .on { candidate: PushCandidate => candidate.target }
      .withStats(statsReceiver.scope(s"predicate_$name"))
      .withName(name)
  }

  def socialContextBeingFollowed(
    edgeStore: ReadableStore[RelationEdge, Boolean]
  )(
    implicit statsReceiver: StatsReceiver
  ): NamedPredicate[PushCandidate with SocialContextActions] =
    SocialGraphPredicate
      .allRelationEdgesExist(edgeStore, RelationshipType.Following)
      .on { candidate: PushCandidate with SocialContextActions =>
        candidate.socialContextUserIds.map { u => Edge(candidate.target.targetId, u) }
      }
      .withStats(statsReceiver.scope("predicate_social_context_being_followed"))
      .withName("social_context_being_followed")

  private def edgeFromCandidate(candidate: PushCandidate with TweetAuthor): Option[Edge] = {
    candidate.authorId map { authorId => Edge(candidate.target.targetId, authorId) }
  }

  def authorNotBeingDeviceFollowed(
    edgeStore: ReadableStore[RelationEdge, Boolean]
  )(
    implicit statsReceiver: StatsReceiver
  ): NamedPredicate[PushCandidate with TweetAuthor] = {
    SocialGraphPredicate
      .relationExists(edgeStore, RelationshipType.DeviceFollowing)
      .optionalOn(
        edgeFromCandidate,
        missingResult = false
      )
      .flip
      .withStats(statsReceiver.scope("predicate_author_not_device_followed"))
      .withName("author_not_device_followed")
  }

  def authorBeingFollowed(
    edgeStore: ReadableStore[RelationEdge, Boolean]
  )(
    implicit statsReceiver: StatsReceiver
  ): NamedPredicate[PushCandidate with TweetAuthor] = {
    SocialGraphPredicate
      .relationExists(edgeStore, RelationshipType.Following)
      .optionalOn(
        edgeFromCandidate,
        missingResult = false
      )
      .withStats(statsReceiver.scope("predicate_author_being_followed"))
      .withName("author_being_followed")
  }

  def authorNotBeingFollowed(
    edgeStore: ReadableStore[RelationEdge, Boolean]
  )(
    implicit statsReceiver: StatsReceiver
  ): NamedPredicate[PushCandidate with TweetAuthor] = {
    SocialGraphPredicate
      .relationExists(edgeStore, RelationshipType.Following)
      .optionalOn(
        edgeFromCandidate,
        missingResult = false
      )
      .flip
      .withStats(statsReceiver.scope("predicate_author_not_being_followed"))
      .withName("author_not_being_followed")
  }

  def recommendedTweetAuthorAcceptableToTargetUser(
    edgeStore: ReadableStore[RelationEdge, Boolean]
  )(
    implicit statsReceiver: StatsReceiver
  ): NamedPredicate[PushCandidate with TweetAuthor] = {
    val name = "recommended_tweet_author_acceptable_to_target_user"
    SocialGraphPredicate
      .anyRelationExists(
        edgeStore,
        Set(
          RelationshipType.Blocking,
          RelationshipType.BlockedBy,
          RelationshipType.HideRecommendations,
          RelationshipType.Muting
        )
      )
      .flip
      .optionalOn(
        edgeFromCandidate,
        missingResult = false
      )
      .withStats(statsReceiver.scope(s"predicate_$name"))
      .withName(name)
  }

  def relationNotExistsPredicate(
    edgeStore: ReadableStore[RelationEdge, Boolean],
    relations: Set[RelationshipType]
  ): Predicate[(Long, Iterable[Long])] =
    SocialGraphPredicate
      .anyRelationExistsForMultiEdge(
        edgeStore,
        relations
      )
      .flip
      .on {
        case (targetUserId, userIds) =>
          MultiEdge(targetUserId, userIds.toSet)
      }

  def blocking(edgeStore: ReadableStore[RelationEdge, Boolean]): Predicate[(Long, Iterable[Long])] =
    relationNotExistsPredicate(
      edgeStore,
      Set(RelationshipType.BlockedBy, RelationshipType.Blocking)
    )

  def blockingOrMuting(
    edgeStore: ReadableStore[RelationEdge, Boolean]
  ): Predicate[(Long, Iterable[Long])] =
    relationNotExistsPredicate(
      edgeStore,
      Set(RelationshipType.BlockedBy, RelationshipType.Blocking, RelationshipType.Muting)
    )

  def socialContextNotRetweetFollowing(
    edgeStore: ReadableStore[RelationEdge, Boolean]
  )(
    implicit statsReceiver: StatsReceiver
  ): NamedPredicate[PushCandidate with SocialContextActions] = {
    val name = "social_context_not_retweet_following"
    relationNotExistsPredicate(edgeStore, Set(RelationshipType.NotRetweetFollowing))
      .optionalOn[PushCandidate with SocialContextActions](
        {
          case candidate: PushCandidate with SocialContextActions
              if RecTypes.isTweetRetweetType(candidate.commonRecType) =>
            Some((candidate.target.targetId, candidate.socialContextUserIds))
          case _ =>
            None
        },
        missingResult = true
      )
      .withStats(statsReceiver.scope(s"predicate_$name"))
      .withName(name)
  }

  def socialContextBlockingOrMuting(
    edgeStore: ReadableStore[RelationEdge, Boolean]
  )(
    implicit statsReceiver: StatsReceiver
  ): NamedPredicate[PushCandidate with SocialContextActions] =
    blockingOrMuting(edgeStore)
      .on { candidate: PushCandidate with SocialContextActions =>
        (candidate.target.targetId, candidate.socialContextUserIds)
      }
      .withStats(statsReceiver.scope("predicate_social_context_blocking_or_muting"))
      .withName("social_context_blocking_or_muting")

  /**
   * Use hyrated Tweet object for F1 Protected experiment for checking null cast as Tweetypie hydration
   * fails for protected Authors without passing in Target id. We do this specifically for
   * F1 Protected Tweet Experiment in Earlybird Adaptor.
   * For rest of the traffic refer to existing Nullcast Predicate
   */
  def nullCastF1ProtectedExperientPredicate(
    tweetypieStore: ReadableStore[Long, TweetyPieResult]
  )(
    implicit statsReceiver: StatsReceiver
  ): NamedPredicate[PushCandidate with TweetCandidate with TweetDetails] = {
    val name = "f1_exempted_null_cast_tweet"
    val f1NullCastCheckCounter = statsReceiver.scope(name).counter("f1_null_cast_check")
    Predicate
      .fromAsync { tweetCandidate: PushCandidate with TweetCandidate with TweetDetails =>
        if (RecTypes.f1FirstDegreeTypes(tweetCandidate.commonRecType) && tweetCandidate.target
            .params(PushFeatureSwitchParams.EnableF1FromProtectedTweetAuthors)) {
          f1NullCastCheckCounter.incr()
          tweetCandidate.tweet match {
            case Some(tweetObj) =>
              baseNullCastTweet().apply(Seq(TweetyPieResult(tweetObj, None, None))).map(_.head)
            case _ => Future.False
          }
        } else {
          nullCastTweet(tweetypieStore).apply(Seq(tweetCandidate)).map(_.head)
        }
      }
      .withStats(statsReceiver.scope(s"predicate_$name"))
      .withName(name)
  }

  private def baseNullCastTweet(): Predicate[TweetyPieResult] =
    Predicate.from { t: TweetyPieResult => !t.tweet.coreData.exists { cd => cd.nullcast } }

  def nullCastTweet(
    tweetyPieStore: ReadableStore[Long, TweetyPieResult]
  )(
    implicit statsReceiver: StatsReceiver
  ): NamedPredicate[PushCandidate with TweetCandidate] = {
    val name = "null_cast_tweet"
    baseNullCastTweet()
      .flatOptionContraMap[PushCandidate with TweetCandidate](
        f = (tweetCandidate: PushCandidate
          with TweetCandidate) => tweetyPieStore.get(tweetCandidate.tweetId),
        missingResult = false
      )
      .withStats(statsReceiver.scope(s"predicate_$name"))
      .withName(name)
  }

  /**
   * Use the predicate except fn is true.
   */
  def exceptedPredicate[T <: PushCandidate](
    name: String,
    fn: T => Future[Boolean],
    predicate: Predicate[T]
  )(
    implicit statsReceiver: StatsReceiver
  ): NamedPredicate[T] = {
    Predicate
      .fromAsync { e: T => fn(e) }
      .or(predicate)
      .withStats(statsReceiver.scope(name))
      .withName(name)
  }

  /**
   *
   * @param edgeStore [[ReadableStore[RelationEdge, Boolean]]]
   * @return - allow only out-network tweets if in-network tweets are disabled
   */
  def disableInNetworkTweetPredicate(
    edgeStore: ReadableStore[RelationEdge, Boolean]
  )(
    implicit statsReceiver: StatsReceiver
  ): NamedPredicate[PushCandidate with TweetAuthor] = {
    val name = "disable_in_network_tweet"
    Predicate
      .fromAsync { candidate: PushCandidate with TweetAuthor =>
        if (candidate.target.params(PushParams.DisableInNetworkTweetCandidatesParam)) {
          authorNotBeingFollowed(edgeStore)
            .apply(Seq(candidate))
            .map(_.head)
        } else Future.True
      }.withStats(statsReceiver.scope(name))
      .withName(name)
  }

  /**
   *
   * @param edgeStore [[ReadableStore[RelationEdge, Boolean]]]
   * @return - allow only in-network tweets if out-network tweets are disabled
   */
  def disableOutNetworkTweetPredicate(
    edgeStore: ReadableStore[RelationEdge, Boolean]
  )(
    implicit statsReceiver: StatsReceiver
  ): NamedPredicate[PushCandidate with TweetAuthor] = {
    val name = "disable_out_network_tweet"
    Predicate
      .fromAsync { candidate: PushCandidate with TweetAuthor =>
        if (candidate.target.params(PushFeatureSwitchParams.DisableOutNetworkTweetCandidatesFS)) {
          authorBeingFollowed(edgeStore)
            .apply(Seq(candidate))
            .map(_.head)
        } else Future.True
      }.withStats(statsReceiver.scope(name))
      .withName(name)
  }

  def alwaysTruePredicate: NamedPredicate[PushCandidate] = {
    Predicate
      .all[PushCandidate]
      .withName("predicate_AlwaysTrue")
  }

  def alwaysTruePushCandidatePredicate: NamedPredicate[PushCandidate] = {
    Predicate
      .all[PushCandidate]
      .withName("predicate_AlwaysTrue")
  }

  def alwaysFalsePredicate(implicit statsReceiver: StatsReceiver): NamedPredicate[PushCandidate] = {
    val name = "predicate_AlwaysFalse"
    val scopedStatsReceiver = statsReceiver.scope(name)
    Predicate
      .from { candidate: PushCandidate => false }
      .withStats(scopedStatsReceiver)
      .withName(name)
  }

  def accountCountryPredicate(
    allowedCountries: Set[String]
  )(
    implicit statsReceiver: StatsReceiver
  ): NamedPredicate[PushCandidate] = {
    val name = "AccountCountryPredicate"
    val stats = statsReceiver.scope(name)
    AccountCountryPredicate(allowedCountries)
      .on { candidate: PushCandidate => candidate.target }
      .withStats(stats)
      .withName(name)
  }

  def paramPredicate[T <: PushCandidate](
    param: Param[Boolean]
  )(
    implicit statsReceiver: StatsReceiver
  ): NamedPredicate[T] = {
    val name = param.getClass.getSimpleName.stripSuffix("$")
    TargetPredicates
      .paramPredicate(param)
      .on { candidate: PushCandidate => candidate.target }
      .withStats(statsReceiver.scope(s"param_${name}_controlled_predicate"))
      .withName(s"param_${name}_controlled_predicate")
  }

  def isDeviceEligibleForNewsOrSports(
    implicit stats: StatsReceiver
  ): NamedPredicate[PushCandidate] = {
    val name = "is_device_eligible_for_news_or_sports"
    val scopedStatsReceiver = stats.scope(s"predicate_$name")
    Predicate
      .fromAsync { candidate: PushCandidate =>
        candidate.target.deviceInfo.map(_.exists(_.isNewsEligible))
      }
      .withStats(scopedStatsReceiver)
      .withName(name)
  }

  def isDeviceEligibleForCreatorPush(
    implicit stats: StatsReceiver
  ): NamedPredicate[PushCandidate] = {
    val name = "is_device_eligible_for_creator_push"
    val scopedStatsReceiver = stats.scope(s"predicate_$name")
    Predicate
      .fromAsync { candidate: PushCandidate =>
        candidate.target.deviceInfo.map(_.exists(settings =>
          settings.isNewsEligible || settings.isRecommendationsEligible))
      }
      .withStats(scopedStatsReceiver)
      .withName(name)
  }

  /**
   * Like [[TargetUserPredicates.homeTimelineFatigue()]] but for candidate.
   */
  def htlFatiguePredicate(
    fatigueDuration: Param[Duration]
  )(
    implicit statsReceiver: StatsReceiver
  ): NamedPredicate[PushCandidate] = {
    val name = "htl_fatigue"
    Predicate
      .fromAsync { candidate: PushCandidate =>
        val _fatigueDuration = candidate.target.params(fatigueDuration)
        TargetUserPredicates
          .homeTimelineFatigue(
            fatigueDuration = _fatigueDuration
          ).apply(Seq(candidate.target)).map(_.head)
      }
      .withStats(statsReceiver.scope(name))
      .withName(name)
  }

  def mrWebHoldbackPredicate(
    implicit stats: StatsReceiver
  ): NamedPredicate[PushCandidate] = {
    val name = "mr_web_holdback_for_candidate"
    val scopedStats = stats.scope(name)
    PredicatesForCandidate.exludeCrtFromPushHoldback
      .or(
        TargetPredicates
          .webNotifsHoldback()
          .on { candidate: PushCandidate => candidate.target }
      )
      .withStats(scopedStats)
      .withName(name)
  }

  def candidateEnabledForEmailPredicate(
  )(
    implicit stats: StatsReceiver
  ): NamedPredicate[PushCandidate] = {
    val name = "candidates_enabled_for_email"
    Predicate
      .from { candidate: PushCandidate =>
        if (candidate.target.isEmailUser)
          candidate.isInstanceOf[TweetCandidate with TweetAuthor with RecommendationType]
        else true
      }
      .withStats(stats.scope(name))
      .withName(name)
  }

  def protectedTweetF1ExemptPredicate[
    T <: TargetUser with TargetABDecider,
    Cand <: TweetCandidate with TweetAuthorDetails with TargetInfo[T]
  ](
    implicit stats: StatsReceiver
  ): NamedPredicate[
    TweetCandidate with TweetAuthorDetails with TargetInfo[
      TargetUser with TargetABDecider
    ]
  ] = {
    val name = "f1_exempt_tweet_author_protected"
    val skipForProtectedAuthorScope = stats.scope(name).scope("skip_protected_author_for_f1")
    val authorIsProtectedCounter = skipForProtectedAuthorScope.counter("author_protected_true")
    val authorIsNotProtectedCounter = skipForProtectedAuthorScope.counter("author_protected_false")
    val authorNotFoundCounter = stats.scope(name).counter("author_not_found")
    Predicate
      .fromAsync[TweetCandidate with TweetAuthorDetails with TargetInfo[
        TargetUser with TargetABDecider
      ]] {
        case candidate: F1Candidate
            if candidate.target.params(PushFeatureSwitchParams.EnableF1FromProtectedTweetAuthors) =>
          candidate.tweetAuthor.foreach {
            case Some(author) =>
              if (GizmoduckUserPredicate.isProtected(author)) {
                authorIsProtectedCounter.incr()
              } else authorIsNotProtectedCounter.incr()
            case _ => authorNotFoundCounter.incr()
          }
          Future.True
        case cand =>
          TweetAuthorPredicates.recTweetAuthorProtected.apply(Seq(cand)).map(_.head)
      }
      .withStats(stats.scope(name))
      .withName(name)
  }

  /**
   * filter a notification if user has already received ANY prior notification about the space id
   * @param stats
   * @return
   */
  def duplicateSpacesPredicate(
    implicit stats: StatsReceiver
  ): NamedPredicate[Space with PushCandidate] = {
    val name = "duplicate_spaces_predicate"
    Predicate
      .fromAsync { c: Space with PushCandidate =>
        c.target.pushRecItems.map { pushRecItems =>
          !pushRecItems.spaceIds.contains(c.spaceId)
        }
      }
      .withStats(stats.scope(name))
      .withName(name)
  }

  def filterOONCandidatePredicate(
  )(
    implicit stats: StatsReceiver
  ): NamedPredicate[PushCandidate] = {
    val name = "filter_oon_candidate"

    Predicate
      .fromAsync[PushCandidate] { cand =>
        val crt = cand.commonRecType
        val isOONCandidate =
          RecTypes.isOutOfNetworkTweetRecType(crt) || RecTypes.outOfNetworkTopicTweetTypes
            .contains(crt) || RecTypes.isOutOfNetworkSpaceType(crt) || RecTypes.userTypes.contains(
            crt)
        if (isOONCandidate) {
          cand.target.notificationsFromOnlyPeopleIFollow.map { inNetworkOnly =>
            if (inNetworkOnly) {
              stats.scope(name, crt.toString).counter("inNetworkOnlyOn").incr()
            } else {
              stats.scope(name, crt.toString).counter("inNetworkOnlyOff").incr()
            }
            !(inNetworkOnly && cand.target.params(
              PushFeatureSwitchParams.EnableOONFilteringBasedOnUserSettings))
          }
        } else Future.True
      }
      .withStats(stats.scope(name))
      .withName(name)
  }

  def exludeCrtFromPushHoldback(
    implicit stats: StatsReceiver
  ): NamedPredicate[PushCandidate] = Predicate
    .from { candidate: PushCandidate =>
      val crtName = candidate.commonRecType.name
      val target = candidate.target
      target
        .params(PushFeatureSwitchParams.CommonRecommendationTypeDenyListPushHoldbacks)
        .exists(crtName.equalsIgnoreCase)
    }
    .withStats(stats.scope("exclude_crt_from_push_holdbacks"))

  def enableSendHandlerCandidates(implicit stats: StatsReceiver): NamedPredicate[PushCandidate] = {
    val name = "sendhandler_enable_push_recommendations"
    PredicatesForCandidate.exludeCrtFromPushHoldback
      .or(PredicatesForCandidate.paramPredicate(
        PushFeatureSwitchParams.EnablePushRecommendationsParam))
      .withStats(stats.scope(name))
      .withName(name)
  }

  def openAppExperimentUserCandidateAllowList(
    implicit stats: StatsReceiver
  ): NamedPredicate[PushCandidate] = {
    val name = "open_app_experiment_user_candidate_allow_list"
    Predicate
      .fromAsync { candidate: PushCandidate =>
        val target = candidate.target
        Future.join(target.isOpenAppExperimentUser, target.targetUser).map {
          case (isOpenAppUser, targetUser) =>
            val shouldLimitOpenAppCrts =
              isOpenAppUser || targetUser.exists(_.userType == UserType.Soft)

            if (shouldLimitOpenAppCrts) {
              val listOfAllowedCrt = target
                .params(PushFeatureSwitchParams.ListOfCrtsForOpenApp)
                .flatMap(CommonRecommendationType.valueOf)
              listOfAllowedCrt.contains(candidate.commonRecType)
            } else true
        }
      }.withStats(stats.scope(name))
      .withName(name)
  }

  def isTargetBlueVerified(
    implicit stats: StatsReceiver
  ): NamedPredicate[PushCandidate] = {
    val name = "is_target_already_blue_verified"
    Predicate
      .fromAsync { candidate: PushCandidate =>
        val target = candidate.target
        target.isBlueVerified.map(_.getOrElse(false))
      }.withStats(stats.scope(name))
      .withName(name)
  }

  def isTargetLegacyVerified(
    implicit stats: StatsReceiver
  ): NamedPredicate[PushCandidate] = {
    val name = "is_target_already_legacy_verified"
    Predicate
      .fromAsync { candidate: PushCandidate =>
        val target = candidate.target
        target.isVerified.map(_.getOrElse(false))
      }.withStats(stats.scope(name))
      .withName(name)
  }

  def isTargetSuperFollowCreator(implicit stats: StatsReceiver): NamedPredicate[PushCandidate] = {
    val name = "is_target_already_super_follow_creator"
    Predicate
      .fromAsync { candidate: PushCandidate =>
        val target = candidate.target
        target.isSuperFollowCreator.map(
          _.getOrElse(false)
        )
      }.withStats(stats.scope(name))
      .withName(name)
  }

  def isChannelValidPredicate(
    implicit stats: StatsReceiver
  ): NamedPredicate[PushCandidate] = {
    val name = "is_channel_valid"
    val scopedStatsReceiver = stats.scope(s"predicate_$name")
    Predicate
      .fromAsync { candidate: PushCandidate =>
        candidate
          .getChannels().map(channels =>
            !(channels.toSet.size == 1 && channels.head == ChannelName.None))
      }
      .withStats(scopedStatsReceiver)
      .withName(name)
  }
}
