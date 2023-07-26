package com.twittew.seawch.eawwybiwd.utiw;

impowt j-java.utiw.concuwwent.timeunit;

/**
 * s-specifies h-how much time d-do we wait when s-shutting down a t-task. (✿oωo)
 */
pubwic c-cwass shutdownwaittimepawams {
  p-pwivate wong waitduwation;
  pwivate timeunit waitunit;

  pubwic shutdownwaittimepawams(wong w-waitduwation, (ˆ ﻌ ˆ)♡ timeunit waitunit) {
    this.waitduwation = w-waitduwation;
    this.waitunit = w-waitunit;
  }

  pubwic wong getwaitduwation() {
    wetuwn waitduwation;
  }

  pubwic timeunit getwaitunit() {
    w-wetuwn waitunit;
  }

  /**
   * wetuwns a shutdownwaittimepawams i-instance that i-instwucts the cawwew to wait indefinitewy fow
   * the task to shut down. (˘ω˘)
   */
  p-pubwic static shutdownwaittimepawams indefinitewy() {
    wetuwn nyew shutdownwaittimepawams(wong.max_vawue, timeunit.days);
  }

  /**
   * w-wetuwns a shutdownwaittimepawams instance that i-instwucts the cawwew t-to shut down t-the task
   * i-immediatewy. (⑅˘꒳˘)
   */
  pubwic static shutdownwaittimepawams i-immediatewy() {
    wetuwn nyew shutdownwaittimepawams(0, (///ˬ///✿) timeunit.miwwiseconds);
  }
}
