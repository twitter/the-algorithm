require "spec_helper"
require "webmock/rspec"

module ApiHelper
  # set up a valid token and some headers
  def valid_headers
    api = ApiKey.first_or_create!(name: "Test", access_token: "testabc")
    {
      "HTTP_AUTHORIZATION" => ActionController::HttpAuthentication::Token.encode_credentials(api.access_token),
      "HTTP_ACCEPT" => "application/json",
      "CONTENT_TYPE" => "application/json"
    }
  end

  # Values in API fake content
  def content_fields
    {
      title: "Detected Title", summary: "Detected summary", fandoms: "Detected Fandom", warnings: "Underage",
      characters: "Detected 1, Detected 2", rating: "Explicit", relationships: "Detected 1/Detected 2",
      categories: "F/F", freeform: "Detected tag 1, Detected tag 2", external_author_name: "Detected Author",
      external_author_email: "detected@foo.com", notes: "This is a <i>content note</i>.",
      date: "2002-01-12", chapter_title: "Detected chapter title"
    }
  end

  def api_fields
    {
      title: "API Title", summary: "API summary", fandoms: "API Fandom", warnings: ArchiveConfig.WARNING_NONCON_TAG_NAME,
      characters: "API 1, API 2", rating: ArchiveConfig.RATING_GENERAL_TAG_NAME, relationships: "bar 1/bar 2",
      categories: ArchiveConfig.CATEGORY_SLASH_TAG_NAME, freeform: "API tag 1, API tag 2", external_author_name: "API Author",
      external_author_email: "api@foo.com", notes: "This is an <i>API note</i>.",
      date: "2002-01-12", chapter_title: "API chapter title (TBD)", language_code: "es"
    }
  end

  # Let the test get at external sites, but stub out anything containing certain keywords
  def mock_external
    WebMock.allow_net_connect!
    WebMock.stub_request(:any, /foo/).
      to_return(status: 200,
                body:
                  "Title: #{content_fields[:title]}
Summary:  #{content_fields[:summary]}
Date: #{content_fields[:date]}
Fandom:  #{content_fields[:fandoms]}
Rating: #{content_fields[:rating]}
Warnings:  #{content_fields[:warnings]}
Characters:  #{content_fields[:characters]}
Pairings:  #{content_fields[:relationships]}
Category:  #{content_fields[:categories]}
Tags:  #{content_fields[:freeform]}
Author's notes:  #{content_fields[:notes]}
Chapter title:  #{content_fields[:chapter_title]}

stubbed response", headers: {})

    WebMock.stub_request(:any, /no-metadata/).
      to_return(status: 200,
                body: "stubbed response",
                headers: {})

    WebMock.stub_request(:any, /no-content/).
      to_return(status: 200,
                body: "",
                headers: {})

    WebMock.stub_request(:any, /bar/).
      to_return(status: 404, headers: {})
  end
end
