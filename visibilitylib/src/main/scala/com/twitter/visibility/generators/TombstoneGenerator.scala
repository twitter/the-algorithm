package com.tw ter.v s b l y.generators

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.ut l. mo z ngStatsRece ver
 mport com.tw ter.v s b l y.bu lder.V s b l yResult
 mport com.tw ter.v s b l y.common.act ons.TombstoneReason
 mport com.tw ter.v s b l y.conf gap .V s b l yParams
 mport com.tw ter.v s b l y.rules.Ep aph
 mport com.tw ter.v s b l y.rules.Local zedTombstone
 mport com.tw ter.v s b l y.rules.Tombstone

object TombstoneGenerator {
  def apply(
    v s b l yParams: V s b l yParams,
    countryNa Generator: CountryNa Generator,
    statsRece ver: StatsRece ver
  ): TombstoneGenerator = {
    new TombstoneGenerator(v s b l yParams, countryNa Generator, statsRece ver)
  }
}

class TombstoneGenerator(
  paramsFactory: V s b l yParams,
  countryNa Generator: CountryNa Generator,
  baseStatsRece ver: StatsRece ver) {

  pr vate[t ] val statsRece ver = new  mo z ngStatsRece ver(
    baseStatsRece ver.scope("tombstone_generator"))
  pr vate[t ] val deletedRece ver = statsRece ver.scope("deleted_state")
  pr vate[t ] val authorStateRece ver = statsRece ver.scope("t et_author_state")
  pr vate[t ] val v sResultRece ver = statsRece ver.scope("v s b l y_result")

  def apply(
    result: V s b l yResult,
    language: Str ng
  ): V s b l yResult = {

    result.verd ct match {
      case tombstone: Tombstone =>
        val ep aph = tombstone.ep aph
        v sResultRece ver.scope("tombstone").counter(ep aph.na .toLo rCase())

        val overr ddenLanguage = ep aph match {
          case Ep aph.LegalDemandsW h ld d a | Ep aph.LocalLawsW h ld d a => "en"
          case _ => language
        }

        tombstone.appl cableCountryCodes match {
          case So (countryCodes) => {
            val countryNa s = countryCodes.map(countryNa Generator.getCountryNa (_))

            result.copy(verd ct = Local zedTombstone(
              reason = ep aphToTombstoneReason(ep aph),
               ssage = Ep aphToLocal zed ssage(ep aph, overr ddenLanguage, countryNa s)))
          }
          case _ => {
            result.copy(verd ct = Local zedTombstone(
              reason = ep aphToTombstoneReason(ep aph),
               ssage = Ep aphToLocal zed ssage(ep aph, overr ddenLanguage)))
          }
        }
      case _ =>
        result
    }
  }

  pr vate def ep aphToTombstoneReason(ep aph: Ep aph): TombstoneReason = {
    ep aph match {
      case Ep aph.Deleted => TombstoneReason.Deleted
      case Ep aph.Bounced => TombstoneReason.Bounced
      case Ep aph.BounceDeleted => TombstoneReason.BounceDeleted
      case Ep aph.Protected => TombstoneReason.ProtectedAuthor
      case Ep aph.Suspended => TombstoneReason.SuspendedAuthor
      case Ep aph.BlockedBy => TombstoneReason.AuthorBlocksV e r
      case Ep aph.SuperFollowsContent => TombstoneReason.Exclus veT et
      case Ep aph.Underage => TombstoneReason.NsfwV e r sUnderage
      case Ep aph.NoStatedAge => TombstoneReason.NsfwV e rHasNoStatedAge
      case Ep aph.LoggedOutAge => TombstoneReason.NsfwLoggedOut
      case Ep aph.Deact vated => TombstoneReason.Deact vatedAuthor
      case Ep aph.Commun yT etH dden => TombstoneReason.Commun yT etH dden
      case Ep aph.Commun yT etCommun y sSuspended =>
        TombstoneReason.Commun yT etCommun y sSuspended
      case Ep aph.Develop ntOnly => TombstoneReason.Develop ntOnly
      case Ep aph.Adult d a => TombstoneReason.Adult d a
      case Ep aph.V olent d a => TombstoneReason.V olent d a
      case Ep aph.Ot rSens  ve d a => TombstoneReason.Ot rSens  ve d a
      case Ep aph.DmcaW h ld d a => TombstoneReason.DmcaW h ld d a
      case Ep aph.LegalDemandsW h ld d a => TombstoneReason.LegalDemandsW h ld d a
      case Ep aph.LocalLawsW h ld d a => TombstoneReason.LocalLawsW h ld d a
      case Ep aph.Tox cReplyF ltered => TombstoneReason.ReplyF ltered
      case _ => TombstoneReason.Unspec f ed
    }
  }
}
