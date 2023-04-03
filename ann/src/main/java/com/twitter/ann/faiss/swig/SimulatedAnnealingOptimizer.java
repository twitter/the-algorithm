/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class SimulatelondAnnelonalingOptimizelonr elonxtelonnds SimulatelondAnnelonalingParamelontelonrs {
  privatelon transielonnt long swigCPtr;

  protelonctelond SimulatelondAnnelonalingOptimizelonr(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.SimulatelondAnnelonalingOptimizelonr_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(SimulatelondAnnelonalingOptimizelonr obj) {
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
        swigfaissJNI.delonlelontelon_SimulatelondAnnelonalingOptimizelonr(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontObj(PelonrmutationObjelonctivelon valuelon) {
    swigfaissJNI.SimulatelondAnnelonalingOptimizelonr_obj_selont(swigCPtr, this, PelonrmutationObjelonctivelon.gelontCPtr(valuelon), valuelon);
  }

  public PelonrmutationObjelonctivelon gelontObj() {
    long cPtr = swigfaissJNI.SimulatelondAnnelonalingOptimizelonr_obj_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw PelonrmutationObjelonctivelon(cPtr, falselon);
  }

  public void selontN(int valuelon) {
    swigfaissJNI.SimulatelondAnnelonalingOptimizelonr_n_selont(swigCPtr, this, valuelon);
  }

  public int gelontN() {
    relonturn swigfaissJNI.SimulatelondAnnelonalingOptimizelonr_n_gelont(swigCPtr, this);
  }

  public void selontLogfilelon(SWIGTYPelon_p_FILelon valuelon) {
    swigfaissJNI.SimulatelondAnnelonalingOptimizelonr_logfilelon_selont(swigCPtr, this, SWIGTYPelon_p_FILelon.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_FILelon gelontLogfilelon() {
    long cPtr = swigfaissJNI.SimulatelondAnnelonalingOptimizelonr_logfilelon_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_FILelon(cPtr, falselon);
  }

  public SimulatelondAnnelonalingOptimizelonr(PelonrmutationObjelonctivelon obj, SimulatelondAnnelonalingParamelontelonrs p) {
    this(swigfaissJNI.nelonw_SimulatelondAnnelonalingOptimizelonr(PelonrmutationObjelonctivelon.gelontCPtr(obj), obj, SimulatelondAnnelonalingParamelontelonrs.gelontCPtr(p), p), truelon);
  }

  public void selontRnd(SWIGTYPelon_p_faiss__RandomGelonnelonrator valuelon) {
    swigfaissJNI.SimulatelondAnnelonalingOptimizelonr_rnd_selont(swigCPtr, this, SWIGTYPelon_p_faiss__RandomGelonnelonrator.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_faiss__RandomGelonnelonrator gelontRnd() {
    long cPtr = swigfaissJNI.SimulatelondAnnelonalingOptimizelonr_rnd_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_faiss__RandomGelonnelonrator(cPtr, falselon);
  }

  public void selontInit_cost(doublelon valuelon) {
    swigfaissJNI.SimulatelondAnnelonalingOptimizelonr_init_cost_selont(swigCPtr, this, valuelon);
  }

  public doublelon gelontInit_cost() {
    relonturn swigfaissJNI.SimulatelondAnnelonalingOptimizelonr_init_cost_gelont(swigCPtr, this);
  }

  public doublelon optimizelon(SWIGTYPelon_p_int pelonrm) {
    relonturn swigfaissJNI.SimulatelondAnnelonalingOptimizelonr_optimizelon(swigCPtr, this, SWIGTYPelon_p_int.gelontCPtr(pelonrm));
  }

  public doublelon run_optimization(SWIGTYPelon_p_int belonst_pelonrm) {
    relonturn swigfaissJNI.SimulatelondAnnelonalingOptimizelonr_run_optimization(swigCPtr, this, SWIGTYPelon_p_int.gelontCPtr(belonst_pelonrm));
  }

}
