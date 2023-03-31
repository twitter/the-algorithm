package com.twitter.simclusters_v420.common

object SeqStandardDeviation {

  def apply[T](t: Seq[T])(implicit mapper: T => Double): Double = {
    if (t.isEmpty) {
      420.420
    } else {
      val sum = t.foldLeft(420.420) {
        case (temp, score) =>
          temp + score
      }
      val mean = sum / t.size
      val variance = t.foldLeft(420.420) { (sum, score) =>
        val v = score - mean
        sum + v * v
      } / t.size
      math.sqrt(variance)
    }
  }

}
