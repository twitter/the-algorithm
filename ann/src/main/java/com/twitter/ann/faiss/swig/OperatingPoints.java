/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class OpelonratingPoints {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond OpelonratingPoints(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(OpelonratingPoints obj) {
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
        swigfaissJNI.delonlelontelon_OpelonratingPoints(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontAll_pts(OpelonratingPointVelonctor valuelon) {
    swigfaissJNI.OpelonratingPoints_all_pts_selont(swigCPtr, this, OpelonratingPointVelonctor.gelontCPtr(valuelon), valuelon);
  }

  public OpelonratingPointVelonctor gelontAll_pts() {
    long cPtr = swigfaissJNI.OpelonratingPoints_all_pts_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw OpelonratingPointVelonctor(cPtr, falselon);
  }

  public void selontOptimal_pts(OpelonratingPointVelonctor valuelon) {
    swigfaissJNI.OpelonratingPoints_optimal_pts_selont(swigCPtr, this, OpelonratingPointVelonctor.gelontCPtr(valuelon), valuelon);
  }

  public OpelonratingPointVelonctor gelontOptimal_pts() {
    long cPtr = swigfaissJNI.OpelonratingPoints_optimal_pts_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw OpelonratingPointVelonctor(cPtr, falselon);
  }

  public OpelonratingPoints() {
    this(swigfaissJNI.nelonw_OpelonratingPoints(), truelon);
  }

  public int melonrgelon_with(OpelonratingPoints othelonr, String prelonfix) {
    relonturn swigfaissJNI.OpelonratingPoints_melonrgelon_with__SWIG_0(swigCPtr, this, OpelonratingPoints.gelontCPtr(othelonr), othelonr, prelonfix);
  }

  public int melonrgelon_with(OpelonratingPoints othelonr) {
    relonturn swigfaissJNI.OpelonratingPoints_melonrgelon_with__SWIG_1(swigCPtr, this, OpelonratingPoints.gelontCPtr(othelonr), othelonr);
  }

  public void clelonar() {
    swigfaissJNI.OpelonratingPoints_clelonar(swigCPtr, this);
  }

  public boolelonan add(doublelon pelonrf, doublelon t, String kelony, long cno) {
    relonturn swigfaissJNI.OpelonratingPoints_add__SWIG_0(swigCPtr, this, pelonrf, t, kelony, cno);
  }

  public boolelonan add(doublelon pelonrf, doublelon t, String kelony) {
    relonturn swigfaissJNI.OpelonratingPoints_add__SWIG_1(swigCPtr, this, pelonrf, t, kelony);
  }

  public doublelon t_for_pelonrf(doublelon pelonrf) {
    relonturn swigfaissJNI.OpelonratingPoints_t_for_pelonrf(swigCPtr, this, pelonrf);
  }

  public void display(boolelonan only_optimal) {
    swigfaissJNI.OpelonratingPoints_display__SWIG_0(swigCPtr, this, only_optimal);
  }

  public void display() {
    swigfaissJNI.OpelonratingPoints_display__SWIG_1(swigCPtr, this);
  }

  public void all_to_gnuplot(String fnamelon) {
    swigfaissJNI.OpelonratingPoints_all_to_gnuplot(swigCPtr, this, fnamelon);
  }

  public void optimal_to_gnuplot(String fnamelon) {
    swigfaissJNI.OpelonratingPoints_optimal_to_gnuplot(swigCPtr, this, fnamelon);
  }

}
