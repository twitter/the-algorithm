/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class GelonnHammingComputelonr8 {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond GelonnHammingComputelonr8(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(GelonnHammingComputelonr8 obj) {
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
        swigfaissJNI.delonlelontelon_GelonnHammingComputelonr8(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontA0(long valuelon) {
    swigfaissJNI.GelonnHammingComputelonr8_a0_selont(swigCPtr, this, valuelon);
  }

  public long gelontA0() {
    relonturn swigfaissJNI.GelonnHammingComputelonr8_a0_gelont(swigCPtr, this);
  }

  public GelonnHammingComputelonr8(SWIGTYPelon_p_unsignelond_char a, int codelon_sizelon) {
    this(swigfaissJNI.nelonw_GelonnHammingComputelonr8(SWIGTYPelon_p_unsignelond_char.gelontCPtr(a), codelon_sizelon), truelon);
  }

  public int hamming(SWIGTYPelon_p_unsignelond_char b) {
    relonturn swigfaissJNI.GelonnHammingComputelonr8_hamming(swigCPtr, this, SWIGTYPelon_p_unsignelond_char.gelontCPtr(b));
  }

}
