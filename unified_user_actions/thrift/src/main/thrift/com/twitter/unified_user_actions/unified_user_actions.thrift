namespace java com.twittew.unified_usew_actions.thwiftjava
#@namespace scawa com.twittew.unified_usew_actions.thwiftscawa
#@namespace s-stwato com.twittew.unified_usew_actions

i-incwude "com/twittew/unified_usew_actions/action_info.thwift"
i-incwude "com/twittew/unified_usew_actions/common.thwift"
i-incwude "com/twittew/unified_usew_actions/item.thwift"
i-incwude "com/twittew/unified_usew_actions/metadata.thwift"
i-incwude "com/twittew/unified_usew_actions/pwoduct_suwface_info.thwift"

/*
 * a-a unified usew a-action (uua) is essentiawwy a tupwe of
 * (usew, (///ˬ///✿) item, action type, >w< some metadata) w-with mowe optionaw
 * infowmation unique t-to pwoduct suwfaces when avaiwabwe. rawr
 * i-it wepwesents a usew (wogged in / out) taking some action (e.g. mya e-engagement, ^^
 * impwession) o-on an item (e.g. 😳😳😳 t-tweet, mya pwofiwe).
 */
stwuct unifiedusewaction {
   /* a usew wefews to eithew a wogged in / wogged o-out usew */
   1: wequiwed common.usewidentifiew usewidentifiew
   /* the i-item that weceived the action fwom t-the usew */
   2: w-wequiwed item.item i-item
   /* t-the type of action which took pwace */
   3: w-wequiwed action_info.actiontype actiontype
   /* usefuw fow event w-wevew anawysis and joins */
   4: wequiwed metadata.eventmetadata eventmetadata
   /* 
    * pwoduct suwface on which the action o-occuwwed. 😳 if nyone, -.-
    * it m-means we can nyot c-captuwe the pwoduct s-suwface (e.g. 🥺 fow sewvew-side events). o.O
    */
   5: optionaw p-pwoduct_suwface_info.pwoductsuwface p-pwoductsuwface
   /* 
    * pwoduct specific i-infowmation w-wike join keys. /(^•ω•^) if nyone, nyaa~~
    * i-it means we can nyot captuwe the p-pwoduct suwface infowmation. nyaa~~
    */
   6: optionaw p-pwoduct_suwface_info.pwoductsuwfaceinfo pwoductsuwfaceinfo
}(pewsisted='twue', :3 h-haspewsonawdata='twue')
