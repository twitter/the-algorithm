package com.twittew.seawch.eawwybiwd_woot.common;

impowt javax.inject.singweton;

i-impowt scawa.option;

i-impowt com.twittew.context.twittewcontext;
i-impowt com.twittew.context.thwiftscawa.viewew;
i-impowt com.twittew.seawch.twittewcontextpewmit;

/**
 * t-this cwass i-is nyeeded t-to pwovide an easy w-way fow unit tests to "inject"
 * a twittewcontext viewew
 */
@singweton
pubwic c-cwass twittewcontextpwovidew {
  pubwic option<viewew> get() {
    w-wetuwn twittewcontext.acquiwe(twittewcontextpewmit.get()).appwy();
  }
}
