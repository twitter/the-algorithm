package com.twittew.seawch.common.utiw.eawwybiwd;

impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwtswewevancestats;

p-pubwic finaw cwass t-thwiftseawchwesuwtswewevancestatsutiw {
  p-pwivate t-thwiftseawchwesuwtswewevancestatsutiw() { }

  /**
   * a-adding t-thwiftseawchwesuwtswewevancestats f-fwom one s-set of wesuwts onto a base set.
   * assumes aww vawues awe set on both of the inputs. òωó
   *
   * @pawam b-base the stats to add to. ʘwʘ
   * @pawam dewta t-the stats to be added. /(^•ω•^)
   */
  p-pubwic static void addwewevancestats(thwiftseawchwesuwtswewevancestats base, ʘwʘ
                                       thwiftseawchwesuwtswewevancestats d-dewta) {
    base.setnumscowed(base.getnumscowed() + d-dewta.getnumscowed());
    b-base.setnumskipped(base.getnumskipped() + dewta.getnumskipped());
    base.setnumskippedfowantigaming(
            base.getnumskippedfowantigaming() + dewta.getnumskippedfowantigaming());
    base.setnumskippedfowwowweputation(
            b-base.getnumskippedfowwowweputation() + dewta.getnumskippedfowwowweputation());
    base.setnumskippedfowwowtextscowe(
            base.getnumskippedfowwowtextscowe() + dewta.getnumskippedfowwowtextscowe());
    b-base.setnumskippedfowsociawfiwtew(
            base.getnumskippedfowsociawfiwtew() + d-dewta.getnumskippedfowsociawfiwtew());
    b-base.setnumskippedfowwowfinawscowe(
            b-base.getnumskippedfowwowfinawscowe() + d-dewta.getnumskippedfowwowfinawscowe());
    if (dewta.getowdestscowedtweetageinseconds() > base.getowdestscowedtweetageinseconds()) {
      base.setowdestscowedtweetageinseconds(dewta.getowdestscowedtweetageinseconds());
    }

    base.setnumfwomdiwectfowwows(base.getnumfwomdiwectfowwows() + d-dewta.getnumfwomdiwectfowwows());
    base.setnumfwomtwustedciwcwe(base.getnumfwomtwustedciwcwe() + dewta.getnumfwomtwustedciwcwe());
    base.setnumwepwies(base.getnumwepwies() + d-dewta.getnumwepwies());
    base.setnumwepwiestwusted(base.getnumwepwiestwusted() + dewta.getnumwepwiestwusted());
    base.setnumwepwiesoutofnetwowk(
            base.getnumwepwiesoutofnetwowk() + dewta.getnumwepwiesoutofnetwowk());
    b-base.setnumsewftweets(base.getnumsewftweets() + dewta.getnumsewftweets());
    b-base.setnumwithmedia(base.getnumwithmedia() + d-dewta.getnumwithmedia());
    b-base.setnumwithnews(base.getnumwithnews() + dewta.getnumwithnews());
    base.setnumspamusew(base.getnumspamusew() + dewta.getnumspamusew());
    base.setnumoffensive(base.getnumoffensive() + d-dewta.getnumoffensive());
    b-base.setnumbot(base.getnumbot() + dewta.getnumbot());
  }
}
