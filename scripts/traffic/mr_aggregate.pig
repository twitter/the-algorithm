/*  Aggregate output from processed logs:
 *
 *  Go from entry per unique_id (including # of impressions)
 *  to total # of uniques, total # of impressions
 *
 *  Needs to be passed: INPUT, OUTPUT
 */

/****************************************************
 * DEFINITIONS
 ****************************************************/

-- Cleanup
rmf $OUTPUT

/****************************************************
 * AGGREGATE
 ****************************************************/

-- sitewide --
sitewide = LOAD '$INPUT/sitewide' AS (unique_id, count:long);

sitewide_grouped = GROUP sitewide BY unique_id;
sitewide_combined = FOREACH sitewide_grouped
                      GENERATE group AS unique_id,
                               SUM(sitewide.count) as count;

sitewide_grouped2 = GROUP sitewide_combined ALL;
sitewide_totals = FOREACH sitewide_grouped2
                    GENERATE group,
                             COUNT(sitewide_combined),
                             SUM(sitewide_combined.count);

STORE sitewide_totals INTO '$OUTPUT/sitewide';

-- subreddit --
subreddit = LOAD '$INPUT/subreddit' AS (subreddit, unique_id, count:long);

subreddit_grouped = GROUP subreddit BY (subreddit, unique_id);
subreddit_combined = FOREACH subreddit_grouped
                        GENERATE group.subreddit AS subreddit,
                                 group.unique_id AS unique_id,
                                 SUM(subreddit.count) AS count;

subreddit_grouped2 = GROUP subreddit_combined BY subreddit;
subreddit_totals = FOREACH subreddit_grouped2
                      GENERATE group,
                      COUNT(subreddit_combined),
                      SUM(subreddit_combined.count);

STORE subreddit_totals INTO '$OUTPUT/subreddit';

-- subreddit path
srpath = LOAD '$INPUT/srpath' AS (srpath, unique_id, count:long);

srpath_grouped = GROUP srpath BY (srpath, unique_id);
srpath_combined = FOREACH srpath_grouped
                    GENERATE group.srpath AS srpath,
                             group.unique_id AS unique_id,
                             SUM(srpath.count) AS count;

srpath_grouped2 = GROUP srpath_combined BY srpath;
srpath_totals = FOREACH srpath_grouped2
                  GENERATE group,
                           COUNT(srpath_combined),
                           SUM(srpath_combined.count);

STORE srpath_totals INTO '$OUTPUT/srpath';

-- language
lang = LOAD '$INPUT/lang' AS (lang, unique_id, count:long);

lang_grouped = GROUP lang BY (lang, unique_id);
lang_combined = FOREACH lang_grouped
                  GENERATE group.lang AS lang,
                           group.unique_id AS unique_id,
                           SUM(lang.count) AS count;

lang_grouped2 = GROUP lang_combined BY lang;
lang_totals = FOREACH lang_grouped2
                GENERATE group,
                         COUNT(lang_combined),
                         SUM(lang_combined.count);

STORE lang_totals INTO '$OUTPUT/lang';

-- clicks
click = LOAD '$INPUT/clicks' AS (fullname, unique_id, count:long);

click_grouped = GROUP click BY (fullname, unique_id);
click_combined = FOREACH click_grouped
                    GENERATE group.fullname AS fullname,
                             group.unique_id AS unique_id,
                             SUM(click.count) AS count;

click_grouped2 = GROUP click_combined BY fullname;
click_totals = FOREACH click_grouped2
                  GENERATE group,
                           COUNT(click_combined),
                           SUM(click_combined.count);

STORE click_totals INTO '$OUTPUT/clicks';

-- targeted clicks
t_click = LOAD '$INPUT/clicks_targeted' AS (fullname, sr, unique_id, count:long);

t_click_grouped = GROUP t_click BY (fullname, sr, unique_id);
t_click_combined = FOREACH t_click_grouped
                      GENERATE group.fullname AS fullname,
                               group.sr AS sr,
                               group.unique_id AS unique_id,
                               SUM(t_click.count) AS count;

t_click_grouped2 = GROUP t_click_combined BY (fullname, sr);
t_click_totals = FOREACH t_click_grouped2
                    GENERATE group.fullname,
                             group.sr,
                             COUNT(t_click_combined),
                             SUM(t_click_combined.count);

STORE t_click_totals INTO '$OUTPUT/clicks_targeted';

-- things
thing = LOAD '$INPUT/thing'AS (fullname, unique_id, count:long);

thing_grouped = GROUP thing BY (fullname, unique_id);
thing_combined = FOREACH thing_grouped
                    GENERATE group.fullname AS fullname,
                             group.unique_id AS unique_id,
                             SUM(thing.count) AS count;

thing_grouped2 = GROUP thing_combined BY fullname;
thing_totals = FOREACH thing_grouped2
                  GENERATE group,
                           COUNT(thing_combined),
                           SUM(thing_combined.count);

STORE thing_totals INTO '$OUTPUT/thing';

-- targeted things
t_thing = LOAD '$INPUT/thingtarget' AS (fullname, sr, unique_id, count:long);

t_thing_grouped = GROUP t_thing BY (fullname, sr, unique_id);
t_thing_combined = FOREACH t_thing_grouped
                      GENERATE group.fullname AS fullname,
                               group.sr AS sr,
                               group.unique_id AS unique_id,
                               SUM(t_thing.count) AS count;

t_thing_grouped2 = GROUP t_thing_combined BY (fullname, sr);
t_thing_totals = FOREACH t_thing_grouped2
                    GENERATE group.fullname,
                             group.sr,
                             COUNT(t_thing_combined),
                             SUM(t_thing_combined.count);

STORE t_thing_totals INTO '$OUTPUT/thingtarget';
