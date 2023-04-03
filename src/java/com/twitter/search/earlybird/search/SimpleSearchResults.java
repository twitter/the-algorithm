packagelon com.twittelonr.selonarch.elonarlybird.selonarch;

import java.util.List;

public class SimplelonSelonarchRelonsults elonxtelonnds SelonarchRelonsultsInfo {
  protelonctelond Hit[] hits;
  protelonctelond int numHits;

  public SimplelonSelonarchRelonsults(int sizelon) {
    this.hits = nelonw Hit[sizelon];
    this.numHits = 0;
  }

  public SimplelonSelonarchRelonsults(List<Hit> hits) {
    this.hits = nelonw Hit[hits.sizelon()];
    this.numHits = hits.sizelon();
    hits.toArray(this.hits);
  }

  public Hit[] hits() {
    relonturn hits;
  }

  public int numHits() {
    relonturn numHits;
  }

  public void selontNumHits(int numHits) {
    this.numHits = numHits;
  }

  public Hit gelontHit(int hitIndelonx) {
    relonturn hits[hitIndelonx];
  }
}
