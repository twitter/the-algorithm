namespace java com.twitter.unified_user_actions.thriftjava
#@namespace scala com.twitter.unified_user_actions.thriftscala
#@namespace strato com.twitter.unified_user_actions

include "com/twitter/unified_user_actions/action_info.thrift"
include "com/twitter/unified_user_actions/common.thrift"
include "com/twitter/unified_user_actions/item.thrift"
include "com/twitter/unified_user_actions/metadata.thrift"
include "com/twitter/unified_user_actions/product_surface_info.thrift"

/*
 * A Unified User Action (UUA) is essentially a tuple of
 * (user, item, action type, some metadata) with more optional
 * information unique to product surfaces when available.
 * It represents a user (logged in / out) taking some action (e.g. engagement,
 * impression) on an item (e.g. tweet, profile).
 */
struct UnifiedUserAction {
   /* A user refers to either a logged in / logged out user */
   1: required common.UserIdentifier userIdentifier
   /* The item that received the action from the user */
   2: required item.Item item
   /* The type of action which took place */
   3: required action_info.ActionType actionType
   /* Useful for event level analysis and joins */
   4: required metadata.EventMetadata eventMetadata
   /* 
    * Product surface on which the action occurred. If None,
    * it means we can not capture the product surface (e.g. for server-side events).
    */
   5: optional product_surface_info.ProductSurface productSurface
   /* 
    * Product specific information like join keys. If None,
    * it means we can not capture the product surface information.
    */
   6: optional product_surface_info.ProductSurfaceInfo productSurfaceInfo
}(persisted='true', hasPersonalData='true')
