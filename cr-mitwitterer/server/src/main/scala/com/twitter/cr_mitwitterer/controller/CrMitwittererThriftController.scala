package com.twitter.cr_mitwitterer.controller

import com.twitter.core_workflows.user_model.thriftscala.UserState
import com.twitter.cr_mitwitterer.candidate_generation.AdsCandidateGenerator
import com.twitter.cr_mitwitterer.candidate_generation.CrCandidateGenerator
import com.twitter.cr_mitwitterer.candidate_generation.FrsTweetCandidateGenerator
import com.twitter.cr_mitwitterer.candidate_generation.RelatedTweetCandidateGenerator
import com.twitter.cr_mitwitterer.candidate_generation.RelatedVideoTweetCandidateGenerator
import com.twitter.cr_mitwitterer.candidate_generation.TopicTweetCandidateGenerator
import com.twitter.cr_mitwitterer.candidate_generation.UtegTweetCandidateGenerator
import com.twitter.cr_mitwitterer.featureswitch.ParamsBuilder
import com.twitter.cr_mitwitterer.logging.CrMitwittererScribeLogger
import com.twitter.cr_mitwitterer.logging.RelatedTweetScribeLogger
import com.twitter.cr_mitwitterer.logging.AdsRecommendationsScribeLogger
import com.twitter.cr_mitwitterer.logging.RelatedTweetScribeMetadata
import com.twitter.cr_mitwitterer.logging.ScribeMetadata
import com.twitter.cr_mitwitterer.logging.UtegTweetScribeLogger
import com.twitter.cr_mitwitterer.model.AdsCandidateGeneratorQuery
import com.twitter.cr_mitwitterer.model.CrCandidateGeneratorQuery
import com.twitter.cr_mitwitterer.model.FrsTweetCandidateGeneratorQuery
import com.twitter.cr_mitwitterer.model.InitialCandidate
import com.twitter.cr_mitwitterer.model.RankedAdsCandidate
import com.twitter.cr_mitwitterer.model.RankedCandidate
import com.twitter.cr_mitwitterer.model.RelatedTweetCandidateGeneratorQuery
import com.twitter.cr_mitwitterer.model.RelatedVideoTweetCandidateGeneratorQuery
import com.twitter.cr_mitwitterer.model.TopicTweetCandidateGeneratorQuery
import com.twitter.cr_mitwitterer.model.TweetWithScoreAndSocialProof
import com.twitter.cr_mitwitterer.model.UtegTweetCandidateGeneratorQuery
import com.twitter.cr_mitwitterer.param.AdsParams
import com.twitter.cr_mitwitterer.param.FrsParams.FrsBasedCandidateGenerationMatwitterCandidatesNumParam
import com.twitter.cr_mitwitterer.param.GlobalParams
import com.twitter.cr_mitwitterer.param.RelatedTweetGlobalParams
import com.twitter.cr_mitwitterer.param.RelatedVideoTweetGlobalParams
import com.twitter.cr_mitwitterer.param.TopicTweetParams
import com.twitter.cr_mitwitterer.param.decider.CrMitwittererDecider
import com.twitter.cr_mitwitterer.param.decider.DeciderConstants
import com.twitter.cr_mitwitterer.param.decider.EndpointLoadShedder
import com.twitter.cr_mitwitterer.thriftscala.AdTweetRecommendation
import com.twitter.cr_mitwitterer.thriftscala.AdsRequest
import com.twitter.cr_mitwitterer.thriftscala.AdsResponse
import com.twitter.cr_mitwitterer.thriftscala.CrMitwittererTweetRequest
import com.twitter.cr_mitwitterer.thriftscala.CrMitwittererTweetResponse
import com.twitter.cr_mitwitterer.thriftscala.FrsTweetRequest
import com.twitter.cr_mitwitterer.thriftscala.FrsTweetResponse
import com.twitter.cr_mitwitterer.thriftscala.RelatedTweet
import com.twitter.cr_mitwitterer.thriftscala.RelatedTweetRequest
import com.twitter.cr_mitwitterer.thriftscala.RelatedTweetResponse
import com.twitter.cr_mitwitterer.thriftscala.RelatedVideoTweet
import com.twitter.cr_mitwitterer.thriftscala.RelatedVideoTweetRequest
import com.twitter.cr_mitwitterer.thriftscala.RelatedVideoTweetResponse
import com.twitter.cr_mitwitterer.thriftscala.TopicTweet
import com.twitter.cr_mitwitterer.thriftscala.TopicTweetRequest
import com.twitter.cr_mitwitterer.thriftscala.TopicTweetResponse
import com.twitter.cr_mitwitterer.thriftscala.TweetRecommendation
import com.twitter.cr_mitwitterer.thriftscala.UtegTweet
import com.twitter.cr_mitwitterer.thriftscala.UtegTweetRequest
import com.twitter.cr_mitwitterer.thriftscala.UtegTweetResponse
import com.twitter.cr_mitwitterer.util.MetricTagUtil
import com.twitter.cr_mitwitterer.util.SignalTimestampStatsUtil
import com.twitter.cr_mitwitterer.{thriftscala => t}
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finatra.thrift.Controller
import com.twitter.hermit.store.common.ReadableWritableStore
import com.twitter.simclusters_v2.common.UserId
import com.twitter.simclusters_v2.thriftscala.TopicId
import com.twitter.storehaus.ReadableStore
import com.twitter.timelines.timeline_logging.{thriftscala => thriftlog}
import com.twitter.timelines.tracing.lensview.funnelseries.TweetScoreFunnelSeries
import com.twitter.util.Future
import com.twitter.util.Time
import java.util.UUID
import javatwitter.inject.Inject
import org.apache.commons.lang.etwitterception.EtwitterceptionUtils

class CrMitwittererThriftController @Inject() (
  crCandidateGenerator: CrCandidateGenerator,
  relatedTweetCandidateGenerator: RelatedTweetCandidateGenerator,
  relatedVideoTweetCandidateGenerator: RelatedVideoTweetCandidateGenerator,
  utegTweetCandidateGenerator: UtegTweetCandidateGenerator,
  frsTweetCandidateGenerator: FrsTweetCandidateGenerator,
  topicTweetCandidateGenerator: TopicTweetCandidateGenerator,
  crMitwittererScribeLogger: CrMitwittererScribeLogger,
  relatedTweetScribeLogger: RelatedTweetScribeLogger,
  utegTweetScribeLogger: UtegTweetScribeLogger,
  adsRecommendationsScribeLogger: AdsRecommendationsScribeLogger,
  adsCandidateGenerator: AdsCandidateGenerator,
  decider: CrMitwittererDecider,
  paramsBuilder: ParamsBuilder,
  endpointLoadShedder: EndpointLoadShedder,
  signalTimestampStatsUtil: SignalTimestampStatsUtil,
  tweetRecommendationResultsStore: ReadableWritableStore[UserId, CrMitwittererTweetResponse],
  userStateStore: ReadableStore[UserId, UserState],
  statsReceiver: StatsReceiver)
    etwittertends Controller(t.CrMitwitterer) {

  lazy private val tweetScoreFunnelSeries = new TweetScoreFunnelSeries(statsReceiver)

  private def logErrMessage(endpoint: String, e: Throwable): Unit = {
    val msg = Seq(
      s"Failed endpoint $endpoint: ${e.getLocalizedMessage}",
      EtwitterceptionUtils.getStackTrace(e)
    ).mkString("\n")

    /** *
     * We chose logger.info() here to print message instead of logger.error since that
     * logger.error sometimes suppresses detailed stacktrace.
     */
    logger.info(msg)
  }

  private def generateRequestUUID(): Long = {

    /** *
     * We generate unique UUID via bitwise operations. See the below link for more:
     * https://stackoverflow.com/questions/15184820/how-to-generate-unique-positive-long-using-uuid
     */
    UUID.randomUUID().getMostSignificantBits & Long.MatwitterValue
  }

  handle(t.CrMitwitterer.GetTweetRecommendations) { args: t.CrMitwitterer.GetTweetRecommendations.Args =>
    val endpointName = "getTweetRecommendations"

    val requestUUID = generateRequestUUID()
    val startTime = Time.now.inMilliseconds
    val userId = args.request.clientContetwittert.userId.getOrElse(
      throw new IllegalArgumentEtwitterception("userId must be present in the Thrift clientContetwittert")
    )
    val queryFut = buildCrCandidateGeneratorQuery(args.request, requestUUID, userId)
    queryFut.flatMap { query =>
      val scribeMetadata = ScribeMetadata.from(query)
      endpointLoadShedder(endpointName, query.product.originalName) {

        val response = crCandidateGenerator.get(query)

        val blueVerifiedScribedResponse = response.flatMap { rankedCandidates =>
          val hasBlueVerifiedCandidate = rankedCandidates.etwitterists { tweet =>
            tweet.tweetInfo.hasBlueVerifiedAnnotation.contains(true)
          }

          if (hasBlueVerifiedCandidate) {
            crMitwittererScribeLogger.scribeGetTweetRecommendationsForBlueVerified(
              scribeMetadata,
              response)
          } else {
            response
          }
        }

        val thriftResponse = blueVerifiedScribedResponse.map { candidates =>
          if (query.product == t.Product.Home) {
            scribeTweetScoreFunnelSeries(candidates)
          }
          buildThriftResponse(candidates)
        }

        cacheTweetRecommendationResults(args.request, thriftResponse)

        crMitwittererScribeLogger.scribeGetTweetRecommendations(
          args.request,
          startTime,
          scribeMetadata,
          thriftResponse)
      }.rescue {
        case EndpointLoadShedder.LoadSheddingEtwitterception =>
          Future(CrMitwittererTweetResponse(Seq.empty))
        case e =>
          logErrMessage(endpointName, e)
          Future(CrMitwittererTweetResponse(Seq.empty))
      }
    }

  }

  /** *
   * GetRelatedTweetsForQueryTweet and GetRelatedTweetsForQueryAuthor are essentially
   * doing very similar things, etwittercept that one passes in TweetId which calls TweetBased engine,
   * and the other passes in AuthorId which calls ProducerBased engine.
   */
  handle(t.CrMitwitterer.GetRelatedTweetsForQueryTweet) {
    args: t.CrMitwitterer.GetRelatedTweetsForQueryTweet.Args =>
      val endpointName = "getRelatedTweetsForQueryTweet"
      getRelatedTweets(endpointName, args.request)
  }

  handle(t.CrMitwitterer.GetRelatedVideoTweetsForQueryTweet) {
    args: t.CrMitwitterer.GetRelatedVideoTweetsForQueryTweet.Args =>
      val endpointName = "getRelatedVideoTweetsForQueryVideoTweet"
      getRelatedVideoTweets(endpointName, args.request)

  }

  handle(t.CrMitwitterer.GetRelatedTweetsForQueryAuthor) {
    args: t.CrMitwitterer.GetRelatedTweetsForQueryAuthor.Args =>
      val endpointName = "getRelatedTweetsForQueryAuthor"
      getRelatedTweets(endpointName, args.request)
  }

  private def getRelatedTweets(
    endpointName: String,
    request: RelatedTweetRequest
  ): Future[RelatedTweetResponse] = {
    val requestUUID = generateRequestUUID()
    val startTime = Time.now.inMilliseconds
    val queryFut = buildRelatedTweetQuery(request, requestUUID)

    queryFut.flatMap { query =>
      val relatedTweetScribeMetadata = RelatedTweetScribeMetadata.from(query)
      endpointLoadShedder(endpointName, query.product.originalName) {
        relatedTweetScribeLogger.scribeGetRelatedTweets(
          request,
          startTime,
          relatedTweetScribeMetadata,
          relatedTweetCandidateGenerator
            .get(query)
            .map(buildRelatedTweetResponse))
      }.rescue {
        case EndpointLoadShedder.LoadSheddingEtwitterception =>
          Future(RelatedTweetResponse(Seq.empty))
        case e =>
          logErrMessage(endpointName, e)
          Future(RelatedTweetResponse(Seq.empty))
      }
    }

  }

  private def getRelatedVideoTweets(
    endpointName: String,
    request: RelatedVideoTweetRequest
  ): Future[RelatedVideoTweetResponse] = {
    val requestUUID = generateRequestUUID()
    val queryFut = buildRelatedVideoTweetQuery(request, requestUUID)

    queryFut.flatMap { query =>
      endpointLoadShedder(endpointName, query.product.originalName) {
        relatedVideoTweetCandidateGenerator.get(query).map { initialCandidateSeq =>
          buildRelatedVideoTweetResponse(initialCandidateSeq)
        }
      }.rescue {
        case EndpointLoadShedder.LoadSheddingEtwitterception =>
          Future(RelatedVideoTweetResponse(Seq.empty))
        case e =>
          logErrMessage(endpointName, e)
          Future(RelatedVideoTweetResponse(Seq.empty))
      }
    }
  }

  handle(t.CrMitwitterer.GetFrsBasedTweetRecommendations) {
    args: t.CrMitwitterer.GetFrsBasedTweetRecommendations.Args =>
      val endpointName = "getFrsBasedTweetRecommendations"

      val requestUUID = generateRequestUUID()
      val queryFut = buildFrsBasedTweetQuery(args.request, requestUUID)
      queryFut.flatMap { query =>
        endpointLoadShedder(endpointName, query.product.originalName) {
          frsTweetCandidateGenerator.get(query).map(FrsTweetResponse(_))
        }.rescue {
          case e =>
            logErrMessage(endpointName, e)
            Future(FrsTweetResponse(Seq.empty))
        }
      }
  }

  handle(t.CrMitwitterer.GetTopicTweetRecommendations) {
    args: t.CrMitwitterer.GetTopicTweetRecommendations.Args =>
      val endpointName = "getTopicTweetRecommendations"

      val requestUUID = generateRequestUUID()
      val query = buildTopicTweetQuery(args.request, requestUUID)

      endpointLoadShedder(endpointName, query.product.originalName) {
        topicTweetCandidateGenerator.get(query).map(TopicTweetResponse(_))
      }.rescue {
        case e =>
          logErrMessage(endpointName, e)
          Future(TopicTweetResponse(Map.empty[Long, Seq[TopicTweet]]))
      }
  }

  handle(t.CrMitwitterer.GetUtegTweetRecommendations) {
    args: t.CrMitwitterer.GetUtegTweetRecommendations.Args =>
      val endpointName = "getUtegTweetRecommendations"

      val requestUUID = generateRequestUUID()
      val startTime = Time.now.inMilliseconds
      val queryFut = buildUtegTweetQuery(args.request, requestUUID)
      queryFut
        .flatMap { query =>
          val scribeMetadata = ScribeMetadata.from(query)
          endpointLoadShedder(endpointName, query.product.originalName) {
            utegTweetScribeLogger.scribeGetUtegTweetRecommendations(
              args.request,
              startTime,
              scribeMetadata,
              utegTweetCandidateGenerator
                .get(query)
                .map(buildUtegTweetResponse)
            )
          }.rescue {
            case e =>
              logErrMessage(endpointName, e)
              Future(UtegTweetResponse(Seq.empty))
          }
        }
  }

  handle(t.CrMitwitterer.GetAdsRecommendations) { args: t.CrMitwitterer.GetAdsRecommendations.Args =>
    val endpointName = "getAdsRecommendations"
    val queryFut = buildAdsCandidateGeneratorQuery(args.request)
    val startTime = Time.now.inMilliseconds
    queryFut.flatMap { query =>
      {
        val scribeMetadata = ScribeMetadata.from(query)
        val response = adsCandidateGenerator
          .get(query).map { candidates =>
            buildAdsResponse(candidates)
          }
        adsRecommendationsScribeLogger.scribeGetAdsRecommendations(
          args.request,
          startTime,
          scribeMetadata,
          response,
          query.params(AdsParams.EnableScribe)
        )
      }.rescue {
        case e =>
          logErrMessage(endpointName, e)
          Future(AdsResponse(Seq.empty))
      }
    }

  }

  private def buildCrCandidateGeneratorQuery(
    thriftRequest: CrMitwittererTweetRequest,
    requestUUID: Long,
    userId: Long
  ): Future[CrCandidateGeneratorQuery] = {

    val product = thriftRequest.product
    val productContetwittert = thriftRequest.productContetwittert
    val scopedStats = statsReceiver
      .scope(product.toString).scope("CrMitwittererTweetRequest")

    userStateStore
      .get(userId).map { userStateOpt =>
        val userState = userStateOpt
          .getOrElse(UserState.EnumUnknownUserState(100))
        scopedStats.scope("UserState").counter(userState.toString).incr()

        val params =
          paramsBuilder.buildFromClientContetwittert(
            thriftRequest.clientContetwittert,
            thriftRequest.product,
            userState
          )

        // Specify product-specific behavior mapping here
        val matwitterNumResults = (product, productContetwittert) match {
          case (t.Product.Home, Some(t.ProductContetwittert.HomeContetwittert(homeContetwittert))) =>
            homeContetwittert.matwitterResults.getOrElse(9999)
          case (t.Product.Notifications, Some(t.ProductContetwittert.NotificationsContetwittert(ctwittert))) =>
            params(GlobalParams.MatwitterCandidatesPerRequestParam)
          case (t.Product.Email, None) =>
            params(GlobalParams.MatwitterCandidatesPerRequestParam)
          case (t.Product.ImmersiveMediaViewer, None) =>
            params(GlobalParams.MatwitterCandidatesPerRequestParam)
          case (t.Product.VideoCarousel, None) =>
            params(GlobalParams.MatwitterCandidatesPerRequestParam)
          case _ =>
            throw new IllegalArgumentEtwitterception(
              s"Product ${product} and ProductContetwittert ${productContetwittert} are not allowed in CrMitwitterer"
            )
        }

        CrCandidateGeneratorQuery(
          userId = userId,
          product = product,
          userState = userState,
          matwitterNumResults = matwitterNumResults,
          impressedTweetList = thriftRequest.etwittercludedTweetIds.getOrElse(Nil).toSet,
          params = params,
          requestUUID = requestUUID,
          languageCode = thriftRequest.clientContetwittert.languageCode
        )
      }
  }

  private def buildRelatedTweetQuery(
    thriftRequest: RelatedTweetRequest,
    requestUUID: Long
  ): Future[RelatedTweetCandidateGeneratorQuery] = {

    val product = thriftRequest.product
    val scopedStats = statsReceiver
      .scope(product.toString).scope("RelatedTweetRequest")
    val userStateFut: Future[UserState] = (thriftRequest.clientContetwittert.userId match {
      case Some(userId) => userStateStore.get(userId)
      case None => Future.value(Some(UserState.EnumUnknownUserState(100)))
    }).map(_.getOrElse(UserState.EnumUnknownUserState(100)))

    userStateFut.map { userState =>
      scopedStats.scope("UserState").counter(userState.toString).incr()
      val params =
        paramsBuilder.buildFromClientContetwittert(
          thriftRequest.clientContetwittert,
          thriftRequest.product,
          userState)

      // Specify product-specific behavior mapping here
      // Currently, Home takes 10, and RUtwitter takes 100
      val matwitterNumResults = params(RelatedTweetGlobalParams.MatwitterCandidatesPerRequestParam)

      RelatedTweetCandidateGeneratorQuery(
        internalId = thriftRequest.internalId,
        clientContetwittert = thriftRequest.clientContetwittert,
        product = product,
        matwitterNumResults = matwitterNumResults,
        impressedTweetList = thriftRequest.etwittercludedTweetIds.getOrElse(Nil).toSet,
        params = params,
        requestUUID = requestUUID
      )
    }
  }

  private def buildAdsCandidateGeneratorQuery(
    thriftRequest: AdsRequest
  ): Future[AdsCandidateGeneratorQuery] = {
    val userId = thriftRequest.clientContetwittert.userId.getOrElse(
      throw new IllegalArgumentEtwitterception("userId must be present in the Thrift clientContetwittert")
    )
    val product = thriftRequest.product
    val requestUUID = generateRequestUUID()
    userStateStore
      .get(userId).map { userStateOpt =>
        val userState = userStateOpt
          .getOrElse(UserState.EnumUnknownUserState(100))
        val params =
          paramsBuilder.buildFromClientContetwittert(
            thriftRequest.clientContetwittert,
            thriftRequest.product,
            userState)
        val matwitterNumResults = params(AdsParams.AdsCandidateGenerationMatwitterCandidatesNumParam)
        AdsCandidateGeneratorQuery(
          userId = userId,
          product = product,
          userState = userState,
          params = params,
          matwitterNumResults = matwitterNumResults,
          requestUUID = requestUUID
        )
      }
  }

  private def buildRelatedVideoTweetQuery(
    thriftRequest: RelatedVideoTweetRequest,
    requestUUID: Long
  ): Future[RelatedVideoTweetCandidateGeneratorQuery] = {

    val product = thriftRequest.product
    val scopedStats = statsReceiver
      .scope(product.toString).scope("RelatedVideoTweetRequest")
    val userStateFut: Future[UserState] = (thriftRequest.clientContetwittert.userId match {
      case Some(userId) => userStateStore.get(userId)
      case None => Future.value(Some(UserState.EnumUnknownUserState(100)))
    }).map(_.getOrElse(UserState.EnumUnknownUserState(100)))

    userStateFut.map { userState =>
      scopedStats.scope("UserState").counter(userState.toString).incr()
      val params =
        paramsBuilder.buildFromClientContetwittert(
          thriftRequest.clientContetwittert,
          thriftRequest.product,
          userState)

      val matwitterNumResults = params(RelatedVideoTweetGlobalParams.MatwitterCandidatesPerRequestParam)

      RelatedVideoTweetCandidateGeneratorQuery(
        internalId = thriftRequest.internalId,
        clientContetwittert = thriftRequest.clientContetwittert,
        product = product,
        matwitterNumResults = matwitterNumResults,
        impressedTweetList = thriftRequest.etwittercludedTweetIds.getOrElse(Nil).toSet,
        params = params,
        requestUUID = requestUUID
      )
    }

  }

  private def buildUtegTweetQuery(
    thriftRequest: UtegTweetRequest,
    requestUUID: Long
  ): Future[UtegTweetCandidateGeneratorQuery] = {

    val userId = thriftRequest.clientContetwittert.userId.getOrElse(
      throw new IllegalArgumentEtwitterception("userId must be present in the Thrift clientContetwittert")
    )
    val product = thriftRequest.product
    val productContetwittert = thriftRequest.productContetwittert
    val scopedStats = statsReceiver
      .scope(product.toString).scope("UtegTweetRequest")

    userStateStore
      .get(userId).map { userStateOpt =>
        val userState = userStateOpt
          .getOrElse(UserState.EnumUnknownUserState(100))
        scopedStats.scope("UserState").counter(userState.toString).incr()

        val params =
          paramsBuilder.buildFromClientContetwittert(
            thriftRequest.clientContetwittert,
            thriftRequest.product,
            userState
          )

        // Specify product-specific behavior mapping here
        val matwitterNumResults = (product, productContetwittert) match {
          case (t.Product.Home, Some(t.ProductContetwittert.HomeContetwittert(homeContetwittert))) =>
            homeContetwittert.matwitterResults.getOrElse(9999)
          case _ =>
            throw new IllegalArgumentEtwitterception(
              s"Product ${product} and ProductContetwittert ${productContetwittert} are not allowed in CrMitwitterer"
            )
        }

        UtegTweetCandidateGeneratorQuery(
          userId = userId,
          product = product,
          userState = userState,
          matwitterNumResults = matwitterNumResults,
          impressedTweetList = thriftRequest.etwittercludedTweetIds.getOrElse(Nil).toSet,
          params = params,
          requestUUID = requestUUID
        )
      }

  }

  private def buildTopicTweetQuery(
    thriftRequest: TopicTweetRequest,
    requestUUID: Long
  ): TopicTweetCandidateGeneratorQuery = {
    val userId = thriftRequest.clientContetwittert.userId.getOrElse(
      throw new IllegalArgumentEtwitterception(
        "userId must be present in the TopicTweetRequest clientContetwittert")
    )
    val product = thriftRequest.product
    val productContetwittert = thriftRequest.productContetwittert

    // Specify product-specific behavior mapping here
    val isVideoOnly = (product, productContetwittert) match {
      case (t.Product.EtwitterploreTopics, Some(t.ProductContetwittert.EtwitterploreContetwittert(contetwittert))) =>
        contetwittert.isVideoOnly
      case (t.Product.TopicLandingPage, None) =>
        false
      case (t.Product.HomeTopicsBackfill, None) =>
        false
      case (t.Product.TopicTweetsStrato, None) =>
        false
      case _ =>
        throw new IllegalArgumentEtwitterception(
          s"Product ${product} and ProductContetwittert ${productContetwittert} are not allowed in CrMitwitterer"
        )
    }

    statsReceiver.scope(product.toString).counter(TopicTweetRequest.toString).incr()

    val params =
      paramsBuilder.buildFromClientContetwittert(
        thriftRequest.clientContetwittert,
        product,
        UserState.EnumUnknownUserState(100)
      )

    val topicIds = thriftRequest.topicIds.map { topicId =>
      TopicId(
        entityId = topicId,
        language = thriftRequest.clientContetwittert.languageCode,
        country = None
      )
    }.toSet

    TopicTweetCandidateGeneratorQuery(
      userId = userId,
      topicIds = topicIds,
      product = product,
      matwitterNumResults = params(TopicTweetParams.MatwitterTopicTweetCandidatesParam),
      impressedTweetList = thriftRequest.etwittercludedTweetIds.getOrElse(Nil).toSet,
      params = params,
      requestUUID = requestUUID,
      isVideoOnly = isVideoOnly
    )
  }

  private def buildFrsBasedTweetQuery(
    thriftRequest: FrsTweetRequest,
    requestUUID: Long
  ): Future[FrsTweetCandidateGeneratorQuery] = {
    val userId = thriftRequest.clientContetwittert.userId.getOrElse(
      throw new IllegalArgumentEtwitterception(
        "userId must be present in the FrsTweetRequest clientContetwittert")
    )
    val product = thriftRequest.product
    val productContetwittert = thriftRequest.productContetwittert

    val scopedStats = statsReceiver
      .scope(product.toString).scope("FrsTweetRequest")

    userStateStore
      .get(userId).map { userStateOpt =>
        val userState = userStateOpt
          .getOrElse(UserState.EnumUnknownUserState(100))
        scopedStats.scope("UserState").counter(userState.toString).incr()

        val params =
          paramsBuilder.buildFromClientContetwittert(
            thriftRequest.clientContetwittert,
            thriftRequest.product,
            userState
          )
        val matwitterNumResults = (product, productContetwittert) match {
          case (t.Product.Home, Some(t.ProductContetwittert.HomeContetwittert(homeContetwittert))) =>
            homeContetwittert.matwitterResults.getOrElse(
              params(FrsBasedCandidateGenerationMatwitterCandidatesNumParam))
          case _ =>
            params(FrsBasedCandidateGenerationMatwitterCandidatesNumParam)
        }

        FrsTweetCandidateGeneratorQuery(
          userId = userId,
          product = product,
          matwitterNumResults = matwitterNumResults,
          impressedTweetList = thriftRequest.etwittercludedTweetIds.getOrElse(Nil).toSet,
          impressedUserList = thriftRequest.etwittercludedUserIds.getOrElse(Nil).toSet,
          params = params,
          languageCodeOpt = thriftRequest.clientContetwittert.languageCode,
          countryCodeOpt = thriftRequest.clientContetwittert.countryCode,
          requestUUID = requestUUID
        )
      }
  }

  private def buildThriftResponse(
    candidates: Seq[RankedCandidate]
  ): CrMitwittererTweetResponse = {

    val tweets = candidates.map { candidate =>
      TweetRecommendation(
        tweetId = candidate.tweetId,
        score = candidate.predictionScore,
        metricTags = Some(MetricTagUtil.buildMetricTags(candidate)),
        latestSourceSignalTimestampInMillis =
          SignalTimestampStatsUtil.buildLatestSourceSignalTimestamp(candidate)
      )
    }
    signalTimestampStatsUtil.statsSignalTimestamp(tweets)
    CrMitwittererTweetResponse(tweets)
  }

  private def scribeTweetScoreFunnelSeries(
    candidates: Seq[RankedCandidate]
  ): Seq[RankedCandidate] = {
    // 202210210901 is a random number for code search of Lensview
    tweetScoreFunnelSeries.startNewSpan(
      name = "GetTweetRecommendationsTopLevelTweetSimilarityEngineType",
      codePtr = 202210210901L) {
      (
        candidates,
        candidates.map { candidate =>
          thriftlog.TweetDimensionMeasure(
            dimension = Some(
              thriftlog
                .RequestTweetDimension(
                  candidate.tweetId,
                  candidate.reasonChosen.similarityEngineInfo.similarityEngineType.value)),
            measure = Some(thriftlog.RequestTweetMeasure(candidate.predictionScore))
          )
        }
      )
    }
  }

  private def buildRelatedTweetResponse(candidates: Seq[InitialCandidate]): RelatedTweetResponse = {
    val tweets = candidates.map { candidate =>
      RelatedTweet(
        tweetId = candidate.tweetId,
        score = Some(candidate.getSimilarityScore),
        authorId = Some(candidate.tweetInfo.authorId)
      )
    }
    RelatedTweetResponse(tweets)
  }

  private def buildRelatedVideoTweetResponse(
    candidates: Seq[InitialCandidate]
  ): RelatedVideoTweetResponse = {
    val tweets = candidates.map { candidate =>
      RelatedVideoTweet(
        tweetId = candidate.tweetId,
        score = Some(candidate.getSimilarityScore)
      )
    }
    RelatedVideoTweetResponse(tweets)
  }

  private def buildUtegTweetResponse(
    candidates: Seq[TweetWithScoreAndSocialProof]
  ): UtegTweetResponse = {
    val tweets = candidates.map { candidate =>
      UtegTweet(
        tweetId = candidate.tweetId,
        score = candidate.score,
        socialProofByType = candidate.socialProofByType
      )
    }
    UtegTweetResponse(tweets)
  }

  private def buildAdsResponse(
    candidates: Seq[RankedAdsCandidate]
  ): AdsResponse = {
    AdsResponse(ads = candidates.map { candidate =>
      AdTweetRecommendation(
        tweetId = candidate.tweetId,
        score = candidate.predictionScore,
        lineItems = Some(candidate.lineItemInfo))
    })
  }

  private def cacheTweetRecommendationResults(
    request: CrMitwittererTweetRequest,
    response: Future[CrMitwittererTweetResponse]
  ): Unit = {

    val userId = request.clientContetwittert.userId.getOrElse(
      throw new IllegalArgumentEtwitterception(
        "userId must be present in getTweetRecommendations() Thrift clientContetwittert"))

    if (decider.isAvailableForId(userId, DeciderConstants.getTweetRecommendationsCacheRate)) {
      response.map { crMitwittererTweetResponse =>
        {
          (
            request.product,
            request.clientContetwittert.userId,
            crMitwittererTweetResponse.tweets.nonEmpty) match {
            case (t.Product.Home, Some(userId), true) =>
              tweetRecommendationResultsStore.put((userId, crMitwittererTweetResponse))
            case _ => Future.value(Unit)
          }
        }
      }
    }
  }
}
