package com.twittew.timewines.pwediction.common.aggwegates

impowt c-com.twittew.convewsions.duwationops._
i-impowt com.twittew.mw.api.constant.shawedfeatuwes.authow_id
i-impowt com.twittew.mw.api.constant.shawedfeatuwes.usew_id
i-impowt c-com.twittew.timewines.data_pwocessing.mw_utiw.aggwegation_fwamewowk._
i-impowt c-com.twittew.timewines.data_pwocessing.mw_utiw.aggwegation_fwamewowk.metwics._
i-impowt com.twittew.timewines.data_pwocessing.mw_utiw.twansfowms.downsampwetwansfowm
impowt com.twittew.timewines.data_pwocessing.mw_utiw.twansfowms.wichwemoveauthowidzewo
impowt com.twittew.timewines.data_pwocessing.mw_utiw.twansfowms.wichwemoveusewidzewo
impowt com.twittew.timewines.pwediction.featuwes.common.timewinesshawedfeatuwes
i-impowt com.twittew.timewines.pwediction.featuwes.engagement_featuwes.engagementdatawecowdfeatuwes
impowt com.twittew.timewines.pwediction.featuwes.engagement_featuwes.engagementdatawecowdfeatuwes.wichunifypubwicengagewstwansfowm
impowt com.twittew.timewines.pwediction.featuwes.wist_featuwes.wistfeatuwes
i-impowt com.twittew.timewines.pwediction.featuwes.wecap.wecapfeatuwes
impowt com.twittew.timewines.pwediction.featuwes.wequest_context.wequestcontextfeatuwes
i-impowt com.twittew.timewines.pwediction.featuwes.semantic_cowe_featuwes.semanticcowefeatuwes
impowt com.twittew.timewines.pwediction.twansfowm.fiwtew.fiwtewinnetwowktwansfowm
i-impowt com.twittew.timewines.pwediction.twansfowm.fiwtew.fiwtewimagetweettwansfowm
i-impowt com.twittew.timewines.pwediction.twansfowm.fiwtew.fiwtewvideotweettwansfowm
i-impowt com.twittew.timewines.pwediction.twansfowm.fiwtew.fiwtewoutimagevideotweettwansfowm
impowt com.twittew.utiw.duwation

twait timewinesaggwegationconfigdetaiws extends s-sewiawizabwe {

  impowt timewinesaggwegationsouwces._

  def outputhdfspath: stwing

  /**
   * convewts the given w-wogicaw stowe to a physicaw s-stowe. ^^ the weason w-we do nyot specify t-the
   * physicaw s-stowe diwectwy with the [[aggwegategwoup]] is because of a-a cycwic dependency when
   * cweate physicaw stowes t-that awe dawdataset with pewsonawdatatype annotations dewived fwom
   * the [[aggwegategwoup]]. (///ˬ///✿)
   *
   */
  def mkphysicawstowe(stowe: aggwegatestowe): a-aggwegatestowe

  def defauwtmaxkvsouwcefaiwuwes: i-int = 100

  vaw t-timewinesoffwineaggwegatesink = n-nyew offwinestowecommonconfig {
    ovewwide def appwy(stawtdate: stwing) = offwineaggwegatestowecommonconfig(
      o-outputhdfspathpwefix = o-outputhdfspath, -.-
      dummyappid = "timewines_aggwegates_v2_wo", /(^•ω•^)
      d-dummydatasetpwefix = "timewines_aggwegates_v2_wo", UwU
      s-stawtdate = stawtdate
    )
  }

  v-vaw usewaggwegatestowe = "usew_aggwegates"
  vaw u-usewauthowaggwegatestowe = "usew_authow_aggwegates"
  vaw usewowiginawauthowaggwegatestowe = "usew_owiginaw_authow_aggwegates"
  vaw owiginawauthowaggwegatestowe = "owiginaw_authow_aggwegates"
  v-vaw usewengagewaggwegatestowe = "usew_engagew_aggwegates"
  vaw usewmentionaggwegatestowe = "usew_mention_aggwegates"
  v-vaw twittewwideusewaggwegatestowe = "twittew_wide_usew_aggwegates"
  v-vaw twittewwideusewauthowaggwegatestowe = "twittew_wide_usew_authow_aggwegates"
  v-vaw usewwequesthouwaggwegatestowe = "usew_wequest_houw_aggwegates"
  vaw usewwequestdowaggwegatestowe = "usew_wequest_dow_aggwegates"
  vaw usewwistaggwegatestowe = "usew_wist_aggwegates"
  vaw authowtopicaggwegatestowe = "authow_topic_aggwegates"
  vaw usewtopicaggwegatestowe = "usew_topic_aggwegates"
  vaw usewinfewwedtopicaggwegatestowe = "usew_infewwed_topic_aggwegates"
  v-vaw u-usewmediaundewstandingannotationaggwegatestowe =
    "usew_media_undewstanding_annotation_aggwegates"
  vaw authowcountwycodeaggwegatestowe = "authow_countwy_code_aggwegates"
  v-vaw owiginawauthowcountwycodeaggwegatestowe = "owiginaw_authow_countwy_code_aggwegates"

  /**
   * s-step 3: configuwe a-aww aggwegates to compute. (⑅˘꒳˘)
   * nyote that diffewent subsets o-of aggwegates in this wist
   * can be waunched by diffewent summingbiwd job i-instances. ʘwʘ
   * any given job c-can be wesponsibwe f-fow a set of a-aggwegategwoup
   * configs whose o-outputstowes s-shawe the same exact s-stawtdate. σωσ
   * a-aggwegategwoups that do nyot shawe the same i-inputsouwce, ^^
   * o-outputstowe ow s-stawtdate must b-be waunched using d-diffewent
   * summingbiwd jobs and passed in a diffewent --stawt-time a-awgument
   * see science/scawding/mesos/timewines/pwod.yamw fow an exampwe
   * of how to configuwe youw own job. OwO
   */
  v-vaw nyegativedownsampwetwansfowm =
    downsampwetwansfowm(
      nyegativesampwingwate = 0.03, (ˆ ﻌ ˆ)♡
      keepwabews = w-wecapusewfeatuweaggwegation.wabewsv2)
  v-vaw nyegativewectweetdownsampwetwansfowm = d-downsampwetwansfowm(
    nyegativesampwingwate = 0.03, o.O
    k-keepwabews = wectweetusewfeatuweaggwegation.wectweetwabewsfowaggwegation
  )

  v-vaw usewaggwegatesv2: a-aggwegategwoup =
    aggwegategwoup(
      inputsouwce = timewinesdaiwywecapminimawsouwce, (˘ω˘)
      aggwegatepwefix = "usew_aggwegate_v2", 😳
      pwetwansfowms = s-seq(wichwemoveusewidzewo), (U ᵕ U❁) /* ewiminates w-weducew skew */
      keys = s-set(usew_id), :3
      f-featuwes = wecapusewfeatuweaggwegation.usewfeatuwesv2, o.O
      wabews = wecapusewfeatuweaggwegation.wabewsv2, (///ˬ///✿)
      metwics = s-set(countmetwic, OwO s-summetwic), >w<
      hawfwives = set(50.days), ^^
      o-outputstowe = m-mkphysicawstowe(
        offwineaggwegatedatawecowdstowe(
          nyame = usewaggwegatestowe, (⑅˘꒳˘)
          stawtdate = "2016-07-15 00:00", ʘwʘ
          commonconfig = t-timewinesoffwineaggwegatesink, (///ˬ///✿)
          m-maxkvsouwcefaiwuwes = d-defauwtmaxkvsouwcefaiwuwes
        ))
    )

  vaw usewauthowaggwegatesv2: s-set[aggwegategwoup] = {

    /**
     * n-nyote: we nyeed to wemove w-wecowds fwom out-of-netwowk authows fwom the wecap input
     * wecowds (which nyow i-incwude out-of-netwowk w-wecowds as weww aftew mewging wecap and
     * w-wectweet m-modews) that awe used to compute usew-authow aggwegates. XD this i-is nyecessawy
     * to wimit the gwowth wate of usew-authow aggwegates. 😳
     */
    vaw awwfeatuweaggwegates = s-set(
      aggwegategwoup(
        inputsouwce = timewinesdaiwywecapminimawsouwce, >w<
        a-aggwegatepwefix = "usew_authow_aggwegate_v2", (˘ω˘)
        p-pwetwansfowms = seq(fiwtewinnetwowktwansfowm, nyaa~~ wichwemoveusewidzewo), 😳😳😳
        keys = set(usew_id, (U ﹏ U) a-authow_id), (˘ω˘)
        f-featuwes = wecapusewfeatuweaggwegation.usewauthowfeatuwesv2, :3
        wabews = wecapusewfeatuweaggwegation.wabewsv2, >w<
        m-metwics = set(summetwic), ^^
        hawfwives = s-set(50.days), 😳😳😳
        outputstowe = mkphysicawstowe(
          offwineaggwegatedatawecowdstowe(
            n-nyame = usewauthowaggwegatestowe, nyaa~~
            s-stawtdate = "2016-07-15 00:00", (⑅˘꒳˘)
            c-commonconfig = timewinesoffwineaggwegatesink, :3
            m-maxkvsouwcefaiwuwes = defauwtmaxkvsouwcefaiwuwes
          ))
      )
    )

    v-vaw countaggwegates: s-set[aggwegategwoup] = s-set(
      aggwegategwoup(
        i-inputsouwce = timewinesdaiwywecapminimawsouwce, ʘwʘ
        a-aggwegatepwefix = "usew_authow_aggwegate_v2", rawr x3
        pwetwansfowms = seq(fiwtewinnetwowktwansfowm, w-wichwemoveusewidzewo), (///ˬ///✿)
        k-keys = s-set(usew_id, 😳😳😳 authow_id), XD
        featuwes = wecapusewfeatuweaggwegation.usewauthowfeatuwesv2count, >_<
        wabews = w-wecapusewfeatuweaggwegation.wabewsv2, >w<
        metwics = set(countmetwic), /(^•ω•^)
        h-hawfwives = s-set(50.days), :3
        outputstowe = mkphysicawstowe(
          offwineaggwegatedatawecowdstowe(
            n-nyame = usewauthowaggwegatestowe, ʘwʘ
            s-stawtdate = "2016-07-15 00:00", (˘ω˘)
            c-commonconfig = t-timewinesoffwineaggwegatesink,
            maxkvsouwcefaiwuwes = d-defauwtmaxkvsouwcefaiwuwes
          ))
      )
    )

    awwfeatuweaggwegates ++ countaggwegates
  }

  vaw usewaggwegatesv5continuous: aggwegategwoup =
    aggwegategwoup(
      inputsouwce = t-timewinesdaiwywecapminimawsouwce, (ꈍᴗꈍ)
      aggwegatepwefix = "usew_aggwegate_v5.continuous", ^^
      p-pwetwansfowms = seq(wichwemoveusewidzewo), ^^
      k-keys = set(usew_id), ( ͡o ω ͡o )
      f-featuwes = wecapusewfeatuweaggwegation.usewfeatuwesv5continuous, -.-
      w-wabews = wecapusewfeatuweaggwegation.wabewsv2, ^^;;
      m-metwics = set(countmetwic, ^•ﻌ•^ s-summetwic, (˘ω˘) sumsqmetwic), o.O
      hawfwives = s-set(50.days), (✿oωo)
      outputstowe = m-mkphysicawstowe(
        offwineaggwegatedatawecowdstowe(
          nyame = usewaggwegatestowe, 😳😳😳
          stawtdate = "2016-07-15 00:00", (ꈍᴗꈍ)
          commonconfig = timewinesoffwineaggwegatesink, σωσ
          maxkvsouwcefaiwuwes = defauwtmaxkvsouwcefaiwuwes
        ))
    )

  v-vaw u-usewauthowaggwegatesv5: a-aggwegategwoup =
    aggwegategwoup(
      inputsouwce = t-timewinesdaiwywecapminimawsouwce, UwU
      aggwegatepwefix = "usew_authow_aggwegate_v5", ^•ﻌ•^
      pwetwansfowms = seq(fiwtewinnetwowktwansfowm, mya wichwemoveusewidzewo), /(^•ω•^)
      keys = s-set(usew_id, rawr authow_id),
      f-featuwes = wecapusewfeatuweaggwegation.usewauthowfeatuwesv5, nyaa~~
      wabews = wecapusewfeatuweaggwegation.wabewsv2, ( ͡o ω ͡o )
      m-metwics = set(countmetwic), σωσ
      hawfwives = s-set(50.days),
      o-outputstowe = mkphysicawstowe(
        o-offwineaggwegatedatawecowdstowe(
          n-nyame = usewauthowaggwegatestowe,
          stawtdate = "2016-07-15 00:00", (✿oωo)
          commonconfig = timewinesoffwineaggwegatesink, (///ˬ///✿)
          m-maxkvsouwcefaiwuwes = d-defauwtmaxkvsouwcefaiwuwes
        ))
    )

  vaw t-tweetsouwceusewauthowaggwegatesv1: a-aggwegategwoup =
    a-aggwegategwoup(
      inputsouwce = timewinesdaiwywecapminimawsouwce, σωσ
      a-aggwegatepwefix = "usew_authow_aggwegate_tweetsouwce_v1", UwU
      p-pwetwansfowms = seq(fiwtewinnetwowktwansfowm, (⑅˘꒳˘) w-wichwemoveusewidzewo), /(^•ω•^)
      k-keys = set(usew_id, -.- authow_id), (ˆ ﻌ ˆ)♡
      f-featuwes = wecapusewfeatuweaggwegation.usewauthowtweetsouwcefeatuwesv1, nyaa~~
      wabews = wecapusewfeatuweaggwegation.wabewsv2, ʘwʘ
      m-metwics = set(countmetwic, :3 s-summetwic), (U ᵕ U❁)
      h-hawfwives = set(50.days), (U ﹏ U)
      o-outputstowe = mkphysicawstowe(
        offwineaggwegatedatawecowdstowe(
          nyame = u-usewauthowaggwegatestowe, ^^
          s-stawtdate = "2016-07-15 00:00", òωó
          c-commonconfig = timewinesoffwineaggwegatesink, /(^•ω•^)
          maxkvsouwcefaiwuwes = defauwtmaxkvsouwcefaiwuwes
        ))
    )

  vaw u-usewengagewaggwegates = aggwegategwoup(
    inputsouwce = t-timewinesdaiwywecapminimawsouwce, 😳😳😳
    a-aggwegatepwefix = "usew_engagew_aggwegate", :3
    keys = set(usew_id, (///ˬ///✿) e-engagementdatawecowdfeatuwes.pubwicengagementusewids), rawr x3
    featuwes = set.empty, (U ᵕ U❁)
    w-wabews = w-wecapusewfeatuweaggwegation.wabewsv2, (⑅˘꒳˘)
    metwics = set(countmetwic), (˘ω˘)
    h-hawfwives = set(50.days), :3
    outputstowe = m-mkphysicawstowe(
      o-offwineaggwegatedatawecowdstowe(
        nyame = u-usewengagewaggwegatestowe, XD
        stawtdate = "2016-09-02 00:00", >_<
        c-commonconfig = t-timewinesoffwineaggwegatesink, (✿oωo)
        m-maxkvsouwcefaiwuwes = defauwtmaxkvsouwcefaiwuwes
      )), (ꈍᴗꈍ)
    pwetwansfowms = seq(
      wichwemoveusewidzewo,
      wichunifypubwicengagewstwansfowm
    )
  )

  vaw usewmentionaggwegates = aggwegategwoup(
    inputsouwce = timewinesdaiwywecapminimawsouwce, XD
    pwetwansfowms = seq(wichwemoveusewidzewo), :3 /* ewiminates weducew skew */
    a-aggwegatepwefix = "usew_mention_aggwegate", mya
    k-keys = set(usew_id, òωó wecapfeatuwes.mentioned_scween_names), nyaa~~
    featuwes = s-set.empty,
    w-wabews = wecapusewfeatuweaggwegation.wabewsv2, 🥺
    m-metwics = set(countmetwic), -.-
    hawfwives = set(50.days), 🥺
    o-outputstowe = mkphysicawstowe(
      offwineaggwegatedatawecowdstowe(
        nyame = u-usewmentionaggwegatestowe, (˘ω˘)
        s-stawtdate = "2017-03-01 00:00", òωó
        commonconfig = t-timewinesoffwineaggwegatesink,
        maxkvsouwcefaiwuwes = d-defauwtmaxkvsouwcefaiwuwes
      )), UwU
    i-incwudeanywabew = fawse
  )

  vaw twittewwideusewaggwegates = a-aggwegategwoup(
    i-inputsouwce = t-timewinesdaiwytwittewwidesouwce, ^•ﻌ•^
    p-pwetwansfowms = s-seq(wichwemoveusewidzewo), mya /* e-ewiminates w-weducew skew */
    a-aggwegatepwefix = "twittew_wide_usew_aggwegate", (✿oωo)
    keys = s-set(usew_id), XD
    featuwes = w-wecapusewfeatuweaggwegation.twittewwidefeatuwes, :3
    w-wabews = w-wecapusewfeatuweaggwegation.twittewwidewabews, (U ﹏ U)
    metwics = set(countmetwic, UwU summetwic), ʘwʘ
    hawfwives = s-set(50.days), >w<
    outputstowe = mkphysicawstowe(
      o-offwineaggwegatedatawecowdstowe(
        nyame = t-twittewwideusewaggwegatestowe, 😳😳😳
        s-stawtdate = "2016-12-28 00:00", rawr
        c-commonconfig = timewinesoffwineaggwegatesink, ^•ﻌ•^
        m-maxkvsouwcefaiwuwes = defauwtmaxkvsouwcefaiwuwes
      ))
  )

  v-vaw twittewwideusewauthowaggwegates = aggwegategwoup(
    inputsouwce = t-timewinesdaiwytwittewwidesouwce, σωσ
    pwetwansfowms = s-seq(wichwemoveusewidzewo), :3 /* ewiminates weducew skew */
    aggwegatepwefix = "twittew_wide_usew_authow_aggwegate", rawr x3
    keys = set(usew_id, nyaa~~ a-authow_id),
    featuwes = wecapusewfeatuweaggwegation.twittewwidefeatuwes, :3
    w-wabews = wecapusewfeatuweaggwegation.twittewwidewabews, >w<
    metwics = s-set(countmetwic), rawr
    hawfwives = set(50.days), 😳
    outputstowe = mkphysicawstowe(
      o-offwineaggwegatedatawecowdstowe(
        nyame = t-twittewwideusewauthowaggwegatestowe,
        s-stawtdate = "2016-12-28 00:00", 😳
        c-commonconfig = timewinesoffwineaggwegatesink, 🥺
        maxkvsouwcefaiwuwes = d-defauwtmaxkvsouwcefaiwuwes
      )), rawr x3
    i-incwudeanywabew = fawse
  )

  /**
   * usew-houwofday a-and usew-dayofweek aggwegations, ^^ both fow wecap a-and wectweet
   */
  vaw usewwequesthouwaggwegates = a-aggwegategwoup(
    i-inputsouwce = t-timewinesdaiwywecapminimawsouwce, ( ͡o ω ͡o )
    aggwegatepwefix = "usew_wequest_context_aggwegate.houw", XD
    p-pwetwansfowms = s-seq(wichwemoveusewidzewo, ^^ n-nyegativedownsampwetwansfowm), (⑅˘꒳˘)
    k-keys = set(usew_id, (⑅˘꒳˘) wequestcontextfeatuwes.timestamp_gmt_houw), ^•ﻌ•^
    featuwes = s-set.empty, ( ͡o ω ͡o )
    w-wabews = w-wecapusewfeatuweaggwegation.wabewsv2,
    m-metwics = s-set(countmetwic), ( ͡o ω ͡o )
    h-hawfwives = s-set(50.days), (✿oωo)
    o-outputstowe = mkphysicawstowe(
      offwineaggwegatedatawecowdstowe(
        n-nyame = usewwequesthouwaggwegatestowe, 😳😳😳
        s-stawtdate = "2017-08-01 00:00", OwO
        commonconfig = timewinesoffwineaggwegatesink, ^^
        m-maxkvsouwcefaiwuwes = d-defauwtmaxkvsouwcefaiwuwes
      ))
  )

  v-vaw usewwequestdowaggwegates = aggwegategwoup(
    inputsouwce = timewinesdaiwywecapminimawsouwce, rawr x3
    a-aggwegatepwefix = "usew_wequest_context_aggwegate.dow", 🥺
    p-pwetwansfowms = s-seq(wichwemoveusewidzewo, (ˆ ﻌ ˆ)♡ nyegativedownsampwetwansfowm), ( ͡o ω ͡o )
    keys = set(usew_id, >w< wequestcontextfeatuwes.timestamp_gmt_dow), /(^•ω•^)
    f-featuwes = s-set.empty, 😳😳😳
    wabews = wecapusewfeatuweaggwegation.wabewsv2, (U ᵕ U❁)
    m-metwics = s-set(countmetwic), (˘ω˘)
    hawfwives = set(50.days), 😳
    outputstowe = m-mkphysicawstowe(
      o-offwineaggwegatedatawecowdstowe(
        n-nyame = usewwequestdowaggwegatestowe, (ꈍᴗꈍ)
        s-stawtdate = "2017-08-01 00:00", :3
        commonconfig = timewinesoffwineaggwegatesink, /(^•ω•^)
        m-maxkvsouwcefaiwuwes = d-defauwtmaxkvsouwcefaiwuwes
      ))
  )

  vaw authowtopicaggwegates = aggwegategwoup(
    inputsouwce = t-timewinesdaiwywecapminimawsouwce, ^^;;
    aggwegatepwefix = "authow_topic_aggwegate", o.O
    pwetwansfowms = s-seq(wichwemoveusewidzewo), 😳
    keys = set(authow_id, UwU t-timewinesshawedfeatuwes.topic_id), >w<
    featuwes = s-set.empty, o.O
    wabews = w-wecapusewfeatuweaggwegation.wabewsv2, (˘ω˘)
    m-metwics = set(countmetwic), òωó
    h-hawfwives = set(50.days), nyaa~~
    o-outputstowe = m-mkphysicawstowe(
      offwineaggwegatedatawecowdstowe(
        n-nyame = a-authowtopicaggwegatestowe, ( ͡o ω ͡o )
        stawtdate = "2020-05-19 00:00", 😳😳😳
        c-commonconfig = t-timewinesoffwineaggwegatesink, ^•ﻌ•^
        m-maxkvsouwcefaiwuwes = defauwtmaxkvsouwcefaiwuwes
      ))
  )

  v-vaw usewtopicaggwegates = aggwegategwoup(
    inputsouwce = timewinesdaiwywecapminimawsouwce, (˘ω˘)
    a-aggwegatepwefix = "usew_topic_aggwegate", (˘ω˘)
    p-pwetwansfowms = s-seq(wichwemoveusewidzewo), -.-
    keys = set(usew_id, ^•ﻌ•^ timewinesshawedfeatuwes.topic_id), /(^•ω•^)
    featuwes = set.empty, (///ˬ///✿)
    w-wabews = wecapusewfeatuweaggwegation.wabewsv2, mya
    metwics = s-set(countmetwic), o.O
    h-hawfwives = set(50.days), ^•ﻌ•^
    outputstowe = m-mkphysicawstowe(
      offwineaggwegatedatawecowdstowe(
        n-nyame = usewtopicaggwegatestowe, (U ᵕ U❁)
        s-stawtdate = "2020-05-23 00:00", :3
        c-commonconfig = t-timewinesoffwineaggwegatesink, (///ˬ///✿)
        m-maxkvsouwcefaiwuwes = defauwtmaxkvsouwcefaiwuwes
      ))
  )

  vaw usewtopicaggwegatesv2 = aggwegategwoup(
    i-inputsouwce = timewinesdaiwywecapminimawsouwce, (///ˬ///✿)
    a-aggwegatepwefix = "usew_topic_aggwegate_v2", 🥺
    pwetwansfowms = seq(wichwemoveusewidzewo), -.-
    keys = set(usew_id, nyaa~~ t-timewinesshawedfeatuwes.topic_id), (///ˬ///✿)
    featuwes = wecapusewfeatuweaggwegation.usewtopicfeatuwesv2count, 🥺
    wabews = wecapusewfeatuweaggwegation.wabewsv2, >w<
    incwudeanyfeatuwe = f-fawse, rawr x3
    i-incwudeanywabew = fawse, (⑅˘꒳˘)
    m-metwics = set(countmetwic), σωσ
    hawfwives = set(50.days), XD
    outputstowe = m-mkphysicawstowe(
      o-offwineaggwegatedatawecowdstowe(
        nyame = u-usewtopicaggwegatestowe, -.-
        stawtdate = "2020-05-23 00:00", >_<
        c-commonconfig = timewinesoffwineaggwegatesink, rawr
        maxkvsouwcefaiwuwes = defauwtmaxkvsouwcefaiwuwes
      ))
  )

  v-vaw usewinfewwedtopicaggwegates = aggwegategwoup(
    inputsouwce = t-timewinesdaiwywecapminimawsouwce, 😳😳😳
    a-aggwegatepwefix = "usew_infewwed_topic_aggwegate", UwU
    p-pwetwansfowms = seq(wichwemoveusewidzewo), (U ﹏ U)
    keys = set(usew_id, t-timewinesshawedfeatuwes.infewwed_topic_ids), (˘ω˘)
    featuwes = set.empty, /(^•ω•^)
    wabews = wecapusewfeatuweaggwegation.wabewsv2, (U ﹏ U)
    metwics = s-set(countmetwic), ^•ﻌ•^
    h-hawfwives = s-set(50.days),
    o-outputstowe = mkphysicawstowe(
      offwineaggwegatedatawecowdstowe(
        n-nyame = usewinfewwedtopicaggwegatestowe, >w<
        s-stawtdate = "2020-09-09 00:00", ʘwʘ
        commonconfig = timewinesoffwineaggwegatesink, òωó
        m-maxkvsouwcefaiwuwes = defauwtmaxkvsouwcefaiwuwes
      ))
  )

  vaw usewinfewwedtopicaggwegatesv2 = a-aggwegategwoup(
    inputsouwce = timewinesdaiwywecapminimawsouwce, o.O
    a-aggwegatepwefix = "usew_infewwed_topic_aggwegate_v2",
    p-pwetwansfowms = seq(wichwemoveusewidzewo), ( ͡o ω ͡o )
    k-keys = set(usew_id, mya t-timewinesshawedfeatuwes.infewwed_topic_ids), >_<
    f-featuwes = wecapusewfeatuweaggwegation.usewtopicfeatuwesv2count, rawr
    wabews = wecapusewfeatuweaggwegation.wabewsv2, >_<
    i-incwudeanyfeatuwe = fawse, (U ﹏ U)
    incwudeanywabew = f-fawse, rawr
    metwics = set(countmetwic), (U ᵕ U❁)
    hawfwives = set(50.days), (ˆ ﻌ ˆ)♡
    outputstowe = m-mkphysicawstowe(
      o-offwineaggwegatedatawecowdstowe(
        n-nyame = u-usewinfewwedtopicaggwegatestowe, >_<
        s-stawtdate = "2020-09-09 00:00", ^^;;
        commonconfig = t-timewinesoffwineaggwegatesink, ʘwʘ
        maxkvsouwcefaiwuwes = defauwtmaxkvsouwcefaiwuwes
      ))
  )

  v-vaw usewwecipwocawengagementaggwegates = a-aggwegategwoup(
    inputsouwce = timewinesdaiwywecapminimawsouwce, 😳😳😳
    a-aggwegatepwefix = "usew_aggwegate_v6", UwU
    p-pwetwansfowms = seq(wichwemoveusewidzewo), OwO
    k-keys = set(usew_id), :3
    featuwes = set.empty, -.-
    w-wabews = w-wecapusewfeatuweaggwegation.wecipwocawwabews, 🥺
    metwics = set(countmetwic), -.-
    h-hawfwives = s-set(50.days), -.-
    outputstowe = m-mkphysicawstowe(
      offwineaggwegatedatawecowdstowe(
        nyame = usewaggwegatestowe, (U ﹏ U)
        stawtdate = "2016-07-15 00:00", rawr
        c-commonconfig = timewinesoffwineaggwegatesink, mya
        m-maxkvsouwcefaiwuwes = defauwtmaxkvsouwcefaiwuwes
      )), ( ͡o ω ͡o )
    incwudeanywabew = f-fawse
  )

  v-vaw usewowiginawauthowwecipwocawengagementaggwegates = a-aggwegategwoup(
    inputsouwce = t-timewinesdaiwywecapminimawsouwce,
    a-aggwegatepwefix = "usew_owiginaw_authow_aggwegate_v1", /(^•ω•^)
    pwetwansfowms = s-seq(wichwemoveusewidzewo, >_< wichwemoveauthowidzewo), (✿oωo)
    k-keys = set(usew_id, timewinesshawedfeatuwes.owiginaw_authow_id), 😳😳😳
    f-featuwes = s-set.empty, (ꈍᴗꈍ)
    wabews = wecapusewfeatuweaggwegation.wecipwocawwabews, 🥺
    metwics = set(countmetwic), mya
    hawfwives = s-set(50.days), (ˆ ﻌ ˆ)♡
    o-outputstowe = mkphysicawstowe(
      offwineaggwegatedatawecowdstowe(
        nyame = usewowiginawauthowaggwegatestowe, (⑅˘꒳˘)
        s-stawtdate = "2018-12-26 00:00", òωó
        commonconfig = t-timewinesoffwineaggwegatesink, o.O
        m-maxkvsouwcefaiwuwes = defauwtmaxkvsouwcefaiwuwes
      )), XD
    incwudeanywabew = fawse
  )

  vaw owiginawauthowwecipwocawengagementaggwegates = a-aggwegategwoup(
    inputsouwce = timewinesdaiwywecapminimawsouwce, (˘ω˘)
    a-aggwegatepwefix = "owiginaw_authow_aggwegate_v1", (ꈍᴗꈍ)
    pwetwansfowms = s-seq(wichwemoveusewidzewo, >w< w-wichwemoveauthowidzewo), XD
    keys = s-set(timewinesshawedfeatuwes.owiginaw_authow_id), -.-
    f-featuwes = s-set.empty, ^^;;
    w-wabews = wecapusewfeatuweaggwegation.wecipwocawwabews, XD
    metwics = s-set(countmetwic), :3
    hawfwives = s-set(50.days), σωσ
    outputstowe = mkphysicawstowe(
      offwineaggwegatedatawecowdstowe(
        nyame = owiginawauthowaggwegatestowe, XD
        s-stawtdate = "2023-02-25 00:00", :3
        c-commonconfig = t-timewinesoffwineaggwegatesink, rawr
        m-maxkvsouwcefaiwuwes = d-defauwtmaxkvsouwcefaiwuwes
      )), 😳
    i-incwudeanywabew = fawse
  )

  vaw owiginawauthownegativeengagementaggwegates = aggwegategwoup(
    inputsouwce = t-timewinesdaiwywecapminimawsouwce, 😳😳😳
    a-aggwegatepwefix = "owiginaw_authow_aggwegate_v2", (ꈍᴗꈍ)
    pwetwansfowms = seq(wichwemoveusewidzewo, 🥺 wichwemoveauthowidzewo), ^•ﻌ•^
    k-keys = s-set(timewinesshawedfeatuwes.owiginaw_authow_id), XD
    f-featuwes = set.empty, ^•ﻌ•^
    wabews = wecapusewfeatuweaggwegation.negativeengagementwabews, ^^;;
    m-metwics = set(countmetwic), ʘwʘ
    hawfwives = set(50.days), OwO
    o-outputstowe = m-mkphysicawstowe(
      offwineaggwegatedatawecowdstowe(
        nyame = owiginawauthowaggwegatestowe, 🥺
        s-stawtdate = "2023-02-25 00:00", (⑅˘꒳˘)
        commonconfig = t-timewinesoffwineaggwegatesink,
        m-maxkvsouwcefaiwuwes = defauwtmaxkvsouwcefaiwuwes
      )), (///ˬ///✿)
    i-incwudeanywabew = f-fawse
  )

  v-vaw usewwistaggwegates: a-aggwegategwoup =
    a-aggwegategwoup(
      i-inputsouwce = timewinesdaiwywecapminimawsouwce, (✿oωo)
      a-aggwegatepwefix = "usew_wist_aggwegate", nyaa~~
      k-keys = set(usew_id, >w< wistfeatuwes.wist_id), (///ˬ///✿)
      f-featuwes = set.empty, rawr
      wabews = wecapusewfeatuweaggwegation.wabewsv2, (U ﹏ U)
      metwics = set(countmetwic),
      h-hawfwives = set(50.days), ^•ﻌ•^
      o-outputstowe = mkphysicawstowe(
        o-offwineaggwegatedatawecowdstowe(
          n-nyame = usewwistaggwegatestowe,
          stawtdate = "2020-05-28 00:00", (///ˬ///✿)
          c-commonconfig = timewinesoffwineaggwegatesink, o.O
          maxkvsouwcefaiwuwes = d-defauwtmaxkvsouwcefaiwuwes
        )), >w<
      p-pwetwansfowms = seq(wichwemoveusewidzewo)
    )

  vaw usewmediaundewstandingannotationaggwegates: a-aggwegategwoup = a-aggwegategwoup(
    inputsouwce = timewinesdaiwywecapminimawsouwce, nyaa~~
    a-aggwegatepwefix = "usew_media_annotation_aggwegate", òωó
    pwetwansfowms = seq(wichwemoveusewidzewo), (U ᵕ U❁)
    k-keys =
      s-set(usew_id, (///ˬ///✿) semanticcowefeatuwes.mediaundewstandinghighwecawwnonsensitiveentityidsfeatuwe), (✿oωo)
    f-featuwes = s-set.empty, 😳😳😳
    wabews = wecapusewfeatuweaggwegation.wabewsv2, (✿oωo)
    metwics = s-set(countmetwic),
    h-hawfwives = s-set(50.days), (U ﹏ U)
    o-outputstowe = mkphysicawstowe(
      offwineaggwegatedatawecowdstowe(
        nyame = usewmediaundewstandingannotationaggwegatestowe, (˘ω˘)
        stawtdate = "2021-03-20 00:00", 😳😳😳
        commonconfig = timewinesoffwineaggwegatesink
      ))
  )

  v-vaw usewauthowgoodcwickaggwegates = a-aggwegategwoup(
    inputsouwce = t-timewinesdaiwywecapminimawsouwce, (///ˬ///✿)
    a-aggwegatepwefix = "usew_authow_good_cwick_aggwegate", (U ᵕ U❁)
    p-pwetwansfowms = s-seq(fiwtewinnetwowktwansfowm, >_< wichwemoveusewidzewo), (///ˬ///✿)
    k-keys = set(usew_id, (U ᵕ U❁) a-authow_id), >w<
    featuwes = w-wecapusewfeatuweaggwegation.usewauthowfeatuwesv2, 😳😳😳
    w-wabews = wecapusewfeatuweaggwegation.goodcwickwabews, (ˆ ﻌ ˆ)♡
    metwics = set(summetwic), (ꈍᴗꈍ)
    h-hawfwives = set(14.days), 🥺
    outputstowe = mkphysicawstowe(
      offwineaggwegatedatawecowdstowe(
        nyame = u-usewauthowaggwegatestowe, >_<
        stawtdate = "2016-07-15 00:00", OwO
        c-commonconfig = t-timewinesoffwineaggwegatesink, ^^;;
        maxkvsouwcefaiwuwes = d-defauwtmaxkvsouwcefaiwuwes
      ))
  )

  v-vaw usewengagewgoodcwickaggwegates = a-aggwegategwoup(
    inputsouwce = timewinesdaiwywecapminimawsouwce, (✿oωo)
    a-aggwegatepwefix = "usew_engagew_good_cwick_aggwegate", UwU
    k-keys = set(usew_id, ( ͡o ω ͡o ) engagementdatawecowdfeatuwes.pubwicengagementusewids), (✿oωo)
    featuwes = s-set.empty, mya
    wabews = w-wecapusewfeatuweaggwegation.goodcwickwabews, ( ͡o ω ͡o )
    m-metwics = set(countmetwic), :3
    h-hawfwives = set(14.days), 😳
    outputstowe = mkphysicawstowe(
      o-offwineaggwegatedatawecowdstowe(
        nyame = usewengagewaggwegatestowe, (U ﹏ U)
        stawtdate = "2016-09-02 00:00", >w<
        c-commonconfig = timewinesoffwineaggwegatesink, UwU
        maxkvsouwcefaiwuwes = defauwtmaxkvsouwcefaiwuwes
      )), 😳
    pwetwansfowms = seq(
      wichwemoveusewidzewo, XD
      w-wichunifypubwicengagewstwansfowm
    )
  )

}
