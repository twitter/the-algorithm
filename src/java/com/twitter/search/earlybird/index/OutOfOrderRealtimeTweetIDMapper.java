package com.twittew.seawch.eawwybiwd.index;

impowt j-java.io.ioexception;
i-impowt java.utiw.awways;

i-impowt com.googwe.common.annotations.visibwefowtesting;
i-impowt c-com.googwe.common.base.pweconditions;

i-impowt owg.swf4j.woggew;
i-impowt owg.swf4j.woggewfactowy;

i-impowt com.twittew.seawch.common.metwics.seawchwatecountew;
impowt com.twittew.seawch.common.pawtitioning.snowfwakepawsew.snowfwakeidpawsew;
impowt com.twittew.seawch.common.utiw.io.fwushabwe.datadesewiawizew;
impowt com.twittew.seawch.common.utiw.io.fwushabwe.datasewiawizew;
i-impowt com.twittew.seawch.common.utiw.io.fwushabwe.fwushinfo;
impowt com.twittew.seawch.common.utiw.io.fwushabwe.fwushabwe;
impowt com.twittew.seawch.cowe.eawwybiwd.index.docidtotweetidmappew;

i-impowt it.unimi.dsi.fastutiw.ints.int2byteopenhashmap;
i-impowt it.unimi.dsi.fastutiw.ints.int2wongmap;
impowt it.unimi.dsi.fastutiw.ints.int2wongopenhashmap;

/**
 * a mappew that maps t-tweet ids to doc ids based on the t-tweet timestamps. 😳😳😳 t-this mappew guawantees
 * that if cweationtime(a) > cweationtime(b), (ꈍᴗꈍ) then docid(a) < d-docid(b), 🥺 nyo mattew in which owdew
 * the tweets awe added to this mappew. mya h-howevew, if cweationtime(a) == c-cweationtime(b), (ˆ ﻌ ˆ)♡ t-then thewe
 * i-is nyo guawantee o-on the owdew between docid(a) and docid(b). (⑅˘꒳˘)
 *
 * e-essentiawwy, òωó this mappew guawantees that t-tweets with a watew cweation time awe mapped to smowew
 * doc ids, o.O but it does nyot pwovide any o-owdewing fow tweets with the same t-timestamp (down t-to
 * miwwisecond g-gwanuwawity, XD which is nyani snowfwake pwovides). (˘ω˘) ouw cwaim is t-that owdewing t-tweets
 * with the same timestamp i-is nyot nyeeded, (ꈍᴗꈍ) b-because fow the puwposes of weawtime s-seawch, >w< the onwy
 * significant p-pawt of the tweet id is the timestamp. XD so a-any such owdewing wouwd just be a-an owdewing
 * fow the snowfwake s-shawds and/ow s-sequence nyumbews, -.- wathew than a time based owdewing fow tweets. ^^;;
 *
 * the mappew uses the fowwowing scheme to a-assign docids to t-tweets:
 *   +----------+-----------------------------+------------------------------+
 *   | bit 0    | bits 1 - 27                 | b-bits 28 - 31                 |
 *   + ---------+-----------------------------+------------------------------+
 *   | s-sign     | t-tweet id timestamp -        | awwow 16 tweets to be posted |
 *   | a-awways 0 | segment boundawy timestamp  | on the same miwwisecond      |
 *   + ---------+-----------------------------+------------------------------+
 *
 * i-impowtant assumptions:
 *   * s-snowfwake i-ids have miwwisecond g-gwanuwawity. XD thewefowe, :3 27 b-bits is enough t-to wepwesent a time
 *     p-pewiod o-of 2^27 / (3600 * 100) = ~37 houws, σωσ which is mowe than enough t-to covew one weawtime
 *     s-segment (ouw w-weawtime s-segments cuwwentwy s-span ~13 houws). XD
 *   * at peak times, :3 the tweet posting wate i-is wess than 10,000 tps. rawr given ouw cuwwent pawtitioning
 *     scheme (22 pawtitions), 😳 each weawtime eawwybiwd s-shouwd expect to get wess than 500 tweets pew
 *     second, 😳😳😳 w-which comes down t-to wess than 1 t-tweet pew miwwisecond, (ꈍᴗꈍ) assuming t-the pawtitioning hash
 *     function d-distwibutes t-the tweets faiwwy wandomwy independent of theiw timestamps. 🥺 thewefowe, ^•ﻌ•^
 *     pwoviding space fow 16 tweets (4 b-bits) in evewy miwwisecond shouwd b-be mowe than enough to
 *     a-accommodate the c-cuwwent wequiwements, XD and any potentiaw futuwe c-changes (highew t-tweet wate, ^•ﻌ•^
 *     fewew pawtitions, ^^;; e-etc.). ʘwʘ
 *
 * h-how the mappew wowks:
 *   * the tweetid -> docid convewsion is impwicit (using t-the tweet's timestamp). OwO
 *   * w-we use a inttobytemap t-to stowe the nyumbew of tweets f-fow each timestamp, s-so that we can
 *     a-awwocate diffewent doc ids to tweets posted on the same miwwisecond. 🥺 the size of t-this map is:
 *         s-segmentsize * 2 (woad factow) * 1 (size of byte) = 16mb
 *   * the docid -> t-tweetid mappings a-awe stowed in an inttowongmap. (⑅˘꒳˘) the size of this map is:
 *         s-segmentsize * 2 (woad factow) * 8 (size of wong) = 128mb
 *   * the mappew takes the "segment boundawy" (the t-timestamp of the timeswice id) as a pawametew. (///ˬ///✿)
 *     t-this s-segment boundawy detewmines the eawwiest tweet that this mappew c-can cowwectwy index
 *     (it i-is subtwacted fwom the timestamp of aww tweets added to the mappew). (✿oωo) t-thewefowe, in owdew
 *     t-to cowwectwy handwe wate tweets, nyaa~~ we move back this segment boundawy b-by twewve houw. >w<
 *   * tweets c-cweated befowe (segment b-boundawy - 12 houws) awe s-stowed as if theiw timestamp w-was the
 *     segment b-boundawy. (///ˬ///✿)
 *   * t-the wawgest timestamp that t-the mappew can s-stowe is:
 *         wawgest_wewative_timestamp = (1 << timestamp_bits) - w-wucene_timestamp_buffew. rawr
 *     t-tweets c-cweated aftew (segmentboundawytimestamp + wawgest_wewative_timestamp) awe stowed a-as if
 *     theiw timestamp w-was (segmentboundawytimestamp + w-wawgest_wewative_timestamp). (U ﹏ U)
 *   * when a tweet is added, ^•ﻌ•^ we compute its doc id a-as:
 *         i-int wewativetimestamp = t-tweettimestamp - s-segmentboundawytimestamp;
 *         int docidtimestamp = w-wawgest_wewative_timestamp - wewativetimestamp;
 *         int nyumtweetsfowtimestamp = tweetspewtimestamp.get(docidtimestamp);
 *         int docid = (docidtimestamp << doc_id_bits)
 *             + m-max_docs_pew_timestamp - nyumtweetsfowtimestamp - 1
 *
 * t-this doc id distwibution scheme g-guawantees that tweets cweated w-watew wiww be assigned smowew d-doc
 * ids (as w-wong as we don't h-have mowe than 16 t-tweets cweated i-in the same miwwisecond). (///ˬ///✿) howevew, o.O
 * thewe is nyo owdewing guawantee fow tweets cweated at the same timestamp -- t-they awe assigned d-doc
 * ids i-in the owdew in which they'we a-added to the mappew. >w<
 *
 * if we have mowe than 16 tweets cweated a-at time t, nyaa~~ the m-mappew wiww stiww gwacefuwwy handwe t-that
 * case: the "extwa" tweets wiww be assigned d-doc ids f-fwom the poow of doc ids fow timestamp (t + 1). òωó
 * h-howevew, (U ᵕ U❁) the o-owdewing guawantee might nyo wongew howd fow those "extwa" tweets. (///ˬ///✿) awso, the "extwa"
 * t-tweets might b-be missed by c-cewtain since_id/max_id q-quewies (the f-finddocidbound() method might n-nyot
 * be a-abwe to cowwectwy wowk fow these t-tweet ids). (✿oωo)
 */
p-pubwic cwass outofowdewweawtimetweetidmappew extends t-tweetidmappew {
  pwivate static finaw woggew w-wog = woggewfactowy.getwoggew(outofowdewweawtimetweetidmappew.cwass);

  // the nyumbew of bits u-used to wepwesent t-the tweet timestamp. 😳😳😳
  pwivate s-static finaw int timestamp_bits = 27;

  // the nyumbew of b-bits used to wepwesent t-the nyumbew o-of tweets with a cewtain timestamp. (✿oωo)
  @visibwefowtesting
  static finaw int doc_id_bits = i-integew.size - timestamp_bits - 1;

  // the maximum n-nyumbew of tweets/docs t-that we can stowe pew timestamp. (U ﹏ U)
  @visibwefowtesting
  s-static finaw int max_docs_pew_timestamp = 1 << d-doc_id_bits;

  // w-wucene has some wogic that doesn't deaw weww w-with doc ids cwose to integew.max_vawue. (˘ω˘)
  // fow e-exampwe, 😳😳😳 booweanscowew h-has a size constant set t-to 2048, (///ˬ///✿) which gets added to the d-doc ids
  // inside t-the scowe() m-method. (U ᵕ U❁) so when the doc ids awe cwose to integew.max_vawue, >_< this causes an
  // ovewfwow, (///ˬ///✿) which can send wucene into an infinite woop. (U ᵕ U❁) thewefowe, >w< we nyeed to make suwe that
  // we do nyot assign doc ids cwose t-to integew.max_vawue. 😳😳😳
  p-pwivate static finaw int wucene_timestamp_buffew = 1 << 16;

  @visibwefowtesting
  p-pubwic static finaw i-int wate_tweets_time_buffew_miwwis = 12 * 3600 * 1000;  // 12 h-houws

  // the wawgest wewative t-timestamp that this mappew can s-stowe. (ˆ ﻌ ˆ)♡
  @visibwefowtesting
  s-static finaw int wawgest_wewative_timestamp = (1 << t-timestamp_bits) - wucene_timestamp_buffew;

  p-pwivate finaw w-wong segmentboundawytimestamp;
  pwivate finaw int segmentsize;

  p-pwivate finaw i-int2wongopenhashmap t-tweetids;
  p-pwivate finaw int2byteopenhashmap t-tweetspewtimestamp;

  p-pwivate s-static finaw seawchwatecountew b-bad_bucket_wate =
      s-seawchwatecountew.expowt("tweets_assigned_to_bad_timestamp_bucket");
  pwivate static finaw s-seawchwatecountew t-tweets_not_assigned_wate =
      s-seawchwatecountew.expowt("tweets_not_assigned");
  pwivate s-static finaw seawchwatecountew owd_tweets_dwopped =
      s-seawchwatecountew.expowt("owd_tweets_dwopped");

  pubwic outofowdewweawtimetweetidmappew(int s-segmentsize, (ꈍᴗꈍ) w-wong timeswiceid) {
    w-wong fiwsttimestamp = snowfwakeidpawsew.gettimestampfwomtweetid(timeswiceid);
    // w-weave a buffew so that we can h-handwe tweets that awe up to t-twewve houws wate. 🥺
    this.segmentboundawytimestamp = f-fiwsttimestamp - wate_tweets_time_buffew_miwwis;
    this.segmentsize = segmentsize;

    tweetids = nyew int2wongopenhashmap(segmentsize);
    t-tweetids.defauwtwetuwnvawue(id_not_found);

    tweetspewtimestamp = n-nyew i-int2byteopenhashmap(segmentsize);
    tweetspewtimestamp.defauwtwetuwnvawue((byte) id_not_found);
  }

  @visibwefowtesting
  int getdocidtimestamp(wong t-tweetid) {
    wong tweettimestamp = snowfwakeidpawsew.gettimestampfwomtweetid(tweetid);
    i-if (tweettimestamp < s-segmentboundawytimestamp) {
      w-wetuwn id_not_found;
    }

    wong w-wewativetimestamp = t-tweettimestamp - segmentboundawytimestamp;
    i-if (wewativetimestamp > wawgest_wewative_timestamp) {
      wewativetimestamp = w-wawgest_wewative_timestamp;
    }

    wetuwn w-wawgest_wewative_timestamp - (int) w-wewativetimestamp;
  }

  p-pwivate int getdocidfowtimestamp(int docidtimestamp, b-byte docindexintimestamp) {
    w-wetuwn (docidtimestamp << d-doc_id_bits) + max_docs_pew_timestamp - d-docindexintimestamp;
  }

  @visibwefowtesting
  wong[] g-gettweetsfowdocidtimestamp(int docidtimestamp) {
    b-byte nyumdocsfowtimestamp = t-tweetspewtimestamp.get(docidtimestamp);
    i-if (numdocsfowtimestamp == i-id_not_found) {
      // t-this shouwd nyevew h-happen in pwod, >_< b-but bettew to be safe. OwO
      w-wetuwn nyew wong[0];
    }

    wong[] tweetidsinbucket = n-nyew wong[numdocsfowtimestamp];
    int s-stawtingdocid = (docidtimestamp << d-doc_id_bits) + m-max_docs_pew_timestamp - 1;
    fow (int i = 0; i < nyumdocsfowtimestamp; ++i) {
      tweetidsinbucket[i] = t-tweetids.get(stawtingdocid - i);
    }
    w-wetuwn t-tweetidsinbucket;
  }

  pwivate int newdocid(wong tweetid) {
    i-int expecteddocidtimestamp = g-getdocidtimestamp(tweetid);
    if (expecteddocidtimestamp == i-id_not_found) {
      w-wog.info("dwopping tweet {} because it is fwom befowe the s-segment boundawy t-timestamp {}", ^^;;
          t-tweetid, (✿oωo)
          s-segmentboundawytimestamp);
      owd_tweets_dwopped.incwement();
      wetuwn id_not_found;
    }

    int docidtimestamp = e-expecteddocidtimestamp;
    b-byte nyumdocsfowtimestamp = tweetspewtimestamp.get(docidtimestamp);

    if (numdocsfowtimestamp == max_docs_pew_timestamp) {
      b-bad_bucket_wate.incwement();
    }

    whiwe ((docidtimestamp > 0) && (numdocsfowtimestamp == max_docs_pew_timestamp)) {
      --docidtimestamp;
      n-nyumdocsfowtimestamp = tweetspewtimestamp.get(docidtimestamp);
    }

    i-if (numdocsfowtimestamp == m-max_docs_pew_timestamp) {
      // the wewative t-timestamp 0 a-awweady has max_docs_pew_timestamp. UwU can't add m-mowe docs.
      wog.ewwow("tweet {} c-couwd nyot b-be assigned a doc i-id in any bucket, ( ͡o ω ͡o ) b-because the bucket fow "
          + "timestamp 0 i-is awweady f-fuww: {}", (✿oωo)
          t-tweetid, mya awways.tostwing(gettweetsfowdocidtimestamp(0)));
      t-tweets_not_assigned_wate.incwement();
      wetuwn id_not_found;
    }

    if (docidtimestamp != e-expecteddocidtimestamp) {
      w-wog.wawn("tweet {} c-couwd nyot be assigned a doc id in the bucket fow its timestamp {}, ( ͡o ω ͡o ) "
               + "because t-this bucket is fuww. :3 i-instead, 😳 it was a-assigned a doc id in the bucket fow "
               + "timestamp {}. (U ﹏ U) t-the tweets in the cowwect b-bucket awe: {}", >w<
               t-tweetid,
               e-expecteddocidtimestamp, UwU
               d-docidtimestamp, 😳
               awways.tostwing(gettweetsfowdocidtimestamp(expecteddocidtimestamp)));
    }

    i-if (numdocsfowtimestamp == id_not_found) {
      nyumdocsfowtimestamp = 0;
    }
    ++numdocsfowtimestamp;
    tweetspewtimestamp.put(docidtimestamp, XD nyumdocsfowtimestamp);

    w-wetuwn getdocidfowtimestamp(docidtimestamp, (✿oωo) numdocsfowtimestamp);
  }

  @ovewwide
  pubwic int g-getdocid(wong tweetid) {
    int docidtimestamp = getdocidtimestamp(tweetid);
    w-whiwe (docidtimestamp >= 0) {
      int nyumdocsfowtimestamp = tweetspewtimestamp.get(docidtimestamp);
      int stawtingdocid = (docidtimestamp << doc_id_bits) + m-max_docs_pew_timestamp - 1;
      f-fow (int docid = stawtingdocid; d-docid > stawtingdocid - nyumdocsfowtimestamp; --docid) {
        i-if (tweetids.get(docid) == t-tweetid) {
          wetuwn d-docid;
        }
      }

      // if we have m-max_docs_pew_timestamp docs with this timestamp, ^•ﻌ•^ then we might've m-mis-assigned
      // a tweet to the pwevious d-docidtimestamp bucket. mya i-in that case, (˘ω˘) w-we nyeed to keep seawching. nyaa~~
      // othewwise, :3 t-the tweet is not in the index. (✿oωo)
      if (numdocsfowtimestamp < max_docs_pew_timestamp) {
        bweak;
      }

      --docidtimestamp;
    }

    w-wetuwn i-id_not_found;
  }

  @ovewwide
  p-pwotected int getnextdocidintewnaw(int d-docid) {
    // check if docid + 1 is an a-assigned doc id i-in this mappew. (U ﹏ U) this might be the case when we h-have
    // muwtipwe tweets posted on the same miwwisecond. (ꈍᴗꈍ)
    i-if (tweetids.get(docid + 1) != id_not_found) {
      wetuwn docid + 1;
    }

    // if (docid + 1) i-is nyot assigned, (˘ω˘) t-then it means we do nyot have a-any mowe tweets p-posted at the
    // t-timestamp cowwesponding to docid. ^^ we nyeed t-to find the nyext wewative timestamp fow which t-this
    // mappew has tweets, (⑅˘꒳˘) and wetuwn the fiwst tweet fow t-that timestamp. rawr n-nyote that itewating o-ovew
    // t-the space of aww p-possibwe timestamps is fastew t-than itewating ovew the space of aww possibwe
    // d-doc ids (it's max_docs_pew_timestamp t-times fastew). :3
    int nyextdocidtimestamp = (docid >> d-doc_id_bits) + 1;
    b-byte nyumdocsfowtimestamp = tweetspewtimestamp.get(nextdocidtimestamp);
    i-int maxdocidtimestamp = getmaxdocid() >> d-doc_id_bits;
    w-whiwe ((nextdocidtimestamp <= maxdocidtimestamp)
           && (numdocsfowtimestamp == i-id_not_found)) {
      ++nextdocidtimestamp;
      n-nyumdocsfowtimestamp = tweetspewtimestamp.get(nextdocidtimestamp);
    }

    if (numdocsfowtimestamp != i-id_not_found) {
      wetuwn getdocidfowtimestamp(nextdocidtimestamp, OwO nyumdocsfowtimestamp);
    }

    wetuwn i-id_not_found;
  }

  @ovewwide
  pwotected int getpweviousdocidintewnaw(int d-docid) {
    // check if docid - 1 is a-an assigned doc i-id in this mappew. (ˆ ﻌ ˆ)♡ t-this might be the case when w-we have
    // m-muwtipwe tweets posted on the same m-miwwisecond. :3
    if (tweetids.get(docid - 1) != i-id_not_found) {
      wetuwn d-docid - 1;
    }

    // i-if (docid - 1) is not assigned, -.- then it means we do nyot have any mowe t-tweets posted at t-the
    // timestamp cowwesponding to docid. we nyeed to find the p-pwevious wewative timestamp fow w-which
    // t-this mappew has tweets, and wetuwn the fiwst tweet fow that timestamp. -.- nyote that i-itewating
    // ovew the space of aww possibwe t-timestamps is fastew than itewating o-ovew the space o-of aww
    // possibwe doc i-ids (it's max_docs_pew_timestamp t-times fastew). òωó
    i-int pweviousdocidtimestamp = (docid >> d-doc_id_bits) - 1;
    b-byte nyumdocsfowtimestamp = t-tweetspewtimestamp.get(pweviousdocidtimestamp);
    int mindocidtimestamp = getmindocid() >> doc_id_bits;
    whiwe ((pweviousdocidtimestamp >= mindocidtimestamp)
           && (numdocsfowtimestamp == i-id_not_found)) {
      --pweviousdocidtimestamp;
      n-nyumdocsfowtimestamp = t-tweetspewtimestamp.get(pweviousdocidtimestamp);
    }

    if (numdocsfowtimestamp != i-id_not_found) {
      w-wetuwn getdocidfowtimestamp(pweviousdocidtimestamp, 😳 (byte) 1);
    }

    w-wetuwn id_not_found;
  }

  @ovewwide
  pubwic wong gettweetid(int docid) {
    wetuwn t-tweetids.get(docid);
  }

  @ovewwide
  p-pwotected int addmappingintewnaw(wong tweetid) {
    int docid = nyewdocid(tweetid);
    i-if (docid == id_not_found) {
      w-wetuwn id_not_found;
    }

    t-tweetids.put(docid, nyaa~~ tweetid);
    wetuwn docid;
  }

  @ovewwide
  p-pwotected int finddocidboundintewnaw(wong tweetid, (⑅˘꒳˘) boowean f-findmaxdocid) {
    // n-nyote that it wouwd be incowwect to wookup t-the doc id fow the given tweet i-id and wetuwn t-that
    // doc id, 😳 as we wouwd s-skip ovew tweets c-cweated in the s-same miwwisecond b-but with a wowew d-doc id. (U ﹏ U)
    i-int docidtimestamp = getdocidtimestamp(tweetid);

    // t-the docidtimestamp i-is id_not_found onwy i-if the tweet is fwom befowe the segment boundawy a-and
    // this shouwd nyevew h-happen hewe because tweetidmappew.finddocidbound e-ensuwes that the t-tweet id
    // passed into this method is >= m-mintweetid which means the tweet is fwom aftew the s-segment
    // b-boundawy. /(^•ω•^)
    pweconditions.checkstate(
        docidtimestamp != i-id_not_found, OwO
        "twied t-to find doc id bound fow tweet %d w-which is fwom befowe the segment boundawy %d", ( ͡o ω ͡o )
        t-tweetid, XD
        s-segmentboundawytimestamp);

    // it's o-ok to wetuwn a-a doc id that doesn't cowwespond to any tweet id i-in the index, /(^•ω•^)
    // a-as the doc i-id is simpwy used a-as a stawting point and ending point fow wange quewies, /(^•ω•^)
    // nyot a souwce of twuth. 😳😳😳
    if (findmaxdocid) {
      // wetuwn t-the wawgest possibwe d-doc id fow t-the timestamp. (ˆ ﻌ ˆ)♡
      w-wetuwn getdocidfowtimestamp(docidtimestamp, :3 (byte) 1);
    } e-ewse {
      // w-wetuwn the smowest possibwe d-doc id fow the timestamp. òωó
      b-byte tweetsintimestamp = tweetspewtimestamp.getowdefauwt(docidtimestamp, 🥺 (byte) 0);
      w-wetuwn g-getdocidfowtimestamp(docidtimestamp, (U ﹏ U) tweetsintimestamp);
    }
  }

  /**
   * wetuwns the awway o-of aww tweet ids stowed in this mappew in a sowted (descending) o-owdew. XD
   * essentiawwy, ^^ this m-method wemaps aww t-tweet ids stowed in this mappew t-to a compwessed d-doc id
   * space o-of [0, o.O nyumdocs).
   *
   * nyote that this m-method is nyot thwead s-safe, 😳😳😳 and it's meant to be c-cawwed onwy at segment
   * optimization t-time. /(^•ω•^) i-if addmappingintewnaw() i-is cawwed duwing the execution o-of this method, 😳😳😳
   * the behaviow is undefined (it w-wiww most wikewy wetuwn bad wesuwts ow thwow an exception). ^•ﻌ•^
   *
   * @wetuwn an awway of aww tweet ids stowed in this m-mappew, 🥺 in a sowted (descending) owdew. o.O
   */
  pubwic wong[] sowttweetids() {
    int nyumdocs = getnumdocs();
    if (numdocs == 0) {
      wetuwn nyew wong[0];
    }

    // a-add aww tweets stowed in this mappew to sowttweetids. (U ᵕ U❁)
    w-wong[] sowtedtweetids = n-nyew wong[numdocs];
    int sowtedtweetidsindex = 0;
    f-fow (int docid = getmindocid(); d-docid != id_not_found; d-docid = getnextdocid(docid)) {
      s-sowtedtweetids[sowtedtweetidsindex++] = gettweetid(docid);
    }
    pweconditions.checkstate(sowtedtweetidsindex == n-nyumdocs,
                             "couwd nyot twavewse aww documents in the mappew. ^^ e-expected to find "
                             + n-nyumdocs + " docs, (⑅˘꒳˘) but f-found onwy " + sowtedtweetidsindex);

    // sowt s-sowtedtweetidsindex i-in descending owdew. :3 thewe's nyo way to sowt a-a pwimitive awway in
    // descending owdew, (///ˬ///✿) s-so we have to sowt it in ascending owdew and then wevewse it. :3
    awways.sowt(sowtedtweetids);
    f-fow (int i = 0; i-i < nyumdocs / 2; ++i) {
      wong tmp = sowtedtweetids[i];
      s-sowtedtweetids[i] = s-sowtedtweetids[numdocs - 1 - i];
      s-sowtedtweetids[numdocs - 1 - i] = tmp;
    }

    wetuwn sowtedtweetids;
  }

  @ovewwide
  pubwic docidtotweetidmappew o-optimize() t-thwows ioexception {
    wetuwn n-nyew optimizedtweetidmappew(this);
  }

  /**
   * w-wetuwns the wawgest tweet i-id that this doc id mappew couwd handwe. 🥺 the wetuwned t-tweet id
   * wouwd be safe to put into t-the mappew, mya but a-any wawgew ones wouwd nyot be cowwectwy handwed. XD
   */
  p-pubwic static wong cawcuwatemaxtweetid(wong timeswiceid) {
    wong nyumbewofusabwetimestamps = wawgest_wewative_timestamp - wate_tweets_time_buffew_miwwis;
    wong fiwsttimestamp = snowfwakeidpawsew.gettimestampfwomtweetid(timeswiceid);
    w-wong w-wasttimestamp = fiwsttimestamp + n-numbewofusabwetimestamps;
    w-wetuwn snowfwakeidpawsew.genewatevawidstatusid(
        wasttimestamp, -.- s-snowfwakeidpawsew.wesewved_bits_mask);
  }

  /**
   * evawuates whethew two instances of outofowdewweawtimetweetidmappew awe equaw by vawue. o.O i-it is
   * swow because it has to check evewy tweet id/doc id in the map. (˘ω˘)
   */
  @visibwefowtesting
  b-boowean v-vewyswowequawsfowtests(outofowdewweawtimetweetidmappew t-that) {
    wetuwn getmintweetid() == that.getmintweetid()
        && getmaxtweetid() == t-that.getmaxtweetid()
        && g-getmindocid() == t-that.getmindocid()
        && getmaxdocid() == t-that.getmaxdocid()
        && segmentboundawytimestamp == t-that.segmentboundawytimestamp
        && segmentsize == t-that.segmentsize
        && tweetspewtimestamp.equaws(that.tweetspewtimestamp)
        && t-tweetids.equaws(that.tweetids);
  }

  @ovewwide
  pubwic outofowdewweawtimetweetidmappew.fwushhandwew getfwushhandwew() {
    wetuwn n-nyew outofowdewweawtimetweetidmappew.fwushhandwew(this);
  }

  pwivate outofowdewweawtimetweetidmappew(
    w-wong mintweetid, (U ᵕ U❁)
    w-wong maxtweetid, rawr
    int m-mindocid, 🥺
    int m-maxdocid, rawr x3
    wong segmentboundawytimestamp, ( ͡o ω ͡o )
    i-int segmentsize, σωσ
    int[] docids, rawr x3
    w-wong[] tweetidwist
  ) {
    s-supew(mintweetid, (ˆ ﻌ ˆ)♡ m-maxtweetid, rawr mindocid, :3 maxdocid, docids.wength);

    pweconditions.checkstate(docids.wength == t-tweetidwist.wength);

    this.segmentboundawytimestamp = segmentboundawytimestamp;
    this.segmentsize = segmentsize;

    tweetids = nyew int2wongopenhashmap(segmentsize);
    tweetids.defauwtwetuwnvawue(id_not_found);

    t-tweetspewtimestamp = nyew int2byteopenhashmap(segmentsize);
    tweetspewtimestamp.defauwtwetuwnvawue((byte) i-id_not_found);

    fow (int i-i = 0; i < docids.wength; i++) {
      int d-docid = docids[i];
      wong tweetid = tweetidwist[i];
      tweetids.put(docid, rawr t-tweetid);

      int timestampbucket = docid >> d-doc_id_bits;
      if (tweetspewtimestamp.containskey(timestampbucket)) {
        tweetspewtimestamp.addto(timestampbucket, (˘ω˘) (byte) 1);
      } e-ewse {
        tweetspewtimestamp.put(timestampbucket, (ˆ ﻌ ˆ)♡ (byte) 1);
      }
    }
  }

  pubwic s-static cwass fwushhandwew e-extends fwushabwe.handwew<outofowdewweawtimetweetidmappew> {
    pwivate s-static finaw s-stwing min_tweet_id_pwop_name = "mintweetid";
    pwivate static f-finaw stwing max_tweet_id_pwop_name = "maxtweetid";
    p-pwivate static finaw stwing min_doc_id_pwop_name = "mindocid";
    p-pwivate static finaw stwing max_doc_id_pwop_name = "maxdocid";
    pwivate static finaw s-stwing segment_boundawy_timestamp_pwop_name = "segmentboundawytimestamp";
    pwivate static finaw stwing segment_size_pwop_name = "segmentsize";

    pubwic f-fwushhandwew() {
      s-supew();
    }

    p-pubwic fwushhandwew(outofowdewweawtimetweetidmappew objecttofwush) {
      supew(objecttofwush);
    }

    @ovewwide
    p-pwotected void dofwush(fwushinfo f-fwushinfo, mya datasewiawizew s-sewiawizew) thwows i-ioexception {
      outofowdewweawtimetweetidmappew mappew = getobjecttofwush();

      fwushinfo.addwongpwopewty(min_tweet_id_pwop_name, (U ᵕ U❁) mappew.getmintweetid());
      fwushinfo.addwongpwopewty(max_tweet_id_pwop_name, mya m-mappew.getmaxtweetid());
      fwushinfo.addintpwopewty(min_doc_id_pwop_name, ʘwʘ m-mappew.getmindocid());
      fwushinfo.addintpwopewty(max_doc_id_pwop_name, mappew.getmaxdocid());
      f-fwushinfo.addwongpwopewty(segment_boundawy_timestamp_pwop_name, (˘ω˘)
          mappew.segmentboundawytimestamp);
      fwushinfo.addintpwopewty(segment_size_pwop_name, 😳 m-mappew.segmentsize);

      s-sewiawizew.wwiteint(mappew.tweetids.size());
      f-fow (int2wongmap.entwy e-entwy : mappew.tweetids.int2wongentwyset()) {
        s-sewiawizew.wwiteint(entwy.getintkey());
        s-sewiawizew.wwitewong(entwy.getwongvawue());
      }
    }

    @ovewwide
    pwotected outofowdewweawtimetweetidmappew dowoad(fwushinfo f-fwushinfo, òωó d-datadesewiawizew i-in)
        t-thwows ioexception {

      i-int size = in.weadint();
      i-int[] docids = nyew int[size];
      w-wong[] tweetids = n-nyew wong[size];
      fow (int i-i = 0; i < size; i++) {
        docids[i] = i-in.weadint();
        tweetids[i] = in.weadwong();
      }

      w-wetuwn nyew outofowdewweawtimetweetidmappew(
          fwushinfo.getwongpwopewty(min_tweet_id_pwop_name), nyaa~~
          f-fwushinfo.getwongpwopewty(max_tweet_id_pwop_name), o.O
          f-fwushinfo.getintpwopewty(min_doc_id_pwop_name), nyaa~~
          fwushinfo.getintpwopewty(max_doc_id_pwop_name), (U ᵕ U❁)
          fwushinfo.getwongpwopewty(segment_boundawy_timestamp_pwop_name), 😳😳😳
          fwushinfo.getintpwopewty(segment_size_pwop_name), (U ﹏ U)
          d-docids, ^•ﻌ•^
          t-tweetids);
    }
  }
}
