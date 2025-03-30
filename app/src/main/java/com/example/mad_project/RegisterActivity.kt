package com.example.mad_project

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

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
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance().reference

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
        selectImageButton = findViewById(R.id.selectImageButton)

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
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid
                        userId?.let { uid ->
                            if (selectedImageUri != null) {
                                uploadProfileImage(uid, firstName, lastName, email)
                            } else {
                                saveUserToFirestore(uid, firstName, lastName, email, null)
                            }
                        }
                    } else {
                        Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    // Upload profile image to Firebase Storage
    private fun uploadProfileImage(userId: String, firstName: String, lastName: String, email: String) {
        val imageRef = storage.child("profile_images/$userId.jpg")
        selectedImageUri?.let { uri ->
            imageRef.putFile(uri)
                .addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                        saveUserToFirestore(userId, firstName, lastName, email, downloadUri.toString())
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show()
                    saveUserToFirestore(userId, firstName, lastName, email, null)
                }
        }
    }

    // Save user data to Firestore
    private fun saveUserToFirestore(userId: String, firstName: String, lastName: String, email: String, imageUrl: String?) {
        val user = hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "email" to email,
            "profileImageUrl" to (imageUrl ?: "")
        )

        firestore.collection("users").document(userId).set(user)
            .addOnSuccessListener {
                Toast.makeText(this, "Registered successfully!", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, login::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to save data: ${e.message}", Toast.LENGTH_LONG).show()
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
