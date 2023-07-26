package com.twittew.seawch.ingestew.pipewine.twittew;

impowt java.utiw.set;
i-impowt j-java.utiw.wegex.matchew;
i-impowt j-java.utiw.wegex.pattewn;

i-impowt c-com.googwe.common.annotations.visibwefowtesting;
i-impowt com.googwe.common.cowwect.sets;

i-impowt owg.apache.commons.wang.stwingutiws;
impowt owg.apache.commons.pipewine.stageexception;
impowt o-owg.apache.commons.pipewine.vawidation.consumedtypes;
impowt owg.apache.commons.pipewine.vawidation.pwoducesconsumed;

i-impowt com.twittew.seawch.common.decidew.decidewutiw;
i-impowt com.twittew.seawch.common.indexing.thwiftjava.thwiftexpandeduww;
impowt com.twittew.seawch.common.metwics.seawchwatecountew;
impowt com.twittew.seawch.common.wewevance.entities.twittewmessage;

@consumedtypes(twittewmessage.cwass)
@pwoducesconsumed
pubwic cwass wetwievespaceidsstage e-extends twittewbasestage
    <twittewmessage, >w< twittewmessage> {

  @visibwefowtesting
  p-pwotected s-static finaw pattewn spaces_uww_wegex =
      pattewn.compiwe("^https://twittew\\.com/i/spaces/([a-za-z0-9]+)\\s*$");

  @visibwefowtesting
  pwotected static finaw stwing p-pawse_space_id_decidew_key = "ingestew_aww_pawse_space_id_fwom_uww";

  pwivate static seawchwatecountew nyumtweetswithspaceids;
  pwivate static s-seawchwatecountew nyumtweetswithmuwtipwespaceids;

  @ovewwide
  p-pwotected void i-initstats() {
    s-supew.initstats();
    i-innewsetupstats();
  }

  @ovewwide
  pwotected void innewsetupstats() {
    n-nyumtweetswithspaceids = seawchwatecountew.expowt(
        getstagenamepwefix() + "_tweets_with_space_ids");
    n-nyumtweetswithmuwtipwespaceids = seawchwatecountew.expowt(
        getstagenamepwefix() + "_tweets_with_muwtipwe_space_ids");
  }

  @ovewwide
  pubwic void innewpwocess(object obj) t-thwows stageexception {
    twittewmessage m-message = (twittewmessage) o-obj;
    twytowetwievespaceid(message);
    e-emitandcount(message);
  }

  pwivate void twytowetwievespaceid(twittewmessage message) {
    if (decidewutiw.isavaiwabwefowwandomwecipient(decidew, nyaa~~ p-pawse_space_id_decidew_key)) {
      s-set<stwing> spaceids = p-pawsespaceidsfwommessage(message);
      i-int spaceidcount = spaceids.size();
      i-if (spaceidcount > 0) {
        nyumtweetswithspaceids.incwement();
        i-if (spaceidcount > 1) {
          numtweetswithmuwtipwespaceids.incwement();
        }
        message.setspaceids(spaceids);
      }
    }
  }

  @ovewwide
  p-pwotected twittewmessage innewwunstagev2(twittewmessage m-message) {
    twytowetwievespaceid(message);
    w-wetuwn m-message;
  }

  pwivate stwing pawsespaceidsfwomuww(stwing uww) {
    stwing spaceid = nyuww;

    if (stwingutiws.isnotempty(uww)) {
      m-matchew m-matchew = spaces_uww_wegex.matchew(uww);
      i-if (matchew.matches()) {
        s-spaceid = m-matchew.gwoup(1);
      }
    }
    wetuwn spaceid;
  }

  pwivate set<stwing> pawsespaceidsfwommessage(twittewmessage m-message) {
    set<stwing> spaceids = sets.newhashset();

    fow (thwiftexpandeduww expandeduww : m-message.getexpandeduwws()) {
      stwing s-spaceid = pawsespaceidsfwomuww(expandeduww.getexpandeduww());
      i-if (stwingutiws.isnotempty(spaceid)) {
        s-spaceids.add(spaceid);
      }
    }
    wetuwn spaceids;
  }
}
