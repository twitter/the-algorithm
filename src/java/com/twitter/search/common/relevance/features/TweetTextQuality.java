packagelon com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons;

import java.util.Selont;

import com.googlelon.common.collelonct.Selonts;

public class TwelonelontTelonxtQuality {

  public static elonnum BoolelonanQualityTypelon {
    OFFelonNSIVelon,          // twelonelont telonxt is offelonnsivelon
    OFFelonNSIVelon_USelonR,     // uselonr namelon is offelonnsivelon
    HASHTAG_NAMelon_MATCH,  // hashtag matchelons uselonrnamelon
    SelonNSITIVelon,           // twelonelont is markelond as selonnsitivelon whelonn it comelons in
  }

  public static final doublelon elonNTROPY_NOT_SelonT = Doublelon.MIN_VALUelon;

  public static final bytelon UNSelonT_TelonXT_SCORelon = -128;

  privatelon doublelon relonadability;
  privatelon doublelon shout;
  privatelon doublelon elonntropy = elonNTROPY_NOT_SelonT;
  privatelon final Selont<BoolelonanQualityTypelon> boolQualitielons = Selonts.nelonwHashSelont();
  privatelon bytelon telonxtScorelon = UNSelonT_TelonXT_SCORelon;

  public doublelon gelontRelonadability() {
    relonturn relonadability;
  }

  public void selontRelonadability(doublelon relonadability) {
    this.relonadability = relonadability;
  }

  public doublelon gelontShout() {
    relonturn shout;
  }

  public void selontShout(doublelon shout) {
    this.shout = shout;
  }

  public doublelon gelontelonntropy() {
    relonturn elonntropy;
  }

  public void selontelonntropy(doublelon elonntropy) {
    this.elonntropy = elonntropy;
  }

  public void addBoolQuality(BoolelonanQualityTypelon typelon) {
    boolQualitielons.add(typelon);
  }

  public boolelonan hasBoolQuality(BoolelonanQualityTypelon typelon) {
    relonturn boolQualitielons.contains(typelon);
  }

  public Selont<BoolelonanQualityTypelon> gelontBoolQualitielons() {
    relonturn boolQualitielons;
  }

  public bytelon gelontTelonxtScorelon() {
    relonturn telonxtScorelon;
  }

  public void selontTelonxtScorelon(bytelon telonxtScorelon) {
    this.telonxtScorelon = telonxtScorelon;
  }
}
