/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (U ﹏ U)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. :3
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass p-powysemoustwaining extends simuwatedanneawingpawametews {
  pwivate t-twansient wong swigcptw;

  p-pwotected powysemoustwaining(wong cptw, ( ͡o ω ͡o ) boowean cmemowyown) {
    supew(swigfaissjni.powysemoustwaining_swigupcast(cptw), σωσ c-cmemowyown);
    swigcptw = c-cptw;
  }

  p-pwotected static wong getcptw(powysemoustwaining obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  p-pwotected void finawize() {
    dewete();
  }

  pubwic synchwonized void dewete() {
    i-if (swigcptw != 0) {
      if (swigcmemown) {
        s-swigcmemown = f-fawse;
        s-swigfaissjni.dewete_powysemoustwaining(swigcptw);
      }
      s-swigcptw = 0;
    }
    supew.dewete();
  }

  pubwic void setoptimization_type(powysemoustwaining.optimization_type_t v-vawue) {
    swigfaissjni.powysemoustwaining_optimization_type_set(swigcptw, >w< this, 😳😳😳 vawue.swigvawue());
  }

  p-pubwic powysemoustwaining.optimization_type_t getoptimization_type() {
    wetuwn powysemoustwaining.optimization_type_t.swigtoenum(swigfaissjni.powysemoustwaining_optimization_type_get(swigcptw, OwO this));
  }

  pubwic void setntwain_pewmutation(int v-vawue) {
    swigfaissjni.powysemoustwaining_ntwain_pewmutation_set(swigcptw, 😳 t-this, 😳😳😳 v-vawue);
  }

  p-pubwic int getntwain_pewmutation() {
    wetuwn swigfaissjni.powysemoustwaining_ntwain_pewmutation_get(swigcptw, (˘ω˘) this);
  }

  p-pubwic void setdis_weight_factow(doubwe v-vawue) {
    swigfaissjni.powysemoustwaining_dis_weight_factow_set(swigcptw, ʘwʘ t-this, ( ͡o ω ͡o ) vawue);
  }

  p-pubwic doubwe getdis_weight_factow() {
    w-wetuwn swigfaissjni.powysemoustwaining_dis_weight_factow_get(swigcptw, o.O this);
  }

  p-pubwic void setmax_memowy(wong vawue) {
    s-swigfaissjni.powysemoustwaining_max_memowy_set(swigcptw, >w< this, vawue);
  }

  p-pubwic wong getmax_memowy() {
    w-wetuwn swigfaissjni.powysemoustwaining_max_memowy_get(swigcptw, 😳 t-this);
  }

  pubwic void setwog_pattewn(stwing vawue) {
    swigfaissjni.powysemoustwaining_wog_pattewn_set(swigcptw, 🥺 this, vawue);
  }

  p-pubwic stwing g-getwog_pattewn() {
    wetuwn swigfaissjni.powysemoustwaining_wog_pattewn_get(swigcptw, rawr x3 t-this);
  }

  p-pubwic powysemoustwaining() {
    t-this(swigfaissjni.new_powysemoustwaining(), o.O twue);
  }

  pubwic void optimize_pq_fow_hamming(pwoductquantizew pq, rawr wong n-ny, ʘwʘ swigtype_p_fwoat x) {
    swigfaissjni.powysemoustwaining_optimize_pq_fow_hamming(swigcptw, 😳😳😳 this, pwoductquantizew.getcptw(pq), ^^;; pq, ny, swigtype_p_fwoat.getcptw(x));
  }

  pubwic void optimize_wanking(pwoductquantizew pq, o.O w-wong ny, (///ˬ///✿) swigtype_p_fwoat x) {
    s-swigfaissjni.powysemoustwaining_optimize_wanking(swigcptw, σωσ t-this, nyaa~~ pwoductquantizew.getcptw(pq), ^^;; p-pq, ^•ﻌ•^ ny, swigtype_p_fwoat.getcptw(x));
  }

  pubwic void optimize_wepwoduce_distances(pwoductquantizew p-pq) {
    s-swigfaissjni.powysemoustwaining_optimize_wepwoduce_distances(swigcptw, σωσ t-this, -.- p-pwoductquantizew.getcptw(pq), ^^;; pq);
  }

  pubwic wong memowy_usage_pew_thwead(pwoductquantizew p-pq) {
    wetuwn s-swigfaissjni.powysemoustwaining_memowy_usage_pew_thwead(swigcptw, XD t-this, 🥺 pwoductquantizew.getcptw(pq), òωó p-pq);
  }

  p-pubwic finaw static cwass optimization_type_t {
    pubwic finaw static powysemoustwaining.optimization_type_t o-ot_none = nyew powysemoustwaining.optimization_type_t("ot_none");
    pubwic finaw static powysemoustwaining.optimization_type_t ot_wepwoducedistances_affine = nyew powysemoustwaining.optimization_type_t("ot_wepwoducedistances_affine");
    p-pubwic finaw static powysemoustwaining.optimization_type_t ot_wanking_weighted_diff = nyew p-powysemoustwaining.optimization_type_t("ot_wanking_weighted_diff");

    p-pubwic f-finaw int swigvawue() {
      wetuwn s-swigvawue;
    }

    pubwic s-stwing tostwing() {
      w-wetuwn swigname;
    }

    pubwic static optimization_type_t swigtoenum(int swigvawue) {
      i-if (swigvawue < swigvawues.wength && s-swigvawue >= 0 && swigvawues[swigvawue].swigvawue == s-swigvawue)
        w-wetuwn swigvawues[swigvawue];
      fow (int i-i = 0; i < s-swigvawues.wength; i++)
        i-if (swigvawues[i].swigvawue == s-swigvawue)
          wetuwn swigvawues[i];
      thwow nyew iwwegawawgumentexception("no enum " + optimization_type_t.cwass + " w-with vawue " + swigvawue);
    }

    p-pwivate optimization_type_t(stwing s-swigname) {
      this.swigname = s-swigname;
      t-this.swigvawue = swignext++;
    }

    p-pwivate optimization_type_t(stwing swigname, (ˆ ﻌ ˆ)♡ int swigvawue) {
      this.swigname = swigname;
      t-this.swigvawue = s-swigvawue;
      swignext = swigvawue+1;
    }

    p-pwivate o-optimization_type_t(stwing swigname, -.- optimization_type_t swigenum) {
      this.swigname = swigname;
      this.swigvawue = swigenum.swigvawue;
      s-swignext = this.swigvawue+1;
    }

    pwivate static optimization_type_t[] swigvawues = { o-ot_none, :3 ot_wepwoducedistances_affine, ʘwʘ ot_wanking_weighted_diff };
    pwivate s-static int s-swignext = 0;
    pwivate finaw int swigvawue;
    pwivate finaw s-stwing swigname;
  }

}
