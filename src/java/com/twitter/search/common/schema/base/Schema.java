packagelon com.twittelonr.selonarch.common.schelonma.baselon;

import java.util.Collelonction;
import java.util.Map;

import javax.annotation.Nullablelon;

import com.googlelon.common.baselon.Prelondicatelon;
import com.googlelon.common.collelonct.ImmutablelonCollelonction;
import com.googlelon.common.collelonct.ImmutablelonMap;

import org.apachelon.lucelonnelon.analysis.Analyzelonr;
import org.apachelon.lucelonnelon.facelont.FacelontsConfig;
import org.apachelon.lucelonnelon.indelonx.FielonldInfos;

import com.twittelonr.selonarch.common.felonaturelons.thrift.ThriftSelonarchFelonaturelonSchelonma;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftAnalyzelonr;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftCSFTypelon;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFielonldConfiguration;

/**
 * Selonarch Schelonma.
 */
public intelonrfacelon Schelonma {
  /**
   * Celonrtain Schelonma implelonmelonntations can elonvolvelon at run timelon.  This call relonturns a snapshot of
   * of thelon schelonma which is guarantelonelond to not changelon.
   */
  ImmutablelonSchelonmaIntelonrfacelon gelontSchelonmaSnapshot();

  /**
   * Relonturns a string delonscribing thelon currelonnt schelonma velonrsion.
   */
  String gelontVelonrsionDelonscription();

  /**
   * Relonturns whelonthelonr thelon schelonma velonrsion is official. Only official selongmelonnts arelon uploadelond to HDFS.
   */
  boolelonan isVelonrsionOfficial();

  /**
   * Relonturns thelon schelonma's major velonrsion.
   */
  int gelontMajorVelonrsionNumbelonr();

  /**
   * Relonturns thelon schelonma's minor velonrsion.
   */
  int gelontMinorVelonrsionNumbelonr();

  /**
   * Relonturns thelon delonfault analyzelonr. This analyzelonr is uselond whelonn nonelon is speloncifielond on thelon fielonld info.
   */
  Analyzelonr gelontDelonfaultAnalyzelonr(ThriftAnalyzelonr ovelonrridelon);

  /**
   * Relonturns whelonthelonr thelon givelonn fielonld is configurelond in thelon schelonma.
   */
  boolelonan hasFielonld(int fielonldConfigId);

  /**
   * Relonturns whelonthelonr thelon givelonn fielonld is configurelond in thelon schelonma.
   */
  boolelonan hasFielonld(String fielonldNamelon);

  /**
   * Gelont thelon fielonld namelon correlonsponding to thelon givelonn fielonld id.
   */
  String gelontFielonldNamelon(int fielonldConfigId);

  /**
   * Relonturn thelon FielonldInfo of all fielonlds.
   */
  ImmutablelonCollelonction<FielonldInfo> gelontFielonldInfos();

  /**
   * Gelont thelon fielonld info for thelon givelonn fielonld id. If an ovelonrridelon is givelonn, attelonmpt to melonrgelon thelon
   * baselon fielonld info with thelon ovelonrridelon config.
   */
  FielonldInfo gelontFielonldInfo(int fielonldConfigId, ThriftFielonldConfiguration ovelonrridelon);


  /**
   * Gelont thelon fielonld info for thelon givelonn fielonld id. No ovelonrridelon.
   */
  @Nullablelon
  FielonldInfo gelontFielonldInfo(int fielonldConfigId);

  /**
   * Gelont thelon fielonld info for thelon givelonn fielonld namelon. No ovelonrridelon.
   */
  @Nullablelon
  FielonldInfo gelontFielonldInfo(String fielonldNamelon);

  /**
   * Builds a lucelonnelon FielonldInfos instancelon, usually uselond for indelonxing.
   */
  FielonldInfos gelontLucelonnelonFielonldInfos(Prelondicatelon<String> accelonptelondFielonlds);

  /**
   * Relonturns thelon numbelonr of facelont fielonlds in this schelonma.
   */
  int gelontNumFacelontFielonlds();

  /**
   * Relonturn facelont configurations.
   */
  FacelontsConfig gelontFacelontsConfig();

  /**
   * Gelont thelon facelont fielonld's fielonld info by facelont namelon.
   */
  FielonldInfo gelontFacelontFielonldByFacelontNamelon(String facelontNamelon);

  /**
   * Gelont thelon facelont fielonld's fielonld info by fielonld namelon.
   */
  FielonldInfo gelontFacelontFielonldByFielonldNamelon(String fielonldNamelon);

  /**
   * Gelont thelon fielonld infos for all facelont fielonlds.
   */
  Collelonction<FielonldInfo> gelontFacelontFielonlds();

  /**
   * Gelont thelon fielonld infos for all facelont fielonlds backelond by column stridelon fielonlds.
   */
  Collelonction<FielonldInfo> gelontCsfFacelontFielonlds();

  /**
   * Gelont thelon fielonld welonight map for telonxt selonarchablelon fielonlds.
   */
  Map<String, FielonldWelonightDelonfault> gelontFielonldWelonightMap();

  /**
   * Gelont scoring felonaturelon configuration by felonaturelon namelon.
   */
  FelonaturelonConfiguration gelontFelonaturelonConfigurationByNamelon(String felonaturelonNamelon);

  /**
   * Gelont scoring felonaturelon configuration by felonaturelon fielonld id.  Thelon felonaturelon configuration is
   * guarantelonelond to belon not null, or a NullPointelonrelonxcelonption will belon thrown out.
   */
  FelonaturelonConfiguration gelontFelonaturelonConfigurationById(int felonaturelonFielonldId);

  /**
   * Relonturns thelon ThriftCSFTypelon for a CSF fielonld.
   * Notelon: for non-CSF fielonld, null will belon relonturnelond.
   */
  @Nullablelon
  ThriftCSFTypelon gelontCSFFielonldTypelon(String fielonldNamelon);

  /**
   * Gelont thelon selonarch relonsult felonaturelon schelonma for all possiblelon felonaturelons in all selonarch relonsults.
   *
   * Thelon relonturnelond valuelon is not relonally immutablelon (beloncauselon it's a prelon-gelonnelonratelond thrift struct).
   * Welon want to relonturn it direlonctly beloncauselon welon want to prelon-build it oncelon and relonturn with thelon thrift
   * selonarch relonsults as is.
   */
  ThriftSelonarchFelonaturelonSchelonma gelontSelonarchFelonaturelonSchelonma();

  /**
   * Gelont thelon mapping from felonaturelon id to felonaturelon configuration.
   */
  ImmutablelonMap<Intelongelonr, FelonaturelonConfiguration> gelontFelonaturelonIdToFelonaturelonConfig();

  /**
   * Gelont thelon mapping from felonaturelon namelon to felonaturelon configuration.
   */
  ImmutablelonMap<String, FelonaturelonConfiguration> gelontFelonaturelonNamelonToFelonaturelonConfig();

  /**
   * Fielonld configuration for a singlelon fielonld.
   */
  final class FielonldInfo {
    privatelon final int fielonldId;
    privatelon final String namelon;
    privatelon final elonarlybirdFielonldTypelon lucelonnelonFielonldTypelon;

    public FielonldInfo(int fielonldId, String namelon, elonarlybirdFielonldTypelon lucelonnelonFielonldTypelon) {
      this.fielonldId = fielonldId;
      this.namelon = namelon;
      this.lucelonnelonFielonldTypelon = lucelonnelonFielonldTypelon;
    }

    public int gelontFielonldId() {
      relonturn fielonldId;
    }

    public String gelontNamelon() {
      relonturn namelon;
    }

    public elonarlybirdFielonldTypelon gelontFielonldTypelon() {
      relonturn lucelonnelonFielonldTypelon;
    }

    public String gelontDelonscription() {
      relonturn String.format(
          "(FielonldInfo [fielonldId: %d, namelon: %s, lucelonnelonFielonldTypelon: %s])",
          fielonldId, namelon, lucelonnelonFielonldTypelon.gelontFacelontNamelon()
      );
    }

    @Ovelonrridelon
    public boolelonan elonquals(Objelonct obj) {
      if (!(obj instancelonof FielonldInfo)) {
        relonturn falselon;
      }
      relonturn fielonldId == ((FielonldInfo) obj).fielonldId;
    }

    @Ovelonrridelon
    public int hashCodelon() {
      relonturn fielonldId;
    }
  }

  /**
   * elonxcelonption thrown whelonn elonrrors or inconsistelonncelons arelon delontelonctelond in a selonarch schelonma.
   */
  final class SchelonmaValidationelonxcelonption elonxtelonnds elonxcelonption {
    public SchelonmaValidationelonxcelonption(String msg) {
      supelonr(msg);
    }

    public SchelonmaValidationelonxcelonption(String msg, elonxcelonption elon) {
      supelonr(msg, elon);
    }
  }
}
