require "spec_helper"

class PassingJob
  def self.perform(_string); end
end

class FailingJob
  def self.perform(_string)
    work = Work.find_by(id: 1)
    work&.destroy
    Work.find 1
  end
end

class BrokenJob
  def self.perform(_string)
    Work.no_such_method
  end
end

describe "rake resque:run_failures" do
  let(:worker) { Resque::Worker.new(:tests) }

  before { Resque::Failure.clear }
  after { Resque::Failure.clear }

  it "clears out passing jobs" do
    Resque::Failure.create(exception: Exception.new(ActiveRecord::RecordNotFound),
                           worker: worker,
                           queue: 'tests',
                           payload: { 'class' => 'PassingJob', 'args' => 'retry found me' })

    expect(Resque::Failure.count).to eq(1)
    subject.invoke
    expect(Resque::Failure.count).to eq(0)
  end

  it "clears out failing jobs if they're RecordNotFound" do
    Resque::Failure.create(exception: Exception.new(ActiveRecord::RecordNotFound),
                           worker: worker,
                           queue: 'tests',
                           payload: { 'class' => 'FailingJob', 'args' => 'still missing on retry' })

    expect(Resque::Failure.count).to eq(1)
    subject.invoke
    expect(Resque::Failure.count).to eq(0)
  end

  it "does not clear out failing jobs if they're not RecordNotFound" do
    Resque::Failure.create(exception: Exception.new(NoMethodError),
                           worker: worker,
                           queue: 'tests',
                           payload: { 'class' => 'BrokenJob', 'args' => 'will never work' })

    expect(Resque::Failure.count).to eq(1)
    subject.invoke
    expect(Resque::Failure.count).to eq(1)
  end
end
