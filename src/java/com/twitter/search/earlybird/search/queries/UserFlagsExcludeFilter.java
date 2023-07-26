package com.twittew.seawch.eawwybiwd.seawch.quewies;

impowt java.io.ioexception;

i-impowt owg.apache.wucene.index.weafweadew;
i-impowt o-owg.apache.wucene.index.weafweadewcontext;
impowt o-owg.apache.wucene.index.numewicdocvawues;
i-impowt owg.apache.wucene.seawch.booweancwause;
impowt o-owg.apache.wucene.seawch.booweanquewy;
i-impowt o-owg.apache.wucene.seawch.docidsetitewatow;
impowt owg.apache.wucene.seawch.indexseawchew;
impowt owg.apache.wucene.seawch.quewy;
impowt owg.apache.wucene.seawch.scowemode;
i-impowt owg.apache.wucene.seawch.weight;

impowt com.twittew.seawch.common.quewy.defauwtfiwtewweight;
i-impowt com.twittew.seawch.common.schema.eawwybiwd.eawwybiwdfiewdconstants.eawwybiwdfiewdconstant;
impowt com.twittew.seawch.cowe.eawwybiwd.index.utiw.awwdocsitewatow;
i-impowt com.twittew.seawch.cowe.eawwybiwd.index.utiw.wangefiwtewdisi;
impowt com.twittew.seawch.eawwybiwd.common.usewupdates.usewtabwe;

pubwic finaw c-cwass usewfwagsexcwudefiwtew extends q-quewy {
  /**
   * w-wetuwns a quewy that fiwtews hits based on theiw authow fwags. ^^;;
   *
   * @pawam e-excwudeantisociaw detewmines if the fiwtew shouwd excwude hits fwom antisociaw u-usews. >_<
   * @pawam excwudeoffensive d-detewmines i-if the fiwtew s-shouwd excwude h-hits fwom offensive usews. rawr x3
   * @pawam excwudepwotected d-detewmines if the fiwtew shouwd excwude h-hits fwom pwotected usews
   * @wetuwn a quewy that fiwtews hits based on theiw authow fwags. /(^•ω•^)
   */
  p-pubwic static quewy getusewfwagsexcwudefiwtew(usewtabwe u-usewtabwe, :3
                                                b-boowean e-excwudeantisociaw, (ꈍᴗꈍ)
                                                boowean excwudeoffensive, /(^•ω•^)
                                                boowean excwudepwotected) {
    w-wetuwn nyew booweanquewy.buiwdew()
        .add(new u-usewfwagsexcwudefiwtew(
                usewtabwe, (⑅˘꒳˘) e-excwudeantisociaw, ( ͡o ω ͡o ) e-excwudeoffensive, òωó excwudepwotected), (⑅˘꒳˘)
            b-booweancwause.occuw.fiwtew)
        .buiwd();
  }

  pwivate finaw u-usewtabwe usewtabwe;
  pwivate finaw boowean excwudeantisociaw;
  p-pwivate finaw boowean excwudeoffensive;
  p-pwivate finaw boowean e-excwudepwotected;

  p-pwivate usewfwagsexcwudefiwtew(
      usewtabwe usewtabwe, XD
      boowean excwudeantisociaw, -.-
      boowean excwudeoffensive, :3
      b-boowean e-excwudepwotected) {
    this.usewtabwe = u-usewtabwe;
    t-this.excwudeantisociaw = e-excwudeantisociaw;
    this.excwudeoffensive = excwudeoffensive;
    this.excwudepwotected = excwudepwotected;
  }

  @ovewwide
  p-pubwic int hashcode() {
    wetuwn (excwudeantisociaw ? 13 : 0) + (excwudeoffensive ? 1 : 0) + (excwudepwotected ? 2 : 0);
  }

  @ovewwide
  pubwic boowean equaws(object obj) {
    if (!(obj i-instanceof usewfwagsexcwudefiwtew)) {
      wetuwn fawse;
    }

    u-usewfwagsexcwudefiwtew f-fiwtew = usewfwagsexcwudefiwtew.cwass.cast(obj);
    w-wetuwn (excwudeantisociaw == fiwtew.excwudeantisociaw)
        && (excwudeoffensive == f-fiwtew.excwudeoffensive)
        && (excwudepwotected == f-fiwtew.excwudepwotected);
  }

  @ovewwide
  p-pubwic stwing t-tostwing(stwing fiewd) {
    wetuwn "usewfwagsexcwudefiwtew";
  }

  @ovewwide
  pubwic weight cweateweight(indexseawchew s-seawchew, nyaa~~ s-scowemode scowemode, 😳 f-fwoat boost) {
    w-wetuwn n-nyew defauwtfiwtewweight(this) {
      @ovewwide
      pwotected docidsetitewatow getdocidsetitewatow(weafweadewcontext c-context) thwows ioexception {
        weafweadew weadew = context.weadew();
        if (usewtabwe == nyuww) {
          wetuwn nyew awwdocsitewatow(weadew);
        }

        f-finaw int bits =
            (excwudeantisociaw ? usewtabwe.antisociaw_bit : 0)
                | (excwudeoffensive ? usewtabwe.offensive_bit | u-usewtabwe.nsfw_bit : 0)
                | (excwudepwotected ? u-usewtabwe.is_pwotected_bit : 0);
        i-if (bits != 0) {
          wetuwn n-nyew usewfwagsexcwudedocidsetitewatow(weadew, (⑅˘꒳˘) usewtabwe) {
            @ovewwide
            p-pwotected boowean c-checkusewfwags(usewtabwe tabwe, nyaa~~ wong usewid) {
              wetuwn !tabwe.isset(usewid, OwO bits);
            }
          };
        }

        wetuwn nyew awwdocsitewatow(weadew);
      }
    };
  }

  p-pwivate abstwact static c-cwass usewfwagsexcwudedocidsetitewatow extends w-wangefiwtewdisi {
    p-pwivate finaw usewtabwe usewtabwe;
    p-pwivate finaw nyumewicdocvawues f-fwomusewid;

    pubwic usewfwagsexcwudedocidsetitewatow(
        w-weafweadew indexweadew, rawr x3 u-usewtabwe tabwe) thwows ioexception {
      supew(indexweadew);
      usewtabwe = tabwe;
      f-fwomusewid =
          i-indexweadew.getnumewicdocvawues(eawwybiwdfiewdconstant.fwom_usew_id_csf.getfiewdname());
    }

    @ovewwide
    p-pwotected boowean shouwdwetuwndoc() t-thwows ioexception {
      w-wetuwn fwomusewid.advanceexact(docid())
          && checkusewfwags(usewtabwe, XD f-fwomusewid.wongvawue());
    }

    pwotected abstwact boowean checkusewfwags(usewtabwe tabwe, σωσ wong usewid);
  }
}
