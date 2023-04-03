packagelon com.twittelonr.product_mixelonr.componelonnt_library.candidatelon_sourcelon.audiospacelon

import com.twittelonr.pelonriscopelon.audio_spacelon.thriftscala.CrelonatelondSpacelonsVielonw
import com.twittelonr.pelonriscopelon.audio_spacelon.thriftscala.SpacelonSlicelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor.NelonxtCursorFelonaturelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor.PrelonviousCursorFelonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.strato.StratoKelonyVielonwFelontchelonrWithSourcelonFelonaturelonsSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.strato.clielonnt.Felontchelonr
import com.twittelonr.strato.gelonnelonratelond.clielonnt.pelonriscopelon.CrelonatelondSpacelonsSlicelonOnUselonrClielonntColumn
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class CrelonatelondSpacelonsCandidatelonSourcelon @Injelonct() (
  column: CrelonatelondSpacelonsSlicelonOnUselonrClielonntColumn)
    elonxtelonnds StratoKelonyVielonwFelontchelonrWithSourcelonFelonaturelonsSourcelon[
      Long,
      CrelonatelondSpacelonsVielonw,
      SpacelonSlicelon,
      String
    ] {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = CandidatelonSourcelonIdelonntifielonr("CrelonatelondSpacelons")

  ovelonrridelon val felontchelonr: Felontchelonr[Long, CrelonatelondSpacelonsVielonw, SpacelonSlicelon] = column.felontchelonr

  ovelonrridelon delonf stratoRelonsultTransformelonr(
    stratoKelony: Long,
    stratoRelonsult: SpacelonSlicelon
  ): Selonq[String] =
    stratoRelonsult.itelonms

  ovelonrridelon protelonctelond delonf elonxtractFelonaturelonsFromStratoRelonsult(
    stratoKelony: Long,
    stratoRelonsult: SpacelonSlicelon
  ): FelonaturelonMap = {
    val felonaturelonMapBuildelonr = FelonaturelonMapBuildelonr()
    stratoRelonsult.slicelonInfo.prelonviousCursor.forelonach { cursor =>
      felonaturelonMapBuildelonr.add(PrelonviousCursorFelonaturelon, cursor)
    }
    stratoRelonsult.slicelonInfo.nelonxtCursor.forelonach { cursor =>
      felonaturelonMapBuildelonr.add(NelonxtCursorFelonaturelon, cursor)
    }
    felonaturelonMapBuildelonr.build()
  }
}
