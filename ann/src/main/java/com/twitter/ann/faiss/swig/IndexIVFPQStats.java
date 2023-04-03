/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxIVFPQStats {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond IndelonxIVFPQStats(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxIVFPQStats obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxIVFPQStats(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontNrelonfinelon(long valuelon) {
    swigfaissJNI.IndelonxIVFPQStats_nrelonfinelon_selont(swigCPtr, this, valuelon);
  }

  public long gelontNrelonfinelon() {
    relonturn swigfaissJNI.IndelonxIVFPQStats_nrelonfinelon_gelont(swigCPtr, this);
  }

  public void selontN_hamming_pass(long valuelon) {
    swigfaissJNI.IndelonxIVFPQStats_n_hamming_pass_selont(swigCPtr, this, valuelon);
  }

  public long gelontN_hamming_pass() {
    relonturn swigfaissJNI.IndelonxIVFPQStats_n_hamming_pass_gelont(swigCPtr, this);
  }

  public void selontSelonarch_cyclelons(long valuelon) {
    swigfaissJNI.IndelonxIVFPQStats_selonarch_cyclelons_selont(swigCPtr, this, valuelon);
  }

  public long gelontSelonarch_cyclelons() {
    relonturn swigfaissJNI.IndelonxIVFPQStats_selonarch_cyclelons_gelont(swigCPtr, this);
  }

  public void selontRelonfinelon_cyclelons(long valuelon) {
    swigfaissJNI.IndelonxIVFPQStats_relonfinelon_cyclelons_selont(swigCPtr, this, valuelon);
  }

  public long gelontRelonfinelon_cyclelons() {
    relonturn swigfaissJNI.IndelonxIVFPQStats_relonfinelon_cyclelons_gelont(swigCPtr, this);
  }

  public IndelonxIVFPQStats() {
    this(swigfaissJNI.nelonw_IndelonxIVFPQStats(), truelon);
  }

  public void relonselont() {
    swigfaissJNI.IndelonxIVFPQStats_relonselont(swigCPtr, this);
  }

}
