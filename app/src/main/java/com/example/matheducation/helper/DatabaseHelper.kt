import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.viewbinding.BuildConfig
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "mathdata.db"
        private const val DATABASE_VERSION = 1
        private const val DB_PATH = "/data/data/com.example.matheducation/databases/"
    }

    var database: SQLiteDatabase? = null

    init {
        // Create an empty database in the default system location
        context.openOrCreateDatabase(DATABASE_NAME, 0, null).close()
        try {
            copyDatabase(context)
        } catch (e: IOException) {
            throw RuntimeException("Error copying database", e)
        }
    }

    @Throws(IOException::class)
    private fun copyDatabase(context: Context) {
        val inputStream: InputStream = context.assets.open(DATABASE_NAME)
        val outputFile = DB_PATH + DATABASE_NAME
        val outputStream: OutputStream = FileOutputStream(outputFile)
        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } > 0) {
            outputStream.write(buffer, 0, length)
        }
        outputStream.flush()
        outputStream.close()
        inputStream.close()
    }

    fun openDatabase() {
        val dbPath = DB_PATH + DATABASE_NAME
        if (database == null || !database!!.isOpen) {
            database = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE)
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Database creation not needed here as the database is already copied
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database version upgrades if needed
    }

    // Add your custom methods for database operations here

    override fun close() {
        if (database != null) {
            database!!.close()
        }
        super.close()
    }
}
