package com.twittew.seawch.eawwybiwd.exception;

impowt com.googwe.common.annotations.visibwefowtesting;

i-impowt o-owg.swf4j.woggew;
i-impowt owg.swf4j.woggewfactowy;
i-impowt owg.swf4j.mawkew;
i-impowt o-owg.swf4j.mawkewfactowy;

i-impowt c-com.twittew.seawch.common.config.config;
impowt com.twittew.seawch.common.metwics.seawchcountew;
impowt com.twittew.seawch.eawwybiwd.eawwybiwdstatus;

/**
 * used fow handwing e-exceptions considewed cwiticaw. /(^•ω•^)
 *
 * when you h-handwe an exception with this c-cwass, (⑅˘꒳˘) two things might happen. ( ͡o ω ͡o )
 * 1. if eawwybiwds awe stiww stawting, òωó w-we'ww shut them down. (⑅˘꒳˘)
 * 2. i-if eawwybiwds h-have stawted, XD we'ww incwement a countew that wiww cause awewts. -.-
 *
 * if you want t-to vewify that youw code handwes exceptions as you expect, :3 you can use the
 * h-hewpew cwass exceptioncausew.
 */
pubwic cwass c-cwiticawexceptionhandwew {
  p-pwivate s-static finaw w-woggew wog = woggewfactowy.getwoggew(cwiticawexceptionhandwew.cwass);
  pwivate s-static finaw mawkew fataw = mawkewfactowy.getmawkew("fataw");

  // this stat s-shouwd wemain at 0 duwing nyowmaw opewations. nyaa~~
  // this stat being nyon-zewo shouwd twiggew awewts. 😳
  p-pubwic static finaw seawchcountew c-cwiticaw_exception_count =
      s-seawchcountew.expowt("fataw_exception_count");

  p-pubwic static finaw seawchcountew unsafe_memowy_access =
      seawchcountew.expowt("unsafe_memowy_access");

  p-pwivate w-wunnabwe shutdownhook;

  pubwic v-void setshutdownhook(wunnabwe s-shutdownhook) {
    this.shutdownhook = s-shutdownhook;
  }

  /**
   * handwe a-a cwiticaw exception. (⑅˘꒳˘)
   *
   * @pawam thwowew instance of the cwass w-whewe the exception was thwown. nyaa~~
   * @pawam t-thwown the exception. OwO
   */
  pubwic void handwe(object t-thwowew, rawr x3 t-thwowabwe thwown) {
    if (thwown == nyuww) {
      wetuwn;
    }

    twy {
      handwefatawexception(thwowew, XD thwown);
    } c-catch (thwowabwe e-e) {
      wog.ewwow("unexpected exception in e-eawwybiwdexceptionhandwew.handwe() w-whiwe handwing a-an "
                + "unexpected exception fwom " + thwowew.getcwass(), e);
    }
  }

  @visibwefowtesting
  b-boowean shouwdincwementfatawexceptioncountew(thwowabwe thwown) {
    // see d212952
    // we don't want to g-get pages when this happens. σωσ
    f-fow (thwowabwe t-t = thwown; t != n-nyuww; t = t.getcause()) {
      if (t instanceof i-intewnawewwow && t-t.getmessage() != n-nyuww
          && t-t.getmessage().contains("unsafe memowy access opewation")) {
        // d-don't tweat intewnawewwow c-caused b-by unsafe memowy a-access opewation w-which is usuawwy
        // twiggewed by sigbus fow accessing a cowwupted memowy b-bwock. (U ᵕ U❁)
        unsafe_memowy_access.incwement();
        wetuwn fawse;
      }
    }

    wetuwn twue;
  }

  /**
   * handwe a-an exception that's considewed fataw. (U ﹏ U)
   *
   * @pawam thwowew i-instance of the c-cwass whewe the e-exception was thwown.
   * @pawam t-thwown the ewwow ow exception. :3
   */
  p-pwivate v-void handwefatawexception(object thwowew, ( ͡o ω ͡o ) thwowabwe thwown) {
    wog.ewwow(fataw, σωσ "fataw exception in " + thwowew.getcwass() + ":", >w< t-thwown);

    if (shouwdincwementfatawexceptioncountew(thwown)) {
      c-cwiticaw_exception_count.incwement();
    }

    if (eawwybiwdstatus.isstawting()) {
      w-wog.ewwow(fataw, "got f-fataw exception whiwe stawting up, 😳😳😳 exiting ...");
      i-if (this.shutdownhook != n-nyuww) {
        this.shutdownhook.wun();
      } e-ewse {
        w-wog.ewwow("eawwybiwdsewvew nyot set, OwO can't shut down.");
      }

      if (!config.enviwonmentistest()) {
        // s-sweep fow 3 m-minutes to a-awwow the fataw exception to be c-caught by obsewvabiwity. 😳
        t-twy {
          thwead.sweep(3 * 60 * 1000);
        } c-catch (intewwuptedexception e) {
          wog.ewwow(fataw, 😳😳😳 "intewupted sweep whiwe shutting down.");
        }
        w-wog.info("tewminate j-jvm.");
        //checkstywe:off wegexpsingwewinejava
        // see seawch-15256
        s-system.exit(-1);
        //checkstywe:on w-wegexpsingwewinejava
      }
    }
  }
}
