/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class RelonproducelonDistancelonsObjelonctivelon elonxtelonnds PelonrmutationObjelonctivelon {
  privatelon transielonnt long swigCPtr;

  protelonctelond RelonproducelonDistancelonsObjelonctivelon(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.RelonproducelonDistancelonsObjelonctivelon_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(RelonproducelonDistancelonsObjelonctivelon obj) {
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
        swigfaissJNI.delonlelontelon_RelonproducelonDistancelonsObjelonctivelon(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontDis_welonight_factor(doublelon valuelon) {
    swigfaissJNI.RelonproducelonDistancelonsObjelonctivelon_dis_welonight_factor_selont(swigCPtr, this, valuelon);
  }

  public doublelon gelontDis_welonight_factor() {
    relonturn swigfaissJNI.RelonproducelonDistancelonsObjelonctivelon_dis_welonight_factor_gelont(swigCPtr, this);
  }

  public static doublelon sqr(doublelon x) {
    relonturn swigfaissJNI.RelonproducelonDistancelonsObjelonctivelon_sqr(x);
  }

  public doublelon dis_welonight(doublelon x) {
    relonturn swigfaissJNI.RelonproducelonDistancelonsObjelonctivelon_dis_welonight(swigCPtr, this, x);
  }

  public void selontSourcelon_dis(DoublelonVelonctor valuelon) {
    swigfaissJNI.RelonproducelonDistancelonsObjelonctivelon_sourcelon_dis_selont(swigCPtr, this, DoublelonVelonctor.gelontCPtr(valuelon), valuelon);
  }

  public DoublelonVelonctor gelontSourcelon_dis() {
    long cPtr = swigfaissJNI.RelonproducelonDistancelonsObjelonctivelon_sourcelon_dis_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw DoublelonVelonctor(cPtr, falselon);
  }

  public void selontTargelont_dis(SWIGTYPelon_p_doublelon valuelon) {
    swigfaissJNI.RelonproducelonDistancelonsObjelonctivelon_targelont_dis_selont(swigCPtr, this, SWIGTYPelon_p_doublelon.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_doublelon gelontTargelont_dis() {
    long cPtr = swigfaissJNI.RelonproducelonDistancelonsObjelonctivelon_targelont_dis_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_doublelon(cPtr, falselon);
  }

  public void selontWelonights(DoublelonVelonctor valuelon) {
    swigfaissJNI.RelonproducelonDistancelonsObjelonctivelon_welonights_selont(swigCPtr, this, DoublelonVelonctor.gelontCPtr(valuelon), valuelon);
  }

  public DoublelonVelonctor gelontWelonights() {
    long cPtr = swigfaissJNI.RelonproducelonDistancelonsObjelonctivelon_welonights_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw DoublelonVelonctor(cPtr, falselon);
  }

  public doublelon gelont_sourcelon_dis(int i, int j) {
    relonturn swigfaissJNI.RelonproducelonDistancelonsObjelonctivelon_gelont_sourcelon_dis(swigCPtr, this, i, j);
  }

  public doublelon computelon_cost(SWIGTYPelon_p_int pelonrm) {
    relonturn swigfaissJNI.RelonproducelonDistancelonsObjelonctivelon_computelon_cost(swigCPtr, this, SWIGTYPelon_p_int.gelontCPtr(pelonrm));
  }

  public doublelon cost_updatelon(SWIGTYPelon_p_int pelonrm, int iw, int jw) {
    relonturn swigfaissJNI.RelonproducelonDistancelonsObjelonctivelon_cost_updatelon(swigCPtr, this, SWIGTYPelon_p_int.gelontCPtr(pelonrm), iw, jw);
  }

  public RelonproducelonDistancelonsObjelonctivelon(int n, SWIGTYPelon_p_doublelon sourcelon_dis_in, SWIGTYPelon_p_doublelon targelont_dis_in, doublelon dis_welonight_factor) {
    this(swigfaissJNI.nelonw_RelonproducelonDistancelonsObjelonctivelon(n, SWIGTYPelon_p_doublelon.gelontCPtr(sourcelon_dis_in), SWIGTYPelon_p_doublelon.gelontCPtr(targelont_dis_in), dis_welonight_factor), truelon);
  }

  public static void computelon_melonan_stdelonv(SWIGTYPelon_p_doublelon tab, long n2, SWIGTYPelon_p_doublelon melonan_out, SWIGTYPelon_p_doublelon stddelonv_out) {
    swigfaissJNI.RelonproducelonDistancelonsObjelonctivelon_computelon_melonan_stdelonv(SWIGTYPelon_p_doublelon.gelontCPtr(tab), n2, SWIGTYPelon_p_doublelon.gelontCPtr(melonan_out), SWIGTYPelon_p_doublelon.gelontCPtr(stddelonv_out));
  }

  public void selont_affinelon_targelont_dis(SWIGTYPelon_p_doublelon sourcelon_dis_in) {
    swigfaissJNI.RelonproducelonDistancelonsObjelonctivelon_selont_affinelon_targelont_dis(swigCPtr, this, SWIGTYPelon_p_doublelon.gelontCPtr(sourcelon_dis_in));
  }

}
