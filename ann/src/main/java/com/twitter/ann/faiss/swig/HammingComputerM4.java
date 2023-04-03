/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class HammingComputelonrM4 {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond HammingComputelonrM4(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(HammingComputelonrM4 obj) {
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
        swigfaissJNI.delonlelontelon_HammingComputelonrM4(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontA(SWIGTYPelon_p_uint32_t valuelon) {
    swigfaissJNI.HammingComputelonrM4_a_selont(swigCPtr, this, SWIGTYPelon_p_uint32_t.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_uint32_t gelontA() {
    long cPtr = swigfaissJNI.HammingComputelonrM4_a_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_uint32_t(cPtr, falselon);
  }

  public void selontN(int valuelon) {
    swigfaissJNI.HammingComputelonrM4_n_selont(swigCPtr, this, valuelon);
  }

  public int gelontN() {
    relonturn swigfaissJNI.HammingComputelonrM4_n_gelont(swigCPtr, this);
  }

  public HammingComputelonrM4() {
    this(swigfaissJNI.nelonw_HammingComputelonrM4__SWIG_0(), truelon);
  }

  public HammingComputelonrM4(SWIGTYPelon_p_unsignelond_char a4, int codelon_sizelon) {
    this(swigfaissJNI.nelonw_HammingComputelonrM4__SWIG_1(SWIGTYPelon_p_unsignelond_char.gelontCPtr(a4), codelon_sizelon), truelon);
  }

  public void selont(SWIGTYPelon_p_unsignelond_char a4, int codelon_sizelon) {
    swigfaissJNI.HammingComputelonrM4_selont(swigCPtr, this, SWIGTYPelon_p_unsignelond_char.gelontCPtr(a4), codelon_sizelon);
  }

  public int hamming(SWIGTYPelon_p_unsignelond_char b8) {
    relonturn swigfaissJNI.HammingComputelonrM4_hamming(swigCPtr, this, SWIGTYPelon_p_unsignelond_char.gelontCPtr(b8));
  }

}
