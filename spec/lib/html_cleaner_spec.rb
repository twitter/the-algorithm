# -*- coding: utf-8 -*-
require 'spec_helper'
require 'nokogiri'

describe HtmlCleaner do
  include HtmlCleaner

  describe "sanitize_value" do
    ArchiveConfig.FIELDS_ALLOWING_VIDEO_EMBEDS.each do |field|
      context "#{field} is configured to allow video embeds" do
        %w[youtube.com youtube-nocookie.com vimeo.com player.vimeo.com 
           vidders.net criticalcommons.org google.com archiveofourown.org podfic.com archive.org
           open.spotify.com spotify.com 8tracks.com w.soundcloud.com soundcloud.com viddertube.com].each do |source|

          it "keeps embeds from #{source}" do
            html = '<iframe width="560" height="315" src="//' + source + '/embed/123" frameborder="0"></iframe>'
            result = sanitize_value(field, html)
            expect(result).to include(html)
          end
        end

        %w[youtube.com youtube-nocookie.com vimeo.com player.vimeo.com
           archiveofourown.org archive.org 8tracks.com podfic.com
           open.spotify.com spotify.com w.soundcloud.com soundcloud.com vidders.net viddertube.com].each do |source|

          it "converts src to https for #{source}" do
            html = '<iframe width="560" height="315" src="http://' + source + '/embed/123" frameborder="0"></iframe>'
            result = sanitize_value(field, html)
            expect(result).to match('https:')
          end
        end

        %w[vidders.net].each do |source|
          it "converts flashvars to https for #{source}" do
            html = '<embed flashvars="config=http://' + source + '/embed/123" src="http://' + source + '/embed/123" type="application/x-shockwave-flash" width="456" height="344"></embed>'
            result = sanitize_value(field, html)
            expect(result).to match('flashvars=.*https:')
          end
        end

        it "keeps google player embeds" do
          html = '<embed type="application/x-shockwave-flash" flashvars="audioUrl=http://dl.dropbox.com/u/123/foo.mp3" src="http://www.google.com/reader/ui/123-audio-player.swf" width="400" height="27" allowscriptaccess="never" allownetworking="internal"></embed>'
          result = sanitize_value(field, html)
          expect(result).to include(html)
        end

        it "converts an Archive-hosted Dewplayer embed into an audio tag" do
          html = '<embed type="application/x-shockwave-flash" flashvars="mp3=http://example.com/next%20color%20planet.mp3?dl=0" src="https://archiveofourown.org/system/dewplayer/dewplayer.swf" width="200" height="27" allowscriptaccess="never" allownetworking="internal"></embed>'
          expect(sanitize_value(field, html)).to include('<audio src="https://example.com/next%20color%20planet.mp3?dl=0" controls="controls" crossorigin="anonymous" preload="metadata"></audio>')
        end

        it "converts an Archive-hosted Dewplayer embed with single quotes in the source URL into an audio tag" do
          html = '<embed type="application/x-shockwave-flash" flashvars="mp3=http://example.com/\'quote%27.mp3" src="https://archiveofourown.org/system/dewplayer/dewplayer.swf" width="200" height="27" allowscriptaccess="never" allownetworking="internal"></embed>'
          expect(sanitize_value(field, html)).to include('<audio src="https://example.com/\'quote%27.mp3" controls="controls" crossorigin="anonymous" preload="metadata"></audio>')
        end

        it "converts an Archive-hosted Dewplayer embed with double quotes in the source URL into an audio tag" do
          html = '<embed type="application/x-shockwave-flash" flashvars=\'mp3=http://example.com/"quote%22.mp3\' src="https://archiveofourown.org/system/dewplayer/dewplayer.swf" width="200" height="27" allowscriptaccess="never" allownetworking="internal"></embed>'
          expect(sanitize_value(field, html)).to include('<audio src="https://example.com/%22quote%22.mp3" controls="controls" crossorigin="anonymous" preload="metadata"></audio>')
        end

        it "converts an Archive-hosted Dewplayer embed with spaces around the source URL into an audio tag" do
          html = '<embed type="application/x-shockwave-flash" flashvars="mp3=  http://example.com/fic.mp3 " src="https://archiveofourown.org/system/dewplayer/dewplayer-vol.swf" allowscriptaccess="never" allownetworking="internal"></embed>'
          expect(sanitize_value(field, html)).to include('<audio src="https://example.com/fic.mp3" controls="controls" crossorigin="anonymous" preload="metadata"></audio>')
        end

        it "converts an Archive-hosted Dewplayer embed with an encoded source URL into an audio tag" do
          html = '<embed type="application/x-shockwave-flash" flashvars="param=val&amp;mp3=http%3A%2F%2Fexample.com%2Fnext%2520color%2520planet%2527.mp3%3Fdl%3D0" src="https://archiveofourown.org/system/dewplayer/dewplayer.swf" width="200" height="27" allowscriptaccess="never" allownetworking="internal"></embed>'
          expect(sanitize_value(field, html)).to include('<audio src="https://example.com/next%20color%20planet%27.mp3?dl=0" controls="controls" crossorigin="anonymous" preload="metadata"></audio>')
        end

        it "converts an Archive-hosted Dewplayer multi embed into audio tags" do
          html = '<embed type="application/x-shockwave-flash" flashvars="mp3=http://example.com/live-again.mp3|http://example.com/cursed-night.mp3" src="https://archiveofourown.org/system/dewplayer/dewplayer.swf" width="200" height="27" allowscriptaccess="never" allownetworking="internal"></embed>'
          result = sanitize_value(field, html)
          expect(result.squish).to eq('<p> <audio src="https://example.com/live-again.mp3" controls="controls" crossorigin="anonymous" preload="metadata"></audio> <br /> <audio src="https://example.com/cursed-night.mp3" controls="controls" crossorigin="anonymous" preload="metadata"></audio> </p>')
        end

        it "strips embeds with unknown source" do
          html = '<embed src="http://www.evil.org"></embed>'
          result = sanitize_value(field, html)
          expect(result).to be_empty
        end

        it "strips archive.org iframe if the src is not the embed directory" do
          html = '<iframe src="http://archive.org/embed/../123/wrong/456.html"></iframe>'
          result = sanitize_value(field, html)
          expect(result).to be_empty
        end

        %w[criticalcommons.org].each do |source|
          it "doesn't convert src to https for #{source}" do
            html = '<iframe width="560" height="315" src="http://' + source + '/embed/123" frameborder="0"></iframe>'
            result = sanitize_value(field, html)
            expect(result).not_to match('https:')
          end
        end

        it "allows video tags" do
          html = '<video controls="controls" width="250" playsinline="playsinline" crossorigin="anonymous" preload="metadata">\
              <track kind="subtitles" src="http://example.com/english.vtt" srclang="en">\
              <track kind="subtitles" src="http://example.com/japanese.vtt" srclang="ja" default="default">\
            </video>'
          expect(sanitize_value(field, html)).to eq(html)
        end

        it "allows audio tags" do
          html = '<audio controls="controls" crossorigin="anonymous" preload="metadata" loop="loop">\
              <source src="http://example.com/podfic.mp3" type="audio/mpeg">\
              <p>Maybe you want to <a href="http://example.com/podfic.mp3" rel="nofollow">download this podfic instead</a>?</p>\
            </audio>'
          expect(sanitize_value(field, html)).to eq(html)
        end
      end
    end

    context "Strip out tags not allowed in text fields other than content" do
      [:endnotes, :notes, :summary].each do |field|
        it "strips iframes" do
          value = '<iframe width="560" height="315" src="//youtube.com/embed/123" frameborder="0"></iframe>'
          result = sanitize_value(field, value)
          expect(result).to eq("")
        end

        it "strips video tags" do
          value = "<video></video>"
          result = sanitize_value(field, value)
          expect(result).to eq("")
        end
      end
    end

    ArchiveConfig.FIELDS_ALLOWING_CSS.each do |field|
      context "#{field} field allows class attribute for CSS" do
        context "class has one value" do
          it "keeps values containing only letters, numbers, and hyphens" do
            result = sanitize_value(field, '<p class="f-5">foobar</p>')
            doc = Nokogiri::HTML.fragment(result)
            expect(doc.xpath("./p[@class='f-5']").children.to_s.strip).to eq("foobar")
          end

          it "strips values starting with a number" do
            result = sanitize_value(field, '<p class="8ball">foobar</p>')
            expect(result).not_to match(/8ball/)
          end

          it "strips values starting with a hyphen" do
            result = sanitize_value(field, '<p class="-dash">foobar</p>')
            expect(result).not_to match(/-dash/)
          end

          it "strips values with special characters" do
            result = sanitize_value(field, '<p class="foo@bar">foobar</p>')
            expect(result).not_to match(/foo@bar/)
          end
        end

        context "class attribute has multiple values" do
          it "keeps all valid values" do
            result = sanitize_value(field, '<p class="foo bar">foobar</p>')
            doc = Nokogiri::HTML.fragment(result)
            expect(doc.xpath("./p[contains(@class, 'foo bar')]").children.to_s.strip).to eq("foobar")
          end

          it "strips values starting with numbers" do
            result = sanitize_value(field, '<p class="magic 8ball">foobar</p>')
            expect(result).not_to match(/8ball/)
            expect(result).to match(/magic/)
          end

          it "strips values starting with hypens" do
            result = sanitize_value(field, '<p class="rainbow -dash">foobar</p>')
            expect(result).not_to match(/-dash/)
            expect(result).to match(/rainbow/)
          end
        end
      end
    end

    [:comment_content, :bookmarker_notes, :summary].each do |field|
      context "#{field} field does not allow class attribute" do
        it "strips attribute even if value is valid" do
          result = sanitize_value(field, '<p class="f-5">foobar</p>')
          expect(result).not_to match(/f-5/)
          expect(result).not_to match(/class/)
        end
      end
    end

    [:content, :endnotes, :notes, :summary].each do |field|
      context "Sanitize #{field} field" do
        it "should keep html" do
          value = "<em>hello</em> <blockquote>world</blockquote>"
          result = sanitize_value(field, value)
          doc = Nokogiri::HTML.fragment(result)
          expect(doc.xpath(".//em").children.to_s.strip).to eq("hello")
          expect(doc.xpath(".//blockquote").children.to_s.strip).to eq("<p>world</p>")
        end

        it "should keep valid unicode chars as is" do
          result = sanitize_value(field, "„‚nörmäl’—téxt‘“")
          expect(result).to match(/„‚nörmäl’—téxt‘“/)
        end

        it "should allow RTL content in p" do
          html = '<p dir="rtl">This is RTL content</p>'
          result = sanitize_value(field, html)
          expect(result).to eq(html)
        end

        it "should allow RTL content in div" do
          html = '<div dir="rtl"><p>This is RTL content</p></div>'
          result = sanitize_value(field, html)
          expect(result.to_s.squish).to eq('<div dir="rtl"> <p>This is RTL content</p> </div>')
        end

        it "should not allow iframes with unknown source" do
          html = '<iframe src="http://www.evil.org"></iframe>'
          result = sanitize_value(field, html)
          expect(result).to be_empty
        end

        [
          "'';!--\"<XSS>=&{()}",
          '<XSS STYLE="behavior: url(xss.htc);">'
        ].each do |value|
          it "should strip xss tags: #{value}" do
            result = sanitize_value(field, value)
            expect(result).not_to match(/xss/i)
          end
        end

        [
          "<SCRIPT SRC=http://ha.ckers.org/xss.js></SCRIPT>",
          '<<SCRIPT>alert("XSS");//<</SCRIPT>',
          "<SCRIPT SRC=http://ha.ckers.org/xss.js?<B>",
          "<SCRIPT SRC=//ha.ckers.org/.j>",
          "<SCRIPT>alert(/XSS/.source)</SCRIPT>",
          '</TITLE><SCRIPT>alert("XSS");</SCRIPT>',
          '<SCRIPT SRC="http://ha.ckers.org/xss.jpg"></SCRIPT>'
        ].each do |value|
          it "should strip script tags: #{value}" do
            result = sanitize_value(field, value)
            expect(result).not_to match(/script/i)
            expect(result).not_to match(/ha.ckers.org/)
          end
        end

        [
          "\\\";alert('XSS');//",
          "xss:expr/*blah*/ession(alert('XSS'))",
          "xss:expression(alert('XSS'))"
        ].each do |value|
          it "should keep text: #{value}" do
            result = sanitize_value(field, value)
            expect(result).to match(/alert\('XSS'\)/)
          end
        end

        it "should strip iframe tags" do
          value = "<iframe src=http://ha.ckers.org/scriptlet.html <"
          result = sanitize_value(field, value)
          expect(result).not_to match(/iframe/i)
          expect(result).not_to match(/ha.ckers.org/)
        end

        [
          "<IMG SRC=\"javascript:alert('XSS');\">",
          "<IMG SRC=JaVaScRiPt:alert('XSS')>",
          "<IMG SRC=javascript:alert(String.fromCharCode(88,83,83))>",
          "<IMG SRC=&#106;&#97;&#118;&#97;&#115;&#99;&#114;&#105;&#112;&#116;&#58;&#97;&#108;&#101;&#114;&#116;&#40;&#39;&#88;&#83;&#83;&#39;&#41;>",
          "<IMG SRC=&#0000106&#0000097&#0000118&#0000097&#0000115&#0000099&#0000114&#0000105&#0000112&#0000116&#0000058&#0000097&#0000108&#0000101&#0000114&#0000116&#0000040&#0000039&#0000088&#0000083&#0000083&#0000039&#0000041>",
          "<IMG SRC=&#x6A&#x61&#x76&#x61&#x73&#x63&#x72&#x69&#x70&#x74&#x3A&#x61&#x6C&#x65&#x72&#x74&#x28&#x27&#x58&#x53&#x53&#x27&#x29>",
          "<IMG SRC=\" &#14;  javascript:alert('XSS');\">",
          "<IMG SRC=\"javascript:alert('XSS')\"",
          "<INPUT TYPE=\"IMAGE\" SRC=\"javascript:alert('XSS');\">",
          "<IMG SRC=\"jav	ascript:alert('XSS');\">",
          "<IMG SRC=\"jav&#x09;ascript:alert('XSS');\">",
          "<IMG SRC=\"jav&#x0A;ascript:alert('XSS');\">",
          "<IMG SRC=\"jav&#x0D;ascript:alert('XSS');\">"
        ].each do |value|
          it "should strip javascript in img src attribute: #{value[0..40]}" do
            result = sanitize_value(field, value)
            expect(result).not_to match(/xss/i)
            expect(result).not_to match(/javascript/i)
          end
        end

        [
          '<META HTTP-EQUIV="Link" Content="<http://ha.ckers.org/xss.css>; REL=stylesheet">',
          "<META HTTP-EQUIV=\"refresh\" CONTENT=\"0;url=javascript:alert('XSS');\">",
          '<META HTTP-EQUIV="refresh" CONTENT="0;url=data:text/html;base64,PHNjcmlwdD5hbGVydCgnWFNTJyk8L3NjcmlwdD4K">',
          "<META HTTP-EQUIV=\"refresh\" CONTENT=\"0; URL=http://;URL=javascript:alert('XSS');\">",
          "<META HTTP-EQUIV=\"Set-Cookie\" Content=\"USERID=&lt;SCRIPT&gt;alert('XSS')&lt;/SCRIPT&gt;\">"
        ].each do |value|
          it "should strip xss in meta tags: #{value[0..40]}" do
            result = sanitize_value(field, value)
            expect(result).not_to match(/javascript/i)
            expect(result).not_to match(/xss/i)
          end
        end

        it "should strip xss inside tags" do
          value = '<IMG """><SCRIPT>alert("XSS")</SCRIPT>">'
          result = sanitize_value(field, value)
          expect(result).not_to match(/script/i)
        end

        it "should strip script/xss tags" do
          value = '<SCRIPT/XSS SRC="http://ha.ckers.org/xss.js"></SCRIPT>'
          result = sanitize_value(field, value)
          expect(result).not_to match(/script/i)
          expect(result).not_to match(/xss/i)
          expect(result).not_to match(/ha.ckers.org/)
        end

        it "should strip script/src tags" do
          value = '<SCRIPT/SRC="http://ha.ckers.org/xss.js"></SCRIPT>'
          result = sanitize_value(field, value)
          expect(result).not_to match(/script/i)
          expect(result).not_to match(/xss/i)
          expect(result).not_to match(/ha.ckers.org/)
        end

        it "should strip xss in body background" do
          value = "<BODY BACKGROUND=\"javascript:alert('XSS')\">"
          result = sanitize_value(field, value)
          expect(result).not_to match(/xss/i)
        end

        [
          "<BODY ONLOAD=alert('XSS')>",
          '<BODY onload!#$%&()*~+-_.,:;?@[/|\]^`=alert("XSS")>'
        ].each do |value|
          it "should strip xss in body onload: #{value}" do
            result = sanitize_value(field, value)
            expect(result).not_to match(/xss/i)
            expect(result).not_to match(/onload/i)
          end
        end

        it "should strip style tag" do
          value = "<STYLE>@import'http://ha.ckers.org/xss.css';</STYLE>"
          result = sanitize_value(field, value)
          expect(result).not_to match(/style/i)
        end

        it "should handle lone @imports" do
          value = "@import'http://ha.ckers.org/xss.css';"
          result = sanitize_value(field, value)
          expect(result).not_to match(/style/i)
          expect(result).to match(/@import/i)
        end

        it "should handle lone borked @imports" do
          value = "@im\port'\ja\vasc\ript:alert(\"XSS\")';"
          result = sanitize_value(field, value)
          expect(result).not_to match(/style/i)
          expect(result).to match(/@import/i)
        end

        it "should strip javascript from img dynsrc" do
          value = "<IMG DYNSRC=\"javascript:alert('XSS')\">"
          result = sanitize_value(field, value)
          expect(result).not_to match(/javascript/i)
          expect(result).not_to match(/xss/i)
        end

        it "should strip javascript from img lowsrc" do
          value = "<IMG DYNSRC=\"javascript:alert('XSS')\">"
          result = sanitize_value(field, value)
          expect(result).not_to match(/javascript/i)
          expect(result).not_to match(/xss/i)
        end

        it "should strip javascript from bgsound src" do
          value = "<BGSOUND SRC=\"javascript:alert('XSS');\">"
          result = sanitize_value(field, value)
          expect(result).not_to match(/javascript/i)
          expect(result).not_to match(/xss/i)
        end

        it "should strip javascript from br size" do
          value = "<BR SIZE=\"&{alert('XSS')}\">"
          result = sanitize_value(field, value)
          expect(result).not_to match(/xss/i)
        end

        it "should strip javascript from link href" do
          value = "<LINK REL=\"stylesheet\" HREF=\"javascript:alert('XSS');\">"
          result = sanitize_value(field, value)
          expect(result).not_to match(/javascript/i)
          expect(result).not_to match(/xss/i)
        end

        it "should strip xss from link href" do
          value = '<LINK REL="stylesheet" HREF="http://ha.ckers.org/xss.css">'
          result = sanitize_value(field, value)
          expect(result).not_to match(/ha.ckers.org/i)
          expect(result).not_to match(/xss/i)
        end

        it "should strip namespace tags" do
          value = '<HTML xmlns:xss><?import namespace="xss" implementation="http://ha.ckers.org/xss.htc"><xss:xss>Blah</xss:xss></HTML>'
          result = sanitize_value(field, value)
          expect(result).not_to match(/xss/i)
          expect(result).not_to match(/ha.ckers.org/i)
          expect(result).to match(/Blah/)
        end

        it "should strip javascript in style=background-image" do
          value = "<span style=background-image:url(\"javascript:alert('XSS')\");>Text</span>"
          result = sanitize_value(field, value)
          expect(result).not_to match(/xss/i)
          expect(result).not_to match(/javascript/i)
        end

        it "should strip script tags" do
          value = "';alert(String.fromCharCode(88,83,83))//\\';alert(String.fromCharCode(88,83,83))//\";alert(String.fromCharCode(88,83,83))//\\\";alert(String.fromCharCode(88,83,83))//--></SCRIPT>\">'><SCRIPT>alert(String.fromCharCode(88,83,83))</SCRIPT>"
          result = sanitize_value(field, value)
          expect(result).not_to match(/xss/i)
          expect(result).not_to match(/javascript/i)
        end

        [
          "<!--#exec cmd=\"/bin/echo '<SCR'\"-->",
          "<!--#exec cmd=\"/bin/echo 'IPT SRC=http://ha.ckers.org/xss.js></SCRIPT>'\"-->"
        ].each do |value|
          it "should strip #exec: #{value[0..40]}" do
            result = sanitize_value(field, value)
            expect(result).to eq("")
          end
        end

        # TODO: Ones with all types of quote marks:
        # "<IMG SRC=`javascript:alert("RSnake says, 'XSS'")`>"

        it "should escape ampersands" do
          result = sanitize_value(field, "& &amp;")
          expect(result).to match(/&amp; &amp;/)
        end

        context "add rel=nofollow to all links to defeat spammers' SEO plans" do
          it "adds rel=nofollow to links with no rel attribute" do
            result = sanitize_value(field, "<a href='foo'>Foo</a>")
            expect(result).to eq("<p>\n  <a href=\"foo\" rel=\"nofollow\">Foo</a>\n</p>")
          end

          it "adds rel=nofollow to links with a rel attribute" do
            result = sanitize_value(field, "<a href='foo' rel='help'>Foo</a>")
            expect(result).to eq("<p>\n  <a href=\"foo\" rel=\"nofollow\">Foo</a>\n</p>")
          end
        end

        # These are from https://github.com/rgrove/sanitize/commit/a11498de9e283cd457b35ee252983662f7452aa9
        it 'should not preserve the content of removed `math` elements' do
          content = sanitize_value(field, '<math>hello! <script>alert(0)</script></math>')
          expect(content).to eq("")
        end

        it 'should not preserve the content of removed `plaintext` elements' do
          content = sanitize_value(field, '<plaintext>hello! <script>alert(0)</script>')
          expect(content).to eq("")
        end

        it 'should not preserve the content of removed `svg` elements' do
          content = sanitize_value(field, '<svg>hello! <script>alert(0)</script></svg>')
          expect(content).to eq("")
        end

        it 'should not preserve the content of removed `xmp` elements' do
          content = sanitize_value(field, '<xmp>hello! <script>alert(0)</script></xmp>')
          expect(content).to eq("")
        end

        # https://github.com/rgrove/sanitize/security/advisories/GHSA-p4x4-rw2p-8j8m
        describe 'foreign content bypass in relaxed config' do
          it 'prevents a sanitization bypass via carefully crafted foreign content' do
            %w[iframe noembed noframes noscript plaintext script style xmp].each do |tag_name|
              content = sanitize_value(field, "<math><#{tag_name}>/*&lt;/#{tag_name}&gt;&lt;img src onerror=alert(1)>*/")
              expect(content).to eq("")

              content = sanitize_value(field, "<svg><#{tag_name}>/*&lt;/#{tag_name}&gt;&lt;img src onerror=alert(1)>*/")
              expect(content).to eq("")
            end
          end
        end
      end
    end

    ArchiveConfig.FIELDS_ALLOWING_HTML.each do |field|
      it "preserves ruby-annotated HTML in #{field}" do
        result = sanitize_value(field, "<ruby>BigText<rp>(</rp><rt>small_text</rt><rp>)</rp></ruby>")
        expect(result).to include("<ruby>BigText<rp>(</rp><rt>small_text</rt><rp>)</rp></ruby>")
      end

      it "preserves ruby-annotated HTML without rp in #{field}" do
        result = sanitize_value(field, "<ruby>BigText<rt>small_text</rt></ruby>")
        expect(result).to include("<ruby>BigText<rt>small_text</rt></ruby>")
      end

      it "transforms open attribute's value when present on details element in #{field}" do
        html = <<~HTML
          <details open="false">
            <summary>Automated Status: Operational</summary>
            <p>Velocity: 12m/s</p>
            <p>Direction: North</p>
          </details>
        HTML

        result = sanitize_value(field, html)
        doc = Nokogiri::HTML.fragment(result)

        expect(doc.xpath("./details/@open").to_s.strip).to eq("open")
      end

      it "does not require details to have an 'open' attribute in #{field}" do
        html = <<~HTML
          <details>
            <summary>Automated Status: Operational</summary>
            <p>Velocity: 12m/s</p>
            <p>Direction: North</p>
          </details>
        HTML

        result = sanitize_value(field, html)
        doc = Nokogiri::HTML.fragment(result)

        expect(doc.xpath("./details[@open]")).to be_empty
      end
    end
  end

  describe "fix_bad_characters" do
    it "should not touch normal text" do
      expect(fix_bad_characters("normal text")).to eq("normal text")
    end

    it "should not touch normal text with valid unicode chars" do
      expect(fix_bad_characters("„‚nörmäl’—téxt‘“")).to eq("„‚nörmäl’—téxt‘“")
    end

    it "should remove invalid unicode chars" do
      bad_string = [65, 150, 65].pack("C*")  # => "A\226A"
      expect(fix_bad_characters(bad_string)).to eq("AA")
    end

    it "should escape <3" do
      expect(fix_bad_characters("normal <3 text")).to eq("normal &lt;3 text")
    end

    it "should convert \\r\\n to \\n" do
      expect(fix_bad_characters("normal\r\ntext")).to eq("normal\ntext")
    end

    it "should remove the spacer" do
      expect(fix_bad_characters("A____spacer____A")).to eq("AA")
    end

    it "should remove unicode chars in the 'other, format' category" do
      expect(fix_bad_characters("A\xE2\x81\xA0A")).to eq("AA")
    end
  end

  describe "add_paragraphs_to_text" do
    %w(a abbr acronym address).each do |tag|
      it "should not add extraneous paragraph breaks after #{tag} tags" do
        result = add_paragraphs_to_text("<#{tag}>quack</#{tag}> quack")
        doc = Nokogiri::HTML.fragment(result)
        expect(doc.xpath(".//p").size).to eq(1)
        expect(doc.xpath(".//br")).to be_empty
      end
    end

    it "leaves audio tags alone" do
      html = "<audio><source>\n</audio>"
      result = add_paragraphs_to_text(html)
      expect(result).not_to match("<p>")
      expect(result).not_to match("<br")
    end

    it "leaves video tags alone" do
      html = "<video><track>\n</video>"
      result = add_paragraphs_to_text(html)
      expect(result).not_to match("<p>")
      expect(result).not_to match("<br")
    end

    it "doesn't break links with images inside them" do
      result = add_paragraphs_to_text("<a href='/users/name'><img src='/icon.png'>name</a>")
      doc = Nokogiri::HTML.fragment(result)
      expect(doc.xpath("./p/a/img").size).to eq(1)
    end

    it "should not convert linebreaks after p tags" do
      result = add_paragraphs_to_text("<p>A</p>\n<p>B</p>\n\n<p>C</p>\n\n\n")
      doc = Nokogiri::HTML.fragment(result)
      expect(doc.xpath(".//p").size).to eq(3)
      expect(doc.xpath(".//br")).to be_empty
    end

    %w(dl h1 h2 h3 h4 h5 h6 ol pre table ul).each do |tag|
      it "should not convert linebreaks after #{tag} tags" do
        result = add_paragraphs_to_text("<#{tag}>A</#{tag}>\n<#{tag}>B</#{tag}>\n\n<#{tag}>C</#{tag}>\n\n\n")
        doc = Nokogiri::HTML.fragment(result)
        expect(doc.xpath(".//p").size).to eq(0)
        expect(doc.xpath(".//br")).to be_empty
      end
    end

    %w(blockquote center div).each do |tag|
      it "should not convert linebreaks after #{tag} tags" do
        result = add_paragraphs_to_text("<#{tag}>A</#{tag}>\n<#{tag}>B</#{tag}>\n\n<#{tag}>C</#{tag}>\n\n\n")
        doc = Nokogiri::HTML.fragment(result)
        expect(doc.xpath(".//p").size).to eq(3)
        expect(doc.xpath(".//br")).to be_empty
      end
    end

    it "should not convert linebreaks after br tags" do
      result = add_paragraphs_to_text("A<br>B<br>\n\nC<br>\n\n\n")
      doc = Nokogiri::HTML.fragment(result)
      expect(doc.xpath(".//p").size).to eq(1)
      expect(doc.xpath(".//br").size).to eq(3)
    end

    it "should not convert linebreaks after hr tags" do
      result = add_paragraphs_to_text("A<hr>B<hr>\n\nC<hr>\n\n\n")
      doc = Nokogiri::HTML.fragment(result)
      expect(doc.xpath(".//p").size).to eq(3)
      expect(doc.xpath(".//br")).to be_empty
    end

    %w[figure dl h1 h2 h3 h4 h5 h6 ol pre summary table ul].each do |tag|
      it "does not wrap #{tag} in p tags" do
        result = add_paragraphs_to_text("aa <#{tag}>foo</#{tag}> bb")
        doc = Nokogiri::HTML.fragment(result)
        expect(doc.xpath(".//p").size).to eq(2)
        expect(doc.xpath(".//#{tag}").children.to_s.strip).to eq("foo")
      end
    end

    it "does not wrap details in p tags" do
      html = <<~HTML
        aa

        <details>
          <summary>Automated Status: Operational</summary>
          <p>Velocity: 12m/s</p>
          <p>Direction: North</p>
        </details>

        bb
      HTML

      result = add_paragraphs_to_text(html)
      doc = Nokogiri::HTML.fragment(result)

      # aa, velocity..., direction..., bb
      expect(doc.xpath(".//p").size).to eq(4)
      expect(doc.xpath("./p/details").size).to eq(0)
      expect(doc.xpath("./details/p").size).to eq(2)
    end

    ["ol", "ul"].each do |tag|
      it "should not convert linebreaks inside #{tag} lists" do
        html = """
        <#{tag}>
          <li>A</li>
          <li>B</li>
        </#{tag}>
        """

        result = add_paragraphs_to_text(html)
        doc = Nokogiri::HTML.fragment(result)
        expect(doc.xpath("./#{tag}/li[1]").children.to_s.strip).to eq("A")
        expect(doc.xpath("./#{tag}/li[2]").children.to_s.strip).to eq("B")
        expect(doc.xpath(".//br")).to be_empty
      end
    end

    it "should not convert linebreaks inside tables" do
      html = """
      <table>
        <tr>
          <th>A</th>
          <th>B</th>
        </tr>
        <tr>
          <td>C</td>
          <td>D</td>
        </tr>
      </table>
      """

      result = add_paragraphs_to_text(html)
      doc = Nokogiri::HTML.fragment(result)
      expect(doc.xpath("./table/tr[1]/th[1]").children.to_s.strip).to eq("A")
      expect(doc.xpath("./table/tr[1]/th[2]").children.to_s.strip).to eq("B")
      expect(doc.xpath("./table/tr[2]/td[1]").children.to_s.strip).to eq("C")
      expect(doc.xpath("./table/tr[2]/td[2]").children.to_s.strip).to eq("D")
      expect(doc.xpath(".//br")).to be_empty
    end

    it "should not convert linebreaks inside definition lists" do
      html = """
      <dl>
        <dt>A</dt>
        <dd>aaa</dd>
        <dt>B</dt>
        <dd>bbb</dd>
      </dl>
      """

      result = add_paragraphs_to_text(html)
      doc = Nokogiri::HTML.fragment(result)
      expect(doc.xpath("./dl/dt[1]").children.to_s.strip).to eq("A")
      expect(doc.xpath("./dl/dd[1]").children.to_s.strip).to eq("aaa")
      expect(doc.xpath("./dl/dt[2]").children.to_s.strip).to eq("B")
      expect(doc.xpath("./dl/dd[2]").children.to_s.strip).to eq("bbb")
      expect(doc.xpath(".//br")).to be_empty
    end

    it "does not add paragraphs inside summary" do
      html = <<~HTML
        <details>
          <summary>
            Automated
          
            Status: 
            
            Operational
          </summary>
          <p>Velocity: 12m/s</p>
          <p>Direction: North</p>
        </details>
      HTML

      result = add_paragraphs_to_text(html)
      doc = Nokogiri::HTML.fragment(result)

      expect(doc.xpath("./summary/p")).to be_empty
    end

    it "does not add paragraphs inside figure" do
      html = <<~HTML
        <figure>

          <img src="http://example.com/Camera-icon.svg" alt="camera icon">

          <img src="http://example.com/Hand-icon.svg" alt="hand icon">

        </figure>
      HTML

      result = add_paragraphs_to_text(html)
      doc = Nokogiri::HTML.fragment(result)

      expect(doc.xpath("./figure/p")).to be_empty
    end

    it "allows alt and title attributes on elements inside figure" do
      html = <<~HTML
        <figure>
          <img src="http://example.com/Camera-icon.svg" alt="camera icon">
          <figcaption title="here is title">Take picture here</figcaption>
        </figure>
      HTML

      result = add_paragraphs_to_text(html)
      doc = Nokogiri::HTML.fragment(result)

      expect(doc.xpath("./figure/img/@alt").to_s.strip).to eq("camera icon")
      expect(doc.xpath("./figure/figcaption/@title").to_s.strip).to eq("here is title")
    end

    it "allows other HTML elements inside figcaption" do
      html = <<~HTML
        <figure>
          <img src="http://example.com/Camera-icon.svg">
          <figcaption><em>Take picture <a href="http://example.com/link">here</a></em></figcaption>
        </figure>
      HTML

      result = add_paragraphs_to_text(html)
      doc = Nokogiri::HTML.fragment(result)

      expect(doc.xpath("./figure/figcaption/em/text()").to_s.strip).to eq("Take picture")
      expect(doc.xpath("./figure/figcaption/em/a/text()").to_s.strip).to eq("here")
      expect(doc.xpath("./figure/figcaption/em/a/@href").to_s.strip).to eq("http://example.com/link")
    end

    it "allows other HTML elements inside summary" do
      html = <<~HTML
        <details>
          <summary><em>Automated Status: <a href="http://example.com/link">Operational</a></em></summary>
          <p>Velocity: 12m/s</p>
          <p>Direction: North</p>
        </details>
      HTML

      result = add_paragraphs_to_text(html)
      doc = Nokogiri::HTML.fragment(result)

      expect(doc.xpath("./details/summary/em/text()").to_s.strip).to eq("Automated Status:")
      expect(doc.xpath("./details/summary/em/a/text()").to_s.strip).to eq("Operational")
      expect(doc.xpath("./details/summary/em/a/@href").to_s.strip).to eq("http://example.com/link")
    end

    %w(address h1 h2 h3 h4 h5 h6 p pre).each do |tag|
      it "should not wrap in p and not convert linebreaks inside #{tag} tags" do
        result = add_paragraphs_to_text("<#{tag}>A\nB\n\nC\n\n\nD</#{tag}>")
        doc = Nokogiri::HTML.fragment(result)
        expect(doc.xpath("./#{tag}[1]").children.to_s.strip).to eq("A\nB\n\nC\n\n\nD")
      end
    end

    %w(a abbr acronym).each do |tag|
      it "should wrap in p and not convert linebreaks inside #{tag} tags" do
        result = add_paragraphs_to_text("<#{tag}>A\nB\n\nC\n\n\nD</#{tag}>")
        doc = Nokogiri::HTML.fragment(result)
        expect(doc.xpath("./p/#{tag}[1]").children.to_s.strip).to eq("A\nB\n\nC\n\n\nD")
      end
    end

    it "should wrap plain text in p tags" do
      result = add_paragraphs_to_text("some text")
      doc = Nokogiri::HTML.fragment(result)
      expect(doc.xpath("./p[1]").children.to_s.strip).to eq("some text")
    end

    it "should convert single linebreak to br" do
      result = add_paragraphs_to_text("some\ntext")
      doc = Nokogiri::HTML.fragment(result)
      expect(doc.xpath("./p[1]").children.to_s.strip).to match(/some<br\/?>text/)
    end

    it "should convert double linebreaks to paragraph break" do
      result = add_paragraphs_to_text("some\n\ntext")
      doc = Nokogiri::HTML.fragment(result)
      expect(doc.xpath("./p[1]").children.to_s.strip).to eq("some")
      expect(doc.xpath("./p[2]").children.to_s.strip).to eq("text")
    end

    it "should convert triple linebreaks into blank paragraph" do
      result = add_paragraphs_to_text("some\n\n\ntext")
      doc = Nokogiri::HTML.fragment(result)
      expect(doc.xpath("./p[1]").children.to_s.strip).to eq("some")
      expect(doc.xpath("./p[2]").children.to_s.strip.ord).to eq(160)
      expect(doc.xpath("./p[3]").children.to_s.strip).to eq("text")
    end

    it "should convert double br tags into paragraph break" do
      result = add_paragraphs_to_text("some<br/><br/>text")
      doc = Nokogiri::HTML.fragment(result)
      expect(doc.xpath("./p[1]").children.to_s.strip).to eq("some")
      expect(doc.xpath("./p[2]").children.to_s.strip).to eq("text")
    end

    it "should convert triple br tags into blank paragraph" do
      result = add_paragraphs_to_text("some<br/><br/><br/>text")
      doc = Nokogiri::HTML.fragment(result)
      expect(doc.xpath("./p[1]").children.to_s.strip).to eq("some")
      expect(doc.xpath("./p[2]").children.to_s.strip.ord).to eq(160)
      expect(doc.xpath("./p[3]").children.to_s.strip).to eq("text")
    end

    it "should not convert double br tags inside p tags" do
      result = add_paragraphs_to_text("<p>some<br/>\n<br/>text</p>")
      doc = Nokogiri::HTML.fragment(result)
      expect(doc.xpath(".//p").size).to eq(1)
      expect(doc.xpath(".//br").size).to eq(2)
    end

    it "should not convert triple br tags inside p tags" do
      result = add_paragraphs_to_text("<p>some<br/>\n<br/>\n<br/>text</p>")
      doc = Nokogiri::HTML.fragment(result)
      expect(doc.xpath(".//p").size).to eq(1)
      expect(doc.xpath(".//br").size).to eq(3)
    end

    %w(b big cite code del dfn em i ins kbd q s samp
     small span strike strong sub sup tt u var).each do |tag|
      it "should handle #{tag} inline tags spanning double line breaks" do
        result = add_paragraphs_to_text("<#{tag}>some\n\ntext</#{tag}>")
        doc = Nokogiri::HTML.fragment(result)
        expect(doc.xpath("./p[1]/#{tag}").children.to_s.strip).to eq("some")
        expect(doc.xpath("./p[2]/#{tag}").children.to_s.strip).to eq("text")
      end

      it "handles #{tag} with an unclosed br tag in it" do
        result = add_paragraphs_to_text("<#{tag}>some<br>text</#{tag}>")
        doc = Nokogiri::HTML.fragment(result)
        expect(doc.xpath("./p[1]/#{tag}[1]").children.to_s.strip).to match(%r{some<br/?>text})
      end
    end

    it "should handle nested inline tags spanning double line breaks" do
      result = add_paragraphs_to_text("<i>have <b>some\n\ntext</b> yay</i>")
      doc = Nokogiri::HTML.fragment(result)
      expect(doc.xpath("./p[1]/i").children.to_s.strip).to match(/\Ahave/)
      expect(doc.xpath("./p[1]/i/b").children.to_s.strip).to eq("some")
      expect(doc.xpath("./p[2]/i/b").children.to_s.strip).to eq("text")
      expect(doc.xpath("./p[2]/i").children.to_s.strip).to match(/ yay\Z/)
    end

    it "should handle nested inline tags spanning double line breaks" do
      result = add_paragraphs_to_text("have <em>some\n\ntext</em> yay")
      doc = Nokogiri::HTML.fragment(result)
      expect(doc.xpath("./p[1]").children.to_s.strip).to match(/\Ahave/)
      expect(doc.xpath("./p[1]/em").children.to_s.strip).to eq("some")
      expect(doc.xpath("./p[2]/em").children.to_s.strip).to eq("text")
      expect(doc.xpath("./p[2]").children.to_s.strip).to match(/ yay\Z/)
    end

    %w[blockquote center div details].each do |tag|
      it "should convert double linebreaks inside #{tag} tag" do
        result = add_paragraphs_to_text("<#{tag}>some\n\ntext</#{tag}>")
        doc = Nokogiri::HTML.fragment(result)
        expect(doc.xpath("./#{tag}/p[1]").children.to_s.strip).to eq("some")
        expect(doc.xpath("./#{tag}/p[2]").children.to_s.strip).to eq("text")
      end

      it "doesn't insert extra <p></p> tags before the #{tag} tag" do
        result = add_paragraphs_to_text("<p>before</p><#{tag}><p>during</p></#{tag}>")
        doc = Nokogiri::HTML.fragment(result)
        expect(doc.xpath(".//p").size).to eq(2)
        expect(doc.xpath("./p[1]").children.to_s.strip).to eq("before")
        expect(doc.xpath("./#{tag}/p[1]").children.to_s.strip).to eq("during")
      end

      it "creates a paragraph for text immediately following the #{tag} tag" do
        result = add_paragraphs_to_text("<#{tag}>during</#{tag}>after")
        doc = Nokogiri::HTML.fragment(result)
        expect(doc.xpath(".//p").size).to eq(2)
        expect(doc.xpath("./#{tag}/p[1]").children.to_s.strip).to eq("during")
        expect(doc.xpath("./p[1]").children.to_s.strip).to eq("after")
      end
    end

    it "should wrap text in p before and after existing p tag" do
      result = add_paragraphs_to_text("boom\n\n<p>da</p>\n\nyadda")
      doc = Nokogiri::HTML.fragment(result)
      expect(doc.xpath("./p[1]").children.to_s.strip).to eq("boom")
      expect(doc.xpath("./p[2]").children.to_s.strip).to eq("da")
      expect(doc.xpath("./p[3]").children.to_s.strip).to eq("yadda")
    end

    it "wraps ruby-annotated text in p tags" do
      result = add_paragraphs_to_text("text with <ruby>ルビ<rp> (</rp><rt>RUBY</rt><rp>)</rp></ruby>")
      doc = Nokogiri::HTML.fragment(result)
      expect(doc.xpath("./p[1]").children.to_s.strip).to eq("text with <ruby>ルビ<rp> (</rp><rt>RUBY</rt><rp>)</rp></ruby>")
    end

    it "should keep attributes of block elements" do
      result = add_paragraphs_to_text("<div class='foo'>some\n\ntext</div>")
      doc = Nokogiri::HTML.fragment(result)
      expect(doc.xpath("./div[@class='foo']/p[1]").children.to_s.strip).to eq("some")
      expect(doc.xpath("./div[@class='foo']/p[2]").children.to_s.strip).to eq("text")
    end

    it "should keep attributes of inline elements across paragraphs" do
      result = add_paragraphs_to_text("<span class='foo'>some\n\ntext</span>")
      doc = Nokogiri::HTML.fragment(result)
      expect(doc.xpath("./p[1]/span[@class='foo']").children.to_s.strip).to eq("some")
      expect(doc.xpath("./p[2]/span[@class='foo']").children.to_s.strip).to eq("text")
    end

    it "should handle two classes" do
      result = add_paragraphs_to_text('<p class="foo bar">foobar</p>')
      doc = Nokogiri::HTML.fragment(result)
      expect(doc.xpath("./p[contains(@class, 'foo')]").children.to_s.strip).to eq("foobar")
      expect(doc.xpath("./p[contains(@class, 'bar')]").children.to_s.strip).to eq("foobar")
    end

    it "closes unclosed tag within other tag" do
      html = "<strong><em>unclosed</strong>"
      doc = Nokogiri::HTML.fragment(add_paragraphs_to_text(html))
      expect(doc.xpath("./p/strong/em").children.to_s.strip).to eq("unclosed")
    end

    it "closes unclosed rt tags" do
      html = "<ruby>big text<rt>small text</ruby>"
      result = add_paragraphs_to_text(html)
      expect(result).to include("<ruby>big text<rt>small text</rt></ruby>")
    end

    it "closes unclosed rp tag" do
      html = "<ruby>big text<rp>(</rp><rt>small text</rt><rp>)</ruby>"
      result = add_paragraphs_to_text(html)
      expect(result).to include("<ruby>big text<rp>(</rp><rt>small text</rt><rp>)</rp></ruby>")
    end

    it "should re-nest mis-nested tags" do
      html = "some <em><strong>text</em></strong>"
      doc = Nokogiri::HTML.fragment(add_paragraphs_to_text(html))
      expect(doc.xpath("./p[1]/em/strong").children.to_s.strip).to eq("text")
    end

    it "should handle mixed uppercase/lowecase html tags" do
      result = add_paragraphs_to_text("<em>mixed</EM> <EM>stuff</em>")
      doc = Nokogiri::HTML.fragment(result)
      expect(doc.xpath("./p[1]/em[1]").children.to_s.strip).to eq("mixed")
      expect(doc.xpath("./p[1]/em[2]").children.to_s.strip).to eq("stuff")
    end

    %w(b big cite code del dfn em i ins kbd q s samp
       small span strike strong sub sup tt u var).each do |tag|
      it "should wrap consecutive #{tag} inline tags in one paragraph " do
        result = add_paragraphs_to_text("<#{tag}>hey</#{tag}> <#{tag}>ho</#{tag}>")
        doc = Nokogiri::HTML.fragment(result)
        expect(doc.xpath("./p[1]/#{tag}[1]").children.to_s.strip).to eq("hey")
        expect(doc.xpath("./p[1]/#{tag}[2]").children.to_s.strip).to eq("ho")
        expect(doc.xpath("./p[1]/text()").to_s).to eq(" ")
      end
    end

    %w(&gt; &lt; &amp;).each do |entity|
      it "should handle #{entity}" do
        result = add_paragraphs_to_text("#{entity}")
        doc = Nokogiri::HTML.fragment(result)
        expect(doc.xpath("./p[1]").children.to_s.strip).to eq("#{entity}")
      end
    end

    it "should not add empty p tags" do
      result = add_paragraphs_to_text("A<p>B</p><p>C</p>")
      doc = Nokogiri::HTML.fragment(result)
      expect(doc.xpath("./p").size).to eq(3)
      expect(doc.xpath("./p[1]").children.to_s.strip).to eq("A")
      expect(doc.xpath("./p[2]").children.to_s.strip).to eq("B")
      expect(doc.xpath("./p[3]").children.to_s.strip).to eq("C")
    end

    it "should not leave p inside i" do
      result = add_paragraphs_to_text("<i><p>foo</p><p>bar</p></i>")
      doc = Nokogiri::HTML.fragment(result)
      expect(doc.xpath(".//i/p")).to be_empty
    end

    it "should deal with br tags at the beginning" do
      result = add_paragraphs_to_text("</br>text")
      doc = Nokogiri::HTML.fragment(result)
      expect(doc.xpath(".//p").children.to_s.strip).to eq("text")
    end

    it "should handle table tags that don't need closing" do
      html = """
      <table>
        <colgroup align=\"left\"><col width=\"20\"></colgroup>
        <colgroup align=\"right\">
        <tr>
          <th>A</th>
          <th>B</th>
        </tr>
        <tr>
          <td>C</td>
          <td>D</td>
        </tr>
      </table>
     """
      result = add_paragraphs_to_text(html)
      doc = Nokogiri::HTML.fragment(result)
      expect(doc.xpath("./table/colgroup[@align='left']/col[@width='20']").size).to eq(1)
      expect(doc.xpath("./table/colgroup[@align='right']").size).to eq(1)
      expect(doc.xpath("./table/tr[1]/th[1]").children.to_s.strip).to eq("A")
      expect(doc.xpath("./table/tr[1]/th[2]").children.to_s.strip).to eq("B")
      expect(doc.xpath("./table/tr[2]/td[1]").children.to_s.strip).to eq("C")
      expect(doc.xpath("./table/tr[2]/td[2]").children.to_s.strip).to eq("D")
    end

    it "doesn't break when an attribute includes a single quote" do
      result = add_paragraphs_to_text(<<~HTML)
        <span title="Don't stop me now">Cause I'm having a good time</span>
      HTML
      doc = Nokogiri::HTML.fragment(result)
      node = doc.xpath(".//span").first
      expect(node.attribute("title").value).to eq("Don't stop me now")
    end

    it "doesn't unescape escaped text when processing newlines" do
      result = add_paragraphs_to_text(<<~HTML.strip)
        &lt;span&gt;

        &lt;div&gt;
      HTML
      doc = Nokogiri::HTML.fragment(result)
      expect(doc.xpath("./p[1]").children.to_s.strip).to eq("&lt;span&gt;")
      expect(doc.xpath("./p[2]").children.to_s.strip).to eq("&lt;div&gt;")
    end

    it "should fail gracefully for missing ending quotation marks" do
      pending "Opened enhancement request with Nokogiri"
      result = add_paragraphs_to_text("<strong><a href='ao3.org>mylink</a></strong>")
      doc = Nokogiri::HTML.fragment(result)
      node = doc.xpath(".//a").first
      expect(node.attribute("href").value).not_to match(/strong/)
      expect(node.text.strip).to eq("mylink")
    end

    it "should fail gracefully for missing starting quotation marks" do
      result = add_paragraphs_to_text('<strong><a href=ao3.org">mylink</a></strong>')
      doc = Nokogiri::HTML.fragment(result)
      node = doc.xpath(".//a").first
      expect(node.attribute("href").value).to eq('ao3.org"')
      expect(node.text.strip).to eq("mylink")
    end
  end

  describe "add_break_between_paragraphs" do
    it "adds <br /> between paragraphs" do
      original = "<p>Hi!</p><p>I need more space.</p>"
      result = "<p>Hi!</p><br /><p>I need more space.</p>"
      expect(add_break_between_paragraphs(original)).to eq(result)
    end

    it "removes any blank spaces before, between, and after the paragraph marks" do
      original = "bla.  </p>   <p>   Bla"
      result = "bla.</p><br /><p>Bla"
      expect(add_break_between_paragraphs(original)).to eq(result)
    end
  end
end
