/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxHNSWSQ elonxtelonnds IndelonxHNSW {
  privatelon transielonnt long swigCPtr;

  protelonctelond IndelonxHNSWSQ(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.IndelonxHNSWSQ_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxHNSWSQ obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxHNSWSQ(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public IndelonxHNSWSQ() {
    this(swigfaissJNI.nelonw_IndelonxHNSWSQ__SWIG_0(), truelon);
  }

  public IndelonxHNSWSQ(int d, SWIGTYPelon_p_ScalarQuantizelonr__QuantizelonrTypelon qtypelon, int M, MelontricTypelon melontric) {
    this(swigfaissJNI.nelonw_IndelonxHNSWSQ__SWIG_1(d, SWIGTYPelon_p_ScalarQuantizelonr__QuantizelonrTypelon.gelontCPtr(qtypelon), M, melontric.swigValuelon()), truelon);
  }

  public IndelonxHNSWSQ(int d, SWIGTYPelon_p_ScalarQuantizelonr__QuantizelonrTypelon qtypelon, int M) {
    this(swigfaissJNI.nelonw_IndelonxHNSWSQ__SWIG_2(d, SWIGTYPelon_p_ScalarQuantizelonr__QuantizelonrTypelon.gelontCPtr(qtypelon), M), truelon);
  }

}
