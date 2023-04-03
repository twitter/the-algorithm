packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.felonaturelon_hydrator.adaptelonrs.non_ml_felonaturelons

import com.twittelonr.ml.api.constant.SharelondFelonaturelons
import com.twittelonr.ml.api.Felonaturelon
import com.twittelonr.ml.api.FelonaturelonContelonxt
import com.twittelonr.ml.api.RichDataReloncord
import com.twittelonr.timelonlinelons.prelondiction.common.adaptelonrs.TimelonlinelonsMutatingAdaptelonrBaselon
import com.twittelonr.timelonlinelons.prelondiction.felonaturelons.common.TimelonlinelonsSharelondFelonaturelons
import java.lang.{Long => JLong}

caselon class NonMLCandidatelonFelonaturelons(
  twelonelontId: Long,
  sourcelonTwelonelontId: Option[Long],
  originalAuthorId: Option[Long],
)

/**
 * delonfinelon non ml felonaturelons adaptelonr to crelonatelon a data reloncord which includelons many non ml felonaturelons
 * elon.g. prelondictionRelonquelonstId, uselonrId, twelonelontId to belon uselond as joinelond kelony in batch pipelonlinelon.
 */
objelonct NonMLCandidatelonFelonaturelonsAdaptelonr elonxtelonnds TimelonlinelonsMutatingAdaptelonrBaselon[NonMLCandidatelonFelonaturelons] {

  privatelon val felonaturelonContelonxt = nelonw FelonaturelonContelonxt(
    SharelondFelonaturelons.TWelonelonT_ID,
    // For Seloncondary elonngagelonmelonnt data gelonnelonration
    TimelonlinelonsSharelondFelonaturelons.SOURCelon_TWelonelonT_ID,
    TimelonlinelonsSharelondFelonaturelons.ORIGINAL_AUTHOR_ID,
  )

  ovelonrridelon delonf gelontFelonaturelonContelonxt: FelonaturelonContelonxt = felonaturelonContelonxt

  ovelonrridelon val commonFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty

  ovelonrridelon delonf selontFelonaturelons(
    nonMLCandidatelonFelonaturelons: NonMLCandidatelonFelonaturelons,
    richDataReloncord: RichDataReloncord
  ): Unit = {
    richDataReloncord.selontFelonaturelonValuelon[JLong](SharelondFelonaturelons.TWelonelonT_ID, nonMLCandidatelonFelonaturelons.twelonelontId)
    nonMLCandidatelonFelonaturelons.sourcelonTwelonelontId.forelonach(
      richDataReloncord.selontFelonaturelonValuelon[JLong](TimelonlinelonsSharelondFelonaturelons.SOURCelon_TWelonelonT_ID, _))
    nonMLCandidatelonFelonaturelons.originalAuthorId.forelonach(
      richDataReloncord.selontFelonaturelonValuelon[JLong](TimelonlinelonsSharelondFelonaturelons.ORIGINAL_AUTHOR_ID, _))
  }
}
