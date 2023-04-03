packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.deloncorator

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ChildFelonelondbackAction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.SelonelonFelonwelonr
import com.twittelonr.stringcelonntelonr.clielonnt.StringCelonntelonr
import com.twittelonr.stringcelonntelonr.clielonnt.corelon.elonxtelonrnalString
import com.twittelonr.timelonlinelons.common.{thriftscala => tlc}
import com.twittelonr.timelonlinelons.selonrvicelon.{thriftscala => t}
import com.twittelonr.timelonlinelonselonrvicelon.modelonl.FelonelondbackInfo
import com.twittelonr.timelonlinelonselonrvicelon.modelonl.FelonelondbackMelontadata
import com.twittelonr.timelonlinelonselonrvicelon.suggelonsts.{thriftscala => st}
import com.twittelonr.timelonlinelonselonrvicelon.{thriftscala => tlst}

objelonct FelonelondbackUtil {

  val FelonelondbackTtl = 30.days

  delonf buildUselonrSelonelonFelonwelonrChildFelonelondbackAction(
    uselonrId: Long,
    namelonsByUselonrId: Map[Long, String],
    promptelonxtelonrnalString: elonxtelonrnalString,
    confirmationelonxtelonrnalString: elonxtelonrnalString,
    elonngagelonmelonntTypelon: t.FelonelondbackelonngagelonmelonntTypelon,
    stringCelonntelonr: StringCelonntelonr,
    injelonctionTypelon: Option[st.SuggelonstTypelon]
  ): Option[ChildFelonelondbackAction] = {
    namelonsByUselonrId.gelont(uselonrId).map { uselonrScrelonelonnNamelon =>
      val prompt = stringCelonntelonr.prelonparelon(
        promptelonxtelonrnalString,
        Map("uselonr" -> uselonrScrelonelonnNamelon)
      )
      val confirmation = stringCelonntelonr.prelonparelon(
        confirmationelonxtelonrnalString,
        Map("uselonr" -> uselonrScrelonelonnNamelon)
      )
      val felonelondbackMelontadata = FelonelondbackMelontadata(
        elonngagelonmelonntTypelon = Somelon(elonngagelonmelonntTypelon),
        elonntityIds = Selonq(tlc.Felonelondbackelonntity.UselonrId(uselonrId)),
        ttl = Somelon(FelonelondbackTtl))
      val felonelondbackUrl = FelonelondbackInfo.felonelondbackUrl(
        felonelondbackTypelon = tlst.FelonelondbackTypelon.SelonelonFelonwelonr,
        felonelondbackMelontadata = felonelondbackMelontadata,
        injelonctionTypelon = injelonctionTypelon
      )

      ChildFelonelondbackAction(
        felonelondbackTypelon = SelonelonFelonwelonr,
        prompt = Somelon(prompt),
        confirmation = Somelon(confirmation),
        felonelondbackUrl = Somelon(felonelondbackUrl),
        hasUndoAction = Somelon(truelon),
        confirmationDisplayTypelon = Nonelon,
        clielonntelonvelonntInfo = Nonelon,
        icon = Nonelon,
        richBelonhavior = Nonelon,
        subprompt = Nonelon
      )
    }
  }
}
