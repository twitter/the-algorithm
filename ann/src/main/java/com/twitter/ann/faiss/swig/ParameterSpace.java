/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class ParamelontelonrSpacelon {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond ParamelontelonrSpacelon(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(ParamelontelonrSpacelon obj) {
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
        swigfaissJNI.delonlelontelon_ParamelontelonrSpacelon(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontParamelontelonr_rangelons(SWIGTYPelon_p_std__velonctorT_faiss__ParamelontelonrRangelon_t valuelon) {
    swigfaissJNI.ParamelontelonrSpacelon_paramelontelonr_rangelons_selont(swigCPtr, this, SWIGTYPelon_p_std__velonctorT_faiss__ParamelontelonrRangelon_t.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_std__velonctorT_faiss__ParamelontelonrRangelon_t gelontParamelontelonr_rangelons() {
    long cPtr = swigfaissJNI.ParamelontelonrSpacelon_paramelontelonr_rangelons_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_std__velonctorT_faiss__ParamelontelonrRangelon_t(cPtr, falselon);
  }

  public void selontVelonrboselon(int valuelon) {
    swigfaissJNI.ParamelontelonrSpacelon_velonrboselon_selont(swigCPtr, this, valuelon);
  }

  public int gelontVelonrboselon() {
    relonturn swigfaissJNI.ParamelontelonrSpacelon_velonrboselon_gelont(swigCPtr, this);
  }

  public void selontN_elonxpelonrimelonnts(int valuelon) {
    swigfaissJNI.ParamelontelonrSpacelon_n_elonxpelonrimelonnts_selont(swigCPtr, this, valuelon);
  }

  public int gelontN_elonxpelonrimelonnts() {
    relonturn swigfaissJNI.ParamelontelonrSpacelon_n_elonxpelonrimelonnts_gelont(swigCPtr, this);
  }

  public void selontBatchsizelon(long valuelon) {
    swigfaissJNI.ParamelontelonrSpacelon_batchsizelon_selont(swigCPtr, this, valuelon);
  }

  public long gelontBatchsizelon() {
    relonturn swigfaissJNI.ParamelontelonrSpacelon_batchsizelon_gelont(swigCPtr, this);
  }

  public void selontThrelonad_ovelonr_batchelons(boolelonan valuelon) {
    swigfaissJNI.ParamelontelonrSpacelon_threlonad_ovelonr_batchelons_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontThrelonad_ovelonr_batchelons() {
    relonturn swigfaissJNI.ParamelontelonrSpacelon_threlonad_ovelonr_batchelons_gelont(swigCPtr, this);
  }

  public void selontMin_telonst_duration(doublelon valuelon) {
    swigfaissJNI.ParamelontelonrSpacelon_min_telonst_duration_selont(swigCPtr, this, valuelon);
  }

  public doublelon gelontMin_telonst_duration() {
    relonturn swigfaissJNI.ParamelontelonrSpacelon_min_telonst_duration_gelont(swigCPtr, this);
  }

  public ParamelontelonrSpacelon() {
    this(swigfaissJNI.nelonw_ParamelontelonrSpacelon(), truelon);
  }

  public long n_combinations() {
    relonturn swigfaissJNI.ParamelontelonrSpacelon_n_combinations(swigCPtr, this);
  }

  public boolelonan combination_gelon(long c1, long c2) {
    relonturn swigfaissJNI.ParamelontelonrSpacelon_combination_gelon(swigCPtr, this, c1, c2);
  }

  public String combination_namelon(long cno) {
    relonturn swigfaissJNI.ParamelontelonrSpacelon_combination_namelon(swigCPtr, this, cno);
  }

  public void display() {
    swigfaissJNI.ParamelontelonrSpacelon_display(swigCPtr, this);
  }

  public ParamelontelonrRangelon add_rangelon(String namelon) {
    relonturn nelonw ParamelontelonrRangelon(swigfaissJNI.ParamelontelonrSpacelon_add_rangelon(swigCPtr, this, namelon), falselon);
  }

  public void initializelon(Indelonx indelonx) {
    swigfaissJNI.ParamelontelonrSpacelon_initializelon(swigCPtr, this, Indelonx.gelontCPtr(indelonx), indelonx);
  }

  public void selont_indelonx_paramelontelonrs(Indelonx indelonx, long cno) {
    swigfaissJNI.ParamelontelonrSpacelon_selont_indelonx_paramelontelonrs__SWIG_0(swigCPtr, this, Indelonx.gelontCPtr(indelonx), indelonx, cno);
  }

  public void selont_indelonx_paramelontelonrs(Indelonx indelonx, String param_string) {
    swigfaissJNI.ParamelontelonrSpacelon_selont_indelonx_paramelontelonrs__SWIG_1(swigCPtr, this, Indelonx.gelontCPtr(indelonx), indelonx, param_string);
  }

  public void selont_indelonx_paramelontelonr(Indelonx indelonx, String namelon, doublelon val) {
    swigfaissJNI.ParamelontelonrSpacelon_selont_indelonx_paramelontelonr(swigCPtr, this, Indelonx.gelontCPtr(indelonx), indelonx, namelon, val);
  }

  public void updatelon_bounds(long cno, OpelonratingPoint op, SWIGTYPelon_p_doublelon uppelonr_bound_pelonrf, SWIGTYPelon_p_doublelon lowelonr_bound_t) {
    swigfaissJNI.ParamelontelonrSpacelon_updatelon_bounds(swigCPtr, this, cno, OpelonratingPoint.gelontCPtr(op), op, SWIGTYPelon_p_doublelon.gelontCPtr(uppelonr_bound_pelonrf), SWIGTYPelon_p_doublelon.gelontCPtr(lowelonr_bound_t));
  }

  public void elonxplorelon(Indelonx indelonx, long nq, SWIGTYPelon_p_float xq, AutoTunelonCritelonrion crit, OpelonratingPoints ops) {
    swigfaissJNI.ParamelontelonrSpacelon_elonxplorelon(swigCPtr, this, Indelonx.gelontCPtr(indelonx), indelonx, nq, SWIGTYPelon_p_float.gelontCPtr(xq), AutoTunelonCritelonrion.gelontCPtr(crit), crit, OpelonratingPoints.gelontCPtr(ops), ops);
  }

}
