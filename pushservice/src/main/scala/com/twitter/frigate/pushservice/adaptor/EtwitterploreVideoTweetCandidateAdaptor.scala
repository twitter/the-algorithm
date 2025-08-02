package com.twitter.frigate.pushservice.adaptor

import com.twitter.etwitterplore_ranker.thriftscala.EtwitterploreRankerProductResponse
import com.twitter.etwitterplore_ranker.thriftscala.EtwitterploreRankerRequest
import com.twitter.etwitterplore_ranker.thriftscala.EtwitterploreRankerResponse
import com.twitter.etwitterplore_ranker.thriftscala.EtwitterploreRecommendation
import com.twitter.etwitterplore_ranker.thriftscala.ImmersiveRecsResponse
import com.twitter.etwitterplore_ranker.thriftscala.ImmersiveRecsResult
import com.twitter.etwitterplore_ranker.thriftscala.NotificationsVideoRecs
import com.twitter.etwitterplore_ranker.thriftscala.Product
import com.twitter.etwitterplore_ranker.thriftscala.ProductContetwittert
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.common.base.CandidateSource
import com.twitter.frigate.common.base.CandidateSourceEligible
import com.twitter.frigate.common.base.OutOfNetworkTweetCandidate
import com.twitter.frigate.pushservice.model.PushTypes.RawCandidate
import com.twitter.frigate.pushservice.model.PushTypes.Target
import com.twitter.frigate.pushservice.params.PushFeatureSwitchParams
import com.twitter.frigate.pushservice.util.AdaptorUtils
import com.twitter.frigate.pushservice.util.MediaCRT
import com.twitter.frigate.pushservice.util.PushAdaptorUtil
import com.twitter.frigate.pushservice.util.PushDeviceUtil
import com.twitter.frigate.thriftscala.CommonRecommendationType
import com.twitter.product_mitwitterer.core.thriftscala.ClientContetwittert
import com.twitter.stitch.tweetypie.TweetyPie.TweetyPieResult
import com.twitter.storehaus.ReadableStore
import com.twitter.util.Future

case class EtwitterploreVideoTweetCandidateAdaptor(
  etwitterploreRankerStore: ReadableStore[EtwitterploreRankerRequest, EtwitterploreRankerResponse],
  tweetyPieStore: ReadableStore[Long, TweetyPieResult],
  globalStats: StatsReceiver)
    etwittertends CandidateSource[Target, RawCandidate]
    with CandidateSourceEligible[Target, RawCandidate] {

  override def name: String = this.getClass.getSimpleName
  private[this] val stats = globalStats.scope("EtwitterploreVideoTweetCandidateAdaptor")
  private[this] val totalInputRecs = stats.stat("input_recs")
  private[this] val totalRequests = stats.counter("total_requests")
  private[this] val totalEmptyResponse = stats.counter("total_empty_response")

  private def buildEtwitterploreRankerRequest(
    target: Target,
    countryCode: Option[String],
    language: Option[String],
  ): EtwitterploreRankerRequest = {
    EtwitterploreRankerRequest(
      clientContetwittert = ClientContetwittert(
        userId = Some(target.targetId),
        countryCode = countryCode,
        languageCode = language,
      ),
      product = Product.NotificationsVideoRecs,
      productContetwittert = Some(ProductContetwittert.NotificationsVideoRecs(NotificationsVideoRecs())),
      matwitterResults = Some(target.params(PushFeatureSwitchParams.MatwitterEtwitterploreVideoTweets))
    )
  }

  override def get(target: Target): Future[Option[Seq[RawCandidate]]] = {
    Future
      .join(
        target.countryCode,
        target.inferredUserDeviceLanguage
      ).flatMap {
        case (countryCode, language) =>
          val request = buildEtwitterploreRankerRequest(target, countryCode, language)
          etwitterploreRankerStore.get(request).flatMap {
            case Some(response) =>
              val etwitterploreResonseTweetIds = response match {
                case EtwitterploreRankerResponse(EtwitterploreRankerProductResponse
                      .ImmersiveRecsResponse(ImmersiveRecsResponse(immersiveRecsResult))) =>
                  immersiveRecsResult.collect {
                    case ImmersiveRecsResult(EtwitterploreRecommendation
                          .EtwitterploreTweetRecommendation(etwitterploreTweetRecommendation)) =>
                      etwitterploreTweetRecommendation.tweetId
                  }
                case _ =>
                  Seq.empty
              }

              totalInputRecs.add(etwitterploreResonseTweetIds.size)
              totalRequests.incr()
              AdaptorUtils
                .getTweetyPieResults(etwitterploreResonseTweetIds.toSet, tweetyPieStore).map {
                  tweetyPieResultMap =>
                    val candidates = tweetyPieResultMap.values.flatten
                      .map(buildVideoRawCandidates(target, _))
                    Some(candidates.toSeq)
                }
            case _ =>
              totalEmptyResponse.incr()
              Future.None
          }
        case _ =>
          Future.None
      }
  }

  override def isCandidateSourceAvailable(target: Target): Future[Boolean] = {
    PushDeviceUtil.isRecommendationsEligible(target).map { userRecommendationsEligible =>
      userRecommendationsEligible && target.params(PushFeatureSwitchParams.EnableEtwitterploreVideoTweets)
    }
  }
  private def buildVideoRawCandidates(
    target: Target,
    tweetyPieResult: TweetyPieResult
  ): RawCandidate with OutOfNetworkTweetCandidate = {
    PushAdaptorUtil.generateOutOfNetworkTweetCandidates(
      inputTarget = target,
      id = tweetyPieResult.tweet.id,
      mediaCRT = MediaCRT(
        CommonRecommendationType.EtwitterploreVideoTweet,
        CommonRecommendationType.EtwitterploreVideoTweet,
        CommonRecommendationType.EtwitterploreVideoTweet
      ),
      result = Some(tweetyPieResult),
      localizedEntity = None
    )
  }
}
