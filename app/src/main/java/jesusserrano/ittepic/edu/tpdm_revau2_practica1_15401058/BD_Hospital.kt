package jesusserrano.ittepic.edu.tpdm_revau2_practica1_15401058

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BD_Hospital(
    context : Context?,
    name : String?,
    cursor : SQLiteDatabase.CursorFactory?,
    version : Int
):SQLiteOpenHelper(context, name, cursor, version) {
    override fun onCreate(db:SQLiteDatabase?){
        db?.execSQL("CREATE TABLE PACIENTE (ID_PAC INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE VARCHAR(200), SEXO VARCHAR(20),EDAD INTEGER, PESO FLOAT,ESTATURA FLOAT)")
        db?.execSQL("CREATE TABLE MEDICO (ID_MED INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE VARCHAR(200), CEDULA VARCHAR(20),ESPECIALIDAD VARCHAR(50), EDAD INTEGER, TELEFONO INTEGER)")
        db?.execSQL("CREATE TABLE CITA (ID_CITA INTEGER PRIMARY KEY AUTOINCREMENT, IDMEDICO INTEGER, IDPACIENTE INTEGER, FECHA DATE, HORA TIME,CONSULTORIO VARCHAR(50), FOREIGN KEY (IDMEDICO) REFERENCES MEDICO(ID_MED), FOREIGN KEY (IDPACIENTE) REFERENCES PACIENTE(ID_PAC))")
    }
    override fun onUpgrade(db:SQLiteDatabase?, oldVersion: Int, newVersion: Int){

    }
}