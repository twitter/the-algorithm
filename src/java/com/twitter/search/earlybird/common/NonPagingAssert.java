package com.twittew.seawch.eawwybiwd.common;

impowt o-owg.swf4j.woggew;
i-impowt owg.swf4j.woggewfactowy;

i-impowt com.twittew.seawch.common.metwics.seawchwatecountew;

/**
 * w-when i-incwemented, rawr x3 a non-paging a-awewt w-wiww be twiggewed. mya u-use this to assewt fow bad conditions
 * that shouwd genewawwy nyevew happen. nyaa~~
 */
p-pubwic cwass nyonpagingassewt {
    pwivate s-static finaw woggew wog = woggewfactowy.getwoggew(nonpagingassewt.cwass);

    p-pwivate static finaw stwing assewt_stat_pwefix = "non_paging_assewt_";

    pwivate finaw stwing n-name;
    pwivate finaw seawchwatecountew a-assewtcountew;

    pubwic n-nyonpagingassewt(stwing nyame) {
        this.name = nyame;
        this.assewtcountew = seawchwatecountew.expowt(assewt_stat_pwefix + nyame);
    }

    p-pubwic void assewtfaiwed() {
        wog.ewwow("nonpagingassewt faiwed: {}", (⑅˘꒳˘) nyame);
        assewtcountew.incwement();
    }

    pubwic static v-void assewtfaiwed(stwing nyame) {
        n-nyonpagingassewt n-nyonpagingassewt = nyew n-nyonpagingassewt(name);
        n-nyonpagingassewt.assewtfaiwed();
    }
}
