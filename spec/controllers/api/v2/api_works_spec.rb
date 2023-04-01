# frozen_string_literal: true
require "spec_helper"
require "controllers/api/api_helper"

include ApiHelper

describe "API v2 WorksController - Create works", type: :request do
  let(:archivist) { create(:archivist) }

  describe "API import with a valid archivist" do
    before :each do
      ApiHelper.mock_external
    end

    after :each do
      WebMock.reset!
    end

    it "returns 200 OK when all stories are created" do
      valid_params = {
        archivist: archivist.login,
        works: [
          { external_author_name: "bar",
            external_author_email: "bar@foo.com",
            chapter_urls: ["http://foo"] }
        ]
      }

      post "/api/v2/works", params: valid_params.to_json, headers: valid_headers

      assert_equal 200, response.status
    end

    it "returns 400 error with an error message when no stories are created" do
      valid_params = {
        archivist: archivist.login,
        works: [
          { external_author_name: "bar",
            external_author_email: "bar@foo.com",
            chapter_urls: ["http://bar"] }
        ]
      }

      post "/api/v2/works", params: valid_params.to_json, headers: valid_headers

      assert_equal 400, response.status
    end

    it "returns 400 OK with an error message when only some stories are created" do
      valid_params = {
        archivist: archivist.login,
        works: [
          { external_author_name: "bar",
            external_author_email: "bar@foo.com",
            chapter_urls: ["http://foo"] },
          { external_author_name: "bar2",
            external_author_email: "bar2@foo.com",
            chapter_urls: ["http://foo"] }
        ]
      }

      post "/api/v2/works", params: valid_params.to_json, headers: valid_headers

      assert_equal 400, response.status
    end

    it "returns the original id" do
      valid_params = {
        archivist: archivist.login,
        works: [
          { id: "123",
            external_author_name: "bar",
            external_author_email: "bar@foo.com",
            chapter_urls: ["http://foo"] }
        ]
      }

      post "/api/v2/works", params: valid_params.to_json, headers: valid_headers
      parsed_body = JSON.parse(response.body, symbolize_names: true)

      expect(parsed_body[:works].first[:original_id]).to eq("123")
    end

    it "returns human-readable messages as an array" do
      valid_params = {
        archivist: archivist.login,
        works: [
          { id: "123",
            external_author_name: "bar",
            external_author_email: "bar@foo.com",
            chapter_urls: ["http://foo"] }
        ]
      }

      post "/api/v2/works", params: valid_params.to_json, headers: valid_headers
      parsed_body = JSON.parse(response.body, symbolize_names: true)

      expect(parsed_body[:works].first[:messages]).to be_a(Array)
    end

    it "sends claim emails if send_claim_email is true" do
      valid_params = {
        archivist: archivist.login,
        send_claim_emails: 1,
        works: [
          { id: "123",
            external_author_name: "bar",
            external_author_email: "send_invite@ao3.org",
            chapter_urls: ["http://foo"] }
        ]
      }

      post "/api/v2/works", params: valid_params.to_json, headers: valid_headers
      parsed_body = JSON.parse(response.body, symbolize_names: true)

      expect(parsed_body[:messages]).to include("Claim emails sent to bar.")
    end

    it "returns 400 Bad Request if no works are specified" do
      valid_params = {
        archivist: archivist.login
      }

      post "/api/v2/works", params: valid_params.to_json, headers: valid_headers

      assert_equal 400, response.status
    end

    it "returns a helpful message if the external work contains no text" do
      valid_params = {
        archivist: archivist.login,
        works: [
          { external_author_name: "bar",
            external_author_email: "bar@foo.com",
            chapter_urls: ["http://no-content"] }
        ]
      }

      post "/api/v2/works", params: valid_params.to_json, headers: valid_headers
      parsed_body = JSON.parse(response.body, symbolize_names: true)

      expect(parsed_body[:works].first[:messages].first).to start_with("Unable to import this work.")
    end

    describe "Provided API metadata should be used if present" do
      before(:all) do
        Language.find_or_create_by(short: "es", name: "Español")

        mock_external

        archivist = create(:archivist)

        valid_params = {
          archivist: archivist.login,
          works: [
            { id: "123",
              title: api_fields[:title],
              summary: api_fields[:summary],
              fandoms: api_fields[:fandoms],
              warnings: api_fields[:warnings],
              characters: api_fields[:characters],
              rating: api_fields[:rating],
              relationships: api_fields[:relationships],
              categories: api_fields[:categories],
              additional_tags: api_fields[:freeform],
              language_code: api_fields[:language_code],
              external_author_name: api_fields[:external_author_name],
              external_author_email: api_fields[:external_author_email],
              notes: api_fields[:notes],
              chapter_urls: ["http://foo"]
            }
          ]
        }

        post "/api/v2/works", params: valid_params.to_json, headers: valid_headers
        parsed_body = JSON.parse(response.body, symbolize_names: true)

        @work = Work.find_by_url(parsed_body[:works].first[:original_url])
      end

      after(:all) do
        @work&.destroy
        WebMock.reset!
      end


      it "API should override content for Title" do
        expect(@work.title).to eq(api_fields[:title])
      end

      it "API should override content for Summary" do
        expect(@work.summary).to eq("<p>" + api_fields[:summary] + "</p>")
      end

      it "API should override content for Fandoms" do
        expect(@work.fandoms.first.name).to eq(api_fields[:fandoms])
      end
      it "API should override content for Warnings" do
        expect(@work.archive_warnings.first.name).to eq(api_fields[:warnings])
      end
      it "API should override content for Characters" do
        expect(@work.characters.flat_map(&:name)).to eq(api_fields[:characters].split(", "))
      end
      it "API should override content for Ratings" do
        expect(@work.ratings.first.name).to eq(api_fields[:rating])
      end
      it "API should override content for Relationships" do
        expect(@work.relationships.first.name).to eq(api_fields[:relationships])
      end
      it "API should override content for Categories" do
        expect(@work.categories.first.name).to eq(api_fields[:categories])
      end
      it "API should override content for Additional Tags" do
        expect(@work.freeforms.flat_map(&:name)).to eq(api_fields[:freeform].split(", "))
      end
      it "API should override content for Language" do
        expect(Language.find_by(id: @work.language_id).short).to eq(api_fields[:language_code])
      end
      it "API should override content for Notes" do
        expect(@work.notes).to eq("<p>" + api_fields[:notes] + "</p>")
      end
      it "API should override content for Author pseud" do
        expect(@work.external_author_names.first.name).to eq(api_fields[:external_author_name])
      end
    end

    describe "Metadata should be extracted from content if no API metadata is supplied" do
      before(:all) do
        mock_external

        archivist = create(:archivist)

        valid_params = {
          archivist: archivist.login,
          works: [
            { external_author_name: api_fields[:external_author_name],
              external_author_email: api_fields[:external_author_email],
              chapter_urls: ["http://foo"] }
          ]
        }

        post "/api/v2/works", params: valid_params.to_json, headers: valid_headers

        parsed_body = JSON.parse(response.body, symbolize_names: true)
        @work = Work.find_by_url(parsed_body[:works].first[:original_url])
        created_user = ExternalAuthor.find_by(email: api_fields[:external_author_email])
        created_user&.destroy
      end

      after(:all) do
        @work&.destroy
        WebMock.reset!
      end

      it "Title should be detected from the content" do
        expect(@work.title).to eq(content_fields[:title])
      end
      it "Summary should be detected from the content" do
        expect(@work.summary).to eq("<p>" + content_fields[:summary] + "</p>")
      end
      it "Date should be detected from the content" do
        expect(@work.revised_at.to_date).to eq(content_fields[:date].to_date)
      end
      it "Chapter title should be detected from the content" do
        expect(@work.chapters.first.title).to eq(content_fields[:chapter_title])
      end
      it "Fandoms should be detected from the content" do
        expect(@work.fandoms.first.name).to eq(content_fields[:fandoms])
      end
      it "Warnings should be detected from the content" do
        expect(@work.archive_warnings.first.name).to eq(content_fields[:warnings])
      end
      it "Characters should be detected from the content" do
        expect(@work.characters.flat_map(&:name)).to eq(content_fields[:characters].split(", "))
      end
      it "Ratings should be detected from the content" do
        expect(@work.ratings.first.name).to eq(content_fields[:rating])
      end
      it "Relationships should be detected from the content" do
        expect(@work.relationships.first.name).to eq(content_fields[:relationships])
      end
      it "Categories should be detected from the content" do
        expect(@work.categories).to be_empty
      end
      it "Additional Tags should be detected from the content" do
        expect(@work.freeforms.flat_map(&:name)).to eq(content_fields[:freeform].split(", "))
      end
      it "Notes should be detected from the content" do
        expect(@work.notes).to eq("<p>" + content_fields[:notes] + "</p>")
      end
    end

    describe "Imports should use fallback values or nil if no metadata is supplied" do
      before(:all) do
        mock_external

        archivist = create(:archivist)

        valid_params = {
          archivist: archivist.login,
          works: [
            { external_author_name: api_fields[:external_author_name],
              external_author_email: api_fields[:external_author_email],
              chapter_urls: ["http://no-metadata"] }
          ]
        }

        post "/api/v2/works", params: valid_params.to_json, headers: valid_headers

        parsed_body = JSON.parse(response.body, symbolize_names: true)
        @work = Work.find_by_url(parsed_body[:works].first[:original_url])
      end

      after(:all) do
        @work&.destroy
        WebMock.reset!
      end

      it "Title should be the default fallback title for imported works" do
        expect(@work.title).to eq("Untitled Imported Work")
      end
      it "Summary should be blank" do
        expect(@work.summary).to eq("")
      end
      it "Date should be todayish" do
        expect(@work.created_at.utc.to_date).to eq(Time.now.getutc.to_date)
      end
      it "Chapter title should be blank" do
        expect(@work.chapters.first.title).to be_nil
      end
      it "Fandoms should be the default Archive fandom ('No Fandom')" do
        expect(@work.fandoms.first.name).to eq(ArchiveConfig.FANDOM_NO_TAG_NAME)
      end
      it "Warnings should be the default Archive warning" do
        expect(@work.archive_warnings.first.name).to eq(ArchiveConfig.WARNING_DEFAULT_TAG_NAME)
      end
      it "Characters should be empty" do
        expect(@work.characters).to be_empty
      end
      it "Ratings should be the default Archive rating" do
        expect(@work.ratings.first.name).to eq(ArchiveConfig.RATING_DEFAULT_TAG_NAME)
      end
      it "Relationships should be empty" do
        expect(@work.relationships).to be_empty
      end
      it "Categories should be empty" do
        expect(@work.categories).to be_empty
      end
      it "Additional Tags should be empty" do
        expect(@work.freeforms).to be_empty
      end
      it "Language should be English" do
        expect(@work.language_id).to eq(Language.find_by(short: "en").id)
      end
      it "Notes should be empty" do
        expect(@work.notes).to be_empty
      end
      it "Author pseud" do
        expect(@work.external_author_names.first.name).to eq(api_fields[:external_author_name])
      end
    end

    describe "Provided API metadata should be used if present and tag detection is turned off" do
      before(:all) do
        mock_external

        archivist = create(:archivist)

        valid_params = {
          archivist: archivist.login,
          works: [
            { id: "123",
              title: api_fields[:title],
              detect_tags: false,
              summary: api_fields[:summary],
              fandoms: api_fields[:fandoms],
              warnings: api_fields[:warnings],
              characters: api_fields[:characters],
              rating: api_fields[:rating],
              relationships: api_fields[:relationships],
              categories: api_fields[:categories],
              additional_tags: api_fields[:freeform],
              external_author_name: api_fields[:external_author_name],
              external_author_email: api_fields[:external_author_email],
              notes: api_fields[:notes],
              chapter_urls: ["http://foo"] }
          ]
        }

        post "/api/v2/works", params: valid_params.to_json, headers: valid_headers

        parsed_body = JSON.parse(response.body, symbolize_names: true)
        @work = Work.find_by_url(parsed_body[:works].first[:original_url])
      end

      after(:all) do
        @work&.destroy
        WebMock.reset!
      end

      it "API should override content for Title" do
        expect(@work.title).to eq(api_fields[:title])
      end
      it "API should override content for Summary" do
        expect(@work.summary).to eq("<p>" + api_fields[:summary] + "</p>")
      end
      it "Date should be detected from the content" do
        expect(@work.revised_at.to_date).to eq(content_fields[:date].to_date)
      end
      it "Chapter title should be detected from the content" do
        expect(@work.chapters.first.title).to eq(content_fields[:chapter_title])
      end
      it "API should override content for Fandoms" do
        expect(@work.fandoms.first.name).to eq(api_fields[:fandoms])
      end
      it "API should override content for Warnings" do
        expect(@work.archive_warnings.first.name).to eq(api_fields[:warnings])
      end
      it "API should override content for Characters" do
        expect(@work.characters.flat_map(&:name)).to eq(api_fields[:characters].split(", "))
      end
      it "API should override content for Ratings" do
        expect(@work.ratings.first.name).to eq(api_fields[:rating])
      end
      it "API should override content for Relationships" do
        expect(@work.relationships.first.name).to eq(api_fields[:relationships])
      end
      it "API should override content for Categories" do
        expect(@work.categories.first.name).to eq(api_fields[:categories])
      end
      it "API should override content for Additional Tags" do
        expect(@work.freeforms.flat_map(&:name)).to eq(api_fields[:freeform].split(", "))
      end
      it "API should override content for Notes" do
        expect(@work.notes).to eq("<p>" + api_fields[:notes] + "</p>")
      end
      it "API should override content for Author pseud" do
        expect(@work.external_author_names.first.name).to eq(api_fields[:external_author_name])
      end
    end

    describe "Some fields should be detected and others use fallback values or nil if no metadata is supplied and tag detection is turned off" do
      before(:all) do
        mock_external

        archivist = create(:archivist)

        valid_params = {
          archivist: archivist.login,
          works: [
            { external_author_name: api_fields[:external_author_name],
              external_author_email: api_fields[:external_author_email],
              detect_tags: false,
              chapter_urls: ["http://foo"] }
          ]
        }

        post "/api/v2/works", params: valid_params.to_json, headers: valid_headers

        parsed_body = JSON.parse(response.body, symbolize_names: true)
        @work = Work.find_by_url(parsed_body[:works].first[:original_url])
      end

      after(:all) do
        @work&.destroy
        WebMock.reset!
      end

      it "Title should be detected from the content" do
        expect(@work.title).to eq(content_fields[:title])
      end
      it "Summary should be detected from the content" do
        expect(@work.summary).to eq("<p>" + content_fields[:summary] + "</p>")
      end
      it "Date should be detected from the content" do
        expect(@work.revised_at.to_date).to eq(content_fields[:date].to_date)
      end
      it "Chapter title should be detected from the content" do
        expect(@work.chapters.first.title).to eq(content_fields[:chapter_title])
      end
      it "Fandoms should be the default Archive fandom ('No Fandom')" do
        expect(@work.fandoms.first.name).to eq(ArchiveConfig.FANDOM_NO_TAG_NAME)
      end
      it "Warnings should be the default Archive warning" do
        expect(@work.archive_warnings.first.name).to eq(ArchiveConfig.WARNING_DEFAULT_TAG_NAME)
      end
      it "Characters should be empty" do
        expect(@work.characters).to be_empty
      end
      it "Ratings should be the default Archive rating" do
        expect(@work.ratings.first.name).to eq(ArchiveConfig.RATING_DEFAULT_TAG_NAME)
      end
      it "Relationships should be empty" do
        expect(@work.relationships).to be_empty
      end
      it "Categories should be empty" do
        expect(@work.categories).to be_empty
      end
      it "Additional Tags should be empty" do
        expect(@work.freeforms).to be_empty
      end
      it "Notes should be empty" do
        expect(@work.notes).to be_empty
      end
      it "Author pseud" do
        expect(@work.external_author_names.first.name).to eq(api_fields[:external_author_name])
      end
    end
  end
end

describe "API v2 WorksController - Unit Tests", type: :request do
  before do
    @under_test = Api::V2::WorksController.new
  end

  it "work_url_from_external returns an error message when the work URL is blank" do
    work_url_response = @under_test.instance_eval { find_work_by_import_url("") }
    expect(work_url_response[:message]).to eq "Please provide the original URL for the work."
  end

  it "work_url_from_external returns the work url when a work is found" do
    work1 = create(:work, imported_from_url: "http://foo")
    work_url_response = @under_test.instance_eval { find_work_by_import_url("http://foo") }
    expect(work_url_response[:works].first).to eq work1
  end

  it "notify_and_return_authors calls find_or_invite on each external author" do
    user = create(:user)
    author1 = create(:external_author)
    author2 = create(:external_author)
    work = create(:work)
    name1 = create(:external_author_name, name: 'n1', external_author: author1)
    name2 = create(:external_author_name, name: 'n2', external_author: author2)
    create(:external_creatorship, external_author_name: name1, creation: work)
    create(:external_creatorship, external_author_name: name2, creation: work)

    @under_test.instance_eval { notify_and_return_authors([work], user) }
    emails = Invitation.all.map(&:invitee_email)
    expect(emails).to include(author1.email)
    expect(emails).to include(author2.email)
  end

  describe "work_errors" do
    it "returns an error if a work doesn't contain chapter urls" do
      work = { chapter_urls: [] }
      error_message = @under_test.instance_eval { work_errors(work) }
      expect(error_message[1][0]).to start_with "This work doesn't contain any chapter URLs."
    end

    it "returns an error if a work has too many chapters" do
      loads_of_items = Array.new(210) { |_| "chapter_url" }
      work = { chapter_urls: loads_of_items }
      error_message = @under_test.instance_eval { work_errors(work) }
      expect(error_message[1][0]).to start_with "This work contains too many chapter URLs"
    end
  end
end
