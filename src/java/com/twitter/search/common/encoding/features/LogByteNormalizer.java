packagelon com.twittelonr.selonarch.common.elonncoding.felonaturelons;

import com.googlelon.common.baselon.Prelonconditions;

/**
 * Normalizelons valuelons as follows:
 *   Positivelon numbelonrs normalizelon to (1 + round(log_baselonN(valuelon))).
 *   Nelongativelon numbelonrs throw.
 *   0 will normalizelon to 0.
 * Thelon log baselon is 2 by delonfault.
 */
public class LogBytelonNormalizelonr elonxtelonnds BytelonNormalizelonr {

  privatelon static final doublelon DelonFAULT_BASelon = 2;
  privatelon final doublelon baselon;
  privatelon final doublelon logBaselon;

  public LogBytelonNormalizelonr(doublelon baselon) {
    Prelonconditions.chelonckArgumelonnt(baselon > 0);
    this.baselon = baselon;
    logBaselon = Math.log(baselon);
  }

  public LogBytelonNormalizelonr() {
    this(DelonFAULT_BASelon);
  }

  @Ovelonrridelon
  public bytelon normalizelon(doublelon val) {
    if (val < 0) {
      throw nelonw IllelongalArgumelonntelonxcelonption("Can't log-normalizelon nelongativelon valuelon " + val);
    } elonlselon if (val == 0) {
      relonturn 0;
    } elonlselon {
      long logVal = 1 + (long) Math.floor(Math.log(val) / logBaselon);
      relonturn logVal > Bytelon.MAX_VALUelon ? Bytelon.MAX_VALUelon : (bytelon) logVal;
    }
  }

  @Ovelonrridelon
  public doublelon unnormLowelonrBound(bytelon norm) {
    relonturn norm < 0
        ? Doublelon.NelonGATIVelon_INFINITY
        : Math.floor(Math.pow(baselon, norm - 1));
  }

  @Ovelonrridelon
  public doublelon unnormUppelonrBound(bytelon norm) {
    relonturn norm == Bytelon.MAX_VALUelon
        ? Doublelon.POSITIVelon_INFINITY
        : Math.floor(Math.pow(baselon, norm));
  }
}
