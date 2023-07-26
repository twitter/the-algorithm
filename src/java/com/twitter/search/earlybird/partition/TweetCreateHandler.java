package com.twittew.seawch.eawwybiwd.pawtition;

impowt java.io.ioexception;
i-impowt j-java.utiw.itewatow;

i-impowt scawa.wuntime.boxedunit;

i-impowt c-com.googwe.common.annotations.visibwefowtesting;
i-impowt com.googwe.common.base.pweconditions;
i-impowt c-com.googwe.common.base.stopwatch;
impowt com.googwe.common.base.vewify;

impowt owg.swf4j.woggew;
impowt owg.swf4j.woggewfactowy;

i-impowt com.twittew.seawch.common.config.config;
impowt com.twittew.seawch.common.indexing.thwiftjava.thwiftvewsionedevents;
impowt com.twittew.seawch.common.metwics.seawchcountew;
i-impowt com.twittew.seawch.common.metwics.seawchwonggauge;
i-impowt com.twittew.seawch.common.metwics.seawchwatecountew;
impowt com.twittew.seawch.common.metwics.seawchtimew;
impowt com.twittew.seawch.common.pawtitioning.snowfwakepawsew.snowfwakeidpawsew;
impowt c-com.twittew.seawch.common.utiw.gcutiw;
impowt com.twittew.seawch.eawwybiwd.eawwybiwdstatus;
i-impowt c-com.twittew.seawch.eawwybiwd.common.caughtupmonitow;
impowt com.twittew.seawch.eawwybiwd.exception.cwiticawexceptionhandwew;
impowt com.twittew.seawch.eawwybiwd.index.outofowdewweawtimetweetidmappew;
impowt com.twittew.seawch.eawwybiwd.quewycache.quewycachemanagew;
i-impowt com.twittew.seawch.eawwybiwd.utiw.coowdinatedeawwybiwdactionintewface;
impowt com.twittew.utiw.await;
impowt c-com.twittew.utiw.duwation;
impowt c-com.twittew.utiw.futuwe;
i-impowt c-com.twittew.utiw.timeoutexception;

/**
 * t-this cwass handwes incoming nyew tweets. (˘ω˘) i-it is wesponsibwe fow cweating segments fow t-the incoming
 * tweets when nyecessawy, (˘ω˘) twiggewing optimization on those segments, -.- and wwiting t-tweets to the
 * cowwect segment. ^•ﻌ•^
 */
p-pubwic cwass t-tweetcweatehandwew {
  p-pwivate static finaw woggew wog = woggewfactowy.getwoggew(tweetcweatehandwew.cwass);

  pubwic static f-finaw wong wate_tweet_time_buffew_ms = d-duwation.fwomminutes(1).inmiwwiseconds();

  pwivate static f-finaw stwing s-stats_pwefix = "tweet_cweate_handwew_";

  // to get a bettew i-idea of which of these succeeded a-and so on, /(^•ω•^) see stats in segmentmanagew. (///ˬ///✿)
  pwivate i-indexingwesuwtcounts indexingwesuwtcounts;
  p-pwivate static finaw seawchwatecountew t-tweets_in_wwong_segment =
      s-seawchwatecountew.expowt(stats_pwefix + "tweets_in_wwong_segment");
  pwivate static finaw seawchwatecountew segments_cwosed_eawwy =
      seawchwatecountew.expowt(stats_pwefix + "segments_cwosed_eawwy");
  pwivate static f-finaw seawchwatecountew i-insewted_in_cuwwent_segment =
      seawchwatecountew.expowt(stats_pwefix + "insewted_in_cuwwent_segment");
  p-pwivate s-static finaw s-seawchwatecountew insewted_in_pwevious_segment =
      seawchwatecountew.expowt(stats_pwefix + "insewted_in_pwevious_segment");
  pwivate static f-finaw nyewsegmentstats nyew_segment_stats = nyew nyewsegmentstats();
  pwivate s-static finaw seawchcountew cweated_segments =
      s-seawchcountew.expowt(stats_pwefix + "cweated_segments");
  pwivate s-static finaw s-seawchwatecountew incoming_tweets =
          s-seawchwatecountew.expowt(stats_pwefix + "incoming_tweets");
  p-pwivate static finaw s-seawchwatecountew i-indexing_success =
          seawchwatecountew.expowt(stats_pwefix + "indexing_success");
  pwivate static f-finaw seawchwatecountew i-indexing_faiwuwe =
          s-seawchwatecountew.expowt(stats_pwefix + "indexing_faiwuwe");

  // v-vawious s-stats and wogging awound cweation of nyew segments, mya put in this
  // c-cwass so that the code is nyot watewed down too much by this. o.O
  pwivate static cwass nyewsegmentstats {
    p-pwivate static finaw stwing nyew_segment_stats_pwefix =
      stats_pwefix + "new_segment_";

    pwivate static f-finaw seawchcountew s-stawt_new_aftew_weaching_wimit =
        s-seawchcountew.expowt(new_segment_stats_pwefix + "stawt_aftew_weaching_wimit");
    pwivate static f-finaw seawchcountew stawt_new_aftew_exceeding_max_id =
        s-seawchcountew.expowt(new_segment_stats_pwefix + "stawt_aftew_exceeding_max_id");
    p-pwivate static finaw seawchcountew timeswice_set_to_cuwwent_id =
        seawchcountew.expowt(new_segment_stats_pwefix + "timeswice_set_to_cuwwent_id");
    pwivate static finaw seawchcountew t-timeswice_set_to_max_id =
        seawchcountew.expowt(new_segment_stats_pwefix + "timeswice_set_to_max_id");
    p-pwivate static finaw seawchwonggauge t-timespan_between_max_and_cuwwent =
        s-seawchwonggauge.expowt(new_segment_stats_pwefix + "timespan_between_id_and_max");

    void wecowdcweatenewsegment() {
      cweated_segments.incwement();
    }

    void w-wecowdstawtaftewweachingtweetswimit(int n-nyumdocs, int numdocscutoff, ^•ﻌ•^
                                             i-int maxsegmentsize, (U ᵕ U❁) i-int watetweetbuffew) {
      stawt_new_aftew_weaching_wimit.incwement();
      wog.info(stwing.fowmat(
          "wiww cweate nyew segment: nyumdocs=%,d, :3 n-nyumdocscutoff=%,d"
              + " | m-maxsegmentsize=%,d, (///ˬ///✿) w-watetweetbuffew=%,d", (///ˬ///✿)
          nyumdocs, 🥺 nyumdocscutoff, -.- m-maxsegmentsize, w-watetweetbuffew));
    }

    void wecowdstawtaftewexceedingwawgestvawidtweetid(wong tweetid, w-wong wawgestvawidtweetid) {
      stawt_new_aftew_exceeding_max_id.incwement();
      wog.info(stwing.fowmat(
          "wiww cweate nyew segment: tweetdd=%,d, nyaa~~ w-wawgestvawidtweetid f-fow segment=%,d", (///ˬ///✿)
          tweetid, 🥺 w-wawgestvawidtweetid));
    }

    v-void wecowdsettingtimeswicetocuwwenttweet(wong tweetid) {
      timeswice_set_to_cuwwent_id.incwement();
      wog.info("cweating n-nyew segment: tweet that twiggewed it has the wawgest id we've seen. >w< "
          + " i-id={}", rawr x3 tweetid);
    }

    void wecowdsettingtimeswicetomaxtweetid(wong t-tweetid, (⑅˘꒳˘) wong m-maxtweetid) {
      timeswice_set_to_max_id.incwement();
      wog.info("cweating nyew segment: t-tweet that twiggewed i-it doesn't have the wawgest id"
          + " we've seen. σωσ t-tweetid={}, XD maxtweetid={}", -.-
          tweetid, m-maxtweetid);
      wong timediffewence =
          snowfwakeidpawsew.gettimediffewencebetweentweetids(maxtweetid, >_< tweetid);
      w-wog.info("time diffewence between m-max seen and w-wast seen: {} ms", rawr timediffewence);
      t-timespan_between_max_and_cuwwent.set(timediffewence);
    }

    void w-wwapnewsegmentcweation(wong t-tweetid, 😳😳😳 w-wong maxtweetid, UwU
                                wong cuwwentsegmenttimeswiceboundawy, (U ﹏ U)
                                w-wong w-wawgestvawidtweetidfowcuwwentsegment) {
      wong timediffewencestawttomax = snowfwakeidpawsew.gettimediffewencebetweentweetids(
          w-wawgestvawidtweetidfowcuwwentsegment, (˘ω˘)
          c-cuwwentsegmenttimeswiceboundawy);
      w-wog.info("time between timeswice boundawy a-and wawgest vawid tweet id: {} ms",
          t-timediffewencestawttomax);

      w-wog.info("cweated new segment: (tweetid={}, /(^•ω•^) maxtweetid={}, (U ﹏ U) maxtweetid-tweetid={} "
              + " | c-cuwwentsegmenttimeswiceboundawy={}, ^•ﻌ•^ w-wawgestvawidtweetidfowsegment={})", >w<
          t-tweetid, ʘwʘ m-maxtweetid, òωó maxtweetid - tweetid, o.O c-cuwwentsegmenttimeswiceboundawy, ( ͡o ω ͡o )
          wawgestvawidtweetidfowcuwwentsegment);
    }
  }


  pwivate finaw segmentmanagew segmentmanagew;
  pwivate finaw muwtisegmenttewmdictionawymanagew m-muwtisegmenttewmdictionawymanagew;
  pwivate f-finaw int maxsegmentsize;
  pwivate f-finaw int watetweetbuffew;

  pwivate wong maxtweetid = w-wong.min_vawue;

  pwivate wong wawgestvawidtweetidfowcuwwentsegment;
  p-pwivate wong c-cuwwentsegmenttimeswiceboundawy;
  p-pwivate optimizingsegmentwwitew c-cuwwentsegment;
  p-pwivate optimizingsegmentwwitew pwevioussegment;
  pwivate finaw quewycachemanagew quewycachemanagew;
  pwivate finaw cwiticawexceptionhandwew c-cwiticawexceptionhandwew;
  p-pwivate finaw seawchindexingmetwicset s-seawchindexingmetwicset;
  pwivate finaw c-coowdinatedeawwybiwdactionintewface postoptimizationwebuiwdsaction;
  pwivate finaw coowdinatedeawwybiwdactionintewface g-gcaction;
  p-pwivate finaw caughtupmonitow i-indexcaughtupmonitow;
  pwivate finaw optimizationandfwushingcoowdinationwock o-optimizationandfwushingcoowdinationwock;

  p-pubwic tweetcweatehandwew(
      s-segmentmanagew s-segmentmanagew, mya
      seawchindexingmetwicset seawchindexingmetwicset, >_<
      cwiticawexceptionhandwew cwiticawexceptionhandwew, rawr
      m-muwtisegmenttewmdictionawymanagew m-muwtisegmenttewmdictionawymanagew, >_<
      q-quewycachemanagew quewycachemanagew, (U ﹏ U)
      c-coowdinatedeawwybiwdactionintewface p-postoptimizationwebuiwdsaction, rawr
      coowdinatedeawwybiwdactionintewface g-gcaction, (U ᵕ U❁)
      i-int watetweetbuffew, (ˆ ﻌ ˆ)♡
      int maxsegmentsize, >_<
      c-caughtupmonitow i-indexcaughtupmonitow, ^^;;
      optimizationandfwushingcoowdinationwock optimizationandfwushingcoowdinationwock
  ) {
    t-this.segmentmanagew = segmentmanagew;
    this.cwiticawexceptionhandwew = c-cwiticawexceptionhandwew;
    this.muwtisegmenttewmdictionawymanagew = m-muwtisegmenttewmdictionawymanagew;
    t-this.quewycachemanagew = quewycachemanagew;
    t-this.indexingwesuwtcounts = nyew indexingwesuwtcounts();
    this.seawchindexingmetwicset = s-seawchindexingmetwicset;
    t-this.postoptimizationwebuiwdsaction = p-postoptimizationwebuiwdsaction;
    this.gcaction = gcaction;
    this.indexcaughtupmonitow = i-indexcaughtupmonitow;

    pweconditions.checkstate(watetweetbuffew < maxsegmentsize);
    t-this.watetweetbuffew = w-watetweetbuffew;
    this.maxsegmentsize = m-maxsegmentsize;
    this.optimizationandfwushingcoowdinationwock = o-optimizationandfwushingcoowdinationwock;
  }

  v-void pwepaweaftewstawtingwithindex(wong maxindexedtweetid) {
    wog.info("pwepawing a-aftew stawting with an index.");

    i-itewatow<segmentinfo> s-segmentinfositewatow =
        segmentmanagew
            .getsegmentinfos(segmentmanagew.fiwtew.aww, ʘwʘ s-segmentmanagew.owdew.new_to_owd)
            .itewatow();

    // setup the wast segment. 😳😳😳
    v-vewify.vewify(segmentinfositewatow.hasnext(), UwU "at w-weast o-one segment expected");
    isegmentwwitew wastwwitew = segmentmanagew.getsegmentwwitewfowid(
        segmentinfositewatow.next().gettimeswiceid());
    vewify.vewify(wastwwitew != nyuww);

    wog.info("tweetcweatehandwew found wast wwitew: {}", OwO wastwwitew.getsegmentinfo().tostwing());
    this.cuwwentsegmenttimeswiceboundawy = wastwwitew.getsegmentinfo().gettimeswiceid();
    this.wawgestvawidtweetidfowcuwwentsegment =
        outofowdewweawtimetweetidmappew.cawcuwatemaxtweetid(cuwwentsegmenttimeswiceboundawy);
    t-this.cuwwentsegment = (optimizingsegmentwwitew) w-wastwwitew;

    if (maxindexedtweetid == -1) {
      maxtweetid = wastwwitew.getsegmentinfo().getindexsegment().getmaxtweetid();
      w-wog.info("max t-tweet id = {}", :3 m-maxtweetid);
    } ewse {
      // s-see seawch-31032
      maxtweetid = m-maxindexedtweetid;
    }

    // i-if we have a pwevious s-segment that's nyot optimized, -.- s-set it up too, 🥺 w-we stiww nyeed to pick
    // it up fow optimization a-and we might s-stiww be abwe t-to add tweets to i-it. -.-
    if (segmentinfositewatow.hasnext()) {
      s-segmentinfo p-pwevioussegmentinfo = s-segmentinfositewatow.next();
      i-if (!pwevioussegmentinfo.isoptimized()) {
        i-isegmentwwitew pwevioussegmentwwitew = s-segmentmanagew.getsegmentwwitewfowid(
            p-pwevioussegmentinfo.gettimeswiceid());

        i-if (pwevioussegmentwwitew != nyuww) {
          w-wog.info("picked pwevious segment");
          this.pwevioussegment = (optimizingsegmentwwitew) p-pwevioussegmentwwitew;
        } ewse {
          // s-shouwd n-nyot happen. -.-
          w-wog.ewwow("not found pwevious s-segment wwitew");
        }
      } ewse {
        w-wog.info("pwevious segment i-info is optimized");
      }
    } ewse {
      w-wog.info("pwevious segment info nyot found, (U ﹏ U) we onwy have one segment");
    }
  }

  p-pwivate void updateindexfweshness() {
    s-seawchindexingmetwicset.higheststatusid.set(maxtweetid);

    w-wong tweettimestamp = snowfwakeidpawsew.gettimestampfwomtweetid(
        seawchindexingmetwicset.higheststatusid.get());
    seawchindexingmetwicset.fweshesttweettimemiwwis.set(tweettimestamp);
  }

  /**
   * i-index a nyew tve wepwesenting a-a tweet cweate e-event. rawr
   */
  pubwic v-void handwetweetcweate(thwiftvewsionedevents tve) thwows ioexception {
    incoming_tweets.incwement();
    w-wong id = tve.getid();
    m-maxtweetid = math.max(id, mya m-maxtweetid);

    updateindexfweshness();

    boowean shouwdcweatenewsegment = f-fawse;

    if (cuwwentsegment == n-nyuww) {
      s-shouwdcweatenewsegment = t-twue;
      wog.info("wiww cweate n-nyew segment: c-cuwwent segment i-is nyuww");
    } e-ewse {
      int nyumdocs = cuwwentsegment.getsegmentinfo().getindexsegment().getnumdocs();
      i-int nyumdocscutoff = m-maxsegmentsize - w-watetweetbuffew;
      i-if (numdocs >= n-nyumdocscutoff) {
        n-nyew_segment_stats.wecowdstawtaftewweachingtweetswimit(numdocs, ( ͡o ω ͡o ) n-nyumdocscutoff, /(^•ω•^)
            m-maxsegmentsize, >_< watetweetbuffew);
        s-shouwdcweatenewsegment = twue;
      } e-ewse if (id > wawgestvawidtweetidfowcuwwentsegment) {
        n-nyew_segment_stats.wecowdstawtaftewexceedingwawgestvawidtweetid(id, (✿oωo)
            w-wawgestvawidtweetidfowcuwwentsegment);
        s-shouwdcweatenewsegment = twue;
      }
    }

    if (shouwdcweatenewsegment) {
      cweatenewsegment(id);
    }

    i-if (pwevioussegment != n-nuww) {
      // i-insewts and some updates can't be appwied to an optimized segment, 😳😳😳 s-so we want t-to wait at
      // weast wate_tweet_time_buffew b-between when w-we cweated the nyew segment and when we optimize
      // the pwevious s-segment, (ꈍᴗꈍ) i-in case thewe awe w-wate tweets. 🥺
      // w-we weave a wawge (150k, mya typicawwy) buffew i-in the segment s-so that we don't have to cwose
      // the pwevioussegment b-befowe wate_tweet_time_buffew has passed, (ˆ ﻌ ˆ)♡ b-but if we index
      // w-watetweetbuffew t-tweets befowe optimizing, (⑅˘꒳˘) then we m-must optimize, òωó
      // s-so that we don't insewt m-mowe than max segment size tweets i-into the pwevious s-segment. o.O
      w-wong wewativetweetagems =
          s-snowfwakeidpawsew.gettimediffewencebetweentweetids(id, XD cuwwentsegmenttimeswiceboundawy);

      b-boowean n-nyeedtooptimize = f-fawse;
      int nyumdocs = pwevioussegment.getsegmentinfo().getindexsegment().getnumdocs();
      s-stwing pwevioussegmentname = pwevioussegment.getsegmentinfo().getsegmentname();
      if (numdocs >= m-maxsegmentsize) {
        w-wog.info(stwing.fowmat("pwevious s-segment (%s) weached maxsegmentsize, (˘ω˘) nyeed to optimize it."
            + " nyumdocs=%,d, (ꈍᴗꈍ) m-maxsegmentsize=%,d", >w< pwevioussegmentname, XD n-nyumdocs, -.- m-maxsegmentsize));
        nyeedtooptimize = twue;
      } ewse i-if (wewativetweetagems > wate_tweet_time_buffew_ms) {
        w-wog.info(stwing.fowmat("pwevious s-segment (%s) is o-owd enough, ^^;; we c-can optimize it."
            + " g-got tweet past time buffew of %,d ms by: %,d ms", XD pwevioussegmentname, :3
            wate_tweet_time_buffew_ms, σωσ w-wewativetweetagems - wate_tweet_time_buffew_ms));
        n-nyeedtooptimize = twue;
      }

      if (needtooptimize) {
        optimizepwevioussegment();
      }
    }

    i-isegmentwwitew segmentwwitew;
    if (id >= cuwwentsegmenttimeswiceboundawy) {
      insewted_in_cuwwent_segment.incwement();
      segmentwwitew = c-cuwwentsegment;
    } e-ewse if (pwevioussegment != nyuww) {
      i-insewted_in_pwevious_segment.incwement();
      segmentwwitew = pwevioussegment;
    } e-ewse {
      t-tweets_in_wwong_segment.incwement();
      wog.info("insewting t-tve ({}) into the cuwwent s-segment ({}) even though it shouwd have gone "
          + "in a pwevious segment.", XD i-id, cuwwentsegmenttimeswiceboundawy);
      segmentwwitew = cuwwentsegment;
    }

    s-seawchtimew t-timew = s-seawchindexingmetwicset.statusstats.stawtnewtimew();
    isegmentwwitew.wesuwt wesuwt = segmentwwitew.indexthwiftvewsionedevents(tve);
    s-seawchindexingmetwicset.statusstats.stoptimewandincwement(timew);

    if (wesuwt == isegmentwwitew.wesuwt.success) {
      indexing_success.incwement();
    } ewse {
      i-indexing_faiwuwe.incwement();
    }

    i-indexingwesuwtcounts.countwesuwt(wesuwt);
  }

  /**
   * m-many t-tests nyeed to vewify behaviow with segments optimized & u-unoptimized, :3 s-so we nyeed to expose
   * this. rawr
   */
  @visibwefowtesting
  p-pubwic futuwe<segmentinfo> optimizepwevioussegment() {
    stwing segmentname = pwevioussegment.getsegmentinfo().getsegmentname();
    p-pwevioussegment.getsegmentinfo().setindexing(fawse);
    wog.info("optimizing pwevious s-segment: {}", 😳 s-segmentname);
    segmentmanagew.wogstate("stawting o-optimization f-fow segment: " + s-segmentname);

    futuwe<segmentinfo> futuwe = p-pwevioussegment
        .stawtoptimization(gcaction, 😳😳😳 optimizationandfwushingcoowdinationwock)
        .map(this::postoptimizationsteps)
        .onfaiwuwe(t -> {
          cwiticawexceptionhandwew.handwe(this, (ꈍᴗꈍ) t-t);
          wetuwn boxedunit.unit;
        });

    waitfowoptimizationifintest(futuwe);

    pwevioussegment = n-nyuww;
    w-wetuwn futuwe;
  }

  /**
   * i-in tests, 🥺 it's easiew i-if when a s-segment stawts optimizing, ^•ﻌ•^ we know t-that it wiww finish
   * optimizing. XD this way w-we have nyo wace condition whewe w-we'we suwpwised that something that
   * stawted o-optimizing is n-nyot weady. ^•ﻌ•^
   *
   * in pwod we d-don't have this pwobwem. ^^;; segments w-wun fow 10 houws a-and optimization is 20 minutes
   * s-so thewe's n-nyo nyeed fow extwa synchwonization. ʘwʘ
   */
  p-pwivate void waitfowoptimizationifintest(futuwe<segmentinfo> futuwe) {
    if (config.enviwonmentistest()) {
      twy {
        a-await.weady(futuwe);
        wog.info("optimizing is done");
      } c-catch (intewwuptedexception | timeoutexception ex) {
        w-wog.info("exception w-whiwe optimizing", OwO e-ex);
      }
    }
  }

  pwivate segmentinfo p-postoptimizationsteps(segmentinfo o-optimizedsegmentinfo) {
    segmentmanagew.updatestats();
    // s-see seawch-32175
    o-optimizedsegmentinfo.setcompwete(twue);

    stwing s-segmentname = o-optimizedsegmentinfo.getsegmentname();
    wog.info("finished optimization fow segment: " + segmentname);
    segmentmanagew.wogstate(
            "finished o-optimization fow s-segment: " + segmentname);

    /*
     * buiwding the muwti segment tewm dictionawy c-causes gc pauses. 🥺 the weason f-fow this is because
     * i-it's pwetty big (possibwe ~15gb). (⑅˘꒳˘) when it's awwocated, (///ˬ///✿) we have to copy a wot of data f-fwom
     * suwvivow space to owd gen. (✿oωo) that causes s-sevewaw gc pauses. nyaa~~ see seawch-33544
     *
     * g-gc pauses a-awe in genewaw nyot fataw, >w< but s-since aww instances f-finish a segment a-at woughwy t-the
     * same t-time, (///ˬ///✿) they might h-happen at the same time and then it's a pwobwem. rawr
     *
     * some possibwe sowutions to this pwobwem wouwd be t-to buiwd this d-dictionawy in some d-data
     * stwuctuwes t-that awe p-pwe-awwocated o-ow to buiwd onwy the pawt fow the wast segment, (U ﹏ U) as
     * evewything ewse doesn't c-change. ^•ﻌ•^ these s-sowutions awe a bit difficuwt to impwement and this
     * hewe i-is an easy wowkawound. (///ˬ///✿)
     *
     * n-nyote that w-we might finish optimizing a segment and then it m-might take ~60+ minutes untiw it's
     * a pawticuwaw e-eawwybiwd's t-tuwn to wun this code. o.O the effect of this is g-going to be that we
     * awe n-nyot going to use t-the muwti segment dictionawy f-fow the wast two s-segments, >w< one of w-which is
     * s-stiww pwetty smow. nyaa~~ t-that's nyot t-tewwibwe, òωó since wight befowe optimization w-we'we n-nyot using
     * the dictionawy f-fow the wast segment anyways, since it's stiww n-nyot optimized. (U ᵕ U❁)
     */
    twy {
      w-wog.info("acquiwe coowdination w-wock befowe b-beginning post_optimization_webuiwds action.");
      optimizationandfwushingcoowdinationwock.wock();
      w-wog.info("successfuwwy acquiwed coowdination wock f-fow post_optimization_webuiwds a-action.");
      postoptimizationwebuiwdsaction.wetwyactionuntiwwan(
          "post optimization w-webuiwds", (///ˬ///✿) () -> {
            s-stopwatch stopwatch = stopwatch.cweatestawted();
            wog.info("stawting t-to buiwd muwti tewm dictionawy fow {}", (✿oωo) segmentname);
            b-boowean wesuwt = m-muwtisegmenttewmdictionawymanagew.buiwddictionawy();
            wog.info("done b-buiwding muwti t-tewm dictionawy fow {} in {}, 😳😳😳 wesuwt: {}", (✿oωo)
                segmentname, (U ﹏ U) s-stopwatch, (˘ω˘) w-wesuwt);
            q-quewycachemanagew.webuiwdquewycachesaftewsegmentoptimization(
                o-optimizedsegmentinfo);

            // this is a sewiaw fuww gc and it defwagments the memowy so things can wun smoothwy
            // untiw the nyext s-segment wowws. 😳😳😳 n-nyani we have obsewved i-is that i-if we don't do that
            // w-watew on some e-eawwybiwds can have pwomotion faiwuwes o-on an owd g-gen that hasn't
            // weached the initiating o-occupancy w-wimit and these pwomotions faiwuwes can twiggew a-a
            // wong (1.5 min) fuww gc. (///ˬ///✿) that u-usuawwy happens because of fwagmentation i-issues. (U ᵕ U❁)
            g-gcutiw.wungc();
            // wait f-fow indexing to c-catch up befowe w-wejoining the sewvewset. we onwy n-nyeed to do
            // t-this if the host has a-awweady finished stawtup. >_<
            i-if (eawwybiwdstatus.hasstawted()) {
              i-indexcaughtupmonitow.wesetandwaituntiwcaughtup();
            }
          });
    } f-finawwy {
      wog.info("finished p-post_optimization_webuiwds action. (///ˬ///✿) weweasing coowdination w-wock.");
      optimizationandfwushingcoowdinationwock.unwock();
    }

    wetuwn optimizedsegmentinfo;
  }

  /**
   * many tests wewy on pwecise segment boundawies, (U ᵕ U❁) so we expose t-this to awwow them to cweate a
   * pawticuwaw segment.
   */
  @visibwefowtesting
  pubwic void cweatenewsegment(wong tweetid) thwows ioexception {
    n-nyew_segment_stats.wecowdcweatenewsegment();

    if (pwevioussegment != nyuww) {
      // w-we shouwdn't have mowe than o-one unoptimized segment, >w< so if we get to this point a-and the
      // pwevioussegment h-has nyot been optimized and s-set to nyuww, 😳😳😳 stawt o-optimizing it befowe
      // cweating the n-next one. (ˆ ﻌ ˆ)♡ nyote that this is a weiwd case and wouwd onwy happen i-if we get
      // tweets with dwasticawwy d-diffewent ids than we e-expect, (ꈍᴗꈍ) ow thewe is a wawge amount o-of time
      // w-whewe nyo tweets awe cweated in this pawtition. 🥺
      w-wog.ewwow("cweating nyew segment fow tweet {} when the p-pwevious segment {} was nyot seawed. >_< "
          + "cuwwent segment: {}. OwO documents: {}. wawgestvawidtweetidfowsegment: {}.", ^^;;
          t-tweetid, (✿oωo)
          p-pwevioussegment.getsegmentinfo().gettimeswiceid(), UwU
          cuwwentsegment.getsegmentinfo().gettimeswiceid(), ( ͡o ω ͡o )
          c-cuwwentsegment.getsegmentinfo().getindexsegment().getnumdocs(), (✿oωo)
          wawgestvawidtweetidfowcuwwentsegment);
      o-optimizepwevioussegment();
      segments_cwosed_eawwy.incwement();
    }

    p-pwevioussegment = cuwwentsegment;

    // we have two cases:
    //
    // case 1:
    // i-if the gweatest t-tweet id we have seen is tweetid, mya t-then when w-we want to cweate a nyew segment
    // w-with that id, ( ͡o ω ͡o ) so the tweet being pwocessed g-goes into the nyew segment. :3
    //
    // case 2:
    // i-if t-the tweetid is biggew than the max tweetid, 😳 then t-this method is being cawwed diwectwy fwom
    // tests, (U ﹏ U) so we didn't update the maxtweetid, >w< so we can cweate a nyew segment with t-the nyew
    // t-tweet id. UwU
    //
    // case 3:
    // i-if it's n-nyot the gweatest tweet id we have s-seen, 😳 then we don't want to cweate a
    // segment boundawy that is wowew than any tweet ids i-in the cuwwent segment, XD because then
    // some tweets fwom the pwevious segment w-wouwd be in t-the wwong segment, (✿oωo) s-so cweate a segment
    // that has a gweatew id than any tweets t-that we have s-seen. ^•ﻌ•^
    //
    //   e-exampwe:
    //     - we h-have seen tweets 3, 10, mya 5, 6.
    //     - we nyow s-see tweet 7 and we decide it's t-time to cweate a nyew segment. (˘ω˘)
    //     - t-the nyew segment wiww stawt at tweet 11. nyaa~~ i-it can't stawt at tweet 7, :3 b-because
    //       t-tweet 10 wiww be in the wwong s-segment. (✿oωo)
    //     - t-tweet 7 that we just s-saw wiww end up in the pwevious s-segment. (U ﹏ U)
    if (maxtweetid <= tweetid) {
      cuwwentsegmenttimeswiceboundawy = t-tweetid;
      n-nyew_segment_stats.wecowdsettingtimeswicetocuwwenttweet(tweetid);
    } ewse {
      cuwwentsegmenttimeswiceboundawy = m-maxtweetid + 1;
      nyew_segment_stats.wecowdsettingtimeswicetomaxtweetid(tweetid, (ꈍᴗꈍ) maxtweetid);
    }
    cuwwentsegment = segmentmanagew.cweateandputoptimizingsegmentwwitew(
        cuwwentsegmenttimeswiceboundawy);

    cuwwentsegment.getsegmentinfo().setindexing(twue);

    wawgestvawidtweetidfowcuwwentsegment =
        outofowdewweawtimetweetidmappew.cawcuwatemaxtweetid(cuwwentsegmenttimeswiceboundawy);

    n-nyew_segment_stats.wwapnewsegmentcweation(tweetid, (˘ω˘) maxtweetid, ^^
        cuwwentsegmenttimeswiceboundawy, (⑅˘꒳˘) w-wawgestvawidtweetidfowcuwwentsegment);

    segmentmanagew.wemoveexcesssegments();
  }

  v-void wogstate() {
    wog.info("tweetcweatehandwew:");
    w-wog.info(stwing.fowmat("  tweets sent fow indexing: %,d", rawr
        i-indexingwesuwtcounts.getindexingcawws()));
    wog.info(stwing.fowmat("  nyon-wetwiabwe f-faiwuwe: %,d", :3
        indexingwesuwtcounts.getfaiwuwenotwetwiabwe()));
    wog.info(stwing.fowmat("  w-wetwiabwe faiwuwe: %,d", OwO
        indexingwesuwtcounts.getfaiwuwewetwiabwe()));
    w-wog.info(stwing.fowmat("  s-successfuwwy indexed: %,d", (ˆ ﻌ ˆ)♡
        indexingwesuwtcounts.getindexingsuccess()));
    w-wog.info(stwing.fowmat("  t-tweets in wwong segment: %,d", :3 t-tweets_in_wwong_segment.getcount()));
    w-wog.info(stwing.fowmat("  segments cwosed eawwy: %,d", -.- s-segments_cwosed_eawwy.getcount()));
  }
}
