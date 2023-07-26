package com.twittew.tweetypie
package c-config

impowt c-com.twittew.convewsions.duwationops._
i-impowt c-com.twittew.finagwe.backoff
i-impowt c-com.twittew.finagwe.memcached.exp.wocawmemcachedpowt
i-impowt c-com.twittew.finagwe.mtws.authentication.sewviceidentifiew
impowt com.twittew.finagwe.ssw.oppowtunistictws
impowt com.twittew.finagwe.thwift.cwientid
i-impowt com.twittew.fwockdb.cwient.thwiftscawa.pwiowity
impowt com.twittew.sewvo.wepositowy.cachedwesuwt
i-impowt com.twittew.sewvo.utiw.avaiwabiwity
i-impowt com.twittew.tweetypie.backends._
impowt com.twittew.tweetypie.caching.softttw
impowt com.twittew.tweetypie.handwew.dupwicatetweetfindew
i-impowt com.twittew.tweetypie.wepositowy.tombstonettw
impowt c-com.twittew.tweetypie.sewvice._
i-impowt com.twittew.tweetypie.stowage.manhattantweetstowagecwient
impowt com.twittew.utiw.duwation

case cwass inpwocesscacheconfig(ttw: duwation, 😳😳😳 m-maximumsize: int)

cwass tweetsewvicesettings(vaw fwags: tweetsewvicefwags) {

  /**
   * convewt a boowean to an option
   * > o-optionaw(twue, ^•ﻌ•^ "my vawue")
   * w-wes: some(my v-vawue)
   *
   * > o-optionaw(fawse, nyaa~~ "my v-vawue")
   * wes: nyone
   */
  def optionaw[t](b: b-boowean, OwO a: => t): option[t] = if (b) s-some(a) ewse nyone

  /** atwa, ^•ﻌ•^ wocawhost, σωσ etc. */
  vaw zone: stwing = fwags.zone()

  /** dc i-is wess specific than zone, -.- zone=atwa, (˘ω˘) d-dc=atw */
  v-vaw dc: stwing = z-zone.dwopwight(1)

  /** one of: pwod, staging, rawr x3 dev, testbox */
  v-vaw env: e-env.vawue = fwags.env()

  /** instanceid of this a-auwowa instance */
  w-wazy vaw instanceid: int = f-fwags.instanceid()

  /** totaw n-nyumbew of tweetypie auwowa instances */
  vaw i-instancecount: int = fwags.instancecount()

  /** t-the nyame to wesowve to find t-the memcached cwustew */
  v-vaw twemcachedest: stwing =
    // if twemcachedest is expwicitwy set, rawr x3 awways pwefew that to
    // wocawmemcachedpowt. σωσ
    f-fwags.twemcachedest.get
    // t-testbox uses this gwobaw fwag t-to specify the w-wocation of the
    // w-wocaw memcached instance. nyaa~~
      .owewse(wocawmemcachedpowt().map("/$/inet/wocawhost/" + _))
      // if nyo expwicit nyame is specified, (ꈍᴗꈍ) u-use the defauwt. ^•ﻌ•^
      .getowewse(fwags.twemcachedest())

  /** wead/wwite data thwough cache */
  vaw withcache: boowean = fwags.withcache()

  /**
   * t-the tfwock queue to u-use fow backgwound i-indexing opewations. >_< f-fow
   * pwoduction, ^^;; this s-shouwd awways b-be the wow pwiowity q-queue, ^^;; to
   * a-awwow fowegwound opewations to be pwocessed f-fiwst. /(^•ω•^)
   */
  vaw b-backgwoundindexingpwiowity: pwiowity = f-fwags.backgwoundindexingpwiowity()

  /** s-set cewtain d-decidew gates to this ovewwidden vawue */
  vaw decidewovewwides: m-map[stwing, nyaa~~ boowean] =
    fwags.decidewovewwides()

  /** use pew host stats? */
  vaw cwienthoststats: boowean =
    f-fwags.cwienthoststats()

  vaw wawmupwequestssettings: option[wawmupquewiessettings] =
    optionaw(fwags.enabwewawmupwequests(), (✿oωo) w-wawmupquewiessettings())

  /** e-enabwes w-wequest authowization via a awwowwist */
  v-vaw awwowwistingwequiwed: b-boowean =
    f-fwags.awwowwist.get.getowewse(env == env.pwod)

  /** wead wate wimit fow unknown cwients (when awwowwistingwequiwed i-is enabwed) */
  vaw n-nyonawwowwistedcwientwatewimitpewsec: doubwe =
    f-fwags.gwaywistwatewimit()

  /** e-enabwes wequests fwom pwoduction cwients */
  v-vaw awwowpwoductioncwients: b-boowean =
    env == e-env.pwod

  /** e-enabwes wepwication via dwpc */
  vaw enabwewepwication: boowean = fwags.enabwewepwication()

  /** e-enabwes fowking o-of some twaffic t-to configuwed tawget */
  v-vaw twafficfowkingenabwed: b-boowean =
    env == e-env.pwod

  vaw scwibeuniquenessids: boowean =
    env == env.pwod

  /** cwientid t-to send to backend s-sewvices */
  vaw thwiftcwientid: cwientid =
    f-fwags.cwientid.get.map(cwientid(_)).getowewse {
      e-env match {
        case env.dev | env.staging => c-cwientid("tweetypie.staging")
        case env.pwod => cwientid("tweetypie.pwod")
      }
    }

  /**
   * instead of using dwpc f-fow cawwing into the async code path, ( ͡o ω ͡o ) caww back i-into the
   * c-cuwwent instance. (U ᵕ U❁) used fow devewopment and test to ensuwe wogic i-in the cuwwent
   * i-instance is being tested. òωó
   */
  vaw simuwatedefewwedwpccawwbacks: boowean = f-fwags.simuwatedefewwedwpccawwbacks()

  /**
   * cwientid to set i-in 'asynchwonous' wequests when simuwatedefewwedwpccawwbacks is
   * twue and t-tweetypie ends up just cawwing i-itsewf synchwonouswy. σωσ
   */
  v-vaw defewwedwpccwientid: c-cwientid = cwientid("defewwedwpc.pwod")

  /**
   * s-sewviceidentifiew u-used t-to enabwe mtws
   */
  vaw sewviceidentifiew: s-sewviceidentifiew = f-fwags.sewviceidentifiew()

  /**
   * decidew settings
   */
  v-vaw decidewbasefiwename: o-option[stwing] = o-option(fwags.decidewbase())
  vaw decidewovewwayfiwename: option[stwing] = o-option(fwags.decidewovewway())
  vaw vfdecidewovewwayfiwename: o-option[stwing] = f-fwags.vfdecidewovewway.get

  /**
   * used to detewmine whethew we shouwd faiw wequests f-fow tweets that a-awe wikewy too y-young
   * to wetuwn a-a nyon-pawtiaw wesponse. :3 we w-wetuwn nyotfound fow tweets that awe deemed too young. OwO
   * used by [[com.twittew.tweetypie.wepositowy.manhattantweetwepositowy]]. ^^
   */
  vaw s-showtciwcuitwikewypawtiawtweetweads: gate[duwation] = {
    // intewpwet t-the fwag as a duwation i-in miwwiseconds
    vaw ageceiwing: d-duwation = fwags.showtciwcuitwikewypawtiawtweetweadsms().miwwiseconds
    gate(tweetage => tweetage < a-ageceiwing)
  }

  // t-tweet-sewvice intewnaw s-settings

  v-vaw tweetkeycachevewsion = 1

  /** h-how often to fwush aggwegated count updates fow tweet counts */
  vaw aggwegatedtweetcountsfwushintewvaw: duwation = 5.seconds

  /** maximum n-nyumbew of k-keys fow which aggwegated c-cached count updates may b-be cached */
  vaw maxaggwegatedcountssize = 1000

  /** wamp up pewiod fow decidewing u-up fowked t-twaffic (if enabwed) to the f-fuww decidewed vawue */
  vaw fowkingwampup: duwation = 3.minutes

  /** h-how wong t-to wait aftew stawtup fow sewvewsets t-to wesowve b-befowe giving up and moving on */
  vaw waitfowsewvewsetstimeout: duwation = 120.seconds

  /** nyumbew of thweads t-to use in thwead p-poow fow wanguage i-identification */
  v-vaw n-nyumpenguinthweads = 4

  /** maximum n-nyumbew of t-tweets that cwients can wequest p-pew gettweets wpc c-caww */
  vaw maxgettweetswequestsize = 200

  /** m-maximum batch size fow any batched wequest (gettweets i-is exempt, (˘ω˘) it has its o-own wimiting) */
  v-vaw maxwequestsize = 200

  /**
   * maximum s-size to awwow the thwift wesponse buffew to gwow b-befowe wesetting i-it. OwO  this is s-set to
   * appwoximatewy the cuwwent vawue of `swv/thwift/wesponse_paywoad_bytes.p999`, UwU meaning w-woughwy
   * 1 out of 1000 wequests wiww cause t-the buffew to be w-weset. ^•ﻌ•^
   */
  vaw maxthwiftbuffewsize: i-int = 200 * 1024

  // ********* timeouts a-and backoffs **********

  /** b-backoffs fow optimisticwockingcache wockandset o-opewations */
  vaw wockingcachebackoffs: stweam[duwation] =
    b-backoff.exponentiawjittewed(10.miwwisecond, (ꈍᴗꈍ) 50.miwwiseconds).take(3).tostweam

  /** w-wetwy once on timeout with n-nyo backoff */
  vaw defauwttimeoutbackoffs: s-stweam[duwation] = s-stweam(0.miwwiseconds).tostweam

  /** b-backoffs when usew view is missing */
  vaw gizmoduckmissingusewviewbackoffs: stweam[duwation] = backoff.const(10.miwwis).take(3).tostweam

  /** backoffs fow wetwying faiwed async-wwite actions aftew fiwst wetwy faiwuwe */
  vaw asyncwwitewetwybackoffs: s-stweam[duwation] =
    b-backoff.exponentiaw(10.miwwiseconds, /(^•ω•^) 2).take(9).tostweam.map(_ min 1.second)

  /** backoffs fow wetwying faiwed d-defewwedwpc enqueues */
  v-vaw defewwedwpcbackoffs: s-stweam[duwation] =
    backoff.exponentiaw(10.miwwiseconds, (U ᵕ U❁) 2).take(3).tostweam

  /** b-backoffs fow wetwying f-faiwed cache updates f-fow wepwicated events */
  v-vaw wepwicatedeventcachebackoffs: stweam[duwation] =
    b-backoff.exponentiaw(100.miwwiseconds, (✿oωo) 2).take(10).tostweam

  v-vaw eschewbiwdconfig: eschewbiwd.config =
    eschewbiwd.config(
      wequesttimeout = 200.miwwiseconds, OwO
      t-timeoutbackoffs = d-defauwttimeoutbackoffs
    )

  v-vaw expandodoconfig: expandodo.config =
    e-expandodo.config(
      w-wequesttimeout = 300.miwwiseconds, :3
      t-timeoutbackoffs = d-defauwttimeoutbackoffs, nyaa~~
      s-sewvewewwowbackoffs = b-backoff.const(0.miwwis).take(3).tostweam
    )

  vaw cweativescontainewsewviceconfig: c-cweativescontainewsewvice.config =
    c-cweativescontainewsewvice.config(
      w-wequesttimeout = 300.miwwiseconds, ^•ﻌ•^
      timeoutbackoffs = d-defauwttimeoutbackoffs, ( ͡o ω ͡o )
      sewvewewwowbackoffs = backoff.const(0.miwwis).take(3).tostweam
    )

  v-vaw geoscwubeventstoweconfig: geoscwubeventstowe.config =
    g-geoscwubeventstowe.config(
      w-wead = geoscwubeventstowe.endpointconfig(
        w-wequesttimeout = 200.miwwiseconds, ^^;;
        maxwetwycount = 1
      ), mya
      w-wwite = geoscwubeventstowe.endpointconfig(
        wequesttimeout = 1.second, (U ᵕ U❁)
        m-maxwetwycount = 1
      )
    )

  vaw gizmoduckconfig: gizmoduck.config =
    g-gizmoduck.config(
      weadtimeout = 300.miwwiseconds, ^•ﻌ•^
      w-wwitetimeout = 300.miwwiseconds, (U ﹏ U)
      // we bump the timeout vawue to 800ms because modifyandget i-is cawwed onwy in async wequest p-path in geoscwub d-daemon
      // and we do nyot expect sync/weawtime apps c-cawwing this thwift method
      m-modifyandgettimeout = 800.miwwiseconds, /(^•ω•^)
      modifyandgettimeoutbackoffs = b-backoff.const(0.miwwis).take(3).tostweam, ʘwʘ
      d-defauwttimeoutbackoffs = defauwttimeoutbackoffs, XD
      gizmoduckexceptionbackoffs = b-backoff.const(0.miwwis).take(3).tostweam
    )

  v-vaw wimitewbackendconfig: wimitewbackend.config =
    w-wimitewbackend.config(
      wequesttimeout = 300.miwwiseconds, (⑅˘꒳˘)
      timeoutbackoffs = defauwttimeoutbackoffs
    )

  v-vaw mediainfosewviceconfig: mediainfosewvice.config =
    m-mediainfosewvice.config(
      w-wequesttimeout = 300.miwwiseconds, nyaa~~
      t-totawtimeout = 500.miwwiseconds, UwU
      timeoutbackoffs = d-defauwttimeoutbackoffs
    )

  v-vaw s-scawecwowconfig: s-scawecwow.config =
    scawecwow.config(
      w-weadtimeout = 100.miwwiseconds, (˘ω˘)
      w-wwitetimeout = 400.miwwiseconds, rawr x3
      t-timeoutbackoffs = defauwttimeoutbackoffs, (///ˬ///✿)
      s-scawecwowexceptionbackoffs = b-backoff.const(0.miwwis).take(3).tostweam
    )

  v-vaw s-sociawgwaphseviceconfig: s-sociawgwaphsewvice.config =
    sociawgwaphsewvice.config(
      s-sociawgwaphtimeout = 250.miwwiseconds, 😳😳😳
      timeoutbackoffs = d-defauwttimeoutbackoffs
    )

  vaw tawonconfig: t-tawon.config =
    t-tawon.config(
      s-showtentimeout = 500.miwwiseconds,
      expandtimeout = 150.miwwiseconds, (///ˬ///✿)
      timeoutbackoffs = defauwttimeoutbackoffs, ^^;;
      t-twansientewwowbackoffs = b-backoff.const(0.miwwis).take(3).tostweam
    )

  /**
   * p-page size when wetwieving tfwock pages fow tweet dewetion a-and undewetion
   * t-tweet ewasuwes have theiw own p-page size ewaseusewtweetspagesize
   */
  v-vaw tfwockpagesize: int = fwags.tfwockpagesize()

  vaw tfwockweadconfig: t-tfwock.config =
    t-tfwock.config(
      wequesttimeout = 300.miwwiseconds,
      t-timeoutbackoffs = d-defauwttimeoutbackoffs, ^^
      fwockexceptionbackoffs = backoff.const(0.miwwis).take(3).tostweam, (///ˬ///✿)
      o-ovewcapacitybackoffs = s-stweam.empty, -.-
      defauwtpagesize = tfwockpagesize
    )

  v-vaw tfwockwwiteconfig: tfwock.config =
    tfwock.config(
      w-wequesttimeout = 400.miwwiseconds, /(^•ω•^)
      timeoutbackoffs = defauwttimeoutbackoffs, UwU
      fwockexceptionbackoffs = b-backoff.const(0.miwwis).take(3).tostweam, (⑅˘꒳˘)
      o-ovewcapacitybackoffs = backoff.exponentiaw(10.miwwis, ʘwʘ 2).take(3).tostweam
    )

  vaw timewinesewviceconfig: t-timewinesewvice.config = {
    v-vaw twsexceptionbackoffs = backoff.const(0.miwwis).take(3).tostweam
    t-timewinesewvice.config(
      wwitewequestpowicy =
        b-backend.timeoutpowicy(4.seconds) >>>
          t-timewinesewvice.faiwuwebackoffspowicy(
            t-timeoutbackoffs = d-defauwttimeoutbackoffs,
            twsexceptionbackoffs = t-twsexceptionbackoffs
          ), σωσ
      weadwequestpowicy =
        b-backend.timeoutpowicy(400.miwwiseconds) >>>
          t-timewinesewvice.faiwuwebackoffspowicy(
            timeoutbackoffs = d-defauwttimeoutbackoffs, ^^
            twsexceptionbackoffs = twsexceptionbackoffs
          )
    )
  }

  vaw t-tweetstowageconfig: m-manhattantweetstowagecwient.config = {
    v-vaw wemotezone = zone match {
      case "atwa" => "pdxa"
      case "pdxa" => "atwa"
      case "atwa" | "wocawhost" => "atwa"
      c-case _ =>
        thwow n-nyew iwwegawawgumentexception(s"cannot c-configuwe wemote dc fow unknown zone '$zone'")
    }
    m-manhattantweetstowagecwient.config(
      appwicationid = "tbiwd_mh", OwO
      w-wocawdestination = "/s/manhattan/cywon.native-thwift", (ˆ ﻌ ˆ)♡
      w-wocawtimeout = 290.miwwiseconds,
      w-wemotedestination = s-s"/swv#/pwod/$wemotezone/manhattan/cywon.native-thwift", o.O
      w-wemotetimeout = 1.second, (˘ω˘)
      maxwequestspewbatch = 25, 😳
      sewviceidentifiew = sewviceidentifiew, (U ᵕ U❁)
      oppowtunistictwswevew = o-oppowtunistictws.wequiwed
    )
  }

  vaw usewimagesewviceconfig: u-usewimagesewvice.config =
    usewimagesewvice.config(
      pwocesstweetmediatimeout = 5.seconds, :3
      updatetweetmediatimeout = 2.seconds, o.O
      timeoutbackoffs = d-defauwttimeoutbackoffs
    )

  vaw adswoggingcwienttopicname = env match {
    case env.pwod => "ads_cwient_cawwback_pwod"
    case env.dev | e-env.staging => "ads_cwient_cawwback_staging"
  }

  /** d-deway between successive c-cascadeddewetetweet cawws when deweting wetweets. (///ˬ///✿)  a-appwied via d-decidew. OwO */
  vaw wetweetdewetiondeway: d-duwation = 20.miwwiseconds

  /**
   * deway to sweep befowe e-each tweet dewetion of an ewaseusewtweets wequest. >w<
   * this is a simpwe wate w-wimiting mechanism. ^^ the wong tewm sowution is
   * t-to move async e-endpoints wike u-usew ewasuwes and wetweet dewetions out
   * o-of the the main tweetypie cwustew and into an async cwustew with fiwst cwass
   * w-wate wimiting s-suppowt
   */
  v-vaw ewaseusewtweetsdeway: d-duwation = 100.miwwiseconds

  vaw ewaseusewtweetspagesize = 100

  vaw g-getstowedtweetsbyusewpagesize = 20
  v-vaw getstowedtweetsbyusewmaxpages = 30

  // ********* ttws **********

  // unfowtunatewy, (⑅˘꒳˘) t-this tombstone ttw appwies equawwy to the case
  // w-whewe the tweet was deweted and the case t-that the tweet does n-nyot
  // exist ow is unavaiwabwe. ʘwʘ i-if we couwd d-diffewentiate b-between those
  // cases, (///ˬ///✿) we'd cache deweted fow a-a wong time and nyot
  // found/unavaiwabwe fow a-a showt time. XD we chose 100
  // miwwiseconds fow the minimum ttw b-because thewe a-awe known cases i-in
  // which a n-not found wesuwt c-can be ewwoneouswy wwitten to c-cache on
  // tweet cweation. 😳 this minimum ttw is a-a twade-off between a
  // thundewing h-hewd of database wequests fwom cwients that j-just got
  // t-the fanned-out tweet and the window f-fow which these inconsistent
  // w-wesuwts w-wiww be avaiwabwe. >w<
  vaw tweettombstonettw: c-cachedwesuwt.cachednotfound[tweetid] => d-duwation =
    tombstonettw.wineaw(min = 100.miwwiseconds, (˘ω˘) max = 1.day, nyaa~~ f-fwom = 5.minutes, 😳😳😳 to = 5.houws)

  vaw tweetmemcachettw: duwation = 14.days
  v-vaw uwwmemcachettw: duwation = 1.houw
  v-vaw uwwmemcachesoftttw: duwation = 1.houw
  vaw d-devicesouwcememcachettw: d-duwation = 12.houws
  v-vaw devicesouwcememcachesoftttw: softttw.byage[nothing] =
    softttw.byage(softttw = 1.houw, (U ﹏ U) jittew = 1.minute)
  v-vaw devicesouwceinpwocessttw: d-duwation = 8.houws
  vaw devicesouwceinpwocesssoftttw: d-duwation = 30.minutes
  vaw pwacememcachettw: d-duwation = 1.day
  vaw pwacememcachesoftttw: s-softttw.byage[nothing] =
    s-softttw.byage(softttw = 3.houws, jittew = 1.minute)
  vaw cawdmemcachettw: duwation = 20.minutes
  vaw cawdmemcachesoftttw: d-duwation = 30.seconds
  v-vaw tweetcweatewockingmemcachettw: duwation = 10.seconds
  vaw tweetcweatewockingmemcachewongttw: duwation = 12.houws
  v-vaw geoscwubmemcachettw: d-duwation = 30.minutes

  vaw t-tweetcountsmemcachettw: duwation = 24.houws
  vaw tweetcountsmemcachenonzewosoftttw: duwation = 3.houws
  vaw t-tweetcountsmemcachezewosoftttw: duwation = 7.houws

  vaw cachecwientpendingwequestwimit: i-int = fwags.memcachependingwequestwimit()

  v-vaw devicesouwceinpwocesscachemaxsize = 10000

  v-vaw inpwocesscacheconfigopt: option[inpwocesscacheconfig] =
    i-if (fwags.enabweinpwocesscache()) {
      s-some(
        i-inpwocesscacheconfig(
          t-ttw = fwags.inpwocesscachettwms().miwwiseconds, (˘ω˘)
          m-maximumsize = f-fwags.inpwocesscachesize()
        )
      )
    } ewse {
      nyone
    }

  // begin wetuwning ovewcapacity fow tweet w-wepo when cache s-sw fawws bewow 95%, :3
  // s-scawe t-to wejecting 95% o-of wequests when c-cache sw <= 80%
  vaw tweetcacheavaiwabiwityfwomsuccesswate: doubwe => doubwe =
    avaiwabiwity.wineawwyscawed(0.95, >w< 0.80, 0.05)

  // ******* wepositowy chunking s-size ********

  v-vaw tweetcountswepochunksize = 6
  // ny times `tweetcountswepochunksize`, ^^ so chunking at h-highew wevew does n-nyot
  // genewate s-smow batches at wowew wevew. 😳😳😳
  vaw tweetcountscachechunksize = 18

  v-vaw dupwicatetweetfindewsettings: dupwicatetweetfindew.settings =
    d-dupwicatetweetfindew.settings(numtweetstocheck = 10, nyaa~~ m-maxdupwicateage = 12.houws)

  vaw backendwawmupsettings: wawmup.settings =
    w-wawmup.settings(
      // twy fow twenty s-seconds to wawm u-up the backends befowe giving
      // u-up. (⑅˘꒳˘)
      m-maxwawmupduwation = 20.seconds, :3
      // o-onwy awwow u-up to 50 outstanding w-wawmup w-wequests of any kind
      // to b-be outstanding a-at a time. ʘwʘ
      maxoutstandingwequests = 50, rawr x3
      // t-these timeouts awe just ovew the p999 watency o-obsewved in atwa
      // f-fow wequests to these backends. (///ˬ///✿)
      w-wequesttimeouts = m-map(
        "expandodo" -> 120.miwwiseconds, 😳😳😳
        "geo_wewevance" -> 50.miwwiseconds, XD
        "gizmoduck" -> 200.miwwiseconds, >_<
        "memcache" -> 50.miwwiseconds, >w<
        "scawecwow" -> 120.miwwiseconds, /(^•ω•^)
        "sociawgwaphsewvice" -> 180.miwwiseconds, :3
        "tawon" -> 70.miwwiseconds, ʘwʘ
        "tfwock" -> 320.miwwiseconds, (˘ω˘)
        "timewinesewvice" -> 200.miwwiseconds, (ꈍᴗꈍ)
        "tweetstowage" -> 50.miwwiseconds
      ), ^^
      wewiabiwity = wawmup.wewiabwy(
        // considew a-a backend wawmed up if 99% of wequests awe succeeding. ^^
        w-wewiabiwitythweshowd = 0.99, ( ͡o ω ͡o )
        // w-when pewfowming wawmup, -.- use a maximum of 10 c-concuwwent
        // w-wequests to each backend. ^^;;
        c-concuwwency = 10, ^•ﻌ•^
        // do nyot awwow mowe than t-this many attempts t-to pewfowm the
        // wawmup a-action befowe g-giving up. (˘ω˘)
        maxattempts = 1000
      )
    )
}
