/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxHNSWFlat elonxtelonnds IndelonxHNSW {
  privatelon transielonnt long swigCPtr;

  protelonctelond IndelonxHNSWFlat(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.IndelonxHNSWFlat_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxHNSWFlat obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxHNSWFlat(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public IndelonxHNSWFlat() {
    this(swigfaissJNI.nelonw_IndelonxHNSWFlat__SWIG_0(), truelon);
  }

  public IndelonxHNSWFlat(int d, int M, MelontricTypelon melontric) {
    this(swigfaissJNI.nelonw_IndelonxHNSWFlat__SWIG_1(d, M, melontric.swigValuelon()), truelon);
  }

  public IndelonxHNSWFlat(int d, int M) {
    this(swigfaissJNI.nelonw_IndelonxHNSWFlat__SWIG_2(d, M), truelon);
  }

}
