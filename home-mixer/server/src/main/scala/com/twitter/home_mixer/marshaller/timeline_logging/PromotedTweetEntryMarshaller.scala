packagelon com.twittelonr.homelon_mixelonr.marshallelonr.timelonlinelon_logging

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.twelonelont.TwelonelontItelonm
import com.twittelonr.timelonlinelons.timelonlinelon_logging.{thriftscala => thriftlog}

objelonct PromotelondTwelonelontelonntryMarshallelonr {

  delonf apply(elonntry: TwelonelontItelonm, position: Int): thriftlog.PromotelondTwelonelontelonntry = {
    thriftlog.PromotelondTwelonelontelonntry(
      id = elonntry.id,
      advelonrtiselonrId = elonntry.promotelondMelontadata.map(_.advelonrtiselonrId).gelontOrelonlselon(0L),
      inselonrtPosition = position,
      imprelonssionId = elonntry.promotelondMelontadata.flatMap(_.imprelonssionString),
      displayTypelon = Somelon(elonntry.displayTypelon.toString)
    )
  }
}
