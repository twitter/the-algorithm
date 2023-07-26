namespace java com.twittew.unified_usew_actions.thwiftjava
#@namespace scawa com.twittew.unified_usew_actions.thwiftscawa
#@namespace s-stwato com.twittew.unified_usew_actions

i-incwude "com/twittew/unified_usew_actions/action_info.thwift"
i-incwude "com/twittew/unified_usew_actions/common.thwift"
i-incwude "com/twittew/unified_usew_actions/metadata.thwift"

/*
 * t-this is mainwy f-fow view counts p-pwoject, >_< which o-onwy wequiwe minimum fiewds fow nyow. rawr x3
 * the nyame keyeduuatweet indicates t-the vawue is about a tweet, mya nyot a moment ow othew e-entities. nyaa~~
 */
stwuct keyeduuatweet {
   /* a-a usew wefews to eithew a wogged in / wogged out usew */
   1: w-wequiwed common.usewidentifiew u-usewidentifiew
   /* t-the tweet that weceived the action fwom the usew */
   2: wequiwed i64 tweetid(pewsonawdatatype='tweetid')
   /* t-the type of action which took pwace */
   3: wequiwed action_info.actiontype actiontype
   /* usefuw fow event w-wevew anawysis and joins */
   4: w-wequiwed metadata.eventmetadata e-eventmetadata
}(pewsisted='twue', (⑅˘꒳˘) h-haspewsonawdata='twue')
