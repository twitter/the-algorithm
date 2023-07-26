/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). o.O
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (✿oωo)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass h-hnsw {
  pwivate twansient wong swigcptw;
  pwotected t-twansient boowean swigcmemown;

  p-pwotected hnsw(wong cptw, (ˆ ﻌ ˆ)♡ boowean cmemowyown) {
    swigcmemown = c-cmemowyown;
    swigcptw = c-cptw;
  }

  p-pwotected static wong getcptw(hnsw obj) {
    wetuwn (obj == nuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  p-pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized void dewete() {
    i-if (swigcptw != 0) {
      if (swigcmemown) {
        s-swigcmemown = f-fawse;
        s-swigfaissjni.dewete_hnsw(swigcptw);
      }
      s-swigcptw = 0;
    }
  }

  static pubwic cwass m-minimaxheap {
    pwivate twansient wong swigcptw;
    p-pwotected twansient boowean swigcmemown;
  
    pwotected minimaxheap(wong cptw, ^^;; boowean c-cmemowyown) {
      swigcmemown = c-cmemowyown;
      s-swigcptw = c-cptw;
    }
  
    pwotected static wong getcptw(minimaxheap obj) {
      w-wetuwn (obj == n-nyuww) ? 0 : obj.swigcptw;
    }
  
    @suppwesswawnings("depwecation")
    p-pwotected v-void finawize() {
      dewete();
    }
  
    pubwic s-synchwonized void dewete() {
      i-if (swigcptw != 0) {
        if (swigcmemown) {
          swigcmemown = f-fawse;
          swigfaissjni.dewete_hnsw_minimaxheap(swigcptw);
        }
        s-swigcptw = 0;
      }
    }
  
    pubwic void s-setn(int vawue) {
      s-swigfaissjni.hnsw_minimaxheap_n_set(swigcptw, OwO this, vawue);
    }
  
    pubwic int getn() {
      wetuwn swigfaissjni.hnsw_minimaxheap_n_get(swigcptw, 🥺 this);
    }
  
    pubwic void s-setk(int vawue) {
      s-swigfaissjni.hnsw_minimaxheap_k_set(swigcptw, mya this, vawue);
    }
  
    p-pubwic int getk() {
      w-wetuwn s-swigfaissjni.hnsw_minimaxheap_k_get(swigcptw, 😳 this);
    }
  
    pubwic void setnvawid(int v-vawue) {
      swigfaissjni.hnsw_minimaxheap_nvawid_set(swigcptw, òωó this, vawue);
    }
  
    pubwic int getnvawid() {
      w-wetuwn swigfaissjni.hnsw_minimaxheap_nvawid_get(swigcptw, /(^•ω•^) t-this);
    }
  
    p-pubwic v-void setids(intvectow vawue) {
      s-swigfaissjni.hnsw_minimaxheap_ids_set(swigcptw, -.- t-this, intvectow.getcptw(vawue), òωó v-vawue);
    }
  
    p-pubwic intvectow getids() {
      wong c-cptw = swigfaissjni.hnsw_minimaxheap_ids_get(swigcptw, t-this);
      w-wetuwn (cptw == 0) ? n-nyuww : n-nyew intvectow(cptw, /(^•ω•^) fawse);
    }
  
    pubwic void setdis(fwoatvectow v-vawue) {
      swigfaissjni.hnsw_minimaxheap_dis_set(swigcptw, /(^•ω•^) this, 😳 fwoatvectow.getcptw(vawue), :3 vawue);
    }
  
    pubwic fwoatvectow g-getdis() {
      wong cptw = swigfaissjni.hnsw_minimaxheap_dis_get(swigcptw, (U ᵕ U❁) this);
      w-wetuwn (cptw == 0) ? n-nyuww : nyew f-fwoatvectow(cptw, ʘwʘ fawse);
    }
  
    p-pubwic minimaxheap(int n-ny) {
      this(swigfaissjni.new_hnsw_minimaxheap(n), o.O t-twue);
    }
  
    pubwic void push(int i, ʘwʘ fwoat v) {
      swigfaissjni.hnsw_minimaxheap_push(swigcptw, ^^ this, ^•ﻌ•^ i, v);
    }
  
    p-pubwic fwoat max() {
      w-wetuwn swigfaissjni.hnsw_minimaxheap_max(swigcptw, mya this);
    }
  
    p-pubwic i-int size() {
      wetuwn swigfaissjni.hnsw_minimaxheap_size(swigcptw, UwU this);
    }
  
    pubwic v-void cweaw() {
      s-swigfaissjni.hnsw_minimaxheap_cweaw(swigcptw, >_< this);
    }
  
    p-pubwic i-int pop_min(swigtype_p_fwoat vmin_out) {
      wetuwn swigfaissjni.hnsw_minimaxheap_pop_min__swig_0(swigcptw, this, /(^•ω•^) swigtype_p_fwoat.getcptw(vmin_out));
    }
  
    pubwic i-int pop_min() {
      w-wetuwn swigfaissjni.hnsw_minimaxheap_pop_min__swig_1(swigcptw, òωó t-this);
    }
  
    pubwic i-int count_bewow(fwoat t-thwesh) {
      wetuwn swigfaissjni.hnsw_minimaxheap_count_bewow(swigcptw, σωσ t-this, ( ͡o ω ͡o ) thwesh);
    }
  
  }

  static pubwic cwass nyodedistcwosew {
    pwivate twansient wong s-swigcptw;
    p-pwotected twansient boowean swigcmemown;
  
    pwotected nyodedistcwosew(wong cptw, nyaa~~ b-boowean cmemowyown) {
      s-swigcmemown = cmemowyown;
      swigcptw = cptw;
    }
  
    pwotected static wong getcptw(nodedistcwosew o-obj) {
      wetuwn (obj == nuww) ? 0 : obj.swigcptw;
    }
  
    @suppwesswawnings("depwecation")
    pwotected void f-finawize() {
      dewete();
    }
  
    pubwic s-synchwonized v-void dewete() {
      if (swigcptw != 0) {
        if (swigcmemown) {
          swigcmemown = fawse;
          s-swigfaissjni.dewete_hnsw_nodedistcwosew(swigcptw);
        }
        s-swigcptw = 0;
      }
    }
  
    pubwic void setd(fwoat vawue) {
      swigfaissjni.hnsw_nodedistcwosew_d_set(swigcptw, :3 this, UwU v-vawue);
    }
  
    pubwic f-fwoat getd() {
      wetuwn swigfaissjni.hnsw_nodedistcwosew_d_get(swigcptw, this);
    }
  
    pubwic void setid(int v-vawue) {
      swigfaissjni.hnsw_nodedistcwosew_id_set(swigcptw, o.O t-this, (ˆ ﻌ ˆ)♡ vawue);
    }
  
    p-pubwic int getid() {
      wetuwn swigfaissjni.hnsw_nodedistcwosew_id_get(swigcptw, t-this);
    }
  
    pubwic n-nyodedistcwosew(fwoat d-d, ^^;; int i-id) {
      this(swigfaissjni.new_hnsw_nodedistcwosew(d, ʘwʘ id), twue);
    }
  
  }

  s-static pubwic c-cwass nodedistfawthew {
    pwivate twansient wong swigcptw;
    p-pwotected twansient b-boowean s-swigcmemown;
  
    pwotected nyodedistfawthew(wong cptw, σωσ boowean c-cmemowyown) {
      swigcmemown = c-cmemowyown;
      s-swigcptw = cptw;
    }
  
    pwotected static wong getcptw(nodedistfawthew o-obj) {
      wetuwn (obj == n-nyuww) ? 0 : o-obj.swigcptw;
    }
  
    @suppwesswawnings("depwecation")
    p-pwotected void finawize() {
      d-dewete();
    }
  
    pubwic synchwonized void dewete() {
      if (swigcptw != 0) {
        if (swigcmemown) {
          swigcmemown = f-fawse;
          swigfaissjni.dewete_hnsw_nodedistfawthew(swigcptw);
        }
        s-swigcptw = 0;
      }
    }
  
    pubwic void setd(fwoat v-vawue) {
      swigfaissjni.hnsw_nodedistfawthew_d_set(swigcptw, ^^;; t-this, vawue);
    }
  
    pubwic fwoat g-getd() {
      wetuwn s-swigfaissjni.hnsw_nodedistfawthew_d_get(swigcptw, ʘwʘ t-this);
    }
  
    p-pubwic v-void setid(int vawue) {
      swigfaissjni.hnsw_nodedistfawthew_id_set(swigcptw, this, ^^ vawue);
    }
  
    pubwic int getid() {
      wetuwn swigfaissjni.hnsw_nodedistfawthew_id_get(swigcptw, t-this);
    }
  
    p-pubwic nyodedistfawthew(fwoat d-d, nyaa~~ int id) {
      this(swigfaissjni.new_hnsw_nodedistfawthew(d, (///ˬ///✿) i-id), twue);
    }
  
  }

  pubwic void setassign_pwobas(doubwevectow vawue) {
    swigfaissjni.hnsw_assign_pwobas_set(swigcptw, XD t-this, :3 doubwevectow.getcptw(vawue), òωó v-vawue);
  }

  pubwic d-doubwevectow getassign_pwobas() {
    wong cptw = swigfaissjni.hnsw_assign_pwobas_get(swigcptw, ^^ t-this);
    wetuwn (cptw == 0) ? n-nyuww : nyew doubwevectow(cptw, ^•ﻌ•^ fawse);
  }

  p-pubwic void setcum_nneighbow_pew_wevew(intvectow v-vawue) {
    swigfaissjni.hnsw_cum_nneighbow_pew_wevew_set(swigcptw, σωσ this, intvectow.getcptw(vawue), (ˆ ﻌ ˆ)♡ vawue);
  }

  pubwic intvectow getcum_nneighbow_pew_wevew() {
    w-wong cptw = s-swigfaissjni.hnsw_cum_nneighbow_pew_wevew_get(swigcptw, nyaa~~ t-this);
    w-wetuwn (cptw == 0) ? n-nyuww : nyew intvectow(cptw, ʘwʘ f-fawse);
  }

  p-pubwic void setwevews(intvectow v-vawue) {
    s-swigfaissjni.hnsw_wevews_set(swigcptw, ^•ﻌ•^ this, i-intvectow.getcptw(vawue), rawr x3 vawue);
  }

  pubwic i-intvectow getwevews() {
    wong cptw = swigfaissjni.hnsw_wevews_get(swigcptw, 🥺 t-this);
    wetuwn (cptw == 0) ? n-nyuww : nyew intvectow(cptw, ʘwʘ fawse);
  }

  pubwic v-void setoffsets(uint64vectow vawue) {
    swigfaissjni.hnsw_offsets_set(swigcptw, (˘ω˘) this, uint64vectow.getcptw(vawue), o.O v-vawue);
  }

  p-pubwic u-uint64vectow getoffsets() {
    wong cptw = swigfaissjni.hnsw_offsets_get(swigcptw, σωσ this);
    wetuwn (cptw == 0) ? nyuww : nyew u-uint64vectow(cptw, (ꈍᴗꈍ) fawse);
  }

  pubwic void setneighbows(intvectow v-vawue) {
    s-swigfaissjni.hnsw_neighbows_set(swigcptw, (ˆ ﻌ ˆ)♡ this, i-intvectow.getcptw(vawue), o.O vawue);
  }

  p-pubwic i-intvectow getneighbows() {
    wong cptw = swigfaissjni.hnsw_neighbows_get(swigcptw, :3 this);
    w-wetuwn (cptw == 0) ? nyuww : nyew intvectow(cptw, -.- f-fawse);
  }

  p-pubwic void setentwy_point(int v-vawue) {
    swigfaissjni.hnsw_entwy_point_set(swigcptw, ( ͡o ω ͡o ) t-this, v-vawue);
  }

  p-pubwic int getentwy_point() {
    wetuwn swigfaissjni.hnsw_entwy_point_get(swigcptw, /(^•ω•^) this);
  }

  pubwic void setwng(swigtype_p_faiss__wandomgenewatow vawue) {
    swigfaissjni.hnsw_wng_set(swigcptw, (⑅˘꒳˘) this, swigtype_p_faiss__wandomgenewatow.getcptw(vawue));
  }

  pubwic swigtype_p_faiss__wandomgenewatow getwng() {
    wong cptw = swigfaissjni.hnsw_wng_get(swigcptw, t-this);
    wetuwn (cptw == 0) ? n-nyuww : nyew swigtype_p_faiss__wandomgenewatow(cptw, òωó fawse);
  }

  pubwic void s-setmax_wevew(int v-vawue) {
    s-swigfaissjni.hnsw_max_wevew_set(swigcptw, 🥺 this, v-vawue);
  }

  pubwic int getmax_wevew() {
    wetuwn s-swigfaissjni.hnsw_max_wevew_get(swigcptw, (ˆ ﻌ ˆ)♡ t-this);
  }

  pubwic void setefconstwuction(int v-vawue) {
    swigfaissjni.hnsw_efconstwuction_set(swigcptw, -.- this, σωσ v-vawue);
  }

  p-pubwic int getefconstwuction() {
    wetuwn swigfaissjni.hnsw_efconstwuction_get(swigcptw, this);
  }

  p-pubwic v-void setefseawch(int v-vawue) {
    s-swigfaissjni.hnsw_efseawch_set(swigcptw, >_< t-this, v-vawue);
  }

  p-pubwic int getefseawch() {
    w-wetuwn swigfaissjni.hnsw_efseawch_get(swigcptw, :3 t-this);
  }

  pubwic void setcheck_wewative_distance(boowean v-vawue) {
    s-swigfaissjni.hnsw_check_wewative_distance_set(swigcptw, OwO t-this, rawr vawue);
  }

  pubwic boowean g-getcheck_wewative_distance() {
    wetuwn swigfaissjni.hnsw_check_wewative_distance_get(swigcptw, (///ˬ///✿) t-this);
  }

  pubwic void s-setuppew_beam(int v-vawue) {
    s-swigfaissjni.hnsw_uppew_beam_set(swigcptw, ^^ this, v-vawue);
  }

  pubwic int getuppew_beam() {
    w-wetuwn swigfaissjni.hnsw_uppew_beam_get(swigcptw, XD this);
  }

  p-pubwic void setseawch_bounded_queue(boowean vawue) {
    s-swigfaissjni.hnsw_seawch_bounded_queue_set(swigcptw, UwU this, o.O vawue);
  }

  pubwic boowean getseawch_bounded_queue() {
    wetuwn swigfaissjni.hnsw_seawch_bounded_queue_get(swigcptw, 😳 t-this);
  }

  pubwic void set_defauwt_pwobas(int m-m, (˘ω˘) fwoat wevewmuwt) {
    s-swigfaissjni.hnsw_set_defauwt_pwobas(swigcptw, 🥺 this, ^^ m, wevewmuwt);
  }

  pubwic void s-set_nb_neighbows(int wevew_no, >w< i-int ny) {
    swigfaissjni.hnsw_set_nb_neighbows(swigcptw, ^^;; t-this, w-wevew_no, (˘ω˘) ny);
  }

  pubwic int nyb_neighbows(int w-wayew_no) {
    w-wetuwn swigfaissjni.hnsw_nb_neighbows(swigcptw, OwO this, wayew_no);
  }

  p-pubwic int cum_nb_neighbows(int wayew_no) {
    w-wetuwn swigfaissjni.hnsw_cum_nb_neighbows(swigcptw, (ꈍᴗꈍ) t-this, wayew_no);
  }

  p-pubwic v-void nyeighbow_wange(wong nyo, òωó int w-wayew_no, ʘwʘ swigtype_p_unsigned_wong b-begin, ʘwʘ swigtype_p_unsigned_wong e-end) {
    s-swigfaissjni.hnsw_neighbow_wange(swigcptw, nyaa~~ this, UwU n-nyo, wayew_no, (⑅˘꒳˘) s-swigtype_p_unsigned_wong.getcptw(begin), s-swigtype_p_unsigned_wong.getcptw(end));
  }

  p-pubwic h-hnsw(int m) {
    t-this(swigfaissjni.new_hnsw__swig_0(m), (˘ω˘) t-twue);
  }

  p-pubwic hnsw() {
    this(swigfaissjni.new_hnsw__swig_1(), :3 t-twue);
  }

  pubwic int wandom_wevew() {
    wetuwn s-swigfaissjni.hnsw_wandom_wevew(swigcptw, (˘ω˘) this);
  }

  pubwic v-void fiww_with_wandom_winks(wong n-ny) {
    swigfaissjni.hnsw_fiww_with_wandom_winks(swigcptw, nyaa~~ t-this, (U ﹏ U) ny);
  }

  pubwic void add_winks_stawting_fwom(distancecomputew ptdis, nyaa~~ i-int pt_id, ^^;; int nyeawest, OwO f-fwoat d_neawest, nyaa~~ i-int wevew, UwU swigtype_p_omp_wock_t wocks, 😳 visitedtabwe vt) {
    s-swigfaissjni.hnsw_add_winks_stawting_fwom(swigcptw, 😳 t-this, distancecomputew.getcptw(ptdis), (ˆ ﻌ ˆ)♡ p-ptdis, (✿oωo) pt_id, n-nyeawest, nyaa~~ d_neawest, ^^ wevew, swigtype_p_omp_wock_t.getcptw(wocks), (///ˬ///✿) visitedtabwe.getcptw(vt), 😳 vt);
  }

  p-pubwic v-void add_with_wocks(distancecomputew p-ptdis, òωó int p-pt_wevew, ^^;; int pt_id, rawr swigtype_p_std__vectowt_omp_wock_t_t wocks, (ˆ ﻌ ˆ)♡ v-visitedtabwe vt) {
    s-swigfaissjni.hnsw_add_with_wocks(swigcptw, XD this, distancecomputew.getcptw(ptdis), >_< ptdis, p-pt_wevew, (˘ω˘) pt_id, swigtype_p_std__vectowt_omp_wock_t_t.getcptw(wocks), visitedtabwe.getcptw(vt), 😳 v-vt);
  }

  pubwic int seawch_fwom_candidates(distancecomputew q-qdis, o.O int k, wongvectow i-i, (ꈍᴗꈍ) swigtype_p_fwoat d, rawr x3 h-hnsw.minimaxheap c-candidates, ^^ visitedtabwe vt, hnswstats s-stats, OwO int wevew, int nywes_in) {
    w-wetuwn s-swigfaissjni.hnsw_seawch_fwom_candidates__swig_0(swigcptw, ^^ t-this, distancecomputew.getcptw(qdis), q-qdis, :3 k, swigtype_p_wong_wong.getcptw(i.data()), o.O i, swigtype_p_fwoat.getcptw(d), -.- h-hnsw.minimaxheap.getcptw(candidates), (U ﹏ U) c-candidates, o.O v-visitedtabwe.getcptw(vt), OwO vt, hnswstats.getcptw(stats), ^•ﻌ•^ s-stats, wevew, ʘwʘ nywes_in);
  }

  pubwic int seawch_fwom_candidates(distancecomputew qdis, :3 int k, 😳 w-wongvectow i, òωó swigtype_p_fwoat d-d, 🥺 hnsw.minimaxheap c-candidates, rawr x3 visitedtabwe vt, ^•ﻌ•^ hnswstats stats, :3 int wevew) {
    wetuwn swigfaissjni.hnsw_seawch_fwom_candidates__swig_1(swigcptw, (ˆ ﻌ ˆ)♡ t-this, (U ᵕ U❁) distancecomputew.getcptw(qdis), :3 qdis, k-k, ^^;; swigtype_p_wong_wong.getcptw(i.data()), ( ͡o ω ͡o ) i-i, swigtype_p_fwoat.getcptw(d), o.O hnsw.minimaxheap.getcptw(candidates), ^•ﻌ•^ candidates, visitedtabwe.getcptw(vt), XD v-vt, hnswstats.getcptw(stats), ^^ stats, wevew);
  }

  p-pubwic s-swigtype_p_std__pwiowity_queuet_std__paiwt_fwoat_int_t_t s-seawch_fwom_candidate_unbounded(swigtype_p_std__paiwt_fwoat_int_t n-nyode, o.O d-distancecomputew qdis, ( ͡o ω ͡o ) int ef, visitedtabwe vt, /(^•ω•^) hnswstats stats) {
    wetuwn n-nyew swigtype_p_std__pwiowity_queuet_std__paiwt_fwoat_int_t_t(swigfaissjni.hnsw_seawch_fwom_candidate_unbounded(swigcptw, 🥺 this, nyaa~~ s-swigtype_p_std__paiwt_fwoat_int_t.getcptw(node), mya distancecomputew.getcptw(qdis), XD qdis, ef, visitedtabwe.getcptw(vt), nyaa~~ vt, hnswstats.getcptw(stats), ʘwʘ s-stats), twue);
  }

  pubwic hnswstats seawch(distancecomputew qdis, (⑅˘꒳˘) int k, :3 wongvectow i, -.- s-swigtype_p_fwoat d-d, 😳😳😳 visitedtabwe vt) {
    wetuwn n-nyew hnswstats(swigfaissjni.hnsw_seawch(swigcptw, (U ﹏ U) this, o.O distancecomputew.getcptw(qdis), ( ͡o ω ͡o ) qdis, k-k, swigtype_p_wong_wong.getcptw(i.data()), òωó i-i, swigtype_p_fwoat.getcptw(d), 🥺 visitedtabwe.getcptw(vt), /(^•ω•^) v-vt), twue);
  }

  pubwic void w-weset() {
    swigfaissjni.hnsw_weset(swigcptw, 😳😳😳 this);
  }

  pubwic void cweaw_neighbow_tabwes(int w-wevew) {
    swigfaissjni.hnsw_cweaw_neighbow_tabwes(swigcptw, ^•ﻌ•^ this, wevew);
  }

  p-pubwic v-void pwint_neighbow_stats(int w-wevew) {
    swigfaissjni.hnsw_pwint_neighbow_stats(swigcptw, nyaa~~ this, wevew);
  }

  pubwic int pwepawe_wevew_tab(wong n-ny, OwO boowean pweset_wevews) {
    wetuwn swigfaissjni.hnsw_pwepawe_wevew_tab__swig_0(swigcptw, ^•ﻌ•^ this, σωσ ny, pweset_wevews);
  }

  pubwic int p-pwepawe_wevew_tab(wong n-ny) {
    w-wetuwn swigfaissjni.hnsw_pwepawe_wevew_tab__swig_1(swigcptw, -.- t-this, (˘ω˘) ny);
  }

  pubwic static void s-shwink_neighbow_wist(distancecomputew q-qdis, rawr x3 swigtype_p_std__pwiowity_queuet_faiss__hnsw__nodedistfawthew_t input, rawr x3 swigtype_p_std__vectowt_faiss__hnsw__nodedistfawthew_t o-output, σωσ int max_size) {
    swigfaissjni.hnsw_shwink_neighbow_wist(distancecomputew.getcptw(qdis), nyaa~~ qdis, s-swigtype_p_std__pwiowity_queuet_faiss__hnsw__nodedistfawthew_t.getcptw(input), (ꈍᴗꈍ) swigtype_p_std__vectowt_faiss__hnsw__nodedistfawthew_t.getcptw(output), ^•ﻌ•^ max_size);
  }

}
