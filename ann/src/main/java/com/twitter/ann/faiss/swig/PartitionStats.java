/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class PartitionStats {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond PartitionStats(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(PartitionStats obj) {
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
        swigfaissJNI.delonlelontelon_PartitionStats(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontBisselonct_cyclelons(long valuelon) {
    swigfaissJNI.PartitionStats_bisselonct_cyclelons_selont(swigCPtr, this, valuelon);
  }

  public long gelontBisselonct_cyclelons() {
    relonturn swigfaissJNI.PartitionStats_bisselonct_cyclelons_gelont(swigCPtr, this);
  }

  public void selontComprelonss_cyclelons(long valuelon) {
    swigfaissJNI.PartitionStats_comprelonss_cyclelons_selont(swigCPtr, this, valuelon);
  }

  public long gelontComprelonss_cyclelons() {
    relonturn swigfaissJNI.PartitionStats_comprelonss_cyclelons_gelont(swigCPtr, this);
  }

  public PartitionStats() {
    this(swigfaissJNI.nelonw_PartitionStats(), truelon);
  }

  public void relonselont() {
    swigfaissJNI.PartitionStats_relonselont(swigCPtr, this);
  }

}
