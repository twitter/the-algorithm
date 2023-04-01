/**
 * Copyright (c) Tiny Technologies, Inc. All rights reserved.
 * Licensed under the LGPL or a commercial license.
 * For LGPL see License.txt in the project root for license information.
 * For commercial licenses see https://www.tiny.cloud/
 */
/**
 * Jquery integration plugin.
 *
 * @class tinymce.core.JqueryIntegration
 * @private
 */
!function(){var f,c,u,p,d,s=[];d="undefined"!=typeof global?global:window,p=d.jQuery;function v(){
// Reference to tinymce needs to be lazily evaluated since tinymce
// might be loaded through the compressor or other means
return d.tinymce}p.fn.tinymce=function(o){var e,t,i,l=this,r="";
// No match then just ignore the call
if(!l.length)return l;
// Get editor instance
if(!o)return v()?v().get(l[0].id):null;l.css("visibility","hidden");function n(){var a=[],c=0;
// Apply patches to the jQuery object, only once
u||(m(),u=!0),
// Create an editor instance for each matched node
l.each(function(e,t){var n,i=t.id,r=o.oninit;
// Generate unique id for target element if needed
i||(t.id=i=v().DOM.uniqueId()),
// Only init the editor once
v().get(i)||(
// Create editor instance and render it
n=v().createEditor(i,o),a.push(n),n.on("init",function(){var e,t=r;l.css("visibility",""),
// Run this if the oninit setting is defined
// this logic will fire the oninit callback ones each
// matched editor instance is initialized
r&&++c==a.length&&("string"==typeof t&&(e=-1===t.indexOf(".")?null:v().resolve(t.replace(/\.\w+$/,"")),t=v().resolve(t)),
// Call the oninit function with the object
t.apply(e||v(),a))}))}),
// Render the editor instances in a separate loop since we
// need to have the full editors array used in the onInit calls
p.each(a,function(e,t){t.render()})}
// Load TinyMCE on demand, if we need to
if(d.tinymce||c||!(e=o.script_url))
// Delay the init call until tinymce is loaded
1===c?s.push(n):n();else{c=1,t=e.substring(0,e.lastIndexOf("/")),
// Check if it's a dev/src version they want to load then
// make sure that all plugins, themes etc are loaded in source mode as well
-1!=e.indexOf(".min")&&(r=".min"),
// Setup tinyMCEPreInit object this will later be used by the TinyMCE
// core script to locate other resources like CSS files, dialogs etc
// You can also predefined a tinyMCEPreInit object and then it will use that instead
d.tinymce=d.tinyMCEPreInit||{base:t,suffix:r},
// url contains gzip then we assume it's a compressor
-1!=e.indexOf("gzip")&&(i=o.language||"en",e=e+(/\?/.test(e)?"&":"?")+"js=true&core=true&suffix="+escape(r)+"&themes="+escape(o.theme||"modern")+"&plugins="+escape(o.plugins||"")+"&languages="+(i||""),
// Check if compressor script is already loaded otherwise setup a basic one
d.tinyMCE_GZ||(d.tinyMCE_GZ={start:function(){function n(e){v().ScriptLoader.markDone(v().baseURI.toAbsolute(e))}
// Add core languages
n("langs/"+i+".js"),
// Add themes with languages
n("themes/"+o.theme+"/theme"+r+".js"),n("themes/"+o.theme+"/langs/"+i+".js"),
// Add plugins with languages
p.each(o.plugins.split(","),function(e,t){t&&(n("plugins/"+t+"/plugin"+r+".js"),n("plugins/"+t+"/langs/"+i+".js"))})},end:function(){}}));var a=document.createElement("script");a.type="text/javascript",a.onload=a.onreadystatechange=function(e){e=e||window.event,2===c||"load"!=e.type&&!/complete|loaded/.test(a.readyState)||(v().dom.Event.domLoaded=1,c=2,
// Execute callback after mainscript has been loaded and before the initialization occurs
o.script_loaded&&o.script_loaded(),n(),p.each(s,function(e,t){t()}))},a.src=e,document.body.appendChild(a)}return l},
// Add :tinymce pseudo selector this will select elements that has been converted into editor instances
// it's now possible to use things like $('*:tinymce') to get all TinyMCE bound elements.
p.extend(p.expr[":"],{tinymce:function(e){var t;return!!(e.id&&"tinymce"in d&&(t=v().get(e.id))&&t.editorManager===v())}});
// This function patches internal jQuery functions so that if
// you for example remove an div element containing an editor it's
// automatically destroyed by the TinyMCE API
var m=function(){function r(e){
// If the function is remove
"remove"===e&&this.each(function(e,t){var n=u(t);n&&n.remove()}),this.find("span.mceEditor,div.mceEditor").each(function(e,t){var n=v().get(t.id.replace(/_parent$/,""));n&&n.remove()})}function o(i){var e,t=this;
// Handle set value
/*jshint eqnull:true */if(null!=i)r.call(t),
// Saves the contents before get/set value of textarea/div
t.each(function(e,t){var n;(n=v().get(t.id))&&n.setContent(i)});else if(0<t.length&&(e=v().get(t[0].id)))return e.getContent()}function l(e){return!!(e&&e.length&&d.tinymce&&e.is(":tinymce"))}
// Removes any child editor instances by looking for editor wrapper elements
var u=function(e){var t=null;return e&&e.id&&d.tinymce&&(t=v().get(e.id)),t},s={};
// Loads or saves contents from/to textarea if the value
// argument is defined it will set the TinyMCE internal contents
// Patch some setter/getter functions these will
// now be able to set/get the contents of editor instances for
// example $('#editorid').html('Content'); will update the TinyMCE iframe instance
p.each(["text","html","val"],function(e,t){var a=s[t]=p.fn[t],c="text"===t;p.fn[t]=function(e){var t=this;if(!l(t))return a.apply(t,arguments);if(e!==f)return o.call(t.filter(":tinymce"),e),a.apply(t.not(":tinymce"),arguments),t;// return original set for chaining
var i="",r=arguments;return(c?t:t.eq(0)).each(function(e,t){var n=u(t);i+=n?c?n.getContent().replace(/<(?:"[^"]*"|'[^']*'|[^'">])*>/g,""):n.getContent({save:!0}):a.apply(p(t),r)}),i}}),
// Makes it possible to use $('#id').append("content"); to append contents to the TinyMCE editor iframe
p.each(["append","prepend"],function(e,t){var n=s[t]=p.fn[t],r="prepend"===t;p.fn[t]=function(i){var e=this;return l(e)?i!==f?("string"==typeof i&&e.filter(":tinymce").each(function(e,t){var n=u(t);n&&n.setContent(r?i+n.getContent():n.getContent()+i)}),n.apply(e.not(":tinymce"),arguments),e):void 0:n.apply(e,arguments)}}),
// Makes sure that the editor instance gets properly destroyed when the parent element is removed
p.each(["remove","replaceWith","replaceAll","empty"],function(e,t){var n=s[t]=p.fn[t];p.fn[t]=function(){return r.call(this,t),n.apply(this,arguments)}}),s.attr=p.fn.attr,
// Makes sure that $('#tinymce_id').attr('value') gets the editors current HTML contents
p.fn.attr=function(e,t){var n=this,i=arguments;if(!e||"value"!==e||!l(n))return s.attr.apply(n,i);if(t!==f)return o.call(n.filter(":tinymce"),t),s.attr.apply(n.not(":tinymce"),i),n;// return original set for chaining
var r=n[0],a=u(r);return a?a.getContent({save:!0}):s.attr.apply(p(r),i)}}}();