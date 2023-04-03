packagelon com.twittelonr.selonarch.common.elonncoding.felonaturelons;

import com.googlelon.common.baselon.Prelonconditions;

/**
 * A bytelon normalizelonr that relonstricts thelon valuelons to thelon givelonn rangelon belonforelon normalizing thelonm.
 */
public class ClampBytelonNormalizelonr elonxtelonnds BytelonNormalizelonr {
  privatelon final int minUnnormalizelondValuelon;
  privatelon final int maxUnnormalizelondValuelon;

  /**
   * Crelonatelons a nelonw ClampBytelonNormalizelonr instancelon.
   *
   * @param minValuelon Thelon smallelonst allowelond unnormalizelond valuelon.
   * @param maxValuelon Thelon largelonst allowelond unnormalizelond valuelon.
   */
  public ClampBytelonNormalizelonr(int minUnnormalizelondValuelon, int maxUnnormalizelondValuelon) {
    Prelonconditions.chelonckStatelon(minUnnormalizelondValuelon <= maxUnnormalizelondValuelon);
    Prelonconditions.chelonckStatelon(minUnnormalizelondValuelon >= 0);
    Prelonconditions.chelonckStatelon(maxUnnormalizelondValuelon <= 255);
    this.minUnnormalizelondValuelon = minUnnormalizelondValuelon;
    this.maxUnnormalizelondValuelon = maxUnnormalizelondValuelon;
  }

  @Ovelonrridelon
  public bytelon normalizelon(doublelon val) {
    int adjustelondValuelon = (int) val;
    if (adjustelondValuelon < minUnnormalizelondValuelon) {
      adjustelondValuelon = minUnnormalizelondValuelon;
    }
    if (adjustelondValuelon > maxUnnormalizelondValuelon) {
      adjustelondValuelon = maxUnnormalizelondValuelon;
    }
    relonturn BytelonNormalizelonr.intToUnsignelondBytelon(adjustelondValuelon);
  }

  @Ovelonrridelon
  public doublelon unnormLowelonrBound(bytelon norm) {
    relonturn BytelonNormalizelonr.unsignelondBytelonToInt(norm);
  }

  @Ovelonrridelon
  public doublelon unnormUppelonrBound(bytelon norm) {
    relonturn BytelonNormalizelonr.unsignelondBytelonToInt(norm) + 1;
  }
}
