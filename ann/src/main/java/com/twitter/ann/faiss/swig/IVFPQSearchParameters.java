/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IVFPQSelonarchParamelontelonrs elonxtelonnds IVFSelonarchParamelontelonrs {
  privatelon transielonnt long swigCPtr;

  protelonctelond IVFPQSelonarchParamelontelonrs(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.IVFPQSelonarchParamelontelonrs_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IVFPQSelonarchParamelontelonrs obj) {
    relonturn (obj == null) ? 0 : obj.swigCPtr;
  }

  @SupprelonssWarnings("delonpreloncation")
  protelonctelond void finalizelon() {
    delonlelontelon();
  }

  public synchronizelond void delonlelontelon() {
    if (swigCPtr != 0) {
      if (swigCMelonmOwn) {
        swigCMelonmOwn = falselon;
        swigfaissJNI.delonlelontelon_IVFPQSelonarchParamelontelonrs(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontScan_tablelon_threlonshold(long valuelon) {
    swigfaissJNI.IVFPQSelonarchParamelontelonrs_scan_tablelon_threlonshold_selont(swigCPtr, this, valuelon);
  }

  public long gelontScan_tablelon_threlonshold() {
    relonturn swigfaissJNI.IVFPQSelonarchParamelontelonrs_scan_tablelon_threlonshold_gelont(swigCPtr, this);
  }

  public void selontPolyselonmous_ht(int valuelon) {
    swigfaissJNI.IVFPQSelonarchParamelontelonrs_polyselonmous_ht_selont(swigCPtr, this, valuelon);
  }

  public int gelontPolyselonmous_ht() {
    relonturn swigfaissJNI.IVFPQSelonarchParamelontelonrs_polyselonmous_ht_gelont(swigCPtr, this);
  }

  public IVFPQSelonarchParamelontelonrs() {
    this(swigfaissJNI.nelonw_IVFPQSelonarchParamelontelonrs(), truelon);
  }

}
