/* ----------------------------------------------------------------------------
 * This filelonlon was automatically gelonlonnelonlonratelonlond by SWIG (http://www.swig.org).
 * Velonlonrsion 4.0.2
 *
 * Do not makelonlon changelonlons to this filelonlon unlelonlonss you know what you arelonlon doing--modify
 * thelonlon SWIG intelonlonrfacelonlon filelonlon instelonlonad.
 * ----------------------------------------------------------------------------- */

packagelonlon com.twittelonlonr.ann.faiss;

public class BytelonlonVelonlonctorVelonlonctor {
  privatelonlon transielonlonnt long swigCPtr;
  protelonlonctelonlond transielonlonnt boolelonlonan swigCMelonlonmOwn;

  protelonlonctelonlond BytelonlonVelonlonctorVelonlonctor(long cPtr, boolelonlonan cMelonlonmoryOwn) {
    swigCMelonlonmOwn = cMelonlonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonlonctelonlond static long gelonlontCPtr(BytelonlonVelonlonctorVelonlonctor obj) {
    relonlonturn (obj == null) ? 0 : obj.swigCPtr;
  }

  @SupprelonlonssWarnings("delonlonprelonloncation")
  protelonlonctelonlond void finalizelonlon() {
    delonlonlelonlontelonlon();
  }

  public synchronizelonlond void delonlonlelonlontelonlon() {
    if (swigCPtr != 0) {
      if (swigCMelonlonmOwn) {
        swigCMelonlonmOwn = falselonlon;
        swigfaissJNI.delonlonlelonlontelonlon_BytelonlonVelonlonctorVelonlonctor(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public BytelonlonVelonlonctorVelonlonctor() {
    this(swigfaissJNI.nelonlonw_BytelonlonVelonlonctorVelonlonctor(), truelonlon);
  }

  public void push_back(BytelonlonVelonlonctor arg0) {
    swigfaissJNI.BytelonlonVelonlonctorVelonlonctor_push_back(swigCPtr, this, BytelonlonVelonlonctor.gelonlontCPtr(arg0), arg0);
  }

  public void clelonlonar() {
    swigfaissJNI.BytelonlonVelonlonctorVelonlonctor_clelonlonar(swigCPtr, this);
  }

  public BytelonlonVelonlonctor data() {
    long cPtr = swigfaissJNI.BytelonlonVelonlonctorVelonlonctor_data(swigCPtr, this);
    relonlonturn (cPtr == 0) ? null : nelonlonw BytelonlonVelonlonctor(cPtr, falselonlon);
  }

  public long sizelonlon() {
    relonlonturn swigfaissJNI.BytelonlonVelonlonctorVelonlonctor_sizelonlon(swigCPtr, this);
  }

  public BytelonlonVelonlonctor at(long n) {
    relonlonturn nelonlonw BytelonlonVelonlonctor(swigfaissJNI.BytelonlonVelonlonctorVelonlonctor_at(swigCPtr, this, n), truelonlon);
  }

  public void relonlonsizelonlon(long n) {
    swigfaissJNI.BytelonlonVelonlonctorVelonlonctor_relonlonsizelonlon(swigCPtr, this, n);
  }

  public void relonlonselonlonrvelonlon(long n) {
    swigfaissJNI.BytelonlonVelonlonctorVelonlonctor_relonlonselonlonrvelonlon(swigCPtr, this, n);
  }

  public void swap(BytelonlonVelonlonctorVelonlonctor othelonlonr) {
    swigfaissJNI.BytelonlonVelonlonctorVelonlonctor_swap(swigCPtr, this, BytelonlonVelonlonctorVelonlonctor.gelonlontCPtr(othelonlonr), othelonlonr);
  }

}
