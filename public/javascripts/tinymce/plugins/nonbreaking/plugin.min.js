/**
 * Copyright (c) Tiny Technologies, Inc. All rights reserved.
 * Licensed under the LGPL or a commercial license.
 * For LGPL see License.txt in the project root for license information.
 * For commercial licenses see https://www.tiny.cloud/
 *
 * Version: 5.0.16 (2019-09-24)
 */
!function(){"use strict";function o(n,e){for(var t="",o=0;o<e;o++)t+=n;return t}var n=tinymce.util.Tools.resolve("tinymce.PluginManager"),i=function(n){var e=n.getParam("nonbreaking_force_tab",0);return"boolean"==typeof e?!0===e?3:0:e},a=function(n){return n.getParam("nonbreaking_wrap",!0,"boolean")},r=function(n,e){var t=a(n)||n.plugins.visualchars?'<span class="'+(function(n){return!!n.plugins.visualchars&&n.plugins.visualchars.isEnabled()}(n)?"mce-nbsp-wrap mce-nbsp":"mce-nbsp-wrap")+'" contenteditable="false">'+o("&nbsp;",e)+"</span>":o("&nbsp;",e);n.undoManager.transact(function(){return n.insertContent(t)})},e=function(n){n.addCommand("mceNonBreaking",function(){r(n,1)})},c=tinymce.util.Tools.resolve("tinymce.util.VK"),t=function(e){var t=i(e);0<t&&e.on("keydown",function(n){if(n.keyCode===c.TAB&&!n.isDefaultPrevented()){if(n.shiftKey)return;n.preventDefault(),n.stopImmediatePropagation(),r(e,t)}})},u=function(n){n.ui.registry.addButton("nonbreaking",{icon:"non-breaking",tooltip:"Nonbreaking space",onAction:function(){return n.execCommand("mceNonBreaking")}}),n.ui.registry.addMenuItem("nonbreaking",{icon:"non-breaking",text:"Nonbreaking space",onAction:function(){return n.execCommand("mceNonBreaking")}})};!function s(){n.add("nonbreaking",function(n){e(n),u(n),t(n)})}()}();