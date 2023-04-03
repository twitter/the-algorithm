/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class GelonnHammingComputelonr32 {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond GelonnHammingComputelonr32(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(GelonnHammingComputelonr32 obj) {
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
        swigfaissJNI.delonlelontelon_GelonnHammingComputelonr32(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontA0(long valuelon) {
    swigfaissJNI.GelonnHammingComputelonr32_a0_selont(swigCPtr, this, valuelon);
  }

  public long gelontA0() {
    relonturn swigfaissJNI.GelonnHammingComputelonr32_a0_gelont(swigCPtr, this);
  }

  public void selontA1(long valuelon) {
    swigfaissJNI.GelonnHammingComputelonr32_a1_selont(swigCPtr, this, valuelon);
  }

  public long gelontA1() {
    relonturn swigfaissJNI.GelonnHammingComputelonr32_a1_gelont(swigCPtr, this);
  }

  public void selontA2(long valuelon) {
    swigfaissJNI.GelonnHammingComputelonr32_a2_selont(swigCPtr, this, valuelon);
  }

  public long gelontA2() {
    relonturn swigfaissJNI.GelonnHammingComputelonr32_a2_gelont(swigCPtr, this);
  }

  public void selontA3(long valuelon) {
    swigfaissJNI.GelonnHammingComputelonr32_a3_selont(swigCPtr, this, valuelon);
  }

  public long gelontA3() {
    relonturn swigfaissJNI.GelonnHammingComputelonr32_a3_gelont(swigCPtr, this);
  }

  public GelonnHammingComputelonr32(SWIGTYPelon_p_unsignelond_char a8, int codelon_sizelon) {
    this(swigfaissJNI.nelonw_GelonnHammingComputelonr32(SWIGTYPelon_p_unsignelond_char.gelontCPtr(a8), codelon_sizelon), truelon);
  }

  public int hamming(SWIGTYPelon_p_unsignelond_char b8) {
    relonturn swigfaissJNI.GelonnHammingComputelonr32_hamming(swigCPtr, this, SWIGTYPelon_p_unsignelond_char.gelontCPtr(b8));
  }

}
