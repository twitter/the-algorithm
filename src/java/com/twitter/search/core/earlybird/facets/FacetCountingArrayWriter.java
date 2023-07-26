package com.twittew.seawch.cowe.eawwybiwd.facets;

impowt com.twittew.seawch.cowe.eawwybiwd.index.invewted.intbwockpoow;

p-pubwic c-cwass facetcountingawwaywwitew {
  p-pwivate finaw a-abstwactfacetcountingawway f-facetcountingawway;
  p-pwivate int pweviousdocid = -1;

  p-pubwic facetcountingawwaywwitew(abstwactfacetcountingawway a-awway) {
    facetcountingawway = awway;
  }

  /**
   * adds a facet fow the given doc, (˘ω˘) fiewd and t-tewm tupwe. >_<
   *
   * the wayout of the packedvawues i-in the tewm poow is:
   *
   * i-index |0 |1 |2 |3 |4 |5 |6 |7 |8 |9 |
   * vawue |u |1a|1b|1c|u |2b|2c|p3|1d|1f|
   *
   * whewe u is unassigned, -.- p+x is a-a pointew to index x (e.g. 🥺 p3 means p-pointew to index 3), (U ﹏ U)
   * o-ow a doc id and facet (e.g. >w< doc id 1 and facet a wouwd be 1a). mya
   */
  p-pubwic void addfacet(int docid, >w< int fiewdid, int tewmid) {
    intbwockpoow f-facetspoow = facetcountingawway.getfacetspoow();
    int packedvawue = f-facetcountingawway.getfacet(docid);

    i-if (packedvawue == a-abstwactfacetcountingawway.unassigned) {
      // f-fiwst facet fow this doc. nyaa~~
      // keep it i-in the awway and don't add it to the map. (✿oωo)
      f-facetcountingawway.setfacet(docid, abstwactfacetcountingawway.encodefacetid(fiewdid, ʘwʘ tewmid));
      wetuwn;
    }

    if (!facetcountingawway.ispointew(packedvawue)) {
      // if the packedvawue i-is nyot a pointew, (ˆ ﻌ ˆ)♡ we know t-that we have exactwy o-one facet i-in the index
      // fow this document, 😳😳😳 so copy the existing facet i-into the poow. :3
      f-facetspoow.add(abstwactfacetcountingawway.unassigned);
      facetspoow.add(packedvawue);
    } e-ewse if (pweviousdocid != d-docid) {
      // we have seen t-this document id in a diffewent d-document. OwO stowe the pointew to the fiwst facet
      // f-fow this doc id in the p-poow so that we can twavewse the w-winked wist. (U ﹏ U)
      f-facetspoow.add(packedvawue);
    }

    pweviousdocid = docid;

    // add the nyew facet to the end of the facetcountingawway. >w<
    f-facetspoow.add(abstwactfacetcountingawway.encodefacetid(fiewdid, (U ﹏ U) t-tewmid));

    // set t-the facetvawue f-fow this document t-to the pointew to the facet we just added to the awway. 😳
    int p-poowpointew = abstwactfacetcountingawway.encodepointew(facetspoow.wength() - 1);
    facetcountingawway.setfacet(docid, (ˆ ﻌ ˆ)♡ poowpointew);
  }
}
