package com.twitter.frigate.pushservice.predicate

import com.twitter.abuse.detection.scoring.thriftscala.TweetScoringRequest
import com.twitter.abuse.detection.scoring.thriftscala.TweetScoringResponse
import com.twitter.abuse.detection.scoring.thriftscala.{Model => TweetHealthModel}
import com.twitter.finagle.stats.Counter
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.common.base._
import com.twitter.frigate.common.rec_types.RecTypes
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.pushservice.params.NsfwTextDetectionModel
import com.twitter.frigate.pushservice.params.PushConstants
import com.twitter.frigate.pushservice.params.PushFeatureSwitchParams
import com.twitter.frigate.pushservice.util.CandidateHydrationUtil
import com.twitter.frigate.pushservice.util.CandidateUtil
import com.twitter.frigate.pushservice.util.MediaAnnotationsUtil
import com.twitter.frigate.thriftscala.UserMediaRepresentation
import com.twitter.hermit.predicate.NamedPredicate
import com.twitter.hermit.predicate.Predicate
import com.twitter.hss.api.thriftscala.UserHealthSignal._
import com.twitter.hss.api.thriftscala.SignalValue
import com.twitter.hss.api.thriftscala.UserHealthSignalResponse
import com.twitter.storehaus.ReadableStore
import com.twitter.util.Future
import com.twitter.util.Time

object HealthPredicates {

  private val NsfwTextDetectionModelMap: Map[NsfwTextDetectionModel.Value, TweetHealthModel] =
    Map(
      NsfwTextDetectionModel.ProdModel -> TweetHealthModel.PnsfwTweetText,
      NsfwTextDetectionModel.RetrainedModel -> TweetHealthModel.ExperimentalHealthModelScore1,
    )

  private def tweetIsSupportedLanguage(
    candidate: PushCandidate,
    supportedLanguages: Set[String]
  ): Boolean = {
    val tweetLanguage =
      candidate.categoricalFeatures.getOrElse("RecTweet.TweetyPieResult.Language", "")
    supportedLanguages.contains(tweetLanguage)
  }

  def tweetHealthSignalScorePredicate(
    tweetHealthScoreStore: ReadableStore[TweetScoringRequest, TweetScoringResponse],
    applyToQuoteTweet: Boolean = false
  )(
    implicit stats: StatsReceiver
  ): NamedPredicate[PushCandidate with TweetCandidate with TweetDetails] = {
    val name = "tweet_health_signal_store_applyToQuoteTweet_" + applyToQuoteTweet.toString
    val scopedStatsReceiver = stats.scope(name)
    val numCandidatesStats = scopedStatsReceiver.scope("num_candidates")
    val numCandidatesMediaNsfwScoreStats = numCandidatesStats.scope("media_nsfw_score")

    Predicate
      .fromAsync { candidate: PushCandidate with TweetCandidate with TweetDetails =>
        numCandidatesStats.counter("all").incr()
        val target = candidate.target
        val tweetIdOpt = if (!applyToQuoteTweet) {
          Some(candidate.tweetId)
        } else candidate.tweetyPieResult.flatMap(_.quotedTweet.map(_.id))

        tweetIdOpt match {
          case Some(tweetId) =>
            val pMediaNsfwRequest =
              TweetScoringRequest(tweetId, TweetHealthModel.ExperimentalHealthModelScore4)
            tweetHealthScoreStore.get(pMediaNsfwRequest).map {
              case Some(tweetScoringResponse) =>
                numCandidatesMediaNsfwScoreStats.counter("non_empty").incr()
                val pMediaNsfwScore = tweetScoringResponse.score

                if (!applyToQuoteTweet) {
                  candidate
                    .cacheExternalScore("NsfwMediaProbability", Future.value(Some(pMediaNsfwScore)))
                }

                val pMediaNsfwShouldBucket =
                  pMediaNsfwScore > target.params(
                    PushFeatureSwitchParams.PnsfwTweetMediaBucketingThreshold)
                if (CandidateUtil.shouldApplyHealthQualityFilters(
                    candidate) && pMediaNsfwShouldBucket) {
                  numCandidatesMediaNsfwScoreStats.counter("bucketed").incr()
                  if (target.params(PushFeatureSwitchParams.PnsfwTweetMediaFilterOonOnly)
                    && !RecTypes.isOutOfNetworkTweetRecType(candidate.commonRecType)) {
                    true
                  } else {
                    val pMediaNsfwScoreThreshold =
                      if (applyToQuoteTweet)
                        target.params(PushFeatureSwitchParams.PnsfwQuoteTweetThreshold)
                      else if (candidate.hasPhoto)
                        target.params(PushFeatureSwitchParams.PnsfwTweetImageThreshold)
                      else target.params(PushFeatureSwitchParams.PnsfwTweetMediaThreshold)
                    candidate.cachePredicateInfo(
                      name + "_nsfwMedia",
                      pMediaNsfwScore,
                      pMediaNsfwScoreThreshold,
                      pMediaNsfwScore > pMediaNsfwScoreThreshold)
                    if (pMediaNsfwScore > pMediaNsfwScoreThreshold) {
                      numCandidatesMediaNsfwScoreStats.counter("filtered").incr()
                      false
                    } else true
                  }
                } else true
              case _ =>
                numCandidatesMediaNsfwScoreStats.counter("empty").incr()
                if (candidate.hasPhoto || candidate.hasVideo) {
                  numCandidatesMediaNsfwScoreStats.counter("media_tweet_with_empty_score").incr()
                }
                true
            }
          case _ => Future.True
        }
      }
      .withStats(stats.scope(s"predicate_$name"))
      .withName(name)
  }

  def healthSignalScoreSpammyTweetPredicate(
    tweetHealthScoreStore: ReadableStore[TweetScoringRequest, TweetScoringResponse]
  )(
    implicit stats: StatsReceiver
  ): NamedPredicate[PushCandidate with TweetCandidate with TweetDetails] = {
    val name = "health_signal_store_spammy_tweet"
    val statsScope = stats.scope(name)
    val allCandidatesCounter = statsScope.counter("all_candidates")
    val eligibleCandidatesCounter = statsScope.counter("eligible_candidates")
    val oonCandidatesCounter = statsScope.counter("oon_candidates")
    val inCandidatesCounter = statsScope.counter("in_candidates")
    val bucketedCandidatesCounter = statsScope.counter("num_bucketed")
    val nonEmptySpamScoreCounter = statsScope.counter("non_empty_spam_score")
    val filteredOonCandidatesCounter = statsScope.counter("num_filtered_oon")
    val filteredInCandidatesCounter = statsScope.counter("num_filtered_in")

    Predicate
      .fromAsync { candidate: PushCandidate with TweetCandidate with TweetDetails =>
        allCandidatesCounter.incr()
        val crt = candidate.commonRecType
        val isOonCandidate = RecTypes.isOutOfNetworkTweetRecType(crt) ||
          RecTypes.outOfNetworkTopicTweetTypes.contains(crt)
        if (isOonCandidate) {
          oonCandidatesCounter.incr()
        }
        val target = candidate.target
        if (target.params(PushFeatureSwitchParams.EnableSpammyTweetFilter)) {
          eligibleCandidatesCounter.incr()
          val tweetSpamScore =
            TweetScoringRequest(candidate.tweetId, TweetHealthModel.SpammyTweetContent)
          tweetHealthScoreStore.get(tweetSpamScore).map {
            case (Some(tweetScoringResponse)) =>
              nonEmptySpamScoreCounter.incr()
              val candidateSpamScore = tweetScoringResponse.score

              candidate
                .cacheExternalScore("SpammyTweetScore", Future.value(Some(candidateSpamScore)))

              val tweetSpamShouldBucket =
                candidateSpamScore > target.params(
                  PushFeatureSwitchParams.SpammyTweetBucketingThreshold)
              if (CandidateUtil.shouldApplyHealthQualityFilters(
                  candidate) && tweetSpamShouldBucket) {
                bucketedCandidatesCounter.incr()
                if (isOonCandidate) {
                  val spamScoreThreshold =
                    target.params(PushFeatureSwitchParams.SpammyTweetOonThreshold)
                  if (candidateSpamScore > spamScoreThreshold) {
                    filteredOonCandidatesCounter.incr()
                    false
                  } else true
                } else {
                  inCandidatesCounter.incr()
                  val spamScoreThreshold =
                    target.params(PushFeatureSwitchParams.SpammyTweetInThreshold)
                  if (candidateSpamScore > spamScoreThreshold) {
                    filteredInCandidatesCounter.incr()
                    false
                  } else true
                }
              } else true
            case _ => true
          }
        } else Future.True
      }
      .withStats(stats.scope(s"predicate_$name"))
      .withName(name)
  }

  def healthSignalScorePnsfwTweetTextPredicate(
    tweetHealthScoreStore: ReadableStore[TweetScoringRequest, TweetScoringResponse]
  )(
    implicit stats: StatsReceiver
  ): NamedPredicate[PushCandidate with TweetCandidate] = {
    val name = "health_signal_store_pnsfw_tweet_text"
    val statsScope = stats.scope(name)
    val allCandidatesCounter = statsScope.counter("all_candidates")
    val nonEmptyNsfwTextScoreNum = statsScope.counter("non_empty_nsfw_text_score")
    val filteredCounter = statsScope.counter("num_filtered")
    val lowScoreCounter = statsScope.counter("low_score_count")

    Predicate
      .fromAsync { candidate: PushCandidate with TweetCandidate =>
        val target = candidate.target
        val predEnabled =
          target.params(PushFeatureSwitchParams.EnableHealthSignalStorePnsfwTweetTextPredicate)
        if (CandidateUtil.shouldApplyHealthQualityFilters(
            candidate) && predEnabled && tweetIsSupportedLanguage(candidate, Set(""))) {
          allCandidatesCounter.incr()
          val pnsfwTextRequest =
            TweetScoringRequest(candidate.tweetId, TweetHealthModel.PnsfwTweetText)
          tweetHealthScoreStore.get(pnsfwTextRequest).flatMap {
            case Some(tweetScoringResponse) => {
              nonEmptyNsfwTextScoreNum.incr()
              if (tweetScoringResponse.score < 1e-8) {
                lowScoreCounter.incr()
              }

              candidate
                .cacheExternalScore(
                  "NsfwTextProbability-en",
                  Future.value(Some(tweetScoringResponse.score)))
              val threshold = target.params(PushFeatureSwitchParams.PnsfwTweetTextThreshold)
              candidate.cachePredicateInfo(
                name,
                tweetScoringResponse.score,
                threshold,
                tweetScoringResponse.score > threshold)
              if (tweetScoringResponse.score > threshold) {
                filteredCounter.incr()
                Future.False
              } else Future.True
            }
            case _ => Future.True
          }
        } else Future.True
      }
      .withStats(stats.scope(s"predicate_$name"))
      .withName(name)
  }

  def healthSignalScoreMultilingualPnsfwTweetTextPredicate(
    tweetHealthScoreStore: ReadableStore[TweetScoringRequest, TweetScoringResponse]
  )(
    implicit stats: StatsReceiver
  ): NamedPredicate[PushCandidate with TweetCandidate] = {
    val name = "health_signal_store_multilingual_pnsfw_tweet_text"
    val statsScope = stats.scope(name)

    val allLanguagesIdentifier = "all"
    val languagesSelectedForStats =
      Set("") + allLanguagesIdentifier

    val candidatesCounterMap: Map[String, Counter] = languagesSelectedForStats.map { lang =>
      lang -> statsScope.counter(f"candidates_$lang")
    }.toMap
    val nonEmptyHealthScoreMap: Map[String, Counter] = languagesSelectedForStats.map { lang =>
      lang -> statsScope.counter(f"non_empty_health_score_$lang")
    }.toMap
    val emptyHealthScoreMap: Map[String, Counter] = languagesSelectedForStats.map { lang =>
      lang -> statsScope.counter(f"empty_health_score_$lang")
    }.toMap
    val bucketedCounterMap: Map[String, Counter] = languagesSelectedForStats.map { lang =>
      lang -> statsScope.counter(f"num_candidates_bucketed_$lang")
    }.toMap
    val filteredCounterMap: Map[String, Counter] = languagesSelectedForStats.map { lang =>
      lang -> statsScope.counter(f"num_filtered_$lang")
    }.toMap
    val lowScoreCounterMap: Map[String, Counter] = languagesSelectedForStats.map { lang =>
      lang -> statsScope.counter(f"low_score_count_$lang")
    }.toMap

    val wrongBucketingModelCounter = statsScope.counter("wrong_bucketing_model_count")
    val wrongDetectionModelCounter = statsScope.counter("wrong_detection_model_count")

    def increaseCounterForLanguage(counterMap: Map[String, Counter], language: String): Unit = {
      counterMap.get(allLanguagesIdentifier) match {
        case Some(counter) => counter.incr()
        case _ =>
      }
      counterMap.get(language) match {
        case Some(counter) => counter.incr()
        case _ =>
      }
    }

    Predicate
      .fromAsync { candidate: PushCandidate with TweetCandidate =>
        val target = candidate.target

        val languageFeatureName = "RecTweet.TweetyPieResult.Language"

        lazy val isPredicateEnabledForTarget = target.params(
          PushFeatureSwitchParams.EnableHealthSignalStoreMultilingualPnsfwTweetTextPredicate)

        lazy val targetNsfwTextDetectionModel: NsfwTextDetectionModel.Value =
          target.params(PushFeatureSwitchParams.MultilingualPnsfwTweetTextModel)

        lazy val targetPredicateSupportedLanguageSeq: Seq[String] =
          target.params(PushFeatureSwitchParams.MultilingualPnsfwTweetTextSupportedLanguages)

        lazy val bucketingModelSeq: Seq[NsfwTextDetectionModel.Value] =
          target.params(PushFeatureSwitchParams.MultilingualPnsfwTweetTextBucketingModelList)

        lazy val bucketingThresholdPerLanguageSeq: Seq[Double] =
          target.params(PushFeatureSwitchParams.MultilingualPnsfwTweetTextBucketingThreshold)

        lazy val filteringThresholdPerLanguageSeq: Seq[Double] =
          target.params(PushFeatureSwitchParams.MultilingualPnsfwTweetTextFilteringThreshold)

        if (CandidateUtil.shouldApplyHealthQualityFilters(
            candidate) && isPredicateEnabledForTarget) {
          val candidateLanguage =
            candidate.categoricalFeatures.getOrElse(languageFeatureName, "")

          val indexOfCandidateLanguage =
            targetPredicateSupportedLanguageSeq.indexOf(candidateLanguage)

          val isCandidateLanguageSupported = indexOfCandidateLanguage >= 0

          if (isCandidateLanguageSupported) {
            increaseCounterForLanguage(candidatesCounterMap, candidateLanguage)

            val bucketingModelScoreMap: Map[NsfwTextDetectionModel.Value, Future[Option[Double]]] =
              bucketingModelSeq.map { modelName =>
                NsfwTextDetectionModelMap.get(modelName) match {
                  case Some(targetNsfwTextDetectionModel) =>
                    val pnsfwTweetTextRequest: TweetScoringRequest =
                      TweetScoringRequest(candidate.tweetId, targetNsfwTextDetectionModel)

                    val scoreOptFut: Future[Option[Double]] =
                      tweetHealthScoreStore.get(pnsfwTweetTextRequest).map(_.map(_.score))

                    candidate
                      .cacheExternalScore("NsfwTextProbability", scoreOptFut)

                    modelName -> scoreOptFut
                  case _ =>
                    wrongBucketingModelCounter.incr()
                    modelName -> Future.None
                }
              }.toMap

            val candidateLanguageBucketingThreshold =
              bucketingThresholdPerLanguageSeq(indexOfCandidateLanguage)

            val userShouldBeBucketedFut: Future[Boolean] =
              Future
                .collect(bucketingModelScoreMap.map {
                  case (_, modelScoreOptFut) =>
                    modelScoreOptFut.map {
                      case Some(score) =>
                        increaseCounterForLanguage(nonEmptyHealthScoreMap, candidateLanguage)
                        score > candidateLanguageBucketingThreshold
                      case _ =>
                        increaseCounterForLanguage(emptyHealthScoreMap, candidateLanguage)
                        false
                    }
                }.toSeq).map(_.contains(true))

            val candidateShouldBeFilteredFut: Future[Boolean] = userShouldBeBucketedFut.flatMap {
              userShouldBeBucketed =>
                if (userShouldBeBucketed) {
                  increaseCounterForLanguage(bucketedCounterMap, candidateLanguage)

                  val candidateLanguageFilteringThreshold =
                    filteringThresholdPerLanguageSeq(indexOfCandidateLanguage)

                  bucketingModelScoreMap.get(targetNsfwTextDetectionModel) match {
                    case Some(scoreOptFut) =>
                      scoreOptFut.map {
                        case Some(score) =>
                          val candidateShouldBeFiltered =
                            score > candidateLanguageFilteringThreshold
                          if (candidateShouldBeFiltered) {
                            increaseCounterForLanguage(filteredCounterMap, candidateLanguage)
                          }
                          candidateShouldBeFiltered
                        case _ => false
                      }
                    case _ =>
                      wrongDetectionModelCounter.incr()
                      Future.False
                  }
                } else {
                  increaseCounterForLanguage(lowScoreCounterMap, candidateLanguage)
                  Future.False
                }
            }
            candidateShouldBeFilteredFut.map(result => !result)
          } else Future.True
        } else Future.True
      }
      .withStats(stats.scope(s"predicate_$name"))
      .withName(name)
  }

  def authorProfileBasedPredicate(
  )(
    implicit stats: StatsReceiver
  ): NamedPredicate[PushCandidate with TweetCandidate] = {
    val name = "author_profile"
    val statsScope = stats.scope(name)
    val filterByNsfwToken = statsScope.counter("filter_by_nsfw_token")
    val filterByAccountAge = statsScope.counter("filter_by_account_age")

    Predicate
      .fromAsync { candidate: PushCandidate with TweetCandidate =>
        val target = candidate.target
        candidate match {
          case cand: PushCandidate with TweetAuthorDetails =>
            cand.tweetAuthor.map {
              case Some(author) =>
                val nsfwTokens = target.params(PushFeatureSwitchParams.NsfwTokensParam)
                val accountAgeInHours =
                  (Time.now - Time.fromMilliseconds(author.createdAtMsec)).inHours
                val isNsfwAccount = CandidateHydrationUtil.isNsfwAccount(author, nsfwTokens)
                val isVerified = author.safety.map(_.verified).getOrElse(false)

                if (CandidateUtil.shouldApplyHealthQualityFilters(candidate) && !isVerified) {
                  val enableNsfwTokenCheck =
                    target.params(PushFeatureSwitchParams.EnableNsfwTokenBasedFiltering)
                  val minimumAllowedAge =
                    target.params(PushFeatureSwitchParams.MinimumAllowedAuthorAccountAgeInHours)
                  cand.cachePredicateInfo(
                    name + "_nsfwToken",
                    if (isNsfwAccount) 1.0 else 0.0,
                    0.0,
                    enableNsfwTokenCheck && isNsfwAccount)
                  cand.cachePredicateInfo(
                    name + "_authorAge",
                    accountAgeInHours,
                    minimumAllowedAge,
                    accountAgeInHours < minimumAllowedAge)

                  if (enableNsfwTokenCheck && isNsfwAccount) {
                    filterByNsfwToken.incr()
                    false
                  } else if (accountAgeInHours < minimumAllowedAge) {
                    filterByAccountAge.incr()
                    false
                  } else true
                } else true
              case _ => true
            }
          case _ => Future.value(true)
        }
      }
      .withStats(stats.scope(s"predicate_$name"))
      .withName(name)
  }

  def authorSensitiveMediaPredicate(
    producerMediaRepresentationStore: ReadableStore[Long, UserMediaRepresentation]
  )(
    implicit stats: StatsReceiver
  ): NamedPredicate[PushCandidate with TweetAuthor] = {
    val name = "author_sensitive_media_mrtwistly"
    val statsScope = stats.scope(name)
    val enableQueryNum = statsScope.counter("enable_query")
    val nonEmptyMediaRepresentationNum = statsScope.counter("non_empty_media_representation")
    val filteredOON = statsScope.counter("filtered_oon")

    Predicate
      .fromAsync { candidate: PushCandidate with TweetAuthor =>
        val target = candidate.target
        val useAggressiveThresholds = CandidateUtil.useAggressiveHealthThresholds(candidate)

        if (CandidateUtil.shouldApplyHealthQualityFilters(candidate) &&
          RecTypes.isOutOfNetworkTweetRecType(candidate.commonRecType) &&
          target.params(PushFeatureSwitchParams.EnableQueryAuthorMediaRepresentationStore)) {
          enableQueryNum.incr()

          candidate.authorId match {
            case Some(authorId) =>
              producerMediaRepresentationStore.get(authorId).map {
                case Some(mediaRepresentation) =>
                  nonEmptyMediaRepresentationNum.incr()
                  val sumScore: Double = mediaRepresentation.mediaRepresentation.values.sum
                  val nudityScore: Double = mediaRepresentation.mediaRepresentation
                    .getOrElse(MediaAnnotationsUtil.nudityCategoryId, 0.0)
                  val nudityRate = if (sumScore > 0) nudityScore / sumScore else 0.0

                  candidate
                    .cacheExternalScore("AuthorNudityScore", Future.value(Some(nudityScore)))
                  candidate.cacheExternalScore("AuthorNudityRate", Future.value(Some(nudityRate)))

                  val threshold = if (useAggressiveThresholds) {
                    target.params(
                      PushFeatureSwitchParams.AuthorSensitiveMediaFilteringThresholdForMrTwistly)
                  } else {
                    target.params(PushFeatureSwitchParams.AuthorSensitiveMediaFilteringThreshold)
                  }
                  candidate.cachePredicateInfo(
                    name,
                    nudityRate,
                    threshold,
                    nudityRate > threshold,
                    Some(Map[String, Double]("sumScore" -> sumScore, "nudityScore" -> nudityScore)))

                  if (nudityRate > threshold) {
                    filteredOON.incr()
                    false
                  } else true
                case _ => true
              }
            case _ => Future.True
          }
        } else {
          Future.True
        }
      }
      .withStats(stats.scope(s"predicate_$name"))
      .withName(name)
  }

  def sensitiveMediaCategoryPredicate(
  )(
    implicit stats: StatsReceiver
  ): NamedPredicate[PushCandidate with TweetCandidate] = {
    val name = "sensitive_media_category"
    val tweetMediaAnnotationFeature =
      "tweet.mediaunderstanding.tweet_annotations.sensitive_category_probabilities"
    val scopedStatsReceiver = stats.scope(name)
    val allCandidatesCounter = scopedStatsReceiver.counter("all_candidates")
    val nonZeroNudityCandidatesCounter = scopedStatsReceiver.counter("non_zero_nudity_candidates")
    val nudityScoreStats = scopedStatsReceiver.stat("nudity_scores")

    Predicate
      .fromAsync { candidate: PushCandidate =>
        allCandidatesCounter.incr()
        val target = candidate.target
        val nudityScore = candidate.sparseContinuousFeatures
          .getOrElse(tweetMediaAnnotationFeature, Map.empty[String, Double]).getOrElse(
            MediaAnnotationsUtil.nudityCategoryId,
            0.0)
        if (nudityScore > 0) nonZeroNudityCandidatesCounter.incr()
        nudityScoreStats.add(nudityScore.toFloat)
        val threshold =
          target.params(PushFeatureSwitchParams.TweetMediaSensitiveCategoryThresholdParam)
        candidate.cachePredicateInfo(name, nudityScore, threshold, nudityScore > threshold)
        if (CandidateUtil.shouldApplyHealthQualityFilters(candidate) && nudityScore > threshold) {
          Future.False
        } else {
          Future.True
        }
      }
      .withStats(stats.scope(s"predicate_$name"))
      .withName(name)
  }

  def profanityPredicate(
  )(
    implicit stats: StatsReceiver
  ): NamedPredicate[PushCandidate with TweetCandidate] = {
    val name = "profanity_filter"
    val scopedStatsReceiver = stats.scope(name)
    val allCandidatesCounter = scopedStatsReceiver.counter("all_candidates")

    Predicate
      .fromAsync { candidate: PushCandidate =>
        allCandidatesCounter.incr()
        val target = candidate.target

        lazy val enableFilter =
          target.params(PushFeatureSwitchParams.EnableProfanityFilterParam)
        val tweetSemanticCoreIds = candidate.sparseBinaryFeatures
          .getOrElse(PushConstants.TweetSemanticCoreIdFeature, Set.empty[String])

        if (CandidateUtil.shouldApplyHealthQualityFilters(candidate) &&
          tweetSemanticCoreIds.contains(PushConstants.ProfanityFilter_Id) && enableFilter) {
          Future.False
        } else {
          Future.True
        }
      }
      .withStats(stats.scope(s"predicate_$name"))
      .withName(name)
  }

  def agathaAbusiveTweetAuthorPredicateMrTwistly(
  )(
    implicit stats: StatsReceiver
  ): NamedPredicate[PushCandidate with OutOfNetworkTweetCandidate] = {
    val name = "agatha_abusive_tweet_author_mr_twistly"
    val scopedStatsReceiver = stats.scope(name)
    val allCandidatesCounter = scopedStatsReceiver.counter("all_candidates")
    val isMrBackfillCRCandidateCounter = scopedStatsReceiver.counter("isMrBackfillCR_candidates")
    Predicate
      .fromAsync { cand: PushCandidate with OutOfNetworkTweetCandidate =>
        allCandidatesCounter.incr()
        val target = cand.target
        val tweetSemanticCoreIds = cand.sparseBinaryFeatures
          .getOrElse(PushConstants.TweetSemanticCoreIdFeature, Set.empty[String])

        val hasAbuseStrikeTop2Percent =
          tweetSemanticCoreIds.contains(PushConstants.AbuseStrike_Top2Percent_Id)
        val hasAbuseStrikeTop1Percent =
          tweetSemanticCoreIds.contains(PushConstants.AbuseStrike_Top1Percent_Id)
        val hasAbuseStrikeTop05Percent =
          tweetSemanticCoreIds.contains(PushConstants.AbuseStrike_Top05Percent_Id)

        if (hasAbuseStrikeTop2Percent) {
          scopedStatsReceiver.counter("abuse_strike_top_2_percent_candidates").incr()
        }
        if (hasAbuseStrikeTop1Percent) {
          scopedStatsReceiver.counter("abuse_strike_top_1_percent_candidates").incr()
        }
        if (hasAbuseStrikeTop05Percent) {
          scopedStatsReceiver.counter("abuse_strike_top_05_percent_candidates").incr()
        }

        if (CandidateUtil.shouldApplyHealthQualityFilters(cand) && cand.isMrBackfillCR.getOrElse(
            false)) {
          isMrBackfillCRCandidateCounter.incr()
          if (hasAbuseStrikeTop2Percent) {
            if (target.params(
                PushFeatureSwitchParams.EnableAbuseStrikeTop2PercentFilterSimCluster) && hasAbuseStrikeTop2Percent ||
              target.params(
                PushFeatureSwitchParams.EnableAbuseStrikeTop1PercentFilterSimCluster) && hasAbuseStrikeTop1Percent ||
              target.params(
                PushFeatureSwitchParams.EnableAbuseStrikeTop05PercentFilterSimCluster) && hasAbuseStrikeTop05Percent) {
              Future.False
            } else {
              Future.True
            }
          } else {
            Future.True
          }
        } else Future.True
      }
      .withStats(stats.scope(s"predicate_$name"))
      .withName(name)
  }

  def userHealthSignalsPredicate(
    userHealthSignalStore: ReadableStore[Long, UserHealthSignalResponse]
  )(
    implicit stats: StatsReceiver
  ): NamedPredicate[PushCandidate with TweetDetails] = {
    val name = "agatha_user_health_model_score"
    val scopedStatsReceiver = stats.scope(name)
    val allCandidatesCounter = scopedStatsReceiver.counter("all_candidates")
    val bucketedUserCandidatesCounter =
      scopedStatsReceiver.counter("bucketed_user_candidates")
    val filteredOON = scopedStatsReceiver.counter("filtered_oon")

    Predicate
      .fromAsync { candidate: PushCandidate with TweetDetails =>
        allCandidatesCounter.incr()
        val target = candidate.target
        val useAggressiveThresholds = CandidateUtil.useAggressiveHealthThresholds(candidate)

        if (CandidateUtil.shouldApplyHealthQualityFilters(candidate) && target.params(
            PushFeatureSwitchParams.EnableAgathaUserHealthModelPredicate)) {
          val healthSignalsResponseFutOpt: Future[Option[UserHealthSignalResponse]] =
            candidate.authorId match {
              case Some(authorId) => userHealthSignalStore.get(authorId)
              case _ => Future.None
            }
          healthSignalsResponseFutOpt.map {
            case Some(response) =>
              val agathaRecentAbuseStrikeScore: Double = userHealthSignalValueToDouble(
                response.signalValues
                  .getOrElse(AgathaRecentAbuseStrikeDouble, SignalValue.DoubleValue(0.0)))
              val agathaCalibratedNSFWScore: Double = userHealthSignalValueToDouble(
                response.signalValues
                  .getOrElse(AgathaCalibratedNsfwDouble, SignalValue.DoubleValue(0.0)))
              val agathaTextNSFWScore: Double = userHealthSignalValueToDouble(response.signalValues
                .getOrElse(NsfwTextUserScoreDouble, SignalValue.DoubleValue(0.0)))

              candidate
                .cacheExternalScore(
                  "agathaRecentAbuseStrikeScore",
                  Future.value(Some(agathaRecentAbuseStrikeScore)))
              candidate
                .cacheExternalScore(
                  "agathaCalibratedNSFWScore",
                  Future.value(Some(agathaCalibratedNSFWScore)))
              candidate
                .cacheExternalScore("agathaTextNSFWScore", Future.value(Some(agathaTextNSFWScore)))

              val NSFWShouldBucket = agathaCalibratedNSFWScore > target.params(
                PushFeatureSwitchParams.AgathaCalibratedNSFWBucketThreshold)
              val textNSFWShouldBucket = agathaTextNSFWScore > target.params(
                PushFeatureSwitchParams.AgathaTextNSFWBucketThreshold)

              if (NSFWShouldBucket || textNSFWShouldBucket) {
                bucketedUserCandidatesCounter.incr()
                if (NSFWShouldBucket) {
                  scopedStatsReceiver.counter("calibrated_nsfw_bucketed_user_candidates").incr()
                }
                if (textNSFWShouldBucket) {
                  scopedStatsReceiver.counter("text_nsfw_bucketed_user_candidates").incr()
                }

                val (thresholdAgathaNsfw, thresholdTextNsfw) = if (useAggressiveThresholds) {
                  (
                    target.params(
                      PushFeatureSwitchParams.AgathaCalibratedNSFWThresholdForMrTwistly),
                    target
                      .params(PushFeatureSwitchParams.AgathaTextNSFWThresholdForMrTwistly))
                } else {
                  (
                    target.params(PushFeatureSwitchParams.AgathaCalibratedNSFWThreshold),
                    target.params(PushFeatureSwitchParams.AgathaTextNSFWThreshold))
                }
                candidate.cachePredicateInfo(
                  name + "_agathaNsfw",
                  agathaCalibratedNSFWScore,
                  thresholdAgathaNsfw,
                  agathaCalibratedNSFWScore > thresholdAgathaNsfw)
                candidate.cachePredicateInfo(
                  name + "_authorTextNsfw",
                  agathaTextNSFWScore,
                  thresholdTextNsfw,
                  agathaTextNSFWScore > thresholdTextNsfw)

                if ((agathaCalibratedNSFWScore > thresholdAgathaNsfw) ||
                  (agathaTextNSFWScore > thresholdTextNsfw)) {
                  filteredOON.incr()
                  false
                } else true
              } else {
                true
              }
            case _ => true
          }
        } else {
          Future.True
        }
      }
      .withStats(stats.scope(s"predicate_$name"))
      .withName(name)
  }

  def userHealthSignalValueToDouble(signalValue: SignalValue): Double = {
    signalValue match {
      case SignalValue.DoubleValue(value) => value
      case _ => throw new Exception(f"Could not convert signal value to double")
    }
  }
}
