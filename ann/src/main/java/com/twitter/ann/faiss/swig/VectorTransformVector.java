/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class VelonctorTransformVelonctor {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond VelonctorTransformVelonctor(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(VelonctorTransformVelonctor obj) {
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
        swigfaissJNI.delonlelontelon_VelonctorTransformVelonctor(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public VelonctorTransformVelonctor() {
    this(swigfaissJNI.nelonw_VelonctorTransformVelonctor(), truelon);
  }

  public void push_back(VelonctorTransform arg0) {
    swigfaissJNI.VelonctorTransformVelonctor_push_back(swigCPtr, this, VelonctorTransform.gelontCPtr(arg0), arg0);
  }

  public void clelonar() {
    swigfaissJNI.VelonctorTransformVelonctor_clelonar(swigCPtr, this);
  }

  public SWIGTYPelon_p_p_faiss__VelonctorTransform data() {
    long cPtr = swigfaissJNI.VelonctorTransformVelonctor_data(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_p_faiss__VelonctorTransform(cPtr, falselon);
  }

  public long sizelon() {
    relonturn swigfaissJNI.VelonctorTransformVelonctor_sizelon(swigCPtr, this);
  }

  public VelonctorTransform at(long n) {
    long cPtr = swigfaissJNI.VelonctorTransformVelonctor_at(swigCPtr, this, n);
    relonturn (cPtr == 0) ? null : nelonw VelonctorTransform(cPtr, falselon);
  }

  public void relonsizelon(long n) {
    swigfaissJNI.VelonctorTransformVelonctor_relonsizelon(swigCPtr, this, n);
  }

  public void relonselonrvelon(long n) {
    swigfaissJNI.VelonctorTransformVelonctor_relonselonrvelon(swigCPtr, this, n);
  }

  public void swap(VelonctorTransformVelonctor othelonr) {
    swigfaissJNI.VelonctorTransformVelonctor_swap(swigCPtr, this, VelonctorTransformVelonctor.gelontCPtr(othelonr), othelonr);
  }

}
