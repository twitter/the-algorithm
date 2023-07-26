/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). XD
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. ^^;;
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic cwass i-indexpq extends indexfwatcodes {
  pwivate twansient w-wong swigcptw;

  pwotected i-indexpq(wong cptw, 🥺 boowean cmemowyown) {
    supew(swigfaissjni.indexpq_swigupcast(cptw), XD cmemowyown);
    swigcptw = c-cptw;
  }

  pwotected static w-wong getcptw(indexpq o-obj) {
    wetuwn (obj == nyuww) ? 0 : obj.swigcptw;
  }

  @suppwesswawnings("depwecation")
  pwotected v-void finawize() {
    dewete();
  }

  pubwic synchwonized void dewete() {
    i-if (swigcptw != 0) {
      if (swigcmemown) {
        s-swigcmemown = f-fawse;
        s-swigfaissjni.dewete_indexpq(swigcptw);
      }
      s-swigcptw = 0;
    }
    supew.dewete();
  }

  pubwic v-void setpq(pwoductquantizew vawue) {
    swigfaissjni.indexpq_pq_set(swigcptw, (U ᵕ U❁) t-this, :3 pwoductquantizew.getcptw(vawue), ( ͡o ω ͡o ) vawue);
  }

  pubwic pwoductquantizew getpq() {
    wong cptw = swigfaissjni.indexpq_pq_get(swigcptw, òωó t-this);
    wetuwn (cptw == 0) ? n-nyuww : n-nyew pwoductquantizew(cptw, σωσ f-fawse);
  }

  pubwic indexpq(int d, (U ᵕ U❁) wong m, (✿oωo) wong nybits, metwictype m-metwic) {
    t-this(swigfaissjni.new_indexpq__swig_0(d, ^^ m, n-nybits, ^•ﻌ•^ metwic.swigvawue()), XD t-twue);
  }

  pubwic i-indexpq(int d, wong m, :3 wong nybits) {
    t-this(swigfaissjni.new_indexpq__swig_1(d, (ꈍᴗꈍ) m, nybits), :3 twue);
  }

  p-pubwic indexpq() {
    this(swigfaissjni.new_indexpq__swig_2(), (U ﹏ U) t-twue);
  }

  pubwic void twain(wong n-ny, UwU swigtype_p_fwoat x-x) {
    swigfaissjni.indexpq_twain(swigcptw, this, 😳😳😳 ny, swigtype_p_fwoat.getcptw(x));
  }

  pubwic void seawch(wong ny, XD swigtype_p_fwoat x-x, o.O wong k, swigtype_p_fwoat d-distances, (⑅˘꒳˘) wongvectow wabews) {
    s-swigfaissjni.indexpq_seawch(swigcptw, 😳😳😳 t-this, n-ny, nyaa~~ swigtype_p_fwoat.getcptw(x), rawr k, swigtype_p_fwoat.getcptw(distances), -.- swigtype_p_wong_wong.getcptw(wabews.data()), (✿oωo) wabews);
  }

  p-pubwic void sa_encode(wong ny, /(^•ω•^) swigtype_p_fwoat x, 🥺 swigtype_p_unsigned_chaw bytes) {
    swigfaissjni.indexpq_sa_encode(swigcptw, ʘwʘ t-this, UwU ny, swigtype_p_fwoat.getcptw(x), XD swigtype_p_unsigned_chaw.getcptw(bytes));
  }

  p-pubwic void sa_decode(wong n-ny, (✿oωo) swigtype_p_unsigned_chaw b-bytes, :3 swigtype_p_fwoat x) {
    swigfaissjni.indexpq_sa_decode(swigcptw, (///ˬ///✿) t-this, nyaa~~ ny, swigtype_p_unsigned_chaw.getcptw(bytes), >w< s-swigtype_p_fwoat.getcptw(x));
  }

  p-pubwic d-distancecomputew get_distance_computew() {
    wong cptw = swigfaissjni.indexpq_get_distance_computew(swigcptw, -.- t-this);
    wetuwn (cptw == 0) ? n-nyuww : nyew distancecomputew(cptw, (✿oωo) f-fawse);
  }

  p-pubwic void s-setdo_powysemous_twaining(boowean vawue) {
    swigfaissjni.indexpq_do_powysemous_twaining_set(swigcptw, (˘ω˘) this, vawue);
  }

  pubwic b-boowean getdo_powysemous_twaining() {
    wetuwn swigfaissjni.indexpq_do_powysemous_twaining_get(swigcptw, this);
  }

  pubwic void setpowysemous_twaining(powysemoustwaining vawue) {
    swigfaissjni.indexpq_powysemous_twaining_set(swigcptw, rawr t-this, powysemoustwaining.getcptw(vawue), OwO vawue);
  }

  pubwic powysemoustwaining getpowysemous_twaining() {
    w-wong cptw = s-swigfaissjni.indexpq_powysemous_twaining_get(swigcptw, ^•ﻌ•^ t-this);
    wetuwn (cptw == 0) ? n-nyuww : nyew powysemoustwaining(cptw, UwU f-fawse);
  }

  p-pubwic void setseawch_type(indexpq.seawch_type_t vawue) {
    swigfaissjni.indexpq_seawch_type_set(swigcptw, (˘ω˘) this, (///ˬ///✿) vawue.swigvawue());
  }

  pubwic indexpq.seawch_type_t getseawch_type() {
    w-wetuwn indexpq.seawch_type_t.swigtoenum(swigfaissjni.indexpq_seawch_type_get(swigcptw, σωσ this));
  }

  p-pubwic void setencode_signs(boowean v-vawue) {
    s-swigfaissjni.indexpq_encode_signs_set(swigcptw, this, /(^•ω•^) vawue);
  }

  pubwic b-boowean getencode_signs() {
    w-wetuwn swigfaissjni.indexpq_encode_signs_get(swigcptw, 😳 this);
  }

  p-pubwic v-void setpowysemous_ht(int vawue) {
    swigfaissjni.indexpq_powysemous_ht_set(swigcptw, 😳 this, vawue);
  }

  pubwic i-int getpowysemous_ht() {
    w-wetuwn swigfaissjni.indexpq_powysemous_ht_get(swigcptw, (⑅˘꒳˘) t-this);
  }

  pubwic v-void seawch_cowe_powysemous(wong n-ny, 😳😳😳 swigtype_p_fwoat x, 😳 wong k, s-swigtype_p_fwoat distances, XD wongvectow wabews) {
    swigfaissjni.indexpq_seawch_cowe_powysemous(swigcptw, mya this, n-ny, ^•ﻌ•^ swigtype_p_fwoat.getcptw(x), ʘwʘ k-k, ( ͡o ω ͡o ) swigtype_p_fwoat.getcptw(distances), mya swigtype_p_wong_wong.getcptw(wabews.data()), o.O wabews);
  }

  p-pubwic void h-hamming_distance_histogwam(wong ny, (✿oωo) swigtype_p_fwoat x, :3 wong nyb, 😳 swigtype_p_fwoat x-xb, (U ﹏ U) wongvectow dist_histogwam) {
    swigfaissjni.indexpq_hamming_distance_histogwam(swigcptw, mya this, ny, (U ᵕ U❁) swigtype_p_fwoat.getcptw(x), :3 n-nyb, swigtype_p_fwoat.getcptw(xb), mya swigtype_p_wong_wong.getcptw(dist_histogwam.data()), OwO d-dist_histogwam);
  }

  p-pubwic void hamming_distance_tabwe(wong ny, (ˆ ﻌ ˆ)♡ swigtype_p_fwoat x, ʘwʘ swigtype_p_int d-dis) {
    s-swigfaissjni.indexpq_hamming_distance_tabwe(swigcptw, o.O this, UwU ny, swigtype_p_fwoat.getcptw(x), rawr x3 swigtype_p_int.getcptw(dis));
  }

  p-pubwic finaw static cwass s-seawch_type_t {
    pubwic finaw static indexpq.seawch_type_t st_pq = nyew indexpq.seawch_type_t("st_pq");
    p-pubwic finaw static indexpq.seawch_type_t s-st_he = n-nyew indexpq.seawch_type_t("st_he");
    pubwic f-finaw static indexpq.seawch_type_t s-st_genewawized_he = n-nyew i-indexpq.seawch_type_t("st_genewawized_he");
    pubwic finaw static i-indexpq.seawch_type_t s-st_sdc = nyew indexpq.seawch_type_t("st_sdc");
    pubwic f-finaw static i-indexpq.seawch_type_t s-st_powysemous = nyew indexpq.seawch_type_t("st_powysemous");
    pubwic finaw s-static indexpq.seawch_type_t st_powysemous_genewawize = n-nyew i-indexpq.seawch_type_t("st_powysemous_genewawize");

    pubwic finaw int swigvawue() {
      wetuwn swigvawue;
    }

    p-pubwic s-stwing tostwing() {
      w-wetuwn s-swigname;
    }

    pubwic s-static seawch_type_t swigtoenum(int swigvawue) {
      if (swigvawue < swigvawues.wength && swigvawue >= 0 && s-swigvawues[swigvawue].swigvawue == swigvawue)
        w-wetuwn swigvawues[swigvawue];
      fow (int i-i = 0; i < swigvawues.wength; i++)
        if (swigvawues[i].swigvawue == s-swigvawue)
          wetuwn swigvawues[i];
      t-thwow n-nyew iwwegawawgumentexception("no e-enum " + seawch_type_t.cwass + " w-with vawue " + s-swigvawue);
    }

    pwivate seawch_type_t(stwing swigname) {
      this.swigname = swigname;
      this.swigvawue = s-swignext++;
    }

    p-pwivate seawch_type_t(stwing swigname, 🥺 i-int swigvawue) {
      this.swigname = s-swigname;
      this.swigvawue = swigvawue;
      swignext = swigvawue+1;
    }

    p-pwivate seawch_type_t(stwing s-swigname, :3 seawch_type_t swigenum) {
      t-this.swigname = swigname;
      this.swigvawue = s-swigenum.swigvawue;
      s-swignext = this.swigvawue+1;
    }

    pwivate s-static seawch_type_t[] s-swigvawues = { st_pq, (ꈍᴗꈍ) st_he, st_genewawized_he, st_sdc, 🥺 st_powysemous, (✿oωo) s-st_powysemous_genewawize };
    p-pwivate static i-int swignext = 0;
    p-pwivate f-finaw int swigvawue;
    pwivate f-finaw stwing s-swigname;
  }

}
