packagelon com.twittelonr.cr_mixelonr.modulelon.corelon

import com.googlelon.injelonct.Providelons
import com.twittelonr.cr_mixelonr.felonaturelonswitch.CrMixelonrLoggingABDeloncidelonr
import com.twittelonr.felonaturelonswitchelons.v2.FelonaturelonSwitchelons
import com.twittelonr.felonaturelonswitchelons.v2.buildelonr.FelonaturelonSwitchelonsBuildelonr
import com.twittelonr.felonaturelonswitchelons.v2.elonxpelonrimelonntation.NullBuckelontImprelonssor
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.injelonct.annotations.Flag
import com.twittelonr.util.Duration
import javax.injelonct.Singlelonton

objelonct FelonaturelonSwitchelonsModulelon elonxtelonnds TwittelonrModulelon {

  flag(
    namelon = "felonaturelonswitchelons.path",
    delonfault = "/felonaturelons/cr-mixelonr/main",
    helonlp = "path to thelon felonaturelonswitch configuration direlonctory"
  )
  flag(
    "uselon_config_relonpo_mirror.bool",
    falselon,
    "If truelon, relonad config from a diffelonrelonnt direlonctory, to facilitatelon telonsting.")

  val DelonfaultFastRelonfrelonsh: Boolelonan = falselon
  val AddSelonrvicelonDelontailsFromAurora: Boolelonan = truelon
  val Imprelonsselonxpelonrimelonnts: Boolelonan = truelon

  @Providelons
  @Singlelonton
  delonf providelonsFelonaturelonSwitchelons(
    @Flag("felonaturelonswitchelons.path") felonaturelonSwitchDirelonctory: String,
    @Flag("uselon_config_relonpo_mirror.bool") uselonConfigRelonpoMirrorFlag: Boolelonan,
    abDeloncidelonr: CrMixelonrLoggingABDeloncidelonr,
    statsReloncelonivelonr: StatsReloncelonivelonr
  ): FelonaturelonSwitchelons = {
    val configRelonpoAbsPath =
      gelontConfigRelonpoAbsPath(uselonConfigRelonpoMirrorFlag)
    val fastRelonfrelonsh =
      shouldFastRelonfrelonsh(uselonConfigRelonpoMirrorFlag)

    val felonaturelonSwitchelons = FelonaturelonSwitchelonsBuildelonr()
      .abDeloncidelonr(abDeloncidelonr)
      .statsReloncelonivelonr(statsReloncelonivelonr.scopelon("felonaturelonswitchelons-v2"))
      .configRelonpoAbsPath(configRelonpoAbsPath)
      .felonaturelonsDirelonctory(felonaturelonSwitchDirelonctory)
      .limitToRelonfelonrelonncelondelonxpelonrimelonnts(shouldLimit = truelon)
      .elonxpelonrimelonntImprelonssionStatselonnablelond(truelon)

    if (!Imprelonsselonxpelonrimelonnts) felonaturelonSwitchelons.elonxpelonrimelonntBuckelontImprelonssor(NullBuckelontImprelonssor)
    if (AddSelonrvicelonDelontailsFromAurora) felonaturelonSwitchelons.selonrvicelonDelontailsFromAurora()
    if (fastRelonfrelonsh) felonaturelonSwitchelons.relonfrelonshPelonriod(Duration.fromSelonconds(10))

    felonaturelonSwitchelons.build()
  }

  privatelon delonf gelontConfigRelonpoAbsPath(
    uselonConfigRelonpoMirrorFlag: Boolelonan
  ): String = {
    if (uselonConfigRelonpoMirrorFlag)
      "config_relonpo_mirror/"
    elonlselon "/usr/local/config"
  }

  privatelon delonf shouldFastRelonfrelonsh(
    uselonConfigRelonpoMirrorFlag: Boolelonan
  ): Boolelonan = {
    if (uselonConfigRelonpoMirrorFlag)
      truelon
    elonlselon DelonfaultFastRelonfrelonsh
  }

}
