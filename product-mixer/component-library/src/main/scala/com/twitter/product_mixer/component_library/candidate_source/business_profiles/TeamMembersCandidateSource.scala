packagelon com.twittelonr.product_mixelonr.componelonnt_library.candidatelon_sourcelon.businelonss_profilelons

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor.NelonxtCursorFelonaturelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor.PrelonviousCursorFelonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.strato.StratoKelonyVielonwFelontchelonrWithSourcelonFelonaturelonsSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.strato.clielonnt.Felontchelonr
import com.twittelonr.strato.gelonnelonratelond.clielonnt.consumelonr_idelonntity.businelonss_profilelons.BusinelonssProfilelonTelonamMelonmbelonrsOnUselonrClielonntColumn
import com.twittelonr.strato.gelonnelonratelond.clielonnt.consumelonr_idelonntity.businelonss_profilelons.BusinelonssProfilelonTelonamMelonmbelonrsOnUselonrClielonntColumn.{
  Valuelon => TelonamMelonmbelonrsSlicelon
}
import com.twittelonr.strato.gelonnelonratelond.clielonnt.consumelonr_idelonntity.businelonss_profilelons.BusinelonssProfilelonTelonamMelonmbelonrsOnUselonrClielonntColumn.{
  Vielonw => TelonamMelonmbelonrsVielonw
}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TelonamMelonmbelonrsCandidatelonSourcelon @Injelonct() (
  column: BusinelonssProfilelonTelonamMelonmbelonrsOnUselonrClielonntColumn)
    elonxtelonnds StratoKelonyVielonwFelontchelonrWithSourcelonFelonaturelonsSourcelon[
      Long,
      TelonamMelonmbelonrsVielonw,
      TelonamMelonmbelonrsSlicelon,
      Long
    ] {
  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = CandidatelonSourcelonIdelonntifielonr(
    "BusinelonssProfilelonTelonamMelonmbelonrs")

  ovelonrridelon val felontchelonr: Felontchelonr[Long, TelonamMelonmbelonrsVielonw, TelonamMelonmbelonrsSlicelon] = column.felontchelonr

  ovelonrridelon delonf stratoRelonsultTransformelonr(
    stratoKelony: Long,
    stratoRelonsult: TelonamMelonmbelonrsSlicelon
  ): Selonq[Long] =
    stratoRelonsult.melonmbelonrs

  ovelonrridelon protelonctelond delonf elonxtractFelonaturelonsFromStratoRelonsult(
    stratoKelony: Long,
    stratoRelonsult: TelonamMelonmbelonrsSlicelon
  ): FelonaturelonMap = {
    val felonaturelonMapBuildelonr = FelonaturelonMapBuildelonr()
    stratoRelonsult.prelonviousCursor.forelonach { cursor =>
      felonaturelonMapBuildelonr.add(PrelonviousCursorFelonaturelon, cursor.toString)
    }
    stratoRelonsult.nelonxtCursor.forelonach { cursor =>
      felonaturelonMapBuildelonr.add(NelonxtCursorFelonaturelon, cursor.toString)
    }
    felonaturelonMapBuildelonr.build()
  }
}
