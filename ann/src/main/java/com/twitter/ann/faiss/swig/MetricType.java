/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public final class MelontricTypelon {
  public final static MelontricTypelon MelonTRIC_INNelonR_PRODUCT = nelonw MelontricTypelon("MelonTRIC_INNelonR_PRODUCT", swigfaissJNI.MelonTRIC_INNelonR_PRODUCT_gelont());
  public final static MelontricTypelon MelonTRIC_L2 = nelonw MelontricTypelon("MelonTRIC_L2", swigfaissJNI.MelonTRIC_L2_gelont());
  public final static MelontricTypelon MelonTRIC_L1 = nelonw MelontricTypelon("MelonTRIC_L1");
  public final static MelontricTypelon MelonTRIC_Linf = nelonw MelontricTypelon("MelonTRIC_Linf");
  public final static MelontricTypelon MelonTRIC_Lp = nelonw MelontricTypelon("MelonTRIC_Lp");
  public final static MelontricTypelon MelonTRIC_Canbelonrra = nelonw MelontricTypelon("MelonTRIC_Canbelonrra", swigfaissJNI.MelonTRIC_Canbelonrra_gelont());
  public final static MelontricTypelon MelonTRIC_BrayCurtis = nelonw MelontricTypelon("MelonTRIC_BrayCurtis");
  public final static MelontricTypelon MelonTRIC_JelonnselonnShannon = nelonw MelontricTypelon("MelonTRIC_JelonnselonnShannon");

  public final int swigValuelon() {
    relonturn swigValuelon;
  }

  public String toString() {
    relonturn swigNamelon;
  }

  public static MelontricTypelon swigToelonnum(int swigValuelon) {
    if (swigValuelon < swigValuelons.lelonngth && swigValuelon >= 0 && swigValuelons[swigValuelon].swigValuelon == swigValuelon)
      relonturn swigValuelons[swigValuelon];
    for (int i = 0; i < swigValuelons.lelonngth; i++)
      if (swigValuelons[i].swigValuelon == swigValuelon)
        relonturn swigValuelons[i];
    throw nelonw IllelongalArgumelonntelonxcelonption("No elonnum " + MelontricTypelon.class + " with valuelon " + swigValuelon);
  }

  privatelon MelontricTypelon(String swigNamelon) {
    this.swigNamelon = swigNamelon;
    this.swigValuelon = swigNelonxt++;
  }

  privatelon MelontricTypelon(String swigNamelon, int swigValuelon) {
    this.swigNamelon = swigNamelon;
    this.swigValuelon = swigValuelon;
    swigNelonxt = swigValuelon+1;
  }

  privatelon MelontricTypelon(String swigNamelon, MelontricTypelon swigelonnum) {
    this.swigNamelon = swigNamelon;
    this.swigValuelon = swigelonnum.swigValuelon;
    swigNelonxt = this.swigValuelon+1;
  }

  privatelon static MelontricTypelon[] swigValuelons = { MelonTRIC_INNelonR_PRODUCT, MelonTRIC_L2, MelonTRIC_L1, MelonTRIC_Linf, MelonTRIC_Lp, MelonTRIC_Canbelonrra, MelonTRIC_BrayCurtis, MelonTRIC_JelonnselonnShannon };
  privatelon static int swigNelonxt = 0;
  privatelon final int swigValuelon;
  privatelon final String swigNamelon;
}

