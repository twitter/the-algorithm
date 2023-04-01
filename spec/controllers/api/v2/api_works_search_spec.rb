# frozen_string_literal: true
require "spec_helper"
require "controllers/api/api_helper"

include ApiHelper

def post_search_result(valid_params)
  post "/api/v2/works/search", params: valid_params.to_json, headers: valid_headers
  JSON.parse(response.body, symbolize_names: true)
end

describe "API v2 WorksController - Search", type: :request, work_search: true do
  describe "valid work URL request" do
    let!(:work) { create(:work, imported_from_url: "foo") }
    
    it "returns 200 OK" do
      valid_params = { works: [{ original_urls: %w(bar foo) }] }
      post "/api/v2/works/search", params: valid_params.to_json, headers: valid_headers
  
      assert_equal 200, response.status
    end
  
    it "returns the work URL for an imported work" do
      valid_params = { works: [{ original_urls: %w(foo) }] }
      parsed_body = post_search_result(valid_params)
      search_results = parsed_body[:works].first[:search_results]
  
      expect(parsed_body[:works].first[:status]).to eq "found"
      expect(search_results).to include(include(archive_url: work_url(work)))
      expect(search_results.any? { |w| w[:created].to_date == work.created_at.to_date }).to be_truthy
    end
  
    it "returns the original reference if one was provided" do
      valid_params = { works: [{ original_urls: [{ id: "123", url: "foo" }] }] }
      parsed_body = post_search_result(valid_params)
  
      expect(parsed_body[:works].first[:status]).to eq "found"
      expect(parsed_body[:works].first[:original_id]).to eq "123"
      expect(parsed_body[:works].first[:original_url]).to eq "foo"
    end

    it "returns human-readable messages as an array" do
      valid_params = { works: [{ original_urls: [{ id: "123", url: "foo" }] }] }
      parsed_body = post_search_result(valid_params)

      expect(parsed_body[:works].first[:status]).to eq "found"
      expect(parsed_body[:works].first[:messages]).to include("Work \"#{work.title}\", created on #{work.created_at.to_date.to_s(:iso_date)} was found at \"#{url_for(work)}\".")
    end
  
    it "returns an error when no works are provided" do
      invalid_params = { works: [] }
      parsed_body = post_search_result(invalid_params)
  
      expect(parsed_body[:messages].first).to eq "Please provide a list of URLs to find."
    end
    
    it "returns an error when no URLs are provided" do
      invalid_params = { works: [{ original_urls: [] }] }
      parsed_body = post_search_result(invalid_params)
  
      expect(parsed_body[:messages].first).to eq "Please provide a list of URLs to find."
    end
  
    it "returns an error when too many works are provided" do
      loads_of_items = Array.new(210) { |_| { original_urls: ["url"] } }
      valid_params = { works: loads_of_items }
      parsed_body = post_search_result(valid_params)
  
      expect(parsed_body[:messages].first).to start_with "Please provide no more than"
    end
    
    it "returns an error when too many URLs are provided" do
      loads_of_items = Array.new(210) { |_| "url" }
      valid_params = { works: [{ original_urls: loads_of_items }] }
      parsed_body = post_search_result(valid_params)
  
      expect(parsed_body[:messages].first).to start_with "Please provide no more than"
    end
  
    it "returns a not found message for a work that wasn't found" do
      valid_params = { works: [{ original_urls: %w(bar) }] }
      parsed_body = post_search_result(valid_params)
      
      expect(parsed_body[:works].first[:status]).to eq("not_found")
      expect(parsed_body[:works].first[:messages]).to include("No work has been imported from \"bar\".")
    end
  
    it "only does an exact match on the original url" do
      valid_params = { works: [{ original_urls: %w(fo food) }] }
      parsed_body = post_search_result(valid_params)
  
      expect(parsed_body[:works].first[:status]).to eq("not_found")
      expect(parsed_body[:works].first[:messages]).to include("No work has been imported from \"fo\".")
      expect(parsed_body[:works].second[:status]).to eq("not_found")
      expect(parsed_body[:works].second).to include(:messages)
    end
  end
end
