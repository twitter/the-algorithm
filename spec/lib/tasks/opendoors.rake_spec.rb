require 'spec_helper'
require 'rake'

describe 'rake opendoors:import_url_mapping' do
  id1 = 1235
  id2 = 1236
  temp_url = "http://temp/1"
  original_url = "http://another/1"
  original_url2 = "http://another/2"

  let!(:work_with_temp_url) { create(:work, id: id1, imported_from_url: temp_url) }
  let!(:work_with_no_url) { create(:work, id: id2, imported_from_url: nil) }

  context "Open Doors rake task" do
    it "takes a path to a CSV and updates works" do
      path = file_fixture("opendoors_import_url_mapping.csv")
      subject.invoke(path)
      expect(Work.find(work_with_temp_url.id).imported_from_url).to eq(original_url)
      expect(Work.find(work_with_no_url.id).imported_from_url).to eq(original_url2)
    end
    it "fails if the CSV path is invalid" do
      expect { subject.invoke("not a path") }.to raise_error Errno::ENOENT
    end
  end

  context "UrlUpdater" do
    let(:url_updater) { UrlUpdater.new }
    let(:work_with_other_url) { create(:work, imported_from_url: "http://another/1") }

    it "returns an error if the work is not found" do
      row = {
        "URL Imported From" => "http://temp/1",
        "Original URL" => "http://another/2",
        "AO3 id" => 7777773
      }
      result = url_updater.update_work(row)
      expect(result).to eq("7777773\twas not changed: Couldn't find Work with 'id'=7777773")
    end
    
    it "returns a message if the work already has another imported URL" do
      row = {
        "URL Imported From" => "http://temp/1",
        "Original URL" => "http://another/2",
        "AO3 id" => work_with_other_url.id
      }
      result = url_updater.update_work(row)
      expect(result).to match("\\d+\twas not changed: its import url is http://another/1")
    end
    
    it "updates the work if it has no imported URL" do
      row = {
        "URL Imported From" => "http://temp/1",
        "Original URL" => "http://another/2",
        "AO3 id" => work_with_no_url.id
      }
      result = url_updater.update_work(row)
      expect(result).to match("\\d+\twas updated: its import url is now http://another/2")
    end
    
    it "updates the work if it has the temp site imported URL" do
      row = {
        "URL Imported From" => "http://temp/1",
        "Original URL" => "http://another/2",
        "AO3 id" => work_with_temp_url.id
      }
      result = url_updater.update_work(row)
      expect(result).to match("\\d+\twas updated: its import url is now http://another/2")
    end
  end
end
