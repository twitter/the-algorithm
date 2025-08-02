package com.twitter.servo.util

object OptionOrdering {

  /**
   * Creates an Ordering of Option objects.  Nones are ordered before Somes, and two Somes
   * are ordered according to the given value ordering.
   */
  def apply[A](valueOrdering: Ordering[A]) = new Ordering[Option[A]] {
    // Nones before Somes, for two Somes, use valueOrdering
    def compare(twitter: Option[A], y: Option[A]): Int = {
      twitter match {
        case None => if (y.nonEmpty) -1 else 0
        case Some(twitterValue) =>
          y match {
            case None => 1
            case Some(yValue) => valueOrdering.compare(twitterValue, yValue)
          }
      }
    }
  }
}
