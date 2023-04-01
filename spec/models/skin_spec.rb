require 'spec_helper'

describe Skin do
  describe "save" do
    before(:each) do
      @skin = Skin.new(title: "Test Skin")
    end

    # good css
    {
      "should allow through basic CSS including font family" =>
        "body { background-color: #ffffff;}
         h1 { font-family: 'Fertigo Pro', Verdana, serif; }",

      "should allow through valid CSS shorthand values" =>
        "body {background:#ffffff url('http://mywebsite.com/img_tree.png') no-repeat right top;}",

      "should allow images in the images directory" =>
        "body {background:#ffffff url('/images/img_tree.png') no-repeat right top;}",

      "should allow unquoted urls" =>
        "body {background:#ffffff url(http://mywebsite.com/images/img_tree.png) no-repeat right top;}",

      "should allow comments on their own lines" =>
        "/* starting comment */
        li {color: green;}
        /* middle comment */
        dd {color: blue;}
        /* end comment */",

      "should allow hsl(a) colours" =>
        "ol {color: hsl(180, 100%, 50%);}
        li {color: hsla(90, 30%, 70%, 50%);}",
      
      "should allow through border-radius (CSS3 property)" =>
        ".profile { border-radius: 5px }",

      ".should allow through specific border radius properties" =>
        ".profile { border-bottom-right-radius: 10px; }",

      "should allow through box-shadow (CSS3 property)" =>
        ".profile { box-shadow: 5px 5px 5px black; }",

      "should allow through alphabetic strings as keyword values even if they are not explicitly listed" =>
        "#main .navigation input { vertical-align: baseline; }
        #header .navigation li { text-transform: capitalize; }
        table { border-collapse: separate !important; }
        ",

      "should allow through valid CSS3 rules using quoted strings as content." =>
        "li.characters + li.freeforms:before {content: '||'}
        li.relationships + li.freeforms:before { content: 'Freeform: '; }
        li:before {content: url('http://foo.com/bullet.jpg')}",

      "allows whitelisted image extensions" =>
        ".a { background: url('http://example.com/i.jpg'); }
        .b { background: url('http://example.com/i.jpeg'); }
        .c { background: url('http://example.com/i.png'); }
        .d { background: url('http://example.com/i.gif'); }",

      "should allow through properties that are variations on the ones in the shorthand config list" =>
        "#main ul.sorting {
          background: rgba(120,120,120,1) 5%;
          -moz-border-radius:0.15em !important;
          border-color:rgba(86,86,86,0.75) !important;
          box-shadow:0 2px 5px rgba(0,0,0,0.5);
          float:none !important;
          text-align:center;
        }
        #main ul.sorting a {
          border-color:rgba(86,86,86,1) !important;
          color:rgba(231,231,231,1);
          text-shadow:-1px -1px 0 rgba(0,0,0,0.75)
        }
        ul.sorting  a:hover {
          background: rgba(71,71,71,1) 5% !important;
          color:rgba(254,254,254,1);
        }
        #main .navigation ul.sorting a:visited{
          color:rgba(254,254,254,1)
        }",

      "should allow through gradients, clip, scale, skew, translate, rotate" =>
        "#main ul.sorting {
        background:-moz-linear-gradient(bottom, rgba(120,120,120,1) 5%, rgba(94,94,94,1) 50%, rgba(108,108,108,1) 55%, rgba(137,137,137,1) 100%) ;
        }
        ul.sorting  a:hover {
        background:-webkit-linear-gradient(bottom, rgba(71,71,71,1) 5%, rgba(59,59,59,1) 50%, rgba(74,74,74,1) 55%, rgba(91,91,91,1) 100%) !important;
        }
        #main .clip {clip: rect(1em, 2em, 3em, 4em);}
        #main li.blurb:nth-child(2n), #main.works-show .meta, .thread .thread li.comment:nth-child(3n+1) {-moz-transform: rotate(-0.5deg);}
        #main .foo {-moz-transform:rotate(120deg); -moz-transform:skewx(25deg) translatex(150px);}
        #menu {
        	background: -webkit-gradient(linear, left bottom, left top, color-stop(0, rgb(82,82,82)), color-stop(1, rgb(125,124,125)));
                    	-webkit-box-shadow: 0 1px 2px #000;
                    	-webkit-border-radius:2px;
                    	-webkit-transition:text-shadow .7s ease-out, background .7s ease-out;
                    	-webkit-transform: scale(2.1) rotate(-90deg)
        }
        #main .rotatevert {transform: rotatey(180deg);}
        .rotatehoriz {transform: rotatex(50deg)}",

        "should allow multiple valid values for a single property" =>
        "#outer .actions a:hover,symbol .question:hover,.actions input:hover,#outer input[type=\"submit\"]:hover,button:hover,.actions label:hover
                { background:#ddd;
                background:-webkit-linear-gradient(top,#fafafa,#ddd);
                background:-moz-linear-gradient(top,#fafafa,#ddd);
                background:-ms-linear-gradient(top,#fafafa,#ddd);
                background:-o-linear-gradient(top,#fafafa,#ddd);
                background:linear-gradient(top,#fafafa,#ddd);
                color:#555 }",
 
        "saves box shadows with multiple shadows" =>
          "li { box-shadow: 5px 5px 5px black, inset 0 0 0 1px #dadada; }"
    }.each_pair do |condition, css|
      it condition do
        @skin.css = css
        expect(@skin.save).to be_truthy
      end
    end

    # bad bad bad css
    {
      "should not save garbage with braces" => "blhalkdfasd {ljaflkasjdflasd}",
      "should not save garbage with braces and colon" => "blhalkdfasd {ljaflkasjdflasd: }",
      "should not save garbage with invalid property" => "blhalkdfasd {ljaflkasjdflasd: aklsdfjsdf}",
      "should not save urls with xss" => "body {-moz-binding:url('http://ha.ckers.org/xssmoz.xml#xss')}",
      "should not save @font-face" => "@font-face { font-family: Delicious; src: url('Delicious-Roman.otf');}",
      "should not save @import" => "@import url('http://ha.ckers.org/xss.css');",
      "should not save src" => "body {border: src('http://foo.com/')}",
      "should not save url for font" => "body {font: url(http://foo.com/bar.png)}",
      "should not save htc urls" => "body {behavior: url(xss.htc);}",
      "should not save javascript in li" => "li {background-image: url(javascript:alert('XSS'));}",
      "should not save expression" => "div {width: expression(alert('XSS'));}",
      "should not save javascript with escaped quote" => "div {background-image: url(&#1;javascript:alert('XSS'))}",
      "should not save gradient with xss" => "div {background: -webkit-linear-gradient(url(xss.htc))}",
      "should not save dsf images" => "body {background: url(http://foo.com/bar.dsf)}",
      "should not save urls with invalid domain" => "body {background: url(http://foo.htc/bar.png)}",
      "should not save xss interrupted with comments" => "div {xss:expr/*XSS*/ession(alert('XSS'))}",
    }.each_pair do |condition, css|
      it condition do
        @skin.css = css
        expect(@skin.save).not_to be_truthy
        expect(@skin.errors[:base]).not_to be_empty
      end
    end

    it "should require a title" do
      @skin.title = ""
      expect(@skin.save).not_to be_truthy
      expect(@skin.errors[:title]).not_to be_empty
    end

    it "should have a unique title" do
      expect(@skin.save).to be_truthy
      skin2 = Skin.new(title: "Test Skin")
      expect(skin2.save).not_to be_truthy
      expect(skin2.errors[:title]).not_to be_empty
    end

    it "should require a preview image if public" do
      @skin.css = "body {background: #fff;}"
      @skin.public = true
      expect(@skin.save).not_to be_truthy
      expect(@skin.errors[:base]).not_to be_empty
      expect(@skin.errors[:base].join(' ').match(/upload a screencap/)).to be_truthy
    end

    context "when a media query is provided" do
      [
        "all",
        "screen",
        "handheld",
        "speech",
        "print",
        "braille",
        "embossed",
        "projection",
        "tty",
        "tv",
        "only screen and (max-width: 42em)",
        "only screen and (max-width: 62em)",
        "(prefers-color-scheme: dark)",
        "(prefers-color-scheme: light)"
      ].each do |media_query|
        it "allows #{media_query}" do
          @skin.media = [media_query]
          expect(@skin.save).to be_truthy
          expect(@skin.errors[:base]).to be_empty
        end
      end

      {
        "doesn't allow max-width that isn't whitelisted" => "only screen and (max-width: 1024px)",
        "doesn't allow media that isn't whitelisted" => "(min-aspect-ratio: 8/5)",
        "doesn't allow two whitelisted media combined with and instead of a comma" => "screen and (prefers-color-scheme: dark",
        "doesn't allow combination of whitelisted media and non-whitelisted media" => "(prefers-color-scheme: dark), (monochrome)"
      }.each_pair do |description, media_query|
        it description do
          @skin.media = [media_query]
          expect(@skin.save).not_to be_truthy
          expect(@skin.errors[:base]).not_to be_empty
        end
      end
    end

    it "should only allow valid roles" do
      @skin.role = "foobar"
      expect(@skin.save).not_to be_truthy
      expect(@skin.errors[:role]).not_to be_empty
      @skin.role = "override"
      expect(@skin.save).to be_truthy
      expect(@skin.errors[:role]).to be_empty
    end

    it "should only allow valid ie-only conditions" do
      @skin.ie_condition = "foobar"
      expect(@skin.save).not_to be_truthy
      expect(@skin.errors[:ie_condition]).not_to be_empty
      @skin.ie_condition = "IE8_or_lower"
      expect(@skin.save).to be_truthy
      expect(@skin.errors[:ie_condition]).to be_empty
    end
  end


  describe "use", default_skin: true do
    before do
      Skin.load_site_css
      Skin.set_default_to_current_version
    end

    let(:css) { "body {background: purple;}" }
    let(:skin) { Skin.create(title: "Test Skin", css: css) }
    let(:style) { skin.get_style }

    it "should have a valid style block" do
      expect(style).to match(%r{<style type="text/css" media="all">})
    end

    it "should include the css" do
      expect(style).to match(/background: purple;/)
    end

    it "should include links to the default archive skin" do
      expect(style).to match(%r{<link rel="stylesheet" type="text/css"})
    end
  end

  describe ".approved_or_owned_by", default_skin: true do
    let(:skin_owner) { FactoryBot.create(:user) }
    let(:random_user) { FactoryBot.create(:user) }

    before do
      create(:work_skin, :private, author: skin_owner, title: "Private Skin 1")
      create(:work_skin, :private, author: skin_owner, title: "Private Skin 2")
    end

    context 'no user argument given' do
      context 'User.current_user is nil' do
        it 'returns approved skins' do
          allow(User).to receive(:current_user).and_return(nil)
          expect(Skin.approved_or_owned_by.pluck(:title)).to eq(['Default'])
        end
      end

      context 'User.current_user is not nil' do
        context 'user does not own skins' do
          it 'returns approved skins' do
            allow(User).to receive(:current_user).and_return(random_user)
            expect(Skin.approved_or_owned_by.pluck(:title)).to eq(['Default'])
          end
        end

        context 'user owns skins' do
          it 'returns approved and owned skins' do
            allow(User).to receive(:current_user).and_return(skin_owner)
            expect(Skin.approved_or_owned_by.pluck(:title)).to eq(['Default', 'Private Skin 1', 'Private Skin 2'])
          end
        end
      end
    end

    context 'user argument is given' do
      context 'user is nil' do
        it 'returns approved skins' do
          expect(Skin.approved_or_owned_by(nil).pluck(:title)).to eq(['Default'])
        end
      end

      context 'user is not nil' do
        context 'user does not own skins' do
          it 'returns approved skins' do
            expect(Skin.approved_or_owned_by(random_user).pluck(:title)).to eq(['Default'])
          end
        end

        context 'user owns skins' do
          it 'returns approved and owned skins' do
            expect(Skin.approved_or_owned_by(skin_owner).pluck(:title)).to eq(['Default',
                                                                               'Private Skin 1',
                                                                               'Private Skin 2'])
          end
        end
      end
    end
  end

  describe ".approved_or_owned_by_any", default_skin: true do
    let(:users) { Array.new(3) { FactoryBot.create(:user) } }

    context 'users do not own skins' do
      it 'returns approved skins' do
        expect(Skin.approved_or_owned_by_any(users).pluck(:title)).to eq(['Default'])
      end
    end

    context 'users own skins' do
      before do
        create(:work_skin, :private, author: users[1], title: "User 2's First Skin")
        create(:work_skin, :private, author: users[1], title: "User 2's Second Skin")
        create(:work_skin, :private, author: users[2], title: "User 3's Skin")
        create(:work_skin, :private, title: "Unowned Private Skin")
      end

      it 'returns approved and owned skins' do
        expect(Skin.approved_or_owned_by_any(users).pluck(:title)).to eq(["Default",
                                                                          "User 2's First Skin",
                                                                          "User 2's Second Skin",
                                                                          "User 3's Skin"])
      end

      it 'does not return unassociated private work skins' do
        expect(Skin.approved_or_owned_by_any(users).pluck(:title)).not_to include(['Unowned Private Skin'])
      end
    end
  end
end

