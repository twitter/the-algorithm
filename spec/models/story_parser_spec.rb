require 'spec_helper'
require 'webmock'

describe StoryParser do

  # Temporarily make the methods we want to test public
  before(:all) do
    class StoryParser
      public :get_source_if_known, :check_for_previous_import, :parse_common, :parse_author
    end
  end

  after(:all) do
    class StoryParser
      protected :get_source_if_known, :check_for_previous_import, :parse_common, :parse_author
    end
  end

  before(:each) do
    @sp = StoryParser.new
  end

  describe "get_source_if_known:" do

    describe "the SOURCE_FFNET pattern" do

      it "should match http://fanfiction.net" do
        url = "http://fanfiction.net"
        expect(@sp.get_source_if_known(StoryParser::CHAPTERED_STORY_LOCATIONS, url)).to eq("ffnet")
      end

      it "should match fanfiction.net" do
        url = "fanfiction.net"
        expect(@sp.get_source_if_known(StoryParser::CHAPTERED_STORY_LOCATIONS, url)).to eq("ffnet")
      end

      it "should match http://www.fanfiction.net" do
        url = "http://www.fanfiction.net"
        expect(@sp.get_source_if_known(StoryParser::CHAPTERED_STORY_LOCATIONS, url)).to eq("ffnet")
      end

      it "should match www.fanfiction.net" do
        url = "www.fanfiction.net"
        expect(@sp.get_source_if_known(StoryParser::CHAPTERED_STORY_LOCATIONS, url)).to  eq("ffnet")
      end

      it "should not match http://adultfanfiction.net" do
        url = "http://adultfanfiction.net"
        expect(@sp.get_source_if_known(StoryParser::CHAPTERED_STORY_LOCATIONS, url)).to be_nil
      end

      it "should not match adultfanfiction.net" do
        url = "adultfanfiction.net"
        expect(@sp.get_source_if_known(StoryParser::CHAPTERED_STORY_LOCATIONS, url)).to be_nil
      end

      it "should not match http://www.adultfanfiction.net" do
        url = "http://www.adultfanfiction.net"
        expect(@sp.get_source_if_known(StoryParser::CHAPTERED_STORY_LOCATIONS, url)).to be_nil
      end

      it "should not match www.adultfanfiction.net" do
        url = "www.adultfanfiction.net"
        expect(@sp.get_source_if_known(StoryParser::CHAPTERED_STORY_LOCATIONS, url)).to be_nil
      end
    end

    describe "the SOURCE_LJ pattern" do
      # SOURCE_LJ = '((live|dead|insane)?journal(fen)?\.com)|dreamwidth\.org'
      it "should match a regular domain on livejournal" do
        url = "http://mydomain.livejournal.com"
        expect(@sp.get_source_if_known(StoryParser::KNOWN_STORY_LOCATIONS, url)).to eq("lj")
      end

      it "should match a domain with underscores within on livejournal" do
        url = "http://my_domain.livejournal.com"
        expect(@sp.get_source_if_known(StoryParser::KNOWN_STORY_LOCATIONS, url)).to eq("lj")
      end

      it "should match a folder style link to an individual user on livejournal" do
        url = "http://www.livejournal.com/users/_underscore"
        expect(@sp.get_source_if_known(StoryParser::KNOWN_STORY_LOCATIONS, url)).to eq("lj")
      end

      it "should match a folder style link to a community on livejournal" do
        url = "http://www.livejournal.com/community/underscore_"
        expect(@sp.get_source_if_known(StoryParser::KNOWN_STORY_LOCATIONS, url)).to eq("lj")
      end

      it "should match a domain on dreamwidth" do
        url = "http://mydomain.dreamwidth.org"
        expect(@sp.get_source_if_known(StoryParser::KNOWN_STORY_LOCATIONS, url)).to eq("lj")
      end

      it "should match a domain on deadjournal" do
        url = "http://mydomain.deadjournal.com"
        expect(@sp.get_source_if_known(StoryParser::KNOWN_STORY_LOCATIONS, url)).to eq("lj")
      end

      it "should match a domain on insanejournal" do
        url = "http://mydomain.insanejournal.com"
        expect(@sp.get_source_if_known(StoryParser::KNOWN_STORY_LOCATIONS, url)).to eq("lj")
      end

      it "should match a folder style link to an individual user on journalfen" do
        url = "http://www.journalfen.net/users/username"
        expect(@sp.get_source_if_known(StoryParser::KNOWN_STORY_LOCATIONS, url)).to eq("lj")
      end
    end

    # TODO: KNOWN_STORY_PARSERS
  end

  describe "check_for_previous_import" do
    let(:location_with_www) { "http://www.testme.org/welcome_to_test_vale.html" }
    let(:location_no_www) { "http://testme.org/welcome_to_test_vale.html" }
    let(:location_partial_match) { "http://testme.org/welcome_to_test_vale/12345" }

    it "should recognise previously imported www. works" do
      @work = FactoryBot.create(:work, imported_from_url: location_with_www)

      expect { @sp.check_for_previous_import(location_no_www) }.to raise_exception(StoryParser::Error)
    end

    it "should recognise previously imported non-www. works" do
      @work = FactoryBot.create(:work, imported_from_url: location_no_www)

      expect { @sp.check_for_previous_import(location_with_www) }.to raise_exception(StoryParser::Error)
    end

    it "should not perform a partial match on work import locations" do
      @work = create(:work, imported_from_url: location_partial_match)

      expect { @sp.check_for_previous_import("http://testme.org/welcome_to_test_vale/123") }.to_not raise_exception
    end
  end

  context "#download_and_parse_chapters_into_story" do
    it "should set the work revision date to the date of the last chapter" do

      # Let the test get at external sites, but stub out anything containing "url1" and "url2"
      WebMock.allow_net_connect!
      WebMock.stub_request(:any, /url1/).
        to_return(status: 200, body: "Date: 2001-01-10 13:45\nstubbed response", headers: {})
      WebMock.stub_request(:any, /url2/).
        to_return(status: 200, body: "Date: 2001-01-22 12:56\nstubbed response", headers: {})

      storyparser_user = FactoryBot.create(:user)
      urls = %w(http://url1 http://url2)
      work = @sp.download_and_parse_chapters_into_story(urls, { pseuds: [storyparser_user.default_pseud], do_not_set_current_author: false })
      work.save
      actual_date = work.revised_at.to_date
      expected_date = Date.new(2001, 1, 22)
      expect(actual_date).to eq(expected_date)
    end
  end

  describe "#parse_common" do
    it "converts relative to absolute links" do
      # This one doesn't work because the sanitizer is converting the & to &amp;
      # ['http://foo.com/bar.html', 'search.php?here=is&a=query'] => 'http://foo.com/search.php?here=is&a=query',
      {
       ['http://foo.com/bar.html', 'thisdir.html'] => 'http://foo.com/thisdir.html',
       ['http://foo.com/bar.html?hello=foo', 'thisdir.html'] => 'http://foo.com/thisdir.html',
       ['http://foo.com/bar.html', './thisdir.html'] => 'http://foo.com/thisdir.html',
       ['http://foo.com/bar.html', 'img.jpg'] => 'http://foo.com/img.jpg',
       ['http://foo.com/bat/bar.html', '../updir.html'] => 'http://foo.com/updir.html',
       ['http://foo.com/bar.html', 'http://bar.com/foo.html'] => 'http://bar.com/foo.html',
       ['http://foo.com/bar.html', 'search.php?hereis=aquery'] => 'http://foo.com/search.php?hereis=aquery',
      }.each_pair do |input, output|
        location, href = input
        story_in = '<html><body><p>here is <a href="' + href + '">a link</a>.</p></body></html>'
        story_out = '<p>here is <a href="' + output + '" rel="nofollow">a link</a>.</p>'
        results = @sp.parse_common(story_in, location)
        expect(results[:chapter_attributes][:content]).to include(story_out)
      end
    end

    it "does NOT convert raw anchor links to absolute links" do
      location = "http://external_site"
      story_in = "<html><body><p><a href=#local>local href</p></body></html>"
      result = @sp.parse_common(story_in, location)
      expect(result[:chapter_attributes][:content]).not_to include(location)
      expect(result[:chapter_attributes][:content]).to include("#local")
    end
  end

  describe "#parse_author" do
    it "returns an external author name when a name and email are provided" do
      results = @sp.parse_author("", "Author Name", "author@example.com")
      expect(results.name).to eq("Author Name")
      expect(results.external_author.email).to eq("author@example.com")
    end

    it "raises an exception when the external author name is not provided" do
      expect {
        @sp.parse_author("", nil, "author@example.com")
      }.to raise_exception(StoryParser::Error) { |e| expect(e.message).to eq("No author name specified") }
    end

    it "raises an exception when the external author email is not provided" do
      expect {
        @sp.parse_author("", "Author Name", nil)
      }.to raise_exception(StoryParser::Error) { |e| expect(e.message).to eq("No author email specified") }
    end

    it "raises an exception when neither the external author name nor email is provided" do
      expect {
        @sp.parse_author("", nil, nil)
      }.to raise_exception(StoryParser::Error) { |e| expect(e.message).to eq("No author name specified\nNo author email specified") }
    end
  end

  # Let the test get at external sites, but stub out anything containing certain keywords
  def mock_external
    curly_quotes = "String with non-ASCII “Curly quotes” and apostrophes’"

    body = "
      Title: #{curly_quotes}
      Summary: #{curly_quotes}
      Fandom: #{curly_quotes}
      Rating: #{curly_quotes}
      Warnings: #{curly_quotes}
      Characters: #{curly_quotes}
      Pairing: Includes a character – that broke the importer
      Category: #{curly_quotes}
      Tags: #{curly_quotes}
      Author's notes: #{curly_quotes}

      stubbed response".gsub('      ', '')

    WebMock.allow_net_connect!

    WebMock.stub_request(:any, /ascii-8bit/).
      to_return(status: 200,
                body: body.force_encoding("ASCII-8BIT"),
                headers: {})

    WebMock.stub_request(:any, /utf-8/).
      to_return(status: 200,
                body: body,
                headers: {})

    WebMock.stub_request(:any, /win-1252/).
      to_return(status: 200,
                body: body.force_encoding("Windows-1252"),
                headers: {})

    WebMock.stub_request(:any, /non-sgml-character-number-3/).
      to_return(status: 200,
                body: "<body>\0When I get out of here</body>")
  end

  describe "Import" do
    before do
      mock_external
      @user = create(:user)
    end

    after do
      WebMock.reset!
    end

    it "should not throw an exception with non-ASCII characters in metadata fields" do
      urls = %w(http://ascii-8bit http://utf-8 http://win-1252)
      urls.each do |url|
        expect {
          @sp.download_and_parse_story(url, pseuds: [@user.default_pseud], do_not_set_current_author: false)
        }.to_not raise_exception
      end
    end

    it "ignores string terminators (AO3-2251)" do
      story = @sp.download_and_parse_story("http://non-sgml-character-number-3", pseuds: [@user.default_pseud])
      expect(story.chapters[0].content).to include("When I get out of here")
    end
  end
end
