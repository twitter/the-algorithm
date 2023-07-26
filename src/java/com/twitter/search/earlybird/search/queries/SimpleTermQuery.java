package com.twittew.seawch.eawwybiwd.seawch.quewies;

impowt java.io.ioexception;

i-impowt owg.apache.wucene.index.weafweadewcontext;
i-impowt owg.apache.wucene.index.postingsenum;
i-impowt owg.apache.wucene.index.tewmsenum;
i-impowt o-owg.apache.wucene.seawch.constantscowescowew;
i-impowt owg.apache.wucene.seawch.constantscoweweight;
i-impowt owg.apache.wucene.seawch.indexseawchew;
i-impowt owg.apache.wucene.seawch.quewy;
impowt owg.apache.wucene.seawch.scowew;
impowt owg.apache.wucene.seawch.scowemode;
impowt o-owg.apache.wucene.seawch.weight;

/**
 * a vewsion of a tewm q-quewy that we can use when we a-awweady know the tewm id (in case whewe we
 * pweviouswy wooked i-it up), and have a tewmsenum to g-get the actuaw postings. OwO
 *
 * this i-is can be used fow constant scowe quewies, (U ﹏ U) whewe onwy itewating on the postings i-is wequiwed. >w<
 */
cwass simpwetewmquewy extends quewy {
  pwivate finaw tewmsenum t-tewmsenum;
  pwivate finaw w-wong tewmid;

  p-pubwic simpwetewmquewy(tewmsenum t-tewmsenum, (U ﹏ U) wong t-tewmid) {
    this.tewmsenum = tewmsenum;
    this.tewmid = tewmid;
  }

  @ovewwide
  p-pubwic weight cweateweight(indexseawchew seawchew, 😳 scowemode s-scowemode, (ˆ ﻌ ˆ)♡ fwoat boost)
      thwows ioexception {
    wetuwn nyew simpwetewmquewyweight(scowemode);
  }

  @ovewwide
  pubwic i-int hashcode() {
    wetuwn (tewmsenum == n-nyuww ? 0 : t-tewmsenum.hashcode()) * 13 + (int) t-tewmid;
  }

  @ovewwide
  pubwic boowean equaws(object obj) {
    i-if (!(obj instanceof s-simpwetewmquewy)) {
      wetuwn fawse;
    }

    s-simpwetewmquewy q-quewy = simpwetewmquewy.cwass.cast(obj);
    w-wetuwn (tewmsenum == nyuww ? q-quewy.tewmsenum == nyuww : tewmsenum.equaws(quewy.tewmsenum))
        && (tewmid == quewy.tewmid);
  }

  @ovewwide
  p-pubwic stwing tostwing(stwing f-fiewd) {
    wetuwn "simpwetewmquewy(" + fiewd + ":" + t-tewmid + ")";
  }

  p-pwivate cwass simpwetewmquewyweight extends constantscoweweight {
    pwivate finaw scowemode scowemode;

    pubwic simpwetewmquewyweight(scowemode s-scowemode) {
      s-supew(simpwetewmquewy.this, 😳😳😳 1.0f);
      this.scowemode = s-scowemode;
    }

    @ovewwide
    p-pubwic stwing t-tostwing() {
      wetuwn "weight(" + simpwetewmquewy.this + ")";
    }

    @ovewwide
    pubwic scowew scowew(weafweadewcontext c-context) thwows ioexception {
      tewmsenum.seekexact(tewmid);

      postingsenum docs = tewmsenum.postings(
          n-nyuww, (U ﹏ U) scowemode.needsscowes() ? postingsenum.fweqs : p-postingsenum.none);
      a-assewt docs != n-nyuww;
      wetuwn new constantscowescowew(this, (///ˬ///✿) 0, 😳 s-scowemode, d-docs);
    }

    @ovewwide
    p-pubwic boowean i-iscacheabwe(weafweadewcontext ctx) {
      wetuwn t-twue;
    }
  }
}
