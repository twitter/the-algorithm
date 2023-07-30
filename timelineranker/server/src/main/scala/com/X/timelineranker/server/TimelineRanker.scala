package com.X.timelineranker.server

import com.X.abdecider.LoggingABDecider
import com.X.finagle.TimeoutException
import com.X.finagle.stats.Stat
import com.X.finagle.stats.StatsReceiver
import com.X.servo.util.FunctionArrow
import com.X.timelineranker.entity_tweets.EntityTweetsRepository
import com.X.timelineranker.in_network_tweets.InNetworkTweetRepository
import com.X.timelineranker.model._
import com.X.timelineranker.observe.ObservedRequests
import com.X.timelineranker.recap_author.RecapAuthorRepository
import com.X.timelineranker.recap_hydration.RecapHydrationRepository
import com.X.timelineranker.repository._
import com.X.timelineranker.uteg_liked_by_tweets.UtegLikedByTweetsRepository
import com.X.timelineranker.{thriftscala => thrift}
import com.X.timelines.authorization.TimelinesClientRequestAuthorizer
import com.X.timelines.observe.DebugObserver
import com.X.timelines.observe.ObservedAndValidatedRequests
import com.X.timelines.observe.QueryWidth
import com.X.timelines.observe.ServiceObserver
import com.X.util.Future
import com.X.util.Return
import com.X.util.Throw
import com.X.util.Try

object TimelineRanker {
  def toTimelineErrorThriftResponse(
    ex: Throwable,
    reason: Option[thrift.ErrorReason] = None
  ): thrift.GetTimelineResponse = {
    thrift.GetTimelineResponse(
      error = Some(thrift.TimelineError(message = ex.toString, reason))
    )
  }

  def getTimelinesExceptionHandler: PartialFunction[
    Throwable,
    Future[thrift.GetTimelineResponse]
  ] = {
    case e: TimeoutException =>
      Future.value(toTimelineErrorThriftResponse(e, Some(thrift.ErrorReason.UpstreamTimeout)))
    case e: Throwable if ObservedAndValidatedRequests.isOverCapacityException(e) =>
      Future.value(toTimelineErrorThriftResponse(e, Some(thrift.ErrorReason.OverCapacity)))
    case e => Future.value(toTimelineErrorThriftResponse(e))
  }

  def toErrorThriftResponse(
    ex: Throwable,
    reason: Option[thrift.ErrorReason] = None
  ): thrift.GetCandidateTweetsResponse = {
    thrift.GetCandidateTweetsResponse(
      error = Some(thrift.TimelineError(message = ex.toString, reason))
    )
  }

  def exceptionHandler: PartialFunction[Throwable, Future[thrift.GetCandidateTweetsResponse]] = {
    case e: TimeoutException =>
      Future.value(toErrorThriftResponse(e, Some(thrift.ErrorReason.UpstreamTimeout)))
    case e: Throwable if ObservedAndValidatedRequests.isOverCapacityException(e) =>
      Future.value(toErrorThriftResponse(e, Some(thrift.ErrorReason.OverCapacity)))
    case e => Future.value(toErrorThriftResponse(e))
  }
}

class TimelineRanker(
  routingRepository: RoutingTimelineRepository,
  inNetworkTweetRepository: InNetworkTweetRepository,
  recapHydrationRepository: RecapHydrationRepository,
  recapAuthorRepository: RecapAuthorRepository,
  entityTweetsRepository: EntityTweetsRepository,
  utegLikedByTweetsRepository: UtegLikedByTweetsRepository,
  serviceObserver: ServiceObserver,
  val abdecider: Option[LoggingABDecider],
  override val clientRequestAuthorizer: TimelinesClientRequestAuthorizer,
  override val debugObserver: DebugObserver,
  queryParamInitializer: FunctionArrow[RecapQuery, Future[RecapQuery]],
  statsReceiver: StatsReceiver)
    extends thrift.TimelineRanker.MethodPerEndpoint
    with ObservedRequests {

  override val requestWidthStats: Stat = statsReceiver.stat("TimelineRanker/requestWidth")

  private[this] val getTimelinesStats = serviceObserver.readMethodStats(
    "getTimelines",
    QueryWidth.one[TimelineQuery]
  )

  private[this] val getInNetworkTweetCandidatesStats = serviceObserver.readMethodStats(
    "getInNetworkTweetCandidates",
    QueryWidth.one[RecapQuery]
  )

  private[this] val hydrateTweetCandidatesStats = serviceObserver.readMethodStats(
    "hydrateTweetCandidates",
    QueryWidth.one[RecapQuery]
  )

  private[this] val getRecapCandidatesFromAuthorsStats = serviceObserver.readMethodStats(
    "getRecapCandidatesFromAuthors",
    QueryWidth.one[RecapQuery]
  )

  private[this] val getEntityTweetCandidatesStats = serviceObserver.readMethodStats(
    "getEntityTweetCandidates",
    QueryWidth.one[RecapQuery]
  )

  private[this] val getUtegLikedByTweetCandidatesStats = serviceObserver.readMethodStats(
    "getUtegLikedByTweetCandidates",
    QueryWidth.one[RecapQuery]
  )

  def getTimelines(
    thriftQueries: Seq[thrift.TimelineQuery]
  ): Future[Seq[thrift.GetTimelineResponse]] = {
    Future.collect(
      thriftQueries.map { thriftQuery =>
        Try(TimelineQuery.fromThrift(thriftQuery)) match {
          case Return(query) =>
            observeAndValidate(
              query,
              Seq(query.userId),
              getTimelinesStats,
              TimelineRanker.getTimelinesExceptionHandler) { validatedQuery =>
              routingRepository.get(validatedQuery).map { timeline =>
                thrift.GetTimelineResponse(Some(timeline.toThrift))
              }
            }
          case Throw(e) => Future.value(TimelineRanker.toTimelineErrorThriftResponse(e))
        }
      }
    )
  }

  def getRecycledTweetCandidates(
    thriftQueries: Seq[thrift.RecapQuery]
  ): Future[Seq[thrift.GetCandidateTweetsResponse]] = {
    Future.collect(
      thriftQueries.map { thriftQuery =>
        Try(RecapQuery.fromThrift(thriftQuery)) match {
          case Return(query) =>
            observeAndValidate(
              query,
              Seq(query.userId),
              getInNetworkTweetCandidatesStats,
              TimelineRanker.exceptionHandler
            ) { validatedQuery =>
              Future(queryParamInitializer(validatedQuery)).flatten.liftToTry.flatMap {
                case Return(q) => inNetworkTweetRepository.get(q).map(_.toThrift)
                case Throw(e) => Future.value(TimelineRanker.toErrorThriftResponse(e))
              }
            }
          case Throw(e) => Future.value(TimelineRanker.toErrorThriftResponse(e))
        }
      }
    )
  }

  def hydrateTweetCandidates(
    thriftQueries: Seq[thrift.RecapHydrationQuery]
  ): Future[Seq[thrift.GetCandidateTweetsResponse]] = {
    Future.collect(
      thriftQueries.map { thriftQuery =>
        Try(RecapQuery.fromThrift(thriftQuery)) match {
          case Return(query) =>
            observeAndValidate(
              query,
              Seq(query.userId),
              hydrateTweetCandidatesStats,
              TimelineRanker.exceptionHandler
            ) { validatedQuery =>
              Future(queryParamInitializer(validatedQuery)).flatten.liftToTry.flatMap {
                case Return(q) => recapHydrationRepository.hydrate(q).map(_.toThrift)
                case Throw(e) => Future.value(TimelineRanker.toErrorThriftResponse(e))
              }
            }
          case Throw(e) => Future.value(TimelineRanker.toErrorThriftResponse(e))
        }
      }
    )
  }

  def getRecapCandidatesFromAuthors(
    thriftQueries: Seq[thrift.RecapQuery]
  ): Future[Seq[thrift.GetCandidateTweetsResponse]] = {
    Future.collect(
      thriftQueries.map { thriftQuery =>
        Try(RecapQuery.fromThrift(thriftQuery)) match {
          case Return(query) =>
            observeAndValidate(
              query,
              Seq(query.userId),
              getRecapCandidatesFromAuthorsStats,
              TimelineRanker.exceptionHandler
            ) { validatedQuery =>
              Future(queryParamInitializer(validatedQuery)).flatten.liftToTry.flatMap {
                case Return(q) => recapAuthorRepository.get(q).map(_.toThrift)
                case Throw(e) => Future.value(TimelineRanker.toErrorThriftResponse(e))
              }
            }
          case Throw(e) => Future.value(TimelineRanker.toErrorThriftResponse(e))
        }
      }
    )
  }

  def getEntityTweetCandidates(
    thriftQueries: Seq[thrift.EntityTweetsQuery]
  ): Future[Seq[thrift.GetCandidateTweetsResponse]] = {
    Future.collect(
      thriftQueries.map { thriftQuery =>
        Try(RecapQuery.fromThrift(thriftQuery)) match {
          case Return(query) =>
            observeAndValidate(
              query,
              Seq(query.userId),
              getEntityTweetCandidatesStats,
              TimelineRanker.exceptionHandler
            ) { validatedQuery =>
              Future(queryParamInitializer(validatedQuery)).flatten.liftToTry.flatMap {
                case Return(q) => entityTweetsRepository.get(q).map(_.toThrift)
                case Throw(e) => Future.value(TimelineRanker.toErrorThriftResponse(e))
              }
            }
          case Throw(e) => Future.value(TimelineRanker.toErrorThriftResponse(e))
        }
      }
    )
  }

  def getUtegLikedByTweetCandidates(
    thriftQueries: Seq[thrift.UtegLikedByTweetsQuery]
  ): Future[Seq[thrift.GetCandidateTweetsResponse]] = {
    Future.collect(
      thriftQueries.map { thriftQuery =>
        Try(RecapQuery.fromThrift(thriftQuery)) match {
          case Return(query) =>
            observeAndValidate(
              query,
              Seq(query.userId),
              getUtegLikedByTweetCandidatesStats,
              TimelineRanker.exceptionHandler
            ) { validatedQuery =>
              Future(queryParamInitializer(validatedQuery)).flatten.liftToTry.flatMap {
                case Return(q) => utegLikedByTweetsRepository.get(q).map(_.toThrift)
                case Throw(e) => Future.value(TimelineRanker.toErrorThriftResponse(e))
              }
            }
          case Throw(e) => Future.value(TimelineRanker.toErrorThriftResponse(e))
        }
      }
    )
  }
}
