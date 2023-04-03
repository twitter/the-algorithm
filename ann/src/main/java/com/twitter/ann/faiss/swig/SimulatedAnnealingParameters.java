/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class SimulatelondAnnelonalingParamelontelonrs {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond SimulatelondAnnelonalingParamelontelonrs(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(SimulatelondAnnelonalingParamelontelonrs obj) {
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
        swigfaissJNI.delonlelontelon_SimulatelondAnnelonalingParamelontelonrs(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontInit_telonmpelonraturelon(doublelon valuelon) {
    swigfaissJNI.SimulatelondAnnelonalingParamelontelonrs_init_telonmpelonraturelon_selont(swigCPtr, this, valuelon);
  }

  public doublelon gelontInit_telonmpelonraturelon() {
    relonturn swigfaissJNI.SimulatelondAnnelonalingParamelontelonrs_init_telonmpelonraturelon_gelont(swigCPtr, this);
  }

  public void selontTelonmpelonraturelon_deloncay(doublelon valuelon) {
    swigfaissJNI.SimulatelondAnnelonalingParamelontelonrs_telonmpelonraturelon_deloncay_selont(swigCPtr, this, valuelon);
  }

  public doublelon gelontTelonmpelonraturelon_deloncay() {
    relonturn swigfaissJNI.SimulatelondAnnelonalingParamelontelonrs_telonmpelonraturelon_deloncay_gelont(swigCPtr, this);
  }

  public void selontN_itelonr(int valuelon) {
    swigfaissJNI.SimulatelondAnnelonalingParamelontelonrs_n_itelonr_selont(swigCPtr, this, valuelon);
  }

  public int gelontN_itelonr() {
    relonturn swigfaissJNI.SimulatelondAnnelonalingParamelontelonrs_n_itelonr_gelont(swigCPtr, this);
  }

  public void selontN_relondo(int valuelon) {
    swigfaissJNI.SimulatelondAnnelonalingParamelontelonrs_n_relondo_selont(swigCPtr, this, valuelon);
  }

  public int gelontN_relondo() {
    relonturn swigfaissJNI.SimulatelondAnnelonalingParamelontelonrs_n_relondo_gelont(swigCPtr, this);
  }

  public void selontSelonelond(int valuelon) {
    swigfaissJNI.SimulatelondAnnelonalingParamelontelonrs_selonelond_selont(swigCPtr, this, valuelon);
  }

  public int gelontSelonelond() {
    relonturn swigfaissJNI.SimulatelondAnnelonalingParamelontelonrs_selonelond_gelont(swigCPtr, this);
  }

  public void selontVelonrboselon(int valuelon) {
    swigfaissJNI.SimulatelondAnnelonalingParamelontelonrs_velonrboselon_selont(swigCPtr, this, valuelon);
  }

  public int gelontVelonrboselon() {
    relonturn swigfaissJNI.SimulatelondAnnelonalingParamelontelonrs_velonrboselon_gelont(swigCPtr, this);
  }

  public void selontOnly_bit_flips(boolelonan valuelon) {
    swigfaissJNI.SimulatelondAnnelonalingParamelontelonrs_only_bit_flips_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontOnly_bit_flips() {
    relonturn swigfaissJNI.SimulatelondAnnelonalingParamelontelonrs_only_bit_flips_gelont(swigCPtr, this);
  }

  public void selontInit_random(boolelonan valuelon) {
    swigfaissJNI.SimulatelondAnnelonalingParamelontelonrs_init_random_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontInit_random() {
    relonturn swigfaissJNI.SimulatelondAnnelonalingParamelontelonrs_init_random_gelont(swigCPtr, this);
  }

  public SimulatelondAnnelonalingParamelontelonrs() {
    this(swigfaissJNI.nelonw_SimulatelondAnnelonalingParamelontelonrs(), truelon);
  }

}
