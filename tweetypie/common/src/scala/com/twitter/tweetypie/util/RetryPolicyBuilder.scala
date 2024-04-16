package com.tw ter.t etyp e.ut l

 mport com.tw ter.f nagle.Backoff
 mport com.tw ter.f nagle.serv ce.RetryPol cy
 mport com.tw ter.f nagle.serv ce.RetryPol cy.RetryableWr eExcept on
 mport com.tw ter.servo.except on.thr ftscala.ServerError
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.T  outExcept on
 mport com.tw ter.ut l.Try

object RetryPol cyBu lder {

  /**
   * Retry on any except on.
   */
  def anyFa lure[A](backoffs: Stream[Durat on]): RetryPol cy[Try[A]] =
    RetryPol cy.backoff[Try[A]](Backoff.fromStream(backoffs)) {
      case Throw(_) => true
    }

  /**
   * Retry on com.tw ter.ut l.T  outExcept on
   */
  def t  outs[A](backoffs: Stream[Durat on]): RetryPol cy[Try[A]] =
    RetryPol cy.backoff[Try[A]](Backoff.fromStream(backoffs)) {
      case Throw(_: T  outExcept on) => true
    }

  /**
   * Retry on com.tw ter.f nagle.serv ce.RetryableWr eExcept ons
   */
  def wr es[A](backoffs: Stream[Durat on]): RetryPol cy[Try[A]] =
    RetryPol cy.backoff[Try[A]](Backoff.fromStream(backoffs)) {
      case Throw(RetryableWr eExcept on(_)) => true
    }

  /**
   * Retry on com.tw ter.servo.except on.thr ftscala.ServerError
   */
  def servoServerError[A](backoffs: Stream[Durat on]): RetryPol cy[Try[A]] =
    RetryPol cy.backoff[Try[A]](Backoff.fromStream(backoffs)) {
      case Throw(ServerError(_)) => true
    }
}
