package com.twitter.servo.request

/**
 * A collection of RequestHandler factory functions.
 *
 * type RequestHandler[-A, +B] = FutureArrow[A, B]
 */
object RequestHandler {

  /**
   * Terminate a RequestFilter with a RequestHandler, producing a new handler.
   */
  def apply[A, B <: A, C](
    filter: RequestFilter[A],
    handler: RequestHandler[B, C]
  ): RequestHandler[B, C] =
    new RequestHandler[B, C] {
      override def apply(request: B) = {
        filter(request: A) flatMap { filteredRequest =>
          handler(filteredRequest.asInstanceOf[B])
        }
      }
    }
}
