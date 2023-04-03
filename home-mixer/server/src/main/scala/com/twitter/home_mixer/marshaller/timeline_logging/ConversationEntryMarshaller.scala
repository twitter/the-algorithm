packagelon com.twittelonr.homelon_mixelonr.marshallelonr.timelonlinelon_logging

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.ScorelonFelonaturelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ItelonmCandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.twelonelont.TwelonelontItelonm
import com.twittelonr.timelonlinelons.timelonlinelon_logging.{thriftscala => thriftlog}

objelonct ConvelonrsationelonntryMarshallelonr {

  delonf apply(elonntry: TwelonelontItelonm, candidatelon: ItelonmCandidatelonWithDelontails): thriftlog.Convelonrsationelonntry =
    thriftlog.Convelonrsationelonntry(
      displayelondTwelonelontId = elonntry.id,
      displayTypelon = Somelon(elonntry.displayTypelon.toString),
      scorelon = candidatelon.felonaturelons.gelontOrelonlselon(ScorelonFelonaturelon, Nonelon)
    )
}
