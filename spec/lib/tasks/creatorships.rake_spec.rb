# frozen_string_literal: true

require "spec_helper"

describe "rake creatorships:remove_deleted_work_creatorships" do
  let(:work) { create(:work) }

  let(:work_creatorships_relation) do
    Creatorship.where(creation_type: "Work", creation_id: work.id)
  end

  context "when the creatorship belongs to a valid work" do
    it "doesn't delete the creatorship" do
      expect(work_creatorships_relation.reset.count).to eq(1)
      expect { subject.invoke }.to change { Creatorship.count }.by(0)
      expect(work_creatorships_relation.reset.count).to eq(1)
    end
  end

  context "when the creatorship belongs to a deleted work" do
    it "does delete the creatorship" do
      work.delete # use delete to avoid deleting the creatorships
      expect(work_creatorships_relation.reset.count).to eq(1)
      expect { subject.invoke }.to change { Creatorship.count }.by(-1)
      expect(work_creatorships_relation.reset.count).to eq(0)
    end
  end
end

describe "rake creatorships:remove_deleted_chapter_creatorships" do
  let(:chapter) { create(:work).chapters.first }

  let(:chapter_creatorships_relation) do
    Creatorship.where(creation_type: "Chapter", creation_id: chapter.id)
  end

  context "when the creatorship belongs to a valid chapter" do
    it "doesn't delete the creatorship" do
      expect(chapter_creatorships_relation.reset.count).to eq(1)
      expect { subject.invoke }.to change { Creatorship.count }.by(0)
      expect(chapter_creatorships_relation.reset.count).to eq(1)
    end
  end

  context "when the creatorship belongs to a deleted chapter" do
    it "does delete the creatorship" do
      chapter.delete # use delete to avoid deleting the creatorships
      expect(chapter_creatorships_relation.reset.count).to eq(1)
      expect { subject.invoke }.to change { Creatorship.count }.by(-1)
      expect(chapter_creatorships_relation.reset.count).to eq(0)
    end
  end
end

describe "rake creatorships:remove_deleted_series_creatorships" do
  let(:series) { create(:series) }

  let(:series_creatorships_relation) do
    Creatorship.where(creation_type: "Series", creation_id: series.id)
  end

  context "when the creatorship belongs to a valid series" do
    it "doesn't delete the creatorship" do
      expect(series_creatorships_relation.reset.count).to eq(1)
      expect { subject.invoke }.to change { Creatorship.count }.by(0)
      expect(series_creatorships_relation.reset.count).to eq(1)
    end
  end

  context "when the creatorship belongs to a deleted series" do
    it "does delete the creatorship" do
      series.delete # use delete to avoid deleting the creatorships
      expect(series_creatorships_relation.reset.count).to eq(1)
      expect { subject.invoke }.to change { Creatorship.count }.by(-1)
      expect(series_creatorships_relation.reset.count).to eq(0)
    end
  end
end

describe "rake creatorships:add_missing_series_creatorships" do
  let(:series) { create(:series) }

  let!(:work) do
    create(:work, authors: series.pseuds, series: [series])
  end

  before { series.creatorships.delete_all }

  it "adds pseuds on the series that are listed on the work" do
    subject.invoke
    expect(series.pseuds.reload).to eq(work.pseuds.reload)
  end
end

describe "rake creatorships:remove_orphaned_empty_series" do
  let(:series) { create(:series) }

  context "when the series has a work but no pseuds" do
    before do
      create(:work, authors: series.pseuds, series: [series])
      series.creatorships.delete_all
    end

    it "doesn't delete the series" do
      subject.invoke
      expect { series.reload }.not_to raise_exception
    end
  end

  context "when the series has a pseud but no works" do
    it "doesn't delete the series" do
      subject.invoke
      expect { series.reload }.not_to raise_exception
    end
  end

  context "when the series has no works and no pseuds" do
    before do
      series.creatorships.delete_all
    end

    it "deletes the series" do
      subject.invoke
      expect { series.reload }.to raise_exception(ActiveRecord::RecordNotFound)
    end
  end
end
