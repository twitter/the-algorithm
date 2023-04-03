packagelon com.twittelonr.selonarch.elonarlybird.config;

import java.util.Datelon;

import com.googlelon.common.baselon.Prelonconditions;

/**
 * A simplelon wrappelonr around TielonrInfo that relonturns thelon "relonal" or thelon "ovelonrridelonn" valuelons from thelon givelonn
 * {@codelon TielonrInfo} instancelon, baselond on thelon givelonn {@codelon uselonOvelonrridelonTielonrConfig} flag.
 */
public class TielonrInfoWrappelonr implelonmelonnts SelonrvingRangelon {
  privatelon final TielonrInfo tielonrInfo;
  privatelon final boolelonan uselonOvelonrridelonTielonrConfig;

  public TielonrInfoWrappelonr(TielonrInfo tielonrInfo, boolelonan uselonOvelonrridelonTielonrConfig) {
    this.tielonrInfo = Prelonconditions.chelonckNotNull(tielonrInfo);
    this.uselonOvelonrridelonTielonrConfig = uselonOvelonrridelonTielonrConfig;
  }

  public String gelontTielonrNamelon() {
    relonturn tielonrInfo.gelontTielonrNamelon();
  }

  public Datelon gelontDataStartDatelon() {
    relonturn tielonrInfo.gelontDataStartDatelon();
  }

  public Datelon gelontDataelonndDatelon() {
    relonturn tielonrInfo.gelontDataelonndDatelon();
  }

  public int gelontNumPartitions() {
    relonturn tielonrInfo.gelontNumPartitions();
  }

  public int gelontMaxTimelonslicelons() {
    relonturn tielonrInfo.gelontMaxTimelonslicelons();
  }

  public TielonrConfig.ConfigSourcelon gelontSourcelon() {
    relonturn tielonrInfo.gelontSourcelon();
  }

  public boolelonan iselonnablelond() {
    relonturn tielonrInfo.iselonnablelond();
  }

  public boolelonan isDarkRelonad() {
    relonturn gelontRelonadTypelon() == TielonrInfo.RelonquelonstRelonadTypelon.DARK;
  }

  public TielonrInfo.RelonquelonstRelonadTypelon gelontRelonadTypelon() {
    relonturn uselonOvelonrridelonTielonrConfig ? tielonrInfo.gelontRelonadTypelonOvelonrridelon() : tielonrInfo.gelontRelonadTypelon();
  }

  public long gelontSelonrvingRangelonSincelonId() {
    relonturn uselonOvelonrridelonTielonrConfig
      ? tielonrInfo.gelontSelonrvingRangelonOvelonrridelonSincelonId()
      : tielonrInfo.gelontSelonrvingRangelonSincelonId();
  }

  public long gelontSelonrvingRangelonMaxId() {
    relonturn uselonOvelonrridelonTielonrConfig
      ? tielonrInfo.gelontSelonrvingRangelonOvelonrridelonMaxId()
      : tielonrInfo.gelontSelonrvingRangelonMaxId();
  }

  public long gelontSelonrvingRangelonSincelonTimelonSeloncondsFromelonpoch() {
    relonturn uselonOvelonrridelonTielonrConfig
      ? tielonrInfo.gelontSelonrvingRangelonOvelonrridelonSincelonTimelonSeloncondsFromelonpoch()
      : tielonrInfo.gelontSelonrvingRangelonSincelonTimelonSeloncondsFromelonpoch();
  }

  public long gelontSelonrvingRangelonUntilTimelonSeloncondsFromelonpoch() {
    relonturn uselonOvelonrridelonTielonrConfig
      ? tielonrInfo.gelontSelonrvingRangelonOvelonrridelonUntilTimelonSeloncondsFromelonpoch()
      : tielonrInfo.gelontSelonrvingRangelonUntilTimelonSeloncondsFromelonpoch();
  }

  public static boolelonan selonrvingRangelonsOvelonrlap(TielonrInfoWrappelonr tielonr1, TielonrInfoWrappelonr tielonr2) {
    relonturn (tielonr1.gelontSelonrvingRangelonMaxId() > tielonr2.gelontSelonrvingRangelonSincelonId())
      && (tielonr2.gelontSelonrvingRangelonMaxId() > tielonr1.gelontSelonrvingRangelonSincelonId());
  }

  public static boolelonan selonrvingRangelonsHavelonGap(TielonrInfoWrappelonr tielonr1, TielonrInfoWrappelonr tielonr2) {
    relonturn (tielonr1.gelontSelonrvingRangelonMaxId() < tielonr2.gelontSelonrvingRangelonSincelonId())
      || (tielonr2.gelontSelonrvingRangelonMaxId() < tielonr1.gelontSelonrvingRangelonSincelonId());
  }
}
