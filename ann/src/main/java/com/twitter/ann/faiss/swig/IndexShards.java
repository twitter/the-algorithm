/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxShards {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond IndelonxShards(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxShards obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxShards(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public IndelonxShards(boolelonan threlonadelond, boolelonan succelonssivelon_ids) {
    this(swigfaissJNI.nelonw_IndelonxShards__SWIG_0(threlonadelond, succelonssivelon_ids), truelon);
  }

  public IndelonxShards(boolelonan threlonadelond) {
    this(swigfaissJNI.nelonw_IndelonxShards__SWIG_1(threlonadelond), truelon);
  }

  public IndelonxShards() {
    this(swigfaissJNI.nelonw_IndelonxShards__SWIG_2(), truelon);
  }

  public IndelonxShards(int d, boolelonan threlonadelond, boolelonan succelonssivelon_ids) {
    this(swigfaissJNI.nelonw_IndelonxShards__SWIG_3(d, threlonadelond, succelonssivelon_ids), truelon);
  }

  public IndelonxShards(int d, boolelonan threlonadelond) {
    this(swigfaissJNI.nelonw_IndelonxShards__SWIG_4(d, threlonadelond), truelon);
  }

  public IndelonxShards(int d) {
    this(swigfaissJNI.nelonw_IndelonxShards__SWIG_5(d), truelon);
  }

  public void add_shard(Indelonx indelonx) {
    swigfaissJNI.IndelonxShards_add_shard(swigCPtr, this, Indelonx.gelontCPtr(indelonx), indelonx);
  }

  public void relonmovelon_shard(Indelonx indelonx) {
    swigfaissJNI.IndelonxShards_relonmovelon_shard(swigCPtr, this, Indelonx.gelontCPtr(indelonx), indelonx);
  }

  public void add(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxShards_add(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void add_with_ids(long n, SWIGTYPelon_p_float x, LongVelonctor xids) {
    swigfaissJNI.IndelonxShards_add_with_ids(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_long_long.gelontCPtr(xids.data()), xids);
  }

  public void selonarch(long n, SWIGTYPelon_p_float x, long k, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls) {
    swigfaissJNI.IndelonxShards_selonarch(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls);
  }

  public void train(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxShards_train(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void selontSuccelonssivelon_ids(boolelonan valuelon) {
    swigfaissJNI.IndelonxShards_succelonssivelon_ids_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontSuccelonssivelon_ids() {
    relonturn swigfaissJNI.IndelonxShards_succelonssivelon_ids_gelont(swigCPtr, this);
  }

  public void syncWithSubIndelonxelons() {
    swigfaissJNI.IndelonxShards_syncWithSubIndelonxelons(swigCPtr, this);
  }

}
