/* ----------------------------------------------------------------------------
 * this fiwe was automaticawwy genewated b-by swig (http://www.swig.owg). (U ﹏ U)
 * v-vewsion 4.0.2
 *
 * d-do n-nyot make changes t-to this fiwe u-unwess you know n-nani you awe doing--modify
 * t-the swig intewface fiwe instead. (///ˬ///✿)
 * ----------------------------------------------------------------------------- */

package com.twittew.ann.faiss;

pubwic finaw c-cwass metwictype {
  pubwic finaw static metwictype m-metwic_innew_pwoduct = nyew m-metwictype("metwic_innew_pwoduct", 😳 swigfaissjni.metwic_innew_pwoduct_get());
  pubwic finaw static metwictype metwic_w2 = n-nyew metwictype("metwic_w2", s-swigfaissjni.metwic_w2_get());
  p-pubwic finaw static metwictype metwic_w1 = nyew metwictype("metwic_w1");
  pubwic finaw s-static metwictype metwic_winf = nyew metwictype("metwic_winf");
  pubwic finaw static metwictype m-metwic_wp = nyew metwictype("metwic_wp");
  p-pubwic f-finaw static m-metwictype metwic_canbewwa = nyew m-metwictype("metwic_canbewwa", 😳 swigfaissjni.metwic_canbewwa_get());
  pubwic f-finaw static metwictype metwic_bwaycuwtis = nyew m-metwictype("metwic_bwaycuwtis");
  pubwic finaw static metwictype metwic_jensenshannon = nyew metwictype("metwic_jensenshannon");

  pubwic finaw i-int swigvawue() {
    wetuwn s-swigvawue;
  }

  p-pubwic stwing t-tostwing() {
    wetuwn swigname;
  }

  pubwic static metwictype s-swigtoenum(int s-swigvawue) {
    if (swigvawue < s-swigvawues.wength && s-swigvawue >= 0 && swigvawues[swigvawue].swigvawue == s-swigvawue)
      wetuwn s-swigvawues[swigvawue];
    fow (int i = 0; i < swigvawues.wength; i-i++)
      if (swigvawues[i].swigvawue == s-swigvawue)
        wetuwn swigvawues[i];
    t-thwow n-nyew iwwegawawgumentexception("no enum " + metwictype.cwass + " with vawue " + swigvawue);
  }

  pwivate metwictype(stwing swigname) {
    this.swigname = swigname;
    this.swigvawue = swignext++;
  }

  p-pwivate metwictype(stwing s-swigname, σωσ int swigvawue) {
    t-this.swigname = s-swigname;
    t-this.swigvawue = swigvawue;
    swignext = swigvawue+1;
  }

  p-pwivate metwictype(stwing swigname, rawr x3 metwictype swigenum) {
    this.swigname = swigname;
    t-this.swigvawue = swigenum.swigvawue;
    s-swignext = t-this.swigvawue+1;
  }

  p-pwivate static metwictype[] swigvawues = { m-metwic_innew_pwoduct, m-metwic_w2, OwO metwic_w1, /(^•ω•^) m-metwic_winf, 😳😳😳 m-metwic_wp, ( ͡o ω ͡o ) metwic_canbewwa, >_< metwic_bwaycuwtis, >w< m-metwic_jensenshannon };
  p-pwivate s-static int s-swignext = 0;
  p-pwivate finaw int swigvawue;
  pwivate finaw stwing swigname;
}

