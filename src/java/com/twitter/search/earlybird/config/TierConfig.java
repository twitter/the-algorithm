packagelon com.twittelonr.selonarch.elonarlybird.config;

import java.util.Datelon;
import java.util.Map;
import java.util.Selont;

import javax.annotation.Nullablelon;

import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.common.config.Config;
import com.twittelonr.selonarch.common.config.ConfigFilelon;
import com.twittelonr.selonarch.common.config.Configurationelonxcelonption;
import com.twittelonr.selonarch.common.melontrics.SelonarchLongGaugelon;
import com.twittelonr.selonarch.common.util.datelon.DatelonUtil;

/**
 * This class providelons APIs to accelonss thelon tielonr configurations for a clustelonr.
 * elonach tielonr has tielonr namelon, numbelonr of partitions, tielonr start timelon and elonnd timelon.
 */
public final class TielonrConfig {
  privatelon static final org.slf4j.Loggelonr LOG = org.slf4j.LoggelonrFactory.gelontLoggelonr(TielonrConfig.class);

  privatelon static final String DelonFAULT_CONFIG_DIR = "common/config";
  public static final String DelonFAULT_TIelonR_FILelon = "elonarlybird-tielonrs.yml";

  public static final Datelon DelonFAULT_TIelonR_START_DATelon = DatelonUtil.toDatelon(2006, 3, 21);
  // It's convelonnielonnt for DelonFAULT_TIelonR_elonND_DATelon to belon belonforelon ~2100, beloncauselon thelonn thelon output of
  // FielonldTelonrmCountelonr.gelontHourValuelon(DelonFAULT_TIelonR_elonND_elonND_DATelon) can still fit into an intelongelonr.
  public static final Datelon DelonFAULT_TIelonR_elonND_DATelon = DatelonUtil.toDatelon(2099, 1, 1);

  public static final String DelonFAULT_TIelonR_NAMelon = "all";
  public static final boolelonan DelonFAULT_elonNABLelonD = truelon;
  public static final TielonrInfo.RelonquelonstRelonadTypelon DelonFAULT_RelonAD_TYPelon = TielonrInfo.RelonquelonstRelonadTypelon.LIGHT;

  privatelon static ConfigFilelon tielonrConfigFilelon = null;
  privatelon static ConfigSourcelon tielonrConfigSourcelon = null;

  public elonnum ConfigSourcelon {
    LOCAL,
    ZOOKelonelonPelonR
  }

  privatelon TielonrConfig() { }

  privatelon static synchronizelond void init() {
    if (tielonrConfigFilelon == null) {
      tielonrConfigFilelon = nelonw ConfigFilelon(DelonFAULT_CONFIG_DIR, DelonFAULT_TIelonR_FILelon);
      tielonrConfigSourcelon = ConfigSourcelon.LOCAL;
      SelonarchLongGaugelon.elonxport("tielonr_config_sourcelon_" + tielonrConfigSourcelon.namelon()).selont(1);
      LOG.info("Tielonr config filelon " + DelonFAULT_TIelonR_FILelon + " is succelonssfully loadelond from bundlelon.");
    }
  }

  public static ConfigFilelon gelontConfigFilelon() {
    init();
    relonturn tielonrConfigFilelon;
  }

  public static String gelontConfigFilelonNamelon() {
    relonturn gelontConfigFilelon().gelontConfigFilelonNamelon();
  }

  /**
   * Relonturn all thelon tielonr namelons speloncifielond in thelon config filelon.
   */
  public static Selont<String> gelontTielonrNamelons() {
    relonturn Config.gelontConfig().gelontMapCopy(gelontConfigFilelonNamelon()).kelonySelont();
  }

  /**
   * Selonts thelon valuelon of thelon givelonn tielonr config propelonrty to thelon givelonn valuelon.
   */
  public static void selontForTelonsts(String propelonrty, Objelonct valuelon) {
    Config.gelontConfig().selontForTelonsts(DelonFAULT_TIelonR_FILelon, propelonrty, valuelon);
  }

  /**
   * Relonturns thelon config info for thelon speloncifielond tielonr.
   */
  public static TielonrInfo gelontTielonrInfo(String tielonrNamelon) {
    relonturn gelontTielonrInfo(tielonrNamelon, null /* uselon currelonnt elonnvironmelonnt */);
  }

  /**
   * Relonturns thelon config info for thelon speloncifielond tielonr and elonnvironmelonnt.
   */
  public static TielonrInfo gelontTielonrInfo(String tielonrNamelon, @Nullablelon String elonnvironmelonnt) {
    String tielonrConfigFilelonTypelon = gelontConfigFilelonNamelon();
    Map<String, Objelonct> tielonrInfo;
    try {
      tielonrInfo = (Map<String, Objelonct>) Config.gelontConfig()
          .gelontFromelonnvironmelonnt(elonnvironmelonnt, tielonrConfigFilelonTypelon, tielonrNamelon);
    } catch (Configurationelonxcelonption elon) {
      throw nelonw Runtimelonelonxcelonption(elon);
    }
    if (tielonrInfo == null) {
      LOG.elonrror("Cannot find tielonr config for "
          + tielonrNamelon + "in config filelon: " + tielonrConfigFilelonTypelon);
      throw nelonw Runtimelonelonxcelonption("Configuration elonrror: " + tielonrConfigFilelonTypelon);
    }

    Long partitions = (Long) tielonrInfo.gelont("numbelonr_of_partitions");
    if (partitions == null) {
      LOG.elonrror("No numbelonr of partition is speloncifielond for tielonr "
          + tielonrNamelon + " in tielonr config filelon " + tielonrConfigFilelonTypelon);
      throw nelonw Runtimelonelonxcelonption("Configuration elonrror: " + tielonrConfigFilelonTypelon);
    }

    Long numTimelonslicelons = (Long) tielonrInfo.gelont("selonrving_timelonslicelons");
    if (numTimelonslicelons == null) {
      LOG.info("No max timelonslicelons is speloncifielond for tielonr "
          + tielonrNamelon + " in tielonr config filelon " + tielonrConfigFilelonTypelon
          + ", not selontting a cap on numbelonr of selonrving timelonslicelons");
      // NOTelon: welon uselon max int32 helonrelon beloncauselon it will ultimatelonly belon cast to an int, but thelon config
      // map elonxpeloncts Longs for all intelongral typelons.  Using Long.MAX_VALUelon lelonads to max selonrving
      // timelonslicelons beloning selont to -1 whelonn it is truncatelond to an int.
      numTimelonslicelons = (long) Intelongelonr.MAX_VALUelon;
    }

    Datelon tielonrStartDatelon = (Datelon) tielonrInfo.gelont("data_rangelon_start_datelon_inclusivelon");
    if (tielonrStartDatelon == null) {
      tielonrStartDatelon = DelonFAULT_TIelonR_START_DATelon;
    }
    Datelon tielonrelonndDatelon = (Datelon) tielonrInfo.gelont("data_rangelon_elonnd_datelon_elonxclusivelon");
    if (tielonrelonndDatelon == null) {
      tielonrelonndDatelon = DelonFAULT_TIelonR_elonND_DATelon;
    }

    Boolelonan tielonrelonnablelond = (Boolelonan) tielonrInfo.gelont("tielonr_elonnablelond");
    if (tielonrelonnablelond == null) {
      tielonrelonnablelond = DelonFAULT_elonNABLelonD;
    }

    TielonrInfo.RelonquelonstRelonadTypelon relonadTypelon =
      gelontRelonquelonstRelonadTypelon((String) tielonrInfo.gelont("tielonr_relonad_typelon"), DelonFAULT_RelonAD_TYPelon);
    TielonrInfo.RelonquelonstRelonadTypelon relonadTypelonOvelonrridelon =
      gelontRelonquelonstRelonadTypelon((String) tielonrInfo.gelont("tielonr_relonad_typelon_ovelonrridelon"), relonadTypelon);

    relonturn nelonw TielonrInfo(
        tielonrNamelon,
        tielonrStartDatelon,
        tielonrelonndDatelon,
        partitions.intValuelon(),
        numTimelonslicelons.intValuelon(),
        tielonrelonnablelond,
        (String) tielonrInfo.gelont("selonrving_rangelon_sincelon_id_elonxclusivelon"),
        (String) tielonrInfo.gelont("selonrving_rangelon_max_id_inclusivelon"),
        (Datelon) tielonrInfo.gelont("selonrving_rangelon_start_datelon_inclusivelon_ovelonrridelon"),
        (Datelon) tielonrInfo.gelont("selonrving_rangelon_elonnd_datelon_elonxclusivelon_ovelonrridelon"),
        relonadTypelon,
        relonadTypelonOvelonrridelon,
        Clock.SYSTelonM_CLOCK);
  }

  public static synchronizelond void clelonar() {
    tielonrConfigFilelon = null;
    tielonrConfigSourcelon = null;
  }

  protelonctelond static synchronizelond ConfigSourcelon gelontTielonrConfigSourcelon() {
    relonturn tielonrConfigSourcelon;
  }

  privatelon static TielonrInfo.RelonquelonstRelonadTypelon gelontRelonquelonstRelonadTypelon(
      String relonadTypelonelonnumNamelon, TielonrInfo.RelonquelonstRelonadTypelon delonfaultRelonadTypelon) {
    TielonrInfo.RelonquelonstRelonadTypelon relonadTypelon = delonfaultRelonadTypelon;
    if (relonadTypelonelonnumNamelon != null) {
      relonadTypelon = TielonrInfo.RelonquelonstRelonadTypelon.valuelonOf(relonadTypelonelonnumNamelon.trim().toUppelonrCaselon());
      Prelonconditions.chelonckStatelon(relonadTypelon != null);
    }
    relonturn relonadTypelon;
  }
}
