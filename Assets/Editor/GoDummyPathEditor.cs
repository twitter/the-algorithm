using UnityEngine;
using UnityEditor;
using System.Collections;
using System.Linq;
using System.Collections.Generic;
using System.IO;



[CustomEditor( typeof( GoDummyPath ) )]
public class GoDummyPathEditor : Editor
{
	private GoDummyPath _target;
	private GUIStyle _labelStyle;
	private GUIStyle _indexStyle;
	
	private int _insertIndex = 0;
	private float _snapDistance = 5f;
	private bool _showNodeDetails;
	private bool _fileLoadSaveDetails;
	private int _selectedNodeIndex = -1;
	
	
	#region Monobehaviour and Editor
	
	void OnEnable()
	{
		// setup the font for the 'begin' 'end' text
		_labelStyle = new GUIStyle();
		_labelStyle.fontStyle = FontStyle.Bold;
		_labelStyle.normal.textColor = Color.white;
		_labelStyle.fontSize = 16;
		
		_indexStyle = new GUIStyle();
		_indexStyle.fontStyle = FontStyle.Bold;
		_indexStyle.normal.textColor = Color.white;
		_indexStyle.fontSize = 12;
		
		_target = (GoDummyPath)target;
	}
	
	
	public override void OnInspectorGUI()
	{
		// what kind of handles shall we use?
		EditorGUILayout.BeginHorizontal();
		EditorGUILayout.PrefixLabel( "Use Standard Handles" );
		_target.useStandardHandles = EditorGUILayout.Toggle( _target.useStandardHandles );
		EditorGUILayout.EndHorizontal();

		
		// path name:
		EditorGUILayout.BeginHorizontal();
		EditorGUILayout.PrefixLabel( "Route Name" );
		_target.pathName = EditorGUILayout.TextField( _target.pathName );
		EditorGUILayout.EndHorizontal();
		
		if( _target.pathName == string.Empty )
			_target.pathName = "route" + Random.Range( 1, 100000 );
		
		
		// path color:
		EditorGUILayout.BeginHorizontal();
		EditorGUILayout.PrefixLabel( "Route Color" );
		_target.pathColor = EditorGUILayout.ColorField( _target.pathColor );
		EditorGUILayout.EndHorizontal();
		
		
		// force straight lines:
		EditorGUILayout.BeginHorizontal();
		EditorGUILayout.PrefixLabel( "Force Straight Line Path" );
		_target.forceStraightLinePath = EditorGUILayout.Toggle( _target.forceStraightLinePath );
		EditorGUILayout.EndHorizontal();
		
		
		// resolution
		EditorGUILayout.BeginHorizontal();
		EditorGUILayout.PrefixLabel( "Editor Drawing Resolution" );
		_target.pathResolution = EditorGUILayout.IntSlider( _target.pathResolution, 2, 100 );
		EditorGUILayout.EndHorizontal();

		
		EditorGUILayout.Separator();
		
		
		// insert node - we need 3 or more nodes for insert to make sense
		if( _target.nodes.Count > 2 )
		{
			EditorGUILayout.BeginHorizontal();
			EditorGUILayout.PrefixLabel( "Insert Node" );
			_insertIndex = EditorGUILayout.IntField( _insertIndex );
			if( GUILayout.Button( "Insert" ) )
			{
				// validate the index
				if( _insertIndex >= 0 && _insertIndex < _target.nodes.Count )
				{
					// insert the node offsetting it a bit from the previous node
					var copyNodeIndex = _insertIndex == 0 ? 0 : _insertIndex;
					var copyNode = _target.nodes[copyNodeIndex];
					copyNode.x += 10;
					copyNode.z += 10;
					
					insertNodeAtIndex( copyNode, _insertIndex );
				}
			}
			EditorGUILayout.EndHorizontal();
		}


		// close route?
		if( GUILayout.Button( "Close Path" ) )
		{
			Undo.RecordObject( _target, "Path Vector Changed" );
			closeRoute();
			GUI.changed = true;
		}
		
		
		// shift the start point to the origin
		if( GUILayout.Button( "Shift Path to Start at Origin" ) )
		{
			Undo.RecordObject( _target, "Path Vector Changed" );
			
			var offset = Vector3.zero;
			
			// see what kind of path we are. the simplest case is just a straight line
			var path = new GoSpline( _target.nodes, _target.forceStraightLinePath );
			if( path.splineType == GoSplineType.StraightLine || _target.nodes.Count < 5 )
				offset = Vector3.zero - _target.nodes[0];
			else
				offset = Vector3.zero - _target.nodes[1];
			
			for( var i = 0; i < _target.nodes.Count; i++ )
				_target.nodes[i] += offset;
			
			GUI.changed = true;
		}
		
		
		// reverse
		if( GUILayout.Button( "Reverse Path" ) )
		{
			Undo.RecordObject( _target, "Path Vector Changed" );
			_target.nodes.Reverse();
			GUI.changed = true;
		}
		
		
		// persist to disk
		EditorGUILayout.Space();
		EditorGUILayout.LabelField( "Save to/Read from Disk" );

		EditorGUILayout.Space();
		EditorGUILayout.BeginHorizontal();
		EditorGUILayout.PrefixLabel( "Serialize and Save Path" );
		if( GUILayout.Button( "Save" ) )
		{
			var path = EditorUtility.SaveFilePanel( "Save path", Application.dataPath + "/StreamingAssets", _target.pathName + ".asset", "asset" );
			if( path != string.Empty )
			{
				persistRouteToDisk( path );
				
				// fetch the filename and set it as the routeName
				_target.pathName = Path.GetFileName( path ).Replace( ".asset", string.Empty );
				GUI.changed = true;
			}
		}
		EditorGUILayout.EndHorizontal();


		// load from disk
		EditorGUILayout.BeginHorizontal();
		EditorGUILayout.PrefixLabel( "Load saved path" );
		if( GUILayout.Button( "Load" ) )
		{
			var path = EditorUtility.OpenFilePanel( "Choose path to load", Path.Combine( Application.dataPath, "StreamingAssets" ), "asset" );
			if( path != string.Empty )
			{
				if( !File.Exists( path ) )
				{
					EditorUtility.DisplayDialog( "File does not exist", "Path couldn't find the file you specified", "Close" );
				}
				else
				{
					_target.nodes = GoSpline.bytesToVector3List( File.ReadAllBytes( path ) );
					_target.pathName = Path.GetFileName( path ).Replace( ".asset", string.Empty );
					GUI.changed = true;
				}
			}
		}
		EditorGUILayout.EndHorizontal();

				
		// node display
		EditorGUILayout.Space();
		_showNodeDetails = EditorGUILayout.Foldout( _showNodeDetails, "Show Node Values" );
		if( _showNodeDetails )
		{
			EditorGUI.indentLevel++;
			for( int i = 0; i < _target.nodes.Count; i++ )
				_target.nodes[i] = EditorGUILayout.Vector3Field( "Node " + ( i + 1 ), _target.nodes[i] );
			EditorGUI.indentLevel--;
		}
		
		
		// instructions
		EditorGUILayout.Space();
		EditorGUILayout.HelpBox( "While dragging a node, hold down Ctrl and slowly move the cursor to snap to a nearby point\n\n" +
		               "Click the 'Close Path' button to add a new node that will close out the current path.\n\n" +
		               "Hold Command while dragging a node to snap in 5 point increments\n\n" +
		               "Double click to add a new node at the end of the path\n\n" +
					   "Hold down alt while adding a node to prepend the new node at the front of the route\n\n" +
		               "Press delete or backspace to delete the selected node\n\n" +
		               "NOTE: make sure you have the pan tool selected while editing paths", MessageType.None );

		
		// update and redraw:
		if( GUI.changed )
		{
			EditorUtility.SetDirty( _target );
			Repaint();
		}
	}
	
	
	void OnSceneGUI()
	{
		if( !_target.gameObject.activeSelf )
			return;
		
		// handle current selection and node addition via double click or ctrl click
		if( Event.current.type == EventType.MouseDown )
		{
			var nearestIndex = getNearestNodeForMousePosition( Event.current.mousePosition );
			_selectedNodeIndex = nearestIndex;
			
			// double click to add
			if( Event.current.clickCount > 1 )
			{
				var translatedPoint = HandleUtility.GUIPointToWorldRay( Event.current.mousePosition )
						.GetPoint( ( _target.transform.position - Camera.current.transform.position ).magnitude );
				
				Undo.RecordObject( _target, "Path Node Added" );
				
				// if alt is down then prepend the node to the beginning
				if( Event.current.alt )
					insertNodeAtIndex( translatedPoint, 0 );
				else
					appendNodeAtPoint( translatedPoint );
			}
		}
		

		if( _selectedNodeIndex >= 0 )
		{
			// shall we delete the selected node?
			if( Event.current.keyCode == KeyCode.Delete || Event.current.keyCode == KeyCode.Backspace )
			{
				if (_target.nodes.Count > 2) {
					Undo.RecordObject( _target, "Path Node Deleted" );
					Event.current.Use();
					removeNodeAtIndex( _selectedNodeIndex );
					_selectedNodeIndex = -1;
				}
			}
		}
		
		
		if( _target.nodes.Count > 1 )
		{
			// allow path adjustment undo:
			Undo.RecordObject( _target, "Path Vector Changed" );
			
			// path begin and end labels or just one if the path is closed
			if( Vector3.Distance( _target.nodes[0], _target.nodes[_target.nodes.Count - 1] ) == 0 )
			{
				Handles.Label( _target.nodes[0], "  Begin and End", _labelStyle );
			}
			else
			{
				Handles.Label( _target.nodes[0], "  Begin", _labelStyle );
				Handles.Label( _target.nodes[_target.nodes.Count - 1], "  End", _labelStyle );
			}
			
			// draw the handles, arrows and lines
			drawRoute();
			
			for( var i = 0; i < _target.nodes.Count; i++ )
			{
				Handles.color = _target.pathColor;
				
				// dont label the first and last nodes
				if( i > 0 && i < _target.nodes.Count - 1 )
					Handles.Label( _target.nodes[i] + new Vector3( 3f, 0, 1.5f ), i.ToString(), _indexStyle );
				
				Handles.color = Color.white;
				if( _target.useStandardHandles )
				{
					_target.nodes[i] = Handles.PositionHandle( _target.nodes[i], Quaternion.identity );
				}
				else
				{
					// how big shall we draw the handles?
					var distanceToTarget = Vector3.Distance( SceneView.lastActiveSceneView.camera.transform.position, _target.transform.position );
					distanceToTarget = Mathf.Abs( distanceToTarget );
					var handleSize = Mathf.Ceil( distanceToTarget / 75 );
					
					_target.nodes[i] = Handles.FreeMoveHandle( _target.nodes[i],
					                        Quaternion.identity,
					                        handleSize,
					                        new Vector3( 5, 0, 5 ),
					                        Handles.SphereCap );
				}
				

				// should we snap?  we need at least 4 nodes because we dont snap to the previous and next nodes
				if( Event.current.control && _target.nodes.Count > 3 )
				{
					// dont even bother checking for snapping to the previous/next nodes
					var index = getNearestNode( _target.nodes[i], i, i + 1, i - 1 );
					var nearest = _target.nodes[index];
					var distanceToNearestNode = Vector3.Distance( nearest, _target.nodes[i] );
					
					// is it close enough to snap?
					if( distanceToNearestNode <= _snapDistance )
					{
						GUI.changed = true;
						_target.nodes[i] = nearest;
					}
					else if( distanceToNearestNode <= _snapDistance * 2 )
					{
						// show which nodes are getting close enough to snap to
						var color = Color.red;
						color.a = 0.3f;
						Handles.color = color;
						Handles.SphereCap( 0, _target.nodes[i], Quaternion.identity, _snapDistance * 2 );
						//Handles.DrawWireDisc( _target.nodes[i], Vector3.up, _snapDistance );
						Handles.color = Color.white;
					}
				}					
			} // end for

			
			if( GUI.changed )
			{
				Repaint();
				EditorUtility.SetDirty( _target );
			}
		} // end if
	}
	
	#endregion
	
	
	#region Private methods
	
	private void appendNodeAtPoint( Vector3 node )
	{
		_target.nodes.Add( node );
		
		GUI.changed = true;
	}
	
	
	private void removeNodeAtIndex( int index )
	{
		if( index >= _target.nodes.Count || index < 0 )
			return;
		
		_target.nodes.RemoveAt( index );
		
		GUI.changed = true;
	}
	
	
	private void insertNodeAtIndex( Vector3 node, int index )
	{
		// validate the index
		if( index >= 0 && index < _target.nodes.Count )
		{
			_target.nodes.Insert( index, node );
			
			GUI.changed = true;
		}
	}
	
	
	private void drawArrowBetweenPoints( Vector3 point1, Vector3 point2 )
	{
		// no need to draw arrows for tiny segments
		var distance = Vector3.Distance( point1, point2 );
		if( distance < 40 )
			return;
		
		// we dont want to be exactly in the middle so we offset the length of the arrow
		var lerpModifier = ( distance * 0.5f - 25 ) / distance;
		
		Handles.color = _target.pathColor;
		
		// get the midpoint between the 2 points
		var dir = Vector3.Lerp( point1, point2, lerpModifier );
		var quat = Quaternion.LookRotation( point2 - point1 );
		Handles.ArrowCap( 0, dir, quat, 25 );
		
		Handles.color = Color.white;
	}
	
	
	private int getNearestNode( Vector3 pos, params int[] excludeNodes )
	{
		var excludeNodesList = new System.Collections.Generic.List<int>( excludeNodes );
		var bestDistance = float.MaxValue;
		var index = -1;
		
		var distance = float.MaxValue;
		for( var i = _target.nodes.Count - 1; i >= 0; i-- )
		{
			if( excludeNodesList.Contains( i ) )
				continue;

			distance = Vector3.Distance( pos, _target.nodes[i] );
			if( distance < bestDistance )
			{
				bestDistance = distance;
				index = i;
			}
		}
		return index;
	}
	
	
	private int getNearestNodeForMousePosition( Vector3 mousePos )
	{
		var bestDistance = float.MaxValue;
		var index = -1;
		
		var distance = float.MaxValue;
		for( var i = _target.nodes.Count - 1; i >= 0; i-- )
		{
			var nodeToGui = HandleUtility.WorldToGUIPoint( _target.nodes[i] );
			distance = Vector2.Distance( nodeToGui, mousePos );
			
			if( distance < bestDistance )
			{
				bestDistance = distance;
				index = i;
			}
		}
		
		// make sure we are close enough to a node
		if( bestDistance < 10 )
			return index;
		return -1;
	}
	
	
	private void closeRoute()
	{
		// we will use the GoSpline class to handle the dirtywork of closing the path
		var path = new GoSpline( _target.nodes, _target.forceStraightLinePath );
		path.closePath();
		
		_target.nodes = path.nodes;
		
		GUI.changed = true;
	}
	
	
	private void persistRouteToDisk( string path )
	{
		var bytes = new List<byte>();
		
		foreach( var vec in _target.nodes )
		{
			bytes.AddRange( System.BitConverter.GetBytes( vec.x ) );
			bytes.AddRange( System.BitConverter.GetBytes( vec.y ) );
			bytes.AddRange( System.BitConverter.GetBytes( vec.z ) );
		}

		File.WriteAllBytes( path, bytes.ToArray() );
	}
	
	
	private void drawRoute()
	{
		// if we are forcing straight lines just use this setup
		if( _target.forceStraightLinePath )
		{
			// draw just the route here and optional arrows
			for( var i = 0; i < _target.nodes.Count; i++ )
			{
				Handles.color = _target.pathColor;
				if( i < _target.nodes.Count - 1 )
				{
					Handles.DrawLine( _target.nodes[i], _target.nodes[i + 1] );
					drawArrowBetweenPoints( _target.nodes[i], _target.nodes[i + 1] );
				}
			}
		}
	}
	
	#endregion
	
}
