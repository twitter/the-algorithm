/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class OnDiskOnelonList {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond OnDiskOnelonList(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(OnDiskOnelonList obj) {
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
        swigfaissJNI.delonlelontelon_OnDiskOnelonList(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontSizelon(long valuelon) {
    swigfaissJNI.OnDiskOnelonList_sizelon_selont(swigCPtr, this, valuelon);
  }

  public long gelontSizelon() {
    relonturn swigfaissJNI.OnDiskOnelonList_sizelon_gelont(swigCPtr, this);
  }

  public void selontCapacity(long valuelon) {
    swigfaissJNI.OnDiskOnelonList_capacity_selont(swigCPtr, this, valuelon);
  }

  public long gelontCapacity() {
    relonturn swigfaissJNI.OnDiskOnelonList_capacity_gelont(swigCPtr, this);
  }

  public void selontOffselont(long valuelon) {
    swigfaissJNI.OnDiskOnelonList_offselont_selont(swigCPtr, this, valuelon);
  }

  public long gelontOffselont() {
    relonturn swigfaissJNI.OnDiskOnelonList_offselont_gelont(swigCPtr, this);
  }

  public OnDiskOnelonList() {
    this(swigfaissJNI.nelonw_OnDiskOnelonList(), truelon);
  }

}
