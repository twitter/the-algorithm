package com.twitter.servo

import com.twitter.servo.util.FutureArrow

package object request {

  /**
   * RequestFilters provide a mechanism for composing a chain of actions
   * (e.g. logging, authentication, replication, etc) to be performed per
   * request. The intention is for a series of RequestFilters are terminated in a
   * RequestHandler, which returns an object of some response type.
   *
   * Upon completion of a filter's work, the convention is to either:
   *
   * a) Return a Future of a request object of type `A` to be passed to the next
   *    member of the filter/handler chain.
   * b) Return a Future response outright in cases where request handling must
   *    be halted at the current filter (i.e. returning `Future.exception(...)`.
   *
   * @tparam A
   *   A type encapsulating all context and data required to satisfy a request.
   */
  type RequestFilter[A] = FutureArrow[A, A]

  /**
   * A handler of requests parameterized on the request and response types.
   *
   * @tparam A
   *   A type encapsulating all context and data required to satisfy a request.
   *
   * @tparam B
   *   A response type.
   */
  type RequestHandler[-A, +B] = FutureArrow[A, B]
}
