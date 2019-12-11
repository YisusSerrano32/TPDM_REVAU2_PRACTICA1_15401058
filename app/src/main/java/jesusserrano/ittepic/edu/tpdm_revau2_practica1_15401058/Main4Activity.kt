package jesusserrano.ittepic.edu.tpdm_revau2_practica1_15401058

import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class Main4Activity : AppCompatActivity() {

    var buscar : Button?=null
    var insertar : Button?=null
    var actualizar : Button?=null
    var eliminar : Button?=null
    var id : EditText?=null
    var idmed : EditText?=null
    var idpac : EditText?=null
    var fecha : EditText?=null
    var hora : EditText?=null
    var consultorio : EditText?=null
    var etiqueta : TextView?=null
    var regresar : Button?=null
    var basedatos = BD_Hospital(this, "Hospital",null,1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        buscar = findViewById(R.id.buscar_cita)
        insertar = findViewById(R.id.insertar_cita)
        actualizar = findViewById(R.id.actualizar_cita)
        eliminar = findViewById(R.id.eliminar_cita)
        id = findViewById(R.id.id_cita)
        idmed = findViewById(R.id.idmed)
        idpac = findViewById(R.id.idpac)
        fecha= findViewById(R.id.fecha_cita)
        hora = findViewById(R.id.hora_cita)
        consultorio = findViewById(R.id.consultorio_cita)
        etiqueta = findViewById(R.id.etiquetaCita)
        regresar = findViewById(R.id.regresar_cita)

        buscarCitas()

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
                insertar(idmed?.text.toString(), idpac?.text.toString(),fecha?.text.toString(),hora?.text.toString(),consultorio?.text.toString())
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
                AlertDialog.Builder(this).setTitle("ADVERTENCIA").setMessage("¿Estás seguro que deseas eliminar esta cita?")
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

    fun insertar(id_med: String, id_pac :String, fecha_cita :String, hora_cita:String, consult:String){
        try{
            var transaccion = basedatos.writableDatabase
            var SQL = "INSERT INTO CITA VALUES(null,'$id_med','$id_pac','$fecha_cita','$hora_cita','$consult')"
            transaccion.execSQL(SQL)
            transaccion.close()
            mensaje("Los datos de la cita se insertaron correctamente")
            limpiarCampos()
            buscarCitas()
        }catch(err: SQLiteException){
            mensaje("No se pudo insertar")
        }
    }
    fun buscarId(id:String){
        try{
            var transaccion = basedatos.readableDatabase
            var SQL = "SELECT * FROM CITA WHERE ID_CITA="+id
            var resultado = transaccion.rawQuery(SQL,null)
            if(resultado.moveToFirst()){
                idmed?.setText(resultado.getString(1))
                idpac?.setText(resultado.getString(2))
                fecha?.setText(resultado.getString(3))
                hora?.setText(resultado.getString(4))
                consultorio?.setText(resultado.getString(5))
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
            var SQL = "UPDATE CITA SET IDMEDICO='"+idmed?.text.toString()+"', IDPACIENTE='"+idpac?.text.toString()+"', FECHA='"+fecha?.text.toString()+"', HORA='"+hora?.text.toString()+"', CONSULTORIO ='"+consultorio?.text.toString()+"' WHERE ID_CITA="+id
            transaccion.execSQL(SQL)
            transaccion.close()
            mensaje("La cita se actualizó correctamente.")
            limpiarCampos()
            buscarCitas()
        }catch(err: SQLiteException){
            mensaje("No se pudo actualizar la cita")
        }
    }
    fun eliminar(id:String){
        try{
            var transaccion = basedatos.writableDatabase
            var SQL = "DELETE FROM CITA WHERE ID_CITA="+id
            transaccion.execSQL(SQL)
            transaccion.close()
            mensaje("La cita se eliminó correctamente.")
            limpiarCampos()
            buscarCitas()
        }catch(err: SQLiteException){
            mensaje("No se pudo eliminar la cita")
        }
    }

    fun buscarCitas(){
        try{
            var transaccion = basedatos.readableDatabase
            var SQL = "SELECT * FROM CITA"
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
        idmed?.setText("")
        idpac?.setText("")
        fecha?.setText("")
        hora?.setText("")
        consultorio?.setText("")
    }
    fun mensaje(mensaje:String){
        Toast.makeText(this,mensaje, Toast.LENGTH_SHORT).show()
    }

    fun validarCamposVacios() : Boolean{
        return (idmed?.text.toString().isEmpty()||idpac?.text.toString().isEmpty()||fecha?.text.toString().isEmpty() || hora?.text.toString().isEmpty() || consultorio?.text.toString().isEmpty())
    }

}
