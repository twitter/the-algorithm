# frozen_string_literal: true

require 'active_support/inflector'
require_relative '../../../app/models/search/query_cleaner'

describe QueryCleaner do
  describe "#clean" do
    it "should return a hash" do
      q = { query: "hello world" }
      cleaner = QueryCleaner.new(q)
      expect(cleaner.clean).to eq(q)
    end

    it "should not error if there is no query" do
      cleaner = QueryCleaner.new({})
      expect(cleaner.clean).to eq({})
    end

    it "should extract word count from a query" do
      ["words=100", "wordcount:100", "word_count = 100"].each do |query|
        cleaner = QueryCleaner.new(query: query)
        clean_params = cleaner.clean
        expect(clean_params[:query]).to eq(nil)
        expect(clean_params[:word_count]).to eq("100")
      end
    end

    it "should extract hits from a query" do
      cleaner = QueryCleaner.new(query: "hit count > 50")
      clean_params = cleaner.clean
      expect(clean_params[:query]).to eq(nil)
      expect(clean_params[:hits]).to eq("> 50")
    end

    it "should extract comment count from a query" do
      cleaner = QueryCleaner.new(query: "pumpkins comments<10")
      clean_params = cleaner.clean
      expect(clean_params[:query]).to eq("pumpkins")
      expect(clean_params[:comments_count]).to eq("<10")
    end

    it "should put quotes around category tags" do
      cleaner = QueryCleaner.new(query: "Buffy F/F")
      expect(cleaner.clean[:query]).to eq("Buffy \"f/f\"")
    end

    it "should not ID category tags that are part of larger words" do
      cleaner = QueryCleaner.new(query: "Jim/Frank")
      expect(cleaner.clean[:query]).to eq("Jim/Frank")
    end

    it "should extract sorting options from a query" do
      cleaner = QueryCleaner.new(query: "sort by: hits ascending")
      clean_params = cleaner.clean
      expect(clean_params[:query]).to eq(nil)
      expect(clean_params[:sort_column]).to eq("hits")
      expect(clean_params[:sort_direction]).to eq("asc")
    end

    it "should extract word count sorting from a query" do
      cleaner = QueryCleaner.new(query: "sort:<words")
      clean_params = cleaner.clean
      expect(clean_params[:query]).to eq(nil)
      expect(clean_params[:sort_column]).to eq("word_count")
      expect(clean_params[:sort_direction]).to eq("desc")
    end

    it "should extract author sorting from a query" do
      cleaner = QueryCleaner.new(query: "sorted by:>author")
      clean_params = cleaner.clean
      expect(clean_params[:query]).to eq(nil)
      expect(clean_params[:sort_column]).to eq("authors_to_sort_on")
      expect(clean_params[:sort_direction]).to eq("asc")
    end
  end
end
