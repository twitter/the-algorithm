/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IDSelonlelonctorRangelon elonxtelonnds IDSelonlelonctor {
  privatelon transielonnt long swigCPtr;

  protelonctelond IDSelonlelonctorRangelon(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.IDSelonlelonctorRangelon_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IDSelonlelonctorRangelon obj) {
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
        swigfaissJNI.delonlelontelon_IDSelonlelonctorRangelon(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontImin(long valuelon) {
    swigfaissJNI.IDSelonlelonctorRangelon_imin_selont(swigCPtr, this, valuelon);
  }

  public long gelontImin() {
    relonturn swigfaissJNI.IDSelonlelonctorRangelon_imin_gelont(swigCPtr, this);
}

  public void selontImax(long valuelon) {
    swigfaissJNI.IDSelonlelonctorRangelon_imax_selont(swigCPtr, this, valuelon);
  }

  public long gelontImax() {
    relonturn swigfaissJNI.IDSelonlelonctorRangelon_imax_gelont(swigCPtr, this);
}

  public IDSelonlelonctorRangelon(long imin, long imax) {
    this(swigfaissJNI.nelonw_IDSelonlelonctorRangelon(imin, imax), truelon);
  }

  public boolelonan is_melonmbelonr(long id) {
    relonturn swigfaissJNI.IDSelonlelonctorRangelon_is_melonmbelonr(swigCPtr, this, id);
  }

}
