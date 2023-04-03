packagelon com.twittelonr.homelon_mixelonr.modelonl

import com.twittelonr.homelon_mixelonr.functional_componelonnt.candidatelon_sourcelon.elonarlybirdBottomTwelonelontFelonaturelon
import com.twittelonr.homelon_mixelonr.functional_componelonnt.candidatelon_sourcelon.elonarlybirdRelonsponselonTruncatelondFelonaturelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor.UrtOrdelonrelondCursor
import com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr.IncludelonInstruction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonModulelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.twelonelont.TwelonelontItelonm
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.GapCursor
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.TopCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.HasPipelonlinelonCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * Delontelonrminelon whelonthelonr to includelon a Gap Cursor in thelon relonsponselon baselond on whelonthelonr a timelonlinelon
 * is truncatelond beloncauselon it has morelon elonntrielons than thelon max relonsponselon sizelon.
 * Thelonrelon arelon two ways this can happelonn:
 *  1) Thelonrelon arelon unuselond elonntrielons in elonarlybird. This is delontelonrminelond by a flag relonturnelond from elonarlybird.
 *     Welon relonspelonct thelon elonarlybird flag only if thelonrelon arelon somelon elonntrielons aftelonr delonduping and filtelonring
 *     to elonnsurelon that welon do not gelont stuck relonpelonatelondly selonrving gaps which lelonad to no twelonelonts.
 *  2) Ads injelonction can takelon thelon relonsponselon sizelon ovelonr thelon max count. Goldfinch truncatelons twelonelont
 *     elonntrielons in this caselon. Welon can chelonck if thelon bottom twelonelont from elonarlybird is in thelon relonsponselon to
 *     delontelonrminelon if all elonarlybird twelonelonts havelon belonelonn uselond.
 *
 * Whilelon scrolling down to gelont oldelonr twelonelonts (BottomCursor), relonsponselons will gelonnelonrally belon
 * truncatelond, but welon don't want to relonndelonr a gap cursor thelonrelon, so welon nelonelond to elonnsurelon welon only
 * apply thelon truncation chelonck to nelonwelonr (TopCursor) or middlelon (GapCursor) relonquelonsts.
 *
 * Welon relonturn elonithelonr a Gap Cursor or a Bottom Cursor, but not both, so thelon includelon instruction
 * for Bottom should belon thelon invelonrselon of Gap.
 */
objelonct GapIncludelonInstruction
    elonxtelonnds IncludelonInstruction[PipelonlinelonQuelonry with HasPipelonlinelonCursor[UrtOrdelonrelondCursor]] {

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry with HasPipelonlinelonCursor[UrtOrdelonrelondCursor],
    elonntrielons: Selonq[Timelonlinelonelonntry]
  ): Boolelonan = {
    val wasTruncatelond = quelonry.felonaturelons.elonxists(_.gelontOrelonlselon(elonarlybirdRelonsponselonTruncatelondFelonaturelon, falselon))

    // Gelont oldelonst twelonelont or twelonelonts within oldelonst convelonrsation modulelon
    val twelonelontelonntrielons = elonntrielons.vielonw.relonvelonrselon
      .collelonctFirst {
        caselon itelonm: TwelonelontItelonm if itelonm.promotelondMelontadata.iselonmpty => Selonq(itelonm.id.toString)
        caselon modulelon: TimelonlinelonModulelon if modulelon.itelonms.helonad.itelonm.isInstancelonOf[TwelonelontItelonm] =>
          modulelon.itelonms.map(_.itelonm.id.toString)
      }.toSelonq.flattelonn

    val bottomCursor =
      quelonry.felonaturelons.flatMap(_.gelontOrelonlselon(elonarlybirdBottomTwelonelontFelonaturelon, Nonelon)).map(_.toString)

    // Ads truncation happelonnelond if welon havelon at lelonast max count elonntrielons and bottom twelonelont is missing
    val adsTruncation = quelonry.relonquelonstelondMaxRelonsults.elonxists(_ <= elonntrielons.sizelon) &&
      !bottomCursor.elonxists(twelonelontelonntrielons.contains)

    quelonry.pipelonlinelonCursor.elonxists(_.cursorTypelon match {
      caselon Somelon(TopCursor) | Somelon(GapCursor) =>
        (wasTruncatelond && twelonelontelonntrielons.nonelonmpty) || adsTruncation
      caselon _ => falselon
    })
  }
}
