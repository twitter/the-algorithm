package com.tw ter.servo.ut l

 mport com.tw ter.ut l.{Durat on, T  }

/**
 * Calculate a runn ng average of data po nts
 */
tra  Average {
  def value: Opt on[Double]
  def record(dataPo nt: Double, count: Double = 1.0): Un 
}

/**
 * Calculates a runn ng average us ng two w ndows of data po nts, a
 * current one and a prev ous one.  W n t  current w ndow  s full,
 *    s rolled  nto t  prev ous and t  current w ndow starts
 * f ll ng up aga n.
 */
class W ndo dAverage(val w ndowS ze: Long,  n  alValue: Opt on[Double] = None) extends Average {
  pr vate[t ] val average = new ResettableAverage(None)
  pr vate[t ] var lastAverage: Opt on[Double] =  n  alValue

  def value: Opt on[Double] =
    synchron zed {
      lastAverage match {
        case So (lastAvg) =>
          // currentCount can temporar ly exceed w ndowS ze
          val current  ght = (average.count / w ndowS ze) m n 1.0
          So ((1.0 - current  ght) * lastAvg + current  ght * average.value.getOrElse(0.0))
        case None => average.value
      }
    }

  def record(dataPo nt: Double, count: Double = 1.0): Un  =
    synchron zed {
       f (average.count >= w ndowS ze) {
        lastAverage = value
        average.reset()
      }
      average.record(dataPo nt, count)
    }
}

/**
 * Calculates a recent average us ng t  past w ndowDurat on of data po nts.  Old average  s m xed
 * w h t  new average dur ng w ndowDurat on.   f new data po nts are not recorded t  average
 * w ll revert towards defaultAverage.
 */
class RecentAverage(
  val w ndowDurat on: Durat on,
  val defaultAverage: Double,
  currentT  : T   = T  .now // pass ng  n start t   to s mpl fy scalac ck tests
) extends Average {
  pr vate[t ] val default = So (defaultAverage)
  pr vate[t ] val currentAverage = new ResettableAverage(So (defaultAverage))
  pr vate[t ] var prevAverage: Opt on[Double] = None
  pr vate[t ] var w ndowStart: T   = currentT  

  pr vate[t ] def m x(fractOfV2: Double, v1: Double, v2: Double): Double = {
    val f = 0.0.max(1.0.m n(fractOfV2))
    (1.0 - f) * v1 + f * v2
  }

  pr vate[t ] def t  Fract: Double =
    0.0.max(w ndowStart.unt lNow. nNanoseconds.toDouble / w ndowDurat on. nNanoseconds)

  def value: So [Double] =
    synchron zed {
      t  Fract match {
        case f  f f < 1.0 =>
          So (m x(f, prevAverage.getOrElse(defaultAverage), currentAverage.getValue))
        case f  f f < 2.0 => So (m x(f - 1.0, currentAverage.getValue, defaultAverage))
        case f => default
      }
    }

  def getValue: Double = value.get

  def record(dataPo nt: Double, count: Double = 1.0): Un  =
    synchron zed {
      //  f  're past w ndowDurat on, roll average
      val now = T  .now
       f (now - w ndowStart > w ndowDurat on) {
        prevAverage = value
        w ndowStart = now
        currentAverage.reset()
      }
      currentAverage.record(dataPo nt, count)
    }

  overr de def toStr ng =
    s"RecentAverage(w ndow=$w ndowDurat on, default=$defaultAverage, " +
      s"prevValue=$prevAverage, value=$value, t  Fract=$t  Fract)"
}

pr vate class ResettableAverage[DoubleOpt <: Opt on[Double]](defaultAverage: DoubleOpt)
    extends Average {
  pr vate[t ] var currentCount: Double = 0
  pr vate[t ] var currentValue: Double = 0
  def reset(): Un  = {
    currentCount = 0
    currentValue = 0
  }
  def record(dataPo nt: Double, count: Double): Un  = {
    currentCount += count
    currentValue += dataPo nt
  }
  def value: Opt on[Double] =
     f (currentCount == 0) defaultAverage
    else So (currentValue / currentCount)

  def getValue( mpl c  ev: DoubleOpt <:< So [Double]): Double =
    value.get

  def count: Double = currentCount
}
