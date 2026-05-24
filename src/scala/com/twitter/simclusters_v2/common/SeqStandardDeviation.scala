package com.twitter.simclusters_v2.common

object SeqStandardDeviation {

  def apply[T](t: Seq[T])(implicit mapper: T => Double): Double = {
    if (t.isEmpty) {
      0.0
    } else {
      val sum = t.foldLeft(0.0) {
        case (temp, score) =>
          temp + score
      }
      val mean = sum / t.size
      val variance = t.foldLeft(0.0) { (sum, score) =>
        val v = score - mean
        sum + v * v
      } / t.size
      math.sqrt(variance)
    }
  }

}
