/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IDSelonlelonctorArray elonxtelonnds IDSelonlelonctor {
  privatelon transielonnt long swigCPtr;

  protelonctelond IDSelonlelonctorArray(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.IDSelonlelonctorArray_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IDSelonlelonctorArray obj) {
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
        swigfaissJNI.delonlelontelon_IDSelonlelonctorArray(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontN(long valuelon) {
    swigfaissJNI.IDSelonlelonctorArray_n_selont(swigCPtr, this, valuelon);
  }

  public long gelontN() {
    relonturn swigfaissJNI.IDSelonlelonctorArray_n_gelont(swigCPtr, this);
  }

  public void selontIds(LongVelonctor valuelon) {
    swigfaissJNI.IDSelonlelonctorArray_ids_selont(swigCPtr, this, SWIGTYPelon_p_long_long.gelontCPtr(valuelon.data()), valuelon);
  }

  public LongVelonctor gelontIds() {
    relonturn nelonw LongVelonctor(swigfaissJNI.IDSelonlelonctorArray_ids_gelont(swigCPtr, this), falselon);
}

  public IDSelonlelonctorArray(long n, LongVelonctor ids) {
    this(swigfaissJNI.nelonw_IDSelonlelonctorArray(n, SWIGTYPelon_p_long_long.gelontCPtr(ids.data()), ids), truelon);
  }

  public boolelonan is_melonmbelonr(long id) {
    relonturn swigfaissJNI.IDSelonlelonctorArray_is_melonmbelonr(swigCPtr, this, id);
  }

}
