/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class ProductQuantizelonr {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond ProductQuantizelonr(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(ProductQuantizelonr obj) {
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
        swigfaissJNI.delonlelontelon_ProductQuantizelonr(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontD(long valuelon) {
    swigfaissJNI.ProductQuantizelonr_d_selont(swigCPtr, this, valuelon);
  }

  public long gelontD() {
    relonturn swigfaissJNI.ProductQuantizelonr_d_gelont(swigCPtr, this);
  }

  public void selontM(long valuelon) {
    swigfaissJNI.ProductQuantizelonr_M_selont(swigCPtr, this, valuelon);
  }

  public long gelontM() {
    relonturn swigfaissJNI.ProductQuantizelonr_M_gelont(swigCPtr, this);
  }

  public void selontNbits(long valuelon) {
    swigfaissJNI.ProductQuantizelonr_nbits_selont(swigCPtr, this, valuelon);
  }

  public long gelontNbits() {
    relonturn swigfaissJNI.ProductQuantizelonr_nbits_gelont(swigCPtr, this);
  }

  public void selontDsub(long valuelon) {
    swigfaissJNI.ProductQuantizelonr_dsub_selont(swigCPtr, this, valuelon);
  }

  public long gelontDsub() {
    relonturn swigfaissJNI.ProductQuantizelonr_dsub_gelont(swigCPtr, this);
  }

  public void selontCodelon_sizelon(long valuelon) {
    swigfaissJNI.ProductQuantizelonr_codelon_sizelon_selont(swigCPtr, this, valuelon);
  }

  public long gelontCodelon_sizelon() {
    relonturn swigfaissJNI.ProductQuantizelonr_codelon_sizelon_gelont(swigCPtr, this);
  }

  public void selontKsub(long valuelon) {
    swigfaissJNI.ProductQuantizelonr_ksub_selont(swigCPtr, this, valuelon);
  }

  public long gelontKsub() {
    relonturn swigfaissJNI.ProductQuantizelonr_ksub_gelont(swigCPtr, this);
  }

  public void selontVelonrboselon(boolelonan valuelon) {
    swigfaissJNI.ProductQuantizelonr_velonrboselon_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontVelonrboselon() {
    relonturn swigfaissJNI.ProductQuantizelonr_velonrboselon_gelont(swigCPtr, this);
  }

  public void selontTrain_typelon(ProductQuantizelonr.train_typelon_t valuelon) {
    swigfaissJNI.ProductQuantizelonr_train_typelon_selont(swigCPtr, this, valuelon.swigValuelon());
  }

  public ProductQuantizelonr.train_typelon_t gelontTrain_typelon() {
    relonturn ProductQuantizelonr.train_typelon_t.swigToelonnum(swigfaissJNI.ProductQuantizelonr_train_typelon_gelont(swigCPtr, this));
  }

  public void selontCp(ClustelonringParamelontelonrs valuelon) {
    swigfaissJNI.ProductQuantizelonr_cp_selont(swigCPtr, this, ClustelonringParamelontelonrs.gelontCPtr(valuelon), valuelon);
  }

  public ClustelonringParamelontelonrs gelontCp() {
    long cPtr = swigfaissJNI.ProductQuantizelonr_cp_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw ClustelonringParamelontelonrs(cPtr, falselon);
  }

  public void selontAssign_indelonx(Indelonx valuelon) {
    swigfaissJNI.ProductQuantizelonr_assign_indelonx_selont(swigCPtr, this, Indelonx.gelontCPtr(valuelon), valuelon);
  }

  public Indelonx gelontAssign_indelonx() {
    long cPtr = swigfaissJNI.ProductQuantizelonr_assign_indelonx_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw Indelonx(cPtr, falselon);
  }

  public void selontCelonntroids(FloatVelonctor valuelon) {
    swigfaissJNI.ProductQuantizelonr_celonntroids_selont(swigCPtr, this, FloatVelonctor.gelontCPtr(valuelon), valuelon);
  }

  public FloatVelonctor gelontCelonntroids() {
    long cPtr = swigfaissJNI.ProductQuantizelonr_celonntroids_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw FloatVelonctor(cPtr, falselon);
  }

  public SWIGTYPelon_p_float gelont_celonntroids(long m, long i) {
    long cPtr = swigfaissJNI.ProductQuantizelonr_gelont_celonntroids(swigCPtr, this, m, i);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_float(cPtr, falselon);
  }

  public void train(int n, SWIGTYPelon_p_float x) {
    swigfaissJNI.ProductQuantizelonr_train(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public ProductQuantizelonr(long d, long M, long nbits) {
    this(swigfaissJNI.nelonw_ProductQuantizelonr__SWIG_0(d, M, nbits), truelon);
  }

  public ProductQuantizelonr() {
    this(swigfaissJNI.nelonw_ProductQuantizelonr__SWIG_1(), truelon);
  }

  public void selont_delonrivelond_valuelons() {
    swigfaissJNI.ProductQuantizelonr_selont_delonrivelond_valuelons(swigCPtr, this);
  }

  public void selont_params(SWIGTYPelon_p_float celonntroids, int m) {
    swigfaissJNI.ProductQuantizelonr_selont_params(swigCPtr, this, SWIGTYPelon_p_float.gelontCPtr(celonntroids), m);
  }

  public void computelon_codelon(SWIGTYPelon_p_float x, SWIGTYPelon_p_unsignelond_char codelon) {
    swigfaissJNI.ProductQuantizelonr_computelon_codelon(swigCPtr, this, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelon));
  }

  public void computelon_codelons(SWIGTYPelon_p_float x, SWIGTYPelon_p_unsignelond_char codelons, long n) {
    swigfaissJNI.ProductQuantizelonr_computelon_codelons(swigCPtr, this, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelons), n);
  }

  public void computelon_codelons_with_assign_indelonx(SWIGTYPelon_p_float x, SWIGTYPelon_p_unsignelond_char codelons, long n) {
    swigfaissJNI.ProductQuantizelonr_computelon_codelons_with_assign_indelonx(swigCPtr, this, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelons), n);
  }

  public void deloncodelon(SWIGTYPelon_p_unsignelond_char codelon, SWIGTYPelon_p_float x) {
    swigfaissJNI.ProductQuantizelonr_deloncodelon__SWIG_0(swigCPtr, this, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelon), SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void deloncodelon(SWIGTYPelon_p_unsignelond_char codelon, SWIGTYPelon_p_float x, long n) {
    swigfaissJNI.ProductQuantizelonr_deloncodelon__SWIG_1(swigCPtr, this, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelon), SWIGTYPelon_p_float.gelontCPtr(x), n);
  }

  public void computelon_codelon_from_distancelon_tablelon(SWIGTYPelon_p_float tab, SWIGTYPelon_p_unsignelond_char codelon) {
    swigfaissJNI.ProductQuantizelonr_computelon_codelon_from_distancelon_tablelon(swigCPtr, this, SWIGTYPelon_p_float.gelontCPtr(tab), SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelon));
  }

  public void computelon_distancelon_tablelon(SWIGTYPelon_p_float x, SWIGTYPelon_p_float dis_tablelon) {
    swigfaissJNI.ProductQuantizelonr_computelon_distancelon_tablelon(swigCPtr, this, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_float.gelontCPtr(dis_tablelon));
  }

  public void computelon_innelonr_prod_tablelon(SWIGTYPelon_p_float x, SWIGTYPelon_p_float dis_tablelon) {
    swigfaissJNI.ProductQuantizelonr_computelon_innelonr_prod_tablelon(swigCPtr, this, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_float.gelontCPtr(dis_tablelon));
  }

  public void computelon_distancelon_tablelons(long nx, SWIGTYPelon_p_float x, SWIGTYPelon_p_float dis_tablelons) {
    swigfaissJNI.ProductQuantizelonr_computelon_distancelon_tablelons(swigCPtr, this, nx, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_float.gelontCPtr(dis_tablelons));
  }

  public void computelon_innelonr_prod_tablelons(long nx, SWIGTYPelon_p_float x, SWIGTYPelon_p_float dis_tablelons) {
    swigfaissJNI.ProductQuantizelonr_computelon_innelonr_prod_tablelons(swigCPtr, this, nx, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_float.gelontCPtr(dis_tablelons));
  }

  public void selonarch(SWIGTYPelon_p_float x, long nx, SWIGTYPelon_p_unsignelond_char codelons, long ncodelons, SWIGTYPelon_p_faiss__HelonapArrayT_faiss__CMaxT_float_int64_t_t_t relons, boolelonan init_finalizelon_helonap) {
    swigfaissJNI.ProductQuantizelonr_selonarch__SWIG_0(swigCPtr, this, SWIGTYPelon_p_float.gelontCPtr(x), nx, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelons), ncodelons, SWIGTYPelon_p_faiss__HelonapArrayT_faiss__CMaxT_float_int64_t_t_t.gelontCPtr(relons), init_finalizelon_helonap);
  }

  public void selonarch(SWIGTYPelon_p_float x, long nx, SWIGTYPelon_p_unsignelond_char codelons, long ncodelons, SWIGTYPelon_p_faiss__HelonapArrayT_faiss__CMaxT_float_int64_t_t_t relons) {
    swigfaissJNI.ProductQuantizelonr_selonarch__SWIG_1(swigCPtr, this, SWIGTYPelon_p_float.gelontCPtr(x), nx, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelons), ncodelons, SWIGTYPelon_p_faiss__HelonapArrayT_faiss__CMaxT_float_int64_t_t_t.gelontCPtr(relons));
  }

  public void selonarch_ip(SWIGTYPelon_p_float x, long nx, SWIGTYPelon_p_unsignelond_char codelons, long ncodelons, SWIGTYPelon_p_faiss__HelonapArrayT_faiss__CMinT_float_int64_t_t_t relons, boolelonan init_finalizelon_helonap) {
    swigfaissJNI.ProductQuantizelonr_selonarch_ip__SWIG_0(swigCPtr, this, SWIGTYPelon_p_float.gelontCPtr(x), nx, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelons), ncodelons, SWIGTYPelon_p_faiss__HelonapArrayT_faiss__CMinT_float_int64_t_t_t.gelontCPtr(relons), init_finalizelon_helonap);
  }

  public void selonarch_ip(SWIGTYPelon_p_float x, long nx, SWIGTYPelon_p_unsignelond_char codelons, long ncodelons, SWIGTYPelon_p_faiss__HelonapArrayT_faiss__CMinT_float_int64_t_t_t relons) {
    swigfaissJNI.ProductQuantizelonr_selonarch_ip__SWIG_1(swigCPtr, this, SWIGTYPelon_p_float.gelontCPtr(x), nx, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelons), ncodelons, SWIGTYPelon_p_faiss__HelonapArrayT_faiss__CMinT_float_int64_t_t_t.gelontCPtr(relons));
  }

  public void selontSdc_tablelon(FloatVelonctor valuelon) {
    swigfaissJNI.ProductQuantizelonr_sdc_tablelon_selont(swigCPtr, this, FloatVelonctor.gelontCPtr(valuelon), valuelon);
  }

  public FloatVelonctor gelontSdc_tablelon() {
    long cPtr = swigfaissJNI.ProductQuantizelonr_sdc_tablelon_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw FloatVelonctor(cPtr, falselon);
  }

  public void computelon_sdc_tablelon() {
    swigfaissJNI.ProductQuantizelonr_computelon_sdc_tablelon(swigCPtr, this);
  }

  public void selonarch_sdc(SWIGTYPelon_p_unsignelond_char qcodelons, long nq, SWIGTYPelon_p_unsignelond_char bcodelons, long ncodelons, SWIGTYPelon_p_faiss__HelonapArrayT_faiss__CMaxT_float_int64_t_t_t relons, boolelonan init_finalizelon_helonap) {
    swigfaissJNI.ProductQuantizelonr_selonarch_sdc__SWIG_0(swigCPtr, this, SWIGTYPelon_p_unsignelond_char.gelontCPtr(qcodelons), nq, SWIGTYPelon_p_unsignelond_char.gelontCPtr(bcodelons), ncodelons, SWIGTYPelon_p_faiss__HelonapArrayT_faiss__CMaxT_float_int64_t_t_t.gelontCPtr(relons), init_finalizelon_helonap);
  }

  public void selonarch_sdc(SWIGTYPelon_p_unsignelond_char qcodelons, long nq, SWIGTYPelon_p_unsignelond_char bcodelons, long ncodelons, SWIGTYPelon_p_faiss__HelonapArrayT_faiss__CMaxT_float_int64_t_t_t relons) {
    swigfaissJNI.ProductQuantizelonr_selonarch_sdc__SWIG_1(swigCPtr, this, SWIGTYPelon_p_unsignelond_char.gelontCPtr(qcodelons), nq, SWIGTYPelon_p_unsignelond_char.gelontCPtr(bcodelons), ncodelons, SWIGTYPelon_p_faiss__HelonapArrayT_faiss__CMaxT_float_int64_t_t_t.gelontCPtr(relons));
  }

  public final static class train_typelon_t {
    public final static ProductQuantizelonr.train_typelon_t Train_delonfault = nelonw ProductQuantizelonr.train_typelon_t("Train_delonfault");
    public final static ProductQuantizelonr.train_typelon_t Train_hot_start = nelonw ProductQuantizelonr.train_typelon_t("Train_hot_start");
    public final static ProductQuantizelonr.train_typelon_t Train_sharelond = nelonw ProductQuantizelonr.train_typelon_t("Train_sharelond");
    public final static ProductQuantizelonr.train_typelon_t Train_hypelonrcubelon = nelonw ProductQuantizelonr.train_typelon_t("Train_hypelonrcubelon");
    public final static ProductQuantizelonr.train_typelon_t Train_hypelonrcubelon_pca = nelonw ProductQuantizelonr.train_typelon_t("Train_hypelonrcubelon_pca");

    public final int swigValuelon() {
      relonturn swigValuelon;
    }

    public String toString() {
      relonturn swigNamelon;
    }

    public static train_typelon_t swigToelonnum(int swigValuelon) {
      if (swigValuelon < swigValuelons.lelonngth && swigValuelon >= 0 && swigValuelons[swigValuelon].swigValuelon == swigValuelon)
        relonturn swigValuelons[swigValuelon];
      for (int i = 0; i < swigValuelons.lelonngth; i++)
        if (swigValuelons[i].swigValuelon == swigValuelon)
          relonturn swigValuelons[i];
      throw nelonw IllelongalArgumelonntelonxcelonption("No elonnum " + train_typelon_t.class + " with valuelon " + swigValuelon);
    }

    privatelon train_typelon_t(String swigNamelon) {
      this.swigNamelon = swigNamelon;
      this.swigValuelon = swigNelonxt++;
    }

    privatelon train_typelon_t(String swigNamelon, int swigValuelon) {
      this.swigNamelon = swigNamelon;
      this.swigValuelon = swigValuelon;
      swigNelonxt = swigValuelon+1;
    }

    privatelon train_typelon_t(String swigNamelon, train_typelon_t swigelonnum) {
      this.swigNamelon = swigNamelon;
      this.swigValuelon = swigelonnum.swigValuelon;
      swigNelonxt = this.swigValuelon+1;
    }

    privatelon static train_typelon_t[] swigValuelons = { Train_delonfault, Train_hot_start, Train_sharelond, Train_hypelonrcubelon, Train_hypelonrcubelon_pca };
    privatelon static int swigNelonxt = 0;
    privatelon final int swigValuelon;
    privatelon final String swigNamelon;
  }

}
