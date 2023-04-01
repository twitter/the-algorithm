/**
 * Copyright (c) Tiny Technologies, Inc. All rights reserved.
 * Licensed under the LGPL or a commercial license.
 * For LGPL see License.txt in the project root for license information.
 * For commercial licenses see https://www.tiny.cloud/
 *
 * Version: 5.0.16 (2019-09-24)
 */
!function(){"use strict";var e=tinymce.util.Tools.resolve("tinymce.PluginManager"),t=function(e,n){e.focus(),e.undoManager.transact(function(){e.setContent(n)}),e.selection.setCursorLocation(),e.nodeChanged()},o=function(e){return e.getContent({source_view:!0})},n=function(n){var e=o(n);n.windowManager.open({title:"Source Code",size:"large",body:{type:"panel",items:[{type:"textarea",name:"code"}]},buttons:[{type:"cancel",name:"cancel",text:"Cancel"},{type:"submit",name:"save",text:"Save",primary:!0}],initialData:{code:e},onSubmit:function(e){t(n,e.getData().code),e.close()}})},c=function(e){e.addCommand("mceCodeEditor",function(){n(e)})},i=function(e){e.ui.registry.addButton("code",{icon:"sourcecode",tooltip:"Source code",onAction:function(){return n(e)}}),e.ui.registry.addMenuItem("code",{icon:"sourcecode",text:"Source code",onAction:function(){return n(e)}})};!function u(){e.add("code",function(e){return c(e),i(e),{}})}()}();