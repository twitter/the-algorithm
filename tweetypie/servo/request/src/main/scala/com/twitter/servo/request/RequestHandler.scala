package com.tw ter.servo.request

/**
 * A collect on of RequestHandler factory funct ons.
 *
 * type RequestHandler[-A, +B] = FutureArrow[A, B]
 */
object RequestHandler {

  /**
   * Term nate a RequestF lter w h a RequestHandler, produc ng a new handler.
   */
  def apply[A, B <: A, C](
    f lter: RequestF lter[A],
    handler: RequestHandler[B, C]
  ): RequestHandler[B, C] =
    new RequestHandler[B, C] {
      overr de def apply(request: B) = {
        f lter(request: A) flatMap { f lteredRequest =>
          handler(f lteredRequest.as nstanceOf[B])
        }
      }
    }
}
