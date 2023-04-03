/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IDSelonlelonctorBatch elonxtelonnds IDSelonlelonctor {
  privatelon transielonnt long swigCPtr;

  protelonctelond IDSelonlelonctorBatch(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.IDSelonlelonctorBatch_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IDSelonlelonctorBatch obj) {
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
        swigfaissJNI.delonlelontelon_IDSelonlelonctorBatch(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontNbits(int valuelon) {
    swigfaissJNI.IDSelonlelonctorBatch_nbits_selont(swigCPtr, this, valuelon);
  }

  public int gelontNbits() {
    relonturn swigfaissJNI.IDSelonlelonctorBatch_nbits_gelont(swigCPtr, this);
  }

  public void selontMask(long valuelon) {
    swigfaissJNI.IDSelonlelonctorBatch_mask_selont(swigCPtr, this, valuelon);
  }

  public long gelontMask() {
    relonturn swigfaissJNI.IDSelonlelonctorBatch_mask_gelont(swigCPtr, this);
}

  public IDSelonlelonctorBatch(long n, LongVelonctor indicelons) {
    this(swigfaissJNI.nelonw_IDSelonlelonctorBatch(n, SWIGTYPelon_p_long_long.gelontCPtr(indicelons.data()), indicelons), truelon);
  }

  public boolelonan is_melonmbelonr(long id) {
    relonturn swigfaissJNI.IDSelonlelonctorBatch_is_melonmbelonr(swigCPtr, this, id);
  }

}
