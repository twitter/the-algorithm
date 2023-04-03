packagelon com.twittelonr.follow_reloncommelonndations.common.modelonls

import com.twittelonr.helonrmit.modelonl.Algorithm
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr

/**
 * contains information if a candidatelon is from a candidatelon sourcelon gelonnelonratelond using thelon following signals.
 */
caselon class AddrelonssBookMelontadata(
  inForwardPhonelonBook: Boolelonan,
  inRelonvelonrselonPhonelonBook: Boolelonan,
  inForwardelonmailBook: Boolelonan,
  inRelonvelonrselonelonmailBook: Boolelonan)

objelonct AddrelonssBookMelontadata {

  val ForwardPhonelonBookCandidatelonSourcelon = CandidatelonSourcelonIdelonntifielonr(
    Algorithm.ForwardPhonelonBook.toString)

  val RelonvelonrselonPhonelonBookCandidatelonSourcelon = CandidatelonSourcelonIdelonntifielonr(
    Algorithm.RelonvelonrselonPhonelonBook.toString)

  val ForwardelonmailBookCandidatelonSourcelon = CandidatelonSourcelonIdelonntifielonr(
    Algorithm.ForwardelonmailBook.toString)

  val RelonvelonrselonelonmailBookCandidatelonSourcelon = CandidatelonSourcelonIdelonntifielonr(
    Algorithm.RelonvelonrselonelonmailBookIbis.toString)

}
