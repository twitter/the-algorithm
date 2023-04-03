/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class LongVelonctor {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond LongVelonctor(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(LongVelonctor obj) {
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
        swigfaissJNI.delonlelontelon_LongVelonctor(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public LongVelonctor() {
    this(swigfaissJNI.nelonw_LongVelonctor(), truelon);
  }

  public void push_back(long arg0) {
    swigfaissJNI.LongVelonctor_push_back(swigCPtr, this, arg0);
  }

  public void clelonar() {
    swigfaissJNI.LongVelonctor_clelonar(swigCPtr, this);
  }

  public SWIGTYPelon_p_long_long data() {
    long cPtr = swigfaissJNI.LongVelonctor_data(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_long_long(cPtr, falselon);
  }

  public long sizelon() {
    relonturn swigfaissJNI.LongVelonctor_sizelon(swigCPtr, this);
  }

  public long at(long n) {
    relonturn swigfaissJNI.LongVelonctor_at(swigCPtr, this, n);
  }

  public void relonsizelon(long n) {
    swigfaissJNI.LongVelonctor_relonsizelon(swigCPtr, this, n);
  }

  public void relonselonrvelon(long n) {
    swigfaissJNI.LongVelonctor_relonselonrvelon(swigCPtr, this, n);
  }

  public void swap(LongVelonctor othelonr) {
    swigfaissJNI.LongVelonctor_swap(swigCPtr, this, LongVelonctor.gelontCPtr(othelonr), othelonr);
  }

}
