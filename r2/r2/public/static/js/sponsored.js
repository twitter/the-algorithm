!function(r) {

var UseDefaultClassName = (function() {
  var camelCaseRegex = /([a-z])([A-Z])/g;
  function hyphenate(match, $1, $2) {
    return $1 + '-' + $2;
  }

  return {
    /**
     * derive a className automatically from the displayName property
     * e.g. MyDisplayName => my-display-name
     * if a className state or prop is passed in, add that
     * if values are passed into the function, add those in as well
     * @param {string} arguments optionally pass in any number of
     *                           classNames to to add to the list
     * @return {string} css class name
     */
    getClassName: function(/* classNames */) {
      var classNames = [];

      if (this.constructor.displayName) {
        classNames.push(
          this.constructor.displayName.replace(camelCaseRegex, hyphenate)
                                      .toLowerCase()
          );
      }

      if (this.state && this.state.className) {
        classNames.push(this.state.className);
      }
      else if (this.props.className) {
        classNames.push(this.props.className);
      }

      if (arguments.length) {
        classNames.push.apply(classNames, arguments);
      }

      return classNames.join(' ');
    }
  };
})();


var CampaignFormattedProps = {
  componentWillMount: function() {
    this.formattedProps = this.getFormattedProps(_.clone(this.props), this.props);
  },

  componentWillUpdate: function(nextProps) {
    this.formattedProps = this.getFormattedProps(_.clone(nextProps), nextProps);
  },

  getFormattedProps: function(formattedProps, props) {
    if (props.impressions) {
      formattedProps.impressions = r.utils.prettyNumber(props.impressions);
    }
    if (props.totalBudgetDollars === null) {
      formattedProps.totalBudgetDollars = 'N/A';
    } else if (_.isNaN(props.budget)) {
      formattedProps.totalBudgetDollars = 0;
    } else if (props.totalBudgetDollars) {
      formattedProps.totalBudgetDollars = props.totalBudgetDollars.toFixed(2);
    }
    return formattedProps;
  },
};


var CampaignButton = React.createClass({
  displayName: 'CampaignButton',

  mixins: [UseDefaultClassName],

  getDefaultProps: function() {
    return {
      isNew: true,
    };
  },

  render: function() {
    if (this.props.isNew) {
      return React.DOM.div({ className: 'button-group' },
        React.DOM.button(
          { ref: 'keepOpen', className: 'campaign-button', onClick: this.handleClick },
          r._('create')
        ),
        React.DOM.button(
          { className: this.getClassName(), onClick: this.handleClick },
          r._('+ close')
        )
      );
    }
    return React.DOM.button(
      { className: this.getClassName(), onClick: this.handleClick },
      this.props.isNew ? r._('create') : r._('save')
    );
  },

  handleClick: function(e) {
    var close = true;
    if (this.refs.keepOpen) {
      close = !(e.target === this.refs.keepOpen.getDOMNode());
    }
    if (typeof this.props.onClick === 'function') {
      this.props.onClick(close);
    }
  },
});


var InfoText = React.createClass({
  displayName: 'InfoText',

  mixins: [UseDefaultClassName, CampaignFormattedProps],

  render: function() {
    var text = Array.isArray(this.props.children)
             ? this.props.children.join('\n')
             : this.props.children;
    return React.DOM.span({ className: this.getClassName() },
      text.format(this.formattedProps)
    );
  },

});

var CampaignOptionTable = React.createClass({
  displayName: 'CampaignOptionTable',

  mixins: [UseDefaultClassName],

  render: function() {
    return React.DOM.table({ className: this.getClassName() },
      React.DOM.tbody(null, this.props.children)
    );
  }
})

var CampaignOption = React.createClass({
  displayName: 'CampaignOption',

  mixins: [UseDefaultClassName, CampaignFormattedProps],

  getDefaultProps: function() {
    return {
      primary: false,
      start: '',
      end: '',
      bid: '',
      impressions: '',
      isNew: true,
      costBasis: '',
      totalBudgetDollars: '',
      costBasis: '',
      bidDollars: '',
    };
  },

  render: function() {
    var customText;
    if (r.sponsored.isAuction) {
      customText = '$' + parseFloat(this.props.bidDollars).toFixed(2) + ' ' + this.props.costBasis;
    } else {
      customText = this.formattedProps.impressions + ' impressions';
    }
    return React.DOM.tr({ className: this.getClassName() },
      React.DOM.td({ className: 'date start-date' }, this.props.start),
      React.DOM.td({ className: 'date end-date' }, this.props.end),
      React.DOM.td({ className: 'total-budget' }, '$', this.formattedProps.totalBudgetDollars,
        ' total'),
      React.DOM.td({}, customText),
      React.DOM.td({ className: 'buttons' },
        CampaignButton({
          className: this.props.primary ? 'primary-button' : '',
          isNew: this.props.isNew,
          onClick: this.handleClick,
        })
      )
    );
  },

  handleClick: function(close) {
    var $startdate = $('#startdate');
    var $enddate = $('#enddate');
    var $totalBudgetDollars = $('#total_budget_dollars');
    var $bidDollars = $('#bid_dollars');
    var userStartdate = $startdate.val();
    var userEnddate = $enddate.val();
    var userTotalBudgetDollars = $totalBudgetDollars.val();
    var userBidDollars = $bidDollars.val() || 0.;
    $('#startdate').val(this.props.start);
    $('#enddate').val(this.props.end);
    $('#total_budget_dollars').val(this.props.totalBudgetDollars);
    $('#bid_dollars').val(this.props.bidDollars);
    setTimeout(function(){
      send_campaign(close);
      // hack, needed because post_pseudo_form hides any element in the form
      // with an `error` class, which might be one of our InfoText components
      // but we want react to manage that
      $('.campaign-creator .info-text').removeAttr('style');
      // reset the form with the user's original values
      $startdate.val(userStartdate);
      $enddate.val(userEnddate);
      $totalBudgetDollars.val(userTotalBudgetDollars);
      $bidDollars.val(userBidDollars);
    }, 0);
  },
});


var CampaignSet = React.createClass({
  displayName: 'CampaignSet',

  mixins: [UseDefaultClassName],

  render: function() {
    return React.DOM.div({ className: this.getClassName() },
      this.props.children
    );
  },
});

var CampaignCreator = React.createClass({
  displayName: 'CampaignCreator',

  mixins: [UseDefaultClassName],

  getDefaultProps: function() {
    return {
      totalBudgetDollars: 0,
      targetName: '',
      cpm: 0,
      minBidDollars: 0,
      maxBidDollars: 0,
      maxBudgetDollars: 0,
      minBudgetDollars: 0,
      dates: [],
      inventory: [],
      requested: 0,
      override: false,
      isNew: true,
    };
  },

  getInitialState: function() {
    var totalAvailable = this.getAvailable(this.props);
    var available = totalAvailable;
    if (this.props.maxBudgetDollars) {
      available = Math.min(available, this.getImpressions(this.props.maxBudgetDollars));
    }
    return {
      totalAvailable: totalAvailable,
      available: available,
      maxTime: 0,
    };
  },

  componentWillMount: function() {
    this.setState({
      maxTime: dateFromInput('#date-start-max').getTime(),
    });
  },

  componentWillReceiveProps: function(nextProps) {
    var totalAvailable = this.getAvailable(nextProps);
    var available = totalAvailable;
    if (this.props.maxBudgetDollars) {
      available = Math.min(available, this.getImpressions(this.props.maxBudgetDollars));
    }
    this.setState({
      totalAvailable: totalAvailable,
      available: available,
    });
  },

  getAvailable: function(props) {
    if (props.override) {
      return _.reduce(props.inventory, sum, 0);
    }
    else {
      return _.min(props.inventory) * props.dates.length;
    }
  },

  render: function() {
    return React.DOM.div({
        className: this.getClassName(),
      },
      this.getCampaignSets()
    );
  },

  getCampaignSets: function() {
    if (r.sponsored.isAuction) {
      var auction = this.getAuctionOption(),
          cssClass = null,
          message = r._('Please confirm the details of your campaign');
      if (auction.totalBudgetDollars < this.props.minBudgetDollars) {
        cssClass = {className: 'error', minBudgetDollars: auction.minBudgetDollars};
        message = r._('your budget must be at least $%(minBudgetDollars)s');
      } else if (auction.totalBudgetDollars > this.props.maxBudgetDollars &&
                 this.props.maxBudgetDollars > 0) {
        cssClass = {className: 'error', maxBudgetDollars: auction.maxBudgetDollars};
        message = r._('your budget must not exceed $%(maxBudgetDollars)s');
      } else {
        if (r.sponsored.userIsSponsor) {
          auction.primary = true;
        } else if (auction.maxBidDollars < auction.minBidDollars) {
          formattedMinBidDollars = parseFloat(auction.minBidDollars).toFixed(2);
          cssClass = {className: 'error', minBid: formattedMinBidDollars};
          message = r._('Your campaign must be capable of claiming at least \
                         1,000 impressions per day. Please adjust your bid, \
                         budget, or schedule in order to enable this.');
        } else if (auction.bidDollars < auction.minBidDollars) {
          formattedMinBidDollars = parseFloat(auction.minBidDollars).toFixed(2);
          cssClass = {className: 'error', minBid: formattedMinBidDollars};
          message = r._('your bid must be at least $%(minBid)s');
        } else if (auction.bidDollars > auction.maxBidDollars) {
          formattedMaxBidDollars = parseFloat(auction.maxBidDollars).toFixed(2);
          cssClass = {className: 'error', maxBid: formattedMaxBidDollars};
          message = r._('your bid must not exceed $%(maxBid)s');
        } else {
          auction.primary = true;
        }
      }
      return [CampaignSet(null,
          InfoText(cssClass, message),
          CampaignOptionTable(null, CampaignOption(auction))
        ),
      ];
    } else {
      var requested = this.getRequestedOption();
      requested.primary = true;
      var maximized = this.getMaximizedOption();

      if (this.props.override) {
        if (requested.impressions <= this.state.available) {
          return [CampaignSet(null,
              InfoText(null, r._('the campaign you requested is available!')),
              CampaignOptionTable(null, CampaignOption(requested))
            ),
            InfoText(maximized,
                r._('the maximum budget available is $%(totalBudgetDollars)s (%(impressions)s impressions)')
            )
          ];
        }
        else {
          return CampaignSet(null,
            InfoText({
                className: 'error',
                available: this.state.available,
                target: this.props.targetName
              },
              r._('we expect to only have %(available)s impressions on %(target)s. ' +
                   'we may not fully deliver.')
            ),
            CampaignOptionTable(null, CampaignOption(requested))
          );
        }
      }
      else if (requested.totalBudgetDollars >= this.props.minBudgetDollars &&
               requested.impressions <= this.state.available) {
        var result = CampaignSet(null,
          InfoText(null, r._('the campaign you requested is available!')),
          CampaignOptionTable(null, CampaignOption(requested))
        );
        if (maximized.totalBudgetDollars > requested.totalBudgetDollars &&
            requested.totalBudgetDollars * 1.2 >= maximized.totalBudgetDollars &&
            this.state.available === this.state.totalAvailable) {
          var difference = maximized.totalBudgetDollars - requested.totalBudgetDollars;
          result = [result, CampaignSet(null,
            InfoText({ difference: difference.toFixed(2) },
              r._('want to maximize your campaign? for only $%(difference)s more ' +
                   'you can buy all available inventory for your selected dates!')
            ),
            CampaignOptionTable(null, CampaignOption(maximized))
          )];
        }
        else {
          result = [result, InfoText(maximized,
            r._('the maximum budget available is $%(totalBudgetDollars)s (%(impressions)s impressions)')
          )];
        }
        return result;
      }
      else if (requested.totalBudgetDollars < this.props.minBudgetDollars) {
        var minimal = this.getMinimizedOption();
        if (minimal.impressions <= this.state.available) {
          if (r.sponsored.userIsSponsor) {
            return CampaignSet(null,
              InfoText(null, r._('the campaign you requested is available!')),
              CampaignOptionTable(null, CampaignOption(requested))
            );
          } else {
            return CampaignSet(null,
              InfoText({ className: 'error' },
                r._('the campaign you requested is too small! this campaign is available:')
              ),
              CampaignOptionTable(null, CampaignOption(minimal))
            );
          }
        }
        else {
          return InfoText({ className: 'error' },
            r._('the campaign you requested is too small!')
          );
        }
      }
      else if (requested.impressions > this.state.available &&
               this.state.totalAvailable > this.state.available &&
               maximized.totalBudgetDollars > this.props.minBudgetDollars) {
        return CampaignSet(null,
          InfoText(null,
            r._('the campaign you requested is too big! the largest campaign ' +
                 'available is:')
          ),
          CampaignOptionTable(null, CampaignOption(maximized))
        );
      }
      else if (requested.impressions > this.state.available) {

        var options = [];
        if (maximized.totalBudgetDollars >= this.props.minBudgetDollars) {
          options.push(CampaignOption(maximized));
        }
        var reduced = this.getReducedWindowOption();
        if (reduced && reduced.totalBudgetDollars >= this.props.minBudgetDollars) {
          if (reduced.impressions > requested.impressions) {
            reduced.impressions = requested.impressions;
            reduced.totalBudgetDollars = requested.totalBudgetDollars;
          }
          options.push(CampaignOption(reduced));
        }
        if (options.length) {
          return CampaignSet(null,
            InfoText({
                className: 'error',
                target: this.props.targetName,
              },
              r._('we have insufficient available inventory targeting %(target)s to fulfill ' +
                   'your requested dates. the following campaigns are available:')
            ),
            CampaignOptionTable(null, options)
          );
        }
        else {
          r.analytics.fireFunnelEvent('ads', 'inventory-error');

          return InfoText({
              className: 'error',
              target: this.props.targetName
            },
            r._('inventory for %(target)s is sold out for your requested dates. ' +
                 'please try a different target or different dates.')
          );
        }
      }
    }

    return null;
  },

  formatDate: function(date) {
    return $.datepicker.formatDate('mm/dd/yy', date);
  },

  getBudget: function(impressions, requestedBudget) {
    if (this.getImpressions(requestedBudget) === impressions) {
      return requestedBudget;
    } else {
      return Math.floor((impressions / 1000) * this.props.cpm) / 100;
    }
  },

  getImpressions: function(bid) {
    return Math.floor(bid / this.props.cpm * 1000 * 100);
  },

  getOptionDates: function(startDate, duration) {
    var endDate = new Date();
    endDate.setTime(startDate.getTime());
    endDate.setDate(startDate.getDate() + duration);
    return {
      start: this.formatDate(startDate),
      end: this.formatDate(endDate),
    }
  },

  getFixedCPMOptionData: function(startDate, duration, impressions, requestedBudget) {
    var dates = this.getOptionDates(startDate, duration);
    return {
      start: dates.start,
      end: dates.end,
      totalBudgetDollars: this.getBudget(impressions, requestedBudget),
      impressions: Math.floor(impressions),
      isNew: this.props.isNew,
    };
  },

  getAuctionOption: function() {
    var dates = this.getOptionDates(this.props.dates[0], this.props.dates.length);
    return {
      start: dates.start,
      end: dates.end,
      totalBudgetDollars: this.props.totalBudgetDollars,
      costBasis: this.props.costBasis,
      bidDollars: this.props.bidDollars,
      isNew: this.props.isNew,
      minBidDollars: this.props.minBidDollars,
      maxBidDollars: this.props.maxBidDollars,
      minBudgetDollars: this.props.minBudgetDollars,
      maxBudgetDollars: this.props.maxBudgetDollars
    };
  },

  getRequestedOption: function() {
    return this.getFixedCPMOptionData(
      this.props.dates[0],
      this.props.dates.length,
      this.props.requested,
      this.props.totalBudgetDollars
    );
  },

  getMaximizedOption: function() {
    return this.getFixedCPMOptionData(
      this.props.dates[0],
      this.props.dates.length,
      this.state.available,
      this.props.totalBudgetDollars
    );
  },

  getMinimizedOption: function() {
    return this.getFixedCPMOptionData(
      this.props.dates[0],
      this.props.dates.length,
      this.getImpressions(this.props.minBudgetDollars),
      this.props.minBudgetDollars
    );
  },

  getReducedWindowOption: function() {
    var days = (1000 * 60 * 60 * 24);
    var maxOffset = (this.state.maxTime - this.props.dates[0].getTime()) / days | 0;
    var res =  r.sponsored.getMaximumRequest(
      this.props.inventory,
      this.getImpressions(this.props.minBudgetDollars),
      this.props.requested,
      maxOffset
    );
    if (res && res.days.length < this.props.dates.length) {
      return this.getFixedCPMOptionData(
        this.props.dates[res.offset],
        res.days.length,
        res.maxRequest,
        this.props.totalBudgetDollars
      );
    }
    else {
      return null;
    }
  },
});


var exports = r.sponsored = {
    set_form_render_fnc: function(render) {
        this.render = render;
    },

    render: function() {},

    init: function() {
        $("#sr-autocomplete").on("sr-changed blur", function() {
            r.sponsored.render()
        })
        this.targetValid = true;
        this.bidValid = true;
        this.inventory = {}
        this.campaignListColumns = $('.existing-campaigns thead th').length
        $("input[name='media_url_type']").on("change", this.mediaInputChange)

        this.initUploads();
    },

    initUploads: function() {
      $('.c-image-upload')
        .imageUpload()
        .on('failed.imageUpload', function(e, data) {
          alert(data.message);
        });
    },

    setup: function(inventory_by_sr, priceDict, isEmpty, userIsSponsor, forceAuction) {
        if (forceAuction) {
            this.isAuction = true;
        }
        this.inventory = inventory_by_sr
        this.priceDict = priceDict

        var $platformField = $('.platform-field');
        this.$platformInputs = $platformField.find('input[name=platform]');
        this.$mobileOSInputs = $platformField.find('.mobile-os-group input');
        this.$iOSDeviceInputs = $platformField.find('.ios-device input');
        this.$iOSMinSelect = $platformField.find('#ios_min');
        this.$iOSMaxSelect = $platformField.find('#ios_max');
        this.$androidDeviceInputs = $platformField.find('.android-device input');
        this.$androidMinSelect = $platformField.find('#android_min');
        this.$androidMaxSelect = $platformField.find('#android_max');
        this.$deviceAndVersionInputs = $platformField.find('input[name="os_versions"]');

        var render = this.render.bind(this);

        $('.platform-field input, .platform-field select').on('change', render);

        if (isEmpty) {
            this.render();
            init_startdate()
            init_enddate()
            $("#campaign").find("button[name=create]").show().end()
                .find("button[name=save]").hide().end()
        }
        this.userIsSponsor = userIsSponsor
    },

    setupAuctionFields: function($form, targeting, timing) {
        if (this.isAuction) {
            $('.auction-field').show();
            $('.fixed-cpm-field').hide();
            $('.priority-field').hide();
            $('#is_auction_true').prop('checked', true);

            this.setup_auction($form, targeting, timing);
            this.check_bid_dollars($form);
        } else {
            $('.auction-field').hide();
            $('.fixed-cpm-field').show();
            $('.priority-field').show();
            $('#is_auction_false').prop('checked', true);
        }
    },

    setupLiveEditing: function(isLive) {
        var $budgetChangeWarning = $('.budget-unchangeable-warning');
        var $targetChangeWarning = $('.target-change-warning');
        if (isLive && !this.userIsSponsor) {
            $budgetChangeWarning.show();
            $targetChangeWarning.show();
            $('#total_budget_dollars').prop('disabled', true);
            $('#startdate').prop('disabled', true);
        } else {
            $budgetChangeWarning.hide();
            $targetChangeWarning.hide();
            $('#total_budget_dollars').removeAttr('disabled');
            $('#startdate').removeAttr('disabled');
        }
    },

    setup_collection_selector: function() {
        var $collectionSelector = $('.collection-selector');
        var $collectionList = $('.form-group-list');
        var $collections = $collectionList.find('.form-group .label-group');
        var collectionCount = $collections.length;
        var collectionHeight = $collections.eq(0).outerHeight();
        var $subredditList = $('.collection-subreddit-list ul');
        var $collectionLabel = $('.collection-subreddit-list .collection-label');
        var $frontpageLabel = $('.collection-subreddit-list .frontpage-label');

        var subredditNameTemplate = _.template('<% _.each(sr_names, function(name) { %>'
            + ' <li><%= name %></li> <% }); %>');
        var render_subreddit_list = _.bind(function(collection) {
            if (collection === 'none' ||
                    typeof this.collectionsByName[collection] === 'undefined') {
                return '';
            }
            else {
                return subredditNameTemplate(this.collectionsByName[collection]);
            }
        }, this);

        var collapse = _.bind(function() {
            this.collapse_collection_selector();
            this.render();
        }, this);

        this.collapse_collection_selector = function collapse_widget() {
            $('body').off('click', collapse);
            var $selected = get_selected();
            var index = $collections.index($selected);
            $collectionSelector.addClass('collapsed').removeClass('expanded');
            $collectionList.innerHeight(collectionHeight)
                .css('top', -collectionHeight * index);
            var val = $collectionList.find('input[type=radio]:checked').val();
            var subredditListItems = render_subreddit_list(val);
            $subredditList.html(subredditListItems);
            if (val === 'none') {
                $collectionLabel.hide();
                $frontpageLabel.show();
            }
            else {
                $collectionLabel.show();
                $frontpageLabel.hide();
            }
        }

        function expand() {
            $('body').on('click', collapse);
            $collectionSelector.addClass('expanded').removeClass('collapsed');
            $collectionList
                .innerHeight(collectionCount * collectionHeight)
                .css('top', 0);
        }

        function get_selected() {
            return $collectionList.find('input[type=radio]:checked')
                .siblings('.label-group')
        }

        $collectionSelector
            .removeClass('uninitialized')
            .on('click', '.label-group', function(e) {
                if ($collectionSelector.is('.collapsed')) {
                    expand();
                }
                else {
                    var $selected = get_selected();
                    if ($selected[0] !== this) {
                        $selected.siblings('input').prop('checked', false);
                        $(this).siblings('input').prop('checked', 'checked');
                    }
                    collapse();
                }
                return false;
            });

        collapse();
    },

    toggleFrequency: function() {
        var prevChecked = this.frequency_capped;
        var currentlyChecked = ($('input[name="frequency_capped"]:checked').val() === 'true');
        if (prevChecked != currentlyChecked) {
            $('.frequency-cap-field').toggle('slow');
            this.frequency_capped = currentlyChecked;
            this.render();
        }
    },

    toggleAuctionFields: function() {
        var prevChecked = this.isAuction;
        var currentlyChecked = ($('input[name="is_auction"]:checked').val() === 'true');
        if (prevChecked != currentlyChecked) {
            $('.auction-field').toggle();
            $('.fixed-cpm-field').toggle();
            $('.priority-field').toggle();
            this.isAuction = currentlyChecked;
            this.render();
        }
    },

    setup_frequency_cap: function(frequency_capped) {
        this.frequency_capped = !!frequency_capped;
    },

    setup_mobile_targeting: function(mobileOS, iOSDevices, iOSVersions,
                                     androidDevices, androidVersions) {
      this.mobileOS = mobileOS;
      this.iOSDevices = iOSDevices;
      this.iOSVersions = iOSVersions;
      this.androidDevices = androidDevices;
      this.androidVersions = androidVersions;
    },

    setup_geotargeting: function(regions, metros) {
        this.regions = regions
        this.metros = metros
    },

    setup_collections: function(collections, defaultValue) {
        defaultValue = defaultValue || 'none';

        this.collections = [{
            name: 'none',
            sr_names: null,
            description: 'influencers on reddit’s highest trafficking page',
        }].concat(collections || []);

        this.collectionsByName = _.reduce(collections, function(obj, item) {
            if (item.sr_names) {
                item.sr_names = item.sr_names.slice(0, 20);
            }
            obj[item.name] = item;
            return obj;
        }, {});

        var template = _.template('<label class="form-group">'
          + '<input type="radio" name="collection" value="<%= name %>"'
          + '    <% print(name === \'' + defaultValue + '\' ? "checked=\'checked\'" : "") %>/>'
          + '  <div class="label-group">'
          + '    <span class="label"><% print(name === \'none\' ? \'Reddit front page\' : name) %></span>'           + '    <small class="description"><%= description %></small>'
          + '  </div>'
          + '</label>');

        var rendered = _.map(this.collections, template).join('');
        $(_.bind(function() {
            $('.collection-selector .form-group-list').html(rendered);
            this.setup_collection_selector();
            this.render_campaign_dashboard_header();
        }, this))
    },

    get_dates: function(startdate, enddate) {
        var start = $.datepicker.parseDate('mm/dd/yy', startdate),
            end = $.datepicker.parseDate('mm/dd/yy', enddate),
            ndays = Math.round((end - start) / (1000 * 60 * 60 * 24)),
            dates = []

        for (var i=0; i < ndays; i++) {
            var d = new Date(start.getTime())
            d.setDate(start.getDate() + i)
            dates.push(d)
        }
        return dates
    },

    get_inventory_key: function(srname, collection, geotarget, platform) {
        var inventoryKey = collection ? '#' + collection : srname
        inventoryKey += "/" + platform
        if (geotarget.country != "") {
            inventoryKey += "/" + geotarget.country
        }
        if (geotarget.metro != "") {
            inventoryKey += "/" + geotarget.metro
        }
        return inventoryKey
    },

    needs_to_fetch_inventory: function(targeting, timing) {
        var dates = timing.dates,
            inventoryKey = targeting.inventoryKey;
        return _.some(dates, function(date) {
            var datestr = $.datepicker.formatDate('mm/dd/yy', date);
            if (_.has(this.inventory, inventoryKey) && _.has(this.inventory[inventoryKey], datestr)) {
                return false;
            }
            else {
                r.debug('need to fetch ' + datestr + ' for ' + inventoryKey);
                return true;
            }
        }, this);
    },

    fetch_inventory: function(targeting, timing) {
        var srname = targeting.sr,
            collection = targeting.collection,
            geotarget = targeting.geotarget,
            platform = targeting.platform,
            inventoryKey = targeting.inventoryKey,
            dates = timing.dates;

        dates.sort(function(d1,d2){return d1 - d2})
        var end = new Date(dates[dates.length-1].getTime())
        end.setDate(end.getDate() + 5)
        return $.ajax({
            type: 'GET',
            url: '/api/check_inventory.json',
            data: {
                sr: srname,
                collection: collection,
                country: geotarget.country,
                region: geotarget.region,
                metro: geotarget.metro,
                startdate: $.datepicker.formatDate('mm/dd/yy', dates[0]),
                enddate: $.datepicker.formatDate('mm/dd/yy', end),
                platform: platform
            },
        });
    },

    get_check_inventory: function(targeting, timing) {
        var inventoryKey = targeting.inventoryKey;
        if (this.needs_to_fetch_inventory(targeting, timing)) {
            return this.fetch_inventory(targeting, timing).then(
                function(data) {
                    if (!r.sponsored.inventory[inventoryKey]) {
                        r.sponsored.inventory[inventoryKey] = {}
                    }

                    for (var datestr in data.inventory) {
                        if (!r.sponsored.inventory[inventoryKey][datestr]) {
                            r.sponsored.inventory[inventoryKey][datestr] = data.inventory[datestr]
                        }
                    }
                });
        } else {
            return true
        }
    },

    get_booked_inventory: function($form, srname, geotarget, isOverride) {
        var campaign_name = $form.find('input[name="campaign_name"]').val()
        if (!campaign_name) {
            return {}
        }

        var $campaign_row = $('.existing-campaigns .' + campaign_name)
        if (!$campaign_row.length) {
            return {}
        }

        if (!$campaign_row.data('paid')) {
            return {}
        }

        var existing_srname = $campaign_row.data("targeting")
        if (srname != existing_srname) {
            return {}
        }

        var existing_country = $campaign_row.data("country")
        if (geotarget.country != existing_country) {
            return {}
        }

        var existing_metro = $campaign_row.data("metro")
        if (geotarget.metro != existing_metro) {
            return {}
        }

        var existingOverride = $campaign_row.data("override")
        if (isOverride != existingOverride) {
            return {}
        }

        var startdate = $campaign_row.data("startdate"),
            enddate = $campaign_row.data("enddate"),
            dates = this.get_dates(startdate, enddate),
            bid = $campaign_row.data("bid"),
            cpm = $campaign_row.data("cpm"),
            ndays = this.duration_from_dates(startdate, enddate),
            impressions = this.calc_impressions(bid, cpm),
            daily = Math.floor(impressions / ndays),
            booked = {}

        _.each(dates, function(date) {
            var datestr = $.datepicker.formatDate('mm/dd/yy', date)
            booked[datestr] = daily
        })
        return booked

    },

    getAvailableImpsByDay: function(dates, booked, inventoryKey) {
        return _.map(dates, function(date) {
            var datestr = $.datepicker.formatDate('mm/dd/yy', date);
            var daily_booked = booked[datestr] || 0;
            return r.sponsored.inventory[inventoryKey][datestr] + daily_booked;
        });
    },

    setup_auction: function($form, targeting, timing) {
        var dates = timing.dates,
            totalBudgetDollars = parseFloat($("#total_budget_dollars").val()),
            costBasisValue = $form.find('#cost_basis').val(),
            bidDollars = $form.find('#bid_dollars').val() || 0.,
            minBidDollars = r.sponsored.get_min_bid_dollars(),
            maxBidDollars = r.sponsored.get_lowest_max_bid_dollars($form),
            minBudgetDollars = r.sponsored.get_min_budget_dollars(),
            maxBudgetDollars = r.sponsored.get_max_budget_dollars();

        React.renderComponent(
          CampaignCreator({
            totalBudgetDollars: totalBudgetDollars,
            dates: dates,
            isNew: $form.find('#is_new').val() === 'true',
            minBidDollars: minBidDollars,
            maxBidDollars: maxBidDollars,
            maxBudgetDollars: parseFloat(maxBudgetDollars),
            minBudgetDollars: parseFloat(minBudgetDollars),
            targetName: targeting.displayName,
            costBasis: costBasisValue.toUpperCase(),
            bidDollars: parseFloat(bidDollars),
          }),
          document.getElementById('campaign-creator')
        );
    },

    setup_house: function($form, targeting, timing, isOverride) {
      $.when(r.sponsored.get_check_inventory(targeting, timing)).then(
        function() {
          var booked = this.get_booked_inventory($form, targeting.sr,
                                                 targeting.geotarget, isOverride);
          var availableByDate = this.getAvailableImpsByDay(timing.dates, booked,
                                                           targeting.inventoryKey);
          var totalImpsAvailable = _.reduce(availableByDate, sum, 0);

          React.renderComponent(
            React.DOM.div(null,
              CampaignSet(null,
                InfoText(null, r._('house campaigns, man.')),
                CampaignOptionTable(null,
                  CampaignOption({
                    bid: null,
                    end: timing.enddate,
                    impressions: 'unsold ',
                    isNew: $form.find('#is_new').val() === 'true',
                    primary: true,
                    start: timing.startdate,
                  })
                )
              ),
              InfoText({impressions: totalImpsAvailable},
                  r._('maximum possible impressions: %(impressions)s')
              )
            ),
            document.getElementById('campaign-creator')
          );
        }.bind(this)
      );

    },

    check_inventory: function($form, targeting, timing, budget, isOverride) {
        var totalBudgetDollars = budget.totalBudgetDollars,
            cpm = budget.cpm,
            requested = budget.impressions,
            daily_request = Math.floor(requested / timing.duration),
            inventoryKey = targeting.inventoryKey,
            booked = this.get_booked_inventory($form, targeting.sr,
                    targeting.geotarget, isOverride),
            minBudgetDollars = r.sponsored.get_min_budget_dollars(),
            maxBudgetDollars = r.sponsored.get_max_budget_dollars();

        $.when(r.sponsored.get_check_inventory(targeting, timing)).then(
            function() {
                var dates = timing.dates;
                var availableByDay = this.getAvailableImpsByDay(dates, booked, inventoryKey)
                React.renderComponent(
                  CampaignCreator({
                    totalBudgetDollars: totalBudgetDollars,
                    cpm: cpm,
                    dates: timing.dates,
                    inventory: availableByDay,
                    isNew: $form.find('#is_new').val() === 'true',
                    maxBudgetDollars: parseFloat(maxBudgetDollars),
                    minBudgetDollars: parseFloat(minBudgetDollars),
                    override: isOverride,
                    requested: requested,
                    targetName: targeting.displayName,
                  }),

                  document.getElementById('campaign-creator')
                );
            }.bind(this),
            function () {
                React.renderComponent(
                  CampaignSet(null,
                    InfoText(null,
                      r._('sorry, there was an error retrieving available impressions. ' +
                           'please try again later.')
                    )
                  ),
                  document.getElementById('campaign-creator')
                );
            }
        )
    },

    duration_from_dates: function(start, end) {
        return Math.round((Date.parse(end) - Date.parse(start)) / (86400*1000))
    },

    get_total_budget: function($form) {
        return parseFloat($form.find('*[name="total_budget_dollars"]').val()) || 0
    },

    get_cpm: function($form) {
        var isMetroGeotarget = $('#metro').val() !== null && !$('#metro').is(':disabled');
        var metro = $('#metro').val();
        var country = $('#country').val();
        var isGeotarget = country !== '' && !$('#country').is(':disabled');
        var isSubreddit = $form.find('input[name="targeting"][value="one"]').is(':checked');
        var collectionVal = $form.find('input[name="collection"]:checked').val();
        var isFrontpage = !isSubreddit && collectionVal === 'none';
        var isCollection = !isSubreddit && !isFrontpage;
        var sr = isSubreddit ? $form.find('*[name="sr"]').val() : '';
        var collection = isCollection ? collectionVal : null;
        var prices = [];

        if (isMetroGeotarget) {
            var metroKey = metro + country;
            prices.push(this.priceDict.METRO[metro] || this.priceDict.METRO_DEFAULT);
        } else if (isGeotarget) {
            prices.push(this.priceDict.COUNTRY[country] || this.priceDict.COUNTRY_DEFAULT);
        }

        if (isFrontpage) {
            prices.push(this.priceDict.COLLECTION_DEFAULT);
        } else if (isCollection) {
            prices.push(this.priceDict.COLLECTION[collectionVal] || this.priceDict.COLLECTION_DEFAULT);
        } else {
            prices.push(this.priceDict.SUBREDDIT[sr] || this.priceDict.SUBREDDIT_DEFAULT);
        }

        return _.max(prices);
    },

    getPlatformTargeting: function() {
      var platform = this.$platformInputs.filter(':checked').val();
      var isMobile = platform === 'mobile' || platform === 'all';

      function mapTargets(target) {
        targets = target.filter(':checked').map(function() {
          return $(this).attr('value');
        }).toArray().join(',');
        return targets.length === 1 ? targets[0] : targets
      }

      function getSelect(target) {
        return target.find(':selected').val();
      }

      var targets;
      if (isMobile) {
        targets = {
          os: mapTargets(this.$mobileOSInputs),
          deviceAndVersion: mapTargets(this.$deviceAndVersionInputs),
          iOSDevices: mapTargets(this.$iOSDeviceInputs),
          iOSVersionRange: (getSelect(this.$iOSMinSelect) + ','
            + getSelect(this.$iOSMaxSelect)),
          iOSMinVersion: getSelect(this.$iOSMinSelect),
          iOSMaxVersion: getSelect(this.$iOSMaxSelect),
          androidDevices: mapTargets(this.$androidDeviceInputs),
          androidVersionRange: (getSelect(this.$androidMinSelect) + ','
            + getSelect(this.$androidMaxSelect)),
          androidMinVersion: getSelect(this.$androidMinSelect),
          androidMaxVersion: getSelect(this.$androidMaxSelect),
        };
      } else {
        targets = {
          os: null,
          deviceAndVersion: null,
          iOSDevices: null,
          iOSVersionRange: null,
          iOSMinVersion: null,
          iOSMaxVersion: null,
          androidDevices: null,
          androidVersionRange: null,
          androidMinVersion: null,
          androidMaxVersion: null,
        };
      }

      return $.extend({
        platform: platform,
        isMobile: isMobile,
      }, targets);
    },

    get_targeting: function($form) {
        var isSubreddit = $form.find('input[name="targeting"][value="one"]').is(':checked'),
            collectionVal = $form.find('input[name="collection"]:checked').val(),
            isFrontpage = !isSubreddit && collectionVal === 'none',
            isCollection = !isSubreddit && !isFrontpage,
            type = isFrontpage ? 'frontpage' : isCollection ? 'collection' : 'subreddit',
            sr = isSubreddit ? $form.find('*[name="sr"]').val() : '',
            collection = isCollection ? collectionVal : null,
            canGeotarget = isFrontpage || this.userIsSponsor || this.isAuction,
            country = canGeotarget && $('#country').val() || '',
            region = canGeotarget && $('#region').val() || '',
            metro = canGeotarget && $('#metro').val() || '',
            geotarget = {'country': country, 'region': region, 'metro': metro},
            inventoryKey = this.get_inventory_key(sr, collection, geotarget, platform),
            isValid = isFrontpage || (isSubreddit && sr) || (isCollection && collection);

        var displayName;
        switch(type) {
            case 'frontpage':
                displayName = 'the frontpage'
                break;
            case 'subreddit':
                displayName = '/r/' + sr
                break;
            default:
                displayName = collection
        }

        if (canGeotarget) {
            var geoStrings = []
            if (country) {
                if (region) {
                    if (metro) {
                        var metroName = $('#metro option[value="'+metro+'"]').text()
                        // metroName is in the form 'metro, state abbreviation';
                        // since we want 'metro, full state', split the metro
                        // from the state, then add the full state separately
                        geoStrings.push(metroName.split(',')[0])
                    }
                    var regionName = $('#region option[value="'+region+'"]').text()
                    geoStrings.push(regionName)
                }
                var countryName = $('#country option[value="'+country+'"]').text()
                geoStrings.push(countryName)
            }

            if (geoStrings.length > 0) {
                displayName += ' in '
                displayName += geoStrings.join(', ')
            }
        }

        var targets = {
            'type': type,
            'displayName': displayName,
            'isValid': isValid,
            'sr': sr,
            'collection': collection,
            'canGeotarget': canGeotarget,
            'geotarget': geotarget,
        };

        if (this.$platformInputs) {
            var platformTargets = this.getPlatformTargeting();

            var os = platformTargets.os;
            var platform = platformTargets.platform;
            var iOSDevices = platformTargets.iOSDevices;
            var iOSVersionRange = platformTargets.iOSVersionRange;
            var androidDevices = platformTargets.androidDevices;
            var androidVersionRange = platformTargets.androidVersionRange;

            platformTargetsList = ['platform',
                                   'iOSDevices',
                                   'iOSVersionRange',
                                   'androidDevices',
                                   'androidVersionRange',];

            platformTargetsList.forEach(function(platformStr) {
              targets[platformStr] = eval(platformStr)
            });

            targets['inventoryKey'] = this.get_inventory_key(sr, collection, geotarget, platform);
        } else {
            targets['inventoryKey'] = this.get_inventory_key(sr, collection, geotarget);
        }

        return targets;
    },

    get_timing: function($form) {
        var startdate = $form.find('*[name="startdate"]').val(),
            enddate = $form.find('*[name="enddate"]').val(),
            duration = this.duration_from_dates(startdate, enddate),
            dates = r.sponsored.get_dates(startdate, enddate);

        return {
            'startdate': startdate,
            'enddate': enddate,
            'duration': duration,
            'dates': dates,
        }
    },

    get_budget: function($form) {
        var totalBudgetDollars = this.get_total_budget($form),
            cpm = this.get_cpm($form),
            impressions = this.calc_impressions(totalBudgetDollars, cpm);

        return {
            'totalBudgetDollars': totalBudgetDollars,
            'cpm': cpm,
            'impressions': impressions,
        };
    },

    get_priority: function($form) {
        var priority = $form.find('*[name="priority"]:checked'),
            isOverride = priority.data("override"),
            isHouse = priority.data("house");

        return {
            isOverride: isOverride,
            isHouse: isHouse,
        };
    },


    get_reporting: function($form) {
        var link_text = $form.find('[name=link_text]').val(),
            owner = $form.find('[name=owner]').val();

        return {
            link_text: link_text,
            owner: owner,
        };
    },

    get_campaigns: function($list, $form) {
        var campaignRows = $list.find('.existing-campaigns tbody tr').toArray();
        var collections = this.collectionsByName;
        var fixedCPMCampaigns = 0;
        var fixedCPMSubreddits = {};
        var totalFixedCPMBudgetDollars = 0;
        var auctionCampaigns = 0;
        var auctionSubreddits = {};
        var totalAuctionBudgetDollars = 0;
        var totalImpressions = 0;

        function mapSubreddit(name, subreddits) {
            subreddits[name] = 1;
        }

        function getSubredditsByCollection(name) {
            return collections[name] && collections[name].sr_names || null;
        }

        function mapCollection(name, subreddits) {
            var subredditNames = getSubredditsByCollection(name);
            if (subredditNames) {
                _.each(subredditNames, function(subredditName) {
                    mapSubreddit(subredditName, subreddits);
                });
            }
        }

        _.each(campaignRows, function(row) {
            var data = $(row).data();
            var isCollection = (data.targetingCollection === 'True');
            var mappingFunction = isCollection ? mapCollection : mapSubreddit;
            var budget = parseFloat(data.total_budget_dollars, 10);

            if (data.is_auction === 'True') {
                auctionCampaigns++;
                mappingFunction(data.targeting, auctionSubreddits);
                totalAuctionBudgetDollars += budget;
            } else {
                fixedCPMCampaigns++;
                mappingFunction(data.targeting, fixedCPMSubreddits);
                totalFixedCPMBudgetDollars += budget;
                var bid = data.bid_dollars;
                var impressions = Math.floor(budget / bid * 1000);
                totalImpressions += impressions;
            }
        });

        return {
            count: campaignRows.length,
            fixedCPMCampaigns: fixedCPMCampaigns,
            auctionCampaigns: auctionCampaigns,
            fixedCPMSubreddits: fixedCPMSubreddits,
            auctionSubreddits: _.keys(auctionSubreddits),
            fixedCPMSubreddits: _.keys(fixedCPMSubreddits),
            prettyTotalAuctionBudgetDollars: '$' + totalAuctionBudgetDollars.toFixed(2),
            prettyTotalFixedCPMBudgetDollars: '$' + totalFixedCPMBudgetDollars.toFixed(2),
            totalImpressions: r.utils.prettyNumber(totalImpressions),
        };
    },

    auction_dashboard_help_template: _.template('<p>there '
        + '<% auctionCampaigns > 1 ? print("are") : print("is") %> '
        + '<%= auctionCampaigns %> auction campaign'
        + '<% auctionCampaigns > 1 && print("s") %> with a total budget of '
        + '<%= prettyTotalAuctionBudgetDollars %> in '
        + '<%= auctionSubreddits.length %> subreddit'
        + '<% auctionSubreddits.length > 1 && print("s") %></p>'),

    fixed_cpm_dashboard_help_template: _.template('<p>there '
        + '<% fixedCPMCampaigns > 1 ? print("are") : print("is") %> '
        + '<%= fixedCPMCampaigns %> fixed CPM campaign'
        + '<% fixedCPMCampaigns > 1 && print("s") %> with a total budget of '
        + '<%= prettyTotalFixedCPMBudgetDollars %> in '
        + '<%= fixedCPMSubreddits.length %> subreddit'
        + '<% fixedCPMSubreddits.length > 1 && print("s") %>, amounting to a '
        + 'total of <%= totalImpressions %> impressions</p>'),

    render_campaign_dashboard_header: function() {
        var $form = $("#campaign");
        var campaigns = this.get_campaigns($('.campaign-list'), $form);
        var $campaignDashboardHeader = $('.campaign-dashboard header');
        if (campaigns.count) {
            var templateText = '';
            if (campaigns.auctionCampaigns > 0) {
                templateText += this.auction_dashboard_help_template(campaigns);
            }
            if (campaigns.fixedCPMCampaigns > 0) {
                templateText += this.fixed_cpm_dashboard_help_template(campaigns);
            }
            $campaignDashboardHeader
                .find('.help').show().html(templateText).end()
                .find('.error').hide();
        }
        else {
            $campaignDashboardHeader
                .find('.error').show().end()
                .find('.help').hide();
        }
    },

    on_date_change: function() {
        this.render()
    },

    on_bid_change: function() {
        this.render()
    },

    on_cost_basis_change: function() {
        this.render();
    },

    on_budget_change: function() {
        this.render()
    },

    on_impression_change: function() {
        var $form = $("#campaign"),
            cpm = this.get_cpm($form),
            impressions = parseInt($form.find('*[name="impressions"]').val().replace(/,/g, "") || 0),
            totalBudgetDollars = this.calc_budget_dollars_from_impressions(impressions, cpm),
            $totalBudgetDollars = $form.find('*[name="total_budget_dollars"]')
        $totalBudgetDollars.val(totalBudgetDollars)
        $totalBudgetDollars.trigger("change")
    },

    on_frequency_cap_change: function() {
        this.render();
    },

    validateDeviceAndVersion: function(os, generalData, osData) {
      var deviceError = false;
      var versionError = false;
      /* if OS is selected to target, populate hidden inputs */
      if (generalData.platformTargetingOS.indexOf(os) !== -1) {
        osData.deviceHiddenInput.val(osData.platformTargetingDevices);
        osData.versionHiddenInput.val(osData.platformTargetingVersions);
        osData.group.show();

        if (generalData.deviceAndVersion == 'filter') {
          /* check that at least one devices is selected */
          if (!osData.deviceHiddenInput.val()) {
            deviceError = true;
          }

          /* check that min version is less-or-equal-to max */
          var versions = osData.versionHiddenInput.val().split(',');
          if ((versions[1] !== '') && (versions[0] > versions[1])) {
            versionError = true;
          }
        }
      } else {
        osData.deviceHiddenInput.val('');
        osData.versionHiddenInput.val('');
        osData.group.hide();
      }
      return {'deviceError': deviceError,
              'versionError': versionError}
    },

    fill_campaign_editor: function() {

        var $form = $("#campaign");
        var platformTargeting = this.getPlatformTargeting();
        var platformOverride = platformTargeting.isMobile && platformTargeting.platform === 'mobile';

        this.currentPlatform = platformTargeting.platform;

        var priority = this.get_priority($form),
            targeting = this.get_targeting($form),
            timing = this.get_timing($form),
            ndays = timing.duration,
            budget = this.get_budget($form),
            cpm = budget.cpm,
            impressions = budget.impressions,
            validTargeting = targeting.isValid;

        var durationInDays = ndays + " " + ((ndays > 1) ? r._("days") : r._("day"))
        $(".duration").text(durationInDays)
        var totalBudgetDollars = parseFloat($("#total_budget_dollars").val())
        var dailySpend = totalBudgetDollars / parseInt(durationInDays)
        $(".daily-max-spend").text((isNaN(dailySpend) ? 0.00 : dailySpend).toFixed(2));

        $(".price-info").text(r._("$%(cpm)s per 1,000 impressions").format({cpm: (cpm/100).toFixed(2)}))
        $form.find('*[name="impressions"]').val(r.utils.prettyNumber(impressions))
        $(".OVERSOLD").hide()

        var costBasisValue = $form.find('#cost_basis').val();
        var $costBasisLabel = $form.find('.cost-basis-label');
        var $pricingMessageDiv = $form.find('.pricing-message');

        var pricingMessage = (costBasisValue === 'cpc') ? 'click' : '1,000 impressions';

        $costBasisLabel.text(costBasisValue);
        $pricingMessageDiv.text('Set how much you\'re willing to pay per ' + pricingMessage);

        var $mobileOSGroup = $('.mobile-os-group');
        var $mobileOSHiddenInput = $('#mobile_os');

        var $OSDeviceGroup = $('.os-device-group');
        var $iOSDeviceHiddenInput = $('#ios_device');
        var $iOSVersionHiddenInput = $('#ios_version_range');
        var $androidDeviceHiddenInput = $('#android_device');
        var $androidVersionHiddenInput = $('#android_version_range');

        if (platformTargeting.isMobile) {
          var $mobileOSError = $mobileOSGroup.find('.error');
          var $OSDeviceError = $OSDeviceGroup.find('.error.device-error');
          var $OSVersionError = $OSDeviceGroup.find('.error.version-error');

          $mobileOSGroup.show();
          $mobileOSHiddenInput.val(platformTargeting.os || '');

          $OSDeviceGroup.show();

          if (!platformTargeting.os) {
            $mobileOSError.show();
          } else {
            $mobileOSError.hide();
          }

          var $deviceVersionGroup = $('.device-version-group');
          var $deviceAndVersion = (platformTargeting.deviceAndVersion || 'all')

          if ($deviceAndVersion === 'all') {
            $deviceVersionGroup.hide();
            $OSDeviceError.hide();
            $OSVersionError.hide();
            $iOSDeviceHiddenInput.val('');
            $iOSVersionHiddenInput.val('');
            $androidDeviceHiddenInput.val('');
            $androidVersionHiddenInput.val('');
          } else {
            $deviceVersionGroup.show();

            $iOSGroup = $('.ios-group');
            $androidGroup = $('.android-group');

            var generalData = {
              platformTargetingOS: platformTargeting.os,
              deviceAndVersion: $deviceAndVersion,
            }

            var iOSData = {
              deviceHiddenInput: $iOSDeviceHiddenInput,
              versionHiddenInput: $iOSVersionHiddenInput,
              platformTargetingDevices: platformTargeting.iOSDevices,
              platformTargetingVersions: platformTargeting.iOSVersionRange,
              group: $iOSGroup,
            }

            var androidData = {
              deviceHiddenInput: $androidDeviceHiddenInput,
              versionHiddenInput: $androidVersionHiddenInput,
              platformTargetingDevices: platformTargeting.androidDevices,
              platformTargetingVersions: platformTargeting.androidVersionRange,
              group: $androidGroup,
            }

            var iOSErrors = this.validateDeviceAndVersion('iOS', generalData, iOSData);
            var androidErrors = this.validateDeviceAndVersion('Android', generalData, androidData);
            var iOSDeviceError = iOSErrors['deviceError']
            var iOSVersionError = iOSErrors['versionError'];
            var androidDeviceError = androidErrors['deviceError'];
            var androidVersionError = androidErrors['versionError'];

            if (iOSDeviceError || androidDeviceError) {
              $OSDeviceError.show();
            } else {
              $OSDeviceError.hide();
            }

            if (iOSVersionError || androidVersionError) {
              $OSVersionError.show();
            } else {
              $OSVersionError.hide();
            }
          }
        } else {
          $mobileOSHiddenInput.val('');
          $iOSDeviceHiddenInput.val('');
          $iOSVersionHiddenInput.val('');
          $androidDeviceHiddenInput.val('');
          $androidVersionHiddenInput.val('');
          $mobileOSGroup.hide();
          $OSDeviceGroup.hide();
        }

        if (targeting.isValid) {
            this.targetValid = true;
            this.enable_form($form);
        } else {
            this.targetValid = false;
            this.disable_form($form);
        }

        if (priority.isHouse) {
            this.hide_budget()
        } else {
            this.show_budget()
            this.check_budget($form)
        }

        this.setupAuctionFields($form, targeting, timing);
        if (priority.isHouse && validTargeting) {
            this.setup_house($form, targeting, timing, priority.isOverride);
        } else if (!this.isAuction && validTargeting) {
            this.check_inventory($form, targeting, timing, budget, priority.isOverride)
        }

        if (targeting.canGeotarget) {
            this.enable_geotargeting();
        } else {
            this.disable_geotargeting();
        }

        var $frequencyCapped = $form.find('[name=frequency_capped]');
        if (this.frequency_capped === null) {
            this.frequency_capped = !!$frequencyCapped.val();
        }
        // In some cases, the frequency cap is automatically set, but no
        // frequencyCapped field is rendered; if so, skip this check
        if (this.frequency_capped && $frequencyCapped.length > 0) {
            var $frequencyCapField = $form.find('#frequency_cap'),
                frequencyCapValue = $frequencyCapField.val(),
                frequencyCapMin = $frequencyCapField.data('frequency_cap_min'),
                $frequencyCapError = $('.frequency-cap-field').find('.error');

            if (frequencyCapValue < frequencyCapMin || _.isNaN(parseInt(frequencyCapValue, 10))) {
                $frequencyCapError.show();
                this.disable_form($form);
            } else {
                $frequencyCapError.hide();
                this.enable_form($form);
            }
        }

        // If campaign is new, don't set up live editing fields
        if ($form.find('#is_new').val() === 'true') {
            this.setupLiveEditing(false);
        }
    },

    disable_geotargeting: function() {
        $('.geotargeting-selects').find('select').prop('disabled', true).end().hide();
        $('.geotargeting-disabled').show();
    },

    enable_geotargeting: function() {
        $('.geotargeting-selects').find('select').prop('disabled', false).end().show();
        $('.geotargeting-disabled').hide();
    },

    disable_form: function($form) {
        $form.find('button[class*="campaign-button"]')
            .prop("disabled", true)
            .addClass("disabled");
    },

    enable_form: function($form) {
        if (this.bidValid && this.targetValid) {
            $form.find('button[class*="campaign-button"]')
                .prop("disabled", false)
                .removeClass("disabled");
        }
    },

    hide_budget: function() {
        $('.budget-field').css('display', 'none');
    },

    show_budget: function() {
        $('.budget-field').css('display', 'block');
    },

    subreddit_targeting: function() {
        $('.subreddit-targeting').find('*[name="sr"]').prop("disabled", false).end().slideDown();
        $('.collection-targeting').find('*[name="collection"]').prop("disabled", true).end().slideUp();
        this.render()
    },

    collection_targeting: function() {
        $('.subreddit-targeting').find('*[name="sr"]').prop("disabled", true).end().slideUp();
        $('.collection-targeting').find('*[name="collection"]').prop("disabled", false).end().slideDown();
        this.render()
    },

    priority_changed: function() {
        this.render()
    },

    update_regions: function() {
        var $country = $('#country'),
            $region = $('#region'),
            $metro = $('#metro')

        $region.find('option').remove().end().hide()
        $metro.find('option').remove().end().hide()
        $region.prop('disabled', true)
        $metro.prop('disabled', true)

        if (_.has(this.regions, $country.val())) {
            _.each(this.regions[$country.val()], function(item) {
                var code = item[0],
                    name = item[1],
                    selected = item[2]

                $('<option/>', {value: code, selected: selected}).text(name).appendTo($region)
            })
            $region.prop('disabled', false)
            $region.show()
        }
    },

    update_metros: function() {
        var $region = $('#region'),
            $metro = $('#metro')

        $metro.find('option').remove().end().hide()
        if (_.has(this.metros, $region.val())) {
            _.each(this.metros[$region.val()], function(item) {
                var code = item[0],
                    name = item[1],
                    selected = item[2]

                $('<option/>', {value: code, selected: selected}).text(name).appendTo($metro)
            })
            $metro.prop('disabled', false)
            $metro.show()
        }
    },

    country_changed: function() {
        this.update_regions()
        this.render()
    },

    region_changed: function() {
        this.update_metros()
        this.render()
    },

    metro_changed: function() {
        this.render()
    },

    get_min_bid_dollars: function() {
        return $('#bid_dollars').data('min_bid_dollars');
    },

    get_max_bid_dollars: function() {
        return $('#bid_dollars').data('max_bid_dollars');
    },

    get_lowest_max_bid_dollars: function($form) {
      var totalBudgetDollars = $form.find('#total_budget_dollars').val(),
          duration = this.get_timing($form).duration;

        // maxBidDollars should be the lowest either
        // of maxBidDollars or dailyMaxBid
        var maxBidDollars = r.sponsored.get_max_bid_dollars(),
            dailyMaxBid = totalBudgetDollars / duration;

        return Math.min(maxBidDollars, dailyMaxBid);
    },

    get_min_budget_dollars: function() {
        return $('#total_budget_dollars').data('min_budget_dollars');
    },

    get_max_budget_dollars: function() {
        return $('#total_budget_dollars').data('max_budget_dollars');
    },

    check_budget: function($form) {
        var budget = this.get_budget($form),
            minBudgetDollars = this.get_min_budget_dollars(),
            maxBudgetDollars = this.get_max_budget_dollars(),
            campaignName = $form.find('*[name=campaign_name]').val()

        $('.budget-change-warning').hide()
        if (campaignName != '') {
            var $campaignRow = $('.' + campaignName),
                campaignIsPaid = $campaignRow.data('paid'),
                campaignTotalBudgetDollars = $campaignRow.data('total_budget_dollars')
            if (campaignIsPaid && budget.totalBudgetDollars != campaignTotalBudgetDollars) {
                $('.budget-change-warning').show()
            }
        }

        $(".minimum-spend").removeClass("error");

        if (!this.userIsSponsor) {
            if (budget.totalBudgetDollars < minBudgetDollars) {
                this.bidValid = false;
                $(".minimum-spend").addClass("error");
            } else if (budget.totalBudgetDollars > maxBudgetDollars) {
                this.bidValid = false;
            } else {
                this.bidValid = true;
                $(".minimum-spend").removeClass("error");
            }
        } else {
            this.bidValid = true;
            $(".minimum-spend").removeClass("error");
        }

        if (this.bidValid) {
            this.enable_form($form);
        } else {
            this.disable_form($form);
        }
    },

    check_bid_dollars: function($form) {
      var maxBidDollars = r.sponsored.get_lowest_max_bid_dollars($form);
      var minBidDollars = r.sponsored.get_min_bid_dollars();
      var bidDollars = $form.find('#bid_dollars').val() || 0.;

      $form.find('.daily-max-spend').text(maxBidDollars.toFixed(2));

      // Form validation
      if ((maxBidDollars < minBidDollars) ||
              (bidDollars < minBidDollars) ||
              (bidDollars > maxBidDollars)) {
          this.disable_form($form);
      }
    },

    calc_impressions: function(bid, cpm_pennies) {
        return Math.floor(bid / cpm_pennies * 1000 * 100);
    },

    calc_budget_dollars_from_impressions: function(impressions, cpm_pennies) {
        return (Math.floor(impressions * cpm_pennies / 1000) / 100).toFixed(2)
    },

    render_timing_duration: function($form, ndays) {
        var totalBudgetDollars = ndays + ' ' + ((ndays > 1) ? r._('days') : r._('day'));
        $form.find('.timing-field .duration').text(totalBudgetDollars);
    },

    fill_inventory_form: function() {
        var $form = $('.inventory-dashboard'),
            targeting = this.get_targeting($form),
            timing = this.get_timing($form);

        this.render_timing_duration($form, timing.duration);
    },

    submit_inventory_form: function() {
        var $form = $('.inventory-dashboard'),
            targeting = this.get_targeting($form),
            timing = this.get_timing($form);

        var data = {
            startdate: timing.startdate,
            enddate: timing.enddate,
        };

        if (targeting.type === 'collection') {
            data.collection_name = targeting.collection;
        }
        else if (targeting.type === 'subreddit') {
            data.sr_name = targeting.sr;
        }

        this.reload_with_params(data);
    },

    fill_reporting_form: function() {
        var $form = $('.reporting-dashboard'),
            timing = this.get_timing($form);

        this.render_timing_duration($form, timing.duration);
    },

    submit_reporting_form: function() {
        var $form = $('.reporting-dashboard'),
            timing = this.get_timing($form),
            reporting = this.get_reporting($form),
            grouping = $form.find("[name='grouping']").val();

        var data = {
            startdate: timing.startdate,
            enddate: timing.enddate,
            link_text: reporting.link_text,
            owner: reporting.owner,
            grouping: grouping,
        };

        this.reload_with_params(data);
    },

    reload_with_params: function(data) {
        var queryString = '?' + $.param(data);
        var location = window.location;
        window.location = location.origin + location.pathname + queryString;
    },

    mediaInputChange: function() {
        var $scraperInputWrapper = $('#scraper_input');
        var $rgInputWrapper = $('#rg_input');
        var isScraper = $(this).val() === 'scrape';

        $scraperInputWrapper.toggle(isScraper);
        $scraperInputWrapper.find('input').prop('disabled', !isScraper);
        $rgInputWrapper.toggle(!isScraper);
        $rgInputWrapper.find('input').prop('disabled', isScraper);
    },
};

}(r);

var dateFromInput = function(selector, offset) {
   if(selector) {
     var input = $(selector);
     if(input.length) {
        var d = new Date();
        offset = $.with_default(offset, 0);
        d.setTime(Date.parse(input.val()) + offset);
        return d;
     }
   }
};

function attach_calendar(where, min_date_src, max_date_src, callback, min_date_offset) {
     $(where).siblings(".datepicker").mousedown(function() {
            $(this).addClass("clicked active");
         }).click(function() {
            $(this).removeClass("clicked")
               .not(".selected").siblings("input").focus().end()
               .removeClass("selected");
         }).end()
         .focus(function() {
          var target = $(this);
          var dp = $(this).siblings(".datepicker");
          if (dp.children().length == 0) {
             dp.each(function() {
               $(this).datepicker(
                  {
                      defaultDate: dateFromInput(target),
                          minDate: dateFromInput(min_date_src, min_date_offset),
                          maxDate: dateFromInput(max_date_src),
                          prevText: "&laquo;", nextText: "&raquo;",
                          altField: "#" + target.attr("id"),
                          onSelect: function() {
                              $(dp).addClass("selected").removeClass("clicked");
                              $(target).blur();
                              if(callback) callback(this);
                          }
                })
              })
              .addClass("drop-choices");
          };
          dp.addClass("inuse active");
     }).blur(function() {
        $(this).siblings(".datepicker").not(".clicked").removeClass("inuse");
     }).click(function() {
        $(this).siblings(".datepicker.inuse").addClass("active");
     });
}

function sum(a, b) {
    // for things like _.reduce(list, sum);
    return a + b;
}

function check_enddate(startdate, enddate) {
  var startdate = $(startdate)
  var enddate = $(enddate);
  if(dateFromInput(startdate) >= dateFromInput(enddate)) {
    var newd = new Date();
    newd.setTime(startdate.datepicker('getDate').getTime() + 86400*1000);
    enddate.val((newd.getMonth()+1) + "/" +
      newd.getDate() + "/" + newd.getFullYear());
  }
  $("#datepicker-" + enddate.attr("id")).datepicker("destroy");
}

(function($) {
    $.update_campaign = function(campaign_name, campaign_html) {
        cancel_edit(function() {
            var $existing = $('.existing-campaigns .' + campaign_name),
                tableWasEmpty = $('.existing-campaigns table tr.campaign-row').length == 0

            if ($existing.length) {
                $existing.replaceWith(campaign_html)
                $existing.fadeIn()
            } else {
                $(campaign_html).hide()
                .appendTo('.existing-campaigns tbody')
                .css('display', 'table-row')
                .fadeIn()
            }

            if (tableWasEmpty) {
                $('.existing-campaigns p.error').hide()
                $('.existing-campaigns table').fadeIn()
                $('#campaign .buttons button[name=cancel]').removeClass('hidden')
                $("button.new-campaign").prop("disabled", false);
            }

            r.sponsored.render_campaign_dashboard_header();
        })
    }
}(jQuery));

function detach_campaign_form() {
    /* remove datepicker from fields */
    $("#campaign").find(".datepicker").each(function() {
            $(this).datepicker("destroy").siblings().unbind();
        });

    /* detach and return */
    var campaign = $("#campaign").detach();
    return campaign;
}

function cancel_edit(callback) {
    var $campaign = $('#campaign');
    var isEditingExistingCampaign = !!$campaign.parents('tr:first').length;

    if (isEditingExistingCampaign) {
        var tr = $campaign.parents("tr:first").prev();
        /* copy the campaign element */
        /* delete the original */
        $campaign.slideUp(function() {
                $(this).parent('tr').prev().fadeIn();
                var td = $(this).parent();
                var campaign = detach_campaign_form();
                td.delete_table_row(function() {
                        tr.fadeIn(function() {
                                $('.new-campaign-container').append(campaign);
                                campaign.hide();
                                if (callback) { callback(); }
                            });
                    });
            });
    } else {
        var keep_open = $campaign.hasClass('keep-open');

        if ($campaign.is(':visible') && !keep_open) {
            $campaign.slideUp(callback);
        } else if (callback) {
            callback();
        }

        if (keep_open) {
            $campaign.removeClass('keep-open');
            $campaign.find('.status')
                .text(r._('Created new campaign!'))
                .show()
                .delay(1000)
                .fadeOut();

            r.sponsored.render();
        }
    }
}

function send_campaign(close) {
    if (!close) {
        $('#campaign').addClass('keep-open');
    }

    post_pseudo_form('.campaign', 'edit_campaign');
}

function del_campaign($campaign_row) {
    var link_id36 = $("#campaign").find('*[name="link_id36"]').val(),
        campaign_id36 = $campaign_row.data('campaign_id36')
    $.request("delete_campaign", {"campaign_id36": campaign_id36,
                                  "link_id36": link_id36},
              null, true, "json", false);
    $campaign_row.children(":first").delete_table_row(function() {
        r.sponsored.render_campaign_dashboard_header();
        return check_number_of_campaigns();
    });
}

function toggle_pause_campaign($campaign_row, shouldPause) {
    var link_id36 = $('#campaign').find('*[name="link_id36"]').val(),
        campaign_id36 = $campaign_row.data('campaign_id36')
    $.request('toggle_pause_campaign', {'campaign_id36': campaign_id36,
                                        'link_id36': link_id36,
                                        'should_pause': shouldPause},
              null, true, 'json', false);
    r.sponsored.render();
}

function edit_campaign($campaign_row) {
    cancel_edit(function() {
        cancel_edit_promotion();
        var campaign = detach_campaign_form(),
            campaignTable = $(".existing-campaigns table").get(0),
            editRowIndex = $campaign_row.get(0).rowIndex + 1
            $editRow = $(campaignTable.insertRow(editRowIndex)),
            $editCell = $("<td>").attr("colspan", r.sponsored.campaignListColumns).append(campaign)

        $editRow.attr("id", "edit-campaign-tr")
        $editRow.append($editCell)
        $campaign_row.fadeOut(function() {
            /* fill inputs from data in campaign row */
            _.each(['startdate', 'enddate', 'bid', 'campaign_id36', 'campaign_name',
                    'frequency_cap', 'total_budget_dollars',
                    'bid_dollars'],
                function(input) {
                    var val = $campaign_row.data(input),
                        $input = campaign.find('*[name="' + input + '"]')
                    $input.val(val)
            })

            if ($campaign_row.data('is_auction') === 'True') {
              r.sponsored.isAuction = true;
            } else {
              r.sponsored.isAuction = false;
            }

            var platform = $campaign_row.data('platform');
            campaign.find('*[name="platform"][value="' + platform + '"]').prop("checked", "checked");

            /* set mobile targeting */
            r.sponsored.setup_mobile_targeting(
              $campaign_row.data('mobile_os'),
              $campaign_row.data('ios_devices'),
              $campaign_row.data('ios_versions'),
              $campaign_row.data('android_devices'),
              $campaign_row.data('android_versions')
            );

            /* pre-select mobile OS checkboxes if current platform is not mobile */
            campaign.find('.mobile-os-group input').prop("checked", !r.sponsored.mobileOS);

            /* logic if filtering by device and OS */
            if (r.sponsored.iOSDevices || r.sponsored.androidDevices) {
              /* pre-select the device and OS version radio button */
              campaign.find('#filter_os_devices').prop('checked', 'checked');

              /* first, clear all checked devices (they're checked by default),
                 but only if the campaign has devices for the OS */
              if (r.sponsored.iOSDevices) {
                campaign.find('.ios-device input[type="checkbox"]').prop('checked', false);
              }
              if (r.sponsored.androidDevices) {
                campaign.find('.android-device input[type="checkbox"]').prop('checked', false);
              }

              /* then, pre-select all appropriate devices */
              var allDevices = [].concat(r.sponsored.iOSDevices, r.sponsored.androidDevices);
              allDevices.forEach(function(device) {
                if (device) {
                  campaign.find('#'+device.toLowerCase()).prop('checked', true);
                }
              });

              /* pre-select iOS versions */
              if (r.sponsored.iOSVersions) {
                campaign.find('#ios_min').val(r.sponsored.iOSVersions[0]);
                campaign.find('#ios_max').val(r.sponsored.iOSVersions[1]);
              }

              /* pre-select Android versions */
              if (r.sponsored.androidVersions) {
                campaign.find('#android_min').val(r.sponsored.androidVersions[0]);
                campaign.find('#android_max').val(r.sponsored.androidVersions[1]);
              }
            } else {
              campaign.find('#all_os_devices').prop('checked', true);
            }

            var mobile_os_names = $campaign_row.data('mobile_os');
            if (mobile_os_names) {
              mobile_os_names.forEach(function(name) {
                campaign.find('#mobile_os_' + name).prop("checked", "checked");
              });
            }

            r.sponsored.setup_frequency_cap($campaign_row.data('frequency_cap'));
            /* show frequency inputs */
            if ($campaign_row.data('frequency_cap')) {
              $('.frequency-cap-field').show();
              $('#frequency_capped_true').prop('checked', 'checked');
            }

            /* set priority */
            var priorities = campaign.find('*[name="priority"]'),
                campPriority = $campaign_row.data("priority")

            priorities.filter('*[value="' + campPriority + '"]')
                .prop("checked", "checked")

            /* check if targeting is turned on */
            var targeting = $campaign_row.data("targeting"),
                radios = campaign.find('*[name="targeting"]'),
                isCollection = ($campaign_row.data("targeting-collection") === "True"),
                collectionTargeting = isCollection ? targeting : 'none';
            if (targeting && !isCollection) {
                radios.filter('*[value="one"]')
                    .prop("checked", "checked");
                campaign.find('*[name="sr"]').val(targeting).prop("disabled", false).end()
                    .find(".subreddit-targeting").show();
                $(".collection-targeting").hide();
            } else {
                radios.filter('*[value="collection"]')
                    .prop("checked", "checked");
                $('.collection-targeting input[value="' + collectionTargeting + '"]')
                    .prop("checked", "checked");
                campaign.find('*[name="sr"]').val("").prop("disabled", true).end()
                    .find(".subreddit-targeting").hide();
                $('.collection-targeting').show();
            }

            r.sponsored.collapse_collection_selector();

            /* set geotargeting */
            var country = $campaign_row.data("country"),
                region = $campaign_row.data("region"),
                metro = $campaign_row.data("metro")
            campaign.find("#country").val(country)
            r.sponsored.update_regions()
            if (region != "") {
                campaign.find("#region").val(region)
                r.sponsored.update_metros()

                if (metro != "") {
                    campaign.find("#metro").val(metro)
                }
            }

            /* set cost basis */
            $('#cost_basis').val($campaign_row.data('cost_basis'));

            /* attach the dates to the date widgets */
            init_startdate();
            init_enddate();

            /* setup fields for live campaign editing */
            r.sponsored.setupLiveEditing($campaign_row.data('is_live') === 'True');

            campaign.find('#is_new').val('false')

            campaign.find('button[name="save"]').show().end()
                .find('.create').hide().end();
            campaign.slideDown();
            r.sponsored.render();
        })
    })
}

function check_number_of_campaigns(){
    if ($(".campaign-row").length >= $(".existing-campaigns").data("max-campaigns")){
      $(".error.TOO_MANY_CAMPAIGNS").fadeIn();
      $("button.new-campaign").prop("disabled", true);
      return true;
    } else {
      $(".error.TOO_MANY_CAMPAIGNS").fadeOut();
      $("button.new-campaign").prop("disabled", false);
      return false;
    }
}

function create_campaign() {
    if (check_number_of_campaigns()){
        return;
    }

    r.analytics.fireFunnelEvent('ads', 'new-campaign');

    cancel_edit(function() {
            cancel_edit_promotion();
            var defaultBudgetDollars = $("#total_budget_dollars").data("default_budget_dollars");

            init_startdate();
            init_enddate();

            $('#campaign')
                .find(".collection-targeting").show().end()
                .find('input[name="collection"]').prop("disabled", false).end()
                .find('input[name="collection"]').eq(0).prop("checked", "checked").end().end()
                .find('input[name="collection"]').slice(1).prop("checked", false).end().end()
                .find('.collection-selector .form-group-list').css('top', 0).end()
            r.sponsored.collapse_collection_selector();

            $("#campaign")
                .find('button[name="save"]').hide().end()
                .find('.create').show().end()
                .find('input[name="campaign_id36"]').val('').end()
                .find('input[name="campaign_name"]').val('').end()
                .find('input[name="sr"]').val('').prop("disabled", true).end()
                .find('input[name="targeting"][value="collection"]').prop("checked", "checked").end()
                .find('input[name="priority"][data-default="true"]').prop("checked", "checked").end()
                .find('input[name="total_budget_dollars"]').val(defaultBudgetDollars).end()
                .find(".subreddit-targeting").hide().end()
                .find('select[name="country"]').val('').end()
                .find('select[name="region"]').hide().end()
                .find('select[name="metro"]').hide().end()
                .find('input[name="frequency_cap"]').val('').end()
                .find('input[name="startdate"]').prop('disabled', false).end()
                .find('#frequency_capped_false').prop('checked', 'checked').end()
                .find('.frequency-cap-field').hide().end()
                .find('input[name="is_new"]').val('true').end()
                .slideDown();
            r.sponsored.render();
        });
}

function free_campaign($campaign_row) {
    var link_id36 = $("#campaign").find('*[name="link_id36"]').val(),
        campaign_id36 = $campaign_row.data('campaign_id36')
    $.request("freebie", {"campaign_id36": campaign_id36, "link_id36": link_id36},
              null, true, "json", false);
    $campaign_row.find(".free").fadeOut();
    return false;
}

function terminate_campaign($campaign_row) {
    var link_id36 = $("#campaign").find('*[name="link_id36"]').val(),
        campaign_id36 = $campaign_row.data('campaign_id36')
    $.request("terminate_campaign", {"campaign_id36": campaign_id36,
                                     "link_id36": link_id36},
              null, true, "json", false);
}

function edit_promotion() {
    $("button.new-campaign").prop("disabled", false);
    cancel_edit(function() {
        $('.promotelink-editor')
            .find('.collapsed-display').slideUp().end()
            .find('.uncollapsed-display').slideDown().end()
    })
    return false;
}

function cancel_edit_promotion() {
    $('.promotelink-editor')
        .find('.collapsed-display').slideDown().end()
        .find('.uncollapsed-display').slideUp().end()

    return false;
}

function cancel_edit_campaign() {
    $("button.new-campaign").prop("disabled", false);
    return cancel_edit()
}

!function(exports) {
    /*
     * @param {number[]} days An array of inventory for the campaign's timing
     * @param {number} minValidRequest The minimum request a campaign is allowed
     *                                 to have, should be in the same units as `days`
     * @param {number} requested The campaign's requested inventory, in the same
     *                           units as `days` and `minValidRequest`.
     * @param {number} maxOffset maximum valid start index
     * @returns {{days: number[], maxRequest: number, offset:number}|null}
     *                            The sub-array, maximum request for it, and
     *                            its offset from the original `days` array.
     */
    exports.getMaximumRequest = _.memoize(
      function getMaximumRequest(days, minValidRequest, requested, maxOffset) {
        return check(days, 0);

        /**
         * check if a set of days is valid, then compare to results of this
         * function called on subsets of that date range
         * @param  {Number[]} days inventory values
         * @param  {Number} offset offset from the original days array we are
         *                         working on
         * @return {Object|null}  object describing the best range found,
         *                        or null if no valid range was found
         */
        function check(days, offset) {
          var bestOption = null;
          if (days.length > 0 && offset <= maxOffset) {
            // check the validity of the days array.
            var minValue = min(days);
            var maxRequest = minValue * days.length;
            if (maxRequest >= minValidRequest) {
              bestOption = {days: days, maxRequest: maxRequest, offset: offset};
            }
          }
          if (bestOption === null || bestOption.maxRequest < requested) {
            // if bestOptions does not hit our target, check sub-arrays.  start
            // by splitting on values that invalidate the date range (anything
            // with inventory below the minimum daily amount).
            // subtract 0.1 because the comparison used to filter is > (not >=)
            var minDaily = days.length / minValidRequest - 0.1;
            return split(days, offset, bestOption, minDaily, check, true)
          }
          else {
            return bestOption;
          }
        }
      },
      function hashFunction(days, minValidRequest, requested) {
        return [days.join(','), minValidRequest, requested].join('|');
      }
    );

    /**
     * compare two date range options, returning the better
     * options are compared on their maximum request first, then their duration
     * @param  {Object|null} a
     * @param  {Object|null} b
     * @return {Object|null}
     */
    function compare(a, b) {
      if (!b) {
        return a;
      }
      else if (!a) {
        return b;
      }
      if (b.maxRequest > a.maxRequest ||
          (b.maxRequest === a.maxRequest && b.days.length > a.days.length)) {
        return b;
      }
      else {
        return a;
      }
    }

    function min(arr) {
      return Math.min.apply(Math, arr);
    }

    /**
     * split an array of inventory into sub-arrays, checking each
     * @param  {number[]} days - inventory data for a range of contiguous dates
     * @param  {number} offset - index offset from original array
     * @param  {Object|null} bestOption - current best option
     * @param  {number} minValue - value used to split the days array on; values
     *                             below this are excluded
     * @param  {function} check - function to call on sub-arrays
     * @param  {boolean} recurse - whether or not to call this function again if
     *                             unable to split array (more on this below)
     * @return {Object|null} - best option found
     */
    function split(days, offset, bestOption, minValue, check, recurse) {
      var sub = [];
      var subOffset = 0;
      for (var i = 0, l = days.length; i < l; i++) {
        if (days[i] > minValue) {
          if (sub.length === 0) {
            subOffset = offset + i;
          }
          sub.push(days[i])
        }
        else {
          // whenever we hit the end of a contiguous set of days above the
          // minValue threshold, compare that sub-array to our current bestOption
          if (sub.length) {
            bestOption = compare(bestOption, check(sub, subOffset))
            sub = [];
          }
        }
      }
      if (sub.length === days.length) {
        // if the array was not split at all:
        if (recurse) {
          // if we were previously splitting on the minimum valid value, try
          // splitting on the smallest value in the array.  The `recurse` value
          // prevents this from looping infinitely
          return compare(bestOption, split(days, offset, null, min(days), check, false));
        }
        else {
          // otherwise, just return the current best
          return bestOption;
        }
      }
      else if (sub.length) {
        // need to compare the last sub array, as it won't checked in the for loop
        return compare(bestOption, check(sub, subOffset));
      }
      else {
        // if _no_ values were found above the minValue threshold
        return bestOption;
      }
    }
}(r.sponsored);
