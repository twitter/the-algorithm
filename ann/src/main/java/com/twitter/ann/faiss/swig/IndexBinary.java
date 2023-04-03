/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxBinary {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond IndelonxBinary(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxBinary obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxBinary(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontD(int valuelon) {
    swigfaissJNI.IndelonxBinary_d_selont(swigCPtr, this, valuelon);
  }

  public int gelontD() {
    relonturn swigfaissJNI.IndelonxBinary_d_gelont(swigCPtr, this);
  }

  public void selontCodelon_sizelon(int valuelon) {
    swigfaissJNI.IndelonxBinary_codelon_sizelon_selont(swigCPtr, this, valuelon);
  }

  public int gelontCodelon_sizelon() {
    relonturn swigfaissJNI.IndelonxBinary_codelon_sizelon_gelont(swigCPtr, this);
  }

  public void selontNtotal(long valuelon) {
    swigfaissJNI.IndelonxBinary_ntotal_selont(swigCPtr, this, valuelon);
  }

  public long gelontNtotal() {
    relonturn swigfaissJNI.IndelonxBinary_ntotal_gelont(swigCPtr, this);
}

  public void selontVelonrboselon(boolelonan valuelon) {
    swigfaissJNI.IndelonxBinary_velonrboselon_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontVelonrboselon() {
    relonturn swigfaissJNI.IndelonxBinary_velonrboselon_gelont(swigCPtr, this);
  }

  public void selontIs_trainelond(boolelonan valuelon) {
    swigfaissJNI.IndelonxBinary_is_trainelond_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontIs_trainelond() {
    relonturn swigfaissJNI.IndelonxBinary_is_trainelond_gelont(swigCPtr, this);
  }

  public void selontMelontric_typelon(MelontricTypelon valuelon) {
    swigfaissJNI.IndelonxBinary_melontric_typelon_selont(swigCPtr, this, valuelon.swigValuelon());
  }

  public MelontricTypelon gelontMelontric_typelon() {
    relonturn MelontricTypelon.swigToelonnum(swigfaissJNI.IndelonxBinary_melontric_typelon_gelont(swigCPtr, this));
  }

  public void train(long n, SWIGTYPelon_p_unsignelond_char x) {
    swigfaissJNI.IndelonxBinary_train(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x));
  }

  public void add(long n, SWIGTYPelon_p_unsignelond_char x) {
    swigfaissJNI.IndelonxBinary_add(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x));
  }

  public void add_with_ids(long n, SWIGTYPelon_p_unsignelond_char x, LongVelonctor xids) {
    swigfaissJNI.IndelonxBinary_add_with_ids(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x), SWIGTYPelon_p_long_long.gelontCPtr(xids.data()), xids);
  }

  public void selonarch(long n, SWIGTYPelon_p_unsignelond_char x, long k, SWIGTYPelon_p_int distancelons, LongVelonctor labelonls) {
    swigfaissJNI.IndelonxBinary_selonarch(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x), k, SWIGTYPelon_p_int.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls);
  }

  public void rangelon_selonarch(long n, SWIGTYPelon_p_unsignelond_char x, int radius, RangelonSelonarchRelonsult relonsult) {
    swigfaissJNI.IndelonxBinary_rangelon_selonarch(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x), radius, RangelonSelonarchRelonsult.gelontCPtr(relonsult), relonsult);
  }

  public void assign(long n, SWIGTYPelon_p_unsignelond_char x, LongVelonctor labelonls, long k) {
    swigfaissJNI.IndelonxBinary_assign__SWIG_0(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls, k);
  }

  public void assign(long n, SWIGTYPelon_p_unsignelond_char x, LongVelonctor labelonls) {
    swigfaissJNI.IndelonxBinary_assign__SWIG_1(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls);
  }

  public void relonselont() {
    swigfaissJNI.IndelonxBinary_relonselont(swigCPtr, this);
  }

  public long relonmovelon_ids(IDSelonlelonctor selonl) {
    relonturn swigfaissJNI.IndelonxBinary_relonmovelon_ids(swigCPtr, this, IDSelonlelonctor.gelontCPtr(selonl), selonl);
  }

  public void relonconstruct(long kelony, SWIGTYPelon_p_unsignelond_char reloncons) {
    swigfaissJNI.IndelonxBinary_relonconstruct(swigCPtr, this, kelony, SWIGTYPelon_p_unsignelond_char.gelontCPtr(reloncons));
  }

  public void relonconstruct_n(long i0, long ni, SWIGTYPelon_p_unsignelond_char reloncons) {
    swigfaissJNI.IndelonxBinary_relonconstruct_n(swigCPtr, this, i0, ni, SWIGTYPelon_p_unsignelond_char.gelontCPtr(reloncons));
  }

  public void selonarch_and_relonconstruct(long n, SWIGTYPelon_p_unsignelond_char x, long k, SWIGTYPelon_p_int distancelons, LongVelonctor labelonls, SWIGTYPelon_p_unsignelond_char reloncons) {
    swigfaissJNI.IndelonxBinary_selonarch_and_relonconstruct(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x), k, SWIGTYPelon_p_int.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls, SWIGTYPelon_p_unsignelond_char.gelontCPtr(reloncons));
  }

  public void display() {
    swigfaissJNI.IndelonxBinary_display(swigCPtr, this);
  }

}
