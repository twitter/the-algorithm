# -*- coding: utf-8 -*-

require 'word_counter'
require 'spec_helper'

describe WordCounter do
  let(:word_counter) { WordCounter.new("") }

  it "should return zero for an empty string" do
    expect(word_counter.count).to eq(0)
  end

  it "should count plain words delimited with spaces" do
    word_counter.text = "one two three four"
    expect(word_counter.count).to eq(4)
  end

  it "should count plain words delimited with linebreaks" do
    word_counter.text = "one\ntwo\nthree\nfour"
    expect(word_counter.count).to eq(4)
  end

  it "should count hyphenated words as one" do
    word_counter.text = "arm-rest is hyphenated"
    expect(word_counter.count).to eq(3)
  end

  it "should count contractions as one" do
    word_counter.text = "don't do that"
    expect(word_counter.count).to eq(3)
  end

  it "should not count lone html tags" do
    word_counter.text = "<p align='center'> one </p> <i> two </i> <s> three </s>"
    expect(word_counter.count).to eq(3)
  end

  it "should recognise html tags as word delimiter" do
    word_counter.text = "<p>one</p>two<br/>three"
    expect(word_counter.count).to eq(3)
  end

  it "should not count empty html tags as words" do
    word_counter.text = "<p>one</p> <p>  </p> <p>two</p>"
    expect(word_counter.count).to eq(2)
  end

  %w(* ~ !? - ~* ~!).each do |char|
    it "should not count a line of #{char} as word" do
      word_counter.text = "<p>one</p> <p>#{char*10}</p> <p>two</p>"
      expect(word_counter.count).to eq(2)
    end
  end

  it "should count words with special charcters correctly" do
    word_counter.text = "zwölf naïve fiancés"
    expect(word_counter.count).to eq(3)
  end

  %w(— -- , /).each do |char|
    it "should recognise #{char} as word delimiter" do
      word_counter.text = "one#{char}two#{char}three"
      expect(word_counter.count).to eq(3)
    end
  end

  %w(— -- - ! ? . , / " ' ).each do |char| #"
    it "should not count lone #{char} as words" do
      word_counter.text = "one #{char} two #{char} three"
      expect(word_counter.count).to eq(3)
    end
  end

  it "should handle common punctuation" do
    word_counter.text = "\Hey Bob,\" said Alice, 'Yay?!?'"
    expect(word_counter.count).to eq(5)
  end

  it "should count words not delimited with space correctly" do
    word_counter.text = "一个简单の栗子"
    expect(word_counter.count).to eq(7)
  end

  it "should count words in mixed languages correctly" do
    word_counter.text = "\“嘿Bob,\” Alice说，‘啊？！？’"
    expect(word_counter.count).to eq(5)
  end
end
