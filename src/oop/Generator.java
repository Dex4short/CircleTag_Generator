package oop;

import java.util.ArrayList;
import java.util.Collections;

import marker.CircleMark;
import marker.CircleTag;
import marker.enums.CardinalDirection;
import marker.enums.DiscreteSize;
import marker.enums.Polarity;
import misc.Debug;

public abstract class Generator {
	private CircleTag circleTag;
	private ArrayList<CircleTag> savedStates;
	private CircleMark mark;
	private int d, o, s, direction, orbit, size, markerId, idDifference, leastIdDifference, lastIdAdjustment;
	private boolean hasDifference;
	
	public Generator() {
		circleTag = new CircleTag();
		savedStates = new ArrayList<CircleTag>();
		Debug.isDebugOn = false;
	}
	
	public abstract void onIdGenerated(CircleTag circleTag);

	public CircleTag getCircleTag() {
		return circleTag;
	}
	

	public void generateCircleTag(int id) {
		Debug.showConsoleWindow();
		Debug.clear();
		Debug.println("ID: " + id);
		
		if(!generateCircleTag(id, 1, 1)) {
			clearCircleMarks();
		}
		
		onIdGenerated(circleTag);
		Debug.println("ID Generated: " + circleTag.getMarkerId());
	}
	
	protected boolean generateCircleTag(int id, int offset, int alt) {
		int target_id = (id - (((offset)/2) * alt));
		
		clearCircleMarks();
		clearSavedStates();
		
		int maxSize, minOrbit=1, added=-1, minDifference=Integer.MAX_VALUE;
		while(minOrbit < 5) {
			maxSize=7;
			
			while(maxSize > 0) {
				added = 0;
				
				do {
					if(markerId < id) {
						added = addHollows(id, maxSize, minOrbit);
					}
					else if(markerId > id){
						added = addSolids(id, maxSize, minOrbit);
					}
					else{
						if(markerId != target_id) {
							//System.out.print("for(" + target_id + " w/ " + id +") ");
							minOrbit = 4;
							break;
						}
						else {
							return true;
						}
					}

					if(leastIdDifference <= minDifference) {
						minDifference = leastIdDifference;
						Debug.println("minDifference: " + minDifference);
						
						saveCircleTagState();
					}
				} while(added != 0);
				
				clearCircleMarks();

				maxSize--;
				Debug.println("\t\t\tmaxSize: " + maxSize);
			}

			minOrbit++;
			Debug.println("\t\tminOrbit: " + minOrbit);
		}
		
		//reorderSavedStates();
		for(int i=savedStates.size()-1; i>=0; i--) {
			if(adjustCircleMarks(savedStates.get(i), target_id)) return true;
		}
		
		//System.out.print(" [" + markerId + "] ");
		if(offset == 10) {
			//System.out.print(" no solution..."+ markerId + "  ");
			return false;
		}

		//System.out.print(" recursing: " + offset + " ");
		return generateCircleTag(id-(alt*offset), offset+1, alt*-1);
	}
	
	protected void clearCircleMarks() {
		circleTag.clearCircleMarks();
		Debug.println("\t\t\tclear();");
		
		markerId = 0;
	}
	
	protected void clearSavedStates() {
		savedStates.clear();
	}
	
	protected int addHollows(int targetId, int maxSizeLimit, int minOrbitLimit) {
		Debug.println("\t\t\t\taddHollows();");
		return addCircleMarks(targetId, maxSizeLimit, minOrbitLimit, Polarity.HOLLOW);
	}
	
	protected int addSolids(int targetId, int maxSizeLimit, int minOrbitLimit) {
		Debug.println("\t\t\t\taddSolids();");
		return addCircleMarks(targetId, maxSizeLimit, minOrbitLimit, Polarity.SOLID);
	}
	
	protected int addCircleMarks(int targetId, int maxSizeLimit, int minOrbitLimit, Polarity polarity) {
		leastIdDifference = Integer.MAX_VALUE;
		
		int added = 0;
		while(markerId != targetId) {
			mark = new CircleMark(1, CardinalDirection.NW, DiscreteSize.A, polarity);
			circleTag.addCircleMark(mark);

			for(d=7; d>=0; d--) {
				mark.setDirection(CardinalDirection.values()[d]);
				
				for(s=0; s<maxSizeLimit; s++) {
					mark.setDiscreteSize(DiscreteSize.values()[s]);
					
					for(o=5; o>=minOrbitLimit; o--) {
						mark.setOrbit(o);

						if(circleTag.isMarkerValid()) {
							markerId = circleTag.getMarkerId();
							
							setPartialCircleMarkPosition(markerId, targetId, polarity);
						}
					}
				}
			}
			
			if(!setFinalCircleMarkPosition()) {
				Debug.println("\t\t\t\t\tinfinity");
				return added;
			}

			added++;
		}
		
		return added;
	}
	
	protected void setPartialCircleMarkPosition(int markerId, int targetId, Polarity polarity) {
		idDifference = idDifference(markerId, targetId);
		
		if(idDifference < leastIdDifference){
			leastIdDifference = idDifference;
			
			direction = d;
			orbit = o;
			size = s;
			
			hasDifference = true;
			
			Debug.println("\t\t\t\t\tdifference: " + leastIdDifference);
		}
	}
	
	protected int idDifference(int id1, int id2) {
		return Math.abs(id1 - id2);
	}
	
	protected boolean setFinalCircleMarkPosition() {
		if(hasDifference) {
			mark.setDirection(CardinalDirection.values()[direction]);
			mark.setDiscreteSize(DiscreteSize.values()[size]);
			mark.setOrbit(orbit);
			
			Debug.println("\t\t\t\t\t" + mark.toString());

			markerId = circleTag.getMarkerId();
			hasDifference = false;
			return true;
		}
		else {
			circleTag.removeCircleMark(mark);
			Debug.println("\t\t\t\t\tremoved(" + mark.toString() +"); ");
			
			markerId = circleTag.getMarkerId();
			return false;
		}
	}
	
	protected void saveCircleTagState() {
		Debug.println("saveState();");
		
		CircleTag newSavedState = new CircleTag();
		newSavedState.copyCircleMarks(circleTag);
		
		savedStates.add(newSavedState);
	}
	
	public void reorderSavedStates() {
		ArrayList<CircleTag> zigzagOrder = new ArrayList<CircleTag>();
    	
    	int 
    	i,
    	length = savedStates.size(),
    	index = length/2,
    	direction = 1;
    	
    	for(i=0; i<length; i++){
    		index += i * direction;
    		
    		zigzagOrder.add(savedStates.get(index));
    		
    		direction *= -1;
    	}
    	
    	savedStates = zigzagOrder;
    }
	
	protected boolean adjustCircleMarks(CircleTag savedState, int id) {
		Debug.println("adjust();");

		Collections.sort(savedState.getCircleMarkList(), (a, b) -> a.getDiscreteSize().compareTo(b.getDiscreteSize()));
		
		CircleTag tempState = new CircleTag();
		CircleMark lastMark;
		
		tempState.copyCircleMarks(savedState);
		while(tempState.getCircleMarksCount() > 1) {
			circleTag.copyCircleMarks(tempState);
			
			if(adjustCircleMark(circleTag, 0, circleTag.getCircleMarksCount(), id)) {
				return true;
			}
			
			lastMark = tempState.getCircleMarkList().get(0);
			tempState.removeCircleMark(lastMark);
		}
		
		return false;
	}
	
	protected boolean adjustCircleMark(CircleTag circleTag, int circleMarkIndex, int circleMarkCount, int id) {
		Debug.println("\tsubAdjustment(index: " + circleMarkIndex + ");");
		
		if(circleMarkIndex < circleMarkCount) {
			CircleTag tempState = new CircleTag();
			CircleMark mark = circleTag.getCircleMark(circleMarkIndex);
			
			int orbitOrigin = mark.getOrbit();
			
			for(int orbit=orbitOrigin; orbit<=5; orbit++) {
				tempState.copyCircleMarks(circleTag);
				
				mark.setOrbit(orbit);
				if(adjustCircleMark(circleTag, circleMarkIndex + 1, circleMarkCount, id)) {
					return true;
				}
				else {
					Debug.println("\tsubRedjustment(index: " + circleMarkIndex + ", orbit: " + orbit + ");");
				}
				
				circleTag.copyCircleMarks(tempState);
			}

			mark.setOrbit(orbitOrigin);
		}
		
		if(circleTag.isMarkerValid()) {
			int added = -1;
			
			while(added != 0) {
				if(markerId < id) {
					added = addHollows(id, 7, 1);
				}
				else if(markerId > id){
					added = addSolids(id, 7, 1);
				}
				else {
					return true;
				}
			}
		}
		
		return false;
	}
	
	
	
}
