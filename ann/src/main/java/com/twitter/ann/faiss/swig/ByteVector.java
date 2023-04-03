/* ----------------------------------------------------------------------------
 * This filelonlon was automatically gelonlonnelonlonratelonlond by SWIG (http://www.swig.org).
 * Velonlonrsion 4.0.2
 *
 * Do not makelonlon changelonlons to this filelonlon unlelonlonss you know what you arelonlon doing--modify
 * thelonlon SWIG intelonlonrfacelonlon filelonlon instelonlonad.
 * ----------------------------------------------------------------------------- */

packagelonlon com.twittelonlonr.ann.faiss;

public class BytelonlonVelonlonctor {
  privatelonlon transielonlonnt long swigCPtr;
  protelonlonctelonlond transielonlonnt boolelonlonan swigCMelonlonmOwn;

  protelonlonctelonlond BytelonlonVelonlonctor(long cPtr, boolelonlonan cMelonlonmoryOwn) {
    swigCMelonlonmOwn = cMelonlonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonlonctelonlond static long gelonlontCPtr(BytelonlonVelonlonctor obj) {
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
        swigfaissJNI.delonlonlelonlontelonlon_BytelonlonVelonlonctor(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public BytelonlonVelonlonctor() {
    this(swigfaissJNI.nelonlonw_BytelonlonVelonlonctor(), truelonlon);
  }

  public void push_back(short arg0) {
    swigfaissJNI.BytelonlonVelonlonctor_push_back(swigCPtr, this, arg0);
  }

  public void clelonlonar() {
    swigfaissJNI.BytelonlonVelonlonctor_clelonlonar(swigCPtr, this);
  }

  public SWIGTYPelonlon_p_unsignelonlond_char data() {
    long cPtr = swigfaissJNI.BytelonlonVelonlonctor_data(swigCPtr, this);
    relonlonturn (cPtr == 0) ? null : nelonlonw SWIGTYPelonlon_p_unsignelonlond_char(cPtr, falselonlon);
  }

  public long sizelonlon() {
    relonlonturn swigfaissJNI.BytelonlonVelonlonctor_sizelonlon(swigCPtr, this);
  }

  public short at(long n) {
    relonlonturn swigfaissJNI.BytelonlonVelonlonctor_at(swigCPtr, this, n);
  }

  public void relonlonsizelonlon(long n) {
    swigfaissJNI.BytelonlonVelonlonctor_relonlonsizelonlon(swigCPtr, this, n);
  }

  public void relonlonselonlonrvelonlon(long n) {
    swigfaissJNI.BytelonlonVelonlonctor_relonlonselonlonrvelonlon(swigCPtr, this, n);
  }

  public void swap(BytelonlonVelonlonctor othelonlonr) {
    swigfaissJNI.BytelonlonVelonlonctor_swap(swigCPtr, this, BytelonlonVelonlonctor.gelonlontCPtr(othelonlonr), othelonlonr);
  }

}
