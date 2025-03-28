import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mad_project.EventActivity
import com.example.mad_project.R

class LoginActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)  // Use the correct layout name

        val continueButton: Button = findViewById(R.id.loginButton)  // Ensure ID matches the XML
        continueButton.setOnClickListener {
            startActivity(Intent(this, EventActivity::class.java))
        }
    }
}
