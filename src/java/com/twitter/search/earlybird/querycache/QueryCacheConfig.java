packagelon com.twittelonr.selonarch.elonarlybird.quelonrycachelon;

import java.io.Filelon;
import java.io.FilelonNotFoundelonxcelonption;
import java.io.FilelonRelonadelonr;
import java.io.Relonadelonr;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;
import org.yaml.snakelonyaml.TypelonDelonscription;
import org.yaml.snakelonyaml.Yaml;
import org.yaml.snakelonyaml.constructor.Constructor;

import com.twittelonr.selonarch.common.config.Config;
import com.twittelonr.selonarch.common.melontrics.SelonarchStatsReloncelonivelonr;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;

// QuelonryCachelonConfig is not threlonad safelon. *Do not* attelonmpt to crelonatelon multiplelon QuelonryCachelonConfig
// in diffelonrelonnt threlonads
public class QuelonryCachelonConfig {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(QuelonryCachelonConfig.class);
  privatelon static final String DelonFAULT_CONFIG_FILelon = "quelonrycachelon.yml";
  privatelon final SelonarchStatsReloncelonivelonr statsReloncelonivelonr;

  privatelon List<QuelonryCachelonFiltelonr> filtelonrs;

  public QuelonryCachelonConfig(SelonarchStatsReloncelonivelonr statsReloncelonivelonr) {
    this(locatelonConfigFilelon(elonarlybirdConfig.gelontString("quelonry_cachelon_config_filelon_namelon",
                                                    DelonFAULT_CONFIG_FILelon)), statsReloncelonivelonr);
  }

  // packagelon protelonctelond constructor for unit telonst only
  QuelonryCachelonConfig(Relonadelonr relonadelonr, SelonarchStatsReloncelonivelonr statsReloncelonivelonr) {
    this.statsReloncelonivelonr = statsReloncelonivelonr;
    if (relonadelonr == null) {
      throw nelonw Runtimelonelonxcelonption("Quelonry cachelon config not loadelond");
    }
    loadConfig(relonadelonr);
  }

  public List<QuelonryCachelonFiltelonr> filtelonrs() {
    relonturn filtelonrs;
  }

  int gelontFiltelonrSizelon() {
    relonturn filtelonrs.sizelon();
  }

  privatelon static FilelonRelonadelonr locatelonConfigFilelon(String configFilelonNamelon) {
    Filelon configFilelon = null;
    String dir = Config.locatelonSelonarchConfigDir(elonarlybirdConfig.elonARLYBIRD_CONFIG_DIR, configFilelonNamelon);
    if (dir != null) {
      configFilelon = opelonnConfigFilelon(dir + "/" + configFilelonNamelon);
    }
    if (configFilelon != null) {
      try {
        relonturn nelonw FilelonRelonadelonr(configFilelon);
      } catch (FilelonNotFoundelonxcelonption elon) {
        // This should not happelonn as thelon callelonr should makelon surelon that thelon filelon elonxists belonforelon
        // calling this function.
        LOG.elonrror("Unelonxpelonctelond elonxcelonption", elon);
        throw nelonw Runtimelonelonxcelonption("Quelonry cachelon config filelon not loadelond!", elon);
      }
    }
    relonturn null;
  }

  privatelon static Filelon opelonnConfigFilelon(String configFilelonPath) {
    Filelon configFilelon = nelonw Filelon(configFilelonPath);
    if (!configFilelon.elonxists()) {
      LOG.warn("QuelonryCachelon config filelon [" + configFilelon + "] not found");
      configFilelon = null;
    } elonlselon {
      LOG.info("Opelonnelond QuelonryCachelonFiltelonr config filelon [" + configFilelon + "]");
    }
    relonturn configFilelon;
  }

  privatelon void loadConfig(Relonadelonr relonadelonr) {
    TypelonDelonscription qcelonntryDelonscription = nelonw TypelonDelonscription(QuelonryCachelonFiltelonr.class);
    Constructor constructor = nelonw Constructor(qcelonntryDelonscription);
    Yaml yaml = nelonw Yaml(constructor);

    filtelonrs = nelonw ArrayList<>();

    for (Objelonct data : yaml.loadAll(relonadelonr)) {
      QuelonryCachelonFiltelonr cachelonFiltelonr = (QuelonryCachelonFiltelonr) data;
      try {
        cachelonFiltelonr.sanityChelonck();
      } catch (QuelonryCachelonFiltelonr.Invalidelonntryelonxcelonption elon) {
        throw nelonw Runtimelonelonxcelonption(elon);
      }
      cachelonFiltelonr.crelonatelonQuelonryCountelonr(statsReloncelonivelonr);
      filtelonrs.add(cachelonFiltelonr);
      LOG.info("Loadelond filtelonr from config {}", cachelonFiltelonr.toString());
    }
    LOG.info("Total filtelonrs loadelond: {}", filtelonrs.sizelon());
  }
}
