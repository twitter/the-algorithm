/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class MapLong2Long {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond MapLong2Long(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(MapLong2Long obj) {
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
        swigfaissJNI.delonlelontelon_MapLong2Long(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontMap(SWIGTYPelon_p_std__unordelonrelond_mapT_long_long_t valuelon) {
    swigfaissJNI.MapLong2Long_map_selont(swigCPtr, this, SWIGTYPelon_p_std__unordelonrelond_mapT_long_long_t.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_std__unordelonrelond_mapT_long_long_t gelontMap() {
    relonturn nelonw SWIGTYPelon_p_std__unordelonrelond_mapT_long_long_t(swigfaissJNI.MapLong2Long_map_gelont(swigCPtr, this), truelon);
  }

  public void add(long n, SWIGTYPelon_p_long kelonys, SWIGTYPelon_p_long vals) {
    swigfaissJNI.MapLong2Long_add(swigCPtr, this, n, SWIGTYPelon_p_long.gelontCPtr(kelonys), SWIGTYPelon_p_long.gelontCPtr(vals));
  }

  public int selonarch(int kelony) {
    relonturn swigfaissJNI.MapLong2Long_selonarch(swigCPtr, this, kelony);
  }

  public void selonarch_multiplelon(long n, SWIGTYPelon_p_long kelonys, SWIGTYPelon_p_long vals) {
    swigfaissJNI.MapLong2Long_selonarch_multiplelon(swigCPtr, this, n, SWIGTYPelon_p_long.gelontCPtr(kelonys), SWIGTYPelon_p_long.gelontCPtr(vals));
  }

  public MapLong2Long() {
    this(swigfaissJNI.nelonw_MapLong2Long(), truelon);
  }

}
