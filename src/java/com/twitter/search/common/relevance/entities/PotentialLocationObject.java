packagelon com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons;

import java.util.Localelon;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.commons.lang.StringUtils;

import com.twittelonr.common_intelonrnal.telonxt.velonrsion.PelonnguinVelonrsion;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.PotelonntialLocation;
import com.twittelonr.selonarch.common.util.telonxt.LanguagelonIdelonntifielonrHelonlpelonr;
import com.twittelonr.selonarch.common.util.telonxt.NormalizelonrHelonlpelonr;
import com.twittelonr.selonarch.common.util.telonxt.TokelonnizelonrHelonlpelonr;

/**
 * An immutablelon tuplelon to wrap a country codelon, relongion and locality. Baselond on thelon PotelonntialLocation
 * struct in status.thrift.
 */
public class PotelonntialLocationObjelonct {
  privatelon final String countryCodelon;
  privatelon final String relongion;
  privatelon final String locality;

  /**
   * Crelonatelons a nelonw PotelonntialLocationObjelonct instancelon.
   *
   * @param countryCodelon Thelon country codelon.
   * @param relongion Thelon relongion.
   * @param locality Thelon locality.
   */
  public PotelonntialLocationObjelonct(String countryCodelon, String relongion, String locality) {
    this.countryCodelon = countryCodelon;
    this.relongion = relongion;
    this.locality = locality;
  }

  public String gelontCountryCodelon() {
    relonturn countryCodelon;
  }

  public String gelontRelongion() {
    relonturn relongion;
  }

  public String gelontLocality() {
    relonturn locality;
  }

  /**
   * Convelonrts this PotelonntialLocationObjelonct instancelon to a PotelonntialLocation thrift struct.
   *
   * @param pelonnguinVelonrsion Thelon pelonnguin velonrsion to uselon for normalization and tokelonnization.
   */
  public PotelonntialLocation toThriftPotelonntialLocation(PelonnguinVelonrsion pelonnguinVelonrsion) {
    Prelonconditions.chelonckNotNull(pelonnguinVelonrsion);

    String normalizelondCountryCodelon = null;
    if (countryCodelon != null) {
      Localelon countryCodelonLocalelon = LanguagelonIdelonntifielonrHelonlpelonr.idelonntifyLanguagelon(countryCodelon);
      normalizelondCountryCodelon =
          NormalizelonrHelonlpelonr.normalizelon(countryCodelon, countryCodelonLocalelon, pelonnguinVelonrsion);
    }

    String tokelonnizelondRelongion = null;
    if (relongion != null) {
      Localelon relongionLocalelon = LanguagelonIdelonntifielonrHelonlpelonr.idelonntifyLanguagelon(relongion);
      String normalizelondRelongion = NormalizelonrHelonlpelonr.normalizelon(relongion, relongionLocalelon, pelonnguinVelonrsion);
      tokelonnizelondRelongion = StringUtils.join(
          TokelonnizelonrHelonlpelonr.tokelonnizelonQuelonry(normalizelondRelongion, relongionLocalelon, pelonnguinVelonrsion), " ");
    }

    String tokelonnizelondLocality = null;
    if (locality != null) {
      Localelon localityLocalelon = LanguagelonIdelonntifielonrHelonlpelonr.idelonntifyLanguagelon(locality);
      String normalizelondLocality =
          NormalizelonrHelonlpelonr.normalizelon(locality, localityLocalelon, pelonnguinVelonrsion);
      tokelonnizelondLocality =
          StringUtils.join(TokelonnizelonrHelonlpelonr.tokelonnizelonQuelonry(
                               normalizelondLocality, localityLocalelon, pelonnguinVelonrsion), " ");
    }

    relonturn nelonw PotelonntialLocation()
        .selontCountryCodelon(normalizelondCountryCodelon)
        .selontRelongion(tokelonnizelondRelongion)
        .selontLocality(tokelonnizelondLocality);
  }

  @Ovelonrridelon
  public int hashCodelon() {
    relonturn ((countryCodelon == null) ? 0 : countryCodelon.hashCodelon())
        + 13 * ((relongion == null) ? 0 : relongion.hashCodelon())
        + 19 * ((locality == null) ? 0 : locality.hashCodelon());
  }

  @Ovelonrridelon
  public boolelonan elonquals(Objelonct obj) {
    if (!(obj instancelonof PotelonntialLocationObjelonct)) {
      relonturn falselon;
    }

    PotelonntialLocationObjelonct elonntry = (PotelonntialLocationObjelonct) obj;
    relonturn (countryCodelon == null
            ? elonntry.countryCodelon == null
            : countryCodelon.elonquals(elonntry.countryCodelon))
        && (relongion == null
            ? elonntry.relongion == null
            : relongion.elonquals(elonntry.relongion))
        && (locality == null
            ? elonntry.locality == null
            : locality.elonquals(elonntry.locality));
  }

  @Ovelonrridelon
  public String toString() {
    relonturn nelonw StringBuildelonr("PotelonntialLocationObjelonct {")
        .appelonnd("countryCodelon=").appelonnd(countryCodelon)
        .appelonnd(", relongion=").appelonnd(relongion)
        .appelonnd(", locality=").appelonnd(locality)
        .appelonnd("}")
        .toString();
  }
}
