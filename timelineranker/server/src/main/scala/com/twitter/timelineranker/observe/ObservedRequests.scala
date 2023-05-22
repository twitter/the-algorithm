package com.twitter.timelineranker.observe

import com.twitter.timelines.authorization.ReadRequest
import com.twitter.timelines.model.UserId
import com.twitter.timelines.observe.{ObservedAndValidatedRequests, ServiceObserver, ServiceTracer}
import com.twitter.util.Future

/**
 * A trait that provides methods to observe and validate requests.
 */
trait ObservedRequests extends ObservedAndValidatedRequests {

  /**
   * Observe and validate a request.
   *
   * @param request           the request to be observed and validated
   * @param viewerIds         the IDs of the viewers who can access the request
   * @param stats             the statistics to track for the request
   * @param exceptionHandler  a partial function to handle exceptions thrown during validation or observation
   * @param f                 a function that takes a request and returns a future of the response
   * @tparam R                the type of the response
   * @tparam Q                the type of the request
   * @return a future of the response
   */
  def observeAndValidate[R, Q](
    request: Q,
    viewerIds: Seq[UserId],
    stats: ServiceObserver.Stats[Q],
    exceptionHandler: PartialFunction[Throwable, Future[R]]
  )(
    f: Q => Future[R]
  ): Future[R] = {
    super.observeAndValidate(
      request = request,
      viewerIds = viewerIds,
      accessLevel = ReadRequest,
      validator = validateRequest[Q],
      errorHandler = exceptionHandler,
      stats = stats,
      tracer = ServiceTracer.identity[Q]
    )(f)
  }

  /**
   * Validate a request.
   *
   * @param request the request to be validated
   * @tparam Q     the type of the request
   */
  def validateRequest[Q](request: Q): Unit = {
    // TimelineQuery and its derived classes do not permit invalid instances to be constructed.
    // Therefore no additional validation is required.
  }
}
