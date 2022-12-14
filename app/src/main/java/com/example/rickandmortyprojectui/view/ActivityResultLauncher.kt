package com.example.rickandmortyprojectui.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.view.isVisible
import com.example.rickandmortyprojectui.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ActivityResultLauncher : AppCompatActivity() {
    private val signInLauncher = registerForActivityResult(FirebaseAuthUIActivityResultContract())
    { res -> this.onSignInResult(res) }
    private lateinit var sharedPreference: SharedPreferences
    private lateinit var btnSignOut: Button
    private lateinit var btnSignIn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_launcher)

        sharedPreference = getSharedPreferences("CONNEXION", MODE_PRIVATE)
        val isLogged = sharedPreference.getBoolean("isLogged",false)

        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        btnSignOut = findViewById(R.id.signout_btn)
        btnSignIn = findViewById(R.id.signin_btn)
        btnSignIn.setOnClickListener { createSignInIntent() }
        btnSignOut.setOnClickListener { signOut() }
        if (isLogged) {
            btnSignIn.isVisible = false
            btnSignOut.isVisible = true
        }
        else {
            btnSignIn.isVisible = true
            btnSignOut.isVisible = false
        }

        val btnCharacters: Button = findViewById(R.id.characters_btn)
        btnCharacters.setOnClickListener {
            val intent = Intent(this, CharactersListActivity::class.java)
            startActivity(intent)
        }

    }

    private fun createSignInIntent() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
//            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build())
//            AuthUI.IdpConfig.FacebookBuilder().build(),
//            AuthUI.IdpConfig.TwitterBuilder().build())

        val signInIntent =  AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == RESULT_OK) {
            val editor = sharedPreference.edit()
            editor.putBoolean("isLogged", true)
            editor.apply()
            btnSignIn.isVisible = false
            btnSignOut.isVisible = true
        } else {
            createSignInIntent()
        }
    }

    open fun emailLink() {
        val actionCodeSettings = ActionCodeSettings.newBuilder()
            .setAndroidPackageName( /* yourPackageName= */
                "...",  /* installIfNotAvailable= */
                true,  /* minimumVersion= */
                null)
            .setHandleCodeInApp(true) // This must be set to true
            .setUrl("https://google.com") // This URL needs to be whitelisted
            .build()

        val providers = listOf(
            AuthUI.IdpConfig.EmailBuilder()
                .enableEmailLinkSignIn()
                .setActionCodeSettings(actionCodeSettings)
                .build()
        )
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun signOut() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                val editor = sharedPreference.edit()
                editor.putBoolean("isLogged", false)
                editor.apply()
                btnSignIn.isVisible = true
                btnSignOut.isVisible = false
                createSignInIntent()
            }
    }

    private fun delete() {
        AuthUI.getInstance()
            .delete(this)
            .addOnCompleteListener {
                createSignInIntent()
            }
    }

    private fun themeAndLogo() {
        val providers = emptyList<AuthUI.IdpConfig>()

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
//            .setLogo(R.drawable.my_great_logo) // Set logo drawable
//            .setTheme(R.style.MySuperAppTheme) // Set theme
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun privacyAndTerms() {
        val providers = emptyList<AuthUI.IdpConfig>()
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setTosAndPrivacyPolicyUrls(
                "https://example.com/terms.html",
                "https://example.com/privacy.html")
            .build()
        signInLauncher.launch(signInIntent)
    }

    open fun catchEmailLink() {
        val providers: List<AuthUI.IdpConfig> = emptyList()

        if (AuthUI.canHandleIntent(intent)) {
            val extras = intent.extras ?: return
            val link = extras.getString("email_link_sign_in")
            if (link != null) {
                val signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setEmailLink(link)
                    .setAvailableProviders(providers)
                    .build()
                signInLauncher.launch(signInIntent)
            }
        }
    }
}