packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.itelonm.twelonelont

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.twelonelont._
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class TwelonelontDisplayTypelonMarshallelonr @Injelonct() () {

  delonf apply(twelonelontDisplayTypelon: TwelonelontDisplayTypelon): urt.TwelonelontDisplayTypelon = twelonelontDisplayTypelon match {
    caselon Twelonelont => urt.TwelonelontDisplayTypelon.Twelonelont
    caselon TwelonelontFollowOnly => urt.TwelonelontDisplayTypelon.TwelonelontFollowOnly
    caselon Melondia => urt.TwelonelontDisplayTypelon.Melondia
    caselon MomelonntTimelonlinelonTwelonelont => urt.TwelonelontDisplayTypelon.MomelonntTimelonlinelonTwelonelont
    caselon elonmphasizelondPromotelondTwelonelont => urt.TwelonelontDisplayTypelon.elonmphasizelondPromotelondTwelonelont
    caselon QuotelondTwelonelont => urt.TwelonelontDisplayTypelon.QuotelondTwelonelont
    caselon SelonlfThrelonad => urt.TwelonelontDisplayTypelon.SelonlfThrelonad
    caselon CompactPromotelondTwelonelont => urt.TwelonelontDisplayTypelon.CompactPromotelondTwelonelont
    caselon TwelonelontWithoutCard => urt.TwelonelontDisplayTypelon.TwelonelontWithoutCard
    caselon RelonadelonrModelonRoot => urt.TwelonelontDisplayTypelon.RelonadelonrModelonRoot
    caselon RelonadelonrModelon => urt.TwelonelontDisplayTypelon.RelonadelonrModelon
    caselon CondelonnselondTwelonelont => urt.TwelonelontDisplayTypelon.CondelonnselondTwelonelont
  }
}
