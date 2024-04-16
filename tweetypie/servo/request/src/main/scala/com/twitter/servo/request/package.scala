package com.tw ter.servo

 mport com.tw ter.servo.ut l.FutureArrow

package object request {

  /**
   * RequestF lters prov de a  chan sm for compos ng a cha n of act ons
   * (e.g. logg ng, aut nt cat on, repl cat on, etc) to be perfor d per
   * request. T   ntent on  s for a ser es of RequestF lters are term nated  n a
   * RequestHandler, wh ch returns an object of so  response type.
   *
   * Upon complet on of a f lter's work, t  convent on  s to e  r:
   *
   * a) Return a Future of a request object of type `A` to be passed to t  next
   *     mber of t  f lter/handler cha n.
   * b) Return a Future response outr ght  n cases w re request handl ng must
   *    be halted at t  current f lter ( .e. return ng `Future.except on(...)`.
   *
   * @tparam A
   *   A type encapsulat ng all context and data requ red to sat sfy a request.
   */
  type RequestF lter[A] = FutureArrow[A, A]

  /**
   * A handler of requests para ter zed on t  request and response types.
   *
   * @tparam A
   *   A type encapsulat ng all context and data requ red to sat sfy a request.
   *
   * @tparam B
   *   A response type.
   */
  type RequestHandler[-A, +B] = FutureArrow[A, B]
}
