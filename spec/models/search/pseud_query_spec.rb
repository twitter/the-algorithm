require 'spec_helper'

describe PseudQuery, pseud_search: true do
  let!(:pseuds) do
    users = {
      user_abc: create(:user, login: "abc"),
      user_abc_d: create(:user, login: "abc_d"),
      user_abc_num: create(:user, login: "abc123"),
      user_foo: create(:user, login: "foo"),
      user_bar: create(:user, login: "bar"),
      user_aisha: create(:user, login: "aisha")
    }
    pseuds = {
      pseud_abc: users[:user_abc].default_pseud,
      pseud_abc_d: users[:user_abc_d].default_pseud,
      pseud_abc_d_2: create(:pseud, user: users[:user_abc_d], name: "Abc_ D"),
      pseud_abc_num: users[:user_abc_num].default_pseud,
      pseud_abc_num_2: create(:pseud, user: users[:user_abc_num], name: "Abc 123 Pseud"),
      pseud_foo: users[:user_foo].default_pseud,
      pseud_foo_2: create(:pseud, user: users[:user_foo], name: "bar"),
      pseud_bar: users[:user_bar].default_pseud,
      pseud_bar_2: create(:pseud, user: users[:user_bar], name: "foo"),
      pseud_aisha: create(:pseud, user: users[:user_aisha], name: "عيشة")
    }
    run_all_indexing_jobs
    pseuds
  end

  context "Search all fields" do
    it "performs a case-insensitive search ('AbC' matches 'abc' first, then variations including abc)" do
      pseud_query = PseudQuery.new(query: "AbC")
      results = pseud_query.search_results
      expect(results[0]).to eq(pseuds[:pseud_abc])
      expect(results[1]).to eq(pseuds[:pseud_abc_num_2])
      expect(results[2]).to eq(pseuds[:pseud_abc_num])
      # these two have the same score
      expect(results).to include(pseuds[:pseud_abc_d_2])
      expect(results).to include(pseuds[:pseud_abc_d])
    end

    it "matches a pseud with and without numbers ('abc123' matches 'abc123' first, then 'Abc 123 Pseud' and 'abc')" do
      pseud_query = PseudQuery.new(query: "abc123")
      results = pseud_query.search_results
      expect(results[0]).to eq(pseuds[:pseud_abc_num])
      expect(results[1]).to eq(pseuds[:pseud_abc_num_2])
      expect(results[2]).to eq(pseuds[:pseud_abc])
      expect(results).to include(pseuds[:pseud_abc_d])
      expect(results).to include(pseuds[:pseud_abc_d_2])
    end

    it "matches both pseud and user ('bar' matches 'foo (bar)' and 'bar (foo)'" do
      pseud_query = PseudQuery.new(query: "bar")
      results = pseud_query.search_results
      expect(results[0]).to eq(pseuds[:pseud_bar])
      expect(results[1]).to eq(pseuds[:pseud_foo_2])
      expect(results[2]).to eq(pseuds[:pseud_bar_2])
    end
  end

  context "Name field" do
    it "performs a case-insensitive search ('AbC' matches 'abc' and 'abc123')" do
      pseud_query = PseudQuery.new(name: "AbC")
      results = pseud_query.search_results
      expect(results[0]).to eq(pseuds[:pseud_abc])
      expect(results[1]).to eq(pseuds[:pseud_abc_num_2])
    end

    it "matches a pseud with and without numbers ('abc123' matches 'abc123' first, then 'Abc 123 Pseud')" do
      pseud_query = PseudQuery.new(name: "abc123")
      results = pseud_query.search_results
      expect(results[0]).to eq(pseuds[:pseud_abc_num])
      expect(results[1]).to eq(pseuds[:pseud_abc_num_2])
    end

    it "matches multiple pseuds with and without numbers ('abc123, عيشة' matches 'abc123' and 'aisha', then 'Abc 123 Pseud')" do
      pseud_query = PseudQuery.new(name: "abc123,عيشة")
      results = pseud_query.search_results
      expect(results).to include(pseuds[:pseud_aisha])
      expect(results).to include(pseuds[:pseud_abc_num])
      expect(results[2]).to eq(pseuds[:pseud_abc_num_2])
    end
  end
end
