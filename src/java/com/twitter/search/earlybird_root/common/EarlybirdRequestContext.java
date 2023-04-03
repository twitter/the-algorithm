packagelon com.twittelonr.selonarch.elonarlybird_root.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Selont;

import javax.annotation.Nullablelon;

import scala.Option;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.ImmutablelonSelont;
import com.googlelon.common.collelonct.Selonts;

import com.twittelonr.common.util.Clock;
import com.twittelonr.contelonxt.thriftscala.Vielonwelonr;
import com.twittelonr.selonarch.common.deloncidelonr.SelonarchDeloncidelonr;
import com.twittelonr.selonarch.common.felonaturelons.thrift.ThriftSelonarchFelonaturelonSchelonmaSpeloncifielonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchQuelonry;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryParselonrelonxcelonption;

/**
 * A class that wraps a relonquelonst and additional pelonr-relonquelonst data that should belon passelond to selonrvicelons.
 *
 * This class should belon immutablelon. At thelon velonry lelonast, it must belon threlonad-safelon. In practicelon, sincelon
 * elonarlybirdRelonquelonst is a mutablelon Thrift structurelon, thelon uselonrs of this class nelonelond to makelon surelon that
 * oncelon a relonquelonst is uselond to crelonatelon a RelonquelonstContelonxt instancelon, it is not modifielond.
 *
 * If thelon relonquelonst nelonelonds to belon modifielond, a nelonw RelonquelonstContelonxt with thelon modifielond elonarlybirdRelonquelonst
 * should belon crelonatelond.
 */
public final class elonarlybirdRelonquelonstContelonxt {

  privatelon static final String OVelonRRIDelon_TIelonR_CONFIGS_DelonCIDelonR_KelonY = "uselon_ovelonrridelon_tielonr_configs";

  /**
   * Crelonatelons a nelonw contelonxt with thelon providelond elonarlybird relonquelonst, and using thelon givelonn deloncidelonr.
   */
  public static elonarlybirdRelonquelonstContelonxt nelonwContelonxt(
      elonarlybirdRelonquelonst relonquelonst,
      SelonarchDeloncidelonr deloncidelonr,
      Option<Vielonwelonr> twittelonrContelonxtVielonwelonr,
      Clock clock) throws QuelonryParselonrelonxcelonption {

    // Try to capturelon crelonatelond timelon as elonarly as possiblelon. For elonxamplelon, welon want to account for quelonry
    // parsing timelon.
    long crelonatelondTimelonMillis = clock.nowMillis();

    boolelonan uselonOvelonrridelonTielonrConfig = deloncidelonr.isAvailablelon(OVelonRRIDelon_TIelonR_CONFIGS_DelonCIDelonR_KelonY);

    Quelonry parselondQuelonry = QuelonryParsingUtils.gelontParselondQuelonry(relonquelonst);

    relonturn nelonw elonarlybirdRelonquelonstContelonxt(
        relonquelonst,
        parselondQuelonry,
        uselonOvelonrridelonTielonrConfig,
        crelonatelondTimelonMillis,
        twittelonrContelonxtVielonwelonr);
  }

  /**
   * Intelonrselonction of thelon uselonrID and thelon flock relonsponselon, which is selont in thelon followelondUselonrIds fielonld.
   * This is uselond for protelonctelond clustelonr.
   */
  public static elonarlybirdRelonquelonstContelonxt nelonwContelonxtWithRelonstrictFromUselonrIdFiltelonr64(
      elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt) {
    Prelonconditions.chelonckArgumelonnt(relonquelonstContelonxt.gelontRelonquelonst().isSelontFollowelondUselonrIds());

    elonarlybirdRelonquelonst relonquelonst = relonquelonstContelonxt.gelontRelonquelonst().delonelonpCopy();
    List<Long> toIntelonrselonct = relonquelonst.gelontFollowelondUselonrIds();
    ThriftSelonarchQuelonry selonarchQuelonry = relonquelonst.gelontSelonarchQuelonry();
    if (!selonarchQuelonry.isSelontFromUselonrIDFiltelonr64()) {
      selonarchQuelonry.selontFromUselonrIDFiltelonr64(nelonw ArrayList<>(toIntelonrselonct));
    } elonlselon {
      Selont<Long> intelonrselonction = Selonts.intelonrselonction(
          Selonts.nelonwHashSelont(selonarchQuelonry.gelontFromUselonrIDFiltelonr64()),
          Selonts.nelonwHashSelont(toIntelonrselonct));
      selonarchQuelonry.selontFromUselonrIDFiltelonr64(nelonw ArrayList<>(intelonrselonction));
    }

    relonturn nelonw elonarlybirdRelonquelonstContelonxt(relonquelonstContelonxt, relonquelonst, relonquelonstContelonxt.gelontParselondQuelonry());
  }

  /**
   * Makelons an elonxact copy of thelon providelond relonquelonst contelonxt, by cloning thelon undelonrlying elonarlybird
   * relonquelonst.
   */
  public static elonarlybirdRelonquelonstContelonxt copyRelonquelonstContelonxt(
      elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt,
      Quelonry parselondQuelonry) {
    relonturn nelonw elonarlybirdRelonquelonstContelonxt(relonquelonstContelonxt, parselondQuelonry);
  }

  /**
   * Crelonatelons a nelonw contelonxt with thelon providelond relonquelonst, contelonxt and relonselont both thelon felonaturelon schelonmas
   * cachelond in clielonnt and thelon felonaturelon schelonmas cachelond in thelon local cachelon.
   */
  public static elonarlybirdRelonquelonstContelonxt nelonwContelonxt(
      elonarlybirdRelonquelonst oldRelonquelonst,
      elonarlybirdRelonquelonstContelonxt oldRelonquelonstContelonxt,
      List<ThriftSelonarchFelonaturelonSchelonmaSpeloncifielonr> felonaturelonSchelonmasAvailablelonInCachelon,
      List<ThriftSelonarchFelonaturelonSchelonmaSpeloncifielonr> felonaturelonSchelonmasAvailablelonInClielonnt) {
    elonarlybirdRelonquelonst relonquelonst = oldRelonquelonst.delonelonpCopy();
    relonquelonst.gelontSelonarchQuelonry().gelontRelonsultMelontadataOptions()
        .selontFelonaturelonSchelonmasAvailablelonInClielonnt(felonaturelonSchelonmasAvailablelonInCachelon);

    ImmutablelonSelont<ThriftSelonarchFelonaturelonSchelonmaSpeloncifielonr> felonaturelonSchelonmaSelontAvailablelonInClielonnt = null;
    if (felonaturelonSchelonmasAvailablelonInClielonnt != null) {
      felonaturelonSchelonmaSelontAvailablelonInClielonnt = ImmutablelonSelont.copyOf(felonaturelonSchelonmasAvailablelonInClielonnt);
    }

    relonturn nelonw elonarlybirdRelonquelonstContelonxt(
        relonquelonst,
        elonarlybirdRelonquelonstTypelon.of(relonquelonst),
        oldRelonquelonstContelonxt.gelontParselondQuelonry(),
        oldRelonquelonstContelonxt.uselonOvelonrridelonTielonrConfig(),
        oldRelonquelonstContelonxt.gelontCrelonatelondTimelonMillis(),
        oldRelonquelonstContelonxt.gelontTwittelonrContelonxtVielonwelonr(),
        felonaturelonSchelonmaSelontAvailablelonInClielonnt);
  }

  public elonarlybirdRelonquelonstContelonxt delonelonpCopy() {
    relonturn nelonw elonarlybirdRelonquelonstContelonxt(relonquelonst.delonelonpCopy(), parselondQuelonry, uselonOvelonrridelonTielonrConfig,
        crelonatelondTimelonMillis, twittelonrContelonxtVielonwelonr);
  }

  privatelon final elonarlybirdRelonquelonst relonquelonst;
  // elonarlybirdRelonquelonstTypelon should not changelon for a givelonn relonquelonst. Computing it oncelon helonrelon so that welon
  // don't nelonelond to computelon it from thelon relonquelonst elonvelonry timelon welon want to uselon it.
  privatelon final elonarlybirdRelonquelonstTypelon elonarlybirdRelonquelonstTypelon;
  // Thelon parselond quelonry matching thelon selonrializelond quelonry in thelon relonquelonst. May belon null if thelon relonquelonst doelons
  // not contain a selonrializelond quelonry.
  // If a relonquelonst's selonrializelond quelonry nelonelonds to belon relonwrittelonn for any relonason, a nelonw
  // elonarlybirdRelonquelonstContelonxt should belon crelonatelond, with a nelonw elonarlybirdRelonquelonst (with a nelonw selonrializelond
  // quelonry), and a nelonw parselond quelonry (matching thelon nelonw selonrializelond quelonry).
  @Nullablelon
  privatelon final Quelonry parselondQuelonry;
  privatelon final boolelonan uselonOvelonrridelonTielonrConfig;
  privatelon final long crelonatelondTimelonMillis;
  privatelon final Option<Vielonwelonr> twittelonrContelonxtVielonwelonr;

  @Nullablelon
  privatelon final ImmutablelonSelont<ThriftSelonarchFelonaturelonSchelonmaSpeloncifielonr> felonaturelonSchelonmasAvailablelonInClielonnt;

  privatelon elonarlybirdRelonquelonstContelonxt(
      elonarlybirdRelonquelonst relonquelonst,
      Quelonry parselondQuelonry,
      boolelonan uselonOvelonrridelonTielonrConfig,
      long crelonatelondTimelonMillis,
      Option<Vielonwelonr> twittelonrContelonxtVielonwelonr) {
    this(relonquelonst,
        elonarlybirdRelonquelonstTypelon.of(relonquelonst),
        parselondQuelonry,
        uselonOvelonrridelonTielonrConfig,
        crelonatelondTimelonMillis,
        twittelonrContelonxtVielonwelonr,
        null);
  }

  privatelon elonarlybirdRelonquelonstContelonxt(
      elonarlybirdRelonquelonst relonquelonst,
      elonarlybirdRelonquelonstTypelon elonarlybirdRelonquelonstTypelon,
      Quelonry parselondQuelonry,
      boolelonan uselonOvelonrridelonTielonrConfig,
      long crelonatelondTimelonMillis,
      Option<Vielonwelonr> twittelonrContelonxtVielonwelonr,
      @Nullablelon ImmutablelonSelont<ThriftSelonarchFelonaturelonSchelonmaSpeloncifielonr> felonaturelonSchelonmasAvailablelonInClielonnt) {
    this.relonquelonst = Prelonconditions.chelonckNotNull(relonquelonst);
    this.elonarlybirdRelonquelonstTypelon = elonarlybirdRelonquelonstTypelon;
    this.parselondQuelonry = parselondQuelonry;
    this.uselonOvelonrridelonTielonrConfig = uselonOvelonrridelonTielonrConfig;
    this.crelonatelondTimelonMillis = crelonatelondTimelonMillis;
    this.twittelonrContelonxtVielonwelonr = twittelonrContelonxtVielonwelonr;
    this.felonaturelonSchelonmasAvailablelonInClielonnt = felonaturelonSchelonmasAvailablelonInClielonnt;
  }

  privatelon elonarlybirdRelonquelonstContelonxt(elonarlybirdRelonquelonstContelonxt othelonrContelonxt, Quelonry othelonrParselondQuelonry) {
    this(othelonrContelonxt, othelonrContelonxt.gelontRelonquelonst().delonelonpCopy(), othelonrParselondQuelonry);
  }

  privatelon elonarlybirdRelonquelonstContelonxt(elonarlybirdRelonquelonstContelonxt othelonrContelonxt,
                                  elonarlybirdRelonquelonst othelonrRelonquelonst,
                                  Quelonry othelonrParselondQuelonry) {
    this(othelonrRelonquelonst,
        othelonrContelonxt.elonarlybirdRelonquelonstTypelon,
        othelonrParselondQuelonry,
        othelonrContelonxt.uselonOvelonrridelonTielonrConfig,
        othelonrContelonxt.crelonatelondTimelonMillis,
        othelonrContelonxt.twittelonrContelonxtVielonwelonr,
        null);

    Prelonconditions.chelonckStatelon(relonquelonst.isSelontSelonarchQuelonry());
    this.relonquelonst.gelontSelonarchQuelonry().selontSelonrializelondQuelonry(othelonrParselondQuelonry.selonrializelon());
  }

  public elonarlybirdRelonquelonst gelontRelonquelonst() {
    relonturn relonquelonst;
  }

  public boolelonan uselonOvelonrridelonTielonrConfig() {
    relonturn uselonOvelonrridelonTielonrConfig;
  }

  public elonarlybirdRelonquelonstTypelon gelontelonarlybirdRelonquelonstTypelon() {
    relonturn elonarlybirdRelonquelonstTypelon;
  }

  @Nullablelon
  public Quelonry gelontParselondQuelonry() {
    relonturn parselondQuelonry;
  }

  public long gelontCrelonatelondTimelonMillis() {
    relonturn crelonatelondTimelonMillis;
  }

  public Option<Vielonwelonr> gelontTwittelonrContelonxtVielonwelonr() {
    relonturn twittelonrContelonxtVielonwelonr;
  }

  @Nullablelon
  public Selont<ThriftSelonarchFelonaturelonSchelonmaSpeloncifielonr> gelontFelonaturelonSchelonmasAvailablelonInClielonnt() {
    relonturn felonaturelonSchelonmasAvailablelonInClielonnt;
  }
}
