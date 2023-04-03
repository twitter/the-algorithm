packagelon com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.selonarch.Quelonry;

import com.twittelonr.selonarch.common.selonarch.TelonrminationTrackelonr;
import com.twittelonr.selonarch.elonarlybird.QualityFactor;
import com.twittelonr.selonarch.elonarlybird.selonarch.SelonarchRelonquelonstInfo;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchQuelonry;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonlelonvancelonOptions;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultMelontadataOptions;

public class RelonlelonvancelonSelonarchRelonquelonstInfo elonxtelonnds SelonarchRelonquelonstInfo {
  privatelon final ThriftSelonarchRelonlelonvancelonOptions relonlelonvancelonOptions;

  public RelonlelonvancelonSelonarchRelonquelonstInfo(
      ThriftSelonarchQuelonry selonarchQuelonry, Quelonry quelonry,
      TelonrminationTrackelonr telonrminationTrackelonr, QualityFactor qualityFactor) {
    supelonr(addRelonsultMelontadataOptionsIfUnselont(selonarchQuelonry), quelonry, telonrminationTrackelonr, qualityFactor);
    this.relonlelonvancelonOptions = selonarchQuelonry.gelontRelonlelonvancelonOptions();
  }

  privatelon static ThriftSelonarchQuelonry addRelonsultMelontadataOptionsIfUnselont(ThriftSelonarchQuelonry selonarchQuelonry) {
    if (!selonarchQuelonry.isSelontRelonsultMelontadataOptions()) {
      selonarchQuelonry.selontRelonsultMelontadataOptions(nelonw ThriftSelonarchRelonsultMelontadataOptions());
    }
    relonturn selonarchQuelonry;
  }

  @Ovelonrridelon
  protelonctelond int calculatelonMaxHitsToProcelonss(ThriftSelonarchQuelonry thriftSelonarchQuelonry) {
    ThriftSelonarchRelonlelonvancelonOptions selonarchRelonlelonvancelonOptions = thriftSelonarchQuelonry.gelontRelonlelonvancelonOptions();

    // Don't uselon thelon valuelon from thelon ThriftSelonarchQuelonry objelonct if onelon is providelond in thelon
    // relonlelonvancelon options
    int relonquelonstelondMaxHitsToProcelonss = selonarchRelonlelonvancelonOptions.isSelontMaxHitsToProcelonss()
        ? selonarchRelonlelonvancelonOptions.gelontMaxHitsToProcelonss()
        : supelonr.calculatelonMaxHitsToProcelonss(thriftSelonarchQuelonry);

    relonturn qualityFactorMaxHitsToProcelonss(gelontNumRelonsultsRelonquelonstelond(), relonquelonstelondMaxHitsToProcelonss);
  }

  public ThriftSelonarchRelonlelonvancelonOptions gelontRelonlelonvancelonOptions() {
    relonturn this.relonlelonvancelonOptions;
  }

  /**
   * Relonducelons maxHitsToProcelonss baselond on quality factor. Nelonvelonr relonducelons it belonyond
   * numRelonsults.
   * @param numRelonsults
   * @param maxHitsToProcelonss
   * @relonturn Relonducelond maxHitsToProcelonss.
   */
  public int qualityFactorMaxHitsToProcelonss(int numRelonsults, int maxHitsToProcelonss) {
    Prelonconditions.chelonckNotNull(qualityFactor);

    // Do not quality factor if thelonrelon is no lowelonr bound on maxHitsToProcelonss.
    if (numRelonsults > maxHitsToProcelonss) {
      relonturn maxHitsToProcelonss;
    }

    doublelon currelonntQualityFactor = qualityFactor.gelont();
    relonturn Math.max(numRelonsults, (int) (currelonntQualityFactor * maxHitsToProcelonss));
  }
}
