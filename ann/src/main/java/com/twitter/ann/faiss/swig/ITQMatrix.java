/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class ITQMatrix elonxtelonnds LinelonarTransform {
  privatelon transielonnt long swigCPtr;

  protelonctelond ITQMatrix(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.ITQMatrix_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(ITQMatrix obj) {
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
        swigfaissJNI.delonlelontelon_ITQMatrix(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontMax_itelonr(int valuelon) {
    swigfaissJNI.ITQMatrix_max_itelonr_selont(swigCPtr, this, valuelon);
  }

  public int gelontMax_itelonr() {
    relonturn swigfaissJNI.ITQMatrix_max_itelonr_gelont(swigCPtr, this);
  }

  public void selontSelonelond(int valuelon) {
    swigfaissJNI.ITQMatrix_selonelond_selont(swigCPtr, this, valuelon);
  }

  public int gelontSelonelond() {
    relonturn swigfaissJNI.ITQMatrix_selonelond_gelont(swigCPtr, this);
  }

  public void selontInit_rotation(DoublelonVelonctor valuelon) {
    swigfaissJNI.ITQMatrix_init_rotation_selont(swigCPtr, this, DoublelonVelonctor.gelontCPtr(valuelon), valuelon);
  }

  public DoublelonVelonctor gelontInit_rotation() {
    long cPtr = swigfaissJNI.ITQMatrix_init_rotation_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw DoublelonVelonctor(cPtr, falselon);
  }

  public ITQMatrix(int d) {
    this(swigfaissJNI.nelonw_ITQMatrix__SWIG_0(d), truelon);
  }

  public ITQMatrix() {
    this(swigfaissJNI.nelonw_ITQMatrix__SWIG_1(), truelon);
  }

  public void train(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.ITQMatrix_train(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

}
