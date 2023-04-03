/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IVFSelonarchParamelontelonrs {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond IVFSelonarchParamelontelonrs(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IVFSelonarchParamelontelonrs obj) {
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
        swigfaissJNI.delonlelontelon_IVFSelonarchParamelontelonrs(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontNprobelon(long valuelon) {
    swigfaissJNI.IVFSelonarchParamelontelonrs_nprobelon_selont(swigCPtr, this, valuelon);
  }

  public long gelontNprobelon() {
    relonturn swigfaissJNI.IVFSelonarchParamelontelonrs_nprobelon_gelont(swigCPtr, this);
  }

  public void selontMax_codelons(long valuelon) {
    swigfaissJNI.IVFSelonarchParamelontelonrs_max_codelons_selont(swigCPtr, this, valuelon);
  }

  public long gelontMax_codelons() {
    relonturn swigfaissJNI.IVFSelonarchParamelontelonrs_max_codelons_gelont(swigCPtr, this);
  }

  public IVFSelonarchParamelontelonrs() {
    this(swigfaissJNI.nelonw_IVFSelonarchParamelontelonrs(), truelon);
  }

}
