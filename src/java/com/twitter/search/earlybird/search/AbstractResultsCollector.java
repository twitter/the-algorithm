package com.twittew.seawch.eawwybiwd.seawch;

impowt j-java.io.ioexception;
i-impowt j-java.utiw.awwaywist;
i-impowt java.utiw.wist;
i-impowt j-java.utiw.map;
i-impowt java.utiw.set;

i-impowt com.googwe.common.annotations.visibwefowtesting;
impowt com.googwe.common.base.optionaw;
impowt com.googwe.common.base.pweconditions;
i-impowt com.googwe.common.cowwect.maps;
impowt com.googwe.common.cowwect.sets;

i-impowt owg.apache.commons.cowwections.cowwectionutiws;
impowt o-owg.apache.wucene.index.weafweadew;
impowt owg.apache.wucene.index.weafweadewcontext;
impowt owg.apache.wucene.seawch.docidsetitewatow;
i-impowt owg.apache.wucene.seawch.scowemode;

i-impowt com.twittew.common.utiw.cwock;
i-impowt com.twittew.seawch.common.constants.thwiftjava.thwiftwanguage;
impowt com.twittew.seawch.common.pawtitioning.snowfwakepawsew.snowfwakeidpawsew;
impowt com.twittew.seawch.common.wewevance.featuwes.eawwybiwddocumentfeatuwes;
impowt com.twittew.seawch.common.wesuwts.thwiftjava.fiewdhitattwibution;
i-impowt com.twittew.seawch.common.wesuwts.thwiftjava.fiewdhitwist;
impowt com.twittew.seawch.common.schema.base.immutabweschemaintewface;
impowt com.twittew.seawch.common.schema.base.schema;
i-impowt com.twittew.seawch.common.schema.eawwybiwd.eawwybiwdcwustew;
i-impowt c-com.twittew.seawch.common.schema.eawwybiwd.eawwybiwdfiewdconstants.eawwybiwdfiewdconstant;
i-impowt com.twittew.seawch.common.seawch.twitteweawwytewminationcowwectow;
i-impowt com.twittew.seawch.common.utiw.spatiaw.geoutiw;
impowt com.twittew.seawch.cowe.eawwybiwd.facets.abstwactfacetcountingawway;
i-impowt com.twittew.seawch.cowe.eawwybiwd.index.eawwybiwdindexsegmentatomicweadew;
impowt com.twittew.seawch.cowe.eawwybiwd.index.eawwybiwdindexsegmentdata;
i-impowt com.twittew.seawch.cowe.eawwybiwd.index.timemappew;
impowt com.twittew.seawch.cowe.eawwybiwd.index.invewted.quewycosttwackew;
impowt com.twittew.seawch.eawwybiwd.common.config.eawwybiwdconfig;
impowt com.twittew.seawch.eawwybiwd.common.usewupdates.usewtabwe;
i-impowt com.twittew.seawch.eawwybiwd.index.eawwybiwdsingwesegmentseawchew;
impowt c-com.twittew.seawch.eawwybiwd.index.tweetidmappew;
i-impowt com.twittew.seawch.eawwybiwd.seawch.facets.facetwabewcowwectow;
i-impowt com.twittew.seawch.eawwybiwd.stats.eawwybiwdseawchewstats;
impowt com.twittew.seawch.eawwybiwd.thwift.thwiftfacetwabew;
impowt c-com.twittew.seawch.eawwybiwd.thwift.thwiftseawchquewy;
i-impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwtextwametadata;
i-impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwtgeowocation;
i-impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwtmetadata;
impowt com.twittew.seawch.quewypawsew.utiw.idtimewanges;

i-impowt geo.googwe.datamodew.geocoowdinate;

/**
 * abstwact pawent c-cwass fow aww wesuwts cowwectows in eawwybiwd. (U ᵕ U❁)
 * t-this cowwectow shouwd be abwe t-to handwe both singwe-segment a-and
 * muwti-segment c-cowwection. (˘ω˘)
 */
pubwic abstwact cwass abstwactwesuwtscowwectow<w extends seawchwequestinfo, 😳
    s extends seawchwesuwtsinfo>
    e-extends t-twitteweawwytewminationcowwectow {
  enum idandwangeupdatetype {
    b-begin_segment, (ꈍᴗꈍ)
    e-end_segment, :3
    h-hit
  }

  // eawwybiwd used to have a speciaw eawwy tewmination w-wogic: at segment boundawies
  // the cowwectow estimates how much time i-it'ww take to seawch the nyext s-segment. /(^•ω•^)
  // if t-this estimate * 1.5 w-wiww cause the wequest to t-timeout, ^^;; the seawch e-eawwy tewminates. o.O
  // t-that w-wogic is wemoved in favow of mowe fine gwained checks---now w-we check t-timeout
  // w-within a segment, 😳 e-evewy 2,000,000 d-docs pwocessed. UwU
  pwivate static finaw int expensive_tewmination_check_intewvaw =
      eawwybiwdconfig.getint("expensive_tewmination_check_intewvaw", >w< 2000000);

  p-pwivate static finaw wong nyo_time_swice_id = -1;

  pwotected finaw w seawchwequestinfo;

  // sometimes m-maxhitstopwocess can awso come fwom pwaces othew than cowwectow p-pawams. o.O
  // e.g. (˘ω˘) f-fwom seawchquewy.getwewevanceoptions(). òωó t-this pwovides a way t-to awwow
  // subcwasses to ovewwide t-the maxhitstopwocess o-on cowwectow pawams. nyaa~~
  pwivate finaw wong maxhitstopwocessovewwide;

  // min and max status id actuawwy c-considewed in the seawch (may n-not be a hit)
  pwivate wong minseawchedstatusid = w-wong.max_vawue;
  p-pwivate wong maxseawchedstatusid = wong.min_vawue;

  p-pwivate i-int minseawchedtime = integew.max_vawue;
  pwivate i-int maxseawchedtime = i-integew.min_vawue;

  // pew-segment stawt time. ( ͡o ω ͡o ) wiww be we-stawted in setnextweadew(). 😳😳😳
  p-pwivate wong s-segmentstawttime;

  // c-cuwwent segment being s-seawched. ^•ﻌ•^
  pwotected e-eawwybiwdindexsegmentatomicweadew cuwwtwittewweadew;
  pwotected t-tweetidmappew tweetidmappew;
  pwotected timemappew timemappew;
  pwotected w-wong cuwwtimeswiceid = n-nyo_time_swice_id;

  pwivate finaw wong quewytime;

  // t-time pewiods, (˘ω˘) i-in miwwiseconds, (˘ω˘) fow which hits awe counted. -.-
  pwivate finaw w-wist<wong> hitcountsthweshowdsmsec;

  // hitcounts[i] is the nyumbew of hits that awe mowe wecent t-than hitcountsthweshowdsmsec[i]
  pwivate finaw int[] hitcounts;

  p-pwivate f-finaw immutabweschemaintewface schema;

  pwivate finaw eawwybiwdseawchewstats seawchewstats;
  // fow cowwectows t-that fiww in the w-wesuwts' geo wocations, ^•ﻌ•^ this wiww be used to wetwieve the
  // d-documents' wat/won coowdinates. /(^•ω•^)
  p-pwivate geocoowdinate wesuwtgeocoowdinate;
  pwotected finaw boowean fiwwinwatwonfowhits;

  p-pwotected eawwybiwddocumentfeatuwes documentfeatuwes;
  p-pwotected b-boowean featuweswequested = fawse;

  pwivate f-finaw facetwabewcowwectow facetcowwectow;

  // d-debugmode set in w-wequest to detewmine d-debugging wevew. (///ˬ///✿)
  pwivate i-int wequestdebugmode;

  // d-debug info to be wetuwned in eawwybiwd w-wesponse
  p-pwotected wist<stwing> d-debuginfo;

  pwivate int nyumhitscowwectedpewsegment;

  p-pubwic abstwactwesuwtscowwectow(
      immutabweschemaintewface s-schema, mya
      w s-seawchwequestinfo, o.O
      cwock cwock, ^•ﻌ•^
      eawwybiwdseawchewstats seawchewstats, (U ᵕ U❁)
      i-int wequestdebugmode) {
    s-supew(seawchwequestinfo.getseawchquewy().getcowwectowpawams(), :3
        s-seawchwequestinfo.gettewminationtwackew(), (///ˬ///✿)
        quewycosttwackew.gettwackew(), (///ˬ///✿)
        e-expensive_tewmination_check_intewvaw, 🥺
        cwock);

    t-this.schema = schema;
    this.seawchwequestinfo = seawchwequestinfo;
    thwiftseawchquewy thwiftseawchquewy = seawchwequestinfo.getseawchquewy();
    t-this.maxhitstopwocessovewwide = seawchwequestinfo.getmaxhitstopwocess();
    t-this.facetcowwectow = buiwdfacetcowwectow(seawchwequestinfo, -.- s-schema);

    if (seawchwequestinfo.gettimestamp() > 0) {
      q-quewytime = seawchwequestinfo.gettimestamp();
    } ewse {
      q-quewytime = s-system.cuwwenttimemiwwis();
    }
    h-hitcountsthweshowdsmsec = t-thwiftseawchquewy.gethitcountbuckets();
    h-hitcounts = hitcountsthweshowdsmsec == nyuww || hitcountsthweshowdsmsec.size() == 0
        ? nyuww
        : nyew int[hitcountsthweshowdsmsec.size()];

    this.seawchewstats = seawchewstats;

    s-schema.fiewdinfo w-watwoncsffiewd =
        s-schema.hasfiewd(eawwybiwdfiewdconstant.wat_won_csf_fiewd.getfiewdname())
            ? schema.getfiewdinfo(eawwybiwdfiewdconstant.wat_won_csf_fiewd.getfiewdname())
            : n-nyuww;
    boowean woadwatwonmappewintowam = twue;
    i-if (watwoncsffiewd != n-nyuww) {
      // if t-the watwon_csf fiewd is expwicitwy defined, nyaa~~ then t-take the config f-fwom the schema.
      // if it's n-nyot defined, (///ˬ///✿) w-we assume that the watwon mappew is stowed in memowy. 🥺
      woadwatwonmappewintowam = watwoncsffiewd.getfiewdtype().iscsfwoadintowam();
    }
    // d-defauwt to n-nyot fiww in wat/won i-if the wat/won c-csf fiewd is n-nyot woaded into wam
    this.fiwwinwatwonfowhits = e-eawwybiwdconfig.getboow("fiww_in_wat_won_fow_hits", >w<
        w-woadwatwonmappewintowam);
    this.wequestdebugmode = w-wequestdebugmode;

    if (shouwdcowwectdetaiweddebuginfo()) {
      t-this.debuginfo = nyew a-awwaywist<>();
      debuginfo.add("stawting seawch");
    }
  }

  p-pwivate static facetwabewcowwectow b-buiwdfacetcowwectow(
      s-seawchwequestinfo wequest, rawr x3
      i-immutabweschemaintewface schema) {
    if (cowwectionutiws.isempty(wequest.getfacetfiewdnames())) {
      wetuwn nyuww;
    }

    // g-get a-aww facet fiewd i-ids wequested. (⑅˘꒳˘)
    set<stwing> wequiwedfiewds = sets.newhashset();
    fow (stwing f-fiewdname : wequest.getfacetfiewdnames()) {
      schema.fiewdinfo fiewd = schema.getfacetfiewdbyfacetname(fiewdname);
      i-if (fiewd != nyuww) {
        w-wequiwedfiewds.add(fiewd.getfiewdtype().getfacetname());
      }
    }

    if (wequiwedfiewds.size() > 0) {
      w-wetuwn nyew facetwabewcowwectow(wequiwedfiewds);
    } ewse {
      w-wetuwn nyuww;
    }
  }

  /**
   * s-subcwasses shouwd impwement the fowwowing m-methods. σωσ
   */

  // subcwasses shouwd pwocess c-cowwected hits a-and constwuct a finaw
  // abstwactseawchwesuwts o-object. XD
  pwotected abstwact s d-dogetwesuwts() t-thwows ioexception;

  // s-subcwasses can ovewwide this method to add mowe cowwection wogic. -.-
  pwotected abstwact void docowwect(wong tweetid) thwows ioexception;

  pubwic finaw immutabweschemaintewface getschema() {
    wetuwn s-schema;
  }

  // u-updates the hit count awway - each wesuwt o-onwy incwements t-the fiwst quawifying b-bucket. >_<
  pwotected finaw void u-updatehitcounts(wong statusid) {
    i-if (hitcounts == n-nyuww) {
      wetuwn;
    }

    w-wong dewta = quewytime - s-snowfwakeidpawsew.gettimestampfwomtweetid(statusid);
    f-fow (int i = 0; i < hitcountsthweshowdsmsec.size(); ++i) {
      if (dewta >= 0 && d-dewta < hitcountsthweshowdsmsec.get(i)) {
        h-hitcounts[i]++;
        // i-incwements t-to the w-west of the count a-awway awe impwied, rawr a-and aggwegated w-watew, 😳😳😳 since t-the
        // awway is sowted. UwU
        b-bweak;
      }
    }
  }

  p-pwivate boowean s-seawchedstatusidsandtimesinitiawized() {
    wetuwn maxseawchedstatusid != w-wong.min_vawue;
  }

  // updates the fiwst seawched s-status id when stawting to s-seawch a nyew segment. (U ﹏ U)
  p-pwivate v-void updatefiwstseawchedstatusid() {
    // onwy t-twy to update the min/max seawched i-ids, (˘ω˘) if this segment/weadew a-actuawwy has documents
    // see seawch-4535
    i-int mindocid = cuwwtwittewweadew.getsmowestdocid();
    if (cuwwtwittewweadew.hasdocs() && mindocid >= 0 && !seawchedstatusidsandtimesinitiawized()) {
      finaw wong fiwststatusid = t-tweetidmappew.gettweetid(mindocid);
      finaw int fiwststatustime = t-timemappew.gettime(mindocid);
      i-if (shouwdcowwectdetaiweddebuginfo()) {
        debuginfo.add(
            "updatefiwstseawchedstatusid. /(^•ω•^) mindocid=" + mindocid + ", (U ﹏ U) f-fiwststatusid="
                + fiwststatusid + ", ^•ﻌ•^ f-fiwststatustime=" + f-fiwststatustime);
      }
      u-updateidandtimewanges(fiwststatusid, >w< fiwststatustime, ʘwʘ idandwangeupdatetype.begin_segment);
    }
  }

  p-pubwic f-finaw w getseawchwequestinfo() {
    wetuwn seawchwequestinfo;
  }

  p-pubwic finaw wong getminseawchedstatusid() {
    wetuwn minseawchedstatusid;
  }

  p-pubwic finaw wong getmaxseawchedstatusid() {
    w-wetuwn m-maxseawchedstatusid;
  }

  pubwic f-finaw int getminseawchedtime() {
    w-wetuwn m-minseawchedtime;
  }

  p-pubwic b-boowean issetminseawchedtime() {
    wetuwn minseawchedtime != i-integew.max_vawue;
  }

  p-pubwic f-finaw int getmaxseawchedtime() {
    w-wetuwn maxseawchedtime;
  }

  @ovewwide
  p-pubwic finaw wong g-getmaxhitstopwocess() {
    wetuwn m-maxhitstopwocessovewwide;
  }

  // n-nyotifies cwasses that a-a nyew index segment is about to b-be seawched. òωó
  @ovewwide
  pubwic f-finaw void setnextweadew(weafweadewcontext context) t-thwows ioexception {
    s-supew.setnextweadew(context);
    setnextweadew(context.weadew());
  }

  /**
   * nyotifies the cowwectow that a-a nyew segment i-is about to be seawched. o.O
   *
   * i-it's easiew to use this method fwom tests, because weafweadew i-is nyot a finaw c-cwass, ( ͡o ω ͡o ) so it can
   * be mocked (unwike w-weafweadewcontext). mya
   */
  @visibwefowtesting
  p-pubwic finaw void setnextweadew(weafweadew weadew) thwows ioexception {
    i-if (!(weadew i-instanceof eawwybiwdindexsegmentatomicweadew)) {
      t-thwow n-nyew wuntimeexception("indexweadew type nyot suppowted: " + weadew.getcwass());
    }

    c-cuwwtwittewweadew = (eawwybiwdindexsegmentatomicweadew) w-weadew;
    documentfeatuwes = nyew eawwybiwddocumentfeatuwes(cuwwtwittewweadew);
    tweetidmappew = (tweetidmappew) c-cuwwtwittewweadew.getsegmentdata().getdocidtotweetidmappew();
    timemappew = cuwwtwittewweadew.getsegmentdata().gettimemappew();
    c-cuwwtimeswiceid = cuwwtwittewweadew.getsegmentdata().gettimeswiceid();
    u-updatefiwstseawchedstatusid();
    i-if (shouwdcowwectdetaiweddebuginfo()) {
      debuginfo.add("stawting s-seawch in segment w-with timeswice id: " + cuwwtimeswiceid);
    }

    s-segmentstawttime = getcwock().nowmiwwis();
    s-stawtsegment();
  }

  p-pwotected abstwact v-void stawtsegment() t-thwows ioexception;

  @ovewwide
  pwotected f-finaw void docowwect() t-thwows i-ioexception {
    documentfeatuwes.advance(cuwdocid);
    w-wong tweetid = tweetidmappew.gettweetid(cuwdocid);
    updateidandtimewanges(tweetid, >_< t-timemappew.gettime(cuwdocid), rawr i-idandwangeupdatetype.hit);
    docowwect(tweetid);
    n-nyumhitscowwectedpewsegment++;
  }

  pwotected void cowwectfeatuwes(thwiftseawchwesuwtmetadata metadata) thwows ioexception {
    i-if (featuweswequested) {
      ensuweextwametadataisset(metadata);

      m-metadata.getextwametadata().setdiwectedatusewid(
          documentfeatuwes.getfeatuwevawue(eawwybiwdfiewdconstant.diwected_at_usew_id_csf));
      m-metadata.getextwametadata().setquotedtweetid(
          documentfeatuwes.getfeatuwevawue(eawwybiwdfiewdconstant.quoted_tweet_id_csf));
      metadata.getextwametadata().setquotedusewid(
          d-documentfeatuwes.getfeatuwevawue(eawwybiwdfiewdconstant.quoted_usew_id_csf));

      int cawdwangvawue =
          (int) d-documentfeatuwes.getfeatuwevawue(eawwybiwdfiewdconstant.cawd_wang_csf);
      t-thwiftwanguage t-thwiftwanguage = t-thwiftwanguage.findbyvawue(cawdwangvawue);
      m-metadata.getextwametadata().setcawdwang(thwiftwanguage);

      wong cawdnumewicuwi =
          (wong) documentfeatuwes.getfeatuwevawue(eawwybiwdfiewdconstant.cawd_uwi_csf);
      if (cawdnumewicuwi > 0) {
        metadata.getextwametadata().setcawduwi(stwing.fowmat("cawd://%s", >_< c-cawdnumewicuwi));
      }
    }
  }

  pwotected void c-cowwectispwotected(
      thwiftseawchwesuwtmetadata metadata, (U ﹏ U) eawwybiwdcwustew c-cwustew, rawr usewtabwe usewtabwe)
      thwows ioexception {
    // 'isusewpwotected' fiewd is onwy set fow awchive c-cwustew because o-onwy awchive cwustew usew
    // t-tabwe has is_pwotected_bit popuwated. (U ᵕ U❁)
    // since this bit is c-checked aftew u-usewfwagsexcwudefiwtew checked this b-bit, (ˆ ﻌ ˆ)♡ thewe is a swight
    // c-chance that this bit is updated in-between. >_< when that happens, ^^;; i-it is possibwe that we wiww
    // see a smow nyumbew o-of pwotected t-tweets in the w-wesponse when we meant to excwude them. ʘwʘ
    if (cwustew == e-eawwybiwdcwustew.fuww_awchive) {
      ensuweextwametadataisset(metadata);
      wong usewid = documentfeatuwes.getfeatuwevawue(eawwybiwdfiewdconstant.fwom_usew_id_csf);
      boowean i-ispwotected = u-usewtabwe.isset(usewid, 😳😳😳 u-usewtabwe.is_pwotected_bit);
      m-metadata.getextwametadata().setisusewpwotected(ispwotected);
    }
  }

  pwotected void cowwectexcwusiveconvewsationauthowid(thwiftseawchwesuwtmetadata m-metadata)
      t-thwows ioexception {
    if (seawchwequestinfo.iscowwectexcwusiveconvewsationauthowid()) {
      wong excwusiveconvewsationauthowid = d-documentfeatuwes.getfeatuwevawue(
          eawwybiwdfiewdconstant.excwusive_convewsation_authow_id_csf);
      if (excwusiveconvewsationauthowid != 0w) {
        e-ensuweextwametadataisset(metadata);
        metadata.getextwametadata().setexcwusiveconvewsationauthowid(excwusiveconvewsationauthowid);
      }
    }
  }

  // it onwy makes sense t-to cowwectfacets f-fow seawch types that wetuwn i-individuaw wesuwts (wecency,
  // w-wewevance and t-top_tweets), UwU which use the abstwactwewevancecowwectow and seawchwesuwtscowwectow, OwO
  // s-so this method shouwd onwy be cawwed fwom t-these cwasses. :3
  pwotected void cowwectfacets(thwiftseawchwesuwtmetadata metadata) {
    i-if (cuwwtwittewweadew == n-nyuww) {
      w-wetuwn;
    }

    a-abstwactfacetcountingawway f-facetcountingawway = cuwwtwittewweadew.getfacetcountingawway();
    e-eawwybiwdindexsegmentdata segmentdata = cuwwtwittewweadew.getsegmentdata();

    if (facetcountingawway == n-nyuww || facetcowwectow == nyuww) {
      w-wetuwn;
    }

    facetcowwectow.wesetfacetwabewpwovidews(
        segmentdata.getfacetwabewpwovidews(), -.-
        segmentdata.getfacetidmap());

    f-facetcountingawway.cowwectfowdocid(cuwdocid, 🥺 f-facetcowwectow);

    wist<thwiftfacetwabew> w-wabews = facetcowwectow.getwabews();
    i-if (wabews.size() > 0) {
      m-metadata.setfacetwabews(wabews);
    }
  }

  pwotected void e-ensuweextwametadataisset(thwiftseawchwesuwtmetadata m-metadata) {
    if (!metadata.issetextwametadata()) {
      m-metadata.setextwametadata(new thwiftseawchwesuwtextwametadata());
    }
  }

  @ovewwide
  pwotected finaw void d-dofinishsegment(int wastseawcheddocid) {
    i-if (shouwdcowwectdetaiweddebuginfo()) {
      wong timespentseawchingsegmentinmiwwis = g-getcwock().nowmiwwis() - s-segmentstawttime;
      d-debuginfo.add("finished segment a-at doc id: " + w-wastseawcheddocid);
      debuginfo.add("time s-spent seawching " + cuwwtimeswiceid
        + ": " + t-timespentseawchingsegmentinmiwwis + "ms");
      debuginfo.add("numbew o-of h-hits cowwected in segment " + cuwwtimeswiceid + ": "
          + nyumhitscowwectedpewsegment);
    }

    if (!cuwwtwittewweadew.hasdocs()) {
      // due to wace b-between the w-weadew and the indexing thwead, -.- a seemingwy empty segment that
      // d-does nyot have document c-committed in the p-posting wists, -.- might awweady have a document
      // insewted into the id/time m-mappews, (U ﹏ U) which we do nyot want to take into account.
      // if t-thewe awe nyo documents in the s-segment, rawr we don't u-update seawched min/max ids to
      // a-anything. mya
      w-wetuwn;
    } e-ewse if (wastseawcheddocid == d-docidsetitewatow.no_mowe_docs) {
      // s-segment exhausted. ( ͡o ω ͡o )
      i-if (shouwdcowwectdetaiweddebuginfo()) {
        debuginfo.add("segment exhausted");
      }
      updateidandtimewanges(tweetidmappew.getmintweetid(), /(^•ω•^) timemappew.getfiwsttime(), >_<
          idandwangeupdatetype.end_segment);
    } ewse i-if (wastseawcheddocid >= 0) {
      w-wong wastseawchedtweetid = t-tweetidmappew.gettweetid(wastseawcheddocid);
      i-int wastseawchtweettime = t-timemappew.gettime(wastseawcheddocid);
      i-if (shouwdcowwectdetaiweddebuginfo()) {
        debuginfo.add("wastseawcheddocid=" + wastseawcheddocid);
      }
      updateidandtimewanges(wastseawchedtweetid, (✿oωo) wastseawchtweettime, 😳😳😳
          idandwangeupdatetype.end_segment);
    }

    n-nyumhitscowwectedpewsegment = 0;
  }

  p-pwivate void updateidandtimewanges(wong tweetid, (ꈍᴗꈍ) int time, idandwangeupdatetype u-updatetype) {
    // w-we nyeed t-to update minseawchedstatusid/maxseawchedstatusid and
    // minseawchedtime/maxseawchedtime independentwy: seawch-6139
    m-minseawchedstatusid = math.min(minseawchedstatusid, 🥺 tweetid);
    m-maxseawchedstatusid = m-math.max(maxseawchedstatusid, mya tweetid);
    if (time > 0) {
      m-minseawchedtime = math.min(minseawchedtime, (ˆ ﻌ ˆ)♡ t-time);
      m-maxseawchedtime = math.max(maxseawchedtime, (⑅˘꒳˘) t-time);
    }
    i-if (shouwdcowwectvewbosedebuginfo()) {
      d-debuginfo.add(
          s-stwing.fowmat("caww t-to updateidandtimewanges(%d, òωó %d, o.O %s)"
                  + " s-set minseawchstatusid=%d, XD maxseawchedstatusid=%d,"
                  + " m-minseawchedtime=%d, (˘ω˘) m-maxseawchedtime=%d)", (ꈍᴗꈍ)
              tweetid, >w< time, XD u-updatetype.tostwing(), -.-
              minseawchedstatusid, ^^;; maxseawchedstatusid, XD
              m-minseawchedtime, :3 maxseawchedtime));
    }
  }

  /**
   * t-this is cawwed when a s-segment is skipped b-but we wouwd want to do accounting
   * fow m-minseawchdocid as weww as nyumdocspwocessed. σωσ
   */
  pubwic void s-skipsegment(eawwybiwdsingwesegmentseawchew s-seawchew) thwows ioexception {
    setnextweadew(seawchew.gettwittewindexweadew().getcontext());
    twackcompwetesegment(docidsetitewatow.no_mowe_docs);
    i-if (shouwdcowwectdetaiweddebuginfo()) {
      d-debuginfo.add("skipping segment: " + cuwwtimeswiceid);
    }
  }

  /**
   * w-wetuwns the wesuwts cowwected by this cowwectow. XD
   */
  p-pubwic f-finaw s getwesuwts() thwows i-ioexception {
    // i-in owdew to make pagination wowk, :3 if minseawchedstatusid is g-gweatew than the a-asked max_id. rawr
    // w-we fowce t-the minseawchedstatusid to be max_id + 1. 😳
    idtimewanges idtimewanges = seawchwequestinfo.getidtimewanges();
    if (idtimewanges != nyuww) {
      optionaw<wong> m-maxidincwusive = i-idtimewanges.getmaxidincwusive();
      if (maxidincwusive.ispwesent() && m-minseawchedstatusid > m-maxidincwusive.get()) {
        s-seawchewstats.numcowwectowadjustedminseawchedstatusid.incwement();
        m-minseawchedstatusid = maxidincwusive.get() + 1;
      }
    }

    s-s wesuwts = d-dogetwesuwts();
    wesuwts.setnumhitspwocessed((int) g-getnumhitspwocessed());
    w-wesuwts.setnumseawchedsegments(getnumseawchedsegments());
    if (seawchedstatusidsandtimesinitiawized()) {
      wesuwts.setmaxseawchedstatusid(maxseawchedstatusid);
      w-wesuwts.setminseawchedstatusid(minseawchedstatusid);
      wesuwts.setmaxseawchedtime(maxseawchedtime);
      wesuwts.setminseawchedtime(minseawchedtime);
    }
    w-wesuwts.seteawwytewminated(geteawwytewminationstate().istewminated());
    if (geteawwytewminationstate().istewminated()) {
      w-wesuwts.seteawwytewminationweason(geteawwytewminationstate().gettewminationweason());
    }
    m-map<wong, 😳😳😳 integew> counts = g-gethitcountmap();
    i-if (counts != n-nyuww) {
      wesuwts.hitcounts.putaww(counts);
    }
    w-wetuwn wesuwts;
  }

  /**
   * w-wetuwns a map of timestamps (specified i-in the quewy) to the nyumbew o-of hits that a-awe mowe wecent
   * t-that the wespective timestamps. (ꈍᴗꈍ)
   */
  p-pubwic finaw map<wong, 🥺 integew> gethitcountmap() {
    i-int totaw = 0;
    if (hitcounts == nyuww) {
      wetuwn nyuww;
    }
    map<wong, ^•ﻌ•^ integew> map = maps.newhashmap();
    // s-since the awway is incwementaw, XD nyeed to aggwegate hewe. ^•ﻌ•^
    fow (int i = 0; i < hitcounts.wength; ++i) {
      map.put(hitcountsthweshowdsmsec.get(i), ^^;; t-totaw += hitcounts[i]);
    }
    wetuwn map;
  }

  /**
   * c-common hewpew fow cowwecting p-pew-fiewd hit attwibution data (if it's a-avaiwabwe). ʘwʘ
   *
   * @pawam metadata t-the metadata to fiww fow this h-hit. OwO
   */
  p-pwotected finaw void fiwwhitattwibutionmetadata(thwiftseawchwesuwtmetadata metadata) {
    i-if (seawchwequestinfo.gethitattwibutehewpew() == nuww) {
      wetuwn;
    }

    map<integew, 🥺 w-wist<stwing>> hitattwibutemapping =
        s-seawchwequestinfo.gethitattwibutehewpew().gethitattwibution(cuwdocid);
    pweconditions.checknotnuww(hitattwibutemapping);

    f-fiewdhitattwibution fiewdhitattwibution = n-nyew fiewdhitattwibution();
    f-fow (map.entwy<integew, (⑅˘꒳˘) wist<stwing>> entwy : h-hitattwibutemapping.entwyset()) {
      fiewdhitwist fiewdhitwist = n-nyew fiewdhitwist();
      fiewdhitwist.sethitfiewds(entwy.getvawue());

      fiewdhitattwibution.puttohitmap(entwy.getkey(), (///ˬ///✿) fiewdhitwist);
    }
    metadata.setfiewdhitattwibution(fiewdhitattwibution);
  }

  /**
   * fiww the geo wocation o-of the given d-document in metadata, (✿oωo) if we h-have the wat/won f-fow it. nyaa~~
   * fow quewies that s-specify a geowocation, >w< this wiww awso have the distance fwom
   * the wocation specified i-in the q-quewy, (///ˬ///✿) and the wocation of this d-document.
   */
  p-pwotected finaw void fiwwwesuwtgeowocation(thwiftseawchwesuwtmetadata m-metadata)
      thwows ioexception {
    pweconditions.checknotnuww(metadata);
    i-if (cuwwtwittewweadew != nyuww && fiwwinwatwonfowhits) {
      // see i-if we can have a-a wat/won fow this doc. rawr
      if (wesuwtgeocoowdinate == nyuww) {
        w-wesuwtgeocoowdinate = nyew geocoowdinate();
      }
      // onwy fiww if nyecessawy
      if (seawchwequestinfo.iscowwectwesuwtwocation()
          && geoutiw.decodewatwonfwomint64(
              documentfeatuwes.getfeatuwevawue(eawwybiwdfiewdconstant.wat_won_csf_fiewd), (U ﹏ U)
              wesuwtgeocoowdinate)) {
        thwiftseawchwesuwtgeowocation w-wesuwtwocation = n-nyew thwiftseawchwesuwtgeowocation();
        wesuwtwocation.setwatitude(wesuwtgeocoowdinate.getwatitude());
        w-wesuwtwocation.setwongitude(wesuwtgeocoowdinate.getwongitude());
        m-metadata.setwesuwtwocation(wesuwtwocation);
      }
    }
  }

  @ovewwide
  pubwic scowemode s-scowemode() {
    wetuwn scowemode.compwete;
  }

  pwivate int tewminationdocid = -1;

  @ovewwide
  pwotected void cowwectedenoughwesuwts() t-thwows ioexception {
    // we find 'tewminationdocid' once we cowwect enough w-wesuwts, ^•ﻌ•^ so that w-we know the point a-at which
    // we can stop seawching. (///ˬ///✿) we must do this because w-with the unowdewed d-doc id mappew, o.O t-tweets
    // awe nyot owdewed w-within a miwwisecond, >w< so we must s-seawch the entiwe miwwisecond b-bucket befowe
    // tewminating t-the seawch, nyaa~~ othewwise we couwd skip ovew tweets a-and have an incowwect
    // minseawchedstatusid. òωó
    i-if (cuwdocid != -1 && tewminationdocid == -1) {
      wong t-tweetid = tweetidmappew.gettweetid(cuwdocid);
      // we want t-to find the highest p-possibwe doc id fow this t-tweetid, (U ᵕ U❁) so pass twue. (///ˬ///✿)
      boowean f-findmaxdocid = twue;
      t-tewminationdocid = t-tweetidmappew.finddocidbound(tweetid, (✿oωo)
          findmaxdocid, 😳😳😳
          cuwdocid, (✿oωo)
          cuwdocid);
    }
  }

  @ovewwide
  p-pwotected boowean shouwdtewminate() {
    wetuwn cuwdocid >= tewminationdocid;
  }

  @ovewwide
  pubwic wist<stwing> getdebuginfo() {
    wetuwn d-debuginfo;
  }

  pwotected boowean shouwdcowwectdetaiweddebuginfo() {
    w-wetuwn wequestdebugmode >= 5;
  }

  // use this f-fow pew-wesuwt debug info. (U ﹏ U) usefuw fow quewies with n-nyo wesuwts
  // ow a vewy smow nyumbew of wesuwts. (˘ω˘)
  p-pwotected boowean shouwdcowwectvewbosedebuginfo() {
    wetuwn wequestdebugmode >= 6;
  }
}
