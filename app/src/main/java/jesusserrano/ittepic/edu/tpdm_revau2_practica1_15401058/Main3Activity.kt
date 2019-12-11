package jesusserrano.ittepic.edu.tpdm_revau2_practica1_15401058

import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class Main3Activity : AppCompatActivity() {

    var buscar : Button?=null
    var insertar : Button?=null
    var actualizar : Button?=null
    var eliminar : Button?=null
    var id : EditText?=null
    var nombre : EditText?=null
    var cedula : EditText?=null
    var especialidad : EditText?=null
    var edad : EditText?=null
    var telefono : EditText?=null
    var etiqueta : TextView?=null
    var regresar : Button?=null
    var basedatos = BD_Hospital(this, "Hospital",null,1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        buscar = findViewById(R.id.buscar_med)
        insertar = findViewById(R.id.insertar_med)
        actualizar = findViewById(R.id.actualizar_med)
        eliminar = findViewById(R.id.eliminar_med)
        id = findViewById(R.id.id_med)
        nombre = findViewById(R.id.nombre_med)
        cedula = findViewById(R.id.cedula_med)
        especialidad = findViewById(R.id.especialidad_med)
        edad = findViewById(R.id.edad_med)
        telefono = findViewById(R.id.telefono_med)
        etiqueta = findViewById(R.id.etiquetaMedicos)
        regresar = findViewById(R.id.regresar_med)

        buscarMedicos()

        buscar?.setOnClickListener {
            if(id?.text.toString().isEmpty()) {
                mensaje("Ingresa el id del medico")
            }else{
                buscarId(id?.text.toString())
            }
        }
        insertar?.setOnClickListener {
            if(validarCamposVacios()){
                mensaje("Aun hay campos vacios")
            }else {
                insertar(nombre?.text.toString(), cedula?.text.toString(),especialidad?.text.toString(),edad?.text.toString(),telefono?.text.toString())
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
                mensaje("Ingresa el id del medico")
            }else{
                AlertDialog.Builder(this).setTitle("ADVERTENCIA").setMessage("¿Estás seguro que deseas eliminar a este medico?")
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


    fun insertar(nombre: String, cedula:String, especialidad :String, edad:String, telefono:String){
        try{
            var transaccion = basedatos.writableDatabase
            var SQL = "INSERT INTO MEDICO VALUES(null,'$nombre','$cedula','$especialidad','$edad','$telefono')"
            transaccion.execSQL(SQL)
            transaccion.close()
            mensaje("Los datos del medico se insertaron correctamente")
            limpiarCampos()
            buscarMedicos()
        }catch(err: SQLiteException){
            mensaje("No se pudo insertar")
        }
    }
    fun buscarId(id:String){
        try{
            var transaccion = basedatos.readableDatabase
            var SQL = "SELECT * FROM MEDICO WHERE ID_MED="+id
            var resultado = transaccion.rawQuery(SQL,null)
            if(resultado.moveToFirst()){
                nombre?.setText(resultado.getString(1))
                cedula?.setText(resultado.getString(2))
                especialidad?.setText(resultado.getString(3))
                edad?.setText(resultado.getString(4))
                telefono?.setText(resultado.getString(5))
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
            var SQL = "UPDATE MEDICO SET NOMBRE='"+nombre?.text.toString()+"', CEDULA='"+cedula?.text.toString()+"', ESPECIALIDAD='"+especialidad?.text.toString()+"', EDAD='"+edad?.text.toString()+"', TELEFONO ='"+telefono?.text.toString()+" WHERE ID_MED="+id
            transaccion.execSQL(SQL)
            transaccion.close()
            mensaje("El medico se actualizó correctamente.")
            limpiarCampos()
            buscarMedicos()
        }catch(err: SQLiteException){
            mensaje("No se pudo actualizar el medico")
        }
    }
    fun eliminar(id:String){
        try{
            var transaccion = basedatos.writableDatabase
            var SQL = "DELETE FROM MEDICO WHERE ID_MED="+id
            transaccion.execSQL(SQL)
            transaccion.close()
            mensaje("EL medico se eliminó correctamente.")
            limpiarCampos()
            buscarMedicos()
        }catch(err: SQLiteException){
            mensaje("No se pudo eliminar el medico")
        }
    }

    fun buscarMedicos(){
        try{
            var transaccion = basedatos.readableDatabase
            var SQL = "SELECT * FROM MEDICO"
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
        cedula?.setText("")
        especialidad?.setText("")
        edad?.setText("")
        telefono?.setText("")
    }
    fun mensaje(mensaje:String){
        Toast.makeText(this,mensaje, Toast.LENGTH_SHORT).show()
    }

    fun validarCamposVacios() : Boolean{
        return (nombre?.text.toString().isEmpty()||cedula?.text.toString().isEmpty()||especialidad?.text.toString().isEmpty() || edad?.text.toString().isEmpty() || telefono?.text.toString().isEmpty())
    }

}
