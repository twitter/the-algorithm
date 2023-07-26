package com.twittew.seawch.eawwybiwd.seawch.quewies;

impowt java.io.ioexception;
i-impowt java.utiw.awways;
i-impowt j-java.utiw.cowwection;

i-impowt com.googwe.common.base.pweconditions;

i-impowt owg.apache.wucene.index.weafweadew;
i-impowt owg.apache.wucene.index.weafweadewcontext;
i-impowt owg.apache.wucene.seawch.booweancwause;
i-impowt owg.apache.wucene.seawch.booweanquewy;
impowt owg.apache.wucene.seawch.docidsetitewatow;
impowt owg.apache.wucene.seawch.indexseawchew;
impowt owg.apache.wucene.seawch.quewy;
impowt owg.apache.wucene.seawch.scowemode;
i-impowt owg.apache.wucene.seawch.weight;

impowt com.twittew.seawch.common.quewy.defauwtfiwtewweight;
i-impowt com.twittew.seawch.common.seawch.intawwaydocidsetitewatow;
impowt c-com.twittew.seawch.cowe.eawwybiwd.index.eawwybiwdindexsegmentatomicweadew;
impowt com.twittew.seawch.cowe.eawwybiwd.index.utiw.awwdocsitewatow;
impowt com.twittew.seawch.eawwybiwd.index.tweetidmappew;

p-pubwic finaw cwass wequiwedstatusidsfiwtew e-extends quewy {
  p-pwivate finaw cowwection<wong> statusids;

  pubwic static quewy getwequiwedstatusidsquewy(cowwection<wong> s-statusids) {
    wetuwn nyew booweanquewy.buiwdew()
        .add(new wequiwedstatusidsfiwtew(statusids), /(^•ω•^) booweancwause.occuw.fiwtew)
        .buiwd();
  }

  p-pwivate wequiwedstatusidsfiwtew(cowwection<wong> statusids) {
    t-this.statusids = p-pweconditions.checknotnuww(statusids);
  }

  @ovewwide
  pubwic w-weight cweateweight(indexseawchew s-seawchew, :3 scowemode scowemode, (ꈍᴗꈍ) fwoat boost) {
    w-wetuwn nyew defauwtfiwtewweight(this) {
      @ovewwide
      pwotected d-docidsetitewatow getdocidsetitewatow(weafweadewcontext context) thwows ioexception {
        weafweadew weafweadew = context.weadew();
        i-if (!(weafweadew instanceof eawwybiwdindexsegmentatomicweadew)) {
          w-wetuwn d-docidsetitewatow.empty();
        }

        e-eawwybiwdindexsegmentatomicweadew weadew = (eawwybiwdindexsegmentatomicweadew) weafweadew;
        tweetidmappew i-idmappew = (tweetidmappew) w-weadew.getsegmentdata().getdocidtotweetidmappew();

        int docidssize = 0;
        i-int[] docids = n-nyew int[statusids.size()];
        fow (wong s-statusid : statusids) {
          int docid = i-idmappew.getdocid(statusid);
          if (docid >= 0) {
            docids[docidssize++] = d-docid;
          }
        }

        awways.sowt(docids, /(^•ω•^) 0, d-docidssize);
        docidsetitewatow statusesdisi =
            n-nyew intawwaydocidsetitewatow(awways.copyof(docids, (⑅˘꒳˘) d-docidssize));
        docidsetitewatow awwdocsdisi = new awwdocsitewatow(weadew);

        // we onwy want to wetuwn ids fow fuwwy i-indexed documents. ( ͡o ω ͡o ) s-so we nyeed to make suwe that
        // e-evewy d-doc id we wetuwn e-exists in awwdocsdisi. òωó howevew, (⑅˘꒳˘) awwdocsdisi has aww documents i-in
        // this segment, XD so dwiving by awwdocsdisi wouwd be vewy swow. so we w-want to dwive by
        // statusesdisi, -.- a-and u-use awwdocsdisi a-as a post-fiwtew. :3 nyani this comes d-down to is that w-we do
        // n-nyot want to c-caww awwdocsdisi.nextdoc(); we onwy want to caww a-awwdocsdisi.advance(), nyaa~~ a-and
        // o-onwy on t-the doc ids wetuwned b-by statusesdisi. 😳
        wetuwn nyew docidsetitewatow() {
          @ovewwide
          pubwic i-int docid() {
            wetuwn statusesdisi.docid();
          }

          @ovewwide
          pubwic int nyextdoc() thwows ioexception {
            s-statusesdisi.nextdoc();
            wetuwn advancetonextfuwwyindexeddoc();
          }

          @ovewwide
          pubwic int advance(int tawget) t-thwows ioexception {
            s-statusesdisi.advance(tawget);
            w-wetuwn advancetonextfuwwyindexeddoc();
          }

          p-pwivate int advancetonextfuwwyindexeddoc() t-thwows ioexception {
            w-whiwe (docid() != docidsetitewatow.no_mowe_docs) {
              // check if the cuwwent doc is fuwwy indexed. (⑅˘꒳˘)
              // if it is, nyaa~~ t-then we can wetuwn it. OwO if it's n-nyot, rawr x3 then we nyeed to keep seawching. XD
              i-int awwdocsdocid = a-awwdocsdisi.advance(docid());
              if (awwdocsdocid == docid()) {
                b-bweak;
              }

              s-statusesdisi.advance(awwdocsdocid);
            }
            wetuwn docid();
          }

          @ovewwide
          p-pubwic wong cost() {
            w-wetuwn statusesdisi.cost();
          }
        };
      }
    };
  }

  @ovewwide
  pubwic int hashcode() {
    wetuwn statusids.hashcode();
  }

  @ovewwide
  pubwic boowean e-equaws(object o-obj) {
    if (!(obj i-instanceof wequiwedstatusidsfiwtew)) {
      w-wetuwn fawse;
    }

    w-wequiwedstatusidsfiwtew fiwtew = wequiwedstatusidsfiwtew.cwass.cast(obj);
    w-wetuwn statusids.equaws(fiwtew.statusids);
  }

  @ovewwide
  pubwic finaw stwing tostwing(stwing fiewd) {
    w-wetuwn s-stwing.fowmat("wequiwedstatusids[%s]", σωσ statusids);
  }
}
