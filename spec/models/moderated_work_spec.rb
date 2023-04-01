require "spec_helper"

describe ModeratedWork do
  describe ".register" do
    it "returns a moderated work object" do
      mod_work = ModeratedWork.register(Work.new(id: 42))
      expect(mod_work.work_id).to eq(42)
    end
  end

  describe ".processed_bulk_ids" do
    it "removes conflicting ids" do
      params = { spam: [1,2,3], ham: [2,4,6] }
      results = ModeratedWork.processed_bulk_ids(params)
      expect(results).to eq({ spam: [1,3], ham: [4,6] })
    end
    it "doesn't error when params are missing" do
      results = ModeratedWork.processed_bulk_ids({})
      expect(results).to eq({ spam: [], ham: [] })
    end
  end
end
