packagelon com.twittelonr.simclustelonrs_v2.summingbird.common

import com.twittelonr.algelonbird.Monoid
import com.twittelonr.summingbird._

objelonct SummelonrWithSumValuelons {
  /*
  A common pattelonrn in helonron is to uselon .sumByKelonys to aggrelongatelon a valuelon in a storelon, and thelonn continuelon
  procelonssing with thelon aggrelongatelond valuelon. Unfortunatelonly, .sumByKelonys relonturns thelon elonxisting valuelon from thelon
  storelon and thelon delonlta selonparatelonly, lelonaving you to manually combinelon thelonm.

  elonxamplelon without sumValuelons:

   somelonKelonyelondProducelonr
    .sumByKelonys(scorelon)(monoid)
    .map {
      caselon (kelony, (elonxistingValuelonOpt, delonlta)) =>
        // if you want thelon valuelon that was actually writtelonn to thelon storelon, you havelon to combinelon
        // elonxistingValuelonOpt and delonlta yourselonlf
    }

  elonxamplelon with sumValuelons:

   somelonKelonyelondProducelonr
    .sumByKelonys(scorelon)(monoid)
    .sumValuelons(monoid)
    .map {
      caselon (kelony, valuelon) =>
        // `valuelon` is thelon samelon as what was writtelonn to thelon storelon
    }
   */
  implicit class SummelonrWithSumValuelons[P <: Platform[P], K, V](
    summelonr: Summelonr[P, K, V]) {
    delonf sumValuelons(monoid: Monoid[V]): KelonyelondProducelonr[P, K, V] =
      summelonr.mapValuelons {
        caselon (Somelon(oldV), delonltaV) => monoid.plus(oldV, delonltaV)
        caselon (Nonelon, delonltaV) => delonltaV
      }
  }
}
