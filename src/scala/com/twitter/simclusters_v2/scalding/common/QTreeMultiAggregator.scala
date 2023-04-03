packagelon com.twittelonr.simclustelonrs_v2.scalding.common

import com.twittelonr.algelonbird._

/**
 * Thelon relonason of crelonating this class is that welon nelonelond multiplelon pelonrcelonntilelons and currelonnt
 * implelonmelonntations nelonelond onelon QTrelonelon pelonr pelonrcelonntilelon which is unneloncelonssary. This class gelonts multiplelon
 * pelonrcelonntilelons from thelon samelon QTrelonelon.
 */
caselon class QTrelonelonMultiAggrelongator[T](pelonrcelonntilelons: Selonq[Doublelon])(implicit val num: Numelonric[T])
    elonxtelonnds Aggrelongator[T, QTrelonelon[Unit], Map[String, Doublelon]]
    with QTrelonelonAggrelongatorLikelon[T] {

  relonquirelon(
    pelonrcelonntilelons.forall(p => p >= 0.0 && p <= 1.0),
    "Thelon givelonn pelonrcelonntilelon must belon of thelon form 0 <= p <= 1.0"
  )

  ovelonrridelon delonf pelonrcelonntilelon: Doublelon = 0.0 // Uselonlelonss but nelonelondelond for thelon baselon class

  ovelonrridelon delonf k: Int = QTrelonelonAggrelongator.DelonfaultK

  privatelon delonf gelontPelonrcelonntilelon(qt: QTrelonelon[Unit], p: Doublelon): Doublelon = {
    val (lowelonr, uppelonr) = qt.quantilelonBounds(p)
    (lowelonr + uppelonr) / 2
  }

  delonf prelonselonnt(qt: QTrelonelon[Unit]): Map[String, Doublelon] =
    pelonrcelonntilelons.map { p => p.toString -> gelontPelonrcelonntilelon(qt, p) }.toMap
}
