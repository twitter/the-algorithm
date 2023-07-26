package com.twittew.home_mixew.modew

impowt com.twittew.cowe_wowkfwows.usew_modew.{thwiftscawa => u-um}
impowt com.twittew.daw.pewsonaw_data.{thwiftjava => p-pd}
impowt c-com.twittew.gizmoduck.{thwiftscawa => g-gt}
impowt c-com.twittew.home_mixew.{thwiftscawa => h-hmt}
i-impowt com.twittew.mw.api.constant.shawedfeatuwes
i-impowt com.twittew.pwoduct_mixew.component_wibwawy.modew.candidate.tweetcandidate
impowt com.twittew.pwoduct_mixew.cowe.featuwe.featuwe
impowt com.twittew.pwoduct_mixew.cowe.featuwe.featuwewithdefauwtonfaiwuwe
impowt com.twittew.pwoduct_mixew.cowe.featuwe.datawecowd.boowdatawecowdcompatibwe
i-impowt com.twittew.pwoduct_mixew.cowe.featuwe.datawecowd.datawecowdfeatuwe
impowt com.twittew.pwoduct_mixew.cowe.featuwe.datawecowd.datawecowdoptionawfeatuwe
impowt com.twittew.pwoduct_mixew.cowe.featuwe.datawecowd.doubwedatawecowdcompatibwe
i-impowt com.twittew.pwoduct_mixew.cowe.featuwe.datawecowd.wongdiscwetedatawecowdcompatibwe
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.topiccontextfunctionawitytype
impowt com.twittew.pwoduct_mixew.cowe.pipewine.pipewinequewy
impowt com.twittew.seawch.common.featuwes.{thwiftscawa => s-sc}
impowt com.twittew.seawch.eawwybiwd.{thwiftscawa => eb}
i-impowt com.twittew.timewinemixew.cwients.manhattan.dismissinfo
i-impowt com.twittew.timewinemixew.cwients.pewsistence.timewinewesponsev3
impowt com.twittew.timewinemixew.injection.modew.candidate.audiospacemetadata
impowt com.twittew.timewines.convewsation_featuwes.v1.thwiftscawa.convewsationfeatuwes
impowt com.twittew.timewines.impwession.{thwiftscawa => i-imp}
impowt com.twittew.timewines.impwessionbwoomfiwtew.{thwiftscawa => bwm}
impowt com.twittew.timewines.modew.usewid
impowt com.twittew.timewines.pwediction.featuwes.common.timewinesshawedfeatuwes
i-impowt com.twittew.timewines.pwediction.featuwes.engagement_featuwes.engagementdatawecowdfeatuwes
i-impowt com.twittew.timewines.pwediction.featuwes.wecap.wecapfeatuwes
i-impowt com.twittew.timewines.pwediction.featuwes.wequest_context.wequestcontextfeatuwes
i-impowt c-com.twittew.timewines.sewvice.{thwiftscawa => tst}
impowt com.twittew.timewinesewvice.modew.feedbackentwy
impowt c-com.twittew.timewinesewvice.suggests.wogging.candidate_tweet_souwce_id.{thwiftscawa => cts}
impowt com.twittew.timewinesewvice.suggests.{thwiftscawa => s-st}
impowt com.twittew.tsp.{thwiftscawa => tsp}
impowt com.twittew.tweetconvosvc.tweet_ancestow.{thwiftscawa => ta}
impowt com.twittew.utiw.time

object h-homefeatuwes {
  // candidate f-featuwes
  object a-ancestowsfeatuwe e-extends featuwe[tweetcandidate, ^^;; seq[ta.tweetancestow]]
  object audiospacemetadatafeatuwe extends featuwe[tweetcandidate, ^^ o-option[audiospacemetadata]]
  object t-twittewwistidfeatuwe extends f-featuwe[tweetcandidate, (///ˬ///✿) o-option[wong]]

  /**
   * fow wetweets, -.- t-this shouwd wefew to the wetweeting u-usew. /(^•ω•^) use [[souwceusewidfeatuwe]] if you want to know
   * w-who cweated the tweet that was w-wetweeted.
   */
  object authowidfeatuwe
      e-extends datawecowdoptionawfeatuwe[tweetcandidate, UwU w-wong]
      with wongdiscwetedatawecowdcompatibwe {
    ovewwide vaw featuwename: stwing = shawedfeatuwes.authow_id.getfeatuwename
    ovewwide vaw pewsonawdatatypes: s-set[pd.pewsonawdatatype] = s-set(pd.pewsonawdatatype.usewid)
  }

  object a-authowisbwuevewifiedfeatuwe extends f-featuwe[tweetcandidate, (⑅˘꒳˘) boowean]
  o-object authowisgowdvewifiedfeatuwe extends featuwe[tweetcandidate, ʘwʘ b-boowean]
  object authowisgwayvewifiedfeatuwe extends featuwe[tweetcandidate, σωσ boowean]
  o-object authowiswegacyvewifiedfeatuwe extends f-featuwe[tweetcandidate, ^^ b-boowean]
  o-object authowiscweatowfeatuwe extends featuwe[tweetcandidate, b-boowean]
  object a-authowispwotectedfeatuwe extends f-featuwe[tweetcandidate, OwO boowean]

  o-object authowedbycontextuawusewfeatuwe extends featuwe[tweetcandidate, (ˆ ﻌ ˆ)♡ b-boowean]
  object c-cachedcandidatepipewineidentifiewfeatuwe e-extends f-featuwe[tweetcandidate, o.O o-option[stwing]]
  object candidatesouwceidfeatuwe
      extends featuwe[tweetcandidate, (˘ω˘) option[cts.candidatetweetsouwceid]]
  o-object convewsationfeatuwe extends featuwe[tweetcandidate, 😳 option[convewsationfeatuwes]]

  /**
   * this fiewd shouwd be set to the f-focaw tweet's tweetid fow aww tweets which awe expected to
   * b-be wendewed in the s-same convo moduwe. (U ᵕ U❁) f-fow nyon-convo moduwe tweets, :3 t-this wiww be
   * set to nyone. o.O n-nyote this is d-diffewent fwom how tweetypie defines convewsationid which is defined
   * on aww tweets and points t-to the woot tweet. (///ˬ///✿) this featuwe i-is used fow gwouping convo m-moduwes togethew. OwO
   */
  o-object convewsationmoduwefocawtweetidfeatuwe extends featuwe[tweetcandidate, >w< o-option[wong]]

  /**
   * t-this fiewd shouwd awways be set t-to the woot tweet i-in a convewsation fow aww tweets. ^^ fow wepwies, this wiww
   * point back to the w-woot tweet. (⑅˘꒳˘) fow n-nyon-wepwies, ʘwʘ t-this wiww be the candidate's tweet i-id. (///ˬ///✿) this is c-consistent with
   * the tweetypie d-definition of convewsationmoduweid. XD
   */
  object convewsationmoduweidfeatuwe extends featuwe[tweetcandidate, 😳 option[wong]]
  o-object diwectedatusewidfeatuwe e-extends featuwe[tweetcandidate, >w< option[wong]]
  object eawwybiwdfeatuwe e-extends f-featuwe[tweetcandidate, (˘ω˘) option[sc.thwifttweetfeatuwes]]
  object eawwybiwdscowefeatuwe e-extends featuwe[tweetcandidate, nyaa~~ option[doubwe]]
  object eawwybiwdseawchwesuwtfeatuwe e-extends featuwe[tweetcandidate, 😳😳😳 option[eb.thwiftseawchwesuwt]]
  object e-entitytokenfeatuwe e-extends featuwe[tweetcandidate, (U ﹏ U) option[stwing]]
  object e-excwusiveconvewsationauthowidfeatuwe e-extends featuwe[tweetcandidate, (˘ω˘) option[wong]]
  object favowitedbycountfeatuwe
      extends d-datawecowdfeatuwe[tweetcandidate, :3 doubwe]
      w-with doubwedatawecowdcompatibwe {
    ovewwide vaw featuwename: stwing =
      e-engagementdatawecowdfeatuwes.innetwowkfavowitescount.getfeatuwename
    ovewwide v-vaw pewsonawdatatypes: s-set[pd.pewsonawdatatype] =
      set(pd.pewsonawdatatype.countofpwivatewikes, >w< p-pd.pewsonawdatatype.countofpubwicwikes)
  }
  object favowitedbyusewidsfeatuwe e-extends f-featuwe[tweetcandidate, ^^ s-seq[wong]]
  object feedbackhistowyfeatuwe e-extends featuwe[tweetcandidate, 😳😳😳 s-seq[feedbackentwy]]
  object wetweetedbycountfeatuwe
      e-extends d-datawecowdfeatuwe[tweetcandidate, d-doubwe]
      with doubwedatawecowdcompatibwe {
    ovewwide v-vaw featuwename: stwing =
      e-engagementdatawecowdfeatuwes.innetwowkwetweetscount.getfeatuwename
    o-ovewwide vaw pewsonawdatatypes: set[pd.pewsonawdatatype] =
      set(pd.pewsonawdatatype.countofpwivatewetweets, nyaa~~ p-pd.pewsonawdatatype.countofpubwicwetweets)
  }
  o-object w-wetweetedbyengagewidsfeatuwe e-extends featuwe[tweetcandidate, (⑅˘꒳˘) seq[wong]]
  object w-wepwiedbycountfeatuwe
      extends datawecowdfeatuwe[tweetcandidate, :3 doubwe]
      with doubwedatawecowdcompatibwe {
    ovewwide vaw featuwename: stwing =
      e-engagementdatawecowdfeatuwes.innetwowkwepwiescount.getfeatuwename
    ovewwide vaw pewsonawdatatypes: set[pd.pewsonawdatatype] =
      s-set(pd.pewsonawdatatype.countofpwivatewepwies, ʘwʘ pd.pewsonawdatatype.countofpubwicwepwies)
  }
  object wepwiedbyengagewidsfeatuwe e-extends featuwe[tweetcandidate, rawr x3 seq[wong]]
  object f-fowwowedbyusewidsfeatuwe extends f-featuwe[tweetcandidate, (///ˬ///✿) s-seq[wong]]

  o-object t-topicidsociawcontextfeatuwe extends f-featuwe[tweetcandidate, 😳😳😳 option[wong]]
  object topiccontextfunctionawitytypefeatuwe
      extends featuwe[tweetcandidate, XD option[topiccontextfunctionawitytype]]
  object fwominnetwowksouwcefeatuwe extends f-featuwe[tweetcandidate, >_< b-boowean]

  o-object fuwwscowingsucceededfeatuwe extends f-featuwe[tweetcandidate, >w< boowean]
  object hasdispwayedtextfeatuwe extends featuwe[tweetcandidate, /(^•ω•^) b-boowean]
  o-object inwepwytotweetidfeatuwe extends featuwe[tweetcandidate, :3 option[wong]]
  object i-inwepwytousewidfeatuwe extends featuwe[tweetcandidate, ʘwʘ o-option[wong]]
  o-object isancestowcandidatefeatuwe extends f-featuwe[tweetcandidate, (˘ω˘) boowean]
  o-object isextendedwepwyfeatuwe
      extends datawecowdfeatuwe[tweetcandidate, (ꈍᴗꈍ) boowean]
      w-with boowdatawecowdcompatibwe {
    o-ovewwide v-vaw featuwename: s-stwing = wecapfeatuwes.is_extended_wepwy.getfeatuwename
    o-ovewwide vaw pewsonawdatatypes: set[pd.pewsonawdatatype] = s-set.empty
  }
  o-object iswandomtweetfeatuwe
      e-extends d-datawecowdfeatuwe[tweetcandidate, ^^ boowean]
      w-with boowdatawecowdcompatibwe {
    ovewwide vaw featuwename: s-stwing = timewinesshawedfeatuwes.is_wandom_tweet.getfeatuwename
    ovewwide v-vaw pewsonawdatatypes: s-set[pd.pewsonawdatatype] = set.empty
  }
  o-object isweadfwomcachefeatuwe extends featuwe[tweetcandidate, boowean]
  object i-iswetweetfeatuwe e-extends featuwe[tweetcandidate, ^^ b-boowean]
  object iswetweetedwepwyfeatuwe extends featuwe[tweetcandidate, ( ͡o ω ͡o ) boowean]
  object i-issuppowtaccountwepwyfeatuwe extends featuwe[tweetcandidate, -.- b-boowean]
  o-object wastscowedtimestampmsfeatuwe e-extends featuwe[tweetcandidate, ^^;; o-option[wong]]
  o-object nyonsewffavowitedbyusewidsfeatuwe extends featuwe[tweetcandidate, ^•ﻌ•^ s-seq[wong]]
  object nyumimagesfeatuwe extends f-featuwe[tweetcandidate, o-option[int]]
  object o-owiginawtweetcweationtimefwomsnowfwakefeatuwe extends featuwe[tweetcandidate, (˘ω˘) o-option[time]]
  o-object positionfeatuwe e-extends featuwe[tweetcandidate, o.O option[int]]
  // intewnaw id genewated pew pwediction sewvice wequest
  object pwedictionwequestidfeatuwe extends featuwe[tweetcandidate, option[wong]]
  object quotedtweetidfeatuwe extends featuwe[tweetcandidate, (✿oωo) option[wong]]
  o-object q-quotedusewidfeatuwe extends featuwe[tweetcandidate, 😳😳😳 o-option[wong]]
  o-object s-scowefeatuwe extends featuwe[tweetcandidate, (ꈍᴗꈍ) o-option[doubwe]]
  object semanticcoweidfeatuwe e-extends f-featuwe[tweetcandidate, σωσ option[wong]]
  // key f-fow kafka wogging
  object sewvedidfeatuwe e-extends f-featuwe[tweetcandidate, UwU option[wong]]
  object s-simcwustewstweettopkcwustewswithscowesfeatuwe
      e-extends f-featuwe[tweetcandidate, ^•ﻌ•^ m-map[stwing, mya d-doubwe]]
  o-object sociawcontextfeatuwe e-extends f-featuwe[tweetcandidate, o-option[tst.sociawcontext]]
  object s-souwcetweetidfeatuwe
      e-extends d-datawecowdoptionawfeatuwe[tweetcandidate, /(^•ω•^) wong]
      w-with wongdiscwetedatawecowdcompatibwe {
    ovewwide vaw featuwename: stwing = t-timewinesshawedfeatuwes.souwce_tweet_id.getfeatuwename
    ovewwide vaw p-pewsonawdatatypes: s-set[pd.pewsonawdatatype] = s-set(pd.pewsonawdatatype.tweetid)
  }
  object souwceusewidfeatuwe e-extends featuwe[tweetcandidate, rawr option[wong]]
  o-object stweamtokafkafeatuwe extends f-featuwe[tweetcandidate, nyaa~~ boowean]
  o-object suggesttypefeatuwe extends featuwe[tweetcandidate, ( ͡o ω ͡o ) option[st.suggesttype]]
  object tspmetwictagfeatuwe e-extends featuwe[tweetcandidate, σωσ set[tsp.metwictag]]
  o-object t-tweetwanguagefeatuwe extends featuwe[tweetcandidate, (✿oωo) option[stwing]]
  o-object tweetuwwsfeatuwe e-extends featuwe[tweetcandidate, (///ˬ///✿) s-seq[stwing]]
  o-object videoduwationmsfeatuwe extends featuwe[tweetcandidate, σωσ option[int]]
  object v-viewewidfeatuwe
      e-extends datawecowdfeatuwe[tweetcandidate, UwU w-wong]
      with wongdiscwetedatawecowdcompatibwe {
    ovewwide d-def featuwename: stwing = s-shawedfeatuwes.usew_id.getfeatuwename
    o-ovewwide d-def pewsonawdatatypes: set[pd.pewsonawdatatype] = s-set(pd.pewsonawdatatype.usewid)
  }
  o-object w-weightedmodewscowefeatuwe e-extends featuwe[tweetcandidate, (⑅˘꒳˘) o-option[doubwe]]
  o-object m-mentionusewidfeatuwe e-extends f-featuwe[tweetcandidate, /(^•ω•^) s-seq[wong]]
  o-object mentionscweennamefeatuwe e-extends featuwe[tweetcandidate, -.- seq[stwing]]
  o-object hasimagefeatuwe extends f-featuwe[tweetcandidate, (ˆ ﻌ ˆ)♡ boowean]
  o-object hasvideofeatuwe extends f-featuwe[tweetcandidate, nyaa~~ boowean]

  // t-tweetypie vf featuwes
  object ishydwatedfeatuwe extends featuwe[tweetcandidate, ʘwʘ boowean]
  o-object i-isnsfwfeatuwe extends f-featuwe[tweetcandidate, :3 boowean]
  object quotedtweetdwoppedfeatuwe extends f-featuwe[tweetcandidate, (U ᵕ U❁) b-boowean]
  // waw tweet t-text fwom tweetypie
  o-object tweettextfeatuwe extends featuwe[tweetcandidate, (U ﹏ U) option[stwing]]

  o-object authowenabwedpweviewsfeatuwe e-extends f-featuwe[tweetcandidate, ^^ b-boowean]
  object istweetpweviewfeatuwe extends featuwe[tweetcandidate, òωó b-boowean]

  // sgs f-featuwes
  /**
   * by convention, /(^•ω•^) this is set t-to twue fow wetweets of nyon-fowwowed authows
   * e-e.g. 😳😳😳 whewe somebody the viewew f-fowwows wetweets a-a tweet fwom somebody the viewew d-doesn't fowwow
   */
  o-object innetwowkfeatuwe e-extends featuwewithdefauwtonfaiwuwe[tweetcandidate, :3 boowean] {
    o-ovewwide v-vaw defauwtvawue: b-boowean = twue
  }

  // q-quewy featuwes
  object a-accountagefeatuwe e-extends featuwe[pipewinequewy, (///ˬ///✿) o-option[time]]
  object cwientidfeatuwe
      e-extends datawecowdoptionawfeatuwe[pipewinequewy, rawr x3 wong]
      with wongdiscwetedatawecowdcompatibwe {
    o-ovewwide d-def featuwename: s-stwing = shawedfeatuwes.cwient_id.getfeatuwename
    ovewwide def pewsonawdatatypes: set[pd.pewsonawdatatype] = set(pd.pewsonawdatatype.cwienttype)
  }
  object c-cachedscowedtweetsfeatuwe extends featuwe[pipewinequewy, (U ᵕ U❁) seq[hmt.scowedtweet]]
  o-object devicewanguagefeatuwe e-extends featuwe[pipewinequewy, (⑅˘꒳˘) option[stwing]]
  object dismissinfofeatuwe
      e-extends featuwewithdefauwtonfaiwuwe[pipewinequewy, (˘ω˘) map[st.suggesttype, :3 o-option[dismissinfo]]] {
    o-ovewwide d-def defauwtvawue: m-map[st.suggesttype, XD o-option[dismissinfo]] = map.empty
  }
  object fowwowingwastnonpowwingtimefeatuwe extends f-featuwe[pipewinequewy, >_< option[time]]
  o-object getinitiawfeatuwe
      extends datawecowdfeatuwe[pipewinequewy, (✿oωo) boowean]
      with boowdatawecowdcompatibwe {
    o-ovewwide def featuwename: stwing = wequestcontextfeatuwes.is_get_initiaw.getfeatuwename
    ovewwide def pewsonawdatatypes: s-set[pd.pewsonawdatatype] = s-set.empty
  }
  object g-getmiddwefeatuwe
      extends datawecowdfeatuwe[pipewinequewy, (ꈍᴗꈍ) boowean]
      with b-boowdatawecowdcompatibwe {
    o-ovewwide def featuwename: stwing = w-wequestcontextfeatuwes.is_get_middwe.getfeatuwename
    ovewwide d-def pewsonawdatatypes: set[pd.pewsonawdatatype] = set.empty
  }
  object g-getnewewfeatuwe
      extends datawecowdfeatuwe[pipewinequewy, XD boowean]
      with b-boowdatawecowdcompatibwe {
    o-ovewwide def featuwename: s-stwing = wequestcontextfeatuwes.is_get_newew.getfeatuwename
    ovewwide d-def pewsonawdatatypes: set[pd.pewsonawdatatype] = set.empty
  }
  object getowdewfeatuwe
      extends datawecowdfeatuwe[pipewinequewy, :3 b-boowean]
      w-with b-boowdatawecowdcompatibwe {
    o-ovewwide def featuwename: stwing = wequestcontextfeatuwes.is_get_owdew.getfeatuwename
    o-ovewwide d-def pewsonawdatatypes: set[pd.pewsonawdatatype] = set.empty
  }
  o-object guestidfeatuwe
      extends datawecowdoptionawfeatuwe[pipewinequewy, mya wong]
      with w-wongdiscwetedatawecowdcompatibwe {
    ovewwide def featuwename: s-stwing = shawedfeatuwes.guest_id.getfeatuwename
    o-ovewwide def pewsonawdatatypes: s-set[pd.pewsonawdatatype] = s-set(pd.pewsonawdatatype.guestid)
  }
  o-object hasdawkwequestfeatuwe extends featuwe[pipewinequewy, òωó o-option[boowean]]
  object impwessionbwoomfiwtewfeatuwe
      e-extends featuwewithdefauwtonfaiwuwe[pipewinequewy, nyaa~~ bwm.impwessionbwoomfiwtewseq] {
    ovewwide def defauwtvawue: b-bwm.impwessionbwoomfiwtewseq =
      b-bwm.impwessionbwoomfiwtewseq(seq.empty)
  }
  o-object isfowegwoundwequestfeatuwe e-extends f-featuwe[pipewinequewy, 🥺 boowean]
  o-object iswaunchwequestfeatuwe extends featuwe[pipewinequewy, -.- boowean]
  object w-wastnonpowwingtimefeatuwe extends f-featuwe[pipewinequewy, 🥺 option[time]]
  object n-nyonpowwingtimesfeatuwe e-extends featuwe[pipewinequewy, (˘ω˘) s-seq[wong]]
  object pewsistenceentwiesfeatuwe e-extends f-featuwe[pipewinequewy, òωó seq[timewinewesponsev3]]
  o-object powwingfeatuwe e-extends featuwe[pipewinequewy, UwU b-boowean]
  object puwwtowefweshfeatuwe extends featuwe[pipewinequewy, ^•ﻌ•^ b-boowean]
  // scowes f-fwom weaw gwaph wepwesenting the wewationship b-between the viewew a-and anothew usew
  o-object weawgwaphinnetwowkscowesfeatuwe extends f-featuwe[pipewinequewy, m-map[usewid, mya doubwe]]
  o-object wequestjoinidfeatuwe extends featuwe[tweetcandidate, (✿oωo) option[wong]]
  // i-intewnaw id genewated pew wequest, XD m-mainwy to dedupwicate w-we-sewved cached tweets in wogging
  object sewvedwequestidfeatuwe extends f-featuwe[pipewinequewy, :3 o-option[wong]]
  object sewvedtweetidsfeatuwe extends f-featuwe[pipewinequewy, (U ﹏ U) seq[wong]]
  o-object sewvedtweetpweviewidsfeatuwe e-extends featuwe[pipewinequewy, UwU seq[wong]]
  object timewinesewvicetweetsfeatuwe extends f-featuwe[pipewinequewy, ʘwʘ seq[wong]]
  object timestampfeatuwe
      e-extends datawecowdfeatuwe[pipewinequewy, >w< wong]
      w-with wongdiscwetedatawecowdcompatibwe {
    o-ovewwide def featuwename: stwing = s-shawedfeatuwes.timestamp.getfeatuwename
    o-ovewwide def p-pewsonawdatatypes: s-set[pd.pewsonawdatatype] = set.empty
  }
  object t-timestampgmtdowfeatuwe
      e-extends datawecowdfeatuwe[pipewinequewy, 😳😳😳 wong]
      with wongdiscwetedatawecowdcompatibwe {
    ovewwide def featuwename: stwing = wequestcontextfeatuwes.timestamp_gmt_dow.getfeatuwename
    o-ovewwide def p-pewsonawdatatypes: s-set[pd.pewsonawdatatype] = s-set.empty
  }
  o-object t-timestampgmthouwfeatuwe
      extends datawecowdfeatuwe[pipewinequewy, rawr wong]
      with wongdiscwetedatawecowdcompatibwe {
    ovewwide def f-featuwename: stwing = w-wequestcontextfeatuwes.timestamp_gmt_houw.getfeatuwename
    ovewwide def pewsonawdatatypes: set[pd.pewsonawdatatype] = set.empty
  }
  object t-tweetimpwessionsfeatuwe e-extends f-featuwe[pipewinequewy, ^•ﻌ•^ seq[imp.tweetimpwessionsentwy]]
  object usewfowwowedtopicscountfeatuwe e-extends featuwe[pipewinequewy, σωσ option[int]]
  object usewfowwowingcountfeatuwe e-extends featuwe[pipewinequewy, :3 o-option[int]]
  object usewscweennamefeatuwe extends featuwe[pipewinequewy, rawr x3 o-option[stwing]]
  object usewstatefeatuwe e-extends f-featuwe[pipewinequewy, nyaa~~ option[um.usewstate]]
  object u-usewtypefeatuwe e-extends featuwe[pipewinequewy, :3 o-option[gt.usewtype]]
  o-object w-whotofowwowexcwudedusewidsfeatuwe
      e-extends featuwewithdefauwtonfaiwuwe[pipewinequewy, >w< s-seq[wong]] {
    ovewwide d-def defauwtvawue = seq.empty
  }

  // wesuwt f-featuwes
  object sewvedsizefeatuwe extends f-featuwe[pipewinequewy, rawr option[int]]
  o-object haswandomtweetfeatuwe extends featuwe[pipewinequewy, 😳 b-boowean]
  object i-iswandomtweetabovefeatuwe extends featuwe[tweetcandidate, 😳 boowean]
  object s-sewvedinconvewsationmoduwefeatuwe extends featuwe[tweetcandidate, 🥺 boowean]
  object c-convewsationmoduwe2dispwayedtweetsfeatuwe e-extends featuwe[tweetcandidate, rawr x3 boowean]
  object convewsationmoduwehasgapfeatuwe e-extends featuwe[tweetcandidate, ^^ b-boowean]
  object sgsvawidwikedbyusewidsfeatuwe e-extends featuwe[tweetcandidate, ( ͡o ω ͡o ) seq[wong]]
  object sgsvawidfowwowedbyusewidsfeatuwe e-extends featuwe[tweetcandidate, XD s-seq[wong]]
  object pewspectivefiwtewedwikedbyusewidsfeatuwe e-extends featuwe[tweetcandidate, ^^ s-seq[wong]]
  object scweennamesfeatuwe extends f-featuwe[tweetcandidate, (⑅˘꒳˘) m-map[wong, (⑅˘꒳˘) s-stwing]]
  o-object weawnamesfeatuwe extends featuwe[tweetcandidate, ^•ﻌ•^ map[wong, ( ͡o ω ͡o ) stwing]]

  /**
   * featuwes awound the focaw t-tweet fow tweets w-which shouwd be w-wendewed in convo m-moduwes. ( ͡o ω ͡o )
   * t-these awe nyeeded i-in owdew to wendew sociaw context a-above the w-woot tweet in a convo moduwes. (✿oωo)
   * f-fow exampwe i-if we have a convo moduwe a-b-c (a tweets, 😳😳😳 b wepwies t-to a, OwO c wepwies to b), ^^ the descendant featuwes a-awe
   * fow the tweet c. rawr x3 these f-featuwes awe n-nyone except fow the woot tweet f-fow tweets which s-shouwd wendew i-into
   * convo moduwes. 🥺
   */
  o-object focawtweetauthowidfeatuwe e-extends featuwe[tweetcandidate, (ˆ ﻌ ˆ)♡ option[wong]]
  o-object focawtweetinnetwowkfeatuwe extends featuwe[tweetcandidate, o-option[boowean]]
  o-object focawtweetweawnamesfeatuwe e-extends featuwe[tweetcandidate, ( ͡o ω ͡o ) o-option[map[wong, >w< stwing]]]
  object focawtweetscweennamesfeatuwe e-extends featuwe[tweetcandidate, option[map[wong, /(^•ω•^) stwing]]]
  object mediaundewstandingannotationidsfeatuwe extends featuwe[tweetcandidate, 😳😳😳 seq[wong]]
}
