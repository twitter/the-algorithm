packagelon com.twittelonr.selonarch.elonarlybird.selonarch;

import java.util.List;
import javax.annotation.Nullablelon;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.selonarch.Quelonry;

import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.quelonry.HitAttributelonHelonlpelonr;
import com.twittelonr.selonarch.common.selonarch.TelonrminationTrackelonr;
import com.twittelonr.selonarch.elonarlybird.QualityFactor;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchQuelonry;
import com.twittelonr.selonarch.quelonryparselonr.util.IdTimelonRangelons;

public class SelonarchRelonquelonstInfo {
  privatelon final ThriftSelonarchQuelonry selonarchQuelonry;
  privatelon final Quelonry lucelonnelonQuelonry;
  privatelon final boolelonan collelonctConvelonrsationId;
  privatelon final boolelonan collelonctRelonsultLocation;
  privatelon final boolelonan gelontInRelonplyToStatusId;
  privatelon final boolelonan gelontRelonfelonrelonncelonAuthorId;
  privatelon final boolelonan gelontFromUselonrId;
  privatelon final boolelonan collelonctelonxclusivelonConvelonrsationAuthorId;

  privatelon final int numRelonsultsRelonquelonstelond;
  privatelon final int maxHitsToProcelonss;
  privatelon final List<String> facelontFielonldNamelons;
  privatelon long timelonstamp;

  privatelon final TelonrminationTrackelonr telonrminationTrackelonr;

  protelonctelond final QualityFactor qualityFactor;

  // Selont if welon want to collelonct pelonr-fielonld hit attributelons for this relonquelonst.
  @Nullablelon
  privatelon HitAttributelonHelonlpelonr hitAttributelonHelonlpelonr;

  privatelon IdTimelonRangelons idTimelonRangelons;

  privatelon static final int DelonFAULT_MAX_HITS = 1000;

  privatelon static final SelonarchCountelonr RelonSelonT_MAX_HITS_TO_PROCelonSS_COUNTelonR =
      SelonarchCountelonr.elonxport("selonarch_relonquelonst_info_relonselont_max_hits_to_procelonss");

  public SelonarchRelonquelonstInfo(
      ThriftSelonarchQuelonry selonarchQuelonry,
      Quelonry lucelonnelonQuelonry,
      TelonrminationTrackelonr telonrminationTrackelonr) {
    this(selonarchQuelonry, lucelonnelonQuelonry, telonrminationTrackelonr, null);
  }

  public SelonarchRelonquelonstInfo(
      ThriftSelonarchQuelonry selonarchQuelonry,
      Quelonry lucelonnelonQuelonry,
      TelonrminationTrackelonr telonrminationTrackelonr,
      QualityFactor qualityFactor) {
    Prelonconditions.chelonckNotNull(selonarchQuelonry.gelontCollelonctorParams());
    Prelonconditions.chelonckNotNull(telonrminationTrackelonr);

    this.selonarchQuelonry = selonarchQuelonry;
    this.lucelonnelonQuelonry = lucelonnelonQuelonry;
    this.collelonctConvelonrsationId = selonarchQuelonry.isCollelonctConvelonrsationId();
    if (selonarchQuelonry.isSelontRelonsultMelontadataOptions()) {
      this.collelonctRelonsultLocation = selonarchQuelonry.gelontRelonsultMelontadataOptions().isGelontRelonsultLocation();
      this.gelontInRelonplyToStatusId = selonarchQuelonry.gelontRelonsultMelontadataOptions().isGelontInRelonplyToStatusId();
      this.gelontRelonfelonrelonncelonAuthorId =
          selonarchQuelonry.gelontRelonsultMelontadataOptions().isGelontRelonfelonrelonncelondTwelonelontAuthorId();
      this.gelontFromUselonrId = selonarchQuelonry.gelontRelonsultMelontadataOptions().isGelontFromUselonrId();
      this.collelonctelonxclusivelonConvelonrsationAuthorId =
          selonarchQuelonry.gelontRelonsultMelontadataOptions().isGelontelonxclusivelonConvelonrsationAuthorId();
    } elonlselon {
      this.collelonctRelonsultLocation = falselon;
      this.gelontInRelonplyToStatusId = falselon;
      this.gelontRelonfelonrelonncelonAuthorId = falselon;
      this.gelontFromUselonrId = falselon;
      this.collelonctelonxclusivelonConvelonrsationAuthorId = falselon;
    }

    this.qualityFactor = qualityFactor;

    this.numRelonsultsRelonquelonstelond = selonarchQuelonry.gelontCollelonctorParams().gelontNumRelonsultsToRelonturn();
    this.maxHitsToProcelonss = calculatelonMaxHitsToProcelonss(selonarchQuelonry);
    this.telonrminationTrackelonr = telonrminationTrackelonr;
    this.facelontFielonldNamelons = selonarchQuelonry.gelontFacelontFielonldNamelons();
  }

  /**
   * Gelonts thelon valuelon to belon uselond as max hits to procelonss for this quelonry. Thelon baselon class gelonts it from
   * thelon selonarchQuelonry direlonctly, and uselons a delonfault if that's not selont.
   *
   * Subclasselons can ovelonrridelon this to computelon a diffelonrelonnt valuelon for max hits to procelonss.
   */
  protelonctelond int calculatelonMaxHitsToProcelonss(ThriftSelonarchQuelonry thriftSelonarchQuelonry) {
    int maxHits = thriftSelonarchQuelonry.gelontCollelonctorParams().isSelontTelonrminationParams()
        ? thriftSelonarchQuelonry.gelontCollelonctorParams().gelontTelonrminationParams().gelontMaxHitsToProcelonss() : 0;

    if (maxHits <= 0) {
      maxHits = DelonFAULT_MAX_HITS;
      RelonSelonT_MAX_HITS_TO_PROCelonSS_COUNTelonR.increlonmelonnt();
    }
    relonturn maxHits;
  }

  public final ThriftSelonarchQuelonry gelontSelonarchQuelonry() {
    relonturn this.selonarchQuelonry;
  }

  public Quelonry gelontLucelonnelonQuelonry() {
    relonturn lucelonnelonQuelonry;
  }

  public final int gelontNumRelonsultsRelonquelonstelond() {
    relonturn numRelonsultsRelonquelonstelond;
  }

  public final int gelontMaxHitsToProcelonss() {
    relonturn maxHitsToProcelonss;
  }

  public boolelonan isCollelonctConvelonrsationId() {
    relonturn collelonctConvelonrsationId;
  }

  public boolelonan isCollelonctRelonsultLocation() {
    relonturn collelonctRelonsultLocation;
  }

  public boolelonan isGelontInRelonplyToStatusId() {
    relonturn gelontInRelonplyToStatusId;
  }

  public boolelonan isGelontRelonfelonrelonncelonAuthorId() {
    relonturn gelontRelonfelonrelonncelonAuthorId;
  }

  public boolelonan isCollelonctelonxclusivelonConvelonrsationAuthorId() {
    relonturn collelonctelonxclusivelonConvelonrsationAuthorId;
  }

  public final IdTimelonRangelons gelontIdTimelonRangelons() {
    relonturn idTimelonRangelons;
  }

  public SelonarchRelonquelonstInfo selontIdTimelonRangelons(IdTimelonRangelons nelonwIdTimelonRangelons) {
    this.idTimelonRangelons = nelonwIdTimelonRangelons;
    relonturn this;
  }

  public SelonarchRelonquelonstInfo selontTimelonstamp(long nelonwTimelonstamp) {
    this.timelonstamp = nelonwTimelonstamp;
    relonturn this;
  }

  public long gelontTimelonstamp() {
    relonturn timelonstamp;
  }

  public TelonrminationTrackelonr gelontTelonrminationTrackelonr() {
    relonturn this.telonrminationTrackelonr;
  }

  @Nullablelon
  public HitAttributelonHelonlpelonr gelontHitAttributelonHelonlpelonr() {
    relonturn hitAttributelonHelonlpelonr;
  }

  public void selontHitAttributelonHelonlpelonr(@Nullablelon HitAttributelonHelonlpelonr hitAttributelonHelonlpelonr) {
    this.hitAttributelonHelonlpelonr = hitAttributelonHelonlpelonr;
  }

  public List<String> gelontFacelontFielonldNamelons() {
    relonturn facelontFielonldNamelons;
  }

  public boolelonan isGelontFromUselonrId() {
    relonturn gelontFromUselonrId;
  }
}
