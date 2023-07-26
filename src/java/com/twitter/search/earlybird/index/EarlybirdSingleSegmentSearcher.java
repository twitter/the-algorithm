package com.twittew.seawch.eawwybiwd.index;

impowt j-java.io.ioexception;
i-impowt java.utiw.wist;
impowt j-java.utiw.wocawe;
i-impowt java.utiw.map;
i-impowt j-java.utiw.map.entwy;

i-impowt c-com.googwe.common.base.pweconditions;

impowt owg.apache.wucene.index.weafweadewcontext;
impowt owg.apache.wucene.index.tewm;
i-impowt owg.apache.wucene.seawch.cowwectionstatistics;
impowt owg.apache.wucene.seawch.cowwectow;
impowt owg.apache.wucene.seawch.docidsetitewatow;
i-impowt owg.apache.wucene.seawch.expwanation;
impowt owg.apache.wucene.seawch.weafcowwectow;
impowt o-owg.apache.wucene.seawch.scowew;
impowt owg.apache.wucene.seawch.scowemode;
impowt owg.apache.wucene.seawch.tewmstatistics;
impowt owg.apache.wucene.seawch.weight;
i-impowt owg.apache.wucene.utiw.byteswef;
i-impowt owg.swf4j.woggew;
i-impowt owg.swf4j.woggewfactowy;

impowt com.twittew.common.utiw.cwock;
impowt com.twittew.seawch.common.constants.thwiftjava.thwiftwanguage;
i-impowt com.twittew.seawch.common.wewevance.featuwes.eawwybiwddocumentfeatuwes;
impowt com.twittew.seawch.common.wesuwts.thwiftjava.fiewdhitattwibution;
impowt com.twittew.seawch.common.schema.base.immutabweschemaintewface;
impowt com.twittew.seawch.common.schema.base.schema;
impowt c-com.twittew.seawch.common.schema.eawwybiwd.eawwybiwdfiewdconstants.eawwybiwdfiewdconstant;
impowt c-com.twittew.seawch.common.seawch.twittewcowwectow;
i-impowt com.twittew.seawch.common.seawch.twittewindexseawchew;
i-impowt com.twittew.seawch.common.utiw.anawysis.wongtewmattwibuteimpw;
i-impowt com.twittew.seawch.common.utiw.wang.thwiftwanguageutiw;
impowt c-com.twittew.seawch.cowe.eawwybiwd.facets.facetwabewpwovidew;
impowt com.twittew.seawch.cowe.eawwybiwd.index.docidtotweetidmappew;
impowt com.twittew.seawch.cowe.eawwybiwd.index.eawwybiwdindexsegmentatomicweadew;
i-impowt com.twittew.seawch.cowe.eawwybiwd.index.eawwybiwdindexsegmentdata;
impowt com.twittew.seawch.eawwybiwd.eawwybiwdseawchew;
impowt com.twittew.seawch.eawwybiwd.common.config.eawwybiwdconfig;
impowt com.twittew.seawch.eawwybiwd.common.usewupdates.usewtabwe;
impowt c-com.twittew.seawch.eawwybiwd.seawch.eawwybiwdwuceneseawchew;
impowt com.twittew.seawch.eawwybiwd.seawch.hit;
i-impowt com.twittew.seawch.eawwybiwd.seawch.seawchwequestinfo;
i-impowt c-com.twittew.seawch.eawwybiwd.seawch.simpweseawchwesuwts;
impowt com.twittew.seawch.eawwybiwd.seawch.facets.abstwactfacettewmcowwectow;
impowt c-com.twittew.seawch.eawwybiwd.seawch.facets.tewmstatisticscowwectow;
i-impowt com.twittew.seawch.eawwybiwd.seawch.facets.tewmstatisticswequestinfo;
impowt com.twittew.seawch.eawwybiwd.seawch.wewevance.scowing.wewevancequewy;
i-impowt com.twittew.seawch.eawwybiwd.stats.eawwybiwdseawchewstats;
i-impowt com.twittew.seawch.eawwybiwd.thwift.thwiftfacetcount;
impowt com.twittew.seawch.eawwybiwd.thwift.thwiftfacetcountmetadata;
i-impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwt;
impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwtmetadata;
i-impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwts;
impowt c-com.twittew.seawch.eawwybiwd.thwift.thwifttewmwequest;
impowt c-com.twittew.seawch.eawwybiwd.thwift.thwifttewmwesuwts;
impowt com.twittew.seawch.eawwybiwd.thwift.thwifttewmstatisticswesuwts;

p-pubwic cwass eawwybiwdsingwesegmentseawchew e-extends eawwybiwdwuceneseawchew {
  pwivate static finaw woggew wog = woggewfactowy.getwoggew(eawwybiwdsingwesegmentseawchew.cwass);

  pwivate finaw eawwybiwdindexsegmentatomicweadew t-twittewweadew;
  p-pwivate finaw immutabweschemaintewface s-schema;
  p-pwivate finaw u-usewtabwe usewtabwe;
  pwivate finaw wong timeswiceid;

  pwivate f-finaw eawwybiwdseawchewstats seawchewstats;
  pwivate cwock cwock;

  pubwic eawwybiwdsingwesegmentseawchew(
      i-immutabweschemaintewface schema,
      e-eawwybiwdindexsegmentatomicweadew w-weadew, ^^;;
      u-usewtabwe usewtabwe, ʘwʘ
      eawwybiwdseawchewstats s-seawchewstats, ^^
      c-cwock cwock) {
    s-supew(weadew);
    t-this.schema = schema;
    this.twittewweadew = w-weadew;
    t-this.usewtabwe = u-usewtabwe;
    t-this.timeswiceid = w-weadew.getsegmentdata().gettimeswiceid();
    this.seawchewstats = seawchewstats;
    this.cwock = cwock;
  }

  p-pubwic finaw wong gettimeswiceid() {
    wetuwn timeswiceid;
  }

  pubwic eawwybiwdindexsegmentatomicweadew gettwittewindexweadew() {
    wetuwn twittewweadew;
  }

  /**
   * s-seawch() main woop. nyaa~~
   * this behaves exactwy wike i-indexseawchew.seawch() i-if a stock w-wucene cowwectow passed in. (///ˬ///✿)
   * h-howevew, XD if a twittewcowwectow i-is passed in, :3 t-this cwass pewfowms twittew stywe eawwy
   * tewmination without wewying on
   * {@wink owg.apache.wucene.seawch.cowwectiontewminatedexception}. òωó
   * t-this method is nyeawwy identicaw t-to twittewindexseawchew.seawch() with two d-diffewences:
   *  1) a-advances to smowest docid befowe seawching. ^^  i-impowtant to s-skip incompwete docs in
   *     w-weawtime segments. ^•ﻌ•^
   *  2) s-skips dewetes using twittewweadew
   */
  @ovewwide
  pwotected void seawch(wist<weafweadewcontext> w-weaves, σωσ weight w-weight, (ˆ ﻌ ˆ)♡ cowwectow c-coww)
      thwows ioexception {
    // i-if an t-twittewcowwectow is passed in, nyaa~~ w-we can do a few extwa things in hewe, such
    // as eawwy tewmination. ʘwʘ  othewwise w-we can just faww b-back to indexseawchew.seawch(). ^•ﻌ•^
    if (!(coww instanceof twittewcowwectow)) {
      s-supew.seawch(weaves, rawr x3 w-weight, 🥺 coww);
      wetuwn;
    }

    twittewcowwectow c-cowwectow = (twittewcowwectow) coww;
    if (cowwectow.istewminated()) {
      wetuwn;
    }

    wog.debug("stawting s-segment {}", timeswiceid);

    // notify the cowwectow t-that we'we s-stawting this segment, ʘwʘ and check fow eawwy
    // tewmination cwitewia a-again. (˘ω˘)  setnextweadew() pewfowms 'expensive' e-eawwy
    // tewmination checks in some impwementations such a-as twitteweawwytewminationcowwectow. o.O
    weafcowwectow w-weafcowwectow = cowwectow.getweafcowwectow(twittewweadew.getcontext());
    if (cowwectow.istewminated()) {
      wetuwn;
    }

    // i-initiawize the scowew:
    // nyote t-that constwucting t-the scowew may actuawwy do w-weaw wowk, σωσ such as advancing to t-the
    // fiwst h-hit. (ꈍᴗꈍ)
    // the s-scowew may be nyuww if we can t-teww wight away t-that the quewy has nyo hits: e.g. (ˆ ﻌ ˆ)♡ if the
    // f-fiwst hit does nyot a-actuawwy exist. o.O
    s-scowew scowew = weight.scowew(twittewweadew.getcontext());
    if (scowew == n-nyuww) {
      wog.debug("scowew w-was nyuww, :3 n-nyot seawching segment {}", -.- timeswiceid);
      cowwectow.finishsegment(docidsetitewatow.no_mowe_docs);
      wetuwn;
    }
    weafcowwectow.setscowew(scowew);

    // m-make suwe t-to stawt seawching a-at the smowest d-docid. ( ͡o ω ͡o )
    docidsetitewatow d-docidsetitewatow = scowew.itewatow();
    int smowestdocid = twittewweadew.getsmowestdocid();
    int docid = docidsetitewatow.advance(smowestdocid);

    // c-cowwect wesuwts. /(^•ω•^)
    whiwe (docid != d-docidsetitewatow.no_mowe_docs) {
      // excwude deweted docs. (⑅˘꒳˘)
      i-if (!twittewweadew.getdewetesview().isdeweted(docid)) {
        weafcowwectow.cowwect(docid);
      }

      // c-check if we'we done aftew w-we consumed t-the document. òωó
      i-if (cowwectow.istewminated()) {
        b-bweak;
      }

      d-docid = docidsetitewatow.nextdoc();
    }

    // awways finish the segment, 🥺 pwoviding the wast docid advanced to. (ˆ ﻌ ˆ)♡
    cowwectow.finishsegment(docid);
  }

  @ovewwide
  pubwic v-void fiwwfacetwesuwts(
      a-abstwactfacettewmcowwectow c-cowwectow, -.- thwiftseawchwesuwts s-seawchwesuwts)
      thwows ioexception {
    if (seawchwesuwts == nyuww || s-seawchwesuwts.getwesuwtssize() == 0) {
      w-wetuwn;
    }

    eawwybiwdindexsegmentdata s-segmentdata = twittewweadew.getsegmentdata();
    cowwectow.wesetfacetwabewpwovidews(
        segmentdata.getfacetwabewpwovidews(), σωσ segmentdata.getfacetidmap());
    d-docidtotweetidmappew d-docidmappew = segmentdata.getdocidtotweetidmappew();
    f-fow (thwiftseawchwesuwt w-wesuwt : seawchwesuwts.getwesuwts()) {
      int docid = docidmappew.getdocid(wesuwt.getid());
      if (docid < 0) {
        c-continue;
      }

      s-segmentdata.getfacetcountingawway().cowwectfowdocid(docid, >_< cowwectow);
      c-cowwectow.fiwwwesuwtandcweaw(wesuwt);
    }
  }

  @ovewwide
  p-pubwic tewmstatisticscowwectow.tewmstatisticsseawchwesuwts c-cowwecttewmstatistics(
      tewmstatisticswequestinfo s-seawchwequestinfo, :3
      e-eawwybiwdseawchew seawchew, OwO i-int wequestdebugmode) t-thwows ioexception {
    t-tewmstatisticscowwectow cowwectow = nyew tewmstatisticscowwectow(
        s-schema, seawchwequestinfo, rawr seawchewstats, (///ˬ///✿) c-cwock, w-wequestdebugmode);

    seawch(seawchwequestinfo.getwucenequewy(), c-cowwectow);
    seawchew.maybesetcowwectowdebuginfo(cowwectow);
    wetuwn cowwectow.getwesuwts();
  }

  /** t-this method is o-onwy used fow debugging, ^^ s-so it's nyot optimized fow speed */
  @ovewwide
  pubwic v-void expwainseawchwesuwts(seawchwequestinfo seawchwequestinfo, XD
                                   simpweseawchwesuwts hits, UwU
                                   t-thwiftseawchwesuwts s-seawchwesuwts) thwows ioexception {
    w-weight weight =
        c-cweateweight(wewwite(seawchwequestinfo.getwucenequewy()), o.O s-scowemode.compwete, 😳 1.0f);

    docidtotweetidmappew docidmappew = t-twittewweadew.getsegmentdata().getdocidtotweetidmappew();
    fow (int i = 0; i < hits.numhits(); i-i++) {
      f-finaw hit hit = hits.gethit(i);
      p-pweconditions.checkstate(hit.gettimeswiceid() == timeswiceid, (˘ω˘)
          "hit: " + h-hit.tostwing() + " i-is n-nyot in timeswice: " + timeswiceid);
      finaw thwiftseawchwesuwt wesuwt = seawchwesuwts.getwesuwts().get(i);
      if (!wesuwt.issetmetadata()) {
        wesuwt.setmetadata(new thwiftseawchwesuwtmetadata()
            .setpenguinvewsion(eawwybiwdconfig.getpenguinvewsionbyte()));
      }

      finaw int docidtoexpwain = docidmappew.getdocid(hit.getstatusid());
      if (docidtoexpwain == docidtotweetidmappew.id_not_found) {
        w-wesuwt.getmetadata().setexpwanation(
            "ewwow: c-couwd nyot find doc id to expwain fow " + hit.tostwing());
      } e-ewse {
        e-expwanation expwanation;
        f-fiewdhitattwibution fiewdhitattwibution = w-wesuwt.getmetadata().getfiewdhitattwibution();
        if (weight instanceof w-wewevancequewy.wewevanceweight && f-fiewdhitattwibution != nyuww) {
          w-wewevancequewy.wewevanceweight wewevanceweight =
              (wewevancequewy.wewevanceweight) w-weight;

          e-expwanation = wewevanceweight.expwain(
              twittewweadew.getcontext(), 🥺 d-docidtoexpwain, f-fiewdhitattwibution);
        } e-ewse {
          e-expwanation = w-weight.expwain(twittewweadew.getcontext(), ^^ d-docidtoexpwain);
        }
        h-hit.sethasexpwanation(twue);
        w-wesuwt.getmetadata().setexpwanation(expwanation.tostwing());
      }
    }
  }

  @ovewwide
  p-pubwic void fiwwfacetwesuwtmetadata(map<tewm, >w< t-thwiftfacetcount> f-facetwesuwts, ^^;;
                                      i-immutabweschemaintewface documentschema, (˘ω˘)
                                      b-byte debugmode) thwows ioexception {
    f-facetwabewpwovidew pwovidew = t-twittewweadew.getfacetwabewpwovidews(
            d-documentschema.getfacetfiewdbyfacetname(eawwybiwdfiewdconstant.twimg_facet));

    f-facetwabewpwovidew.facetwabewaccessow photoaccessow = nyuww;

    if (pwovidew != n-nyuww) {
      photoaccessow = p-pwovidew.getwabewaccessow();
    }

    fow (entwy<tewm, OwO t-thwiftfacetcount> facetwesuwt : f-facetwesuwts.entwyset()) {
      tewm tewm = facetwesuwt.getkey();
      thwiftfacetcount facetcount = f-facetwesuwt.getvawue();

      thwiftfacetcountmetadata m-metadata = facetcount.getmetadata();
      i-if (metadata == nyuww) {
        metadata = nyew thwiftfacetcountmetadata();
        f-facetcount.setmetadata(metadata);
      }

      fiwwtewmmetadata(tewm, m-metadata, (ꈍᴗꈍ) p-photoaccessow, òωó d-debugmode);
    }
  }

  @ovewwide
  pubwic void fiwwtewmstatsmetadata(thwifttewmstatisticswesuwts t-tewmstatswesuwts, ʘwʘ
                                    i-immutabweschemaintewface documentschema, ʘwʘ
                                    b-byte debugmode) thwows ioexception {

    f-facetwabewpwovidew pwovidew = t-twittewweadew.getfacetwabewpwovidews(
        documentschema.getfacetfiewdbyfacetname(eawwybiwdfiewdconstant.twimg_facet));

    f-facetwabewpwovidew.facetwabewaccessow p-photoaccessow = nyuww;

    i-if (pwovidew != n-nyuww) {
      p-photoaccessow = p-pwovidew.getwabewaccessow();
    }

    fow (map.entwy<thwifttewmwequest, nyaa~~ t-thwifttewmwesuwts> e-entwy
         : t-tewmstatswesuwts.tewmwesuwts.entwyset()) {

      t-thwifttewmwequest t-tewmwequest = e-entwy.getkey();
      i-if (tewmwequest.getfiewdname().isempty()) {
        c-continue;
      }
      schema.fiewdinfo f-facetfiewd = schema.getfacetfiewdbyfacetname(tewmwequest.getfiewdname());
      t-tewm tewm = nyuww;
      if (facetfiewd != n-nyuww) {
        t-tewm = nyew tewm(facetfiewd.getname(), UwU t-tewmwequest.gettewm());
      }
      if (tewm == nyuww) {
        continue;
      }

      thwiftfacetcountmetadata m-metadata = e-entwy.getvawue().getmetadata();
      if (metadata == nyuww) {
        m-metadata = nyew thwiftfacetcountmetadata();
        entwy.getvawue().setmetadata(metadata);
      }

      fiwwtewmmetadata(tewm, (⑅˘꒳˘) m-metadata, photoaccessow, (˘ω˘) d-debugmode);
    }
  }

  pwivate void f-fiwwtewmmetadata(tewm t-tewm, :3 thwiftfacetcountmetadata metadata, (˘ω˘)
                                facetwabewpwovidew.facetwabewaccessow photoaccessow, nyaa~~
                                b-byte debugmode) t-thwows ioexception {
    b-boowean i-istwimg = tewm.fiewd().equaws(eawwybiwdfiewdconstant.twimg_winks_fiewd.getfiewdname());
    int intewnawdocid = d-docidtotweetidmappew.id_not_found;
    w-wong statusid = -1;
    wong usewid = -1;
    t-tewm facettewm = tewm;

    // deaw with t-the fwom_usew_id facet. (U ﹏ U)
    i-if (tewm.fiewd().equaws(eawwybiwdfiewdconstant.fwom_usew_id_csf.getfiewdname())) {
      u-usewid = wong.pawsewong(tewm.text());
      f-facettewm = n-nyew tewm(eawwybiwdfiewdconstant.fwom_usew_id_fiewd.getfiewdname(), nyaa~~
          wongtewmattwibuteimpw.copyintonewbyteswef(usewid));
    } ewse if (istwimg) {
      s-statusid = wong.pawsewong(tewm.text());
      intewnawdocid = t-twittewweadew.getsegmentdata().getdocidtotweetidmappew().getdocid(statusid);
    }

    i-if (intewnawdocid == d-docidtotweetidmappew.id_not_found) {
      // i-if this is nyot a twimg, ^^;; t-this is how s-statusid shouwd b-be wooked up
      //
      // if this is a twimg b-but we couwdn't find the intewnawdocid, OwO that m-means this segment, nyaa~~
      // o-ow m-maybe even this eawwybiwd, UwU does nyot contain the owiginaw tweet. 😳 then we tweat this a-as
      // a nyowmaw facet f-fow nyow
      intewnawdocid = twittewweadew.getowdestdocid(facettewm);
      i-if (intewnawdocid >= 0) {
        statusid =
            twittewweadew.getsegmentdata().getdocidtotweetidmappew().gettweetid(intewnawdocid);
      } e-ewse {
        statusid = -1;
      }
    }

    // m-make suwe t-tweet is nyot deweted
    i-if (intewnawdocid < 0 || t-twittewweadew.getdewetesview().isdeweted(intewnawdocid)) {
      w-wetuwn;
    }

    if (metadata.issetstatusid()
        && metadata.getstatusid() > 0
        && metadata.getstatusid() <= statusid) {
      // w-we awweady have the metadata f-fow this facet fwom an eawwiew tweet
      wetuwn;
    }

    // nyow check if t-this tweet is offensive, 😳 e.g. (ˆ ﻌ ˆ)♡ antisociaw, nysfw, (✿oωo) sensitive
    eawwybiwddocumentfeatuwes d-documentfeatuwes = n-nyew eawwybiwddocumentfeatuwes(twittewweadew);
    d-documentfeatuwes.advance(intewnawdocid);
    boowean isoffensivefwagset =
        d-documentfeatuwes.isfwagset(eawwybiwdfiewdconstant.is_offensive_fwag);
    b-boowean issensitivefwagset =
        d-documentfeatuwes.isfwagset(eawwybiwdfiewdconstant.is_sensitive_content);
    boowean o-offensive = isoffensivefwagset || issensitivefwagset;

    // awso, nyaa~~ usew shouwd n-nyot be mawked as antisociaw, ^^ nysfw ow offensive
    i-if (usewid < 0) {
      u-usewid = documentfeatuwes.getfeatuwevawue(eawwybiwdfiewdconstant.fwom_usew_id_csf);
    }
    o-offensive |= usewtabwe.isset(usewid, (///ˬ///✿)
        usewtabwe.antisociaw_bit
        | usewtabwe.offensive_bit
        | u-usewtabwe.nsfw_bit);

    metadata.setstatusid(statusid);
    metadata.settwittewusewid(usewid);
    metadata.setcweated_at(twittewweadew.getsegmentdata().gettimemappew().gettime(intewnawdocid));
    int wangid = (int) d-documentfeatuwes.getfeatuwevawue(eawwybiwdfiewdconstant.wanguage);
    w-wocawe wang = t-thwiftwanguageutiw.getwocaweof(thwiftwanguage.findbyvawue(wangid));
    m-metadata.setstatuswanguage(thwiftwanguageutiw.getthwiftwanguageof(wang));
    metadata.setstatuspossibwysensitive(offensive);
    if (istwimg && p-photoaccessow != n-nyuww && !metadata.issetnativephotouww()) {
      int tewmid = twittewweadew.gettewmid(tewm);
      if (tewmid != eawwybiwdindexsegmentatomicweadew.tewm_not_found) {
        b-byteswef tewmpaywoad = photoaccessow.gettewmpaywoad(tewmid);
        i-if (tewmpaywoad != nyuww) {
          metadata.setnativephotouww(tewmpaywoad.utf8tostwing());
        }
      }
    }

    i-if (debugmode > 3) {
      s-stwingbuiwdew sb = nyew stwingbuiwdew(256);
      i-if (metadata.issetexpwanation()) {
        s-sb.append(metadata.getexpwanation());
      }
      s-sb.append(stwing.fowmat("tweetid=%d (%s %s), 😳 usewid=%d (%s %s), òωó tewm=%s\n", ^^;;
          s-statusid, rawr
          isoffensivefwagset ? "offensive" : "", (ˆ ﻌ ˆ)♡
          issensitivefwagset ? "sensitive" : "", XD
          u-usewid, >_<
          usewtabwe.isset(usewid, (˘ω˘) usewtabwe.antisociaw_bit) ? "antisociaw" : "", 😳
          usewtabwe.isset(usewid, o.O u-usewtabwe.nsfw_bit) ? "nsfw" : "", (ꈍᴗꈍ)
          t-tewm.tostwing()));
      m-metadata.setexpwanation(sb.tostwing());
    }
  }

  p-pubwic i-immutabweschemaintewface getschemasnapshot() {
    w-wetuwn schema;
  }

  @ovewwide
  pubwic cowwectionstatistics cowwectionstatistics(stwing f-fiewd) thwows ioexception {
    w-wetuwn twittewindexseawchew.cowwectionstatistics(fiewd, rawr x3 getindexweadew());
  }

  @ovewwide
  p-pubwic t-tewmstatistics tewmstatistics(tewm t-tewm, ^^ int docfweq, OwO wong totawtewmfweq) {
    w-wetuwn twittewindexseawchew.tewmstats(tewm, ^^ d-docfweq, :3 totawtewmfweq);
  }
}
