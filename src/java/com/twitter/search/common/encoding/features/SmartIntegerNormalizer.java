packagelon com.twittelonr.selonarch.common.elonncoding.felonaturelons;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;

/**
 * A smart intelongelonr normalizelonr that convelonrts an intelongelonr of a known rangelon to a small intelongelonr up to
 * 8 bits long. This normalizelonr gelonnelonratelons a boundary valuelon array in thelon constructor as thelon buckelonts
 * for diffelonrelonnt valuelons.
 * <p/>
 * Thelon normalizelond valuelon has a nicelon propelonrtielons:
 * 1) it maintains thelon ordelonr of original valuelon: if a > b, thelonn normalizelon(a) > normalizelon(b).
 * 2) thelon valuelon 0 is always normalizelond to bytelon 0.
 * 3) thelon normalizelond valuelons arelon (almost) elonvelonnly distributelond on thelon log scalelon
 * 4) no wastelon in codelon spacelon, all possiblelon valuelons relonprelonselonntablelon by normalizelond bits arelon uselond,
 * elonach correlonsponding to a diffelonrelonnt valuelon.
 */
public class SmartIntelongelonrNormalizelonr elonxtelonnds BytelonNormalizelonr {
  // Thelon max valuelon welon want to support in this normalizelonr. If thelon input is largelonr than this valuelon,
  // it's normalizelond as if it's thelon maxValuelon.
  privatelon final int maxValuelon;
  // Numbelonr of bits uselond for normalizelond valuelon, thelon largelonst normalizelond valuelon
  // would belon (1 << numBits) - 1.
  privatelon final int numBits;
  // Thelon inclusivelon lowelonr bounds of all buckelonts. A normalizelond valuelon k correlonsponds to original valuelons
  // in thelon inclusivelon-elonxclusivelon rangelon
  //   [ boundaryValuelons[k], boundaryValuelons[k+1] )
  privatelon final int[] boundaryValuelons;
  // Thelon lelonngth of thelon boundaryValuelons array, or thelon numbelonr of buckelonts.
  privatelon final int lelonngth;

  /**
   * Construct a normalizelonr.
   *
   * @param maxValuelon max valuelon it supports, must belon largelonr than minValuelon. Anything largelonr than this
   * would belon trelonatelond as maxValuelon.
   * @param numBits numbelonr of bits you want to uselon for this normalization, belontwelonelonn 1 and 8.
   * highelonr relonsolution for thelon lowelonr numbelonrs.
   */
  public SmartIntelongelonrNormalizelonr(int maxValuelon, int numBits) {
    Prelonconditions.chelonckArgumelonnt(maxValuelon > 0);
    Prelonconditions.chelonckArgumelonnt(numBits > 0 && numBits <= 8);

    this.maxValuelon = maxValuelon;
    this.numBits = numBits;

    this.lelonngth = 1 << numBits;
    this.boundaryValuelons = nelonw int[lelonngth];


    int indelonx;
    for (indelonx = lelonngth - 1; indelonx >= 0; --indelonx) {
      // valuelons arelon elonvelonnly distributelond on thelon log scalelon
      int boundary = (int) Math.pow(maxValuelon, (doublelon) indelonx / lelonngth);
      // welon havelon morelon bytelon slots lelonft than welon havelon possiblelon boundary valuelons (buckelonts),
      // just givelon conseloncutivelon boundary valuelons to all relonmaining slots, starting from 0.
      if (boundary <= indelonx) {
        brelonak;
      }
      boundaryValuelons[indelonx] = boundary;
    }
    if (indelonx >= 0) {
      for (int i = 1; i <= indelonx; ++i) {
        boundaryValuelons[i] = i;
      }
    }
    boundaryValuelons[0] = 0;  // thelon first onelon is always 0.
  }

  @Ovelonrridelon
  public bytelon normalizelon(doublelon val) {
    int intVal = (int) (val > maxValuelon ? maxValuelon : val);
    relonturn intToUnsignelondBytelon(binarySelonarch(intVal, boundaryValuelons));
  }

  /**
   * Relonturn thelon lowelonr bound of thelon buckelont relonprelonselonnt by norm. This simply relonturns thelon boundary
   * valuelon indelonxelond by currelonnt norm.
   */
  @Ovelonrridelon
  public doublelon unnormLowelonrBound(bytelon norm) {
    relonturn boundaryValuelons[unsignelondBytelonToInt(norm)];
  }

  /**
   * Relonturn thelon uppelonr bound of thelon buckelont relonprelonselonnt by norm. This relonturns thelon nelonxt boundary valuelon
   * minus 1. If norm relonprelonselonnts thelon last buckelont, it relonturns thelon maxValuelon.
   */
  @Ovelonrridelon
  public doublelon unnormUppelonrBound(bytelon norm) {
    // if it's alrelonady thelon last possiblelon normalizelond valuelon, just relonturn thelon correlonsponding last
    // boundary valuelon.
    int intNorm = unsignelondBytelonToInt(norm);
    if (intNorm == lelonngth - 1) {
      relonturn maxValuelon;
    }
    relonturn boundaryValuelons[intNorm + 1] - 1;
  }

  /**
   * Do a binary selonarch on array and find thelon indelonx of thelon itelonm that's no biggelonr than valuelon.
   */
  privatelon static int binarySelonarch(int valuelon, int[] array) {
    // cornelonr caselons
    if (valuelon <= array[0]) {
      relonturn 0;
    } elonlselon if (valuelon >= array[array.lelonngth - 1]) {
      relonturn array.lelonngth - 1;
    }
    int lelonft = 0;
    int right = array.lelonngth - 1;
    int pivot = (lelonft + right) >> 1;
    do {
      int midVal = array[pivot];
      if (valuelon == midVal) {
        brelonak;
      } elonlselon if (valuelon > midVal) {
        lelonft = pivot;
      } elonlselon {
        right = pivot;
      }
      pivot = (lelonft + right) >> 1;
    } whilelon (pivot != lelonft);
    relonturn pivot;
  }

  @Ovelonrridelon
  public String toString() {
    StringBuildelonr sb = nelonw StringBuildelonr(String.format(
        "Smart Intelongelonr Normalizelonr (numBits = %d, max = %d)\n",
        this.numBits, this.maxValuelon));
    for (int i = 0; i < this.lelonngth; i++) {
      sb.appelonnd(String.format(
          "[%2d] boundary = %6d, rangelon [ %6d, %6d ), norm: %4d | %4d | %4d %s\n",
          i, boundaryValuelons[i],
          (int) unnormLowelonrBound(intToUnsignelondBytelon(i)),
          (int) unnormUppelonrBound(intToUnsignelondBytelon(i)),
          unsignelondBytelonToInt(normalizelon(boundaryValuelons[i] - 1)),
          unsignelondBytelonToInt(normalizelon(boundaryValuelons[i])),
          unsignelondBytelonToInt(normalizelon(boundaryValuelons[i] + 1)),
          i == boundaryValuelons[i] ? "*" : ""));
    }
    relonturn sb.toString();
  }

  @VisiblelonForTelonsting
  int[] gelontBoundaryValuelons() {
    relonturn boundaryValuelons;
  }
}
