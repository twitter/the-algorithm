/**
 * Copyright (c) Tiny Technologies, Inc. All rights reserved.
 * Licensed under the LGPL or a commercial license.
 * For LGPL see License.txt in the project root for license information.
 * For commercial licenses see https://www.tiny.cloud/
 *
 * Version: 5.0.16 (2019-09-24)
 */
!function(){"use strict";var n=tinymce.util.Tools.resolve("tinymce.PluginManager"),o=function(n){n.addCommand("InsertHorizontalRule",function(){n.execCommand("mceInsertContent",!1,"<hr />")})},t=function(n){n.ui.registry.addButton("hr",{icon:"horizontal-rule",tooltip:"Horizontal line",onAction:function(){return n.execCommand("InsertHorizontalRule")}}),n.ui.registry.addMenuItem("hr",{icon:"horizontal-rule",text:"Horizontal line",onAction:function(){return n.execCommand("InsertHorizontalRule")}})};!function e(){n.add("hr",function(n){o(n),t(n)})}()}();