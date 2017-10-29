package org.deveden.groupactivity.signin

import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.properties.Delegates
import org.deveden.groupactivity.R

/**
 * Copyright (c) 2017 Xiaofeng Wang <wasphin@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


class LoginActivity : AppCompatActivity() {

    companion object {
        private val RESULT_NONE = Activity.RESULT_FIRST_USER

        private var _progressDialog: ProgressDialog by Delegates.notNull()
    }

    private var _doubleBackToExitPressedOnce: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        setupUi()

        setResult(RESULT_NONE)
    }

    override fun onBackPressed() {
        if (_doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        _doubleBackToExitPressedOnce = true

        Toast.makeText(this, getString(R.string.double_press_to_exit), Toast.LENGTH_SHORT).show()

        Handler().postDelayed({
            _doubleBackToExitPressedOnce = false
        }, 2000)
    }

    private fun setupUi() {
        login.setOnClickListener {
            login(it)
        }

        _progressDialog = ProgressDialog(this, R.style.Theme_AppCompat_NoActionBar)
        _progressDialog.setMessage(getString(R.string.logging_in))
        _progressDialog.setIndeterminate(true)
    }

    private fun login(view: View) {
        if (!validate()) {
            return
        }

        view.setEnabled(false)

        _progressDialog.show()

        Handler().postDelayed({
            onLoggedIn()
        }, 3000)
    }

    private fun validate(): Boolean {
        var valid: Boolean = true

        if (login_email.text.toString().isEmpty()) {
            login_email.error = getString(R.string.email) + getString(R.string._cannot_be_empty)
            valid = false
        }

        if (login_password.text.toString().isEmpty()) {
            login_password.error = getString(R.string.password) + getString(R.string._cannot_be_empty)
            valid = false
        }

        return valid
    }

    fun onLoggedIn() {
        _progressDialog.dismiss()

        setResult(Activity.RESULT_OK)

        // todo:
        // intent.putExtra("user-id", )

        finish()
    }

    fun onLoginFailed(message: String?) {
        login.setEnabled(true)

        _progressDialog.dismiss()

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
