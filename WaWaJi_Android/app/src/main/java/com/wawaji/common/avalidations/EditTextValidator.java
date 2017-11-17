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

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import java.util.ArrayList;

/**
 * EditText校验器
 * 
 * @since 2016.03.09
 * @author admin
 * 
 */
@SuppressWarnings("JavaDoc")
public class EditTextValidator {

	private View button; // 按钮
	private ArrayList<ValidationModel> validationModels; // 校验器列表

	// 构造方法
	public EditTextValidator(View button) {
		init(button);
	}

	/**
	 * 初始化校验器
	 * @param button
	 */
	private void init(View button) {
		this.button = button;
		validationModels = new ArrayList<>();
	}

	/**
	 * 
	 * 设置button，支持各种有点击事件的view
	 * 
	 * @param button
	 * @return
	 */
	public EditTextValidator setButton(View button) {
		this.button = button;
		return this;
	}
	
	/**
	 * 得到button
	 * @return
	 */
	public View getButton() {
		return button;
	}

	/**
	 * 添加校验器模型
	 * @param validationModel
	 * @return
	 */
	public EditTextValidator add(ValidationModel validationModel) {
		validationModels.add(validationModel);
		return this;
	}

	/**
	 * 校验EditText
	 * @return
	 */
	public EditTextValidator execute() {
		for (ValidationModel validationModel : validationModels) {
			if (validationModel.getEditText() == null) {
				return this;
			}
			validationModel.getEditText().addTextChangedListener(
					new TextWatcher() {

						@Override
						public void onTextChanged(CharSequence s, int start,
								int before, int count) {
//							setEnabled();
						}

						@Override
						public void beforeTextChanged(CharSequence s,
								int start, int count, int after) {
						}

						@Override
						public void afterTextChanged(Editable s) {
						}
					});
		}
//		setEnabled();
		return this;
	}

	/**
	 * 设置Button是否可点击
	 */
	protected void setEnabled() {
		for (ValidationModel validationModel : validationModels) {
			if (button != null) {
				if (validationModel.isTextEmpty()) {
					// 如果有一个是空的，button直接不可点击
					button.setEnabled(false);
					return;
				} else {
					if (!button.isEnabled()) {
						button.setEnabled(true);
					}
				}
			} else {
				return;
			}
		}
	}

	/**
	 * 判断是否通过效验
	 * @return
	 */
	public boolean validate() {
		for (ValidationModel validationModel : validationModels) {
			if (validationModel.getValidationExecutor() == null
					|| validationModel.getEditText() == null) {
				String text = validationModel.getText();
				if (!TextUtils.isEmpty(text) 
						&& !validationModel.getValidationExecutor().doValidate(text)) {
					return false;
				}
				// 如果没有验证处理器，直接返回正确
				return true;
			}
			if (!validationModel.getValidationExecutor().doValidate(
					validationModel.getEditText().getText().toString())) {
				// 如果需要做单个EditText验证不通过标记，可以在这里实现
				// 只要有不通过的直接返回false，不要往下执行了
				return false;
			}
		}
		return true;
	}

}