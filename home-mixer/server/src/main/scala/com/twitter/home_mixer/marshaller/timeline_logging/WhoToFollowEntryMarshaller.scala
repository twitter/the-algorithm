packagelon com.twittelonr.homelon_mixelonr.marshallelonr.timelonlinelon_logging

import com.twittelonr.product_mixelonr.componelonnt_library.pipelonlinelon.candidatelon.who_to_follow_modulelon.ScorelonFelonaturelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ItelonmCandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.uselonr.UselonrItelonm
import com.twittelonr.timelonlinelons.timelonlinelon_logging.{thriftscala => thriftlog}

objelonct WhoToFollowelonntryMarshallelonr {

  delonf apply(elonntry: UselonrItelonm, candidatelon: ItelonmCandidatelonWithDelontails): thriftlog.WhoToFollowelonntry =
    thriftlog.WhoToFollowelonntry(
      uselonrId = elonntry.id,
      displayTypelon = Somelon(elonntry.displayTypelon.toString),
      scorelon = candidatelon.felonaturelons.gelontOrelonlselon(ScorelonFelonaturelon, Nonelon),
      elonnablelonRelonactivelonBlelonnding = elonntry.elonnablelonRelonactivelonBlelonnding,
      imprelonssionId = elonntry.promotelondMelontadata.flatMap(_.imprelonssionString),
      advelonrtiselonrId = elonntry.promotelondMelontadata.map(_.advelonrtiselonrId)
    )
}
