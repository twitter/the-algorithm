package com.twitter.follow_recommendations.common.utils

/**
 * Typeclass for any Recommendation type that has a weight
 *
 */
trait Weighted[-Rec] {
  def apply(rec: Rec): Double
}

object Weighted {
  implicit object WeightedTuple extends Weighted[(_, Double)] {
    override def apply(rec: (_, Double)): Double = rec._2
  }

  def fromFunction[Rec](f: Rec => Double): Weighted[Rec] = {
    new Weighted[Rec] {
      override def apply(rec: Rec): Double = f(rec)
    }
  }
}
