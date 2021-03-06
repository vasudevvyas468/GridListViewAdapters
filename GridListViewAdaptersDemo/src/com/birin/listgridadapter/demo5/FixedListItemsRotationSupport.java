/**
 * Copyright 2014-present Biraj Patel
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.birin.listgridadapter.demo5;

import android.content.res.Configuration;
import android.os.Bundle;

import com.birin.gridlistviewadapters.utils.PositionCalculator;
import com.birin.gridlistviewadaptersdemo.common.Constants;
import com.birin.listgridadapter.demo4.ChildAndCardClickHandlingFixedListItems;

public class FixedListItemsRotationSupport extends
		ChildAndCardClickHandlingFixedListItems {

	// //////////////////////////////////////////////////////////////////////////////
	// TO MAINTAIN CORRECT POSITION AFTER ORIENTATION CHANGE
	// //////////////////////////////////////////////////////////////////////////////

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adjustScroll(savedInstanceState);
	}

	private int currentOrientation = Configuration.ORIENTATION_UNDEFINED;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		int firstVisible = listView.getFirstVisiblePosition();
		outState.putInt(Constants.KEY_OLD_POSITION, firstVisible);
		outState.putInt(Constants.KEY_OLD_ORIENTATION, currentOrientation);
	}

	private void adjustScroll(Bundle savedInstanceState) {
		currentOrientation = getCurrentOrientation();
		if (null != savedInstanceState) {
			int oldPosition = savedInstanceState.getInt(
					Constants.KEY_OLD_POSITION, 0);
			int oldOrientation = savedInstanceState.getInt(
					Constants.KEY_OLD_ORIENTATION,
					Configuration.ORIENTATION_UNDEFINED);
			final int newPostion = new PositionCalculator(
					Constants.MAX_CARDS_INFO)
					.calculatePositionInNewOrientation(oldPosition,
							oldOrientation, currentOrientation);
			listView.post(new Runnable() {

				@Override
				public void run() {
					listView.setSelection(newPostion);
				}
			});
		}
	}

	// //////////////////////////////////////////////////////////////////////////////
	// TO MAINTAIN CORRECT POSITION AFTER ORIENTATION CHANGE
	// //////////////////////////////////////////////////////////////////////////////

	@Override
	protected String getToastMessage() {
		// Sending null to avoid showing toast.
		return null;
	}
}
