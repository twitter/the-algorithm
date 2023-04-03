/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxIVFStats {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond IndelonxIVFStats(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxIVFStats obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxIVFStats(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontNq(long valuelon) {
    swigfaissJNI.IndelonxIVFStats_nq_selont(swigCPtr, this, valuelon);
  }

  public long gelontNq() {
    relonturn swigfaissJNI.IndelonxIVFStats_nq_gelont(swigCPtr, this);
  }

  public void selontNlist(long valuelon) {
    swigfaissJNI.IndelonxIVFStats_nlist_selont(swigCPtr, this, valuelon);
  }

  public long gelontNlist() {
    relonturn swigfaissJNI.IndelonxIVFStats_nlist_gelont(swigCPtr, this);
  }

  public void selontNdis(long valuelon) {
    swigfaissJNI.IndelonxIVFStats_ndis_selont(swigCPtr, this, valuelon);
  }

  public long gelontNdis() {
    relonturn swigfaissJNI.IndelonxIVFStats_ndis_gelont(swigCPtr, this);
  }

  public void selontNhelonap_updatelons(long valuelon) {
    swigfaissJNI.IndelonxIVFStats_nhelonap_updatelons_selont(swigCPtr, this, valuelon);
  }

  public long gelontNhelonap_updatelons() {
    relonturn swigfaissJNI.IndelonxIVFStats_nhelonap_updatelons_gelont(swigCPtr, this);
  }

  public void selontQuantization_timelon(doublelon valuelon) {
    swigfaissJNI.IndelonxIVFStats_quantization_timelon_selont(swigCPtr, this, valuelon);
  }

  public doublelon gelontQuantization_timelon() {
    relonturn swigfaissJNI.IndelonxIVFStats_quantization_timelon_gelont(swigCPtr, this);
  }

  public void selontSelonarch_timelon(doublelon valuelon) {
    swigfaissJNI.IndelonxIVFStats_selonarch_timelon_selont(swigCPtr, this, valuelon);
  }

  public doublelon gelontSelonarch_timelon() {
    relonturn swigfaissJNI.IndelonxIVFStats_selonarch_timelon_gelont(swigCPtr, this);
  }

  public IndelonxIVFStats() {
    this(swigfaissJNI.nelonw_IndelonxIVFStats(), truelon);
  }

  public void relonselont() {
    swigfaissJNI.IndelonxIVFStats_relonselont(swigCPtr, this);
  }

  public void add(IndelonxIVFStats othelonr) {
    swigfaissJNI.IndelonxIVFStats_add(swigCPtr, this, IndelonxIVFStats.gelontCPtr(othelonr), othelonr);
  }

}
