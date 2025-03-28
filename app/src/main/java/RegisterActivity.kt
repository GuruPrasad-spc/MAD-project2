package com.example.mad_project

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var profileImageView: ImageView
    private lateinit var firstNameInput: EditText
    private lateinit var lastNameInput: EditText
    private lateinit var mailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmPasswordInput: EditText
    private lateinit var registerButton: Button
    private lateinit var selectImageButton: Button

    private var selectedImageUri: Uri? = null

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        // Initialize UI components
        profileImageView = findViewById(R.id.profileImageView)
        firstNameInput = findViewById(R.id.firstNameInput)
        lastNameInput = findViewById(R.id.lastNameInput)
        mailInput = findViewById(R.id.mailInput)
        passwordInput = findViewById(R.id.passwordInput)
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput)
        registerButton = findViewById(R.id.button8)
        selectImageButton = findViewById(R.id.selectImageButton)  // Correct reference

        // Select image button click listener
        selectImageButton.setOnClickListener {
            openImageChooser()
        }

        // Register button click listener
        registerButton.setOnClickListener {
            handleRegister()
        }
    }

    // Open image chooser (Gallery)
    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    // Handle image selection result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            profileImageView.setImageURI(selectedImageUri)  // Display the selected image
        }
    }

    // Handle registration
    private fun handleRegister() {
        val firstName = firstNameInput.text.toString().trim()
        val lastName = lastNameInput.text.toString().trim()
        val email = mailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()
        val confirmPassword = confirmPasswordInput.text.toString().trim()

        if (validateInputs(firstName, lastName, email, password, confirmPassword)) {
            val imageMessage = if (selectedImageUri != null) {
                " with profile image"
            } else {
                " without profile image"
            }

            Toast.makeText(this, "Registered successfully$imageMessage!", Toast.LENGTH_LONG).show()

            // Navigate to login activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Validate user inputs
    private fun validateInputs(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {

        if (firstName.isEmpty()) {
            firstNameInput.error = "First name is required"
            return false
        }

        if (lastName.isEmpty()) {
            lastNameInput.error = "Last name is required"
            return false
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mailInput.error = "Enter a valid email"
            return false
        }

        if (password.isEmpty() || password.length < 6) {
            passwordInput.error = "Password must be at least 6 characters"
            return false
        }

        if (confirmPassword.isEmpty() || confirmPassword != password) {
            confirmPasswordInput.error = "Passwords do not match"
            return false
        }

        return true
    }
}
