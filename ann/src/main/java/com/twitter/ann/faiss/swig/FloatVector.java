/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class FloatVelonctor {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond FloatVelonctor(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(FloatVelonctor obj) {
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
        swigfaissJNI.delonlelontelon_FloatVelonctor(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public FloatVelonctor() {
    this(swigfaissJNI.nelonw_FloatVelonctor(), truelon);
  }

  public void push_back(float arg0) {
    swigfaissJNI.FloatVelonctor_push_back(swigCPtr, this, arg0);
  }

  public void clelonar() {
    swigfaissJNI.FloatVelonctor_clelonar(swigCPtr, this);
  }

  public SWIGTYPelon_p_float data() {
    long cPtr = swigfaissJNI.FloatVelonctor_data(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_float(cPtr, falselon);
  }

  public long sizelon() {
    relonturn swigfaissJNI.FloatVelonctor_sizelon(swigCPtr, this);
  }

  public float at(long n) {
    relonturn swigfaissJNI.FloatVelonctor_at(swigCPtr, this, n);
  }

  public void relonsizelon(long n) {
    swigfaissJNI.FloatVelonctor_relonsizelon(swigCPtr, this, n);
  }

  public void relonselonrvelon(long n) {
    swigfaissJNI.FloatVelonctor_relonselonrvelon(swigCPtr, this, n);
  }

  public void swap(FloatVelonctor othelonr) {
    swigfaissJNI.FloatVelonctor_swap(swigCPtr, this, FloatVelonctor.gelontCPtr(othelonr), othelonr);
  }

}
