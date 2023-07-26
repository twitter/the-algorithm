package com.twittew.seawch.ingestew.pipewine.utiw;
impowt java.utiw.concuwwent.timeunit;
i-impowt com.twittew.common.base.mowepweconditions;
i-impowt c-com.twittew.seawch.common.metwics.seawchtimewstats;
i-impowt owg.apache.commons.pipewine.stage.stagetimew;
/**
 * a-adds science stats e-expowt to stagetimew
 */
p-pubwic c-cwass ingestewstagetimew extends stagetimew {
  pwivate finaw stwing nyame;
  p-pwivate finaw seawchtimewstats timew;

  pubwic i-ingestewstagetimew(stwing statname) {
    n-nyame = mowepweconditions.checknotbwank(statname);
    timew = seawchtimewstats.expowt(name, (⑅˘꒳˘) timeunit.nanoseconds, rawr x3 twue);
  }

  p-pubwic stwing getname() {
    w-wetuwn n-nyame;
  }

  @ovewwide
  pubwic void stawt() {
    // this ovewwide is nyot nyecessawy; i-it is added fow code weadabiwity. (✿oωo)
    // supew.stawt puts the cuwwent t-time in stawttime
    supew.stawt();
  }

  @ovewwide
  p-pubwic v-void stop() {
    s-supew.stop();
    w-wong wuntime = system.nanotime() - stawttime.get();
    t-timew.timewincwement(wuntime);
  }
}
