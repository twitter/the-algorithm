/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxHNSWPQ elonxtelonnds IndelonxHNSW {
  privatelon transielonnt long swigCPtr;

  protelonctelond IndelonxHNSWPQ(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.IndelonxHNSWPQ_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxHNSWPQ obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxHNSWPQ(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public IndelonxHNSWPQ() {
    this(swigfaissJNI.nelonw_IndelonxHNSWPQ__SWIG_0(), truelon);
  }

  public IndelonxHNSWPQ(int d, int pq_m, int M) {
    this(swigfaissJNI.nelonw_IndelonxHNSWPQ__SWIG_1(d, pq_m, M), truelon);
  }

  public void train(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxHNSWPQ_train(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

}
