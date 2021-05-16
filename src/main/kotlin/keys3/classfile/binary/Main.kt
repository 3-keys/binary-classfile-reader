package keys3.classfile.binary

import io.vlinx.logging.Logger
import org.apache.commons.io.FileUtils
import org.riversun.bigdoc.bin.BigFileSearcher
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import kotlin.system.exitProcess


fun main(args: Array<String>) {

    if (args.size < 2) {
        printUsage()
        exitProcess(1)
    }

    val path = args[0]
    val outputFolderPath = args[1]

    Logger.INFO("${Constants.APP_NAME} ${Constants.APP_VERSION}")
    Logger.INFO("Source file", path)
    Logger.INFO("Output folder", outputFolderPath)

    if (!File(path).exists()) {
        Logger.ERROR("$path doesn't exists")
        exitProcess(1)
    }

    if (File(path).isDirectory) {
        Logger.ERROR("$path is a direcotry")
        exitProcess(1)
    }

    val magic = byteArrayOf(0xca.toByte(), 0xfe.toByte(), 0xba.toByte(), 0xbe.toByte())

    Logger.INFO("Search classes in $path")
    val searcher = BigFileSearcher()
    val posList = searcher.searchBigFile(File(path), magic)

    if (!File(outputFolderPath).exists()) {
        FileUtils.forceMkdir(File(outputFolderPath))
    }

    for (pos in posList) {
        try {
            val fis = BufferedInputStream(FileInputStream(path))
            Logger.INFO("Analyze class data at $pos")
            fis.skip(pos)
            val classFile = JavaClassFile(fis)
            fis.close()
            val data = classFile.classReader.data.toByteArray()
            val className = classFile.thisClassName
            val destPath = "${outputFolderPath}${File.separator}${className}.class"
            Logger.INFO("Write $destPath")
            FileUtils.writeByteArrayToFile(File(destPath), data)
        } catch (e: Exception) {
            Logger.ERROR("Can't get valid class info at $pos")
        }

    }
}

fun printUsage() {
    println("binary-class-reader <source-file> <output-folder>")
}
