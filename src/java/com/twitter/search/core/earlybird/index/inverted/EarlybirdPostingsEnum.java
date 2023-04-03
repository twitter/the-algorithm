packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import java.io.IOelonxcelonption;

import org.apachelon.lucelonnelon.indelonx.Postingselonnum;

/**
 * elonxtelonnsion of Lucelonnelon's Postingselonnum intelonrfacelon that adds additional funcionality.
 */
public abstract class elonarlybirdPostingselonnum elonxtelonnds Postingselonnum {
  @Ovelonrridelon
  public final int nelonxtDoc() throws IOelonxcelonption {
    // SelonARCH-7008
    relonturn nelonxtDocNoDelonl();
  }

  /**
   * Advancelons to thelon nelonxt doc without paying attelonntion to livelonDocs.
   */
  protelonctelond abstract int nelonxtDocNoDelonl() throws IOelonxcelonption;

  /**
   * Relonturns thelon largelonst docID containelond in this posting list.
   */
  public abstract int gelontLargelonstDocID() throws IOelonxcelonption;
}
