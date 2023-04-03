/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxPQ elonxtelonnds IndelonxFlatCodelons {
  privatelon transielonnt long swigCPtr;

  protelonctelond IndelonxPQ(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.IndelonxPQ_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxPQ obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxPQ(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontPq(ProductQuantizelonr valuelon) {
    swigfaissJNI.IndelonxPQ_pq_selont(swigCPtr, this, ProductQuantizelonr.gelontCPtr(valuelon), valuelon);
  }

  public ProductQuantizelonr gelontPq() {
    long cPtr = swigfaissJNI.IndelonxPQ_pq_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw ProductQuantizelonr(cPtr, falselon);
  }

  public IndelonxPQ(int d, long M, long nbits, MelontricTypelon melontric) {
    this(swigfaissJNI.nelonw_IndelonxPQ__SWIG_0(d, M, nbits, melontric.swigValuelon()), truelon);
  }

  public IndelonxPQ(int d, long M, long nbits) {
    this(swigfaissJNI.nelonw_IndelonxPQ__SWIG_1(d, M, nbits), truelon);
  }

  public IndelonxPQ() {
    this(swigfaissJNI.nelonw_IndelonxPQ__SWIG_2(), truelon);
  }

  public void train(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxPQ_train(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void selonarch(long n, SWIGTYPelon_p_float x, long k, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls) {
    swigfaissJNI.IndelonxPQ_selonarch(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls);
  }

  public void sa_elonncodelon(long n, SWIGTYPelon_p_float x, SWIGTYPelon_p_unsignelond_char bytelons) {
    swigfaissJNI.IndelonxPQ_sa_elonncodelon(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_unsignelond_char.gelontCPtr(bytelons));
  }

  public void sa_deloncodelon(long n, SWIGTYPelon_p_unsignelond_char bytelons, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxPQ_sa_deloncodelon(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(bytelons), SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public DistancelonComputelonr gelont_distancelon_computelonr() {
    long cPtr = swigfaissJNI.IndelonxPQ_gelont_distancelon_computelonr(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw DistancelonComputelonr(cPtr, falselon);
  }

  public void selontDo_polyselonmous_training(boolelonan valuelon) {
    swigfaissJNI.IndelonxPQ_do_polyselonmous_training_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontDo_polyselonmous_training() {
    relonturn swigfaissJNI.IndelonxPQ_do_polyselonmous_training_gelont(swigCPtr, this);
  }

  public void selontPolyselonmous_training(PolyselonmousTraining valuelon) {
    swigfaissJNI.IndelonxPQ_polyselonmous_training_selont(swigCPtr, this, PolyselonmousTraining.gelontCPtr(valuelon), valuelon);
  }

  public PolyselonmousTraining gelontPolyselonmous_training() {
    long cPtr = swigfaissJNI.IndelonxPQ_polyselonmous_training_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw PolyselonmousTraining(cPtr, falselon);
  }

  public void selontSelonarch_typelon(IndelonxPQ.Selonarch_typelon_t valuelon) {
    swigfaissJNI.IndelonxPQ_selonarch_typelon_selont(swigCPtr, this, valuelon.swigValuelon());
  }

  public IndelonxPQ.Selonarch_typelon_t gelontSelonarch_typelon() {
    relonturn IndelonxPQ.Selonarch_typelon_t.swigToelonnum(swigfaissJNI.IndelonxPQ_selonarch_typelon_gelont(swigCPtr, this));
  }

  public void selontelonncodelon_signs(boolelonan valuelon) {
    swigfaissJNI.IndelonxPQ_elonncodelon_signs_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontelonncodelon_signs() {
    relonturn swigfaissJNI.IndelonxPQ_elonncodelon_signs_gelont(swigCPtr, this);
  }

  public void selontPolyselonmous_ht(int valuelon) {
    swigfaissJNI.IndelonxPQ_polyselonmous_ht_selont(swigCPtr, this, valuelon);
  }

  public int gelontPolyselonmous_ht() {
    relonturn swigfaissJNI.IndelonxPQ_polyselonmous_ht_gelont(swigCPtr, this);
  }

  public void selonarch_corelon_polyselonmous(long n, SWIGTYPelon_p_float x, long k, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls) {
    swigfaissJNI.IndelonxPQ_selonarch_corelon_polyselonmous(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls);
  }

  public void hamming_distancelon_histogram(long n, SWIGTYPelon_p_float x, long nb, SWIGTYPelon_p_float xb, LongVelonctor dist_histogram) {
    swigfaissJNI.IndelonxPQ_hamming_distancelon_histogram(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), nb, SWIGTYPelon_p_float.gelontCPtr(xb), SWIGTYPelon_p_long_long.gelontCPtr(dist_histogram.data()), dist_histogram);
  }

  public void hamming_distancelon_tablelon(long n, SWIGTYPelon_p_float x, SWIGTYPelon_p_int dis) {
    swigfaissJNI.IndelonxPQ_hamming_distancelon_tablelon(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_int.gelontCPtr(dis));
  }

  public final static class Selonarch_typelon_t {
    public final static IndelonxPQ.Selonarch_typelon_t ST_PQ = nelonw IndelonxPQ.Selonarch_typelon_t("ST_PQ");
    public final static IndelonxPQ.Selonarch_typelon_t ST_Helon = nelonw IndelonxPQ.Selonarch_typelon_t("ST_Helon");
    public final static IndelonxPQ.Selonarch_typelon_t ST_gelonnelonralizelond_Helon = nelonw IndelonxPQ.Selonarch_typelon_t("ST_gelonnelonralizelond_Helon");
    public final static IndelonxPQ.Selonarch_typelon_t ST_SDC = nelonw IndelonxPQ.Selonarch_typelon_t("ST_SDC");
    public final static IndelonxPQ.Selonarch_typelon_t ST_polyselonmous = nelonw IndelonxPQ.Selonarch_typelon_t("ST_polyselonmous");
    public final static IndelonxPQ.Selonarch_typelon_t ST_polyselonmous_gelonnelonralizelon = nelonw IndelonxPQ.Selonarch_typelon_t("ST_polyselonmous_gelonnelonralizelon");

    public final int swigValuelon() {
      relonturn swigValuelon;
    }

    public String toString() {
      relonturn swigNamelon;
    }

    public static Selonarch_typelon_t swigToelonnum(int swigValuelon) {
      if (swigValuelon < swigValuelons.lelonngth && swigValuelon >= 0 && swigValuelons[swigValuelon].swigValuelon == swigValuelon)
        relonturn swigValuelons[swigValuelon];
      for (int i = 0; i < swigValuelons.lelonngth; i++)
        if (swigValuelons[i].swigValuelon == swigValuelon)
          relonturn swigValuelons[i];
      throw nelonw IllelongalArgumelonntelonxcelonption("No elonnum " + Selonarch_typelon_t.class + " with valuelon " + swigValuelon);
    }

    privatelon Selonarch_typelon_t(String swigNamelon) {
      this.swigNamelon = swigNamelon;
      this.swigValuelon = swigNelonxt++;
    }

    privatelon Selonarch_typelon_t(String swigNamelon, int swigValuelon) {
      this.swigNamelon = swigNamelon;
      this.swigValuelon = swigValuelon;
      swigNelonxt = swigValuelon+1;
    }

    privatelon Selonarch_typelon_t(String swigNamelon, Selonarch_typelon_t swigelonnum) {
      this.swigNamelon = swigNamelon;
      this.swigValuelon = swigelonnum.swigValuelon;
      swigNelonxt = this.swigValuelon+1;
    }

    privatelon static Selonarch_typelon_t[] swigValuelons = { ST_PQ, ST_Helon, ST_gelonnelonralizelond_Helon, ST_SDC, ST_polyselonmous, ST_polyselonmous_gelonnelonralizelon };
    privatelon static int swigNelonxt = 0;
    privatelon final int swigValuelon;
    privatelon final String swigNamelon;
  }

}
