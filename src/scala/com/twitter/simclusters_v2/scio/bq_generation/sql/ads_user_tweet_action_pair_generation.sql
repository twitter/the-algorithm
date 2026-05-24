WITH
  vars AS (
    SELECT
      TIMESTAMP("{START_TIME}") AS start_date,
      TIMESTAMP("{END_TIME}") AS end_date
  ),

  ads_engagement AS (
    SELECT
        userId64 as userId,
        promotedTweetId as tweetId,
        UNIX_MILLIS(timestamp) AS tsMillis,
        lineItemId
    FROM `twttr-rev-core-data-prod.core_served_impressions.spend`, vars
    WHERE TIMESTAMP(_batchEnd) >= vars.start_date AND TIMESTAMP(_batchEnd) <= vars.end_date
    AND
      engagementType IN ({CONTRIBUTING_ACTION_TYPES_STR})
      AND lineItemObjective != 9 -- not pre-roll ads
  ),

  line_items AS (
      SELECT
        id AS lineItemId,
        end_time.posixTime AS endTime
      FROM
        `twttr-rev-core-data-prod.rev_ads_production.line_items`
  )


SELECT
  userId,
  tweetId,
  tsMillis
FROM ads_engagement JOIN line_items USING(lineItemId), vars
WHERE
  line_items.endTime IS NULL
  OR TIMESTAMP_MILLIS(line_items.endTime) >= vars.end_date

