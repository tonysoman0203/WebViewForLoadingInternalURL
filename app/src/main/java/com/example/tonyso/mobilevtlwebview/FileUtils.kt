package com.example.tonyso.mobilevtlwebview

import android.os.Environment
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by tony.so on 21/8/2017.
 */
class FileUtils {
    companion object {
        public fun getExternalSdCardPath () : String{
            var path:String? = null;
            var sdCardFillePath: File? = null;
            var sdCardPossiblePath:List<String> = Arrays.asList("external_sd","ext_sd", "external", "extSdCard");
            for ( sdCardPath in sdCardPossiblePath) {
                var file = File("/mnt/", sdCardPath);
                if(file.isDirectory && file.canWrite()){
                    path = file.absolutePath;
                    val timeStamp = SimpleDateFormat("ddMMyyyy_HHmmss".format(Date()));
                    val testCanWrite = File(path,"test_"+timeStamp);
                    if (testCanWrite.mkdirs()){
                        testCanWrite.delete();
                    }else{
                        path = null;
                    }
                }
            }
            if (path != null){
                sdCardFillePath = File(path);
            }else{
                sdCardFillePath = File(Environment.getExternalStorageDirectory().absolutePath);
            }
            return sdCardFillePath.absolutePath;
        }

        public fun getFile (path: String) : String{
            try{
                var file = File(path);
                var fileList = file.listFiles();
                for (ff in fileList) {
                    //Log.d(this.localClassName, ff.absolutePath);
                    if (ff.absolutePath.contains("20170817")){
                        return ff.absolutePath;
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
            return "";
        }
    }
}