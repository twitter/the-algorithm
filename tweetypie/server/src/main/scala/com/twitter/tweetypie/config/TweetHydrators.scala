package com.twitter.tweetypie
package config

import com.twitter.featureswitches.v2.FeatureSwitches
import com.twitter.servo.cache.Cached
import com.twitter.servo.cache.LockingCache
import com.twitter.servo.util.ExceptionCategorizer
import com.twitter.servo.util.ExceptionCounter
import com.twitter.servo.util.FutureEffect
import com.twitter.servo.util.Scribe
import com.twitter.stitch.NotFound
import com.twitter.tweetypie.core.FilteredState
import com.twitter.tweetypie.core.TweetData
import com.twitter.tweetypie.core.ValueState
import com.twitter.tweetypie.hydrator._
import com.twitter.tweetypie.repository.TweetQuery
import com.twitter.tweetypie.serverutil.{ExceptionCounter => TpExceptionCounter}
import com.twitter.tweetypie.thriftscala._
import com.twitter.tweetypie.client_id.ClientIdHelper

trait TweetHydrators {

  /**
   * Hydrator that has all the Tweet hydrators (entire "pipeline") configured
   * and wired up.
   * This hydrator is used both on the read and write path and is
   * customized by different TweetQuery.Options.
   * Modifications are not automatically written back to cache.
   * `cacheChanges` must be used for that.
   */
  def hydrator: TweetDataValueHydrator

  /**
   * The `Effect` to use to write modified tweets back to cache.
   */
  def cacheChangesEffect: Effect[ValueState[TweetData]]
}

object TweetHydrators {

  /**
   * Creates all the hydrators and calls TweetHydration to wire them up.
   */
  def apply(
    stats: StatsReceiver,
    deciderGates: TweetypieDeciderGates,
    repos: LogicalRepositories,
    tweetDataCache: LockingCache[TweetId, Cached[TweetData]],
    hasMedia: Tweet => Boolean,
    featureSwitchesWithoutExperiments: FeatureSwitches,
    clientIdHelper: ClientIdHelper
  ): TweetHydrators = {
    import repos._

    val repairStats = stats.scope("repairs")
    val hydratorStats = stats.scope("hydrators")

    def scoped[A](stats: StatsReceiver, name: String)(f: StatsReceiver => A): A = {
      val scopedStats = stats.scope(name)
      f(scopedStats)
    }

    val isFailureException: Throwable => Boolean = {
      case _: FilteredState => false
      case NotFound => false
      case _ => true
    }

    def hydratorExceptionCategorizer(failureScope: String) =
      ExceptionCategorizer.const("filtered").onlyIf(_.isInstanceOf[FilteredState]) ++
        ExceptionCategorizer.const("not_found").onlyIf(_ == NotFound) ++
        TpExceptionCounter.defaultCategorizer(failureScope).onlyIf(isFailureException)

    val hydratorExceptionCounter: (StatsReceiver, String) => ExceptionCounter =
      (stats, scope) => TpExceptionCounter(stats, hydratorExceptionCategorizer(scope))

    val tweetHydrator =
      TweetHydration(
        hydratorStats = hydratorStats,
        hydrateFeatureSwitchResults =
          FeatureSwitchResultsHydrator(featureSwitchesWithoutExperiments, clientIdHelper),
        hydrateMentions = MentionEntitiesHydrator
          .once(MentionEntityHydrator(userIdentityRepo))
          .observe(hydratorStats.scope("mentions"), hydratorExceptionCounter),
        hydrateLanguage = LanguageHydrator(languageRepo)
          .observe(hydratorStats.scope("language"), hydratorExceptionCounter),
        hydrateUrls = scoped(hydratorStats, "url") { stats =>
          UrlEntitiesHydrator
            .once(UrlEntityHydrator(urlRepo, stats))
            .observe(stats, hydratorExceptionCounter)
        },
        hydrateQuotedTweetRef = QuotedTweetRefHydrator
          .once(
            QuotedTweetRefHydrator(tweetRepo)
          )
          .observe(hydratorStats.scope("quoted_tweet_ref"), hydratorExceptionCounter),
        hydrateQuotedTweetRefUrls = QuotedTweetRefUrlsHydrator(userIdentityRepo)
          .observe(hydratorStats.scope("quoted_tweet_ref_urls"), hydratorExceptionCounter),
        hydrateMediaCacheable = MediaEntitiesHydrator.Cacheable
          .once(
            MediaEntityHydrator.Cacheable(
              hydrateMediaUrls = MediaUrlFieldsHydrator()
                .observe(hydratorStats.scope("media_urls"), hydratorExceptionCounter),
              hydrateMediaIsProtected = MediaIsProtectedHydrator(userProtectionRepo)
                .observe(hydratorStats.scope("media_is_protected"), hydratorExceptionCounter)
            )
          )
          .observe(hydratorStats.scope("media_cacheable"), hydratorExceptionCounter)
          .ifEnabled(deciderGates.hydrateMedia),
        hydrateReplyScreenName = ReplyScreenNameHydrator
          .once(ReplyScreenNameHydrator(userIdentityRepo))
          .observe(hydratorStats.scope("in_reply_to_screen_name"), hydratorExceptionCounter),
        hydrateConvoId = ConversationIdHydrator(conversationIdRepo)
          .observe(hydratorStats.scope("conversation_id"), hydratorExceptionCounter),
        hydratePerspective = // Don't cache with the tweet because it depends on the request
          PerspectiveHydrator(
            repo = perspectiveRepo,
            shouldHydrateBookmarksPerspective = deciderGates.hydrateBookmarksPerspective,
            stats = hydratorStats.scope("perspective_by_safety_label")
          ).observe(hydratorStats.scope("perspective"), hydratorExceptionCounter)
            .ifEnabled(deciderGates.hydratePerspectives),
        hydrateEditPerspective = EditPerspectiveHydrator(
          repo = perspectiveRepo,
          timelinesGate = deciderGates.hydratePerspectivesEditsForTimelines,
          tweetDetailsGate = deciderGates.hydratePerspectivesEditsForTweetDetail,
          otherSafetyLevelsGate = deciderGates.hydratePerspectivesEditsForOtherSafetyLevels,
          bookmarksGate = deciderGates.hydrateBookmarksPerspective,
          stats = hydratorStats
        ).observe(hydratorStats.scope("edit_perspective"), hydratorExceptionCounter),
        hydrateConversationMuted = // Don't cache because it depends on the request.  If
          // possible, this hydrator should be in the same stage as
          // PerspectiveHydrator, so that the calls can be batched
          // together.
          ConversationMutedHydrator(conversationMutedRepo)
            .observe(hydratorStats.scope("conversation_muted"), hydratorExceptionCounter)
            .ifEnabled(deciderGates.hydrateConversationMuted),
        hydrateContributor = ContributorHydrator
          .once(ContributorHydrator(userIdentityRepo))
          .observe(hydratorStats.scope("contributors"), hydratorExceptionCounter),
        hydrateTakedowns = TakedownHydrator(takedownRepo)
          .observe(hydratorStats.scope("takedowns"), hydratorExceptionCounter),
        hydrateDirectedAt = scoped(hydratorStats, "directed_at") { stats =>
          DirectedAtHydrator
            .once(DirectedAtHydrator(userIdentityRepo, stats))
            .observe(stats, hydratorExceptionCounter)
        },
        hydrateGeoScrub = GeoScrubHydrator(
          geoScrubTimestampRepo,
          Scribe("test_tweetypie_read_time_geo_scrubs")
            .contramap[TweetId](_.toString)
        ).observe(hydratorStats.scope("geo_scrub"), hydratorExceptionCounter),
        hydrateCacheableRepairs = ValueHydrator
          .fromMutation[Tweet, TweetQuery.Options](
            RepairMutation(
              repairStats.scope("on_read"),
              "created_at" ->
                new CreatedAtRepairer(Scribe("test_tweetypie_bad_created_at")),
              "retweet_media" -> RetweetMediaRepairer,
              "parent_status_id" -> RetweetParentStatusIdRepairer.tweetMutation,
              "visible_text_range" -> NegativeVisibleTextRangeRepairer.tweetMutation
            )
          )
          .lensed(TweetData.Lenses.tweet)
          .onlyIf((td, opts) => opts.cause.reading(td.tweet.id)),
        hydrateMediaUncacheable = MediaEntityHydrator
          .Uncacheable(
            hydrateMediaKey = MediaKeyHydrator()
              .observe(hydratorStats.scope("media_key"), hydratorExceptionCounter),
            hydrateMediaInfo = scoped(hydratorStats, "media_info") { stats =>
              MediaInfoHydrator(mediaMetadataRepo, stats)
                .observe(stats, hydratorExceptionCounter)
            }
          )
          .observe(hydratorStats.scope("media_uncacheable"), hydratorExceptionCounter)
          .liftSeq
          .ifEnabled(deciderGates.hydrateMedia),
        hydratePostCacheRepairs =
          // clean-up partially hydrated entities before any of the hydrators that look at
          // url and media entities run, so that they never see bad entities.
          ValueHydrator.fromMutation[TweetData, TweetQuery.Options](
            RepairMutation(
              repairStats.scope("on_read"),
              "partial_entity_cleanup" -> PartialEntityCleaner(repairStats),
              "strip_not_display_coords" -> StripHiddenGeoCoordinates
            ).lensed(TweetData.Lenses.tweet)
          ),
        hydrateTweetLegacyFormat = scoped(hydratorStats, "tweet_legacy_formatter") { stats =>
          TweetLegacyFormatter(stats)
            .observe(stats, hydratorExceptionCounter)
            .onlyIf((td, opts) => opts.cause.reading(td.tweet.id))
        },
        hydrateQuoteTweetVisibility = QuoteTweetVisibilityHydrator(quotedTweetVisibilityRepo)
          .observe(hydratorStats.scope("quote_tweet_visibility"), hydratorExceptionCounter),
        hydrateQuotedTweet = QuotedTweetHydrator(tweetResultRepo)
          .observe(hydratorStats.scope("quoted_tweet"), hydratorExceptionCounter),
        hydratePastedMedia =
          // Don't cache with the tweet because we want to automatically drop this media if
          // the referenced tweet is deleted or becomes non-public.
          PastedMediaHydrator(pastedMediaRepo)
            .observe(hydratorStats.scope("pasted_media"))
            .ifEnabled(deciderGates.hydratePastedMedia),
        hydrateMediaRefs = MediaRefsHydrator(
          optionalTweetRepo,
          deciderGates.mediaRefsHydratorIncludePastedMedia
        ).observe(hydratorStats.scope("media_refs"))
          .ifEnabled(deciderGates.hydrateMediaRefs),
        hydrateMediaTags = // depends on AdditionalFieldsHydrator
          MediaTagsHydrator(userViewRepo)
            .observe(hydratorStats.scope("media_tags"), hydratorExceptionCounter)
            .ifEnabled(deciderGates.hydrateMediaTags),
        hydrateClassicCards = CardHydrator(cardRepo)
          .observe(hydratorStats.scope("cards"), hydratorExceptionCounter),
        hydrateCard2 = Card2Hydrator(card2Repo)
          .observe(hydratorStats.scope("card2")),
        hydrateContributorVisibility =
          // Filter out contributors field for all but the user who owns the tweet
          ContributorVisibilityFilter()
            .observe(hydratorStats.scope("contributor_visibility"), hydratorExceptionCounter),
        hydrateHasMedia =
          // Sets hasMedia. Comes after PastedMediaHydrator in order to include pasted
          // pics as well as other media & urls.
          HasMediaHydrator(hasMedia)
            .observe(hydratorStats.scope("has_media"), hydratorExceptionCounter)
            .ifEnabled(deciderGates.hydrateHasMedia),
        hydrateTweetCounts = // Don't cache counts with the tweet because it has its own cache with
          // a different TTL
          TweetCountsHydrator(tweetCountsRepo, deciderGates.hydrateBookmarksCount)
            .observe(hydratorStats.scope("tweet_counts"), hydratorExceptionCounter)
            .ifEnabled(deciderGates.hydrateCounts),
        hydratePreviousTweetCounts = // previous counts are not cached
          scoped(hydratorStats, "previous_counts") { stats =>
            PreviousTweetCountsHydrator(tweetCountsRepo, deciderGates.hydrateBookmarksCount)
              .observe(stats, hydratorExceptionCounter)
              .ifEnabled(deciderGates.hydratePreviousCounts)
          },
        hydratePlace =
          // Don't cache with the tweet because Place has its own tweetypie cache keyspace
          // with a different TTL, and it's more efficient to store separately.
          // See com.twitter.tweetypie.repository.PlaceKey
          PlaceHydrator(placeRepo)
            .observe(hydratorStats.scope("place"), hydratorExceptionCounter)
            .ifEnabled(deciderGates.hydratePlaces),
        hydrateDeviceSource = // Don't cache with the tweet because it has its own cache,
          // and it's more efficient to cache it separately
          DeviceSourceHydrator(deviceSourceRepo)
            .observe(hydratorStats.scope("device_source"), hydratorExceptionCounter)
            .ifEnabled(deciderGates.hydrateDeviceSources),
        hydrateProfileGeo =
          // Don't cache gnip profile geo as read request volume is expected to be low
          ProfileGeoHydrator(profileGeoRepo)
            .observe(hydratorStats.scope("profile_geo"), hydratorExceptionCounter)
            .ifEnabled(deciderGates.hydrateGnipProfileGeoEnrichment),
        hydrateSourceTweet = scoped(hydratorStats, "source_tweet") { stats =>
          SourceTweetHydrator(
            tweetResultRepo,
            stats,
            FutureEffect
              .inParallel(
                Scribe(DetachedRetweet, "tweetypie_detached_retweets"),
                Scribe(DetachedRetweet, "test_tweetypie_detached_retweets"),
              )
          ).observe(stats, hydratorExceptionCounter)
        },
        hydrateIM1837State = IM1837FilterHydrator()
          .observe(hydratorStats.scope("im1837_filter"), hydratorExceptionCounter)
          .onlyIf { (_, ctx) =>
            ctx.opts.forExternalConsumption && ctx.opts.cause.reading(ctx.tweetId)
          },
        hydrateIM2884State = scoped(hydratorStats, "im2884_filter") { stats =>
          IM2884FilterHydrator(stats)
            .observe(stats, hydratorExceptionCounter)
            .onlyIf { (_, ctx) =>
              ctx.opts.forExternalConsumption && ctx.opts.cause.reading(ctx.tweetId)
            }
        },
        hydrateIM3433State = scoped(hydratorStats, "im3433_filter") { stats =>
          IM3433FilterHydrator(stats)
            .observe(stats, hydratorExceptionCounter)
            .onlyIf { (_, ctx) =>
              ctx.opts.forExternalConsumption && ctx.opts.cause.reading(ctx.tweetId)
            }
        },
        hydrateTweetAuthorVisibility = TweetAuthorVisibilityHydrator(userVisibilityRepo)
          .observe(hydratorStats.scope("tweet_author_visibility"), hydratorExceptionCounter)
          .onlyIf((_, ctx) => ctx.opts.cause.reading(ctx.tweetId)),
        hydrateReportedTweetVisibility = ReportedTweetFilter()
          .observe(hydratorStats.scope("reported_tweet_filter"), hydratorExceptionCounter),
        scrubSuperfluousUrlEntities = ValueHydrator
          .fromMutation[Tweet, TweetQuery.Options](SuperfluousUrlEntityScrubber.mutation)
          .lensed(TweetData.Lenses.tweet),
        copyFromSourceTweet = CopyFromSourceTweet.hydrator
          .observe(hydratorStats.scope("copy_from_source_tweet"), hydratorExceptionCounter),
        hydrateTweetVisibility = scoped(hydratorStats, "tweet_visibility") { stats =>
          TweetVisibilityHydrator(
            tweetVisibilityRepo,
            deciderGates.failClosedInVF,
            stats
          ).observe(stats, hydratorExceptionCounter)
        },
        hydrateEscherbirdAnnotations = EscherbirdAnnotationHydrator(escherbirdAnnotationRepo)
          .observe(hydratorStats.scope("escherbird_annotations"), hydratorExceptionCounter)
          .ifEnabled(deciderGates.hydrateEscherbirdAnnotations),
        hydrateScrubEngagements = ScrubEngagementHydrator()
          .observe(hydratorStats.scope("scrub_engagements"), hydratorExceptionCounter)
          .ifEnabled(deciderGates.hydrateScrubEngagements),
        hydrateConversationControl = scoped(hydratorStats, "tweet_conversation_control") { stats =>
          ConversationControlHydrator(
            conversationControlRepo,
            deciderGates.disableInviteViaMention,
            stats
          ).observe(stats, hydratorExceptionCounter)
        },
        hydrateEditControl = scoped(hydratorStats, "tweet_edit_control") { stats =>
          EditControlHydrator(
            tweetRepo,
            deciderGates.setEditTimeWindowToSixtyMinutes,
            stats
          ).observe(stats, hydratorExceptionCounter)
        },
        hydrateUnmentionData = UnmentionDataHydrator(),
        hydrateNoteTweetSuffix = NoteTweetSuffixHydrator().observe(stats, hydratorExceptionCounter)
      )

    new TweetHydrators {
      val hydrator: TweetDataValueHydrator =
        tweetHydrator.onlyIf { (tweetData, opts) =>
          // When the caller requests fetchStoredTweets and Tweets are fetched from Manhattan
          // irrespective of state, the stored data for some Tweets may be incomplete.
          // We skip the hydration of those Tweets.
          !opts.fetchStoredTweets ||
          tweetData.storedTweetResult.exists(_.canHydrate)
        }

      val cacheChangesEffect: Effect[ValueState[TweetData]] =
        TweetHydration.cacheChanges(
          tweetDataCache,
          hydratorStats.scope("tweet_caching")
        )
    }
  }
}
