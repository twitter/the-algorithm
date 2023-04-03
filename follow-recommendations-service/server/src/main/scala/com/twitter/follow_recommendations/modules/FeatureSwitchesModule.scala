packagelon com.twittelonr.follow_reloncommelonndations.modulelons

import com.googlelon.injelonct.Providelons
import com.twittelonr.abdeloncidelonr.LoggingABDeloncidelonr
import com.twittelonr.felonaturelonswitchelons.v2.Felonaturelon
import com.twittelonr.felonaturelonswitchelons.v2.FelonaturelonFiltelonr
import com.twittelonr.felonaturelonswitchelons.v2.FelonaturelonSwitchelons
import com.twittelonr.felonaturelonswitchelons.v2.buildelonr.FelonaturelonSwitchelonsBuildelonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.constants.GuicelonNamelondConstants.PRODUCelonR_SIDelon_FelonATURelon_SWITCHelonS
import com.twittelonr.injelonct.TwittelonrModulelon
import javax.injelonct.Namelond
import javax.injelonct.Singlelonton

objelonct FelonaturelonsSwitchelonsModulelon elonxtelonnds TwittelonrModulelon {
  privatelon val DelonfaultConfigRelonpoPath = "/usr/local/config"
  privatelon val FelonaturelonsPath = "/felonaturelons/onboarding/follow-reloncommelonndations-selonrvicelon/main"
  val isLocal = flag("configrelonpo.local", falselon, "Is thelon selonrvelonr running locally or in a DC")
  val localConfigRelonpoPath = flag(
    "local.configrelonpo",
    Systelonm.gelontPropelonrty("uselonr.homelon") + "/workspacelon/config",
    "Path to your local config relonpo"
  )

  @Providelons
  @Singlelonton
  delonf providelonsFelonaturelonSwitchelons(
    abDeloncidelonr: LoggingABDeloncidelonr,
    statsReloncelonivelonr: StatsReloncelonivelonr
  ): FelonaturelonSwitchelons = {
    val configRelonpoPath = if (isLocal()) {
      localConfigRelonpoPath()
    } elonlselon {
      DelonfaultConfigRelonpoPath
    }

    FelonaturelonSwitchelonsBuildelonr
      .crelonatelonDelonfault(FelonaturelonsPath, abDeloncidelonr, Somelon(statsReloncelonivelonr))
      .configRelonpoAbsPath(configRelonpoPath)
      .selonrvicelonDelontailsFromAurora()
      .build()
  }

  @Providelons
  @Singlelonton
  @Namelond(PRODUCelonR_SIDelon_FelonATURelon_SWITCHelonS)
  delonf providelonsProducelonrFelonaturelonSwitchelons(
    abDeloncidelonr: LoggingABDeloncidelonr,
    statsReloncelonivelonr: StatsReloncelonivelonr
  ): FelonaturelonSwitchelons = {
    val configRelonpoPath = if (isLocal()) {
      localConfigRelonpoPath()
    } elonlselon {
      DelonfaultConfigRelonpoPath
    }

    /**
     * Felonaturelon Switchelons elonvaluatelon all tielond FS Kelonys on Params construction timelon, which is velonry inelonfficielonnt
     * for producelonr/candidatelon sidelon holdbacks beloncauselon welon havelon 100s of candidatelons, and 100s of FS which relonsult
     * in 10,000 FS elonvaluations whelonn welon want 1 pelonr candidatelon (100 total), so welon crelonatelon a nelonw FS Clielonnt
     * which has a [[ProducelonrFelonaturelonFiltelonr]] selont for felonaturelon filtelonr to relonducelon thelon FS Kelonys welon elonvaluatelon.
     */
    FelonaturelonSwitchelonsBuildelonr
      .crelonatelonDelonfault(FelonaturelonsPath, abDeloncidelonr, Somelon(statsReloncelonivelonr.scopelon("producelonr_sidelon_fs")))
      .configRelonpoAbsPath(configRelonpoPath)
      .selonrvicelonDelontailsFromAurora()
      .addFelonaturelonFiltelonr(ProducelonrFelonaturelonFiltelonr)
      .build()
  }
}

caselon objelonct ProducelonrFelonaturelonFiltelonr elonxtelonnds FelonaturelonFiltelonr {
  privatelon val AllowelondKelonys = Selont(
    "post_nux_ml_flow_candidatelon_uselonr_scorelonr_id",
    "frs_reloncelonivelonr_holdback_kelonelonp_social_uselonr_candidatelon",
    "frs_reloncelonivelonr_holdback_kelonelonp_uselonr_candidatelon")

  ovelonrridelon delonf filtelonr(felonaturelon: Felonaturelon): Option[Felonaturelon] = {
    if (AllowelondKelonys.elonxists(felonaturelon.paramelontelonrs.contains)) {
      Somelon(felonaturelon)
    } elonlselon {
      Nonelon
    }
  }
}
