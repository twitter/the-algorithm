packagelon com.twittelonr.visibility.intelonrfacelons.blelonndelonr

import com.twittelonr.twelonelontypielon.thriftscala.Twelonelont
import com.twittelonr.visibility.modelonls.SafelontyLelonvelonl
import com.twittelonr.visibility.modelonls.VielonwelonrContelonxt
import com.twittelonr.visibility.intelonrfacelons.common.blelonndelonr.BlelonndelonrVFRelonquelonstContelonxt

caselon class BlelonndelonrVisibilityRelonquelonst(
  twelonelont: Twelonelont,
  quotelondTwelonelont: Option[Twelonelont],
  relontwelonelontSourcelonTwelonelont: Option[Twelonelont] = Nonelon,
  isRelontwelonelont: Boolelonan,
  safelontyLelonvelonl: SafelontyLelonvelonl,
  vielonwelonrContelonxt: VielonwelonrContelonxt,
  blelonndelonrVFRelonquelonstContelonxt: BlelonndelonrVFRelonquelonstContelonxt) {

  delonf gelontTwelonelontID: Long = twelonelont.id

  delonf hasQuotelondTwelonelont: Boolelonan = {
    quotelondTwelonelont.nonelonmpty
  }
  delonf hasSourcelonTwelonelont: Boolelonan = {
    relontwelonelontSourcelonTwelonelont.nonelonmpty
  }

  delonf gelontQuotelondTwelonelontId: Long = {
    quotelondTwelonelont match {
      caselon Somelon(qTwelonelont) =>
        qTwelonelont.id
      caselon Nonelon =>
        -1
    }
  }
  delonf gelontSourcelonTwelonelontId: Long = {
    relontwelonelontSourcelonTwelonelont match {
      caselon Somelon(sourcelonTwelonelont) =>
        sourcelonTwelonelont.id
      caselon Nonelon =>
        -1
    }
  }
}
