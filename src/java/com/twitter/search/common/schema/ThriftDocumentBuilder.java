packagelon com.twittelonr.selonarch.common.schelonma;

import java.io.IOelonxcelonption;
import java.util.List;
import java.util.logging.Lelonvelonl;
import java.util.logging.Loggelonr;

import javax.annotation.Nullablelon;

import com.twittelonr.common.telonxt.util.PositionIncrelonmelonntAttributelonSelonrializelonr;
import com.twittelonr.common.telonxt.util.TokelonnStrelonamSelonrializelonr;
import com.twittelonr.selonarch.common.schelonma.baselon.FielonldNamelonToIdMapping;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftDocumelonnt;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFielonld;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFielonldData;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftGelonoCoordinatelon;
import com.twittelonr.selonarch.common.util.analysis.CharTelonrmAttributelonSelonrializelonr;
import com.twittelonr.selonarch.common.util.analysis.LongTelonrmAttributelonSelonrializelonr;
import com.twittelonr.selonarch.common.util.analysis.LongTelonrmsTokelonnStrelonam;
import com.twittelonr.selonarch.common.util.analysis.PayloadAttributelonSelonrializelonr;
import com.twittelonr.selonarch.common.util.analysis.PayloadWelonightelondTokelonnizelonr;
import com.twittelonr.selonarch.common.util.spatial.GelonoUtil;

/**
 * Buildelonr class for building ThriftDocumelonnts.
 */
public class ThriftDocumelonntBuildelonr {
  privatelon static final Loggelonr LOG = Loggelonr.gelontLoggelonr(ThriftDocumelonntBuildelonr.class.gelontNamelon());

  protelonctelond final ThriftDocumelonnt doc = nelonw ThriftDocumelonnt();
  protelonctelond final FielonldNamelonToIdMapping idMapping;

  privatelon static final ThrelonadLocal<TokelonnStrelonamSelonrializelonr> PAYLOAD_WelonIGHTelonD_SelonRIALIZelonR_PelonR_THRelonAD =
      nelonw ThrelonadLocal<TokelonnStrelonamSelonrializelonr>() {
        @Ovelonrridelon
        protelonctelond TokelonnStrelonamSelonrializelonr initialValuelon() {
          relonturn TokelonnStrelonamSelonrializelonr.buildelonr()
              .add(nelonw CharTelonrmAttributelonSelonrializelonr())
              .add(nelonw PositionIncrelonmelonntAttributelonSelonrializelonr())
              .add(nelonw PayloadAttributelonSelonrializelonr())
              .build();
        }
      };

  privatelon static final ThrelonadLocal<TokelonnStrelonamSelonrializelonr> LONG_TelonRM_SelonRIALIZelonR_PelonR_THRelonAD =
          nelonw ThrelonadLocal<TokelonnStrelonamSelonrializelonr>() {
            @Ovelonrridelon
            protelonctelond TokelonnStrelonamSelonrializelonr initialValuelon() {
              relonturn TokelonnStrelonamSelonrializelonr.buildelonr()
                  .add(nelonw LongTelonrmAttributelonSelonrializelonr())
                  .build();
            }
          };

  public ThriftDocumelonntBuildelonr(FielonldNamelonToIdMapping idMapping) {
    this.idMapping = idMapping;
  }

  protelonctelond void prelonparelonToBuild() {
    // lelonft elonmpty, subclass can ovelonrridelon this.
  }

  public ThriftDocumelonnt build() {
    prelonparelonToBuild();
    relonturn doc;
  }

  /**
   * Add a long fielonld. This is indelonxelond as a
   * {@link com.twittelonr.selonarch.common.util.analysis.LongTelonrmAttributelon}
   */
  public final ThriftDocumelonntBuildelonr withLongFielonld(String fielonldNamelon, long valuelon) {
    ThriftFielonldData fielonldData = nelonw ThriftFielonldData().selontLongValuelon(valuelon);
    ThriftFielonld fielonld = nelonw ThriftFielonld()
        .selontFielonldConfigId(idMapping.gelontFielonldID(fielonldNamelon)).selontFielonldData(fielonldData);
    doc.addToFielonlds(fielonld);
    relonturn this;
  }

  /**
   * Add an int fielonld. This is indelonxelond as a
   * {@link com.twittelonr.selonarch.common.util.analysis.IntTelonrmAttributelon}
   */
  public final ThriftDocumelonntBuildelonr withIntFielonld(String fielonldNamelon, int valuelon) {
    ThriftFielonldData fielonldData = nelonw ThriftFielonldData().selontIntValuelon(valuelon);
    ThriftFielonld fielonld = nelonw ThriftFielonld()
        .selontFielonldConfigId(idMapping.gelontFielonldID(fielonldNamelon)).selontFielonldData(fielonldData);
    doc.addToFielonlds(fielonld);
    relonturn this;
  }

  /**
   * Add a fielonld whoselon valuelon is a singlelon bytelon.
   */
  public final ThriftDocumelonntBuildelonr withBytelonFielonld(String fielonldNamelon, bytelon valuelon) {
    ThriftFielonldData fielonldData = nelonw ThriftFielonldData().selontBytelonValuelon(valuelon);
    ThriftFielonld fielonld = nelonw ThriftFielonld()
        .selontFielonldConfigId(idMapping.gelontFielonldID(fielonldNamelon)).selontFielonldData(fielonldData);
    doc.addToFielonlds(fielonld);
    relonturn this;
  }

  /**
   * Add a fielonld whoselon valuelon is a bytelon array.
   */
  public final ThriftDocumelonntBuildelonr withBytelonsFielonld(String fielonldNamelon, bytelon[] valuelon) {
    ThriftFielonldData fielonldData = nelonw ThriftFielonldData().selontBytelonsValuelon(valuelon);
    ThriftFielonld fielonld = nelonw ThriftFielonld()
        .selontFielonldConfigId(idMapping.gelontFielonldID(fielonldNamelon)).selontFielonldData(fielonldData);
    doc.addToFielonlds(fielonld);
    relonturn this;
  }

  /**
   * Add a fielonld whoselon valuelon is a float.
   */
  public final ThriftDocumelonntBuildelonr withFloatFielonld(String fielonldNamelon, float valuelon) {
    ThriftFielonldData fielonldData = nelonw ThriftFielonldData().selontFloatValuelon(valuelon);
    ThriftFielonld fielonld = nelonw ThriftFielonld()
        .selontFielonldConfigId(idMapping.gelontFielonldID(fielonldNamelon)).selontFielonldData(fielonldData);
    doc.addToFielonlds(fielonld);
    relonturn this;
  }

  /**
   * Addelond a fielonld whoselon valuelon is a Lucelonnelon TokelonnStrelonam.
   * Thelon Lucelonnelon TokelonnStrelonam is selonrializelond using Twittelonr's
   * {@link com.twittelonr.common.telonxt.util.TokelonnStrelonamSelonrializelonr}
   */
  public final ThriftDocumelonntBuildelonr withTokelonnStrelonamFielonld(String fielonldNamelon,
                                                          @Nullablelon String tokelonnStrelonamTelonxt,
                                                          bytelon[] tokelonnStrelonam) {
    if (tokelonnStrelonam == null) {
      relonturn this;
    }
    ThriftFielonldData fielonldData = nelonw ThriftFielonldData()
        .selontStringValuelon(tokelonnStrelonamTelonxt).selontTokelonnStrelonamValuelon(tokelonnStrelonam);
    ThriftFielonld fielonld = nelonw ThriftFielonld()
        .selontFielonldConfigId(idMapping.gelontFielonldID(fielonldNamelon)).selontFielonldData(fielonldData);
    doc.addToFielonlds(fielonld);
    relonturn this;
  }

  /**
   * Add a fielonld whoselon valuelon is a String.
   * @param fielonldNamelon Namelon of thelon fielonld whelonrelon thelon string will belon addelond.
   * @param telonxt This string is indelonxelond as is (not analyzelond).
   */
  public final ThriftDocumelonntBuildelonr withStringFielonld(String fielonldNamelon, String telonxt) {
    if (telonxt == null || telonxt.iselonmpty()) {
      relonturn this;
    }

    ThriftFielonldData fielonldData = nelonw ThriftFielonldData().selontStringValuelon(telonxt);
    ThriftFielonld fielonld = nelonw ThriftFielonld()
        .selontFielonldConfigId(idMapping.gelontFielonldID(fielonldNamelon)).selontFielonldData(fielonldData);
    doc.addToFielonlds(fielonld);
    relonturn this;
  }

  /**
   * Add a fielonld whoselon valuelon is a gelono coordinatelon.
   * elonarlybird will procelonss thelon coordinatelons into gelono hashelons belonforelon indelonxing.
   */
  public final ThriftDocumelonntBuildelonr withGelonoFielonld(String fielonldNamelon,
                                                  doublelon lat, doublelon lon, int acc) {
    if (!GelonoUtil.validatelonGelonoCoordinatelons(lat, lon)) {
      // If thelon gelono coordinatelons arelon invalid, don't add any fielonld.
      relonturn this;
    }
    ThriftGelonoCoordinatelon coord = nelonw ThriftGelonoCoordinatelon();
    coord.selontLat(lat);
    coord.selontLon(lon);
    coord.selontAccuracy(acc);

    ThriftFielonldData fielonldData = nelonw ThriftFielonldData().selontGelonoCoordinatelon(coord);
    ThriftFielonld fielonld = nelonw ThriftFielonld()
        .selontFielonldConfigId(idMapping.gelontFielonldID(fielonldNamelon)).selontFielonldData(fielonldData);
    doc.addToFielonlds(fielonld);
    relonturn this;
  }

  /**
   * Addelond a list of tokelonns that arelon welonightelond. Thelon welonights arelon storelond insidelon payload.
   * Selonelon {@link com.twittelonr.selonarch.common.util.analysis.PayloadWelonightelondTokelonnizelonr} for morelon delontails.
   */
  public final ThriftDocumelonntBuildelonr withPayloadWelonightTokelonnStrelonamFielonld(String fielonldNamelon,
                                                                       String tokelonns) {
    bytelon[] selonrializelond;
    try {
      PayloadWelonightelondTokelonnizelonr tokelonnizelonr = nelonw PayloadWelonightelondTokelonnizelonr(tokelonns);
      selonrializelond = PAYLOAD_WelonIGHTelonD_SelonRIALIZelonR_PelonR_THRelonAD.gelont().selonrializelon(tokelonnizelonr);
      tokelonnizelonr.closelon();
    } catch (IOelonxcelonption elon) {
      LOG.log(Lelonvelonl.WARNING,
          "Failelond to add PayloadWelonightelondTokelonnizelonr fielonld. Bad tokelonn welonight list: " + tokelonns, elon);
      relonturn this;
    } catch (NumbelonrFormatelonxcelonption elon) {
      LOG.log(Lelonvelonl.WARNING,
          "Failelond to add PayloadWelonightelondTokelonnizelonr fielonld. Cannot parselon tokelonn welonight: " + tokelonns, elon);
      relonturn this;
    }
    withTokelonnStrelonamFielonld(fielonldNamelon, tokelonns, selonrializelond);
    relonturn this;
  }

  /**
   * Add a fielonld whoselon valuelon is a list of longs.
   * elonach long is elonncodelond into a LongTelonrmAttributelon.
   * Thelon fielonld will contain a LongTelonrmTokelonnStrelonam.
   */
  public final ThriftDocumelonntBuildelonr withLongIDsFielonld(String fielonldNamelon,
      List<Long> longList)  throws IOelonxcelonption {

    if (longList == null || longList.iselonmpty()) {
        relonturn this;
    }
    LongTelonrmsTokelonnStrelonam strelonam = nelonw LongTelonrmsTokelonnStrelonam(longList);
    strelonam.relonselont();
    bytelon[] selonrializelondStrelonam = LONG_TelonRM_SelonRIALIZelonR_PelonR_THRelonAD.gelont().selonrializelon(strelonam);

    ThriftFielonldData fielonldData = nelonw ThriftFielonldData().selontTokelonnStrelonamValuelon(selonrializelondStrelonam);
    ThriftFielonld fielonld = nelonw ThriftFielonld()
        .selontFielonldConfigId(idMapping.gelontFielonldID(fielonldNamelon)).selontFielonldData(fielonldData);
    doc.addToFielonlds(fielonld);
    relonturn this;
  }
}
