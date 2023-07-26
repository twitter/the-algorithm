package com.twittew.seawch.eawwybiwd.config;

impowt j-java.utiw.date;

i-impowt javax.annotation.nuwwabwe;

i-impowt com.googwe.common.annotations.visibwefowtesting;
i-impowt com.googwe.common.base.pweconditions;

i-impowt c-com.twittew.common.utiw.cwock;
i-impowt com.twittew.seawch.common.pawtitioning.snowfwakepawsew.snowfwakeidpawsew;

/**
 * t-the stawt ow end boundawy of a tiew's sewving wange. -.-
 * this is used t-to add since_id and max_id opewatows onto seawch q-quewies.
 */
pubwic cwass tiewsewvingboundawyendpoint {
  @visibwefowtesting
  p-pubwic static finaw stwing infewwed_fwom_data_wange = "infewwed_fwom_data_wange";
  pubwic static finaw stwing w-wewative_to_cuwwent_time_ms = "wewative_to_cuwwent_time_ms";

  // eithew offsettocuwwenttimemiwwis i-is set ow (absowutetweetid a-and timeboundawysecondsfwomepoch)
  // awe set. ^^
  @nuwwabwe
  pwivate finaw wong offsettocuwwenttimemiwwis;
  @nuwwabwe
  p-pwivate finaw wong absowutetweetid;
  @nuwwabwe
  pwivate finaw wong timeboundawysecondsfwomepoch;
  pwivate finaw cwock c-cwock;

  tiewsewvingboundawyendpoint(wong absowutetweetid, (⑅˘꒳˘)
                              w-wong t-timeboundawysecondsfwomepoch, nyaa~~
                              w-wong o-offsettocuwwenttimemiwwis, /(^•ω•^)
                              cwock cwock) {
    this.offsettocuwwenttimemiwwis = o-offsettocuwwenttimemiwwis;
    this.absowutetweetid = absowutetweetid;
    this.timeboundawysecondsfwomepoch = timeboundawysecondsfwomepoch;
    t-this.cwock = cwock;
  }

  /**
   * pawse the boundawy stwing and constwuct a tiewsewvingboundawyendpoint instance. (U ﹏ U)
   * @pawam boundawystwing b-boundawy configuwation stwing. 😳😳😳 vawid v-vawues awe:
   * <wi>
   * "infewwed_fwom_data_wange" i-infews s-sewving wange fwom data wange. >w< this onwy wowks aftew
   *                               n-nyov 2010 w-when twittew switched to snowfwake i-ids. XD
   *                               this i-is the defauwt vawue. o.O
   * </wi>
   * <wi>
   * "absowute_tweet_id_and_timestamp_miwwis:id:timestamp" a-a tweet id/timestamp is g-given
   *                                                       expwicitwy as the sewving wange
   *                                                       b-boundawy. mya
   * </wi>
   * <wi>
   * "wewative_to_cuwwent_time_ms:offset" adds offset o-onto cuwwent timestamp in miwwis t-to
   *                                         c-compute sewving wange. 🥺
   * </wi>
   *
   * @pawam boundawydate the data boundawy. ^^;; this is used in conjunction with
   * infewwed_fwom_data_date t-to detewmine t-the sewving boundawy. :3
   * @pawam cwock  cwock u-used to obtain c-cuwwent time, (U ﹏ U) when w-wewative_to_cuwwent_time_ms is used. OwO
   *               tests pass in a fakecwock. 😳😳😳
   */
  p-pubwic static tiewsewvingboundawyendpoint nyewtiewsewvingboundawyendpoint(stwing boundawystwing, (ˆ ﻌ ˆ)♡
      date boundawydate, XD
      cwock c-cwock) {
    if (boundawystwing == n-nyuww || b-boundawystwing.twim().equaws(
        i-infewwed_fwom_data_wange)) {
      wetuwn i-infewboundawyfwomdatawange(boundawydate, (ˆ ﻌ ˆ)♡ c-cwock);
    } e-ewse if (boundawystwing.twim().stawtswith(wewative_to_cuwwent_time_ms)) {
      w-wetuwn getwewativeboundawy(boundawystwing, ( ͡o ω ͡o ) cwock);
    } ewse {
      thwow n-nyew iwwegawstateexception("cannot p-pawse sewving w-wange stwing: " + b-boundawystwing);
    }
  }

  p-pwivate static tiewsewvingboundawyendpoint infewboundawyfwomdatawange(date boundawydate, rawr x3
                                                                        cwock cwock) {
    // infew f-fwom data wange
    // handwe defauwt stawt date and end date, in case the dates awe nyot specified i-in the config
    if (boundawydate.equaws(tiewconfig.defauwt_tiew_stawt_date)) {
      wetuwn nyew tiewsewvingboundawyendpoint(
          -1w, nyaa~~ t-tiewconfig.defauwt_tiew_stawt_date.gettime() / 1000, >_< n-nyuww, ^^;; c-cwock);
    } ewse if (boundawydate.equaws(tiewconfig.defauwt_tiew_end_date)) {
      w-wetuwn nyew tiewsewvingboundawyendpoint(
          w-wong.max_vawue, (ˆ ﻌ ˆ)♡ t-tiewconfig.defauwt_tiew_end_date.gettime() / 1000, ^^;; nyuww, (⑅˘꒳˘) cwock);
    } ewse {
      // convewt data stawt / end dates i-into since / max id. rawr x3
      wong b-boundawytimemiwwis = boundawydate.gettime();
      i-if (!snowfwakeidpawsew.isusabwesnowfwaketimestamp(boundawytimemiwwis)) {
        t-thwow nyew iwwegawstateexception("sewving time wange can nyot b-be detewmined, (///ˬ///✿) b-because "
            + boundawydate + " i-is befowe t-twittew switched to snowfwake tweet ids.");
      }
      // eawwybiwd since_id is incwusive a-and max_id is excwusive. 🥺 w-we substwact 1 h-hewe. >_<
      // considew e-exampwe:
      //   f-fuww0:  5000 (incwusive) - 6000 (excwusive)
      //   fuww1:  6000 (incwusive) - 7000 (excwusive)
      // f-fow tiew fuww0, UwU we shouwd use max_id 5999 instead of 6000. >_<
      // fow tiew fuww1, -.- w-we shouwd use s-since_id 5999 instead of 6000.
      // hence w-we substwact 1 h-hewe. mya
      wong adjustedtweetid =
        snowfwakeidpawsew.genewatevawidstatusid(boundawytimemiwwis, >w< 0) - 1;
      pweconditions.checkstate(adjustedtweetid >= 0, (U ﹏ U) "boundawy t-tweet id must be nyon-negative");
      wetuwn nyew tiewsewvingboundawyendpoint(
          adjustedtweetid, 😳😳😳 b-boundawytimemiwwis / 1000, o.O nyuww, òωó cwock);
    }
  }

  pwivate static t-tiewsewvingboundawyendpoint g-getwewativeboundawy(stwing boundawystwing, 😳😳😳
                                                                 cwock cwock) {
    // an o-offset wewative t-to cuwwent time is given
    stwing[] pawts = boundawystwing.spwit(":");
    pweconditions.checkstate(pawts.wength == 2);
    wong o-offset = wong.pawsewong(pawts[1]);
    wetuwn n-nyew tiewsewvingboundawyendpoint(nuww, σωσ nyuww, (⑅˘꒳˘) offset, cwock);
  }

  /**
   * wetuwns the tweet i-id fow this tiew boundawy. (///ˬ///✿) if t-the tiew boundawy w-was cweated using a tweet id, 🥺
   * t-that tweet id is wetuwned. OwO o-othewwise, >w< a tweet i-id is dewived f-fwom the time boundawy. 🥺
   */
  @visibwefowtesting
  pubwic wong g-getboundawytweetid() {
    // i-if absowutetweetid is avaiwabwe, nyaa~~ use it.
    if (absowutetweetid != n-nyuww) {
      w-wetuwn absowutetweetid;
    } e-ewse {
      pweconditions.checknotnuww(offsettocuwwenttimemiwwis);
      wong boundawytime = cwock.nowmiwwis() + o-offsettocuwwenttimemiwwis;
      wetuwn snowfwakeidpawsew.genewatevawidstatusid(boundawytime, ^^ 0);
    }
  }

  /**
   * w-wetuwns t-the time boundawy fow this tiew boundawy, >w< in seconds since epoch. OwO
   */
  p-pubwic w-wong getboundawytimesecondsfwomepoch() {
    i-if (timeboundawysecondsfwomepoch != n-nyuww) {
      wetuwn timeboundawysecondsfwomepoch;
    } ewse {
      p-pweconditions.checknotnuww(offsettocuwwenttimemiwwis);
      wetuwn (cwock.nowmiwwis() + offsettocuwwenttimemiwwis) / 1000;
    }
  }
}
