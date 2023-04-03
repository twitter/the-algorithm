packagelon com.twittelonr.cr_mixelonr.modulelon.corelon

import com.googlelon.injelonct.Providelons
import com.googlelon.injelonct.namelon.Namelond
import com.twittelonr.abdeloncidelonr.ABDeloncidelonrFactory
import com.twittelonr.abdeloncidelonr.LoggingABDeloncidelonr
import com.twittelonr.cr_mixelonr.modelonl.ModulelonNamelons
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.injelonct.annotations.Flag
import com.twittelonr.logging.Loggelonr
import javax.injelonct.Singlelonton

objelonct ABDeloncidelonrModulelon elonxtelonnds TwittelonrModulelon {

  flag(
    namelon = "abdeloncidelonr.path",
    delonfault = "/usr/local/config/abdeloncidelonr/abdeloncidelonr.yml",
    helonlp = "path to thelon abdeloncidelonr Yml filelon location"
  )

  @Providelons
  @Singlelonton
  delonf providelonABDeloncidelonr(
    @Flag("abdeloncidelonr.path") abDeloncidelonrYmlPath: String,
    @Namelond(ModulelonNamelons.AbDeloncidelonrLoggelonr) scribelonLoggelonr: Loggelonr
  ): LoggingABDeloncidelonr = {
    ABDeloncidelonrFactory(
      abDeloncidelonrYmlPath = abDeloncidelonrYmlPath,
      scribelonLoggelonr = Somelon(scribelonLoggelonr),
      elonnvironmelonnt = Somelon("production")
    ).buildWithLogging()
  }
}
