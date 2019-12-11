package jesusserrano.ittepic.edu.tpdm_revau2_practica1_15401058

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    var btnPacientes : Button ?= null
    var btnMedicos : Button ?= null
    var btnCitas : Button ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnPacientes = findViewById(R.id.btnpacientes)
        btnMedicos = findViewById(R.id.btnmedicos)
        btnCitas = findViewById(R.id.btncitas)

        btnPacientes?.setOnClickListener {
            var activity = Intent(this, Main2Activity::class.java)
            startActivity(activity)
        }
        btnMedicos?.setOnClickListener {
            var activity = Intent(this, Main3Activity::class.java)
            startActivity(activity)
        }
        btnCitas?.setOnClickListener {
            var activity = Intent(this, Main4Activity::class.java)
            startActivity(activity)
        }
    }
}
