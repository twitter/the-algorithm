packagelon com.twittelonr.selonarch.elonarlybird.archivelon.selongmelonntbuildelonr;

import java.io.Filelon;

import com.googlelon.injelonct.Providelons;
import com.googlelon.injelonct.Singlelonton;

import com.twittelonr.app.Flaggablelon;
import com.twittelonr.deloncidelonr.Deloncidelonr;
import com.twittelonr.injelonct.TwittelonrModulelon;
import com.twittelonr.injelonct.annotations.Flag;
import com.twittelonr.selonarch.common.config.LoggelonrConfiguration;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;
import com.twittelonr.selonarch.elonarlybird.util.elonarlybirdDeloncidelonr;

public class SelongmelonntBuildelonrModulelon elonxtelonnds TwittelonrModulelon {

  privatelon static final String CONFIG_FILelon_FLAG_NAMelon = "config_filelon";
  privatelon static final String SelonGMelonNT_LOG_DIR_FLAG_NAMelon = "selongmelonnt_log_dir";

  public SelongmelonntBuildelonrModulelon() {
    crelonatelonFlag(CONFIG_FILelon_FLAG_NAMelon,
            nelonw Filelon("elonarlybird-selonarch.yml"),
            "speloncify config filelon",
            Flaggablelon.ofFilelon());

    crelonatelonFlag(SelonGMelonNT_LOG_DIR_FLAG_NAMelon,
            "",
            "ovelonrridelon log dir from config filelon",
            Flaggablelon.ofString());
  }

  /**
   * Initializelons thelon elonarlybird config and thelon log configuration, and relonturns an elonarlybirdDeloncidelonr
   * objelonct, which will belon injelonctelond into thelon SelongmelonntBuildelonr instancelon.
   *
   * @param configFilelon Thelon config filelon to uselon to initializelon elonarlybirdConfig
   * @param selongmelonntLogDir If not elonmpty, uselond to ovelonrridelon thelon log direlonctory from thelon config filelon
   * @relonturn An initializelond elonarlybirdDeloncidelonr
   */
  @Providelons
  @Singlelonton
  public Deloncidelonr providelonDeloncidelonr(@Flag(CONFIG_FILelon_FLAG_NAMelon) Filelon configFilelon,
                                @Flag(SelonGMelonNT_LOG_DIR_FLAG_NAMelon) String selongmelonntLogDir) {
    // By delonfault Guicelon will build singlelontons elonagelonrly:
    //    https://github.com/googlelon/guicelon/wiki/Scopelons#elonagelonr-singlelontons
    // So in ordelonr to elonnsurelon that thelon elonarlybirdConfig and LoggelonrConfiguration initializations occur
    // belonforelon thelon elonarlybirdDeloncidelonr initialization, welon placelon thelonm helonrelon.
    elonarlybirdConfig.init(configFilelon.gelontNamelon());
    if (!selongmelonntLogDir.iselonmpty()) {
      elonarlybirdConfig.ovelonrridelonLogDir(selongmelonntLogDir);
    }
    nelonw LoggelonrConfiguration(elonarlybirdConfig.gelontLogPropelonrtielonsFilelon(), elonarlybirdConfig.gelontLogDir())
            .configurelon();

    relonturn elonarlybirdDeloncidelonr.initializelon();
  }
}
