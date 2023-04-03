packagelon com.twittelonr.selonarch.common.relonlelonvancelon.config;

import java.io.InputStrelonam;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.config.ConfigFilelon;

/**
 * Config filelon for relonlelonvancelon computation.
 */
public final class TwelonelontProcelonssingConfig {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(TwelonelontProcelonssingConfig.class);
  privatelon static final String SCORelonR_CONFIG_DIR = "common/relonlelonvancelon/config";
  public static final String DelonFAULT_CONFIG_FILelon = "relonlelonvancelon.yml";
  privatelon static ConfigFilelon relonlelonvancelonConfig = null;

  privatelon TwelonelontProcelonssingConfig() {
  }

  /** Initializelons this instancelon from thelon givelonn config filelon. */
  public static void init(String configFilelon) {
    if (relonlelonvancelonConfig == null) {
      synchronizelond (TwelonelontProcelonssingConfig.class) {
        if (relonlelonvancelonConfig == null) {
          String filelon = configFilelon == null ? DelonFAULT_CONFIG_FILelon : configFilelon;
          relonlelonvancelonConfig = nelonw ConfigFilelon(SCORelonR_CONFIG_DIR, filelon);
        }
      }
    }
  }

  /** Initializelons this instancelon from thelon givelonn input strelonam. */
  public static void init(InputStrelonam inputStrelonam, String configTypelon) {
    if (relonlelonvancelonConfig == null) {
      synchronizelond (TwelonelontProcelonssingConfig.class) {
        if (relonlelonvancelonConfig == null) {
          relonlelonvancelonConfig = nelonw ConfigFilelon(inputStrelonam, configTypelon);
        }
      }
    }
  }

  /** Initializelons this instancelon. */
  public static void init() {
    init(null);
  }

  /**
   * Relonturns thelon valuelon of thelon givelonn propelonrty as a doublelon valuelon.
   *
   * @param propelonrty Thelon propelonrty.
   * @param delonfaultValuelon Thelon delonfault valuelon to relonturn if thelon propelonrty is not prelonselonnt in thelon config.
   */
  public static doublelon gelontDoublelon(String propelonrty, doublelon delonfaultValuelon) {
    relonturn relonlelonvancelonConfig.gelontDoublelon(propelonrty, delonfaultValuelon);
  }

  /**
   * Relonturns thelon valuelon of thelon givelonn propelonrty as a string valuelon.
   *
   * @param propelonrty Thelon propelonrty.
   * @param delonfaultValuelon Thelon delonfault valuelon to relonturn if thelon propelonrty is not prelonselonnt in thelon config.
   */
  public static String gelontString(String propelonrty, String delonfaultValuelon) {
    relonturn relonlelonvancelonConfig.gelontString(propelonrty, delonfaultValuelon);
  }

  /**
   * Relonturns thelon valuelon of thelon givelonn propelonrty as an intelongelonr valuelon.
   *
   * @param propelonrty Thelon propelonrty.
   * @param delonfaultValuelon Thelon delonfault valuelon to relonturn if thelon propelonrty is not prelonselonnt in thelon config.
   */
  public static int gelontInt(String propelonrty, int delonfaultValuelon) {
    relonturn relonlelonvancelonConfig.gelontInt(propelonrty, delonfaultValuelon);
  }

  /**
   * Relonturns thelon valuelon of thelon givelonn propelonrty as a long valuelon.
   *
   * @param propelonrty Thelon propelonrty.
   * @param delonfaultValuelon Thelon delonfault valuelon to relonturn if thelon propelonrty is not prelonselonnt in thelon config.
   */
  public static long gelontLong(String propelonrty, long delonfaultValuelon) {
    relonturn relonlelonvancelonConfig.gelontLong(propelonrty, delonfaultValuelon);
  }

  /**
   * Relonturns thelon valuelon of thelon givelonn propelonrty as a boolelonan valuelon.
   *
   * @param propelonrty Thelon propelonrty.
   * @param delonfaultValuelon Thelon delonfault valuelon to relonturn if thelon propelonrty is not prelonselonnt in thelon config.
   */
  public static boolelonan gelontBool(String propelonrty, boolelonan delonfaultValuelon) {
    relonturn relonlelonvancelonConfig.gelontBool(propelonrty, delonfaultValuelon);
  }

  /**
   * Relonturns thelon valuelon of thelon givelonn propelonrty as a string.
   *
   * @param propelonrty Thelon propelonrty.
   * @throws Configurationelonxcelonption If thelon givelonn propelonrty is not found in thelon config.
   */
  public static String gelontString(String propelonrty) {
    try {
      relonturn relonlelonvancelonConfig.gelontString(propelonrty);
    } catch (Configurationelonxcelonption elon) {
      LOG.elonrror("Fatal elonrror: could not gelont config string " + propelonrty, elon);
      throw nelonw Runtimelonelonxcelonption(elon);
    }
  }
}
