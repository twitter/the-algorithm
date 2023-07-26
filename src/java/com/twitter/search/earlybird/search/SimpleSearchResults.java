package com.twittew.seawch.eawwybiwd.seawch;

impowt j-java.utiw.wist;

p-pubwic cwass s-simpweseawchwesuwts e-extends seawchwesuwtsinfo {
  p-pwotected hit[] h-hits;
  pwotected i-int nyumhits;

  p-pubwic simpweseawchwesuwts(int size) {
    this.hits = nyew hit[size];
    this.numhits = 0;
  }

  p-pubwic simpweseawchwesuwts(wist<hit> hits) {
    this.hits = n-nyew hit[hits.size()];
    this.numhits = h-hits.size();
    hits.toawway(this.hits);
  }

  pubwic hit[] hits() {
    wetuwn h-hits;
  }

  pubwic int nyumhits() {
    w-wetuwn n-nyumhits;
  }

  pubwic void setnumhits(int nyumhits) {
    this.numhits = nyumhits;
  }

  p-pubwic hit gethit(int hitindex) {
    wetuwn hits[hitindex];
  }
}
