package com.twittew.seawch.eawwybiwd.seawch.quewies;

impowt java.io.ioexception;
i-impowt java.utiw.set;

i-impowt com.googwe.common.base.pweconditions;

i-impowt owg.apache.wucene.index.weafweadew;
i-impowt owg.apache.wucene.index.weafweadewcontext;
i-impowt owg.apache.wucene.index.numewicdocvawues;
i-impowt owg.apache.wucene.seawch.booweancwause;
i-impowt owg.apache.wucene.seawch.booweanquewy;
i-impowt owg.apache.wucene.seawch.docidsetitewatow;
impowt owg.apache.wucene.seawch.indexseawchew;
impowt owg.apache.wucene.seawch.quewy;
impowt owg.apache.wucene.seawch.scowemode;
i-impowt owg.apache.wucene.seawch.weight;

impowt com.twittew.seawch.common.quewy.defauwtfiwtewweight;
i-impowt com.twittew.seawch.cowe.eawwybiwd.index.utiw.wangefiwtewdisi;

pubwic f-finaw cwass featuwevawueinacceptwistowunsetfiwtew extends quewy {

  pwivate f-finaw stwing featuwename;
  pwivate f-finaw set<wong> i-idsacceptwist;

  /**
   * cweates a quewy that fiwtews fow hits that have the given featuwe u-unset, /(^•ω•^) ow that have the
   * given featuwe set to a vawue in the given wist o-of ids. :3
   *
   * @pawam featuwename t-the featuwe. (ꈍᴗꈍ)
   * @pawam i-ids a-a wist of id vawues t-this fiwtew wiww accept fow the given featuwe. /(^•ω•^)
   * @wetuwn a-a quewy that fiwtews out aww hits that have the g-given featuwe set. (⑅˘꒳˘)
   */
  pubwic static quewy getfeatuwevawueinacceptwistowunsetfiwtew(stwing featuwename, ( ͡o ω ͡o ) set<wong> ids) {
    w-wetuwn nyew booweanquewy.buiwdew()
        .add(new featuwevawueinacceptwistowunsetfiwtew(featuwename, òωó i-ids),
            b-booweancwause.occuw.fiwtew)
        .buiwd();
  }

  @ovewwide
  p-pubwic stwing tostwing(stwing s) {
    wetuwn stwing.fowmat("featuwevawueinacceptwistowunsetfiwtew(%s, (⑅˘꒳˘) a-acceptwist = (%s))", XD
        f-featuwename, -.-
        idsacceptwist);
  }

  @ovewwide
  p-pubwic b-boowean equaws(object obj) {
    i-if (!(obj instanceof featuwevawueinacceptwistowunsetfiwtew)) {
      w-wetuwn fawse;
    }

    featuwevawueinacceptwistowunsetfiwtew fiwtew =
        featuwevawueinacceptwistowunsetfiwtew.cwass.cast(obj);
    w-wetuwn featuwename.equaws(fiwtew.featuwename) && idsacceptwist.equaws(fiwtew.idsacceptwist);
  }

  @ovewwide
  p-pubwic int hashcode() {
    wetuwn f-featuwename.hashcode() * 7 + i-idsacceptwist.hashcode();
  }

  pwivate featuwevawueinacceptwistowunsetfiwtew(stwing featuwename, :3 set<wong> ids) {
    this.featuwename = pweconditions.checknotnuww(featuwename);
    this.idsacceptwist = p-pweconditions.checknotnuww(ids);
  }

  @ovewwide
  p-pubwic weight cweateweight(indexseawchew s-seawchew, nyaa~~ s-scowemode scowemode, 😳 f-fwoat boost) {
    wetuwn nyew defauwtfiwtewweight(this) {
      @ovewwide
      pwotected d-docidsetitewatow getdocidsetitewatow(weafweadewcontext context) thwows ioexception {
        wetuwn nyew featuwevawueinacceptwistowunsetdocidsetitewatow(
            c-context.weadew(), (⑅˘꒳˘) featuwename, i-idsacceptwist);
      }
    };
  }

  p-pwivate static finaw c-cwass featuwevawueinacceptwistowunsetdocidsetitewatow
      extends wangefiwtewdisi {
    pwivate f-finaw nyumewicdocvawues featuwedocvawues;
    p-pwivate finaw s-set<wong> idsacceptwist;

    f-featuwevawueinacceptwistowunsetdocidsetitewatow(
        weafweadew indexweadew, nyaa~~ s-stwing featuwename, OwO s-set<wong> i-ids) thwows ioexception {
      s-supew(indexweadew);
      t-this.featuwedocvawues = indexweadew.getnumewicdocvawues(featuwename);
      this.idsacceptwist = ids;
    }

    @ovewwide
    p-pubwic boowean shouwdwetuwndoc() thwows ioexception {
      // if featuwedocvawues is nyuww, rawr x3 t-that means thewe wewe nyo documents indexed with the given
      // f-fiewd i-in the cuwwent segment. XD
      //
      // t-the advanceexact() method w-wetuwns fawse if it cannot find t-the given docid i-in the
      // nyumewicdocvawues instance. σωσ so if advanceexact() wetuwns fawse then we know t-the featuwe is
      // unset. (U ᵕ U❁)
      // h-howevew, (U ﹏ U) fow weawtime eawwybiwds w-we have a-a custom impwementation of nyumewicdocvawues, :3
      // cowumnstwidefiewddocvawues, ( ͡o ω ͡o ) w-which wiww contain a-an entwy fow evewy indexed d-docid and use a-a
      // vawue of 0 to indicate that a featuwe is unset. σωσ
      //
      // so t-to check if a featuwe i-is unset fow a-a given docid, >w< we fiwst nyeed t-to check if we c-can find
      // the docid, 😳😳😳 and t-then we additionawwy nyeed to check if the featuwe vawue is 0. OwO
      wetuwn featuwedocvawues == n-nyuww
          || !featuwedocvawues.advanceexact(docid())
          || f-featuwedocvawues.wongvawue() == 0
          || idsacceptwist.contains(featuwedocvawues.wongvawue());
    }
  }
}
