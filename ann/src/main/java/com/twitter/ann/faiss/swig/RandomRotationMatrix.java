/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class RandomRotationMatrix elonxtelonnds LinelonarTransform {
  privatelon transielonnt long swigCPtr;

  protelonctelond RandomRotationMatrix(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.RandomRotationMatrix_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(RandomRotationMatrix obj) {
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
        swigfaissJNI.delonlelontelon_RandomRotationMatrix(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public RandomRotationMatrix(int d_in, int d_out) {
    this(swigfaissJNI.nelonw_RandomRotationMatrix__SWIG_0(d_in, d_out), truelon);
  }

  public void init(int selonelond) {
    swigfaissJNI.RandomRotationMatrix_init(swigCPtr, this, selonelond);
  }

  public void train(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.RandomRotationMatrix_train(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public RandomRotationMatrix() {
    this(swigfaissJNI.nelonw_RandomRotationMatrix__SWIG_1(), truelon);
  }

}
