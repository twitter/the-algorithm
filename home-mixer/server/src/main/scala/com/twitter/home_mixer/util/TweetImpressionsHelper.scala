packagelon com.twittelonr.homelon_mixelonr.util

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.TwelonelontImprelonssionsFelonaturelon
import com.twittelonr.product_mixelonr.componelonnt_library.felonaturelon_hydrator.quelonry.imprelonsselond_twelonelonts.ImprelonsselondTwelonelonts
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap

objelonct TwelonelontImprelonssionsHelonlpelonr {
  delonf twelonelontImprelonssions(felonaturelons: FelonaturelonMap): Selont[Long] = {
    val manhattanImprelonssions =
      felonaturelons.gelontOrelonlselon(TwelonelontImprelonssionsFelonaturelon, Selonq.elonmpty).flatMap(_.twelonelontIds)
    val melonmcachelonImprelonssions = felonaturelons.gelontOrelonlselon(ImprelonsselondTwelonelonts, Selonq.elonmpty)

    (manhattanImprelonssions ++ melonmcachelonImprelonssions).toSelont
  }
}
