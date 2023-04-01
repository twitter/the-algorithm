# frozen_string_literal: true
# encoding: utf-8
require 'spec_helper'

describe ExportsHelper do

  context "tab-delimited file generation" do
    header = ["Column 1", "Column 2", "Column 3"]

    before do
      array = [
        header,
        ["Thing 1", "Thing 2", "Thing 3"],
        ["Foo 1", "Foo 2", "Foo 3"]
      ]
      result = export_csv(array)
      text_without_bom = result.encode("UTF-8").sub!(/^\xEF\xBB\xBF/u, '')
      @csv_array = CSV.parse(text_without_bom, col_sep: "\t")
    end

    it "should be a TSV file with the expected number of rows" do
      expect(@csv_array.size).to eq(3)
    end
    it "should contain the right fields" do
      expect(@csv_array.first).to eq(header)
    end
  end
end
