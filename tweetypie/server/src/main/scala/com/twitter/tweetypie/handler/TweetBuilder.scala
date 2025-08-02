package com.twitter.tweetypie
package handler

import com.twitter.featureswitches.v2.FeatureSwitchResults
import com.twitter.featureswitches.v2.FeatureSwitches
import com.twitter.gizmoduck.thriftscala.AccessPolicy
import com.twitter.gizmoduck.thriftscala.LabelValue
import com.twitter.gizmoduck.thriftscala.UserType
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.stitch.NotFound
import com.twitter.stitch.Stitch
import com.twitter.tweetypie.additionalfields.AdditionalFields._
import com.twitter.tweetypie.core._
import com.twitter.tweetypie.jiminy.tweetypie.NudgeBuilder
import com.twitter.tweetypie.jiminy.tweetypie.NudgeBuilderRequest
import com.twitter.tweetypie.media.Media
import com.twitter.tweetypie.repository.StratoCommunityAccessRepository.CommunityAccess
import com.twitter.tweetypie.repository._
import com.twitter.tweetypie.serverutil.DeviceSourceParser
import com.twitter.tweetypie.serverutil.EtwittertendedTweetMetadataBuilder
import com.twitter.tweetypie.store._
import com.twitter.tweetypie.thriftscala._
import com.twitter.tweetypie.thriftscala.entities.EntityEtwittertractor
import com.twitter.tweetypie.tweettetwittert._
import com.twitter.tweetypie.util.CommunityAnnotation
import com.twitter.tweetypie.util.CommunityUtil
import com.twitter.twittertetwittert.Regetwitter.{VALID_URL => UrlPattern}
import com.twitter.twittertetwittert.TwitterTetwittertParser

case class TweetBuilderResult(
  tweet: Tweet,
  user: User,
  createdAt: Time,
  sourceTweet: Option[Tweet] = None,
  sourceUser: Option[User] = None,
  parentUserId: Option[UserId] = None,
  isSilentFail: Boolean = false,
  geoSearchRequestId: Option[GeoSearchRequestId] = None,
  initialTweetUpdateRequest: Option[InitialTweetUpdateRequest] = None)

object TweetBuilder {
  import GizmoduckUserCountsUpdatingStore.isUserTweet
  import PostTweet._
  import Preprocessor._
  import TweetCreateState.{Spam => CreateStateSpam, _}
  import TweetTetwittert._
  import UpstreamFailure._

  type Type = FutureArrow[PostTweetRequest, TweetBuilderResult]

  val log: Logger = Logger(getClass)

  private[this] val _unitMutation = Future.value(Mutation.unit[Any])
  def MutationUnitFuture[T]: Future[Mutation[T]] = _unitMutation.asInstanceOf[Future[Mutation[T]]]

  case class MissingConversationId(inReplyToTweetId: TweetId) etwittertends RuntimeEtwitterception

  case class TetwittertVisibility(
    visibleTetwittertRange: Option[TetwittertRange],
    totalTetwittertDisplayLength: Offset.DisplayUnit,
    visibleTetwittert: String) {
    val isEtwittertendedTweet: Boolean = totalTetwittertDisplayLength.toInt > OriginalMatwitterDisplayLength

    /**
     *  Going forward we will be moving away from quoted-tweets urls in tweet tetwittert, but we
     *  have a backwards-compat layer in Tweetypie which adds the QT url to tetwittert to provide
     *  support for all clients to read in a backwards-compatible way until they upgrade.
     *
     *  Tweets can become etwittertended as their display length can go beyond 140
     *  after adding the QT short url. Therefore, we are adding below function
     *  to account for legacy formatting during read-time and generate a self-permalink.
     */
    def isEtwittertendedWithEtwittertraChars(etwittertraChars: Int): Boolean =
      totalTetwittertDisplayLength.toInt > (OriginalMatwitterDisplayLength - etwittertraChars)
  }

  /** Matwitter number of users that can be tagged on a single tweet */
  val MatwitterMediaTagCount = 10

  val MobileWebApp = "oauth:49152"
  val M2App = "oauth:3033294"
  val M5App = "oauth:3033300"

  val TestRateLimitUserRole = "stresstest"

  /**
   * The fields to fetch for the user creating the tweet.
   */
  val userFields: Set[UserField] =
    Set(
      UserField.Profile,
      UserField.ProfileDesign,
      UserField.Account,
      UserField.Safety,
      UserField.Counts,
      UserField.Roles,
      UserField.UrlEntities,
      UserField.Labels
    )

  /**
   * The fields to fetch for the user of the source tweet in a retweet.
   */
  val sourceUserFields: Set[UserField] =
    userFields + UserField.View

  /**
   * Converts repository etwitterceptions into an API-compatible etwitterception type
   */
  def convertRepoEtwitterceptions[A](
    notFoundState: TweetCreateState,
    failureHandler: Throwable => Throwable
  ): PartialFunction[Throwable, Stitch[A]] = {
    // stitch.NotFound is converted to the supplied TweetCreateState, wrapped in TweetCreateFailure
    case NotFound => Stitch.etwitterception(TweetCreateFailure.State(notFoundState))
    // OverCapacity etwitterceptions should not be translated and should bubble up to the top
    case etwitter: OverCapacity => Stitch.etwitterception(etwitter)
    // Other etwitterceptions are wrapped in the supplied failureHandler
    case etwitter => Stitch.etwitterception(failureHandler(etwitter))
  }

  /**
   * Adapts a UserRepository to a Repository for looking up a single user and that
   * fails with an appropriate TweetCreateFailure if the user is not found.
   */
  def userLookup(userRepo: UserRepository.Type): UserId => Stitch[User] = {
    val opts = UserQueryOptions(queryFields = userFields, visibility = UserVisibility.All)

    userId =>
      userRepo(UserKey(userId), opts)
        .rescue(convertRepoEtwitterceptions[User](UserNotFound, UserLookupFailure(_)))
  }

  /**
   * Adapts a UserRepository to a Repository for looking up a single user and that
   * fails with an appropriate TweetCreateFailure if the user is not found.
   */
  def sourceUserLookup(userRepo: UserRepository.Type): (UserId, UserId) => Stitch[User] = {
    val opts = UserQueryOptions(queryFields = sourceUserFields, visibility = UserVisibility.All)

    (userId, forUserId) =>
      userRepo(UserKey(userId), opts.copy(forUserId = Some(forUserId)))
        .rescue(convertRepoEtwitterceptions[User](SourceUserNotFound, UserLookupFailure(_)))
  }

  /**
   * Any fields that are loaded on the user via TweetBuilder/RetweetBuilder, but which should not
   * be included on the user in the async-insert actions (such as hosebird) should be removed here.
   *
   * This will include perspectival fields that were loaded relative to the user creating the tweet.
   */
  def scrubUserInAsyncInserts: User => User =
    user => user.copy(view = None)

  /**
   * Any fields that are loaded on the source user via TweetBuilder/RetweetBuilder, but which
   * should not be included on the user in the async-insert actions (such as hosebird) should
   * be removed here.
   *
   * This will include perspectival fields that were loaded relative to the user creating the tweet.
   */
  def scrubSourceUserInAsyncInserts: User => User =
    // currently the same as scrubUserInAsyncInserts, could be different in the future
    scrubUserInAsyncInserts

  /**
   * Any fields that are loaded on the source tweet via RetweetBuilder, but which should not be
   * included on the source tweetypie in the async-insert actions (such as hosebird) should
   * be removed here.
   *
   * This will include perspectival fields that were loaded relative to the user creating the tweet.
   */
  def scrubSourceTweetInAsyncInserts: Tweet => Tweet =
    tweet => tweet.copy(perspective = None, cards = None, card2 = None)

  /**
   * Adapts a DeviceSource to a Repository for looking up a single device-source and that
   * fails with an appropriate TweetCreateFailure if not found.
   */
  def deviceSourceLookup(devSrcRepo: DeviceSourceRepository.Type): DeviceSourceRepository.Type =
    appIdStr => {
      val result: Stitch[DeviceSource] =
        if (DeviceSourceParser.isValid(appIdStr)) {
          devSrcRepo(appIdStr)
        } else {
          Stitch.etwitterception(NotFound)
        }

      result.rescue(convertRepoEtwitterceptions(DeviceSourceNotFound, DeviceSourceLookupFailure(_)))
    }

  /**
   * Checks:
   *   - that we have all the user fields we need
   *   - that the user is active
   *   - that they are not a frictionless follower account
   */
  def validateUser(user: User): Future[Unit] =
    if (user.safety.isEmpty)
      Future.etwitterception(UserSafetyEmptyEtwitterception)
    else if (user.profile.isEmpty)
      Future.etwitterception(UserProfileEmptyEtwitterception)
    else if (user.safety.get.deactivated)
      Future.etwitterception(TweetCreateFailure.State(UserDeactivated))
    else if (user.safety.get.suspended)
      Future.etwitterception(TweetCreateFailure.State(UserSuspended))
    else if (user.labels.etwitterists(_.labels.etwitterists(_.labelValue == LabelValue.ReadOnly)))
      Future.etwitterception(TweetCreateFailure.State(CreateStateSpam))
    else if (user.userType == UserType.Frictionless)
      Future.etwitterception(TweetCreateFailure.State(UserNotFound))
    else if (user.userType == UserType.Soft)
      Future.etwitterception(TweetCreateFailure.State(UserNotFound))
    else if (user.safety.get.accessPolicy == AccessPolicy.BounceAll ||
      user.safety.get.accessPolicy == AccessPolicy.BounceAllPublicWrites)
      Future.etwitterception(TweetCreateFailure.State(UserReadonly))
    else
      Future.Unit

  def validateCommunityReply(
    communities: Option[Communities],
    replyResult: Option[ReplyBuilder.Result]
  ): Future[Unit] = {

    if (replyResult.flatMap(_.reply.inReplyToStatusId).nonEmpty) {
      val rootCommunities = replyResult.flatMap(_.community)
      val rootCommunityIds = CommunityUtil.communityIds(rootCommunities)
      val replyCommunityIds = CommunityUtil.communityIds(communities)

      if (rootCommunityIds == replyCommunityIds) {
        Future.Unit
      } else {
        Future.etwitterception(TweetCreateFailure.State(CommunityReplyTweetNotAllowed))
      }
    } else {
      Future.Unit
    }
  }

  // Project requirements do not allow etwitterclusive tweets to be replies.
  // All etwitterclusive tweets must be root tweets.
  def validateEtwitterclusiveTweetNotReplies(
    etwitterclusiveTweetControls: Option[EtwitterclusiveTweetControl],
    replyResult: Option[ReplyBuilder.Result]
  ): Future[Unit] = {
    val isInReplyToTweet = replyResult.etwitterists(_.reply.inReplyToStatusId.isDefined)
    if (etwitterclusiveTweetControls.isDefined && isInReplyToTweet) {
      Future.etwitterception(TweetCreateFailure.State(SuperFollowsInvalidParams))
    } else {
      Future.Unit
    }
  }

  // Invalid parameters for Etwitterclusive Tweets:
  // - Community field set # Tweets can not be both at the same time.
  def validateEtwitterclusiveTweetParams(
    etwitterclusiveTweetControls: Option[EtwitterclusiveTweetControl],
    communities: Option[Communities]
  ): Future[Unit] = {
    if (etwitterclusiveTweetControls.isDefined && CommunityUtil.hasCommunity(communities)) {
      Future.etwitterception(TweetCreateFailure.State(SuperFollowsInvalidParams))
    } else {
      Future.Unit
    }
  }

  def validateTrustedFriendsNotReplies(
    trustedFriendsControl: Option[TrustedFriendsControl],
    replyResult: Option[ReplyBuilder.Result]
  ): Future[Unit] = {
    val isInReplyToTweet = replyResult.etwitterists(_.reply.inReplyToStatusId.isDefined)
    if (trustedFriendsControl.isDefined && isInReplyToTweet) {
      Future.etwitterception(TweetCreateFailure.State(TrustedFriendsInvalidParams))
    } else {
      Future.Unit
    }
  }

  def validateTrustedFriendsParams(
    trustedFriendsControl: Option[TrustedFriendsControl],
    conversationControl: Option[TweetCreateConversationControl],
    communities: Option[Communities],
    etwitterclusiveTweetControl: Option[EtwitterclusiveTweetControl]
  ): Future[Unit] = {
    if (trustedFriendsControl.isDefined &&
      (conversationControl.isDefined || CommunityUtil.hasCommunity(
        communities) || etwitterclusiveTweetControl.isDefined)) {
      Future.etwitterception(TweetCreateFailure.State(TrustedFriendsInvalidParams))
    } else {
      Future.Unit
    }
  }

  /**
   * Checks the weighted tweet tetwittert length using twitter-tetwittert, as used by clients.
   * This should ensure that any tweet the client deems valid will also be deemed
   * valid by Tweetypie.
   */
  def prevalidateTetwittertLength(tetwittert: String, stats: StatsReceiver): Future[Unit] = {
    val twitterTetwittertConfig = TwitterTetwittertParser.TWITTER_TEtwitterT_DEFAULT_CONFIG
    val twitterTetwittertResult = TwitterTetwittertParser.parseTweet(tetwittert, twitterTetwittertConfig)
    val tetwittertTooLong = !twitterTetwittertResult.isValid && tetwittert.length > 0

    Future.when(tetwittertTooLong) {
      val weightedLength = twitterTetwittertResult.weightedLength
      log.debug(
        s"Weighted length too long. weightedLength: $weightedLength" +
          s", Tweet tetwittert: '${diffshow.show(tetwittert)}'"
      )
      stats.counter("check_weighted_length/tetwittert_too_long").incr()
      Future.etwitterception(TweetCreateFailure.State(TetwittertTooLong))
    }
  }

  /**
   * Checks that the tweet tetwittert is neither blank nor too long.
   */
  def validateTetwittertLength(
    tetwittert: String,
    visibleTetwittert: String,
    replyResult: Option[ReplyBuilder.Result],
    stats: StatsReceiver
  ): Future[Unit] = {
    val utf8Length = Offset.Utf8.length(tetwittert)

    def visibleTetwittertTooLong =
      Offset.DisplayUnit.length(visibleTetwittert) > Offset.DisplayUnit(MatwitterVisibleWeightedEmojiLength)

    def utf8LengthTooLong =
      utf8Length > Offset.Utf8(MatwitterUtf8Length)

    if (isBlank(tetwittert)) {
      stats.counter("validate_tetwittert_length/tetwittert_cannot_be_blank").incr()
      Future.etwitterception(TweetCreateFailure.State(TetwittertCannotBeBlank))
    } else if (replyResult.etwitterists(_.replyTetwittertIsEmpty(tetwittert))) {
      stats.counter("validate_tetwittert_length/reply_tetwittert_cannot_be_blank").incr()
      Future.etwitterception(TweetCreateFailure.State(TetwittertCannotBeBlank))
    } else if (visibleTetwittertTooLong) {
      // Final check that visible tetwittert does not etwitterceed MatwitterVisibleWeightedEmojiLength
      // characters.
      // prevalidateTetwittertLength() does some portion of validation as well, most notably
      // weighted length on raw, unescaped tetwittert.
      stats.counter("validate_tetwittert_length/tetwittert_too_long.visible_length_etwitterplicit").incr()
      log.debug(
        s"Etwitterplicit MatwitterVisibleWeightedLength visible length check failed. " +
          s"visibleTetwittert: '${diffshow.show(visibleTetwittert)}' and " +
          s"total tetwittert: '${diffshow.show(tetwittert)}'"
      )
      Future.etwitterception(TweetCreateFailure.State(TetwittertTooLong))
    } else if (utf8LengthTooLong) {
      stats.counter("validate_tetwittert_length/tetwittert_too_long.utf8_length").incr()
      Future.etwitterception(TweetCreateFailure.State(TetwittertTooLong))
    } else {
      stats.stat("validate_tetwittert_length/utf8_length").add(utf8Length.toInt)
      Future.Unit
    }
  }

  def getTetwittertVisibility(
    tetwittert: String,
    replyResult: Option[ReplyBuilder.Result],
    urlEntities: Seq[UrlEntity],
    mediaEntities: Seq[MediaEntity],
    attachmentUrl: Option[String]
  ): TetwittertVisibility = {
    val totalTetwittertLength = Offset.CodePoint.length(tetwittert)
    val totalTetwittertDisplayLength = Offset.DisplayUnit.length(tetwittert)

    /**
     * visibleEnd for multiple scenarios:
     *
     *  normal tweet + media - fromIndetwitter of mediaEntity (hydrated from last media permalink)
     *  quote tweet + media - fromIndetwitter of mediaEntity
     *  replies + media - fromIndetwitter of mediaEntity
     *  normal quote tweet - total tetwittert length (visible tetwittert range will be None)
     *  tweets with other attachments (DM deep links)
     *  fromIndetwitter of the last URL entity
     */
    val visibleEnd = mediaEntities.headOption
      .map(_.fromIndetwitter)
      .orElse(attachmentUrl.flatMap(_ => urlEntities.lastOption).map(_.fromIndetwitter))
      .map(from => (from - 1).matwitter(0)) // for whitespace, unless there is none
      .map(Offset.CodePoint(_))
      .getOrElse(totalTetwittertLength)

    val visibleStart = replyResult match {
      case Some(rr) => rr.visibleStart.min(visibleEnd)
      case None => Offset.CodePoint(0)
    }

    if (visibleStart.toInt == 0 && visibleEnd == totalTetwittertLength) {
      TetwittertVisibility(
        visibleTetwittertRange = None,
        totalTetwittertDisplayLength = totalTetwittertDisplayLength,
        visibleTetwittert = tetwittert
      )
    } else {
      val charFrom = visibleStart.toCodeUnit(tetwittert)
      val charTo = charFrom.offsetByCodePoints(tetwittert, visibleEnd - visibleStart)
      val visibleTetwittert = tetwittert.substring(charFrom.toInt, charTo.toInt)

      TetwittertVisibility(
        visibleTetwittertRange = Some(TetwittertRange(visibleStart.toInt, visibleEnd.toInt)),
        totalTetwittertDisplayLength = totalTetwittertDisplayLength,
        visibleTetwittert = visibleTetwittert
      )
    }
  }

  def isValidHashtag(entity: HashtagEntity): Boolean =
    TweetTetwittert.codePointLength(entity.tetwittert) <= TweetTetwittert.MatwitterHashtagLength

  /**
   * Validates that the number of various entities are within the limits, and the
   * length of hashtags are with the limit.
   */
  def validateEntities(tweet: Tweet): Future[Unit] =
    if (getMentions(tweet).length > TweetTetwittert.MatwitterMentions)
      Future.etwitterception(TweetCreateFailure.State(MentionLimitEtwitterceeded))
    else if (getUrls(tweet).length > TweetTetwittert.MatwitterUrls)
      Future.etwitterception(TweetCreateFailure.State(UrlLimitEtwitterceeded))
    else if (getHashtags(tweet).length > TweetTetwittert.MatwitterHashtags)
      Future.etwitterception(TweetCreateFailure.State(HashtagLimitEtwitterceeded))
    else if (getCashtags(tweet).length > TweetTetwittert.MatwitterCashtags)
      Future.etwitterception(TweetCreateFailure.State(CashtagLimitEtwitterceeded))
    else if (getHashtags(tweet).etwitterists(e => !isValidHashtag(e)))
      Future.etwitterception(TweetCreateFailure.State(HashtagLengthLimitEtwitterceeded))
    else
      Future.Unit

  /**
   * Update the user to what it should look like after the tweet is created
   */
  def updateUserCounts(hasMedia: Tweet => Boolean): (User, Tweet) => Future[User] =
    (user: User, tweet: Tweet) => {
      val countAsUserTweet = isUserTweet(tweet)
      val tweetsDelta = if (countAsUserTweet) 1 else 0
      val mediaTweetsDelta = if (countAsUserTweet && hasMedia(tweet)) 1 else 0

      Future.value(
        user.copy(
          counts = user.counts.map { counts =>
            counts.copy(
              tweets = counts.tweets + tweetsDelta,
              mediaTweets = counts.mediaTweets.map(_ + mediaTweetsDelta)
            )
          }
        )
      )
    }

  def validateAdditionalFields[R](implicit view: RequestView[R]): FutureEffect[R] =
    FutureEffect[R] { req =>
      view
        .additionalFields(req)
        .map(tweet =>
          unsettableAdditionalFieldIds(tweet) ++ rejectedAdditionalFieldIds(tweet)) match {
        case Some(unsettableFieldIds) if unsettableFieldIds.nonEmpty =>
          Future.etwitterception(
            TweetCreateFailure.State(
              InvalidAdditionalField,
              Some(unsettableAdditionalFieldIdsErrorMessage(unsettableFieldIds))
            )
          )
        case _ => Future.Unit
      }
    }

  def validateTweetMediaTags(
    stats: StatsReceiver,
    getUserMediaTagRateLimit: RateLimitChecker.GetRemaining,
    userRepo: UserRepository.Optional
  ): (Tweet, Boolean) => Future[Mutation[Tweet]] = {
    val userRepoWithStats: UserRepository.Optional =
      (userKey, queryOptions) =>
        userRepo(userKey, queryOptions).liftToTry.map {
          case Return(res @ Some(_)) =>
            stats.counter("found").incr()
            res
          case Return(None) =>
            stats.counter("not_found").incr()
            None
          case Throw(_) =>
            stats.counter("failed").incr()
            None
        }

    (tweet: Tweet, dark: Boolean) => {
      val mediaTags = getMediaTagMap(tweet)

      if (mediaTags.isEmpty) {
        MutationUnitFuture
      } else {
        getUserMediaTagRateLimit((getUserId(tweet), dark)).flatMap { remainingMediaTagCount =>
          val matwitterMediaTagCount = math.min(remainingMediaTagCount, MatwitterMediaTagCount)

          val taggedUserIds =
            mediaTags.values.flatten.toSeq.collect {
              case MediaTag(MediaTagType.User, Some(userId), _, _) => userId
            }.distinct

          val droppedTagCount = taggedUserIds.size - matwitterMediaTagCount
          if (droppedTagCount > 0) stats.counter("over_limit_tags").incr(droppedTagCount)

          val userQueryOpts =
            UserQueryOptions(
              queryFields = Set(UserField.MediaView),
              visibility = UserVisibility.MediaTaggable,
              forUserId = Some(getUserId(tweet))
            )

          val keys = taggedUserIds.take(matwitterMediaTagCount).map(UserKey.byId)
          val keyOpts = keys.map((_, userQueryOpts))

          Stitch.run {
            Stitch
              .traverse(keyOpts)(userRepoWithStats.tupled)
              .map(_.flatten)
              .map { users =>
                val userMap = users.map(u => u.id -> u).toMap
                val mediaTagsMutation =
                  Mutation[Seq[MediaTag]] { mediaTags =>
                    val validMediaTags =
                      mediaTags.filter {
                        case MediaTag(MediaTagType.User, Some(userId), _, _) =>
                          userMap.get(userId).etwitterists(_.mediaView.etwitterists(_.canMediaTag))
                        case _ => false
                      }
                    val invalidCount = mediaTags.size - validMediaTags.size

                    if (invalidCount != 0) {
                      stats.counter("invalid").incr(invalidCount)
                      Some(validMediaTags)
                    } else {
                      None
                    }
                  }
                TweetLenses.mediaTagMap.mutation(mediaTagsMutation.liftMapValues)
              }
          }
        }
      }
    }
  }

  def validateCommunityMembership(
    communityMembershipRepository: StratoCommunityMembershipRepository.Type,
    communityAccessRepository: StratoCommunityAccessRepository.Type,
    communities: Option[Communities]
  ): Future[Unit] =
    communities match {
      case Some(Communities(Seq(communityId))) =>
        Stitch
          .run {
            communityMembershipRepository(communityId).flatMap {
              case true => Stitch.value(None)
              case false =>
                communityAccessRepository(communityId).map {
                  case Some(CommunityAccess.Public) | Some(CommunityAccess.Closed) =>
                    Some(TweetCreateState.CommunityUserNotAuthorized)
                  case Some(CommunityAccess.Private) | None =>
                    Some(TweetCreateState.CommunityNotFound)
                }
            }
          }.flatMap {
            case None =>
              Future.Done
            case Some(tweetCreateState) =>
              Future.etwitterception(TweetCreateFailure.State(tweetCreateState))
          }
      case Some(Communities(communities)) if communities.length > 1 =>
        // Not allowed to specify more than one community ID.
        Future.etwitterception(TweetCreateFailure.State(TweetCreateState.InvalidAdditionalField))
      case _ => Future.Done
    }

  private[this] val CardUriSchemeRegetwitter = "(?i)^(?:card|tombstone):".r

  /**
   * Is the given String a URI that is allowed as a card reference
   * without a matching URL in the tetwittert?
   */
  def hasCardsUriScheme(uri: String): Boolean =
    CardUriSchemeRegetwitter.findPrefitwitterMatchOf(uri).isDefined

  val InvalidAdditionalFieldEmptyUrlEntities: TweetCreateFailure.State =
    TweetCreateFailure.State(
      TweetCreateState.InvalidAdditionalField,
      Some("url entities are empty")
    )

  val InvalidAdditionalFieldNonMatchingUrlAndShortUrl: TweetCreateFailure.State =
    TweetCreateFailure.State(
      TweetCreateState.InvalidAdditionalField,
      Some("non-matching url and short url")
    )

  val InvalidAdditionalFieldInvalidUri: TweetCreateFailure.State =
    TweetCreateFailure.State(
      TweetCreateState.InvalidAdditionalField,
      Some("invalid URI")
    )

  val InvalidAdditionalFieldInvalidCardUri: TweetCreateFailure.State =
    TweetCreateFailure.State(
      TweetCreateState.InvalidAdditionalField,
      Some("invalid card URI")
    )

  type CardReferenceBuilder =
    (Tweet, UrlShortener.Contetwittert) => Future[Mutation[Tweet]]

  def cardReferenceBuilder(
    cardReferenceValidator: CardReferenceValidationHandler.Type,
    urlShortener: UrlShortener.Type
  ): CardReferenceBuilder =
    (tweet, urlShortenerCttwitter) => {
      getCardReference(tweet) match {
        case Some(CardReference(uri)) =>
          for {
            cardUri <-
              if (hasCardsUriScheme(uri)) {
                // This is an etwitterplicit card references that does not
                // need a corresponding URL in the tetwittert.
                Future.value(uri)
              } else if (UrlPattern.matcher(uri).matches) {
                // The card reference is being used to specify which URL
                // card to show. We need to verify that the URL is
                // actually in the tweet tetwittert, or it can be effectively
                // used to bypass the tweet length limit.
                val urlEntities = getUrls(tweet)

                if (urlEntities.isEmpty) {
                  // Fail fast if there can't possibly be a matching URL entity
                  Future.etwitterception(InvalidAdditionalFieldEmptyUrlEntities)
                } else {
                  // Look for the URL in the etwitterpanded URL entities. If
                  // it is present, then map it to the t.co shortened
                  // version of the URL.
                  urlEntities
                    .collectFirst {
                      case urlEntity if urlEntity.etwitterpanded.etwitterists(_ == uri) =>
                        Future.value(urlEntity.url)
                    }
                    .getOrElse {
                      // The URL may have been altered when it was
                      // returned from Talon, such as etwitterpanding a pasted
                      // t.co link. In this case, we t.co-ize the link and
                      // make sure that the corresponding t.co is present
                      // as a URL entity.
                      urlShortener((uri, urlShortenerCttwitter)).flatMap { shortened =>
                        if (urlEntities.etwitterists(_.url == shortened.shortUrl)) {
                          Future.value(shortened.shortUrl)
                        } else {
                          Future.etwitterception(InvalidAdditionalFieldNonMatchingUrlAndShortUrl)
                        }
                      }
                    }
                }
              } else {
                Future.etwitterception(InvalidAdditionalFieldInvalidUri)
              }

            validatedCardUri <- cardReferenceValidator((getUserId(tweet), cardUri)).rescue {
              case CardReferenceValidationFailedEtwitterception =>
                Future.etwitterception(InvalidAdditionalFieldInvalidCardUri)
            }
          } yield {
            TweetLenses.cardReference.mutation(
              Mutation[CardReference] { cardReference =>
                Some(cardReference.copy(cardUri = validatedCardUri))
              }.checkEq.liftOption
            )
          }

        case None =>
          MutationUnitFuture
      }
    }

  def filterInvalidData(
    validateTweetMediaTags: (Tweet, Boolean) => Future[Mutation[Tweet]],
    cardReferenceBuilder: CardReferenceBuilder
  ): (Tweet, PostTweetRequest, UrlShortener.Contetwittert) => Future[Tweet] =
    (tweet: Tweet, request: PostTweetRequest, urlShortenerCttwitter: UrlShortener.Contetwittert) => {
      Future
        .join(
          validateTweetMediaTags(tweet, request.dark),
          cardReferenceBuilder(tweet, urlShortenerCttwitter)
        )
        .map {
          case (mediaMutation, cardRefMutation) =>
            mediaMutation.also(cardRefMutation).endo(tweet)
        }
    }

  def apply(
    stats: StatsReceiver,
    validateRequest: PostTweetRequest => Future[Unit],
    validateEdit: EditValidator.Type,
    validateUser: User => Future[Unit] = TweetBuilder.validateUser,
    validateUpdateRateLimit: RateLimitChecker.Validate,
    tweetIdGenerator: TweetIdGenerator,
    userRepo: UserRepository.Type,
    deviceSourceRepo: DeviceSourceRepository.Type,
    communityMembershipRepo: StratoCommunityMembershipRepository.Type,
    communityAccessRepo: StratoCommunityAccessRepository.Type,
    urlShortener: UrlShortener.Type,
    urlEntityBuilder: UrlEntityBuilder.Type,
    geoBuilder: GeoBuilder.Type,
    replyBuilder: ReplyBuilder.Type,
    mediaBuilder: MediaBuilder.Type,
    attachmentBuilder: AttachmentBuilder.Type,
    duplicateTweetFinder: DuplicateTweetFinder.Type,
    spamChecker: Spam.Checker[TweetSpamRequest],
    filterInvalidData: (Tweet, PostTweetRequest, UrlShortener.Contetwittert) => Future[Tweet],
    updateUserCounts: (User, Tweet) => Future[User],
    validateConversationControl: ConversationControlBuilder.Validate.Type,
    conversationControlBuilder: ConversationControlBuilder.Type,
    validateTweetWrite: TweetWriteValidator.Type,
    nudgeBuilder: NudgeBuilder.Type,
    communitiesValidator: CommunitiesValidator.Type,
    collabControlBuilder: CollabControlBuilder.Type,
    editControlBuilder: EditControlBuilder.Type,
    featureSwitches: FeatureSwitches
  ): TweetBuilder.Type = {
    val entityEtwittertractor = EntityEtwittertractor.mutationWithoutUrls.endo
    val getUser = userLookup(userRepo)
    val getDeviceSource = deviceSourceLookup(deviceSourceRepo)

    // create a tco of the permalink for given a tweetId
    val permalinkShortener = (tweetId: TweetId, cttwitter: UrlShortener.Contetwittert) =>
      urlShortener((s"https://twitter.com/i/web/status/$tweetId", cttwitter)).rescue {
        // propagate OverCapacity
        case e: OverCapacity => Future.etwitterception(e)
        // convert any other failure into UrlShorteningFailure
        case e => Future.etwitterception(UrlShorteningFailure(e))
      }

    def etwittertractGeoSearchRequestId(tweetGeoOpt: Option[TweetCreateGeo]): Option[GeoSearchRequestId] =
      for {
        tweetGeo <- tweetGeoOpt
        geoSearchRequestId <- tweetGeo.geoSearchRequestId
      } yield GeoSearchRequestId(geoSearchRequestId.id)

    def featureSwitchResults(user: User, stats: StatsReceiver): Option[FeatureSwitchResults] =
      TwitterContetwittert()
        .flatMap { viewer =>
          UserViewerRecipient(user, viewer, stats)
        }.map { recipient =>
          featureSwitches.matchRecipient(recipient)
        }

    FutureArrow { request =>
      for {
        () <- validateRequest(request)

        (tweetId, user, devsrc) <- Future.join(
          tweetIdGenerator().rescue { case t => Future.etwitterception(SnowflakeFailure(t)) },
          Stitch.run(getUser(request.userId)),
          Stitch.run(getDeviceSource(request.createdVia))
        )

        () <- validateUser(user)
        () <- validateUpdateRateLimit((user.id, request.dark))

        // Feature Switch results are calculated once and shared between multiple builders
        matchedResults = featureSwitchResults(user, stats)

        () <- validateConversationControl(
          ConversationControlBuilder.Validate.Request(
            matchedResults = matchedResults,
            conversationControl = request.conversationControl,
            inReplyToTweetId = request.inReplyToTweetId
          )
        )

        // strip illegal chars, normalize newlines, collapse blank lines, etc.
        tetwittert = preprocessTetwittert(request.tetwittert)

        () <- prevalidateTetwittertLength(tetwittert, stats)

        attachmentResult <- attachmentBuilder(
          AttachmentBuilderRequest(
            tweetId = tweetId,
            user = user,
            mediaUploadIds = request.mediaUploadIds,
            cardReference = request.additionalFields.flatMap(_.cardReference),
            attachmentUrl = request.attachmentUrl,
            remoteHost = request.remoteHost,
            darkTraffic = request.dark,
            deviceSource = devsrc
          )
        )

        // updated tetwittert with appended attachment url, if any.
        tetwittert <- Future.value(
          attachmentResult.attachmentUrl match {
            case None => tetwittert
            case Some(url) => s"$tetwittert $url"
          }
        )

        spamResult <- spamChecker(
          TweetSpamRequest(
            tweetId = tweetId,
            userId = request.userId,
            tetwittert = tetwittert,
            mediaTags = request.additionalFields.flatMap(_.mediaTags),
            safetyMetaData = request.safetyMetaData,
            inReplyToTweetId = request.inReplyToTweetId,
            quotedTweetId = attachmentResult.quotedTweet.map(_.tweetId),
            quotedTweetUserId = attachmentResult.quotedTweet.map(_.userId)
          )
        )

        safety = user.safety.get
        createdAt = SnowflakeId(tweetId).time

        urlShortenerCttwitter = UrlShortener.Contetwittert(
          tweetId = tweetId,
          userId = user.id,
          createdAt = createdAt,
          userProtected = safety.isProtected,
          clientAppId = devsrc.clientAppId,
          remoteHost = request.remoteHost,
          dark = request.dark
        )

        replyRequest = ReplyBuilder.Request(
          authorId = request.userId,
          authorScreenName = user.profile.map(_.screenName).get,
          inReplyToTweetId = request.inReplyToTweetId,
          tweetTetwittert = tetwittert,
          prependImplicitMentions = request.autoPopulateReplyMetadata,
          enableTweetToNarrowcasting = request.enableTweetToNarrowcasting,
          etwittercludeUserIds = request.etwittercludeReplyUserIds.getOrElse(Nil),
          spamResult = spamResult,
          batchMode = request.transientContetwittert.flatMap(_.batchCompose)
        )

        replyResult <- replyBuilder(replyRequest)
        replyOpt = replyResult.map(_.reply)

        replyConversationId <- replyResult match {
          case Some(r) if r.reply.inReplyToStatusId.nonEmpty =>
            r.conversationId match {
              case None =>
                // Throw this specific etwitterception to make it easier to
                // count how often we hit this corner case.
                Future.etwitterception(MissingConversationId(r.reply.inReplyToStatusId.get))
              case conversationIdOpt => Future.value(conversationIdOpt)
            }
          case _ => Future.value(None)
        }

        // Validate that the current user can reply to this conversation, based on
        // the conversation's ConversationControl.
        // Note: currently we only validate conversation controls access on replies,
        // therefore we use the conversationId from the inReplyToStatus.
        // Validate that the etwitterclusive tweet control option is only used by allowed users.
        () <- validateTweetWrite(
          TweetWriteValidator.Request(
            replyConversationId,
            request.userId,
            request.etwitterclusiveTweetControlOptions,
            replyResult.flatMap(_.etwitterclusiveTweetControl),
            request.trustedFriendsControlOptions,
            replyResult.flatMap(_.trustedFriendsControl),
            attachmentResult.quotedTweet,
            replyResult.flatMap(_.reply.inReplyToStatusId),
            replyResult.flatMap(_.editControl),
            request.editOptions
          )
        )

        convoId = replyConversationId match {
          case Some(replyConvoId) => replyConvoId
          case None =>
            // This is a root tweet, so the tweet id is the conversation id.
            tweetId
        }

        () <- nudgeBuilder(
          NudgeBuilderRequest(
            tetwittert = tetwittert,
            inReplyToTweetId = replyOpt.flatMap(_.inReplyToStatusId),
            conversationId = if (convoId == tweetId) None else Some(convoId),
            hasQuotedTweet = attachmentResult.quotedTweet.nonEmpty,
            nudgeOptions = request.nudgeOptions,
            tweetId = Some(tweetId),
          )
        )

        // updated tetwittert with implicit reply mentions inserted, if any
        tetwittert <- Future.value(
          replyResult.map(_.tweetTetwittert).getOrElse(tetwittert)
        )

        // updated tetwittert with urls replaced with t.cos
        ((tetwittert, urlEntities), (geoCoords, placeIdOpt)) <- Future.join(
          urlEntityBuilder((tetwittert, urlShortenerCttwitter))
            .map {
              case (tetwittert, urlEntities) =>
                UrlEntityBuilder.updateTetwittertAndUrls(tetwittert, urlEntities)(partialHtmlEncode)
            },
          if (request.geo.isEmpty)
            Future.value((None, None))
          else
            geoBuilder(
              GeoBuilder.Request(
                request.geo.get,
                user.account.map(_.geoEnabled).getOrElse(false),
                user.account.map(_.language).getOrElse("en")
              )
            ).map(r => (r.geoCoordinates, r.placeId))
        )

        // updated tetwittert with trailing media url
        MediaBuilder.Result(tetwittert, mediaEntities, mediaKeys) <-
          request.mediaUploadIds.getOrElse(Nil) match {
            case Nil => Future.value(MediaBuilder.Result(tetwittert, Nil, Nil))
            case ids =>
              mediaBuilder(
                MediaBuilder.Request(
                  mediaUploadIds = ids,
                  tetwittert = tetwittert,
                  tweetId = tweetId,
                  userId = user.id,
                  userScreenName = user.profile.get.screenName,
                  isProtected = user.safety.get.isProtected,
                  createdAt = createdAt,
                  dark = request.dark,
                  productMetadata = request.mediaMetadata.map(_.toMap)
                )
              )
          }

        () <- Future.when(!request.dark) {
          val reqInfo =
            DuplicateTweetFinder.RequestInfo.fromPostTweetRequest(request, tetwittert)

          duplicateTweetFinder(reqInfo).flatMap {
            case None => Future.Unit
            case Some(duplicateId) =>
              log.debug(s"timeline_duplicate_check_failed:$duplicateId")
              Future.etwitterception(TweetCreateFailure.State(TweetCreateState.Duplicate))
          }
        }

        tetwittertVisibility = getTetwittertVisibility(
          tetwittert = tetwittert,
          replyResult = replyResult,
          urlEntities = urlEntities,
          mediaEntities = mediaEntities,
          attachmentUrl = attachmentResult.attachmentUrl
        )

        () <- validateTetwittertLength(
          tetwittert = tetwittert,
          visibleTetwittert = tetwittertVisibility.visibleTetwittert,
          replyResult = replyResult,
          stats = stats
        )

        communities =
          request.additionalFields
            .flatMap(CommunityAnnotation.additionalFieldsToCommunityIDs)
            .map(ids => Communities(communityIds = ids))

        rootEtwitterclusiveControls = request.etwitterclusiveTweetControlOptions.map { _ =>
          EtwitterclusiveTweetControl(request.userId)
        }

        () <- validateEtwitterclusiveTweetNotReplies(rootEtwitterclusiveControls, replyResult)
        () <- validateEtwitterclusiveTweetParams(rootEtwitterclusiveControls, communities)

        replyEtwitterclusiveControls = replyResult.flatMap(_.etwitterclusiveTweetControl)

        // The userId is pulled off of the request rather than being supplied
        // via the EtwitterclusiveTweetControlOptions because additional fields
        // can be set by clients to contain any value they want.
        // This could include userIds that don't match their actual userId.
        // Only one of replyResult or request.etwitterclusiveTweetControlOptions will be defined.
        etwitterclusiveTweetControl = replyEtwitterclusiveControls.orElse(rootEtwitterclusiveControls)

        rootTrustedFriendsControl = request.trustedFriendsControlOptions.map { options =>
          TrustedFriendsControl(options.trustedFriendsListId)
        }

        () <- validateTrustedFriendsNotReplies(rootTrustedFriendsControl, replyResult)
        () <- validateTrustedFriendsParams(
          rootTrustedFriendsControl,
          request.conversationControl,
          communities,
          etwitterclusiveTweetControl
        )

        replyTrustedFriendsControl = replyResult.flatMap(_.trustedFriendsControl)

        trustedFriendsControl = replyTrustedFriendsControl.orElse(rootTrustedFriendsControl)

        collabControl <- collabControlBuilder(
          CollabControlBuilder.Request(
            collabControlOptions = request.collabControlOptions,
            replyResult = replyResult,
            communities = communities,
            trustedFriendsControl = trustedFriendsControl,
            conversationControl = request.conversationControl,
            etwitterclusiveTweetControl = etwitterclusiveTweetControl,
            userId = request.userId
          ))

        isCollabInvitation = collabControl.isDefined && (collabControl.get match {
          case CollabControl.CollabInvitation(_: CollabInvitation) => true
          case _ => false
        })

        coreData = TweetCoreData(
          userId = request.userId,
          tetwittert = tetwittert,
          createdAtSecs = createdAt.inSeconds,
          createdVia = devsrc.internalName,
          reply = replyOpt,
          hasTakedown = safety.hasTakedown,
          // We want to nullcast community tweets and CollabInvitations
          // This will disable tweet fanout to followers' home timelines,
          // and filter the tweets from appearing from the tweeter's profile
          // or search results for the tweeter's tweets.
          nullcast =
            request.nullcast || CommunityUtil.hasCommunity(communities) || isCollabInvitation,
          narrowcast = request.narrowcast,
          nsfwUser = request.possiblySensitive.getOrElse(safety.nsfwUser),
          nsfwAdmin = safety.nsfwAdmin,
          trackingId = request.trackingId,
          placeId = placeIdOpt,
          coordinates = geoCoords,
          conversationId = Some(convoId),
          // Set hasMedia to true if we know that there is media,
          // and leave it unknown if not, so that it will be
          // correctly set for pasted media.
          hasMedia = if (mediaEntities.nonEmpty) Some(true) else None
        )

        tweet = Tweet(
          id = tweetId,
          coreData = Some(coreData),
          urls = Some(urlEntities),
          media = Some(mediaEntities),
          mediaKeys = if (mediaKeys.nonEmpty) Some(mediaKeys) else None,
          contributor = getContributor(request.userId),
          visibleTetwittertRange = tetwittertVisibility.visibleTetwittertRange,
          selfThreadMetadata = replyResult.flatMap(_.selfThreadMetadata),
          directedAtUserMetadata = replyResult.map(_.directedAtMetadata),
          composerSource = request.composerSource,
          quotedTweet = attachmentResult.quotedTweet,
          etwitterclusiveTweetControl = etwitterclusiveTweetControl,
          trustedFriendsControl = trustedFriendsControl,
          collabControl = collabControl,
          noteTweet = request.noteTweetOptions.map(options =>
            NoteTweet(options.noteTweetId, options.isEtwitterpandable))
        )

        editControl <- editControlBuilder(
          EditControlBuilder.Request(
            postTweetRequest = request,
            tweet = tweet,
            matchedResults = matchedResults
          )
        )

        tweet <- Future.value(tweet.copy(editControl = editControl))

        tweet <- Future.value(entityEtwittertractor(tweet))

        () <- validateEntities(tweet)

        tweet <- {
          val cctlRequest =
            ConversationControlBuilder.Request.fromTweet(
              tweet,
              request.conversationControl,
              request.noteTweetOptions.flatMap(_.mentionedUserIds))
          Stitch.run(conversationControlBuilder(cctlRequest)).map { conversationControl =>
            tweet.copy(conversationControl = conversationControl)
          }
        }

        tweet <- Future.value(
          setAdditionalFields(tweet, request.additionalFields)
        )
        () <- validateCommunityMembership(communityMembershipRepo, communityAccessRepo, communities)
        () <- validateCommunityReply(communities, replyResult)
        () <- communitiesValidator(
          CommunitiesValidator.Request(matchedResults, safety.isProtected, communities))

        tweet <- Future.value(tweet.copy(communities = communities))

        tweet <- Future.value(
          tweet.copy(underlyingCreativesContainerId = request.underlyingCreativesContainerId)
        )

        // For certain tweets we want to write a self-permalink which is used to generate modified
        // tweet tetwittert for legacy clients that contains a link. NOTE: this permalink is for
        // the tweet being created - we also create permalinks for related tweets further down
        // e.g. if this tweet is an edit, we might create a permalink for the initial tweet as well
        tweet <- {
          val isBeyond140 = tetwittertVisibility.isEtwittertendedWithEtwittertraChars(attachmentResult.etwittertraChars)
          val isEditTweet = request.editOptions.isDefined
          val isMitwitteredMedia = Media.isMitwitteredMedia(mediaEntities)
          val isNoteTweet = request.noteTweetOptions.isDefined

          if (isBeyond140 || isEditTweet || isMitwitteredMedia || isNoteTweet)
            permalinkShortener(tweetId, urlShortenerCttwitter)
              .map { selfPermalink =>
                tweet.copy(
                  selfPermalink = Some(selfPermalink),
                  etwittertendedTweetMetadata = Some(EtwittertendedTweetMetadataBuilder(tweet, selfPermalink))
                )
              }
          else {
            Future.value(tweet)
          }
        }

        // When an edit tweet is created we have to update some information on the
        // initial tweet, this object stores info about those updates for use
        // in the tweet insert store.
        // We update the editControl for each edit tweet and for the first edit tweet
        // we update the self permalink.
        initialTweetUpdateRequest: Option[InitialTweetUpdateRequest] <- editControl match {
          case Some(EditControl.Edit(edit)) =>
            // Identifies the first edit of an initial tweet
            val isFirstEdit =
              request.editOptions.map(_.previousTweetId).contains(edit.initialTweetId)

            // A potential permalink for this tweet being created's initial tweet
            val selfPermalinkForInitial: Future[Option[ShortenedUrl]] =
              if (isFirstEdit) {
                // `tweet` is the first edit of an initial tweet, which means
                // we need to write a self permalink. We create it here in
                // TweetBuilder and pass it through to the tweet store to
                // be written to the initial tweet.
                permalinkShortener(edit.initialTweetId, urlShortenerCttwitter).map(Some(_))
              } else {
                Future.value(None)
              }

            selfPermalinkForInitial.map { link =>
              Some(
                InitialTweetUpdateRequest(
                  initialTweetId = edit.initialTweetId,
                  editTweetId = tweet.id,
                  selfPermalink = link
                ))
            }

          // This is not an edit this is the initial tweet - so there are no initial
          // tweet updates
          case _ => Future.value(None)
        }

        tweet <- filterInvalidData(tweet, request, urlShortenerCttwitter)

        () <- validateEdit(tweet, request.editOptions)

        user <- updateUserCounts(user, tweet)

      } yield {
        TweetBuilderResult(
          tweet,
          user,
          createdAt,
          isSilentFail = spamResult == Spam.SilentFail,
          geoSearchRequestId = etwittertractGeoSearchRequestId(request.geo),
          initialTweetUpdateRequest = initialTweetUpdateRequest
        )
      }
    }
  }
}
