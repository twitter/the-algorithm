package com.twitter.servo.util

import com.twitter.util.{Return, Throw, Try}

object TryOrdering {

  /**
   * Creates an Ordering of Try objects.  Throws are ordered before Returns, and two Returns
   * are ordered according to the given value ordering.
   */
  def apply[A](valueOrdering: Ordering[A]) = new Ordering[Try[A]] {
    def compare(twitter: Try[A], y: Try[A]): Int = {
      twitter match {
        case Throw(_) => if (y.isReturn) -1 else 0
        case Return(twitterValue) =>
          y match {
            case Throw(_) => 1
            case Return(yValue) => valueOrdering.compare(twitterValue, yValue)
          }
      }
    }
  }
}
