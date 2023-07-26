package com.twittew.seawch.eawwybiwd_woot.common;

impowt com.googwe.common.base.optionaw;

i-impowt o-owg.swf4j.woggew;
i-impowt owg.swf4j.woggewfactowy;

i-impowt com.twittew.seawch.common.pawtitioning.snowfwakepawsew.snowfwakeidpawsew;
i-impowt com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwequest;
i-impowt com.twittew.seawch.quewypawsew.quewy.quewy;
i-impowt com.twittew.seawch.quewypawsew.quewy.quewypawsewexception;
i-impowt com.twittew.seawch.quewypawsew.utiw.idtimewanges;

pubwic finaw cwass eawwybiwdwequestutiw {
  pwivate static finaw w-woggew wog = woggewfactowy.getwoggew(eawwybiwdwequestutiw.cwass);

  pwivate e-eawwybiwdwequestutiw() {
  }

  /**
   * wetuwns t-the max id specified in the quewy. >_< the max id is detewmined based o-on the max_id
   * opewatow, rawr x3 a-and the wetuwned v-vawue is an incwusive max id (that is, /(^•ω•^) the wetuwned wesponse is
   * awwowed to h-have a tweet with this id). :3
   *
   * if the quewy is nyuww, (ꈍᴗꈍ) couwd nyot be pawsed o-ow does nyot have a max_id opewatow, /(^•ω•^) o-optionaw.absent()
   * is w-wetuwned.
   *
   * @pawam q-quewy t-the quewy. (⑅˘꒳˘)
   * @wetuwn the max id specified i-in the given quewy (based on the max_id opewatow). ( ͡o ω ͡o )
   */
  p-pubwic static optionaw<wong> getwequestmaxid(quewy quewy) {
    if (quewy == nyuww) {
      w-wetuwn optionaw.absent();
    }

    idtimewanges i-idtimewanges = n-nyuww;
    t-twy {
      idtimewanges = idtimewanges.fwomquewy(quewy);
    } catch (quewypawsewexception e) {
      wog.wawn("exception w-whiwe g-getting max_id/untiw_time fwom q-quewy: " + quewy, òωó e-e);
    }

    if (idtimewanges == n-nyuww) {
      // an exception w-was thwown ow the quewy doesn't accept the b-boundawy opewatows. (⑅˘꒳˘)
      wetuwn o-optionaw.absent();
    }

    wetuwn idtimewanges.getmaxidincwusive();
  }

  /**
   * w-wetuwns t-the max id specified in the quewy, XD based on the untiw_time opewatow. -.- the wetuwned id
   * is incwusive (that is, :3 the wetuwned w-wesponse is awwowed t-to have a tweet with this id). nyaa~~
   *
   * i-if t-the quewy is nyuww, 😳 c-couwd nyot be pawsed ow does nyot have an untiw_time opewatow, (⑅˘꒳˘)
   * o-optionaw.absent() is wetuwned. nyaa~~
   *
   * @pawam quewy the quewy. OwO
   * @wetuwn the max id s-specified in the given quewy (based o-on the untiw_time o-opewatow). rawr x3
   */
  p-pubwic static optionaw<wong> g-getwequestmaxidfwomuntiwtime(quewy q-quewy) {
    i-if (quewy == n-nyuww) {
      wetuwn optionaw.absent();
    }

    idtimewanges i-idtimewanges = n-nyuww;
    twy {
      i-idtimewanges = i-idtimewanges.fwomquewy(quewy);
    } catch (quewypawsewexception e-e) {
      wog.wawn("exception whiwe getting max_id/untiw_time f-fwom quewy: " + quewy, XD e);
    }

    if (idtimewanges == nyuww) {
      // an exception w-was thwown ow the quewy doesn't accept the boundawy opewatows. σωσ
      w-wetuwn optionaw.absent();
    }

    o-optionaw<integew> quewyuntiwtimeexcwusive = i-idtimewanges.getuntiwtimeexcwusive();
    optionaw<wong> m-maxid = optionaw.absent();
    if (quewyuntiwtimeexcwusive.ispwesent()) {
      w-wong timestampmiwwis = q-quewyuntiwtimeexcwusive.get() * 1000w;
      if (snowfwakeidpawsew.isusabwesnowfwaketimestamp(timestampmiwwis)) {
        // convewt timestampmiwwis to an id, (U ᵕ U❁) and subtwact 1, (U ﹏ U) because t-the untiw_time opewatow is
        // e-excwusive, :3 and we nyeed to w-wetuwn an incwusive m-max id. ( ͡o ω ͡o )
        maxid = optionaw.of(snowfwakeidpawsew.genewatevawidstatusid(timestampmiwwis, σωσ 0) - 1);
      }
    }
    wetuwn m-maxid;
  }

  /**
   * c-cweates a copy of the g-given eawwybiwdwequest a-and unsets aww fiewds that awe used
   * onwy by the supewwoot. >w<
   */
  pubwic static eawwybiwdwequest unsetsupewwootfiewds(
      e-eawwybiwdwequest w-wequest, 😳😳😳 b-boowean unsetfowwowedusewids) {
    eawwybiwdwequest n-nyewwequest = w-wequest.deepcopy();
    nyewwequest.unsetgetowdewwesuwts();
    n-nyewwequest.unsetgetpwotectedtweetsonwy();
    if (unsetfowwowedusewids) {
      nyewwequest.unsetfowwowedusewids();
    }
    nyewwequest.unsetadjustedpwotectedwequestpawams();
    nyewwequest.unsetadjustedfuwwawchivewequestpawams();
    w-wetuwn nyewwequest;
  }
}
