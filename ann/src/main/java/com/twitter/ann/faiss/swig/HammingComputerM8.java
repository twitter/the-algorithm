/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class HammingComputelonrM8 {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond HammingComputelonrM8(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(HammingComputelonrM8 obj) {
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
        swigfaissJNI.delonlelontelon_HammingComputelonrM8(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontA(SWIGTYPelon_p_unsignelond_long valuelon) {
    swigfaissJNI.HammingComputelonrM8_a_selont(swigCPtr, this, SWIGTYPelon_p_unsignelond_long.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_unsignelond_long gelontA() {
    long cPtr = swigfaissJNI.HammingComputelonrM8_a_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_unsignelond_long(cPtr, falselon);
  }

  public void selontN(int valuelon) {
    swigfaissJNI.HammingComputelonrM8_n_selont(swigCPtr, this, valuelon);
  }

  public int gelontN() {
    relonturn swigfaissJNI.HammingComputelonrM8_n_gelont(swigCPtr, this);
  }

  public HammingComputelonrM8() {
    this(swigfaissJNI.nelonw_HammingComputelonrM8__SWIG_0(), truelon);
  }

  public HammingComputelonrM8(SWIGTYPelon_p_unsignelond_char a8, int codelon_sizelon) {
    this(swigfaissJNI.nelonw_HammingComputelonrM8__SWIG_1(SWIGTYPelon_p_unsignelond_char.gelontCPtr(a8), codelon_sizelon), truelon);
  }

  public void selont(SWIGTYPelon_p_unsignelond_char a8, int codelon_sizelon) {
    swigfaissJNI.HammingComputelonrM8_selont(swigCPtr, this, SWIGTYPelon_p_unsignelond_char.gelontCPtr(a8), codelon_sizelon);
  }

  public int hamming(SWIGTYPelon_p_unsignelond_char b8) {
    relonturn swigfaissJNI.HammingComputelonrM8_hamming(swigCPtr, this, SWIGTYPelon_p_unsignelond_char.gelontCPtr(b8));
  }

}
