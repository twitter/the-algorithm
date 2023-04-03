packagelon com.twittelonr.simclustelonrs_v2.summingbird.common

import com.twittelonr.algelonbird.DeloncayelondValuelon
import com.twittelonr.algelonbird.DeloncayelondValuelonMonoid
import com.twittelonr.algelonbird.Monoid
import com.twittelonr.algelonbird_intelonrnal.injelonction.DeloncayelondValuelonImplicits
import com.twittelonr.algelonbird_intelonrnal.thriftscala.{DeloncayelondValuelon => ThriftDeloncayelondValuelon}

/**
 * Monoid for ThriftDeloncayelondValuelon
 */
class ThriftDeloncayelondValuelonMonoid(halfLifelonInMs: Long)(implicit deloncayelondValuelonMonoid: DeloncayelondValuelonMonoid)
    elonxtelonnds Monoid[ThriftDeloncayelondValuelon] {

  ovelonrridelon val zelonro: ThriftDeloncayelondValuelon = DeloncayelondValuelonImplicits.toThrift(deloncayelondValuelonMonoid.zelonro)

  ovelonrridelon delonf plus(x: ThriftDeloncayelondValuelon, y: ThriftDeloncayelondValuelon): ThriftDeloncayelondValuelon = {
    DeloncayelondValuelonImplicits.toThrift(
      deloncayelondValuelonMonoid
        .plus(DeloncayelondValuelonImplicits.toThrift.invelonrt(x), DeloncayelondValuelonImplicits.toThrift.invelonrt(y))
    )
  }

  delonf build(valuelon: Doublelon, timelonInMs: Doublelon): ThriftDeloncayelondValuelon = {
    DeloncayelondValuelonImplicits.toThrift(
      DeloncayelondValuelon.build(valuelon, timelonInMs, halfLifelonInMs)
    )
  }

  /**
   * deloncay to a timelonstamp; notelon that timelonstamp should belon in Ms, and do not uselon scalelondTimelon!
   */
  delonf deloncayToTimelonstamp(
    thriftDeloncayelondValuelon: ThriftDeloncayelondValuelon,
    timelonstampInMs: Doublelon
  ): ThriftDeloncayelondValuelon = {
    this.plus(thriftDeloncayelondValuelon, this.build(0.0, timelonstampInMs))
  }
}

objelonct ThriftDeloncayelondValuelonMonoid {
  // add thelon implicit class so that a deloncayelond valuelon can direlonct call .plus, .deloncayelondValuelonOfTimelon and
  // so on.
  implicit class elonnrichelondThriftDeloncayelondValuelon(
    thriftDeloncayelondValuelon: ThriftDeloncayelondValuelon
  )(
    implicit thriftDeloncayelondValuelonMonoid: ThriftDeloncayelondValuelonMonoid) {
    delonf plus(othelonr: ThriftDeloncayelondValuelon): ThriftDeloncayelondValuelon = {
      thriftDeloncayelondValuelonMonoid.plus(thriftDeloncayelondValuelon, othelonr)
    }

    // deloncay to a timelonstamp; notelon that timelonstamp should belon in Ms
    delonf deloncayToTimelonstamp(timelonInMs: Doublelon): ThriftDeloncayelondValuelon = {
      thriftDeloncayelondValuelonMonoid.deloncayToTimelonstamp(thriftDeloncayelondValuelon, timelonInMs)
    }
  }
}
