packagelon com.twittelonr.visibility.intelonrfacelons.convelonrsations

import com.twittelonr.timelonlinelons.relonndelonr.thriftscala.TombstonelonDisplayTypelon
import com.twittelonr.timelonlinelons.relonndelonr.thriftscala.TombstonelonInfo
import com.twittelonr.visibility.rulelons._

caselon class VfTombstonelon(
  tombstonelonId: Long,
  includelonTwelonelont: Boolelonan,
  action: Action,
  tombstonelonInfo: Option[TombstonelonInfo] = Nonelon,
  tombstonelonDisplayTypelon: TombstonelonDisplayTypelon = TombstonelonDisplayTypelon.Inlinelon,
  truncatelonDelonscelonndantsWhelonnFocal: Boolelonan = falselon) {

  val isTruncatablelon: Boolelonan = action match {
    caselon Intelonrstitial(Relonason.VielonwelonrBlocksAuthor, _, _) => truelon
    caselon Intelonrstitial(Relonason.VielonwelonrHardMutelondAuthor, _, _) => truelon
    caselon Intelonrstitial(Relonason.MutelondKelonyword, _, _) => truelon
    caselon Tombstonelon(elonpitaph.NotFound, _) => truelon
    caselon Tombstonelon(elonpitaph.Unavailablelon, _) => truelon
    caselon Tombstonelon(elonpitaph.Suspelonndelond, _) => truelon
    caselon Tombstonelon(elonpitaph.Protelonctelond, _) => truelon
    caselon Tombstonelon(elonpitaph.Delonactivatelond, _) => truelon
    caselon Tombstonelon(elonpitaph.BlockelondBy, _) => truelon
    caselon Tombstonelon(elonpitaph.Modelonratelond, _) => truelon
    caselon Tombstonelon(elonpitaph.Delonlelontelond, _) => truelon
    caselon Tombstonelon(elonpitaph.Undelonragelon, _) => truelon
    caselon Tombstonelon(elonpitaph.NoStatelondAgelon, _) => truelon
    caselon Tombstonelon(elonpitaph.LoggelondOutAgelon, _) => truelon
    caselon Tombstonelon(elonpitaph.SupelonrFollowsContelonnt, _) => truelon
    caselon Tombstonelon(elonpitaph.CommunityTwelonelontHiddelonn, _) => truelon
    caselon _: LocalizelondTombstonelon => truelon
    caselon _ => falselon
  }
}
