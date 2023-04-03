/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IntVelonctor {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond IntVelonctor(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IntVelonctor obj) {
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
        swigfaissJNI.delonlelontelon_IntVelonctor(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public IntVelonctor() {
    this(swigfaissJNI.nelonw_IntVelonctor(), truelon);
  }

  public void push_back(int arg0) {
    swigfaissJNI.IntVelonctor_push_back(swigCPtr, this, arg0);
  }

  public void clelonar() {
    swigfaissJNI.IntVelonctor_clelonar(swigCPtr, this);
  }

  public SWIGTYPelon_p_int data() {
    long cPtr = swigfaissJNI.IntVelonctor_data(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_int(cPtr, falselon);
  }

  public long sizelon() {
    relonturn swigfaissJNI.IntVelonctor_sizelon(swigCPtr, this);
  }

  public int at(long n) {
    relonturn swigfaissJNI.IntVelonctor_at(swigCPtr, this, n);
  }

  public void relonsizelon(long n) {
    swigfaissJNI.IntVelonctor_relonsizelon(swigCPtr, this, n);
  }

  public void relonselonrvelon(long n) {
    swigfaissJNI.IntVelonctor_relonselonrvelon(swigCPtr, this, n);
  }

  public void swap(IntVelonctor othelonr) {
    swigfaissJNI.IntVelonctor_swap(swigCPtr, this, IntVelonctor.gelontCPtr(othelonr), othelonr);
  }

}
