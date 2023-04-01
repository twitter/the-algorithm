class WranglingGuidelinesController < ApplicationController
  before_action :admin_only, except: [:index, :show]

  # GET /wrangling_guidelines
  def index
    @wrangling_guidelines = WranglingGuideline.order('position ASC')
  end

  # GET /wrangling_guidelines/1
  def show
    @wrangling_guideline = WranglingGuideline.find(params[:id])
  end

  # GET /wrangling_guidelines/new
  def new
    @wrangling_guideline = WranglingGuideline.new
  end

  # GET /wrangling_guidelines/1/edit
  def edit
    @wrangling_guideline = WranglingGuideline.find(params[:id])
  end

  # GET /wrangling_guidelines/manage
  def manage
    @wrangling_guidelines = WranglingGuideline.order('position ASC')
  end

  # POST /wrangling_guidelines
  def create
    @wrangling_guideline = WranglingGuideline.new(wrangling_guideline_params)

    if @wrangling_guideline.save
      flash[:notice] = ts('Wrangling Guideline was successfully created.')
      redirect_to(@wrangling_guideline)
    else
      render action: 'new'
    end
  end

  # PUT /wrangling_guidelines/1
  def update
    @wrangling_guideline = WranglingGuideline.find(params[:id])

    if @wrangling_guideline.update(wrangling_guideline_params)
      flash[:notice] = ts('Wrangling Guideline was successfully updated.')
      redirect_to(@wrangling_guideline)
    else
      render action: 'edit'
    end
  end

  # reorder FAQs
  def update_positions
    if params[:wrangling_guidelines]
      @wrangling_guidelines = WranglingGuideline.reorder_list(params[:wrangling_guidelines])
      flash[:notice] = ts('Wrangling Guidelines order was successfully updated.')
    end
    redirect_to(wrangling_guidelines_path)
  end

  # DELETE /wrangling_guidelines/1
  def destroy
    @wrangling_guideline = WranglingGuideline.find(params[:id])
    @wrangling_guideline.destroy
    flash[:notice] = ts('Wrangling Guideline was successfully deleted.')
    redirect_to(wrangling_guidelines_path)
  end

  private

  def wrangling_guideline_params
    params.require(:wrangling_guideline).permit(:title, :content)
  end

end
