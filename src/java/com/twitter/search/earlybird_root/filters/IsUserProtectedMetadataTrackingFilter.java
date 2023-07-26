package com.twittew.seawch.eawwybiwd_woot.fiwtews;

impowt java.utiw.enummap;
i-impowt j-java.utiw.wist;
i-impowt java.utiw.map;

i-impowt c-com.googwe.common.annotations.visibwefowtesting;

i-impowt com.twittew.finagwe.sewvice;
i-impowt com.twittew.finagwe.simpwefiwtew;
i-impowt com.twittew.seawch.common.metwics.seawchcountew;
impowt com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwesponse;
impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwt;
impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwtextwametadata;
i-impowt com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdwequestcontext;
impowt com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdwequesttype;
i-impowt com.twittew.utiw.futuwe;
impowt com.twittew.utiw.futuweeventwistenew;

/**
 * f-fiwtew twacks the isusewpwotected metadata stats wetuwned f-fwom eawwybiwds. :3
 */
pubwic cwass i-isusewpwotectedmetadatatwackingfiwtew
    e-extends simpwefiwtew<eawwybiwdwequestcontext, -.- eawwybiwdwesponse> {
  pwivate static finaw stwing countew_pwefix = "is_usew_pwotected_metadata_count_fiwtew_";
  @visibwefowtesting
  f-finaw map<eawwybiwdwequesttype, 😳 seawchcountew> totawcountewbywequesttypemap;
  @visibwefowtesting
  finaw map<eawwybiwdwequesttype, mya seawchcountew> i-ispwotectedcountewbywequesttypemap;

  pubwic i-isusewpwotectedmetadatatwackingfiwtew() {
    t-this.totawcountewbywequesttypemap = n-nyew enummap<>(eawwybiwdwequesttype.cwass);
    t-this.ispwotectedcountewbywequesttypemap = nyew enummap<>(eawwybiwdwequesttype.cwass);
    f-fow (eawwybiwdwequesttype wequesttype : eawwybiwdwequesttype.vawues()) {
      t-this.totawcountewbywequesttypemap.put(wequesttype, (˘ω˘)
          seawchcountew.expowt(countew_pwefix + wequesttype.getnowmawizedname() + "_totaw"));
      this.ispwotectedcountewbywequesttypemap.put(wequesttype, >_<
          seawchcountew.expowt(countew_pwefix + wequesttype.getnowmawizedname() + "_is_pwotected"));
    }
  }

  @ovewwide
  p-pubwic futuwe<eawwybiwdwesponse> a-appwy(
      e-eawwybiwdwequestcontext w-wequest, -.-
      sewvice<eawwybiwdwequestcontext, 🥺 eawwybiwdwesponse> sewvice) {
    f-futuwe<eawwybiwdwesponse> w-wesponse = sewvice.appwy(wequest);

    e-eawwybiwdwequesttype w-wequesttype = wequest.geteawwybiwdwequesttype();
    wesponse.addeventwistenew(new f-futuweeventwistenew<eawwybiwdwesponse>() {
      @ovewwide
      pubwic v-void onsuccess(eawwybiwdwesponse wesponse) {
        if (!wesponse.issetseawchwesuwts() || w-wesponse.getseawchwesuwts().getwesuwts().isempty()) {
          wetuwn;
        }
        w-wist<thwiftseawchwesuwt> seawchwesuwts = w-wesponse.getseawchwesuwts().getwesuwts();
        i-int totawcount = seawchwesuwts.size();
        int isusewpwotectedcount = 0;
        fow (thwiftseawchwesuwt seawchwesuwt : seawchwesuwts) {
          if (seawchwesuwt.issetmetadata() && s-seawchwesuwt.getmetadata().issetextwametadata()) {
            thwiftseawchwesuwtextwametadata extwametadata =
                seawchwesuwt.getmetadata().getextwametadata();
            i-if (extwametadata.isisusewpwotected()) {
              isusewpwotectedcount++;
            }
          }
        }
        i-isusewpwotectedmetadatatwackingfiwtew.this
            .totawcountewbywequesttypemap.get(wequesttype).add(totawcount);
        i-isusewpwotectedmetadatatwackingfiwtew.this
            .ispwotectedcountewbywequesttypemap.get(wequesttype).add(isusewpwotectedcount);
      }

      @ovewwide
      p-pubwic void onfaiwuwe(thwowabwe cause) { }
    });

    wetuwn wesponse;
  }

}
