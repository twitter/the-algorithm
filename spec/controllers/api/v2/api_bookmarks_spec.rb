require "spec_helper"
require "controllers/api/api_helper"

describe "API v2 BookmarksController", type: :request, bookmark_search: true do
  include ApiHelper

  bookmark = { id: "123",
               url: "http://example.com",
               author: "Thing",
               title: "Title Thing",
               summary: "<p>blah blah blah</p>",
               fandom_string: "Testing",
               rating_string: "General Audiences",
               category_string: "M/M",
               relationship_string: "Starsky/Hutch",
               character_string: "Starsky,hutch",
               bookmarker_notes: "<p>Notes</p>",
               tag_string: "youpi",
               collection_names: "",
               private: "0",
               rec: "0" }

  before do
    WebMock.stub_request(:any, "http://example.com")
  end

  after do
    WebMock.reset!
  end

  context "Valid API bookmark import" do
    let!(:archivist) { create(:archivist) }

    it "returns 200 OK when all bookmarks are created" do
      post "/api/v2/bookmarks",
           params: { archivist: archivist.login,
             bookmarks: [ bookmark ]
           }.to_json,
           headers: valid_headers
      assert_equal 200, response.status
    end

    it "returns 200 OK when no bookmarks are created" do
      post "/api/v2/bookmarks",
           params: { archivist: archivist.login,
             bookmarks: [ bookmark ]
           }.to_json,
           headers: valid_headers
      assert_equal 200, response.status
    end

    it "does not create duplicate bookmarks for the same archivist and external URL" do
      pseud_id = archivist.default_pseud_id
      post "/api/v2/bookmarks",
           params: { archivist: archivist.login,
             bookmarks: [ bookmark, bookmark ]
           }.to_json,
           headers: valid_headers
      bookmarks = Bookmark.where(pseud_id: pseud_id)
      assert_equal bookmarks.count, 1
    end

    it "passes back any original references unchanged" do
      post "/api/v2/bookmarks",
           params: { archivist: archivist.login,
             bookmarks: [ bookmark ]
           }.to_json,
           headers: valid_headers
      bookmark_response = JSON.parse(response.body, symbolize_names: true)[:bookmarks].first
      assert_equal "123", bookmark_response[:original_id], "Original reference should be passed back unchanged"
      assert_equal "http://example.com", bookmark_response[:original_url], "Original URL should be passed back unchanged"
    end

    it "returns the URL of the created bookmark" do
      pseud_id = archivist.default_pseud.id
      post "/api/v2/bookmarks",
           params: { archivist: archivist.login,
             bookmarks: [ bookmark ]
           }.to_json,
           headers: valid_headers
      first_bookmark = Bookmark.where(pseud_id: pseud_id).first
      bookmark_response = JSON.parse(response.body, symbolize_names: true)[:bookmarks].first
      assert_equal bookmark_response[:archive_url], bookmark_url(first_bookmark)
    end
  end

  describe "Invalid API bookmark import" do
    let(:archivist) { create(:archivist) }
    
    it "returns 400 Bad Request if no bookmarks are specified" do
      post "/api/v2/bookmarks",
           params: { archivist: archivist.login }.to_json,
           headers: valid_headers
      assert_equal 400, response.status
    end

    it "returns an error message if no URL is specified" do
      post "/api/v2/bookmarks",
           params: { archivist: archivist.login,
             bookmarks: [ bookmark.except(:url) ]
           }.to_json,
           headers: valid_headers
      assert_equal 400, response.status
      bookmark_response = JSON.parse(response.body, symbolize_names: true)[:bookmarks].first
      assert_equal bookmark_response[:messages].first,
                   "This bookmark does not contain a URL to an external site. Please specify a valid, non-AO3 URL."
    end

    it "returns an error message if the URL is on AO3" do
      work = create(:work)
      post "/api/v2/bookmarks",
           params: { archivist: archivist.login,
             bookmarks: [ bookmark.merge(url: work_url(work)) ]
           }.to_json,
           headers: valid_headers
      assert_equal 400, response.status
      bookmark_response = JSON.parse(response.body, symbolize_names: true)[:bookmarks].first
      assert_equal bookmark_response[:messages].first,
                   "Url could not be reached. If the URL is correct and the site is currently down, please try again later."
    end

    it "returns an error message if there is no fandom" do
      post "/api/v2/bookmarks",
           params: { archivist: archivist.login,
             bookmarks: [ bookmark.merge(fandom_string: "") ]
           }.to_json,
           headers: valid_headers
      assert_equal 400, response.status
      bookmark_response = JSON.parse(response.body, symbolize_names: true)[:bookmarks].first
      assert_equal bookmark_response[:messages].first,
                   "This bookmark does not contain a fandom. Please specify a fandom."
    end

    it "returns an error message if there is no title" do
      post "/api/v2/bookmarks",
           params: { archivist: archivist.login,
             bookmarks: [ bookmark.merge(title: "") ]
           }.to_json,
           headers: valid_headers
      assert_equal 400, response.status
      bookmark_response = JSON.parse(response.body, symbolize_names: true)[:bookmarks].first
      assert_equal bookmark_response[:messages].first, "Title can't be blank"
    end

    it "returns an error message if there is no author" do
      post "/api/v2/bookmarks",
           params: { archivist: archivist.login,
             bookmarks: [ bookmark.merge(author: "") ]
           }.to_json,
           headers: valid_headers
      assert_equal 400, response.status
      bookmark_response = JSON.parse(response.body, symbolize_names: true)[:bookmarks].first
      assert_equal bookmark_response[:messages].first,
                   "This bookmark does not contain an external author name. Please specify an author."
    end
  end

  describe "Search for bookmarks" do
    it "does not crash if the archivist has works bookmarked" do
      archivist = create(:archivist)
      pseud_id = archivist.default_pseud.id
      work = create(:work)
      create(:bookmark, pseud_id: pseud_id, bookmarkable_id: work.id)
      post "/api/v2/bookmarks/search",
           params: { archivist: archivist.login,
                     bookmarks: [ bookmark ]
           }.to_json,
           headers: valid_headers

      bookmark_response = JSON.parse(response.body)
      messages = bookmark_response["messages"]
      expect(messages[0]).to_not start_with "An error occurred in the Archive code: undefined method `url' for "
      expect(messages).to include "Successfully searched bookmarks for archivist '#{archivist.login}'"
      expect(bookmark_response["original_url"]).to eq bookmark["id"]
      expect(bookmark_response["bookmarks"][0]["messages"]).to include "There is no bookmark for #{archivist.login} and the URL #{bookmark[:url]}"
    end
  end

  describe "Unit tests - import_bookmark" do
    it "returns an error message when an Exception is raised" do
      user = create(:user)
      # Stub the Bookmark.new method to throw an exception
      allow(Bookmark).to receive(:new).and_raise(StandardError)
      under_test = Api::V2::BookmarksController.new
      bookmark_response = under_test.instance_eval { create_bookmark(user, bookmark, []) }
      expect(bookmark_response[:messages][0]).to eq "StandardError"
      expect(bookmark_response[:original_id]).to eq "123"
      expect(bookmark_response[:status]).to eq :unprocessable_entity
    end
  end
end
