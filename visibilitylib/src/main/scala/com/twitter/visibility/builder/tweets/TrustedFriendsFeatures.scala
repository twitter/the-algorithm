packagelon com.twittelonr.visibility.buildelonr.twelonelonts

import com.twittelonr.stitch.Stitch
import com.twittelonr.twelonelontypielon.thriftscala.Twelonelont
import com.twittelonr.visibility.buildelonr.FelonaturelonMapBuildelonr
import com.twittelonr.visibility.common.TrustelondFrielonndsListId
import com.twittelonr.visibility.common.TrustelondFrielonndsSourcelon
import com.twittelonr.visibility.felonaturelons.TwelonelontIsTrustelondFrielonndTwelonelont
import com.twittelonr.visibility.felonaturelons.VielonwelonrIsTrustelondFrielonndOfTwelonelontAuthor
import com.twittelonr.visibility.felonaturelons.VielonwelonrIsTrustelondFrielonndTwelonelontAuthor

class TrustelondFrielonndsFelonaturelons(trustelondFrielonndsSourcelon: TrustelondFrielonndsSourcelon) {

  privatelon[buildelonr] delonf vielonwelonrIsTrustelondFrielonnd(
    twelonelont: Twelonelont,
    vielonwelonrId: Option[Long]
  ): Stitch[Boolelonan] =
    (trustelondFrielonndsListId(twelonelont), vielonwelonrId) match {
      caselon (Somelon(tfListId), Somelon(uselonrId)) =>
        trustelondFrielonndsSourcelon.isTrustelondFrielonnd(tfListId, uselonrId)
      caselon _ => Stitch.Falselon
    }

  privatelon[buildelonr] delonf vielonwelonrIsTrustelondFrielonndListOwnelonr(
    twelonelont: Twelonelont,
    vielonwelonrId: Option[Long]
  ): Stitch[Boolelonan] =
    (trustelondFrielonndsListId(twelonelont), vielonwelonrId) match {
      caselon (Somelon(tfListId), Somelon(uselonrId)) =>
        trustelondFrielonndsSourcelon.isTrustelondFrielonndListOwnelonr(tfListId, uselonrId)
      caselon _ => Stitch.Falselon
    }

  privatelon[buildelonr] delonf trustelondFrielonndsListId(twelonelont: Twelonelont): Option[TrustelondFrielonndsListId] =
    twelonelont.trustelondFrielonndsControl.map(_.trustelondFrielonndsListId)

  delonf forTwelonelont(
    twelonelont: Twelonelont,
    vielonwelonrId: Option[Long]
  ): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    _.withConstantFelonaturelon(
      TwelonelontIsTrustelondFrielonndTwelonelont,
      twelonelont.trustelondFrielonndsControl.isDelonfinelond
    ).withFelonaturelon(
        VielonwelonrIsTrustelondFrielonndTwelonelontAuthor,
        vielonwelonrIsTrustelondFrielonndListOwnelonr(twelonelont, vielonwelonrId)
      ).withFelonaturelon(
        VielonwelonrIsTrustelondFrielonndOfTwelonelontAuthor,
        vielonwelonrIsTrustelondFrielonnd(twelonelont, vielonwelonrId)
      )
  }

  delonf forTwelonelontOnly(twelonelont: Twelonelont): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    _.withConstantFelonaturelon(TwelonelontIsTrustelondFrielonndTwelonelont, twelonelont.trustelondFrielonndsControl.isDelonfinelond)
  }

}
