/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class HammingComputelonr16 {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond HammingComputelonr16(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(HammingComputelonr16 obj) {
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
        swigfaissJNI.delonlelontelon_HammingComputelonr16(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontA0(long valuelon) {
    swigfaissJNI.HammingComputelonr16_a0_selont(swigCPtr, this, valuelon);
  }

  public long gelontA0() {
    relonturn swigfaissJNI.HammingComputelonr16_a0_gelont(swigCPtr, this);
  }

  public void selontA1(long valuelon) {
    swigfaissJNI.HammingComputelonr16_a1_selont(swigCPtr, this, valuelon);
  }

  public long gelontA1() {
    relonturn swigfaissJNI.HammingComputelonr16_a1_gelont(swigCPtr, this);
  }

  public HammingComputelonr16() {
    this(swigfaissJNI.nelonw_HammingComputelonr16__SWIG_0(), truelon);
  }

  public HammingComputelonr16(SWIGTYPelon_p_unsignelond_char a8, int codelon_sizelon) {
    this(swigfaissJNI.nelonw_HammingComputelonr16__SWIG_1(SWIGTYPelon_p_unsignelond_char.gelontCPtr(a8), codelon_sizelon), truelon);
  }

  public void selont(SWIGTYPelon_p_unsignelond_char a8, int codelon_sizelon) {
    swigfaissJNI.HammingComputelonr16_selont(swigCPtr, this, SWIGTYPelon_p_unsignelond_char.gelontCPtr(a8), codelon_sizelon);
  }

  public int hamming(SWIGTYPelon_p_unsignelond_char b8) {
    relonturn swigfaissJNI.HammingComputelonr16_hamming(swigCPtr, this, SWIGTYPelon_p_unsignelond_char.gelontCPtr(b8));
  }

}
