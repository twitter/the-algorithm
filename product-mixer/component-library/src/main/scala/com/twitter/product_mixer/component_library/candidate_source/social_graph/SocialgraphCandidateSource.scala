packagelon com.twittelonr.product_mixelonr.componelonnt_library.candidatelon_sourcelon.social_graph

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.CursorTypelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.NelonxtCursor
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.PrelonviousCursor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.strato.StratoKelonyVielonwFelontchelonrSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.socialgraph.thriftscala
import com.twittelonr.socialgraph.thriftscala.IdsRelonquelonst
import com.twittelonr.socialgraph.thriftscala.IdsRelonsult
import com.twittelonr.socialgraph.util.BytelonBuffelonrUtil
import com.twittelonr.strato.clielonnt.Felontchelonr
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

selonalelond trait SocialgraphRelonsponselon
caselon class SocialgraphRelonsult(id: Long) elonxtelonnds SocialgraphRelonsponselon
caselon class SocialgraphCursor(cursor: Long, cursorTypelon: CursorTypelon) elonxtelonnds SocialgraphRelonsponselon

@Singlelonton
class SocialgraphCandidatelonSourcelon @Injelonct() (
  ovelonrridelon val felontchelonr: Felontchelonr[thriftscala.IdsRelonquelonst, Option[
    thriftscala.RelonquelonstContelonxt
  ], thriftscala.IdsRelonsult])
    elonxtelonnds StratoKelonyVielonwFelontchelonrSourcelon[
      thriftscala.IdsRelonquelonst,
      Option[thriftscala.RelonquelonstContelonxt],
      thriftscala.IdsRelonsult,
      SocialgraphRelonsponselon
    ] {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = CandidatelonSourcelonIdelonntifielonr("Socialgraph")

  ovelonrridelon delonf stratoRelonsultTransformelonr(
    stratoKelony: IdsRelonquelonst,
    stratoRelonsult: IdsRelonsult
  ): Selonq[SocialgraphRelonsponselon] = {
    val prelonvCursor =
      SocialgraphCursor(BytelonBuffelonrUtil.toLong(stratoRelonsult.pagelonRelonsult.prelonvCursor), PrelonviousCursor)
    /* Whelonn an elonnd cursor is passelond to Socialgraph,
     * Socialgraph relonturns thelon start cursor. To prelonvelonnt
     * clielonnts from circularly felontching thelon timelonlinelon again,
     * if welon selonelon a start cursor relonturnelond from Socialgraph,
     * welon relonplacelon it with an elonnd cursor.
     */
    val nelonxtCursor = BytelonBuffelonrUtil.toLong(stratoRelonsult.pagelonRelonsult.nelonxtCursor) match {
      caselon SocialgraphCursorConstants.StartCursor =>
        SocialgraphCursor(SocialgraphCursorConstants.elonndCursor, NelonxtCursor)
      caselon cursor => SocialgraphCursor(cursor, NelonxtCursor)
    }

    stratoRelonsult.ids
      .map { id =>
        SocialgraphRelonsult(id)
      } ++ Selonq(nelonxtCursor, prelonvCursor)
  }
}
