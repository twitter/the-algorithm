using UnityEngine;
using System.Collections;
using System.Collections.Generic;


/// <summary>
/// base class for TweenChains and TweenFlows
/// </summary>
public class AbstractGoTweenCollection : AbstractGoTween
{
	protected List<TweenFlowItem> _tweenFlows = new List<TweenFlowItem>();
	
	
	/// <summary>
	/// data class that wraps an AbstractTween and its start time for the timeline
	/// </summary>
	protected class TweenFlowItem
	{
		public float startTime;
        public float endTime { get { return startTime + duration; } }
        public float duration;
		public AbstractGoTween tween;


		public TweenFlowItem( float startTime, AbstractGoTween tween )
		{
			this.tween = tween;
			this.startTime = startTime;
			this.duration = tween.totalDuration;
		}
		
		
		public TweenFlowItem( float startTime, float duration )
		{
			this.duration = duration;
			this.startTime = startTime;
		}
	}
	

	public AbstractGoTweenCollection( GoTweenCollectionConfig config )
	{
        // allow events by default
        allowEvents = true;

        // setup callback bools
        _didInit = false;
        _didBegin = false;

        // flag the onIterationStart event to fire.
        // as long as goTo is not called on this tween, the onIterationStart event will fire
        _fireIterationStart = true;

		// copy the TweenConfig info over
		id = config.id;
		loopType = config.loopType;
		iterations = config.iterations;
		updateType = config.propertyUpdateType;
        timeScale = 1;
        state = GoTweenState.Paused;

        _onInit = config.onInitHandler;
        _onBegin = config.onBeginHandler;
        _onIterationStart = config.onIterationStartHandler;
        _onUpdate = config.onUpdateHandler;
        _onIterationEnd = config.onIterationEndHandler;
        _onComplete = config.onCompleteHandler;

		Go.addTween( this );
	}
		
	
	#region AbstractTween overrides
	
	/// <summary>
	/// returns a list of all Tweens with the given target in the collection
	/// technically, this should be marked as internal
	/// </summary>
	public List<GoTween> tweensWithTarget( object target )
	{
		List<GoTween> list = new List<GoTween>();
		
		foreach( var flowItem in _tweenFlows )
		{
			// skip TweenFlowItems with no target
			if( flowItem.tween == null )
				continue;
			
			// check Tweens first
			var tween = flowItem.tween as GoTween;
			if( tween != null && tween.target == target )
				list.Add( tween );
			
			// check for TweenCollections
			if( tween == null )
			{
				var tweenCollection = flowItem.tween as AbstractGoTweenCollection;
				if( tweenCollection != null )
				{
					var tweensInCollection = tweenCollection.tweensWithTarget( target );
					if( tweensInCollection.Count > 0 )
						list.AddRange( tweensInCollection );
				}
			}
		}
		
		return list;
	}
	
	
	public override bool removeTweenProperty( AbstractTweenProperty property )
	{
		foreach( var flowItem in _tweenFlows )
		{
			// skip delay items which have no tween
			if( flowItem.tween == null )
				continue;
			
			if( flowItem.tween.removeTweenProperty( property ) )
				return true;
		}
		
		return false;
	}
	
	
	public override bool containsTweenProperty( AbstractTweenProperty property )
	{
		foreach( var flowItem in _tweenFlows )
		{
			// skip delay items which have no tween
			if( flowItem.tween == null )
				continue;
			
			if( flowItem.tween.containsTweenProperty( property ) )
				return true;
		}
		
		return false;
	}
	
	
	public override List<AbstractTweenProperty> allTweenProperties()
	{
		var propList = new List<AbstractTweenProperty>();
		
		foreach( var flowItem in _tweenFlows )
		{
			// skip delay items which have no tween
			if( flowItem.tween == null )
				continue;
			
			propList.AddRange( flowItem.tween.allTweenProperties() );
		}
		
		return propList;
	}

	
	/// <summary>
	/// we are always considered valid because our constructor adds us to Go and we start paused
	/// </summary>
	public override bool isValid()
	{
		return true;
	}

    /// <summary>
    /// resumes playback
    /// </summary>
    public override void play()
    {
        base.play();

        foreach( var flowItem in _tweenFlows )
        {
            if( flowItem.tween != null )
                flowItem.tween.play();
        }
    }

    /// <summary>
    /// pauses playback
    /// </summary>
    public override void pause()
    {
        base.pause();

        foreach( var flowItem in _tweenFlows )
        {
            if( flowItem.tween != null )
                flowItem.tween.pause();
        }
    }
	
	/// <summary>
	/// tick method. if it returns true it indicates the tween is complete
	/// </summary>
	public override bool update( float deltaTime )
	{
        if ( !_didInit )
            onInit();

        if ( !_didBegin )
            onBegin();

        if ( _fireIterationStart )
            onIterationStart();

        // update the timeline and state.
		base.update( deltaTime );

		// get the proper elapsedTime if we're doing a PingPong
		var convertedElapsedTime = _isLoopingBackOnPingPong ? duration - _elapsedTime : _elapsedTime;

        // used for iterating over flowItems below.
        TweenFlowItem flowItem = null;

        // if we iterated last frame and this flow restarts from the beginning, we now need to reset all
        // of the flowItem tweens to either the beginning or the end of their respective timelines
        // we also want to do this in the _opposite_ way that we would normally iterate on them
        // as the start value of a later flowItem may alter a property of an earlier flowItem.
        if ( _didIterateLastFrame && loopType == GoLoopType.RestartFromBeginning )
        {
            if ( isReversed || _isLoopingBackOnPingPong )
            {
                for ( int i = 0; i < _tweenFlows.Count; ++i )
                {
                    flowItem = _tweenFlows[i];

                    if ( flowItem.tween == null )
                        continue;

                    var cacheAllow = flowItem.tween.allowEvents;
                    flowItem.tween.allowEvents = false;
                    flowItem.tween.restart();
                    flowItem.tween.allowEvents = cacheAllow;
                }
            }
            else
            {
                for ( int i = _tweenFlows.Count - 1; i >= 0; --i )
                {
                    flowItem = _tweenFlows[i];

                    if ( flowItem.tween == null )
                        continue;

                    var cacheAllow = flowItem.tween.allowEvents;
                    flowItem.tween.allowEvents = false;
                    flowItem.tween.restart();
                    flowItem.tween.allowEvents = cacheAllow;
                }
            }
        }
        else
        {
            if ( ( isReversed && !_isLoopingBackOnPingPong ) || ( !isReversed && _isLoopingBackOnPingPong ) )
            {
                // if we are moving the tween in reverse, we should be iterating over the flowItems in reverse
                // to help properties behave a bit better.
                for ( var i = _tweenFlows.Count - 1; i >= 0; --i )
                {
                    flowItem = _tweenFlows[i];

                    if ( flowItem.tween == null )
                        continue;

                    // if there's been an iteration this frame and we're not done yet, we want to make sure
                    // this tween is set to play in the right direction, and isn't set to complete/paused.
                    if ( _didIterateLastFrame && state != GoTweenState.Complete )
                    {
                        if ( !flowItem.tween.isReversed )
                            flowItem.tween.reverse();

                        flowItem.tween.play();
                    }

                    if ( flowItem.tween.state == GoTweenState.Running && flowItem.endTime >= convertedElapsedTime )
                    {
                        var convertedDeltaTime = Mathf.Abs( convertedElapsedTime - flowItem.startTime - flowItem.tween.totalElapsedTime );
                        flowItem.tween.update( convertedDeltaTime );
                    }
                }
            }
            else
            {
                for ( int i = 0; i < _tweenFlows.Count; ++i )
                {
                    flowItem = _tweenFlows[i];

                    if ( flowItem.tween == null )
                        continue;

                    // if there's been an iteration this frame and we're not done yet, we want to make sure
                    // this tween is set to play in the right direction, and isn't set to complete/paused.
                    if ( _didIterateLastFrame && state != GoTweenState.Complete )
                    {
                        if ( flowItem.tween.isReversed )
                            flowItem.tween.reverse();

                        flowItem.tween.play();
                    }

                    if ( flowItem.tween.state == GoTweenState.Running && flowItem.startTime <= convertedElapsedTime )
                    {
                        var convertedDeltaTime = convertedElapsedTime - flowItem.startTime - flowItem.tween.totalElapsedTime;
                        flowItem.tween.update( convertedDeltaTime );
                    }
                }
            }
        }

        onUpdate();

        if ( _fireIterationEnd )
            onIterationEnd();

        if ( state == GoTweenState.Complete )
        {
            onComplete();

            return true; // true if complete
        }

        return false; // false if not complete
	}

    /// <summary>
    /// reverses playback. if going forward it will be going backward after this and vice versa.
    /// </summary>
    public override void reverse()
    {
        base.reverse();

        var convertedElapsedTime = _isLoopingBackOnPingPong ? duration - _elapsedTime : _elapsedTime;

        foreach ( var flowItem in _tweenFlows )
        {
            if ( flowItem.tween == null )
                continue;

            if ( isReversed != flowItem.tween.isReversed )
                flowItem.tween.reverse();

            flowItem.tween.pause();

            // we selectively mark tweens for play if they will be played immediately or in the future.
            // update() will filter out more tweens that should not be played yet.
            if ( isReversed || _isLoopingBackOnPingPong )
            {
                if ( flowItem.startTime <= convertedElapsedTime )
                    flowItem.tween.play();
            }
            else
            {
                if ( flowItem.endTime >= convertedElapsedTime )
                    flowItem.tween.play();
            }
        }
    }

    /// <summary>
    /// goes to the specified time clamping it from 0 to the total duration of the tween. if the tween is
    /// not playing it will be force updated to the time specified.
    /// </summary>
	public override void goTo( float time, bool skipDelay = true )
    {
        time = Mathf.Clamp( time, 0f, totalDuration );

		// provide an early out for calling goTo on the same time multiple times.
        if ( time == _totalElapsedTime )
            return;

        // we don't simply call base.goTo because that would force an update within AbstractGoTweenCollection,
        // which forces an update on all the tweenFlowItems without putting them in the right position.
        // it's also possible that people will move around a tween via the goTo method, so we want to
        // try to make that as efficient as possible.

        // if we are doing a goTo at the "start" of the timeline, based on the isReversed variable,
        // allow the onBegin and onIterationStart callback to fire again.
        // we only allow the onIterationStart event callback to fire at the start of the timeline,
        // as doing a goTo(x) where x % duration == 0 will trigger the onIterationEnd before we
        // go to the start.
        if ( ( isReversed && time == totalDuration ) || ( !isReversed && time == 0f ) )
        {
            _didBegin = false;
            _fireIterationStart = true;
        }
        else
        {
            _didBegin = true;
            _fireIterationStart = false;
        }

        // since we're doing a goTo, we want to stop this tween from remembering that it iterated.
        // this could cause issues if you caused the tween to complete an iteration and then goTo somewhere
        // else while still paused.
        _didIterateThisFrame = false;

        // force a time and completedIterations before we update
        _totalElapsedTime = time;
        _completedIterations = isReversed ? Mathf.CeilToInt( _totalElapsedTime / duration ) : Mathf.FloorToInt( _totalElapsedTime / duration );

        // we don't want to use the Collection update function, because we don't have all of our
        // child tweenFlowItems setup properly. this will properly setup our iterations,
        // totalElapsedTime, and other useful information.
        base.update( 0 );

        var convertedElapsedTime = _isLoopingBackOnPingPong ? duration - _elapsedTime : _elapsedTime;

        // we always want to process items in the future of this tween from last to first.
        // and items that have already occured from first to last.
        TweenFlowItem flowItem = null;
        if ( isReversed || _isLoopingBackOnPingPong )
        {
            // flowItems in the future of the timeline
            for ( int i = 0; i < _tweenFlows.Count; ++i )
            {
                flowItem = _tweenFlows[i];

                if ( flowItem == null )
                    continue;

                if ( flowItem.endTime >= convertedElapsedTime )
                    break;

                changeTimeForFlowItem( flowItem, convertedElapsedTime );
            }

            // flowItems in the past & current part of the timeline
            for ( int i = _tweenFlows.Count - 1; i >= 0; --i )
            {
                flowItem = _tweenFlows[i];

                if ( flowItem == null )
                    continue;

                if ( flowItem.endTime < convertedElapsedTime )
                    break;

                changeTimeForFlowItem( flowItem, convertedElapsedTime );
            }
        }
        else
        {
            // flowItems in the future of the timeline
            for ( int i = _tweenFlows.Count - 1; i >= 0; --i )
            {
                flowItem = _tweenFlows[i];

                if ( flowItem == null )
                    continue;

                if ( flowItem.startTime <= convertedElapsedTime )
                    break;

                changeTimeForFlowItem( flowItem, convertedElapsedTime );
            }

            // flowItems in the past & current part of the timeline
            for ( int i = 0; i < _tweenFlows.Count; ++i )
            {
                flowItem = _tweenFlows[i];

                if ( flowItem == null )
                    continue;

                if ( flowItem.startTime > convertedElapsedTime )
                    break;

                changeTimeForFlowItem( flowItem, convertedElapsedTime );
            }
        }
    }

    private void changeTimeForFlowItem( TweenFlowItem flowItem, float time )
    {
        if ( flowItem == null || flowItem.tween == null )
            return;

        if ( flowItem.tween.isReversed != ( isReversed || _isLoopingBackOnPingPong ) )
            flowItem.tween.reverse();

        var convertedTime = Mathf.Clamp( time - flowItem.startTime, 0f, flowItem.endTime );

        if ( flowItem.startTime <= time && flowItem.endTime >= time )
        {
            flowItem.tween.goToAndPlay( convertedTime );
        }
        else
        {
            flowItem.tween.goTo( convertedTime );
            flowItem.tween.pause();
        }
    }

	#endregion
	
}
