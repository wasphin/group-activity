package org.deveden.groupactivity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import org.deveden.groupactivity.account.UserAccountManager
import org.deveden.groupactivity.signin.LoginActivity

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


class LaunchActivity : Activity() {

    companion object {
        private val REQUEST_LOGIN = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (UserAccountManager.getInstance().hasActivateAccount()) {
            val intent = Intent(this as Context, LoginActivity::class.java)
            startActivityForResult(intent, REQUEST_LOGIN)
            return
        }

        transferToMain()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_LOGIN) {
            if (resultCode == RESULT_OK) {
                transferToMain()
                return
            }
        }

        finish()
    }

    private fun transferToMain() {
        val intent = Intent(this as Context, GroupActivity::class.java)
        startActivity(intent)

        finish()
    }
}
