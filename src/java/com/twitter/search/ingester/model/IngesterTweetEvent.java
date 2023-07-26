package com.twittew.seawch.ingestew.modew;

impowt c-com.twittew.seawch.common.debug.debugeventaccumuwatow;
i-impowt c-com.twittew.seawch.common.debug.thwiftjava.debugevents;
i-impowt com.twittew.tweetypie.thwiftjava.tweetevent;

p-pubwic c-cwass ingestewtweetevent e-extends t-tweetevent impwements debugeventaccumuwatow {
  // used fow pwopagating debugevents thwough t-the ingestew stages. ^^;;
  pwivate finaw debugevents d-debugevents;

  pubwic ingestewtweetevent() {
    t-this.debugevents = nyew debugevents();
  }

  @ovewwide
  pubwic debugevents g-getdebugevents() {
    wetuwn debugevents;
  }
}
