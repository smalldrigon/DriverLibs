package com.grgbanking.baselibrary.util

/**
 * Author: gongxiaobiao
 * Date: on 2019/10/8 13:47
 * Email: 904430803@qq.com
 * Description:
 */
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import com.orhanobut.logger.CsvFormatStrategy
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.LogAdapter
import com.orhanobut.logger.LogStrategy
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class DamonDiskLogAdapter : LogAdapter {

    private val formatStrategy: FormatStrategy

    constructor(folder:File, maxFileSize: Int = 500 * 1024, maxFileCount:Int = 10) {


        val ht = HandlerThread("AndroidFileLogger.$folder")
        ht.start()

        val handler = DamonDiskLogStrategy.WriteHandler(ht.looper, folder, maxFileSize, maxFileCount)
        val logStrategy = DamonDiskLogStrategy(handler)

        formatStrategy = CsvFormatStrategy.newBuilder()
            .logStrategy(logStrategy)
            .build()
    }

    constructor(formatStrategy: FormatStrategy) {
        this.formatStrategy = formatStrategy
    }

    override fun isLoggable(priority: Int, tag: String?): Boolean {
        return true
    }

    override fun log(priority: Int, tag: String?, message: String) {
        formatStrategy.log(priority, tag, message)
    }
}


class DamonDiskLogStrategy( handler: Handler) : LogStrategy {


    private val handler: Handler

    init {
        this.handler = handler
    }

    override fun log(level: Int, tag: String?,  message: String) {
        checkNotNull(message)

        // do nothing on the calling thread, simply pass the tag/msg to the background thread
        handler.sendMessage(handler.obtainMessage(level, message))
    }

    internal class WriteHandler( looper: Looper,  folder: File, private val maxFileSize: Int, private val maxFileCount:Int = 0) : Handler(checkNotNull(looper)) {


        private val folder: File

        init {
            this.folder = folder
        }

        override fun handleMessage( msg: Message) {
            val content = msg.obj as String

            var fileWriter:FileWriter? = null

            try {

                fileWriter = getFileWriter()

                fileWriter.append(content)

                fileWriter.flush()
                //fileWriter.close()
            } catch (e: IOException) {
                if (fileWriter != null) {
                    try {
                        fileWriter.flush()
                        fileWriter.close()
                    } catch (e1: IOException) { /* fail silently */
                    }
                    fileWriter_ = null
                }
            }

        }



        var fileWriter_: FileWriter? = null
        fun getFileWriter(): FileWriter
        {
            if (fileWriter_ != null && logFile != null)
            {
                if (logFile!!.length() < maxFileSize) {
                    return fileWriter_!!
                }
            }

            try {
                fileWriter_?.close()
            }
            catch (e:Exception)
            {
            }

            val logFile = getNextLogFile(folder, "logs")
            fileWriter_ = FileWriter(logFile, true)

            return fileWriter_!!
        }

        var fileIndex = 0
        var logFile:File? = null
        private fun getNextLogFile( folder: File,  fileName: String): File {


            if (!folder.exists()) {
                //TODO: What if folder is not created, what happens then?
                folder.mkdirs()
            }

            if (maxFileCount != 0)
            {
                val subFiles = folder.listFiles()
                if (subFiles != null)
                {
                    if (subFiles.count() >= maxFileCount)
                    {
                        subFiles.sortBy { it.name }
                        for (i in 0..(subFiles.count() - maxFileCount))
                        {
                            subFiles[i].delete()
                        }
                    }
                }
            }

            logFile = File(folder, "${SimpleDateFormat("yyyy-MM-dd-hh.mm.ss").format(Date())}.$fileIndex.txt")
            fileIndex ++

            return logFile!!

        }
    }
}