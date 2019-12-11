package jesusserrano.ittepic.edu.tpdm_revau2_practica1_15401058

import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class Main2Activity : AppCompatActivity() {

    var buscar : Button?=null
    var insertar : Button?=null
    var actualizar : Button?=null
    var eliminar : Button?=null
    var id : EditText?=null
    var nombre : EditText?=null
    var sexo : EditText?=null
    var edad : EditText?=null
    var peso : EditText?=null
    var estatura : EditText?=null
    var etiqueta : TextView?=null
    var regresar : Button?=null
    var basedatos = BD_Hospital(this, "Hospital",null,1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        buscar = findViewById(R.id.buscar_pac)
        insertar = findViewById(R.id.insertar_pac)
        actualizar = findViewById(R.id.actualizar_pac)
        eliminar = findViewById(R.id.eliminar_pac)
        id = findViewById(R.id.id_pac)
        nombre = findViewById(R.id.nombre_pac)
        sexo = findViewById(R.id.sexo_pac)
        edad = findViewById(R.id.edad_pac)
        peso = findViewById(R.id.peso_pac)
        estatura = findViewById(R.id.estat_pac)
        etiqueta = findViewById(R.id.etiquetaPaciente)
        regresar = findViewById(R.id.regresar_pac)


        buscarPaciente()

        buscar?.setOnClickListener {
            if(id?.text.toString().isEmpty()) {
                mensaje("Ingresa el id del paciente")
            }else{
                buscarId(id?.text.toString())
            }
        }
        insertar?.setOnClickListener {
            if(validarCamposVacios()){
                mensaje("Aun hay campos vacios")
            }else {
                insertar(nombre?.text.toString(), sexo?.text.toString(),edad?.text.toString(),peso?.text.toString(),estatura?.text.toString())
            }
        }
        actualizar?.setOnClickListener {
            if(id?.text.toString().isEmpty()|| validarCamposVacios()){
                mensaje("Aun hay campos vacios")
            }else {
                actualizar(id?.text.toString())
            }
        }
        eliminar?.setOnClickListener {
            if(id?.text.toString().isEmpty()) {
                mensaje("Ingresa el id del paciente")
            }else{
                AlertDialog.Builder(this).setTitle("ADVERTENCIA").setMessage("¿Estás seguro que deseas eliminar a este paciente?")
                    .setPositiveButton("Confirmar"){dialog,which->
                        eliminar(id?.text.toString())
                    }.setNeutralButton("Cancelar"){dialog,which->
                        return@setNeutralButton
                    }.show()
            }
        }
        regresar?.setOnClickListener{
            finish()
        }
    }

    fun insertar(nombre: String, sexo:String, edad:String, peso:String, estatura:String){
        try{
            var transaccion = basedatos.writableDatabase
            var SQL = "INSERT INTO PACIENTE VALUES(null,'$nombre','$sexo','$edad','$peso','$estatura')"
            transaccion.execSQL(SQL)
            transaccion.close()
            mensaje("Los datos del paciente se insertaron correctamente")
            limpiarCampos()
            buscarPaciente()
        }catch(err: SQLiteException){
            mensaje("No se pudo insertar")
        }
    }
    fun buscarId(id:String){
        try{
            var transaccion = basedatos.readableDatabase
            var SQL = "SELECT * FROM PACIENTE WHERE ID_PAC="+id
            var resultado = transaccion.rawQuery(SQL,null)
            if(resultado.moveToFirst()){
                nombre?.setText(resultado.getString(1))
                sexo?.setText(resultado.getString(2))
                edad?.setText(resultado.getString(3))
                peso?.setText(resultado.getString(4))
                estatura?.setText(resultado.getString(5))
            }else {
                mensaje("No se encontró el id")
            }
            transaccion.close()

        }catch(err: SQLiteException){
            mensaje("No se pudo realizar la consulta")
        }
    }
    fun actualizar(id:String){
        try{
            var transaccion = basedatos.writableDatabase
            var SQL = "UPDATE PACIENTE SET NOMBRE='"+nombre?.text.toString()+"', SEXO='"+sexo?.text.toString()+"', EDAD='"+edad?.text.toString()+"', PESO='"+peso?.text.toString()+"', ESTATURA ='"+estatura?.text.toString()+"' WHERE ID_PAC="+id
            transaccion.execSQL(SQL)
            transaccion.close()
            mensaje("Los datos del paciente se actualizaron correctamente.")
            limpiarCampos()
            buscarPaciente()
        }catch(err: SQLiteException){
            mensaje("No se pudieron actualizar los datos del paciente")
        }
    }
    fun eliminar(id:String){
        try{
            var transaccion = basedatos.writableDatabase
            var SQL = "DELETE FROM PACIENTE WHERE ID_PAC="+id
            transaccion.execSQL(SQL)
            transaccion.close()
            mensaje("EL paciente se eliminó correctamente.")
            limpiarCampos()
            buscarPaciente()
        }catch(err: SQLiteException){
            mensaje("No se pudo eliminar el paciente")
        }
    }

    fun buscarPaciente(){
        try{
            var transaccion = basedatos.readableDatabase
            var SQL = "SELECT * FROM PACIENTE"
            var resultado = transaccion.rawQuery(SQL,null)
            var cadena=""
            while(resultado.moveToNext()){
                cadena = cadena + resultado.getString(0)+" - "+resultado.getString(1)+" - "+resultado.getString(2)+" - "+resultado.getString(3)+" - "+ resultado.getString(4)+" - "+resultado.getString(5)+"\n"
            }
            etiqueta?.setText(cadena)
        }catch(err: SQLiteException){
            mensaje("No se pudo realizar la consulta")
        }
    }
    fun limpiarCampos(){
        id?.setText("")
        nombre?.setText("")
        sexo?.setText("")
        edad?.setText("")
        peso?.setText("")
        estatura?.setText("")
    }
    fun mensaje(mensaje:String){
        Toast.makeText(this,mensaje, Toast.LENGTH_SHORT).show()
    }

    fun validarCamposVacios() : Boolean{
        return (nombre?.text.toString().isEmpty()||sexo?.text.toString().isEmpty()||edad?.text.toString().isEmpty() || peso?.text.toString().isEmpty() || estatura?.text.toString().isEmpty())
    }
}
