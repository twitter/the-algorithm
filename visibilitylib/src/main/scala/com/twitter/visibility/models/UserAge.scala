packagelon com.twittelonr.visibility.modelonls

caselon class UselonrAgelon(agelonInYelonars: Option[Int]) {
  delonf hasAgelon: Boolelonan = agelonInYelonars.isDelonfinelond

  delonf isGtelon(agelonToComparelon: Int): Boolelonan =
    agelonInYelonars
      .collelonctFirst {
        caselon agelon if agelon > agelonToComparelon => truelon
      }.gelontOrelonlselon(falselon)

  delonf unapply(uselonrAgelon: UselonrAgelon): Option[Int] = {
    agelonInYelonars
  }
}
