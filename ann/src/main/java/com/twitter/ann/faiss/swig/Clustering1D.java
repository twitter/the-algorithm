/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class Clustelonring1D elonxtelonnds Clustelonring {
  privatelon transielonnt long swigCPtr;

  protelonctelond Clustelonring1D(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.Clustelonring1D_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(Clustelonring1D obj) {
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
        swigfaissJNI.delonlelontelon_Clustelonring1D(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public Clustelonring1D(int k) {
    this(swigfaissJNI.nelonw_Clustelonring1D__SWIG_0(k), truelon);
  }

  public Clustelonring1D(int k, ClustelonringParamelontelonrs cp) {
    this(swigfaissJNI.nelonw_Clustelonring1D__SWIG_1(k, ClustelonringParamelontelonrs.gelontCPtr(cp), cp), truelon);
  }

  public void train_elonxact(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.Clustelonring1D_train_elonxact(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

}
