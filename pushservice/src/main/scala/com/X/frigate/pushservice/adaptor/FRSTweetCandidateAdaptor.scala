package com.X.frigate.pushservice.adaptor

import com.X.cr_mixer.thriftscala.FrsTweetRequest
import com.X.cr_mixer.thriftscala.NotificationsContext
import com.X.cr_mixer.thriftscala.Product
import com.X.cr_mixer.thriftscala.ProductContext
import com.X.finagle.stats.Counter
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.CandidateSource
import com.X.frigate.common.base.CandidateSourceEligible
import com.X.frigate.common.base._
import com.X.frigate.common.predicate.CommonOutNetworkTweetCandidatesSourcePredicates.filterOutReplyTweet
import com.X.frigate.pushservice.model.PushTypes.RawCandidate
import com.X.frigate.pushservice.model.PushTypes.Target
import com.X.frigate.pushservice.params.PushFeatureSwitchParams
import com.X.frigate.pushservice.store.CrMixerTweetStore
import com.X.frigate.pushservice.store.UttEntityHydrationStore
import com.X.frigate.pushservice.util.MediaCRT
import com.X.frigate.pushservice.util.PushAdaptorUtil
import com.X.frigate.pushservice.util.PushDeviceUtil
import com.X.frigate.pushservice.util.TopicsUtil
import com.X.frigate.thriftscala.CommonRecommendationType
import com.X.hermit.constants.AlgorithmFeedbackTokens
import com.X.hermit.model.Algorithm.Algorithm
import com.X.hermit.model.Algorithm.CrowdSearchAccounts
import com.X.hermit.model.Algorithm.ForwardEmailBook
import com.X.hermit.model.Algorithm.ForwardPhoneBook
import com.X.hermit.model.Algorithm.ReverseEmailBookIbis
import com.X.hermit.model.Algorithm.ReversePhoneBook
import com.X.hermit.store.tweetypie.UserTweet
import com.X.product_mixer.core.thriftscala.ClientContext
import com.X.stitch.tweetypie.TweetyPie.TweetyPieResult
import com.X.storehaus.ReadableStore
import com.X.tsp.thriftscala.TopicSocialProofRequest
import com.X.tsp.thriftscala.TopicSocialProofResponse
import com.X.util.Future

object FRSAlgorithmFeedbackTokenUtil {
  private val crtsByAlgoToken = Map(
    getAlgorithmToken(ReverseEmailBookIbis) -> CommonRecommendationType.ReverseAddressbookTweet,
    getAlgorithmToken(ReversePhoneBook) -> CommonRecommendationType.ReverseAddressbookTweet,
    getAlgorithmToken(ForwardEmailBook) -> CommonRecommendationType.ForwardAddressbookTweet,
    getAlgorithmToken(ForwardPhoneBook) -> CommonRecommendationType.ForwardAddressbookTweet,
    getAlgorithmToken(CrowdSearchAccounts) -> CommonRecommendationType.CrowdSearchTweet
  )

  def getAlgorithmToken(algorithm: Algorithm): Int = {
    AlgorithmFeedbackTokens.AlgorithmToFeedbackTokenMap(algorithm)
  }

  def getCRTForAlgoToken(algorithmToken: Int): Option[CommonRecommendationType] = {
    crtsByAlgoToken.get(algorithmToken)
  }
}

case class FRSTweetCandidateAdaptor(
  crMixerTweetStore: CrMixerTweetStore,
  tweetyPieStore: ReadableStore[Long, TweetyPieResult],
  tweetyPieStoreNoVF: ReadableStore[Long, TweetyPieResult],
  userTweetTweetyPieStore: ReadableStore[UserTweet, TweetyPieResult],
  uttEntityHydrationStore: UttEntityHydrationStore,
  topicSocialProofServiceStore: ReadableStore[TopicSocialProofRequest, TopicSocialProofResponse],
  globalStats: StatsReceiver)
    extends CandidateSource[Target, RawCandidate]
    with CandidateSourceEligible[Target, RawCandidate] {

  private val stats = globalStats.scope(this.getClass.getSimpleName)
  private val crtStats = stats.scope("CandidateDistribution")
  private val totalRequests = stats.counter("total_requests")

  // Candidate Distribution stats
  private val reverseAddressbookCounter = crtStats.counter("reverse_addressbook")
  private val forwardAddressbookCounter = crtStats.counter("forward_addressbook")
  private val frsTweetCounter = crtStats.counter("frs_tweet")
  private val nonReplyTweetsCounter = stats.counter("non_reply_tweets")
  private val crtToCounterMapping: Map[CommonRecommendationType, Counter] = Map(
    CommonRecommendationType.ReverseAddressbookTweet -> reverseAddressbookCounter,
    CommonRecommendationType.ForwardAddressbookTweet -> forwardAddressbookCounter,
    CommonRecommendationType.FrsTweet -> frsTweetCounter
  )

  private val emptyTweetyPieResult = stats.stat("empty_tweetypie_result")

  private[this] val numberReturnedCandidates = stats.stat("returned_candidates_from_earlybird")
  private[this] val numberCandidateWithTopic: Counter = stats.counter("num_can_with_topic")
  private[this] val numberCandidateWithoutTopic: Counter = stats.counter("num_can_without_topic")

  private val userTweetTweetyPieStoreCounter = stats.counter("user_tweet_tweetypie_store")

  override val name: String = this.getClass.getSimpleName

  private def filterInvalidTweets(
    tweetIds: Seq[Long],
    target: Target
  ): Future[Map[Long, TweetyPieResult]] = {
    val resMap = {
      if (target.params(PushFeatureSwitchParams.EnableF1FromProtectedTweetAuthors)) {
        userTweetTweetyPieStoreCounter.incr()
        val keys = tweetIds.map { tweetId =>
          UserTweet(tweetId, Some(target.targetId))
        }
        userTweetTweetyPieStore
          .multiGet(keys.toSet).map {
            case (userTweet, resultFut) =>
              userTweet.tweetId -> resultFut
          }.toMap
      } else {
        (if (target.params(PushFeatureSwitchParams.EnableVFInTweetypie)) {
           tweetyPieStore
         } else {
           tweetyPieStoreNoVF
         }).multiGet(tweetIds.toSet)
      }
    }

    Future.collect(resMap).map { tweetyPieResultMap =>
      // Filter out replies and generate earlybird candidates only for non-empty tweetypie result
      val cands = filterOutReplyTweet(tweetyPieResultMap, nonReplyTweetsCounter).collect {
        case (id: Long, Some(result)) =>
          id -> result
      }

      emptyTweetyPieResult.add(tweetyPieResultMap.size - cands.size)
      cands
    }
  }

  private def buildRawCandidates(
    target: Target,
    ebCandidates: Seq[FRSTweetCandidate]
  ): Future[Option[Seq[RawCandidate with TweetCandidate]]] = {

    val enableTopic = target.params(PushFeatureSwitchParams.EnableFrsTweetCandidatesTopicAnnotation)
    val topicScoreThre =
      target.params(PushFeatureSwitchParams.FrsTweetCandidatesTopicScoreThreshold)

    val ebTweets = ebCandidates.map { ebCandidate =>
      ebCandidate.tweetId -> ebCandidate.tweetyPieResult
    }.toMap

    val tweetIdLocalizedEntityMapFut = TopicsUtil.getTweetIdLocalizedEntityMap(
      target,
      ebTweets,
      uttEntityHydrationStore,
      topicSocialProofServiceStore,
      enableTopic,
      topicScoreThre
    )

    Future.join(target.deviceInfo, tweetIdLocalizedEntityMapFut).map {
      case (Some(deviceInfo), tweetIdLocalizedEntityMap) =>
        val candidates = ebCandidates
          .map { ebCandidate =>
            val crt = ebCandidate.commonRecType
            crtToCounterMapping.get(crt).foreach(_.incr())

            val tweetId = ebCandidate.tweetId
            val localizedEntityOpt = {
              if (tweetIdLocalizedEntityMap
                  .contains(tweetId) && tweetIdLocalizedEntityMap.contains(
                  tweetId) && deviceInfo.isTopicsEligible) {
                tweetIdLocalizedEntityMap(tweetId)
              } else {
                None
              }
            }

            PushAdaptorUtil.generateOutOfNetworkTweetCandidates(
              inputTarget = target,
              id = ebCandidate.tweetId,
              mediaCRT = MediaCRT(
                crt,
                crt,
                crt
              ),
              result = ebCandidate.tweetyPieResult,
              localizedEntity = localizedEntityOpt)
          }.filter { candidate =>
            // If user only has the topic setting enabled, filter out all non-topic cands
            deviceInfo.isRecommendationsEligible || (deviceInfo.isTopicsEligible && candidate.semanticCoreEntityId.nonEmpty)
          }

        candidates.map { candidate =>
          if (candidate.semanticCoreEntityId.nonEmpty) {
            numberCandidateWithTopic.incr()
          } else {
            numberCandidateWithoutTopic.incr()
          }
        }

        numberReturnedCandidates.add(candidates.length)
        Some(candidates)
      case _ => Some(Seq.empty)
    }
  }

  def getTweetCandidatesFromCrMixer(
    inputTarget: Target,
    showAllResultsFromFrs: Boolean,
  ): Future[Option[Seq[RawCandidate with TweetCandidate]]] = {
    Future
      .join(
        inputTarget.seenTweetIds,
        inputTarget.pushRecItems,
        inputTarget.countryCode,
        inputTarget.targetLanguage).flatMap {
        case (seenTweetIds, pastRecItems, countryCode, language) =>
          val pastUserRecs = pastRecItems.userIds.toSeq
          val request = FrsTweetRequest(
            clientContext = ClientContext(
              userId = Some(inputTarget.targetId),
              countryCode = countryCode,
              languageCode = language
            ),
            product = Product.Notifications,
            productContext = Some(ProductContext.NotificationsContext(NotificationsContext())),
            excludedUserIds = Some(pastUserRecs),
            excludedTweetIds = Some(seenTweetIds)
          )
          crMixerTweetStore.getFRSTweetCandidates(request).flatMap {
            case Some(response) =>
              val tweetIds = response.tweets.map(_.tweetId)
              val validTweets = filterInvalidTweets(tweetIds, inputTarget)
              validTweets.flatMap { tweetypieMap =>
                val ebCandidates = response.tweets
                  .map { frsTweet =>
                    val candidateTweetId = frsTweet.tweetId
                    val resultFromTweetyPie = tweetypieMap.get(candidateTweetId)
                    new FRSTweetCandidate {
                      override val tweetId = candidateTweetId
                      override val features = None
                      override val tweetyPieResult = resultFromTweetyPie
                      override val feedbackToken = frsTweet.frsPrimarySource
                      override val commonRecType: CommonRecommendationType = feedbackToken
                        .flatMap(token =>
                          FRSAlgorithmFeedbackTokenUtil.getCRTForAlgoToken(token)).getOrElse(
                          CommonRecommendationType.FrsTweet)
                    }
                  }.filter { ebCandidate =>
                    showAllResultsFromFrs || ebCandidate.commonRecType == CommonRecommendationType.ReverseAddressbookTweet
                  }

                numberReturnedCandidates.add(ebCandidates.length)
                buildRawCandidates(
                  inputTarget,
                  ebCandidates
                )
              }
            case _ => Future.None
          }
      }
  }

  override def get(inputTarget: Target): Future[Option[Seq[RawCandidate with TweetCandidate]]] = {
    totalRequests.incr()
    val enableResultsFromFrs =
      inputTarget.params(PushFeatureSwitchParams.EnableResultFromFrsCandidates)
    getTweetCandidatesFromCrMixer(inputTarget, enableResultsFromFrs)
  }

  override def isCandidateSourceAvailable(target: Target): Future[Boolean] = {
    lazy val enableFrsCandidates = target.params(PushFeatureSwitchParams.EnableFrsCandidates)
    PushDeviceUtil.isRecommendationsEligible(target).flatMap { isEnabledForRecosSetting =>
      PushDeviceUtil.isTopicsEligible(target).map { topicSettingEnabled =>
        val isEnabledForTopics =
          topicSettingEnabled && target.params(
            PushFeatureSwitchParams.EnableFrsTweetCandidatesTopicSetting)
        (isEnabledForRecosSetting || isEnabledForTopics) && enableFrsCandidates
      }
    }
  }
}
