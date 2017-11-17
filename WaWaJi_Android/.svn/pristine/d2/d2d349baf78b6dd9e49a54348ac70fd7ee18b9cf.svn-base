/**
 *  Copyright 2014 ken.cai (http://www.shangpuyun.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 *
 */
package com.wawaji.common.avalidations;

import android.text.TextUtils;
import android.widget.EditText;

/**
 * 校验器模型
 * 
 * @since 2016.03.09
 * @author admin
 */
public class ValidationModel {

	private String text;
	private EditText editText;
	private ValidationExecutor validationExecutor;

	// 构造方法
	public ValidationModel(EditText editText,
			ValidationExecutor validationExecutor) {
		this.editText = editText;
		this.validationExecutor = validationExecutor;
	}

	// 构造方法
	public ValidationModel(String text,
			ValidationExecutor validationExecutor) {
		this.text = text;
		this.validationExecutor = validationExecutor;
	}

	public String getText() {
		return text;
	}
	
	public EditText getEditText() {
		return editText;
	}

	public ValidationModel setEditText(EditText editText) {
		this.editText = editText;
		return this;
	}

	public ValidationExecutor getValidationExecutor() {
		return validationExecutor;
	}

	public ValidationModel setValidationExecutor(
			ValidationExecutor validationExecutor) {
		this.validationExecutor = validationExecutor;
		return this;
	}

	/**
	 * 判断文本是否为空
	 * 
	 * @return boolean
	 */
	public boolean isTextEmpty() {
		return editText == null || TextUtils.isEmpty(editText.getText());
	}

}