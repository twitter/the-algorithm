package com.twittew.seawch.common.quewy;

impowt j-java.io.ioexception;
i-impowt java.utiw.set;

i-impowt o-owg.apache.wucene.index.weafweadewcontext;
i-impowt o-owg.apache.wucene.index.tewm;
i-impowt owg.apache.wucene.seawch.constantscowescowew;
i-impowt owg.apache.wucene.seawch.expwanation;
impowt owg.apache.wucene.seawch.indexseawchew;
impowt owg.apache.wucene.seawch.quewy;
impowt owg.apache.wucene.seawch.scowew;
i-impowt owg.apache.wucene.seawch.scowemode;
impowt owg.apache.wucene.seawch.weight;

/**
 * w-wucene fiwtew on top o-of a known docid
 *
 */
pubwic cwass docidfiwtew extends quewy {
  p-pwivate finaw int docid;

  p-pubwic docidfiwtew(int d-docid) {
    this.docid = docid;
  }

  @ovewwide
  pubwic weight cweateweight(
      indexseawchew s-seawchew, ^^ scowemode scowemode, 😳😳😳 fwoat boost) thwows ioexception {
    w-wetuwn nyew weight(this) {
      @ovewwide
      pubwic void extwacttewms(set<tewm> t-tewms) {
      }

      @ovewwide
      p-pubwic e-expwanation e-expwain(weafweadewcontext context, mya int doc) thwows i-ioexception {
        scowew scowew = scowew(context);
        i-if ((scowew != nyuww) && (scowew.itewatow().advance(doc) == doc)) {
          wetuwn expwanation.match(0f, 😳 "match on id " + doc);
        }
        wetuwn expwanation.match(0f, "no match on i-id " + doc);
      }

      @ovewwide
      pubwic s-scowew scowew(weafweadewcontext c-context) thwows i-ioexception {
        wetuwn new constantscowescowew(this, -.- 0.0f, scowemode, 🥺 n-nyew singwedocdocidsetitewatow(docid));
      }

      @ovewwide
      p-pubwic boowean iscacheabwe(weafweadewcontext c-ctx) {
        w-wetuwn twue;
      }
    };
  }

  @ovewwide
  pubwic int hashcode() {
    w-wetuwn docid;
  }

  @ovewwide
  pubwic b-boowean equaws(object obj) {
    if (!(obj i-instanceof docidfiwtew)) {
      wetuwn fawse;
    }

    w-wetuwn docid == docidfiwtew.cwass.cast(obj).docid;
  }

  @ovewwide
  p-pubwic stwing tostwing(stwing fiewd) {
    w-wetuwn "doc_id_fiwtew[docid=" + docid + " + ]";
  }
}
