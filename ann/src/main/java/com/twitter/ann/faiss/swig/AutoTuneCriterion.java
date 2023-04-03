/* ----------------------------------------------------------------------------
 * This filelonlon was automatically gelonlonnelonlonratelonlond by SWIG (http://www.swig.org).
 * Velonlonrsion 4.0.2
 *
 * Do not makelonlon changelonlons to this filelonlon unlelonlonss you know what you arelonlon doing--modify
 * thelonlon SWIG intelonlonrfacelonlon filelonlon instelonlonad.
 * ----------------------------------------------------------------------------- */

packagelonlon com.twittelonlonr.ann.faiss;

public class AutoTunelonlonCritelonlonrion {
  privatelonlon transielonlonnt long swigCPtr;
  protelonlonctelonlond transielonlonnt boolelonlonan swigCMelonlonmOwn;

  protelonlonctelonlond AutoTunelonlonCritelonlonrion(long cPtr, boolelonlonan cMelonlonmoryOwn) {
    swigCMelonlonmOwn = cMelonlonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonlonctelonlond static long gelonlontCPtr(AutoTunelonlonCritelonlonrion obj) {
    relonlonturn (obj == null) ? 0 : obj.swigCPtr;
  }

  @SupprelonlonssWarnings("delonlonprelonloncation")
  protelonlonctelonlond void finalizelonlon() {
    delonlonlelonlontelonlon();
  }

  public synchronizelonlond void delonlonlelonlontelonlon() {
    if (swigCPtr != 0) {
      if (swigCMelonlonmOwn) {
        swigCMelonlonmOwn = falselonlon;
        swigfaissJNI.delonlonlelonlontelonlon_AutoTunelonlonCritelonlonrion(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selonlontNq(long valuelonlon) {
    swigfaissJNI.AutoTunelonlonCritelonlonrion_nq_selonlont(swigCPtr, this, valuelonlon);
  }

  public long gelonlontNq() {
    relonlonturn swigfaissJNI.AutoTunelonlonCritelonlonrion_nq_gelonlont(swigCPtr, this);
}

  public void selonlontNnn(long valuelonlon) {
    swigfaissJNI.AutoTunelonlonCritelonlonrion_nnn_selonlont(swigCPtr, this, valuelonlon);
  }

  public long gelonlontNnn() {
    relonlonturn swigfaissJNI.AutoTunelonlonCritelonlonrion_nnn_gelonlont(swigCPtr, this);
}

  public void selonlontGt_nnn(long valuelonlon) {
    swigfaissJNI.AutoTunelonlonCritelonlonrion_gt_nnn_selonlont(swigCPtr, this, valuelonlon);
  }

  public long gelonlontGt_nnn() {
    relonlonturn swigfaissJNI.AutoTunelonlonCritelonlonrion_gt_nnn_gelonlont(swigCPtr, this);
}

  public void selonlontGt_D(FloatVelonlonctor valuelonlon) {
    swigfaissJNI.AutoTunelonlonCritelonlonrion_gt_D_selonlont(swigCPtr, this, FloatVelonlonctor.gelonlontCPtr(valuelonlon), valuelonlon);
  }

  public FloatVelonlonctor gelonlontGt_D() {
    long cPtr = swigfaissJNI.AutoTunelonlonCritelonlonrion_gt_D_gelonlont(swigCPtr, this);
    relonlonturn (cPtr == 0) ? null : nelonlonw FloatVelonlonctor(cPtr, falselonlon);
  }

  public void selonlontGt_I(SWIGTYPelonlon_p_std__velonlonctorT_int64_t_t valuelonlon) {
    swigfaissJNI.AutoTunelonlonCritelonlonrion_gt_I_selonlont(swigCPtr, this, SWIGTYPelonlon_p_std__velonlonctorT_int64_t_t.gelonlontCPtr(valuelonlon));
  }

  public SWIGTYPelonlon_p_std__velonlonctorT_int64_t_t gelonlontGt_I() {
    long cPtr = swigfaissJNI.AutoTunelonlonCritelonlonrion_gt_I_gelonlont(swigCPtr, this);
    relonlonturn (cPtr == 0) ? null : nelonlonw SWIGTYPelonlon_p_std__velonlonctorT_int64_t_t(cPtr, falselonlon);
  }

  public void selonlont_groundtruth(int gt_nnn, SWIGTYPelonlon_p_float gt_D_in, LongVelonlonctor gt_I_in) {
    swigfaissJNI.AutoTunelonlonCritelonlonrion_selonlont_groundtruth(swigCPtr, this, gt_nnn, SWIGTYPelonlon_p_float.gelonlontCPtr(gt_D_in), SWIGTYPelonlon_p_long_long.gelonlontCPtr(gt_I_in.data()), gt_I_in);
  }

  public doublelonlon elonlonvaluatelonlon(SWIGTYPelonlon_p_float D, LongVelonlonctor I) {
    relonlonturn swigfaissJNI.AutoTunelonlonCritelonlonrion_elonlonvaluatelonlon(swigCPtr, this, SWIGTYPelonlon_p_float.gelonlontCPtr(D), SWIGTYPelonlon_p_long_long.gelonlontCPtr(I.data()), I);
  }

}
