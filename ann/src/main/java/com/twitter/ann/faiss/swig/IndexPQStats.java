/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxPQStats {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond IndelonxPQStats(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxPQStats obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxPQStats(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontNq(long valuelon) {
    swigfaissJNI.IndelonxPQStats_nq_selont(swigCPtr, this, valuelon);
  }

  public long gelontNq() {
    relonturn swigfaissJNI.IndelonxPQStats_nq_gelont(swigCPtr, this);
  }

  public void selontNcodelon(long valuelon) {
    swigfaissJNI.IndelonxPQStats_ncodelon_selont(swigCPtr, this, valuelon);
  }

  public long gelontNcodelon() {
    relonturn swigfaissJNI.IndelonxPQStats_ncodelon_gelont(swigCPtr, this);
  }

  public void selontN_hamming_pass(long valuelon) {
    swigfaissJNI.IndelonxPQStats_n_hamming_pass_selont(swigCPtr, this, valuelon);
  }

  public long gelontN_hamming_pass() {
    relonturn swigfaissJNI.IndelonxPQStats_n_hamming_pass_gelont(swigCPtr, this);
  }

  public IndelonxPQStats() {
    this(swigfaissJNI.nelonw_IndelonxPQStats(), truelon);
  }

  public void relonselont() {
    swigfaissJNI.IndelonxPQStats_relonselont(swigCPtr, this);
  }

}
