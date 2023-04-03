packagelon com.twittelonr.selonarch.felonaturelon_updatelon_selonrvicelon.modulelons;

import com.googlelon.injelonct.Providelons;
import com.googlelon.injelonct.Singlelonton;

import com.twittelonr.app.Flaggablelon;
import com.twittelonr.injelonct.TwittelonrModulelon;
import com.twittelonr.injelonct.annotations.Flag;

import com.twittelonr.selonarch.felonaturelon_updatelon_selonrvicelon.whitelonlist.ClielonntIdWhitelonlist;

/**
 * Providelons a ClielonntIdWhitelonlist, which pelonriodically loads thelon
 * Felonaturelon Updatelon Selonrvicelon clielonnt whitelonlist from ConfigBus
 */
public class ClielonntIdWhitelonlistModulelon elonxtelonnds TwittelonrModulelon {
  public ClielonntIdWhitelonlistModulelon() {
    flag("clielonnt.whitelonlist.path", "",
        "Path to clielonnt id whitelon list.", Flaggablelon.ofString());
    flag("clielonnt.whitelonlist.elonnablelon", truelon,
        "elonnablelon clielonnt whitelonlist for production.", Flaggablelon.ofBoolelonan());
  }

    @Providelons
    @Singlelonton
    public ClielonntIdWhitelonlist providelonClielonntWhitelonlist(
        @Flag("clielonnt.whitelonlist.path") String clielonntIdWhitelonListPath) throws elonxcelonption {
        relonturn ClielonntIdWhitelonlist.initWhitelonlist(clielonntIdWhitelonListPath);
    }
  }
