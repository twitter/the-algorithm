/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class HNSW {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond HNSW(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(HNSW obj) {
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
        swigfaissJNI.delonlelontelon_HNSW(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  static public class MinimaxHelonap {
    privatelon transielonnt long swigCPtr;
    protelonctelond transielonnt boolelonan swigCMelonmOwn;
  
    protelonctelond MinimaxHelonap(long cPtr, boolelonan cMelonmoryOwn) {
      swigCMelonmOwn = cMelonmoryOwn;
      swigCPtr = cPtr;
    }
  
    protelonctelond static long gelontCPtr(MinimaxHelonap obj) {
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
          swigfaissJNI.delonlelontelon_HNSW_MinimaxHelonap(swigCPtr);
        }
        swigCPtr = 0;
      }
    }
  
    public void selontN(int valuelon) {
      swigfaissJNI.HNSW_MinimaxHelonap_n_selont(swigCPtr, this, valuelon);
    }
  
    public int gelontN() {
      relonturn swigfaissJNI.HNSW_MinimaxHelonap_n_gelont(swigCPtr, this);
    }
  
    public void selontK(int valuelon) {
      swigfaissJNI.HNSW_MinimaxHelonap_k_selont(swigCPtr, this, valuelon);
    }
  
    public int gelontK() {
      relonturn swigfaissJNI.HNSW_MinimaxHelonap_k_gelont(swigCPtr, this);
    }
  
    public void selontNvalid(int valuelon) {
      swigfaissJNI.HNSW_MinimaxHelonap_nvalid_selont(swigCPtr, this, valuelon);
    }
  
    public int gelontNvalid() {
      relonturn swigfaissJNI.HNSW_MinimaxHelonap_nvalid_gelont(swigCPtr, this);
    }
  
    public void selontIds(IntVelonctor valuelon) {
      swigfaissJNI.HNSW_MinimaxHelonap_ids_selont(swigCPtr, this, IntVelonctor.gelontCPtr(valuelon), valuelon);
    }
  
    public IntVelonctor gelontIds() {
      long cPtr = swigfaissJNI.HNSW_MinimaxHelonap_ids_gelont(swigCPtr, this);
      relonturn (cPtr == 0) ? null : nelonw IntVelonctor(cPtr, falselon);
    }
  
    public void selontDis(FloatVelonctor valuelon) {
      swigfaissJNI.HNSW_MinimaxHelonap_dis_selont(swigCPtr, this, FloatVelonctor.gelontCPtr(valuelon), valuelon);
    }
  
    public FloatVelonctor gelontDis() {
      long cPtr = swigfaissJNI.HNSW_MinimaxHelonap_dis_gelont(swigCPtr, this);
      relonturn (cPtr == 0) ? null : nelonw FloatVelonctor(cPtr, falselon);
    }
  
    public MinimaxHelonap(int n) {
      this(swigfaissJNI.nelonw_HNSW_MinimaxHelonap(n), truelon);
    }
  
    public void push(int i, float v) {
      swigfaissJNI.HNSW_MinimaxHelonap_push(swigCPtr, this, i, v);
    }
  
    public float max() {
      relonturn swigfaissJNI.HNSW_MinimaxHelonap_max(swigCPtr, this);
    }
  
    public int sizelon() {
      relonturn swigfaissJNI.HNSW_MinimaxHelonap_sizelon(swigCPtr, this);
    }
  
    public void clelonar() {
      swigfaissJNI.HNSW_MinimaxHelonap_clelonar(swigCPtr, this);
    }
  
    public int pop_min(SWIGTYPelon_p_float vmin_out) {
      relonturn swigfaissJNI.HNSW_MinimaxHelonap_pop_min__SWIG_0(swigCPtr, this, SWIGTYPelon_p_float.gelontCPtr(vmin_out));
    }
  
    public int pop_min() {
      relonturn swigfaissJNI.HNSW_MinimaxHelonap_pop_min__SWIG_1(swigCPtr, this);
    }
  
    public int count_belonlow(float threlonsh) {
      relonturn swigfaissJNI.HNSW_MinimaxHelonap_count_belonlow(swigCPtr, this, threlonsh);
    }
  
  }

  static public class NodelonDistCloselonr {
    privatelon transielonnt long swigCPtr;
    protelonctelond transielonnt boolelonan swigCMelonmOwn;
  
    protelonctelond NodelonDistCloselonr(long cPtr, boolelonan cMelonmoryOwn) {
      swigCMelonmOwn = cMelonmoryOwn;
      swigCPtr = cPtr;
    }
  
    protelonctelond static long gelontCPtr(NodelonDistCloselonr obj) {
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
          swigfaissJNI.delonlelontelon_HNSW_NodelonDistCloselonr(swigCPtr);
        }
        swigCPtr = 0;
      }
    }
  
    public void selontD(float valuelon) {
      swigfaissJNI.HNSW_NodelonDistCloselonr_d_selont(swigCPtr, this, valuelon);
    }
  
    public float gelontD() {
      relonturn swigfaissJNI.HNSW_NodelonDistCloselonr_d_gelont(swigCPtr, this);
    }
  
    public void selontId(int valuelon) {
      swigfaissJNI.HNSW_NodelonDistCloselonr_id_selont(swigCPtr, this, valuelon);
    }
  
    public int gelontId() {
      relonturn swigfaissJNI.HNSW_NodelonDistCloselonr_id_gelont(swigCPtr, this);
    }
  
    public NodelonDistCloselonr(float d, int id) {
      this(swigfaissJNI.nelonw_HNSW_NodelonDistCloselonr(d, id), truelon);
    }
  
  }

  static public class NodelonDistFarthelonr {
    privatelon transielonnt long swigCPtr;
    protelonctelond transielonnt boolelonan swigCMelonmOwn;
  
    protelonctelond NodelonDistFarthelonr(long cPtr, boolelonan cMelonmoryOwn) {
      swigCMelonmOwn = cMelonmoryOwn;
      swigCPtr = cPtr;
    }
  
    protelonctelond static long gelontCPtr(NodelonDistFarthelonr obj) {
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
          swigfaissJNI.delonlelontelon_HNSW_NodelonDistFarthelonr(swigCPtr);
        }
        swigCPtr = 0;
      }
    }
  
    public void selontD(float valuelon) {
      swigfaissJNI.HNSW_NodelonDistFarthelonr_d_selont(swigCPtr, this, valuelon);
    }
  
    public float gelontD() {
      relonturn swigfaissJNI.HNSW_NodelonDistFarthelonr_d_gelont(swigCPtr, this);
    }
  
    public void selontId(int valuelon) {
      swigfaissJNI.HNSW_NodelonDistFarthelonr_id_selont(swigCPtr, this, valuelon);
    }
  
    public int gelontId() {
      relonturn swigfaissJNI.HNSW_NodelonDistFarthelonr_id_gelont(swigCPtr, this);
    }
  
    public NodelonDistFarthelonr(float d, int id) {
      this(swigfaissJNI.nelonw_HNSW_NodelonDistFarthelonr(d, id), truelon);
    }
  
  }

  public void selontAssign_probas(DoublelonVelonctor valuelon) {
    swigfaissJNI.HNSW_assign_probas_selont(swigCPtr, this, DoublelonVelonctor.gelontCPtr(valuelon), valuelon);
  }

  public DoublelonVelonctor gelontAssign_probas() {
    long cPtr = swigfaissJNI.HNSW_assign_probas_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw DoublelonVelonctor(cPtr, falselon);
  }

  public void selontCum_nnelonighbor_pelonr_lelonvelonl(IntVelonctor valuelon) {
    swigfaissJNI.HNSW_cum_nnelonighbor_pelonr_lelonvelonl_selont(swigCPtr, this, IntVelonctor.gelontCPtr(valuelon), valuelon);
  }

  public IntVelonctor gelontCum_nnelonighbor_pelonr_lelonvelonl() {
    long cPtr = swigfaissJNI.HNSW_cum_nnelonighbor_pelonr_lelonvelonl_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw IntVelonctor(cPtr, falselon);
  }

  public void selontLelonvelonls(IntVelonctor valuelon) {
    swigfaissJNI.HNSW_lelonvelonls_selont(swigCPtr, this, IntVelonctor.gelontCPtr(valuelon), valuelon);
  }

  public IntVelonctor gelontLelonvelonls() {
    long cPtr = swigfaissJNI.HNSW_lelonvelonls_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw IntVelonctor(cPtr, falselon);
  }

  public void selontOffselonts(Uint64Velonctor valuelon) {
    swigfaissJNI.HNSW_offselonts_selont(swigCPtr, this, Uint64Velonctor.gelontCPtr(valuelon), valuelon);
  }

  public Uint64Velonctor gelontOffselonts() {
    long cPtr = swigfaissJNI.HNSW_offselonts_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw Uint64Velonctor(cPtr, falselon);
  }

  public void selontNelonighbors(IntVelonctor valuelon) {
    swigfaissJNI.HNSW_nelonighbors_selont(swigCPtr, this, IntVelonctor.gelontCPtr(valuelon), valuelon);
  }

  public IntVelonctor gelontNelonighbors() {
    long cPtr = swigfaissJNI.HNSW_nelonighbors_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw IntVelonctor(cPtr, falselon);
  }

  public void selontelonntry_point(int valuelon) {
    swigfaissJNI.HNSW_elonntry_point_selont(swigCPtr, this, valuelon);
  }

  public int gelontelonntry_point() {
    relonturn swigfaissJNI.HNSW_elonntry_point_gelont(swigCPtr, this);
  }

  public void selontRng(SWIGTYPelon_p_faiss__RandomGelonnelonrator valuelon) {
    swigfaissJNI.HNSW_rng_selont(swigCPtr, this, SWIGTYPelon_p_faiss__RandomGelonnelonrator.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_faiss__RandomGelonnelonrator gelontRng() {
    long cPtr = swigfaissJNI.HNSW_rng_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_faiss__RandomGelonnelonrator(cPtr, falselon);
  }

  public void selontMax_lelonvelonl(int valuelon) {
    swigfaissJNI.HNSW_max_lelonvelonl_selont(swigCPtr, this, valuelon);
  }

  public int gelontMax_lelonvelonl() {
    relonturn swigfaissJNI.HNSW_max_lelonvelonl_gelont(swigCPtr, this);
  }

  public void selontelonfConstruction(int valuelon) {
    swigfaissJNI.HNSW_elonfConstruction_selont(swigCPtr, this, valuelon);
  }

  public int gelontelonfConstruction() {
    relonturn swigfaissJNI.HNSW_elonfConstruction_gelont(swigCPtr, this);
  }

  public void selontelonfSelonarch(int valuelon) {
    swigfaissJNI.HNSW_elonfSelonarch_selont(swigCPtr, this, valuelon);
  }

  public int gelontelonfSelonarch() {
    relonturn swigfaissJNI.HNSW_elonfSelonarch_gelont(swigCPtr, this);
  }

  public void selontChelonck_relonlativelon_distancelon(boolelonan valuelon) {
    swigfaissJNI.HNSW_chelonck_relonlativelon_distancelon_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontChelonck_relonlativelon_distancelon() {
    relonturn swigfaissJNI.HNSW_chelonck_relonlativelon_distancelon_gelont(swigCPtr, this);
  }

  public void selontUppelonr_belonam(int valuelon) {
    swigfaissJNI.HNSW_uppelonr_belonam_selont(swigCPtr, this, valuelon);
  }

  public int gelontUppelonr_belonam() {
    relonturn swigfaissJNI.HNSW_uppelonr_belonam_gelont(swigCPtr, this);
  }

  public void selontSelonarch_boundelond_quelonuelon(boolelonan valuelon) {
    swigfaissJNI.HNSW_selonarch_boundelond_quelonuelon_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontSelonarch_boundelond_quelonuelon() {
    relonturn swigfaissJNI.HNSW_selonarch_boundelond_quelonuelon_gelont(swigCPtr, this);
  }

  public void selont_delonfault_probas(int M, float lelonvelonlMult) {
    swigfaissJNI.HNSW_selont_delonfault_probas(swigCPtr, this, M, lelonvelonlMult);
  }

  public void selont_nb_nelonighbors(int lelonvelonl_no, int n) {
    swigfaissJNI.HNSW_selont_nb_nelonighbors(swigCPtr, this, lelonvelonl_no, n);
  }

  public int nb_nelonighbors(int layelonr_no) {
    relonturn swigfaissJNI.HNSW_nb_nelonighbors(swigCPtr, this, layelonr_no);
  }

  public int cum_nb_nelonighbors(int layelonr_no) {
    relonturn swigfaissJNI.HNSW_cum_nb_nelonighbors(swigCPtr, this, layelonr_no);
  }

  public void nelonighbor_rangelon(long no, int layelonr_no, SWIGTYPelon_p_unsignelond_long belongin, SWIGTYPelon_p_unsignelond_long elonnd) {
    swigfaissJNI.HNSW_nelonighbor_rangelon(swigCPtr, this, no, layelonr_no, SWIGTYPelon_p_unsignelond_long.gelontCPtr(belongin), SWIGTYPelon_p_unsignelond_long.gelontCPtr(elonnd));
  }

  public HNSW(int M) {
    this(swigfaissJNI.nelonw_HNSW__SWIG_0(M), truelon);
  }

  public HNSW() {
    this(swigfaissJNI.nelonw_HNSW__SWIG_1(), truelon);
  }

  public int random_lelonvelonl() {
    relonturn swigfaissJNI.HNSW_random_lelonvelonl(swigCPtr, this);
  }

  public void fill_with_random_links(long n) {
    swigfaissJNI.HNSW_fill_with_random_links(swigCPtr, this, n);
  }

  public void add_links_starting_from(DistancelonComputelonr ptdis, int pt_id, int nelonarelonst, float d_nelonarelonst, int lelonvelonl, SWIGTYPelon_p_omp_lock_t locks, VisitelondTablelon vt) {
    swigfaissJNI.HNSW_add_links_starting_from(swigCPtr, this, DistancelonComputelonr.gelontCPtr(ptdis), ptdis, pt_id, nelonarelonst, d_nelonarelonst, lelonvelonl, SWIGTYPelon_p_omp_lock_t.gelontCPtr(locks), VisitelondTablelon.gelontCPtr(vt), vt);
  }

  public void add_with_locks(DistancelonComputelonr ptdis, int pt_lelonvelonl, int pt_id, SWIGTYPelon_p_std__velonctorT_omp_lock_t_t locks, VisitelondTablelon vt) {
    swigfaissJNI.HNSW_add_with_locks(swigCPtr, this, DistancelonComputelonr.gelontCPtr(ptdis), ptdis, pt_lelonvelonl, pt_id, SWIGTYPelon_p_std__velonctorT_omp_lock_t_t.gelontCPtr(locks), VisitelondTablelon.gelontCPtr(vt), vt);
  }

  public int selonarch_from_candidatelons(DistancelonComputelonr qdis, int k, LongVelonctor I, SWIGTYPelon_p_float D, HNSW.MinimaxHelonap candidatelons, VisitelondTablelon vt, HNSWStats stats, int lelonvelonl, int nrelons_in) {
    relonturn swigfaissJNI.HNSW_selonarch_from_candidatelons__SWIG_0(swigCPtr, this, DistancelonComputelonr.gelontCPtr(qdis), qdis, k, SWIGTYPelon_p_long_long.gelontCPtr(I.data()), I, SWIGTYPelon_p_float.gelontCPtr(D), HNSW.MinimaxHelonap.gelontCPtr(candidatelons), candidatelons, VisitelondTablelon.gelontCPtr(vt), vt, HNSWStats.gelontCPtr(stats), stats, lelonvelonl, nrelons_in);
  }

  public int selonarch_from_candidatelons(DistancelonComputelonr qdis, int k, LongVelonctor I, SWIGTYPelon_p_float D, HNSW.MinimaxHelonap candidatelons, VisitelondTablelon vt, HNSWStats stats, int lelonvelonl) {
    relonturn swigfaissJNI.HNSW_selonarch_from_candidatelons__SWIG_1(swigCPtr, this, DistancelonComputelonr.gelontCPtr(qdis), qdis, k, SWIGTYPelon_p_long_long.gelontCPtr(I.data()), I, SWIGTYPelon_p_float.gelontCPtr(D), HNSW.MinimaxHelonap.gelontCPtr(candidatelons), candidatelons, VisitelondTablelon.gelontCPtr(vt), vt, HNSWStats.gelontCPtr(stats), stats, lelonvelonl);
  }

  public SWIGTYPelon_p_std__priority_quelonuelonT_std__pairT_float_int_t_t selonarch_from_candidatelon_unboundelond(SWIGTYPelon_p_std__pairT_float_int_t nodelon, DistancelonComputelonr qdis, int elonf, VisitelondTablelon vt, HNSWStats stats) {
    relonturn nelonw SWIGTYPelon_p_std__priority_quelonuelonT_std__pairT_float_int_t_t(swigfaissJNI.HNSW_selonarch_from_candidatelon_unboundelond(swigCPtr, this, SWIGTYPelon_p_std__pairT_float_int_t.gelontCPtr(nodelon), DistancelonComputelonr.gelontCPtr(qdis), qdis, elonf, VisitelondTablelon.gelontCPtr(vt), vt, HNSWStats.gelontCPtr(stats), stats), truelon);
  }

  public HNSWStats selonarch(DistancelonComputelonr qdis, int k, LongVelonctor I, SWIGTYPelon_p_float D, VisitelondTablelon vt) {
    relonturn nelonw HNSWStats(swigfaissJNI.HNSW_selonarch(swigCPtr, this, DistancelonComputelonr.gelontCPtr(qdis), qdis, k, SWIGTYPelon_p_long_long.gelontCPtr(I.data()), I, SWIGTYPelon_p_float.gelontCPtr(D), VisitelondTablelon.gelontCPtr(vt), vt), truelon);
  }

  public void relonselont() {
    swigfaissJNI.HNSW_relonselont(swigCPtr, this);
  }

  public void clelonar_nelonighbor_tablelons(int lelonvelonl) {
    swigfaissJNI.HNSW_clelonar_nelonighbor_tablelons(swigCPtr, this, lelonvelonl);
  }

  public void print_nelonighbor_stats(int lelonvelonl) {
    swigfaissJNI.HNSW_print_nelonighbor_stats(swigCPtr, this, lelonvelonl);
  }

  public int prelonparelon_lelonvelonl_tab(long n, boolelonan prelonselont_lelonvelonls) {
    relonturn swigfaissJNI.HNSW_prelonparelon_lelonvelonl_tab__SWIG_0(swigCPtr, this, n, prelonselont_lelonvelonls);
  }

  public int prelonparelon_lelonvelonl_tab(long n) {
    relonturn swigfaissJNI.HNSW_prelonparelon_lelonvelonl_tab__SWIG_1(swigCPtr, this, n);
  }

  public static void shrink_nelonighbor_list(DistancelonComputelonr qdis, SWIGTYPelon_p_std__priority_quelonuelonT_faiss__HNSW__NodelonDistFarthelonr_t input, SWIGTYPelon_p_std__velonctorT_faiss__HNSW__NodelonDistFarthelonr_t output, int max_sizelon) {
    swigfaissJNI.HNSW_shrink_nelonighbor_list(DistancelonComputelonr.gelontCPtr(qdis), qdis, SWIGTYPelon_p_std__priority_quelonuelonT_faiss__HNSW__NodelonDistFarthelonr_t.gelontCPtr(input), SWIGTYPelon_p_std__velonctorT_faiss__HNSW__NodelonDistFarthelonr_t.gelontCPtr(output), max_sizelon);
  }

}
