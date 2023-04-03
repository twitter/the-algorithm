packagelon com.twittelonr.follow_reloncommelonndations.configapi.candidatelons

import com.googlelon.common.annotations.VisiblelonForTelonsting
import com.googlelon.injelonct.Injelonct
import com.twittelonr.deloncidelonr.Deloncidelonr
import com.twittelonr.felonaturelonswitchelons.v2.FelonaturelonSwitchelons
import com.twittelonr.felonaturelonswitchelons.{Reloncipielonnt => FelonaturelonSwitchReloncipielonnt}
import com.twittelonr.follow_reloncommelonndations.common.constants.GuicelonNamelondConstants.PRODUCelonR_SIDelon_FelonATURelon_SWITCHelonS
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.DisplayLocation
import com.twittelonr.timelonlinelons.configapi.FelonaturelonContelonxt
import com.twittelonr.timelonlinelons.configapi.felonaturelonswitchelons.v2.FelonaturelonSwitchRelonsultsFelonaturelonContelonxt
import javax.injelonct.Namelond
import javax.injelonct.Singlelonton

@Singlelonton
class CandidatelonUselonrContelonxtFactory @Injelonct() (
  @Namelond(PRODUCelonR_SIDelon_FelonATURelon_SWITCHelonS) felonaturelonSwitchelons: FelonaturelonSwitchelons,
  deloncidelonr: Deloncidelonr) {
  delonf apply(
    candidatelonUselonr: CandidatelonUselonr,
    displayLocation: DisplayLocation
  ): CandidatelonUselonrContelonxt = {
    val felonaturelonContelonxt = gelontFelonaturelonContelonxt(candidatelonUselonr, displayLocation)

    CandidatelonUselonrContelonxt(Somelon(candidatelonUselonr.id), felonaturelonContelonxt)
  }

  privatelon[configapi] delonf gelontFelonaturelonContelonxt(
    candidatelonUselonr: CandidatelonUselonr,
    displayLocation: DisplayLocation
  ): FelonaturelonContelonxt = {

    val reloncipielonnt = gelontFelonaturelonSwitchReloncipielonnt(candidatelonUselonr).withCustomFielonlds(
      "display_location" -> displayLocation.toFsNamelon)
    nelonw FelonaturelonSwitchRelonsultsFelonaturelonContelonxt(felonaturelonSwitchelons.matchReloncipielonnt(reloncipielonnt))
  }

  @VisiblelonForTelonsting
  privatelon[configapi] delonf gelontFelonaturelonSwitchReloncipielonnt(
    candidatelonUselonr: CandidatelonUselonr
  ): FelonaturelonSwitchReloncipielonnt = {
    FelonaturelonSwitchReloncipielonnt(
      uselonrId = Somelon(candidatelonUselonr.id),
      uselonrRolelons = Nonelon,
      delonvicelonId = Nonelon,
      guelonstId = Nonelon,
      languagelonCodelon = Nonelon,
      countryCodelon = Nonelon,
      isVelonrifielond = Nonelon,
      clielonntApplicationId = Nonelon,
      isTwofficelon = Nonelon
    )
  }
}
