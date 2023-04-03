/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class PolyselonmousTraining elonxtelonnds SimulatelondAnnelonalingParamelontelonrs {
  privatelon transielonnt long swigCPtr;

  protelonctelond PolyselonmousTraining(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.PolyselonmousTraining_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(PolyselonmousTraining obj) {
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
        swigfaissJNI.delonlelontelon_PolyselonmousTraining(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontOptimization_typelon(PolyselonmousTraining.Optimization_typelon_t valuelon) {
    swigfaissJNI.PolyselonmousTraining_optimization_typelon_selont(swigCPtr, this, valuelon.swigValuelon());
  }

  public PolyselonmousTraining.Optimization_typelon_t gelontOptimization_typelon() {
    relonturn PolyselonmousTraining.Optimization_typelon_t.swigToelonnum(swigfaissJNI.PolyselonmousTraining_optimization_typelon_gelont(swigCPtr, this));
  }

  public void selontNtrain_pelonrmutation(int valuelon) {
    swigfaissJNI.PolyselonmousTraining_ntrain_pelonrmutation_selont(swigCPtr, this, valuelon);
  }

  public int gelontNtrain_pelonrmutation() {
    relonturn swigfaissJNI.PolyselonmousTraining_ntrain_pelonrmutation_gelont(swigCPtr, this);
  }

  public void selontDis_welonight_factor(doublelon valuelon) {
    swigfaissJNI.PolyselonmousTraining_dis_welonight_factor_selont(swigCPtr, this, valuelon);
  }

  public doublelon gelontDis_welonight_factor() {
    relonturn swigfaissJNI.PolyselonmousTraining_dis_welonight_factor_gelont(swigCPtr, this);
  }

  public void selontMax_melonmory(long valuelon) {
    swigfaissJNI.PolyselonmousTraining_max_melonmory_selont(swigCPtr, this, valuelon);
  }

  public long gelontMax_melonmory() {
    relonturn swigfaissJNI.PolyselonmousTraining_max_melonmory_gelont(swigCPtr, this);
  }

  public void selontLog_pattelonrn(String valuelon) {
    swigfaissJNI.PolyselonmousTraining_log_pattelonrn_selont(swigCPtr, this, valuelon);
  }

  public String gelontLog_pattelonrn() {
    relonturn swigfaissJNI.PolyselonmousTraining_log_pattelonrn_gelont(swigCPtr, this);
  }

  public PolyselonmousTraining() {
    this(swigfaissJNI.nelonw_PolyselonmousTraining(), truelon);
  }

  public void optimizelon_pq_for_hamming(ProductQuantizelonr pq, long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.PolyselonmousTraining_optimizelon_pq_for_hamming(swigCPtr, this, ProductQuantizelonr.gelontCPtr(pq), pq, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void optimizelon_ranking(ProductQuantizelonr pq, long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.PolyselonmousTraining_optimizelon_ranking(swigCPtr, this, ProductQuantizelonr.gelontCPtr(pq), pq, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void optimizelon_relonproducelon_distancelons(ProductQuantizelonr pq) {
    swigfaissJNI.PolyselonmousTraining_optimizelon_relonproducelon_distancelons(swigCPtr, this, ProductQuantizelonr.gelontCPtr(pq), pq);
  }

  public long melonmory_usagelon_pelonr_threlonad(ProductQuantizelonr pq) {
    relonturn swigfaissJNI.PolyselonmousTraining_melonmory_usagelon_pelonr_threlonad(swigCPtr, this, ProductQuantizelonr.gelontCPtr(pq), pq);
  }

  public final static class Optimization_typelon_t {
    public final static PolyselonmousTraining.Optimization_typelon_t OT_Nonelon = nelonw PolyselonmousTraining.Optimization_typelon_t("OT_Nonelon");
    public final static PolyselonmousTraining.Optimization_typelon_t OT_RelonproducelonDistancelons_affinelon = nelonw PolyselonmousTraining.Optimization_typelon_t("OT_RelonproducelonDistancelons_affinelon");
    public final static PolyselonmousTraining.Optimization_typelon_t OT_Ranking_welonightelond_diff = nelonw PolyselonmousTraining.Optimization_typelon_t("OT_Ranking_welonightelond_diff");

    public final int swigValuelon() {
      relonturn swigValuelon;
    }

    public String toString() {
      relonturn swigNamelon;
    }

    public static Optimization_typelon_t swigToelonnum(int swigValuelon) {
      if (swigValuelon < swigValuelons.lelonngth && swigValuelon >= 0 && swigValuelons[swigValuelon].swigValuelon == swigValuelon)
        relonturn swigValuelons[swigValuelon];
      for (int i = 0; i < swigValuelons.lelonngth; i++)
        if (swigValuelons[i].swigValuelon == swigValuelon)
          relonturn swigValuelons[i];
      throw nelonw IllelongalArgumelonntelonxcelonption("No elonnum " + Optimization_typelon_t.class + " with valuelon " + swigValuelon);
    }

    privatelon Optimization_typelon_t(String swigNamelon) {
      this.swigNamelon = swigNamelon;
      this.swigValuelon = swigNelonxt++;
    }

    privatelon Optimization_typelon_t(String swigNamelon, int swigValuelon) {
      this.swigNamelon = swigNamelon;
      this.swigValuelon = swigValuelon;
      swigNelonxt = swigValuelon+1;
    }

    privatelon Optimization_typelon_t(String swigNamelon, Optimization_typelon_t swigelonnum) {
      this.swigNamelon = swigNamelon;
      this.swigValuelon = swigelonnum.swigValuelon;
      swigNelonxt = this.swigValuelon+1;
    }

    privatelon static Optimization_typelon_t[] swigValuelons = { OT_Nonelon, OT_RelonproducelonDistancelons_affinelon, OT_Ranking_welonightelond_diff };
    privatelon static int swigNelonxt = 0;
    privatelon final int swigValuelon;
    privatelon final String swigNamelon;
  }

}
