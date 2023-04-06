using UnityEngine;
using System;
using System.Collections;
using System.Collections.Generic;


/// <summary>
/// base class shared by the Tween and TweenChain classes to allow a seemless API when controlling
/// either of them
/// </summary>
public abstract class AbstractGoTween
{
	public int id = 0; // optional id used for identifying this tween
	public GoTweenState state { get; protected set; } // current state of the tween
	public float duration { get; protected set; } // duration for a single loop
	public float totalDuration { get; protected set; } // duration for all loops of this tween
	public float timeScale { get; set; } // time scale to be used by this tween

	public GoUpdateType updateType { get; protected set; }
	public GoLoopType loopType { get; protected set; }
	public int iterations { get; protected set; } // set to -1 for infinite

	public bool autoRemoveOnComplete { get; set; } // should we automatically remove ourself from the Go's list of tweens when done?
	public bool isReversed { get; protected set; } // have we been reversed? this is different than a PingPong loop's backwards section
	public bool allowEvents { get; set; } // allow the user to surpress events.
	protected bool _didInit; // flag to ensure event only gets fired once
	protected bool _didBegin; // flag to ensure event only gets fired once
	protected bool _fireIterationStart;
	protected bool _fireIterationEnd;

	// internal state for update logic
	protected float _elapsedTime; // elapsed time for the current loop iteration
	protected float _totalElapsedTime; // total elapsed time of the entire tween
	public float totalElapsedTime { get { return _totalElapsedTime; } }

	protected bool _isLoopingBackOnPingPong;
	public bool isLoopingBackOnPingPong { get { return _isLoopingBackOnPingPong; } }

	protected bool _didIterateLastFrame;
	protected bool _didIterateThisFrame;
	protected int _deltaIterations; // change in completed iterations this frame.
	protected int _completedIterations;
	public int completedIterations { get { return _completedIterations; } }

	// action event handlers
	protected Action<AbstractGoTween> _onInit; // executes before initial setup.
	protected Action<AbstractGoTween> _onBegin; // executes when a tween starts.
	protected Action<AbstractGoTween> _onIterationStart; // executes whenever a tween starts an iteration.
	protected Action<AbstractGoTween> _onUpdate; // execute whenever a tween updates.
	protected Action<AbstractGoTween> _onIterationEnd; // executes whenever a tween ends an iteration.
	protected Action<AbstractGoTween> _onComplete; // exectures whenever a tween completes

	public void setOnInitHandler( Action<AbstractGoTween> onInit )
	{
		_onInit = onInit;
	}

	public void setOnBeginHandler( Action<AbstractGoTween> onBegin )
	{
		_onBegin = onBegin;
	}

	public void setonIterationStartHandler( Action<AbstractGoTween> onIterationStart )
	{
		_onIterationStart = onIterationStart;
	}

	public void setOnUpdateHandler( Action<AbstractGoTween> onUpdate )
	{
		_onUpdate = onUpdate;
	}

	public void setonIterationEndHandler( Action<AbstractGoTween> onIterationEnd )
	{
		_onIterationEnd = onIterationEnd;
	}

	public void setOnCompleteHandler( Action<AbstractGoTween> onComplete )
	{
		_onComplete = onComplete;
	}

	/// <summary>
	/// called once per tween when it is first updated
	/// </summary>
	protected virtual void onInit()
	{
		if( !allowEvents )
			return;

		if( _onInit != null )
			_onInit( this );

		_didInit = true;
	}

	/// <summary>
	/// called whenever the tween is updated and the playhead is at the start (or end, depending on isReversed) of the tween.
	/// </summary>
	protected virtual void onBegin()
	{
		if( !allowEvents )
			return;

		if( isReversed && _totalElapsedTime != totalDuration )
			return;
		else if( !isReversed && _totalElapsedTime != 0f )
			return;

		if( _onBegin != null )
			_onBegin( this );

		_didBegin = true;
	}


	/// <summary>
	/// called once per iteration at the start of the iteration.
	/// </summary>
	protected virtual void onIterationStart()
	{
		if( !allowEvents )
			return;

		if( _onIterationStart != null )
			_onIterationStart( this );
	}

	/// <summary>
	/// called once per update, after the update has occured.
	/// </summary>
	protected virtual void onUpdate()
	{
		if( !allowEvents )
			return;

		if( _onUpdate != null )
			_onUpdate( this );
	}

	/// <summary>
	/// called once per iteration at the end of the iteration.
	/// </summary>
	protected virtual void onIterationEnd()
	{
		if( !allowEvents )
			return;

		if( _onIterationEnd != null )
			_onIterationEnd( this );
	}


	/// <summary>
	/// called when the tween completes playing.
	/// </summary>
	protected virtual void onComplete()
	{
		if( !allowEvents )
			return;

		if( _onComplete != null )
			_onComplete( this );
	}


	/// <summary>
	/// tick method. if it returns true it indicates the tween is complete.
	///   note: at it's base, AbstractGoTween does not fire events, it is up to the implementer to
	///         do so. see GoTween and AbstractGoTweenCollection for examples.
	/// </summary>
	public virtual bool update( float deltaTime )
	{
		// increment or decrement the total elapsed time then clamp from 0 to totalDuration
		if( isReversed )
			_totalElapsedTime -= deltaTime;
		else
			_totalElapsedTime += deltaTime;

		_totalElapsedTime = Mathf.Clamp( _totalElapsedTime, 0, totalDuration );

		_didIterateLastFrame = _didIterateThisFrame || ( !isReversed && _totalElapsedTime == 0 ) || ( isReversed && _totalElapsedTime == totalDuration );

		// we flip between ceil and floor based on the direction, because we want the iteration change
		// to happen when "duration" seconds has elapsed, not immediately, as was the case if you
		// were doing a floor and were going in reverse.
		if( isReversed )
			_deltaIterations = Mathf.CeilToInt( _totalElapsedTime / duration ) - _completedIterations;
		else
			_deltaIterations = Mathf.FloorToInt( _totalElapsedTime / duration ) - _completedIterations;

		// we iterated this frame if we have done a goTo() to an iteration point, or we've passed over
		// an iteration threshold.
		_didIterateThisFrame = !_didIterateLastFrame && ( _deltaIterations != 0f || _totalElapsedTime % duration == 0f );

		_completedIterations += _deltaIterations;

		// set the elapsedTime, given what we know.
		if( _didIterateLastFrame )
		{
			_elapsedTime = isReversed ? duration : 0f;
		}
		else if( _didIterateThisFrame )
		{
			// if we iterated this frame, we force the _elapsedTime to the end of the timeline.
			_elapsedTime = isReversed ? 0f : duration;
		}
		else
		{
			_elapsedTime = _totalElapsedTime % duration;

			// if you do a goTo(x) where x is a multiple of duration, we assume that you want
			// to be at the end of your duration, as this sets you up to have an automatic OnIterationStart fire
			// the next updated frame. the only caveat is when you do a goTo(0) when playing forwards,
			// or a goTo(totalDuration) when playing in reverse. we assume that at that point, you want to be
			// at the start of your tween.
			if( _elapsedTime == 0f && ( ( isReversed && _totalElapsedTime == totalDuration ) || ( !isReversed && _totalElapsedTime > 0f ) ) )
			{
				_elapsedTime = duration;
			}
		}

		// we can only be looping back on a PingPong if our loopType is PingPong and we are on an odd numbered iteration
		_isLoopingBackOnPingPong = false;
		if( loopType == GoLoopType.PingPong )
		{
			// due to the way that we count iterations, and force a tween to remain at the end
			// of it's timeline for one frame after passing the duration threshold,
			// we need to make sure that _isLoopingBackOnPingPong references the current
			// iteration, and not the next one.
			if( isReversed )
			{
				_isLoopingBackOnPingPong = _completedIterations % 2 == 0;

				if( _elapsedTime == 0f )
					_isLoopingBackOnPingPong = !_isLoopingBackOnPingPong;
			}
			else
			{
				_isLoopingBackOnPingPong = _completedIterations % 2 != 0;

				if( _elapsedTime == duration )
					_isLoopingBackOnPingPong = !_isLoopingBackOnPingPong;
			}
		}

		// set a flag whether to fire the onIterationEnd event or not.
		_fireIterationStart = _didIterateThisFrame || ( !isReversed && _elapsedTime == duration ) || ( isReversed && _elapsedTime == 0f );
		_fireIterationEnd = _didIterateThisFrame;

		// check for completion
		if( ( !isReversed && iterations >= 0 && _completedIterations >= iterations ) || ( isReversed && _totalElapsedTime <= 0 ) )
			state = GoTweenState.Complete;

		if( state == GoTweenState.Complete )
		{
			// these variables need to be reset here. if a tween were to complete, 
			// and then get played again:
			// * The onIterationStart event would get fired
			// * tweens would flip their elapsedTime between 0 and totalDuration

			_fireIterationStart = false;
			_didIterateThisFrame = false;

			return true; // true if complete
		}

		return false; // false if not complete
	}


	/// <summary>
	/// subclasses should return true if they are a valid and ready to be added to the list of running tweens
	/// or false if not ready.
	/// technically, this should be marked as internal
	/// </summary>
	public abstract bool isValid();


	/// <summary>
	/// attempts to remove the tween property returning true if successful
	/// technically, this should be marked as internal
	/// </summary>
	public abstract bool removeTweenProperty( AbstractTweenProperty property );


	/// <summary>
	/// returns true if the tween contains the same type (or propertyName) property in its property list
	/// technically, this should be marked as internal
	/// </summary>
	public abstract bool containsTweenProperty( AbstractTweenProperty property );


	/// <summary>
	/// returns a list of all the TweenProperties contained in the tween and all its children (if it is
	/// a TweenChain or a TweenFlow)
	/// technically, this should be marked as internal
	/// </summary>
	public abstract List<AbstractTweenProperty> allTweenProperties();


	/// <summary>
	/// removes the Tween from action and cleans up its state
	/// </summary>
	public virtual void destroy()
	{
		state = GoTweenState.Destroyed;
	}


	/// <summary>
	/// pauses playback
	/// </summary>
	public virtual void pause()
	{
		state = GoTweenState.Paused;
	}


	/// <summary>
	/// resumes playback
	/// </summary>
	public virtual void play()
	{
		state = GoTweenState.Running;
	}


	/// <summary>
	/// plays the tween forward. if it is already playing forward has no effect
	/// </summary>
	public void playForward()
	{
		if( isReversed )
			reverse();

		play();
	}


	/// <summary>
	/// plays the tween backwards. if it is already playing backwards has no effect
	/// </summary>
	public void playBackwards()
	{
		if( !isReversed )
			reverse();

		play();
	}


	/// <summary>
	/// resets the tween to the beginning, taking isReversed into account.
	/// </summary>
	protected virtual void reset( bool skipDelay = true )
	{
		goTo( isReversed ? totalDuration : 0, skipDelay );

		_fireIterationStart = true;
	}


	/// <summary>
	/// rewinds the tween to the beginning (or end, depending on isReversed) and pauses playback.
	/// </summary>
	public virtual void rewind( bool skipDelay = true )
	{
		reset( skipDelay );
		pause();
	}


	/// <summary>
	/// rewinds the tween to the beginning (or end, depending on isReversed) and starts playback,
	/// optionally skipping delay (only relevant for Tweens).
	/// </summary>
	public void restart( bool skipDelay = true )
	{
		reset( skipDelay );
		play();
	}


	/// <summary>
	/// reverses playback. if going forward it will be going backward after this and vice versa.
	/// </summary>
	public virtual void reverse()
	{
		isReversed = !isReversed;

		_completedIterations = isReversed ? Mathf.CeilToInt( _totalElapsedTime / duration ) : Mathf.FloorToInt( _totalElapsedTime / duration );

		// if we are at the "start" of the timeline, based on isReversed,
		// allow the onBegin/onIterationStart callbacks to fire again.
		if( ( isReversed && _totalElapsedTime == totalDuration ) || ( !isReversed && _totalElapsedTime == 0f ) )
		{
			_didBegin = false;
			_fireIterationStart = true;
		}
	}


	/// <summary>
	/// completes the tween. sets the playhead to it's final position as if the tween completed normally.
	/// takes into account if the tween was playing forward or reversed.
	/// </summary>
	public virtual void complete()
	{
		if( iterations < 0 )
			return;

		// set full elapsed time and let the next iteration finish it off
		goTo( isReversed ? 0 : totalDuration, true );
	}


	/// <summary>
	/// goes to the specified time clamping it from 0 to the total duration of the tween. if the tween is
	/// not playing it can optionally be force updated to the time specified. delays are not taken into effect.
	/// (must be implemented by inherited classes.)
	/// </summary>
	public abstract void goTo( float time, bool skipDelay = true );

	/// <summary>
	/// goes to the time and starts playback skipping any delays
	/// </summary>
	public void goToAndPlay( float time, bool skipDelay = true )
	{
		goTo( time, skipDelay );
		play();
	}


	/// <summary>
	/// waits for either completion or destruction. call in a Coroutine and yield on the return
	/// </summary>
	public IEnumerator waitForCompletion()
	{
		while( state != GoTweenState.Complete && state != GoTweenState.Destroyed )
			yield return null;

		yield break;
	}
}
