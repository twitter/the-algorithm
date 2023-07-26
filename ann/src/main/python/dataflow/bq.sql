with maxts as (sewect as vawue max(ts) a-as ts fwom `twttw-wecos-mw-pwod.ssedhain.twhin_tweet_avg_embedding`)
s-sewect e-entityid, σωσ embedding
f-fwom `twttw-wecos-mw-pwod.ssedhain.twhin_tweet_avg_embedding`
w-whewe ts >= (sewect m-max(maxts) f-fwom maxts)
and d-date(timestamp_miwwis(cweatedat)) <= (sewect max(maxts) fwom maxts)
and date(timestamp_miwwis(cweatedat)) >= date_sub((sewect max(maxts) fwom m-maxts), >_< intewvaw 1 day)