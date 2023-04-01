/**
 * Copyright (c) Tiny Technologies, Inc. All rights reserved.
 * Licensed under the LGPL or a commercial license.
 * For LGPL see License.txt in the project root for license information.
 * For commercial licenses see https://www.tiny.cloud/
 *
 * Version: 5.0.16 (2019-09-24)
 */
!function(){"use strict";var n=tinymce.util.Tools.resolve("tinymce.PluginManager"),t=tinymce.util.Tools.resolve("tinymce.Env"),i=function(n){n.addCommand("mcePrint",function(){t.ie&&t.ie<=11?n.getDoc().execCommand("print",!1,null):n.getWin().print()})},e=function(n){n.ui.registry.addButton("print",{icon:"print",tooltip:"Print",onAction:function(){return n.execCommand("mcePrint")}}),n.ui.registry.addMenuItem("print",{text:"Print...",icon:"print",onAction:function(){return n.execCommand("mcePrint")}})};!function o(){n.add("print",function(n){i(n),e(n),n.addShortcut("Meta+P","","mcePrint")})}()}();