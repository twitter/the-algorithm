# -*- coding: utf-8 -*-

require 'string_cleaner'

class Foo
  include StringCleaner
end

describe Foo do
  let(:foo) { Foo.new }

  describe "#remove_articles_from_string" do
    it "should remove 'the '" do
      expect(foo.remove_articles_from_string("The Hobbit")).to eq("Hobbit")
    end

    it "should remove 'a '" do
      expect(foo.remove_articles_from_string("A Song of Ice And Fire")).to eq("Song of Ice And Fire")
    end

    it "should remove 'an '" do
      expect(foo.remove_articles_from_string("An Opportunity")).to eq("Opportunity")
    end

    it "should not remove 'the' if followed by other characters" do
      expect(foo.remove_articles_from_string("There Will Be Blood")).to eq("There Will Be Blood")
    end

  end

end
