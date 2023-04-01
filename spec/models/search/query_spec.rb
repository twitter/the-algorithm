require 'spec_helper'

describe Query do
  describe '#split_query_text_phrases' do
    it "should add quoted phrases to a query string" do
      q = Query.new
      result = q.split_query_text_phrases(:tag, "bork bork bork")
      expect(result).to eq(" tag:\"bork bork bork\"")
    end

    it "should separate phrases by comma" do
      q = Query.new
      result = q.split_query_text_phrases(:tag, "unicorns, i love turnips")
      expect(result).to eq(" tag:\"unicorns\" tag:\"i love turnips\"")
    end
  end

  describe '#split_query_text_words' do
    it "should add individual words to a query string" do
      q = Query.new
      result = q.split_query_text_words(:notes, "carrots celery potato")
      expect(result).to eq(" notes:carrots notes:celery notes:potato")
    end

    it "should replace minuses with NOTs" do
      q = Query.new
      result = q.split_query_text_words(:hero, "superman -batman")
      expect(result).to eq(" hero:superman NOT hero:batman")
    end
  end
end
