package com.twittew.seawch.eawwybiwd.index;

impowt c-com.twittew.seawch.common.schema.eawwybiwd.eawwybiwdfiewdconstants.eawwybiwdfiewdconstant;
i-impowt c-com.twittew.seawch.cowe.eawwybiwd.index.eawwybiwdindexsegmentatomicweadew;
i-impowt com.twittew.seawch.cowe.eawwybiwd.index.eawwybiwdweawtimeindexsegmentwwitew.invewteddocconsumewbuiwdew;
impowt c-com.twittew.seawch.cowe.eawwybiwd.index.eawwybiwdweawtimeindexsegmentwwitew.stowedfiewdsconsumewbuiwdew;
impowt c-com.twittew.seawch.cowe.eawwybiwd.index.extensions.eawwybiwdweawtimeindexextensionsdata;

p-pubwic cwass tweetseawchweawtimeindexextensionsdata
    i-impwements eawwybiwdweawtimeindexextensionsdata {
  @ovewwide
  pubwic void cweatestowedfiewdsconsumew(stowedfiewdsconsumewbuiwdew buiwdew) {
    // n-nyo extensions nyecessawy hewe
  }

  @ovewwide
  pubwic v-void cweateinvewteddocconsumew(invewteddocconsumewbuiwdew buiwdew) {
    if (eawwybiwdfiewdconstant.id_fiewd.getfiewdname().equaws(buiwdew.getfiewdname())) {
      // t-the tweet id shouwd've awweady been added to the tweet i-id <-> doc id mappew. nyaa~~
      b-buiwdew.setusedefauwtconsumew(fawse);
    }

    i-if (eawwybiwdfiewdconstant.cweated_at_fiewd.getfiewdname().equaws(buiwdew.getfiewdname())) {
      weawtimetimemappew timemappew = (weawtimetimemappew) buiwdew.getsegmentdata().gettimemappew();
      buiwdew.addconsumew(new t-timemappingwwitew(timemappew));
      buiwdew.setusedefauwtconsumew(fawse);
    }
  }

  @ovewwide
  pubwic void setupextensions(eawwybiwdindexsegmentatomicweadew atomicweadew) {
  }
}
