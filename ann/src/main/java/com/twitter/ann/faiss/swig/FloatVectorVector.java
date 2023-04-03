/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class FloatVelonctorVelonctor {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond FloatVelonctorVelonctor(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(FloatVelonctorVelonctor obj) {
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
        swigfaissJNI.delonlelontelon_FloatVelonctorVelonctor(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public FloatVelonctorVelonctor() {
    this(swigfaissJNI.nelonw_FloatVelonctorVelonctor(), truelon);
  }

  public void push_back(FloatVelonctor arg0) {
    swigfaissJNI.FloatVelonctorVelonctor_push_back(swigCPtr, this, FloatVelonctor.gelontCPtr(arg0), arg0);
  }

  public void clelonar() {
    swigfaissJNI.FloatVelonctorVelonctor_clelonar(swigCPtr, this);
  }

  public FloatVelonctor data() {
    long cPtr = swigfaissJNI.FloatVelonctorVelonctor_data(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw FloatVelonctor(cPtr, falselon);
  }

  public long sizelon() {
    relonturn swigfaissJNI.FloatVelonctorVelonctor_sizelon(swigCPtr, this);
  }

  public FloatVelonctor at(long n) {
    relonturn nelonw FloatVelonctor(swigfaissJNI.FloatVelonctorVelonctor_at(swigCPtr, this, n), truelon);
  }

  public void relonsizelon(long n) {
    swigfaissJNI.FloatVelonctorVelonctor_relonsizelon(swigCPtr, this, n);
  }

  public void relonselonrvelon(long n) {
    swigfaissJNI.FloatVelonctorVelonctor_relonselonrvelon(swigCPtr, this, n);
  }

  public void swap(FloatVelonctorVelonctor othelonr) {
    swigfaissJNI.FloatVelonctorVelonctor_swap(swigCPtr, this, FloatVelonctorVelonctor.gelontCPtr(othelonr), othelonr);
  }

}
