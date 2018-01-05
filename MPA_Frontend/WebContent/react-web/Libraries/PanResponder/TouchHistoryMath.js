/**
 * Copyright (c) 2015-present, Alibaba Group Holding Limited.
 * All rights reserved.
 *
 * Copyright 2013-2015, Facebook, Inc.
 * All rights reserved.
 *
 */
'use strict';

let TouchHistoryMath = {
  /**
   * This code is optimized and not intended to look beautiful. This allows
   * computing of touch centroids that have moved after `touchesChangedAfter`
   * timeStamp. You can compute the current centroid involving all touches
   * moves after `touchesChangedAfter`, or you can compute the previous
   * centroid of all touches that were moved after `touchesChangedAfter`.
   *
   * @param {TouchHistoryMath} touchHistory Standard Responder touch track
   * data.
   * @param {number} touchesChangedAfter timeStamp after which moved touches
   * are considered "actively moving" - not just "active".
   * @param {boolean} isXAxis Consider `x` dimension vs. `y` dimension.
   * @param {boolean} ofCurrent Compute current centroid for actively moving
   * touches vs. previous centroid of now actively moving touches.
   * @return {number} value of centroid in specified dimension.
   */
  centroidDimension(touchHistory, touchesChangedAfter, isXAxis, ofCurrent) {
    let touchBank = touchHistory.touchBank;
    let total = 0;
    let count = 0;

    let oneTouchData = touchHistory.numberActiveTouches === 1 ?
      touchHistory.touchBank[touchHistory.indexOfSingleActiveTouch] : null;

    if (oneTouchData !== null) {
      if (oneTouchData.touchActive && oneTouchData.currentTimeStamp > touchesChangedAfter) {
        total += ofCurrent && isXAxis ? oneTouchData.currentPageX :
          ofCurrent && !isXAxis ? oneTouchData.currentPageY :
          !ofCurrent && isXAxis ? oneTouchData.previousPageX :
          oneTouchData.previousPageY;
        count = 1;
      }
    } else {
      for (let i = 0; i < touchBank.length; i++) {
        let touchTrack = touchBank[i];
        if (touchTrack !== null &&
            touchTrack !== undefined &&
            touchTrack.touchActive &&
            touchTrack.currentTimeStamp >= touchesChangedAfter) {
          let toAdd;  // Yuck, program temporarily in invalid state.
          if (ofCurrent && isXAxis) {
            toAdd = touchTrack.currentPageX;
          } else if (ofCurrent && !isXAxis) {
            toAdd = touchTrack.currentPageY;
          } else if (!ofCurrent && isXAxis) {
            toAdd = touchTrack.previousPageX;
          } else {
            toAdd = touchTrack.previousPageY;
          }
          total += toAdd;
          count++;
        }
      }
    }
    return count > 0 ? total / count : TouchHistoryMath.noCentroid;
  },

  currentCentroidXOfTouchesChangedAfter(touchHistory, touchesChangedAfter) {
    return TouchHistoryMath.centroidDimension(
      touchHistory,
      touchesChangedAfter,
      true,  // isXAxis
      true   // ofCurrent
    );
  },

  currentCentroidYOfTouchesChangedAfter(touchHistory, touchesChangedAfter) {
    return TouchHistoryMath.centroidDimension(
      touchHistory,
      touchesChangedAfter,
      false,  // isXAxis
      true    // ofCurrent
    );
  },

  previousCentroidXOfTouchesChangedAfter(touchHistory, touchesChangedAfter) {
    return TouchHistoryMath.centroidDimension(
      touchHistory,
      touchesChangedAfter,
      true,  // isXAxis
      false  // ofCurrent
    );
  },

  previousCentroidYOfTouchesChangedAfter(touchHistory, touchesChangedAfter) {
    return TouchHistoryMath.centroidDimension(
      touchHistory,
      touchesChangedAfter,
      false,  // isXAxis
      false   // ofCurrent
    );
  },

  currentCentroidX(touchHistory) {
    return TouchHistoryMath.centroidDimension(
      touchHistory,
      0,     // touchesChangedAfter
      true,  // isXAxis
      true   // ofCurrent
    );
  },

  currentCentroidY(touchHistory) {
    return TouchHistoryMath.centroidDimension(
      touchHistory,
      0,     // touchesChangedAfter
      false,  // isXAxis
      true    // ofCurrent
    );
  },

  noCentroid: -1,
};

export default TouchHistoryMath;
