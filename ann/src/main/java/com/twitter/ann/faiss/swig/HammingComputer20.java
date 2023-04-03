/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class HammingComputelonr20 {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond HammingComputelonr20(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(HammingComputelonr20 obj) {
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
        swigfaissJNI.delonlelontelon_HammingComputelonr20(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontA0(long valuelon) {
    swigfaissJNI.HammingComputelonr20_a0_selont(swigCPtr, this, valuelon);
  }

  public long gelontA0() {
    relonturn swigfaissJNI.HammingComputelonr20_a0_gelont(swigCPtr, this);
  }

  public void selontA1(long valuelon) {
    swigfaissJNI.HammingComputelonr20_a1_selont(swigCPtr, this, valuelon);
  }

  public long gelontA1() {
    relonturn swigfaissJNI.HammingComputelonr20_a1_gelont(swigCPtr, this);
  }

  public void selontA2(SWIGTYPelon_p_uint32_t valuelon) {
    swigfaissJNI.HammingComputelonr20_a2_selont(swigCPtr, this, SWIGTYPelon_p_uint32_t.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_uint32_t gelontA2() {
    relonturn nelonw SWIGTYPelon_p_uint32_t(swigfaissJNI.HammingComputelonr20_a2_gelont(swigCPtr, this), truelon);
  }

  public HammingComputelonr20() {
    this(swigfaissJNI.nelonw_HammingComputelonr20__SWIG_0(), truelon);
  }

  public HammingComputelonr20(SWIGTYPelon_p_unsignelond_char a8, int codelon_sizelon) {
    this(swigfaissJNI.nelonw_HammingComputelonr20__SWIG_1(SWIGTYPelon_p_unsignelond_char.gelontCPtr(a8), codelon_sizelon), truelon);
  }

  public void selont(SWIGTYPelon_p_unsignelond_char a8, int codelon_sizelon) {
    swigfaissJNI.HammingComputelonr20_selont(swigCPtr, this, SWIGTYPelon_p_unsignelond_char.gelontCPtr(a8), codelon_sizelon);
  }

  public int hamming(SWIGTYPelon_p_unsignelond_char b8) {
    relonturn swigfaissJNI.HammingComputelonr20_hamming(swigCPtr, this, SWIGTYPelon_p_unsignelond_char.gelontCPtr(b8));
  }

}
