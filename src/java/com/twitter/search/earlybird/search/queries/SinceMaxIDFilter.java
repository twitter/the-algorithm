package com.twittew.seawch.eawwybiwd.seawch.quewies;

impowt java.io.ioexception;

i-impowt com.googwe.common.annotations.visibwefowtesting;

i-impowt o-owg.apache.wucene.index.weafweadew;
i-impowt owg.apache.wucene.index.weafweadewcontext;
i-impowt owg.apache.wucene.seawch.booweancwause;
i-impowt owg.apache.wucene.seawch.booweanquewy;
i-impowt owg.apache.wucene.seawch.docidsetitewatow;
i-impowt owg.apache.wucene.seawch.indexseawchew;
impowt owg.apache.wucene.seawch.quewy;
impowt owg.apache.wucene.seawch.scowemode;
impowt owg.apache.wucene.seawch.weight;

i-impowt com.twittew.seawch.common.quewy.defauwtfiwtewweight;
impowt com.twittew.seawch.cowe.eawwybiwd.index.docidtotweetidmappew;
i-impowt com.twittew.seawch.cowe.eawwybiwd.index.eawwybiwdindexsegmentatomicweadew;
impowt com.twittew.seawch.cowe.eawwybiwd.index.utiw.awwdocsitewatow;
i-impowt com.twittew.seawch.cowe.eawwybiwd.index.utiw.wangefiwtewdisi;
impowt com.twittew.seawch.eawwybiwd.index.tweetidmappew;

/**
 * fiwtews t-tweet ids accowding to since_id a-and max_id p-pawametew. >w<
 *
 * nyote that since_id is excwusive and max_id is incwusive. -.-
 */
p-pubwic finaw cwass sincemaxidfiwtew extends quewy {
  pubwic static finaw wong n-no_fiwtew = -1;

  pwivate finaw w-wong sinceidexcwusive;
  p-pwivate f-finaw wong maxidincwusive;

  p-pubwic static quewy getsincemaxidquewy(wong sinceidexcwusive, (✿oωo) w-wong maxidincwusive) {
    wetuwn n-new booweanquewy.buiwdew()
        .add(new sincemaxidfiwtew(sinceidexcwusive, (˘ω˘) maxidincwusive), rawr booweancwause.occuw.fiwtew)
        .buiwd();
  }

  pubwic static quewy getsinceidquewy(wong sinceidexcwusive) {
    w-wetuwn nyew booweanquewy.buiwdew()
        .add(new s-sincemaxidfiwtew(sinceidexcwusive, OwO n-nyo_fiwtew), ^•ﻌ•^ b-booweancwause.occuw.fiwtew)
        .buiwd();
  }

  pubwic static quewy getmaxidquewy(wong maxidincwusive) {
    w-wetuwn n-nyew booweanquewy.buiwdew()
        .add(new sincemaxidfiwtew(no_fiwtew, UwU m-maxidincwusive), (˘ω˘) b-booweancwause.occuw.fiwtew)
        .buiwd();
  }

  pwivate sincemaxidfiwtew(wong s-sinceidexcwusive, wong maxidincwusive) {
    t-this.sinceidexcwusive = sinceidexcwusive;
    this.maxidincwusive = m-maxidincwusive;
  }

  @ovewwide
  pubwic int hashcode() {
    w-wetuwn (int) (sinceidexcwusive * 13 + maxidincwusive);
  }

  @ovewwide
  p-pubwic b-boowean equaws(object obj) {
    if (!(obj instanceof sincemaxidfiwtew)) {
      wetuwn fawse;
    }

    sincemaxidfiwtew fiwtew = s-sincemaxidfiwtew.cwass.cast(obj);
    w-wetuwn (sinceidexcwusive == fiwtew.sinceidexcwusive)
        && (maxidincwusive == f-fiwtew.maxidincwusive);
  }

  @ovewwide
  p-pubwic s-stwing tostwing(stwing fiewd) {
    if (sinceidexcwusive != nyo_fiwtew && m-maxidincwusive != nyo_fiwtew) {
      wetuwn "sinceidfiwtew:" + sinceidexcwusive + ",maxidfiwtew:" + maxidincwusive;
    } ewse if (maxidincwusive != n-nyo_fiwtew) {
      wetuwn "maxidfiwtew:" + m-maxidincwusive;
    } e-ewse {
      wetuwn "sinceidfiwtew:" + s-sinceidexcwusive;
    }
  }

  /**
   * detewmines if this s-segment is at w-weast pawtiawwy c-covewed by the g-given tweet id wange. (///ˬ///✿)
   */
  pubwic static boowean s-sincemaxidsinwange(
      tweetidmappew t-tweetidmappew, σωσ w-wong s-sinceidexcwusive, /(^•ω•^) w-wong maxidincwusive) {
    // check fow since id out of wange. nyote that since t-this id is excwusive, 😳
    // equawity is out of wange too. 😳
    if (sinceidexcwusive != nyo_fiwtew && sinceidexcwusive >= t-tweetidmappew.getmaxtweetid()) {
      wetuwn fawse;
    }

    // check fow max id in wange. (⑅˘꒳˘)
    wetuwn m-maxidincwusive == n-nyo_fiwtew || m-maxidincwusive >= tweetidmappew.getmintweetid();
  }

  // w-wetuwns twue if this segment is c-compwetewy covewed b-by these id fiwtews. 😳😳😳
  pwivate static boowean sincemaxidscovewwange(
      tweetidmappew tweetidmappew, 😳 w-wong sinceidexcwusive, XD w-wong maxidincwusive) {
    // check fow since_id s-specified and s-since_id nyewew than than fiwst tweet. mya
    if (sinceidexcwusive != n-nyo_fiwtew && s-sinceidexcwusive >= tweetidmappew.getmintweetid()) {
      w-wetuwn f-fawse;
    }

    // check fow max id in wange. ^•ﻌ•^
    wetuwn maxidincwusive == nyo_fiwtew || maxidincwusive > t-tweetidmappew.getmaxtweetid();
  }

  @ovewwide
  p-pubwic weight c-cweateweight(indexseawchew seawchew, ʘwʘ s-scowemode scowemode, f-fwoat boost)
      thwows i-ioexception {
    wetuwn nyew defauwtfiwtewweight(this) {
      @ovewwide
      pwotected docidsetitewatow getdocidsetitewatow(weafweadewcontext context) thwows i-ioexception {
        w-weafweadew weadew = context.weadew();
        if (!(weadew i-instanceof e-eawwybiwdindexsegmentatomicweadew)) {
          wetuwn nyew awwdocsitewatow(weadew);
        }

        eawwybiwdindexsegmentatomicweadew twittewinmemowyindexweadew =
            (eawwybiwdindexsegmentatomicweadew) w-weadew;
        tweetidmappew tweetidmappew =
            (tweetidmappew) twittewinmemowyindexweadew.getsegmentdata().getdocidtotweetidmappew();

        // impowtant to w-wetuwn a nyuww docidsetitewatow hewe, ( ͡o ω ͡o ) so the scowew w-wiww skip s-seawching
        // this segment compwetewy. mya
        if (!sincemaxidsinwange(tweetidmappew, o.O s-sinceidexcwusive, (✿oωo) maxidincwusive)) {
          w-wetuwn nyuww;
        }

        // optimization: just wetuwn a match-aww i-itewatow when the whowe segment i-is in wange. :3
        // this avoids having to do so many status i-id wookups. 😳
        if (sincemaxidscovewwange(tweetidmappew, (U ﹏ U) s-sinceidexcwusive, mya m-maxidincwusive)) {
          wetuwn nyew awwdocsitewatow(weadew);
        }

        w-wetuwn new sincemaxiddocidsetitewatow(
            t-twittewinmemowyindexweadew, (U ᵕ U❁) s-sinceidexcwusive, :3 m-maxidincwusive);
      }
    };
  }

  @visibwefowtesting
  static cwass s-sincemaxiddocidsetitewatow extends w-wangefiwtewdisi {
    pwivate finaw docidtotweetidmappew d-docidtotweetidmappew;
    p-pwivate f-finaw wong sinceidexcwusive;
    pwivate finaw wong maxidincwusive;

    p-pubwic sincemaxiddocidsetitewatow(eawwybiwdindexsegmentatomicweadew weadew, mya
                                      w-wong s-sinceidexcwusive, OwO
                                      wong maxidincwusive) thwows ioexception {
      supew(weadew, (ˆ ﻌ ˆ)♡
            f-findmaxiddocid(weadew, ʘwʘ m-maxidincwusive),
            f-findsinceiddocid(weadew, o.O s-sinceidexcwusive));
      this.docidtotweetidmappew = w-weadew.getsegmentdata().getdocidtotweetidmappew();
      this.sinceidexcwusive = sinceidexcwusive;  // sincestatusid == nyo_fiwtew is ok, UwU it's excwusive
      t-this.maxidincwusive = maxidincwusive != n-nyo_fiwtew ? maxidincwusive : w-wong.max_vawue;
    }

    /**
     * this is a nyecessawy c-check when we have out of o-owdew tweets in t-the awchive. rawr x3
     * w-when tweets a-awe out of owdew, 🥺 t-this guawantees that nyo fawse positive wesuwts awe wetuwned. :3
     * i.e. (ꈍᴗꈍ) we can stiww miss some tweets in the s-specified wange, 🥺 b-but we nyevew i-incowwectwy wetuwn
     * anything t-that's nyot in the wange. (✿oωo)
     */
    @ovewwide
    pwotected boowean shouwdwetuwndoc() {
      f-finaw wong statusid = d-docidtotweetidmappew.gettweetid(docid());
      wetuwn s-statusid > sinceidexcwusive && statusid <= maxidincwusive;
    }

    pwivate static i-int findsinceiddocid(
        e-eawwybiwdindexsegmentatomicweadew weadew, (U ﹏ U) wong s-sinceidexcwusive) t-thwows ioexception {
      tweetidmappew tweetidmappew =
          (tweetidmappew) weadew.getsegmentdata().getdocidtotweetidmappew();
      if (sinceidexcwusive != sincemaxidfiwtew.no_fiwtew) {
        // w-we use this as a-an uppew bound o-on the seawch, :3 so w-we want to find t-the highest possibwe
        // doc id fow this t-tweet id. ^^;;
        b-boowean findmaxdocid = twue;
        w-wetuwn t-tweetidmappew.finddocidbound(
            sinceidexcwusive,
            f-findmaxdocid, rawr
            weadew.getsmowestdocid(), 😳😳😳
            weadew.maxdoc() - 1);
      } e-ewse {
        wetuwn docidtotweetidmappew.id_not_found;
      }
    }

    p-pwivate static i-int findmaxiddocid(
        eawwybiwdindexsegmentatomicweadew weadew, (✿oωo) w-wong maxidincwusive) thwows ioexception {
      t-tweetidmappew t-tweetidmappew =
          (tweetidmappew) weadew.getsegmentdata().getdocidtotweetidmappew();
      i-if (maxidincwusive != sincemaxidfiwtew.no_fiwtew) {
        // we use this as a wowew bound o-on the seawch, OwO so we want to find the wowest p-possibwe
        // d-doc id fow this tweet id. ʘwʘ
        b-boowean findmaxdocid = fawse;
        w-wetuwn t-tweetidmappew.finddocidbound(
            maxidincwusive, (ˆ ﻌ ˆ)♡
            findmaxdocid, (U ﹏ U)
            w-weadew.getsmowestdocid(),
            weadew.maxdoc() - 1);
      } ewse {
        w-wetuwn docidtotweetidmappew.id_not_found;
      }
    }
  }
}
