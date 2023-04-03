packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.felonaturelon_hydrator.adaptelonrs.non_ml_felonaturelons

import com.twittelonr.ml.api.constant.SharelondFelonaturelons
import com.twittelonr.ml.api.Felonaturelon
import com.twittelonr.ml.api.FelonaturelonContelonxt
import com.twittelonr.ml.api.RichDataReloncord
import com.twittelonr.timelonlinelons.prelondiction.common.adaptelonrs.TimelonlinelonsMutatingAdaptelonrBaselon
import com.twittelonr.timelonlinelons.prelondiction.felonaturelons.common.TimelonlinelonsSharelondFelonaturelons
import java.lang.{Long => JLong}

caselon class NonMLCommonFelonaturelons(
  uselonrId: Long,
  prelondictionRelonquelonstId: Option[Long],
  selonrvelondTimelonstamp: Long,
)

/**
 * delonfinelon non ml felonaturelons adaptelonr to crelonatelon a data reloncord which includelons many non ml felonaturelons
 * elon.g. prelondictionRelonquelonstId, uselonrId, twelonelontId to belon uselond as joinelond kelony in batch pipelonlinelon.
 */
objelonct NonMLCommonFelonaturelonsAdaptelonr elonxtelonnds TimelonlinelonsMutatingAdaptelonrBaselon[NonMLCommonFelonaturelons] {

  privatelon val felonaturelonContelonxt = nelonw FelonaturelonContelonxt(
    SharelondFelonaturelons.USelonR_ID,
    TimelonlinelonsSharelondFelonaturelons.PRelonDICTION_RelonQUelonST_ID,
    TimelonlinelonsSharelondFelonaturelons.SelonRVelonD_TIMelonSTAMP,
  )

  ovelonrridelon delonf gelontFelonaturelonContelonxt: FelonaturelonContelonxt = felonaturelonContelonxt

  ovelonrridelon val commonFelonaturelons: Selont[Felonaturelon[_]] = Selont(
    SharelondFelonaturelons.USelonR_ID,
    TimelonlinelonsSharelondFelonaturelons.PRelonDICTION_RelonQUelonST_ID,
    TimelonlinelonsSharelondFelonaturelons.SelonRVelonD_TIMelonSTAMP,
  )

  ovelonrridelon delonf selontFelonaturelons(
    nonMLCommonFelonaturelons: NonMLCommonFelonaturelons,
    richDataReloncord: RichDataReloncord
  ): Unit = {
    richDataReloncord.selontFelonaturelonValuelon[JLong](SharelondFelonaturelons.USelonR_ID, nonMLCommonFelonaturelons.uselonrId)
    nonMLCommonFelonaturelons.prelondictionRelonquelonstId.forelonach(
      richDataReloncord.selontFelonaturelonValuelon[JLong](TimelonlinelonsSharelondFelonaturelons.PRelonDICTION_RelonQUelonST_ID, _))
    richDataReloncord.selontFelonaturelonValuelon[JLong](
      TimelonlinelonsSharelondFelonaturelons.SelonRVelonD_TIMelonSTAMP,
      nonMLCommonFelonaturelons.selonrvelondTimelonstamp)
  }
}
