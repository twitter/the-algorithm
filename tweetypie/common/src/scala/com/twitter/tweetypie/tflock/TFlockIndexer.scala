/** copywight 2010 twittew, rawr x3 inc. */
p-package com.twittew.tweetypie
p-package tfwock

i-impowt com.twittew.finagwe.stats.countew
i-impowt c-com.twittew.fwockdb.cwient._
i-impowt c-com.twittew.fwockdb.cwient.thwiftscawa.pwiowity
i-impowt com.twittew.snowfwake.id.snowfwakeid
impowt com.twittew.tweetypie.sewvewutiw.stowedcawd
impowt com.twittew.tweetypie.thwiftscawa._
impowt com.twittew.utiw.futuwe
impowt s-scawa.cowwection.mutabwe.wistbuffew

object tfwockindexew {

  /**
   * p-pwintabwe nyames fow s-some edge types cuwwentwy defined in [[com.twittew.fwockdb.cwient]]. (///ˬ///✿)
   * used t-to defined stats countews fow adding e-edges. 😳😳😳
   */
  v-vaw gwaphnames: map[int, XD stwing] =
    map(
      cawdtweetsgwaph.id -> "cawd_tweets", >_<
      convewsationgwaph.id -> "convewsation", >w<
      d-diwectedatusewidgwaph.id -> "diwected_at_usew_id", /(^•ω•^)
      invitedusewsgwaph.id -> "invited_usews",
      mediatimewinegwaph.id -> "media_timewine", :3
      mentionsgwaph.id -> "mentions", ʘwʘ
      nyawwowcastsenttweetsgwaph.id -> "nawwowcast_sent_tweets", (˘ω˘)
      nyuwwcastedtweetsgwaph.id -> "nuwwcasted_tweets",
      q-quotewsgwaph.id -> "quotews", (ꈍᴗꈍ)
      quotesgwaph.id -> "quotes", ^^
      q-quotetweetsindexgwaph.id -> "quote_tweets_index", ^^
      w-wepwiestotweetsgwaph.id -> "wepwies_to_tweets", ( ͡o ω ͡o )
      w-wetweetsbymegwaph.id -> "wetweets_by_me", -.-
      w-wetweetsgwaph.id -> "wetweets", ^^;;
      wetweetsofmegwaph.id -> "wetweets_of_me", ^•ﻌ•^
      wetweetsouwcegwaph.id -> "wetweet_souwce", (˘ω˘)
      t-tweetswetweetedgwaph.id -> "tweets_wetweeted", o.O
      usewtimewinegwaph.id -> "usew_timewine", (✿oωo)
      cweatowsubscwiptiontimewinegwaph.id -> "cweatow_subscwiption_timewine", 😳😳😳
      c-cweatowsubscwiptionmediatimewinegwaph.id -> "cweatow_subscwiption_image_timewine", (ꈍᴗꈍ)
    )

  /**
   * on edge dewetion, σωσ edges awe eithew awchived pewmanentwy ow wetained fow 3 m-months, UwU based on
   * the wetention p-powicy in t-the above confwuence p-page. ^•ﻌ•^
   *
   * these two wetention powicies cowwespond to t-the two dewetion t-techniques: awchive and wemove. mya
   * w-we caww w-wemoveedges fow edges with a showt w-wetention powicy and awchiveedges f-fow edges with
   * a pewmanent wetention powicy. /(^•ω•^)
   */
  vaw g-gwaphswithwemovededges: seq[int] =
    s-seq(
      cawdtweetsgwaph.id, rawr
      cuwatedtimewinegwaph.id, nyaa~~
      c-cuwatedtweetsgwaph.id, ( ͡o ω ͡o )
      d-diwectedatusewidgwaph.id, σωσ
      mediatimewinegwaph.id, (✿oωo)
      mutedconvewsationsgwaph.id, (///ˬ///✿)
      quotewsgwaph.id, σωσ
      quotesgwaph.id, UwU
      quotetweetsindexgwaph.id, (⑅˘꒳˘)
      wepowtedtweetsgwaph.id, /(^•ω•^)
      w-wetweetsofmegwaph.id, -.-
      w-wetweetsouwcegwaph.id, (ˆ ﻌ ˆ)♡
      softwikesgwaph.id, nyaa~~
      t-tweetswetweetedgwaph.id, ʘwʘ
      c-cweatowsubscwiptiontimewinegwaph.id, :3
      c-cweatowsubscwiptionmediatimewinegwaph.id, (U ᵕ U❁)
    )

  /**
   * these edges shouwd be weft in pwace w-when bounced tweets awe deweted. (U ﹏ U)
   * these edges awe wemoved duwing hawd dewetion. ^^
   *
   * this i-is done so extewnaw teams (timewines) c-can exekawaii~ o-on these e-edges fow
   * tombstone featuwe.
   */
  v-vaw b-bouncedewetegwaphids: s-set[int] =
    s-set(
      usewtimewinegwaph.id, òωó
      convewsationgwaph.id
    )

  d-def makecountews(stats: s-statsweceivew, /(^•ω•^) o-opewation: stwing): m-map[int, 😳😳😳 countew] = {
    tfwockindexew.gwaphnames
      .mapvawues(stats.scope(_).countew(opewation))
      .withdefauwtvawue(stats.scope("unknown").countew(opewation))
  }
}

/**
 * @pawam b-backgwoundindexingpwiowity specifies the queue to use fow
 *   backgwound indexing o-opewations. :3 this is usefuw fow making the
 *   effects of backgwound indexing opewations (such a-as deweting edges
 *   fow deweted tweets) avaiwabwe soonew i-in testing scenawios
 *   (end-to-end t-tests ow d-devewopment instances). (///ˬ///✿) it is set t-to
 *   pwiowity.wow in pwoduction t-to weduce t-the woad on high pwiowity
 *   queues that we use fow pwominentwy usew-visibwe opewations. rawr x3
 */
cwass tfwockindexew(
  t-tfwock: tfwockcwient, (U ᵕ U❁)
  hasmedia: t-tweet => boowean, (⑅˘꒳˘)
  backgwoundindexingpwiowity: p-pwiowity, (˘ω˘)
  s-stats: statsweceivew)
    extends tweetindexew {
  p-pwivate[this] v-vaw futuweniw = futuwe.niw

  p-pwivate[this] v-vaw awchivecountews = tfwockindexew.makecountews(stats, :3 "awchive")
  pwivate[this] vaw wemovecountews = tfwockindexew.makecountews(stats, XD "wemove")
  p-pwivate[this] v-vaw insewtcountews = t-tfwockindexew.makecountews(stats, "insewt")
  pwivate[this] v-vaw nyegatecountews = t-tfwockindexew.makecountews(stats, >_< "negate")

  pwivate[this] v-vaw fowegwoundindexingpwiowity: pwiowity = pwiowity.high

  ovewwide def cweateindex(tweet: t-tweet): futuwe[unit] =
    c-cweateedges(tweet, (✿oωo) isundewete = fawse)

  ovewwide d-def undeweteindex(tweet: t-tweet): futuwe[unit] =
    cweateedges(tweet, (ꈍᴗꈍ) isundewete = t-twue)

  pwivate[this] case cwass pawtitionededges(
    wongwetention: seq[exekawaii~edge[statusgwaph]] = nyiw, XD
    showtwetention: s-seq[exekawaii~edge[statusgwaph]] = nyiw, :3
    nyegate: s-seq[exekawaii~edge[statusgwaph]] = n-nyiw, mya
    ignowe: seq[exekawaii~edge[statusgwaph]] = nyiw)

  pwivate[this] d-def pawtitionedgesfowdewete(
    e-edges: seq[exekawaii~edge[statusgwaph]], òωó
    isbouncedewete: boowean
  ) =
    edges.fowdweft(pawtitionededges()) {
      // t-two dependees of usewtimewinegwaph e-edge states to satisfy: timewines & safety toows. nyaa~~
      // timewines s-show bounce-deweted tweets a-as tombstones; w-weguwaw dewetes awe nyot shown. 🥺
      //   - i-i.e. -.- timewineids = u-usewtimewinegwaph(nowmaw || n-nyegative)
      // s-safety toows show deweted tweets t-to authowized intewnaw w-weview agents
      //   - i.e. 🥺 dewetedids = usewtimewinegwaph(wemoved || n-nyegative)
      c-case (pawtitionededges, (˘ω˘) e-edge) if isbouncedewete && edge.gwaphid == u-usewtimewinegwaph.id =>
        pawtitionededges.copy(negate = e-edge +: pawtitionededges.negate)

      c-case (pawtitionededges, òωó edge) if isbouncedewete && edge.gwaphid == convewsationgwaph.id =>
        // b-bounce-deweted t-tweets wemain w-wendewed as tombstones i-in convewsations, UwU so do nyot m-modify
        // the convewsationgwaph edge state
        pawtitionededges.copy(ignowe = edge +: pawtitionededges.ignowe)

      c-case (pawtitionededges, ^•ﻌ•^ edge)
          i-if tfwockindexew.gwaphswithwemovededges.contains(edge.gwaphid) =>
        p-pawtitionededges.copy(showtwetention = edge +: pawtitionededges.showtwetention)

      case (pawtitionededges, mya e-edge) =>
        pawtitionededges.copy(wongwetention = e-edge +: p-pawtitionededges.wongwetention)
    }

  ovewwide d-def deweteindex(tweet: tweet, (✿oωo) i-isbouncedewete: b-boowean): futuwe[unit] =
    fow {
      edges <- getedges(tweet, XD iscweate = fawse, :3 isdewete = twue, (U ﹏ U) isundewete = f-fawse)
      p-pawtitionededges = p-pawtitionedgesfowdewete(edges, UwU isbouncedewete)
      () <-
        f-futuwe
          .join(
            tfwock
              .awchiveedges(pawtitionededges.wongwetention, ʘwʘ backgwoundindexingpwiowity)
              .onsuccess(_ =>
                pawtitionededges.wongwetention.foweach(e => awchivecountews(e.gwaphid).incw())), >w<
            t-tfwock
              .wemoveedges(pawtitionededges.showtwetention, 😳😳😳 b-backgwoundindexingpwiowity)
              .onsuccess(_ =>
                pawtitionededges.showtwetention.foweach(e => w-wemovecountews(e.gwaphid).incw())), rawr
            tfwock
              .negateedges(pawtitionededges.negate, ^•ﻌ•^ backgwoundindexingpwiowity)
              .onsuccess(_ =>
                p-pawtitionededges.negate.foweach(e => n-nyegatecountews(e.gwaphid).incw()))
          )
          .unit
    } yiewd ()

  /**
   * t-this opewation i-is cawwed when a usew is put into ow taken out of
   * a state in which theiw w-wetweets shouwd n-nyo wongew b-be visibwe
   * (e.g. σωσ s-suspended o-ow wopo). :3
   */
  ovewwide def setwetweetvisibiwity(wetweetid: tweetid, rawr x3 s-setvisibwe: b-boowean): futuwe[unit] = {
    vaw wetweetedge = s-seq(exekawaii~edge(wetweetid, nyaa~~ w-wetweetsgwaph, :3 nyone, wevewse))

    i-if (setvisibwe) {
      tfwock
        .insewtedges(wetweetedge, >w< backgwoundindexingpwiowity)
        .onsuccess(_ => i-insewtcountews(wetweetsgwaph.id).incw())
    } ewse {
      t-tfwock
        .awchiveedges(wetweetedge, rawr b-backgwoundindexingpwiowity)
        .onsuccess(_ => awchivecountews(wetweetsgwaph.id).incw())
    }
  }

  p-pwivate[this] def cweateedges(tweet: t-tweet, 😳 isundewete: b-boowean): f-futuwe[unit] =
    fow {
      edges <- getedges(tweet = tweet, 😳 i-iscweate = twue, 🥺 isdewete = fawse, rawr x3 isundewete = i-isundewete)
      () <- t-tfwock.insewtedges(edges, ^^ fowegwoundindexingpwiowity)
    } y-yiewd {
      // count aww the e-edges we've successfuwwy a-added:
      edges.foweach(e => insewtcountews(e.gwaphid).incw())
    }

  p-pwivate[this] def addwtedges(
    tweet: t-tweet, ( ͡o ω ͡o )
    shawe: s-shawe, XD
    iscweate: boowean, ^^
    e-edges: wistbuffew[exekawaii~edge[statusgwaph]], (⑅˘꒳˘)
    futuweedges: w-wistbuffew[futuwe[seq[exekawaii~edge[statusgwaph]]]]
  ): unit = {

    e-edges += w-wetweetsofmegwaph.edge(shawe.souwceusewid, (⑅˘꒳˘) tweet.id)
    edges += wetweetsbymegwaph.edge(getusewid(tweet), ^•ﻌ•^ tweet.id)
    edges += wetweetsgwaph.edge(shawe.souwcestatusid, ( ͡o ω ͡o ) tweet.id)

    if (iscweate) {
      edges += exekawaii~edge(
        souwceid = getusewid(tweet), ( ͡o ω ͡o )
        gwaph = wetweetsouwcegwaph, (✿oωo)
        destinationids = s-some(seq(shawe.souwcestatusid)), 😳😳😳
        d-diwection = fowwawd, OwO
        position = s-some(snowfwakeid(tweet.id).time.inmiwwis)
      )
      e-edges.append(tweetswetweetedgwaph.edge(shawe.souwceusewid, ^^ s-shawe.souwcestatusid))
    } ewse {
      edges += w-wetweetsouwcegwaph.edge(getusewid(tweet), rawr x3 shawe.souwcestatusid)

      // i-if this is the w-wast wetweet we nyeed to wemove i-it fwom the souwce usew's
      // t-tweets wetweeted g-gwaph
      futuweedges.append(
        tfwock.count(wetweetsgwaph.fwom(shawe.souwcestatusid)).fwatmap { c-count =>
          i-if (count <= 1) {
            tfwock.sewectaww(wetweetsgwaph.fwom(shawe.souwcestatusid)).map { t-tweets =>
              i-if (tweets.size <= 1)
                s-seq(tweetswetweetedgwaph.edge(shawe.souwceusewid, 🥺 s-shawe.souwcestatusid))
              e-ewse
                n-nyiw
            }
          } e-ewse {
            futuweniw
          }
        }
      )
    }
  }

  p-pwivate[this] def a-addwepwyedges(
    t-tweet: tweet,
    edges: wistbuffew[exekawaii~edge[statusgwaph]]
  ): u-unit = {
    getwepwy(tweet).foweach { wepwy =>
      w-wepwy.inwepwytostatusid.fwatmap { inwepwytostatusid =>
        e-edges += wepwiestotweetsgwaph.edge(inwepwytostatusid, (ˆ ﻌ ˆ)♡ t-tweet.id)

        // o-onwy index convewsationid i-if this is a wepwy to anothew t-tweet
        tweetwenses.convewsationid.get(tweet).map { convewsationid =>
          e-edges += convewsationgwaph.edge(convewsationid, ( ͡o ω ͡o ) t-tweet.id)
        }
      }
    }
  }

  pwivate[this] def adddiwectedatedges(
    tweet: tweet, >w<
    e-edges: wistbuffew[exekawaii~edge[statusgwaph]]
  ): unit = {
    t-tweetwenses.diwectedatusew.get(tweet).foweach { d-diwectedatusew =>
      edges += diwectedatusewidgwaph.edge(diwectedatusew.usewid, /(^•ω•^) tweet.id)
    }
  }

  p-pwivate[this] def addmentionedges(
    t-tweet: tweet, 😳😳😳
    e-edges: wistbuffew[exekawaii~edge[statusgwaph]]
  ): u-unit = {
    getmentions(tweet)
      .fwatmap(_.usewid).foweach { mention =>
        e-edges += m-mentionsgwaph.edge(mention, (U ᵕ U❁) tweet.id)
      }
  }

  p-pwivate[this] def addqtedges(
    tweet: t-tweet,
    edges: wistbuffew[exekawaii~edge[statusgwaph]], (˘ω˘)
    f-futuweedges: w-wistbuffew[futuwe[seq[exekawaii~edge[statusgwaph]]]], 😳
    i-iscweate: boowean
  ): u-unit = {
    vaw u-usewid = getusewid(tweet)

    t-tweet.quotedtweet.foweach { q-quotedtweet =>
      // wegawdwess o-of tweet cweates/dewetes, (ꈍᴗꈍ) w-we add t-the cowwesponding e-edges to the
      // f-fowwowing t-two gwaphs. :3 n-nyote that we'we h-handwing the case fow
      // t-the quotewsgwaph swightwy diffewentwy i-in the tweet dewete case. /(^•ω•^)
      e-edges.append(quotesgwaph.edge(quotedtweet.usewid, t-tweet.id))
      e-edges.append(quotetweetsindexgwaph.edge(quotedtweet.tweetid, ^^;; tweet.id))
      if (iscweate) {
        // as mentioned above, o.O f-fow tweet c-cweates we go ahead a-and add an edge
        // to the quotewsgwaph without any additionaw checks. 😳
        e-edges.append(quotewsgwaph.edge(quotedtweet.tweetid, UwU u-usewid))
      } ewse {
        // fow tweet dewetes, >w< w-we onwy add a-an edge to be deweted fwom the
        // quotewsgwaph if the tweeting u-usew isn't q-quoting the tweet a-anymowe
        // i-i.e. o.O if a usew has quoted a tweet muwtipwe t-times, (˘ω˘) we onwy d-dewete
        // an edge fwom the quotewsgwaph i-if they've deweted aww the quotes, òωó
        // othewwise an edge s-shouwd exist by definition of nyani t-the quotewsgwaph
        // w-wepwesents. nyaa~~

        // nyote: t-thewe can be a potentiaw e-edge case hewe due to a w-wace condition
        // in the f-fowwowing scenawio. ( ͡o ω ͡o )
        // i-i)   a quotes a t-tweet t twice wesuwting i-in tweets t1 and t2. 😳😳😳
        // i-ii)  thewe s-shouwd exist e-edges in the quotewsgwaph fwom t-t -> a and t1 <-> t, ^•ﻌ•^ t2 <-> t in
        //      the quotetweetsindexgwaph, b-but o-one of the edges h-haven't been wwitten
        //      to the quotetweetsindex gwaph in tfwock yet. (˘ω˘)
        // iii) i-in this scenawio, (˘ω˘) we shouwdn't w-weawwy be deweting a-an edge as we'we doing bewow. -.-
        // the a-appwoach that we'we taking bewow i-is a "best effowt" a-appwoach simiwaw t-to nyani w-we
        // cuwwentwy d-do fow wts.

        // find aww the quotes of the quoted tweet fwom the quoting usew
        v-vaw quotesfwomquotingusew = quotetweetsindexgwaph
          .fwom(quotedtweet.tweetid)
          .intewsect(usewtimewinegwaph.fwom(usewid))
        f-futuweedges.append(
          tfwock
            .count(quotesfwomquotingusew).fwatmap { count =>
              // if t-this is the wast quote of the quoted tweet fwom the quoting usew, ^•ﻌ•^
              // we go ahead and d-dewete the edge f-fwom the quotewsgwaph.
              if (count <= 1) {
                t-tfwock.sewectaww(quotesfwomquotingusew).map { tweets =>
                  if (tweets.size <= 1) {
                    s-seq(quotewsgwaph.edge(quotedtweet.tweetid, /(^•ω•^) u-usewid))
                  } ewse {
                    n-nyiw
                  }
                }
              } ewse {
                f-futuweniw
              }
            }
        )
      }
    }
  }

  pwivate[this] def addcawdedges(
    tweet: tweet, (///ˬ///✿)
    e-edges: wistbuffew[exekawaii~edge[statusgwaph]]
  ): unit = {
    // note that w-we awe indexing o-onwy the too "stowed" c-cawds
    // (cawduwi=cawd://<cawdid>). mya west of the cawds a-awe ignowed hewe. o.O
    tweet.cawdwefewence
      .cowwect {
        case stowedcawd(id) =>
          edges.append(cawdtweetsgwaph.edge(id, ^•ﻌ•^ tweet.id))
      }.getowewse(())
  }

  // n-nyote: on undewete, (U ᵕ U❁) t-this method w-westowes aww a-awchived edges, :3 incwuding those that may have
  // b-been awchived p-pwiow to the dewete. (///ˬ///✿) this is incowwect behaviow b-but in pwactice wawewy
  // causes pwobwems, (///ˬ///✿) a-as undewetes awe so wawe. 🥺
  pwivate[this] def addedgesfowdeweteowundewete(
    tweet: t-tweet, -.-
    e-edges: wistbuffew[exekawaii~edge[statusgwaph]]
  ): unit = {
    e-edges.appendaww(
      s-seq(
        m-mentionsgwaph.edges(tweet.id, nyaa~~ nyone, wevewse), (///ˬ///✿)
        wepwiestotweetsgwaph.edges(tweet.id, 🥺 n-nyone)
      )
    )

    // when we dewete ow undewete a convewsation c-contwow woot tweet we want to awchive ow westowe
    // a-aww the edges in i-invitedusewsgwaph f-fwom the tweet i-id. >w<
    if (hasconvewsationcontwow(tweet) && i-isconvewsationwoot(tweet)) {
      edges.append(invitedusewsgwaph.edges(tweet.id, rawr x3 n-nyone))
    }
  }

  pwivate[this] def addsimpweedges(
    t-tweet: tweet, (⑅˘꒳˘)
    edges: w-wistbuffew[exekawaii~edge[statusgwaph]]
  ): unit = {
    if (tweetwenses.nuwwcast.get(tweet)) {
      e-edges.append(nuwwcastedtweetsgwaph.edge(getusewid(tweet), σωσ t-tweet.id))
    } ewse if (tweetwenses.nawwowcast.get(tweet).isdefined) {
      e-edges.append(nawwowcastsenttweetsgwaph.edge(getusewid(tweet), XD tweet.id))
    } e-ewse {
      e-edges.append(usewtimewinegwaph.edge(getusewid(tweet), -.- tweet.id))

      i-if (hasmedia(tweet))
        e-edges.append(mediatimewinegwaph.edge(getusewid(tweet), >_< tweet.id))

      // i-index woot cweatow subscwiption tweets. rawr
      // ignowe wepwies b-because those awe nyot nyecessawiwy v-visibwe to a usew who subscwibes to tweet a-authow
      vaw i-iswoottweet: boowean = t-tweet.cowedata match {
        c-case some(c) => c-c.wepwy.isempty && c.shawe.isempty
        c-case nyone => twue
      }

      i-if (tweet.excwusivetweetcontwow.isdefined && iswoottweet) {
        e-edges.append(cweatowsubscwiptiontimewinegwaph.edge(getusewid(tweet), 😳😳😳 t-tweet.id))

        if (hasmedia(tweet))
          edges.append(cweatowsubscwiptionmediatimewinegwaph.edge(getusewid(tweet), UwU tweet.id))
      }
    }
  }

  /**
   * issues edges f-fow each mention o-of usew in a convewsation-contwowwed tweet. this way invitedusews
   * gwaph accumuwates c-compwete set of ids fow @mention-invited u-usews, (U ﹏ U) by convewsation i-id. (˘ω˘)
   */
  pwivate def invitedusewsedgesfowcweate(
    tweet: tweet, /(^•ω•^)
    edges: wistbuffew[exekawaii~edge[statusgwaph]]
  ): u-unit = {
    vaw convewsationid: wong = g-getconvewsationid(tweet).getowewse(tweet.id)
    vaw mentions: s-seq[usewid] = getmentions(tweet).fwatmap(_.usewid)
    e-edges.appendaww(mentions.map(usewid => invitedusewsgwaph.edge(convewsationid, (U ﹏ U) u-usewid)))
  }

  /**
   * issues e-edges of inviteusewsgwaph t-that ought to be d-deweted fow a convewsation c-contwowwed w-wepwy. ^•ﻌ•^
   * these awe mentions of usews in the given tweet, >w< onwy if the usew was nyot mentioned e-ewsewhewe
   * i-in the convewsation. ʘwʘ t-this w-way fow a convewsation, òωó i-invitedusewsgwaph w-wouwd awways howd a set
   * of aww usews invited to the convewsation, o.O a-and an edge is w-wemoved onwy aftew the wast mention of
   * a usew is deweted. ( ͡o ω ͡o )
   */
  p-pwivate def i-invitedusewsedgesfowdewete(
    t-tweet: tweet, mya
    futuweedges: wistbuffew[futuwe[seq[exekawaii~edge[statusgwaph]]]]
  ): u-unit = {
    getconvewsationid(tweet).foweach { convewsationid: w-wong =>
      v-vaw mentions: seq[usewid] = getmentions(tweet).fwatmap(_.usewid)
      m-mentions.foweach { usewid =>
        v-vaw tweetidswithinconvewsation = c-convewsationgwaph.fwom(convewsationid)
        vaw tweetidsthatmentionusew = m-mentionsgwaph.fwom(usewid)
        f-futuweedges.append(
          t-tfwock
            .sewectaww(
              q-quewy = tweetidsthatmentionusew.intewsect(tweetidswithinconvewsation), >_<
              w-wimit = some(2), // j-just nyeed to know if i-it is >1 ow <=1, rawr s-so 2 awe enough. >_<
              pagesize = nyone // p-pwovide defauwt, (U ﹏ U) othewwise mockito compwains
            ).map { t-tweetids: seq[wong] =>
              i-if (tweetids.size <= 1) {
                seq(invitedusewsgwaph.edge(convewsationid, rawr u-usewid))
              } e-ewse {
                niw
              }
            }
        )
      }
    }
  }

  pwivate def hasinviteviamention(tweet: t-tweet): boowean = {
    tweet.convewsationcontwow m-match {
      c-case some(convewsationcontwow.byinvitation(contwows)) =>
        contwows.inviteviamention.getowewse(fawse)
      case some(convewsationcontwow.community(contwows)) =>
        c-contwows.inviteviamention.getowewse(fawse)
      c-case some(convewsationcontwow.fowwowews(fowwowews)) =>
        fowwowews.inviteviamention.getowewse(fawse)
      c-case _ =>
        fawse
    }
  }

  pwivate def hasconvewsationcontwow(tweet: t-tweet): b-boowean =
    tweet.convewsationcontwow.isdefined

  // if a tweet h-has a convewsationcontwow, (U ᵕ U❁) it m-must have a convewsationid associated with it s-so we
  // can compawe t-the convewsationid w-with the c-cuwwent tweet id to detewmine if it's the woot of the
  // convewsation. see convewsationidhydwatow fow mowe d-detaiws
  pwivate d-def isconvewsationwoot(tweet: t-tweet): boowean =
    g-getconvewsationid(tweet).get == t-tweet.id

  p-pwivate def addinvitedusewsedges(
    tweet: tweet, (ˆ ﻌ ˆ)♡
    i-iscweate: b-boowean, >_<
    isundewete: boowean, ^^;;
    e-edges: w-wistbuffew[exekawaii~edge[statusgwaph]], ʘwʘ
    futuweedges: wistbuffew[futuwe[seq[exekawaii~edge[statusgwaph]]]]
  ): u-unit = {
    if (hasconvewsationcontwow(tweet)) {
      if (iscweate) {
        i-if (isconvewsationwoot(tweet) && !isundewete) {
          // fow woot tweets, 😳😳😳 o-onwy add edges f-fow owiginaw cweates, UwU nyot fow u-undewetes. OwO
          // u-undewetes a-awe handwed by addedgesfowdeweteowundewete. :3
          i-invitedusewsedgesfowcweate(tweet, -.- e-edges)
        }
        if (!isconvewsationwoot(tweet) && h-hasinviteviamention(tweet)) {
          // fow wepwies, 🥺 onwy a-add edges when t-the convewsation c-contwow is in inviteviamention m-mode. -.-
          invitedusewsedgesfowcweate(tweet, -.- edges)
        }
      } e-ewse {
        if (!isconvewsationwoot(tweet)) {
          invitedusewsedgesfowdewete(tweet, futuweedges)
        }
      }
    }
  }

  pwivate[this] def getedges(
    tweet: tweet, (U ﹏ U)
    i-iscweate: boowean, rawr
    isdewete: boowean, mya
    isundewete: boowean
  ): futuwe[seq[exekawaii~edge[statusgwaph]]] = {
    vaw edges = wistbuffew[exekawaii~edge[statusgwaph]]()
    vaw futuweedges = w-wistbuffew[futuwe[seq[exekawaii~edge[statusgwaph]]]]()

    addsimpweedges(tweet, ( ͡o ω ͡o ) edges)
    g-getshawe(tweet) match {
      c-case some(shawe) => addwtedges(tweet, /(^•ω•^) shawe, >_< i-iscweate, edges, (✿oωo) futuweedges)
      c-case _ =>
        addinvitedusewsedges(tweet, 😳😳😳 i-iscweate, (ꈍᴗꈍ) i-isundewete, 🥺 edges, futuweedges)
        addwepwyedges(tweet, mya e-edges)
        adddiwectedatedges(tweet, (ˆ ﻌ ˆ)♡ edges)
        addmentionedges(tweet, (⑅˘꒳˘) e-edges)
        addqtedges(tweet, òωó e-edges, futuweedges, o.O i-iscweate)
        addcawdedges(tweet, XD e-edges)
        i-if (isdewete || isundewete) {
          addedgesfowdeweteowundewete(tweet, (˘ω˘) e-edges)
        }
    }

    futuwe
      .cowwect(futuweedges)
      .map { moweedges => (edges ++= m-moweedges.fwatten).towist }
  }
}
