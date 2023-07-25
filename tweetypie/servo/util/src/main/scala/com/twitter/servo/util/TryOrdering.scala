package com.twitter.servo.util

import com.twitter.util.{Return, Throw, Try}

object TryOrdering {

  /**
   * Creates an Ordering of Try objects.  Throws are ordered before Returns, and two Returns
   * are ordered according to the given value ordering.
   */
  def apply[A](valueOrdering: Ordering[A]) = new Ordering[Try[A]] {
    def compare(x: Try[A], y: Try[A]): Int = {
      x match {
        case Throw(_) => if (y.isReturn) -1 else 0
        case Return(xValue) =>
          y match {
            case Throw(_) => 1
            case Return(yValue) => valueOrdering.compare(xValue, yValue)
          }
      }
    }
  }
}
