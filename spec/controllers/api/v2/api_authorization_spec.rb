require "spec_helper"
require "webmock"
require "controllers/api/api_helper"

describe "API v2 Authorization", type: :request do
  include ApiHelper
  end_points = %w(/api/v2/works /api/v2/bookmarks)

  describe "API POST with invalid request" do
    it "returns 401 Unauthorized if no token is supplied and forgery protection is enabled" do
      ActionController::Base.allow_forgery_protection = true
      end_points.each do |url|
        post url
        assert_equal 401, response.status
      end
      ActionController::Base.allow_forgery_protection = false
    end

    it "returns 401 Unauthorized if no token is supplied" do
      end_points.each do |url|
        post url
        assert_equal 401, response.status
      end
    end

    it "returns 403 Forbidden if the specified user isn't an archivist" do
      end_points.each do |url|
        post url, params: { archivist: "mr_nobody" }.to_json, headers: valid_headers
        assert_equal 403, response.status
      end
    end
  end
end
