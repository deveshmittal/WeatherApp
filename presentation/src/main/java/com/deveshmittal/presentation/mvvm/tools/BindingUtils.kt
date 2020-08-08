/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.deveshmittal.presentation.mvvm.tools

import android.databinding.BindingAdapter
import android.view.View


@BindingAdapter("visibleOrGone")
fun View.setVisibleOrGone(show: Boolean?) {
    visibility = if (show ?: false) View.VISIBLE else View.GONE
}

@BindingAdapter("activated")
fun View.setActivated(show: Boolean?) {
    isActivated = show ?: false
}

@BindingAdapter("visible")
fun View.setVisible(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.INVISIBLE
}








