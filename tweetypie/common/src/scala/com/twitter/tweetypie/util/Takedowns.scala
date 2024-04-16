package com.tw ter.t etyp e.ut l

 mport com.tw ter.takedown.ut l.TakedownReasons
 mport com.tw ter.takedown.ut l.TakedownReasons.CountryCode
 mport com.tw ter.tseng.w hhold ng.thr ftscala.TakedownReason
 mport com.tw ter.tseng.w hhold ng.thr ftscala.Unspec f edReason
 mport com.tw ter.t etyp e.thr ftscala.T et

/**
 * Conta ns t etyp e-spec f c ut ls for work ng w h TakedownReasons.
 */
object Takedowns {

  type CountryCode = Str ng

  /**
   * Take a l st of [[TakedownReason]] and return values to be saved on t  [[T et]]  n f elds
   * t etyp eOnlyTakedownCountryCode and t etyp eOnlyTakedownReason.
   *
   * - t etyp eOnlyTakedownCountryCode conta ns t  country_code of all Unspec f edReasons
   * - t etyp eOnlyTakedownReason conta ns all ot r reasons
   */
  def part  onReasons(reasons: Seq[TakedownReason]): (Seq[Str ng], Seq[TakedownReason]) = {
    val (unspec f edReasons, spec f edReasons) = reasons.part  on {
      case TakedownReason.Unspec f edReason(Unspec f edReason(_)) => true
      case _ => false
    }
    val unspec f edCountryCodes = unspec f edReasons.collect(TakedownReasons.reasonToCountryCode)
    (unspec f edCountryCodes, spec f edReasons)
  }

  def fromT et(t: T et): Takedowns =
    Takedowns(
      Seq
        .concat(
          t.t etyp eOnlyTakedownCountryCodes
            .getOrElse(N l).map(TakedownReasons.countryCodeToReason),
          t.t etyp eOnlyTakedownReasons.getOrElse(N l)
        ).toSet
    )
}

/**
 * T  class  s used to ensure t  caller has access to both t  full l st of reasons as  ll
 * as t  backwards-compat ble l st of country codes.
 */
case class Takedowns(reasons: Set[TakedownReason]) {
  def countryCodes: Set[CountryCode] = reasons.collect(TakedownReasons.reasonToCountryCode)
}
