/*  EMR Version
 *
 *  Coalesce output from multiple processed logs within interval
 *  hours --> day
 *  days --> month
 *
 *  Needs to be passed: INPUT, OUTPUT
 */

/****************************************************
 * DEFINITIONS
 ****************************************************/

-- Cleanup
rmf $OUTPUT

/****************************************************
 * COALESCE
 ****************************************************/

-- sitewide
sitewide = LOAD '$INPUT/sitewide' AS (unique_id, count:long);  -- load all input files (multiple hours)

sitewide_grouped = GROUP sitewide BY unique_id; -- (unique_id, {(unique_id, count), ...}, ...)

sitewide_coalesced = FOREACH sitewide_grouped 
                     GENERATE group, SUM(sitewide.count); -- ((unique_id, SUM(sitewide.count), ...)

STORE sitewide_coalesced INTO '$OUTPUT/sitewide';

-- subreddit
subreddit_counters = LOAD '$INPUT/subreddit' AS (subreddit, unique_id, count:long);

subreddits_grouped = GROUP subreddit_counters BY (subreddit, unique_id);

subreddits_coalesced = FOREACH subreddits_grouped
                       GENERATE group.subreddit, group.unique_id,
                                SUM(subreddit_counters.count) AS count;

STORE subreddits_coalesced INTO '$OUTPUT/subreddit';

-- subreddit path
srpath = LOAD '$INPUT/srpath' AS (srpath, unique_id, count:long);

srpath_grouped = GROUP srpath BY (srpath, unique_id);

srpath_coalesced = FOREACH srpath_grouped
                   GENERATE group.srpath, group.unique_id,
                            SUM(srpath.count) AS count;

STORE srpath_coalesced INTO '$OUTPUT/srpath';

-- language 
lang = LOAD '$INPUT/lang' AS (lang, unique_id, count:long);

lang_grouped = GROUP lang BY (lang, unique_id);

lang_coalesced = FOREACH lang_grouped
                 GENERATE group.lang, group.unique_id,
                          SUM(lang.count) AS count;

STORE lang_coalesced INTO '$OUTPUT/lang';

-- click
click = LOAD '$INPUT/clicks' AS (fullname, unique_id, count:long);

click_grouped = GROUP click BY (fullname, unique_id);

click_coalesced = FOREACH click_grouped
                  GENERATE group.fullname, group.unique_id,
                           SUM(click.count) AS count;

STORE click_coalesced INTO '$OUTPUT/clicks';

-- clicktarget
clicktarget = LOAD '$INPUT/clicks_targeted' AS (fullname, sr, unique_id, count:long);

clicktarget_grouped = GROUP clicktarget BY (fullname, sr, unique_id);

clicktarget_coalesced = FOREACH clicktarget_grouped
                        GENERATE group.fullname, group.sr, group.unique_id,
                                 SUM(clicktarget.count) AS count;

STORE clicktarget_coalesced INTO '$OUTPUT/clicks_targeted';

-- thing
thing = LOAD '$INPUT/thing' AS (fullname, unique_id, count:long);

thing_grouped = GROUP thing BY (fullname, unique_id);

thing_coalesced = FOREACH thing_grouped
                  GENERATE group.fullname, group.unique_id,
                           SUM(thing.count) AS count;

STORE thing_coalesced INTO '$OUTPUT/thing';

-- thingtarget
thingtarget = LOAD '$INPUT/thingtarget' AS (fullname, sr, unique_id, count:long);

thingtarget_grouped = GROUP thingtarget BY (fullname, sr, unique_id);

thingtarget_coalesced = FOREACH thingtarget_grouped
                        GENERATE group.fullname, group.sr, group.unique_id,
                                 SUM(thingtarget.count) AS count;

STORE thingtarget_coalesced INTO '$OUTPUT/thingtarget';
